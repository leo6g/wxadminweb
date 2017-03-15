package com.lfc.wxadminweb.modules.weixin.web;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.sd4324530.fastweixin.api.config.ChangeType;
import com.github.sd4324530.fastweixin.api.config.ConfigChangeNotice;
import com.github.sd4324530.fastweixin.api.response.GetTokenResponse;
import com.github.sd4324530.fastweixin.exception.WeixinException;
import com.github.sd4324530.fastweixin.util.JSONUtil;
import com.github.sd4324530.fastweixin.util.NetWorkCenter;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;

import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;




import com.lfc.wxadminweb.modules.weixin.form.AccessTokenForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author geguangyang
 * @date 2016-12-19 19:30
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/accesstoken")
public class AccessTokenController extends BaseController{
	@Value("#{system.appId}") 
	private  String appid;
	@Value("#{system.secret}") 
	private  String secret;
	
	private String accessToken ;
	/**
	* 校验前台传入的date格式，格式必须一样
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	
	/**
	 * 跳转到微信获取token列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/acesstoken-list");
		return mv;
	}
	/**
	 * 分页查询微信获取token列表
	 * @param AccessTokenForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("accessTokenForm") AccessTokenForm accessTokenForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(accessTokenForm);
		outputObject = getOutputObject(map, "accessTokenService", "getList");
		return outputObject;
	}
	
	@ResponseBody
	@RequestMapping(value = "getToken")
	public Map<String, Object> getToken() {		
		OutputObject outputObject = null;
		Map<String,Object> map = new HashMap<String,Object>();
		outputObject = getOutputObject(map, "accessTokenService", "getList");
		String accessToken = null;
		if(outputObject.getReturnCode().equals("0") && outputObject.getBeans().get(0) !=null){
			accessToken = (String) outputObject.getBeans().get(0).get("accessToken");
			map.put("returnCode", "0");
		}
		map.put("accessToken", accessToken);
		
		return map;
	}
	/**
	/**
	 * 编辑微信获取token
	 * @param AccessTokenForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateAccessToken")
	public OutputObject updateAccessToken(@ModelAttribute("AccessTokenForm") @Valid AccessTokenForm accessTokenForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		initToken();
		accessTokenForm.setAccessToken(accessToken);
		Map<String, Object> map = BeanUtil.convertBean2Map(accessTokenForm);
		outputObject = getOutputObject(map, "accessTokenService", "updateAccessToken");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信获取token生成成功!");
		}
		return outputObject;
	}
	
	/**
     * 初始化微信配置，即第一次获取access_token
     *
     * @param refreshTime 刷新时间
     */
    private void initToken() {
        logger.info("开始初始化access_token........");
         
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.appid + "&secret=" + this.secret;
        NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
                if (HttpStatus.SC_OK == resultCode) {
                    GetTokenResponse response = JSONUtil.toBean(resultJson, GetTokenResponse.class);
                    logger.info("获取access_token:{}", response.getAccessToken());
                    if (null == response.getAccessToken()) {
                        throw new WeixinException("微信公众号token获取出错，错误信息:" + response.getErrcode() + "," + response.getErrmsg());
                    }
                    accessToken = response.getAccessToken();
                }
            }
        });
    }
}
