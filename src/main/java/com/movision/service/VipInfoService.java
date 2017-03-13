package com.movision.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movision.mybatis.dao.VipInfoDao;
import com.movision.mybatis.entity.VipMemberInfo;

/**
 * VIP服务
 *
 * @author zhuangyuhao
 *
 */
@Transactional
@Service
public class VipInfoService {
	@Autowired
	private VipInfoDao vipInfoDao;

	/**
	 * 查询到期会员信息
	 * 
	 * @param num
	 * @return
	 */
	public List<VipMemberInfo> listExpiredVipMemberInfo(int num) {
		return vipInfoDao.listExpiredVipMemberInfo(num);
	}

	/**
	 * 根据ID更新
	 * 
	 * @param vipMemberInfo
	 * @return
	 */
	public int updateVipMemberInfoById(VipMemberInfo vipMemberInfo) {
		return vipInfoDao.updateVipMemberInfoById(vipMemberInfo);
	}
}
