package com.movision.mybatis.mapper;

import com.movision.mybatis.activePeriod.entity.ActivePeriod;
import com.movision.mybatis.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivePeriodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ActivePeriod record);

    int insertSelective(ActivePeriod record);

    ActivePeriod selectByPrimaryKey(Integer id);

    User queryUserByMaxPostNum();

    int queryPostNum(int userid);

    User queryUserByMaxZanNum();

    int queryZanNum(int userid);

    int updateByPrimaryKeySelective(ActivePeriod record);

    int updateByPrimaryKey(ActivePeriod record);
}