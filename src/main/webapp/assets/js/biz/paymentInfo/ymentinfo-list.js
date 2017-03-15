$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化缴费数据信息列表信息
	initPaymentInfoListInfo();
	
	
	//新增缴费数据信息信息时，validate验证，验证通过后调用保存方法 
	$("#addPaymentInfoForm").validate({
        submitHandler:function(form){
        	addPaymentInfo();
        }    
    });
	
	//新增缴费数据信息信息
	$("#savePaymentInfoBtn").click(function(){
		var form = $("#addPaymentInfoForm");
		form.submit();
	});
	
	//选中缴费数据信息信息
	$("#paymentInfoListTable").delegate("tr","click",function(){
		var code = $(this).find("td:eq(1)").html();
		var name = $(this).find("td:eq(2)").html();
		var id = $(this).find("td:eq(3)").html();
		var linkUrl = $(this).find("td:eq(4)").html();
		var imgPath = $(this).find("td:eq(5)").html();
		
		$("#edit-code").val(code);
		$("#edit-name").val(name);
		$("#edit-id").val(id);
		$("#edit-linkUrl").val(linkUrl);
		$("#edit-imgPath").val(imgPath);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改缴费数据信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editPaymentInfoForm").validate({
        submitHandler:function(form){
        	editPaymentInfo();
        }    
    });
	
	//保存编辑的缴费数据信息信息
	$("#edit-savePaymentInfoBtn").click(function(){
		var form = $("#editPaymentInfoForm");
		form.submit();
	});
	//编辑缴费数据信息信息
	$("#editPaymentInfoBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除缴费数据信息信息
	$("#delPaymentInfoBtn").click(function(){
		//logicDelPaymentInfo();//逻辑删除
		delPaymentInfo()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initPaymentInfoListInfo();
	});
	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   });
	
	//初始化缴费数据信息列表信息
	function initPaymentInfoListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/paymentInfo/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求缴费数据信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#paymentInfo-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#paymentInfoListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initPaymentInfoListInfo);
			}
		});
	}
	
	
	//新增缴费数据信息信息
	function addPaymentInfo(){
		var url = contextPath + "/biz/paymentInfo/insertPaymentInfo";
		var params = $("#addPaymentInfoForm").serialize();
		//异步请求新增缴费数据信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("缴费数据信息新增成功!");
					//$("#alert-info").modal("show");
					alert("缴费数据信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initPaymentInfoListInfo();
					//清空新增缴费数据信息的弹窗信息
					$("#addPaymentInfoForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("缴费数据信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改缴费数据信息信息
	function editPaymentInfo(){
		
		
		var url = contextPath + "/biz/paymentInfo/updatePaymentInfo";
		var params = $("#editPaymentInfoForm").serialize();
		//异步请求修改缴费数据信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("缴费数据信息信息修改成功!");
					//编辑缴费数据信息信息清空
					$("#editPaymentInfoForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initPaymentInfoListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("缴费数据信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除缴费数据信息
	function delPaymentInfo(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/paymentInfo/deletePaymentInfo";
			var params = $("#editPaymentInfoForm").serialize();
			//异步请求逻辑删除缴费数据信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("缴费数据信息删除成功!");
						//编辑缴费数据信息信息清空
						$("#editPaymentInfoForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initPaymentInfoListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除缴费数据信息
	function logicDelPaymentInfo(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/paymentInfo/logicDeletePaymentInfo";
			var params = $("#editPaymentInfoForm").serialize();
			//异步请求逻辑删除缴费数据信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("缴费数据信息删除成功!");
						//编辑缴费数据信息信息清空
						$("#editPaymentInfoForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initPaymentInfoListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



