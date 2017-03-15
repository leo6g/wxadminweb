package com.lfc.wxadminweb.modules.weixin.Statistics.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.exportexcel.ExportUserAnalysisData;
import com.lfc.wxadminweb.common.web.BaseController;

@Controller
@RequestMapping("/wx/useranalysis")
public class UserAnalysisController extends BaseController{

	/**
	 * 用户分析
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView getKeyIndicatorsOfYesterday(ModelAndView mv){
		mv.setViewName("weixin/user-analysis");
		return mv;
		
	}
	
	/**
	 * 昨日关键指标
	 * @return
	 */
	public OutputObject getKeyIndicatorsOfYesterday(){
		OutputObject outputObject = null;
		//TODO...暂时只统计了新关注人数，取消关注人数，净增关注人数，累积关注人数，与时间日期的百分比值没有计算出来，暂没有研究这里的算法
		outputObject = getOutputObject(null,"wXUserPropertyService","getKeyIndicatorsOfYesterday");
		return outputObject;
	}
	
	
	/**
	 * 关键指标详解
	 * @param request searchType:day,week,half,month;sourceType,searchPopType人数统计类型
	 * @return
	 */
	public OutputObject getKeyIndicatorsDetails(HttpServletRequest request){
		return getUserAnalysis(request);
	}
	
	private OutputObject getUserAnalysis(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String,Object>();
		//封装查询条件
		String searchType = request.getParameter("searchType");
		switch (searchType){
            case "day":
            	String startTime = request.getParameter("startTime");
            	String endTime = request.getParameter("endTime");
            	map.put("searchType", searchType);
            	map.put("startTime", startTime);
            	map.put("endTime", endTime);
            	break;
            case "week":
            	map.put("searchType", searchType);
            	break;
            case "half":
            	map.put("searchType", searchType);
            	break;
            case "month":
            	map.put("searchType", searchType);
            	break;
            default:
            	map.put("searchType", "month");
            	break;
		}
		OutputObject outputObject = null;
		outputObject = getOutputObject(map,"wXUserPropertyService","getSubscriberInfosOfYesterday");
		return outputObject;
	}
	
	/**
	 * 用户属性
	 * @param request
	 * @return
	 */
	public OutputObject getUserProperty(HttpServletRequest request){
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String,Object>();
		outputObject = getOutputObject(map,"wXUserPropertyService","getUserProperty");
		return outputObject;
		
	}
	
	/**
	 * 导出
	 * @param request
	 * @param response
	 */
	public void exportData(HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = getUserAnalysis(request);
		List<Map<String,Object>> datas=outputObject.getBeans();
		try {
			ExportUserAnalysisData.exportData(datas, request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
	}

}
