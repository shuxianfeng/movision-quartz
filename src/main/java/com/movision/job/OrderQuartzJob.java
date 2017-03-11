package com.movision.job;

import com.movision.task.OrderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jianglz
 * @since 15/7/13.
 */
public class OrderQuartzJob {
    private static final Logger logger = LoggerFactory.getLogger(OrderQuartzJob.class);

    @Autowired
    OrderTask orderTask;

    public void execute() throws Exception {
        logger.info("job start...");

        orderTask.run();

        logger.info("job end...");
    }
}
