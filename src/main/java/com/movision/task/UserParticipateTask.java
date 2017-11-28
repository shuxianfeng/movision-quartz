package com.movision.task;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.service.PostService;
import com.movision.mybatis.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author shuxf
 * @Date 2017/11/28 9:50
 */
@Service
public class UserParticipateTask {

    private static final Logger log = LoggerFactory.getLogger(UserParticipateTask.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public void run() {
        log.info("统计前一天的用户互动活跃数据...start...");

        //获取前一天的日期
        Date intime = new Date();//当前日期和时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(intime);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        Date date = calendar.getTime();

        //统计前一天的新增帖子数
        List<Post> postIdList = postService.queryPostNumYesterday();

        //统计前一天被回复、被转发、被点赞或被收藏的帖子总数
        int activepost = postService.queryActivePostNum(postIdList);

        //统计前一天中产生回复的评论总数（对贴回复和对回复回复）(过滤掉机器人数据)
        int replynum = postService.queryReply();

        //统计前一天的点赞总数(过滤掉机器人数据)
        int zannum = postService.queryZan();

        //统计前一天的转发总数
        int forwardnum = postService.queryForward();

        //统计前一天的单次关注总数(过滤掉机器人数据)
        int follownum = userService.queryFollowNum();

        //统计前一天的互相关注总数
        int followdnum = userService.queryFollowdNum();

        //更新所有数据到数据库中
        Map<String, Object> parammap = new HashMap<>();
        parammap.put("date", date);
        parammap.put("newpost", postIdList.size());
        parammap.put("activepost", activepost);
        parammap.put("replynum", replynum);
        parammap.put("zannum", zannum);
        parammap.put("forwardnum", forwardnum);
        parammap.put("follownum", follownum);
        parammap.put("followdnum", followdnum);
        parammap.put("intime", intime);
        userService.insertUserParticipate(parammap);

        log.info("统计前一天的用户互动活跃数据...end...");
    }
}
