package com.movision.mybatis.entity;

import java.util.Date;

public class Shop {
    private Integer id;

    private String shopname;

    private Integer companyid;

    private String companyname;

    private String companyaccount;

    private String mobileBannerUrlT;

    private String mobileBannerUrlS;

    private String mobileBannerUrlF;

    private String bannerurl;

    private Date updatetime;

    private Integer opreatorid;

    private String status;

    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname == null ? null : shopname.trim();
    }

    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname == null ? null : companyname.trim();
    }

    public String getCompanyaccount() {
        return companyaccount;
    }

    public void setCompanyaccount(String companyaccount) {
        this.companyaccount = companyaccount == null ? null : companyaccount.trim();
    }

    public String getMobileBannerUrlT() {
        return mobileBannerUrlT;
    }

    public void setMobileBannerUrlT(String mobileBannerUrlT) {
        this.mobileBannerUrlT = mobileBannerUrlT == null ? null : mobileBannerUrlT.trim();
    }

    public String getMobileBannerUrlS() {
        return mobileBannerUrlS;
    }

    public void setMobileBannerUrlS(String mobileBannerUrlS) {
        this.mobileBannerUrlS = mobileBannerUrlS == null ? null : mobileBannerUrlS.trim();
    }

    public String getMobileBannerUrlF() {
        return mobileBannerUrlF;
    }

    public void setMobileBannerUrlF(String mobileBannerUrlF) {
        this.mobileBannerUrlF = mobileBannerUrlF == null ? null : mobileBannerUrlF.trim();
    }

    public String getBannerurl() {
        return bannerurl;
    }

    public void setBannerurl(String bannerurl) {
        this.bannerurl = bannerurl == null ? null : bannerurl.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getOpreatorid() {
        return opreatorid;
    }

    public void setOpreatorid(Integer opreatorid) {
        this.opreatorid = opreatorid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}