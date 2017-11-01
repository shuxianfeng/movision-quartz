package com.movision.mybatis.robotOperationJob.service;

import com.movision.mybatis.mapper.RobotOperationJobMapper;
import com.movision.mybatis.robotOperationJob.entity.RobotOperationJobBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author zhuangyuhao
 * @Date 2017/10/31 13:46
 */
@Service
public class RobotOperationJobService {

    private static Logger log = LoggerFactory.getLogger(RobotOperationJobService.class);

    @Autowired
    private RobotOperationJobMapper robotOperationJobMapper;

    public List<RobotOperationJobBean> queryNotHandleJob() {
        try {
            log.info("查询未处理的任务");
            return robotOperationJobMapper.queryNotHandlerJob();
        } catch (Exception e) {
            log.error("查询未处理的任务失败", e);
            throw e;
        }
    }

    public int updateByPrimaryKeySelective(RobotOperationJobBean jobBean) {
        try {
            log.info("更新机器人任务表需要的字段");
            return robotOperationJobMapper.updateByPrimaryKeySelective(jobBean);
        } catch (Exception e) {
            log.error("更新机器人任务表需要的字段失败", e);
            throw e;
        }
    }
}
