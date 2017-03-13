package com.movision.mybatis.dao;

import com.movision.mybatis.entity.Package;
import com.movision.mybatis.mapper.PackageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 套餐相关接口实现类
 *
 * @author zhuangyuhao
 * @date 2016年12月15日
 */
@Repository
public class PackageDao {

    @Autowired
    private PackageMapper packageMapper;

    /**
     * 根据主键获取套餐详情
     *
     * @param id
     * @return
     */
    public Package getPackage(Long id) {
        return packageMapper.selectByPrimaryKey(id);
    }

}
