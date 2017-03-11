package com.movision.mybatis.dao;

import com.movision.mybatis.entity.PackageBuyRecord;
import com.movision.mybatis.mapper.PackageBuyRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 套餐购买记录业务相关数据层
 *
 * @author zhuangyuhao
 * @date 2016年12月15日
 */
@Repository
public class PackageBuyRecordDao {

    @Autowired
    private PackageBuyRecordMapper mapper;

    public List<PackageBuyRecord> findIneffectivePackageBuyRecord() {
        return mapper.findIneffectivePackageBuyRecord();
    }

    public List<PackageBuyRecord> findEffectivePackageBuyRecord() {
        return mapper.findEffectivePackageBuyRecord();
    }

    public void updateByPrimaryKeySelective(PackageBuyRecord packageBuyRecord) {
        mapper.updateByPrimaryKeySelective(packageBuyRecord);
    }

    public PackageBuyRecord getPackageByUserId(Long userId){
     return  mapper.getPackageByUserId(userId);
    }
}
