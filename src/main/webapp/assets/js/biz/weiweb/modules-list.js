$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微网站模块信息列表信息
	initBizModulesListInfo();
	
	
	//新增微网站模块信息信息时，validate验证，验证通过后调用保存方法 
	$("#addBizModulesForm").validate({
        submitHandler:function(form){
        	addBizModules();
        }    
    });
	
	//新增微网站模块信息信息
	$("#saveBizModulesBtn").click(function(){
		var form = $("#addBizModulesForm");
		form.submit();
	});
	
	//选中微网站模块信息信息
	$("#bizModulesListTable").delegate("tr","click",function(){
		var moduleCode = $(this).find("td:eq(1)").html();
		var name = $(this).find("td:eq(2)").html();
		var moduleId = $(this).find("td:eq(3)").html();
		var createUser = $(this).find("td:eq(4)").html();
		var leaf = $(this).find("td:eq(5)").html();
//		var parentModuleId = $(this).find("td:eq(6)").html();
//		var levels = $(this).find("td:eq(7)").html();
		
		$("#edit-moduleCode").val(moduleCode);
		$("#edit-name").val(name);
		$("#edit-moduleId").val(moduleId);
		$("#edit-createUser").val(createUser);
		if(leaf == "是"){
			leaf ="T";
		}else if(leaf == "否"){
			leaf ="F";
		}else{
			leaf ="";
		}
		
		$("#edit-leaf").val(leaf);
//		$("#edit-parentModuleId").val(parentModuleId);
//		$("#edit-levels").val(levels);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改微网站模块信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editBizModulesForm").validate({
        submitHandler:function(form){
        	editBizModules();
        }    
    });
	
	//保存编辑的微网站模块信息信息
	$("#edit-saveBizModulesBtn").click(function(){
		var form = $("#editBizModulesForm");
		form.submit();
	});
	//编辑微网站模块信息信息
	$("#editBizModulesBtn").click(function(){
		var moduleId = $("#edit-moduleId").val();
		if(moduleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微网站模块信息信息
	$("#delBizModulesBtn").click(function(){
		logicDelBizModules();//逻辑删除
		//delBizModules()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initBizModulesListInfo();
	});

	//自动转换审批状态
	 Handlebars.registerHelper("transformat",function(value){
		 if(value=="T"){
			 return "是";
		 }else if(value=="F"){
			 return "否";
		 }else{
			 return "";
		 }
	 });
	 
	//初始化微网站模块信息列表信息
	function initBizModulesListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/modules/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微网站模块信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#bizModules-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#bizModulesListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initBizModulesListInfo);
			}
		});
	}
	
	
	//新增微网站模块信息信息
	function addBizModules(){
		var url = contextPath + "/biz/modules/insertBizModules";
		var params = $("#addBizModulesForm").serialize();
		//异步请求新增微网站模块信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微网站模块信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initBizModulesListInfo();
					//清空新增微网站模块信息的弹窗信息
					$("#addBizModulesForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("模块编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改微网站模块信息信息
	function editBizModules(){
		var moduleId = $("#edit-moduleId").val();
	
		if(moduleId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/modules/updateBizModules";
		var params = $("#editBizModulesForm").serialize();
		//异步请求修改微网站模块信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微网站模块信息修改成功!");
					//编辑微网站模块信息信息清空
					$("#editBizModulesForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initBizModulesListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除微网站模块信息
	function delBizModules(){
		var moduleId = $("#edit-moduleId").val();
		if(moduleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/modules/deleteBizModules";
			var params = $("#editBizModulesForm").serialize();
			//异步请求逻辑删除微网站模块信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站模块信息删除成功!");
						//编辑微网站模块信息信息清空
						$("#editBizModulesForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBizModulesListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除微网站模块信息
	function logicDelBizModules(){
		var moduleId = $("#edit-moduleId").val();
		if(moduleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/modules/logicDeleteBizModules";
			var params = $("#editBizModulesForm").serialize();
			//异步请求逻辑删除微网站模块信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站模块信息删除成功!");
						//编辑微网站模块信息信息清空
						$("#editBizModulesForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBizModulesListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



