package com.movision.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author shuxf
 * @Date 2017/3/11 17:09
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CancelOrdersTask {
    private static final Logger logger = LoggerFactory.getLogger(CancelOrdersTask.class);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void run() {
        logger.info("轮训所有待支付的订单开始...");

        logger.info("轮训所有待支付的订单结束...");
    }
}
