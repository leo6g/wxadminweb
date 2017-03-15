package com.lfc.wxadminweb.modules.system.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.lfc.core.util.ControlConstants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.system.form.MenuForm;

@Controller
@RequestMapping(value = "system/menu")
public class MenuController extends BaseController {
	/**
	 * 去往菜单列表
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView menuList() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("system/menu/menu-list");
		return mav;
	}
	/**
	 * 查看菜单列表
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryMenuList")
	public OutputObject queryMenuList(@ModelAttribute("MenuForm") MenuForm menuForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(menuForm);
		//开始查询
		outputObject = getOutputObject(map,"menuService","queryMenuList");
		return outputObject;
	}
	
	/**
	 * 查看菜单列表tree--bootstrap-treeview-插件
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryMenuTree")
	public OutputObject queryMenuTree(@ModelAttribute("MenuForm") MenuForm menuForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(menuForm);	
		outputObject = getOutputObject(map,"menuService","queryMenuTree");
		return outputObject;
	}
	
	/**
	 * 查看菜单列表tree-jquery-treetable-插件
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryMenusTree")
	public OutputObject queryMenusTree(@ModelAttribute("MenuForm") MenuForm menuForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(menuForm);	
		outputObject = getOutputObject(map,"menuService","queryMenusTree");
		System.out.println(outputObject.getBeans());
		return outputObject;
	}
	/**
	 * 添加菜单信息
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertMenu")
	public OutputObject insertMenu(@ModelAttribute("MenuForm") @Valid MenuForm menuForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		String menuUrl=menuForm.getMenuUrl();
		if(menuForm.getMenuType().intValue()==1 && (menuUrl==null||"".equals(menuUrl))){
			OutputObject outputObject = new OutputObject(ControlConstants.RETURN_CODE.SYSTEM_ERROR,"菜单路径不能为空");
			return outputObject;
		}
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(menuForm);
		outputObject = getOutputObject(map,"menuService","insertMenuInfo");
		return outputObject;
	}
    /**
     * 编辑菜单信息
     * @param menuForm
     * @param result
     * @param request
     * @param model
     * @param mm
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "updateMenu")
	public OutputObject updateMenu(@ModelAttribute("MenuForm") @Valid MenuForm menuForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(menuForm);
		outputObject = getOutputObject(map,"menuService","updateMenuInfoById");
		return outputObject;
	}
	/**
	 * 去往添加菜单页面
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView menuAdd(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> menuMap=new HashMap<String, Object>();
		menuMap.put("menuId", request.getParameter("menuId"));
		model.addAllAttributes(menuMap);
		mav.setViewName("system/menu/add-menu");
		return mav;
	}
	/**
	 * 去往更新页面
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView menuEdit(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> menuMap=new HashMap<String, Object>();
		menuMap.put("menuId", request.getParameter("menuId"));
		model.addAllAttributes(menuMap);
		mav.setViewName("system/menu/edit-menu");
		return mav;
	}
	/**
	 * 获取菜单基本信息-更新页面用
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryMenu")
	public OutputObject queryMenu(@ModelAttribute("MenuForm") MenuForm menuForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(menuForm);	
		outputObject = getOutputObject(map,"menuService","selectByPrimaryKey");
		return outputObject;
	}
	/**
	 * 去往查看菜单页面
	 * @return
	 */
	@RequestMapping(value = "view")
	public ModelAndView menuView(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> menuMap=new HashMap<String, Object>();
		menuMap.put("menuId", request.getParameter("menuId"));
		model.addAllAttributes(menuMap);
		mav.setViewName("system/menu/view-menu");
		return mav;
	}
	/**
	 * 查看菜单详细信息
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "queryMenuView")
	public OutputObject queryMenuView(@ModelAttribute("MenuForm") MenuForm menuForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(menuForm);	
		outputObject = getOutputObject(map,"menuService","selectByPrimaryKey");
		return outputObject;
	}
	/**
	 * 删除菜单信息
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteMenu")
	public OutputObject deleteMenu(@ModelAttribute("MenuForm") MenuForm menuForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
        OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(menuForm);
		outputObject = getOutputObject(map,"menuService","deletetMenuInfoById");
		return outputObject;
	}

}
