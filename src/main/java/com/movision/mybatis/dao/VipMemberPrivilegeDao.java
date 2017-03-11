package com.movision.mybatis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.entity.VipMemberPrivilege;
import com.movision.mybatis.mapper.VipMemberPrivilegeMapper;

import java.util.Map;

/**
 * 用户特权相关接口实现类
 *
 * @author liyang
 * @date 2016年12月21日
 */
@Repository
public class VipMemberPrivilegeDao {

    @Autowired
    private VipMemberPrivilegeMapper vipMemberPrivilegeMapper;

    public VipMemberPrivilege selectVipMemberPrivilege(Map<String, Object> param) {
        return vipMemberPrivilegeMapper.selectVipMemberPrivilege(param);
    }

    public void updateVipMemberPrivilegeValue(VipMemberPrivilege vipMemberPrivilege) {
        vipMemberPrivilegeMapper.updateVipMemberPrivilegeValue(vipMemberPrivilege);
    }

    public void insertVipMemberPrivilege(VipMemberPrivilege vipMemberPrivilege) {
        vipMemberPrivilegeMapper.insertSelective(vipMemberPrivilege);
    }

}
