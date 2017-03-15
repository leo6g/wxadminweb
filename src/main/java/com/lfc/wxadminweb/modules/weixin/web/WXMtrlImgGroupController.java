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
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.weixin.form.WXMtrlImgGroupForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2017-01-11 15:55
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/mtrlimggroup")
public class WXMtrlImgGroupController extends BaseController{
	
	
	
	/**
	 * 跳转到图片素材分组表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/mtrlimggroup-list");
		return mv;
	}
	/**
	 * 分页查询图片素材分组表列表
	 * @param WXMtrlImgGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXMtrlImgGroupForm") WXMtrlImgGroupForm wXMtrlImgGroupForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMtrlImgGroupForm);
		outputObject = getOutputObject(map, "wXMtrlImgGroupService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询图片素材分组表
	 * @param WXMtrlImgGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXMtrlImgGroupForm") WXMtrlImgGroupForm wXMtrlImgGroupForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMtrlImgGroupForm);	
		outputObject = getOutputObject(map,"wXMtrlImgGroupService","getById");
		return outputObject;
	}
	/**
	 * 查看所有图片素材分组表
	 * @param "WXMtrlImgGroupForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXMtrlImgGroupForm") WXMtrlImgGroupForm wXMtrlImgGroupForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMtrlImgGroupForm);	
		outputObject = getOutputObject(map,"wXMtrlImgGroupService","getAll");
		return outputObject;
	}
	/**
	 * 添加图片素材分组表
	 * @param WXMtrlImgGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXMtrlImgGroup")
	public OutputObject insertWXMtrlImgGroup(@ModelAttribute("WXMtrlImgGroupForm") @Valid WXMtrlImgGroupForm wXMtrlImgGroupForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(wXMtrlImgGroupForm);
			outputObject = getOutputObject(map, "wXMtrlImgGroupService", "insertWXMtrlImgGroup");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("图片素材分组表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑图片素材分组表
	 * @param WXMtrlImgGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXMtrlImgGroup")
	public OutputObject updateWXMtrlImgGroup(@ModelAttribute("WXMtrlImgGroupForm") @Valid WXMtrlImgGroupForm wXMtrlImgGroupForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMtrlImgGroupForm);
		outputObject = getOutputObject(map, "wXMtrlImgGroupService", "updateWXMtrlImgGroup");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("图片素材分组表编辑成功!");
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
		mav.setViewName("weixin/add-mtrlimggroup");
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
		outputObject = getOutputObject(map,"wXMtrlImgGroupService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-mtrlimggroup");
		return mav;
	}
	/**
	 * 删除图片素材分组表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXMtrlImgGroup")
	public OutputObject deleteWXMtrlImgGroup(@ModelAttribute("WXMtrlImgGroupForm") WXMtrlImgGroupForm wXMtrlImgGroupForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMtrlImgGroupForm);
		outputObject = getOutputObject(map, "wXMtrlImgGroupService", "deleteWXMtrlImgGroup");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("图片素材分组表删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除图片素材分组表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXMtrlImgGroup")
	public OutputObject logicDeleteWXMtrlImgGroup(@ModelAttribute("WXMtrlImgGroupForm") WXMtrlImgGroupForm wXMtrlImgGroupForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMtrlImgGroupForm);
		outputObject = getOutputObject(map, "wXMtrlImgGroupService", "logicDeleteWXMtrlImgGroup");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	
	
	/**
	 * 查询分组信息
	 * @param WXMtrlImgGroupForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getListInfoById")
	public OutputObject getListInfoById(@ModelAttribute("wXMtrlImgGroupForm") WXMtrlImgGroupForm wXMtrlImgGroupForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXMtrlImgGroupForm);
		outputObject = getOutputObject(map, "wXMtrlImgGroupService", "getListInfoById");
		return outputObject;
	}
	
}
