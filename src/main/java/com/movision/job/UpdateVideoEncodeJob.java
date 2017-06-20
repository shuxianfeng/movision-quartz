package com.movision.job;

import com.movision.task.UpdateVideoEncodeTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author shuxf
 * @Date 2017/6/19 19:32
 * 前提：发布包含视频的帖子，因为视频需要转码时间，所以发布后帖子不能立即被公布
 * 用途：用于包含视频的帖子内视频的轮训检测，视频是否转码成功，转码成功后立即公布用户发布的帖子
 */
public class UpdateVideoEncodeJob {

    private static final Logger logger = LoggerFactory.getLogger(UpdateVideoEncodeJob.class);

    @Autowired
    UpdateVideoEncodeTask updateVideoEncodeTask;

    public void execute() throws Exception {
        //2 上传中 4 上传完成 5 视频转码中 6 视频审核中 8 视频被屏蔽
        logger.info("轮训所有 isdel 为 2 4 5 6 8 的帖子>>>>>>>>>start");

        updateVideoEncodeTask.run();

        logger.info("轮训所有 isdel 为 2 4 5 6 8 的帖子>>>>>>>>>>>end");
    }
}
