package com.lfc.wxadminweb.modules.weixin.web;


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
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.utils.StringUtilLY;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.weixin.form.WXNewsItemsForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author jzp
 * @date 2016-10-12 14:58
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/wx/newsitems")
public class WXNewsItemsController extends BaseController{
	
	@Value("#{system.picture_path}") 
	private  String path;
	
	/**
	 * 跳转到微信图文详情列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("weixin/newsitems-list");
		return mv;
	}
	
	/**
	 * 跳转到微信图文详情列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "act_tend")
	public ModelAndView actTend( ModelAndView mv,  Model model) {
		model.addAttribute("itemId", "act_tend001");//专属活动文章Id
		mv.setViewName("weixin/act-tend");
		return mv;
	}
	
	/**
	 * 分页查询微信图文详情列表
	 * @param WXNewsItemsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("wXNewsItemsForm") WXNewsItemsForm wXNewsItemsForm,
			BindingResult result, HttpServletRequest request,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsItemsForm);
		outputObject = getOutputObject(map, "wXNewsItemsService", "getList");
		List<Map<String,Object>> newsItemlist=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> newsItemMap:newsItemlist){
			newsItemMap.put("imagePath", contextPath+"/viewImage/viewImage?imgPath="+newsItemMap.get("imagePath"));
			
		}
		return outputObject;
	}
	/**
	 *根据ID查询微信图文详情
	 * @param WXNewsItemsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("WXNewsItemsForm") WXNewsItemsForm wXNewsItemsForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsItemsForm);	
		outputObject = getOutputObject(map,"wXNewsItemsService","getById");
		Map<String, Object> mapObject = (Map<String, Object>)outputObject.getObject();
		String content  = (String)mapObject.get("content");
		String title  = (String)mapObject.get("title");
		if(!"".equals(content) && null != content){
			content = replaceHTMLEntity(content);
			title=StringUtilLY.replaceHTMLEntity(title);
			mapObject.put("content", content);
			mapObject.put("title", title);
		}
		outputObject.setBean(mapObject);
		return outputObject;
	}
	/**
	 * 查看所有微信图文详情
	 * @param "WXNewsItemsForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("WXNewsItemsForm") WXNewsItemsForm wXNewsItemsForm,BindingResult result,
			HttpServletRequest request, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsItemsForm);	
		outputObject = getOutputObject(map,"wXNewsItemsService","getAll");
		List<Map<String,Object>> newsItemlist=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> newsItemMap:newsItemlist){
			newsItemMap.put("imagePath", contextPath+"/viewImage/viewImage?imgPath="+newsItemMap.get("imagePath"));
			
		}
		return outputObject;
	}
	/**
	 * 添加微信图文详情
	 * @param WXNewsItemsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertWXNewsItems")
	public OutputObject insertWXNewsItems(@ModelAttribute("WXNewsItemsForm") @Valid WXNewsItemsForm wXNewsItemsForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			String createUser = (String)((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName");
			wXNewsItemsForm.setCreateUser(createUser);
			String content = wXNewsItemsForm.getContent();
			if(!"".equals(content) && null != content){
				content = replaceHTMLEntity(content);
				wXNewsItemsForm.setContent(content);
			}
			Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsItemsForm);
			outputObject = getOutputObject(map, "wXNewsItemsService", "insertWXNewsItems");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("微信图文详情添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑微信图文详情
	 * @param WXNewsItemsForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateWXNewsItems")
	public OutputObject updateWXNewsItems(@ModelAttribute("WXNewsItemsForm") @Valid WXNewsItemsForm wXNewsItemsForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		String content = wXNewsItemsForm.getContent();
		if(!"".equals(content) && null != content){
			content = replaceHTMLEntity(content);
			wXNewsItemsForm.setContent(content);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsItemsForm);
		outputObject = getOutputObject(map, "wXNewsItemsService", "updateWXNewsItems");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信图文详情编辑成功!");
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
		mav.setViewName("weixin/add-newsitems");
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
		outputObject = getOutputObject(map,"wXNewsItemsService","selectByPrimaryKey");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-newsitems");
		return mav;
	}
	/**
	 * 删除微信图文详情
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteWXNewsItems")
	public OutputObject deleteWXNewsItems(@ModelAttribute("WXNewsItemsForm") WXNewsItemsForm wXNewsItemsForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsItemsForm);
		outputObject = getOutputObject(map, "wXNewsItemsService", "deleteWXNewsItems");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("微信图文详情删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除微信图文详情
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteWXNewsItems")
	public OutputObject logicDeleteWXNewsItems(@ModelAttribute("WXNewsItemsForm") WXNewsItemsForm wXNewsItemsForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(wXNewsItemsForm);
		outputObject = getOutputObject(map, "wXNewsItemsService", "logicDeleteWXNewsItems");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	/**
	 * 去往图文详情编辑页面
	 * @return
	 */
	@RequestMapping(value = "newsItemEdit")
	public ModelAndView newsItemEdit(HttpServletRequest request,Model model) {
		ModelAndView mav=new ModelAndView();
		model.addAttribute("itemId", request.getParameter("itemId"));
		model.addAttribute("htmlTittle", request.getParameter("htmlTittle"));
		model.addAttribute("htmlHostTittle", request.getParameter("htmlHostTittle"));
		mav.setViewName("weixin/newsitemedit");
		return mav;
	}
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @param model
	 * @param mm
	 */
	@ResponseBody
	@RequestMapping(value = "doUpload")
	public OutputObject doUpload(HttpServletRequest request, HttpServletResponse response, Model model,ModelMap mm) {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile=upload(uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("imagePath", uploadFile.getTitleField());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		return outputObject;
	}
	@SuppressWarnings("unchecked")
	public  UploadFile upload(UploadFile uploadFile) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path;// 文件的硬盘真实路径
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
				String savePath = realPath + fileName;// 文件保存全路径
				File savefile = new File(savePath);
					// 设置文件数据库的物理路径
					uploadFile.setRealPath(realPath + fileName);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
	/**
	 * html转意字符转换
	 * @param originalStr
	 * @return
	 */
	private String replaceHTMLEntity(String originalStr){
		originalStr = originalStr.replaceAll("amp;", "");
		originalStr = originalStr.replaceAll("&lt;", "<");
		originalStr = originalStr.replaceAll("&gt;", ">");
		originalStr = originalStr.replaceAll("&quot;", "\"");
		originalStr = originalStr.replaceAll("&nbsp;", " ");
		originalStr = originalStr.replaceAll("&ldquo;", "“");
		originalStr = originalStr.replaceAll("&rdquo;", "”");
		originalStr = originalStr.replaceAll("&middot;", "·");
		originalStr = originalStr.replaceAll("&amp;nbsp;", " ");
		originalStr = originalStr.replaceAll("&amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;amp;ldquo;", "“");
		originalStr = originalStr.replaceAll("&amp;amp;rdquo;", "”");
		originalStr = originalStr.replaceAll("&amp;rdquo;", "”");
		return originalStr;
	}
}
