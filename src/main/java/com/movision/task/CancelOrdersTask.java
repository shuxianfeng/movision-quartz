package com.movision.task;

import com.movision.mybatis.orders.entity.Orders;
import com.movision.mybatis.orders.service.OrdersService;
import com.movision.mybatis.subOrder.entity.SubOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author shuxf
 * @Date 2017/3/11 17:09
 */
@Service
@Transactional
public class CancelOrdersTask {

    private static final Logger log = LoggerFactory.getLogger(CancelOrdersTask.class);

    @Autowired
    private OrdersService ordersService;

    public void run() {
        log.info("轮训所有未支付完成订单的task开始...");

        //获取订单表中未支付完成的所有订单
        List<Orders> noPayOrdersList = ordersService.queryAllNoPayOrdersList();

        //轮训所有订单，检查时间
        for (int i = 0; i < noPayOrdersList.size(); i++) {
            //检查超过30分钟未支付的订单
            Date intime = noPayOrdersList.get(i).getIntime();
            Date now = new Date();
            long dif = now.getTime() - intime.getTime();//相差的毫秒数

            double result = dif * 1.0 / (1000 * 60);//分钟
            if (result > 30) {
                //如果下单时间超过30分钟，直接取消订单
                log.info("取消的订单id有====================================>" + noPayOrdersList.get(i).getId());
                CancelOrder(noPayOrdersList.get(i));
            }
        }

        log.info("轮训所有未支付完成订单的task结束...");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void CancelOrder(Orders orders) {

        //根据传入的订单id取消订单
        ordersService.CancelOrder(orders.getId());

        //如果有优惠券返还优惠券
        if (orders.getIsdiscount() == 1) {
            ordersService.returnCoupon(Integer.parseInt(orders.getCouponsid()));
        }

        //如果有积分返还积分并增加积分流水
        if (null != orders.getDispointmoney()) {
            Map<String, Object> parammap = new HashMap<>();
            parammap.put("userid", orders.getUserid());
            parammap.put("points", (int) (orders.getDispointmoney() * 100));
            parammap.put("orderid", orders.getId());

            ordersService.returnPoints(parammap);//根据积分优惠金额返还积分数
            ordersService.insertPointsRecord(parammap);//插入一条积分流水记录
        }

        //恢复商品库存
        //根据主订单id查询子订单表中该订单包含的商品明细列表
        List<SubOrder> subOrderList = ordersService.querySubOrdersList(orders.getId());
        //取库存恢复商品库存
        for (int i = 0; i < subOrderList.size(); i++) {
            int goodsid = subOrderList.get(i).getGoodsid();//商品id
            int sum = subOrderList.get(i).getSum();//件数

            Map<String, Object> parammap = new HashMap<>();
            parammap.put("goodsid", goodsid);
            parammap.put("sum", sum);
            ordersService.returnStock(parammap);
        }
    }
}
