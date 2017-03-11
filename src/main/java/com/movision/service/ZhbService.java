package com.movision.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.movision.constants.ZhbConstant;
import com.movision.mybatis.dao.MemberDao;
import com.movision.mybatis.dao.ZhbDao;
import com.movision.mybatis.entity.ZhbAccount;
import com.movision.mybatis.entity.ZhbRecord;
import com.movision.utils.MapUtil;
import com.movision.utils.sms.SDKSendSms;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tongxinglong
 * @date 2017/1/11 0011.
 */
@Transactional
@Service
public class ZhbService {

    @Autowired
    private ZhbDao zhbDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 查询大于ID的筑慧币账户信息
     *
     * @param startId
     * @return
     */
    public List<ZhbAccount> getZhbAccountList(Long startId) {
        return zhbDao.getZhbAccountList(startId);
    }

    /**
     * 查询即将到期的筑慧币充值记录
     * 
     * @param startId
     * @param expiredDays
     * @return
     */
    public List<ZhbRecord> getExpiredZhbRecordList(Long startId, int expiredDays) {
        return zhbDao.getExpiredZhbRecordList(startId, expiredDays);
    }

    /**
     * 查询有效的筑慧币总量
     * 
     * @param memberId
     * @return
     */
    public BigDecimal getTotalValidAmount(Long memberId, Date comparedTime) {
        return zhbDao.getTotalValidAmount(memberId, comparedTime);
    }

    /**
     * 成功处理到期数据则返回1，若没有到期筑慧币则返回0
     * 
     * @param account
     * @return
     */
    public int dealExpiredZhb(ZhbAccount account) {
        int result = 0;
        BigDecimal amount = getTotalValidAmount(account.getMemberId(), new Date());
        if (null == amount) {
            amount = BigDecimal.ZERO;
        }
        // 若账户金额大于有效的金额，则将多余金额处理掉
        if (account.getAmount().compareTo(amount) > 0) {
            BigDecimal expiredZhb = account.getAmount().subtract(amount);
            // 添加流水记录
            zhbDao.insertZhbRecord("0", account.getMemberId(), account.getMemberId(), expiredZhb, ZhbConstant.ZhbRecordType.PAYFOR.toString(), null, ZhbConstant.ZhbGoodsType.DQKC.toString());

            // 更新account
            account.setAmount(amount);
            result = zhbDao.updateZhbAccount(account);
        }

        return result;
    }

    /**
     * 发送筑慧币过期提醒
     * 
     * @param zhbRecord
     * @param days
     *            过期天数
     * @param templateCode
     *            短信模板ID
     * @param sentMobile
     *            已发送过的手机号码
     * @return
     */
    public int sendZhbExpiredNotice(ZhbRecord zhbRecord, int days, String templateCode, List<String> sentMobile) {
        int result = 0;
        Map<String, String> memberMobile = memberDao.findMobileByCreateid(zhbRecord.getBuyerId().toString());

        if (MapUtils.isNotEmpty(memberMobile)) {
            String mobile = memberMobile.get("mobile");
            if (StringUtils.isNotBlank(mobile) && !sentMobile.contains(mobile)) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, days);
                BigDecimal validAmount = getTotalValidAmount(zhbRecord.getBuyerId(), calendar.getTime());
                if (null == validAmount) {
                    validAmount = BigDecimal.ZERO;
                }

                ZhbAccount account = zhbDao.getZhbAccount(zhbRecord.getBuyerId());
                if (account.getAmount().compareTo(validAmount) > 0) {
                    BigDecimal expiredZhb = account.getAmount().subtract(validAmount);
                    if (expiredZhb.compareTo(BigDecimal.ZERO) > 0) {
                        String buyDate = new SimpleDateFormat("yyyy年MM月dd日").format((zhbRecord.getAddTime()));
                        String endDate = new SimpleDateFormat("yyyy年MM月dd日").format((calendar.getTime()));
                        Map paramMap = MapUtil.convert2LinkedHashMap("buyDate", buyDate, "recordAmount", zhbRecord.getAmount().toString(), "expiredZhb", expiredZhb.toString(), "endDate", endDate);
                        String params = JSONUtils.toJSONString(paramMap);

                        boolean isSucceed = SDKSendSms.sendSMS(mobile, params, templateCode);
                        if (isSucceed) {
                            sentMobile.add(mobile);
                            result = 1;
                        }
                    }

                }
            }

        }

        return result;
    }

}
