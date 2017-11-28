
package com.movision.mybatis.post.service;

import com.movision.mybatis.mapper.PostMapper;
import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.entity.PostAuthor;
import com.movision.mybatis.post.entity.PostVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    public Date queryPostIdByDate(Integer id) {
        return postMapper.queryPostIdByDate(id);
    }

    public int updatePostBycommentsum(int postid) {
        try {
            log.info("更新帖子的评论数量 postid=" + postid);
            return postMapper.updatePostBycommentsum(postid);
        } catch (Exception e) {
            log.error("帖子更新评论数量异常 postid=" + postid, e);
            throw e;
        }
    }

    public Integer queryCrileid(int postid) {
        try {
            log.info("查询帖子属于哪个圈子");
            return postMapper.queryCrileid(postid);
        } catch (Exception e) {
            log.error("查询帖子属于哪个圈子失败");
            throw e;
        }
    }

    //跟新帖子点赞次数
    public int updatePostByZanSum(int id) {
        try {
            log.info("更新帖子点赞次数 id=" + id);
            return postMapper.updatePostByZanSum(id);
        } catch (Exception e) {
            log.error("帖子点赞次数更新异常 id=" + id, e);
            throw e;
        }
    }

    //插入一条用户点赞帖子的记录
    public void insertZanRecord(Map<String, Object> parammap) {
        try {
            log.info("插入一条用户点赞帖子的记录");
            postMapper.insertZanRecord(parammap);
        } catch (Exception e) {
            log.error("插入一条用户点赞帖子的记录失败", e);
            throw e;
        }
    }

    public Integer queryPostHotHeat(int postid) {
        try {
            log.info("根据id查询是否为精选");
            return postMapper.queryPostHotHeat(postid);
        } catch (Exception e) {
            log.error("根据id查询是否为精选失败", e);
            throw e;
        }
    }

    public int updatePostHeatValue(Map map) {
        try {
            log.info("修改热度");
            return postMapper.updatePostHeatValue(map);
        } catch (Exception e) {
            log.error("修改热度失败", e);
            throw e;
        }
    }

    public Integer queryPostIsessenceHeat(int postid) {
        try {
            log.info("根据id查询是否为精选");
            return postMapper.queryPostIsessenceHeat(postid);
        } catch (Exception e) {
            log.error("根据id查询是否为精选失败", e);
            throw e;
        }
    }

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

    /**
     * 查询当天没有被操作过热度值的帖子
     * @return
     */
    public Integer queryIsHeatOperate(){
        try {
            log.info("查询当天没有被操作过热度值的帖子");
            return postMapper.queryIsHeatOperate();
        } catch (Exception e) {
            log.error("查询当天没有被操作过热度值的帖子异常",e);
            throw e;
        }
    }

    /**
     * 查询帖子是否被操作过热度值标志位
     */
    public void updateIsHeatOperate(){
        try {
            log.info("更新帖子是否被操作过热度值标志位");
            postMapper.updateIsHeatOperate();
        } catch (Exception e) {
            log.error("更新帖子是否被操作过热度值标志位异常",e);
            throw e;
        }
    }

    public List<Post> queryAllTodayPost(){
        try {
            log.info("查询昨天新帖");
            return postMapper.queryAllTodayPost();
        }catch (Exception e){
            log.error("查询昨天新帖失败");
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

    public int updateHeatValueTwo(int id){
        try {
            log.info("减少热度");
            return postMapper.updateHeatValueTwo(id);
        }catch (Exception e){
            log.error("减少热度失败");
            throw e;
        }
    }

    /**
     * 减少旧帖的热度值
     * @param map
     * @return
     */
    public int updateOldPostHeatValueTwo(Map map){
        try {
            log.info("减少旧帖的热度值");
            return postMapper.updateOldPostHeatValueTwo(map);
        } catch (Exception e) {
            log.error("减少旧帖的热度值异常",e);
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



    public  String postDate(int id){
        try {
            log.info("查询帖子发帖日期");
            return postMapper.postDate(id);
        }catch (Exception e){
            log.error("查询帖子发帖日期失败");
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

    public List<PostAuthor> queryAllPostInDB() {
        try {
            log.info("查询数据库中的所有帖子（包括活动）");
            return postMapper.queryAllPostInDB();
        } catch (Exception e) {
            log.error("查询数据库中的所有帖子（包括活动）失败", e);
            throw e;
        }
    }

    //查询当前用户有没有点赞过该帖子
    public int queryIsZanPost(Map<String, Object> parammap) {
        try {
            log.info("查询当前用户有没有点赞过该帖子");
            return postMapper.queryIsZanPost(parammap);
        } catch (Exception e) {
            log.error("查询当前用户有没有点赞过该帖子失败", e);
            throw e;
        }
    }

    public Integer queryRandPostid(){
        try {
            log.info("随机查询一个isdel为9的帖子id");
            return postMapper.queryRandPostid();
        }catch (Exception e){
            log.error("随机查询一个isdel为9的帖子id失败");
            throw e;
        }
    }

    public void releasePost(int postid){
        try {
            log.info("定时任务发布帖子");
            postMapper.releasePost(postid);
        }catch (Exception e){
            log.error("定时任务发布帖子失败");
            throw e;
        }
    }

    /**
     * 查询所有帖子
     * @return
     */
    public List<Post> findAllPost(){
        try {
            log.info("查询所有帖子");
            return postMapper.findAllPost();
        }catch (Exception e){
            log.error("查询所有帖子失败",e);
            throw e;
        }
    }

    /**
     * 修改帖子浏览量
     * @param
     * @return
     */
    public  int updatePostCountView(Map map){
        try {
            log.info("修改帖子浏览量");
            return postMapper.updatePostCountView(map);
        }catch (Exception e){
            log.error("修改帖子浏览量失败",e);
            throw e;
        }
    }

    public List<Post> queryPostNumYesterday(){
        try {
            log.info("统计前一天的新增帖子总数");
            return postMapper.queryPostNumYesterday();
        }catch (Exception e){
            log.error("统计前一天的新增帖子总数失败", e);
            throw e;
        }
    }

    public int queryActivePostNum(List<Post> postIdList){
        try {
            log.info("统计前一天的活跃帖数量");
            int count = 0;//活跃帖子数
            for (int i = 0; i < postIdList.size(); i++){
                int id = postIdList.get(i).getId();
                //通过帖子id查询该帖是否活跃
                int replynum = postMapper.queryReplyNum(id);//被回复过的帖子数
                int forwardnum = postMapper.queryForwardNum(id);//被转发过的帖子数
                int zannum = postMapper.queryZanNum(id);//被点赞过的帖子数
                int collectsum = postMapper.queryCollectSum(id);//被收藏过的帖子数
                if (replynum > 0 || forwardnum > 0 || zannum > 0 || collectsum > 0){
                    count = count + 1;
                }
            }
            return count;
        }catch (Exception e){
            log.error("统计前一天的活跃帖数量失败", e);
            throw e;
        }
    }

    public int queryReply(){
        try {
            log.info("统计前一天的产生回复的评论总数");
            return postMapper.queryReply();
        }catch (Exception e){
            log.error("统计前一天的产生回复的评论总数失败", e);
            throw e;
        }
    }

    public int queryZan(){
        try {
            log.info("统计前一天的点赞总数");
            return postMapper.queryZan();
        }catch (Exception e){
            log.error("统计前一天的点赞总数失败", e);
            throw e;
        }
    }

    public int queryForward(){
        try {
            log.info("统计前一天的转发总数");
            return postMapper.queryForward();
        }catch (Exception e){
            log.error("统计前一天的转发总数失败", e);
            throw e;
        }
    }


}