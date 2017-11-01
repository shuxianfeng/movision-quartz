package com.movision.mybatis.mapper;

import com.movision.mybatis.postHeatvalueRecord.entity.PostHeatvalueRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHeatvalueRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostHeatvalueRecord record);

    int insertSelective(PostHeatvalueRecord record);

    PostHeatvalueRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostHeatvalueRecord record);

    int updateByPrimaryKey(PostHeatvalueRecord record);
}