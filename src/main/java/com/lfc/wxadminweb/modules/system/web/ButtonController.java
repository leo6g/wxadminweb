package com.lfc.wxadminweb.modules.system.web;

import java.util.HashMap;
import java.util.List;
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
import com.lfc.wxadminweb.modules.system.form.ButtonForm;

@Controller
@RequestMapping(value = "system/button")
public class ButtonController extends BaseController {
	/**
	 * 去往查看菜单-按钮列表页面
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView btnList(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> menuMap=new HashMap<String, Object>();
		menuMap.put("menuId", request.getParameter("menuId"));
		model.addAllAttributes(menuMap);
		mav.setViewName("system/menu/btn-list");
		return mav;
	}
	/**
	 * 查看按钮列表
	 * @param ButtonForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryButtonList")
	public OutputObject queryButtonList(@ModelAttribute("ButtonForm") ButtonForm ButtonForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(ButtonForm);	
		outputObject = getOutputObject(map,"buttonService","queryButtonList");
		return outputObject;
	}
	/**
	 * 去往添加菜单-按钮页面
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView btnAdd(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> menuMap=new HashMap<String, Object>();
		menuMap.put("menuId", request.getParameter("menuId"));
		model.addAllAttributes(menuMap);
		mav.setViewName("system/menu/add-btn");
		return mav;
	}
	/**
	 * 添加按钮信息
	 * @param ButtonForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertButton")
	public OutputObject insertButton(@ModelAttribute("ButtonForm") @Valid ButtonForm ButtonForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(ButtonForm);
		outputObject = getOutputObject(map,"buttonService","insertButtonInfo");
	    return outputObject;
	}
	/**
	 * 去往更新页面
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView btnEdit(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> menuMap=new HashMap<String, Object>();
		menuMap.put("menuId", request.getParameter("menuId"));
		menuMap.put("btnId", request.getParameter("btnId"));
		model.addAllAttributes(menuMap);
		mav.setViewName("system/menu/edit-btn");
		return mav;
	}
	/**
	 * 获取菜单基本信息-更新页面用
	 * @param ButtonForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryButton")
	public OutputObject queryButton(@ModelAttribute("ButtonForm") @Valid ButtonForm ButtonForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(ButtonForm);	
		outputObject = getOutputObject(map,"buttonService","selectByPrimaryKey");
		return outputObject;
	}
	/**
	 * 更新按钮信息
	 * @param ButtonForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateButton")
	public OutputObject updateButton(@ModelAttribute("ButtonForm") @Valid ButtonForm ButtonForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(ButtonForm);
		outputObject = getOutputObject(map,"buttonService","updateButtonInfoById");
		return outputObject;
	}
	/**
	 * 删除按钮信息
	 * @param ButtonForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteButton")
	public OutputObject deleteButton(@ModelAttribute("ButtonForm") @Valid ButtonForm ButtonForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(ButtonForm);
		outputObject = getOutputObject(map,"buttonService","deletetButtonInfoById");
		return outputObject;
	}

}
