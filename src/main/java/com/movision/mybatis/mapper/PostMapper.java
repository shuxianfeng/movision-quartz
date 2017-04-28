package com.movision.mybatis.mapper;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.entity.PostVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer id);

    List<PostVo> queryAllPost();

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);
}