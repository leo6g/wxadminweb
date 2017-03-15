package com.lfc.wxadminweb.modules.system.web;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.core.util.ControlConstants;
import com.lfc.wxadminweb.common.beanvalidator.ILoginAndUserInfo;
import com.lfc.wxadminweb.common.constant.Constants;
import com.lfc.wxadminweb.common.importexcel.ImportExcel;
import com.lfc.wxadminweb.common.upload.UploadFile;
import com.lfc.wxadminweb.common.upload.UploadUtil;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.system.form.ManagerForm;
import com.lfc.wxadminweb.modules.system.form.RoleUserForm;

@Controller
@RequestMapping(value = "system/manager")
public class ManagerController extends BaseController {
	
	@Value("#{system.appId}")
	private String appId;

	@Value("#{system.secret}")
	private String secret;
	
	/**
	 * 跳转到列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("system/manager/manager-list");
		return mv;
	}
	
	
	/**
	 * 分业查询用户数据
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getList")
	public OutputObject getList(@ModelAttribute("managerForm") ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "getList");
		return outputObject;
	}
	
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "add")
	public ModelAndView add(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("system/manager/add-manager");
		return mv;
	}
	
	
	
	/**
	 * 新增管理用户信息
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertManager")
	public OutputObject insertManager(@ModelAttribute("managerForm") @Validated(ILoginAndUserInfo.class) ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "insertManager");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("用户添加成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 跳转到编辑页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView edit(@ModelAttribute("managerForm") ManagerForm managerForm, 
			HttpServletRequest request, ModelAndView mv, Model model) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "getById");
		model.addAttribute("manager", outputObject.getObject());
		mv.setViewName("system/manager/edit-manager");
		return mv;
	}
	
	

	/**
	 * 修改管理用户信息
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateManager")
	public OutputObject updateManager(@ModelAttribute("managerForm") @Valid ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "updateManager");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("用户修改成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 跳转到查看页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "view")
	public ModelAndView view(@ModelAttribute("managerForm") ManagerForm managerForm, 
			HttpServletRequest request, ModelAndView mv, Model model) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "getById");
		model.addAttribute("manager", outputObject.getObject());
		mv.setViewName("system/manager/view-manager");
		return mv;
	}
	
	
	/**
	 * 冻结用户
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "freezeManager")
	public OutputObject freezeManager(@ModelAttribute("managerForm") ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		managerForm.setStatus(-1);
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "updateManagerObj");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("冻结成功!");
		}
		return outputObject;
	}
	
	
	/**
	 * 解冻用户
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "unfreezeManager")
	public OutputObject unfreezeManager(@ModelAttribute("managerForm") ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		managerForm.setStatus(1);
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "updateManagerObj");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("解冻成功!");
		}
		return outputObject;
	}
	
    /** 
	 * 逻辑删除用户
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteManager")
	public OutputObject deleteManager(@ModelAttribute("managerForm") ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		managerForm.setDeleteFlag(new BigDecimal("-1"));
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "updateManagerObj");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("用户删除成功!");
		}
		return outputObject;
	}
	/**
	 *判断原密码是否正确
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkPassword")
	public OutputObject checkPassword(@ModelAttribute("managerForm") ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "checkPassword");
		return outputObject;
	}
	/**
	 * 修改密码
	 * @param managerForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "editPassWord")
	public OutputObject editPassWord(@ModelAttribute("managerForm") ManagerForm managerForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(managerForm);
		outputObject = getOutputObject(map, "managerService", "editPassWord");
		return outputObject;
	}
	

	
	/**
	 * 二维码生成页面跳转
	 * */
	@RequestMapping(value = "qrList")
	public ModelAndView qrList(HttpServletRequest request, ModelAndView mv,Model model) {
		
		model.addAttribute("userName", (((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("userName")));
		model.addAttribute("id", (((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("id")));
		model.addAttribute("codeImg", (((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("codeImg")));
		System.out.println( (((HashMap<String,Object>)getSession().getAttribute(Constants.USER_SESSION.USER)).get("codeImg"))+"-------->");
		mv.setViewName("system/manager/qrCode-list");
		return mv;
	}
	
	/**
	 *根据用户帐号产生二维码并保存 
	 * */
	@ResponseBody
	@RequestMapping(value = "createQrAndSave")
	public OutputObject createQrAndSave(HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>(); 
		OutputObject outputObject = null;
		String id = request.getParameter("id");
		String userName=request.getParameter("userName");
		String access_token = getAccess_Token(this.appId, this.secret);
		String ticket = getTicket(access_token, userName);
		String codeImg="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
		map.put("userId", id);
		map.put("codeImg", codeImg);
		outputObject = getOutputObject(map, "managerService", "updateManagerCodeImg");
		outputObject.setObject("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket);
		return outputObject;
	}
	
	/**
	 * 通过appId  secret获取对应的access_token
	 * get 方式
	 * */
	public String getAccess_Token(String appId,String secret){
		/*
		String access_token="";
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet=new HttpGet(url);
		System.out.println(url+"-------------------<");
		httpGet.setHeader("Content-type", "application/json");
		try {
			HttpResponse response = httpClient.execute(httpGet);
			String content = EntityUtils.toString(response.getEntity());
			System.out.println(content+"------------->");
			JSONObject tokenJson = new JSONObject(content);
			access_token = String.valueOf(tokenJson.get("access_token"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//关闭释放连接
			httpGet.releaseConnection();
			httpClient.getConnectionManager().shutdown(); 
		}
		return access_token;
		*/
		
		String accessToken = "";
		OutputObject outputObject = null;
		Map< String, Object> map = new HashMap< String, Object>();
		map.put("id", "1");
		outputObject = getOutputObject(map,"accessTokenService","getById");
		Map< String, Object> outputMap = (Map< String, Object>)outputObject.getObject();
		if(!outputMap.isEmpty()){
			accessToken = (String)outputMap.get("accessToken");
		}
		return accessToken;
	}
	
	/**
	 * 通过access_token与用户帐号获取ticket信息
	 * post 需要带参数
	 * */
	public String getTicket(String access_token,String userAccount){
		String ticket="";
		  String json="{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+userAccount+"\"}}}";
//		String json = "{\"action_name\":\"QR_LIMIT_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":"+userAccount+"}}}";
		System.out.println(json+"--------------------->");
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token;
		HttpClient httpClient = new DefaultHttpClient();  
		HttpPost post = new HttpPost(url);
		System.out.println(url+"------------>url");
		try {
			StringEntity postingString = new StringEntity(json);// json传递  
			post.setEntity(postingString);
			post.setHeader("Content-type", "application/json");
			HttpResponse response = httpClient.execute(post);
			
			String content = EntityUtils.toString(response.getEntity());
			JSONObject ticketJson = new JSONObject(content);
			
			ticket = String.valueOf(ticketJson.get("ticket"));
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//关闭释放连接
			post.releaseConnection();
			httpClient.getConnectionManager().shutdown(); 
		}
		return ticket;
	}
	
	

	/**
	 * 导入Excel表的员工信息，批量插入到数据库表中
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "importUserInfo")
	public OutputObject importUserInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		UploadFile uploadFile = new UploadFile(request, response);
		uploadFile.setCusPath("manager");//上传文件的目录 可以多级如xx/xx/xx
		uploadFile=UploadUtil.uploadFile(uploadFile);
		Map<String,Object> map=new HashMap<String,Object>();
		uploadFile.getMultipartRequest().setCharacterEncoding("UTF-8");
		MultipartHttpServletRequest multipartRequest = uploadFile.getMultipartRequest();
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		List<Map<String,Object>> managerList=new ArrayList<Map<String,Object>>();
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
							//这里将excel表的科学计数法转变成普通计数法
							String name =ImportExcel.getStringCellValue((Cell)row.getCell(0));
							DecimalFormat df = new DecimalFormat("0");
							Double userName = Double.parseDouble(name); //将获取到的值转换为Double 
							userMap.put("userName",df.format(userName));
							userMap.put("realName",ImportExcel.getStringCellValue((Cell)row.getCell(1)));
							userMap.put("departId",ImportExcel.getStringCellValue((Cell)row.getCell(2)));
							managerList.add(userMap);
					       }
				       }
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		OutputObject outputObject=new OutputObject();
		map.put("managerList", managerList);
		outputObject = getOutputObject(map, "managerService", "importUserInfo");
		map.put("realPath", uploadFile.getRealPath());//上传文件的路径
		map.put("titleField", uploadFile.getTitleField());//上传文件的名称
		outputObject.setBean(map);
		return outputObject;
		
	}
	
	/**
	 * 根据用户id获取拥有的权限
	 * @param managerForm
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRolesByUserId")
	public OutputObject getRolesByUserId(HttpServletRequest request, Model model, ModelMap mm) {
		OutputObject outputObject = null;
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId",request.getParameter("userId"));
		outputObject = getOutputObject(map, "managerService", "getRolesByUserId");
		return outputObject;
	}
	
	/**
	 * 添加用户角色关联表信息(此方法不同于RoleController里的方法，此方法是根据userid删除对应的role，而RoleController的方法反之)
	 * @param RoleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertRoleUserInfo")
	public OutputObject insertRoleUserInfo(@RequestBody List<RoleUserForm> roleUserForm,BindingResult result,Model model,ModelMap mm,HttpServletRequest request) {
		if (roleUserForm.size()<1){
			OutputObject outputObject = new OutputObject();
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("请至少选中一个角色");
			return outputObject;
		}
		OutputObject outputObject = null;
		Map<String, Object> map=new HashMap<String, Object>();
		if(CollectionUtils.isNotEmpty(roleUserForm)){
			map.put("userId", roleUserForm.get(0).getUserId());
		}
		map.put("roleUserForm", roleUserForm);
		outputObject = getOutputObject(map,"managerService","insertRoleUserInfo");
		return outputObject;
	}
}
