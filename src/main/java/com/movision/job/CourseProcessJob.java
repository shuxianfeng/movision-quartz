package com.movision.job;

import com.movision.task.CourseProcessTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author zhuangyuhao
 * @since 16/6/6.
 */
public class CourseProcessJob {
    private static final Logger logger = LoggerFactory.getLogger(CourseProcessJob.class);

    @Autowired
    CourseProcessTask courseTask;


    public void execute() throws Exception {
        logger.info("job start...");

        courseTask.run();

        logger.info("job end...");
    }


}
