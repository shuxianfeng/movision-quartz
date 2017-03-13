package com.movision.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.mapper.ExhibitionMapper;

@Repository
public class ExhibitionDao {

	
	private static final Logger log = LoggerFactory.getLogger(ExhibitionDao.class);
	
	@Autowired
	ExhibitionMapper exMapper;
	
	public List<Map<String,Object>> selectImg(){
    	try{
    		return exMapper.selectImg();
    	}catch(Exception e){
    		log.error("查询活动图片失败");
    		throw e;
    	}
    }
}
