package com.lfc.wxadminweb.modules.weixin.web;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
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
import com.lfc.wxadminweb.common.upload.UploadImg;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXNewsItemsForm;
import com.lfc.wxadminweb.modules.weixin.form.WXNewsTemplatesForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author jzp
 * @date 2016-10-11 15:09
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/newstemplates")
public class WXNewsTemplatesController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;
	
	
	/**
	 * 跳转到微信菜单列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( HttpServletRequest request,Model model) {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("weixin/newstemplates-list");
		return mv;
	}
	/**
	 * 分页查询微信菜单列表
	 * @param WXNewsTemplatesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXNewsTemplatesForm") WXNewsTemplatesForm wXNewsTemplatesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsTemplatesForm);
		map.put("wkNumberIsNull", "T");
		outputObject = getOutputObject(map, "wXNewsTemplatesService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询微信菜单
	 * @param WXNewsTemplatesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXNewsTemplatesForm") WXNewsTemplatesForm wXNewsTemplatesForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsTemplatesForm);	
		outputObject = getOutputObject(map,"wXNewsTemplatesService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信图文详情
	 * @param "WXNewsTemplatesForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXNewsTemplatesForm") WXNewsTemplatesForm wXNewsTemplatesForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsTemplatesForm);	
		map.put("wkNumberIsNull", "T");
		outputObject = getOutputObject(map,"wXNewsTemplatesService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信图文详情
	 * @param WXNewsTemplatesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXNewsTemplates")
	public OutputObject insertWXNewsTemplates(@ModelAttribute("WXNewsTemplatesForm") @Valid WXNewsTemplatesForm wXNewsTemplatesForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsTemplatesForm);
			System.out.println("---------wXNewsTemplatesForm--------"+wXNewsTemplatesForm.getType());
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXNewsTemplatesService", "insertWXNewsTemplates");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信图文详情添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信图文详情
	 * @param WXNewsTemplatesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXNewsTemplates")
	public OutputObject updateWXNewsTemplates(@ModelAttribute("WXNewsTemplatesForm") @Valid WXNewsTemplatesForm wXNewsTemplatesForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsTemplatesForm);
		outputObject = getOutputObject(map, "wXNewsTemplatesService", "updateWXNewsTemplates");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信图文详情编辑成功!");
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
		mav.setViewName("weixin/add-newstemplates");
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
		outputObject = getOutputObject(map,"wXNewsTemplatesService","selectByPrimaryKey");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-newstemplates");
		return mav;
	}
	/**
	 * 删除微信图文详情
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXNewsTemplates")
	public OutputObject deleteWXNewsTemplates(@ModelAttribute("WXNewsTemplatesForm") WXNewsTemplatesForm wXNewsTemplatesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = null;
		//查看模板下是否有图文信息
		WXNewsItemsForm wXNewsItemsForm =new WXNewsItemsForm();
		wXNewsItemsForm.setNewsTempId(wXNewsTemplatesForm.getNewsTempId());
		map = BeanUtil.convertBean2Map(wXNewsItemsForm);
		OutputObject outputObject1 = new OutputObject();
		outputObject1 = getOutputObject(map, "wXNewsItemsService", "getAll");
		map.clear();
		map = BeanUtil.convertBean2Map(wXNewsTemplatesForm);
		//删除该模板下的图文信息
		if(!(outputObject1.getBeans().isEmpty())){
			getOutputObject(map, "wXNewsTemplatesService", "deleteWXNewsItems");
		}
		//删除模板
		outputObject = getOutputObject(map, "wXNewsTemplatesService", "deleteWXNewsTemplates");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信图文详情删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信图文详情
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXNewsTemplates")
	public OutputObject logicDeleteWXNewsTemplates(@ModelAttribute("WXNewsTemplatesForm") WXNewsTemplatesForm wXNewsTemplatesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsTemplatesForm);
		outputObject = getOutputObject(map, "wXNewsTemplatesService", "logicDeleteWXNewsTemplates");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	/**
	 * 上传图片
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
		outputObject.setBean(map);
		return outputObject;
	}
	
	/**
	 * 去往图文新增页面
	 * @return
	 */
	@RequestMapping(value = "newsItemAdd")
	public ModelAndView newsItemAdd(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		model.addAttribute("newsTempId", request.getParameter("newsTempId"));
		model.addAttribute("htmlTittle", "菜单内容");
		model.addAttribute("htmlHostTittle", "菜单内容");
		mav.setViewName("weixin/newsitemadd");
		return mav;
	}
	/**
	 * 去往图文详情编辑显示页面
	 * @return
	 */
	@RequestMapping(value = "newsItemEdit")
	public ModelAndView newsItemEdit(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		model.addAttribute("newsTempId", request.getParameter("newsTempId"));
		String htmlTittle = request.getParameter("htmlTittle");
		System.out.println("----------------------------------------"+request.getParameter("htmlHostTittle"));
		if(null == htmlTittle || "".equals(htmlTittle.trim())){
			model.addAttribute("htmlTittle", "菜单内容");
			model.addAttribute("htmlHostTittle", "菜单内容");
		}else{
			model.addAttribute("htmlTittle", request.getParameter("htmlTittle"));
			model.addAttribute("htmlHostTittle", request.getParameter("htmlHostTittle"));
		}
		
		mav.setViewName("weixin/newsitems-list");
		return mav;
	}
	
	/**
	 * 添加微信图文详情
	 * @param WXNewsItemsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXNewsItems")
	public OutputObject insertWXNewsItems(@ModelAttribute("WXNewsItemsForm") @Valid WXNewsItemsForm wXNewsItemsForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			String createUser = (String)((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName");
			wXNewsItemsForm.setCreateUser(createUser);
			Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsItemsForm);
			outputObject = getOutputObject(map, "wXNewsItemsService", "insertWXNewsItems");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信图文详情添加成功!");
			}
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
				String savePath = realPath + fileName;// 文件保存全路径
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
