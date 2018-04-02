package com.fun.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PIC {

	private Integer XID;
	private Integer group_XID;
	private Integer PIC_iType;
	private String PIC_URL;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date CreateTime;
	private String Creater;
	public Integer getXID() {
		return XID;
	}
	public void setXID(Integer xID) {
		XID = xID;
	}
	public Integer getGroup_XID() {
		return group_XID;
	}
	public void setGroup_XID(Integer group_XID) {
		this.group_XID = group_XID;
	}
	public Integer getPIC_iType() {
		return PIC_iType;
	}
	public void setPIC_iType(Integer pIC_iType) {
		PIC_iType = pIC_iType;
	}
	public String getPIC_URL() {
		return PIC_URL;
	}
	public void setPIC_URL(String pIC_URL) {
		PIC_URL = pIC_URL;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	public String getCreater() {
		return Creater;
	}
	public void setCreater(String creater) {
		Creater = creater;
	}
	@Override
	public String toString() {
		return "PIC [XID=" + XID + ", group_XID=" + group_XID + ", PIC_iType="
				+ PIC_iType + ", PIC_URL=" + PIC_URL + ", CreateTime="
				+ CreateTime + ", Creater=" + Creater + "]";
	}
	
}
