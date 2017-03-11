package com.movision.mybatis.entity;

import java.util.Date;

public class Member {
    private Long id;

    private String mobile;

    private String email;

    private String emailCheckCode;

    private String password;

    private Date registerTime;

    private Integer status;

    private String identify;

    private String enterpriseName;

    private Integer workType;

    private Long enterpriseEmployeeParentId;

    private String province;

    private String city;

    private String area;

    private String address;

    private Integer enterpriseType;

    private String enterpriseLogo;

    private String headShot;

    private String saleProductDesc;

    private Date enterpriseCreaterTime;

    private String registerCapital;

    private String currency;

    private String employeeNumber;

    private String coBusLicNum;

    private String companyBusinessLicenseImg;

    private String enterpriseProvince;

    private String enterpriseCity;

    private String enterpriseArea;

    private String enterpriseAddress;

    private String enterpriseTelephone;

    private String enterpriseFox;

    private String enterpriseWebSite;

    private String enterpriseLinkman;

    private String enterpriseLMDep;

    private String fixedTelephone;

    private String fixedMobile;

    private String QQ;

    private String personRealName;

    private String nickname;

    private Integer sex;

    private String personCompanyType;

    private String personIdentifyCard;

    private String personIDFrontImgUrl;

    private String personIDBackImgUrl;

    private Boolean isrecommend;

    private String reason;

    private String enterpriseDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getEmailCheckCode() {
        return emailCheckCode;
    }

    public void setEmailCheckCode(String emailCheckCode) {
        this.emailCheckCode = emailCheckCode == null ? null : emailCheckCode.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify == null ? null : identify.trim();
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public Integer getWorkType() {
        return workType;
    }

    public void setWorkType(Integer workType) {
        this.workType = workType;
    }

    public Long getEnterpriseEmployeeParentId() {
        return enterpriseEmployeeParentId;
    }

    public void setEnterpriseEmployeeParentId(Long enterpriseEmployeeParentId) {
        this.enterpriseEmployeeParentId = enterpriseEmployeeParentId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(Integer enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getEnterpriseLogo() {
        return enterpriseLogo;
    }

    public void setEnterpriseLogo(String enterpriseLogo) {
        this.enterpriseLogo = enterpriseLogo == null ? null : enterpriseLogo.trim();
    }

    public String getHeadShot() {
        return headShot;
    }

    public void setHeadShot(String headShot) {
        this.headShot = headShot == null ? null : headShot.trim();
    }

    public String getSaleProductDesc() {
        return saleProductDesc;
    }

    public void setSaleProductDesc(String saleProductDesc) {
        this.saleProductDesc = saleProductDesc == null ? null : saleProductDesc.trim();
    }

    public Date getEnterpriseCreaterTime() {
        return enterpriseCreaterTime;
    }

    public void setEnterpriseCreaterTime(Date enterpriseCreaterTime) {
        this.enterpriseCreaterTime = enterpriseCreaterTime;
    }

    public String getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(String registerCapital) {
        this.registerCapital = registerCapital == null ? null : registerCapital.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber == null ? null : employeeNumber.trim();
    }

    public String getCoBusLicNum() {
        return coBusLicNum;
    }

    public void setCoBusLicNum(String coBusLicNum) {
        this.coBusLicNum = coBusLicNum == null ? null : coBusLicNum.trim();
    }

    public String getCompanyBusinessLicenseImg() {
        return companyBusinessLicenseImg;
    }

    public void setCompanyBusinessLicenseImg(String companyBusinessLicenseImg) {
        this.companyBusinessLicenseImg = companyBusinessLicenseImg == null ? null : companyBusinessLicenseImg.trim();
    }

    public String getEnterpriseProvince() {
        return enterpriseProvince;
    }

    public void setEnterpriseProvince(String enterpriseProvince) {
        this.enterpriseProvince = enterpriseProvince == null ? null : enterpriseProvince.trim();
    }

    public String getEnterpriseCity() {
        return enterpriseCity;
    }

    public void setEnterpriseCity(String enterpriseCity) {
        this.enterpriseCity = enterpriseCity == null ? null : enterpriseCity.trim();
    }

    public String getEnterpriseArea() {
        return enterpriseArea;
    }

    public void setEnterpriseArea(String enterpriseArea) {
        this.enterpriseArea = enterpriseArea == null ? null : enterpriseArea.trim();
    }

    public String getEnterpriseAddress() {
        return enterpriseAddress;
    }

    public void setEnterpriseAddress(String enterpriseAddress) {
        this.enterpriseAddress = enterpriseAddress == null ? null : enterpriseAddress.trim();
    }

    public String getEnterpriseTelephone() {
        return enterpriseTelephone;
    }

    public void setEnterpriseTelephone(String enterpriseTelephone) {
        this.enterpriseTelephone = enterpriseTelephone == null ? null : enterpriseTelephone.trim();
    }

    public String getEnterpriseFox() {
        return enterpriseFox;
    }

    public void setEnterpriseFox(String enterpriseFox) {
        this.enterpriseFox = enterpriseFox == null ? null : enterpriseFox.trim();
    }

    public String getEnterpriseWebSite() {
        return enterpriseWebSite;
    }

    public void setEnterpriseWebSite(String enterpriseWebSite) {
        this.enterpriseWebSite = enterpriseWebSite == null ? null : enterpriseWebSite.trim();
    }

    public String getEnterpriseLinkman() {
        return enterpriseLinkman;
    }

    public void setEnterpriseLinkman(String enterpriseLinkman) {
        this.enterpriseLinkman = enterpriseLinkman == null ? null : enterpriseLinkman.trim();
    }

    public String getEnterpriseLMDep() {
        return enterpriseLMDep;
    }

    public void setEnterpriseLMDep(String enterpriseLMDep) {
        this.enterpriseLMDep = enterpriseLMDep == null ? null : enterpriseLMDep.trim();
    }

    public String getFixedTelephone() {
        return fixedTelephone;
    }

    public void setFixedTelephone(String fixedTelephone) {
        this.fixedTelephone = fixedTelephone == null ? null : fixedTelephone.trim();
    }

    public String getFixedMobile() {
        return fixedMobile;
    }

    public void setFixedMobile(String fixedMobile) {
        this.fixedMobile = fixedMobile == null ? null : fixedMobile.trim();
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ == null ? null : QQ.trim();
    }

    public String getPersonRealName() {
        return personRealName;
    }

    public void setPersonRealName(String personRealName) {
        this.personRealName = personRealName == null ? null : personRealName.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPersonCompanyType() {
        return personCompanyType;
    }

    public void setPersonCompanyType(String personCompanyType) {
        this.personCompanyType = personCompanyType == null ? null : personCompanyType.trim();
    }

    public String getPersonIdentifyCard() {
        return personIdentifyCard;
    }

    public void setPersonIdentifyCard(String personIdentifyCard) {
        this.personIdentifyCard = personIdentifyCard == null ? null : personIdentifyCard.trim();
    }

    public String getPersonIDFrontImgUrl() {
        return personIDFrontImgUrl;
    }

    public void setPersonIDFrontImgUrl(String personIDFrontImgUrl) {
        this.personIDFrontImgUrl = personIDFrontImgUrl == null ? null : personIDFrontImgUrl.trim();
    }

    public String getPersonIDBackImgUrl() {
        return personIDBackImgUrl;
    }

    public void setPersonIDBackImgUrl(String personIDBackImgUrl) {
        this.personIDBackImgUrl = personIDBackImgUrl == null ? null : personIDBackImgUrl.trim();
    }

    public Boolean getIsrecommend() {
        return isrecommend;
    }

    public void setIsrecommend(Boolean isrecommend) {
        this.isrecommend = isrecommend;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getEnterpriseDesc() {
        return enterpriseDesc;
    }

    public void setEnterpriseDesc(String enterpriseDesc) {
        this.enterpriseDesc = enterpriseDesc == null ? null : enterpriseDesc.trim();
    }
}