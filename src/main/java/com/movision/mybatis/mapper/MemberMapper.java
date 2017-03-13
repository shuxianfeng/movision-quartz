package com.movision.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.movision.mybatis.entity.Member;

public interface MemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKeyWithBLOBs(Member record);

    int updateByPrimaryKey(Member record);

    Map<String,String> findMobileByCreateid(String id);
    
    List<Map<String,Object>> selectEnterpriseLogo();
}