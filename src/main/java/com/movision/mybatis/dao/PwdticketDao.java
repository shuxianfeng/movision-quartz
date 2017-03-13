package com.movision.mybatis.dao;

import com.movision.mybatis.entity.Pwdticket;
import com.movision.mybatis.mapper.PwdticketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author zhuangyuhao
 * @since 16/7/13.
 */
@Repository
public class PwdticketDao {
    private static final Logger logger = LoggerFactory.getLogger(PwdticketDao.class);

    @Autowired
    PwdticketMapper mapper;

    /**
     * 更新记录
     * @param record
     * @return
     */
    public int update(Pwdticket record){
        int count;
        count = mapper.updateByPrimaryKeySelective(record);
        if (count != 1) {
            logger.error("t_o_pwdticket:无更新数据");
        }
        return count;
    }

    public void batchUpdateStatus(List<Map<String, String>> ticketList) {
           for(Map<String,String> map : ticketList){
               Pwdticket pwdticket = new Pwdticket();
               pwdticket.setOrderNo(map.get("orderNo"));
               pwdticket.setStatus(Integer.valueOf(map.get("status")));
               update(pwdticket);
           }
    }

    public List<Map<String, String>> findByCourseId(Long courseid) {
        return mapper.findByCourseId(courseid);
    }
}
