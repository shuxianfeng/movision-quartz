package com.movision.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.task.ZhbExpiredTask;

/**
 * @author zhuangyuhao
 * @date 2017/1/13 0013.
 */
public class ZhbExpiredJob {
    private static final Logger logger = LoggerFactory.getLogger(ZhbExpiredJob.class);

    @Autowired
    private ZhbExpiredTask zhbExpiredTask;

    public void execute() throws Exception {
        logger.warn("ZhbExpiredJob start...");

        zhbExpiredTask.run();

        logger.warn("ZhbExpiredJob end...");
    }
}
