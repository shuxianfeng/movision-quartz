package com.movision.task;

import com.movision.mybatis.circle.entity.Circle;
import com.movision.mybatis.circle.entity.CircleCategory;
import com.movision.mybatis.circle.service.CircleService;
import com.movision.mybatis.post.entity.PostVo;
import com.movision.mybatis.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.movision.utils.propertiesLoader.PropertiesLoader;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Autowired
    private CircleService circleService;

    public void run() {

        log.info("开始查询所有的帖子列表");
        //开始查询所有的帖子列表
        List<PostVo> allPostList = postService.queryAllPost();
        //开始查询所有的圈子列表
        List<Circle> allCircleList = circleService.queryAllCircle();
        //开始查询圈子分类列表
        List<CircleCategory> allCircleCategoryList = circleService.queryAllCircleCategory();

        //扫描服务器下帖子图片和封面存放路径下的所有图片
        String compressimgpath = PropertiesLoader.getValue("post.compress.img.domain");//帖子内容中压缩后的图片存放路径
        String postprotoimgpath = PropertiesLoader.getValue("post.proto.img.domain");//帖子内容中原图存放路径
        String activeprotoimgpath = PropertiesLoader.getValue("active.proto.img.domain");//活动内容中原图的存放路径
        String postprotovideopath = PropertiesLoader.getValue("post.proto.video.domain");//帖子内容中视频存放路径
        String circleimgpath = PropertiesLoader.getValue("circle.img.domain");//圈子封面图片和圈子猜你喜欢小方图存放路径

        log.info("帖子内容中压缩后的图片存放路径>>>>>>>>>>>>>>>>>>>" + compressimgpath);
        log.info("帖子内容中原图存放路径>>>>>>>>>>>>>>>>>>>" + postprotoimgpath);
        log.info("活动内容中原图的存放路径>>>>>>>>>>>>>>>>>>>" + activeprotoimgpath);
        log.info("帖子内容中视频存放路径>>>>>>>>>>>>>>>>>>>" + postprotovideopath);
        log.info("圈子封面图片和圈子猜你喜欢小方图存放路径>>>>>>>>>>>>>>>>>>>" + circleimgpath);

        //根据文件路径扫描所有带扫描的文件
        String [] compressimgFileName = getFileName(compressimgpath);//压缩图片文件名数组
        String [] postprotoimgFileName = getFileName(postprotoimgpath);//帖子中原图文件名数组
        String [] activeprotoimgFileName = getFileName(activeprotoimgpath);//活动中原图文件名数组
        String [] postprotovideoFileName = getFileName(postprotovideopath);//帖子中视频文件名数组
        String [] circleimgFileName = getFileName(circleimgpath);//圈子封面图片和圈子猜你喜欢小方图文件名数组

        //遍历所有文件夹下的文件名
        for(String name:compressimgFileName)
        {
            clean(name, allPostList, compressimgpath);//清理图片
        }
        for(String name:postprotoimgFileName)
        {
            clean(name, allPostList, postprotoimgpath);//清理图片
        }
        for(String name:activeprotoimgFileName)
        {
            clean(name, allPostList, activeprotoimgpath);//清理图片
        }
        for(String name:postprotovideoFileName)
        {
            cleanvideo(name, allPostList, postprotovideopath);//清理视频
        }
        for(String name:circleimgFileName){
            cleanCircleImg(name, allCircleList, allCircleCategoryList, circleimgpath);//清理圈子封面和猜你喜欢的圈子小方图
        }
    }

    public static String [] getFileName(String path) {
        File file = new File(path);
        String [] fileName = file.list();
        return fileName;
    }

    //清理帖子或活动内容或视频封面图片中未使用到的图片
    public void clean(String name, List<PostVo> allPostList, String path){
        //定义标志位（0 未使用到 >1 有使用到）
        int flag = 0;//初始化为未使用到
        for (int i = 0; i < allPostList.size(); i++){
            int contentindex = allPostList.get(i).getPostcontent().indexOf(name);//帖子活动内容中是否用到
            int coverimgindex = allPostList.get(i).getCoverimg().indexOf(name);//帖子活动封面中是否用到
            int videocoverindex = -1;//视频封面图片中是否用到
            int hotimgindex = -1;//发现页活动封面小方图中是否用到

            if (null != allPostList.get(i).getVideocoverurl()) {
                videocoverindex = allPostList.get(i).getVideocoverurl().indexOf(name);//视频封面图片中是否用到
            }
            if (null != allPostList.get(i).getHotimgurl()){
                hotimgindex = allPostList.get(i).getHotimgurl().indexOf(name);//发现页活动封面小方图中是否用到
            }

            //如果帖子活动封面或者帖子活动内容或者视频封面图片中被用到flag+1
            if (contentindex != -1 || coverimgindex != -1 || videocoverindex != -1 || hotimgindex != -1){
                flag = flag + 1;//每使用一次+1
            }
        }
        //删除所有未使用到的图片文件
        if (flag == 0){
            String filepath = path + name;//文件路径和文件名
            File file = new File(filepath);
            file.delete();
        }
    }

    //清理帖子或活动中无用的视频文件
    public void cleanvideo(String name, List<PostVo> allPostList, String path){
        //定义标志位（0 未使用到 1 有使用到）
        int flag = 0;//初始化为未使用到
        for (int i = 0; i < allPostList.size(); i++){
            if (null != allPostList.get(i).getVideourl()) {
                int index = allPostList.get(i).getVideourl().indexOf(name);
                if (index != -1) {
                    flag = flag + 1;//每使用一次+1
                }
            }
        }
        //删除所有未使用到的视频文件
        if (flag == 0){
            String filepath = path + name;//文件路径和文件名
            File file = new File(filepath);
            file.delete();
        }
    }

    //清理圈子封面小方图中无用的图片文件
    public void cleanCircleImg(String name, List<Circle> allCircleList, List<CircleCategory> allCircleCategoryList, String path){
        //定义标志位（0 未使用到 1 有使用到）
        int flag = 0;//初始化为未使用到

        //检查所有圈子类型banner图
        int indexcircletype = -1;
        for (int j = 0; j < allCircleCategoryList.size(); j++){
            int indexcircle = allCircleCategoryList.get(j).getDiscoverpageurl().indexOf(name);//发现页圈子分类图片中是否使用到

            if (indexcircle != -1){
                indexcircletype = indexcircletype + 1;
            }
        }

        //检查所有圈子封面和猜你喜欢小方图
        for (int i = 0; i < allCircleList.size(); i++){
            int indexcover = allCircleList.get(i).getPhoto().indexOf(name);//圈子封面中是否使用到
            int indexmaylike = allCircleList.get(i).getMaylikeimg().indexOf(name);//猜你喜欢小方图中是否使用到

            if (indexcover != -1 || indexmaylike != -1 || indexcircletype != -1) {
                flag = flag + 1;//每使用一次+1
            }
        }
        //删除所有未使用到的视频文件
        if (flag == 0){
            String filepath = path + name;//文件路径和文件名
            File file = new File(filepath);
            file.delete();
        }
    }
}
