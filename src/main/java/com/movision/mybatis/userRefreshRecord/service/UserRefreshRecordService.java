package com.movision.mybatis.userRefreshRecord.service;

import com.mongodb.*;
import com.movision.mybatis.mapper.UserRefreshRecordMapper;
import com.movision.mybatis.userRefreshRecord.entity.UserRefreshRecord;
import com.movision.utils.propertiesLoader.MongoDbPropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * @Author zhanglei
 * @Date 2017/6/14 16:03
 */
@Service
public class UserRefreshRecordService implements UserRefreshRecordMapper {

    private static Logger log = LoggerFactory.getLogger(UserRefreshRecordService.class);

    @Autowired
    MongoTemplate mongoTemplate;

    public void insert(UserRefreshRecord userRefreshRecord) {

        mongoTemplate.insert(userRefreshRecord);
    }

    /**
     * 根据postid查询帖子的浏览量
     *
     * @return
     */
    public Integer postcount(int postid) {
        MongoClient mongoClient = null;
        int obj = 0;
        DB db = null;
        DBCursor cursor = null;
        try {
            mongoClient = new MongoClient(MongoDbPropertiesLoader.getValue("mongo.hostport"));
            db = mongoClient.getDB("searchRecord");
            DBCollection collection = db.getCollection("userRefreshRecord");
            BasicDBObject queryObject = new BasicDBObject("postid", postid);
            cursor = collection.find(queryObject);
            obj = cursor.count();
            cursor.close();
        } catch (Exception e) {
            log.error("根据postid查询帖子的浏览量失败", e);
        } finally {
            if (null != db) {
                db.requestDone();
                cursor.close();
                mongoClient.close();
            }
        }
        return obj;

    }

    /**
     * 根据用户id和圈子id查出帖子浏览记录
     *
     * @param userid
     * @param circleid
     * @return
     */
    public List getPostViewRecordByUseridAndCircleid(Integer userid, Integer circleid) {

        log.debug("根据用户id和圈子id查出帖子浏览记录");
        TypedAggregation<UserRefreshRecord> agg = Aggregation.newAggregation(
                UserRefreshRecord.class,
                project("postid", "userid", "crileid")
                , match(Criteria.where("userid").is(userid).and("crileid").is(String.valueOf(circleid)))
                , group("postid")
        );
        log.debug("执行语句=" + agg.toString());
        AggregationResults<DBObject> result = mongoTemplate.aggregate(agg, DBObject.class);
        log.debug("查询结果=" + result.getMappedResults());
        log.debug("查询结果数量=" + result.getMappedResults().size());
        return result.getMappedResults();
    }


}
