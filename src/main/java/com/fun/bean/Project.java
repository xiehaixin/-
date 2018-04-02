package com.fun.bean;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Project {

	private Integer XID;
	private String sType;
	private String sName;
	private String sContent;
	private String sInitiator;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date CreateTime;
	private String WeChatGroup;
	private String signature;
	private String PIC_URL;
	private Integer count;
	private List<Visit> visits;
	private List<Sharing> sharings;
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getPIC_URL() {
		return PIC_URL;
	}
	public void setPIC_URL(String pIC_URL) {
		PIC_URL = pIC_URL;
	}
	public Integer getXID() {
		return XID;
	}
	public void setXID(Integer xID) {
		XID = xID;
	}
	public String getsType() {
		return sType;
	}
	public void setsType(String sType) {
		this.sType = sType;
	}
	public String getsName() {
		return sName;
	}
	public void setsName(String sName) {
		this.sName = sName;
	}
	public String getsContent() {
		return sContent;
	}
	public void setsContent(String sContent) {
		this.sContent = sContent;
	}
	public String getsInitiator() {
		return sInitiator;
	}
	public void setsInitiator(String sInitiator) {
		this.sInitiator = sInitiator;
	}
	public Date getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Date createTime) {
		CreateTime = createTime;
	}
	public String getWeChatGroup() {
		return WeChatGroup;
	}
	public void setWeChatGroup(String weChatGroup) {
		WeChatGroup = weChatGroup;
	}
	public List<Visit> getVisits() {
		return visits;
	}
	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}
	public List<Sharing> getSharings() {
		return sharings;
	}
	public void setSharings(List<Sharing> sharings) {
		this.sharings = sharings;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString() {
		return "Project [XID=" + XID + ", sType=" + sType + ", sName=" + sName
				+ ", sContent=" + sContent + ", sInitiator=" + sInitiator
				+ ", CreateTime=" + CreateTime + ", WeChatGroup=" + WeChatGroup
				+ ", signature=" + signature + ", PIC_URL=" + PIC_URL
				+ ", count=" + count + ", visits=" + visits + ", sharings="
				+ sharings + "]";
	}
	
	
	
	
}
