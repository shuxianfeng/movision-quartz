package com.movision.task;

import com.movision.mybatis.footRank.entity.FootRank;
import com.movision.mybatis.user.entity.User;
import com.movision.mybatis.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author shuxf
 * @Date 2017/8/24 10:20
 */
@Service
public class FootRankStatisticsTask {

    private static final Logger log = LoggerFactory.getLogger(FootRankStatisticsTask.class);

    @Autowired
    private UserService userService;

    public void run() {
        log.info("统计更新所有用户足迹点总数task--start...");

        //查询所有用户id
        List<User> useridList = userService.queryUserid();

        //循环所有userid
        for (int i=0; i<useridList.size(); i++){
            int userid = useridList.get(i).getId();
            String invitecode = useridList.get(i).getInvitecode();

            //统计用户足迹点个数并实时更新到数据库yw_foot_rank
            //根据userid查询足迹点个数
            int mapsum = userService.getfootmap(userid);
            //检查表中是否存在该用户的足迹总数记录
            int sum = userService.isFootmapSum(userid);
            FootRank footRank = new FootRank();
            footRank.setUserid(userid);
            footRank.setFootsum(mapsum);
            footRank.setIntime(new Date());
            if (mapsum > 0){
                if (sum > 0) {
                    //更新足迹数目
                    userService.update(footRank);
                }else {
                    //插入足迹数目记录
                    userService.insert(footRank);
                }
            }

            //统计用户关注数和粉丝数并实时更新到数据库yw_user
            int attention = userService.getAttentionSum(userid);
            int fans = userService.getFansSum(userid);
            User user = new User();
            user.setId(userid);
            user.setAttention(attention);
            user.setFans(fans);
            userService.updateAttentionFans(user);

            //统计用户邀请好友数并实时更新到数据库yw_user_invite_rank
            int invitenum = userService.getInviteNum(invitecode);
            //检查表中是否存在该用户的邀请总数记录
            int isum = userService.isInviteSum(userid);
            Map<String, Object> parammap = new HashMap<>();
            parammap.put("userid", userid);
            parammap.put("invitenum", invitenum);
            if (invitenum > 0){
                if (isum > 0){
                    //更新邀请总数记录
                    userService.updateUserInviteNum(parammap);
                }else {
                    //插入邀请总数记录
                    userService.insertUserInviteNum(parammap);
                }
            }
        }

        log.info("统计更新所有用户足迹点总数task--end...");
    }
}
