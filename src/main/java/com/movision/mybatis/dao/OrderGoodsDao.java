package com.movision.mybatis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.entity.OrderGoods;
import com.movision.mybatis.mapper.OrderGoodsMapper;

/**
 * @author jianglz
 * @since 16/6/1.
 */
@Repository
public class OrderGoodsDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderGoodsDao.class);

    @Autowired
    OrderGoodsMapper mapper;


    /**
     * 根据订单编号查询订单商品详情
     * @param orderNo
     * @return
     */
    public OrderGoods findByOrderNo(String orderNo) {
        return mapper.findByOrderNo(orderNo);
    }
}
