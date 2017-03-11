package com.movision.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.mapper.BrandMapper;

@Repository
public class BrandDao {

	private static final Logger log = LoggerFactory.getLogger(BrandDao.class);
	
	@Autowired
	BrandMapper brandMapper;
	
	public List<Map<String,Object>> selectImg(){
    	try{
    		return brandMapper.selectImg();
    	}catch(Exception e){
    		log.error("查询品牌logo图片失败");
    		throw e;
    	}
    }
}
