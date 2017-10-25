package com.movision.job;

import com.movision.task.EverydayPostHeatvalueTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/24 17:37
 */
public class EverydayPostHeatvalueJob {

    private static final Logger logger = LoggerFactory.getLogger(EverydayPostHeatvalueJob.class);

    @Autowired
    EverydayPostHeatvalueTask everydayPostHeatvalueTask;


    public void execute() throws Exception {
        logger.info("EverydayPostHeatvalueJob >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start!");

        everydayPostHeatvalueTask.run();

        logger.info("EverydayPostHeatvalueJob >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> end!");
    }
}
