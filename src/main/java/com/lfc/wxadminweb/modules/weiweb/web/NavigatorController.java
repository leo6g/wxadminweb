package com.lfc.wxadminweb.modules.weiweb.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
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
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weiweb.form.NavigatorForm;


/**
 * <h2></br>
 * 
 * @descript 
 * @author helei
 * @date 2016-11-10 09:30
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/weiweb/navigator")
public class NavigatorController extends BaseController{
	
	@Value("#{system.picture_path}") 
	private  String path;
	
	private static final String subImgPath = "navg"; 
	

	/**
	 * 跳转到导航菜单管理列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weiweb/navigator/navigator-list");
		return mv;
	}
	
	
	/**
	 * 分页查询导航菜单管理列表
	 * @param NavigatorForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("navigatorForm") NavigatorForm navigatorForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(navigatorForm);
		outputObject = getOutputObject(map, "navigatorService", "getList");
		return outputObject;
	}
	
	
	/**
	 *根据ID查询导航菜单管理
	 * @param NavigatorForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("NavigatorForm") NavigatorForm navigatorForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(navigatorForm);	
		outputObject = getOutputObject(map,"navigatorService","getById");
		return outputObject;
	}
	
	
	/**
	 * 查看所有导航菜单管理
	 * @param "NavigatorForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("NavigatorForm") NavigatorForm navigatorForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(navigatorForm);	
		outputObject = getOutputObject(map,"navigatorService","getAll");
		return outputObject;
	}
	
	
	/**
	 * 添加导航菜单管理
	 * @param NavigatorForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertNavigator")
	public OutputObject insertNavigator(@ModelAttribute("NavigatorForm") @Valid NavigatorForm navigatorForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(navigatorForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "navigatorService", "insertNavigator");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("导航菜单管理添加成功!");
			}
			return outputObject;
	}
	
	
	/**
	 * 编辑导航菜单管理
	 * @param NavigatorForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateNavigator")
	public OutputObject updateNavigator(@ModelAttribute("NavigatorForm") @Valid NavigatorForm navigatorForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(navigatorForm);
		outputObject = getOutputObject(map, "navigatorService", "updateNavigator");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("导航菜单管理编辑成功!");
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
		mav.setViewName("weixin/add-navigator");
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
		outputObject = getOutputObject(map,"navigatorService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-navigator");
		return mav;
	}
	
	
	/**
	 * 删除导航菜单管理
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteNavigator")
	public OutputObject deleteNavigator(@ModelAttribute("NavigatorForm") NavigatorForm navigatorForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(navigatorForm);
		outputObject = getOutputObject(map, "navigatorService", "deleteNavigator");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("导航菜单管理删除成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 逻辑删除导航菜单管理
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteNavigator")
	public OutputObject logicDeleteNavigator(@ModelAttribute("NavigatorForm") NavigatorForm navigatorForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(navigatorForm);
		outputObject = getOutputObject(map, "navigatorService", "logicDeleteNavigator");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 上传图标
	 * @param request
	 * @param response
	 * @param model
	 * @param mm
	 */
	@ResponseBody
	@RequestMapping(value = "doUpload")
	public OutputObject doUpload(HttpServletRequest request, HttpServletResponse response, Model model,ModelMap mm) {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile.setCusPath("system");//上传文件的目录 可以多级如xx/xx/xx
		uploadFile=UploadUtil.uploadFile(uploadFile,path+subImgPath);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		return outputObject;
	}
	
}
