package com.movision.task;

import com.movision.service.TestRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author zhuangyuhao
 * @Date 2017/5/16 11:18
 */
@Service
@Transactional
public class TestRedisTask {

    private static final Logger log = LoggerFactory.getLogger(TestRedisTask.class);

    @Autowired
    private TestRedisService testRedisService;

    public void run() {
        log.info("轮训所有未支付完成订单的task开始...");

        testRedisService.deleteRedisKey();

        log.info("轮训所有未支付完成订单的task结束...");
    }
}
