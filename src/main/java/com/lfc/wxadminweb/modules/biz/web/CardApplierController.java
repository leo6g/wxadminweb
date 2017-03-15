package com.lfc.wxadminweb.modules.biz.web;



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
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.exportexcel.ExportCardAppliData;
import com.lfc.wxadminweb.common.exportexcel.ExportLoanApplierData;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;









import com.lfc.wxadminweb.modules.biz.form.CardApplierForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author star
 * @date 2016-10-20 17:07
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/cardapplier")
public class CardApplierController extends BaseController{
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到信用卡申办信息表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(Model model, ModelAndView mv) {
		mv.setViewName("biz/cardappli/rdapplier-list");
		return mv;
	}
	
	/**
	 * 跳转到信用卡申办处理页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "handleCardList")
	public ModelAndView handleCardList(Model model, ModelAndView mv) {
		HashMap<String, Object> user = (HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER);
		String userId = user.get("id").toString();
		Map<String, Object> inmap = new HashMap<String, Object>();
		inmap.put("userId", userId); 
		OutputObject outPutObjectUse = getOutputObject(inmap, "managerService", "getUserDepartCodeByUserId");
		List<Map<String, Object>> outList = outPutObjectUse.getBeans();
		for (Map<String, Object> map : outList) {
			if("CARD_ZG".equals(map.get("roleCode")) || "CARD_MNG".equals(map.get("roleCode"))){
				logger.info(map.get("roleCode")+"-------------------"+map.get("departCode"));
				model.addAttribute("departCode", map.get("departCode"));
				model.addAttribute("roleCode", map.get("roleCode"));
				break;
			}
		}
		
		mv.setViewName("biz/cardappli/handle-cardapplier");
		return mv;
	}
	
	/**
	 * 跳转到贷款申办处理页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "handleLoanList")
	public ModelAndView handleLoanList(Model model, ModelAndView mv) {
		HashMap<String, Object> user = (HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER);
		String userId = user.get("id").toString();
		Map<String, Object> inmap = new HashMap<String, Object>();
		inmap.put("userId", userId);
		OutputObject outPutObjectUse = getOutputObject(inmap, "managerService", "getUserDepartCodeByUserId");
		List<Map<String, Object>> outList = outPutObjectUse.getBeans();
		for (Map<String, Object> map : outList) {
			if("LOAN_ZG".equals(map.get("roleCode")) ||"LOAN_MNG".equals(map.get("roleCode"))){
				logger.info(map.get("roleCode")+"-------------------"+map.get("departCode"));
				model.addAttribute("departCode", map.get("departCode"));
				model.addAttribute("roleCode", map.get("roleCode"));
				break;
			}
		}
		
		mv.setViewName("biz/loanprod/handle-loanapplier");
		return mv;
	}
	
	/**
	 * 分页查询信用卡申办信息表列表
	 * @param CardApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("cardApplierForm") CardApplierForm cardApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		
		/*HashMap<String, Object> user = (HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER);
		String userId = user.get("id").toString();
		Map<String, Object> inmap = new HashMap<String, Object>();
		inmap.put("userId", userId);
		OutputObject outPutObjectUse = getOutputObject(inmap, "managerService", "getUserDepartCodeByUserId");
		String departCode = outPutObjectUse.getBeans().get(0).get("departCode").toString();
		String roleCode = outPutObjectUse.getBeans().get(0).get("roleCode").toString();*/
		
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		/*if(roleCode.equals("card_MNG")==false){
			map.put("taskerId", userId);
		}
		map.put("netPointId", departCode);*/
		map.put("applyType", "card");
		map.put("orderByClause", "apply_time desc"); 
		outputObject = getOutputObject(map, "cardApplierService", "getList");
		return outputObject;
	}
	
	/**
	 * 角色分页查询信用卡或贷款申办信息表列表
	 * @param CardApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getListByRole")
	public OutputObject getListByRole(@ModelAttribute("cardApplierForm") CardApplierForm cardApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		map.put("orderByClause", "apply_time desc"); 
		if("CARD_MNG".equals(cardApplierForm.getRoleCode()) || "LOAN_MNG".equals(cardApplierForm.getRoleCode())){
			//客户经理筛选条件，只能显示自己处理的申请
			map.put("handleId", ((HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER)).get("id"));
		}
		outputObject = getOutputObject(map, "cardApplierService", "getListByRole");
		return outputObject;
	}
	/**
	 *根据ID查询信用卡申办信息表
	 * @param CardApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("CardApplierForm") CardApplierForm cardApplierForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);	
		outputObject = getOutputObject(map,"cardApplierService","getById");
		return outputObject;
	}
	/**
	 * 查看所有信用卡申办信息表
	 * @param "CardApplierForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("CardApplierForm") CardApplierForm cardApplierForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);	
		outputObject = getOutputObject(map,"cardApplierService","getAll");
		return outputObject;
	}
	/**
	 * 添加信用卡申办信息表
	 * @param CardApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertCardApplier")
	public OutputObject insertCardApplier(@ModelAttribute("CardApplierForm") @Valid CardApplierForm cardApplierForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
			outputObject = getOutputObject(map, "cardApplierService", "insertCardApplier");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("信用卡申办信息表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑信用卡申办信息表
	 * @param CardApplierForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateCardApplier")
	public OutputObject updateCardApplier(@ModelAttribute("CardApplierForm") @Valid CardApplierForm cardApplierForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		outputObject = getOutputObject(map, "cardApplierService", "updateCardApplier");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("信用卡申办信息表编辑成功!");
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
		mav.setViewName("weixin/add-rdapplier");
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
		outputObject = getOutputObject(map,"cardApplierService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-rdapplier");
		return mav;
	}
	/**
	 * 删除信用卡申办信息表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteCardApplier")
	public OutputObject deleteCardApplier(@ModelAttribute("CardApplierForm") CardApplierForm cardApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		outputObject = getOutputObject(map, "cardApplierService", "deleteCardApplier");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("信用卡申办信息表删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除信用卡申办信息表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteCardApplier")
	public OutputObject logicDeleteCardApplier(@ModelAttribute("CardApplierForm") CardApplierForm cardApplierForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		outputObject = getOutputObject(map, "cardApplierService", "logicDeleteCardApplier");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
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
	
	
	/**
	 * 信用卡申请处理查询--导出
	 * @param cardApplierForm
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportData")
	public void exportData(@ModelAttribute("CardApplierForm") CardApplierForm cardApplierForm,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		
		//导出数据加上角色限制   zhaoyan  beg
		if("CARD_MNG".equals(cardApplierForm.getRoleCode()) || "LOAN_MNG".equals(cardApplierForm.getRoleCode())){
			//客户经理筛选条件，只能显示自己处理的申请
			map.put("handleId", ((HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER)).get("id"));
		}
		//导出数据加上角色限制   zhaoyan end
		map.put("applyType", "card");
		map.put("orderByClause", "apply_time desc"); 
		outputObject = getOutputObject(map, "cardApplierService", "getListByRoleAll");//根据查询条件，导出所有所应该看到的数据，与当前登陆人查询到的数据一致，没有分页
		try {
			ExportCardAppliData.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
	
	/**
	 * 信用卡申请查询--导出
	 * @param cardApplierForm
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportCardAppInfo")
	public void exportCardAppInfo(@ModelAttribute("CardApplierForm") CardApplierForm cardApplierForm,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		map.put("applyType", "card");
		map.put("orderByClause", "apply_time desc"); 
		outputObject = getOutputObject(map, "cardApplierService", "getListByCardInfo");//根据查询条件，导出所有所应该看到的数据，没有分页
		try {
			ExportCardAppliData.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
	
	/**
	 * 贷款申请处理查询--导出
	 * @param cardApplierForm
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "loanHandleExport")
	public void loanHandleExport(@ModelAttribute("CardApplierForm") CardApplierForm cardApplierForm,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		
		if("CARD_MNG".equals(cardApplierForm.getRoleCode()) || "LOAN_MNG".equals(cardApplierForm.getRoleCode())){
			//客户经理筛选条件，只能显示自己处理的申请
			map.put("handleId", ((HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER)).get("id"));
		}
		//导出数据加上角色限制  
		map.put("applyType", "loan");
		map.put("orderByClause", "apply_time desc"); 
		outputObject = getOutputObject(map, "cardApplierService", "getListByRoleAll");//根据查询条件，导出所有所应该看到的数据，与当前登陆人查询到的数据一致，没有分页
		try {
			ExportLoanApplierData.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
	
	/**
	 * 贷款申请查询--导出
	 * @param cardApplierForm
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "loanExport")
	public void loanExport(@ModelAttribute("CardApplierForm") CardApplierForm cardApplierForm
			,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(cardApplierForm);
		map.put("applyType", "loan");
		map.put("orderByClause", "apply_time desc"); 
		outputObject = getOutputObject(map, "cardApplierService", "getListByCardInfo");//根据查询条件，导出所有，没有分页
		try {
			ExportLoanApplierData.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
	
}
