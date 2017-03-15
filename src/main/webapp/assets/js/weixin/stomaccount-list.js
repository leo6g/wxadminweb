$(document).ready(function(){
	
	//初始化上传按钮      编辑时
//	advGetUpload("edit-uploadIcon","edit-imgPath","edit-picIcon","/wx/customaccount/doUpload");
//	//初始化上传按钮       添加时
//	advGetUpload("add-uploadIcon","add-imgPath","add-picIcon","/wx/customaccount/doUpload");

//	advGetUpload("edit-uploadIcon","edit-imgPath","edit-picIcon","/biz/advertisement/doUpload");
//	//初始化上传按钮
//	advGetUpload("add-uploadIcon","add-imgPath","add-picIcon","/biz/advertisement/doUpload");

	
	
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微信客服管理列表信息
	initCustomAccountListInfo();
	
	
	//新增微信客服管理信息时，validate验证，验证通过后调用保存方法 
	$("#addCustomAccountForm").validate({
        submitHandler:function(form){
        	addCustomAccount();
        }    
    });
	
	//新增微信客服管理信息
	$("#saveCustomAccountBtn").click(function(){
		var form = $("#addCustomAccountForm");
		form.submit();
	});
	
	//选中微信客服管理信息
	$("#customAccountListTable").delegate("tr","click",function(){
		var nickName = $(this).find("td:eq(1)").html();
		var account = $(this).find("td:eq(2)").html();
		var id = $(this).find("td:eq(3)").html();
		var headImg = $(this).find("td:eq(4)").html();
		var pwd = $(this).find("td:eq(5)").html();
		
		$("#edit-nickName").val(nickName);
		$("#edit-account").val(account);
		$("#edit-id").val(id);
		$("#edit-headImg").val(headImg);
		$("#edit-pwd").val(pwd);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改微信客服管理信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editCustomAccountForm").validate({
        submitHandler:function(form){
        	editCustomAccount();
        }    
    });
	
	//保存编辑的微信客服管理信息
	$("#edit-saveCustomAccountBtn").click(function(){
		var form = $("#editCustomAccountForm");
		form.submit();
	});
	//编辑微信客服管理信息
	$("#editCustomAccountBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信客服管理信息
	$("#delCustomAccountBtn").click(function(){
		//logicDelCustomAccount();//逻辑删除
		delCustomAccount()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initCustomAccountListInfo();
	});
	//局部绑定回车事件
	 $("#queryNickName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	  }); 
	 $("#queryAccount").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	  }); 
	//初始化微信客服管理列表信息
	function initCustomAccountListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/customaccount/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微信客服管理列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#customAccount-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#customAccountListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initCustomAccountListInfo);
			}
		});
	}
	
	
	//新增微信客服管理信息
	function addCustomAccount(){
		var url = contextPath + "/wx/customaccount/insertCustomAccount";
		var params = $("#addCustomAccountForm").serialize();
		//异步请求新增微信客服管理信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("微信客服管理新增成功!");
					//$("#alert-info").modal("show");
					alert("微信客服管理新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initCustomAccountListInfo();
					//清空新增微信客服管理的弹窗信息
					$("#addCustomAccountForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信客服管理编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改微信客服管理信息
	function editCustomAccount(){
		var url = contextPath + "/wx/customaccount/updateCustomAccount";
		var params = $("#editCustomAccountForm").serialize();
		//异步请求修改微信客服管理信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信客服管理信息修改成功!");
					//编辑微信客服管理信息清空
					$("#editCustomAccountForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initCustomAccountListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信客服管理编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除微信客服管理
	function delCustomAccount(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/customaccount/deleteCustomAccount";
			var params = $("#editCustomAccountForm").serialize();
			//异步请求逻辑删除微信客服管理信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信客服管理删除成功!");
						//编辑微信客服管理信息清空
						$("#editCustomAccountForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initCustomAccountListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除微信客服管理
	function logicDelCustomAccount(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/logicDeleteCustomAccount";
			var params = $("#editCustomAccountForm").serialize();
			//异步请求逻辑删除微信客服管理信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信客服管理删除成功!");
						//编辑微信客服管理信息清空
						$("#editCustomAccountForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initCustomAccountListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



