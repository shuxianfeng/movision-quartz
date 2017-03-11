package com.movision.service;

import com.movision.constants.OrderConstants;
import com.movision.mybatis.dao.OrderDao;
import com.movision.mybatis.dao.OrderFlowDao;
import com.movision.mybatis.entity.Order;
import com.movision.mybatis.entity.PublishCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jianglz
 * @since 16/7/13.
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderFlowDao orderFlowDao;

    public void findWZFOrderByCourseId(List<PublishCourse> courseList) {
        for (PublishCourse course : courseList) {
            List<Order> orderList = orderDao.findWZFOrderByCourseId(course.getCourseid());
            if (orderList != null && orderList.size() > 0) {
                batchUpdateOrderStatus(orderList);
            }
        }
    }

    private void batchUpdateOrderStatus(List<Order> orderList) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Order order : orderList) {
            Map<String, String> map = new HashMap<>();
            map.put("orderNo", order.getOrderNo());
            map.put("orderStatus", OrderConstants.OrderStatus.CLOSED.toString());
            list.add(map);
        }

        if (list.size() > 0) {
            orderDao.batchUpdateStatus(list);
            orderFlowDao.batchUpdateStatus(list);
        }
    }

    public List<Order> findListByCourseIdAndStatus(List<PublishCourse> courseList, String status) {
        List<Order> orderList = new ArrayList<>();

        for (PublishCourse course : courseList) {
            List<Order> tempList;
            tempList = orderDao.findListByCourseIdAndStatus(String.valueOf(course.getCourseid()), status);
            orderList.addAll(tempList);
        }
        return orderList;
    }
}
