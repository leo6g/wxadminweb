package com.lfc.wxadminweb.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8644940421022266988L;
	/**
	 * 
	 */
	//验证码参数的添加
	private String captcha;
	public CaptchaUsernamePasswordToken(String captcha) {
		super();
		this.captcha = captcha;
	}

	public CaptchaUsernamePasswordToken(String username, String password,
			String captcha) {
		super(username, password);
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
