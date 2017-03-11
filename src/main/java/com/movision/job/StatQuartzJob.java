package com.movision.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.task.StatTask;
/**
 * 定时统计平台业务数据
 * @author zhuangyuhao
 *
 */
public class StatQuartzJob {
	private static final Logger logger = LoggerFactory.getLogger(OrderQuartzJob.class);

    @Autowired
    StatTask statTask;

    public void execute() throws Exception {
        logger.info("statTask start...");

        statTask.run();

        logger.info("statTask end...");
    }
}
