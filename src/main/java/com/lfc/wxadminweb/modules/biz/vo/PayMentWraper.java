package com.lfc.wxadminweb.modules.biz.vo;

import java.util.ArrayList;
import java.util.List;

public class PayMentWraper {
	private String appId;
	private String pwd;
	private List<PayMent> data = new ArrayList<PayMent>();
	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public List<PayMent> getData() {
		return data;
	}
	public void setData(List<PayMent> data) {
		this.data = data;
	}
}
