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
import com.lfc.wxadminweb.modules.system.form.DepartForm;

/**
 * 组织机构管理
 * @author helei
 * @date 2016-09-14
 */
@Controller
@RequestMapping(value = "system/depart")
public class DepartController extends BaseController {
	
	
	/**
	 * 跳转到组织机构列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView depart(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("system/depart/depart-list");
		return mv;
	}
	
	
	/**
	 * 获取组织机构列表树信息
	 * @param testform
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDepartTree")
	public OutputObject getDepartTree(@ModelAttribute("DepartForm") DepartForm departform,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(departform);
		outputObject = getOutputObject(map, "departService", "getDepartTree");
		return outputObject;
	}
	
	
	/**
	 * 根据组织机构ID,获取组织机构信息
	 * @param testform
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDepartById")
	public OutputObject getDepartById(@ModelAttribute("DepartForm") DepartForm departform,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(departform);
		outputObject = getOutputObject(map, "departService", "getDepartById");
		return outputObject;
	}
	
	
	/**
	 * 新增组织机构信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertDepart")
	public OutputObject insertDepart(@ModelAttribute("DepartForm") @Valid DepartForm departform,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(departform);
		outputObject = getOutputObject(map, "departService", "insertDepart");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("组织机构添加成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 编辑组织机构信息	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateDepart")
	public OutputObject updateDepart(@ModelAttribute("DepartForm") @Valid DepartForm departform,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(departform);
		outputObject = getOutputObject(map, "departService", "updateDepart");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("组织机构编辑成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 逻辑删除组织机构信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDelDepart")
	public OutputObject logicDelDepart(@ModelAttribute("DepartForm") DepartForm departform,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(departform);
		outputObject = getOutputObject(map, "departService", "logicDelDepart");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("组织机构逻辑删除成功!");
		}
		return outputObject;
	}
	
	
	

}
