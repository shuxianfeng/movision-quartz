package com.movision.task;

import com.movision.mybatis.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author shuxf
 * @Date 2017/11/8 14:46
 */
@Service
public class QuartzPostTask {

    private static final Logger log = LoggerFactory.getLogger(QuartzPostTask.class);

    @Autowired
    private PostService postService;

    public void run() {

        log.info("定时任务开始发布帖子");

        //首先随机查询一个isdel为9的帖子id-------------鉴于MYSQL报[Err] 1093 - You can't specify target table 'yw_post' for update in FROM clause.建议拆开执行
        int postid = postService.queryRandPostid();
        //发布这个帖子
        postService.releasePost(postid);

        log.info("定时任务开始帖子发布完成");

    }
}
