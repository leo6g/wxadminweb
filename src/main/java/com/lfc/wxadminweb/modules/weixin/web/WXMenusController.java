package com.lfc.wxadminweb.modules.weixin.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import com.github.sd4324530.fastweixin.api.CustomAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXMenusForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2016-10-11 15:43
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/menus")
public class WXMenusController extends BaseController{
	
	@Value("#{system.dev_mode}")  
	private boolean dev_mode;
	@Value("#{system.appId}")  
	private String appId;
	@Value("#{system.secret}")  
	private String secret;
	
	@Value("#{system.local_server}")
	private String localServer;
	
	@Value("#{system.viewImage}")
	private String viewImage;
	
	@Value("#{system.siteDomain_name}")
	private String siteDomain_name;
	
	/**
	 * 跳转到微信菜单列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "goWXMenusListPage")
	public ModelAndView WXMenus(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("weixin/menus-list");
		return mv;
	}
	/**
	 * 分页查询微信菜单列表
	 * @param WXMenusForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getWXMenusListByPage")
	public OutputObject getWXMenusListByPage(@ModelAttribute("wXMenusForm") WXMenusForm wXMenusForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);
		outputObject = getOutputObject(map, "wXMenusService", "getWXMenusListByPage");
		return outputObject;
	}
	/**
	 * 获取微信菜单列表树信息
	 * @param testform
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMenusTree")
	public OutputObject getDepartTree(@ModelAttribute("WXMenusForm") WXMenusForm wXMenusForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);
		outputObject = getOutputObject(map, "wXMenusService", "getMenusTree");
		return outputObject;
	}
	/**
	 *根据ID查询微信菜单
	 * @param WXMenusForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getWXMenusById")
	public OutputObject getWXMenusById(@ModelAttribute("WXMenusForm") WXMenusForm wXMenusForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);	
		outputObject = getOutputObject(map,"wXMenusService","getWXMenusById");
		return outputObject;
	}
	/**
	 * 查看所有微信菜单
	 * @param "WXMenusForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllWXMenus")
	public OutputObject getAllWXMenus(@ModelAttribute("WXMenusForm") WXMenusForm wXMenusForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);	
		map.put("orderByClause", "SORT");
		outputObject = getOutputObject(map,"wXMenusService","getAllWXMenus");
		return outputObject;
	}
	/**
	 * 添加微信菜单
	 * @param WXMenusForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXMenus")
	public OutputObject insertWXMenus(@ModelAttribute("WXMenusForm") @Valid WXMenusForm wXMenusForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXMenusService", "insertWXMenus");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信菜单添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信菜单
	 * @param WXMenusForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXMenus")
	public OutputObject updateWXMenus(@ModelAttribute("WXMenusForm") @Valid WXMenusForm wXMenusForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		System.out.println("------------"+wXMenusForm.getName()+request.getParameter("name"));
		wXMenusForm.setName(request.getParameter("name"));
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);
		outputObject = getOutputObject(map, "wXMenusService", "updateWXMenus");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信菜单编辑成功!");
		}
		return outputObject;
	}
	/**
	 * 去往添加页面
	 * @return
	 */
	@RequestMapping(value = "goAddWXMenusInfo")
	public ModelAndView goAddWXMenusInfo() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("weixin/add-menus");
		return mav;
	}
	/**
	 * 去往更新页面
	 * @return
	 */
	@RequestMapping(value = "goUpdateWXMenusInfo")
	public ModelAndView goUpdateWXMenusInfo(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", request.getParameter("id"));
		outputObject = getOutputObject(map,"wXMenusService","selectByPrimaryKey");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/update-menus");
		return mav;
	}
	/**
	 * 删除微信菜单
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delWXMenus")
	public OutputObject delWXMenus(@ModelAttribute("WXMenusForm") WXMenusForm wXMenusForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);
		outputObject = getOutputObject(map, "wXMenusService", "delWXMenus");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信菜单删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信菜单
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDelWXMenus")
	public OutputObject logicDelWXMenus(@ModelAttribute("WXMenusForm") WXMenusForm wXMenusForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);
		outputObject = getOutputObject(map, "wXMenusService", "logicDelWXMenus");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	@ResponseBody
	@RequestMapping(value = "synchrWXMenu")
	public OutputObject synchrWXMenu(HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String appId ="";
		String secret ="";
		if(dev_mode){
			appId = this.appId;
			secret = this.secret;
		}else{
			OutputObject outputObjectForConfig = null;
			Map<String, Object> mapForConfig = new HashMap<String, Object>();
			mapForConfig.put("type", "WXOfficalAcct");
			outputObjectForConfig = getOutputObject(mapForConfig,"wXConfigService","getByType");
			Map<String,String> mapForResult = (HashMap<String,String>)outputObjectForConfig.getObject();
			appId = mapForResult.get("appId");
			secret = mapForResult.get("appSecret");
		}
		map.put("appid", appId);
		map.put("secret", secret);
		map.put("siteDomain_name", siteDomain_name);
		OutputObject outputObject = getOutputObject(map, "wXMenusService", "synchrWXMenu");
		return outputObject;
	}
	/**
	 *判断menuKey是否存在
	 * @param wXMenusForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkMenuKey")
	public OutputObject checkPassword(@ModelAttribute("WXMenusForm") WXMenusForm wXMenusForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMenusForm);
		outputObject = getOutputObject(map, "wXMenusService", "checkMenuKey");
		return outputObject;
	}
	
	
	
	
	
	
}
