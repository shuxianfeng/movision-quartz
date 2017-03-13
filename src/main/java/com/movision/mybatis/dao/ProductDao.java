package com.movision.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.mapper.ProductMapper;

@Repository
public class ProductDao {
	private static final Logger log = LoggerFactory.getLogger(ProductDao.class);
	
	@Autowired
	ProductMapper productMapper;
	
	public List<Map<String,Object>> selectImg(){
    	try{
    		return productMapper.selectImg();
    	}catch(Exception e){
    		log.error("查询产品图片失败");
    		throw e;
    	}
    }
}
