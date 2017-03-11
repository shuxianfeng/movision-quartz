package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.OfferPrice;

import java.util.List;
import java.util.Map;

public interface OfferPriceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OfferPrice record);

    int insertSelective(OfferPrice record);

    OfferPrice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OfferPrice record);

    int updateByPrimaryKey(OfferPrice record);

    List<Map<String,String>> findLatestOfferPriceList(String hour);
}