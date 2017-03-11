package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.ZhbAccount;

import java.util.List;

/**
 * @author tongxinglong
 * @date 2017/1/11 0011.
 */
public interface ZhbAccountMapper {

    ZhbAccount selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(ZhbAccount zhbAccount);

    int insertSelective(ZhbAccount zhbAccount);

    int updateByPrimaryKeySelective(ZhbAccount zhbAccount);

    int updateByPrimaryKey(ZhbAccount zhbAccount);

    /**
     * 查询大于ID的筑慧币账户信息
     * 
     * @param id
     * @return
     */
    List<ZhbAccount> selectZhbAccountList(Long id);

    /**
     * 根据会员ID查询筑慧币账户信息
     * 
     * @param memberId
     * @return
     */
    ZhbAccount selectByMemberId(Long memberId);
}
