package com.movision.job;

import com.movision.task.CleanScanAllImgTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/4/24 12:05
 * 每个月1号凌晨3点触发一次全文件扫描，清理往期所有未使用的垃圾图片文件
 */
public class CleanScanAllImgJob {

    private static final Logger logger = LoggerFactory.getLogger(CleanScanAllImgJob.class);

    @Autowired
    private CleanScanAllImgTask cleanScanAllImgTask;

    public void execute() throws Exception {

        logger.info("开始全文件扫描 CleanScanAllImg start...");

        cleanScanAllImgTask.run();

        logger.info("全文件扫描完成 CleanScanAllImg end...");
    }
}
