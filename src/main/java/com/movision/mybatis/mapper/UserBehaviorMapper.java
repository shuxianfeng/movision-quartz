package com.movision.mybatis.mapper;

import com.movision.mybatis.userBehavior.entity.UserBehavior;

import java.util.Map;

public interface UserBehaviorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBehavior record);

    int insertSelective(UserBehavior record);

    UserBehavior selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Map map);

    int updateByPrimaryKey(UserBehavior record);

    int IsHave(int userid);
}