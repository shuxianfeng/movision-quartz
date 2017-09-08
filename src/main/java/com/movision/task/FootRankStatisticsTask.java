package com.movision.task;

import com.movision.mybatis.footRank.entity.FootRank;
import com.movision.mybatis.user.entity.User;
import com.movision.mybatis.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        }

        log.info("统计更新所有用户足迹点总数task--end...");
    }
}
