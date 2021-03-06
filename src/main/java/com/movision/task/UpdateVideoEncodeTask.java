package com.movision.task;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.service.PostService;
import com.movision.utils.AliVideoFacade;
import net.sf.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author shuxf
 * @Date 2017/6/19 20:01
 */
@Service
public class UpdateVideoEncodeTask {

    private static final Logger logger = LoggerFactory.getLogger(UpdateVideoEncodeTask.class);

    @Autowired
    private PostService postService;

    @Autowired
    private AliVideoFacade aliVideoFacade;

    public void run() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        logger.info("执行帖子视频状态检测和新方法----------------------------->>>>>>>>");

        //查询所有状态为24568的帖子(转码中和上传中和屏蔽中的帖子)
        List<Post> postlist = postService.queryEncodeVideo();

        if (postlist.size() > 0){
            //轮训检测需要检测的视频贴
            for (int i = 0; i < postlist.size(); i++){
                int flag = 1;//0 转码未完成 1 转码全部完成

                Post post = postlist.get(i);
                int postid = post.getId();//帖子id
                String postcontent = post.getPostcontent();//帖子内容json字符串

                //解析json字符串
                JSONArray moduleArray = JSONArray.fromObject(postcontent);
                for (int j = 0; j < moduleArray.size(); j++) {
                    try {
                        //从img中获取type属性
                        JSONObject moduleobj = JSONObject.parseObject(moduleArray.get(j).toString());
                        Integer type = (Integer) moduleobj.get("type");//帖子模块类型 0 文字 1 图片 2 视频
                        String value = (String) moduleobj.get("value");//模块value
                        Integer orderid = (Integer) moduleobj.get("orderid");//模块排序id
                        if (type == 2) {
                            //如果当前模块是视频的话，检测当前视频状态
                            String vid = value;

                            //生成请求的url，类似：GetVideoPlayAuth
                            String url = aliVideoFacade.generateRequestUrl("GetVideoInfo", vid);
                            Map<String, String> reMap = aliVideoFacade.doGet(url);
                            String result = "";
                            if (!reMap.isEmpty()) {
                                if ("200".equals(reMap.get("status"))) {
                                    result = reMap.get("result").toString();
                                }
                            }

                            JSONObject res = JSONObject.parseObject(result);
                            String str = res.get("Video").toString();// str如下：
//                        {
//                            "VideoId": "93ab850b4f6f44eab54b6e91d24d81d4",
//                                "Title": "阿里云VOD视频标题",
//                                "Description": "阿里云VOD视频描述",
//                                "Duration": 135.6,
//                                "CoverURL": "https://image.example.com/coversample.jpg",
//                                "Status": "Normal",
//                                "CreateTime": "2017-03-10 12:45:56",
//                                "ModifyTime": "2017-03-20 10:25:06",
//                                "Size": 10897890,
//                                "Snapshots": [{"https://image.example.com/snapshotsample1.jpg"}, {"https://image.example.com/snapshotsample2.jpg"}],
//                                "CateId": 78,
//                                "CateName": "分类名",
//                                "Tags": ["标签1", "标签2"]
//                        }
                            JSONObject obj = JSONObject.parseObject(str);
                            String status = obj.get("Status").toString();
                            String CoverURL = obj.get("CoverURL").toString();

                            if (status.equals("Normal")) {
                                //视频转码成功，正常播放
                                //正常的视频不给flag赋1了
                                moduleArray.remove(j);//先移除
                                Map<String, Object> map = new HashMap<>();
                                map.put("type", type);
                                map.put("value", vid);
                                map.put("wh", CoverURL);
                                map.put("dir", "");
                                map.put("orderid", orderid);
                                moduleArray.add(j, map);//再加入
                            } else if (status.equals("Uploading")) {//下面所有情况，只要有任意一个原因失败的，flag就赋值0
                                //上传中
                                flag = 0;
                            } else if (status.equals("UploadFail")) {
                                //上传失败
                                flag = 0;
                            } else if (status.equals("UploadSucc")) {
                                //上传成功
                                flag = 0;
                            } else if (status.equals("Transcoding")) {
                                //转码中
                                flag = 0;
                            } else if (status.equals("Checking")) {
                                //审核中
                                flag = 0;
                            } else if (status.equals("TranscodeFail")) {
                                //转码失败
                                flag = 0;
                            } else if (status.equals("Blocked")) {
                                //视频被屏蔽
                                flag = 0;
                            }
                        }
                    }catch (Exception e){
                        logger.error("当前视频不存在或已被删除");
                        flag = 0;
                        continue;
                    }
                }

                //根据flag标志位判断当前帖子中的所有视频状态
                Map<String, Object> parammap = new HashMap<>();
                parammap.put("postid", postid);

                if (flag==1){

                    //所有视频转码完成
                    parammap.put("isdel", 0);
                    parammap.put("postcontent", moduleArray.toString());
                    postService.updatePostStatus(parammap);//定义为正常播放

                }else if (flag==0){

                    //存在转码未完成的视频
                    parammap.put("isdel", 5);
                    postService.updatePostStatus(parammap);//定义为转码中

                }
            }
        }else {
            logger.info("不存在需要检测的帖子");
        }
    }
}
