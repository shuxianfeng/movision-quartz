package com.movision.job;

import com.movision.task.CancelOrdersTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/3/11 17:07
 */
public class CancelOrdersJob {
    private static final Logger logger = LoggerFactory.getLogger(CancelOrdersJob.class);

    @Autowired
    CancelOrdersTask cancelOrdersTask;


    public void execute() throws Exception {
        logger.info("CancelOrdersJob >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start!");

        cancelOrdersTask.run();

        logger.info("CancelOrdersJob >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> end!");
    }
}
