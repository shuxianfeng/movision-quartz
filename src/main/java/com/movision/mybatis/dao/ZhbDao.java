package com.movision.mybatis.dao;

import com.movision.mybatis.entity.ZhbAccount;
import com.movision.mybatis.entity.ZhbRecord;
import com.movision.mybatis.mapper.ZhbAccountMapper;
import com.movision.mybatis.mapper.ZhbRecordMapper;
import com.movision.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 筑慧币持久层
 * 
 * @author tongxinglong
 * @date 2017/1/11 0011.
 */
@Repository
public class ZhbDao {
    @Autowired
    private ZhbAccountMapper zhbAccountMapper;
    @Autowired
    private ZhbRecordMapper zhbRecordMapper;

    /**
     * 更新筑慧币账户信息
     * 
     * @param zhbAccount
     */
    public int updateZhbAccount(ZhbAccount zhbAccount) {
        zhbAccount.setUpdateTime(new Date());
        return zhbAccountMapper.updateByPrimaryKeySelective(zhbAccount);
    }

    /**
     * 查询大于ID的筑慧币账户信息
     * 
     * @param startId
     * @return
     */
    public List<ZhbAccount> getZhbAccountList(Long startId) {
        return zhbAccountMapper.selectZhbAccountList(startId);
    }

    /**
     * 根据会员ID查询筑慧币账号信息
     * 
     * @param memberId
     * @return
     */
    public ZhbAccount getZhbAccount(Long memberId) {
        return zhbAccountMapper.selectByMemberId(memberId);
    }

    /**
     * 查询有效的筑慧币总量
     * 
     * @param memberId
     * @return
     */
    public BigDecimal getTotalValidAmount(Long memberId, Date comparedTime) {
        Map param = MapUtil.convert2HashMap("memberId", memberId, "comparedTime", comparedTime);
        return zhbRecordMapper.selectTotalValidAmount(param);
    }

    /**
     * 查询即将到期的筑慧币充值记录
     *
     * @param startId
     * @param expiredDays
     * @return
     */
    public List<ZhbRecord> getExpiredZhbRecordList(Long startId, int expiredDays) {
        Map param = MapUtil.convert2HashMap("startId", startId, "expiredDays", expiredDays,"expiredDays2", expiredDays + 1);

        return zhbRecordMapper.selectExpiredZhbRecordList(param);
    }

    /**
     * 添加筑慧币流水记录
     * 
     * @param orderNo
     * @param buyerId
     * @param operaterId
     * @param amount
     * @param type
     * @param goodsId
     * @param goodsType
     */
    public void insertZhbRecord(String orderNo, Long buyerId, Long operaterId, BigDecimal amount, String type, Long goodsId, String goodsType) {
        ZhbRecord zhbRecord = new ZhbRecord();
        zhbRecord.setOrderNo(orderNo);
        zhbRecord.setBuyerId(buyerId);
        zhbRecord.setOperaterId(operaterId);
        zhbRecord.setAmount(amount);
        zhbRecord.setStatus("1");
        zhbRecord.setType(type);
        zhbRecord.setGoodsId(goodsId);
        zhbRecord.setGoodsType(goodsType);

        Date curDate = new Date();
        zhbRecord.setAddTime(curDate);
        zhbRecord.setUpdateTime(curDate);
        zhbRecordMapper.insertSelective(zhbRecord);
    }
}
