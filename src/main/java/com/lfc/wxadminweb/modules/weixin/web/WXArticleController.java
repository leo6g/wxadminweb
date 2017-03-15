package com.lfc.wxadminweb.modules.weixin.web;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.lfc.core.bean.InputObject;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.utils.StringUtilLY;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.threads.SimpleThread;
import com.lfc.wxadminweb.modules.weixin.form.ArticlePraiseForm;
import com.lfc.wxadminweb.modules.weixin.form.WXArticleForm;

/**
 * 微信文章管理
 * 
 * @descript 
 * @author jzp
 * @date 2016-10-25 08:37
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/article")
public class WXArticleController extends BaseController{
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到微信文章列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( HttpServletRequest request,Model model) {
		ModelAndView mv=new ModelAndView();
		//角色绑定
		String roleIds = getSession().getAttribute(Constants.USER_SESSION.ROLEIDS).toString();
		if(roleIds !=null && !"".equals(roleIds)){
			//文章编辑
			if(roleIds.contains("e9a410bff55b4e32b5dd5e57eacf19c0")){
				model.addAttribute("role", "arti_ed");
			}
			//信息办
			if(roleIds.contains("40e993400df54e3b87472c27e578ea9f")){
				model.addAttribute("role", "infor");
			}
			//法规办审核员
			if(roleIds.contains("396712c87c654c5fb008c6ae2469f74a")){
				model.addAttribute("role", "law_spec");
			}
			//电子银行部
			if(roleIds.contains("4c03466ca0e440ef81444a189dfca3d9")){
				model.addAttribute("role", "cardcheck");
			}
		}
		mv.setViewName("weixin/article-list");
		return mv;
	}
	/**
	 * 分页查询微信文章列表
	 * @param WXArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXArticleForm") WXArticleForm wXArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		String roleIds = getSession().getAttribute(Constants.USER_SESSION.ROLEIDS).toString();
		if(roleIds !=null && !"".equals(roleIds)){
			//文章编辑
			if(roleIds.contains("e9a410bff55b4e32b5dd5e57eacf19c0")){
				model.addAttribute("role", "arti_ed");
			}
			//信息办
			if(roleIds.contains("40e993400df54e3b87472c27e578ea9f")){
				model.addAttribute("role", "infor");
			}
			//法规办审核员
			if(roleIds.contains("396712c87c654c5fb008c6ae2469f74a")){
				model.addAttribute("role", "law_spec");
			}
			//电子银行部
			if(roleIds.contains("4c03466ca0e440ef81444a189dfca3d9")){
				model.addAttribute("role", "cardcheck");
			}
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXArticleForm);
		outputObject = getOutputObject(map, "wXArticleService", "getList");
		System.out.println("-------"+outputObject.toString());
		return outputObject;
	}
	/**
	 *根据ID查询微信文章
	 * @param WXArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXArticleForm") WXArticleForm wXArticleForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXArticleForm);	
		outputObject = getOutputObject(map,"wXArticleService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信文章
	 * @param "WXArticleForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXArticleForm") WXArticleForm wXArticleForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXArticleForm);	
		outputObject = getOutputObject(map,"wXArticleService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信文章
	 * @param WXArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXArticle")
	public OutputObject insertWXArticle(@ModelAttribute("WXArticleForm") @Valid WXArticleForm wXArticleForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXArticleForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXArticleService", "insertWXArticle");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信文章添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信文章
	 * @param WXArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXArticle")
	public OutputObject updateWXArticle(@ModelAttribute("WXArticleForm") @Valid WXArticleForm wXArticleForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXArticleForm);
		outputObject = getOutputObject(map, "wXArticleService", "updateWXArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信文章编辑成功!");
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
		mav.setViewName("weixin/add-article");
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
		outputObject = getOutputObject(map,"wXArticleService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-article");
		return mav;
	}
	/**
	 * 跳转到微信文章详情页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "artcView")
	public ModelAndView tempView( HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		model.addAttribute("newsTempId", request.getParameter("newsTempId"));
		model.addAttribute("role", request.getParameter("role"));
		model.addAttribute("authState", request.getParameter("authState"));
		mav.setViewName("weixin/look-article");
		return mav;
	}
	/**
	 * 删除微信文章
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXArticle")
	public OutputObject deleteWXArticle(@ModelAttribute("WXArticleForm") WXArticleForm wXArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXArticleForm);
		outputObject = getOutputObject(map, "wXArticleService", "deleteWXArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信文章删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信文章
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXArticle")
	public OutputObject logicDeleteWXArticle(@ModelAttribute("WXArticleForm") WXArticleForm wXArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXArticleForm);
		outputObject = getOutputObject(map, "wXArticleService", "logicDeleteWXArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 去往文章内容新增页面
	 * @return
	 */
	@RequestMapping(value = "articleDetailsAdd")
	public ModelAndView newsItemAdd(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		model.addAttribute("newsTempId", request.getParameter("newsTempId"));
		model.addAttribute("htmlTittle", "微信文章内容");
		model.addAttribute("htmlHostTittle", "微信文章");
		mav.setViewName("weixin/newsitemadd");
		return mav;
	}
	/**
	 * 去往文章内容显示页面
	 * @return
	 */
	@RequestMapping(value = "articleDetailsView")
	public ModelAndView newsItemEdit(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		model.addAttribute("newsTempId", request.getParameter("newsTempId"));
		model.addAttribute("htmlTittle", "微信文章内容");
		model.addAttribute("htmlHostTittle", "微信文章");
		mav.setViewName("weixin/newsitems-list");
		return mav;
	}
	/**
	 * 添加文章点赞记录
	 * @param WXArticlePraiseForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "insertArticlePraise")
	public OutputObject insertWXArticlePraise(@ModelAttribute("ArticlePraiseForm") @Valid ArticlePraiseForm articlePraiseForm, Model model,ModelMap mm) {
		
			OutputObject outputObject = null;
			Map<String, Object> m = new HashMap<String, Object>();
			//查询点赞积分
			m.put("dicCode", "praiseAmount");
			OutputObject outputAmount = getOutputObject(m, "dictionaryService", "getDicDetailByDicCode");
			Map<String, Object> mapAmount = (Map<String, Object>)outputAmount.getBean();
			String amount = (String)mapAmount.get("dicName");
			//查询文章信息
			m.clear();
			m.put("itemId", articlePraiseForm.getItemId());
			OutputObject outputItem = getOutputObject(m,"wXNewsItemsService","getById");
			Map<String, Object> mapObject = (Map<String, Object>)outputItem.getObject();
			//点赞次数
			BigDecimal praiseTimes  = (BigDecimal)mapObject.get("praiseTimes") == null ? new BigDecimal(0) : (BigDecimal)mapObject.get("praiseTimes");
			m.put("praiseTimes", praiseTimes.intValue()+1);
			//同步文章表中点赞次数
			getOutputObject(m, "wXNewsItemsService", "updateWXNewsItems");
			
			//同步点赞表信息
			Map<String, Object> map = BeanUtil.convertBean2Map(articlePraiseForm);
			//将积分表数据放入参
			map.put("type", "praise");
			map.put("amount", amount);
			outputObject = getOutputObject(map, "articlePraiseService", "insertWXArticlePraise");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("文章点赞记录添加成功!");
			}
			return outputObject;
	}
	/**
	 * 去往文章内容显示页面
	 * @return
	 */
	@RequestMapping(value = "outputArticle")
	public ModelAndView outputArticle(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		String articleId = request.getParameter("articleId");
		//用户ID
		String openId = request.getParameter("openId");
		Map<String, Object> map = new HashMap<String, Object>();
		//查询文章信息
		map.put("itemId", articleId);
		map.put("openId", openId);
		OutputObject outputObject = getOutputObject(map,"wXNewsItemsService","getById");
		Map<String, Object> mapObject = (Map<String, Object>)outputObject.getObject();
		
		//获得点赞状态
		OutputObject outputPrais = getOutputObject(map,"articlePraiseService","getAll");
		List<Map<String, Object>>  listMap = outputPrais.getBeans();
		
		
		String content  = (String)mapObject.get("content");
		String title  = (String)mapObject.get("title");
		String createTime  = "";
		if(mapObject.get("createTime") != null && !"".equals(mapObject.get("createTime"))){
			createTime  = new SimpleDateFormat("yyyy-MM-dd").format((Date)mapObject.get("createTime"));
		}
		
		String author  = (String)mapObject.get("author");
		//浏览次数
		BigDecimal viewTimes  = (BigDecimal)mapObject.get("viewTimes");
		//点赞次数
		BigDecimal praiseTimes  = (BigDecimal)mapObject.get("praiseTimes");
		//点赞状态
		String praiseState = (String) ((listMap == null || listMap.size() == 0) ? "F" : listMap.get(0).get("state"));
		if(createTime == null) createTime = "";
		if(author == null) author = "河南邮储银行";
		if(viewTimes == null) viewTimes = new BigDecimal(0);
		if(praiseTimes == null) praiseTimes = new BigDecimal(0);
		if(!"".equals(content) && null != content){
			content = replaceHTMLEntity(content);
			content = content.replaceAll("$opneId$", openId);
		}
		
		// 更新文章阅读次数  原来方案  beg
		map.put("viewTimes", viewTimes.intValue()+1);
		getOutputObject(map, "wXNewsItemsService", "updateWXNewsItems");
		// 更新文章阅读次数  原来方案   end
		
		// 更新文章阅读次数   多线程方案  beg
//		InputObject paraMap = new InputObject();
//		map.put("viewTimes", viewTimes.intValue()+1);
//		paraMap.setParams(map);
//		paraMap.setService("wXNewsItemsService");
//		paraMap.setMethod("updateWXNewsItems");
//		SimpleThread SimpleThread = new SimpleThread(paraMap);
//		SimpleThread.setControlService(super.getIControlService());
//		SimpleThread.start();
		// 更新文章阅读次数   多线程方案  end
		
		
		
		title=StringUtilLY.replaceHTMLEntity(title);
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		model.addAttribute("createTime", createTime);
		model.addAttribute("author", author);
		model.addAttribute("viewTimes", viewTimes);
		model.addAttribute("praiseTimes", praiseTimes);
		model.addAttribute("praiseState", praiseState);
		model.addAttribute("itemId", articleId);
		model.addAttribute("openId", openId);
		model.addAttribute("showAdver", mapObject.get("showAdver"));
		model.addAttribute("showPraise", mapObject.get("showPraise"));
		model.addAttribute("showRemark", mapObject.get("showRemark"));
		
		mav.setViewName("weixin/view-article");
		return mav;
	}
	
	/*@RequestMapping(value = "outputArticle")
	public void outputArticle(HttpServletRequest request , HttpServletResponse response) {
		try{
			
			response.setHeader("Content-type", "text/html;charset=UTF-8");  
			response.setCharacterEncoding("UTF-8");
			
			String articleId = request.getParameter("articleId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("itemId", articleId);
			OutputObject outputObject = getOutputObject(map,"wXNewsItemsService","getById");
			
			Map<String, Object> mapObject = (Map<String, Object>)outputObject.getObject();
			OutputStream os = response.getOutputStream();
			
			String header = "<html><head><style>img{width: 100%;}</style><meta content='text/html; charset=utf-8'>"
					+ "<meta name='viewport' content='width=device-width,user-scalable=no' />"
					+ "</head><body>";
			String footer = "</body></html>";
			
			String titleTypeH = "<h3><b>";
			String titleTypeF = "</b></h3>";
			
			String timeTypeH = "<font color = 'lightgray'>";
			String timeTypeF = "</font>";
			
			String authorTypeH = "<font color = '#87CEFA'>";
			String authorTypeF = "</font>";
			
			
			String content  = (String)mapObject.get("content");
			String title  = (String)mapObject.get("title");
			String createTime  = new SimpleDateFormat("yyyy-MM-dd").format((Date)mapObject.get("createTime"));
			String author  = (String)mapObject.get("author");
			//浏览次数
			BigDecimal viewTimes  = (BigDecimal)mapObject.get("viewTimes");
			//点赞次数
			BigDecimal praiseTimes  = (BigDecimal)mapObject.get("praiseTimes");
			
			if(createTime == null) createTime = "";
			if(author == null) author = "";
			if(viewTimes == null) viewTimes = new BigDecimal(0);
			if(praiseTimes == null) praiseTimes = new BigDecimal(0);
			if(!"".equals(content) && null != content){
				content = replaceHTMLEntity(content);
				content = header + titleTypeH + title + titleTypeF + 
						timeTypeH + createTime + timeTypeF + " " + 
						authorTypeH + author + authorTypeF + content + 
						timeTypeH + "阅读  "+ viewTimes + timeTypeF + footer;
				os.write(content.getBytes("utf-8"));
			}
			
			if(os != null){
				os.close();
			}
			map.put("viewTimes", viewTimes.intValue()+1);
			outputObject = getOutputObject(map, "wXNewsItemsService", "updateWXNewsItems");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	*/
	private String replaceHTMLEntity(String originalStr){
		originalStr = originalStr.replaceAll("&amp;nbsp;", "&nbsp;");
		originalStr = originalStr.replaceAll("&amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;amp;rdquo;", "”");
		originalStr = originalStr.replaceAll("&amp;rdquo;", "”");
		originalStr = originalStr.replaceAll("amp;", "");
		originalStr = originalStr.replaceAll("&lt;", "<");
		originalStr = originalStr.replaceAll("&gt;", ">");
		originalStr = originalStr.replaceAll("&quot;", "\"");
		//originalStr = originalStr.replaceAll("&nbsp;", " ");
		originalStr = originalStr.replaceAll("&ldquo;", "“");
		originalStr = originalStr.replaceAll("&rdquo;", "”");
		originalStr = originalStr.replaceAll("&middot;", "·");
		return originalStr;
	}
	
}
