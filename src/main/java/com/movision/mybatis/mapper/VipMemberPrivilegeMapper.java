package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.VipMemberPrivilege;

import java.util.Map;

/**
 * 会员特权业务相关数据层
 *
 * @author liyang
 * @date 2016年12月21日
 */

public interface VipMemberPrivilegeMapper {

    int insertSelective(VipMemberPrivilege record);

    VipMemberPrivilege selectVipMemberPrivilege(Map<String, Object> param);

    int updateVipMemberPrivilegeValue(VipMemberPrivilege record);

}
