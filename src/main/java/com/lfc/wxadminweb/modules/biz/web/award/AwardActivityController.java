package com.lfc.wxadminweb.modules.biz.web.award;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.InputObject;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.award.AwardActivityForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author zy
 * @date 2016-11-30 09:48
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/awardActivity")
public class AwardActivityController extends BaseController{
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到微信抽奖活动列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/award/ardactivity-list");
		return mv;
	}
	/**
	 * 分页查询微信抽奖活动列表
	 * @param AwardActivityForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("awardActivityForm") AwardActivityForm awardActivityForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(awardActivityForm);
		outputObject = getOutputObject(map, "awardActivityService", "getList");
		return outputObject;
	}
	
	
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("biz/award/add-activity");
		return mav;
	}
	
	
	/**
	 *根据ID查询微信抽奖活动
	 * @param AwardActivityForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("AwardActivityForm") AwardActivityForm awardActivityForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(awardActivityForm);	
		outputObject = getOutputObject(map,"awardActivityService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信抽奖活动
	 * @param "AwardActivityForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("AwardActivityForm") AwardActivityForm awardActivityForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(awardActivityForm);	
		outputObject = getOutputObject(map,"awardActivityService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信抽奖活动
	 * @param AwardActivityForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertAwardActivity")
	public OutputObject insertAwardActivity(@ModelAttribute("AwardActivityForm") @Valid AwardActivityForm awardActivityForm,BindingResult result,HttpServletRequest request, Model model,ModelMap mm,InputObject inputObject) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			
			String[] names = request.getParameterValues("name");//奖品名称
			String[] amounts = request.getParameterValues("amount");//奖品数量
			String[] ranks = request.getParameterValues("rank");//奖品等级
			List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
			for (int i = 0; i < names.length; i++) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("name", names[i].toString());
				map.put("amount", amounts[i].toString());
				map.put("rank", ranks[i].toString());
				list.add(map);
			}
			
			Map<String, Object> map = BeanUtil.convertBean2Map(awardActivityForm);
			map.put("list", list);
			outputObject = getOutputObject(map, "awardActivityService", "insertAwardActivity");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信抽奖活动添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信抽奖活动
	 * @param AwardActivityForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateAwardActivity")
	public OutputObject updateAwardActivity(@ModelAttribute("AwardActivityForm") @Valid AwardActivityForm awardActivityForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(awardActivityForm);
		outputObject = getOutputObject(map, "awardActivityService", "updateAwardActivity");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信抽奖活动编辑成功!");
		}
		return outputObject;
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
		outputObject = getOutputObject(map,"awardActivityService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-ardactivity");
		return mav;
	}
	/**
	 * 删除微信抽奖活动
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAwardActivity")
	public OutputObject deleteAwardActivity(@ModelAttribute("AwardActivityForm") AwardActivityForm awardActivityForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(awardActivityForm);
		outputObject = getOutputObject(map, "awardActivityService", "deleteAwardActivity");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信抽奖活动删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信抽奖活动
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteAwardActivity")
	public OutputObject logicDeleteAwardActivity(@ModelAttribute("AwardActivityForm") AwardActivityForm awardActivityForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(awardActivityForm);
		outputObject = getOutputObject(map, "awardActivityService", "logicDeleteAwardActivity");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 抽奖活动详细页面
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 * */
	
}
