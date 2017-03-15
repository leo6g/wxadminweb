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
import com.lfc.wxadminweb.modules.biz.form.CardApplierForm;
import com.lfc.wxadminweb.modules.biz.form.FinanceProdForm;
import com.lfc.wxadminweb.modules.weixin.form.WXNewsItemsForm;


/**
 * 
 * @descript 
 * @author jzp
 * @date 2016-10-20 11:37
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/loan")
public class LoanProdController extends BaseController{
	
	@Value("#{system.picture_path}") 
	private  String path;
	
	/**
	 * 跳转到贷款产品管理列表页面
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
		mv.setViewName("biz/loanprod/loanprod-list");
		return mv;
	}
	
	
	/**
	 * 分页查询贷款产品管理列表
	 * @param FinanceProdForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("financeProdForm") FinanceProdForm financeProdForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		String roleIds = getSession().getAttribute(Constants.USER_SESSION.ROLEIDS).toString();
		if(roleIds !=null && !"".equals(roleIds)){
			//一级审核员
			if(roleIds.contains("485d5fca4f474ead88b2ac77832963f6")){
				financeProdForm.setRole("business");
			}
			//二级审核员
			if(roleIds.contains("40e993400df54e3b87472c27e578ea9f")){
				financeProdForm.setRole("infor");
			}
			//三级审核员
			if(roleIds.contains("4c03466ca0e440ef81444a189dfca3d9")){
				financeProdForm.setRole("cardcheck");
			}
		}
		//设置查询贷款
		financeProdForm.setCategory("loan");
		Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);
		outputObject = getOutputObject(map, "financeProdService", "getList");
		return outputObject;
	}
	
	
	/**
	 *根据ID查询贷款产品管理
	 * @param FinanceProdForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("financeProdForm") FinanceProdForm financeProdForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);	
		outputObject = getOutputObject(map,"financeProdService","getById");
		return outputObject;
	}
	
	
	/**
	 * 查看所有贷款产品管理
	 * @param "FinanceProdForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("financeProdForm") FinanceProdForm financeProdForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		//设置查询贷款
		financeProdForm.setCategory("loan");
		Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);	
		outputObject = getOutputObject(map,"financeProdService","getAll");
		return outputObject;
	}
	
	
	/**
	 * 添加贷款产品管理
	 * @param FinanceProdForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertFinanceProd")
	public OutputObject insertFinanceProd(@ModelAttribute("financeProdForm") @Valid FinanceProdForm financeProdForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);
			
			WXNewsItemsForm itemsForm = (WXNewsItemsForm)map.get("itemForm");
			Map<String, Object> itemFormMap = BeanUtil.convertBean2Map(itemsForm);
			map.put("itemFormMap", itemFormMap);
			
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "financeProdService", "insertFinanceProd");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("添加成功!");
			}
			return outputObject;
	}
	
	
	/**
	 * 编辑贷款产品管理
	 * @param FinanceProdForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateFinanceProd")
	public OutputObject updateFinanceProd(@ModelAttribute("financeProdForm") @Valid FinanceProdForm financeProdForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);
		
		WXNewsItemsForm itemsForm = (WXNewsItemsForm)map.get("itemForm");
		Map<String, Object> itemFormMap = BeanUtil.convertBean2Map(itemsForm);
		map.put("itemFormMap", itemFormMap);
		
		outputObject = getOutputObject(map, "financeProdService", "updateFinanceProd");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("修改成功!");
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
		mav.setViewName("biz/loanprod/add-loanprod");
		return mav;
	}
	
	
	/**
	 * 去往查看页面
	 * @return
	 */
	@RequestMapping(value = "view")
	public ModelAndView view(Model model) {
		ModelAndView mav=new ModelAndView();
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		map.put("fincId", request.getParameter("fincId"));
		outputObject = getOutputObject(map,"financeProdService","getById");
		
		Map<String,Object> finance=(Map<String,Object>)outputObject.getObject();
		String contextPath = request.getContextPath();
		finance.put("imagePath",contextPath+"/viewImage/viewImage?imgPath="+finance.get("imagePath"));
		
		model.addAttribute("obj", finance);
		model.addAttribute("role", request.getParameter("role"));
		mav.setViewName("biz/loanprod/view-loanprod");
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
		map.put("fincId", request.getParameter("fincId"));
		outputObject = getOutputObject(map,"financeProdService","getById");
		
		Map<String,Object> finance=(Map<String,Object>)outputObject.getObject();
		String contextPath = request.getContextPath();
		finance.put("imagePath",contextPath+"/viewImage/viewImage?imgPath="+"loan/"+finance.get("imagePath"));
		
		model.addAttribute("obj", outputObject.getObject());
		model.addAttribute("role", request.getParameter("role"));
		mav.setViewName("biz/loanprod/edit-loanprod");
		return mav;
	}
	
	
	
	/**
	 * 逻辑删除贷款产品管理
	 * @param financeProdForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDelFinanceProd")
	public OutputObject logicDelFinanceProd(@ModelAttribute("financeProdForm") FinanceProdForm financeProdForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);
		outputObject = getOutputObject(map, "financeProdService", "logicDelFinanceProd");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("删除成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 重新提交审核
	 * @param financeProdForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "reviewFinanceProd")
	public OutputObject reviewFinanceProd(@ModelAttribute("financeProdForm") FinanceProdForm financeProdForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		//审核状态（-1：审核失败，0：待审核，1：审核通过）
		//financeProdForm.setAuthState("0");
		Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);
		outputObject = getOutputObject(map, "financeProdService", "updateObj");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("提交审核成功!");
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
		return outputObject;
	}
	
	@SuppressWarnings("unchecked")
	public  UploadFile upload(HttpServletRequest request,UploadFile uploadFile) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path+"loan/";// 文件的硬盘真实路径
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
					String showImgPath=contextPath+"/viewImage/viewImage?imgPath="+"loan/"+fileName;
					uploadFile.setRealPath(showImgPath);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
	
	/**
	 * 跳转到贷款业务申办信息表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "applist")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/loanprod/loanapplier-list");
		return mv;
	}
	
	/**
	 * 分页查询贷款业务申办信息表列表
	 * @param CardApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getLoanList")
	public OutputObject getLoanList(@ModelAttribute("cardApplierForm") CardApplierForm cardApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		
		HashMap<String, Object> user = (HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER);
		String userId = user.get("id").toString();
		Map<String, Object> inmap = new HashMap<String, Object>();
		inmap.put("userId", userId);
		OutputObject outPutObjectUse = getOutputObject(inmap, "managerService", "getUserDepartCodeByUserId");
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		List<Map<String, Object>> info = outPutObjectUse.getBeans();
		if(outPutObjectUse.getBeans().get(0).get("roleCode")!=null&&outPutObjectUse.getBeans().get(0).get("roleCode")!=""){
			for (int i = 0; i < info.size(); i++) {
				String roleCode = info.get(i).get("roleCode").toString();
				if(roleCode.equals("JY_LOAN_MNG")||roleCode.equals("XF_LOAN_MNG")){
					map.put("taskerId", userId);
				}
			}
		}
		map.put("applyType", "loan");
		map.put("orderByClause", "apply_time desc"); 
		outputObject = getOutputObject(map, "cardApplierService", "getList");
		return outputObject;
	}
	
}
