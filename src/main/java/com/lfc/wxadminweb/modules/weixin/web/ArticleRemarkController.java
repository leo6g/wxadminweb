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
import com.lfc.wxadminweb.modules.weixin.form.ArticleRemarkForm;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;

/**
 * @descript 
 * @author jzp
 * @date 2016-11-30 17:51
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/articleremark")
public class ArticleRemarkController extends BaseController{
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到文章评论信息列表管理页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request,Model model) {
		ModelAndView mv=new ModelAndView();
		//角色绑定
		String roleIds = getSession().getAttribute(Constants.USER_SESSION.ROLEIDS).toString();
		if(roleIds !=null && !"".equals(roleIds)){
			//评论审核员
			if(roleIds.contains("63c5c4f4f091430f8e1d031221d884af")){
				model.addAttribute("role", "remarkCheck");
			}
		}
		mv.setViewName("weixin/articleremark-list");
		return mv;
	}
	
	/**
	 * 跳转到文章评论页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "remark")
	public ModelAndView remark( HttpServletRequest request,Model model,ModelAndView mv) {
		model.addAttribute("title", request.getParameter("title"));
		model.addAttribute("openId", request.getParameter("openId"));
		model.addAttribute("itemId", request.getParameter("itemId"));
		System.out.println("------title--------"+request.getParameter("title"));
		mv.setViewName("weixin/remark-article");
		return mv;
	}
	
	/**
	 * 分页查询文章评论信息列表
	 * @param WXArticleRemarkForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("articleRemarkForm") ArticleRemarkForm articleRemarkForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(articleRemarkForm);
		outputObject = getOutputObject(map, "articleRemarkService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询文章评论信息
	 * @param WXArticleRemarkForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("articleRemarkForm") ArticleRemarkForm articleRemarkForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(articleRemarkForm);	
		outputObject = getOutputObject(map,"articleRemarkService","getById");
		return outputObject;
	}
	/**
	 * 查看所有文章评论信息
	 * @param "WXArticleRemarkForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("articleRemarkForm") ArticleRemarkForm articleRemarkForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(articleRemarkForm);	
		outputObject = getOutputObject(map,"articleRemarkService","getAll");
		return outputObject;
	}
	/**
	 * 添加文章评论信息
	 * @param WXArticleRemarkForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXArticleRemark")
	public OutputObject insertWXArticleRemark(@ModelAttribute("articleRemarkForm") @Valid ArticleRemarkForm articleRemarkForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			articleRemarkForm.setAuthFlag("P");
			System.out.println("---------Remark----------"+articleRemarkForm.getRemark());
			Map<String, Object> map = BeanUtil.convertBean2Map(articleRemarkForm);
			outputObject = getOutputObject(map, "articleRemarkService", "insertWXArticleRemark");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("文章评论信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑文章评论信息
	 * @param WXArticleRemarkForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXArticleRemark")
	public OutputObject updateWXArticleRemark(@ModelAttribute("articleRemarkForm") @Valid ArticleRemarkForm articleRemarkForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> m = new HashMap<String, Object>();
		
		//查询评论积分
		m.put("dicCode", "remarkAmount");
		OutputObject outputAmount = getOutputObject(m, "dictionaryService", "getDicDetailByDicCode");
		Map<String, Object> mapAmount = (Map<String, Object>)outputAmount.getBean();
		String amount = (String)mapAmount.get("dicName");
		
		Map<String, Object> map = BeanUtil.convertBean2Map(articleRemarkForm);
		//将积分表数据放入参
		map.put("type", "remark");
		map.put("amount", amount);
		map.put("authUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
		
		outputObject = getOutputObject(map, "articleRemarkService", "updateWXArticleRemark");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("文章评论信息编辑成功!");
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
		mav.setViewName("weixin/add-articleremark");
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
		outputObject = getOutputObject(map,"articleRemarkService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-articleremark");
		return mav;
	}
	/**
	 * 删除文章评论信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXArticleRemark")
	public OutputObject deleteWXArticleRemark(@ModelAttribute("articleRemarkForm") ArticleRemarkForm articleRemarkForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(articleRemarkForm);
		outputObject = getOutputObject(map, "articleRemarkService", "deleteWXArticleRemark");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("文章评论信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除文章评论信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXArticleRemark")
	public OutputObject logicDeleteWXArticleRemark(@ModelAttribute("articleRemarkForm") ArticleRemarkForm articleRemarkForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(articleRemarkForm);
		outputObject = getOutputObject(map, "articleRemarkService", "logicDeleteWXArticleRemark");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
