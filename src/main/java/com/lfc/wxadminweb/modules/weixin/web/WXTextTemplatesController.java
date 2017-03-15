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


import com.lfc.wxadminweb.modules.weixin.form.WXTextTemplatesForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2016-10-12 11:53
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/texttemplates")
public class WXTextTemplatesController extends BaseController{
	
	
	
	/**
	 * 跳转到微信文本模版列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/texttemplates-list");
		return mv;
	}
	/**
	 * 分页查询微信文本模版列表
	 * @param WXTextTemplatesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXTextTemplatesForm") WXTextTemplatesForm wXTextTemplatesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXTextTemplatesForm);
		outputObject = getOutputObject(map, "wXTextTemplatesService", "getList");
		logger.info("-------outputObject---->"+outputObject);
		logger.info(""+(outputObject == null));
		return outputObject;
	}
	/**
	 *根据ID查询微信文本模版
	 * @param WXTextTemplatesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXTextTemplatesForm") WXTextTemplatesForm wXTextTemplatesForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXTextTemplatesForm);	
		outputObject = getOutputObject(map,"wXTextTemplatesService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信文本模版
	 * @param "WXTextTemplatesForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXTextTemplatesForm") WXTextTemplatesForm wXTextTemplatesForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXTextTemplatesForm);	
		outputObject = getOutputObject(map,"wXTextTemplatesService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信文本模版
	 * @param WXTextTemplatesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXTextTemplates")
	public OutputObject insertWXTextTemplates(@ModelAttribute("WXTextTemplatesForm") @Valid WXTextTemplatesForm wXTextTemplatesForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXTextTemplatesForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "wXTextTemplatesService", "insertWXTextTemplates");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信文本模版添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信文本模版
	 * @param WXTextTemplatesForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXTextTemplates")
	public OutputObject updateWXTextTemplates(@ModelAttribute("WXTextTemplatesForm") @Valid WXTextTemplatesForm wXTextTemplatesForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXTextTemplatesForm);
		outputObject = getOutputObject(map, "wXTextTemplatesService", "updateWXTextTemplates");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信文本模版编辑成功!");
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
		mav.setViewName("weixin/add-texttemplates");
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
		outputObject = getOutputObject(map,"wXTextTemplatesService","selectByPrimaryKey");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-texttemplates");
		return mav;
	}
	/**
	 * 删除微信文本模版
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXTextTemplates")
	public OutputObject deleteWXTextTemplates(@ModelAttribute("WXTextTemplatesForm") WXTextTemplatesForm wXTextTemplatesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXTextTemplatesForm);
		outputObject = getOutputObject(map, "wXTextTemplatesService", "deleteWXTextTemplates");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信文本模版删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信文本模版
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXTextTemplates")
	public OutputObject logicDeleteWXTextTemplates(@ModelAttribute("WXTextTemplatesForm") WXTextTemplatesForm wXTextTemplatesForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXTextTemplatesForm);
		outputObject = getOutputObject(map, "wXTextTemplatesService", "logicDeleteWXTextTemplates");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
