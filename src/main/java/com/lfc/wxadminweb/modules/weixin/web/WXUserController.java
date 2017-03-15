package com.lfc.wxadminweb.modules.weixin.web;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXUserForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2016-10-17 10:16
 * @since JDK 1.7
 * 
 * ━━━━━━神兽出没━━━━━━
   * 　　　   ┏┓　　    ┏┓
   * 　　      ┏┛┻━━━━━━━┛┻┓
   * 　　      ┃　　　　   ┃
   * 　　      ┃　　━　　 ┃
   * 　　      ┃　┳┛　┗┳　┃
   * 　　      ┃　　　　   ┃
   * 　　      ┃　　┻　　 ┃
   * 　　      ┃　　　　   ┃
   * 　　      ┗━┓　　　┏━┛
   * 　　　　┃　　　┃  神兽保佑
   * 　　　　┃　　　┃  代码无bug　　
   * 　　　　┃　　　┗━━━┓
   * 　　　　┃　　　　   ┣┓
   * 　　　　┃　　　        ┏┛
   * 　　　　┗┓┓    ┏━┳┓   ┏┛
   * 　　　　　┃┫┫　┃┫┫
   * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
*/

@Controller
@RequestMapping("/wx/user")
public class WXUserController extends BaseController{
	
	//同步状态，1为可以同步，0为不可同步
	private static Integer syncState = 1;
	
	@Value("#{system.appId}") 
	private String appId;
	
	@Value("#{system.secret}") 
	private String secret;
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到微信关注用户表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.addObject("syncState", syncState);
		mv.setViewName("weixin/user-management");
		return mv;
	}
	
	
	/**
	 * 跳转同步微信关注用户表列表页面 zhaoyan
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "syncList")
	public ModelAndView syncList( ModelAndView mv) {
		mv.setViewName("weixin/syncUser-list");
		return mv;
	}
	
	/**
	 * 分页查询微信关注用户表列表
	 * @param WXUserForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXUserForm") WXUserForm wXUserForm,
			BindingResult result,  Model model, ModelMap mm,String[] openIds) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXUserForm);
		map.put("appId", appId);
		map.put("secret", secret);
		if(openIds!=null){
			map.put("openIds", Arrays.asList(openIds));
		}
		outputObject = getOutputObject(map, "wXUserService", "getList");
		return outputObject;
	}
	
	
	/**
	 * 同步所有已关注的微信用户
	 * */
	@ResponseBody
	@RequestMapping(value = "syncWXUses")
	public OutputObject syncWXUses() {
		OutputObject outputObject = new OutputObject();
		if(syncState == 0) {
			outputObject.setReturnMessage("正在同步数据!");
			return outputObject;
		}
		syncState = 0;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("appId", appId);
		map.put("secret", secret);
		outputObject = getOutputObject(map, "wXUserService", "syncWXUsers");
		if(outputObject.getReturnCode().equals("0")){
//			System.out.println("0");
			outputObject.setReturnMessage("微信用户同步成功!");
		}else {
//			System.out.println("1");
			outputObject.setReturnMessage("微信用户同步异常!");
		}
		syncState = 1;
		return outputObject;
	}
	
	
	/**
	 *根据ID查询微信关注用户表
	 * @param WXUserForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXUserForm") WXUserForm wXUserForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXUserForm);	
		outputObject = getOutputObject(map,"wXUserService","getById");
		return outputObject;
	}
	
	/**
	 *根据ID查询微信关注用户表
	 * @param WXUserForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getByOpenId")
	public Object getByOpenId(HttpServletRequest request) {
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String,Object>();
		String openId = request.getParameter("openId");
		map.put("openId", openId);
		outputObject = getOutputObject(map,"wXUserService","getByOpenId");
		return outputObject.getObject();
	}
	/**
	 * 查看所有微信关注用户表
	 * @param "WXUserForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXUserForm") WXUserForm wXUserForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXUserForm);	
		outputObject = getOutputObject(map,"wXUserService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信关注用户表
	 * @param WXUserForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXUser")
	public OutputObject insertWXUser(@ModelAttribute("WXUserForm") @Valid WXUserForm wXUserForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXUserForm);
			outputObject = getOutputObject(map, "wXUserService", "insertWXUser");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信关注用户表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信关注用户表
	 * @param WXUserForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXUser")
	public OutputObject updateWXUser(@ModelAttribute("WXUserForm") @Valid WXUserForm wXUserForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXUserForm);
		outputObject = getOutputObject(map, "wXUserService", "updateWXUser");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信关注用户表编辑成功!");
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
		mav.setViewName("weixin/add-user");
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
		outputObject = getOutputObject(map,"wXUserService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-user");
		return mav;
	}
	/**
	 * 删除微信关注用户表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXUser")
	public OutputObject deleteWXUser(@ModelAttribute("WXUserForm") WXUserForm wXUserForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXUserForm);
		outputObject = getOutputObject(map, "wXUserService", "deleteWXUser");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信关注用户表删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信关注用户表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXUser")
	public OutputObject logicDeleteWXUser(@ModelAttribute("WXUserForm") WXUserForm wXUserForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXUserForm);
		outputObject = getOutputObject(map, "wXUserService", "logicDeleteWXUser");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 批量给用户增加标签
	 * @param openid openid集合
	 * @param tagId 标签id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "tagToUser")
	public OutputObject tagToUser(String openid,String selectTag,String unselectTag) {
		OutputObject outputObject = null;
		List<String> openidList = Arrays.asList(openid.split(","));
		List<String> selectTagList = StringUtils.isEmpty(selectTag) ? null : Arrays.asList(selectTag.split(","));
		List<String> unselectTagList = StringUtils.isEmpty(unselectTag) ? null : Arrays.asList(unselectTag.split(","));
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("openidList", openidList);
		params.put("selectTagList", selectTagList);
		params.put("unSelectTagList", unselectTagList);
		outputObject = getOutputObject(params, "wXUserService", "tagToUser");//变更微信用户的标签id
		return outputObject;
	}
	
	/**
	 * 批量给用户取消标签
	 * @param openid openid集合
	 * @param tagId 标签id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTagToUser")
	public OutputObject deleteTagToUser(List<String> openid, String tagId) {
		OutputObject outputObject = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("openidList", openid);
		params.put("tagId", tagId);
		outputObject = getOutputObject(params, "wXUserService", "deleteTagToUser");//变更微信用户的标签id
		return outputObject;
	}
	
	/**
	 * 获取黑名单列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getBlackList")
	public OutputObject getBlackList() {
		OutputObject outputObject = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("appId", appId);
		params.put("secret", secret);
		outputObject = getOutputObject(params, "wXUserService", "getBlackList");
		return outputObject;
	}
	
	/**
	 * 批量添加黑名单列表
	 * @param openid openid集合
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertBlackList")
	public OutputObject insertBlackList(String openid) {
		OutputObject outputObject = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("openidList", JSONArray.parseArray(openid));
		outputObject = getOutputObject(params, "wXUserService", "insertBlackList");//批量添加黑名单
		return outputObject;
	}
	
	/**
	 * 批量删除黑名单列表
	 * @param openid openid集合
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBlackList")
	public OutputObject deleteBlackList(String openid) {
		OutputObject outputObject = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("openidList", JSONArray.parseArray(openid));
		outputObject = getOutputObject(params, "wXUserService", "deleteBlackList");//批量删除黑名单
		return outputObject;
	}
}
