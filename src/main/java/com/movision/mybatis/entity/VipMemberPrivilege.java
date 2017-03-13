package com.movision.mybatis.entity;

import java.util.Date;

/**
 * 会员自定义特权信息
 *
 * @author zhuangyuhao
 *
 */
public class VipMemberPrivilege {

    private Long id;

    private Long memberId;

    private String type;

    private String pinyin;

    private String name;

    private Long value;

    private String description;

    private Long stock;

    private Date addTime;

    private Date updateTime;

    private Date oldUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getOldUpdateTime() {
        return oldUpdateTime;
    }

    public void setOldUpdateTime(Date oldUpdateTime) {
        this.oldUpdateTime = oldUpdateTime;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
