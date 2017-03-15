package com.lfc.wxadminweb.modules.system.web;

import java.util.HashMap;
import java.util.List;
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
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.system.form.MenuForm;

@Controller
@RequestMapping(value = "system/upload")
public class UploadController extends BaseController {

	/**
	 * 去往demo
	 * @return
	 */
	@RequestMapping(value = "upload")
	public ModelAndView upload() {
		ModelAndView mav=new ModelAndView();
		mav.setViewName("system/uploaddemo/uploaddemo");
		return mav;
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
		uploadFile.setCusPath("system");//上传文件的目录 可以多级如xx/xx/xx
		uploadFile=UploadUtil.uploadFile(uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		OutputObject outputObject=new OutputObject();
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		return outputObject;
	}


}
