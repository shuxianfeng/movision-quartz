package com.movision.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.movision.mybatis.entity.Exhibition;

public interface ExhibitionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Exhibition record);

    int insertSelective(Exhibition record);

    Exhibition selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Exhibition record);

    int updateByPrimaryKeyWithBLOBs(Exhibition record);

    int updateByPrimaryKey(Exhibition record);
    
    List<Map<String,Object>> selectImg();
}