package com.movision.task;

import java.util.Date;
import java.util.List;

import com.movision.mybatis.entity.ZhbAccount;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.service.ZhbService;
import com.movision.utils.ApplicationPropertiesUtils;
import com.movision.utils.DateUtil;
import com.movision.utils.EmailUtil;
import org.springframework.stereotype.Service;

/**
 * 筑慧币到期处理
 * 
 * @author tongxinglong
 * @date 2017/1/11 0011.
 */
@Service
public class ZhbExpiredTask extends BaseTask {

    private static final Logger logger = LoggerFactory.getLogger(ZhbExpiredTask.class);

    @Autowired
    private ZhbService zhbService;

    public void run() {

        String mailTo = ApplicationPropertiesUtils.getValue("notice_receive_email");

        String curDateStr = DateUtil.date2Str(new Date(), "YYYY-MM-dd HH:mm:ss");
        String contentStart = EmailUtil.buildContent("筑慧币过期处理" + curDateStr, "开始执行");
        // 发送开始处理邮件
        EmailUtil.send(mailTo, contentStart, "筑慧币过期处理");
        int succNum = 0;
        int failedNum = 0;

        try {
            Long startId = 999L;
            while (true) {
                List<ZhbAccount> accountList = zhbService.getZhbAccountList(startId);
                if (CollectionUtils.isEmpty(accountList)) {
                    break;
                }
                for (ZhbAccount account : accountList) {
                    try {
                        int result = zhbService.dealExpiredZhb(account);
                        startId = account.getId();

                        succNum += result;
                    } catch (Exception e) {
                        logger.error("筑慧币到期处理异常", e);
                        failedNum++;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("筑慧币到期处理异常", e);
        } finally {
            String contentEnd = "处理成功：" + succNum + "，处理失败：" + failedNum;
            EmailUtil.send(mailTo, contentEnd, "筑慧币过期处理" + curDateStr + " 已结束");
        }

    }
}
