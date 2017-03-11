package com.movision.task;

import com.movision.mybatis.dao.JobDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下载简历90天以后回收处理
 *
 * @author liyang
 * @date 2017年1月16日
 */
@Service
public class ResumeDownloadRecordTask {
    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private JobDao jobDao;

    public void run() {
        logger.info("下载简历90天以后回收处理开始ResumeDownloadRecordTask...");
        doBusiness();
        logger.info("下载简历90天以后回收处理结束ResumeDownloadRecordTask...");
    }

    /**
     * 业务处理
     */
    private void doBusiness() {
        // 更改简历下载状态，更新用户购买简历记录--已删除
        List<Map<String, Integer>> maps = jobDao.selectExpireDownloadResume();
        if (!CollectionUtils.isEmpty(maps)) {
            for (Map<String, Integer> map : maps) {
                jobDao.deleteDownloadRecord(String.valueOf(map.get("id")));
                Map query = new HashMap();
                query.put("companyId", map.get("companyID"));
                query.put("goodsId", map.get("resumeID"));
                List<Integer> ids = jobDao.selectViewGoods(query);
                if (!CollectionUtils.isEmpty(ids)) {
                    for (Integer id : ids) {
                        jobDao.deleteViewGoods(String.valueOf(id));
                    }
                }
            }
        }
    }

}
