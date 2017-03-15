package com.lfc.wxadminweb.modules.weixin.web;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;













import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.github.sd4324530.fastweixin.api.response.UploadMaterialResponse;
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
 * @author lbb
 * @date 2016-10-17 10:16
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/voice")
public class WXVoiceController extends BaseController{
	
	
	@Value("#{system.video_path}") 
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
	 * 跳转到微信素材管理-音频管理表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/media/voice-list");
		return mv;
	}	
	
	/**
	 * 跳转到微信素材管理-音频管理添加页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add( ModelAndView mv) {
		mv.setViewName("weixin/media/voice-add");
		return mv;
	}
	/**
	 * 添加微信素材表之音频
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "insertVoice")
	public OutputObject insertImage(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			map.put("category","voice");
			outputObject = getOutputObject(map, "wXMaterialService", "insertVoice");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信素材视频添加成功!");
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
	@RequestMapping(value = "updateVoice")
	public OutputObject updateImage(@ModelAttribute("wXMaterialForm") WXMaterialForm wXMaterialForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		outputObject = getOutputObject(map, "wXMaterialService", "updateWXMaterial");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信素材音频编辑成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 删除微信素材表之音频
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteVoice")
	public OutputObject deleteVoice(@ModelAttribute("wXMaterialForm") WXMaterialForm wXMaterialForm,
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
				outputObject.setReturnMessage("微信素材音频删除成功!");
			}
		}
		return outputObject;
	}
	/**
	 * 查询微信素材管理-音频管理列表
	 * @param WXUserForm
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
//			ApiConfig ApiConfig = new ApiConfig(appId, secret);
//			MaterialAPI materialAPI = new MaterialAPI(ApiConfig);
//			//分页查询上传的音频的数量 0是下标 10是数量（1到20之间）
//			GetMaterialListResponse response = materialAPI.batchGetMaterial(MaterialType.VOICE, 0, 10);
//	        System.out.println("Total Count : " + response.getTotalCount());
//	        System.out.println("Item Count : " + response.getItemCount());
//	        OutputObject outputObject = new OutputObject();
//	        outputObject.setBeans(response.getItems());
//	        outputObject.setReturnCode("0");
//	        outputObject.setReturnMessage("获取音频列表成功");
		    OutputObject outputObject = null;
		    Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		    map.put("category","voice");
		    outputObject = getOutputObject(map, "wXMaterialService", "getList");
			List<Map<String,Object>> voices=outputObject.getBeans();
			String contextPath = request.getContextPath();
			for(Map<String,Object> voice:voices){						
				voice.put("localUrl", contextPath+"/viewVideo/viewVideo?videoPath=material/"+voice.get("localUrl")+"&videoName="+voice.get("localUrl"));
			}
		    return outputObject;
	}	
	
	/**
	 * 微信素材管理-视频管理上传
	 * @param request
	 * @param response
	 * @param model
	 * @param mm
	 */
	@ResponseBody
	@RequestMapping(value = "uploadVoice")
	public OutputObject uploadVoice(HttpServletRequest request, HttpServletResponse response, Model model,ModelMap mm) {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile=UploadMaterial.uploadVoice(request, uploadFile, path, appId, secret);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("realPath", uploadFile.getRealPath());//上传文件的本地路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		map.put("mediaId",uploadFile.getMediaId());//微信服务器返回的mediaID
		map.put("errorMessage",uploadFile.getErrorMessage());//微信服务器返回的错误信息
		outputObject.setBean(map);
		return outputObject;
	}
	
	
	/**
	 * 同步微信视频素材
	 * @param wXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "syncVoice")
	public OutputObject syncVoice(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,
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
		outputObject = getOutputObject(map, "wXMaterialService", "updateSyncVoices");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("音频素材同步成功!");
		}
		return outputObject;
	}
}
