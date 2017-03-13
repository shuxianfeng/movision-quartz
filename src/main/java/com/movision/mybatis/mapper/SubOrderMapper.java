package com.movision.mybatis.mapper;

import com.movision.mybatis.subOrder.entity.SubOrder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SubOrder record);

    int insertSelective(SubOrder record);

    SubOrder selectByPrimaryKey(Integer id);

    List<SubOrder> querySubOrdersList(int id);

    int updateByPrimaryKeySelective(SubOrder record);

    int updateByPrimaryKey(SubOrder record);
}