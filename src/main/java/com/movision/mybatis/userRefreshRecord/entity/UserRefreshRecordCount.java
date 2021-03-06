package com.movision.mybatis.userRefreshRecord.entity;

import org.springframework.data.annotation.Id;

/**
 * @Author zhanglei
 * @Date 2017/10/26 16:26
 */
public class UserRefreshRecordCount {

    @Id
    private Integer crileid;
    private Integer count;

    public Integer getCrileid() {
        return crileid;
    }

    public void setCrileid(Integer crileid) {
        this.crileid = crileid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
