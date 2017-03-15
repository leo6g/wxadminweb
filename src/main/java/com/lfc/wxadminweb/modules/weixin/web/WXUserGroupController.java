package com.lfc.wxadminweb.modules.weixin.web;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.sd4324530.fastweixin.api.TagAPI;
import com.github.sd4324530.fastweixin.api.UserAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.Tag;
import com.github.sd4324530.fastweixin.api.entity.UserInfo;
import com.github.sd4324530.fastweixin.api.response.GetAllTagsResponse;
import com.github.sd4324530.fastweixin.api.response.GetUsersResponse;
import com.github.sd4324530.fastweixin.api.response.GetUsersResponse.Openid;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.core.util.ControlConstants;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXUserForm;
import com.lfc.wxadminweb.modules.weixin.form.WXUserGroupForm;

/**
 * <h2></br>
 * 
 * @descript
 * @author lbb
 * @date 2016-10-17 10:54
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/usergroup")
public class WXUserGroupController extends BaseController {
	@Value("#{system.appId}")
	private String appId;

	@Value("#{system.secret}")
	private String secret;

	/**
	 * 跳转到微信用户组列表页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(ModelAndView mv) {
		mv.setViewName("weixin/usergroup-list");
		return mv;
	}

	/**
	 * 查询微信用户组列表
	 * 
	 * @param WXUserGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getList() {
		TagAPI tagAPI=new TagAPI(new ApiConfig(appId, secret));
		OutputObject outputObject=new OutputObject();
		GetAllTagsResponse allTags=tagAPI.getAllTags();
		List<Tag> list=allTags.getTags();
		if(list==null){
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage(allTags.getErrmsg());
		}else{
			outputObject.setObject(list);
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
		}
		return outputObject;
	}

	/**
	 * 根据组ID查询组下的用户
	 * 
	 * @param WXUserGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getListById")
	public OutputObject getById(Integer tagId) {
		ApiConfig apiConfig=new ApiConfig(appId, secret);
		TagAPI tagApi=new TagAPI(apiConfig);
		GetUsersResponse response=tagApi.getUsersByTagId(tagId, null);
		Integer count=response.getCount();
		Openid openid=response.getData();
		OutputObject outputObject=new OutputObject();
		if(count==0){
			outputObject.setReturnMessage("标签下没有粉丝");;
			outputObject.setReturnCode("-1");
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
		}else{
				List<String> openIds=Arrays.asList(openid.getOpenid());
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("openIds", openIds);
				outputObject.setBean(map);
		}
		return outputObject;
	}

	/**
	 * 设置用户备注名
	 * 
	 * @param "WXUserGroupForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "setUserRemark")
	public OutputObject setUserRemark(@ModelAttribute("WXUserForm") WXUserForm wXUserForm, BindingResult result, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXUserForm);
		outputObject = getOutputObject(map, "wXUserService", "setUserRemark");
		UserAPI userAPI=new UserAPI(new ApiConfig(appId, secret));
		userAPI.setUserRemark(wXUserForm.getOpenId(), wXUserForm.getRemark());
		return outputObject;
	}

	/**
	 * 添加微信用户组
	 * 
	 * @param WXUserGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXUserGroup")
	public OutputObject insertWXUserGroup(String tagName) {
		TagAPI tagApi=new TagAPI(new ApiConfig(appId, secret));
		OutputObject outputObject=new OutputObject();
		outputObject.setObject(tagApi.create(tagName));
		outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
		return outputObject;
	}

	/**
	 * 编辑微信用户组
	 * 
	 * @param WXUserGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXUserGroup")
	public OutputObject updateWXUserGroup(Integer tagId,String newTagName) {
		TagAPI tagApi=new TagAPI(new ApiConfig(appId, secret));
		OutputObject outputObject=new OutputObject();
		outputObject.setObject(tagApi.updateTag(tagId, newTagName));
		outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
		return outputObject;
	}

	/**
	 * 去往添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("weixin/add-usergroup");
		return mav;
	}

	/**
	 * 去往更新页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView edit(Model model) {
		ModelAndView mav = new ModelAndView();
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		map.put("id", request.getParameter("id"));
		outputObject = getOutputObject(map, "wXUserGroupService", "getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-usergroup");
		return mav;
	}

	/**
	 * 删除微信用户组
	 * 
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXUserGroup")
	public OutputObject deleteWXUserGroup(Integer tagId) { 
		TagAPI tagApi=new TagAPI(new ApiConfig(appId, secret));
		OutputObject outputObject=new OutputObject();
		outputObject.setObject(tagApi.deleteTag(tagId));
		outputObject.setReturnCode(ControlConstants.RETURN_CODE.IS_OK);
		return outputObject;
	}

	/**
	 * 逻辑删除微信用户组
	 * 
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXUserGroup")
	public OutputObject logicDeleteWXUserGroup(@ModelAttribute("WXUserGroupForm") WXUserGroupForm wXUserGroupForm, BindingResult result, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXUserGroupForm);
		outputObject = getOutputObject(map, "wXUserGroupService", "logicDeleteWXUserGroup");
		if (outputObject.getReturnCode().equals("0")) {
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}

}
