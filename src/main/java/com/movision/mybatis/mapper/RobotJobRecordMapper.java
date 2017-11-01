package com.movision.mybatis.mapper;

import com.movision.mybatis.robotJobRecord.entity.RobotJobRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotJobRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RobotJobRecord record);

    int insertSelective(RobotJobRecord record);

    RobotJobRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RobotJobRecord record);

    int updateByPrimaryKey(RobotJobRecord record);
}