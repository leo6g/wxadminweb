package com.lfc.wxadminweb.modules.report.form;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.hibernate.validator.constraints.NotEmpty;

import com.lfc.wxadminweb.common.form.BaseForm;
public class TBStaffRecomendR1Form extends BaseForm{

	/*
	字段注释：员工柜员号码
	列名称:STAFF_CODE
	字段类型:VARCHAR2*/
	private String staffCode;
	/*
	字段注释：机构名称
	列名称:DEPART_NAME
	字段类型:VARCHAR2*/
	private String departName;
	/*
	字段注释：机构编码
	列名称:DEPART_CODE
	字段类型:VARCHAR2*/
	private String departCode;
	/*
	字段注释：当日关注用户数量
	列名称:OPEN_DAY_CNT
	字段类型:NUMBER*/
	private Integer openDayCnt;
	/*
	字段注释：按照累计关注用户数量排名
	列名称:OPEN_CNT_RANK
	字段类型:NUMBER*/
	private Integer openCntRank;
	/*
	字段注释：累计关注用户数量
	列名称:OPEN_CNT
	字段类型:NUMBER*/
	private Integer openCnt;
	/*
	字段注释：真实姓名
	列名称:REAL_NAME
	字段类型:VARCHAR2*/
	private String realName;
	/*
	字段注释：统计日期
	列名称:STATIS_DATE
	字段类型:DATE*/
	private Date statisDate;
	//统计日期--接收查询条件传过来的值
	private String statisDate2;
	//登录用户账号
	private String loginUser;
	
	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public void setStaffCode(String staffCode){
		this.staffCode = staffCode;
	}

	public String getStaffCode(){
		return staffCode;
	}

	public void setDepartName(String departName){
		this.departName = departName;
	}

	public String getDepartName(){
		return departName;
	}

	public void setDepartCode(String departCode){
		this.departCode = departCode;
	}

	public String getDepartCode(){
		return departCode;
	}

	public void setOpenDayCnt(Integer openDayCnt){
		this.openDayCnt = openDayCnt;
	}

	public Integer getOpenDayCnt(){
		return openDayCnt;
	}

	public void setOpenCntRank(Integer openCntRank){
		this.openCntRank = openCntRank;
	}

	public Integer getOpenCntRank(){
		return openCntRank;
	}

	public void setOpenCnt(Integer openCnt){
		this.openCnt = openCnt;
	}

	public Integer getOpenCnt(){
		return openCnt;
	}

	public void setRealName(String realName){
		this.realName = realName;
	}

	public String getRealName(){
		return realName;
	}

	public void setStatisDate(Date statisDate){
		this.statisDate = statisDate;
	}

	public Date getStatisDate(){
		return statisDate;
	}

	public  void init(){
		statisDate2=this.getStatisDate2();
	}

	public String getStatisDate2() {
		Date date=new Date();//取时间
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,-1);//把日期往前减一天.整数往后推,负数往前移动
		 date=calendar.getTime(); //这个时间就是日期往前减一天的结果 
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		statisDate2=sf.format(date);
		return statisDate2;
	}

	public void setStatisDate2(String statisDate2) {
		this.statisDate2 = statisDate2;
	}
	
}