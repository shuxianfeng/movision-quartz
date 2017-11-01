package com.movision.mybatis.comment.service;

import com.movision.mybatis.comment.entity.CommentVo;
import com.movision.mybatis.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author zhurui
 * @Date 2017/1/22 15:35
 */
@Service
@Transactional
public class CommentService {

    private static Logger log = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentMapper commentMapper;

    public int insertComment(CommentVo vo) {
        try {
            log.info("插入帖子评论");
            return commentMapper.insertComment(vo);
        } catch (Exception e) {
            log.error("帖子评论失败", e);
            throw e;
        }
    }


}
