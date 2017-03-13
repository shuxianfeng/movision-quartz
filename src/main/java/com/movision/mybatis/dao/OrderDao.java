package com.movision.mybatis.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.movision.mybatis.entity.Order;
import com.movision.mybatis.mapper.OrderMapper;

/**
 * @author zhuangyuhao
 * @since 16/6/1.
 */
@Repository
public class OrderDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderDao.class);

    @Autowired
    OrderMapper mapper;


    /**
     * 根据订单状态获取的订单信息列表
     * @param types 商品类型
     */
    public List<Order> findWZFOrder(String[] types) {
        return mapper.findWZFOrder(types);
    }

    /**
     * 更新记录
     * @param record
     * @return
     */
    public int update(Order record){
        int count;
        count = mapper.updateByPrimaryKeySelective(record);
        if (count != 1) {
            logger.error("t_o_order:无更新数据");
        }
        return count;
    }

    /**
     * 批量更新
     * @param jobList
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchUpdateStatus(List<Map<String, String>> jobList) {

        for(Map<String,String> map: jobList){
            Order order = new Order();
            order.setOrderNo(map.get("orderNo"));
            order.setStatus(map.get("orderStatus"));
            order.setUpdateTime(new Date());
            order.setDescriptions("订单未支付超过失效时间");
            update(order);
        }
    }

    public List<Order> findWZFOrderByCourseId(Long courseid) {
            return mapper.findWZFOrderByCourseId(courseid);
    }

    public List<Order> findListByCourseIdAndStatus(String courseId , String status) {
        return mapper.findListByCourseIdAndStatus(courseId,status);
    }
}
