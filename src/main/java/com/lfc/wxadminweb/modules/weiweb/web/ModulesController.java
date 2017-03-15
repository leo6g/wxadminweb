package com.lfc.wxadminweb.modules.weiweb.web;


import java.util.HashMap;
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
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weiweb.form.ModulesForm;

/**
 * 
 * @descript 
 * @author jzp
 * @date 2016-11-07 09:45
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/weiweb/modules")
public class ModulesController extends BaseController{
	
	
	
	/**
	 * 跳转到微网站模块信息管理列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weiweb/modules/modules-list");
		return mv;
	}
	/**
	 * 分页查询微网站模块信息管理列表
	 * @param WWModulesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("ModulesForm") ModulesForm wWModulesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);
		outputObject = getOutputObject(map, "wWModulesService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询微网站模块信息管理
	 * @param WWModulesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("ModulesForm") ModulesForm wWModulesForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);	
		outputObject = getOutputObject(map,"wWModulesService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微网站模块信息管理
	 * @param "WWModulesForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("ModulesForm") ModulesForm wWModulesForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);	
		outputObject = getOutputObject(map,"wWModulesService","getAll");
		return outputObject;
	}
	
	/**
	 * 查看模块列表tree--bootstrap-treeview-插件
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryModuleTree")
	public OutputObject queryModuleTree(@ModelAttribute("ModulesForm") ModulesForm wWModulesForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);	
		outputObject = getOutputObject(map,"wWModulesService","queryModuleTree");
		return outputObject;
	}
	
	/**
	 * 查看模块列表tree-jquery-treetable-插件
	 * @param menuForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryModulesTree")
	public OutputObject queryModulesTree(@ModelAttribute("ModulesForm") ModulesForm wWModulesForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);	
		outputObject = getOutputObject(map,"wWModulesService","queryModulesTree");
		System.out.println(outputObject.getBeans());
		return outputObject;
	}
	
	/**
	 * 添加微网站模块信息管理
	 * @param WWModulesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWWModules")
	public OutputObject insertWWModules(@ModelAttribute("ModulesForm") @Valid ModulesForm wWModulesForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wWModulesService", "insertWWModules");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微网站模块信息管理添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微网站模块信息管理
	 * @param WWModulesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWWModules")
	public OutputObject updateWWModules(@ModelAttribute("ModulesForm") @Valid ModulesForm wWModulesForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);
		outputObject = getOutputObject(map, "wWModulesService", "updateWWModules");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微网站模块信息管理编辑成功!");
		}
		return outputObject;
	}
	/**
	 * 去往添加页面
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> moduleMap=new HashMap<String, Object>();
		moduleMap.put("moduleId", request.getParameter("moduleId"));
		model.addAllAttributes(moduleMap);
		mav.setViewName("weiweb/modules/add-modules");
		return mav;
	}
	/**
	 * 去往查看模块页面
	 * @return
	 */
	@RequestMapping(value = "view")
	public ModelAndView menuView(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> moduleMap=new HashMap<String, Object>();
		moduleMap.put("moduleId", request.getParameter("moduleId"));
		model.addAllAttributes(moduleMap);
		mav.setViewName("weiweb/modules/view-modules");
		return mav;
	}
	/**
	 * 去往更新页面
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView edit(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> moduleMap=new HashMap<String, Object>();
		moduleMap.put("moduleId", request.getParameter("moduleId"));
		model.addAllAttributes(moduleMap);
		mav.setViewName("weiweb/modules/edit-modules");
		return mav;
	}
	/**
	 * 删除微网站模块信息管理
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWWModules")
	public OutputObject deleteWWModules(@ModelAttribute("ModulesForm") ModulesForm wWModulesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);
		outputObject = getOutputObject(map, "wWModulesService", "deleteWWModules");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微网站模块信息管理删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微网站模块信息管理
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWWModules")
	public OutputObject logicDeleteWWModules(@ModelAttribute("ModulesForm") ModulesForm wWModulesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wWModulesForm);
		outputObject = getOutputObject(map, "wWModulesService", "logicDeleteWWModules");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
