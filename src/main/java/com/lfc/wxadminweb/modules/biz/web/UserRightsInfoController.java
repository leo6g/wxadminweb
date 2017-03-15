package com.lfc.wxadminweb.modules.biz.web;


import java.util.Arrays;
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


import com.lfc.wxadminweb.modules.biz.form.UserRightsInfoForm;
import com.lfc.wxadminweb.modules.weixin.form.WXUserForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author gy
 * @date 2017-02-27 17:23
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/userRights")
public class UserRightsInfoController extends BaseController{
	
	
	
	/**
	 * 跳转到客户权益列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv,Model model) {
		mv.setViewName("weixin/userrightsinfo-list");
		return mv;
	}
	
	/**
	 * 分页查询客户权益列表
	 * @param UserRightsInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("userRightsInfoForm") UserRightsInfoForm userRightsInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(userRightsInfoForm);
		outputObject = getOutputObject(map, "userRightsInfoService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询客户权益
	 * @param UserRightsInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("UserRightsInfoForm") UserRightsInfoForm userRightsInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(userRightsInfoForm);	
		outputObject = getOutputObject(map,"userRightsInfoService","getById");
		return outputObject;
	}
	/**
	 * 查看所有客户权益
	 * @param "UserRightsInfoForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("UserRightsInfoForm") UserRightsInfoForm userRightsInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(userRightsInfoForm);	
		outputObject = getOutputObject(map,"userRightsInfoService","getAll");
		return outputObject;
	}
	/**
	 * 添加客户权益
	 * @param UserRightsInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertUserRightsInfo")
	public OutputObject insertUserRightsInfo(@ModelAttribute("UserRightsInfoForm") @Valid UserRightsInfoForm userRightsInfoForm,BindingResult result, Model model,ModelMap mm,HttpServletRequest request) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(userRightsInfoForm);
			Integer daiJia = Integer.valueOf(request.getParameter("daiJia"));
			Integer guiBin = Integer.valueOf(request.getParameter("guiBin"));
			Integer diDi = Integer.valueOf(request.getParameter("diDi"));
			Integer jiChang = Integer.valueOf(request.getParameter("jiChang"));
			
			map.put("daiJia", daiJia);
			map.put("guiBin", guiBin);
			map.put("diDi", diDi);
			map.put("jiChang", jiChang);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			
			outputObject = getOutputObject(map, "userRightsInfoService", "batchInsertUserRightsInfo");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("客户权益添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑客户权益
	 * @param UserRightsInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateUserRightsInfo")
	public OutputObject updateUserRightsInfo(@ModelAttribute("UserRightsInfoForm") @Valid UserRightsInfoForm userRightsInfoForm,BindingResult result, Model model,ModelMap mm,HttpServletRequest request) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(userRightsInfoForm);
		Integer daiJia = Integer.valueOf(request.getParameter("daiJia"));
		Integer guiBin = Integer.valueOf(request.getParameter("guiBin"));
		Integer diDi = Integer.valueOf(request.getParameter("diDi"));
		Integer jiChang = Integer.valueOf(request.getParameter("jiChang"));
		map.put("daiJia", daiJia);
		map.put("guiBin", guiBin);
		map.put("diDi", diDi);
		map.put("jiChang", jiChang);
		
		outputObject = getOutputObject(map, "userRightsInfoService", "batchupdateUserRightsInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("客户权益编辑成功!");
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
		mav.setViewName("weixin/add-errightsinfo");
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
		outputObject = getOutputObject(map,"userRightsInfoService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-errightsinfo");
		return mav;
	}
	/**
	 * 删除客户权益
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteUserRightsInfo")
	public OutputObject deleteUserRightsInfo(@ModelAttribute("UserRightsInfoForm") UserRightsInfoForm userRightsInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(userRightsInfoForm);
		outputObject = getOutputObject(map, "userRightsInfoService", "deleteUserRightsInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("客户权益删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除客户权益
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteUserRightsInfo")
	public OutputObject logicDeleteUserRightsInfo(@ModelAttribute("UserRightsInfoForm") UserRightsInfoForm userRightsInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(userRightsInfoForm);
		outputObject = getOutputObject(map, "userRightsInfoService", "logicDeleteUserRightsInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
