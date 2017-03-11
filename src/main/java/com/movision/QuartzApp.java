package com.movision;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 本地调试启动
 * @author zhuangyuhao
 * @since 15/7/13.
 */
public class QuartzApp {
    private static final Logger logger = LoggerFactory.getLogger(QuartzApp.class);

    public static void main(String[] args) throws Exception {
        logger.info("quartz app start...");
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");

        //如果配置文件中将startQuertz bean的lazy-init设置为false 则不用实例化
    }
}
