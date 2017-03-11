package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.PackageBuyRecord;

import java.util.List;


/**
 * 套餐购买记录业务相关数据层
 *
 * @author liyang
 * @date 2016年12月15日
 */

public interface PackageBuyRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PackageBuyRecord record);

    int insertSelective(PackageBuyRecord record);

    PackageBuyRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PackageBuyRecord record);

    int updateByPrimaryKey(PackageBuyRecord record);

    List<PackageBuyRecord> findIneffectivePackageBuyRecord();

    List<PackageBuyRecord> findEffectivePackageBuyRecord();

    PackageBuyRecord getPackageByUserId(Long id);


}
