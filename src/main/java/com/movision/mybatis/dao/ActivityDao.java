package com.movision.mybatis.dao;

import com.movision.mybatis.mapper.ActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhuangyuhao
 * @since 2016/8/25.
 */
@Repository
public class ActivityDao {
    private static final Logger logger = LoggerFactory.getLogger(ActivityDao.class);

    @Autowired
    ActivityMapper activityMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchUpdateAddApplyNum(List<Map<String, String>> activityList) {
        for (Map<String, String> record : activityList) {
            updateAddApplyNum(Long.valueOf(record.get("activityId")), Integer.valueOf(record.get("number")));
        }
    }

    private int updateAddApplyNum(Long activityId, Integer number) {
        int count;
        logger.info("*********activityId:[{}],applyNum:[{}]", activityId, number);
        count = activityMapper.updateAddApplyNum(activityId, number);

        return count;
    }
}
