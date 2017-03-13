package com.movision.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.task.BrandAnalysisRecordBatchAddTask;

/**
 * @author zhuangyuhao
 * @date 2017/2/21 0021.
 */
public class BrandAnalysisRecordBatchAddJob {
    private static final Logger logger = LoggerFactory.getLogger(BrandAnalysisRecordBatchAddJob.class);

    @Autowired
    private BrandAnalysisRecordBatchAddTask task;

    public void execute() throws Exception {
        logger.warn("BrandAnalysisRecordBatchAddJob start...");

        task.run();

        logger.warn("BrandAnalysisRecordBatchAddJob end...");
    }
}
