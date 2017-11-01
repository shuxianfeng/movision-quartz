package com.movision.task;

import com.movision.mybatis.post.entity.PostAuthor;
import com.movision.mybatis.post.service.PostService;
import com.movision.mybatis.postHeatvalueEverydayRecord.entity.PostHeatvalueEverydayRecord;
import com.movision.mybatis.postHeatvalueEverydayRecord.service.PostHeatvalueEverydayRecordService;
import com.movision.utils.propertiesLoader.ApplicationPropertiesUtils;
import com.movision.utils.DateUtil;
import com.movision.utils.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/24 17:08
 */
@Service
@Transactional
public class EverydayPostHeatvalueTask {
    private static final Logger logger = LoggerFactory.getLogger(EverydayPostHeatvalueTask.class);

    @Autowired
    PostService postService;

    @Autowired
    PostHeatvalueEverydayRecordService recordService;

    public void run() {
        String mailTo = ApplicationPropertiesUtils.getValue("notice_receive_email");

        String curDateStr = DateUtil.date2Str(new Date(), "YYYY-MM-dd HH:mm:ss");
        String contentStart = EmailUtil.buildContent("记录帖子每天的热度任务" + curDateStr, "开始执行");
        // 发送开始处理邮件
        EmailUtil.send(mailTo, contentStart, "记录帖子每天的热度");

        int succNum = 0;
        int failedNum = 0;

        try {
            List<PostAuthor> postList = postService.queryAllPostInDB();
            for (PostAuthor post : postList) {
                PostHeatvalueEverydayRecord record = new PostHeatvalueEverydayRecord();
                record.setAuthorid(Integer.valueOf(post.getUserid()));  //作者id
                record.setAuthorName(post.getNickname());   //作者名称
                record.setHeatValue(post.getHeatvalue());   //热度
                record.setIntime(new Date());
                record.setIsdel(0);
                record.setPostid(post.getId());
                record.setPostTitle(post.getTitle());   //帖子标题
                int flag = recordService.add(record);
                if (flag == 1) {
                    succNum++;
                } else {
                    failedNum++;
                }
            }

        } catch (Exception e) {
            logger.error("EverydayPostHeatvalueTask::run", e);
        } finally {
            String contentEnd = "处理成功：" + succNum + "，处理失败：" + failedNum;
            EmailUtil.send(mailTo, contentEnd, "记录帖子每天的热度任务" + curDateStr + " 已结束");
        }
    }
}
