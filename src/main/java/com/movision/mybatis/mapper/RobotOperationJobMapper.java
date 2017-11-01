package com.movision.mybatis.mapper;

import com.movision.mybatis.robotOperationJob.entity.RobotOperationJobBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RobotOperationJobMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RobotOperationJobBean record);

    int insertSelective(RobotOperationJobBean record);

    RobotOperationJobBean selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RobotOperationJobBean record);

    int updateByPrimaryKey(RobotOperationJobBean record);

    List<RobotOperationJobBean> queryNotHandlerJob();
}