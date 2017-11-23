package com.movision.job;

import com.movision.task.DauStatisticTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/11/22 17:10
 * 日活用户统计定时任务
 */
public class DauStatisticJob {

    private static final Logger logger = LoggerFactory.getLogger(DauStatisticJob.class);

    @Autowired
    private DauStatisticTask dauStatisticTask;

    public void execute() throws Exception {
        logger.info("开始统计前一天的日活用户数据");

        dauStatisticTask.run();

        logger.info("统计前一天的日活用户数据完成");
    }
}
