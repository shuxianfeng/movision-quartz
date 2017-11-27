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
import java.util.Map;

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

    public Integer updateUserHeatValue(Map map) {
        try {
            log.info("修改用户热度");
            return userMapper.updateUserHeatValue(map);
        } catch (Exception e) {
            log.error("修改用户热度失败", e);
            throw e;
        }
    }

    public void updateUserAttention(Integer userid) {
        try {
            log.info("增加用户关注总数");
            userMapper.updateUserAttention(userid);
        } catch (Exception e) {
            log.error("增加用户关注总数失败");
            throw e;
        }
    }

    public List<User> queryNotRepeatFollowRandomRobots(Map map) {
        try {
            log.info("查询不重复关注的用户机器人用户");
            return userMapper.queryNotRepeatRandomRobots(map);
        } catch (Exception e) {
            log.error("查询不重复关注的用户机器人用户", e);
            throw e;
        }
    }

    /**
     * 查询随机用户
     *
     * @param number
     * @return
     */
    public List<User> queryRandomUser(Integer number) {
        try {
            log.info("随机查询机器人");
            return userMapper.queryRandomUser(number);
        } catch (Exception e) {
            log.error("随机查询机器人异常", e);
            throw e;
        }
    }

    public List<User> queryNotRepeatCollectRobots(Map map) {
        try {
            log.info("查询不重复收藏帖子的机器人用户");
            return userMapper.queryNotRepeatCollectRobots(map);
        } catch (Exception e) {
            log.error("查询不重复收藏帖子的机器人用户", e);
            throw e;
        }
    }

    public int queryUserLevel(int userid) {
        try {
            log.info("查询用户等级");
            return userMapper.queryUserLevel(userid);
        } catch (Exception e) {
            log.error("查询用户等级失败", e);
            throw e;
        }
    }

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

    public int getAttentionSum(int userid){
        try {
            log.info("统计当前用户总关注数");
            return userMapper.getAttentionSum(userid);
        }catch (Exception e){
            log.error("统计当前用户总关注数失败", e);
            throw e;
        }
    }

    public int getFansSum(int userid){
        try {
            log.info("获取当前用户总粉丝数");
            return userMapper.getFansSum(userid);
        }catch (Exception e){
            log.error("获取当前用户总粉丝数失败", e);
            throw e;
        }
    }

    public void updateAttentionFans(User user){
        try {
            log.info("更新用户表中用户的关注数和粉丝总数");
            userMapper.updateByPrimaryKeySelective(user);
        }catch (Exception e){
            log.error("更新用户表中用户的关注数和粉丝总数失败");
            throw e;
        }
    }

    public int getInviteNum(String invitecode){
        try {
            log.info("根据用户id查询该用户的邀请总数");
            return userMapper.getInviteNum(invitecode);
        }catch (Exception e){
            log.error("根据用户id查询该用户的邀请总数失败", e);
            throw e;
        }
    }

    public int isInviteSum(int userid){
        try {
            log.info("判断表中是否存在该用户的邀请排行记录");
            return userMapper.isInviteSum(userid);
        }catch (Exception e){
            log.error("判断表中是否存在该用户的邀请排行记录失败", e);
            throw e;
        }
    }

    public void updateUserInviteNum(Map<String, Object> parammap){
        try {
            log.info("更新该用户的总邀请数");
            userMapper.updateUserInviteNum(parammap);
        }catch (Exception e){
            log.error("更新该用户的总邀请数失败", e);
            throw e;
        }
    }

    public void insertUserInviteNum(Map<String, Object> parammap){
        try {
            log.info("更新该用户的总邀请人数");
            userMapper.insertUserInviteNum(parammap);
        }catch (Exception e){
            log.error("更新该用户的总邀请人数失败", e);
            throw e;
        }
    }

    public List<User> queryNotRepeatZanRobots(Map map) {
        try {
            log.info("查询不重复点赞帖子的机器人用户");
            return userMapper.queryNotRepeatZanRobots(map);
        } catch (Exception e) {
            log.error("查询不重复点赞帖子的机器人用户", e);
            throw e;
        }
    }

    public List<User> queryNotRepeatCommentRobots(Map map) {
        return userMapper.queryNotRepeatCommentRobots(map);
    }

    public List<User> dauStatistic(){
        try {
            log.info("统计用户表中的前一天日活用户数");
            return userMapper.dauStatistic();
        }catch (Exception e){
            log.error("统计用户表中的前一天日活用户数失败", e);
            throw e;
        }
    }

    public int registeNumStatistic(){
        try {
            log.info("统计用户表中的以前一天注册用户总数");
            return userMapper.registeNumStatistic();
        }catch (Exception e){
            log.error("统计用户表中的以前一天注册用户总数失败", e);
            throw e;
        }
    }

    public int queryFollow(int id){
        try {
            log.info("查询该使用是否关注过圈子标签或作者");
            return userMapper.queryFollow(id);
        }catch (Exception e){
            log.error("查询该使用是否关注过圈子标签或作者失败", e);
            throw e;
        }
    }

    public int queryPost(int id){
        try {
            log.info("查询该用户前一天是否发过帖子");
            return userMapper.queryPost(id);
        }catch (Exception e){
            log.error("查询该用户前一天是否发过帖子失败", e);
            throw e;
        }
    }

    public int queryZan(int id){
        try {
            log.info("查询该用户前一天中是否点赞过评论或帖子");
            return userMapper.queryZan(id);
        }catch (Exception e) {
            log.error("查询该用户前一天中是否点赞过评论或帖子", e);
            throw e;
        }
    }

    public int queryCollect(int id){
        try {
            log.info("查询该用户前一天中是否收藏过帖子");
            return userMapper.queryCollect(id);
        }catch (Exception e){
            log.error("查询该用户前一天中是否收藏过帖子失败", e);
            throw e;
        }
    }

    public int queryComment(int id) {
        try {
            log.info("查询该用户前一天中是否评论过帖子或回复过评论");
            return userMapper.queryComment(id);
        }catch (Exception e){
            log.error("查询该用户前一天中是否评论过帖子或回复过评论失败", e);
            throw e;
        }
    }

    public int queryForward(int id){
        try {
            log.info("查询该用户前一天中是否转发过帖子");
            return userMapper.queryForward(id);
        }catch (Exception e){
            log.error("查询该用户前一天中是否转发过帖子失败", e);
            throw e;
        }
    }

    public void updateDauStatistic(Map<String, Object> parammap){
        try {
            log.info("更新日活用户统计数据到数据库");
            userMapper.updateDauStatistic(parammap);
        }catch (Exception e) {
            log.error("更新日活用户统计数据到数据库失败", e);
            throw e;
        }
    }


}
