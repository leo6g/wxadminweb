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
import com.lfc.wxadminweb.modules.biz.form.WXLoanProdInfoForm;




/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2016-10-19 09:11
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/loanprodinfo")
public class WXLoanProdInfoController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;
	
	
	/**
	 * 跳转到微信贷款信息列表页面
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
		mv.setViewName("biz/finance/loanprodinfo-list");
		return mv;
	}
	/**
	 * 分页查询微信贷款信息列表
	 * @param WXLoanProdInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXLoanProdInfoForm") WXLoanProdInfoForm wXLoanProdInfoForm,
			BindingResult result,HttpServletRequest request,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		String roleIds = getSession().getAttribute(Constants.USER_SESSION.ROLEIDS).toString();
		if(roleIds !=null && !"".equals(roleIds)){
			//一级审核员
			if(roleIds.contains("485d5fca4f474ead88b2ac77832963f6")){
				wXLoanProdInfoForm.setRole("business");
			}
			//二级审核员
			if(roleIds.contains("40e993400df54e3b87472c27e578ea9f")){
				wXLoanProdInfoForm.setRole("infor");
			}
			//三级审核员
			if(roleIds.contains("4c03466ca0e440ef81444a189dfca3d9")){
				wXLoanProdInfoForm.setRole("cardcheck");
			}
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXLoanProdInfoForm);
		outputObject = getOutputObject(map, "wXLoanProdInfoService", "getList");
		List<Map<String,Object>> loanprodinfos=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> loanprodinfo:loanprodinfos){
			loanprodinfo.put("imagePath", contextPath+"/viewImage/viewImage?imgPath="+loanprodinfo.get("imagePath"));
			
		}
		return outputObject;
	}
	/**
	 *根据ID查询微信贷款信息
	 * @param WXLoanProdInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXLoanProdInfoForm") WXLoanProdInfoForm wXLoanProdInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXLoanProdInfoForm);	
		outputObject = getOutputObject(map,"wXLoanProdInfoService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信贷款信息
	 * @param "WXLoanProdInfoForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXLoanProdInfoForm") WXLoanProdInfoForm wXLoanProdInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXLoanProdInfoForm);	
		outputObject = getOutputObject(map,"wXLoanProdInfoService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信贷款信息
	 * @param WXLoanProdInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXLoanProdInfo")
	public OutputObject insertWXLoanProdInfo(@ModelAttribute("WXLoanProdInfoForm") @Valid WXLoanProdInfoForm wXLoanProdInfoForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXLoanProdInfoForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXLoanProdInfoService", "insertWXLoanProdInfo");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信贷款信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信贷款信息
	 * @param WXLoanProdInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXLoanProdInfo")
	public OutputObject updateWXLoanProdInfo(@ModelAttribute("WXLoanProdInfoForm") @Valid WXLoanProdInfoForm wXLoanProdInfoForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXLoanProdInfoForm);
		outputObject = getOutputObject(map, "wXLoanProdInfoService", "updateWXLoanProdInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信贷款信息编辑成功!");
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
		mav.setViewName("biz/finance/add-loanprodinfo");
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
		outputObject = getOutputObject(map,"wXLoanProdInfoService","getById");
		List<Map<String,Object>> loanprodinfos=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> loanprodinfo:loanprodinfos){
			loanprodinfo.put("imagePath", contextPath+"/viewImage/viewImage?imgPath="+loanprodinfo.get("imagePath"));
		}
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/finance/edit-loanprodinfo");
		return mav;
	}
	/**
	 * 删除微信贷款信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXLoanProdInfo")
	public OutputObject deleteWXLoanProdInfo(@ModelAttribute("WXLoanProdInfoForm") WXLoanProdInfoForm wXLoanProdInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXLoanProdInfoForm);
		outputObject = getOutputObject(map, "wXLoanProdInfoService", "deleteWXLoanProdInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信贷款信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信贷款信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXLoanProdInfo")
	public OutputObject logicDeleteWXLoanProdInfo(@ModelAttribute("WXLoanProdInfoForm") WXLoanProdInfoForm wXLoanProdInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXLoanProdInfoForm);
		outputObject = getOutputObject(map, "wXLoanProdInfoService", "logicDeleteWXLoanProdInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 跳转到贷款信息详情列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "loanPView")
	public ModelAndView cardView( HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		model.addAttribute("prodId", request.getParameter("prodId"));
		model.addAttribute("role", request.getParameter("role"));
		model.addAttribute("authState", request.getParameter("authState"));
		mav.setViewName("biz/finance/view-loanprodinfo");
		return mav;
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
