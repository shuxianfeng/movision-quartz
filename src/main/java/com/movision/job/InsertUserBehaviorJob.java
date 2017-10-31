package com.movision.job;

import com.movision.task.InsertUserBehaviorTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zhanglei
 * @Date 2017/10/27 9:50
 */
public class InsertUserBehaviorJob {

    private static final Logger logger = LoggerFactory.getLogger(InsertUserBehaviorJob.class);

    @Autowired
    private InsertUserBehaviorTask insertUserBehaviorTask;

    public void execute() throws Exception {
        logger.info("InsertUserBehaviorJob start...");

        insertUserBehaviorTask.run();

        logger.info("InsertUserBehaviorJob end...");
    }

}
