$(document).ready(function(){
	var id='';
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化热点导航列表信息
	initHotNavigatorListInfo();
	//初始化上传按钮
	advGetUpload("uploadIcon","selfImgPath","picIcon","/biz/hotnavigator/doUpload");
	//初始化上传按钮
	advGetUpload("edit-uploadIcon","edit-selfImgPath","edit-picIcon","/biz/hotnavigator/doUpload");
	
	//新增热点导航信息时，validate验证，验证通过后调用保存方法 
	$("#addHotNavigatorForm").validate({
        submitHandler:function(form){
        	addHotNavigator();
        }    
    });
	
	//新增热点导航信息
	$("#saveHotNavigatorBtn").click(function(){
		var form = $("#addHotNavigatorForm");
		form.submit();
	});
	
	//选中热点导航信息
	$("#hotNavigatorListTable").delegate("tr","click",function(){
//		var type = $(this).find("td:eq(1)").html();
//		var articleId = $(this).find("td:eq(2)").html();
		id = $(this).find("td:eq(3)").html();
//		var createUser = $(this).find("td:eq(4)").html();
//		var selfImgPath = $(this).find("td:eq(5)").html();
//		var title = $(this).find("td:eq(6)").html();
//		var localUrl = $(this).find("td:eq(7)").html();
//		
//		$("#edit-type").val(type);
//		$("#edit-articleId").val(articleId);
		$("#edit-id").val(id);
//		$("#edit-createUser").val(createUser);
//		$("#edit-selfImgPath").val(selfImgPath);
//		$("#edit-title").val(title);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改热点导航信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editHotNavigatorForm").validate({
        submitHandler:function(form){
        	editHotNavigator();
        }    
    });
	
	//保存编辑的热点导航信息
	$("#edit-saveHotNavigatorBtn").click(function(){
		var form = $("#editHotNavigatorForm");
		form.submit();
	});
	//编辑热点导航信息
	$("#editHotNavigatorBtn").click(function(){
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		queryHotNavigatorInfo();
	});
	
	//删除热点导航信息
	$("#delHotNavigatorBtn").click(function(){
		//logicDelHotNavigator();//逻辑删除
		delHotNavigator()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initHotNavigatorListInfo();
	});
	
	//自动转换类型
	 Handlebars.registerHelper("transformat",function(value){
        if(value=="finance"){
       	   return "理财";
	     }else if(value=="metal"){
	        return "贵金属";
        }else if(value=="loan"){
        	return "贷款";
        }
	 });
	 
	//初始化热点导航列表信息
	function initHotNavigatorListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/hotnavigator/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求热点导航列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#hotNavigator-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#hotNavigatorListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initHotNavigatorListInfo);
			}
		});
	}
	
	
	//新增热点导航信息
	function addHotNavigator(){
		var url = contextPath + "/biz/hotnavigator/insertHotNavigator";
		var params = $("#addHotNavigatorForm").serialize();
		//异步请求新增热点导航信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("热点导航新增成功!");
					//$("#alert-info").modal("show");
					alert("热点导航新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initHotNavigatorListInfo();
					//清空新增热点导航的弹窗信息
					$("#addHotNavigatorForm input").val("");
					$("#addHotNavigatorForm select").val("");
					$("#selfImgPath").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("热点导航编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改热点导航信息
	function editHotNavigator(){
		var id = $("#edit-id").val();
	
		if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/hotnavigator/updateHotNavigator";
		var params = $("#editHotNavigatorForm").serialize();
		//异步请求修改热点导航信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("热点导航信息修改成功!");
					//编辑热点导航信息清空
					$("#editHotNavigatorForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initHotNavigatorListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("热点导航编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除热点导航
	function delHotNavigator(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/hotnavigator/deleteHotNavigator";
			var params = $("#editHotNavigatorForm").serialize();
			console.info(params);
			//异步请求逻辑删除热点导航信息
			Util.ajax.postJson(url, params, function(data, flag){
				console.info(data.Object);
				if(flag){
					if(data.returnCode=="0"){
						alert("热点导航删除成功!");
						//编辑热点导航信息清空
						$("#editHotNavigatorForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initHotNavigatorListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除热点导航
	function logicDelHotNavigator(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/hotnavigator/logicDeleteHotNavigator";
			var params = $("#editHotNavigatorForm").serialize();
			//异步请求逻辑删除热点导航信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("热点导航删除成功!");
						//编辑热点导航信息清空
						$("#editHotNavigatorForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initHotNavigatorListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	//编辑前根据ID查询信息
	function queryHotNavigatorInfo(){
		var url = contextPath + "/biz/hotnavigator/getById?id="+id;
		var params = "";
		//异步请求信息
		Util.ajax.postJson(url, params, function(data, flag){
			console.info(data.object);
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = data.object;
				if(viewinfo !=null)
				{
					$("#edit-createUser").val(viewinfo.createUser);
					$("#edit-id").val(viewinfo.id);
					$("#edit-title").val(viewinfo.title);
					$("#edit-articleId").val(viewinfo.articleId);
					$("#edit-selfImgPath").val(viewinfo.selfImgPath);
					if(viewinfo.showPath!='' && viewinfo.showPath!=null){
						$("#edit-picIcon").attr("src",viewinfo.showPath);
					}
					$("#edit-type").val(viewinfo.type);
					$("#id").val(id);
				}

			}
		});
	}

});



