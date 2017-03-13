package com.movision.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.movision.mybatis.entity.Shop;

public interface ShopMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKey(Shop record);
    
    List<Map<String,Object>> selectImg();
}