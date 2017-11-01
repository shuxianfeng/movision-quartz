package com.movision.job;

import com.movision.task.RobotTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/31 13:30
 */
public class RobotJob {
    private static final Logger logger = LoggerFactory.getLogger(RobotJob.class);

    @Autowired
    RobotTask robotTask;

    public void execute() throws Exception {
        logger.info("PostHeatValueJob start...");

        robotTask.run();

        logger.info("PostHeatValueJob end...");
    }
}
