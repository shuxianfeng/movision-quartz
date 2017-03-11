package com.movision.task;

import com.movision.constants.CourseConstants;
import com.movision.constants.OrderConstants;
import com.movision.constants.PayConstants;
import com.movision.mybatis.dao.OrderSmsDao;
import com.movision.mybatis.dao.PublishCourseDao;
import com.movision.mybatis.entity.Order;
import com.movision.mybatis.entity.OrderSms;
import com.movision.mybatis.entity.PublishCourse;
import com.movision.service.OrderService;
import com.movision.service.PwdticketService;
import com.movision.utils.ApplicationPropertiesUtils;
import com.movision.utils.sms.SDKSendSms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统自动终止课程
 * @author jianglz
 * @since 16/6/6.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CourseProcessTask {
    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);


    @Autowired
    PublishCourseDao courseDao;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderSmsDao orderSmsDao;

    @Autowired
    PwdticketService pwdticketService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void run(){
        logger.info("系统课程自动处理开始...");

        //系统自动开课  销售中--> 待开课 {到达购买截止日期 达到开课条件 : 购买数量>=最小开课条件}
        autoStartCourse();

        //到培训开始时间 系统自动开课  待开课--> 开课
        List<PublishCourse> startList = courseDao.findListByStartTime(CourseConstants.Status.DKK.toString());
        if(startList.size() > 0){
             courseDao.batchUpdateStatus(startList,CourseConstants.Status.KK.toString());
        }
        //到培训结束时间 系统自动结束课程  开课-->已完成
        List<PublishCourse> endList = courseDao.findListByEndTime(CourseConstants.Status.KK.toString());
        if(endList.size() > 0){
            courseDao.batchUpdateStatus(endList,CourseConstants.Status.YWC.toString());
            //SN码  有效--> 已过期
            pwdticketService.batchUpdateStatus(endList,OrderConstants.TicketStatus.YX.toString(),OrderConstants.TicketStatus.YGQ.toString());
        }

        logger.info("系统课程自动处理结束...");
    }

    /**
     * 系统自动开课    销售中--> 待开课 {到达购买截止日期 达到开课条件 : 购买数量>=最小开课条件}
     */
    private void autoStartCourse() {
        List<PublishCourse> list = courseDao.findListByCondition(CourseConstants.Status.XSZ.toString());
        List<PublishCourse> courseList = new ArrayList<>();
        for(PublishCourse course : list){
            if(course.getCourseType() == 1)  {
                String jsbuyNum =  courseDao.findBuyNumByCouserId(course.getCourseid(), OrderConstants.GoodsType.JSPX.toString());
                if(jsbuyNum!=null && Integer.valueOf(jsbuyNum) >=  Integer.valueOf(course.getMinBuyNumber())){
                    courseList.add(course);
                }
            }
            if(course.getCourseType() == 2) {
                String zjbuyNum =  courseDao.findBuyNumByCouserId(course.getCourseid(),OrderConstants.GoodsType.ZJPX.toString());
                if(zjbuyNum!=null &&Integer.valueOf(zjbuyNum) >=  Integer.valueOf(course.getMinBuyNumber())){
                    courseList.add(course);
                }
            }
        }
        if(courseList.size() > 0){
            courseDao.batchUpdateStatus(courseList,CourseConstants.Status.DKK.toString());
            //发送SN码短信   查询相关订单
            sendSNcodeSMS(courseList);

            //未支付订单处理  未支付-->已关闭
             orderService.findWZFOrderByCourseId(courseList);

            //SN码状态处理    未支付订单 {未生效-->已取消 }  已支付订单  未生效-->有效
            pwdticketService.batchUpdateStatus(courseList);

        }
    }


    /**
     * 已支付课程订单发送SNcode验证码短信
     * @param courseList
     */
    private void sendSNcodeSMS(List<PublishCourse> courseList) {
        List<Order> orderList = orderService.findListByCourseIdAndStatus(courseList, PayConstants.OrderStatus.YZF.toString());

        try {
            sendSMS(orderList, ApplicationPropertiesUtils.getValue("course_begin_sms_template_code"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送短信
     *
     * @param orderList 需要发短信的订单
     */
    public void sendSMS(List<Order> orderList, String template) throws Exception {

        if (orderList.size() > 0) {
            List<String> orderNos = new ArrayList<>();
            for (Order order : orderList) {
                //查询相关订单的短信记录(待发送状态)
                orderNos.add(order.getOrderNo());
            }

            //开课通知短信
            List<OrderSms> smsList = orderSmsDao.findByOrderNoAndStatusCode(
                    orderNos,
                    OrderConstants.SmsStatus.WAITING.toString(),
                    template);

            for (OrderSms sms : smsList) {
                //发送短信
                sendSms(sms, template);
            }
        } else {
            logger.error("无订单");
        }
    }

    /**
     * 发送短息
     *
     * @param sms
     */
    public void sendSms(OrderSms sms, String template) throws Exception {

        boolean suc = SDKSendSms.sendSMS(sms.getMobile(), sms.getContent(), template);
        OrderSms orderSms = new OrderSms();
        orderSms.setTemplateCode(template);
        orderSms.setMobile(sms.getMobile());
        orderSms.setOrderNo(sms.getOrderNo());
        if (suc) {//发送成功
            orderSms.setStatus(OrderConstants.SmsStatus.SUCCESS.toString());
            orderSms.setSendTime(new Date());
        } else {
            //设置发送状态为发送失败
            orderSms.setStatus(OrderConstants.SmsStatus.FAIL.toString());
            orderSms.setUpdateTime(new Date());
        }
        orderSmsDao.update(orderSms);
    }
}
