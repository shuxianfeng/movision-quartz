package com.movision.mybatis.post.mapper;

import com.movision.mybatis.post.entity.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer id);

    List<Post> queryAllPost();

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);
}