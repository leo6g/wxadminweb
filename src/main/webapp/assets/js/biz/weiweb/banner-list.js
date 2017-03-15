$(document).ready(function(){
	//初始化编辑上传按钮
	advGetUpload("edit-uploadIcon","edit-path","edit-picIcon","/biz/banner/doUpload");
	//初始化新增上传按钮
	advGetUpload("add-uploadIcon","path","add-picIcon","/biz/banner/doUpload");
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微网站广告栏信息列表信息
	initBizBannerListInfo();
	getModulesList("query");
	
	//新增微网站广告栏信息信息时，validate验证，验证通过后调用保存方法 
	$("#addBizBannerForm").validate({
        submitHandler:function(form){
        	addBizBanner();
        }    
    });
	
	//新增微网站广告栏信息信息
	$("#saveBizBannerBtn").click(function(){
		var form = $("#addBizBannerForm");
		form.submit();
	});
	
	//选中微网站广告栏信息信息
	$("#bizBannerListTable").delegate("tr","click",function(){
		var name = $(this).find("td:eq(1)").html();
		var moduleId = $(this).find("td:eq(2)").html();
		var bannerId = $(this).find("td:eq(4)").html();
		var sort = $(this).find("td:eq(5)").html();
		var type = $(this).find("td:eq(6)").html();
		var url = $(this).find("td:eq(12)").html();
		//var path = $(this).find("td:eq(8)").html();
		var createUser = $(this).find("td:eq(10)").html();
		var path = $(this).find("td:eq(11)").html();
		
		$("#edit-name").val(name);
		getModulesList("edit");
		$("#edit-moduleId").val(moduleId);
		
		$("#edit-bannerId").val(bannerId);
		$("#edit-sort").val(sort);
		$("#edit-type").val(type);
		$("#edit-url").val(url);
		$("#edit-picIcon").attr("src",path);
		$("#edit-path").val(path.substring(path.lastIndexOf("/") + 1));
		$("#edit-createUser").val(createUser);
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改微网站广告栏信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editBizBannerForm").validate({
        submitHandler:function(form){
        	editBizBanner();
        }    
    });
	
	//保存编辑的微网站广告栏信息信息
	$("#edit-saveBizBannerBtn").click(function(){
		var form = $("#editBizBannerForm");
		form.submit();
	});
	//编辑微网站广告栏信息信息
	$("#editBizBannerBtn").click(function(){
		var bannerId = $("#edit-bannerId").val();
		if(bannerId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	$("#addBizBannerBtn").click(function(){
		getModulesList("add");
	});
	
	//删除微网站广告栏信息信息
	$("#delBizBannerBtn").click(function(){
		logicDelBizBanner();//逻辑删除
		//delBizBanner()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initBizBannerListInfo();
	});
	
	//自动转换审批状态
	 Handlebars.registerHelper("transformat",function(value){
		 if(value=="banner"){
			 return "轮播广告";
		 }else if(value=="background"){
			 return "背景图片";
		 }else{
			 return "";
		 }
	 });
	 
	//初始化微网站广告栏信息列表信息
	function initBizBannerListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/banner/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微网站广告栏信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#bizBanner-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#bizBannerListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initBizBannerListInfo);
			}
		});
	}
	
	
	//新增微网站广告栏信息信息
	function addBizBanner(){
		var url = contextPath + "/biz/banner/insertBizBanner";
		var params = $("#addBizBannerForm").serialize();
		//异步请求新增微网站广告栏信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("微网站广告栏信息新增成功!");
					//$("#alert-info").modal("show");
					alert("微网站广告栏信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initBizBannerListInfo();
					//清空新增微网站广告栏信息的弹窗信息
					$("#addBizBannerForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微网站广告栏信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改微网站广告栏信息信息
	function editBizBanner(){
		var bannerId = $("#edit-bannerId").val();
	
		if(bannerId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/banner/updateBizBanner";
		var params = $("#editBizBannerForm").serialize();
		//异步请求修改微网站广告栏信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微网站广告栏信息信息修改成功!");
					//编辑微网站广告栏信息信息清空
					$("#editBizBannerForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initBizBannerListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微网站广告栏信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除微网站广告栏信息
	function delBizBanner(){
		var bannerId = $("#edit-bannerId").val();
		if(bannerId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/banner/deleteBizBanner";
			var params = $("#editBizBannerForm").serialize();
			//异步请求逻辑删除微网站广告栏信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站广告栏信息删除成功!");
						//编辑微网站广告栏信息信息清空
						$("#editBizBannerForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBizBannerListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除微网站广告栏信息
	function logicDelBizBanner(){
		var bannerId = $("#edit-bannerId").val();
		if(bannerId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/banner/logicDeleteBizBanner";
			var params = $("#editBizBannerForm").serialize();
			//异步请求逻辑删除微网站广告栏信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站广告栏信息删除成功!");
						//编辑微网站广告栏信息信息清空
						$("#editBizBannerForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBizBannerListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

	//动态加载模板下拉列表
	function getModulesList(obj){
		var url = contextPath + "/biz/modules/getAll";
		//异步请求文本模版列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#modules-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				if(obj == 'query') {
					$("#queryModule").empty();
					$("#queryModule").html(htmlStr);
				}
				if(obj == 'edit') {
					$("#edit-moduleId").empty();
					$("#edit-moduleId").html(htmlStr);
				}
				if(obj == 'add') {
					$("#moduleId").empty();
					$("#moduleId").html(htmlStr);
				}
			}
		});
	}
	
});

function fnPreview(url){
	window.open(url); 
}



