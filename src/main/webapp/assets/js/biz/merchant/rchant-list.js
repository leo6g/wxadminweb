$(document).ready(function(){
	getFileMerchant("uploadBtn","filePath");
	var merchantId='' ;	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化特惠商户信息列表信息
	initMerchantListInfo();
	//初始化特惠商户类型信息
	queryMerchantType();
	//初始化特惠商户状态信息
	queryMerchantState();
	//初始化上传按钮
	advGetUpload("uploadIcon","imagePath","picIcon","/biz/merchant/doUpload");
	//初始化上传按钮
	advGetUpload("edit-uploadIcon","edit-imagePath","edit-picIcon","/biz/merchant/doUpload");
	
	advGetUpload("bannerUploadIcon","bannerImg","bannerPicIcon","/biz/merchant/doUpload");
	
	advGetUpload("edit-bannerUploadIcon","edit-bannerImg","edit-bannerPicIcon","/biz/merchant/doUpload");
	
	//新增特惠商户信息信息时，validate验证，验证通过后调用保存方法 
	$("#addMerchantForm").validate({
        submitHandler:function(form){
        	addMerchant();
        }    
    });
	
	//新增特惠商户信息信息
	$("#saveMerchantBtn").click(function(){
		var form = $("#addMerchantForm");
		form.submit();
	});
	
	//选中特惠商户信息信息
	$("#merchantListTable").delegate("tr","click",function(){
		merchantId = $(this).find("td:eq(1)").html();
	});
	
	
	//修改特惠商户信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editMerchantForm").validate({
        submitHandler:function(form){
        	editMerchant();
        }    
    });
	
	//保存编辑的特惠商户信息信息
	$("#edit-saveMerchantBtn").click(function(){
		var form = $("#editMerchantForm");
		form.submit();
	});
	//编辑特惠商户信息信息
	$("#editMerchantBtn").click(function(){
		if(merchantId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		queryMerchantInfo();
		
	});
	
	//查看特惠商户信息信息
	$("#viewMerchant").click(function(){
		if(merchantId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		queryMerchantDetail();
		
	});
	
	//删除特惠商户信息信息
	$("#delMerchantBtn").click(function(){
		logicDelMerchant();//逻辑删除
	});
	
	//查询
	$("#querySubmit").click(function(){
		initMerchantListInfo();
	});
	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   });
	 
	 //是否热门
	 Handlebars.registerHelper("tranHot",function(value){
	        if(value=="T"){
	       	  return "是";
		     }else if(value=="F"){
		      return "否";
	        }
		});
	//初始化特惠商户信息列表信息
	function initMerchantListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/merchant/getList";
		var formdata=$("#queryForm").serialize();
		formdata=formdata +"&pageNumber="+currentPage+"&limit="+limit;
		//异步请求特惠商户信息列表数据
		Util.ajax.postJson(url, formdata, function(data, flag){
			var source = $("#merchant-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#merchantListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initMerchantListInfo);
			}
		});
	}
	
	
	//新增特惠商户信息信息
	function addMerchant(){
		var url = contextPath + "/biz/merchant/insertMerchant";
		var params = $("#addMerchantForm").serialize();
		//异步请求新增特惠商户信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("特惠商户信息新增成功!");
					//$("#alert-info").modal("show");
					alert("特惠商户信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initMerchantListInfo();
					//清空新增特惠商户信息的弹窗信息
					$("#addMerchantForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("特惠商户信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改特惠商户信息信息
	function editMerchant(){
		var merchantId = $("#edit-merchantId").val();
	
		if(merchantId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/merchant/updateMerchant";
		var params = $("#editMerchantForm").serialize();
		//异步请求修改特惠商户信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("特惠商户信息信息修改成功!");
					//编辑特惠商户信息信息清空
					$("#editMerchantForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initMerchantListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("特惠商户信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除特惠商户信息
	function delMerchant(){
		if(merchantId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/merchant/deleteMerchant";
			var params = $("#editMerchantForm").serialize();
			//异步请求逻辑删除特惠商户信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("特惠商户信息删除成功!");
						//编辑特惠商户信息信息清空
						$("#editMerchantForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initMerchantListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除特惠商户信息
	function logicDelMerchant(){
		if(merchantId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/merchant/logicDeleteMerchant?merchantId="+merchantId;
			//异步请求逻辑删除特惠商户信息信息
			Util.ajax.postJson(url, null, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("特惠商户信息删除成功!");
						//重新请求列表数据
						initMerchantListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	
	//获取特惠商户类型信息
	function queryMerchantType(){
		var url = contextPath + "/biz/merchant/getMerchantType";
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
	
	//获取特惠商户状态信息
	function queryMerchantState(){
		var url = contextPath + "/biz/merchant/getMerchantState";
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#state-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$(".stateList").html(htmlStr);
			}
		});
	}
	//编辑前根据ID查询信息
	function queryMerchantInfo(){
		var url = contextPath + "/biz/merchant/getById?merchantId="+merchantId;
		var params = "";
		//异步请求信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = data.object;
				console.info(viewinfo);
				if(viewinfo !=null)
				{
					$("#edit-createUser").val(viewinfo.createUser);
					$("#edit-tactorPhone").val(viewinfo.tactorPhone);
					$("#edit-address").val(viewinfo.address);
					$("#edit-introduction").val(viewinfo.introduction);
					$("#edit-comments").val(viewinfo.comments);
					$("#edit-shortName").val(viewinfo.shortName);
					$("#edit-name").val(viewinfo.name);
					$("#edit-merchantId").val(viewinfo.merchantId);
					$("#edit-imagePath").val(viewinfo.imagePath);
					if(viewinfo.showPath!='' && viewinfo.showPath!=null){
						$("#edit-picIcon").attr("src",viewinfo.showPath);
					}
					$("#edit-bannerImg").val(viewinfo.bannerImg);
					if(viewinfo.showPath2!='' && viewinfo.showPath2!=null){
						$("#edit-bannerPicIcon").attr("src",viewinfo.showPath2);
					}
					$("#edit-isHot").val(viewinfo.isHot);
					$("#edit-state").val(viewinfo.state);
					$("#edit-supportedCards").val(viewinfo.supportedCards);
					$("#edit-type").val(viewinfo.type);
					$("#edit-code").val(viewinfo.code);
					$("#edit-latitude").val(viewinfo.latitude);
					$("#edit-longitude").val(viewinfo.longitude);
					
					$("#edit-activityName").val(viewinfo.activityName);
					$("#edit-startDate").val(viewinfo.startDate);
					$("#edit-endDate").val(viewinfo.endDate);
					$("#edit-activityContent").val(viewinfo.activityContent);
					
					$("#merchantId").val(merchantId);
				}

			}
		});
	}

	
	//查看详情根据ID查询信息
	function queryMerchantDetail(){
		var url = contextPath + "/biz/merchant/getDetail?merchantId="+merchantId;
		var params = "";
		//异步请求信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = data.object;
				if(viewinfo !=null)
				{
					$("#view-createUser").text(viewinfo.createUser);
					$("#view-createTime").text(viewinfo.createTime);
					$("#view-address").text(viewinfo.address);
					$("#view-introduction").text(viewinfo.introduction);
					$("#view-tactorPhone").text(viewinfo.tactorPhone);
					$("#view-comments").text(viewinfo.comments);
					$("#view-shortName").text(viewinfo.shortName);
					$("#view-name").text(viewinfo.name);
					$("#view-merchantId").text(viewinfo.merchantId);
					if(viewinfo.showPath!='' && viewinfo.showPath!=null){
						$("#view-imagePath").attr("src",viewinfo.showPath);
					}
					if(viewinfo.showPath2!='' && viewinfo.showPath2!=null){
						$("#view-bannerImg").attr("src",viewinfo.showPath2);
					}
					$("#view-isHot").text(viewinfo.isHot);
					$("#view-state").text(viewinfo.state);
					$("#view-supportedCards").text(viewinfo.supportedCards);
					$("#view-type").text(viewinfo.type);
					$("#view-code").text(viewinfo.code);
					$("#view-latitude").text(viewinfo.latitude);
					$("#view-longitude").text(viewinfo.longitude);
					$("#view-activityName").text(viewinfo.activityName);
					$("#view-startDate").text(viewinfo.startDate);
					$("#view-endDate").text(viewinfo.endDate);
					$("#view-activityContent").text(viewinfo.activityContent);
				}

			}
		});
	}
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "/biz/merchant/exportData?";
		var formdata=$("#queryForm").serialize();
		window.location.href = url+ encodeURI(formdata);
	});

});





