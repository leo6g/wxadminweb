package com.lfc.wxadminweb.modules.weixin.web;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyleConstants.ColorConstants;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.web.WeixinBaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXAutoResponseForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2016-10-12 11:56
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/autoresponse")
public class WXAutoResponseController extends WeixinBaseController{
	
	
	
	/**
	 * 微信接入入口  GET调用用于微信介入校验  POST用于接收微信消息
	 * @param response
	 */
	@RequestMapping(value = "init")
	@ResponseBody
	public String init(HttpServletResponse response){
		logger.info("微信接入入口!");
		HttpServletRequest request=getRequest();
		String method = getRequest().getMethod();
		if(method.equals("GET")){
			logger.info("GET请求，微信服务器握手!");
			String result = bind(request);
			logger.info("绑定微信服务器结果:"+result);
			return result;
		}else{
			try {
				logger.info("POST请求，微信消息交互处理!");
				process(request, response);
			} catch (ServletException e) {
				logger.info("消息请求异常："+ e);
				e.printStackTrace();
			} catch (IOException e) {
				logger.info("消息请求 IO异常"+ e);
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	/**
	 * 处理微信端接收的文本消息
	 */
	@Override
	protected BaseMsg handleTextMsg(TextReqMsg msg) {
		String reqContent = msg.getContent();
		String keyWord = "你好";
		String content = "你好，欢迎关注陆鹰微信平台测试公众号!";
		if(keyWord.equals(reqContent)) {
			return new TextMsg(content);
		}
		return null;
	}
	
	
	
	/**
	 * 跳转到微信自动回复信息模版列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/autoresponse-list");
		return mv;
	}
	/**
	 * 分页查询微信自动回复信息模版列表
	 * @param WXAutoResponseForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXAutoResponseForm") WXAutoResponseForm wXAutoResponseForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXAutoResponseForm);
		outputObject = getOutputObject(map, "wXAutoResponseService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询微信自动回复信息模版
	 * @param WXAutoResponseForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXAutoResponseForm") WXAutoResponseForm wXAutoResponseForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXAutoResponseForm);	
		outputObject = getOutputObject(map,"wXAutoResponseService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信自动回复信息模版
	 * @param "WXAutoResponseForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXAutoResponseForm") WXAutoResponseForm wXAutoResponseForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXAutoResponseForm);	
		outputObject = getOutputObject(map,"wXAutoResponseService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信自动回复信息模版
	 * @param WXAutoResponseForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXAutoResponse")
	public OutputObject insertWXAutoResponse(@ModelAttribute("WXAutoResponseForm") @Valid WXAutoResponseForm wXAutoResponseForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXAutoResponseForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXAutoResponseService", "insertWXAutoResponse");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信自动回复信息模版添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信自动回复信息模版
	 * @param WXAutoResponseForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXAutoResponse")
	public OutputObject updateWXAutoResponse(@ModelAttribute("WXAutoResponseForm") @Valid WXAutoResponseForm wXAutoResponseForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXAutoResponseForm);
		outputObject = getOutputObject(map, "wXAutoResponseService", "updateWXAutoResponse");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信自动回复信息模版编辑成功!");
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
		mav.setViewName("weixin/add-autoresponse");
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
		outputObject = getOutputObject(map,"wXAutoResponseService","selectByPrimaryKey");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-autoresponse");
		return mav;
	}
	/**
	 * 删除微信自动回复信息模版
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXAutoResponse")
	public OutputObject deleteWXAutoResponse(@ModelAttribute("WXAutoResponseForm") WXAutoResponseForm wXAutoResponseForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXAutoResponseForm);
		outputObject = getOutputObject(map, "wXAutoResponseService", "deleteWXAutoResponse");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信自动回复信息模版删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信自动回复信息模版
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXAutoResponse")
	public OutputObject logicDeleteWXAutoResponse(@ModelAttribute("WXAutoResponseForm") WXAutoResponseForm wXAutoResponseForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXAutoResponseForm);
		outputObject = getOutputObject(map, "wXAutoResponseService", "logicDeleteWXAutoResponse");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
