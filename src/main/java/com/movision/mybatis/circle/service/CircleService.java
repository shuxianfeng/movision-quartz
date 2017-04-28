package com.movision.mybatis.circle.service;

import com.movision.mybatis.circle.entity.Circle;
import com.movision.mybatis.mapper.CircleMapper;
import com.movision.mybatis.post.entity.PostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author shuxf
 * @Date 2017/4/28 15:42
 */
@Service
public class CircleService {
    private static final Logger log = LoggerFactory.getLogger(CircleService.class);

    @Autowired
    private CircleMapper circleMapper;

    public List<Circle> queryAllCircle() {
        try {
            log.info("查询所有圈子");
            return circleMapper.queryAllCircle();
        } catch (Exception e) {
            log.error("查询所有圈子失败", e);
            throw e;
        }
    }
}
