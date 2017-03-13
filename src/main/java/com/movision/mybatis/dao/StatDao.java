package com.movision.mybatis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.mapper.StatMapper;

/**
 * 统计dao
 * @author Administrator
 *
 */
@Repository
public class StatDao {
	 private static final Logger logger = LoggerFactory.getLogger(PublishCourseDao.class);
 
	 @Autowired
	 StatMapper statmapper; 

    /**
     * 统计平台业务数据 
     */
    public void platformStat() {
    	
    	 try {
    		 
    		 statmapper.platformStat();
    		 
    	 } catch (Exception e) {
             e.printStackTrace();
             logger.error(e.getMessage());
             throw e;
         }
    }
}
