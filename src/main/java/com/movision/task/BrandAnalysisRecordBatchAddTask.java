package com.movision.task;

import com.movision.mybatis.entity.BrandAnalysisRecord;
import com.movision.service.BrandAnalysisRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuangyuhao
 * @date 2017/2/21 0021.
 */
@Service
public class BrandAnalysisRecordBatchAddTask extends BaseTask {

    @Autowired
    private BrandAnalysisRecordService service;

    public void run() {
        try {
            List<BrandAnalysisRecord> recordList = service.getLastMonthRecords();
            if (CollectionUtils.isNotEmpty(recordList)) {
                for (BrandAnalysisRecord record : recordList) {
                    try {
                        service.cloneBrandAnalysisRecord(record);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
