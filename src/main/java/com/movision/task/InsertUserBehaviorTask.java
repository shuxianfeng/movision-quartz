package com.movision.task;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.movision.mybatis.user.entity.User;
import com.movision.mybatis.user.service.UserService;
import com.movision.mybatis.userBehavior.entity.UserBehavior;
import com.movision.mybatis.userBehavior.service.UserBehaviorService;
import com.movision.mybatis.userRefreshRecord.entity.UserRefreshRecordCount;
import com.movision.mybatis.userRefreshRecord.service.UserRefreshRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author zhanglei
 * @Date 2017/10/26 16:55
 */
@Service
public class InsertUserBehaviorTask {
    private static final Logger logger = LoggerFactory.getLogger(InsertUserBehaviorTask.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRefreshRecordService userRefreshRecordService;
    @Autowired
    private UserBehaviorService userBehaviorService;


    public void run() throws Exception {

        logger.info("插入用户分析表/更新开始");
        InsertUser();
        logger.info("插入用户分析表/更新结束");

    }



    public void  InsertUser(){
        //查询用户表
        List<User> users = userService.queryUserid();
        for (int i=0;i<users.size();i++){
            int id=users.get(i).getId();
            int count=userRefreshRecordService.mongodbCount(id);
            List<UserRefreshRecordCount> dbCursors=userRefreshRecordService.userFlush(users.get(i).getId());
            //插入分析表
            int ishave=userBehaviorService.IsHave(id);
            List<Integer> iList = new ArrayList<>();
            UserBehavior userBehavior = new UserBehavior();
            if(count>=100) {//历史记录》100
                if (ishave <= 0) {//分析表中没有该用户就插入到分析表中
                      updateBehavior(users, i, dbCursors, iList, userBehavior);
                      userBehaviorService.insertSelective(userBehavior);
                }else {//有的话就更新
                    updateBehavior(users, i, dbCursors, iList, userBehavior);
                    userBehaviorService.updateByPrimaryKeySelective(userBehavior);
                }
            }
        }
    }

    private void updateBehavior(List<User> users, int i, List<UserRefreshRecordCount> dbCursors, List<Integer> iList, UserBehavior userBehavior) {
        for (int j = 0; j < dbCursors.size(); j++) {
            int circle =dbCursors.get(j).getCrileid();
            iList.add(circle);
        }
        if(iList.size()==3){
            userBehavior.setIntime(new Date());
            userBehavior.setUserid(users.get(i).getId());
            userBehavior.setCircle1(iList.get(0));
            userBehavior.setCircle2(iList.get(1));
            userBehavior.setCircle3(iList.get(2));
        }else if(iList.size()==2){
            userBehavior.setIntime(new Date());
            userBehavior.setUserid(users.get(i).getId());
            userBehavior.setCircle1(iList.get(0));
            userBehavior.setCircle2(iList.get(1));
            userBehavior.setCircle3(0);
        }else if(iList.size()==1){
            userBehavior.setIntime(new Date());
            userBehavior.setUserid(users.get(i).getId());
            userBehavior.setCircle1(iList.get(0));
            userBehavior.setCircle2(0);
            userBehavior.setCircle3(0);
        }
    }

}
