package com.movision.task;

import com.movision.constants.CourseConstants;
import com.movision.constants.OrderConstants;
import com.movision.mybatis.dao.PublishCourseDao;
import com.movision.mybatis.entity.PublishCourse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统自动终止课程
 * 
 * @author jianglz
 * @since 16/6/6.
 */
@Service
public class CourseTask {
    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    PublishCourseDao courseDao;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void run() {
        logger.info("系统自动终止课程处理开始CourseJob...");
        // 已经到培训结束时间 购买截止日期已到
        // 销售中状态 && 购买人数<最低开课条件
        List<PublishCourse> list = courseDao.findListByConditionClosed();
        List<PublishCourse> courseList = new ArrayList<>();
        for (PublishCourse course : list) {
            if (course.getCourseType() == 1) { // 技术培训
                String jsbuyNum = courseDao.findBuyNumByCouserId(course.getCourseid(), OrderConstants.GoodsType.JSPX.toString());
                if (StringUtils.isNumeric(jsbuyNum) && Integer.valueOf(jsbuyNum) < Integer.valueOf(course.getMinBuyNumber())) {
                    courseList.add(course);
                } else if (!StringUtils.isNumeric(jsbuyNum)) {
                    courseList.add(course);
                }
            }
            if (course.getCourseType() == 2) { // 专家培训
                String zjbuyNum = courseDao.findBuyNumByCouserId(course.getCourseid(), OrderConstants.GoodsType.ZJPX.toString());
                if (StringUtils.isNumeric(zjbuyNum) && Integer.valueOf(zjbuyNum) < Integer.valueOf(course.getMinBuyNumber())) {
                    courseList.add(course);
                } else if (!StringUtils.isNumeric(zjbuyNum)) {
                    courseList.add(course);
                }
            }
        }
        // 更新课程为终止状态
        if (courseList.size() > 0) {
            courseDao.batchUpdateStatus(courseList, CourseConstants.Status.YZZ.toString());
        }

        logger.info("系统自动终止课程处理结束CourseJob...");
    }
}
