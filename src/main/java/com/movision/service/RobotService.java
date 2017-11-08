package com.movision.service;

import com.movision.facade.CollectionFacade;
import com.movision.facade.FacadeHeatValue;
import com.movision.facade.PostFacade;
import com.movision.mybatis.comment.entity.CommentVo;
import com.movision.mybatis.comment.service.CommentService;
import com.movision.mybatis.post.service.PostService;
import com.movision.mybatis.robotComment.entity.RobotComment;
import com.movision.mybatis.robotComment.service.RobotCommentService;
import com.movision.mybatis.robotJobRecord.entity.RobotJobRecord;
import com.movision.mybatis.robotJobRecord.service.RobotJobRecordService;
import com.movision.mybatis.robotOperationJob.entity.RobotOperationJobBean;
import com.movision.mybatis.robotOperationJob.service.RobotOperationJobService;
import com.movision.mybatis.systemLayout.service.SystemLayoutService;
import com.movision.mybatis.user.entity.User;
import com.movision.mybatis.user.service.UserService;
import com.movision.mybatis.userRefreshRecord.entity.UserRefreshRecord;
import com.movision.mybatis.userRefreshRecord.service.UserRefreshRecordService;
import com.movision.task.RobotTask;
import com.movision.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/31 13:33
 */
@Service
public class RobotService {

    private static Logger log = LoggerFactory.getLogger(RobotTask.class);

    @Autowired
    PostFacade postFacade;

    @Autowired
    SystemLayoutService systemLayoutService;

    @Autowired
    RobotCommentService robotCommentService;

    @Autowired
    CollectionFacade collectionFacade;
    @Autowired
    CommentService commentService;

    @Autowired
    UserRefreshRecordService userRefreshRecordService;

    @Autowired
    FacadeHeatValue facadeHeatValue;

    @Autowired
    PostService postService;

    @Autowired
    RobotOperationJobService robotOperationJobService;

    @Autowired
    RobotJobRecordService robotJobRecordService;

    @Autowired
    UserService userService;

    /**
     * 定时机器人任务的赞流程
     *
     * @param jobBean
     */
    public void zanProcess(RobotOperationJobBean jobBean) {
        //1 点赞的业务处理
        zanBusiProcess(jobBean);
        //2 机器人处理任务的公共流程
        commonProcess(jobBean);
    }

    /**
     * 机器人处理任务的公共流程
     * 1 更新指定次数，即count-1
     * 2 记录任务执行流水
     *
     * @param jobBean
     */
    private void commonProcess(RobotOperationJobBean jobBean) {
        //1 更新指定次数，即count-1
        int jobid = jobBean.getId();
        //剩余执行次数
        int execCount = jobBean.getCount();
        int leftExecCount = execCount - 1;
        RobotOperationJobBean newJob = new RobotOperationJobBean();
        //若下次执行次数为0 ，则设置该任务状态：已执行。--下次定时任务不会再执行
        if (leftExecCount == 0) {
            newJob.setStatus(1);
            newJob.setEndtime(new Date());
        }
        newJob.setId(jobid);
        newJob.setCount(leftExecCount);
        robotOperationJobService.updateByPrimaryKeySelective(newJob);

        //2 记录任务执行流水
        RobotJobRecord record = new RobotJobRecord();
        record.setJobid(jobid);
        record.setIntime(new Date());
        robotJobRecordService.insertSelective(record);
    }

    private void zanBusiProcess(RobotOperationJobBean jobBean) {
        int postid = jobBean.getPostid();
        //1 每次任务只有一个机器人进行操作
        Map map = new HashMap();
        map.put("postid", postid);
        map.put("number", 1);
        List<User> robotArmy = userService.queryNotRepeatZanRobots(map);

        //2 循环进行帖子点赞操作
        for (int i = 0; i < robotArmy.size(); i++) {
            int userid = robotArmy.get(i).getId();
            processRobotZanPost(postid, userid);
        }
        //3 进行5倍的机器人数量的浏览帖子操作
        viewPost5Times(postid, 1, map);
    }

    /**
     * 进行5倍的机器人数量的浏览帖子操作
     *
     * @param postid
     * @param num
     * @param map
     */
    private void viewPost5Times(int postid, int num, Map map) {
        map.put("number", 5 * num);
        List<User> viewRobots = userService.queryNotRepeatZanRobots(map);
        for (int i = 0; i < viewRobots.size(); i++) {
            int userid = viewRobots.get(i).getId();
            int circleid = postService.queryCrileid(postid);
            insertPostViewRecord(userid, postid, circleid);
            facadeHeatValue.addHeatValue(postid, 8, userid);    //增加帖子热度-浏览
        }
    }

    /**
     * 处理机器人点赞帖子
     *
     * @param postid
     * @param userid
     */
    private void processRobotZanPost(int postid, int userid) {

        Map<String, Object> parammap = new HashMap<>();
        parammap.put("postid", postid);
        parammap.put("userid", userid);
        parammap.put("intime", new Date());
        //查询当前用户是否已点赞该帖
        int count = postService.queryIsZanPost(parammap);
        if (count == 0) {
            //增加帖子热度
            facadeHeatValue.addHeatValue(postid, 3, userid);
            //插入点赞历史记录
            postService.insertZanRecord(parammap);
            //更新帖子点赞数量字段
            postService.updatePostByZanSum(postid);

        }
    }

    /**
     * 插入mongoDB帖子浏览记录 推荐页
     *
     * @param userid
     * @param postid
     * @param circleid
     */
    private void insertPostViewRecord(int userid, int postid, int circleid) {
        UserRefreshRecord userRefreshRecord = new UserRefreshRecord();
        userRefreshRecord.setId(UUID.randomUUID().toString().replaceAll("\\-", ""));
        userRefreshRecord.setUserid(userid);
        userRefreshRecord.setPostid(postid);
        userRefreshRecord.setCrileid(String.valueOf(circleid));
        userRefreshRecord.setIntime(DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        userRefreshRecord.setType(1);   //推荐页
        userRefreshRecord.setLabelid(-1);
        userRefreshRecordService.insert(userRefreshRecord);
    }

    /**
     * 收藏流程
     *
     * @param jobBean
     */
    public void collectPostProcess(RobotOperationJobBean jobBean) {
        //1 收藏的业务处理
        robotCollectPost(jobBean);
        //2 机器人处理任务的公共流程
        commonProcess(jobBean);
    }

    /**
     * 机器人收藏帖子操作
     *
     * @param jobBean
     */
    public void robotCollectPost(RobotOperationJobBean jobBean) {
        int postid = jobBean.getPostid();
        //1 每次任务只有一个机器人操作
        Map map = new HashMap();
        map.put("postid", postid);
        map.put("number", 1);
        List<User> robotArmy = userService.queryNotRepeatCollectRobots(map);

        //2 循环进行收藏帖子操作
        for (int i = 0; i < robotArmy.size(); i++) {
            int userid = robotArmy.get(i).getId();
            collectionFacade.collectionPost4Robot(String.valueOf(postid), String.valueOf(userid), String.valueOf(0));
        }

        //3 进行5倍的机器人数量的浏览帖子操作
        viewPost5Times(postid, 1, map);
    }

    /**
     * 机器人定时处理帖子评论流程
     *
     * @param jobBean
     */
    public void commentPostProcess(RobotOperationJobBean jobBean) {
        //1 收藏的业务处理
        robotCommentPostProcess(jobBean);
        //2 机器人处理任务的公共流程
        commonProcess(jobBean);
    }

    public void robotCommentPostProcess(RobotOperationJobBean jobBean) {
        int postid = jobBean.getPostid();
        int num = 1;
        int commentType = jobBean.getCommentType();
        //1 新增评论
        insertPostCommentByRobolt(postid, num, commentType);
        //2 新增机器人5倍的浏览帖子
        Map map = new HashMap();
        map.put("postid", postid);
        viewPost5Times(postid, num, map);
    }

    /**
     * 机器人评论帖子
     *
     * @param postid 帖子id
     * @param num    机器人的数量
     */
    public void insertPostCommentByRobolt(Integer postid, Integer num, Integer commentType) {

        //1 集合num个机器人大军, 不重复
        List<User> users = userService.queryNotRepeatCommentRobots(num);
        //2 随机查询num条评论内容
        List<RobotComment> content = queryRobotComment(users.size(), commentType, postid);
        //3 获取帖子发表时间
        Date date = postService.queryPostIdByDate(postid);

        for (int i = 0; i < users.size(); i++) {
            //机器人的id
            Integer userid = users.get(i).getId();
            //1 插入评论表
            insertPostComment(postid, content, date, i, userid);
            //2 更新帖子表的评论次数字段
            postService.updatePostBycommentsum(postid);
            //3 增加被评论的帖子热度
            facadeHeatValue.addHeatValue(postid, 4, userid);
        }
    }

    /**
     * 插入评论表
     *
     * @param postid
     * @param content
     * @param date
     * @param i
     * @param userid
     */
    private void insertPostComment(Integer postid, List<RobotComment> content, Date date, int i, Integer userid) {
        CommentVo vo = new CommentVo();
        vo.setPostid(postid);
        vo.setContent(content.get(i).getContent());
        vo.setUserid(userid);
        vo.setZansum(0);
        vo.setIsdel("0");
        vo.setStatus(1);    //审核状态：0待审核 1审核通过 2审核不通过（iscontribute为1时不为空）
        vo.setIscontribute(0);  //是否为特邀嘉宾的评论：0否 1是
        vo.setIntime(new Date());
        commentService.insertComment(vo);
    }

    public List<RobotComment> queryRobotComment(Integer num, Integer commentType, Integer postid) {
        List<RobotComment> content;
        List<RobotComment> contens = new ArrayList();

        Map map = new HashMap();
        map.put("number", num);
        map.put("type", commentType);
        map.put("postid", postid);
        //随机查询num条评论内容
        content = robotCommentService.queryRoboltComment(map);
        contens.addAll(content);
        return contens;
    }

    /**
     * 随机查询num条评论内容
     *
     * @param type  处理类型 1 随机查询num条评论内容; 2 根据指定的权重来查询不同类型评论
     * @param num
     * @param theme 帖子主题，1：人像， 2：风光
     * @return
     */
    /*public List<RobotComment> randomRobotComment(Integer type, Integer num, Integer theme) {
        List<RobotComment> content = new ArrayList<>();
        List<RobotComment> contens = new ArrayList();

        if (type == 1) {
            Map map = new HashMap();
            map.put("number", num);
            map.put("type", 0);
            //随机查询num条评论内容
            content = robotCommentService.queryRoboltComment(map);
            contens.addAll(content);
        } else if (type == 2) {//<=200 2:40% 4:30% 5:30%
            //有小数向上进位  Math.ceil 返回大于参数x的最小整数,即对浮点数向上取整.
            //查询评论占比率
            Double number = 0.0d;
            int commentType = 0;
            if (theme == 1) {
                //人像 90%占比
                number = systemLayoutService.queryRobotPercentage("robot_comment_portrait_ratio");
                commentType = 3;
            } else {
                //风光 90%占比
                number = systemLayoutService.queryRobotPercentage("robot_comment_scenery_ratio");
                commentType = 2;
            }
            Map map = new HashMap();
            querySelectedComment(num, contens, map, number, commentType);

            Double number2 = systemLayoutService.queryRobotPercentage("robot_comment_5_ratio"); //5%占比
            querySelectedComment(num, contens, map, number2, 4);
            querySelectedComment(num, contens, map, number2, 5);
        }
        return contens;
    }*/

    /**
     * 查询指定类型的评论字典
     *
     * @param num
     * @param contens
     * @param map
     * @param number2
     * @param commentType 评论类型 ：0：普通 1：专业摄影 2：风光 3：人像 4：诗词 5：段子
     */
    /*private void querySelectedComment(Integer num, List<RobotComment> contens, Map map, Double number2, int commentType) {
        int mm;
        List<RobotComment> content;
        mm = (int) (Math.ceil(num * number2));
        map.put("number", mm);
        map.put("type", commentType);
        content = robotCommentService.queryRoboltComment(map);
        contens.addAll(content);
    }*/

    /**
     * 机器人关注用户任务流程
     *
     * @param jobBean
     */
    public void followUserProcess(RobotOperationJobBean jobBean) {
        //1 关注用户业务流程
        robotFollowUserBusiProcess(jobBean);
        //2 通用流程
        commonProcess(jobBean);
    }

    /**
     * 机器人关注用户操作
     */
    public void robotFollowUserBusiProcess(RobotOperationJobBean jobBean) {
        int userid = jobBean.getUserid();
        int num = 1;
        //1 集合机器人大军
        Map map = new HashMap();
        map.put("number", num);
        map.put("userid", userid);
        List<User> robotArmy = userService.queryNotRepeatFollowRandomRobots(map);

        //2 循环进行关注作者操作
        for (int i = 0; i < robotArmy.size(); i++) {
            int robotid = robotArmy.get(i).getId();
            postFacade.concernedAuthorUser(robotid, userid);
        }
    }
}
