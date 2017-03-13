package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String orderNo);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderNo);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> findWZFOrder(@Param("types") String[] types);

    List<Order> findListByCourseIdAndStatus(@Param("courseId") String courseId, @Param("status") String status);

    List<Order> findWZFOrderByCourseId(@Param("courseid") Long courseid);
}