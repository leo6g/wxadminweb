package com.lfc.wxadminweb.modules.weixin.web;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.sd4324530.fastweixin.api.MediaAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.response.UploadImgResponse;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.utils.DateUtil;
import com.lfc.wxadminweb.common.utils.FileDownLoadUtil;
import com.lfc.wxadminweb.common.utils.PropertiesUtil;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.weixin.form.WXArticleMaterialForm;
import com.lfc.wxadminweb.modules.weixin.form.WXMaterialForm;

/**
 * <h2></br>
 * @descript  微信图文素材管理
 * @author helei
 * @date 2017-01-12
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/artmtl")
public class WXArticleMarterialController extends BaseController{
	
	
	/**
	 * 测试接口页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "test")
	public ModelAndView test(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("weixin/test/testInterface");
		return mv;
	}
	
	/**
	 * 根据外链图片URL，返回微信服务器URL地址，入参为单个图片地址，回参也是单个图片地址
	 * @param remoteUrl
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getWxUrl")
	public OutputObject getWxUrl(@ModelAttribute("img") String img) {
		boolean updSuccFlag = true;
		OutputObject outputObject = new OutputObject();
		//微信公众号appId和secret
		String appId = PropertiesUtil.getString("appId");
		String secret = PropertiesUtil.getString("secret");
		ApiConfig config = new ApiConfig(appId, secret);
		if(StringUtils.isNotEmpty(img)){
			String picSavePath = PropertiesUtil.getString("picture_path") + "wxpic/tmp/" + DateUtil.getCurrYMDHMSS() + ".jpg";
			//下载img的src外链的图片到服务器硬盘
			boolean flag = FileDownLoadUtil.downLoadFile(img, picSavePath);
			if(flag){
				//下载成功后，上传本地图片到微信服务器，替换图文内容的图片地址
				MediaAPI mediaAPI = new MediaAPI(config);
				UploadImgResponse response = mediaAPI.uploadImg(new File(picSavePath));
				String wxUrl = response.getUrl();
				if(StringUtils.isNotEmpty(wxUrl)){
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("img", wxUrl);
					outputObject.setObject(result);
				}else{
					updSuccFlag = false;
				}
			}else{
				updSuccFlag = false;
			}
		}else{
			updSuccFlag = false;
		}
		if(updSuccFlag){
			outputObject.setReturnCode("0");
			outputObject.setReturnMessage("图片上传成功.");
		}else{
			outputObject.setReturnCode("-9999");
			outputObject.setReturnMessage("图片上传失败.");
		}
		return outputObject;
	}
	
	
	
	
	/**
	 * 根据外链图片URL，返回微信服务器URL地址，入参为多个图片地址，回参也是多个图片地址
	 * @param remoteUrl
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getWxUrls")
	public OutputObject getWxUrls(@ModelAttribute("wXMaterialForm") WXArticleMaterialForm wXMaterialForm) {
		boolean updSuccFlag = true;
		OutputObject outputObject = new OutputObject();
		//微信公众号appId和secret
		String appId = PropertiesUtil.getString("appId");
		String secret = PropertiesUtil.getString("secret");
		ApiConfig config = new ApiConfig(appId, secret);
		List<Map<String, Object>> imgs = wXMaterialForm.getImgs();
		if(CollectionUtils.isNotEmpty(imgs)){
			List<Map<String, Object>> wxUrls = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> urlObj : imgs) {
				String picSavePath = PropertiesUtil.getString("picture_path") + "wxpic/tmp/" + DateUtil.getCurrYMDHMSS() + ".jpg";
				//下载img的src外链的图片到服务器硬盘
				boolean flag = FileDownLoadUtil.downLoadFile(urlObj.get("img").toString(), picSavePath);
				if(flag){
					//下载成功后，上传本地图片到微信服务器，替换图文内容的图片地址
					MediaAPI mediaAPI = new MediaAPI(config);
					UploadImgResponse response = mediaAPI.uploadImg(new File(picSavePath));
					String wxUrl = response.getUrl();
					if(StringUtils.isNotEmpty(wxUrl)){
						Map<String, Object> wxMap = new HashMap<String, Object>();
						wxMap.put(urlObj.get("img").toString(), wxUrl);
						wxUrls.add(wxMap);
					}else{
						updSuccFlag = false;
					}
				}else{
					updSuccFlag = false;
				}
				if(CollectionUtils.isNotEmpty(wxUrls)){
					outputObject.setBeans(wxUrls);
				}
			}
		}else{
			updSuccFlag = false;
		}
		if(updSuccFlag){
			outputObject.setReturnCode("0");
			outputObject.setReturnMessage("图片上传成功.");
		}else{
			outputObject.setReturnCode("-9999");
			outputObject.setReturnMessage("图片上传失败.");
		}
		return outputObject;
	}
	
	
	
	/**
	 * 跳转到微信图文素材列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/article/article-list");
		return mv;
	}
	
	
	/**
	 * 分页查询微信图文素材列表
	 * 入参必须有 pageNumber(页码),limit(每页数量)
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
		outputObject = getOutputObject(map, "wXArticleMaterialService", "getList");
		return outputObject;
	}
	
	
	/**
	 * 根据ID查询微信图文素材表
	 * 入参必须有 materialId(图文素材ID)
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
		outputObject = getOutputObject(map,"wXArticleMaterialService","getById");
		return outputObject;
	}
	
	
	/**
	 * 查看所有微信图文素材
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
		outputObject = getOutputObject(map,"wXArticleMaterialService","getAll");
		return outputObject;
	}
	
	
	/**
	 * 添加微信图文素材
	 * 入参必须是WXArticleMaterialForm list json格式的数据集
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
		map.put("appId", PropertiesUtil.getString("appId"));
		map.put("secret", PropertiesUtil.getString("secret"));
		outputObject = getOutputObject(map,"wXArticleMaterialService","insertWXMaterial");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("图文素材添加成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 修改微信图文素材
	 * @param WXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXMaterial")
	public OutputObject updateWXMaterial(@RequestBody List<WXMaterialForm> wXMaterialForms,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		List<Map<String, Object>> wxForms = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", PropertiesUtil.getString("appId"));
		map.put("secret", PropertiesUtil.getString("secret"));
		if(CollectionUtils.isNotEmpty(wXMaterialForms)){
			for (WXMaterialForm wxForm : wXMaterialForms) {
				Map<String, Object> wxfMap = BeanUtil.convertBean2Map(wxForm);
				wxfMap.put("article", wxForm.getArticle());
				wxForms.add(wxfMap);
			}
		}
		map.put("updatls", wxForms);
		outputObject = getOutputObject(map, "wXArticleMaterialService", "updateWXMaterial");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("图文素材编辑成功!");
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
		mav.setViewName("weixin/article/article-add");
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
		mav.setViewName("weixin/article/article-edit");
		return mav;
	}
	
	
	/**
	 * 删除微信素材表
	 * 入参必须有 mediaId(微信mediaId),materialId(素材ID)
	 * @param wXMaterialForm
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
		map.put("appId", PropertiesUtil.getString("appId"));
		map.put("secret", PropertiesUtil.getString("secret"));
		outputObject = getOutputObject(map, "wXArticleMaterialService", "deleteWXMaterial");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("图文素材删除成功!");
		}
		return outputObject;
	}
	
	
	
	/**
	 * 同步微信图文素材
	 * @param wXMaterialForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "syncNews")
	public OutputObject syncNews(@ModelAttribute("WXMaterialForm") WXMaterialForm wXMaterialForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMaterialForm);
		map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
		map.put("appId", PropertiesUtil.getString("appId"));
		map.put("secret", PropertiesUtil.getString("secret"));
		outputObject = getOutputObject(map, "wXArticleMaterialService", "updateSyncNews");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("图文素材同步成功!");
		}
		return outputObject;
	}
	
	
	
	
}
