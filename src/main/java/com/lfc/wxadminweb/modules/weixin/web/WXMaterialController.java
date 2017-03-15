package com.lfc.wxadminweb.modules.weixin.web;


import java.io.File;
import java.util.ArrayList;
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

import com.github.sd4324530.fastweixin.api.MaterialAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.Article;
import com.github.sd4324530.fastweixin.api.response.UploadMaterialResponse;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXMaterialForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author GY
 * @date 2016-10-12 15:11
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/material")
public class WXMaterialController extends BaseController{
	
	@Value("#{system.picture_path}") 
	private  String path;
	
	
	@Value("#{system.appId}") 
	private  String appId;
	
	
	@Value("#{system.secret}") 
	private  String secret;
	
	
	
	
	/**
	 * 跳转到微信素材表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/material-list");
		return mv;
	}
	/**
	 * 分页查询微信素材表列表
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXMaterialForm") WXMaterialForm wXMaterialForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		outputObject = getOutputObject(map, "wXMaterialService", "getList");
		
		
		//
		System.out.println("beg------------"+appId+"----------->"+secret);
		ApiConfig apiConfig = new ApiConfig(appId,secret);
		MaterialAPI mediaAPI = new MaterialAPI(apiConfig);
		File file = new File("d://4-15012G52133.jpg");
		UploadMaterialResponse testResult = mediaAPI.uploadMaterialFile(file, null, "222");
		System.out.println(testResult.getMediaId());
//		System.out.println("end------------");
		//
		
		//图文素材
		List<Article> listArticle = new ArrayList<Article>();
		for (int i = 0; i < 2; i++) {
			Article article = new Article();
			article.setTitle("测试");
			article.setThumbMediaId(testResult.getMediaId());
			article.setAuthor("zhaoyan");
			article.setDigest("ddddd");
			article.setShowConverPic(new Integer(1));
			article.setContent("测试内容");
			article.setContentSourceUrl("sdfklskdflskdf");
			listArticle.add(article);
		}
		
		
		UploadMaterialResponse twResult = mediaAPI.uploadMaterialNews(listArticle);
		System.out.println(twResult.getMediaId()+"----------图文ID");
		
		
		
		
		
		
		return outputObject;
	}
	/**
	 *根据ID查询微信素材表
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);	
		outputObject = getOutputObject(map,"wXMaterialService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信素材表
	 * @param "WXMaterialForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);	
		outputObject = getOutputObject(map,"wXMaterialService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信素材表
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "insertWXMaterial")
	public OutputObject insertWXMaterial(@ModelAttribute("WXMaterialForm") @Valid WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXMaterialService", "insertWXMaterial");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信素材表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信素材表
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXMaterial")
	public OutputObject updateWXMaterial(@ModelAttribute("WXMaterialForm") @Valid WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		outputObject = getOutputObject(map, "wXMaterialService", "updateWXMaterial");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信素材表编辑成功!");
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
		mav.setViewName("weixin/add-material");
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
		outputObject = getOutputObject(map,"wXMaterialService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-material");
		return mav;
	}
	/**
	 * 删除微信素材表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXMaterial")
	public OutputObject deleteWXMaterial(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		outputObject = getOutputObject(map, "wXMaterialService", "deleteWXMaterial");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信素材表删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信素材表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXMaterial")
	public OutputObject logicDeleteWXMaterial(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		outputObject = getOutputObject(map, "wXMaterialService", "logicDeleteWXMaterial");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
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
		System.out.println("进来了........");
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
			String realPath =path+"wxmaterial/";// 文件的硬盘真实路径
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
					String showImgPath=contextPath+"/viewImage/viewImage?imgPath=wxmaterial/"+fileName;
					uploadFile.setRealPath(showImgPath);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
	
}
