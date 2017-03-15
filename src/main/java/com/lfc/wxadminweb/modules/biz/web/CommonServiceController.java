package com.lfc.wxadminweb.modules.biz.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.utils.CacheUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.vo.PayMent;
import com.lfc.wxadminweb.modules.biz.vo.PayMentWraper;

import sun.misc.BASE64Decoder;

/**
 * 提供公共服务的Controller, 供weixinWeb中的页面使用， 为了减少在
 * weixinWeb中引入过多的不相关的类。该类的访问路径需要再过滤器中剔除，也就是不过滤该类的访问。
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping("biz/commonservice")
public class CommonServiceController extends BaseController {

	/**
	 * 根据用户的openId 来获得用户角色编码列表、用户隶属机构代码。
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "getUserInfoByOpenId")
	public Object getUserInfoByOpenId(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String roleCodes = "";
		String orgCode = "";
		String openId = request.getParameter("openId");
		if (StringUtils.isNotBlank(openId)) {

			paraMap.put("openId", openId);
			// 获取对应的角色
			OutputObject outPutObjectRole = getOutputObject(paraMap, "managerService", "getUserRoleCodesByOpenId");
			roleCodes = outPutObjectRole.getReturnCode();
			// 获取对应的机构编码
			OutputObject outPutObjectOrg = getOutputObject(paraMap, "managerService", "getUserDepartCodeByOpenId");
			orgCode = outPutObjectOrg.getReturnCode();
		}
		resultMap.put("roleCodes", roleCodes);
		resultMap.put("orgCode", orgCode);
		return resultMap;
	}

	/**
	 * 根据字典表的code 获取字典表明细
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDictionInfoByCode")
	public Object getDictionInfoByCode(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String, Object> paraMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String dicCode = request.getParameter("dicCode");
		if (StringUtils.isNotBlank(dicCode)) {
			paraMap.put("dicCode", dicCode);
			OutputObject outPutObjectRole = getOutputObject(paraMap, "dictionaryService", "getDicDetailByDicCode");
			resultMap = outPutObjectRole.getBean();
		}

		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = "saveTokenAndOpenId")
	public Object saveTokenAndOpenId(HttpServletRequest request, Object object, HttpServletResponse response) {
		String openId = request.getParameter("openId");
		String token = request.getParameter("token");
		//把token和openId放入redis中
		Map<String, String> map = null;
		if (StringUtils.isNotBlank(openId) && StringUtils.isNotBlank(token)) {
			map = CacheUtil.getMap("tokenMap");
			if(map==null){
				map = new HashMap<String, String>();
			}
			map.put(token, openId);
			CacheUtil.putMap("tokenMap", map);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "getOpenIdByToken")
	public Object getOpenIdByToken(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getParameter("token");
		Map<String,String> opdMap = new HashMap<String, String>();
		opdMap.put("openId", null);
		if (StringUtils.isNotBlank(token)) {
			//从redis中获取tokenMap的token和openId，返回map，不存在返回null
			Map<String,String> resultMap = CacheUtil.getMap("tokenMap");
			if(resultMap!=null && resultMap.size()>0){
				String openId = resultMap.get(token);
				//得到openId后返回并销毁tokenMap中此token，这是孟老师要求的，有疑问或要修改的咨询他
				resultMap.remove(token);
				//先删除key再放，redis存储map类型比较特殊
				CacheUtil.delKey("tokenMap");
				CacheUtil.putMap("tokenMap", resultMap);
				opdMap.put("openId", openId);
				return opdMap;
			}
		}
		return opdMap;
	}

	
	// fs缴费成功数据刷新
	/*
	@ResponseBody
	@RequestMapping(value = "confirmPaymentInfo")
	public Object confirmPaymentInfo(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		ServletContext application = request.getSession().getServletContext();
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, Object> inmap = new HashMap<String, Object>();
		Map<String, String> map = (Map<String, String>) application.getAttribute("tokenMap");
		String token = request.getParameter("token");
		// map = new HashMap<String,String>();
		// map.put(token+"-FSJF", token);
		String openId = map.get(token + "-FSJF");
		inmap.put("openId", openId);
		inmap.put("type", "payment");
		inmap.put("amount", 5);
		OutputObject outputObject = getOutputObject(inmap, "ficMoneyService", "insertFicMoney");
		if (outputObject.getReturnCode().equals("0")) {
			resultMap.put("msg", "个人中心添加成功!");
			map.remove(token + "-FSJF");
		}
		return resultMap;
	}
	*/

	/**
	 * 服务端接口(对外提交) 劲捷缴费
	 */
	@ResponseBody
	@RequestMapping(value = "serverService")
	public Object serverService(HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> resultMap = new HashMap<String, String>();
		OutputObject outputObject = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			String jsonData = request.getParameter("json");
			//String json = new String((new BASE64Decoder()).decodeBuffer(jsonData));
			
			logger.info("---------jsonData---------->"+jsonData);
			
			JSONObject paraJson = new JSONObject(jsonData);
			
			logger.info("---------data---------->"+paraJson.get("data").toString());
			byte[] btDataArr = decoder.decodeBuffer(paraJson.get("data").toString());
			
			JSONObject dataJson = new JSONObject(new String(btDataArr,"utf-8"));
			
			logger.info("---------data---------->"+dataJson);
			
			
			map.put("seriesNo", dataJson.get("seriesNo"));
			map.put("name", dataJson.get("name"));
			if (StringUtils.isNotEmpty(String.valueOf(dataJson.get("payTime")))) {
				map.put("payTime", sdf.parse(dataJson.get("payTime").toString()));
			}

			map.put("payMoney", dataJson.get("payMoney"));
			map.put("openId", dataJson.get("openId"));
			logger.info(map.toString());
			outputObject = getOutputObject(map, "paymentInfoService", "insertPaymentInfo");
			if (outputObject.getReturnCode().equals("0")) {
				resultMap.put("msg", "同步缴费信息成功!");
				resultMap.put("code", "success");
			}
			return resultMap;
		} catch (Exception e) {
			resultMap.put("msg", e.getMessage());
			resultMap.put("code", "failure");
			e.printStackTrace();
		}
		return resultMap;

	}
	
	
	
	
}
