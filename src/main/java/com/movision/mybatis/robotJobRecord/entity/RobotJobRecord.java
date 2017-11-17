package com.movision.mybatis.robotJobRecord.entity;

import java.util.Date;

public class RobotJobRecord {
    private Integer id;

    private Integer jobid;

    private Date intime;

    private Integer immediate;

    public void setImmediate(Integer immediate) {
        this.immediate = immediate;
    }

    public Integer getImmediate() {

        return immediate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobid() {
        return jobid;
    }

    public void setJobid(Integer jobid) {
        this.jobid = jobid;
    }

    public Date getIntime() {
        return intime;
    }

    public void setIntime(Date intime) {
        this.intime = intime;
    }
}