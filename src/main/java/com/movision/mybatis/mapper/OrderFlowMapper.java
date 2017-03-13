package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.OrderFlow;

public interface OrderFlowMapper {
    int insert(OrderFlow record);

    int insertSelective(OrderFlow record);

    int updateByOrderNo(OrderFlow record);
}