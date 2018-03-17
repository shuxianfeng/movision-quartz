package com.movision.job;

import com.movision.task.ActiveRefreshTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2018/3/13 11:35
 */
public class ActiveRefreshJob {
    private static final Logger logger = LoggerFactory.getLogger(ActiveRefreshJob.class);

    @Autowired
    ActiveRefreshTask activeRefreshTask;

    public void execute() throws Exception {
        logger.info("job start...");

        activeRefreshTask.run();

        logger.info("job end...");
    }
}
