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
import com.lfc.wxadminweb.modules.system.form.DictionaryForm;
import com.lfc.wxadminweb.modules.system.form.DictionaryGroupForm;

@Controller
@RequestMapping(value = "system/dic")
public class DictionaryController extends BaseController {
	
	
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("system/dictionary/dic-list");
		return mv;
	}
	
	
	/**
	 * 分业查询数据字典组数据
	 * @param dictionaryGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("dicGroupForm") DictionaryGroupForm dicGroupForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(dicGroupForm);
		outputObject = getOutputObject(map, "dictionaryService", "getList");
		return outputObject;
	}
	
	
	
	/**
	 * 新增数据字典组信息
	 * @param dictionaryForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertDicGroup")
	public OutputObject insertManager(@ModelAttribute("dicGroupForm")@Valid DictionaryGroupForm dicGroupForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(dicGroupForm);
		outputObject = getOutputObject(map, "dictionaryService", "insertDicGroup");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("字典组添加成功!");
		}
		return outputObject;
	}
	
	

	/**
	 * 修改数据字典组信息
	 * @param dictionaryForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateDicGroup")
	public OutputObject updateManager(@ModelAttribute("dicGroupForm")@Valid DictionaryGroupForm dicGroupForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(dicGroupForm);
		outputObject = getOutputObject(map, "dictionaryService", "updateDicGroup");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("修改成功!");
		}
		return outputObject;
	}
	
	
	
	/**
	 * 逻辑删除数据字典组信息
	 * @param dictionaryForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteDicGroup")
	public OutputObject deleteDicGroup(@ModelAttribute("dicGroupForm") DictionaryGroupForm dicGroupForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		dicGroupForm.setDeleteFlag(-1);
		Map<String, Object> map = BeanUtil.convertBean2Map(dicGroupForm);
		outputObject = getOutputObject(map, "dictionaryService", "deleteDicGroup");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("删除成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 跳转到字典详情页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "detail")
	public ModelAndView detail(@ModelAttribute("dicForm") DictionaryForm dicForm,
			BindingResult result, HttpServletRequest request, Model model, ModelAndView mv) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(dicForm);
		outputObject = getOutputObject(map, "dictionaryService", "getDicGroupById");
		model.addAttribute("dicGroup", outputObject.getObject());
		mv.setViewName("system/dictionary/dic-detail");
		return mv;
	}
	
	
	/**
	 * 获取数据字典树信息
	 * @param dicForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDicTree")
	public OutputObject getDicTree(@ModelAttribute("dicForm") DictionaryForm dicForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(dicForm);
		outputObject = getOutputObject(map, "dictionaryService", "getDicTree");
		return outputObject;
	}
	
	
	
	/**
	 * 新增数据字典组信息
	 * @param dictionaryForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertDic")
	public OutputObject insertDic(@ModelAttribute("dicForm") @Valid DictionaryForm dicForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(dicForm);
		outputObject = getOutputObject(map, "dictionaryService", "insertDic");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("字典添加成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 修改数据字典信息
	 * @param dicForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateDic")
	public OutputObject updateDic(@ModelAttribute("dicForm") @Valid DictionaryForm dicForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(dicForm);
		outputObject = getOutputObject(map, "dictionaryService", "updateDic");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("修改成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 逻辑删除字典信息
	 * @param dicForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteObj")
	public OutputObject deleteObj(@ModelAttribute("dicForm") DictionaryForm dicForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		dicForm.setDeleteFlag(-1);
		Map<String, Object> map = BeanUtil.convertBean2Map(dicForm);
		outputObject = getOutputObject(map, "dictionaryService", "updateDic");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("删除成功!");
		}
		return outputObject;
	}
	
	
	
	/**
	 * 根据数据字典组编码，查询子集数据字典信息(PS:目前只查询字典一级目录，如果要查询字典树数据，请自行扩展)
	 * @param dicForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryDicsByGCode")
	public OutputObject queryDicsByGCode(@ModelAttribute("dicGroupForm") DictionaryGroupForm dicGroupForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(dicGroupForm);
		outputObject = getOutputObject(map, "dictionaryService", "queryDicsByGCode");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("查询成功!");
		}
		return outputObject;
	}
	
	
}
