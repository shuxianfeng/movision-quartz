package com.movision.job;

import com.movision.task.CleanImgTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/4/24 10:20
 * 每天凌晨1点定时扫描最近一周帖子相关的图片
 * 清理所有上传后未使用到的图片文件
 */
public class CleanImgJob {

    private static final Logger logger = LoggerFactory.getLogger(CleanImgJob.class);

    @Autowired
    private CleanImgTask cleanImgTask;

    public void execute() throws Exception {

        logger.info("开始全文件扫描 CleanScanAllImg start...");

        cleanImgTask.run();

        logger.info("全文件扫描完成 CleanScanAllImg end...");
    }
}
