package com.fun.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Visit {

	private Integer XID;
	private Integer Group_XID;
	private String openID;
	private String nickName;
	private String avatarUrl;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date VisitDate;
	private String Visitmessage;
	private String WeChatGroup;
	private Project project;
	public Integer getXID() {
		return XID;
	}
	public void setXID(Integer xID) {
		XID = xID;
	}
	public Integer getGroup_XID() {
		return Group_XID;
	}
	public void setGroup_XID(Integer group_XID) {
		Group_XID = group_XID;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public Date getVisitDate() {
		return VisitDate;
	}
	public void setVisitDate(Date visitDate) {
		VisitDate = visitDate;
	}
	public String getVisitmessage() {
		return Visitmessage;
	}
	public void setVisitmessage(String visitmessage) {
		Visitmessage = visitmessage;
	}
	public String getWeChatGroup() {
		return WeChatGroup;
	}
	public void setWeChatGroup(String weChatGroup) {
		WeChatGroup = weChatGroup;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Override
	public String toString() {
		return "Visit [XID=" + XID + ", Group_XID=" + Group_XID + ", openID="
				+ openID + ", nickName=" + nickName + ", avatarUrl="
				+ avatarUrl + ", VisitDate=" + VisitDate + ", Visitmessage="
				+ Visitmessage + ", WeChatGroup=" + WeChatGroup + "]";
	}
	
}
