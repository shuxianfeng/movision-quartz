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

        postService.releasePost();

        log.info("定时任务开始帖子发布完成");

    }
}
