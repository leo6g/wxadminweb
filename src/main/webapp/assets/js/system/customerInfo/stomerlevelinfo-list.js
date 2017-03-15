$(document).ready(function(){
	getFileStomerInfo("uploadBtn","filePath");

//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化邮储个金部客户等级信息列表信息
	initCustomerLevelInfoListInfo();
	
	
	//新增邮储个金部客户等级信息信息时，validate验证，验证通过后调用保存方法 
	$("#addCustomerLevelInfoForm").validate({
        submitHandler:function(form){
        	addCustomerLevelInfo();
        }    
    });
	
	//重置按钮
	$("#queryReset").click(function(){
		$("#customerName").val('');
		$("#phoneNumber").val('');
		initCustomerLevelInfoListInfo();
	})
	
	//新增邮储个金部客户等级信息信息
	$("#saveCustomerLevelInfoBtn").click(function(){
		var form = $("#addCustomerLevelInfoForm");
		form.submit();
	});
	
	//选中邮储个金部客户等级信息信息
	$("#customerLevelInfoListTable").delegate("tr","click",function(){
//		var levelName = $(this).find("td:eq(1)").html();
//		var phoneNumber = $(this).find("td:eq(3)").html();
		var id = $(this).find("td:eq(4)").html();
//		var customerLevel = $(this).find("td:eq(5)").html();
//		var customerName = $(this).find("td:eq(2)").html();
		
		
//		$("#edit-levelName").val(levelName);
//		$("#edit-phoneNumber").val(phoneNumber);
		$("#edit-id").val(id);
//		$("#edit-customerLevel").val(customerLevel);
//		$("#edit-customerName").val(customerName);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改邮储个金部客户等级信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editCustomerLevelInfoForm").validate({
        submitHandler:function(form){
        	editCustomerLevelInfo();
        }    
    });
	
	//保存编辑的邮储个金部客户等级信息信息
	$("#edit-saveCustomerLevelInfoBtn").click(function(){
		var form = $("#editCustomerLevelInfoForm");
		form.submit();
	});
	//编辑邮储个金部客户等级信息信息
	$("#editCustomerLevelInfoBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		queryCustomerLevelInfoById();
	});
	
	//删除邮储个金部客户等级信息信息
	$("#delCustomerLevelInfoBtn").click(function(){
		//logicDelCustomerLevelInfo();//逻辑删除
		delCustomerLevelInfo()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initCustomerLevelInfoListInfo();
	});
	
	//局部绑定回车事件
	 $("#queryForm").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   });
	 //新增动态改变-客户等级-下拉框内容
	 $("#cardType").bind("change",function(){
		 changeContent("customerLevel",this.value);
	});
	
	 //编辑动态改变-客户等级-下拉框内容
	 $("#edit-cardType").bind("change",function(){
		 changeContent("edit-customerLevel",this.value);
	});
	//自动转换类型
	 Handlebars.registerHelper("transform",function(value){
        if(value=="00"){
       	  return "借记卡普通级会员";
	     }else if(value=="01"){
	          return "借记卡金卡级会员";
        }else if(value=="02"){
	          return "借记卡白金级会员";
        }else if(value=="03"){
	          return "借记卡钻石级会员";
        }else if(value=="10"){
	          return "信用卡金卡级会员";
        }else if(value=="11"){
	          return "信用卡白金级会员";
        }
	 });
	//下拉框函数
	 function changeContent(obj,value){
		 $("#"+obj).empty();
			if(value=="normalCard") {
				$("#"+obj).append("<option value=''>---请选择---</option>");
				$("#"+obj).append("<option value='00'>普通级会员</option>");
				$("#"+obj).append("<option value='01'>金卡级会员 </option>");
				$("#"+obj).append("<option value='02'>白金级会员</option>");
				$("#"+obj).append("<option value='03'>钻石级会员</option>");
			};
			if(value=="creditCard") {
				$("#"+obj).append("<option value=''>---请选择---</option>");
				$("#"+obj).append("<option value='10'>金卡级会员</option>");
				$("#"+obj).append("<option value='11'>白金级会员</option>");
			}; 
	 }
	//初始化邮储个金部客户等级信息列表信息
	function initCustomerLevelInfoListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/system/customerLevelInfo/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求邮储个金部客户等级信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			console.info(data.beans);
			var source = $("#customerLevelInfo-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				var json = data.beans;
				var htmlStr = templet(data.beans);
				//渲染列表数据
				$("#customerLevelInfoListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initCustomerLevelInfoListInfo);
			}
		});
	}
	
	
	//新增邮储个金部客户等级信息信息
	function addCustomerLevelInfo(){
		var customerLevel = $("#customerLevel").val();
		$("#levelName").val(customerLevel);
		console.info($("#levelName").val());
		
		var url = contextPath + "/system/customerLevelInfo/insertCustomerLevelInfo";
		var params = $("#addCustomerLevelInfoForm").serialize();
		//异步请求新增邮储个金部客户等级信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("邮储个金部客户等级信息新增成功!");
					//$("#alert-info").modal("show");
					alert("邮储个金部客户等级信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initCustomerLevelInfoListInfo();
					//清空新增邮储个金部客户等级信息的弹窗信息
					$("#addCustomerLevelInfoForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("邮储个金部客户等级信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改邮储个金部客户等级信息信息
	function editCustomerLevelInfo(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!");
			return false;
		}
		var customerLevel = $("#edit-customerLevel").val();
		$("#edit-levelName").val(customerLevel);
		//console.info($("#edit-levelName").val());
		var url = contextPath + "/system/customerLevelInfo/updateCustomerLevelInfo";
		var params = $("#editCustomerLevelInfoForm").serialize();
		console.info(params);
		//异步请求修改邮储个金部客户等级信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("邮储个金部客户等级信息信息修改成功!");
					//编辑邮储个金部客户等级信息信息清空
					$("#editCustomerLevelInfoForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initCustomerLevelInfoListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("邮储个金部客户等级信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除邮储个金部客户等级信息
	function delCustomerLevelInfo(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/system/customerLevelInfo/deleteCustomerLevelInfo";
			var params = $("#editCustomerLevelInfoForm").serialize();
			//异步请求逻辑删除邮储个金部客户等级信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("邮储个金部客户等级信息删除成功!");
						//编辑邮储个金部客户等级信息信息清空
						$("#editCustomerLevelInfoForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initCustomerLevelInfoListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除邮储个金部客户等级信息
	function logicDelCustomerLevelInfo(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/logicDeleteCustomerLevelInfo";
			var params = $("#editCustomerLevelInfoForm").serialize();
			//异步请求逻辑删除邮储个金部客户等级信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("邮储个金部客户等级信息删除成功!");
						//编辑邮储个金部客户等级信息信息清空
						$("#editCustomerLevelInfoForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initCustomerLevelInfoListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	//根据ID获取对应的信息
	function queryCustomerLevelInfoById(){
		var url=contextPath+"/system/customerLevelInfo/getById?id="+$("#edit-id").val();
		var params = "";
		//异步请求信息
		
		//异步请求信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = data.object;
				if(viewinfo !=null)
				{
					var cardType = viewinfo.cardType;
					$("#edit-levelName").val(viewinfo.levelName);
					$("#edit-cardType").val(cardType);
					changeContent("edit-customerLevel",cardType);
					$("#edit-phoneNumber").val(viewinfo.phoneNumber);
					$("#edit-customerLevel").val(viewinfo.customerLevel);
					$("#edit-customerName").val(viewinfo.customerName);
					$("#edit-managerName").val(viewinfo.managerName);
					$("#edit-managerPhone").val(viewinfo.managerPhone);
					$("#edit-id").val(viewinfo.id);
					
				}

			}
		})
	}

});



