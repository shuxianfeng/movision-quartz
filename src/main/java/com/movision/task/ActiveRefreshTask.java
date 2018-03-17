package com.movision.task;

import com.movision.mybatis.activePeriod.entity.ActivePeriod;
import com.movision.mybatis.activePeriod.service.ActiveRefreshService;
import com.movision.mybatis.mapper.ActivePeriodMapper;
import com.movision.mybatis.mapper.PostMapper;
import com.movision.mybatis.post.entity.Post;
import com.movision.utils.DateUtil;
import com.movision.utils.propertiesLoader.PropertiesDBLoader;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author shuxf
 * @Date 2018/3/13 11:37
 */
@Service
public class ActiveRefreshTask {
    private static final Logger logger = LoggerFactory.getLogger(ActiveRefreshTask.class);

    @Autowired
    private PropertiesDBLoader propertiesDBLoader;

    @Autowired
    private ActivePeriodMapper activePeriodMapper;

    @Autowired
    private ActiveRefreshService activeRefreshService;

    @Autowired
    private PostMapper postMapper;

    public void run() throws Exception {
        logger.info("指定活动页面数据实时刷新task开始....");

        //获取需要实时刷新的活动id
        int activeid = Integer.parseInt(propertiesDBLoader.getValue("refresh_activeid"));

        //根据id查询活动开始时间和结束时间
        ActivePeriod vo = activePeriodMapper.selectByPrimaryKey(activeid);
        Date startDate = vo.getBegintime();//活动开始时间
        Date endDate = vo.getEndtime();//活动结束时间
        Date now = new Date();//当前时间
        //计算距离活动结束时间
        int endDays = DateUtil.activeEndDays(now, startDate, endDate);

        if (endDays == -1){
            logger.info("活动未开始");
        }else if (endDays == 0){
            logger.info("活动已结束");
        }else {
            logger.info("活动进行中");

            //统计并获取需要动态插入刷新的业务数据--《美番APP发帖扶持活动》
            String newstr = activeRefreshService.getRefreshStr();

            //动态更新活动中的内容
            logger.info("统计获得的活动内容>>>>" + newstr);
            //根据活动id查询指定活动数据
            Post post = postMapper.selectByPrimaryKey(activeid);
            //更新帖子内容中的信息
            String postcontent = post.getPostcontent();
            //通过字段名称判断内容中之前是否添加过【中奖名单】模块
            if (postcontent.indexOf("【中奖名单】") != -1) {
                JSONArray moduleArray = JSONArray.fromObject(postcontent);
                moduleArray.remove(moduleArray.size()-1);
                Map<String, Object> res = new HashMap<>();
                res.put("type", 0);//类型0表示文字模块
                res.put("orderid", moduleArray.size());
                res.put("value", newstr);
                res.put("wh", "");
                res.put("dir", "");
                moduleArray.add(moduleArray.size(), res);
                post.setPostcontent(moduleArray.toString());
            }else {
                JSONArray moduleArray = JSONArray.fromObject(postcontent);
                Map<String, Object> res = new HashMap<>();
                res.put("type", 0);//类型0表示文字模块
                res.put("orderid", moduleArray.size());
                res.put("value", newstr);
                res.put("wh", "");
                res.put("dir", "");
                moduleArray.add(moduleArray.size(), res);
                post.setPostcontent(moduleArray.toString());
            }
            postMapper.updateByPrimaryKey(post);
        }

        logger.info("指定活动页面数据实时刷新task结束....");
    }
}
