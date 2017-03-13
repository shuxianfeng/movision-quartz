package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.Activity;
import org.apache.ibatis.annotations.Param;

public interface ActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    int updateAddApplyNum(@Param("activityId") Long activityId, @Param("number") Integer number);
}