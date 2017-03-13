package com.movision.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.mapper.ShopMapper;

/**
 * 
 * @author zhuangyuhao
 * @time   2016年10月31日 下午4:23:47
 *
 */
@Repository
public class ShopDao {

	private static final Logger log = LoggerFactory.getLogger(ShopDao.class);
	
	@Autowired
	ShopMapper shopMapper;
	
	public List<Map<String,Object>> selectImg(){
    	try{
    		return shopMapper.selectImg();
    	}catch(Exception e){
    		log.error("查询商铺图片失败");
    		throw e;
    	}
    }
}
