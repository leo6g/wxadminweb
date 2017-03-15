$(document).ready(function(){
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化特惠商户申请信息列表信息
	initMerchApplierListInfo();
	//初始化特惠商户类型信息
	queryMerchantType();
	
	
	//新增特惠商户申请信息信息时，validate验证，验证通过后调用保存方法 
	$("#addMerchApplierForm").validate({
        submitHandler:function(form){
        	addMerchApplier();
        }    
    });
	
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "/biz/merchapplier/merchExport?";
		var formdata=$("#queryForm").serialize();
		window.location.href = url+ encodeURI(formdata);
	});
	
	//新增特惠商户申请信息信息
	$("#saveMerchApplierBtn").click(function(){
		var form = $("#addMerchApplierForm");
		form.submit();
	});
	
	//查看特惠商户信息信息
	$("#viewMerchApplier").click(function(){
		var num=0;
		var id="";
		var checkbox = document.getElementsByName("selectOne");
		for(var i=0;i<checkbox.length;i++){
			if(checkbox[i].checked == true){
				num++;
				id=checkbox[i].value;
			}
		}
		if(num==1){
			queryMerchApplierDetail(id);
		}else{
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//选中特惠商户信息信息
	$("#merchApplierListTable").delegate("tr","click",function(){
		id = $(this).find("td:eq(4)").html();
	});
//	//选中特惠商户申请信息信息
//	$("#merchApplierListTable").delegate("tr","click",function(){
//		var applierName = $(this).find("td:eq(1)").html();
//		var shopName = $(this).find("td:eq(2)").html();
//		var id = $(this).find("td:eq(3)").html();
//		var processState = $(this).find("td:eq(4)").html();
//		var cityName = $(this).find("td:eq(5)").html();
//		var merchType = $(this).find("td:eq(6)").html();
//		var applierPhone = $(this).find("td:eq(7)").html();
//		var procRemark = $(this).find("td:eq(9)").html();
//		
//		$("#edit-applierName").val(applierName);
//		$("#edit-shopName").val(shopName);
//		$("#edit-id").val(id);
//		$("#edit-processState").val(processState);
//		$("#edit-cityName").val(cityName);
//		$("#edit-merchType").val(merchType);
//		$("#edit-applierPhone").val(applierPhone);
//		$("#edit-procRemark").val(procRemark);
//		
//		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
//		return false;
//	});
	
	
	//修改特惠商户申请信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editMerchApplierForm").validate({
        submitHandler:function(form){
        	editMerchApplier();
        }    
    });
	
	//保存编辑的特惠商户申请信息信息
	$("#edit-saveMerchApplierBtn").click(function(){
		var form = $("#editMerchApplierForm");
		form.submit();
	});
	//编辑特惠商户申请信息信息
	$("#editMerchApplierBtn").click(function(){
		var id = $("#edit-id").val();
		var checks=$("input[name='selectOne']:checked").length;
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(checks>1){
			alert("只能选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除特惠商户申请信息信息
	$("#delMerchApplierBtn").click(function(){
		//logicDelMerchApplier();//逻辑删除
		delMerchApplier()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initMerchApplierListInfo();
	});
	//局部绑定回车事件
	 $("#queryShopName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   });
	 $("#queryApplierName").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   });
	 $("#queryApplierPhone").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   });
	
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryForm select").val("");
		$("#queryShopName").val("");
		$("#queryApplierName").val("");
		$("#queryApplierPhone").val("");
//		$("#queryMerchType").val("");
		initMerchApplierListInfo();
	});
	
	//初始化特惠商户申请信息列表信息
	function initMerchApplierListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/merchapplier/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求特惠商户申请信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#merchApplier-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#merchApplierListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initMerchApplierListInfo);
			}
		});
	}
	//处理状态中文转换
	function transState (value){
		if(value=="applied"){
     	  return "已提交";
	     }else if(value=="processing"){
	          return "处理中";
	      }else if(value=="completed"){
		          return "处理完成";
	      }else{
	      	return "其他";
	      }
	}
	//中文化显示列表 处理状态
	Handlebars.registerHelper("tranState",function(value){
		return transState (value);
	});
	//新增特惠商户申请信息信息
	function addMerchApplier(){
		var url = contextPath + "/biz/merchapplier/insertMerchApplier";
		var params = $("#addMerchApplierForm").serialize();
		//异步请求新增特惠商户申请信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("特惠商户申请信息新增成功!");
					//$("#alert-info").modal("show");
					alert("特惠商户申请信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initMerchApplierListInfo();
					//清空新增特惠商户申请信息的弹窗信息
					$("#addMerchApplierForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("特惠商户申请信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改特惠商户申请信息信息
	function editMerchApplier(){
		var id = $("#edit-id").val();
	
		if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/merchapplier/updateMerchApplier";
		var params = $("#editMerchApplierForm").serialize();
		//异步请求修改特惠商户申请信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("特惠商户申请信息信息修改成功!");
					//编辑特惠商户申请信息信息清空
					$("#editMerchApplierForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initMerchApplierListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("特惠商户申请信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除特惠商户申请信息
	function delMerchApplier(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/merchapplier/deleteMerchApplier";
			var params = $("#editMerchApplierForm").serialize();
			//异步请求逻辑删除特惠商户申请信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("特惠商户申请信息删除成功!");
						//编辑特惠商户申请信息信息清空
						$("#editMerchApplierForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initMerchApplierListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除特惠商户申请信息
	function logicDelMerchApplier(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/merchapplier/logicDeleteMerchApplier";
			var params = $("#editMerchApplierForm").serialize();
			//异步请求逻辑删除特惠商户申请信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("特惠商户申请信息删除成功!");
						//编辑特惠商户申请信息信息清空
						$("#editMerchApplierForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initMerchApplierListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//查看详情根据ID查询信息
	function queryMerchApplierDetail(id){
		var url = contextPath + "/biz/merchapplier/getDetail?id="+id;
		var params = "";
		//异步请求信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = data.object;
				if(viewinfo !=null)
				{
					$("#view-applierName").text(viewinfo.applierName);
					$("#view-shopName").text(viewinfo.shopName);
					$("#view-processState").text(transState(viewinfo.processState));
					$("#view-cityName").text(viewinfo.cityName);
					$("#view-type").text(viewinfo.type);
					$("#view-applierPhone").text(viewinfo.applierPhone);
					$("#view-createTime").text(viewinfo.createTime);
					$("#view-procRemark").text(viewinfo.processRemark);
				}

			}
		});
	}	
	
	//点击取消按钮
	$("#returnBtn").click(function(){
		initMerchApplierListInfo();
		$("#edit-id").val("");
		$("#view-applierName").text("");
		$("#view-shopName").text("");
		$("#view-processState").text("");
		$("#view-cityName").text("");
		$("#view-type").text("");
		$("#view-applierPhone").text("");
		$("#view-createTime").text("");
		$("#view-procRemark").text("");
	});
	
	//获取特惠商户类型信息
	function queryMerchantType(){
		var url = contextPath + "/biz/merchApplier/getMerchantType";
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#type-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$(".typeList").html(htmlStr);
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
	
});
//选中微信模板信息
function seltable(){
	var index=$("#merchApplierListTable tr").find("input:checked").parents("tr").index()+1;
	var applierName = $("#merchApplierListTable tr:eq(" +index+") td:eq(2)").text();
	var shopName = $("#merchApplierListTable tr:eq(" +index+") td:eq(3)").text();
	var id = $("#merchApplierListTable tr:eq(" +index+") td:eq(4)").text();
	var processState = $("#merchApplierListTable tr:eq(" +index+") td:eq(5)").text();
	var cityName = $("#merchApplierListTable tr:eq(" +index+") td:eq(6)").text();
	var type = $("#merchApplierListTable tr:eq(" +index+") td:eq(7)").text();
	var applierPhone = $("#merchApplierListTable tr:eq(" +index+") td:eq(8)").text();
	var procRemark = $("#merchApplierListTable tr:eq(" +index+") td:eq(10)").text();
	if(type=="酒店"){
		type = "jd";
   }else if(type=="餐饮"){
	   type = "cy";
   }
//	if(processState=="酒店"){
//		processState = "jd";
//   }else if(processState=="餐饮"){
//	   processState = "cy";
//   }else if(processState=="餐饮"){
//	   processState = "cy";
//   }
	$("#edit-applierName").val(applierName);
	$("#edit-shopName").val(shopName);
	$("#edit-id").val(id);
	$("#edit-processState").val(processState);
	$("#edit-cityName").val(cityName);
	$("#edit-type").val(type);
	$("#edit-applierPhone").val(applierPhone);
	$("#edit-procRemark").val(procRemark);
	return false;
}



