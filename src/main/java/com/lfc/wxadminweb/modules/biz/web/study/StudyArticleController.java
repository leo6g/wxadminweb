package com.lfc.wxadminweb.modules.biz.web.study;


import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.utils.StringUtilLY;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.study.StudyArticleForm;
import com.lfc.wxadminweb.modules.system.form.PositionForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author zhaoyan
 * @date 2016-12-10 18:30
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/studyArticle")
public class StudyArticleController extends BaseController{
	
	@Value("#{system.picture_path}") 
	private  String path;
	
	/**
	 * 跳转到邮学堂文章表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/study/udyarticle-list");
		return mv;
	}
	
	//跳转到操作手册列表页面
	@RequestMapping(value = "manualsList")
	public ModelAndView manualsList( ModelAndView mv) {
		mv.setViewName("biz/study/manuals-list");
		return mv;
	}
	
	/**
	 * 跳转到邮学堂文章添加页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add( ModelAndView mv) {
		mv.setViewName("biz/study/add-studyArticle");
		return mv;
	}
	
	//跳往操作手册添加页面
	@RequestMapping(value = "manualsAdd")
	public ModelAndView manualsAdd( ModelAndView mv) {
		mv.setViewName("biz/study/add-manuals");
		return mv;
	}
	
	//跳往操作手册更改页面
	@RequestMapping(value = "manualsEdit")
	public ModelAndView manualsEdit( ModelAndView mv,Model model,HttpServletRequest request) {
		model.addAttribute("articleId", request.getParameter("id"));
		mv.setViewName("biz/study/edit-manuals");
		return mv;
	}
	
	/**
	 *根据ID查询邮学堂文章表
	 * @param StudyArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getManualsById")
	public OutputObject getManualsById(@ModelAttribute("StudyArticleForm") StudyArticleForm studyArticleForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(studyArticleForm);	
		outputObject = getOutputObject(map,"studyArticleService","getById");
		return outputObject;
	}
	/**
	 * 分页查询邮学堂文章表列表
	 * @param StudyArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("studyArticleForm") StudyArticleForm studyArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(studyArticleForm);
		outputObject = getOutputObject(map, "studyArticleService", "getList");
		List<Map<String,Object>> studyArticles=outputObject.getBeans();
		HttpServletRequest request = getRequest();
		String contextPath = request.getContextPath();
		for(Map<String,Object> studyArticle:studyArticles){
			studyArticle.put("title",StringUtilLY.replaceHTMLEntity(studyArticle.get("title").toString()));
			studyArticle.put("imgPath", contextPath+"/viewImage/viewImage?imgPath=study/"+studyArticle.get("imgPath"));
		}
		return outputObject;
	}
	/**
	 *根据ID查询邮学堂文章表
	 * @param StudyArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("StudyArticleForm") StudyArticleForm studyArticleForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(studyArticleForm);	
		outputObject = getOutputObject(map,"studyArticleService","getById");
		Map<String,Object> studyArticle=(Map<String, Object>) outputObject.getBeans();
		if(studyArticle != null){
			HttpServletRequest request = getRequest();
			String contextPath = request.getContextPath();
			studyArticle.put("imgPath", contextPath+"/viewImage/viewImage?imgPath=study/"+studyArticle.get("imgPath"));
		}
		return outputObject;
	}
	/**
	 * 查看所有邮学堂文章表
	 * @param "StudyArticleForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("StudyArticleForm") StudyArticleForm studyArticleForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(studyArticleForm);	
		outputObject = getOutputObject(map,"studyArticleService","getAll");
		List<Map<String,Object>> studyArticles=outputObject.getBeans();
		HttpServletRequest request = getRequest();
		String contextPath = request.getContextPath();
		for(Map<String,Object> studyArticle:studyArticles){
			studyArticle.put("imgPath", contextPath+"/viewImage/viewImage?imgPath=study/"+studyArticle.get("imgPath"));
		}
		return outputObject;
	}
	/**
	 * 添加邮学堂文章表
	 * @param StudyArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertStudyArticle")
	public OutputObject insertStudyArticle(@ModelAttribute("StudyArticleForm") @Valid StudyArticleForm studyArticleForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(studyArticleForm);
			System.out.println(map+" ---------->");
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "studyArticleService", "insertStudyArticle");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("邮学堂文章表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑邮学堂文章表
	 * @param StudyArticleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateStudyArticle")
	public OutputObject updateStudyArticle(@ModelAttribute("StudyArticleForm") @Valid StudyArticleForm studyArticleForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(studyArticleForm);
		outputObject = getOutputObject(map, "studyArticleService", "updateStudyArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("邮学堂文章表编辑成功!");
		}
		return outputObject;
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
		map.put("articleId", request.getParameter("id"));
		outputObject = getOutputObject(map,"studyArticleService","getById");
		Map<String, Object> studyArticle = (Map<String, Object>)outputObject.getObject();
		System.out.println(outputObject);
		if(null != studyArticle.get("imgPath")){
			String contextPath = request.getContextPath();
			studyArticle.put("imgPath", contextPath+"/viewImage/viewImage?imgPath=study/"+studyArticle.get("imgPath"));
		}
		model.addAttribute("studyArticle", outputObject);
		mav.setViewName("biz/study/edit-studyArticle");
		return mav;
	}
	/**
	 * 删除邮学堂文章表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteStudyArticle")
	public OutputObject deleteStudyArticle(@ModelAttribute("StudyArticleForm") StudyArticleForm studyArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(studyArticleForm);
		
		System.out.println(map+"----删除时的参数");
		outputObject = getOutputObject(map, "studyArticleService", "deleteStudyArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("邮学堂文章表删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除邮学堂文章表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteStudyArticle")
	public OutputObject logicDeleteStudyArticle(@ModelAttribute("StudyArticleForm") StudyArticleForm studyArticleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(studyArticleForm);
		outputObject = getOutputObject(map, "studyArticleService", "logicDeleteStudyArticle");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 查询所有邮学堂板块
	 * @param positionForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllStudyCategory")
	public OutputObject getAllPosition(@ModelAttribute("positionForm") PositionForm positionForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(positionForm);
		outputObject = getOutputObject(map, "studyCategoryService", "getAll");
		return outputObject;
	}
	
	
	
	/**
	 * 输出邮学堂文章内容参数  articleId   openId
	 * */
	@RequestMapping(value = "outputStudyArticleByArticleId")
	public ModelAndView outputStudyArticleByArticleId(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		String articleId = request.getParameter("articleId");
		//用户ID
		Map<String, Object> map = new HashMap<String, Object>();
		//查询文章信息
		map.put("articleId", articleId);
		OutputObject outputObject = getOutputObject(map,"studyArticleService","getById");
		Map<String, Object> mapObject = (Map<String, Object>)outputObject.getObject();
		
		
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
		if(createTime == null) createTime = "";
		if(author == null) author = "河南邮储银行";
		if(viewTimes == null) viewTimes = new BigDecimal(0);
		if(praiseTimes == null) praiseTimes = new BigDecimal(0);
		if(!"".equals(content) && null != content){
			content = replaceHTMLEntity(content);
		}
		
		// 更新文章阅读次数
		map.put("viewTimes", viewTimes.intValue()+1);
		
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		model.addAttribute("createTime", createTime);
		model.addAttribute("author", author);
		model.addAttribute("viewTimes", viewTimes);
		model.addAttribute("praiseTimes", praiseTimes);
		model.addAttribute("itemId", articleId);
		model.addAttribute("showAdver", mapObject.get("showAdver"));
		model.addAttribute("showPraise", mapObject.get("showPraise"));
		model.addAttribute("showRemark", mapObject.get("showRemark"));
		
		mav.setViewName("/biz/study/view-studyArticleByArticleId");
		return mav;
	}
	
	/**
	 * 上传图标
	 * @param request
	 * @param response
	 * @param model
	 * @param mm
	 */
	@ResponseBody
	@RequestMapping(value = "doUpload")
	public OutputObject doUpload(HttpServletRequest request, HttpServletResponse response, Model model,ModelMap mm) {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile=upload(request,uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		System.out.println(outputObject+"------------->");
		return outputObject;
	}
	
	@SuppressWarnings("unchecked")
	public  UploadFile upload(HttpServletRequest request,UploadFile uploadFile) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path+"study/";// 文件的硬盘真实路径
			System.out.println("------path-----"+path);
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			String fileName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// 获取上传文件对象
				String s = UUID.randomUUID().toString();
				String name = mf.getOriginalFilename();
				String extend = name.substring(name.lastIndexOf("."));
				fileName = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24)+extend;// 获取文件名				
				String savePath = realPath + fileName;//文件保存全路径
				File savefile = new File(savePath);
					// 设置文件数据库的物理路径
				String contextPath = request.getContextPath();
					String showImgPath=contextPath+"/viewImage/viewImage?imgPath=study/"+fileName;
					uploadFile.setRealPath(showImgPath);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
	
	
	/**
	 * 输出邮学堂文章内容参数  articleId   openId
	 * */
	@RequestMapping(value = "outputStudyArticle")
	public ModelAndView outputStudyArticle(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		String articleId = request.getParameter("articleId");
		//用户ID
		String openId = request.getParameter("openId");
		Map<String, Object> map = new HashMap<String, Object>();
		//查询文章信息
		map.put("articleId", articleId);
		map.put("openId", openId);
		OutputObject outputObject = getOutputObject(map,"studyArticleService","getById");
		System.out.println(outputObject+" ------------------->");
		Map<String, Object> mapObject = (Map<String, Object>)outputObject.getObject();
		System.out.println(mapObject+"------------------>");
		
		
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
		if(createTime == null) createTime = "";
		if(author == null) author = "河南邮储银行";
		if(viewTimes == null) viewTimes = new BigDecimal(0);
		if(praiseTimes == null) praiseTimes = new BigDecimal(0);
		if(!"".equals(content) && null != content){
			content = replaceHTMLEntity(content);
		}
		
		// 更新文章阅读次数
		map.put("viewTimes", viewTimes.intValue()+1);
		
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		model.addAttribute("createTime", createTime);
		model.addAttribute("author", author);
		model.addAttribute("viewTimes", viewTimes);
		model.addAttribute("praiseTimes", praiseTimes);
		model.addAttribute("itemId", articleId);
		model.addAttribute("openId", openId);
		model.addAttribute("showAdver", mapObject.get("showAdver"));
		model.addAttribute("showPraise", mapObject.get("showPraise"));
		model.addAttribute("showRemark", mapObject.get("showRemark"));
		
		mav.setViewName("weixin/view-studyArticle");
		return mav;
	}
	
	
	
	private String replaceHTMLEntity(String originalStr){
		originalStr = originalStr.replaceAll("amp;", "");
		originalStr = originalStr.replaceAll("&lt;", "<");
		originalStr = originalStr.replaceAll("&gt;", ">");
		originalStr = originalStr.replaceAll("&quot;", "\"");
		originalStr = originalStr.replaceAll("&nbsp;", " ");
		originalStr = originalStr.replaceAll("&ldquo;", "“");
		originalStr = originalStr.replaceAll("&rdquo;", "”");
		originalStr = originalStr.replaceAll("&middot;", "·");
		originalStr = originalStr.replaceAll("&amp;nbsp;", " ");
		originalStr = originalStr.replaceAll("&amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;amp;rdquo;", "”");
		originalStr = originalStr.replaceAll("&amp;rdquo;", "”");
		return originalStr;
	}
	
	
	
}
