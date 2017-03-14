package com.movision.mybatis.orders.service;

import com.movision.mybatis.mapper.GoodsMapper;
import com.movision.mybatis.orders.entity.Orders;
import com.movision.mybatis.mapper.OrdersMapper;
import com.movision.mybatis.subOrder.entity.SubOrder;
import com.movision.mybatis.mapper.SubOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhuangyuhao
 * @Date 2017/3/13 13:31
 */
@Service
public class OrdersService {
    private static final Logger log = LoggerFactory.getLogger(OrdersService.class);

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public SubOrderMapper subOrderMapper;

    @Autowired
    public GoodsMapper goodsMapper;

    public List<Orders> queryAllNoPayOrdersList() {
        try {
            log.info("获取订单表中所有未完成支付的订单");
            return ordersMapper.queryAllNoPayOrdersList();
        } catch (Exception e) {
            log.error("获取订单表中所有未完成支付的订单失败");
            throw e;
        }
    }

    public void CancelOrder(int id) {
        try {
            log.info("根据订单id取消主订单");
            ordersMapper.CancelOrder(id);
        } catch (Exception e) {
            log.info("根据订单id取消主订单失败");
            throw e;
        }
    }

    public void returnCoupon(int couponid) {
        try {
            log.info("根据优惠券id返还优惠券");
            ordersMapper.returnCoupon(couponid);
        } catch (Exception e) {
            log.error("根据优惠券id返还优惠券失败");
            throw e;
        }
    }

    public void returnPoints(Map<String, Object> parammap) {
        try {
            log.info("根据积分优惠金额返还积分");
            ordersMapper.returnPoints(parammap);
        } catch (Exception e) {
            log.error("根据积分优惠金额返还积分失败");
            throw e;
        }
    }

    public void insertPointsRecord(Map<String, Object> parammap) {
        try {
            log.info("插入积分返还流水");
            ordersMapper.insertPointsRecord(parammap);
        } catch (Exception e) {
            log.error("插入积分返还流水失败");
            throw e;
        }
    }

    public List<SubOrder> querySubOrdersList(int id) {
        try {
            log.info("根据主订单id查询子订单列表");
            return subOrderMapper.querySubOrdersList(id);
        } catch (Exception e) {
            log.error("根据主订单id查询子订单列表失败");
            throw e;
        }
    }

    public void returnStock(Map<String, Object> parammap) {
        try {
            log.info("恢复商品库存");
            goodsMapper.returnStock(parammap);
        } catch (Exception e) {
            log.error("恢复商品库存失败");
            throw e;
        }
    }
}
