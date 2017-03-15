package com.lfc.wxadminweb.modules.weixin.web;


import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.GetUserInfoResponse;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.ImageMsg;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.VideoMsg;
import com.github.sd4324530.fastweixin.message.VoiceMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.util.PropertiesUtil;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.util.DateUtils;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.weixin.form.WXNewsItemsForm;
import com.lfc.wxadminweb.modules.weixin.form.WXNewsTemplatesForm;
import com.lfc.wxadminweb.modules.weixin.form.WXSubcribeMsgForm;
import com.lfc.wxadminweb.modules.weixin.form.WXSubcribeRespForm;
import com.lfc.wxadminweb.modules.weixin.form.WXUserForm;

/**
 * <h2></br>
 * 公众号 被添加自动回复语、消息自动回复语 管理类
 * @descript 
 * @author Yaohaohao
 * @date 2016-10-12 15:11
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/Resp")
public class WXSubscribeRespController extends BaseController{
	

	/**
	 * 跳转到微信关注语设置页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "subRespSet")
	public ModelAndView subRespSet() {
		ModelAndView mv=new ModelAndView();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("type", 0);
		OutputObject outputObject = getOutputObject(map, "wXSubscribeRespService", "getReply");
		Object o = JSONObject.toJSON(outputObject.getObject());
		mv.addObject("respInfo", o != null ? o : "null");
		mv.setViewName("weixin/autoRespSet/attention-settings");
		return mv;
	}
	/**
	 * 跳转到消息自动回复设置页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "newsRespSet")
	public ModelAndView newsRespSet() {
		ModelAndView mv=new ModelAndView();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("type", 1);
		OutputObject outputObject = getOutputObject(map, "wXSubscribeRespService", "getReply");
		Object o = JSONObject.toJSON(outputObject.getObject());
		mv.addObject("respInfo", o != null ? o : "null");
		mv.setViewName("weixin/autoRespSet/msg-response");
		return mv;
	}
	/**
     * 保存/更新被添加自动回复信息
     * @param WXSubcribeRespForm
     * @param result
     * @param model
     * @param mm
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "saveSubscribeReply")
	public OutputObject saveSubscribeReply(@ModelAttribute("WXSubcribeRespForm") @Valid WXSubcribeRespForm wXSubcribeRespForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeRespForm);
			map.put("type", 0);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXSubscribeRespService", "saveReply");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("被添加回复内容保存成功!");
			}
			return outputObject;
	}
	/**
     * 保存/更新消息自动回复信息
     * @param WXSubcribeRespForm
     * @param result
     * @param model
     * @param mm
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "saveNewsReply")
	public OutputObject saveNewsReply(@ModelAttribute("WXSubcribeRespForm") @Valid WXSubcribeRespForm wXSubcribeRespForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeRespForm);
			map.put("type", 1);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXSubscribeRespService", "saveReply");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("消息回复内容保存成功!");
			}
			return outputObject;
	}
	/**
	 * 删除被关注回复语
	 * @param WXSubcribeRespForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteSubscribe")
	public OutputObject deleteSubscribe(Model model,ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 0);
		outputObject = getOutputObject(map, "wXSubscribeRespService", "deleteReply");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("被关注回复语删除成功!");
		}
		return outputObject;
	}
	/**
	 * 删除消息自动回复语
	 * @param WXSubcribeRespForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteNews")
	public OutputObject deleteNews(Model model,ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		outputObject = getOutputObject(map, "wXSubscribeRespService", "deleteReply");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("消息自动回复语删除成功!");
		}
		return outputObject;
	}
	
}
