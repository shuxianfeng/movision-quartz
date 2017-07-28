
package com.movision.mybatis.post.service;
import com.movision.mybatis.mapper.PostMapper;
import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.entity.PostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author shuxf
 * @Date 2017/4/24 11:11
 */
@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostMapper postMapper;

    @CacheEvict(value = "indexData", key = "'index_data'")
    public List<PostVo> queryAllPost() {
        try {
            log.info("查询所有帖子");
            return postMapper.queryAllPost();
        } catch (Exception e) {
            log.error("查询所有帖子失败", e);
            throw e;
        }
    }

    public List<Post> queryEncodeVideo(){
        try {
            log.info("查询所有待确认状态的视频贴");
            return postMapper.queryEncodeVideo();
        }catch (Exception e){
            log.error("查询所有待确认状态的视频贴失败", e);
            throw e;
        }
    }

    public void updatePostStatus(Map<String, Object> parammap){
        try {
            log.info("帖子视频检测后更新帖子状态");
            postMapper.updatePostStatus(parammap);
        }catch (Exception e){
            log.error("帖子视频检测后更新帖子状态失败", e);
            throw e;
        }
    }

    public List<Post> queryAllHeatValue(){
        try {
            log.info("查询所有热度");
            return postMapper.queryAllHeatValue();
        }catch (Exception e){
            log.error("查询所有热度失败");
            throw e;
        }
    }

    public int updateHeatValue(int id){
        try {
            log.info("减少热度");
            return postMapper.updateHeatValue(id);
        }catch (Exception e){
            log.error("减少热度失败");
            throw e;
        }
    }

    public  int queryByIdHeatValue(int id){
        try {
            log.info("根据id查询热度");
            return postMapper.queryByIdHeatValue(id);
        }catch (Exception e){
            log.error("根据id查询热度失败");
            throw e;
        }
    }

    public  int updateHaet(int id){
        try {
            log.info("根据id查询热度");
            return postMapper.updateHaet(id);
        }catch (Exception e){
            log.error("根据id查询热度失败");
            throw e;
        }
    }

}