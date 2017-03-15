$(document).ready(function(){
	
//列表分页每页显示多少条记录
    global_limit = 10 ;
	//初始化贷款申办信息表列表信息
    initLoanApplierHandleListInfo();
	
	
	//选中贷款申办信息表信息
	$("#cardApplierListTable").delegate("tr","click",function(){
		var applierId = $(this).find("td:eq(2)").html();//产品Id
		var processResult = $(this).find("td:eq(10)").html();//处理状态
		
		$("#edit-applierId").val(applierId);
		$("#edit-processResult").val(processResult);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	//选中客户经理信息
	$("#orderUserListTable").delegate("tr","click",function(){
		var taskerId = $(this).find("td:eq(3)").html();//任务接收人
		$("#edit-taskerId").val(taskerId);
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	//加载处理页面 
	$("#handleBtn").click(function(){//调配任务
		var applierId = $("#edit-applierId").val();
		if(applierId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var processResult = $("#edit-processResult").val();
		if(processResult!=""){
			alert("该信息已处理,请重新选择!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	//修改申请表（处理结果修改）
	$("#savehandleBtn").click(function(){
		var processRemark = $("#processRemark").val();
		var processResult = $("#processResult").val();
		editLoanApplier("completed",processResult,processRemark,null);
	});
	//加载指派人员列表
	$("#orderBtn").click(function(){
		var applierId = $("#edit-applierId").val();
		if(applierId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		initMNGListInfo();
	});
	
	//修改申请表（指派修改）
	$("#saveOrderBtn").click(function(){
		var taskerId = $("#edit-taskerId").val(); 
		editLoanApplier("processing",null,null,taskerId);
	});
	
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "/biz/cardapplier/loanHandleExport?";
		var formdata=$("#queryForm").serialize();
		window.location.href = url+ encodeURI(formdata);
	});
	
	//初始化贷款申办信息表列表信息
	function initLoanApplierHandleListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/cardapplier/getListByRole";
		var formdata=$("#queryForm").serialize();
		var departCode = $("#departCode").val();
		var roleCode = $("#roleCode").val();
		formdata=formdata +"&pageNumber="+currentPage+"&limit="+limit+"&roleCode="
		+roleCode+"&departCode="+departCode+"&applyType=loan";
		//异步请求贷款申办信息表列表数据
		Util.ajax.postJson(url, formdata, function(data, flag){
			var source = $("#cardApplier-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#cardApplierListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initLoanApplierHandleListInfo);
			}
		});
	}
	
	//中文化显示列表 处理状态
	Handlebars.registerHelper("tranState",function(value){
        if(value=="applied"){
       	  return "申请";
	     }else if(value=="processing"){
	          return "处理中";
        }else if(value=="completed"){
	          return "处理完成";
        }
	});
	Handlebars.registerHelper("tranResult",function(value){
        if(value=="success"){
       	  return "申办成功";
	     }else if(value=="fail"){
	          return "申办失败";
        }
	});
	
	//初始化贷款指派信息
	function initMNGListInfo(){
		var departCode = $("#departCode").val();
		var url = contextPath + "/biz/cardapplier/getTask";
		var params = "roleCode=LOAN_MNG&departId="+departCode;
		//异步请求贷款信息发布表列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#order-user-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#orderUserListTable").html(htmlStr);
			}
		});
	};
	
	//生成客户经理序号
	Handlebars.registerHelper("addOne",function(index,options){
        return parseInt(index)+1;
	});
	
	 
	//查询
	$("#querySubmit").click(function(){
		initLoanApplierHandleListInfo();
	});
	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   }); 
	 $("#queryCellNumber").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   }); 
	
	//新增贷款申办信息表信息
	function addLoanApplier(){
		var url = contextPath + "/insertCardApplier";
		var params = $("#addCardApplierForm").serialize();
		//异步请求新增贷款申办信息表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("贷款申办信息表新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initLoanApplierHandleListInfo();
					//清空新增贷款申办信息表的弹窗信息
					$("#addCardApplierForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("贷款申办信息表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改贷款申办信息表信息
	function editLoanApplier(processState,processResult,processRemark,taskerId){
		var url = contextPath + "/biz/cardapplier/updateCardApplier";
		var params = "";
		var applierId = $("#edit-applierId").val();
		alert(processResult);
		if(processResult != null){
			params = "processState="+processState+"&processResult="+processResult
			+"&processRemark="+processRemark+"&applierId="+applierId;
		}
		if(taskerId != null){
			params = "processState="+processState+"&taskerId="+taskerId+"&applierId="+applierId;
		}
		//异步请求修改贷款申办信息表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("该信息已处理成功!");
					//编辑贷款申办信息表信息清空
					$("#data input").val("");
					//重新请求列表数据
					initLoanApplierHandleListInfo();
					$("#myModal1").modal('hide');
					$("#myModal").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("贷款申办信息表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}

	
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "/biz/cardapplier/loanExport?";
		var formdata=$("#queryForm").serialize();
		var departCode = $("#departCode").val();
		var roleCode = $("#roleCode").val();
		formdata=formdata +"&roleCode="
		+roleCode+"&departCode="+departCode+"&applyType=loan";
		window.location.href = url+ encodeURI(formdata);
	});
});

//按钮隐藏与显示
$(function(){
	var roleCode = $("#roleCode").val();
	 if(roleCode == 'LOAN_ZG'){
		 $("#orderBtn").css('display','');
		 $("#exportBtn").css('display','');
		 $("#handleBtn").css('display','none');
	 }else if(roleCode == 'LOAN_MNG'){
		 $("#orderBtn").css('display','none');
		 $("#exportBtn").css('display','');
		 $("#handleBtn").css('display','');
	 }
	 
})