package com.movision.task;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movision.mybatis.dao.StatDao;

/**
 * 定时统计平台业务数据
 * @author gmli
 *
 */
@Service
public class StatTask {
	private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);


    @Autowired
    StatDao statService;
	 /**
     * 执行
     */
    public void run() throws ParseException {
        logger.info("统计平台业务数据....");
        statService.platformStat();
    }
}
