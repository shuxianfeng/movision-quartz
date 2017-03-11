package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.OrderSms;
import com.movision.mybatis.entity.OrderSmsKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderSmsMapper {
    int deleteByPrimaryKey(OrderSmsKey key);

    int insert(OrderSms record);

    int insertSelective(OrderSms record);

    OrderSms selectByPrimaryKey(OrderSmsKey key);

    int updateByPrimaryKeySelective(OrderSms record);

    int updateByPrimaryKey(OrderSms record);

    List<OrderSms> findByOrderNoAndStatusCode(@Param("orderNos") List<String> orderNos,
                                              @Param("status") String status, @Param("templateCode") String templateCode);
}