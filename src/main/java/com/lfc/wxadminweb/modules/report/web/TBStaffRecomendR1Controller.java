package com.lfc.wxadminweb.modules.report.web;



import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.URL;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.exportexcel.ExportTBStaffRecomendR1Data;
import com.lfc.wxadminweb.common.web.BaseController;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.lfc.wxadminweb.modules.report.form.TBStaffRecomendR1Form;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2017-01-04 09:24
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("report/tBRecomendR1")
public class TBStaffRecomendR1Controller extends BaseController{
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到劳动竞赛个人排名列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("report/staffrecomendr1-list");
		return mv;
	}
	/**
	 * 分页查询劳动竞赛个人排名列表
	 * @param TBStaffRecomendR1Form
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("tBStaffRecomendR1Form") TBStaffRecomendR1Form tBStaffRecomendR1Form,
			BindingResult result,  Model model, ModelMap mm,HttpServletRequest request) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		//初始化日期
		if(StringUtils.isNotEmpty(request.getParameter("statisDate2"))){
			
			tBStaffRecomendR1Form.setStatisDate2(request.getParameter("statisDate2"));
		}else{
			tBStaffRecomendR1Form.init();
		}
		String loginUser = (String)((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName");
		tBStaffRecomendR1Form.setLoginUser(loginUser);
		Map<String, Object> map = BeanUtil.convertBean2Map(tBStaffRecomendR1Form);
		outputObject = getOutputObject(map, "tBStaffRecomendR1Service", "getList");
		return outputObject;
	}	
	
	@RequestMapping(value = "exportData")
	public void exportData(TBStaffRecomendR1Form tBStaffRecomendR1Form,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		tBStaffRecomendR1Form.setDepartName(URL.decode(request.getParameter("departName")));	
		tBStaffRecomendR1Form.setRealName(URL.decode(request.getParameter("realName")));
		//初始化日期
		if(StringUtils.isNotEmpty(request.getParameter("statisDate2"))){
			
			tBStaffRecomendR1Form.setStatisDate2(request.getParameter("statisDate2"));
		}else{
			tBStaffRecomendR1Form.init();
		}
		String loginUser = (String)((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName");
		tBStaffRecomendR1Form.setLoginUser(loginUser);
		Map<String, Object> map = BeanUtil.convertBean2Map(tBStaffRecomendR1Form);
		outputObject = getOutputObject(map, "tBStaffRecomendR1Service", "getListAll");//根据查询条件，导出所有，没有分页
		try {
			ExportTBStaffRecomendR1Data.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
}
