package com.movision.mybatis.activePeriod.service;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.*;
import com.movision.mybatis.mapper.ActivePeriodMapper;
import com.movision.mybatis.mapper.PostMapper;
import com.movision.mybatis.user.entity.User;
import com.movision.utils.propertiesLoader.MongoDbPropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author shuxf
 * @Date 2018/3/13 14:36
 */
@Service
public class ActiveRefreshService {

    private static final Logger logger = LoggerFactory.getLogger(ActiveRefreshService.class);

    @Autowired
    private ActivePeriodMapper activePeriodMapper;

    @Autowired
    private PostMapper postMapper;

    /**
     * 获取《美番APP发帖扶持活动》中统计的业务刷新数据
     * @return
     */
    public String getRefreshStr(){

        //统计本周内阅读量最高的作品作者
        String nickname = "";//获得浏览最高的作者昵称
        int count = 0;//本周浏览量最高的帖子浏览次数
        MongoClient mongoClient = null;
        DB db = null;
        try {
            //计算本周第一天的开始时间和本周最后一天的结束时间
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int d;
            if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                d = -6;
            } else {
                d = 2 - cal.get(Calendar.DAY_OF_WEEK);
            }
            cal.add(Calendar.DAY_OF_WEEK, d);
            // 所在周开始日期
            Date startdate = cal.getTime();
            cal.add(Calendar.DAY_OF_WEEK, 6);
            // 所在周结束日期
            Date enddate = cal.getTime();

            mongoClient = new MongoClient(MongoDbPropertiesLoader.getValue("mongo.hostport"));
            db = mongoClient.getDB("searchRecord");
            DBCollection collection = db.getCollection("userRefreshRecord");
            //查询某个时间段
            BasicDBObject te = new BasicDBObject("$gte", "2018-03-12 00:00:00:330").append("$lte", "2018-03-19 00:00:00:330");
            BasicDBObject searchQuery = new BasicDBObject("intime", te);
            DBObject match = new BasicDBObject("$match", searchQuery);

            //指定需要显示列
            BasicDBObject keys = new BasicDBObject();
            keys.put("_id", 0);
            keys.put("userid", 1);
            keys.put("postid", 1);
            keys.put("intime", 1);

            DBObject project = new BasicDBObject("$project", keys);
            // 利用$group进行分组
            DBObject _group = new BasicDBObject("postid", "$postid");
            DBObject groupFields = new BasicDBObject("_id", _group);
            //总数
            groupFields.put("count", new BasicDBObject("$sum", 1));
            DBObject group = new BasicDBObject("$group", groupFields);
            AggregationOutput output = collection.aggregate(match, project, group);
            logger.info("output>>>" + output.results().toString());

            //解析mongodb中统计的结果集，并找出浏览数最高的帖子id
            int postid = 0;
            Iterable<DBObject> iterable = output.results();
            Iterator<DBObject> iter = iterable.iterator();
            while (iter.hasNext()){
                DBObject ele = iter.next();
                logger.info("postid>>" + JSONObject.parseObject(ele.get("_id").toString()).get("postid").toString());
                logger.info("count>>" + (int)ele.get("count"));
                int newpostid = Integer.parseInt(JSONObject.parseObject(ele.get("_id").toString()).get("postid").toString());
                int newcount = (int)ele.get("count");
                if (newcount > count) {
                    count = newcount;
                    postid = newpostid;
                }
            }

            //如果mongo中存在本周的浏览量数据的话
            if (postid > 0){
                //根据帖子id查询作者昵称
                nickname = postMapper.queryUserNickname(postid);
            }

            /**
             BasicDBList basicDBList = (BasicDBList)db.getCollection("mongodb中集合编码或者编码")
             .group(DBObject key,   --分组字段，即group by的字段
             DBObject cond,        --查询中where条件
             DBObject initial,     --初始化各字段的值
             String reduce,        --每个分组都需要执行的Function
             String finial)         --终结Funciton对结果进行最终的处理
             */

        } catch (Exception e) {
            logger.error("统计mongodb中周阅读量最高的作品作者失败", e);
        } finally {
            if (null != db) {
                db.requestDone();
                mongoClient.close();
            }
        }

        //统计本周内作品数最多获得者
        User user = activePeriodMapper.queryUserByMaxPostNum();

        //统计单作品月点赞量最高获得者
        User zanuser = activePeriodMapper.queryUserByMaxZanNum();

        StringBuffer newInfo = new StringBuffer();
        newInfo.append("\n【中奖名单】：（系统30分钟智能统计）");
        if (!nickname.equals("")) {
            newInfo.append("\n\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;周阅读最高作品获得者：" + nickname + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最高："+ count +"次浏览");
        }
        if (null != user) {
            int postNum = activePeriodMapper.queryPostNum(user.getId());
            newInfo.append("\n\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;周作品数最多获得者：" + user.getNickname() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;周发帖数：" + postNum + "篇");
        }
        if (null != zanuser) {
            int zanNum = activePeriodMapper.queryZanNum(user.getId());
            if (zanNum > 0) {
                newInfo.append("\n\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单作品月点赞最高获得者：" + zanuser.getNickname() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单作品月点赞数：" + zanNum + "赞");
            }
        }

        return newInfo.toString();
    }
}