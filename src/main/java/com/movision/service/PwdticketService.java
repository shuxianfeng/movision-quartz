package com.movision.service;

import com.movision.constants.OrderConstants;
import com.movision.mybatis.dao.PwdticketDao;
import com.movision.mybatis.entity.PublishCourse;
import com.movision.mybatis.entity.Pwdticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuangyuhao
 * @since 16/7/14.
 */
@Service
public class PwdticketService {

    @Autowired
    PwdticketDao pwdticketDao;

    /**
     * 更新课程相关订单     SN码的状态
     * 未支付订单 {未生效-->已取消 }  已支付订单  未生效-->有效
     *
     * @param courseList
     */
    public void batchUpdateStatus(List<PublishCourse> courseList) {
        List<Map<String, String>> upList = new ArrayList<>();

        for (PublishCourse course : courseList) {
            List<Map<String, String>> list = pwdticketDao.findByCourseId(course.getCourseid());

            for (Map<String, String> map : list) {
                String orderStatus = map.get("orderStatus");
                if (orderStatus.equals(OrderConstants.OrderStatus.WZF.toString())) {
                    Map<String, String> wzfMap = new HashMap<>();
                    wzfMap.put("ticketId", String.valueOf(map.get("ticketId")));
                    wzfMap.put("status", OrderConstants.TicketStatus.YQX.toString());
                    upList.add(wzfMap);

                } else if (orderStatus.equals(OrderConstants.OrderStatus.YZF.toString())) {
                    Map<String, String> yzfMap = new HashMap<>();
                    yzfMap.put("ticketId", String.valueOf(map.get("ticketId")));
                    yzfMap.put("status", OrderConstants.TicketStatus.YX.toString());
                    upList.add(yzfMap);
                }
            }
        }

        if (upList.size() > 0) {
            batchUpdate(upList);
        }
    }

    private void batchUpdate(List<Map<String, String>> upList) {
        for (Map<String, String> map : upList) {
            update(map);
        }
    }

    private void update(Map<String, String> map) {
        Pwdticket pwdtiket = new Pwdticket();
        pwdtiket.setTicketId(Long.valueOf(map.get("ticketId")));
        pwdtiket.setStatus(Integer.valueOf(map.get("status")));
        pwdticketDao.update(pwdtiket);
    }

    public void batchUpdateStatus(List<PublishCourse> endList, String oldStatus, String newStatus) {
        List<Map<String, String>> upList = new ArrayList<>();
        for (PublishCourse course : endList) {
            List<Map<String, String>> list = pwdticketDao.findByCourseId(course.getCourseid());
            for (Map<String, String> map : list) {
                String status = String.valueOf(map.get("status"));
                if (status.equals(oldStatus)) {
                    Map<String, String> tmpMap = new HashMap<>();
                    tmpMap.put("ticketId", String.valueOf(map.get("ticketId")));
                    tmpMap.put("status", newStatus);
                    upList.add(tmpMap);
                }
            }
        }

        if(upList.size() > 0){
            batchUpdate(upList);
        }
    }
}
