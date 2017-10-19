package com.movision.task;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.service.PostService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author zhanglei
 * @Date 2017/7/14 18:42
 */
@Service
public class PostHeatValueTask {

    @Autowired
    private PostService postService;

    private static final Logger logger = LoggerFactory.getLogger(PostHeatValueTask.class);

    public void run() throws Exception {
        logger.info("减少热度处理开始");
        List<Post> list= postService.queryAllHeatValue();//所有帖子
        //昨天发的帖子
        List<Post> today =postService.queryAllTodayPost();
        for (int i=0;i<today.size();i++){
            //新帖子的发帖日期
           Date intime= today.get(i).getIntime();
          //对于每个新帖子，在24小时内，若其热度值上涨了180（3次点赞+1次评论+10次浏览），则不再进行递减
          //在24小时内，若其热度值上涨不足180，则按照第二条进行递减，直至0
          //对于每个新帖子，在1-7天，每天减50；
          //在第8天之后，每天减100；
          SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String str=sdf.format(intime);//发帖时间
          String hour=passDate(str,1);//24小时后的日期
          long potime=intime.getTime();//发帖时间
          Date oneposttime=parse(hour,"yyyy-MM-dd HH:mm:ss");
          long onepotime=oneposttime.getTime();//24小时后的日期

          if(potime<=onepotime){//如果发帖时间小于24小时
              //查询该帖子的热度值
              if(list.get(i).getHeatvalue()-3000<150){//24小时内热度值上涨不足180


              }
          }
        }
        for (int i=0;i<list.size();i++){
            int id=list.get(i).getId();
            //根据id查询热度
            int heatvalue=postService.queryByIdHeatValue(id);
            //查询帖子发帖日期
            String postDate=postService.postDate(id);
            //从发帖后的一天开始算
            String one=passDate(postDate,1);
            String out=passDate(one,7);//7天后日期
            Date post=parse(postDate,"yyyy-MM-dd");//发帖日期
            Date sevenDate=parse(out,"yyyy-MM-dd");//7天后日期
            long pd=post.getTime();
            long sd=sevenDate.getTime();
            if(pd<=sd) {//1-7天热度-10
                if (heatvalue >= 50) {
                    postService.updateHeatValue(id);
                } else {
                    postService.updateHaet(id);
                }
            }else {
                String loss=passDate(out,23);//后面的日期
                Date lossD=parse(loss,"yyyy-MM-dd");
                long ld=lossD.getTime();
                if(sd<=ld){
                    if (heatvalue >= 20) {
                        postService.updateHeatValueTwo(id);
                    } else {
                        postService.updateHaet(id);
                    }
                }
            }
        }
        logger.info("减少热度处理结束");
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
