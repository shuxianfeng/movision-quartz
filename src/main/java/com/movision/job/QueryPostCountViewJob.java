package com.movision.job;

import com.movision.task.QueryPostCountViewTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zhanglei
 * @Date 2017/11/10 14:04
 */
public class QueryPostCountViewJob {

    private static final Logger logger = LoggerFactory.getLogger(QueryPostCountViewJob.class);

    @Autowired
    private QueryPostCountViewTask queryPostCountViewTask;

    public void execute() throws Exception {
        logger.info("QueryPostCountViewTask start...");

        queryPostCountViewTask.run();

        logger.info("QueryPostCountViewTask end...");
    }

}
