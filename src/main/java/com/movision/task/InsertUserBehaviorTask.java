package com.movision.task;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.movision.mybatis.user.entity.User;
import com.movision.mybatis.user.service.UserService;
import com.movision.mybatis.userBehavior.entity.UserBehavior;
import com.movision.mybatis.userBehavior.service.UserBehaviorService;
import com.movision.mybatis.userRefreshRecord.entity.UserRefreshRecordCount;
import com.movision.mybatis.userRefreshRecord.service.UserRefreshRecordService;
import com.movision.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
                      insertBehavior(users, i, dbCursors, iList, userBehavior);
                      userBehaviorService.insertSelective(userBehavior);
                }else {//有的话就更新
                    Map map = updateBe(users, i, dbCursors, iList);
                    userBehaviorService.updateByPrimaryKeySelective(map);
                }
            }
        }
    }

    private Map updateBe(List<User> users, int i, List<UserRefreshRecordCount> dbCursors, List<Integer> iList) {
        Map map = new HashMap();
        for (int j = 0; j < dbCursors.size(); j++) {
            int circle = dbCursors.get(j).getCrileid();
            iList.add(circle);
        }
        if (iList.size() == 3) {
            if (StringUtil.isNotEmpty(users.get(i).getId().toString())) {
                map.put("id", users.get(i).getId());
            }
            if (StringUtil.isNotEmpty(iList.get(0).toString())) {
                map.put("circle1", iList.get(0));
            }
            if (StringUtil.isNotEmpty(iList.get(1).toString())) {
                map.put("circle2", iList.get(1));
            }

            if (StringUtil.isNotEmpty(iList.get(2).toString())) {
                map.put("circle3", iList.get(2));
            }
            map.put("intime", new Date());
        } else if (iList.size() == 2) {
            if (StringUtil.isNotEmpty(users.get(i).getId().toString())) {
                map.put("id", users.get(i).getId());
            }
            if (StringUtil.isNotEmpty(iList.get(0).toString())) {
                map.put("circle1", iList.get(0));
            }
            if (StringUtil.isNotEmpty(iList.get(1).toString())) {
                map.put("circle2", iList.get(1));
            }
            map.put("circle3", 0);
            map.put("intime", new Date());
        } else if (iList.size() == 1) {
            if (StringUtil.isNotEmpty(users.get(i).getId().toString())) {
                map.put("id", users.get(i).getId());
            }
            if (StringUtil.isNotEmpty(iList.get(0).toString())) {
                map.put("circle1", iList.get(0));
            }
            map.put("circle2", 0);

            map.put("circle3", 0);
            map.put("intime", new Date());
        }
        return map;
    }

    /**
     * 新增
     * @param users
     * @param i
     * @param dbCursors
     * @param iList
     * @param userBehavior
     */
    private void insertBehavior(List<User> users, int i, List<UserRefreshRecordCount> dbCursors, List<Integer> iList, UserBehavior userBehavior) {
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
