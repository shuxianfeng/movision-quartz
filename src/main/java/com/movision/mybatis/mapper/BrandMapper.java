package com.movision.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.movision.mybatis.entity.Brand;

public interface BrandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Brand record);

    int insertSelective(Brand record);

    Brand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Brand record);

    int updateByPrimaryKeyWithBLOBs(Brand record);

    int updateByPrimaryKey(Brand record);
    
    List<Map<String,Object>> selectImg();
}