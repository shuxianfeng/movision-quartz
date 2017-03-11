package com.movision.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.movision.mybatis.entity.News;

public interface NewsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKeyWithBLOBs(News record);

    int updateByPrimaryKey(News record);
    
    List<Map<String,Object>> selectImg();
}