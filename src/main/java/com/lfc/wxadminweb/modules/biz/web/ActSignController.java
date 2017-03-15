package com.lfc.wxadminweb.modules.biz.web;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.exportexcel.ExportActSignData;
import com.lfc.wxadminweb.common.exportexcel.ExportTBStaffRecomendR2Data;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.biz.form.ActSignForm;
import com.lfc.wxadminweb.modules.report.form.TBStaffRecomendR2Form;

/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2017-02-16 09:21
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/actsign")
public class ActSignController extends BaseController{
	
	
	
	/**
	 * 跳转到活动报名列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/actsign/tsign-list");
		return mv;
	}
	/**
	 * 分页查询活动报名列表
	 * @param ActSignForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("actSignForm") ActSignForm actSignForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(actSignForm);
		outputObject = getOutputObject(map, "actSignService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询活动报名
	 * @param ActSignForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("ActSignForm") ActSignForm actSignForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(actSignForm);	
		outputObject = getOutputObject(map,"actSignService","getById");
		return outputObject;
	}
	/**
	 * 查看所有活动报名
	 * @param "ActSignForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("ActSignForm") ActSignForm actSignForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(actSignForm);	
		outputObject = getOutputObject(map,"actSignService","getAll");
		return outputObject;
	}
	/**
	 * 添加活动报名
	 * @param ActSignForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertActSign")
	public OutputObject insertActSign(@ModelAttribute("ActSignForm") @Valid ActSignForm actSignForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(actSignForm);
			
			outputObject = getOutputObject(map, "actSignService", "insertActSign");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("活动报名添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑活动报名
	 * @param ActSignForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateActSign")
	public OutputObject updateActSign(@ModelAttribute("ActSignForm") @Valid ActSignForm actSignForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(actSignForm);
		outputObject = getOutputObject(map, "actSignService", "updateActSign");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("活动报名编辑成功!");
		}
		return outputObject;
	}
	/**
	 * 去往添加页面
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("weixin/add-tsign");
		return mav;
	}
	/**
	 * 去往更新页面
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView edit(Model model) {
		ModelAndView mav=new ModelAndView();
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		map.put("id", request.getParameter("id"));
		outputObject = getOutputObject(map,"actSignService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-tsign");
		return mav;
	}
	/**
	 * 删除活动报名
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteActSign")
	public OutputObject deleteActSign(@ModelAttribute("ActSignForm") ActSignForm actSignForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(actSignForm);
		outputObject = getOutputObject(map, "actSignService", "deleteActSign");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("活动报名删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除活动报名
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteActSign")
	public OutputObject logicDeleteActSign(@ModelAttribute("ActSignForm") ActSignForm actSignForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(actSignForm);
		outputObject = getOutputObject(map, "actSignService", "logicDeleteActSign");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	//导出功能
	@RequestMapping(value = "exportData")
	public void exportData(ActSignForm actSignForm,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(actSignForm);
		outputObject = getOutputObject(map, "actSignService", "getAll");//根据查询条件，导出所有，没有分页
		try {
			ExportActSignData.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
	
}
