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


import com.lfc.wxadminweb.modules.biz.form.TmpActivityForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2017-03-10 10:15
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/tmpactivity")
public class TmpActivityController extends BaseController{
	
	
	
	/**
	 * 跳转到预约中奖信息设置列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/tmpactivity/pactivity-list");
		return mv;
	}
	/**
	 * 分页查询预约中奖信息设置列表
	 * @param TmpActivityForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("tmpActivityForm") TmpActivityForm tmpActivityForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(tmpActivityForm);
		map.put("type", "metal");
		outputObject = getOutputObject(map, "tmpActivityService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询预约中奖信息设置
	 * @param TmpActivityForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("TmpActivityForm") TmpActivityForm tmpActivityForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(tmpActivityForm);	
		outputObject = getOutputObject(map,"tmpActivityService","getById");
		return outputObject;
	}
	/**
	 * 查看所有预约中奖信息设置
	 * @param "TmpActivityForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("TmpActivityForm") TmpActivityForm tmpActivityForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(tmpActivityForm);	
		outputObject = getOutputObject(map,"tmpActivityService","getAll");
		return outputObject;
	}
	/**
	 * 添加预约中奖信息设置
	 * @param TmpActivityForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertTmpActivity")
	public OutputObject insertTmpActivity(@ModelAttribute("TmpActivityForm") @Valid TmpActivityForm tmpActivityForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(tmpActivityForm);
			
			outputObject = getOutputObject(map, "tmpActivityService", "insertTmpActivity");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("预约中奖信息设置添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑预约中奖信息设置
	 * @param TmpActivityForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateTmpActivity")
	public OutputObject updateTmpActivity(@ModelAttribute("TmpActivityForm") @Valid TmpActivityForm tmpActivityForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(tmpActivityForm);
		outputObject = getOutputObject(map, "tmpActivityService", "updateTmpActivity");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("预约中奖信息设置编辑成功!");
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
		mav.setViewName("biz/tmpactivity/add-pactivity");
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
		outputObject = getOutputObject(map,"tmpActivityService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/tmpactivity/edit-pactivity");
		return mav;
	}
	/**
	 * 删除预约中奖信息设置
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTmpActivity")
	public OutputObject deleteTmpActivity(@ModelAttribute("TmpActivityForm") TmpActivityForm tmpActivityForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(tmpActivityForm);
		outputObject = getOutputObject(map, "tmpActivityService", "deleteTmpActivity");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("预约中奖信息设置删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除预约中奖信息设置
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteTmpActivity")
	public OutputObject logicDeleteTmpActivity(@ModelAttribute("TmpActivityForm") TmpActivityForm tmpActivityForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(tmpActivityForm);
		outputObject = getOutputObject(map, "tmpActivityService", "logicDeleteTmpActivity");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
