package com.movision.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.task.ZhbExpiredNoticeTask;

/**
 * @author zhuangyuhao
 * @date 2017/1/13 0013.
 */
public class ZhbExpiredNoticeJob {
    private static final Logger logger = LoggerFactory.getLogger(ZhbExpiredNoticeJob.class);

    @Autowired
    private ZhbExpiredNoticeTask zhbExpiredNoticeTask;

    public void execute() throws Exception {
        logger.info("ZhbExpiredNoticeJob start...");

        zhbExpiredNoticeTask.run();

        logger.info("ZhbExpiredNoticeJob end...");
    }
}
