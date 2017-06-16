package com.movision.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author shuxf
 * @Date 2017/6/16 11:48
 */
@Service
public class BackupsDBTask {

    private static final Logger logger = LoggerFactory.getLogger(BackupsDBTask.class);

    public void run() {
        logger.info("执行数据库备份文件上传至阿里云OSS静态资源服务器上传方法");

    }
}
