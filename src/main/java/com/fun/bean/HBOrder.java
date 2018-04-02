package com.fun.bean;

public class HBOrder {

	private Integer id;					// ID
	private String appid;				// 小程序ID
	private Integer mch_id;				// 商户号
	private String nonce_str;			// 随机字符串
	private String out_trade_no;		// 商户订单号
	private Integer total_fee;			// 总金额
	private String spbill_create_ip;	// 终端IP
	private String send_name;			// 商户名称
	private Integer total_num;			// 红包发放总人数
	private String wishing;				// 红包祝福语
	private String act_name;			// 活动名称
	private String remark;				// 备注
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public String getWishing() {
		return wishing;
	}
	public void setWishing(String wishing) {
		this.wishing = wishing;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMch_id() {
		return mch_id;
	}
	public void setMch_id(Integer mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public Integer getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	@Override
	public String toString() {
		return "HBOrder [id=" + id + ", appid=" + appid + ", mch_id=" + mch_id
				+ ", nonce_str=" + nonce_str + ", out_trade_no=" + out_trade_no
				+ ", total_fee=" + total_fee + ", spbill_create_ip="
				+ spbill_create_ip + ", send_name=" + send_name
				+ ", total_num=" + total_num + ", wishing=" + wishing
				+ ", act_name=" + act_name + ", remark=" + remark + "]";
	}
}
