package com.movision.mybatis.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movision.mybatis.mapper.MemberMapper;

/**
 * @author cxx
 * @since 16/8/4.
 */
@Repository
public class MemberDao {
    private static final Logger log = LoggerFactory.getLogger(MemberDao.class);

    @Autowired
    MemberMapper memberMapper;

    public Map<String, String> findMobileByCreateid(String id){

        try{
            return memberMapper.findMobileByCreateid(id);
        }catch (Exception e){
        	log.error("MemberDao::findNameByCreateid::id=="+id,e);
            throw e;
        }
    }
    
    public List<Map<String,Object>> selectEnterpriseLogo(){
    	try{
    		return memberMapper.selectEnterpriseLogo();
    	}catch(Exception e){
    		log.error("查询企业logo图片失败");
    		throw e;
    	}
    }
    
    
    
}
