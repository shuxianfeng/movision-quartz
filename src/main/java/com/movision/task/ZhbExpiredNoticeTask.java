package com.movision.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movision.mybatis.entity.ZhbRecord;
import com.movision.service.ZhbService;
import com.movision.utils.propertiesLoader.ApplicationPropertiesUtils;
import com.movision.utils.DateUtil;
import com.movision.utils.EmailUtil;

/**
 * @author zhuangyuhao
 * @date 2017/1/11 0011.
 */
@Service
public class ZhbExpiredNoticeTask {
    private static final Logger logger = LoggerFactory.getLogger(ZhbExpiredNoticeTask.class);

    @Autowired
    private ZhbService zhbService;

    private static int[] expiredDaysArray = { 1, 3, 7, 15 };

    public void run() {

        String mailTo = ApplicationPropertiesUtils.getValue("notice_receive_email");
        String curDateStr = DateUtil.date2Str(new Date(), "YYYY-MM-dd HH:mm:ss");
        String contentStart = EmailUtil.buildContent("筑慧币到期 短信提醒程序" + curDateStr, "开始执行");
        // 发送开始处理邮件
        EmailUtil.send(mailTo, contentStart, "筑慧币到期 短信提醒程序");

        String templateCode = ApplicationPropertiesUtils.getValue("zhb_expired_sms_template_code");
        List<String> sentMobile = new ArrayList<>();

        for (int expiredDays : expiredDaysArray) {
            int succNum = 0;
            int failedNum = 0;
            Long startId = 0L;
            try {
                while (true) {
                    List<ZhbRecord> zhbRecordList = zhbService.getExpiredZhbRecordList(startId, expiredDays);
                    if (CollectionUtils.isEmpty(zhbRecordList)) {
                        break;
                    }
                    for (ZhbRecord zhbRecord : zhbRecordList) {
                        try {
                            int result = zhbService.sendZhbExpiredNotice(zhbRecord, expiredDays, templateCode, sentMobile);
                            succNum += result;
                        } catch (Exception e) {
                            logger.error("筑慧币到期提醒异常", e);
                            failedNum++;
                        } finally {

                            startId = null != zhbRecord.getId() ? zhbRecord.getId() : startId;
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("筑慧币到期提醒异常", e);
                failedNum++;
            } finally {
                String contentEnd = expiredDays + "天到期提醒，成功通知：" + succNum + "，失败：" + failedNum;
                EmailUtil.send(mailTo, contentEnd, "筑慧币到期提醒" + curDateStr + " 已结束");
            }
        }
    }
}
