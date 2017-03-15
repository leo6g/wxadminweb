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

import org.apache.commons.lang3.StringUtils;
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

import com.github.sd4324530.fastweixin.api.CustomAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.message.Article;
import com.github.sd4324530.fastweixin.message.NewsMsg;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weixin.form.MessageForm;

/**
 * @author ZHAOYUAN
 * 
 */
@Controller
@RequestMapping("/wx/message")
public class MessageManagerController extends BaseController {
	@Value("#{system.picture_path}")
	private String path;
	
	@Value("#{system.appId}")
	private String appId;

	@Value("#{system.secret}")
	private String secret;
	
	/**
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(ModelAndView mv) {
		mv.setViewName("weixin/message-list");
		return mv;
	}

	/**
	 * @param messageForm
	 * @param result
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("messageForm") MessageForm messageForm, BindingResult result) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(messageForm);
		outputObject = getOutputObject(map, "messageService", "getList");
		return outputObject;
	}

	/**
	 * @param messageForm
	 * @param result
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("messageForm") MessageForm messageForm, BindingResult result,
			Model model, ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(messageForm);
		outputObject = getOutputObject(map, "messageService", "getById");
		return outputObject;
	}

	/**
	 * @param messageForm
	 * @param result
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertMessage")
	public OutputObject insertUserMessage(@ModelAttribute("messageForm") @Valid MessageForm messageForm,
			BindingResult result, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(messageForm);
		outputObject = getOutputObject(map, "messageService", "getSortIndexCount");
		//排序不能重复
		if(!outputObject.getBean().get("sortCount").toString().equals("0")) {
			outputObject.setReturnCode("-1");
			outputObject.setReturnMessage("排序不能重复");
			return outputObject;
		}
		outputObject = getOutputObject(map, "messageService", "insertMessage");
		if (outputObject.getReturnCode().equals("0")) {
			outputObject.setReturnMessage("微信客服消息表添加成功!");
		}
		return outputObject;
	}

	/**
	 * @param messageForm
	 * @param result
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateMessage")
	public OutputObject updateUserMessage(@ModelAttribute("messageForm") @Valid MessageForm messageForm,
			BindingResult result, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(messageForm);
		//排序不能重复
		outputObject = getOutputObject(map, "messageService", "getSortIndexCount");
		if(!outputObject.getBean().get("sortCount").toString().equals("0")) {
			outputObject.setReturnCode("-1");
			outputObject.setReturnMessage("排序不能重复");
			return outputObject;
		}
		outputObject = getOutputObject(map, "messageService", "updateMessage");
		if (outputObject.getReturnCode().equals("0")) {
			outputObject.setReturnMessage("微信客服消息表编辑成功!");
		}
		return outputObject;
	}

	/**
	 * 去往添加页面
	 * 
	 * @return
	 */
	/**
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("weixin/add-message");
		return mav;
	}

	/**
	 * 去往更新页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "edit")
	@ResponseBody
	public OutputObject edit(Model model) {
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		map.put("id", request.getParameter("id"));
		outputObject = getOutputObject(map, "messageService", "getById");
		return outputObject;
	}

	/**
	 * @param messageForm
	 * @param result
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteMessage")
	public OutputObject deleteUserMessage(@ModelAttribute("messageForm") MessageForm messageForm, BindingResult result,
			Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(messageForm);
		outputObject = getOutputObject(map, "messageService", "deleteMessage");
		if (outputObject.getReturnCode().equals("0")) {
			outputObject.setReturnMessage("微信客服消息表删除成功!");
		} else {
			outputObject.setReturnMessage("微信客服消息表删除失败!");
		}
		return outputObject;
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param mm
	 */
	@ResponseBody
	@RequestMapping(value = "uploadImage")
	public OutputObject uploadImage(HttpServletRequest request, HttpServletResponse response, Model model,
			ModelMap mm) {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile = uploadImg(request, uploadFile, path);
		Map<String, Object> map = new HashMap<String, Object>();
		OutputObject outputObject = new OutputObject();
		map.put("realPath", uploadFile.getRealPath());// 上传文件的本地路径
		outputObject.setReturnCode("0");
		outputObject.setReturnMessage("图片上传成功!");
		outputObject.setBean(map);
		return outputObject;
	}

	// 上传图片
	private static UploadFile uploadImg(HttpServletRequest request, UploadFile uploadFile, String path) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath = path + "material/";// 文件的硬盘真实路径
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
				String imageName = request.getParameter("imageName");
				if (StringUtils.isNotEmpty(imageName)) {
					fileName = imageName + extend;
				} else {
					fileName = s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23)
							+ s.substring(24) + extend;// 获取文件名
				}
				String savePath = realPath + fileName;// 文件保存全路径
				File savefile = new File(savePath);
				// 设置文件数据库的物理路径
				String contextPath = request.getContextPath();
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ contextPath;
				String showImgPath = basePath + "/viewImage/viewImage?imgPath=material/" + fileName;
				uploadFile.setRealPath(showImgPath);
				// 第一步：上传到本地
				FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			uploadFile.setErrorMessage("调用服务器异常");
		}
		return uploadFile;
	}
	
	/**
	 * 预览
	 * @return
	 */
	@RequestMapping(value = "preview")
	@ResponseBody
	public OutputObject preview() {
		OutputObject outputObject = getOutputObject(new HashMap<String, Object>(), "messageService", "getPreviewArt");
		return outputObject;
	}
	
	/**
	 * 群发消息
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "sendAll")
	public OutputObject sendAll(HttpServletRequest request, HttpServletResponse response) {
		OutputObject outputObject = getOutputObject(new HashMap<String, Object>(), "messageService", "getSendList");
		//用户列表
		List<Map<String, Object>> userList = (List<Map<String, Object>>) outputObject.getObject();
		//信息发送列表
		List<Map<String, Object>> sendList = outputObject.getBeans();
		List<Article> articles = new ArrayList<Article>();
		for (Map<String, Object> map : sendList) {
			Article article = new Article(map.get("title").toString(), map.get("digest").toString(), map.get("coverPic").toString(), map.get("url").toString());
			articles.add(article);
		}
		CustomAPI api = new CustomAPI(new ApiConfig(appId, secret));
		NewsMsg newMsg = new NewsMsg();
		newMsg.setArticles(articles);
		for (Map<String, Object> map : userList) {
			try {
				api.sendCustomMessage(map.get("openId").toString(), newMsg);
			} catch (Exception e) {
				logger.info(e.getMessage());
				continue;
			}
		}
		outputObject.setReturnCode("0");
		outputObject.setReturnMessage("消息发送成功");
		return outputObject;
	}
}
