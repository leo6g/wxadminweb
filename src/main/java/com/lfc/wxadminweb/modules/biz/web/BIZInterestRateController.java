package com.lfc.wxadminweb.modules.biz.web;


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


import com.lfc.wxadminweb.modules.biz.form.BIZInterestRateForm;

/**
 * 
 * @descript 
 * @author jzp
 * @date 2016-10-26 10:34
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/interestrate")
public class BIZInterestRateController extends BaseController{
	
	
	
	/**
	 * 跳转到存贷款利率信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/interestrate/interestrate-list");
		return mv;
	}
	/**
	 * 分页查询存贷款利率信息列表
	 * @param BIZInterestRateForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("bIZInterestRateForm") BIZInterestRateForm bIZInterestRateForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZInterestRateForm);
		outputObject = getOutputObject(map, "bIZInterestRateService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询存贷款利率信息
	 * @param BIZInterestRateForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("BIZInterestRateForm") BIZInterestRateForm bIZInterestRateForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZInterestRateForm);	
		outputObject = getOutputObject(map,"bIZInterestRateService","getById");
		return outputObject;
	}
	/**
	 * 查看所有存贷款利率信息
	 * @param "BIZInterestRateForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("BIZInterestRateForm") BIZInterestRateForm bIZInterestRateForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZInterestRateForm);	
		outputObject = getOutputObject(map,"bIZInterestRateService","getAll");
		return outputObject;
	}
	/**
	 * 添加存贷款利率信息
	 * @param BIZInterestRateForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertBIZInterestRate")
	public OutputObject insertBIZInterestRate(@ModelAttribute("BIZInterestRateForm") @Valid BIZInterestRateForm bIZInterestRateForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(bIZInterestRateForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "bIZInterestRateService", "insertBIZInterestRate");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("存贷款利率信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑存贷款利率信息
	 * @param BIZInterestRateForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateBIZInterestRate")
	public OutputObject updateBIZInterestRate(@ModelAttribute("BIZInterestRateForm") @Valid BIZInterestRateForm bIZInterestRateForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZInterestRateForm);
		outputObject = getOutputObject(map, "bIZInterestRateService", "updateBIZInterestRate");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("存贷款利率信息编辑成功!");
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
		mav.setViewName("biz/interestrate/add-interestrate");
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
		outputObject = getOutputObject(map,"bIZInterestRateService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/interestrate/edit-interestrate");
		return mav;
	}
	/**
	 * 删除存贷款利率信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBIZInterestRate")
	public OutputObject deleteBIZInterestRate(@ModelAttribute("BIZInterestRateForm") BIZInterestRateForm bIZInterestRateForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZInterestRateForm);
		outputObject = getOutputObject(map, "bIZInterestRateService", "deleteBIZInterestRate");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("存贷款利率信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除存贷款利率信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteBIZInterestRate")
	public OutputObject logicDeleteBIZInterestRate(@ModelAttribute("BIZInterestRateForm") BIZInterestRateForm bIZInterestRateForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZInterestRateForm);
		outputObject = getOutputObject(map, "bIZInterestRateService", "logicDeleteBIZInterestRate");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	/**
	 * 查看所有存贷款利率信息
	 * @param "BIZInterestRateForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSubTypeByType")
	public OutputObject getSubTypeByType(@ModelAttribute("BIZInterestRateForm") BIZInterestRateForm bIZInterestRateForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZInterestRateForm);	
		outputObject = getOutputObject(map,"bIZInterestRateService","getSubTypeByType");
		return outputObject;
	}
	
	
}
