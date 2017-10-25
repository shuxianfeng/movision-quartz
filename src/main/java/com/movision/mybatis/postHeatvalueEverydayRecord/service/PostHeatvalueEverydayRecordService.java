package com.movision.mybatis.postHeatvalueEverydayRecord.service;

import com.movision.mybatis.postHeatvalueEverydayRecord.entity.PostHeatvalueEverydayRecord;
import com.movision.mybatis.mapper.PostHeatvalueEverydayRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/24 16:53
 */
@Service
public class PostHeatvalueEverydayRecordService {

    private static Logger log = LoggerFactory.getLogger(PostHeatvalueEverydayRecordService.class);

    @Autowired
    private PostHeatvalueEverydayRecordMapper postHeatvalueEverydayRecordMapper;

    public Integer add(PostHeatvalueEverydayRecord postHeatvalueEverydayRecord) {
        try {
            log.info("新增帖子的每天热度流水");
            return postHeatvalueEverydayRecordMapper.insertSelective(postHeatvalueEverydayRecord);
        } catch (Exception e) {
            log.error("新增帖子的每天热度流水失败", e);
            throw e;
        }
    }


}
