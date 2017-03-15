package com.lfc.wxadminweb.modules.system.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.system.form.DepartLevelForm;
/**
 * 机构级别管理
 * @author helei
 * @date 2016-09-18
 */
@Controller
@RequestMapping(value = "system/deplevel")
public class DepartLevelController extends BaseController {
	
	
	/**
	 * 跳转到机构级别列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView depart(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("system/deplevel/deplevel-list");
		return mv;
	}
	
	
	/**
	 * 分业查询机构级别列表
	 * @param depLevelForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDepLevelListByPage")
	public OutputObject getDepLevelListByPage(@ModelAttribute("depLevelForm") DepartLevelForm depLevelForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(depLevelForm);
		outputObject = getOutputObject(map, "deplevelService", "getDepLevelListByPage");
		return outputObject;
	}
	
	
	/**
	 * 查询所有机构级别信息
	 * @param depLevelForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllDepLevel")
	public OutputObject getAllDepLevel(@ModelAttribute("depLevelForm") @Valid DepartLevelForm depLevelForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(depLevelForm);
		outputObject = getOutputObject(map, "deplevelService", "getAllDepLevel");
		return outputObject;
	}
	
	
	/**
	 * 根据机构级别ID,获取机构级别信息
	 * @param depLevelForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDepLevelById")
	public OutputObject getDepLevelById(@ModelAttribute("depLevelForm") @Valid DepartLevelForm depLevelForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(depLevelForm);
		outputObject = getOutputObject(map, "deplevelService", "getDepLevelById");
		return outputObject;
	}
	

	/**
	 * 新增机构级别信息
	 * @param depLevelForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertDepLevel")
	public OutputObject insertDepLevel(@ModelAttribute("depLevelForm") @Valid DepartLevelForm depLevelForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(depLevelForm);
		outputObject = getOutputObject(map, "deplevelService", "insertDepLevel");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("机构级别添加成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 修改机构级别信息
	 * @param depLevelForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateDepLevel")
	public OutputObject updateDepLevel(@ModelAttribute("depLevelForm") @Valid DepartLevelForm depLevelForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(depLevelForm);
		outputObject = getOutputObject(map, "deplevelService", "updateDepLevel");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("机构级别编辑成功!");
		}
		return outputObject;
	}
	

	
	/**
	 * 逻辑删除机构级别信息
	 * @param depLevelForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDelDepLevel")
	public OutputObject logicDelDepLevel(@ModelAttribute("depLevelForm") DepartLevelForm depLevelForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(depLevelForm);
		outputObject = getOutputObject(map, "deplevelService", "logicDelDepLevel");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("机构级别逻辑删除成功!");
		}
		return outputObject;
	}
	
	
}
