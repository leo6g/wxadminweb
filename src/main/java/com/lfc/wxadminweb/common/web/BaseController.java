package com.lfc.wxadminweb.common.web;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.InputObject;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.ControlConstants;
import com.lfc.wxadminweb.common.control.IControlService;
import com.lfc.wxadminweb.common.utils.PaginationUtil;

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource(name = "controlService")
	private IControlService controlService; // 前后工程调用服务

	/** 通过inputObject调用Service获取outputObject **/
	public OutputObject getOutputObject(InputObject inputObject) {
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

	/** 通过入参和service名称和方法名称构造inputObject调用Service获取outputObject **/
	public OutputObject getOutputObject(Map<String, Object> params,
			String service, String method) {
		OutputObject outputObject = null;
		if (StringUtils.isNotBlank(service) && StringUtils.isNotBlank(method)) {
			//计算分页参数
			PaginationUtil.initPagination(params);
			InputObject inputObject = new InputObject();
			inputObject.setParams(params);
			inputObject.setService(service);
			inputObject.setMethod(method);
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

	/* bean校验ajax返回 */
	protected OutputObject returnValidatorAjaxResult(BindingResult result) {
		getParamValidateLog(result);
		OutputObject outputObject = new OutputObject();
		outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
		outputObject.setReturnMessage(result.getFieldError().getDefaultMessage());
		return outputObject;
	}

	/* bean校验String返回 */
	protected String returnValidatorStrResult(BindingResult result) {
		getParamValidateLog(result);
		return "error/500";
	}

	/* bean校验ModelAndView返回 */
	protected ModelAndView returnValidatorMavResult(BindingResult result) {
		getParamValidateLog(result);
		return new ModelAndView("error/500");
	}
	/**
	 * 输出参数校验错误信息
	 * @param result
	 */
	private void getParamValidateLog(BindingResult result)
	{
	List<ObjectError> ls=result.getAllErrors();  
    for (int i = 0; i < ls.size(); i++) {  
        logger.error("参数校验错误{}",ls.get(i)); 
    }  
	}
	/**
	 * 得到request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		return request;
	}

	/** Get the current Session **/
	public Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils
						.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), false));
		// Date 类型转换
		/*
		 * binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
		 * 
		 * @Override public void setAsText(String text) {
		 * setValue(DateUtils.parseDate(text)); }
		 * 
		 * @Override public String getAsText() { Object value = getValue();
		 * return value != null ? DateUtils.formatDateTime((Date)value) : ""; }
		 * });
		 */
	}
	
	public IControlService getIControlService(){
		return this.controlService;
	}

}
