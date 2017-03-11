package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.ZhbRecord;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhuangyuhao
 * @date 2017/1/11 0011.
 */
public interface ZhbRecordMapper {
    ZhbRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(ZhbRecord zhbRecord);

    int insertSelective(ZhbRecord zhbRecord);

    int updateByPrimaryKeySelective(ZhbRecord zhbRecord);

    int updateByPrimaryKey(ZhbRecord zhbRecord);

    /**
     * 查询有效的筑慧币总量
     * 
     * @param param
     * @return
     */
    BigDecimal selectTotalValidAmount(Map param);

    /**
     * 根据条件查询即将到期的筑慧币充值记录
     * 
     * @param param
     * @return
     */
    List<ZhbRecord> selectExpiredZhbRecordList(Map param);
}
