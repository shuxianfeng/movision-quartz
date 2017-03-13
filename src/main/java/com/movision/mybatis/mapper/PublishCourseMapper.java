package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.PublishCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PublishCourseMapper {
    int deleteByPrimaryKey(Long courseid);

    int insert(PublishCourse record);

    int insertSelective(PublishCourse record);

    PublishCourse selectByPrimaryKey(Long courseid);

    int updateByPrimaryKeySelective(PublishCourse record);

    int updateByPrimaryKeyWithBLOBs(PublishCourse record);

    int updateByPrimaryKey(PublishCourse record);

    int updateAddStockNum(@Param("courseId") Long courseId, @Param("number") int number);

    List<PublishCourse> findListByCondition(@Param("status") String status);


    String findBuyNumByCouserId(@Param("courseid") Long courseid, @Param("type") String type);

    List<PublishCourse> findListByStartTime(@Param("status") String status);

    List<PublishCourse> findListByEndTime(@Param("status") String status);

    List<PublishCourse> findListByConditionClosed();
}