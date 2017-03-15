package com.lfc.wxadminweb.modules.biz.web;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.biz.form.RecommendFinanceForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author gy
 * @date 2017-02-21 11:39
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/recfinance")
public class RecommendFinanceController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;	
	
	/**
	 * 跳转到文章推荐列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/recommend/recommendfinance-list");
		return mv;
	}
	/**
	 * 分页查询文章推荐列表
	 * @param RecommendFinanceForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("recommendFinanceForm") RecommendFinanceForm recommendFinanceForm,
			BindingResult result,HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(recommendFinanceForm);
		map.put("orderByClause", "t.CREATE_TIME desc");
		outputObject = getOutputObject(map, "recommendFinanceService", "getList");
		List<Map<String,Object>> recoms=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> recom:recoms){
			recom.put("localUrl", contextPath+"/viewImage/viewImage?imgPath=financeRec/"+recom.get("localUrl"));
			recom.put("hotImgPath", contextPath+"/viewImage/viewImage?imgPath=financeRec/"+recom.get("hotImgPath"));
			recom.put("createTime", recom.get("createTime").toString().substring(0, 10));
		}
		return outputObject;
	}
	/**
	 *根据ID查询文章推荐
	 * @param RecommendFinanceForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("RecommendFinanceForm") RecommendFinanceForm recommendFinanceForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(recommendFinanceForm);	
		outputObject = getOutputObject(map,"recommendFinanceService","getById");
		return outputObject;
	}
	/**
	 * 查看所有文章推荐
	 * @param "RecommendFinanceForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("RecommendFinanceForm") RecommendFinanceForm recommendFinanceForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(recommendFinanceForm);	
		outputObject = getOutputObject(map,"recommendFinanceService","getAll");
		return outputObject;
	}
	/**
	 * 添加文章推荐
	 * @param RecommendFinanceForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertRecommendFinance")
	public OutputObject insertRecommendFinance(@ModelAttribute("RecommendFinanceForm") @Valid RecommendFinanceForm recommendFinanceForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(recommendFinanceForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "recommendFinanceService", "insertRecommendFinance");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("文章推荐添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑文章推荐
	 * @param RecommendFinanceForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateRecommendFinance")
	public OutputObject updateRecommendFinance(@ModelAttribute("RecommendFinanceForm") @Valid RecommendFinanceForm recommendFinanceForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(recommendFinanceForm);
		outputObject = getOutputObject(map, "recommendFinanceService", "updateRecommendFinance");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("文章推荐编辑成功!");
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
		mav.setViewName("biz/recommend/add-recommendfinance");
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
		outputObject = getOutputObject(map,"recommendFinanceService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("biz/recommend/edit-recommendfinance");
		return mav;
	}
	/**
	 * 删除文章推荐
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteRecommendFinance")
	public OutputObject deleteRecommendFinance(@ModelAttribute("RecommendFinanceForm") RecommendFinanceForm recommendFinanceForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(recommendFinanceForm);
		outputObject = getOutputObject(map, "recommendFinanceService", "deleteRecommendFinance");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("文章推荐删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除文章推荐
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteRecommendFinance")
	public OutputObject logicDeleteRecommendFinance(@ModelAttribute("RecommendFinanceForm") RecommendFinanceForm recommendFinanceForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(recommendFinanceForm);
		outputObject = getOutputObject(map, "recommendFinanceService", "logicDeleteRecommendFinance");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 上传图标
	 * @param request
	 * @param response
	 * @param model
	 * @param mm
	 */
	@ResponseBody
	@RequestMapping(value = "doUpload")
	public OutputObject doUpload(HttpServletRequest request, HttpServletResponse response, Model model,ModelMap mm) {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile=upload(request,uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		return outputObject;
	}
	
	@SuppressWarnings("unchecked")
	public  UploadFile upload(HttpServletRequest request,UploadFile uploadFile) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path+"financeRec/";// 文件的硬盘真实路径
			File file = new File(realPath);
			if (!file.exists()) {
				file.mkdirs();// 创建根目录
			}
			String fileName = "";
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();// 获取上传文件对象
				String s = UUID.randomUUID().toString();
				String name = mf.getOriginalFilename();
				String extend = name.substring(name.lastIndexOf("."));
				fileName = s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24)+extend;// 获取文件名				
				String savePath = realPath + fileName;//文件保存全路径
				File savefile = new File(savePath);
					// 设置文件数据库的物理路径
				String contextPath = request.getContextPath();
					String showImgPath=contextPath+"/viewImage/viewImage?imgPath=financeRec/"+fileName;
					uploadFile.setRealPath(showImgPath);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
	
}
