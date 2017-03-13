package com.movision.mybatis.dao;

import com.movision.mybatis.entity.PublishCourse;
import com.movision.mybatis.mapper.PublishCourseMapper;
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
 * @since 16/6/1.
 */
@Repository
public class PublishCourseDao {
    private static final Logger logger = LoggerFactory.getLogger(PublishCourseDao.class);

    @Autowired
    PublishCourseMapper mapper;

    /**
     * 更新记录
     *
     * @param courseId
     * @param number
     * @return
     */
    public int updateAddStockNum(Long courseId, int number) {
        int count;
        logger.info("*********courseId:[{}],storageNumber:[{}]", courseId, number);
        count = mapper.updateAddStockNum(courseId, number);
//        if (count != 1) {
//            logger.error("t_p_group_publishCourse:更新数据失败");
//            throw new BusinessException(1000, "插入数据失败");
//        }
        return count;
    }

    /**
     * 批量更新商品库存数 {添加}
     *
     * @param jobList
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void batchUpdateAddStockNum(List<Map<String, String>> jobList) {
        for (Map<String, String> record : jobList) {
            updateAddStockNum(Long.valueOf(record.get("courseid")), Integer.valueOf(record.get("number")));
        }
    }

    /**
     * 查询需要系统自动终止的课程
     *
     * @param status
     * @return
     */
    public List<PublishCourse> findListByCondition(String status) {
        return mapper.findListByCondition(status);
    }

    /**
     * 查询需要系统自动终止的课程
     *
     * @return
     */
    public List<PublishCourse> findListByConditionClosed() {
        return mapper.findListByConditionClosed();
    }

    /**
     * 更新记录
     *
     * @param record
     */
    public void update(PublishCourse record) {
        int count;
        try {
            count = mapper.updateByPrimaryKeySelective(record);
            if (count != 1) {
                logger.error("t_p_group_publishCourse:无更新数据");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 批量修改状态
     *
     * @param courseList
     * @param status
     */
    public void batchUpdateStatus(List<PublishCourse> courseList, String status) {
        for (PublishCourse course : courseList) {
            course.setStatus(Integer.valueOf(status));
            update(course);
        }
    }

    public String findBuyNumByCouserId(Long courseid, String type) {
        return mapper.findBuyNumByCouserId(courseid,type);
    }

    public List<PublishCourse> findListByEndTime(String status) {
        return mapper.findListByEndTime(status);
    }

    public List<PublishCourse> findListByStartTime(String status) {
        return mapper.findListByStartTime(status);
    }
}
