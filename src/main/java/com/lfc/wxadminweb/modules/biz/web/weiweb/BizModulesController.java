package com.lfc.wxadminweb.modules.biz.web.weiweb;


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
import com.lfc.wxadminweb.modules.biz.form.weiweb.ModulesForm;



/**
 * 
 * @descript 
 * @author jzp
 * @date 2017-02-04 19:00
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/modules")
public class BizModulesController extends BaseController{
	
	
	
	/**
	 * 跳转到微网站模块信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/weiweb/modules-list");
		return mv;
	}
	/**
	 * 分页查询微网站模块信息列表
	 * @param modulesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("modulesForm") ModulesForm modulesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(modulesForm);
		map.put("orderByClause","CREATE_TIME");
		outputObject = getOutputObject(map, "modulesService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询微网站模块信息
	 * @param modulesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("modulesForm") ModulesForm modulesForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(modulesForm);	
		outputObject = getOutputObject(map,"modulesService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微网站模块信息
	 * @param "modulesForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("modulesForm") ModulesForm modulesForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(modulesForm);
		map.put("orderByClause","CREATE_TIME");	
		outputObject = getOutputObject(map,"modulesService","getAll");
		return outputObject;
	}
	/**
	 * 添加微网站模块信息
	 * @param modulesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertBizModules")
	public OutputObject insertBizModules(@ModelAttribute("modulesForm") @Valid ModulesForm modulesForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(modulesForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "modulesService", "insertBizModules");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微网站模块信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微网站模块信息
	 * @param modulesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateBizModules")
	public OutputObject updateBizModules(@ModelAttribute("modulesForm") @Valid ModulesForm modulesForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(modulesForm);
		outputObject = getOutputObject(map, "modulesService", "updateBizModules");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微网站模块信息编辑成功!");
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
		mav.setViewName("biz/weiweb/add-modules");
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
		outputObject = getOutputObject(map,"modulesService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/weiweb/edit-modules");
		return mav;
	}
	/**
	 * 删除微网站模块信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBizModules")
	public OutputObject deleteBizModules(@ModelAttribute("modulesForm") ModulesForm modulesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(modulesForm);
		outputObject = getOutputObject(map, "modulesService", "deleteBizModules");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微网站模块信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微网站模块信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteBizModules")
	public OutputObject logicDeleteBizModules(@ModelAttribute("modulesForm") ModulesForm modulesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(modulesForm);
		outputObject = getOutputObject(map, "modulesService", "logicDeleteBizModules");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
