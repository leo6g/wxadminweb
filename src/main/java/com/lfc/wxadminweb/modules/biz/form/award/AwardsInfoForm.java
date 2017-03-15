package com.lfc.wxadminweb.modules.biz.form.award;

import com.lfc.wxadminweb.common.form.BaseForm;

public class AwardsInfoForm extends BaseForm {
	
	/**
	 * id
	 * */
	private String awardsId;
	
	/**
	 * 抽奖活动ID
	 * */
	private String activityId;	
	
	/**
	 * 奖品名称
	 * */
	private String name; 
	
	/**
	 * 奖品等级
	 * */
	private int rank; 
	
	/**
	 * 奖品数量
	 * */
	private int amount;  
	
	/**
	 * 中奖数量
	 * */
	private int drawnAmount;

	public String getAwardsId() {
		return awardsId;
	}

	public void setAwardsId(String awardsId) {
		this.awardsId = awardsId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getDrawnAmount() {
		return drawnAmount;
	}

	public void setDrawnAmount(int drawnAmount) {
		this.drawnAmount = drawnAmount;
	}

}
