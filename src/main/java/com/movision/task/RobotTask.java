package com.movision.task;

import com.movision.mybatis.robotOperationJob.entity.RobotOperationJobBean;
import com.movision.mybatis.robotOperationJob.service.RobotOperationJobService;
import com.movision.service.RobotService;
import com.movision.utils.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/31 13:31
 */
@Service
public class RobotTask {

    private static Logger log = LoggerFactory.getLogger(RobotTask.class);

    @Autowired
    RobotService robotService;

    @Autowired
    RobotOperationJobService robotOperationJobService;

    public void run() throws Exception {
        //任务类型。1：点赞，2：收藏，3：评论，4：关注
        List<RobotOperationJobBean> robotOperationJobBeanList = robotOperationJobService.queryNotHandleJob();
        if (ListUtil.isNotEmpty(robotOperationJobBeanList)) {
            int len = robotOperationJobBeanList.size();
            for (int i = 0; i < len; i++) {
                //任务类型
                int jobType = robotOperationJobBeanList.get(i).getType();
                //任务id
                int id = robotOperationJobBeanList.get(i).getId();

                if (jobType == 1) {
                    //点赞处理
                    robotService.zanProcess(robotOperationJobBeanList.get(i));
                } else if (jobType == 2) {
                    //收藏处理
                    robotService.collectPostProcess(robotOperationJobBeanList.get(i));
                } else if (jobType == 3) {
                    //评论处理
                    robotService.commentPostProcess(robotOperationJobBeanList.get(i));
                } else if (jobType == 4) {
                    //关注处理
                    robotService.followUserProcess(robotOperationJobBeanList.get(i));
                } else {
                    log.error("任务类型不正确。任务id=" + id + ", 任务类型type=" + jobType);
                    continue;
                }
            }
        }

    }
}
