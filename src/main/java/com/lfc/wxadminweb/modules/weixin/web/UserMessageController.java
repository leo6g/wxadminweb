package com.lfc.wxadminweb.modules.weixin.web;


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
import com.lfc.wxadminweb.modules.weixin.form.UserMessageForm;



/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2016-12-13 09:50
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/usermessage")
public class UserMessageController extends BaseController{
	
	
	
	/**
	 * 跳转到微信会员用户留言表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/ermessage-list");
		return mv;
	}
	/**
	 * 分页查询微信会员用户留言表列表
	 * @param UserMessageForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("userMessageForm") UserMessageForm userMessageForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(userMessageForm);
		outputObject = getOutputObject(map, "userMessageService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询微信会员用户留言表
	 * @param UserMessageForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("UserMessageForm") UserMessageForm userMessageForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(userMessageForm);	
		outputObject = getOutputObject(map,"userMessageService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信会员用户留言表
	 * @param "UserMessageForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("UserMessageForm") UserMessageForm userMessageForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(userMessageForm);	
		outputObject = getOutputObject(map,"userMessageService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信会员用户留言表
	 * @param UserMessageForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertUserMessage")
	public OutputObject insertUserMessage(@ModelAttribute("UserMessageForm") @Valid UserMessageForm userMessageForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(userMessageForm);
			outputObject = getOutputObject(map, "userMessageService", "insertUserMessage");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信会员用户留言表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信会员用户留言表
	 * @param UserMessageForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateUserMessage")
	public OutputObject updateUserMessage(@ModelAttribute("UserMessageForm") @Valid UserMessageForm userMessageForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(userMessageForm);
		outputObject = getOutputObject(map, "userMessageService", "updateUserMessage");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信会员用户留言表编辑成功!");
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
		mav.setViewName("weixin/add-ermessage");
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
		outputObject = getOutputObject(map,"userMessageService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-ermessage");
		return mav;
	}
	/**
	 * 删除微信会员用户留言表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteUserMessage")
	public OutputObject deleteUserMessage(@ModelAttribute("UserMessageForm") UserMessageForm userMessageForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(userMessageForm);
		outputObject = getOutputObject(map, "userMessageService", "deleteUserMessage");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信会员用户留言表删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信会员用户留言表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteUserMessage")
	public OutputObject logicDeleteUserMessage(@ModelAttribute("UserMessageForm") UserMessageForm userMessageForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(userMessageForm);
		outputObject = getOutputObject(map, "userMessageService", "logicDeleteUserMessage");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
