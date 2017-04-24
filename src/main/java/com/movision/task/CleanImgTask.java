package com.movision.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author shuxf
 * @Date 2017/4/24 13:34
 */
@Service
public class CleanImgTask {

    private static final Logger log = LoggerFactory.getLogger(CleanImgTask.class);

    public void run() {
        log.info("每日凌晨1点定时扫描最近7天上传的所有图片文件-------start");

        log.info("每日凌晨1点定时扫描最近7天上传的所有图片文件-------end");
    }
}
