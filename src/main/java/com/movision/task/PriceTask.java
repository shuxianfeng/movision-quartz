package com.movision.task;

import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import com.movision.mybatis.dao.MemberDao;
import com.movision.mybatis.dao.PriceDao;
import com.movision.utils.ApplicationPropertiesUtils;
import com.movision.utils.sms.SDKSendSms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 询报价短信通知处理
 * @author cxx
 * @since 16/6/6.
 */
@Service
public class PriceTask {
    private static final Logger logger = LoggerFactory.getLogger(PriceTask.class);

    @Autowired
    PriceDao priceDao;

    @Autowired
    MemberDao memberDao;

    public void run(String hour){
        logger.info("定向询价、公开询价动态的短信通知处理开始...");

        List<Map<String,String>> askResult = priceDao.findLatestAskPriceList(hour);

        sendAskSms(askResult,"ask_price_sms_template_code");

        logger.info("定向询价、公开询价动态的短信通知处理结束...");

        logger.info("报价动态的短信通知处理开始...");

        List<Map<String,String>> offerResult = priceDao.findLatestOfferPriceList(hour);

        sendOfferSms(offerResult,"offer_price_sms_template_code");

        logger.info("报价动态的短信通知处理结束...");

    }

    private void sendAskSms(List<Map<String,String>> result,String template){

        for(Map<String,String> memberMap:result){
            String receiveId = String.valueOf(memberMap.get("supplierid"));

            String[] idList = receiveId.split(",");

            for(String id:idList){
                Map<String,String> memberInfo = memberDao.findMobileByCreateid(id);

                String mobile = String.valueOf(memberInfo.get("mobile"));
                String name = String.valueOf(memberInfo.get("name"));
                if(StringUtils.isNullOrEmpty(name)){
                    name = "用户";
                }

                if(!StringUtils.isNullOrEmpty(mobile)){
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("name", name);
                    Gson gson = new Gson();
                    String json = gson.toJson(map);
                    SDKSendSms.sendSMS(mobile, json, ApplicationPropertiesUtils.getValue(template));
                }
            }
        }
    }

    private void sendOfferSms(List<Map<String,String>> result,String template){

        for(Map<String,String> memberMap:result){
            String receiveId = String.valueOf(memberMap.get("createid"));

            Map<String,String> memberInfo = memberDao.findMobileByCreateid(receiveId);

            String mobile = String.valueOf(memberInfo.get("mobile"));
            String name = String.valueOf(memberInfo.get("name"));
            if(StringUtils.isNullOrEmpty(name)){
                name = "用户";
            }

            if(!StringUtils.isNullOrEmpty(mobile)){
                Map<String, String> map = new LinkedHashMap<>();
                map.put("name", name);
                Gson gson = new Gson();
                String json = gson.toJson(map);
                SDKSendSms.sendSMS(mobile, json, ApplicationPropertiesUtils.getValue(template));
            }
        }
    }
}
