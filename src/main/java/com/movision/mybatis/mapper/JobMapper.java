package com.movision.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JobMapper {

    int deletePosition(@Param("id") String id);

    int getResumeCount(Map query);

    List<Map<String, Integer>> selectExpireDownloadResume();

    int deleteDownloadRecord(@Param("id") String id);

    List<Integer> selectViewGoods(Map<String,Integer> map);

    int deleteViewGoods(@Param("id") String id);

    int getFbzwCount(Map query);
}