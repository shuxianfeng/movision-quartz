package com.movision.job;

import com.movision.task.PackageTask;
import com.movision.task.PostHeatValueTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zhanglei
 * @Date 2017/7/14 19:57
 */
public class PostHeatValueJob {
    private static final Logger logger = LoggerFactory.getLogger(PostHeatValueJob.class);

    @Autowired
    private PostHeatValueTask postHeatValueTask;

    public void execute() throws Exception {
        logger.info("PostHeatValueJob start...");

        postHeatValueTask.run();

        logger.info("PostHeatValueJob end...");
    }

}
