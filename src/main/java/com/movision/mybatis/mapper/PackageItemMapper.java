package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.PackageItem;

import java.util.List;


/**
 * 套餐条目业务相关数据层
 *
 * @author liyang
 * @date 2016年12月15日
 */

public interface PackageItemMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PackageItem record);

    int insertSelective(PackageItem record);

    PackageItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PackageItem record);

    int updateByPrimaryKey(PackageItem record);

    PackageItem selectByItemId(String itemId);

    List<PackageItem> selectPackageItemList();

}
