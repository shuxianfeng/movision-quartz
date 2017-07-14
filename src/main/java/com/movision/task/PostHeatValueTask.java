package com.movision.task;

import com.movision.mybatis.post.entity.Post;
import com.movision.mybatis.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Post> list= postService.queryAllHeatValue();
        for (int i=0;i<list.size();i++){
            int id=list.get(i).getId();
            //根据id查询热度
            int heatvalue=postService.queryByIdHeatValue(id);
            if(heatvalue!=0){
                postService.updateHeatValue(id);
            }
        }
        logger.info("减少热度处理结束");
     }


}
