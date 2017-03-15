package com.lfc.wxadminweb.modules.weixin.web;


import java.util.HashMap;
import java.util.List;
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


import com.lfc.wxadminweb.modules.weixin.form.WXSubcribeMsgForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author GY
 * @date 2016-10-12 15:11
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/subcribemsg")
public class WXSubcribeMsgController extends BaseController{
	
	
	
	/**
	 * 跳转到微信关注语列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/subcribemsg-list");
		return mv;
	}
	/**
	 * 分页查询微信关注语列表
	 * @param WXSubcribeMsgForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXSubcribeMsgForm") WXSubcribeMsgForm wXSubcribeMsgForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeMsgForm);
		outputObject = getOutputObject(map, "wXSubcribeMsgService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询微信关注语
	 * @param WXSubcribeMsgForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXSubcribeMsgForm") WXSubcribeMsgForm wXSubcribeMsgForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeMsgForm);	
		outputObject = getOutputObject(map,"wXSubcribeMsgService","getById");
		return outputObject;
	}
	/**
	 * 查看所有微信关注语
	 * @param "WXSubcribeMsgForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXSubcribeMsgForm") WXSubcribeMsgForm wXSubcribeMsgForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeMsgForm);	
		outputObject = getOutputObject(map,"wXSubcribeMsgService","getAll");
		return outputObject;
	}
	/**
	 * 添加微信关注语
	 * @param WXSubcribeMsgForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "insertWXSubcribeMsg")
	public OutputObject insertWXSubcribeMsg(@ModelAttribute("WXSubcribeMsgForm") @Valid WXSubcribeMsgForm wXSubcribeMsgForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeMsgForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			WXSubcribeMsgForm subcribeMsg = new WXSubcribeMsgForm();
			Map<String, Object> numap = BeanUtil.convertBean2Map(subcribeMsg);
			outputObject = getOutputObject(numap, "wXSubcribeMsgService", "getList");
			if (outputObject.getBeans().size()>=1) {
				outputObject.setReturnCode("-1");
			}else{
				outputObject = getOutputObject(map, "wXSubcribeMsgService", "insertWXSubcribeMsg");
			}
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信关注语添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信关注语
	 * @param WXSubcribeMsgForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXSubcribeMsg")
	public OutputObject updateWXSubcribeMsg(@ModelAttribute("WXSubcribeMsgForm") @Valid WXSubcribeMsgForm wXSubcribeMsgForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeMsgForm);
		outputObject = getOutputObject(map, "wXSubcribeMsgService", "updateWXSubcribeMsg");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信关注语编辑成功!");
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
		
		mav.setViewName("weixin/add-subcribemsg");
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
		outputObject = getOutputObject(map,"wXSubcribeMsgService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-subcribemsg");
		return mav;
	}
	/**
	 * 删除微信关注语
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXSubcribeMsg")
	public OutputObject deleteWXSubcribeMsg(@ModelAttribute("WXSubcribeMsgForm") WXSubcribeMsgForm wXSubcribeMsgForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeMsgForm);
		outputObject = getOutputObject(map, "wXSubcribeMsgService", "deleteWXSubcribeMsg");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信关注语删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信关注语
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXSubcribeMsg")
	public OutputObject logicDeleteWXSubcribeMsg(@ModelAttribute("WXSubcribeMsgForm") WXSubcribeMsgForm wXSubcribeMsgForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXSubcribeMsgForm);
		outputObject = getOutputObject(map, "wXSubcribeMsgService", "logicDeleteWXSubcribeMsg");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
}
