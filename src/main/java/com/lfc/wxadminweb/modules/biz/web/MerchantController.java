package com.lfc.wxadminweb.modules.biz.web;


import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.Log;
import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.exportexcel.ExportCardAppliData;
import com.lfc.wxadminweb.common.exportexcel.ExportMerchantData;
import com.lfc.wxadminweb.common.importexcel.ImportExcel;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.biz.form.CardApplierForm;
import com.lfc.wxadminweb.modules.biz.form.MerchantForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author star
 * @date 2016-11-02 16:36
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/merchant")
public class MerchantController extends BaseController{
	@Value("#{system.picture_path}") 
	private  String path;	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils
						.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});

		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd"), false));
		
	}
	
	/**
	 * 跳转到特惠商户信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/merchant/rchant-list");
		return mv;
	}
	/**
	 * 分页查询特惠商户信息列表
	 * @param MerchantForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("merchantForm") MerchantForm merchantForm,
			BindingResult result,HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		HashMap<String, Object> user = (HashMap<String, Object>) getSession().getAttribute(Constants.USER_SESSION.USER);
		Map<String, Object> inmap = new HashMap<String, Object>();
		String userId = user.get("id").toString();
		inmap.put("userId", userId);
		OutputObject outPutObjectUse = getOutputObject(inmap, "managerService", "getUserDepartCodeByUserId");
		List<Map<String, Object>> info = outPutObjectUse.getBeans();
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);
		if(info.isEmpty()==false){
			for (int i = 0; i < info.size(); i++) {
				String roleCode = info.get(i).get("roleCode").toString();
				String departCode = info.get(i).get("departCode").toString();
				if(roleCode.equals("MERCHANT_CITY_MNG")){
					map.put("cityCode", departCode);
				}
			}
		}
		OutputObject outputObject = null;
		outputObject = getOutputObject(map, "merchantService", "getList");
		List<Map<String,Object>> merchs=outputObject.getBeans();
		String contextPath = request.getContextPath();
		for(Map<String,Object> merch:merchs){
			merch.put("startDate", merch.get("startDate").toString().substring(0, 10));
			merch.put("endDate", merch.get("startDate").toString().substring(0, 10));
			if(merch.get("imagePath")==null){
				merch.put("imagePath", contextPath+"/viewImage/viewImage?imgPath=defaultf29b7fcd44314b7b7540e45d5f37a.jpg");				
			}else{
				merch.put("imagePath", contextPath+"/viewImage/viewImage?imgPath=merchant/"+merch.get("imagePath"));
			}
			if(merch.get("bannerImg")==null){
				merch.put("bannerImg", contextPath+"/viewImage/viewImage?imgPath=defaultf29b7fcd44314b7b7540e45d5f37a.jpg");
			}else{
				merch.put("bannerImg", contextPath+"/viewImage/viewImage?imgPath=merchant/"+merch.get("bannerImg"));
			}
			//System.out.println(merch.get("imagePath"));
		}
		return outputObject;
	}
	/**
	 *根据ID查询特惠商户信息
	 * @param MerchantForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("MerchantForm") MerchantForm merchantForm,BindingResult result, Model model,ModelMap mm,HttpServletRequest request) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);	
		outputObject = getOutputObject(map,"merchantService","getById");
		Map<String,Object> merch=(Map<String, Object>) outputObject.getObject();
		String contextPath = request.getContextPath();		
			if(merch.get("imagePath")==null){
				merch.put("showPath", contextPath+"/viewImage/viewImage?imgPath=defaultf29b7fcd44314b7b7540e45d5f37a.jpg");
			}else{
				merch.put("showPath", contextPath+"/viewImage/viewImage?imgPath=merchant/"+merch.get("imagePath"));
			}
			if(merch.get("bannerImg")==null){
				merch.put("showPath2", contextPath+"/viewImage/viewImage?imgPath=defaultf29b7fcd44314b7b7540e45d5f37a.jpg");
			}else{
				merch.put("showPath2", contextPath+"/viewImage/viewImage?imgPath=merchant/"+merch.get("bannerImg"));
			}
		return outputObject;
	}
	
	/**
	 *根据ID查询特惠商户详细信息
	 * @param MerchantForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getDetail")
	public OutputObject getDetail(@ModelAttribute("MerchantForm") MerchantForm merchantForm,BindingResult result, Model model,ModelMap mm,HttpServletRequest request) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);	
		outputObject = getOutputObject(map,"merchantService","getDetail");
		Map<String,Object> merch=(Map<String, Object>) outputObject.getObject();
		String contextPath = request.getContextPath();		
			if(merch.get("imagePath")==null){
				merch.put("showPath", contextPath+"/viewImage/viewImage?imgPath=defaultf29b7fcd44314b7b7540e45d5f37a.jpg");
			}else{
				merch.put("showPath", contextPath+"/viewImage/viewImage?imgPath=merchant/"+merch.get("imagePath"));
			}
			if(merch.get("bannerImg")==null){
				merch.put("showPath2", contextPath+"/viewImage/viewImage?imgPath=defaultf29b7fcd44314b7b7540e45d5f37a.jpg");
			}else{
				merch.put("showPath2", contextPath+"/viewImage/viewImage?imgPath=merchant/"+merch.get("bannerImg"));
			}
		return outputObject;
	}
	/**
	 * 查看所有特惠商户信息
	 * @param "MerchantForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("MerchantForm") MerchantForm merchantForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);	
		outputObject = getOutputObject(map,"merchantService","getAll");
		return outputObject;
	}
	/**
	 * 添加特惠商户信息
	 * @param MerchantForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertMerchant")
	public OutputObject insertMerchant(@ModelAttribute("MerchantForm") @Valid MerchantForm merchantForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);
			map.put("createUser", ((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName"));
			outputObject = getOutputObject(map, "merchantService", "insertMerchant");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("特惠商户信息添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑特惠商户信息
	 * @param MerchantForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateMerchant")
	public OutputObject updateMerchant(@ModelAttribute("MerchantForm") @Valid MerchantForm merchantForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);
		outputObject = getOutputObject(map, "merchantService", "updateMerchant");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("特惠商户信息编辑成功!");
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
		mav.setViewName("weixin/add-rchant");
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
		outputObject = getOutputObject(map,"merchantService","getById");
		model.addAttribute("output", outputObject);
		mav.setViewName("weixin/edit-rchant");
		return mav;
	}
	/**
	 * 删除特惠商户信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteMerchant")
	public OutputObject deleteMerchant(@ModelAttribute("MerchantForm") MerchantForm merchantForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);
		outputObject = getOutputObject(map, "merchantService", "deleteMerchant");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("特惠商户信息删除成功!");
		}
		return outputObject;
	}
	/**
	 * 逻辑删除特惠商户信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "logicDeleteMerchant")
	public OutputObject logicDeleteMerchant(@ModelAttribute("MerchantForm") MerchantForm merchantForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);
		outputObject = getOutputObject(map, "merchantService", "logicDeleteMerchant");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	/**
	 * 查询特惠商户类型信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMerchantType")
	public OutputObject getMerchantType(@ModelAttribute("MerchantForm") MerchantForm merchantForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);
		outputObject = getOutputObject(map, "merchantService", "getMerchantType");
		return outputObject;
	}
	
	
	/**
	 * 查询特惠商户状态信息
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMerchantState")
	public OutputObject getMerchantState(@ModelAttribute("MerchantForm") MerchantForm merchantForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);
		outputObject = getOutputObject(map, "merchantService", "getMerchantState");
		return outputObject;
	}
	/**
	 * 导入数据
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "importMerchant")
	public OutputObject importMerchantInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		UploadFile uploadFile = new UploadFile(request, response);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>+掉一次方法-------------");
		uploadFile.setCusPath("merchant");//上传文件的目录 可以多级如xx/xx/xx
		uploadFile=UploadUtil.uploadFile(uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
		MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<Map<String,Object>> merchantList=new ArrayList<Map<String,Object>>();
		Sheet sheet=null;
		Row row=null;
		DecimalFormat df = new DecimalFormat("0");
		logger.info("------个数--------->>>>>>>>>>"+fileMap.size());
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
				logger.info("---------------共"+sheets+"张工作薄--------");
				for (int i = 0; i < sheets; i++) {
					sheet  = wb.getSheetAt(i);//取第i个工作薄
					int lastRowNum=sheet.getLastRowNum();//总行数
					logger.info("---------------共"+lastRowNum+"行--------");
					//二分名称
					String cityCode = ImportExcel.getStringCellValue((Cell)sheet.getRow(1).getCell(1));
					logger.info("-------cityCode--------"+cityCode);
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("departName", cityCode.trim());
					m.put("levelRank", "3");
					OutputObject outputDepart = getOutputObject(m,"merchantService","getByDepartName");
					Map<String,Object> merchan = (Map<String,Object>)(outputDepart.getObject());
					for (int j = 1; j <=lastRowNum; j++) {//从1开始,跳过表头
						row = sheet.getRow(j);//开始读行数据
						if (row!=null&&!ImportExcel.isBlankRow(row)) {		
							Map<String,Object> merchantMap= new HashMap<String, Object>();
							if(merchan != null){
								merchantMap.put("cityCode",merchan.get("id"));
							}
							merchantMap.put("name",ImportExcel.getStringCellValue((Cell)row.getCell(3)));//商户名称
							merchantMap.put("type",ImportExcel.getStringCellValue((Cell)row.getCell(4)));//商户类型
							merchantMap.put("supportedCards",ImportExcel.getStringCellValue((Cell)row.getCell(5)));//支持卡类型
							merchantMap.put("address",ImportExcel.getStringCellValue((Cell)row.getCell(6)));//商户地址
							
							String phon =  ImportExcel.getStringCellValue((Cell)row.getCell(7));
							try{
								Double phones = Double.parseDouble(phon); //将获取到的值转换为Double 
								String phone = df.format(phones);
								merchantMap.put("tactorPhone",phone);
							}catch(NumberFormatException e){
								merchantMap.put("tactorPhone",phon);
							}
							
							merchantMap.put("disaccount",ImportExcel.getStringCellValue((Cell)row.getCell(8))); //优惠折扣
							//起止日期
							String timeStr = ImportExcel.getStringCellValue((Cell)row.getCell(9));
							String[] arr = null;
							String begin = null;
							String end = null;
							//根据日期不同类型进行分割
							if(timeStr.contains("-") && (timeStr.indexOf("-") == timeStr.lastIndexOf("-"))){
								arr = timeStr.split("-");
								logger.info(">>>>>>>>>>>>>>>-");
								begin = timeFormate(arr[0]);
								merchantMap.put("startDate",begin);
								end = timeFormate(arr[1]);
								merchantMap.put("endDate",end);
							}else if(timeStr.contains("--")){
								arr = timeStr.split("--");
								logger.info(">>>>>>>>>>>>>>>--");
								begin = timeFormate(arr[0]);
								merchantMap.put("startDate",begin);
								end = timeFormate(arr[1]);
								merchantMap.put("endDate",end);
							}else if(timeStr.contains("至")){
								arr = timeStr.split("至");
								logger.info(">>>>>>>>>>>>>>>至");
								begin = timeFormate(arr[0]);
								merchantMap.put("startDate",begin);
								end = timeFormate(arr[1]);
								merchantMap.put("endDate",end);
							}else if(timeStr.indexOf("—") == timeStr.lastIndexOf("—") && !timeStr.contains("－")
									&& timeStr.contains("—")){
								arr = timeStr.split("—");
								begin = timeFormate(arr[0]);
								merchantMap.put("startDate",begin);
								end = timeFormate(arr[1]);
								merchantMap.put("endDate",end);
							}else if(timeStr.contains("－") && timeStr.indexOf("－") == timeStr.lastIndexOf("－")){
								arr = timeStr.split("－");
								logger.info(">>>>>>>>>>>>>>>－");
								begin = timeFormate(arr[0]);
								merchantMap.put("startDate",begin);
								end = timeFormate(arr[1]);
								merchantMap.put("endDate",end);
							}else if(timeStr.contains("－－")){
								arr = timeStr.split("－－");
								logger.info(">>>>>>>>>>>>>>>－－");
								begin = timeFormate(arr[0]);
								merchantMap.put("startDate",begin);
								end = timeFormate(arr[1]);
								merchantMap.put("endDate",end);
							}else if(timeStr.contains("——")){
								arr = timeStr.split("——");
								logger.info(">>>>>>>>>>>>>>>——");
								begin = timeFormate(arr[0]);
								merchantMap.put("startDate",begin);
								end = timeFormate(arr[1]);
								merchantMap.put("endDate",end);
							}
							merchantList.add(merchantMap);
					       }
				       }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OutputObject outputObject=new OutputObject();
		map.put("merchantList", merchantList);
		outputObject = getOutputObject(map, "merchantService", "importMerchant");
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		return outputObject;
	}
	
	/**
	 * 格式化起止日期
	 * @param time  :起止日期
	 * @return		:返回格式化后日期字符串
	 * @throws ParseException 
	 */
	private  String timeFormate(String time){
		String date = null;
		DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat f2 = new SimpleDateFormat("yyyy年MM月dd日");
		DateFormat f3 = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat f4 = new SimpleDateFormat("yyyy.MM.dd");
		DateFormat f5 = new SimpleDateFormat("yyyyMMdd");
		try{
		if(time.contains("-")){
			date = f1.format(f1.parse(time));
		}else if(time.contains("日")){
			date = f1.format(f2.parse(time));
		}else if(time.contains("/")){
			date = f1.format(f3.parse(time));
		}else if(time.contains(".")){
			date = f1.format(f4.parse(time));
		}else{
			date = f1.format(f5.parse(time));
		}
		return date;
		}catch(Exception e){
			logger.info("------格式化异常------");
			return date;
		}
	}
	@RequestMapping(value = "exportData")
	public void exportData(@ModelAttribute("MerchantForm") MerchantForm merchantForm,HttpServletRequest request, HttpServletResponse response){
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(merchantForm);
		outputObject = getOutputObject(map, "merchantService", "getAll");//根据查询条件，导出所有，没有分页
		List<Map<String,Object>> merchs=outputObject.getBeans();
		for(Map<String,Object> merch:merchs){
			if(merch.get("imagePath")==null){
				merch.put("imagePath", "E:/img/defaultf29b7fcd44314b7b7540e45d5f37a.jpg");
			}else{
				merch.put("imagePath", "E:/img/merchant/"+merch.get("imagePath"));
			}
		}
		try {
			ExportMerchantData.exportData(merchs, request, response);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			response.setHeader("err_msg", "下载excl文件出现异常");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		} 
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
		System.out.println(outputObject+"------------->");
		return outputObject;
	}
	
	@SuppressWarnings("unchecked")
	public  UploadFile upload(HttpServletRequest request,UploadFile uploadFile) {
		try {
			uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			// 文件数据库保存路径
			String realPath =path+"merchant/";// 文件的硬盘真实路径
			System.out.println("------path-----"+path);
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
					String showImgPath=contextPath+"/viewImage/viewImage?imgPath=merchant/"+fileName;
					uploadFile.setRealPath(showImgPath);
					uploadFile.setTitleField(fileName);
                    FileCopyUtils.copy(mf.getBytes(), savefile);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return uploadFile;
	}
	
	/*public static void main(String[] args) {
		String timeStr = "2015年10月15日－－2016.4.15日";
		String[] arr = null;
		String begin = null;
		String end = null;
		System.out.println(">>>>>>>>>>>>>>>>>"+(!timeStr.contains("－")));
		System.out.println("---------"+(timeStr.indexOf("—") == timeStr.lastIndexOf("—")));
			//根据日期不同类型进行分割
			if(timeStr.contains("-") && timeStr.indexOf("-") == timeStr.lastIndexOf("-")){
				arr = timeStr.split("-");
				System.out.println(">>>>>>>>>>>>>>>-");
				begin = timeFormate(arr[0]);
				System.out.println("----begin----"+begin);
				end = timeFormate(arr[1]);
				System.out.println("----end----"+end);
			}else if(timeStr.contains("--")){
				arr = timeStr.split("--");
				System.out.println(">>>>>>>>>>>>>>>--");
				begin = timeFormate(arr[0]);
				System.out.println("----begin----"+begin);
				end = timeFormate(arr[1]);
				System.out.println("----end----"+end);
			}else if(timeStr.contains("至")){
				arr = timeStr.split("至");
				System.out.println(">>>>>>>>>>>>>>>至");
				begin = timeFormate(arr[0]);
				System.out.println("----begin----"+begin);
				end = timeFormate(arr[1]);
				System.out.println("----end----"+end);
			}else if(timeStr.indexOf("—") == timeStr.lastIndexOf("—") && !timeStr.contains("－") && timeStr.contains("—")){
				arr = timeStr.split("—");
				begin = timeFormate(arr[0]);
				System.out.println("----begin----"+begin);
				end = timeFormate(arr[1]);
				System.out.println("----end----"+end);
			}else if(timeStr.contains("－") && timeStr.indexOf("－") == timeStr.lastIndexOf("－")){
				arr = timeStr.split("－");
				System.out.println(">>>>>>>>>>>>>>>－");
				begin = timeFormate(arr[0]);
				System.out.println("----begin----"+begin);
				end = timeFormate(arr[1]);
				System.out.println("----end----"+end);
			}else if(timeStr.contains("－－")){
				arr = timeStr.split("－－");
				System.out.println(">>>>>>>>>>>>>>>－－");
				begin = timeFormate(arr[0]);
				System.out.println("----begin----"+begin);
				end = timeFormate(arr[1]);
				System.out.println("----end----"+end);
			}else if(timeStr.contains("——")){
				arr = timeStr.split("——");
				System.out.println(">>>>>>>>>>>>>>>——");
				begin = timeFormate(arr[0]);
				System.out.println("----begin----"+begin);
				end = timeFormate(arr[1]);
				System.out.println("----end----"+end);
			}
		
		String phon = "0377-61560000";
		DecimalFormat df = new DecimalFormat("0");
		try{
			Double phones = Double.parseDouble(phon); //将获取到的值转换为Double 
			String phone = df.format(phones);
			System.out.println("----"+phone);
		}catch(NumberFormatException e){
			System.out.println(phon);
		}
	}*/
}
