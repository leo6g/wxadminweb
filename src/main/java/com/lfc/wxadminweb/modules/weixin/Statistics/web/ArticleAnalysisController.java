package com.lfc.wxadminweb.modules.weixin.Statistics.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.exportexcel.ExportArticlesAnalysisData;
import com.lfc.wxadminweb.common.utils.DateUtil;
import com.lfc.wxadminweb.common.web.BaseController;

/**
 * 图文分析
 * 
 * @author zhaoyuan
 * 
 */
@Controller
@RequestMapping("/wx/articleanalysis")
public class ArticleAnalysisController extends BaseController {

	@RequestMapping(value = "list")
	public ModelAndView getList(ModelAndView mv) {
		mv.setViewName("weixin/article-analysis");
		return mv;
	}

	/**
	 * 单篇图文统计列表
	 * 
	 * @return
	 */
	public OutputObject getarticles(HttpServletRequest request) {
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		OutputObject outputObject = null;
		// TODO
		outputObject = getOutputObject(map, "articlesStatisticsService",
				"getArticlesStatistics");
		return outputObject;
	}

	/**
	 * 日报--全部图文
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public ModelAndView getListByDay(HttpServletRequest request, Model model) {

		ModelAndView mav = new ModelAndView();
		OutputObject outputObject = null;
		outputObject = getArticlesAnalysis(request);
		model.addAttribute("output", outputObject);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statDate", DateUtil.getLastDate(new SimpleDateFormat(
				"yyyy-MM-dd").format(new Date())));
		OutputObject output = getOutputObject(map, "articlesStatisticsService",
				"getArticlesListByYesterday");
		model.addAttribute("articles", output);
		mav.setViewName("weixin/articlesanalysis-day");
		return mav;
	}

	/**
	 * 时报-全部图文
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	public ModelAndView getListByHour(HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		String statDate = request.getParameter("statDate");
		map.put("statDate", statDate);
		ModelAndView mav = new ModelAndView();
		OutputObject outputObject = null;
		outputObject = getOutputObject(map, "articlesStatisticsService",
				"getArticlesListByHour");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/articlesanalysis-hour");
		return mav;
	}

	private OutputObject getArticlesAnalysis(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String searchType = request.getParameter("searchType");
		switch (searchType) {
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
		outputObject = getOutputObject(map, "articlesStatisticsService",
				"getArticlesListByDay");
		return outputObject;
	}

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 */
	public void exportData(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type != null) {
			OutputObject outputObject = null;
			if (type == "day" || type.equals("day")) {
				outputObject = getArticlesAnalysis(request);
			}
			if (type == "hour" || type.equals("hour")) {
				Map<String, Object> map = new HashMap<String, Object>();
				String statDate = request.getParameter("statDate");
				map.put("statDate", statDate);
				outputObject = getOutputObject(map, "articlesStatisticsService",
						"getArticlesListByHour");
			}
			List<Map<String, Object>> datas = outputObject.getBeans();
			try {
				ExportArticlesAnalysisData.exportData(datas,request, response);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
				response.setHeader("err_msg", "下载excl文件出现异常");
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				e.printStackTrace();
			}
		}
	}
}
