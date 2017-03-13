package com.movision.mybatis.mapper;

import com.movision.mybatis.entity.Pwdticket;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PwdticketMapper {
    int deleteByPrimaryKey(Long ticketId);

    int insert(Pwdticket record);

    int insertSelective(Pwdticket record);

    Pwdticket selectByPrimaryKey(Long ticketId);

    int updateByPrimaryKeySelective(Pwdticket record);

    int updateByPrimaryKey(Pwdticket record);

    List<Map<String,String>> findByCourseId(@Param("courseid") Long courseid);
}