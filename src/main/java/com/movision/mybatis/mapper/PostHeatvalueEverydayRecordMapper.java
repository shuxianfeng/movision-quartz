package com.movision.mybatis.mapper;

import com.movision.mybatis.postHeatvalueEverydayRecord.entity.PostHeatvalueEverydayRecord;
import org.springframework.stereotype.Repository;

public interface PostHeatvalueEverydayRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostHeatvalueEverydayRecord record);

    int insertSelective(PostHeatvalueEverydayRecord record);

    PostHeatvalueEverydayRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostHeatvalueEverydayRecord record);

    int updateByPrimaryKey(PostHeatvalueEverydayRecord record);
}