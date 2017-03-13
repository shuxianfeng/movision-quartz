package com.movision.mybatis.subOrder.mapper;

import com.movision.mybatis.subOrder.entity.SubOrder;

import java.util.List;

public interface SubOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SubOrder record);

    int insertSelective(SubOrder record);

    SubOrder selectByPrimaryKey(Integer id);

    List<SubOrder> querySubOrdersList(int id);

    int updateByPrimaryKeySelective(SubOrder record);

    int updateByPrimaryKey(SubOrder record);
}