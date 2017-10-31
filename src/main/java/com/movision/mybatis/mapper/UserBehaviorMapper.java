package com.movision.mybatis.mapper;

import com.movision.mybatis.userBehavior.entity.UserBehavior;

public interface UserBehaviorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBehavior record);

    int insertSelective(UserBehavior record);

    UserBehavior selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBehavior record);

    int updateByPrimaryKey(UserBehavior record);

    int IsHave(int userid);
}