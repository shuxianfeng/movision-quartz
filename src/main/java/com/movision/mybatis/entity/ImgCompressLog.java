package com.movision.mybatis.entity;

import java.util.Date;

public class ImgCompressLog {
    private Long id;

    private String sourceTable;

    private Long relationId;

    private String imgUrl;

    private Date processTime;

    private Integer compressFlag;
    
    private String desSize;
    
    

    public String getDesSize() {
		return desSize;
	}

	public void setDesSize(String desSize) {
		this.desSize = desSize;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable == null ? null : sourceTable.trim();
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Integer getCompressFlag() {
        return compressFlag;
    }

    public void setCompressFlag(Integer compressFlag) {
        this.compressFlag = compressFlag;
    }
}