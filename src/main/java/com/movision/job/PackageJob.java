package com.movision.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.movision.task.PackageTask;

/**
 * 套餐购买生效和失效处理
 *
 * @author liyang
 * @date 2016年12月21日
 * 
 */
public class PackageJob {
    private static final Logger logger = LoggerFactory.getLogger(PackageJob.class);

    @Autowired
    private PackageTask packageTask;

    public void execute() throws Exception {
        logger.info("PackageJob start...");

        packageTask.run();

        logger.info("PackageJob end...");
    }

}
