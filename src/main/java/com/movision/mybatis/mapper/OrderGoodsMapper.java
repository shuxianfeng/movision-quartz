package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.OrderGoods;

public interface OrderGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OrderGoods record);

    int insertSelective(OrderGoods record);

    OrderGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OrderGoods record);

    int updateByPrimaryKey(OrderGoods record);

    OrderGoods findByOrderNo(String orderNo);
}