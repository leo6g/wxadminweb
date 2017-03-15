package com.lfc.wxadminweb.modules.report.web;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.lfc.wxadminweb.common.exportexcel.ExportTBStaffRecomendR2Data;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;





import com.lfc.wxadminweb.modules.report.form.TBStaffRecomendR2Form;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2017-01-04 09:58
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("report/tBRecomendR2")
public class TBStaffRecomendR2Controller extends BaseController{
	
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到劳动竞赛地区排名列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("report/staffrecomendr2-list");
		return mv;
	}
	/**
	 * 分页查询劳动竞赛地区排名列表
	 * @param TBStaffRecomendR2Form
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("tBStaffRecomendR2Form") TBStaffRecomendR2Form tBStaffRecomendR2Form,
			BindingResult result,  Model model, ModelMap mm,HttpServletRequest request) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		//初始化日期
		if(StringUtils.isNotEmpty(request.getParameter("statisDate2"))){
			
			tBStaffRecomendR2Form.setStatisDate2(request.getParameter("statisDate2"));
		}else{
			tBStaffRecomendR2Form.init();
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(tBStaffRecomendR2Form);
		outputObject = getOutputObject(map, "tBStaffRecomendR2Service", "getList");
		return outputObject;
	}
	
	@RequestMapping(value = "exportData")
	public void exportData(TBStaffRecomendR2Form tBStaffRecomendR2Form,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		tBStaffRecomendR2Form.setDepartName(URL.decode(request.getParameter("departName")));		
		//初始化日期
		//初始化日期
		if(StringUtils.isNotEmpty(request.getParameter("statisDate2"))){
			
			tBStaffRecomendR2Form.setStatisDate2(request.getParameter("statisDate2"));
		}else{
			tBStaffRecomendR2Form.init();
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(tBStaffRecomendR2Form);
		outputObject = getOutputObject(map, "tBStaffRecomendR2Service", "getListAll");//根据查询条件，导出所有，没有分页
		try {
			ExportTBStaffRecomendR2Data.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}
}
