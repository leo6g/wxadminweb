package com.lfc.wxadminweb.modules.weiweb.web;


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


import com.lfc.wxadminweb.modules.weiweb.form.ArticleForm;

/**
 * 
 * @descript 
 * @author jzp
 * @date 2016-11-07 09:47
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/weiweb/article")
public class ArticleController extends BaseController{
	
	
	
	/**
	 * 跳转到微网站文章信息管理列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weiweb/article/article-list");
		return mv;
	}
	/**
	 * 分页查询微网站文章信息管理列表
	 * @param WWArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("ArticleForm") ArticleForm wWArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wWArticleForm);
		outputObject = getOutputObject(map, "wWArticleService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询微网站文章信息管理
	 * @param WWArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("ArticleForm") ArticleForm wWArticleForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wWArticleForm);	
		outputObject = getOutputObject(map,"wWArticleService","getById");
		Map<String, Object> mapObject = (Map<String, Object>)outputObject.getObject();
		String content  = (String)mapObject.get("content");
		if(!"".equals(content) && null != content){
			content = replaceHTMLEntity(content);
			mapObject.put("content", content);
		}
		outputObject.setBean(mapObject);
		return outputObject;
	}
	/**
	 * 查看所有微网站文章信息管理
	 * @param "WWArticleForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("ArticleForm") ArticleForm wWArticleForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wWArticleForm);	
		outputObject = getOutputObject(map,"wWArticleService","getAll");
		List<Map<String, Object>> listObject = (List<Map<String, Object>>)outputObject.getBeans();
		for (Map<String, Object> mapObject : listObject) {
			String content  = (String)mapObject.get("content");
			if(!"".equals(content) && null != content){
				content = replaceHTMLEntity(content);
				mapObject.put("content", content);
			}
			outputObject.setBean(mapObject);
		}
		return outputObject;
	}
	/**
	 * 添加微网站文章信息管理
	 * @param WWArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWWArticle")
	public OutputObject insertWWArticle(@ModelAttribute("ArticleForm") @Valid ArticleForm wWArticleForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wWArticleForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wWArticleService", "insertWWArticle");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微网站文章信息管理添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微网站文章信息管理
	 * @param WWArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWWArticle")
	public OutputObject updateWWArticle(@ModelAttribute("ArticleForm") @Valid ArticleForm wWArticleForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wWArticleForm);
		outputObject = getOutputObject(map, "wWArticleService", "updateWWArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微网站文章信息管理编辑成功!");
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
		mav.setViewName("weiweb/article/add-article");
		return mav;
	}
	/**
	 * 去往更新页面
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView edit(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> moduleMap=new HashMap<String, Object>();
		moduleMap.put("articleId", request.getParameter("articleId"));
		model.addAllAttributes(moduleMap);
		mav.setViewName("weiweb/article/edit-article");
		return mav;
	}
	/**
	 * 去往查看模块页面
	 * @return
	 */
	@RequestMapping(value = "view")
	public ModelAndView menuView(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		Map<String,Object> moduleMap=new HashMap<String, Object>();
		moduleMap.put("articleId", request.getParameter("articleId"));
		model.addAllAttributes(moduleMap);
		mav.setViewName("weiweb/article/view-article");
		return mav;
	}
	/**
	 * 删除微网站文章信息管理
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWWArticle")
	public OutputObject deleteWWArticle(@ModelAttribute("ArticleForm") ArticleForm wWArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wWArticleForm);
		outputObject = getOutputObject(map, "wWArticleService", "deleteWWArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微网站文章信息管理删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微网站文章信息管理
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWWArticle")
	public OutputObject logicDeleteWWArticle(@ModelAttribute("ArticleForm") ArticleForm wWArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wWArticleForm);
		outputObject = getOutputObject(map, "wWArticleService", "logicDeleteWWArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	//将内容替换为html标签
	private String replaceHTMLEntity(String originalStr){
		originalStr = originalStr.replaceAll("&amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;rdquo;", "”");
		originalStr = originalStr.replaceAll("&lt;", "<");
		originalStr = originalStr.replaceAll("&gt;", ">");
		originalStr = originalStr.replaceAll("&quot;", "\"");
		originalStr = originalStr.replaceAll("&amp;nbsp;", " ");
		return originalStr;
	}
}
