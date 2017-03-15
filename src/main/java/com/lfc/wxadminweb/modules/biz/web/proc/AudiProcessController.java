package com.lfc.wxadminweb.modules.biz.web.proc;


import java.util.HashMap;
import java.util.List;
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
import com.lfc.wxadminweb.modules.biz.form.proc.AudiProcessForm;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;



/**
 * <h2></br>
 * 
 * @descript 
 * @author gy
 * @date 2017-03-03 17:22
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/proc")
public class AudiProcessController extends BaseController{
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到图文审批流程信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/proc/audiprocess-list");
		return mv;
	}
	/**
	 * 分页查询图文审批流程信息列表
	 * @param AudiProcessForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("audiProcessForm") AudiProcessForm audiProcessForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(audiProcessForm);
		
		outputObject = getOutputObject(map, "audiProcessService", "getList");
		List<Map<String, Object>> outList = outputObject.getBeans();
		for (Map<String, Object> map2 : outList) {
			map2.put("publishDate", map2.get("publishDate").toString().substring(0,10));
			map2.put("createTime", map2.get("createTime").toString().substring(0,10));
		}
		return outputObject;
	}
	/**
	 *根据ID查询图文审批流程信息
	 * @param AudiProcessForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("AudiProcessForm") AudiProcessForm audiProcessForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(audiProcessForm);	
		outputObject = getOutputObject(map,"audiProcessService","getById");
		return outputObject;
	}
	
	@ResponseBody
	@RequestMapping(value = "getViewById")
	public OutputObject getViewById(@ModelAttribute("AudiProcessForm") AudiProcessForm audiProcessForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(audiProcessForm);	
		outputObject = getOutputObject(map,"audiProcessService","getViewById");
		return outputObject;
	}
	/**
	 * 查看所有图文审批流程信息
	 * @param "AudiProcessForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("AudiProcessForm") AudiProcessForm audiProcessForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(audiProcessForm);	
		outputObject = getOutputObject(map,"audiProcessService","getAll");
		return outputObject;
	}
	/**
	 * 添加图文审批流程信息
	 * @param AudiProcessForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertAudiProcess")
	public OutputObject insertAudiProcess(@ModelAttribute("AudiProcessForm") @Valid AudiProcessForm audiProcessForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(audiProcessForm);
			HashMap<String, Object> user = (HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER);
			String userId = user.get("id").toString();
			map.put("initiatorId", userId);
			map.put("currentNode", "city_office_f");
			map.put("state", "running");
			map.put("departCode", "0");
			outputObject = getOutputObject(map, "audiProcessService", "insertAudiProcess");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("图文审批流程信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑图文审批流程信息
	 * @param AudiProcessForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateAudiProcess")
	public OutputObject updateAudiProcess(@ModelAttribute("AudiProcessForm") @Valid AudiProcessForm audiProcessForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(audiProcessForm);
		outputObject = getOutputObject(map, "audiProcessService", "updateAudiProcess");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("图文审批流程信息编辑成功!");
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
		mav.setViewName("weixin/add-diprocess");
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
		outputObject = getOutputObject(map,"audiProcessService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-diprocess");
		return mav;
	}
	/**
	 * 删除图文审批流程信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAudiProcess")
	public OutputObject deleteAudiProcess(@ModelAttribute("AudiProcessForm") AudiProcessForm audiProcessForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(audiProcessForm);
		outputObject = getOutputObject(map, "audiProcessService", "deleteAudiProcess");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("图文审批流程信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除图文审批流程信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteAudiProcess")
	public OutputObject logicDeleteAudiProcess(@ModelAttribute("AudiProcessForm") AudiProcessForm audiProcessForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(audiProcessForm);
		outputObject = getOutputObject(map, "audiProcessService", "logicDeleteAudiProcess");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
