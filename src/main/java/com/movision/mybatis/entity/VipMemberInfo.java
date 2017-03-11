package com.movision.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * VIP信息
 * 
 * @author tongxinglong
 *
 */
public class VipMemberInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1082478493898824462L;

	private Long id;

	/**
	 * 会员ID，对应t_m_member库表主键ID
	 */
	private Long memberId;

	/**
	 * VIP级别，0：个人普通，30：个人黄金，60：个人铂金；100：企业普通，130：企业黄金，160：企业铂金
	 */
	private int vipLevel;

	/**
	 * vip级别生效时间
	 */
	private Date activeTime;

	/**
	 * vip级别失效时间
	 */
	private Date expireTime;
	/**
	 * 数据添加时间
	 */
	private Date addTime;

	/**
	 * 数据更新时间
	 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
