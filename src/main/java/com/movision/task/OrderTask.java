package com.movision.task;

import com.movision.constants.Constants;
import com.movision.constants.OrderConstants;
import com.movision.mybatis.dao.*;
import com.movision.mybatis.entity.Order;
import com.movision.mybatis.entity.OrderGoods;
import com.movision.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 未支付订单过期处理定时任务
 */
@Service
public class OrderTask {
    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderFlowDao orderFlowDao;

    @Autowired
    OrderGoodsDao orderGoodsDao;

    @Autowired
    PublishCourseDao publishCourseDao;

    @Autowired
    PwdticketDao pwdticketDao;

    @Autowired
    ActivityDao activityDao;

    @Autowired
    Constants constants;

    /**
     * 执行
     */
    public void run() throws Exception {
        logger.info("未支付订单过期处理开始....");

        List<Map<String, String>> jobList = new ArrayList<>();

        List<Map<String, String>> courseList = new ArrayList<>();

        List<Map<String, String>> ticketList = new ArrayList<>();

        List<Map<String, String>> activityList = new ArrayList<>();

        // 获取未支付订单 (购买VIP套餐 | 购买筑慧币)
        processZhbOrder(jobList);

        // 获取未支付订单 (培训课程购买 活动报名)
        processOrder(jobList, courseList, ticketList, activityList);

        logger.info("未支付订单过期处理结束....");

    }

    /**
     * VIP | 筑慧币 购买订单失效处理
     *
     * @param jobList
     * @throws ParseException
     */
    private void processZhbOrder(List<Map<String, String>> jobList) throws Exception {
        List<Order> orderList = orderDao.findWZFOrder(new String[] { OrderConstants.GoodsType.ZHB.toString(), OrderConstants.GoodsType.VIP.toString() });
        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String end = format.format(now);
        for (Order order : orderList) {
            Date dealTime = order.getDealTime();
            String start = format.format(dealTime);
            long day = DateUtil.getDistanceHours(start, end);
            Map<String, String> jobMap = new HashMap<>();
            if (day > constants.getZhbOrderInvalidTime()) { // 超过失效时间 设置订单状态为已失效
                                                            // 恢复订单商品库存数
                jobMap.put("orderNo", order.getOrderNo());
                jobMap.put("orderStatus", OrderConstants.OrderStatus.CLOSED.toString());
                jobList.add(jobMap);

            }
        }
        // 修改订单状态为已失效
        if (jobList.size() > 0) {
            setOrderStatusClosed(jobList);
        }

    }

    /**
     * 购买订单失效处理
     *
     * @param jobList
     * @param courseList
     * @throws ParseException
     */
    private void processOrder(List<Map<String, String>> jobList, List<Map<String, String>> courseList, List<Map<String, String>> ticketList, List<Map<String, String>> activityList)
            throws ParseException {
        List<Order> orderList = orderDao
                .findWZFOrder(new String[] { OrderConstants.GoodsType.JSPX.toString(), OrderConstants.GoodsType.ZJPX.toString(), OrderConstants.GoodsType.ACTIVITY_APPLY.toString() });

        Date now = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String end = format.format(now);
        for (Order order : orderList) {
            Date dealTime = order.getDealTime();
            String start = format.format(dealTime);
            Long min = DateUtil.getDistanceMinute(start, end);
            Map<String, String> jobMap = new HashMap<>();
            Map<String, String> courseMap = new HashMap<>();
            Map<String, String> activityMap = new HashMap<>();
            Map<String, String> ticketMap = new HashMap<>();

            if (min > constants.getCourseOrderInvalidTime()) { // 超过失效时间
                                                               // 设置订单状态为已失效
                                                               // 恢复订单商品库存数
                OrderGoods orderGoods = orderGoodsDao.findByOrderNo(order.getOrderNo());
                if (null == orderGoods) {
                    continue;
                }
                jobMap.put("orderNo", order.getOrderNo());
                jobMap.put("orderStatus", OrderConstants.OrderStatus.CLOSED.toString());
                jobList.add(jobMap);

                // 课程返还库存
                courseMap.put("courseid", String.valueOf(orderGoods.getGoodsId()));
                courseMap.put("number", String.valueOf(orderGoods.getNumber()));
                courseList.add(courseMap);

                // 活动报名人数返还
                if (order.getGoodsType().equals(OrderConstants.GoodsType.ACTIVITY_APPLY.toString())) {
                    activityMap.put("activityId", String.valueOf(orderGoods.getGoodsId()));
                    activityMap.put("number", String.valueOf(orderGoods.getNumber()));
                    activityList.add(activityMap);
                }

                // 培训密码券 设置已取消 4
                ticketMap.put("orderNo", order.getOrderNo());
                ticketMap.put("status", OrderConstants.TicketStatus.YQX.toString());
                ticketList.add(ticketMap);

            }
        }

        // 修改订单状态为已失效
        logger.error("jobList.size()>>>" + jobList.size());
        if (jobList.size() > 0) {
            setOrderStatusClosed(jobList);
        }

        // 修改密码券为已取消
        if (ticketList.size() > 0) {
            setTicketStatus(ticketList);
        }

        // 课程培训, 修改库存信息
        logger.error("courseList.size()>>>" + courseList.size());
        if (courseList.size() > 0) {
            publishCourseDao.batchUpdateAddStockNum(courseList);
        }

        // 活动报名
        logger.error("activityList.size()>>>" + activityList.size());
        if (activityList.size() > 0) {
            activityDao.batchUpdateAddApplyNum(activityList);
        }
    }

    /**
     * 设置密码券状态为已取消状态
     * 
     * @param ticketList
     */
    private void setTicketStatus(List<Map<String, String>> ticketList) {
        pwdticketDao.batchUpdateStatus(ticketList);
    }

    /**
     * 设置订单状态为[已关闭] t_o_order && t_o_order_flow
     *
     * @param jobList
     */
    private void setOrderStatusClosed(List<Map<String, String>> jobList) {

        orderDao.batchUpdateStatus(jobList);

        orderFlowDao.batchUpdateStatus(jobList);

    }
}
