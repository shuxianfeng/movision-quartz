package com.movision.mybatis.post.service;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.mapper.PostMapper;
import com.movision.mybatis.post.entity.PostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author shuxf
 * @Date 2017/4/24 11:11
 */
@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostMapper postMapper;

    public List<PostVo> queryAllPost() {
        try {
            log.info("查询所有帖子");
            return postMapper.queryAllPost();
        } catch (Exception e) {
            log.error("查询所有帖子失败", e);
            throw e;
        }
    }
}
