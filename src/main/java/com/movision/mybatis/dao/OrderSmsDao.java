package com.movision.mybatis.dao;

import com.movision.mybatis.entity.OrderSms;
import com.movision.mybatis.mapper.OrderSmsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuangyuhao
 * @since 16/7/14.
 */
@Repository
public class OrderSmsDao {
    private static final Logger logger = LoggerFactory.getLogger(OrderGoodsDao.class);

    @Autowired
    OrderSmsMapper mapper;

    public List<OrderSms> findByOrderNoAndStatusCode(List<String> orderNos, String status, String templateCode) {
        List<OrderSms> smsList = null;
        try{
            smsList =   mapper.findByOrderNoAndStatusCode(orderNos,status,templateCode);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("t_o_sms:查询失败");
//            throw new BusinessException(10001,,"查询失败");
        }
        return  smsList;
    }


    /**
     * 更新记录
     * @param record
     */
    public void update(OrderSms record) {
        int count;
        try {
            count = mapper.updateByPrimaryKeySelective(record);
            if (count != 1) {
                logger.error("t_o_sms:更新数据失败");
//                throw new BusinessException(10002, "更新数据失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
//            throw new BusinessException(10003, "更新数据失败");
        }
    }
}
