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
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.NetPointForm;



/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2016-10-24 10:23
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/netpoint")
public class NetPointController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;
	
	
	
	/**
	 * 跳转到网点信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/common/netpoint-list");
		return mv;
	}
	/**
	 * 分页查询网点信息列表
	 * @param NetPointForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("netPointForm") NetPointForm netPointForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(netPointForm);
		
		outputObject = getOutputObject(map, "netPointService", "getList");
		List<Map<String,Object>> netPoints=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> netPoint:netPoints){
			netPoint.put("imagePath", contextPath+"/viewImage/viewImage?imgPath="+netPoint.get("imagePath"));
			System.out.println(netPoint.get("imagePath"));
		}
		return outputObject;
	}
	/**
	 *根据ID查询网点信息
	 * @param NetPointForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("NetPointForm") NetPointForm netPointForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(netPointForm);	
		outputObject = getOutputObject(map,"netPointService","getById");
		return outputObject;
	}
	/**
	 * 查看所有网点信息
	 * @param "NetPointForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("NetPointForm") NetPointForm netPointForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(netPointForm);
		map.put("orderByClause", "AGENCY_SORT");
		outputObject = getOutputObject(map,"netPointService","getAll");
		return outputObject;
	}
	/**
	 * 添加网点信息
	 * @param NetPointForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertNetPoint")
	public OutputObject insertNetPoint(@ModelAttribute("NetPointForm") @Valid NetPointForm netPointForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(netPointForm);
			
			outputObject = getOutputObject(map, "netPointService", "insertNetPoint");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("网点信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑网点信息
	 * @param NetPointForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateNetPoint")
	public OutputObject updateNetPoint(@ModelAttribute("NetPointForm") @Valid NetPointForm netPointForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(netPointForm);
		outputObject = getOutputObject(map, "netPointService", "updateNetPoint");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("网点信息编辑成功!");
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
		mav.setViewName("biz/common/add-netpoint");
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
		outputObject = getOutputObject(map,"netPointService","getById");
		List<Map<String,Object>> netPoints=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> netPoint:netPoints){
			netPoint.put("imagePath", contextPath+"/viewImage/viewImage?imgPath="+netPoint.get("imagePath"));
			System.out.println(netPoint.get("imagePath"));
		}
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/common/edit-netpoint");
		return mav;
	}
	/**
	 * 删除网点信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteNetPoint")
	public OutputObject deleteNetPoint(@ModelAttribute("NetPointForm") NetPointForm netPointForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(netPointForm);
		outputObject = getOutputObject(map, "netPointService", "deleteNetPoint");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("网点信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除网点信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteNetPoint")
	public OutputObject logicDeleteNetPoint(@ModelAttribute("NetPointForm") NetPointForm netPointForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(netPointForm);
		outputObject = getOutputObject(map, "netPointService", "logicDeleteNetPoint");
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
				System.out.println("----------------------"+savePath);
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
	
	
	@ResponseBody
	@RequestMapping(value = "getOrgInfo")
	public OutputObject getOrgInfo(String type,String parentId) {
		OutputObject outputObject = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(type.equals("city")){
			map.put("orgLvl",3);
		}else if(type.equals("town")){
			map.put("orgLvl",4);
			map.put("pagencyId", parentId);
		}else if(type.equals("country")){
			map.put("orgLvl",5);
			map.put("pagencyId", parentId);
		}
		map.put("orderByClause", "AGENCY_SORT");
		outputObject = getOutputObject(map,"netPointService","getAll");
		return outputObject;
	}
	
	@ResponseBody
	@RequestMapping(value = "netGetOrgInfo")
	public OutputObject netGetOrgInfo(String type,String city) {
		OutputObject outputObject = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(type.equals("city")){
			map.put("orgLvl",3);
		}else if(type.equals("town")){
			map.put("orgLvl",4);
			map.put("city", city);
		}else if(type.equals("country")){
			map.put("orgLvl",5);
			map.put("city", city);
		}
		map.put("orderByClause", "AGENCY_SORT");
		outputObject = getOutputObject(map,"netPointService","getAll");
		return outputObject;
	}
	
	
}
