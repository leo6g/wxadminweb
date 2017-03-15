package com.lfc.wxadminweb.modules.biz.web.award;


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

import com.github.sd4324530.fastweixin.util.CollectionUtil;
import com.lfc.core.bean.OutputObject;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.importexcel.ImportExcel;
import com.lfc.core.util.BeanUtil;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.wxadminweb.common.web.BaseController;


import com.lfc.wxadminweb.modules.biz.form.award.TBizAwardPlayerForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author star
 * @date 2017-02-04 16:01
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("biz/awardPlayer")
public class TBizAwardPlayerController extends BaseController{
	
	
	
	/**
	 * 跳转到抽奖人员资格信息表列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list( ModelAndView mv) {
		mv.setViewName("biz/award/awardplayer-list");
		return mv;
	}
	/**
	 * 分页查询抽奖人员资格信息表列表
	 * @param TBizAwardPlayerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("tBizAwardPlayerForm") TBizAwardPlayerForm tBizAwardPlayerForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(tBizAwardPlayerForm);
		outputObject = getOutputObject(map, "tBizAwardPlayerService", "getList");
		return outputObject;
	}
	
	/**
	 * 查询抽奖期次
	 * @param TBizAwardPlayerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSeriesNumList")
	public OutputObject getSeriesNumList(@ModelAttribute("tBizAwardPlayerForm") TBizAwardPlayerForm tBizAwardPlayerForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(tBizAwardPlayerForm);
		outputObject = getOutputObject(map, "tBizAwardPlayerService", "getSeriesNumList");
		return outputObject;
	}
	/**
	 *根据ID查询抽奖人员资格信息表
	 * @param TBizAwardPlayerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getById")
	public OutputObject getById(@ModelAttribute("TBizAwardPlayerForm") TBizAwardPlayerForm tBizAwardPlayerForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(tBizAwardPlayerForm);	
		outputObject = getOutputObject(map,"tBizAwardPlayerService","getById");
		return outputObject;
	}
	/**
	 * 查看所有抽奖人员资格信息表
	 * @param "TBizAwardPlayerForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAll")
	public OutputObject getAll(@ModelAttribute("TBizAwardPlayerForm") TBizAwardPlayerForm tBizAwardPlayerForm,BindingResult result, Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(tBizAwardPlayerForm);	
		outputObject = getOutputObject(map,"tBizAwardPlayerService","getAll");
		return outputObject;
	}
	/**
	 * 添加抽奖人员资格信息表
	 * @param TBizAwardPlayerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertTBizAwardPlayer")
	public OutputObject insertTBizAwardPlayer(@ModelAttribute("TBizAwardPlayerForm") @Valid TBizAwardPlayerForm tBizAwardPlayerForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
				return returnValidatorAjaxResult(result);
			}
			OutputObject outputObject = null;
			Map<String, Object> map = BeanUtil.convertBean2Map(tBizAwardPlayerForm);
			outputObject = getOutputObject(map, "tBizAwardPlayerService", "insertTBizAwardPlayer");
			if(outputObject.getReturnCode().equals("0")){
				outputObject.setReturnMessage("抽奖人员资格信息表添加成功!");
			}
			return outputObject;
	}
	/**
	 * 编辑抽奖人员资格信息表
	 * @param TBizAwardPlayerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateTBizAwardPlayer")
	public OutputObject updateTBizAwardPlayer(@ModelAttribute("TBizAwardPlayerForm") @Valid TBizAwardPlayerForm tBizAwardPlayerForm,BindingResult result, Model model,ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(tBizAwardPlayerForm);
		outputObject = getOutputObject(map, "tBizAwardPlayerService", "updateTBizAwardPlayer");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("抽奖人员资格信息表编辑成功!");
		}
		return outputObject;
	}
	/**
	 * 删除抽奖人员资格信息表
	 * @param departForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteTBizAwardPlayer")
	public OutputObject deleteTBizAwardPlayer(@ModelAttribute("TBizAwardPlayerForm") TBizAwardPlayerForm tBizAwardPlayerForm,
			BindingResult result,  Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(tBizAwardPlayerForm);
		outputObject = getOutputObject(map, "tBizAwardPlayerService", "deleteTBizAwardPlayer");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("抽奖人员资格信息表删除成功!");
		}
		return outputObject;
	}
	

	/**
	 * 获取Excel表的抽奖员工信息
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "getAwardPlayer")
	public OutputObject getAwardPlayer(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile.setCusPath("award");//上传文件的目录 可以多级如xx/xx/xx
		uploadFile=UploadUtil.uploadFile(uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
		MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<Map<String,Object>> awardList=new ArrayList<Map<String,Object>>();
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
							Map<String,Object> userMap= new HashMap<String, Object>();
							userMap.put("name",ImportExcel.getStringCellValue((Cell)row.getCell(0)));
							DecimalFormat df = new DecimalFormat("#");  
							//String phoneNumber =ImportExcel.getStringCellValue((Cell)row.getCell(1)); 
							String phoneNumber = df.format(row.getCell(1).getNumericCellValue());
							userMap.put("phoneNumber",phoneNumber);
							userMap.put("playFlag","F");
							awardList.add(userMap);
					       }
				       }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OutputObject outputObject=new OutputObject();
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		getSession().setAttribute("awardList", awardList);
		System.out.println(awardList.size()+"awardList1");
		outputObject.setBean(map);
		return outputObject;
		
	}
	
	
	/**
	 * 批量插入抽奖人员信息
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "insertAwardPlayer")
	public OutputObject insertAwardPlayer(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		OutputObject outputObject=new OutputObject();
		Map<String,Object> map=new HashMap<String,Object>();
		List<Map<String,Object>> awardList=new ArrayList<Map<String,Object>>();
		awardList=(List<Map<String, Object>>) getSession().getAttribute("awardList");
		if(CollectionUtil.isNotEmpty(awardList)){
			for(Map<String,Object> userMap:awardList){
				userMap.put("seriesNum",request.getParameter("seriesNum"));
			}
		}
		map.put("awardList", awardList);
		System.out.println(awardList.size()+"awardList2");
		outputObject = getOutputObject(map, "tBizAwardPlayerService", "importAwardPlayer");
		outputObject.setBean(map);
		return outputObject;
		
	}
	
}
