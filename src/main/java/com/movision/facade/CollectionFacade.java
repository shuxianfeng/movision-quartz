package com.movision.facade;

import com.movision.mybatis.collection.entity.Collection;
import com.movision.mybatis.collection.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author zhuangyuhao
 * @Date 2017/10/31 16:04
 */
@Service
public class CollectionFacade {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private FacadeHeatValue facadeHeatValue;

    /**
     * 机器人收藏帖子的时候调用
     *
     * @param postid
     * @param userid
     * @param type
     * @return
     */
    public int collectionPost4Robot(String postid, String userid, String type) {

        Collection collection = new Collection();
        collection.setUserid(Integer.parseInt(userid));
        collection.setPostid(Integer.parseInt(postid));
        collection.setType(Integer.parseInt(type));
        //先检查该用户有没有收藏过该帖子
        int count = collectionService.checkIsHave(collection);
        if (count == 0) {
            //该帖子的被收藏次数+1
            collectionService.addCollectionSum(Integer.parseInt(postid));
            //增加熱度
            facadeHeatValue.addHeatValue(Integer.parseInt(postid), 6, Integer.parseInt(userid));
            return collectionService.collectionPost(collection);
        } else {
            return -1;
        }
    }
}
