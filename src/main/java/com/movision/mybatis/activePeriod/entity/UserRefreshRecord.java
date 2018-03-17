package com.movision.mybatis.activePeriod.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author shuxf
 * @Date 2018/3/14 13:37
 * mongodb中和瀑布流相关的用户刷帖记录表实体
 */
public class UserRefreshRecord implements Serializable{
    private String id;//mongo表中的_id
    private int userid;//用户id
    private int postid;//帖子id
    private int circleid;//圈子id
    private Date intime;//插入时间
    private int type;//类型
    private int labelid;//标签id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public int getCircleid() {
        return circleid;
    }

    public void setCircleid(int circleid) {
        this.circleid = circleid;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLabelid() {
        return labelid;
    }

    public void setLabelid(int labelid) {
        this.labelid = labelid;
    }
}
