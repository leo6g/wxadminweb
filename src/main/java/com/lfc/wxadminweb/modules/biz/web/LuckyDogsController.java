package com.lfc.wxadminweb.modules.biz.web;


import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.lfc.wxadminweb.common.exportexcel.LuckyDogsData;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.LuckyDogsForm;



/**
 * <h2></br>
 * 
 * @descript 
 * @author gy
 * @date 2017-01-17 09:34
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/lucky")
public class LuckyDogsController extends BaseController{
	
	
	
	/**
	 * 跳转到中奖信息表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/luckydogs/ckydogs-list");
		return mv;
	}
	/**
	 * 分页查询中奖信息表列表
	 * @param LuckyDogsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("luckyDogsForm") LuckyDogsForm luckyDogsForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(luckyDogsForm);
		outputObject = getOutputObject(map, "luckyDogsService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询中奖信息表
	 * @param LuckyDogsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("LuckyDogsForm") LuckyDogsForm luckyDogsForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(luckyDogsForm);	
		outputObject = getOutputObject(map,"luckyDogsService","getById");
		return outputObject;
	}
	/**
	 * 查看所有中奖信息表
	 * @param "LuckyDogsForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("LuckyDogsForm") LuckyDogsForm luckyDogsForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(luckyDogsForm);	
		outputObject = getOutputObject(map,"luckyDogsService","getAll");
		return outputObject;
	}
	/**
	 * 添加中奖信息表
	 * @param LuckyDogsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertLuckyDogs")
	public OutputObject insertLuckyDogs(@ModelAttribute("LuckyDogsForm") @Valid LuckyDogsForm luckyDogsForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(luckyDogsForm);
			outputObject = getOutputObject(map, "luckyDogsService", "insertLuckyDogs");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("中奖信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑中奖信息表
	 * @param LuckyDogsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateLuckyDogs")
	public OutputObject updateLuckyDogs(@ModelAttribute("LuckyDogsForm") @Valid LuckyDogsForm luckyDogsForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(luckyDogsForm);
		outputObject = getOutputObject(map, "luckyDogsService", "updateLuckyDogs");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("中奖信息编辑成功!");
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
		mav.setViewName("biz/luckydogs/add-ckydogs");
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
		outputObject = getOutputObject(map,"luckyDogsService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/luckydogs/edit-ckydogs");
		return mav;
	}
	/**
	 * 删除中奖信息表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteLuckyDogs")
	public OutputObject deleteLuckyDogs(@ModelAttribute("LuckyDogsForm") LuckyDogsForm luckyDogsForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(luckyDogsForm);
		outputObject = getOutputObject(map, "luckyDogsService", "deleteLuckyDogs");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("中奖信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除中奖信息表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteLuckyDogs")
	public OutputObject logicDeleteLuckyDogs(@ModelAttribute("LuckyDogsForm") LuckyDogsForm luckyDogsForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(luckyDogsForm);
		outputObject = getOutputObject(map, "luckyDogsService", "logicDeleteLuckyDogs");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 导出数据
	 * */
	@RequestMapping(value = "exportData")
	public void exprotData(LuckyDogsForm luckyDogsForm,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(luckyDogsForm);
		outputObject = getOutputObject(map, "luckyDogsService", "getAll");//根据查询条件，导出所有，没有分页
		try {
			LuckyDogsData.exportData(outputObject.getBeans(), request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
	}
}
