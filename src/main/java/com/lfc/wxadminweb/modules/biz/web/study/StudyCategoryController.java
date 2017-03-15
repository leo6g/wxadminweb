package com.lfc.wxadminweb.modules.biz.web.study;


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
import com.lfc.wxadminweb.modules.biz.form.study.StudyCategoryForm;




/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2016-12-10 18:20
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/studycategoty")
public class StudyCategoryController extends BaseController{
	
	
	
	/**
	 * 跳转到邮学堂板块表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/study/udycategory-list");
		return mv;
	}
	/**
	 * 分页查询邮学堂板块表列表
	 * @param StudyCategoryForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("studyCategoryForm") StudyCategoryForm studyCategoryForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(studyCategoryForm);
		outputObject = getOutputObject(map, "studyCategoryService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询邮学堂板块表
	 * @param StudyCategoryForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("StudyCategoryForm") StudyCategoryForm studyCategoryForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(studyCategoryForm);	
		outputObject = getOutputObject(map,"studyCategoryService","getById");
		return outputObject;
	}
	/**
	 * 查看所有邮学堂板块表
	 * @param "StudyCategoryForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("StudyCategoryForm") StudyCategoryForm studyCategoryForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(studyCategoryForm);	
		outputObject = getOutputObject(map,"studyCategoryService","getAll");
		return outputObject;
	}
	/**
	 * 添加邮学堂板块表
	 * @param StudyCategoryForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertStudyCategory")
	public OutputObject insertStudyCategory(@ModelAttribute("StudyCategoryForm") @Valid StudyCategoryForm studyCategoryForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(studyCategoryForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "studyCategoryService", "insertStudyCategory");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("邮学堂板块表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑邮学堂板块表
	 * @param StudyCategoryForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateStudyCategory")
	public OutputObject updateStudyCategory(@ModelAttribute("StudyCategoryForm") @Valid StudyCategoryForm studyCategoryForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(studyCategoryForm);
		outputObject = getOutputObject(map, "studyCategoryService", "updateStudyCategory");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("邮学堂板块表编辑成功!");
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
		mav.setViewName("weixin/add-udycategory");
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
		outputObject = getOutputObject(map,"studyCategoryService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-udycategory");
		return mav;
	}
	/**
	 * 删除邮学堂板块表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteStudyCategory")
	public OutputObject deleteStudyCategory(@ModelAttribute("StudyCategoryForm") StudyCategoryForm studyCategoryForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(studyCategoryForm);
		outputObject = getOutputObject(map, "studyCategoryService", "deleteStudyCategory");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("邮学堂板块表删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除邮学堂板块表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteStudyCategory")
	public OutputObject logicDeleteStudyCategory(@ModelAttribute("StudyCategoryForm") StudyCategoryForm studyCategoryForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(studyCategoryForm);
		outputObject = getOutputObject(map, "studyCategoryService", "logicDeleteStudyCategory");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
