package com.movision.mybatis.dao;

import com.movision.mybatis.entity.PackageItem;
import com.movision.mybatis.mapper.PackageItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 套餐条目相关接口实现类
 *
 * @author liyang
 * @date 2016年12月15日
 */
@Service
@Transactional
public class PackageItemDao {

    @Autowired
    private PackageItemMapper packageItemMapper;

    /**
     * 根据 itemId 获取套餐条目详情信息
     * 
     * @param itemId
     * @return
     */
    public PackageItem getPackageItem(String itemId) {
        return packageItemMapper.selectByItemId(itemId);
    }

    /**
     * 检索系统中所有的套餐条目
     * 
     * @return
     */
    public List<PackageItem> getPackageItemList() {
        return packageItemMapper.selectPackageItemList();
    }

}
