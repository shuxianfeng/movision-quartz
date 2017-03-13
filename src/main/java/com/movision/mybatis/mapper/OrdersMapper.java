package com.movision.mybatis.mapper;

import com.movision.mybatis.orders.entity.Orders;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrdersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Integer id);

    List<Orders> queryAllNoPayOrdersList();

    void CancelOrder(int id);

    void returnCoupon(int couponid);

    void returnPoints(Map<String, Object> parammap);

    void insertPointsRecord(Map<String, Object> parammap);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);
}