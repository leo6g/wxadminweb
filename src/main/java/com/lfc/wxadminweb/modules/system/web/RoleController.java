package com.lfc.wxadminweb.modules.system.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;









import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lfc.core.bean.OutputObject;
import com.lfc.core.util.BeanUtil;
import com.lfc.core.util.ControlConstants;
import com.lfc.wxadminweb.common.web.BaseController;
import com.lfc.wxadminweb.modules.system.form.RoleForm;
import com.lfc.wxadminweb.modules.system.form.RolePrivForm;
import com.lfc.wxadminweb.modules.system.form.RoleUserForm;

/**
 * <h2></br>
 * 
 * @descript 
 * @author lbb
 * @date 2016-09-27 16:37
 * @since JDK 1.7
 *
 */
@Controller
@RequestMapping("system/role")
public class RoleController extends BaseController{

	
	/**
	 * 去往角色信息列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView roleList(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("system/role/role-list");
		return mv;
	}
	
	/**
	 * 去往编辑权限页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "edit")
	public ModelAndView editPower(HttpServletRequest request, ModelAndView mv,Model model) {
		Map<String,Object> menuMap=new HashMap<String, Object>();
		menuMap.put("roleId", request.getParameter("id"));
		model.addAllAttributes(menuMap);
		mv.setViewName("system/role/edit-power");
		return mv;
	}
	/**
	 * 去往成员管理页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping(value = "manage")
	public ModelAndView memberManage(HttpServletRequest request, ModelAndView mv,Model model) {
		Map<String,Object> menuMap=new HashMap<String, Object>();
		menuMap.put("roleId", request.getParameter("id"));
		model.addAllAttributes(menuMap);
		mv.setViewName("system/role/member-manage");
		return mv;
	}
	/**
	 * 查看角色信息列表
	 * @param "RoleForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryRoleList")
	public OutputObject queryRoleList(@ModelAttribute("RoleForm") RoleForm roleForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(roleForm);	
		outputObject = getOutputObject(map,"roleService","queryRoleList");
		return outputObject;
	}
	/**
	 * 添加
	 * @param RoleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertRole")
	public OutputObject insertRole(@ModelAttribute("RoleForm") @Valid RoleForm roleForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(roleForm);
		outputObject = getOutputObject(map,"roleService","insertRoleInfo");
		return outputObject;
	}
	/**
	 * 编辑
	 * @param RoleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateRole")
	public OutputObject updateRole(@ModelAttribute("RoleForm") @Valid RoleForm roleForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(roleForm);
		outputObject = getOutputObject(map,"roleService","updateRoleInfoById");
		return outputObject;
	}
	/**
	 * 逻辑删除
	 * @param RoleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteRole")
	public OutputObject deleteRole(@ModelAttribute("RoleForm") RoleForm roleForm,
			BindingResult result, HttpServletRequest request, Model model, ModelMap mm) {
		if (result.hasErrors()) {
			return returnValidatorAjaxResult(result);
		}
		OutputObject outputObject = null;
		
		Map<String, Object> map = BeanUtil.convertBean2Map(roleForm);
		outputObject = getOutputObject(map, "roleService", "delRoleLogic");
		if(outputObject.getReturnCode().equals("0")){
			outputObject.setReturnMessage("逻辑删除成功!");
		}
		return outputObject;
	}
	/**
	 *根据ID查询
	 * @param RoleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryRoleById")
	public OutputObject queryRoleById(@ModelAttribute("RoleForm") RoleForm roleForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(roleForm);	
		outputObject = getOutputObject(map,"roleService","selectByPrimaryKey");
		return outputObject;
	}
	/**
	 * 根据部门ID查询用户列表
	 * @param "RoleForm"
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryUserByDepartId")
	public OutputObject selectUserByDepartId(@ModelAttribute("RoleForm") RoleForm roleForm,BindingResult result, HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		if (result.hasErrors()) {
			returnValidatorAjaxResult(result);
		}
		Map<String, Object> map = BeanUtil.convertBean2Map(roleForm);	
		outputObject = getOutputObject(map,"roleService","selectUserByDepartId");
		return outputObject;
	}
	/**
	 * 添加用户角色关联表信息
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
			outputObject.setReturnMessage("请至少选中一个用户");
			return outputObject;
		}
		OutputObject outputObject = null;
		Map<String, Object> map=new HashMap<String, Object>();
		if(CollectionUtils.isNotEmpty(roleUserForm)){
			map.put("roleId", roleUserForm.get(0).getRoleId());
		}
		map.put("roleUserForm", roleUserForm);
		outputObject = getOutputObject(map,"roleService","insertRoleUserInfo");
		return outputObject;
	}
	/**
	 * 查询用户角色关联表信息
	 * @param RoleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryRUById")
	public OutputObject selectRUById(@ModelAttribute("RoleForm") RoleForm roleForm,BindingResult result,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(roleForm);	
		outputObject = getOutputObject(map,"roleService","selectRUById");
		return outputObject;
	}
	/**
	 * 删除用户角色关联表信息
	 * @param RoleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deletetRUById")
	public OutputObject deletetRUById(@ModelAttribute("RoleForm") RoleForm roleForm,BindingResult result,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = BeanUtil.convertBean2Map(roleForm);	
		outputObject = getOutputObject(map,"roleService","deletetRUById");
		return outputObject;
	}
	
	/**
	 * 添加用户角色关联表信息
	 * @param RoleForm
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "insertRolePrivInfo")
	public OutputObject insertRolePrivInfo(@RequestBody List<RolePrivForm> rolePrivForm,BindingResult result,Model model,ModelMap mm,HttpServletRequest request) {
		if (rolePrivForm.size()<1){
			OutputObject outputObject = new OutputObject();
			outputObject.setReturnCode(ControlConstants.RETURN_CODE.SYSTEM_ERROR);
			outputObject.setReturnMessage("请选择菜单权限");
			return outputObject;
		}
		OutputObject outputObject = null;
		Map<String, Object> map=new HashMap<String, Object>();
		if(CollectionUtils.isNotEmpty(rolePrivForm)){
			map.put("roleId", rolePrivForm.get(0).getRoleId());
		}
		map.put("rolePrivForm", rolePrivForm);
		outputObject = getOutputObject(map,"roleService","insertRolePrivInfo");
		return outputObject;
	}
	
	/**
	 * 根据code查询用户角色信息
	 * @param result
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryRoleByCode")
	public OutputObject queryRoleByCode(HttpServletRequest req,BindingResult result,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleCode", req.getParameter("roleCode"));
		outputObject = getOutputObject(map,"roleService","selectRoleByCode");
		return outputObject;
	}
	
	/**
	 * 获取所有角色
	 * @param request
	 * @param model
	 * @param mm
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectAllRoles")
	public OutputObject selectAllRoles(HttpServletRequest request,Model model,ModelMap mm) {
		OutputObject outputObject = null;
		outputObject = getOutputObject(new HashMap<String,Object>(),"roleService","selectAllRoles");
		return outputObject;
	}
}
