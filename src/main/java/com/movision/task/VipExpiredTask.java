package com.movision.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.movision.utils.propertiesLoader.ApplicationPropertiesUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movision.mybatis.entity.VipMemberInfo;
import com.movision.service.VipInfoService;
import com.movision.utils.DateUtil;
import com.movision.utils.EmailUtil;

/**
 * VIP服务过期处理
 *
 * @author zhuangyuhao
 *
 */
@Service
public class VipExpiredTask extends BaseTask {
    private static final Logger logger = LoggerFactory.getLogger(VipExpiredTask.class);

    @Autowired
    private VipInfoService vipInfoService;

    public void run() {
        String mailTo = ApplicationPropertiesUtils.getValue("notice_receive_email");

        String curDateStr = DateUtil.date2Str(new Date(), "YYYY-MM-dd HH:mm:ss");
        String contentStart = EmailUtil.buildContent("VIP服务过期处理" + curDateStr, "开始执行");
        // 发送开始处理邮件
        EmailUtil.send(mailTo, contentStart, "VIP服务过期处理");

        int succNum = 0;
        int failedNum = 0;

        try {
            while (true) {
                List<VipMemberInfo> vipMemberList = vipInfoService.listExpiredVipMemberInfo(100);
                if (CollectionUtils.isEmpty(vipMemberList)) {
                    break;
                } else {
                    for (VipMemberInfo vipMember : vipMemberList) {
                        int targetVipLevel = vipMember.getVipLevel() > 100 ? 100 : 0;
                        Calendar cal = Calendar.getInstance();
                        vipMember.setVipLevel(targetVipLevel);
                        vipMember.setActiveTime(cal.getTime());

                        cal.add(Calendar.YEAR, 50);

                        vipMember.setExpireTime(cal.getTime());
                        int succ = vipInfoService.updateVipMemberInfoById(vipMember);
                        if (succ == 1) {
                            succNum++;
                        } else {
                            failedNum++;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("VipExpiredTask::run", e);
        } finally {
            String contentEnd = "处理成功：" + succNum + "，处理失败：" + failedNum;
            EmailUtil.send(mailTo, contentEnd, "VIP服务过期处理" + curDateStr + " 已结束");
        }
    }

}
