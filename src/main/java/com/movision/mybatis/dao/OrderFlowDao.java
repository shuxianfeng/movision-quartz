package com.movision.mybatis.dao;

import com.movision.mybatis.entity.OrderFlow;
import com.movision.mybatis.mapper.OrderFlowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhuangyuhao
 * @since 16/6/17.
 */
@Repository
public class OrderFlowDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderFlowDao.class);

    @Autowired
    OrderFlowMapper mapper;


    /**
     * 更新记录
     * @param record
     * @return
     */
    public int update(OrderFlow record){
        int count;
        count = mapper.updateByOrderNo(record);
        if (count == 0) {
            logger.error("t_o_order_flow:无更新数据");
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
            OrderFlow orderFlow = new OrderFlow();
            orderFlow.setOrderNo(map.get("orderNo"));
            orderFlow.setTradeStatus(map.get("orderStatus"));
            orderFlow.setUpdateTime(new Date());
            update(orderFlow);
        }
    }
}
