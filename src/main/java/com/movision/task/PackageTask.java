package com.movision.task;

import com.movision.constants.ZhbConstant;
import com.movision.mybatis.dao.*;
import com.movision.mybatis.entity.*;
import com.movision.mybatis.entity.Package;
import com.movision.utils.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 套餐购买生效和失效处理
 *
 * @author zhuangyuhao
 * @date 2016年12月21日
 */
@Service
public class PackageTask {
    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private PackageBuyRecordDao buyRecordDao;

    @Autowired
    private PackageDao packageDao;

    @Autowired
    private PackageItemDao packageItemDao;

    @Autowired
    private VipMemberPrivilegeDao vipMemberPrivilegeDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private ZhbDao zhbDao;

    public void run() {
        logger.info("套餐购买生效和失效处理开始PackageJob...");
        doEffectivePackageBusiness();
        doIneffectivePackageBusiness();
        logger.info("套餐购买生效和失效处理结束PackageJob...");
    }

    /**
     * 处理生效的套餐
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void doEffectivePackageBusiness() {
        List<PackageBuyRecord> packageBuyRecords = buyRecordDao.findEffectivePackageBuyRecord();
        if (!CollectionUtils.isEmpty(packageBuyRecords)) {
            doPrivilegeBusiness(packageBuyRecords, 1);
            updateRecordStats(packageBuyRecords, 1);
        }

    }

    /**
     * 处理失效的套餐
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void doIneffectivePackageBusiness() {
        List<PackageBuyRecord> packageBuyRecords = buyRecordDao.findIneffectivePackageBuyRecord();
        if (!CollectionUtils.isEmpty(packageBuyRecords)) {
            doPrivilegeBusiness(packageBuyRecords, 2);
            updateRecordStats(packageBuyRecords, 2);
        }
    }

    /**
     * 更新
     *
     * @param packageBuyRecords
     */
    private void updateRecordStats(List<PackageBuyRecord> packageBuyRecords, int type) {
        for (PackageBuyRecord record : packageBuyRecords) {
            int status = type == 1 ? 3 : 4;
            record.setStatus(status);
            buyRecordDao.updateByPrimaryKeySelective(record);
        }
    }

    /**
     * 处理相关用户特权
     *
     * @param packageBuyRecords
     *            用户购买套餐记录
     * @param type
     *            1 生效处理 2 失效处理
     */
    private void doPrivilegeBusiness(List<PackageBuyRecord> packageBuyRecords, int type) {
        for (PackageBuyRecord record : packageBuyRecords) {
            Package pkg = packageDao.getPackage(record.getPackageId());
            if (null == pkg) {
                continue;
            }
            String[] itemsArr = pkg.getItemsId().split(",");

            int leftFbzwNum = 0;
            int leftXzjlNum = 0;
            for (String itemId : itemsArr) {
                PackageItem packageItem = packageItemDao.getPackageItem(itemId);
                int value = type == 1 ? packageItem.getValue() : -packageItem.getValue();
                PackageBuyRecord lastBuyRecord = buyRecordDao.getPackageByUserId(record.getBuyerId());
                // 同步用户对应的特权服务
                synPackagePrivilege(String.valueOf(record.getBuyerId()), packageItem.getType(), packageItem.getCode(), value, packageItem.getDescription(), record, lastBuyRecord);
                if (value < 0 && packageItem.getCode().equals("FBZW")) {
                    leftFbzwNum = -value - getUsedNum(lastBuyRecord, packageItem.getCode(), value, record);
                } else if (value < 0 && packageItem.getCode().equals("CXXZJL")) {
                    leftXzjlNum = -value - getUsedNum(lastBuyRecord, packageItem.getCode(), value, record);
                }
            }
            // 针对套餐到期 未使用的下载简历数和发布职位数 退还对应的筑慧币操作
            updateMemberAccount(record, leftFbzwNum, leftXzjlNum);
        }
    }

    /**
     * 同步套餐特权到用户特权表
     *
     * @param memberId
     *            用户id
     * @param type
     *            特权类型，1:有无,2:折扣率(value值需除以100),3:数量,4:库存
     * @param code
     *            特权代码
     * @param value
     *            特权值
     */
    public void synPackagePrivilege(String memberId, int type, String code, int value, String name, PackageBuyRecord record, PackageBuyRecord lastBuyRecord) {
        Map<String, Object> param = MapUtil.convert2HashMap("memberId", memberId, "pinyin", code);
        VipMemberPrivilege privilege = vipMemberPrivilegeDao.selectVipMemberPrivilege(param);
        if (null != privilege) {
            privilege.setUpdateTime(new Date());
            if (value < 0) {
                if (code.equals("FBZW") || code.equals("CXXZJL")) {
                    int usedNum = getUsedNum(lastBuyRecord, code, value, record);
                    privilege.setValue(privilege.getValue() - usedNum);
                }
            } else {
                privilege.setValue(privilege.getValue() + value);
            }
            privilege.setStock(privilege.getStock() + value);
            vipMemberPrivilegeDao.updateVipMemberPrivilegeValue(privilege);
        } else {
            privilege = new VipMemberPrivilege();
            privilege.setMemberId(Long.valueOf(memberId));
            privilege.setStock(Long.valueOf(value));
            privilege.setValue(Long.valueOf(value));
            privilege.setName(name);
            privilege.setPinyin(code);
            privilege.setType(String.valueOf(type));
            privilege.setAddTime(new Date());
            privilege.setUpdateTime(new Date());
            vipMemberPrivilegeDao.insertVipMemberPrivilege(privilege);
        }

    }

    /**
     * 获取该到期套餐已经使用的特权数量
     *
     * @param lastBuyRecord
     * @return
     */
    private int getUsedNum(PackageBuyRecord lastBuyRecord, String code, int value, PackageBuyRecord record) {
        Map query = new HashMap();
        int overLoadNum = 0;
        if (null != lastBuyRecord) {
            Package pkg = packageDao.getPackage(lastBuyRecord.getPackageId());
            String[] itemsArr = pkg.getItemsId().split(",");
            for (String itemId : itemsArr) {
                PackageItem packageItem = packageItemDao.getPackageItem(itemId);
                if ((packageItem.getCode().equals("CXXZJL") || packageItem.getCode().equals("FBZW")) && code.equals(packageItem.getCode())) {
                    query.put("activeTime", lastBuyRecord.getActiveTime());
                    query.put("expireTime", lastBuyRecord.getExpireTime());
                    query.put("userId", lastBuyRecord.getBuyerId());
                    // 套餐到期被占用的情况
                    int lastDownLoadCount = packageItem.getCode().equals("CXXZJL") ? jobDao.getResumeCount(query) : jobDao.getFbzwCount(query);
                    if (lastDownLoadCount > packageItem.getValue()) {
                        overLoadNum = lastDownLoadCount - packageItem.getValue();
                    }
                    if (overLoadNum < -value) {
                        query.put("activeTime", lastBuyRecord.getExpireTime());
                        query.put("expireTime", record.getExpireTime());
                        query.put("userId", record.getBuyerId());
                        int recCount = packageItem.getCode().equals("CXXZJL") ? jobDao.getResumeCount(query) : jobDao.getFbzwCount(query);
                        if (-value > (recCount + overLoadNum)) {
                            return recCount + overLoadNum;
                        } else {
                            return -value;
                        }
                    }
                    break;
                }
            }
        } else {
            Package pkg = packageDao.getPackage(record.getPackageId());
            String[] itemsArr = pkg.getItemsId().split(",");
            for (String itemId : itemsArr) {
                PackageItem packageItem = packageItemDao.getPackageItem(itemId);
                if ((packageItem.getCode().equals("CXXZJL") || packageItem.getCode().equals("FBZW")) && code.equals(packageItem.getCode())) {
                    query.put("activeTime", record.getActiveTime());
                    query.put("expireTime", record.getExpireTime());
                    query.put("userId", record.getBuyerId());
                    // 套餐到期被占用的情况
                    return packageItem.getCode().equals("CXXZJL") ? jobDao.getResumeCount(query) : jobDao.getFbzwCount(query);
                }
            }
        }
        return overLoadNum;
    }

    /***
     * 针对套餐到期 未使用的下载简历数和发布职位数 退还对应的筑慧币操作
     *
     * @param record
     *            用户购买的套餐对象
     * @param leftFbzwNum
     *            剩余的发布职位数量
     */
    private void updateMemberAccount(PackageBuyRecord record, int leftFbzwNum, int leftXzjlNum) {
        // 套餐到期未使用的套餐数量折合成筑慧币返还给对应用户
        Package p = packageDao.getPackage(record.getPackageId());
        BigDecimal zhbNum = new BigDecimal(leftXzjlNum + leftFbzwNum).subtract(new BigDecimal(20)).subtract(p.getPrice().divide(p.getOriginalPrice()));
        // 添加流水记录
        zhbDao.insertZhbRecord("0", record.getBuyerId(), record.getBuyerId(), zhbNum, ZhbConstant.ZhbRecordType.PREPAID.toString(), null, ZhbConstant.ZhbGoodsType.TCDQFH.toString());
        ZhbAccount account = zhbDao.getZhbAccount(record.getBuyerId());
        // 更新account
        account.setAmount(account.getAmount().add(zhbNum));
        zhbDao.updateZhbAccount(account);
    }

}
