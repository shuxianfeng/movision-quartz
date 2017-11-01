package com.movision.mybatis.robotJobRecord.service;

import com.movision.mybatis.mapper.RobotJobRecordMapper;
import com.movision.mybatis.robotJobRecord.entity.RobotJobRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/31 14:31
 */
@Service
public class RobotJobRecordService {

    private static Logger log = LoggerFactory.getLogger(RobotJobRecordService.class);

    @Autowired
    private RobotJobRecordMapper robotJobRecordMapper;

    public int insertSelective(RobotJobRecord record) {
        try {
            log.info("新增机器人任务流水");
            return robotJobRecordMapper.insertSelective(record);
        } catch (Exception e) {
            log.error("新增机器人任务流水失败", e);
            throw e;
        }
    }
}
