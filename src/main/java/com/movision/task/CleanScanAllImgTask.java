package com.movision.task;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.movision.utils.propertiesLoader.PropertiesLoader;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author shuxf
 * @Date 2017/4/24 10:44
 */
@Service
public class CleanScanAllImgTask {

    private static final Logger log = LoggerFactory.getLogger(CleanScanAllImgTask.class);

    @Autowired
    private PostService postService;

    public void run() {

        log.info("开始查询所有的帖子列表>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        //开始查询所有的帖子列表
        List<Post> allPostList = postService.queryAllPost();

        //扫描服务器下帖子图片和封面存放路径下的所有图片
        String compressimgpath = PropertiesLoader.getValue("post.compress.img.domain");//帖子内容中压缩后的图片存放路径
        String postprotoimgpath = PropertiesLoader.getValue("post.proto.img.domain");//帖子内容中原图存放路径
        String activeprotoimgpath = PropertiesLoader.getValue("active.proto.img.domain");//活动内容中原图的存放路径
        String postprotovideopath = PropertiesLoader.getValue("post.proto.video.domain");//帖子内容中视频存放路径

        String path = System.getProperty("user.dir");
        log.info("打印绝对路径>>>>>>>>>>>>>>>>>>>" + path);

        //扫描所有帖子的内容和封面进行检查
        for (int i = 0; i < allPostList.size(); i++) {

        }
    }
}
