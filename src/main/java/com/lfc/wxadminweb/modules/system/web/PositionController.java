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
import com.lfc.wxadminweb.modules.system.form.PositionForm;

/**
 * 职位管理
 * @author helei
 * @date 2016-09-23
 */
@Controller
@RequestMapping(value = "system/position")
public class PositionController extends BaseController {
	
	
	/**
	 * 跳转到职位列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView goPositionList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("system/position/position-list");
		return mv;
	}
	
	
	/**
	 * 分业查询职位列表
	 * @param positionForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPositionListByPage")
	public OutputObject getPositionListByPage(@ModelAttribute("positionForm") PositionForm positionForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(positionForm);
		outputObject = getOutputObject(map, "positionService", "getPositionListByPage");
		return outputObject;
	}
	
	
	/**
	 * 查询所有职位信息
	 * @param positionForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllPosition")
	public OutputObject getAllPosition(@ModelAttribute("positionForm") PositionForm positionForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(positionForm);
		outputObject = getOutputObject(map, "positionService", "getAllPosition");
		return outputObject;
	}
	
	
	/**
	 * 根据职位ID,获取职位信息
	 * @param positionForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPositionById")
	public OutputObject getPositionById(@ModelAttribute("positionForm") PositionForm positionForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(positionForm);
		outputObject = getOutputObject(map, "positionService", "getPositionById");
		return outputObject;
	}
	

	/**
	 * 新增职位信息
	 * @param positionForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertPosition")
	public OutputObject insertPosition(@ModelAttribute("positionForm") @Valid PositionForm positionForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(positionForm);
		outputObject = getOutputObject(map, "positionService", "insertPosition");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("职位添加成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 修改职位信息
	 * @param positionForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePosition")
	public OutputObject updatePosition(@ModelAttribute("positionForm") @Valid PositionForm positionForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(positionForm);
		outputObject = getOutputObject(map, "positionService", "updatePosition");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("职位编辑成功!");
		}
		return outputObject;
	}
	

	
	/**
	 * 逻辑删除职位信息
	 * @param positionForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDelPosition")
	public OutputObject logicDelPosition(@ModelAttribute("positionForm") PositionForm positionForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(positionForm);
		outputObject = getOutputObject(map, "positionService", "logicDelPosition");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("职位逻辑删除成功!");
		}
		return outputObject;
	}
	
	
}
