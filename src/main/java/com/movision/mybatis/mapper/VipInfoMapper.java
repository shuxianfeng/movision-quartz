package com.movision.mybatis.mapper;

import java.util.List;

import com.movision.mybatis.entity.VipMemberInfo;

/**
 * 
 * @author tongxinglong
 *
 */
public interface VipInfoMapper {
	/**
	 * 查询到期会员信息
	 * 
	 * @param num
	 *            获取数量
	 * @return
	 */
	List<VipMemberInfo> selectExpiredVipMemberInfo(int num);

	/**
	 * 根据ID更新VIP会员信息
	 * 
	 * @param vipMemberInfo
	 * @return
	 */
	int updateVipMemberInfoById(VipMemberInfo vipMemberInfo);
}
