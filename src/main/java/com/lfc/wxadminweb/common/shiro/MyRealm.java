package com.lfc.wxadminweb.common.shiro;



import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lfc.core.bean.InputObject;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.control.IControlService;


/*实现realm需要调用后台，之后自己实现，目前只重写session*/
public class MyRealm extends AuthorizingRealm{	
	private static final Logger logger = LoggerFactory
			.getLogger(AuthorizingRealm.class);
	
	//@Resource(name = "controlService")
	private IControlService controlService; // 前后工程调用服务

	/** 通过inputObject调用Service获取outputObject **/
	private OutputObject getOutputObject(InputObject inputObject) {
		OutputObject outputObject = null;
		String serviceName = inputObject.getService();
		String methodName = inputObject.getMethod();
		if (StringUtils.isNotBlank(serviceName)
				&& StringUtils.isNotBlank(methodName)) {
			outputObject = this.execute(inputObject);
		} else {
			logger.error("服务名和方法名不能为空!");

		}
		return outputObject;
	}
	
	/* 调用后台执行service方法 */
	private OutputObject execute(InputObject inputObject) {
		OutputObject outputObject = null;
		try {
			outputObject = controlService.execute(inputObject);

		} catch (Exception e) {
			logger.error("Invoke Service Error.", inputObject.getService()
					+ "." + inputObject.getMethod(), e);
			e.printStackTrace();
		}
		return outputObject;
	}

	/**
	   * 授权操作
	   */
	  @Override
	  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
      /*中间部分自己写，调用service层获取数据即可*/	  
	    return  null;
	  }
	  /**
	   * 身份认证
	   */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token)  {

		CaptchaUsernamePasswordToken tokens=(CaptchaUsernamePasswordToken) token;
		
		OutputObject outputObject = new OutputObject();
		Session session = SecurityUtils.getSubject().getSession();
		String code=tokens.getCaptcha();
		String existCode=(String) session.getAttribute("validateCode");
		boolean flag = code.toUpperCase().equals(existCode);
		if(!flag)
		{
			 throw new IncorrectCaptchaException();
		}
	      Map<String,Object> map =new HashMap<String,Object>();
	      map.put("userName", tokens.getUsername());
	      map.put("passWord", tokens.getPassword());
	      InputObject input=new InputObject();
	      input.setParams(map);
	      input.setService("managerService");
	      input.setMethod("login");
		outputObject = getOutputObject(input);
		if(outputObject.getReturnCode().equals("0")){
				//登录成功，用户及角色信息放入session中
				session.setAttribute(Constants.USER_SESSION.USER, outputObject.getBean().get(Constants.USER_SESSION.USER));
				session.setAttribute(Constants.USER_SESSION.ROLEIDS, outputObject.getBean().get(Constants.USER_SESSION.ROLEIDS));
				session.setAttribute(Constants.USER_SESSION.PRIVILEGES, outputObject.getBean().get(Constants.USER_SESSION.PRIVILEGES));
			}
	    Map<String,Object> user=new HashMap<String, Object>();
	    user=(Map<String,Object> )outputObject.getBean().get(Constants.USER_SESSION.USER);
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				(String)user.get("userName"), (String)user.get("passWord"),getName());
				//ByteSource.Util.bytes(user.get("userLoginId")+user.get("loginCertifCrkey")), // salt=username+salt
				//getName() 
		
		return authenticationInfo;
		//登录实例
/*		String username = managerForm.getUserName();
		String password=MD5Util.getMD5(managerForm.getPassWord());
		String code = managerForm.getValidateCode();
		CaptchaUsernamePasswordToken token=new CaptchaUsernamePasswordToken(username, password, code);
		Subject subject = SecurityUtils.getSubject();
		subject.login(token);*/
		
	}
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
