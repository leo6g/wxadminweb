$(document).ready(function(){
	
//列表分页每页显示多少条记录
    global_limit = 10 ;
	//初始化信用卡申办信息表列表信息
	initCardApplierListInfo();
	queryAllCity();
	
	//新增信用卡申办信息表信息时，validate验证，验证通过后调用保存方法 
	$("#addCardApplierForm").validate({
        submitHandler:function(form){
        	addCardApplier();
        }    
    });
	
	//新增信用卡申办信息表信息
	$("#saveCardApplierBtn").click(function(){
		var form = $("#addCardApplierForm");
		form.submit();
	});
	
	//选中信用卡申办信息表信息
	$("#cardApplierListTable").delegate("tr","click",function(){
		var idNumber = $(this).find("td:eq(1)").html();
		var name = $(this).find("td:eq(2)").html();
		var applierId = $(this).find("td:eq(3)").html();
		var applyTime = $(this).find("td:eq(4)").html();
		var netPointId = $(this).find("td:eq(5)").html();
		var cellNumber = $(this).find("td:eq(6)").html();
		
		$("#edit-idNumber").val(idNumber);
		$("#edit-name").val(name);
		$("#edit-applierId").val(applierId);
		$("#edit-applyTime").val(applyTime);
		$("#edit-netPointId").val(netPointId);
		$("#edit-cellNumber").val(cellNumber);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改信用卡申办信息表信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editCardApplierForm").validate({
        submitHandler:function(form){
        	editCardApplier();
        }    
    });
	
	//保存编辑的信用卡申办信息表信息
	$("#edit-saveCardApplierBtn").click(function(){
		var form = $("#editCardApplierForm");
		form.submit();
	});
	//编辑信用卡申办信息表信息
	$("#editCardApplierBtn").click(function(){
		var applierId = $("#edit-applierId").val();
		if(applierId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除信用卡申办信息表信息
	$("#delCardApplierBtn").click(function(){
		//logicDelCardApplier();//逻辑删除
		delCardApplier()
	});
	
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "/biz/cardapplier/loanExport?";
		var formdata=$("#queryForm").serialize();
		window.location.href = url+ encodeURI(formdata);
	});
	
	//初始化信用卡申办信息表列表信息
	function initCardApplierListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/loan/getLoanList";
		var formdata=$("#queryForm").serialize();
		formdata=formdata +"&pageNumber="+currentPage+"&limit="+limit;
		//异步请求信用卡申办信息表列表数据
		Util.ajax.postJson(url, formdata, function(data, flag){
			console.info(data);
			var source = $("#cardApplier-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#cardApplierListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initCardApplierListInfo);
			}
		});
	}
	
	//查询
	$("#querySubmit").click(function(){
		initCardApplierListInfo();
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
	
	//新增信用卡申办信息表信息
	function addCardApplier(){
		var url = contextPath + "/insertCardApplier";
		var params = $("#addCardApplierForm").serialize();
		//异步请求新增信用卡申办信息表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("信用卡申办信息表新增成功!");
					//$("#alert-info").modal("show");
					alert("信用卡申办信息表新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initCardApplierListInfo();
					//清空新增信用卡申办信息表的弹窗信息
					$("#addCardApplierForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("信用卡申办信息表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改信用卡申办信息表信息
	function editCardApplier(){
		
		
		var url = contextPath + "/updateCardApplier";
		var params = $("#editCardApplierForm").serialize();
		//异步请求修改信用卡申办信息表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("信用卡申办信息表信息修改成功!");
					//编辑信用卡申办信息表信息清空
					$("#editCardApplierForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initCardApplierListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("信用卡申办信息表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除信用卡申办信息表
	function delCardApplier(){
		var applierId = $("#edit-applierId").val();
		if(applierId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/deleteCardApplier";
			var params = $("#editCardApplierForm").serialize();
			//异步请求逻辑删除信用卡申办信息表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("信用卡申办信息表删除成功!");
						//编辑信用卡申办信息表信息清空
						$("#editCardApplierForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initCardApplierListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除信用卡申办信息表
	function logicDelCardApplier(){
		var applierId = $("#edit-applierId").val();
		if(applierId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/logicDeleteCardApplier";
			var params = $("#editCardApplierForm").serialize();
			//异步请求逻辑删除信用卡申办信息表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("信用卡申办信息表删除成功!");
						//编辑信用卡申办信息表信息清空
						$("#editCardApplierForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initCardApplierListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	
	//获取所有二分信息
	function queryAllCity(){
		var url = contextPath + "/biz/netpoint/getOrgInfo?type=city";
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#city-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#cityList").html(htmlStr);
				$("#cityList").change(function(){
					var cityId=$(this).val();
					if(cityId != null && cityId !=''){
						queryAllTown(cityId);
					}else{
						$("#townList").val("");
						$("#countryList").val("");
					}
				});
				queryAllTown(data.beans[0].newOrgId);
			}
		});
	}
	
	//获取所有一支信息
	function queryAllTown(cityId){
		var url = contextPath + "/biz/netpoint/getOrgInfo?type=town&parentId="+cityId;
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#town-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#townList").html(htmlStr);
				$("#townList").change(function(){
					var townId=$(this).val();
					if(townId !=null && townId !=""){
						queryAllCountry(townId);
					}else{
						$("#countryList").val("");
					}

				});
				queryAllCountry(data.beans[0].newOrgId);
			}
		});
	}
	//获取所有二支信息
	function queryAllCountry(townId){
		var url = contextPath + "/biz/netpoint/getOrgInfo?type=country&parentId="+townId;
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#country-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#countryList").html(htmlStr);
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

});



