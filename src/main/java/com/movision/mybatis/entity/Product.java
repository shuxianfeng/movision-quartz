package com.movision.mybatis.entity;

import java.util.Date;

public class Product {
    private Long id;

    private String name;

    private Long createid;

    private Integer fcateid;

    private Integer scateid;

    private Long brandid;

    private Byte status;

    private Date publishtime;

    private Date lastmodified;

    private Double price;

    private String unit;

    private Double repository;

    private Double number;

    private Long hit;

    private String imgurl;

    private String paramids;

    private String paramvalues;

    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getCreateid() {
        return createid;
    }

    public void setCreateid(Long createid) {
        this.createid = createid;
    }

    public Integer getFcateid() {
        return fcateid;
    }

    public void setFcateid(Integer fcateid) {
        this.fcateid = fcateid;
    }

    public Integer getScateid() {
        return scateid;
    }

    public void setScateid(Integer scateid) {
        this.scateid = scateid;
    }

    public Long getBrandid() {
        return brandid;
    }

    public void setBrandid(Long brandid) {
        this.brandid = brandid;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Double getRepository() {
        return repository;
    }

    public void setRepository(Double repository) {
        this.repository = repository;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public Long getHit() {
        return hit;
    }

    public void setHit(Long hit) {
        this.hit = hit;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getParamids() {
        return paramids;
    }

    public void setParamids(String paramids) {
        this.paramids = paramids == null ? null : paramids.trim();
    }

    public String getParamvalues() {
        return paramvalues;
    }

    public void setParamvalues(String paramvalues) {
        this.paramvalues = paramvalues == null ? null : paramvalues.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}