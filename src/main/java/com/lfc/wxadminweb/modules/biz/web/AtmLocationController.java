package com.lfc.wxadminweb.modules.biz.web;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.importexcel.ImportExcel;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.AtmLocationForm;
import com.lfc.wxadminweb.modules.biz.form.FinanceProdForm;


/**
 * <h2></br>
 * 
 * @descript 
 * @author helei
 * @date 2016-10-27 11:37
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("/biz/atm")
public class AtmLocationController extends BaseController{
	
	
	
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/atm/atm-list");
		return mv;
	}
	
	
	/**
	 * 分页查询列表
	 * @param FinanceProdForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("atmLocationForm") AtmLocationForm atmLocationForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(atmLocationForm);
		outputObject = getOutputObject(map, "atmLocationService", "getList");
		return outputObject;
	}
	
	
	/**
	 *根据ID查询实体
	 * @param FinanceProdForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("financeProdForm") FinanceProdForm financeProdForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);	
		outputObject = getOutputObject(map,"financeProdService","getById");
		return outputObject;
	}
	
	
	/**
	 * 查看所有实体
	 * @param "FinanceProdForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("financeProdForm") FinanceProdForm financeProdForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(financeProdForm);	
		outputObject = getOutputObject(map,"financeProdService","getAll");
		return outputObject;
	}
	
	
	/**
	 * 添加实体
	 * @param atmLocationForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertObj")
	public OutputObject insertObj(@ModelAttribute("atmLocationForm") @Valid AtmLocationForm atmLocationForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(atmLocationForm);
		map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
		outputObject = getOutputObject(map, "atmLocationService", "insertObj");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("添加成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 编辑实体
	 * @param atmLocationForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateObj")
	public OutputObject updateObj(@ModelAttribute("atmLocationForm") @Valid AtmLocationForm atmLocationForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(atmLocationForm);
		outputObject = getOutputObject(map, "atmLocationService", "updateObj");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("修改成功!");
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
		mav.setViewName("biz/atm/add-atm");
		return mav;
	}
	
	
	/**
	 * 去往查看页面
	 * @return
	 */
	@RequestMapping(value = "view")
	public ModelAndView view(Model model) {
		ModelAndView mav=new ModelAndView();
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = getRequest();
		map.put("atmId", request.getParameter("id"));
		outputObject = getOutputObject(map,"atmLocationService","getById");
		model.addAttribute("obj", outputObject.getObject());
		mav.setViewName("biz/atm/view-atm");
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
		map.put("atmId", request.getParameter("id"));
		outputObject = getOutputObject(map,"atmLocationService","getById");
		model.addAttribute("obj", outputObject.getObject());
		mav.setViewName("biz/atm/edit-atm");
		return mav;
	}
	
	
	
	/**
	 * 逻辑删除实体
	 * @param atmLocationForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDelObj")
	public OutputObject logicDelFinanceProd(@ModelAttribute("atmLocationForm") AtmLocationForm atmLocationForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(atmLocationForm);
		outputObject = getOutputObject(map, "atmLocationService", "logicDelObj");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 跳转到附近的ATM页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "nlist")
	public ModelAndView nlist( ModelAndView mv) {
		mv.setViewName("biz/atm/nearbyatm-list");
		return mv;
	}
	/**
	 * 查询附近的ATM信息
	 * @param request
	 * @param mv
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getNList")
	public OutputObject getNList(@ModelAttribute("atmLocationForm") AtmLocationForm atmLocationForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(atmLocationForm);
		outputObject = getOutputObject(map, "atmLocationService", "getNearbyAtmList");
		return outputObject;
	}
	/**
	 * 导入的ATM信息
	 * @param request
	 * @param mv
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@ResponseBody
	@RequestMapping(value = "importAtm")
	public OutputObject importAtm(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile.setCusPath("atm");//上传文件的目录 可以多级如xx/xx/xx
		uploadFile=UploadUtil.uploadFile(uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
		MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<Map<String,Object>> atmL=new ArrayList<Map<String,Object>>();
		Sheet sheet=null;
		Row row=null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			try {
				Workbook wb = null;
				if (file.getOriginalFilename().endsWith("xls")) {
					wb = new HSSFWorkbook(file.getInputStream());
				} else {
					wb = new XSSFWorkbook(file.getInputStream());// xlsx
				}
				for (int i = 0; i < 1; i++) {
					sheet  = wb.getSheetAt(i);//取第一个工作薄
					int lastRowNum=sheet.getLastRowNum();//总行数					
					for (int j = 1; j <=lastRowNum; j++) {//从1开始,跳过表头
						row = sheet.getRow(j);//开始读行数据
						if (row!=null&&!ImportExcel.isBlankRow(row)) {		
							Map<String,Object> atmMap= new HashMap<String, Object>();
							atmMap.put("departName",ImportExcel.getStringCellValue((Cell)row.getCell(1)));
							atmMap.put("address",ImportExcel.getStringCellValue((Cell)row.getCell(2)));
							atmMap.put("longitude",ImportExcel.getStringCellValue((Cell)row.getCell(3)));
							atmMap.put("latitude",ImportExcel.getStringCellValue((Cell)row.getCell(4)));
							atmMap.put("deleteFlag", 0);
							atmMap.put("createTime", new Date());
							atmL.add(atmMap);
					       }
				       }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OutputObject outputObject=new OutputObject();
		map.put("atmList", atmL);
		outputObject = getOutputObject(map, "atmLocationService", "importAtmLocationInfo");
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		return outputObject;
	}
}
