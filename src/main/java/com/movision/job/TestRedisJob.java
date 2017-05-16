package com.movision.job;

import com.movision.task.TestRedisTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zhuangyuhao
 * @Date 2017/5/16 11:17
 */
public class TestRedisJob {
    private static final Logger logger = LoggerFactory.getLogger(TestRedisJob.class);

    @Autowired
    TestRedisTask testRedisTask;


    public void execute() throws Exception {
        logger.info("TestRedisJob >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start!");

        testRedisTask.run();

        logger.info("TestRedisJob >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> end!");
    }
}
