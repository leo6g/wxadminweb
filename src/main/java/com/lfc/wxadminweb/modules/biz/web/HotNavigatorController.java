package com.lfc.wxadminweb.modules.biz.web;


import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
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


import com.lfc.wxadminweb.modules.biz.form.HotNavigatorForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2017-02-21 15:35
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/hotnavigator")
public class HotNavigatorController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;
	
	
	/**
	 * 跳转到热点导航列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/hotnavigator/tnavigator-list");
		return mv;
	}
	/**
	 * 分页查询热点导航列表
	 * @param HotNavigatorForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("hotNavigatorForm") HotNavigatorForm hotNavigatorForm,
			BindingResult result,HttpServletRequest request,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(hotNavigatorForm);
		map.put("orderByClause", "t.CREATE_TIME desc");
		outputObject = getOutputObject(map, "hotNavigatorService", "getList");
		List<Map<String,Object>> hotNavs=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> hotNav:hotNavs){
			hotNav.put("selfImgPath", contextPath+"/viewImage/viewImage?imgPath=hotPointNav/"+hotNav.get("selfImgPath"));
			hotNav.put("localUrl", contextPath+"/viewImage/viewImage?imgPath=hotPointNav/"+hotNav.get("localUrl"));
//			hotNav.put("createTime", hotNav.get("createTime")).toString().substring(0,10);
	}
		return outputObject;
	}
	/**
	 *根据ID查询热点导航
	 * @param HotNavigatorForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("HotNavigatorForm") HotNavigatorForm hotNavigatorForm,
			BindingResult result,HttpServletRequest request, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(hotNavigatorForm);	
		outputObject = getOutputObject(map,"hotNavigatorService","getById");
		Map<String,Object> hotNav=(Map<String, Object>) outputObject.getObject();
		String contextPath = request.getContextPath();		
			if(hotNav.get("selfImgPath")==null){
				hotNav.put("showPath", contextPath+"/viewImage/viewImage?imgPath=defaultf29b7fcd44314b7b7540e45d5f37a.jpg");
				
			}else{
				hotNav.put("showPath", contextPath+"/viewImage/viewImage?imgPath=hotPointNav/"+hotNav.get("selfImgPath"));
				
			}
		return outputObject;
	}
	/**
	 * 查看所有热点导航
	 * @param "HotNavigatorForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("HotNavigatorForm") HotNavigatorForm hotNavigatorForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(hotNavigatorForm);	
		outputObject = getOutputObject(map,"hotNavigatorService","getAll");
		return outputObject;
	}
	/**
	 * 添加热点导航
	 * @param HotNavigatorForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertHotNavigator")
	public OutputObject insertHotNavigator(@ModelAttribute("HotNavigatorForm") @Valid HotNavigatorForm hotNavigatorForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(hotNavigatorForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "hotNavigatorService", "insertHotNavigator");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("热点导航添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑热点导航
	 * @param HotNavigatorForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateHotNavigator")
	public OutputObject updateHotNavigator(@ModelAttribute("HotNavigatorForm") @Valid HotNavigatorForm hotNavigatorForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(hotNavigatorForm);
		outputObject = getOutputObject(map, "hotNavigatorService", "updateHotNavigator");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("热点导航编辑成功!");
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
		mav.setViewName("weixin/add-tnavigator");
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
		outputObject = getOutputObject(map,"hotNavigatorService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-tnavigator");
		return mav;
	}
	/**
	 * 删除热点导航
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteHotNavigator")
	public OutputObject deleteHotNavigator(@ModelAttribute("HotNavigatorForm") HotNavigatorForm hotNavigatorForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(hotNavigatorForm);
		outputObject = getOutputObject(map,"hotNavigatorService","getById");
		Map<String, Object>  rtMap=  (Map<String, Object>) outputObject.getObject();
		String imgName = (String) rtMap.get("selfImgPath");
		File file = new File(path+"hotPointNav"+File.separator+imgName);
		System.out.println(file+"这是删除图片路径++++++");
		file.delete();
		outputObject = getOutputObject(map, "hotNavigatorService", "deleteHotNavigator");
		
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("热点导航删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除热点导航
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteHotNavigator")
	public OutputObject logicDeleteHotNavigator(@ModelAttribute("HotNavigatorForm") HotNavigatorForm hotNavigatorForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(hotNavigatorForm);
		outputObject = getOutputObject(map, "hotNavigatorService", "logicDeleteHotNavigator");
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
			String realPath =path+"hotPointNav/";// 文件的硬盘真实路径
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
					String showImgPath=contextPath+"/viewImage/viewImage?imgPath=hotPointNav/"+fileName;
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
