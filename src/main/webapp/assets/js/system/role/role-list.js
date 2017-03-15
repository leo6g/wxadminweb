$(document).ready(function(){
	//global_limit=2;
	var id="";
	//角色列表
	initRoleListInfo();
	//新增角色信息时，validate验证，验证通过后调用保存方法 
	$("#addRoleForm").validate({
	    submitHandler:function(form){
	    	addRole();
	    }    
	});
	//新增角色信息
	$("#saveRoleBtn").click(function(){
		var form = $("#addRoleForm");
		form.submit();
	});
	//编辑角色信息时，validate验证，验证通过后调用保存方法 
	$("#editRoleForm").validate({
	    submitHandler:function(form){
	    	editRole();
	    }    
	});
	//编辑角色信息
	$("#editRoleBtn").click(function(){
		var form = $("#editRoleForm");
		form.submit();
	});
	
	//获取选中的角色的ID 
	$("#roleTableList").delegate("tr","click",function(){
		id = $(this).find("td:eq(1)").html();
	});
	
	//新增角色信息
	function addRole(){
		var url = contextPath + "/system/role/insertRole";
		var params = $("#addRoleForm").serialize();
		//异步请求新增角色信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert(data.returnMessage);
					$("#myModal").modal('hide');
					//重新请求树数据
					initRoleListInfo();
					//清空新增职位的弹窗信息
					$("#addRoleTable input").val("");
				}
			}else{
					alert(data.returnMessage);
			}
		});
	}
	//编辑时初始化角色信息
	$("#initRoleInfo").click(function(){
		var url = contextPath + "/system/role/queryRoleById?id="+id;
		var params = "";
		//异步请求按钮数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				var viewinfo = eval(data.object);
				$("#roleId").val(viewinfo.id);
				$("#roleName").val(viewinfo.roleName);
				$("#roleCode").val(viewinfo.roleCode);
				$("#editModal").modal('show');
			}
		});
	});
	
	//删除角色信息
	$("#deleteRoleInfo").click(function(){
		var url = contextPath + "/system/role/deleteRole?id="+id;
		var params = "";
		//删除角色信息
		confirm("确定删除吗？", function(){
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
		           alert(data.returnMessage);
		           //重新加载列表页面
		           initRoleListInfo();
				}
			});
		});
	});

	//初始化角色列表
	function initRoleListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var formdata=$("#queryForm").serialize();
		var url = contextPath + "/system/role/queryRoleList";
		formdata =formdata+ "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求职位列表数据
		Util.ajax.postJson(url, formdata, function(data, flag){
			var source = $("#role-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				var htmlStr = templet(data.beans);
				$("#role-list-table").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initRoleListInfo);
			}
		});
	}

	//查询
	$("#querySubmit").click(function(){
		initRoleListInfo();
	});

	//编辑角色信息
	function editRole(){
		var url = contextPath + "/system/role/updateRole";
		var params = $("#editRoleForm").serialize();
		//异步请求新增角色信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert(data.returnMessage);
					$("#editModal").modal('hide');
					//重新请求树数据
					initRoleListInfo();
					//清空新增职位的弹窗信息
					$("#editRoleTable input").val("");
				}
			}else{
					alert(data.returnMessage);
			}
		});
	}
	
});




