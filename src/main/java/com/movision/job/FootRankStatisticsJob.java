package com.movision.job;

import com.movision.task.FootRankStatisticsTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/8/23 18:05
 * 1.用于每天凌晨定时统计所有用户足迹点总数，并将数值大于1的更新到足迹表中
 * 2.用于每天凌晨定时统计所有用户关注数粉丝数，并将数值实时更新到用户表中
 */
public class FootRankStatisticsJob {
    private static final Logger logger = LoggerFactory.getLogger(BackupsDBJob.class);

    @Autowired
    FootRankStatisticsTask footRankStatisticsTask;

    public void execute() throws Exception {
        logger.info("开始统计更新所有用户足迹点总数和用户的总关注数及粉丝数");

        footRankStatisticsTask.run();

        logger.info("统计更新所有用户足迹点总数和用户的总关注数及粉丝数完成");
    }
}
