package com.movision.task;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.service.PostService;
import com.movision.mybatis.postHeatvalueRecord.entity.PostHeatvalueRecord;
import com.movision.mybatis.postHeatvalueRecord.service.PostHeatvalueRecordService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author zhanglei
 * @Date 2017/7/14 18:42
 */
@Service
public class PostHeatValueTask {

    @Autowired
    private PostService postService;

    @Autowired
    private PostHeatvalueRecordService postHeatvalueRecordService;

    private static final Logger logger = LoggerFactory.getLogger(PostHeatValueTask.class);

    /**
     * 定时任务机制
     *
     * 1.每个帖子的初始值是3000
     * 2.对每个新帖，在1-7天，每天减50。在8天以后，每天减 100
     * 3.对于每个新帖，
     *      1).在24小时内，热度值上涨到180（3次点赞，1次评论，10浏览），不进行2操作
     *      2).在24小时内，热度值上涨不足180，按照2操作
     *
     * 4.对于旧帖子（发帖时间超过72小时）执行以下方法
     * 1).热度《5000，不操作
     * 2).热度》5000《10000每天递减 250
     * 3).热度》10000《500000每天递减2000
     * 4).热度5W》每天递减10000
     *
     * @throws Exception
     */
    public void run() throws Exception {

        logger.info("=========================查询当前日期是否在0点到1点之间");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse("00:59:59");
            endTime = df.parse("1:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("减少热度处理开始");
        Boolean flag = belongCalendar(now, beginTime, endTime);
        updateHeatValue(flag);
        logger.info("减少热度处理结束");

     }

    /**
     * 更新帖子热度值操作
     */
    private void updateHeatValue(Boolean flag) throws ParseException {
        if (flag){
            logger.info("=========================当前日期在零点到一点之间，更新帖子热度标志位");
            //更新所有帖子标志位（isheatoperate）是否操作过热度值 0否1是
            postService.updateIsHeatOperate();
        }

        //查询当天没有被操作过热度值的帖子
        Integer count = postService.queryIsHeatOperate();
        if (count > 0) {
            List<Post> list = postService.queryAllHeatValue();//所有旧帖子
            //昨天发的帖子
            List<Post> today = postService.queryAllTodayPost();
            //新帖
            newPostHeatOperate(list, today);
            //旧帖
            orderPostHeatOperate(list);
        }
    }

    /**
     * 返回当前时间是否在指定时间内，是返回true否返回false
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 新帖热度操作
     * @param list
     * @param today
     * @throws ParseException
     */
    private void newPostHeatOperate(List<Post> list, List<Post> today) throws ParseException {
        for (int i=0;i<today.size();i++){
            //对满24小时帖子热度值不满180的操作
            if (today.get(i).getHeatvalue()-3000<180){
                int id=list.get(i).getId();
                //热度
                int heatvalue=today.get(i).getHeatvalue();
                //发帖日期
                String postDate=today.get(i).getIntime().toString();
                //从发帖后的一天开始算
                String one=passDate(postDate,1);
                String out=passDate(one,7);//7天后日期
                Date post=parse(postDate,"yyyy-MM-dd");//发帖日期
                Date sevenDate=parse(out,"yyyy-MM-dd");//7天后日期
                long pd=post.getTime();
                long sd=sevenDate.getTime();
                if(pd<=sd) {//1-7天热度-50
                    if (heatvalue >= 50) {
                        postService.updateHeatValue(id);
                        addPostHeatvalueRecord(id, 50);

                    } else {
                        postService.updateHaet(id);
                        addPostHeatvalueRecord(id, heatvalue);
                    }
                }else {
                    if (heatvalue >= 100) {
                        postService.updateHeatValueTwo(id);
                        addPostHeatvalueRecord(id, 100);
                    } else {
                        postService.updateHaet(id);
                        addPostHeatvalueRecord(id, heatvalue);
                    }
                }
            }
        }
    }

    /**
     * @param id
     * @param heatvalue
     */
    private void addPostHeatvalueRecord(int id, int heatvalue) {
        PostHeatvalueRecord record = new PostHeatvalueRecord();
        record.setPostid(id);
        record.setType(15); //每日衰减
        record.setUserid(999999);   //表示定时任务
        record.setIsdel(0);
        record.setIsadd(1);
        record.setHeatValue(heatvalue);
        record.setIntime(new Date());
        postHeatvalueRecordService.add(record);
    }

    /**
     * 旧帖热度操作
     * @param list
     */
    private void orderPostHeatOperate(List<Post> list) {
        logger.info("=========================更新旧帖热度操作");
        for (int i = 0; i<list.size(); i++){
            //获取id
            Integer id = list.get(i).getId();
            //获取热度值
            Integer heat = list.get(i).getHeatvalue();
            Map map = new HashMap();
            map.put("id",id);
            if (heat> 5000 && heat < 10000) {
                map.put("heat",250);
                postService.updateOldPostHeatValueTwo(map);
                addPostHeatvalueRecord(id, 250);
            } else if (heat >= 10000 && heat < 50000){
                map.put("heat",2000);
                postService.updateOldPostHeatValueTwo(map);
                addPostHeatvalueRecord(id, 2000);
            } else if (heat >= 50000){
                map.put("heat",10000);
                postService.updateOldPostHeatValueTwo(map);
                addPostHeatvalueRecord(id, 10000);
            }
        }
    }


    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException
    {
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(
                pattern).parse(strDate);
    }


    /**
     * 时间
     * @param postDate
     * @param pase
     * @return
     */
    public static String passDate(String postDate,int pase){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
        Date date = sdf.parse(postDate, new ParsePosition(0));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
        calendar.add(Calendar.DATE,pase);
        Date date1 = calendar.getTime();
        String out = sdf.format(date1);
        return  out;

    }
}
