package com.movision.task;

import com.movision.utils.oss.AliOSSClient;
import com.movision.utils.propertiesLoader.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author shuxf
 * @Date 2017/6/16 11:48
 */
@Service
public class BackupsDBTask {

    private static final Logger logger = LoggerFactory.getLogger(BackupsDBTask.class);

    @Autowired
    private AliOSSClient aliOSSClient;

    public void run() {
        logger.info("执行数据库备份文件上传至阿里云OSS静态资源服务器上传方法");

        //获取配置文件中的待上传数据库备份文件路径
        String dir = PropertiesLoader.getValue("backups.db.domain");

        //根据时间计算数据库备份文件名，例：movision_20170616_020001.sql.gz
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String filename = "movision_" + sdf.format(date) + "_020001.sql";
        String path = dir + filename;
        File file = new File(path);

        //判断path路径下这个文件是否真实存在
        if (file.exists()) {
            logger.info("待上传文件的完整路径>>>>>>>>" + path);
            String type = "doc";
            String chann = "sql";
            aliOSSClient.uploadLocalFile(file, type, chann);
        }else {
            logger.info("无需要上传的文件");
        }
    }
}
