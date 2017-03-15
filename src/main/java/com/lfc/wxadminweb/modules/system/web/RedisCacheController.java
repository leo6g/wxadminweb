package com.lfc.wxadminweb.modules.system.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.utils.CacheUtil;
import com.lfc.wxadminweb.common.web.BaseController;

@Controller
@RequestMapping(value = "system/rediscache")
public class RedisCacheController extends BaseController {
	
//	//一等奖获奖人数
//	@Value("#{system.awardOne}") 
//	private  String awardOne;
//	//二等奖获奖人数
//	@Value("#{system.awardTwo}") 
//	private  String awardTwo;
//	//三等奖获奖人数
//	@Value("#{system.awardThree}") 
//	private  String awardThree;
		
	@RequestMapping(value = "home")
	public ModelAndView home(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("system/rediscache/home");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value = "getRedisValue")
	public Object getRedisValue( HttpServletRequest request) {
		String key = request.getParameter("key");
		Map<String,Object> rtVal = new HashMap<String,Object>();
		return rtVal;
	}
	
	@ResponseBody
	@RequestMapping(value = "setRedisValue")
	public Object setRedisValue( HttpServletRequest request) {
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		Map<String,Object> rtVal = new HashMap<String,Object>();
		return rtVal;
	}
	
	//跳转到抽奖序列设置页面
	@RequestMapping(value = "series")
	public ModelAndView series(ModelAndView mv) {
		mv.setViewName("system/activity/playSeries");
		return mv;
	}
	
	//跳转到抽奖规则设置页面
	@RequestMapping(value = "rule")
	public ModelAndView rule(ModelAndView mv,Model model) {
		String awardOne = CacheUtil.getFromCache("awardOne");
		String awardTwo = CacheUtil.getFromCache("awardTwo");
		String awardThree = CacheUtil.getFromCache("awardThree");
		if(StringUtils.isNotEmpty(awardOne)&&StringUtils.isNotEmpty(awardTwo)&&StringUtils.isNotEmpty(awardThree)){
			Integer prizes =Integer.valueOf(awardOne)+Integer.valueOf(awardTwo)+Integer.valueOf(awardThree);
			model.addAttribute("prizes", prizes);
		}
		mv.setViewName("system/activity/playRule");
		return mv;
	}
	
	//设置序列配置项的值
	@ResponseBody
	@RequestMapping(value = "setSeries")
	public OutputObject setSeries(HttpServletRequest request) {
		OutputObject outputObject = new OutputObject();
		String seriesKey = request.getParameter("seriesKey");
		String seriesValue = request.getParameter("seriesValue");
		CacheUtil.put2Cache(seriesKey, seriesValue);
		
		outputObject.setReturnCode("0");
		return outputObject;
	}
	
	//获取序列配置项的值
	@ResponseBody
	@RequestMapping(value = "getSeries")
	public OutputObject getSeries(HttpServletRequest request) {
		OutputObject outputObject = new OutputObject();
		String seriesKey = request.getParameter("seriesKey");
		String seriesValu = CacheUtil.getFromCache(seriesKey);
		outputObject.setObject(seriesValu);
		
		outputObject.setReturnCode("0");
		return outputObject;
	}
	
	//设置规则配置项
	@ResponseBody
	@RequestMapping(value = "setRule")
	public OutputObject setRule(HttpServletRequest request) {
		OutputObject outputObject = new OutputObject();
		//获取参数
//		Integer prizes =Integer.valueOf(request.getParameter("prizes")) ;
//		Integer mans = Integer.valueOf(request.getParameter("mans"));
		String configInfo = request.getParameter("configInfo");
		//生成获奖序列号
		String arr[] =configInfo.split(";");
		String awardOnes = arr[0].split(":")[1];
		String awardTwos = arr[1].split(":")[1];
		String awardThrees = arr[2].split(":")[1];
		
		CacheUtil.put2Cache("awardOnes", awardOnes);
		CacheUtil.put2Cache("awardTwos", awardTwos);
		CacheUtil.put2Cache("awardThrees", awardThrees);
		
		outputObject.setReturnCode("0");
		return outputObject;
	}
	
	//获取当前规则配置项
	@ResponseBody
	@RequestMapping(value = "setRuleNow")
	public OutputObject setRuleNow(HttpServletRequest request) {
		OutputObject outputObject = new OutputObject();
		//生成获奖序列号
		String awardOnes = CacheUtil.getFromCache("awardOnes");
		String awardTwos = CacheUtil.getFromCache("awardTwos");
		String awardThrees = CacheUtil.getFromCache("awardThrees");
		
		//返回参数
		Map<String, Object> val = new HashMap<String, Object>();
		val.put("awardOne", awardOnes);
		val.put("awardTwo", awardTwos);
		val.put("awardThree", awardThrees);
		outputObject.setBean(val);
		
		outputObject.setReturnCode("0");
		return outputObject;
	}
	
	//获取规则配置项
	@ResponseBody
	@RequestMapping(value = "getRule")
	public OutputObject getRule(HttpServletRequest request) {
		OutputObject outputObject = new OutputObject();
		//获取参数
		//Integer prizes =Integer.valueOf(request.getParameter("prizes")) ;
		Integer prizes =Integer.valueOf(CacheUtil.getFromCache("awardOne"))+Integer.valueOf(CacheUtil.getFromCache("awardTwo"))+Integer.valueOf(CacheUtil.getFromCache("awardThree"));
		Integer mans = Integer.valueOf(request.getParameter("mans"));
		//生成获奖序列号
		int a = 0;
		List<Integer> arrList = new ArrayList<Integer>();
		while(arrList.size()<prizes){
			a = (int) (Math.random() * mans+1);
			if(arrList.contains(a)){
				continue;
			}
			arrList.add(a);
		}
		//设置各等奖项
		String awardOnes = "";
		String awardTwos = "";
		String awardThrees = "";
		for (int i = 0; i < Integer.valueOf(CacheUtil.getFromCache("awardOne")); i++) {
			awardOnes+=arrList.get(i)+",";
		}
		for (int i = Integer.valueOf(CacheUtil.getFromCache("awardOne")); i < Integer.valueOf(CacheUtil.getFromCache("awardTwo"))+Integer.valueOf(CacheUtil.getFromCache("awardOne")); i++) {
			awardTwos+=arrList.get(i)+",";
		}
		for (int i =Integer.valueOf(CacheUtil.getFromCache("awardOne"))+Integer.valueOf(CacheUtil.getFromCache("awardTwo")); i < Integer.valueOf(CacheUtil.getFromCache("awardThree"))+Integer.valueOf(CacheUtil.getFromCache("awardTwo"))+Integer.valueOf(CacheUtil.getFromCache("awardOne")); i++) {
			awardThrees+=arrList.get(i)+",";
		}
		awardOnes=awardOnes.substring(0, awardOnes.length()-1);
		awardTwos=awardTwos.substring(0, awardTwos.length()-1);
		awardThrees=awardThrees.substring(0, awardThrees.length()-1);
		CacheUtil.put2Cache("awardOnes", awardOnes);
		CacheUtil.put2Cache("awardTwos", awardTwos);
		CacheUtil.put2Cache("awardThrees", awardThrees);
		
		//返回参数
		Map<String, Object> val = new HashMap<String, Object>();
		val.put("awardOne", awardOnes);
		val.put("awardTwo", awardTwos);
		val.put("awardThree", awardThrees);
		outputObject.setBean(val);
		
		outputObject.setReturnCode("0");
		return outputObject;
	}
	
//	public Object acquireAwardLevel(HttpServletRequest request){
//		Map<String,String> rtVal = new HashMap<String,String>();
//		CacheUtil.incr("playSeries");
//		String playSeries = CacheUtil.getFromCache("playSeries");
//		String awardOne = CacheUtil.getFromCache("awardOne");
//		String awardTwo = CacheUtil.getFromCache("awardTwo");
//		String awardThree = CacheUtil.getFromCache("awardThree");
//		
//		String arrOne[] = awardOne.split(",");
//		String arrTwo[] = awardTwo.split(",");
//		String arrThree[] = awardThree.split(",");
//		
//		for (int i = 0; i < arrOne.length; i++) {
//			if (playSeries.equals(arrOne[i])) {
//				rtVal.put("awardLevel", "1");
//				break;
//			}
//		}
//		if (rtVal.isEmpty()) {
//			for (int i = 0; i < arrTwo.length; i++) {
//				if (playSeries.equals(arrTwo[i])) {
//					rtVal.put("awardLevel", "2");
//					break;
//				}
//			}
//		}
//		if (rtVal.isEmpty()) {
//			for (int i = 0; i < arrThree.length; i++) {
//				if (playSeries.equals(arrThree[i])) {
//					rtVal.put("awardLevel", "3");
//					break;
//				}
//			}
//		}
//		if (rtVal.isEmpty()) {
//			rtVal.put("awardLevel", "0");
//		}
//		return rtVal;
//	}
}
