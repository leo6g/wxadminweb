package com.lfc.wxadminweb.modules.biz.web.recommend.web;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.recommend.form.StaffRecomendForm;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;




/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2016-12-01 09:01
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/recommend")
public class StaffRecomendController extends BaseController{
	@Value("#{system.picture_path}") 
	private String path;
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到员工推荐列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/recommend/affrecomend-list");
		return mv;
	}
	/**
	 * 分页查询员工推荐列表
	 * @param StaffRecomendForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("staffRecomendForm") StaffRecomendForm staffRecomendForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> user = (HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER);
		OutputObject outputObject = null;
		staffRecomendForm.setStaffCode((String)user.get("userName"));
		Map<String, Object> map = BeanUtil.convertBean2Map(staffRecomendForm);
		outputObject = getOutputObject(map, "staffRecomendService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询员工推荐
	 * @param StaffRecomendForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("StaffRecomendForm") StaffRecomendForm staffRecomendForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(staffRecomendForm);	
		outputObject = getOutputObject(map,"staffRecomendService","getById");
		return outputObject;
	}
	/**
	 * 查看所有员工推荐
	 * @param "StaffRecomendForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("StaffRecomendForm") StaffRecomendForm staffRecomendForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(staffRecomendForm);	
		outputObject = getOutputObject(map,"staffRecomendService","getAll");
		return outputObject;
	}
	/**
	 * 添加员工推荐
	 * @param StaffRecomendForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertStaffRecomend")
	public OutputObject insertStaffRecomend(@ModelAttribute("StaffRecomendForm") @Valid StaffRecomendForm staffRecomendForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(staffRecomendForm);
			
			outputObject = getOutputObject(map, "staffRecomendService", "insertStaffRecomend");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("员工推荐添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑员工推荐
	 * @param StaffRecomendForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateStaffRecomend")
	public OutputObject updateStaffRecomend(@ModelAttribute("StaffRecomendForm") @Valid StaffRecomendForm staffRecomendForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(staffRecomendForm);
		outputObject = getOutputObject(map, "staffRecomendService", "updateStaffRecomend");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("员工推荐编辑成功!");
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
		mav.setViewName("biz/recommend/add-affrecomend");
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
		outputObject = getOutputObject(map,"staffRecomendService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/recommend/edit-affrecomend");
		return mav;
	}
	/**
	 * 删除员工推荐
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteStaffRecomend")
	public OutputObject deleteStaffRecomend(@ModelAttribute("StaffRecomendForm") StaffRecomendForm staffRecomendForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(staffRecomendForm);
		outputObject = getOutputObject(map, "staffRecomendService", "deleteStaffRecomend");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("员工推荐删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除员工推荐
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteStaffRecomend")
	public OutputObject logicDeleteStaffRecomend(@ModelAttribute("StaffRecomendForm") StaffRecomendForm staffRecomendForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(staffRecomendForm);
		outputObject = getOutputObject(map, "staffRecomendService", "logicDeleteStaffRecomend");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
