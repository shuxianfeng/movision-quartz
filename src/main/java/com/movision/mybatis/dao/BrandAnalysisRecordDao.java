package com.movision.mybatis.dao;

import com.movision.mybatis.entity.BrandAnalysisRecord;
import com.movision.mybatis.mapper.BrandAnalysisRecordMapper;
import com.movision.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhuangyuhao
 * @date 2017/2/21 0021.
 */
@Repository
public class BrandAnalysisRecordDao {
    @Autowired
    private BrandAnalysisRecordMapper brandAnalysisRecordMapper;

    /**
     * 根据报告时间查询所有报告记录
     * 
     * @param reportTimeA
     * @param reportTimeB
     * @return
     */
    public List<BrandAnalysisRecord> getRecordsByReportTime(Date reportTimeA, Date reportTimeB) {
        List<BrandAnalysisRecord> resultList = new ArrayList<>();
        if (null != reportTimeA && null != reportTimeB) {
            Map param = MapUtil.convert2HashMap("reportTimeA", reportTimeA, "reportTimeB", reportTimeB);

            resultList = brandAnalysisRecordMapper.selectRecordsByReportTime(param);
        }

        return resultList;
    }

    /**
     * 添加
     * 
     * @param brandAnalysisRecord
     * @return
     */
    public int insert(BrandAnalysisRecord brandAnalysisRecord) {
        return brandAnalysisRecordMapper.insert(brandAnalysisRecord);
    }

    /**
     * 查询监测品牌最近的报告数据
     * 
     * @param analysisId
     * @return
     */
    public BrandAnalysisRecord getLastRecordByAnalysisId(Long analysisId) {
        return brandAnalysisRecordMapper.selectLastRecordByAnalysisId(analysisId);
    }

    public int updateByPrimaryKeySelective(BrandAnalysisRecord brandAnalysisRecord) {
        return brandAnalysisRecordMapper.updateByPrimaryKeySelective(brandAnalysisRecord);
    }
}
