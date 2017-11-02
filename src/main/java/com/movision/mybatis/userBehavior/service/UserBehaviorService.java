package com.movision.mybatis.userBehavior.service;


import com.movision.mybatis.mapper.UserBehaviorMapper;
import com.movision.mybatis.userBehavior.entity.UserBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserBehaviorService {

    private static final Logger log = LoggerFactory.getLogger(UserBehaviorService.class);

    @Autowired
    UserBehaviorMapper userBehaviorMapper;

    public int IsHave(int userid){
        try {
            log.info("分析表中有没有该用户");
            return  userBehaviorMapper.IsHave(userid);
        }catch (Exception e){
            log.error("分析表中有没有该用户失败");
            throw e;
        }

    }

    public int insertSelective(UserBehavior userBehavior){
        try {
            log.info("插入成功");
            return  userBehaviorMapper.insertSelective(userBehavior);
        }catch (Exception e){
            log.error("插入失败");
            throw e;
        }

    }


    public int updateByPrimaryKeySelective(Map map){
        try {
            log.info("修改成功");
            return  userBehaviorMapper.updateByPrimaryKeySelective(map);
        }catch (Exception e){
            log.error("修改失败");
            throw e;
        }

    }


}