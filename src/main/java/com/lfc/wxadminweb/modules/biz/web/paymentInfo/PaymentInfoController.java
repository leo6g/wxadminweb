package com.lfc.wxadminweb.modules.biz.web.paymentInfo;


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
import com.lfc.wxadminweb.modules.biz.form.paymentInfo.PaymentInfoForm;




/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2016-12-05 17:01
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/paymentInfo")
public class PaymentInfoController extends BaseController{
	
	
	
	/**
	 * 跳转到缴费数据信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/paymentInfo/ymentinfo-list");
		return mv;
	}
	/**
	 * 分页查询缴费数据信息列表
	 * @param PaymentInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("paymentInfoForm") PaymentInfoForm paymentInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(paymentInfoForm);
		outputObject = getOutputObject(map, "paymentInfoService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询缴费数据信息
	 * @param PaymentInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("PaymentInfoForm") PaymentInfoForm paymentInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(paymentInfoForm);	
		outputObject = getOutputObject(map,"paymentInfoService","getById");
		return outputObject;
	}
	/**
	 * 查看所有缴费数据信息
	 * @param "PaymentInfoForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("PaymentInfoForm") PaymentInfoForm paymentInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(paymentInfoForm);	
		outputObject = getOutputObject(map,"paymentInfoService","getAll");
		return outputObject;
	}
	/**
	 * 添加缴费数据信息
	 * @param PaymentInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertPaymentInfo")
	public OutputObject insertPaymentInfo(@ModelAttribute("PaymentInfoForm") @Valid PaymentInfoForm paymentInfoForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(paymentInfoForm);
			outputObject = getOutputObject(map, "paymentInfoService", "insertPaymentInfo");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("缴费数据信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑缴费数据信息
	 * @param PaymentInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updatePaymentInfo")
	public OutputObject updatePaymentInfo(@ModelAttribute("PaymentInfoForm") @Valid PaymentInfoForm paymentInfoForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(paymentInfoForm);
		outputObject = getOutputObject(map, "paymentInfoService", "updatePaymentInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("缴费数据信息编辑成功!");
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
		mav.setViewName("weixin/add-ymentinfo");
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
		outputObject = getOutputObject(map,"paymentInfoService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-ymentinfo");
		return mav;
	}
	/**
	 * 删除缴费数据信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deletePaymentInfo")
	public OutputObject deletePaymentInfo(@ModelAttribute("PaymentInfoForm") PaymentInfoForm paymentInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(paymentInfoForm);
		outputObject = getOutputObject(map, "paymentInfoService", "deletePaymentInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("缴费数据信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除缴费数据信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeletePaymentInfo")
	public OutputObject logicDeletePaymentInfo(@ModelAttribute("PaymentInfoForm") PaymentInfoForm paymentInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(paymentInfoForm);
		outputObject = getOutputObject(map, "paymentInfoService", "logicDeletePaymentInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
