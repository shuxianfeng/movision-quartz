package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.ImgCompressLog;

public interface ImgCompressLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ImgCompressLog record);

    int insertSelective(ImgCompressLog record);

    ImgCompressLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ImgCompressLog record);

    int updateByPrimaryKey(ImgCompressLog record);
}