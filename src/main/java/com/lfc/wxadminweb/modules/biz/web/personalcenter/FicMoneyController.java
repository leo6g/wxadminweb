package com.lfc.wxadminweb.modules.biz.web.personalcenter;


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
import com.lfc.wxadminweb.modules.biz.form.personalcenter.FicMoneyForm;





/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2016-11-22 18:21
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/ficmoney")
public class FicMoneyController extends BaseController{
	
	
	
	/**
	 * 跳转到个人中心列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/personalcenter/cmoney-list");
		return mv;
	}
	/**
	 * 分页查询个人中心列表
	 * @param FicMoneyForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("ficMoneyForm") FicMoneyForm ficMoneyForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(ficMoneyForm);
		outputObject = getOutputObject(map, "ficMoneyService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询个人中心
	 * @param FicMoneyForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("FicMoneyForm") FicMoneyForm ficMoneyForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(ficMoneyForm);	
		outputObject = getOutputObject(map,"ficMoneyService","getById");
		return outputObject;
	}
	/**
	 * 查看所有个人中心
	 * @param "FicMoneyForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("FicMoneyForm") FicMoneyForm ficMoneyForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(ficMoneyForm);	
		outputObject = getOutputObject(map,"ficMoneyService","getAll");
		return outputObject;
	}
	/**
	 * 添加个人中心
	 * @param FicMoneyForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertFicMoney")
	public OutputObject insertFicMoney(@ModelAttribute("FicMoneyForm") @Valid FicMoneyForm ficMoneyForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(ficMoneyForm);

			outputObject = getOutputObject(map, "ficMoneyService", "insertFicMoney");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("个人中心添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑个人中心
	 * @param FicMoneyForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateFicMoney")
	public OutputObject updateFicMoney(@ModelAttribute("FicMoneyForm") @Valid FicMoneyForm ficMoneyForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(ficMoneyForm);
		outputObject = getOutputObject(map, "ficMoneyService", "updateFicMoney");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("个人中心编辑成功!");
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
		mav.setViewName("weixin/add-cmoney");
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
		outputObject = getOutputObject(map,"ficMoneyService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-cmoney");
		return mav;
	}
	/**
	 * 删除个人中心
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteFicMoney")
	public OutputObject deleteFicMoney(@ModelAttribute("FicMoneyForm") FicMoneyForm ficMoneyForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(ficMoneyForm);
		outputObject = getOutputObject(map, "ficMoneyService", "deleteFicMoney");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("个人中心删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除个人中心
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteFicMoney")
	public OutputObject logicDeleteFicMoney(@ModelAttribute("FicMoneyForm") FicMoneyForm ficMoneyForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(ficMoneyForm);
		outputObject = getOutputObject(map, "ficMoneyService", "logicDeleteFicMoney");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
