package com.movision.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.movision.mybatis.entity.BrandAnalysisRecord;

/**
 * @author tongxinglong
 * @date 2017/2/20 0020.
 */
public interface BrandAnalysisRecordMapper {

    BrandAnalysisRecord selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(BrandAnalysisRecord brandAnalysisRecord);

    int insertSelective(BrandAnalysisRecord brandAnalysisRecord);

    int updateByPrimaryKeySelective(BrandAnalysisRecord brandAnalysisRecord);

    int updateByPrimaryKey(BrandAnalysisRecord brandAnalysisRecord);

    List<BrandAnalysisRecord> selectRecordsByReportTime(Map param);

    BrandAnalysisRecord selectLastRecordByAnalysisId(Long analysisId);
}
