package com.movision.task;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.service.PostService;
import com.movision.mybatis.userRefreshRecord.service.UserRefreshRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author zhanglei
 * @Date 2017/11/10 13:42
 */
@Service
public class QueryPostCountViewTask {
    private static final Logger logger = LoggerFactory.getLogger(QueryPostCountViewTask.class);

    @Autowired
    private PostService postService;

    @Autowired
    private UserRefreshRecordService userRefreshRecordService;

    public void run() throws Exception {
        logger.info("查询帖子浏览量开始");
        queryPostCountView();
        logger.info("查询帖子浏览量失败");

    }



    public void queryPostCountView(){
        //查询帖子列表
        List<Post> list=postService.findAllPost();
        //循环帖子去mongodb中查询浏览次数
        //查看帖子详情也算
        //插入post表中countview字段
        for (int i=0;i<list.size();i++){
            Map map = new HashMap();
            int postid = list.get(i).getId();
            int uesrreflushCounts = userRefreshRecordService.postcount(postid);
            int poscount = userRefreshRecordService.postcountRecord(postid);
            int count = uesrreflushCounts + poscount;
            map.put("postid",postid);
            map.put("count",count);
            postService.updatePostCountView(map);
        }

    }

}
