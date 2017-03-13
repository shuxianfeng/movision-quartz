package com.movision.job;

import com.movision.task.CourseTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author zhuangyuhao
 * @since 16/6/6.
 */
public class CourseJob {
    private static final Logger logger = LoggerFactory.getLogger(CourseJob.class);

    @Autowired
    CourseTask courseTask;


    public void execute() throws Exception {
        logger.info("CourseJob start...");

        courseTask.run();

        logger.info("CourseJob end...");
    }


}
