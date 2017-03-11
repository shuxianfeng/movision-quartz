package com.movision.mybatis.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.entity.VipMemberInfo;
import com.movision.mybatis.mapper.VipInfoMapper;

/**
 * 
 * @author tongxinglong
 *
 */
@Repository
public class VipInfoDao {
	@Autowired
	private VipInfoMapper vipInfoMapper;

	/**
	 * 查询到期会员VIP信息
	 * 
	 * @param num
	 * @return
	 */
	public List<VipMemberInfo> listExpiredVipMemberInfo(int num) {
		return vipInfoMapper.selectExpiredVipMemberInfo(num);
	}

	/**
	 * 根据ID更新会员VIP信息
	 * @param vipMemberInfo
	 * @return
	 */
	public int updateVipMemberInfoById(VipMemberInfo vipMemberInfo) {
		if (null != vipMemberInfo) {
			return vipInfoMapper.updateVipMemberInfoById(vipMemberInfo);
		} else {
			return 0;
		}
	}
}
