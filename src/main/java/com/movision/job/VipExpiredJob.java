package com.movision.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.task.VipExpiredTask;

/**
 *
 * @author zhuangyuhao
 *
 */
public class VipExpiredJob {
	private static final Logger logger = LoggerFactory.getLogger(VipExpiredJob.class);
	
	@Autowired
	VipExpiredTask vipExpiredTask;

    public void execute() throws Exception {
        logger.info("job start...");

        vipExpiredTask.run();

        logger.info("job end...");
    }
}
