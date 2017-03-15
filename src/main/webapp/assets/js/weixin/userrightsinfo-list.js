$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化客户权益列表信息
	initUserRightsInfoListInfo();
	
	
	//新增客户权益信息时，validate验证，验证通过后调用保存方法 
	$("#addUserRightsInfoForm").validate({
        submitHandler:function(form){
        	addUserRightsInfo();
        }    
    });
	
	//新增客户权益信息
	$("#saveUserRightsInfoBtn").click(function(){
		var form = $("#addUserRightsInfoForm");
		form.submit();
	});
	
	//选中客户权益信息
	$("#userRightsInfoListTable").delegate("tr","click",function(){
//		var customerLevel = $(this).find("td:eq(1)").html();
//		var wxUserId = $(this).find("td:eq(2)").html();
		var id = $(this).find("td:eq(3)").html();
//		var remainCnt = $(this).find("td:eq(4)").html();
//		var usedCnt = $(this).find("td:eq(5)").html();
//		var initCnt = $(this).find("td:eq(6)").html();
//		var serviceCode = $(this).find("td:eq(7)").html();
//		var createUser = $(this).find("td:eq(9)").html();
		
//		$("#edit-customerLevel").val(customerLevel);
//		$("#edit-wxUserId").val(wxUserId);
		$("#edit-id").val(id);
//		$("#edit-remainCnt").val(remainCnt);
//		$("#edit-usedCnt").val(usedCnt);
//		$("#edit-initCnt").val(initCnt);
//		$("#edit-serviceCode").val(serviceCode);
//		$("#edit-createUser").val(createUser);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改客户权益信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editUserRightsInfoForm").validate({
        submitHandler:function(form){
        	editUserRightsInfo();
        }    
    });
	
	//保存编辑的客户权益信息
	$("#edit-saveUserRightsInfoBtn").click(function(){
		var form = $("#editUserRightsInfoForm");
		form.submit();
	});
	
	//编辑客户权益信息
	$("#editUserRightsInfoBtn").click(function(){
		var id = $("#edit-id").val();
//		if(id==""){
//			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
//			return false;
//		}
	});
	
	//删除客户权益信息
	$("#delUserRightsInfoBtn").click(function(){
		//logicDelUserRightsInfo();//逻辑删除
		delUserRightsInfo()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initUserRightsInfoListInfo();
	});
	
	//初始化客户权益列表信息
	function initUserRightsInfoListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/userRights/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求客户权益列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#userRightsInfo-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#userRightsInfoListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initUserRightsInfoListInfo);
			}
		});
	}
	
	
	//新增客户权益信息
	function addUserRightsInfo(){
		var url = contextPath + "/biz/userRights/insertUserRightsInfo";
		var params = $("#addUserRightsInfoForm").serialize();
		//异步请求新增客户权益信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("客户权益新增成功!");
					//$("#alert-info").modal("show");
					alert("客户权益新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initUserRightsInfoListInfo();
					//清空新增客户权益的弹窗信息
					$("#addUserRightsInfoForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("客户权益编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改客户权益信息
	function editUserRightsInfo(){
		var id = $("#edit-id").val();
//		if(id==""){
//		alert("请选择一条信息!");
//		return false;
//	}
		var url = contextPath + "/biz/userRights/updateUserRightsInfo";
		var params = $("#editUserRightsInfoForm").serialize();
		//异步请求修改客户权益信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("客户权益信息修改成功!");
					//编辑客户权益信息清空
					$("#editUserRightsInfoForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initUserRightsInfoListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("客户权益编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除客户权益
	function delUserRightsInfo(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/userRights/deleteUserRightsInfo";
			var params = $("#editUserRightsInfoForm").serialize();
			//异步请求逻辑删除客户权益信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("客户权益删除成功!");
						//编辑客户权益信息清空
						$("#editUserRightsInfoForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initUserRightsInfoListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除客户权益
	function logicDelUserRightsInfo(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/userRights/logicDeleteUserRightsInfo";
			var params = $("#editUserRightsInfoForm").serialize();
			//异步请求逻辑删除客户权益信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("客户权益删除成功!");
						//编辑客户权益信息清空
						$("#editUserRightsInfoForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initUserRightsInfoListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



