package com.movision.mybatis.user.service;

import com.movision.mybatis.footRank.entity.FootRank;
import com.movision.mybatis.mapper.FootRankMapper;
import com.movision.mybatis.user.entity.User;
import com.movision.mybatis.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author shuxf
 * @Date 2017/8/24 10:37
 */
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    FootRankMapper footRankMapper;

    public List<User> queryUserid(){
        try {
            log.info("查询所有用户id");
            return userMapper.queryUserid();
        }catch (Exception e){
            log.error("查询所有用户id失败", e);
            throw e;
        }
    }

    public int getfootmap(int userid){
        try {
            log.info("获取当前用户的足迹点总数");
            return userMapper.getfootmap(userid);
        }catch (Exception e){
            log.error("获取当前用户的足迹点总数失败", e);
            throw e;
        }
    }

    public int isFootmapSum(int userid){
        try {
            log.info("检查表中是否存在该用户的足迹统计记录");
            return userMapper.isFootmapSum(userid);
        }catch (Exception e){
            log.error("检查表中是否存在该用户的足迹统计记录失败", e);
            throw e;
        }
    }

    public void update(FootRank footRank){
        try {
            log.info("更新用户足迹统计记录表");
            footRankMapper.update(footRank);
        }catch (Exception e){
            log.error("更新用户足迹统计记录表失败", e);
            throw e;
        }
    }

    public void insert(FootRank footRank){
        try {
            log.info("插入用户足迹统计记录表");
            footRankMapper.insert(footRank);
        }catch (Exception e){
            log.error("插入用户足迹统计记录表失败", e);
            throw e;
        }
    }
}
