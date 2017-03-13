package com.movision.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.task.ImgCompressTask;

public class ImgCompressJob {

	private static final Logger logger = LoggerFactory.getLogger(ImgCompressJob.class);

    @Autowired
    ImgCompressTask imgCompressTK;


    public void execute() throws Exception {
        logger.info("job start...");

        imgCompressTK.run();

        logger.info("job end...");
    }
}
