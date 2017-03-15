package com.lfc.wxadminweb.modules.weixin.web;

import java.util.HashMap;
import java.util.Map;
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
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.core.util.ControlConstants;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.web.WeixinBaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXArticleForm;
import com.lfc.wxadminweb.modules.weixin.form.WXKeywordsRuleForm;
import com.lfc.wxadminweb.modules.weixin.form.WXKeywordsRuleForm;

/**
 * <h2></br>
 * 
 * @descript 关键词回复内容设置
 * @author yaohaohao
 * @date 2016-10-12 11:56
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/keywordResp")
public class WXKeywordResponseController extends WeixinBaseController{
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/autoRespSet/keyword-response");
		return mv;
	}
	/**
	 * 分页查询关键词回复规则
	 * @param WXKeywordsRuleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("WXKeywordsRuleForm") WXKeywordsRuleForm wXKeywordsRuleForm,
			Model model,HttpServletRequest request, ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXKeywordsRuleForm);
		String contextPath = request.getContextPath();
		map.put("contextPath", contextPath);
		outputObject = getOutputObject(map, "wXKeywordsResponseService", "getList");
		return outputObject;
	}
	

	/**
     * 保存关键词自动回复规则
     * @param WXKeywordsRuleForm
     * @param result
     * @param model
     * @param mm
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "saveRule")
	public OutputObject insertWXKeywordsReply(@ModelAttribute("WXKeywordsRuleForm") @Valid WXKeywordsRuleForm wXKeywordsRuleForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
		//回复内容校验
		if ("".equals(wXKeywordsRuleForm.getMaterialId().concat(wXKeywordsRuleForm.getTxtContent()))) {
			logger.error("WXKeywordsRuleForm---参数校验错误：---回复内容不能为空");
			OutputObject outputObject = new OutputObject();
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("后台参数校验失败！");
			return outputObject;
		}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXKeywordsRuleForm);
			String ruleId=wXKeywordsRuleForm.getRuleId();
			if(ruleId!=null&&!"".equals(ruleId)){
				outputObject = getOutputObject(map, "wXKeywordsResponseService", "updateWXKeywordsResp");
			}else{
				map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
				outputObject = getOutputObject(map, "wXKeywordsResponseService", "insertWXKeywordsResp");
			}
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("关键词自动回复内容保存成功!");
			}
			return outputObject;
	}


	/**
	 * 删除一条回复规则
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "delKeyRespRule")
	public OutputObject delKeyRespRule(@ModelAttribute("WXKeywordsRuleForm") WXKeywordsRuleForm wXKeywordsRuleForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXKeywordsRuleForm);
		outputObject = getOutputObject(map, "wXKeywordsResponseService", "deleteRule");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("回复规则删除成功!");
		}
		return outputObject;
	}
	
}


