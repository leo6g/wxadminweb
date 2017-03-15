package com.lfc.wxadminweb.modules.biz.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.web.BaseController;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;







import com.lfc.wxadminweb.modules.biz.form.AdvertisementForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author star
 * @date 2016-11-22 09:06
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/advertisement")
public class AdvertisementController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到文章植入广告管理列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/advertisement/vertisement-list");
		return mv;
	}
	/**
	 * 分页查询文章植入广告管理列表
	 * @param AdvertisementForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("advertisementForm") AdvertisementForm advertisementForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(advertisementForm);
		outputObject = getOutputObject(map, "advertisementService", "getList");
		List<Map<String,Object>> advertis=outputObject.getBeans();
		HttpServletRequest request = getRequest();
		String contextPath = request.getContextPath();
		for(Map<String,Object> adverti:advertis){
			adverti.put("imgPath", contextPath+"/viewImage/viewImage?imgPath=advertisement/"+adverti.get("imgPath"));
		}
		return outputObject;
	}
	/**
	 *根据ID查询文章植入广告管理
	 * @param AdvertisementForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("AdvertisementForm") AdvertisementForm advertisementForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(advertisementForm);	
		outputObject = getOutputObject(map,"advertisementService","getById");
		return outputObject;
	}
	/**
	 * 查看所有文章植入广告管理
	 * @param "AdvertisementForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("AdvertisementForm") AdvertisementForm advertisementForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(advertisementForm);	
		outputObject = getOutputObject(map,"advertisementService","getAll");
		List<Map<String,Object>> advertis=outputObject.getBeans();
		HttpServletRequest request = getRequest();
		String contextPath = request.getContextPath();
		for(Map<String,Object> adverti:advertis){
			adverti.put("imgPath", contextPath+"/viewImage/viewImage?imgPath=advertisement/"+adverti.get("imgPath"));
		}
		return outputObject;
	}
	/**
	 * 添加文章植入广告管理
	 * @param AdvertisementForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertAdvertisement")
	public OutputObject insertAdvertisement(@ModelAttribute("AdvertisementForm") @Valid AdvertisementForm advertisementForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(advertisementForm);
			System.out.println(map+"------------<>");
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "advertisementService", "insertAdvertisement");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("文章植入广告管理添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑文章植入广告管理
	 * @param AdvertisementForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateAdvertisement")
	public OutputObject updateAdvertisement(@ModelAttribute("AdvertisementForm") @Valid AdvertisementForm advertisementForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(advertisementForm);
		outputObject = getOutputObject(map, "advertisementService", "updateAdvertisement");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("文章植入广告管理编辑成功!");
		}
		return outputObject;
	}
	/**
	 * 删除文章植入广告管理
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAdvertisement")
	public OutputObject deleteAdvertisement(@ModelAttribute("AdvertisementForm") AdvertisementForm advertisementForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(advertisementForm);
		outputObject = getOutputObject(map, "advertisementService", "deleteAdvertisement");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("文章植入广告管理删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除文章植入广告管理
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteAdvertisement")
	public OutputObject logicDeleteAdvertisement(@ModelAttribute("AdvertisementForm") AdvertisementForm advertisementForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(advertisementForm);
		outputObject = getOutputObject(map, "advertisementService", "logicDeleteAdvertisement");
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
			String realPath =path+"advertisement/";// 文件的硬盘真实路径
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
					String showImgPath=contextPath+"/viewImage/viewImage?imgPath=advertisement/"+fileName;
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
