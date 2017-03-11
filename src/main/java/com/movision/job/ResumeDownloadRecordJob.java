package com.movision.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.task.ResumeDownloadRecordTask;

/**
 * 下载简历90天以后回收处理
 *
 * @author liyang
 * @date 2017年1月16日
 * 
 */
public class ResumeDownloadRecordJob {
    private static final Logger logger = LoggerFactory.getLogger(ResumeDownloadRecordJob.class);

    @Autowired
    private ResumeDownloadRecordTask downloadRecordTask;

    public void execute() throws Exception {
        logger.info("ResumeDownloadRecordJob start...");

        downloadRecordTask.run();

        logger.info("ResumeDownloadRecordJob end...");
    }

}
