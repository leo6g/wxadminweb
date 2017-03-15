$(document).ready(function(){
	//列表分页每页显示多少条记录
	var global_limit = 10 ;
	
	//初始化微信账号配置信息列表信息
	initWXConfigListInfo();
	
	
	//新增微信账号配置信息信息时，validate验证，验证通过后调用保存方法 
	$("#addWXConfigForm").validate({
        submitHandler:function(form){
        	addWXConfig();
        }    
    });
	
	//新增微信账号配置信息信息
	$("#saveWXConfigBtn").click(function(){
		var form = $("#addWXConfigForm");
		form.submit();
	});
	
	//选中微信账号配置信息信息
	$("#wXConfigListTable").delegate("tr","click",function(){
		var token = $(this).find("td:eq(1)").html();
		var description = $(this).find("td:eq(2)").html();
		var type = $(this).find("td:eq(3)").html();
		var cfgId = $(this).find("td:eq(4)").html();
		var appId = $(this).find("td:eq(5)").html();
		var accessToke = $(this).find("td:eq(6)").html();
		var account = $(this).find("td:eq(7)").html();
		var accountId = $(this).find("td:eq(8)").html();
		var email = $(this).find("td:eq(9)").html();
		var appSecret = $(this).find("td:eq(10)").html();
		var createUser = $(this).find("td:eq(11)").html();
		
		$("#edit-token").val(token);
		$("#edit-description").val(description);
		$("#edit-type").val(type);
		$("#edit-cfgId").val(cfgId);
		$("#edit-appId").val(appId);
		$("#edit-accessToke").val(accessToke);
		$("#edit-account").val(account);
		$("#edit-accountId").val(accountId);
		$("#edit-email").val(email);
		$("#edit-appSecret").val(appSecret);
		$("#edit-createUser").val(createUser);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");		
		return false;
	});
	
	
	//修改微信账号配置信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXConfigForm").validate({
        submitHandler:function(form){
        	editWXConfig();
        }    
    });
	
	//保存编辑的微信账号配置信息信息
	$("#edit-saveWXConfigBtn").click(function(){
		var form = $("#editWXConfigForm");
		form.submit();
	});
	//编辑微信账号配置信息信息
	$("#editWXConfigBtn").click(function(){
		var cfgId = $("#edit-cfgId").val();
		if(cfgId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信账号配置信息信息
	$("#delWXConfigBtn").click(function(){
		//logicDelWXConfig();//逻辑删除
		delWXConfig()
	});
	



//初始化微信账号配置信息列表信息
function initWXConfigListInfo(currentPage, limit){
	if(typeof currentPage == "undefined"){
		currentPage = 1;
	}
	if(typeof limit == "undefined"){
		limit = global_limit;
	}
	var url = contextPath + "/wx/config/getList";
	var params = "pageNumber="+currentPage+"&limit="+limit;
	//异步请求微信账号配置信息列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		var source = $("#wXConfig-list-template").html();
		var templet = Handlebars.compile(source);
		if(flag && data.returnCode=="0"){
			//渲染列表数据
			var htmlStr = templet(data.beans);
			$("#wXConfigListTable").html(htmlStr);
			//初始化分页数据(当前页码，总数，回调查询函数)
			initPaginator(currentPage, data.bean.count, initWXConfigListInfo);
		}
	});
}


//新增微信账号配置信息信息
function addWXConfig(){
	var url = contextPath + "/wx/config/insertWXConfig";
	var params = $("#addWXConfigForm").serialize();
	//异步请求新增微信账号配置信息信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				//$("#alert-info-content").html("微信账号配置信息新增成功!");
				//$("#alert-info").modal("show");
				alert("微信账号配置信息新增成功!",$.scojs_message.TYPE_OK);
				$("#myModal").modal('hide');
				//重新请求列表数据
				initWXConfigListInfo();
				//清空新增微信账号配置信息的弹窗信息
				$("#addWXConfigForm input").val("");
				$("#descn").val("");
			}
		}else{
			if(data.returnCode=="1"){
				alert("微信账号配置信息编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
			}else{
				alert(data.returnMessage);
			}
		}
	});
}


//修改微信账号配置信息信息
function editWXConfig(){
	var cfgId = $("#edit-cfgId").val();
	
	if(cfgId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	
	var url = contextPath + "/wx/config/updateWXConfig";
	var params = $("#editWXConfigForm").serialize();
	//异步请求修改微信账号配置信息信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				alert("微信账号配置信息信息修改成功!",$.scojs_message.TYPE_OK);
				//编辑微信账号配置信息信息清空
				$("#editWXConfigForm input").val("");
				$("#edit-descn").val("");
				//重新请求列表数据
				initWXConfigListInfo();
				$("#myModal1").modal('hide');
			}
		}else{
			if(data.returnCode=="-1"){
				alert("微信账号配置信息编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
			}else{
				alert(data.returnMessage);
			}
		}
	});
}
//删除微信账号配置信息
function delWXConfig(){
	var cfgId = $("#edit-cfgId").val();
	if(cfgId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	confirm("确定删除吗？",function(){
		var url = contextPath + "/wx/config/deleteWXConfig";
		var params = $("#editWXConfigForm").serialize();
		//异步请求逻辑删除微信账号配置信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信账号配置信息删除成功!",$.scojs_message.TYPE_OK);
					//编辑微信账号配置信息信息清空
					$("#editWXConfigForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXConfigListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	});
}

//逻辑删除微信账号配置信息
function logicDelWXConfig(){
	var cfgId = $("#edit-cfgId").val();
	if(cfgId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	var flag = confirm("确定删除吗？")
	if(flag){
		var url = contextPath + "/wx/config/logicDeleteWXConfig";
		var params = $("#editWXConfigForm").serialize();
		//异步请求逻辑删除微信账号配置信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信账号配置信息删除成功!",$.scojs_message.TYPE_OK);
					//编辑微信账号配置信息信息清空
					$("#editWXConfigForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXConfigListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	}
}
});

