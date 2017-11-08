package com.movision.job;

import com.movision.task.QuartzPostTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/11/8 14:36
 * 1、临时功能：用于lotfer等其他平台峰量帖子导入时，模拟人为定时发布的情况
 */
public class QuartzPostJob {
    private static final Logger logger = LoggerFactory.getLogger(QuartzPostJob.class);

    @Autowired
    private QuartzPostTask quartzPostTask;

    public void execute() throws Exception {
        logger.info("开始轮训xml导入的峰量帖子模拟用户发帖");

        quartzPostTask.run();

        logger.info("开始轮训xml导入的峰量帖子模拟用户发帖完成");
    }
}
