package com.lfc.wxadminweb.modules.weiweb.web;


import java.io.File;
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
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.weiweb.form.BannerForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2016-11-07 09:37
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/weiweb/banner")
public class BannerController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;
	
	
	/**
	 * 跳转到微网站列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weiweb/banner/banner-list");
		return mv;
	}
	/**
	 * 分页查询微网站列表
	 * @param BannerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("BannerForm") BannerForm bannerForm,
			BindingResult result,HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bannerForm);
		outputObject = getOutputObject(map, "bannerService", "getList");
		
		List<Map<String,Object>> banners=outputObject.getBeans();
		System.out.println("集合"+banners+"集合");
		String contextPath = request.getContextPath();
		for(Map<String,Object> banner:banners){
			System.out.println("++++"+banner+"---");
			banner.put("path", contextPath+"/viewImage/viewImage?imgPath="+banner.get("path"));
			System.out.println(banner.get("path")+"哈哈哈哈哈哈哈哈哈哈");
		}
		return outputObject;
	}
	/**
	 *根据ID查询微网站
	 * @param BannerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("BannerForm") BannerForm bannerForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(bannerForm);	
		outputObject = getOutputObject(map,"bannerService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微网站
	 * @param "BannerForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("BannerForm") BannerForm bannerForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(bannerForm);	
		outputObject = getOutputObject(map,"bannerService","getAll");
		return outputObject;
	}
	/**
	 * 添加微网站
	 * @param BannerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertBanner")
	public OutputObject insertBanner(@ModelAttribute("BannerForm") @Valid BannerForm bannerForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(bannerForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "bannerService", "insertBanner");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微网站添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微网站
	 * @param BannerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateBanner")
	public OutputObject updateBanner(@ModelAttribute("BannerForm") @Valid BannerForm bannerForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bannerForm);
		outputObject = getOutputObject(map, "bannerService", "updateBanner");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微网站编辑成功!");
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
		mav.setViewName("weiweb/banner/add-banner");
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
		outputObject = getOutputObject(map,"bannerService","getById");
		List<Map<String,Object>> banners=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> banner:banners){
			banner.put("path", contextPath+"/viewImage/viewImage?imgPath="+banner.get("path"));
			System.out.println(banner.get("path"));
		}
		model.addAttribute("output", outputObject);
		mav.setViewName("weiweb/banner/edit-banner");
		return mav;
	}
	/**
	 * 删除微网站
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBanner")
	public OutputObject deleteBanner(@ModelAttribute("BannerForm") BannerForm bannerForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bannerForm);
		outputObject = getOutputObject(map, "bannerService", "deleteBanner");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微网站删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微网站
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteBanner")
	public OutputObject logicDeleteBanner(@ModelAttribute("BannerForm") BannerForm bannerForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(bannerForm);
		outputObject = getOutputObject(map, "bannerService", "logicDeleteBanner");
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
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile=upload(uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("imagePath", uploadFile.getTitleField());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		System.out.println("ceshi --1---------------------------"+uploadFile.getTitleField());
		outputObject.setBean(map);
		return outputObject;
	}
	
	@SuppressWarnings("unchecked")
	public  UploadFile upload(UploadFile uploadFile) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path;// 文件的硬盘真实路径
			System.out.println("--真实----path-----"+path);
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
				System.out.println("---AAAA-------------------"+savePath);
				File savefile = new File(savePath);
					// 设置文件数据库的物理路径
					uploadFile.setRealPath(realPath + fileName);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
	
}
