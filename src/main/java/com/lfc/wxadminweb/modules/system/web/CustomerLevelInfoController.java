package com.lfc.wxadminweb.modules.system.web;


import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.system.form.CustomerLevelInfoForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author zhaoyan
 * @date 2016-12-05 09:47
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("system/customerLevelInfo")
public class CustomerLevelInfoController extends BaseController{
	
	
	
	/**
	 * 跳转到邮储个金部客户等级信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("system/customerInfo/stomerlevelinfo-list");
		return mv;
	}
	/**
	 * 分页查询邮储个金部客户等级信息列表
	 * @param CustomerLevelInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("customerLevelInfoForm") CustomerLevelInfoForm customerLevelInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(customerLevelInfoForm);
		outputObject = getOutputObject(map, "customerLevelInfoService", "getList");
		return outputObject;
	}
	/**
	 *根据ID查询邮储个金部客户等级信息
	 * @param CustomerLevelInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("CustomerLevelInfoForm") CustomerLevelInfoForm customerLevelInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(customerLevelInfoForm);	
		System.out.println(map+"---------------param");
		outputObject = getOutputObject(map,"customerLevelInfoService","getById");
		System.out.println(outputObject+"-----------------result");
		return outputObject;
	}
	/**
	 * 查看所有邮储个金部客户等级信息
	 * @param "CustomerLevelInfoForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("CustomerLevelInfoForm") CustomerLevelInfoForm customerLevelInfoForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(customerLevelInfoForm);	
		outputObject = getOutputObject(map,"customerLevelInfoService","getAll");
		return outputObject;
	}
	/**
	 * 添加邮储个金部客户等级信息
	 * @param CustomerLevelInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertCustomerLevelInfo")
	public OutputObject insertCustomerLevelInfo(@ModelAttribute("CustomerLevelInfoForm") @Valid CustomerLevelInfoForm customerLevelInfoForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(customerLevelInfoForm);
//			#addCreateUser#
			outputObject = getOutputObject(map, "customerLevelInfoService", "insertCustomerLevelInfo");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("邮储个金部客户等级信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑邮储个金部客户等级信息
	 * @param CustomerLevelInfoForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateCustomerLevelInfo")
	public OutputObject updateCustomerLevelInfo(@ModelAttribute("CustomerLevelInfoForm") @Valid CustomerLevelInfoForm customerLevelInfoForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(customerLevelInfoForm);
		outputObject = getOutputObject(map, "customerLevelInfoService", "updateCustomerLevelInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("邮储个金部客户等级信息编辑成功!");
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
		mav.setViewName("weixin/add-stomerlevelinfo");
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
		outputObject = getOutputObject(map,"customerLevelInfoService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-stomerlevelinfo");
		return mav;
	}
	/**
	 * 删除邮储个金部客户等级信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteCustomerLevelInfo")
	public OutputObject deleteCustomerLevelInfo(@ModelAttribute("CustomerLevelInfoForm") CustomerLevelInfoForm customerLevelInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(customerLevelInfoForm);
		outputObject = getOutputObject(map, "customerLevelInfoService", "deleteCustomerLevelInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("邮储个金部客户等级信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除邮储个金部客户等级信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteCustomerLevelInfo")
	public OutputObject logicDeleteCustomerLevelInfo(@ModelAttribute("CustomerLevelInfoForm") CustomerLevelInfoForm customerLevelInfoForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(customerLevelInfoForm);
		outputObject = getOutputObject(map, "customerLevelInfoService", "logicDeleteCustomerLevelInfo");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	
	/**
	 * 上传个金部客户等级信息
	 * */
	@ResponseBody
	@RequestMapping(value = "importMerchant")
	public OutputObject importMerchant(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile.setCusPath("merchant");//上传文件的目录 可以多级如xx/xx/xx
		uploadFile=UploadUtil.uploadFile(uploadFile);
		uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
		MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<Map<String,Object>> merchantList=new ArrayList<Map<String,Object>>();
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
				int sheets = wb.getNumberOfSheets();//获得sheet个数
				for (int i = 0; i < sheets; i++) {
					sheet  = wb.getSheetAt(i);//取第i个工作薄
					int lastRowNum=sheet.getLastRowNum();//总行数
					for (int j = 1; j <=lastRowNum; j++) {//从1开始,跳过表头
						row = sheet.getRow(j);//开始读行数据
						if (row!=null&&!ImportExcel.isBlankRow(row)) {		
							Map<String,Object> merchantMap= new HashMap<String, Object>();
							merchantMap.put("customerName", ImportExcel.getStringCellValue((Cell)row.getCell(0)).trim());//客户名称
							merchantMap.put("customerLevel", ImportExcel.getStringCellValue((Cell)row.getCell(1)).trim());//客户等级
							merchantMap.put("levelName", ImportExcel.getStringCellValue((Cell)row.getCell(2)).trim());//卡种级别
							merchantMap.put("phoneNumber", ImportExcel.getStringCellValue((Cell)row.getCell(3)).trim());//客户名称
							merchantList.add(merchantMap);
					       }
				       }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OutputObject outputObject=new OutputObject();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("customerLevelList", merchantList);
		outputObject = getOutputObject(map, "customerLevelInfoService", "importCustomerLevelInfo");
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		return outputObject;
	}
	
	
}
