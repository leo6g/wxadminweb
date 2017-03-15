$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微信会员用户留言表列表信息
	initUserMessageListInfo();
	
	
	//新增微信会员用户留言表信息时，validate验证，验证通过后调用保存方法 
	$("#addUserMessageForm").validate({
        submitHandler:function(form){
        	addUserMessage();
        }    
    });
	
	//新增微信会员用户留言表信息
	$("#saveUserMessageBtn").click(function(){
		var form = $("#addUserMessageForm");
		form.submit();
	});
	
	//选中微信会员用户留言表信息
	$("#userMessageListTable").delegate("tr","click",function(){
		var msg = $(this).find("td:eq(1)").html();
		var wxUserId = $(this).find("td:eq(2)").html();
		var msgId = $(this).find("td:eq(3)").html();
		
		$("#edit-msg").val(msg);
		$("#edit-wxUserId").val(wxUserId);
		$("#edit-msgId").val(msgId);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改微信会员用户留言表信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editUserMessageForm").validate({
        submitHandler:function(form){
        	editUserMessage();
        }    
    });
	
	//保存编辑的微信会员用户留言表信息
	$("#edit-saveUserMessageBtn").click(function(){
		var form = $("#editUserMessageForm");
		form.submit();
	});
	//编辑微信会员用户留言表信息
	$("#editUserMessageBtn").click(function(){
		var msgId = $("#edit-msgId").val();
		if(msgId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信会员用户留言表信息
	$("#delUserMessageBtn").click(function(){
		//logicDelUserMessage();//逻辑删除
		delUserMessage()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initUserMessageListInfo();
	});
	
	//初始化微信会员用户留言表列表信息
	function initUserMessageListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/usermessage/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微信会员用户留言表列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#userMessage-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#userMessageListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initUserMessageListInfo);
			}
		});
	}
	
	
	//新增微信会员用户留言表信息
	function addUserMessage(){
		var url = contextPath + "/wx/usermessage/insertUserMessage";
		var params = $("#addUserMessageForm").serialize();
		//异步请求新增微信会员用户留言表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("微信会员用户留言表新增成功!");
					//$("#alert-info").modal("show");
					alert("微信会员用户留言表新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initUserMessageListInfo();
					//清空新增微信会员用户留言表的弹窗信息
					$("#addUserMessageForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信会员用户留言表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改微信会员用户留言表信息
	function editUserMessage(){
		var msgId = $("#edit-msgId").val();
	
		if(msgId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/wx/usermessage/updateUserMessage";
		var params = $("#editUserMessageForm").serialize();
		//异步请求修改微信会员用户留言表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信会员用户留言表信息修改成功!");
					//编辑微信会员用户留言表信息清空
					$("#editUserMessageForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initUserMessageListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信会员用户留言表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除微信会员用户留言表
	function delUserMessage(){
		var msgId = $("#edit-msgId").val();
		if(msgId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/usermessage/deleteUserMessage";
			var params = $("#editUserMessageForm").serialize();
			//异步请求逻辑删除微信会员用户留言表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信会员用户留言表删除成功!");
						//编辑微信会员用户留言表信息清空
						$("#editUserMessageForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initUserMessageListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除微信会员用户留言表
	function logicDelUserMessage(){
		var msgId = $("#edit-msgId").val();
		if(msgId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/usermessage/logicDeleteUserMessage";
			var params = $("#editUserMessageForm").serialize();
			//异步请求逻辑删除微信会员用户留言表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信会员用户留言表删除成功!");
						//编辑微信会员用户留言表信息清空
						$("#editUserMessageForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initUserMessageListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



