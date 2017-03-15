package com.lfc.wxadminweb.modules.login;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.beanvalidator.ILoginCheck;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.servlet.ValidateCodeServlet;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.system.form.ManagerForm;

@Controller
@RequestMapping(value = "/")
public class LoginController extends BaseController {
	
	
	/**
	 * 跳转到登录页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "login")
	public ModelAndView login(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("login/login");
		return mv;
	}
	
	
	/**
	 * 后台用户登录
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loginCheck")
	public OutputObject loginCheck(@ModelAttribute("managerForm") @Validated(ILoginCheck.class) ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = new OutputObject();
		//boolean flag = ValidateCodeServlet.validate(request, managerForm.getValidateCode());
		boolean flag = true;
		if(flag){
			Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
			outputObject = getOutputObject(map, "managerService", "login");
			if(outputObject.getReturnCode().equals("0")){
				//登录成功，用户及角色信息放入session中
				getSession().setAttribute(Constants.USER_SESSION.USER, outputObject.getBean().get(Constants.USER_SESSION.USER));
				getSession().setAttribute(Constants.USER_SESSION.ROLEIDS, outputObject.getBean().get(Constants.USER_SESSION.ROLEIDS));
				getSession().setAttribute(Constants.USER_SESSION.PRIVILEGES, outputObject.getBean().get(Constants.USER_SESSION.PRIVILEGES));
			}
		}else{
			outputObject.setReturnCode(Constants.SYSTEM_LOGIN_STATE.STATE_1);
			outputObject.setReturnMessage("验证码错误.");
		}
		return outputObject;
	}
	
	
	/**
	 * 从session中获取信息
	 */
	@ResponseBody
	@RequestMapping(value = "getSession")
	public OutputObject getSessionInfo() {
		OutputObject outputObject = new OutputObject();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(Constants.USER_SESSION.USER, getSession().getAttribute(Constants.USER_SESSION.USER));
		resultMap.put(Constants.USER_SESSION.ROLEIDS, getSession().getAttribute(Constants.USER_SESSION.ROLEIDS));
		resultMap.put(Constants.USER_SESSION.PRIVILEGES, getSession().getAttribute(Constants.USER_SESSION.PRIVILEGES));
		outputObject.setReturnCode("0");
		outputObject.setReturnMessage("获取成功.");
		outputObject.setBean(resultMap);
		return outputObject;
	}

	
	/**
	 * 退出登录
	 */
	@RequestMapping(value = "logout")
	public void logout(HttpServletResponse response, ModelAndView mv) {
		SecurityUtils.getSubject().logout();
		try {
			response.sendRedirect("login");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
