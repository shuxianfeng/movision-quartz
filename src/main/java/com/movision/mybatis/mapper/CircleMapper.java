package com.movision.mybatis.mapper;

import com.movision.mybatis.circle.entity.Circle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CircleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Circle record);

    int insertSelective(Circle record);

    Circle selectByPrimaryKey(Integer id);

    List<Circle> queryAllCircle();

    int updateByPrimaryKeySelective(Circle record);

    int updateByPrimaryKey(Circle record);
}