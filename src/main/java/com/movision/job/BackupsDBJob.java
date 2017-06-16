package com.movision.job;

import com.movision.task.BackupsDBTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/6/16 11:41
 * 用于每天定时上传数据库定时备份文件到阿里云OSS静态资源服务器
 */
public class BackupsDBJob {

    private static final Logger logger = LoggerFactory.getLogger(BackupsDBJob.class);

    @Autowired
    BackupsDBTask backupsDBTask;

    public void execute() throws Exception {
        logger.info("上传数据库备份文件到阿里云start");

        backupsDBTask.run();

        logger.info("上传数据库备份文件到阿里云end");
    }
}
