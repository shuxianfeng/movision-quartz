package com.movision.facade;

import com.movision.mybatis.followUser.entity.FollowUser;
import com.movision.mybatis.followUser.service.FollowUserService;
import com.movision.mybatis.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhuangyuhao
 * @Date 2017/10/31 16:55
 */
@Service
public class PostFacade {
    private static Logger log = LoggerFactory.getLogger(PostFacade.class);

    @Autowired
    private FollowUserService followUserService;

    @Autowired
    private UserService userService;

    @Autowired
    private FacadeHeatValue facadeHeatValue;

    /**
     * 关注作者
     *
     * @param userid          用户
     * @param interestedusers 被关注的用户
     * @return
     */
    public int concernedAuthorUser(int userid, int interestedusers) {
        Map map = new HashMap();
        if (userid != interestedusers) {
            map.put("userid", userid);
            map.put("interestedusers", interestedusers);
            //查询该用户有没有关注过
            int result = followUserService.yesOrNo(map);

            if (result == 0) {

                FollowUser followUser = new FollowUser();
                followUser.setIntime(new Date());
                followUser.setInterestedusers(interestedusers);
                followUser.setUserid(userid);
                //1 插入关注用户流水
                followUserService.insertSelective(followUser);
                //2 被关注人的粉丝数加1
                followUserService.insertUserFans(interestedusers);//被关注人
                //3 增加用户总关注数 attention
                userService.updateUserAttention(userid);//关注人
                //4 被关注人增加热度
                facadeHeatValue.addUserHeatValue(1, interestedusers);
                return 0;
            } else {
                return 1;
            }
        }
        return 2;
    }
}
