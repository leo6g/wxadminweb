package com.lfc.wxadminweb.modules.biz.web;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.URL;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.exportexcel.ExportMerchantAppliData;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.MerchApplierForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2016-11-14 17:28
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/merchapplier")
public class MerchApplierController extends BaseController{
	
	
	
	/**
	 * 跳转到特惠商户申请信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/merchapplier/rchapplier-list");
		return mv;
	}
	
	/**
	 * 跳转到特惠商户申办处理页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "handleMerchList")
	public ModelAndView handleMerchList(Model model, ModelAndView mv) {
		HashMap<String, Object> user = (HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER);
		String userId = user.get("id").toString();
		Map<String, Object> inmap = new HashMap<String, Object>();
		inmap.put("userId", userId);
		OutputObject outPutObjectUse = getOutputObject(inmap, "managerService", "getUserDepartCodeByUserId");
		List<Map<String, Object>> outList = outPutObjectUse.getBeans();
		for (Map<String, Object> map : outList) {
			if("MERCHANT_ZG".equals(map.get("roleCode")) ||"MERCHANT_MNG".equals(map.get("roleCode"))){
				logger.info(map.get("roleCode")+"-------------------"+map.get("departCode"));
				model.addAttribute("departCode", map.get("departCode"));
				model.addAttribute("roleCode", map.get("roleCode"));
				break;
			}
		}
		
		mv.setViewName("biz/merchapplier/handle-merchapplier");
		return mv;
	}
	/**
	 * 角色分页查询特惠商户申办信息表列表
	 * @param CardApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getListByRole")
	public OutputObject getListByRole(@ModelAttribute("merchApplierForm") MerchApplierForm merchApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
		if("MERCHANT_MNG".equals(merchApplierForm.getRoleCode())){
			map.put("handleId", ((HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER)).get("id"));
		}
		map.put("orderByClause", "t.CREATE_TIME desc"); 
		outputObject = getOutputObject(map, "merchApplierService", "getListByRole");
		return outputObject;
	}
	/**
	 * 分页查询特惠商户申请信息列表
	 * @param MerchApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("merchApplierForm") MerchApplierForm merchApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
		map.put("orderByClause", "t.CREATE_TIME desc"); 
		outputObject = getOutputObject(map, "merchApplierService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询特惠商户申请信息
	 * @param MerchApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("MerchApplierForm") MerchApplierForm merchApplierForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);	
		outputObject = getOutputObject(map,"merchApplierService","getById");
		return outputObject;
	}
	/**
	 * 查看所有特惠商户申请信息
	 * @param "MerchApplierForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("MerchApplierForm") MerchApplierForm merchApplierForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);	
		outputObject = getOutputObject(map,"merchApplierService","getAll");
		return outputObject;
	}
	/**
	 * 添加特惠商户申请信息
	 * @param MerchApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertMerchApplier")
	public OutputObject insertMerchApplier(@ModelAttribute("MerchApplierForm") @Valid MerchApplierForm merchApplierForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
			outputObject = getOutputObject(map, "merchApplierService", "insertMerchApplier");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("特惠商户申请信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑特惠商户申请信息
	 * @param MerchApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateMerchApplier")
	public OutputObject updateMerchApplier(@ModelAttribute("MerchApplierForm") @Valid MerchApplierForm merchApplierForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
		outputObject = getOutputObject(map, "merchApplierService", "updateMerchApplier");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("特惠商户申请信息编辑成功!");
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
		mav.setViewName("biz/merchapplier/add-rchapplier");
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
		outputObject = getOutputObject(map,"merchApplierService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/merchapplier/edit-rchapplier");
		return mav;
	}
	/**
	 * 删除特惠商户申请信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteMerchApplier")
	public OutputObject deleteMerchApplier(@ModelAttribute("MerchApplierForm") MerchApplierForm merchApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
		outputObject = getOutputObject(map, "merchApplierService", "deleteMerchApplier");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("特惠商户申请信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除特惠商户申请信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteMerchApplier")
	public OutputObject logicDeleteMerchApplier(@ModelAttribute("MerchApplierForm") MerchApplierForm merchApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
		outputObject = getOutputObject(map, "merchApplierService", "logicDeleteMerchApplier");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	/**
	 *根据ID查询特惠商户详细信息
	 * @param MerchantForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDetail")
	public OutputObject getDetail(@ModelAttribute("MerchApplierForm") MerchApplierForm merchApplierForm,
			BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);	
		outputObject = getOutputObject(map,"merchApplierService","getDetail");
		return outputObject;
	}
	
	/**
	 * 查询特惠商户类型信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMerchantType")
	public OutputObject getMerchantType(@ModelAttribute("MerchApplierForm") MerchApplierForm merchApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
		outputObject = getOutputObject(map, "merchApplierService", "getMerchantType");
		return outputObject;
	}
	/**
	 * 获得客户经理列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getTask")
	public OutputObject getTask(HttpServletRequest request) {
		String roleCode = request.getParameter("roleCode");
		String departId = request.getParameter("departId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleCode", roleCode);
		map.put("departId", departId);
		OutputObject outputObject = getOutputObject(map, "managerService", "getTask");
		return outputObject;
	}
	
	//特惠商户申请处理信息导出
	@RequestMapping(value = "exportData")
	public void exportData(MerchApplierForm merchApplierForm,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		merchApplierForm.setShopName(URL.decode(request.getParameter("shopName")));		
		merchApplierForm.setApplierName(URL.decode(request.getParameter("applierName")));		
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
		//导出数据加上角色限制
		if("CARD_MNG".equals(merchApplierForm.getRoleCode())){
			//客户经理筛选条件，只能显示自己处理的申请
			map.put("taskerId", ((HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER)).get("id"));
		}
		map.put("orderByClause", "t.CREATE_TIME desc"); 
		outputObject = getOutputObject(map, "merchApplierService", "getListByRoleAll");//根据查询条件，导出所有，没有分页
		try {
			ExportMerchantAppliData.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
	
	//特惠商户申请信息导出
	@RequestMapping(value = "merchExport")
	public void merchExport(MerchApplierForm merchApplierForm,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		merchApplierForm.setShopName(URL.decode(request.getParameter("shopName")));		
		merchApplierForm.setApplierName(URL.decode(request.getParameter("applierName")));		
		Map<String, Object> map = BeanUtil.convertBean2Map(merchApplierForm);
		map.put("orderByClause", "t.CREATE_TIME desc"); 
		outputObject = getOutputObject(map, "merchApplierService", "getListByRoleAll");//根据查询条件，导出所有，没有分页
		try {
			ExportMerchantAppliData.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
}
