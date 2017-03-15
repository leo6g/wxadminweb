package com.lfc.wxadminweb.common.shiro;

import org.apache.shiro.authc.AuthenticationException;
/**
 * 验证码异常类
 * 
 * */
public class IncorrectCaptchaException extends AuthenticationException {
	public IncorrectCaptchaException() {
		// TODO Auto-generated constructor stub
		super();
	}

	public IncorrectCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public IncorrectCaptchaException(String message) {
		super(message);
	}

	public IncorrectCaptchaException(Throwable cause) {
		super(cause);
	}

}
