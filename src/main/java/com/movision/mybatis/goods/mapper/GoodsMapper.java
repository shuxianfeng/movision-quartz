package com.movision.mybatis.goods.mapper;

import com.movision.mybatis.goods.entity.Goods;

import java.util.Map;

public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    void returnStock(Map<String, Object> parammap);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}