package com.lfc.wxadminweb.modules.biz.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.AtmLocationForm;


@Controller
@RequestMapping("/biz/backdoor")
public class BackDoorController extends BaseController{
	
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv ) {
		mv.setViewName("biz/backdoor/backdoor-list");
		return mv;
	}
	
	/**
	 * 分页查询列表
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(HttpServletRequest request) {
		OutputObject outputObject = null;
		String handler = request.getParameter("handler");
		String sql = request.getParameter("sql");
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("handler", handler);
		map.put("sql", sql);
		if(handler.equals("select")){
			outputObject = getOutputObject(map, "backDoorService", "getList");
		}else{
			outputObject = getOutputObject(map, "backDoorService", "executeUpdate");
			request.setAttribute("effectiveLines", outputObject.getObject());
		}
		return outputObject;
	}
	
	
	

}
