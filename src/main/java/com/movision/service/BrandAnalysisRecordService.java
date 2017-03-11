package com.movision.service;

import com.movision.mybatis.dao.BrandAnalysisRecordDao;
import com.movision.mybatis.entity.BrandAnalysisRecord;
import com.movision.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author tongxinglong
 * @date 2017/2/21 0021.
 */
@Service
@Transactional
public class BrandAnalysisRecordService {

    @Autowired
    private BrandAnalysisRecordDao brandAnalysisRecordDao;

    /**
     * 获取上月的数据
     * 
     * @return
     */
    public List<BrandAnalysisRecord> getLastMonthRecords() {
        Date date = DateUtil.getCurDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date reportTimeB = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date reportTimeA = calendar.getTime();

        return brandAnalysisRecordDao.getRecordsByReportTime(reportTimeA, reportTimeB);
    }

    /**
     * 复制数据，生成当前月的初始数据
     * 
     * @param sourceRecord
     * @return
     */
    public int cloneBrandAnalysisRecord(BrandAnalysisRecord sourceRecord) {
        if (null != sourceRecord) {
            BrandAnalysisRecord record = new BrandAnalysisRecord();
            BeanUtils.copyProperties(sourceRecord, record);

            Date curDate = DateUtil.getCurDate();
            record.setId(null);
            record.setReportTime(curDate);
            record.setAddTime(curDate);
            record.setUpdateTime(curDate);
            record.setLastRank(sourceRecord.getRank());
            record.setStatus(0);
            BrandAnalysisRecord lastRecord = brandAnalysisRecordDao.getLastRecordByAnalysisId(record.getAnalysisId());
            if (null == lastRecord || lastRecord.getId().compareTo(sourceRecord.getId()) == 0) {
                brandAnalysisRecordDao.insert(record);
            } else {
                record.setId(lastRecord.getId());
                brandAnalysisRecordDao.updateByPrimaryKeySelective(record);
            }

        }
        return 0;
    }
}
