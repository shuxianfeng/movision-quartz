package com.movision.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.mapper.NewsMapper;

@Repository
public class NewsDao {

	private static final Logger log = LoggerFactory.getLogger(NewsDao.class);
	
	@Autowired
	NewsMapper newsMapper;
	
	public List<Map<String,Object>> selectImg(){
    	try{
    		return newsMapper.selectImg();
    	}catch(Exception e){
    		log.error("查询资讯图片失败");
    		throw e;
    	}
    }
}
