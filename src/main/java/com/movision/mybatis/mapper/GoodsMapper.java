package com.movision.mybatis.mapper;

import com.movision.mybatis.goods.entity.Goods;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    void returnStock(Map<String, Object> parammap);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}