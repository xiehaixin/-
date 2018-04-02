package com.fun.bean;

public class XCXOrWx {

	private Integer id;
	private String xcx_OpenId;
	private String wx_OpenId;
	private String xcx_AppId;
	private String phone;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getXcx_OpenId() {
		return xcx_OpenId;
	}
	public void setXcx_OpenId(String xcx_OpenId) {
		this.xcx_OpenId = xcx_OpenId;
	}
	public String getWx_OpenId() {
		return wx_OpenId;
	}
	public void setWx_OpenId(String wx_OpenId) {
		this.wx_OpenId = wx_OpenId;
	}
	public String getXcx_AppId() {
		return xcx_AppId;
	}
	public void setXcx_AppId(String xcx_AppId) {
		this.xcx_AppId = xcx_AppId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "XCXOrWx [id=" + id + ", xcx_OpenId=" + xcx_OpenId
				+ ", wx_OpenId=" + wx_OpenId + ", xcx_AppId=" + xcx_AppId
				+ ", phone=" + phone + "]";
	}
}
