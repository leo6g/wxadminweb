package com.lfc.wxadminweb.modules.biz.web;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.lfc.wxadminweb.common.exportexcel.ExportActSignData;
import com.lfc.wxadminweb.common.exportexcel.ExportSmsLogData;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.lfc.wxadminweb.modules.biz.form.ActSignForm;
import com.lfc.wxadminweb.modules.biz.form.SmsLogForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2017-02-20 09:02
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/smslog")
public class SmsLogController extends BaseController{
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到短信发送日志列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/smslog/smslog-list");
		return mv;
	}
	/**
	 * 分页查询短信发送日志列表
	 * @param SmsLogForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("smsLogForm") SmsLogForm smsLogForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(smsLogForm);
		outputObject = getOutputObject(map, "smsLogService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询短信发送日志
	 * @param SmsLogForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("SmsLogForm") SmsLogForm smsLogForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(smsLogForm);	
		outputObject = getOutputObject(map,"smsLogService","getById");
		return outputObject;
	}
	/**
	 * 查看所有短信发送日志
	 * @param "SmsLogForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("SmsLogForm") SmsLogForm smsLogForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(smsLogForm);	
		outputObject = getOutputObject(map,"smsLogService","getAll");
		return outputObject;
	}
	/**
	 * 添加短信发送日志
	 * @param SmsLogForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertSmsLog")
	public OutputObject insertSmsLog(@ModelAttribute("SmsLogForm") @Valid SmsLogForm smsLogForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(smsLogForm);
			
			outputObject = getOutputObject(map, "smsLogService", "insertSmsLog");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("短信发送日志添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑短信发送日志
	 * @param SmsLogForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateSmsLog")
	public OutputObject updateSmsLog(@ModelAttribute("SmsLogForm") @Valid SmsLogForm smsLogForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(smsLogForm);
		outputObject = getOutputObject(map, "smsLogService", "updateSmsLog");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("短信发送日志编辑成功!");
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
		mav.setViewName("weixin/add-slog");
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
		outputObject = getOutputObject(map,"smsLogService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-slog");
		return mav;
	}
	/**
	 * 删除短信发送日志
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteSmsLog")
	public OutputObject deleteSmsLog(@ModelAttribute("SmsLogForm") SmsLogForm smsLogForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(smsLogForm);
		outputObject = getOutputObject(map, "smsLogService", "deleteSmsLog");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("短信发送日志删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除短信发送日志
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteSmsLog")
	public OutputObject logicDeleteSmsLog(@ModelAttribute("SmsLogForm") SmsLogForm smsLogForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(smsLogForm);
		outputObject = getOutputObject(map, "smsLogService", "logicDeleteSmsLog");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	//导出功能
		@RequestMapping(value = "exportData")
		public void exportData(SmsLogForm smsLogForm,HttpServletRequest request, HttpServletResponse response){
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(smsLogForm);
			outputObject = getOutputObject(map, "smsLogService", "getAll");//根据查询条件，导出所有，没有分页
			try {
				ExportSmsLogData.exportData(outputObject.getBeans(), request, response);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
				response.setHeader("err_msg", "下载excl文件出现异常");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			} 
		}
	
}
