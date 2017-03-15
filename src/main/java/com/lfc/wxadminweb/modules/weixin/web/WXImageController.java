package com.lfc.wxadminweb.modules.weixin.web;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import com.github.sd4324530.fastweixin.api.MaterialAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.enums.MaterialType;
import com.github.sd4324530.fastweixin.api.enums.ResultType;
import com.github.sd4324530.fastweixin.api.response.GetMaterialListResponse;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadMaterial;
import com.lfc.wxadminweb.common.utils.PropertiesUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXMaterialForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author star
 * @date 2016-10-17 10:16
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/image")
public class WXImageController extends BaseController{
	
	
	@Value("#{system.picture_path}") 
	private  String path;	

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
	 * 跳转到微信素材管理-图片管理表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/media/image-list");
		return mv;
	}	
	
	@RequestMapping(value = "css-test")
	public ModelAndView list3( ModelAndView mv) {
		mv.setViewName("weixin/media/css-test");
		return mv;
	}	
	
	/**
	 * 添加微信素材表之图片--添加移到上传了
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "insertImage")
	public OutputObject insertImage(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			map.put("category","image");
			outputObject = getOutputObject(map, "wXMaterialService", "insertImage");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信素材图片添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信素材表之图片信息
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateImage")
	public OutputObject updateImage(@ModelAttribute("wXMaterialForm") WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		outputObject = getOutputObject(map, "wXMaterialService", "updateWXMaterial");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信素材图片编辑成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 编辑微信素材表之多张图片移动分组
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateImageGroup")
	public OutputObject updateImageGroup(@ModelAttribute("wXMaterialForm") WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		outputObject = getOutputObject(map, "wXMaterialService", "updateImageGroup");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信素材图片编辑成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 删除微信素材表之图片
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteImage")
	public OutputObject deleteImage(@ModelAttribute("wXMaterialForm") WXMaterialForm wXMaterialForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		ApiConfig ApiConfig = new ApiConfig(appId, secret);
		MaterialAPI materialAPI = new MaterialAPI(ApiConfig);
		ResultType rt=materialAPI.deleteMaterial(wXMaterialForm.getMediaId());
		OutputObject outputObject = new OutputObject();
		if(rt.getCode()!=0){
			outputObject.setReturnMessage(rt.getDescription());
		}else{
			Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
			outputObject = getOutputObject(map, "wXMaterialService", "deleteWXMaterial");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信素材图片删除成功!");
			}
		}
		return outputObject;
	}
	
	
	/**
	 * 删除微信素材表之图片批量
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteImageGroup")
	public OutputObject deleteImageGroup(@ModelAttribute("wXMaterialForm") WXMaterialForm wXMaterialForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		ApiConfig ApiConfig = new ApiConfig(appId, secret);
		MaterialAPI materialAPI = new MaterialAPI(ApiConfig);
		ResultType rt=null;
		String[] mediaIds=wXMaterialForm.getMediaId().split(",");
		for(String mediaId:mediaIds){	
			System.out.println(mediaId);
			rt=materialAPI.deleteMaterial(mediaId);
		}
		OutputObject outputObject = new OutputObject();
		if(rt.getCode()!=0){
			outputObject.setReturnMessage(rt.getDescription());
		}else{
			Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
			String[] materialIds=wXMaterialForm.getMaterialId().split(",");
			String materialIdStr="";
			for(String materialId:materialIds){			
				materialIdStr+="'"+materialId+"',";
			}
			materialIdStr=materialIdStr.substring(0,materialIdStr.length()-1);
			map.put("materialId", materialIdStr);
			outputObject = getOutputObject(map, "wXMaterialService", "deleteImageGroup");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信素材图片删除成功!");
			}
		}
		return outputObject;
	}
	
	
	/**
	 * 查询微信素材管理-图片管理列表
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXMaterialForm") WXMaterialForm wXMaterialForm,HttpServletRequest request,
			BindingResult result,  Model model, ModelMap mm){
//		ApiConfig ApiConfig = new ApiConfig(appId, secret);
//		MaterialAPI materialAPI = new MaterialAPI(ApiConfig);
//			//分页查询上传的图片的数量 0是下标 10是数量（1到20之间）
//			GetMaterialListResponse response = materialAPI.batchGetMaterial(MaterialType.IMAGE, 0, 10);
//            System.out.println("微信图片数量"+response.getItemCount());
//			//返回参数详解https://mp.weixin.qq.com/wiki/15/8386c11b7bc4cdd1499c572bfe2e95b3.html
//	        OutputObject outputObject = new OutputObject();
//	        outputObject.setBeans(response.getItems());
//	        outputObject.setReturnCode("0");
//	        outputObject.setReturnMessage("获取图片列表成功");
		    OutputObject outputObject = null;
		    Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		    outputObject = getOutputObject(map, "wXMaterialService", "getImageList");
			List<Map<String,Object>> images=outputObject.getBeans();
			String contextPath = request.getContextPath();
			for(Map<String,Object> image:images){		
				if(image.get("localUrl")==null){
					image.put("localUrl", contextPath+"/viewImage/viewImage?imgPath=defaultf29b7fcd44314b7b7540e45d5f37a.jpg");
				}else{
					image.put("localUrl", contextPath+"/viewImage/viewImage?imgPath=material/"+image.get("localUrl"));
				}
			}

			return outputObject;
	}		
	/**
	 * 微信素材管理-图片管理上传
	 * @param request
	 * @param response
	 * @param model
	 * @param mm
	 */
	@ResponseBody
	@RequestMapping(value = "uploadImage")
	public OutputObject uploadImage(HttpServletRequest request, HttpServletResponse response, Model model,ModelMap mm) {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile=UploadMaterial.uploadImage(request, uploadFile, path, appId, secret);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("realPath", uploadFile.getRealPath());//上传文件的本地路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		map.put("mediaId",uploadFile.getMediaId());//微信服务器返回的mediaID
		map.put("errorMessage",uploadFile.getErrorMessage());//微信服务器返回的错误信息
		if(StringUtils.isNotEmpty(uploadFile.getMediaId())){
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			map.put("category","image");
			String[] fileName=uploadFile.getTitleField().split("\\.");
			map.put("name",fileName[0]);
			map.put("localUrl", uploadFile.getTitleField());
			map.put("groupId", "default");
			map.put("remoteUrl",uploadFile.getUrl());
			outputObject = getOutputObject(map, "wXMaterialService", "insertImage");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信素材图片添加成功!");
			}
		}
		outputObject.setBean(map);
		return outputObject;
	}
	
	
	/**
	 * 同步微信图片素材
	 * @param wXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "syncImage")
	public OutputObject syncImage(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
		map.put("appId", PropertiesUtil.getString("appId"));
		map.put("secret", PropertiesUtil.getString("secret"));
		map.put("path", path);
		outputObject = getOutputObject(map, "wXMaterialService", "updateSyncImages");
		return outputObject;
	}

}
