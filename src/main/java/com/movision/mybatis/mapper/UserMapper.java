package com.movision.mybatis.mapper;

import com.movision.mybatis.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    void updateUserInviteNum(Map<String, Object> parammap);

    void insertUserInviteNum(Map<String, Object> parammap);

    int getInviteNum(String invitecode);

    int isInviteSum(int userid);

    int updateByPrimaryKey(User record);

    List<User> queryUserid();

    int getfootmap(int userid);

    int isFootmapSum(int userid);

    int getAttentionSum(int userid);

    int getFansSum(int userid);
}