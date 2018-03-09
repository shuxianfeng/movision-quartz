package com.movision.task;

import com.movision.mybatis.orders.entity.Orders;
import com.movision.mybatis.orders.service.OrdersService;
import com.movision.mybatis.subOrder.entity.SubOrder;
import com.movision.utils.HttpClientUtils;
import com.movision.utils.UUIDGenerator;
import com.movision.utils.propertiesLoader.PropertiesDBLoader;
import com.movision.utils.wechat.WechatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
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

    @Autowired
    private PropertiesDBLoader propertiesDBLoader;

    public void run() throws UnsupportedEncodingException {
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

                //增加小程序租赁支付的微信支付订单取消代码----------shuxf 2018/03/08
                //背景：现在商城租赁都搬到小程序上了，所以支付方式只有微信支付。
                //如果下单时间超时未支付，同步关闭微信平台的预支付订单，防止超时操作。
                CloseWepayOrder(noPayOrdersList.get(i));
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

    //取消微信平台的预支付订单
    public void CloseWepayOrder(Orders orders) throws UnsupportedEncodingException {
        //-----------------------------------------------------------------1.获取所有必要参数
        String key = propertiesDBLoader.getValue("secret");//一定要注意这里，这个不是小程序的secret，而是商户号平台中自己手动设置的秘钥
        String url = propertiesDBLoader.getValue("closeorder");//---------------微信关闭订单接口

        //小程序ID
        String appid = propertiesDBLoader.getValue("appid");
        //商户号
        String mch_id = propertiesDBLoader.getValue("mchid");
        //商户订单号，即美番平台订单id
        int out_trade_no = orders.getId();
        //随机字符串
        String nonce_str = UUIDGenerator.genUUIDRemoveSep(1)[0];//生成32位UUID随机字符串
        //签名
        String sign;
        //签名类型
        String sign_type = "MD5";

        //-----------------------------------------------------------------2.拼接签名前字符串
        StringBuffer strb = new StringBuffer();
        strb.append("appid=" + appid);
        strb.append("&mch_id=" + mch_id);
        strb.append("&nonce_str=" + nonce_str);
        strb.append("&out_trade_no=" + out_trade_no);
        strb.append("&sign_type=" + sign_type);
        strb.append("&key=" + key);//商户平台设置的密钥secret
        sign = WechatUtils.getSign(strb.toString());

        //-----------------------------------------------------------------3.封装入参map对象
        Map<String, Object> parammap = new HashMap<>();
        parammap.put("appid", appid);
        parammap.put("mch_id", mch_id);
        parammap.put("out_trade_no", out_trade_no);
        parammap.put("nonce_str", nonce_str);
        parammap.put("sign", sign);
        parammap.put("sign_type", sign_type);
        String xml = WechatUtils.map2XmlString(parammap);//转为微信服务器需要的xml格式

        log.info("xml>>>>>>>" + xml);

        //-------------------------------------------------------------------------4.请求微信取消预支付订单
        Map<String, String> resmap = HttpClientUtils.doPostByXML(url, xml, "utf-8");
        if (resmap.get("status").equals("200")) {
            log.info("请求微信取消预支付订单成功");
            log.info(resmap.get("result"));//返回客户端需要的数据
        }
    }
}