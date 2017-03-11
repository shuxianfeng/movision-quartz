package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.Package;
/**
 * 套餐业务相关数据层
 *
 * @author zhuangyuhao
 * @date 2016年12月15日
 */

public interface PackageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Package record);

    int insertSelective(Package record);

    Package selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Package record);

    int updateByPrimaryKey(Package record);

}
