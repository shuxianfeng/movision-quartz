package com.movision.mybatis.mapper;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.entity.PostAuthor;
import com.movision.mybatis.post.entity.PostVo;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PostMapper {
    Date queryPostIdByDate(Integer id);

    int updatePostBycommentsum(int postid);

    Integer queryCrileid(int postid);

    int updatePostByZanSum(int id);

    void insertZanRecord(Map<String, Object> parammap);

    int queryPostHotHeat(int postid);

    int updatePostHeatValue(Map map);//修改热度

    int queryPostIsessenceHeat(int postid);
    int deleteByPrimaryKey(Integer id);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer id);

    List<PostVo> queryAllPost();

    List<Post> queryEncodeVideo();

    void updatePostStatus(Map<String, Object> parammap);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

    List<Post> queryAllHeatValue();

    Integer queryIsHeatOperate();

    List<Post> queryAllTodayPost();

    void updateIsHeatOperate();

    int updateHeatValue(int id);

    int updateHeatValueTwo(int id);

    int updateOldPostHeatValueTwo(Map map);

    int queryByIdHeatValue(int id);

    String postDate(int id);

    int updateHaet(int id);

    List<PostAuthor> queryAllPostInDB();

    int queryIsZanPost(Map<String, Object> parammap);

    void releasePost();
}