package com.lfc.wxadminweb.modules.biz.web;


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
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.WXCardInfoForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author GY
 * @date 2016-10-17 10:15
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/cardinfo")
public class WXCardInfoController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;
	
	
	/**
	 * 跳转到信用卡信息发布表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request,Model model) {
		ModelAndView mv=new ModelAndView();
		
		String roleIds = getSession().getAttribute(Constants.USER_SESSION.ROLEIDS).toString();
		if(roleIds !=null && !"".equals(roleIds)){
			//一级审核员
			if(roleIds.contains("485d5fca4f474ead88b2ac77832963f6")){
				model.addAttribute("role", "business");
			}
			//二级审核员
			if(roleIds.contains("40e993400df54e3b87472c27e578ea9f")){
				model.addAttribute("role", "infor");
			}
			//三级审核员
			if(roleIds.contains("4c03466ca0e440ef81444a189dfca3d9")){
				model.addAttribute("role", "cardcheck");
			}
		}
		mv.setViewName("biz/finance/cardinfo-list");
		return mv;
	}
	/**
	 * 分页查询信用卡信息发布表列表
	 * @param WXCardInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXCardInfoForm") WXCardInfoForm wXCardInfoForm,
			BindingResult result,HttpServletRequest request,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		String roleIds = getSession().getAttribute(Constants.USER_SESSION.ROLEIDS).toString();
		if(roleIds !=null && !"".equals(roleIds)){
			//一级审核员
			if(roleIds.contains("485d5fca4f474ead88b2ac77832963f6")){
				wXCardInfoForm.setRole("business");
			}
			//二级审核员
			if(roleIds.contains("40e993400df54e3b87472c27e578ea9f")){
				wXCardInfoForm.setRole("infor");
			}
			//三级审核员
			if(roleIds.contains("4c03466ca0e440ef81444a189dfca3d9")){
				wXCardInfoForm.setRole("cardcheck");
			}
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXCardInfoForm);
		outputObject = getOutputObject(map, "wXCardInfoService", "getList");
		List<Map<String,Object>> cardinfos=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> cardinfo:cardinfos){
			cardinfo.put("imagePath", contextPath+"/viewImage/viewImage?imgPath="+"card/"+cardinfo.get("imagePath"));
		}
		return outputObject;
	}
	/**
	 *根据ID查询信用卡信息发布表
	 * @param WXCardInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXCardInfoForm") WXCardInfoForm wXCardInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXCardInfoForm);	
		outputObject = getOutputObject(map,"wXCardInfoService","getById");
		return outputObject;
	}
	/**
	 * 查看所有信用卡信息发布表
	 * @param "WXCardInfoForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXCardInfoForm") WXCardInfoForm wXCardInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXCardInfoForm);	
		outputObject = getOutputObject(map,"wXCardInfoService","getAll");
		return outputObject;
	}
	/**
	 * 添加信用卡信息到发布表
	 * @param WXCardInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "insertWXCardInfo")
	public OutputObject insertWXCardInfo(@ModelAttribute("WXCardInfoForm") @Valid WXCardInfoForm wXCardInfoForm,BindingResult result, Model model,ModelMap mm,HttpServletRequest request) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXCardInfoForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			/*map.put("comments", request.getParameter("comments"));*/
			map.put("rights", request.getParameter("rights"));
			outputObject = getOutputObject(map, "wXCardInfoService", "insertWXCardInfo");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("信用卡信息发布表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑信用卡信息发布表
	 * @param WXCardInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXCardInfo")
	public OutputObject updateWXCardInfo(@ModelAttribute("WXCardInfoForm") @Valid WXCardInfoForm wXCardInfoForm,BindingResult result, Model model,ModelMap mm,HttpServletRequest request) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXCardInfoForm);
		/*map.put("comments", request.getParameter("comments"));*/
		map.put("rights", request.getParameter("rights"));
		outputObject = getOutputObject(map, "wXCardInfoService", "updateWXCardInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("信用卡信息发布表编辑成功!");
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
		mav.setViewName("biz/finance/add-cardinfo");
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
		outputObject = getOutputObject(map,"wXCardInfoService","getById");
		List<Map<String,Object>> cardinfos=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> cardinfo:cardinfos){
			cardinfo.put("imagePath", contextPath+"/viewImage/viewImage?imgPath="+"card/"+cardinfo.get("imagePath"));
		}
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/finance/edit-cardinfo");
		return mav;
	}
	/**
	 * 删除信用卡信息发布表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXCardInfo")
	public OutputObject deleteWXCardInfo(@ModelAttribute("WXCardInfoForm") WXCardInfoForm wXCardInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXCardInfoForm);
		outputObject = getOutputObject(map, "wXCardInfoService", "deleteWXCardInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("信用卡信息发布表删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除信用卡信息发布表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXCardInfo")
	public OutputObject logicDeleteWXCardInfo(@ModelAttribute("WXCardInfoForm") WXCardInfoForm wXCardInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXCardInfoForm);
		outputObject = getOutputObject(map, "wXCardInfoService", "logicDeleteWXCardInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 跳转到信用卡信息详情列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "cardView")
	public ModelAndView cardView( HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		model.addAttribute("cardId", request.getParameter("cardId"));
		model.addAttribute("role", request.getParameter("role"));
		model.addAttribute("authState", request.getParameter("authState"));
		mav.setViewName("biz/finance/view-cardinfo");
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
		uploadFile=upload(uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("imagePath", uploadFile.getTitleField());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
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
			String realPath =path+"card/";// 文件的硬盘真实路径
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
