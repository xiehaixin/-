package com.fun.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Sharing {

	private Integer XID;
	private String openID;
	private Integer Group_xid;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date sharingTime;
	private String WeChatGroup;
	public Integer getXID() {
		return XID;
	}
	public void setXID(Integer xID) {
		XID = xID;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public Integer getGroup_xid() {
		return Group_xid;
	}
	public void setGroup_xid(Integer group_xid) {
		Group_xid = group_xid;
	}
	public Date getSharingTime() {
		return sharingTime;
	}
	public void setSharingTime(Date sharingTime) {
		this.sharingTime = sharingTime;
	}
	public String getWeChatGroup() {
		return WeChatGroup;
	}
	public void setWeChatGroup(String weChatGroup) {
		WeChatGroup = weChatGroup;
	}
	@Override
	public String toString() {
		return "XCX__Group_sharing [XID=" + XID + ", openID=" + openID
				+ ", Group_xid=" + Group_xid + ", sharingTime=" + sharingTime
				+ ", WeChatGroup=" + WeChatGroup + "]";
	}
	
}
