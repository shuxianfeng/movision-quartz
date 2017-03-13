package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.AskPrice;

import java.util.List;
import java.util.Map;

public interface AskPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AskPrice record);

    int insertSelective(AskPrice record);

    AskPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AskPrice record);

    int updateByPrimaryKey(AskPrice record);

    List<Map<String,String>> findLatestAskPriceList(String hour);
}