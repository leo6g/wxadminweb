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
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.weixin.form.WXConfigForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2016-10-12 14:52
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/config")
public class WXConfigController extends BaseController{
	
	
	
	/**
	 * 跳转到微信账号配置信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/config-list");
		return mv;
	}
	/**
	 * 分页查询微信账号配置信息列表
	 * @param WXConfigForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXConfigForm") WXConfigForm wXConfigForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXConfigForm);
		outputObject = getOutputObject(map, "wXConfigService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询微信账号配置信息
	 * @param WXConfigForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXConfigForm") WXConfigForm wXConfigForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXConfigForm);	
		outputObject = getOutputObject(map,"wXConfigService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信账号配置信息
	 * @param "WXConfigForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXConfigForm") WXConfigForm wXConfigForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXConfigForm);	
		outputObject = getOutputObject(map,"wXConfigService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信账号配置信息
	 * @param WXConfigForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXConfig")
	public OutputObject insertWXConfig(@ModelAttribute("WXConfigForm") @Valid WXConfigForm wXConfigForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXConfigForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
//			WXConfigForm config = new WXConfigForm();
//			Map<String, Object> configs = BeanUtil.convertBean2Map(config);
//			outputObject = getOutputObject(configs,"wXConfigService","getList");
			outputObject = getOutputObject(map,"wXConfigService","getAll");
			if (outputObject.getBeans().size()>=1) {
				outputObject.setReturnCode("1");
			}else{
				outputObject = getOutputObject(map, "wXConfigService", "insertWXConfig");
			}
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信账号配置信息添加成功!");
			}else if(outputObject.getReturnCode().equals("1")){
				outputObject.setReturnMessage("微信账号配置信息已经存在，请修改!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信账号配置信息
	 * @param WXConfigForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXConfig")
	public OutputObject updateWXConfig(@ModelAttribute("WXConfigForm") @Valid WXConfigForm wXConfigForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXConfigForm);
		outputObject = getOutputObject(map,"wXConfigService","getAll");
		if(outputObject.getBeans().size()>=1){
			outputObject.setReturnCode("1");
		}else {
			outputObject = getOutputObject(map, "wXConfigService", "updateWXConfig");
		}
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信账号配置信息编辑成功!");
		}else if(outputObject.getReturnCode().equals("1")){
			outputObject.setReturnMessage("微信账号配置信息已经存在，请修改!");
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
		mav.setViewName("weixin/add-config");
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
		outputObject = getOutputObject(map,"wXConfigService","selectByPrimaryKey");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-config");
		return mav;
	}
	/**
	 * 删除微信账号配置信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXConfig")
	public OutputObject deleteWXConfig(@ModelAttribute("WXConfigForm") WXConfigForm wXConfigForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXConfigForm);
		outputObject = getOutputObject(map, "wXConfigService", "deleteWXConfig");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信账号配置信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信账号配置信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXConfig")
	public OutputObject logicDeleteWXConfig(@ModelAttribute("WXConfigForm") WXConfigForm wXConfigForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXConfigForm);
		outputObject = getOutputObject(map, "wXConfigService", "logicDeleteWXConfig");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
