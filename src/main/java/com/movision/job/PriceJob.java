package com.movision.job;

import com.movision.task.PriceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author cxx
 * @since 16/8/3.
 */
public class PriceJob {
    private static final Logger logger = LoggerFactory.getLogger(PriceJob.class);

    @Autowired
    PriceTask priceTask;


    public void execute() throws Exception {
        logger.info("job start...");

        String hour = "18:30:00";

        priceTask.run(hour);

        logger.info("job end...");
    }


}
