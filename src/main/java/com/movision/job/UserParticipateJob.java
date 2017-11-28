package com.movision.job;

import com.movision.task.UserParticipateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/11/28 9:47
 * 互动活跃数据统计定时任务
 */
public class UserParticipateJob {

    private static final Logger logger = LoggerFactory.getLogger(UserParticipateJob.class);

    @Autowired
    private UserParticipateTask userParticipateTask;

    public void execute() throws Exception {
        logger.info("开始统计用户互动活跃数据");

        userParticipateTask.run();

        logger.error("统计用户互动活跃数据完成");
    }
}
