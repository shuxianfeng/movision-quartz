package com.movision.task;

import com.movision.mybatis.user.entity.User;
import com.movision.mybatis.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author shuxf
 * @Date 2017/11/22 17:18
 */
@Service
public class DauStatisticTask {

    private static final Logger log = LoggerFactory.getLogger(DauStatisticTask.class);

    @Autowired
    private UserService userService;

    public void run() {
        log.info("统计前一天的日活用户数据...start...");

        //统计前一天的日活用户数据
        List<User> activeUserList = userService.dauStatistic();
        //存入数据库中
        Map<String, Object> parammap = new HashMap<>();

        //获取前一天的日期
        Date intime = new Date();//当前日期和时间
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(intime);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        Date date = calendar.getTime();

        //统计前一天的注册总数
        int registenum = userService.registeNumStatistic();

        //统计前一天的有效活跃用户总数
        //有效的定义：关注 或 发帖 或 点赞 或 收藏 或 评论 或 分享（转发）
        int validsum = 0;
        for (int i = 0; i < activeUserList.size(); i++ ){
            int id = activeUserList.get(i).getId();//用户id
            //根据userid查询用户是否进行过上述行为
            int followsum = userService.queryFollow(id);//是否关注过圈子、标签或作者
            int postsum = userService.queryPost(id);//是否发过贴
            int zansum = userService.queryZan(id);//是否点赞过
            int collectsum = userService.queryCollect(id);//是否收藏过
            int commentsum = userService.queryComment(id);//是否评论过
            int forwardsum = userService.queryForward(id);//是否转发过
            if (followsum > 0 || postsum > 0 || zansum > 0 || collectsum > 0 || commentsum > 0 || forwardsum > 0){
                validsum = validsum + 1;
            }
        }

        //统计前一天的用户单日留存率（前天注册完成的用户里在昨天还登录过app的用户数/前天注册的用户总数 百分比）
        //1、先查询前天注册的所有用户列表
        List<User> qtRegisteUserList = userService.queryQtRegisteUser();
        //定义一个变量，存储前天注册的在昨天里依然登录过的用户数
        int ztloginnum = 0;
        for (int i = 0; i < activeUserList.size(); i++){
            for (int j = 0; j < qtRegisteUserList.size(); j++){
                log.info("活跃用户id>>>>" + activeUserList.get(i).getId() + "登录用户id>>>>>>>>" + qtRegisteUserList.get(j).getId());
                if (activeUserList.get(i).getId() != qtRegisteUserList.get(j).getId()){
                    ztloginnum = ztloginnum + 1;
                    log.info("ztloginnum+1一次！！");
                }
            }
        }
        float keeprate = 0;
        log.info("测试ztloginnum>>>>>>" + ztloginnum);
        if (qtRegisteUserList.size() > 0){
            keeprate = ztloginnum/qtRegisteUserList.size();
        }

        parammap.put("date", date);
        parammap.put("registenum", registenum);
        parammap.put("usersum", activeUserList.size());
        parammap.put("validsum", validsum);
//        parammap.put("channel", channel);//渠道需要APP端集成了不同平台的包时才能进行统计
        parammap.put("keeprate", keeprate);
        parammap.put("intime", intime);
        userService.updateDauStatistic(parammap);

        log.info("统计前一天的日活用户数据...end...");
    }
}
