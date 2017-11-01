package com.movision.mybatis.mapper;

import com.movision.mybatis.robotComment.entity.RobotComment;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RobotCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RobotComment record);

    int insertSelective(RobotComment record);

    RobotComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RobotComment record);

    int updateByPrimaryKey(RobotComment record);

    List<RobotComment> findAllQueryRoboltComment(Integer type, RowBounds rowBounds);

    List<RobotComment> queryRoboltComment(Map map);

    RobotComment queryCommentById(Integer id);

    void updateByCommentById(RobotComment robotComment);

    Integer queryComentMessage(RobotComment robotComment);
}