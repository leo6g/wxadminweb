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


import com.lfc.wxadminweb.modules.biz.form.BIZAuthInfoForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author jzp
 * @date 2016-10-31 11:59
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/authinfo")
public class BIZAuthInfoController extends BaseController{
	
	
	
	/**
	 * 跳转到审核意见信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/authinfo-list");
		return mv;
	}
	/**
	 * 分页查询审核意见信息列表
	 * @param BIZAuthInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("bIZAuthInfoForm") BIZAuthInfoForm bIZAuthInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZAuthInfoForm);
		outputObject = getOutputObject(map, "bIZAuthInfoService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询审核意见信息
	 * @param BIZAuthInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("BIZAuthInfoForm") BIZAuthInfoForm bIZAuthInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZAuthInfoForm);	
		outputObject = getOutputObject(map,"bIZAuthInfoService","getById");
		return outputObject;
	}
	/**
	 * 查看所有审核意见信息
	 * @param "BIZAuthInfoForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("BIZAuthInfoForm") BIZAuthInfoForm bIZAuthInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZAuthInfoForm);	
		outputObject = getOutputObject(map,"bIZAuthInfoService","getAll");
		return outputObject;
	}
	/**
	 * 添加审核意见信息
	 * @param BIZAuthInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertBIZAuthInfo")
	public OutputObject insertBIZAuthInfo(@ModelAttribute("BIZAuthInfoForm") @Valid BIZAuthInfoForm bIZAuthInfoForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			System.out.println("-------------"+bIZAuthInfoForm.getComments());
			Map<String, Object> map = BeanUtil.convertBean2Map(bIZAuthInfoForm);
			System.out.println("-------------"+((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("id"));
			map.put("examiner", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("id"));
			outputObject = getOutputObject(map, "bIZAuthInfoService", "insertBIZAuthInfo");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("审核意见信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑审核意见信息
	 * @param BIZAuthInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateBIZAuthInfo")
	public OutputObject updateBIZAuthInfo(@ModelAttribute("BIZAuthInfoForm") @Valid BIZAuthInfoForm bIZAuthInfoForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZAuthInfoForm);
		outputObject = getOutputObject(map, "bIZAuthInfoService", "updateBIZAuthInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("审核意见信息编辑成功!");
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
		mav.setViewName("weixin/add-authinfo");
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
		outputObject = getOutputObject(map,"bIZAuthInfoService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-authinfo");
		return mav;
	}
	/**
	 * 删除审核意见信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBIZAuthInfo")
	public OutputObject deleteBIZAuthInfo(@ModelAttribute("BIZAuthInfoForm") BIZAuthInfoForm bIZAuthInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZAuthInfoForm);
		outputObject = getOutputObject(map, "bIZAuthInfoService", "deleteBIZAuthInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("审核意见信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除审核意见信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteBIZAuthInfo")
	public OutputObject logicDeleteBIZAuthInfo(@ModelAttribute("BIZAuthInfoForm") BIZAuthInfoForm bIZAuthInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bIZAuthInfoForm);
		outputObject = getOutputObject(map, "bIZAuthInfoService", "logicDeleteBIZAuthInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
