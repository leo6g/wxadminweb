$(document).ready(function(){
	
	//上传组件
	fnGetUpload("uploadBtn","imagePath","preview","/weiweb/navigator/doUpload");
	fnGetUpload("edit-uploadBtn","edit-imagePath","edit-preview","/weiweb/navigator/doUpload");
	
	//列表分页每页显示多少条记录
	var global_limit = 10 ;
	//初始化导航菜单管理列表信息
	initNavigatorListInfo();
	
	
	//新增导航菜单管理信息时，validate验证，验证通过后调用保存方法 
	$("#addNavigatorForm").validate({
        submitHandler:function(form){
        	addNavigator();
        }    
    });
	
	//新增导航菜单管理信息
	$("#saveNavigatorBtn").click(function(){
		var form = $("#addNavigatorForm");
		form.submit();
	});
	
	//选中导航菜单管理信息
	$("#navigatorListTable").delegate("tr","click",function(){
		var name = $(this).find("td:eq(1)").html();
		var url = $(this).find("td:eq(2)").html();
		var sort = $(this).find("td:eq(4)").html();
		var navId = $(this).find("td:eq(5)").html();
		var iconPath = $(this).find("td:eq(8)").html();
		
		$("#edit-name").val(name);
		$("#edit-url").val(url);
		$("#edit-sort").val(sort);
		$("#edit-navId").val(navId);
		$("#edit-imagePath").val(iconPath);
		$("#edit-preview").attr("src", contextPath+"/"+iconPath);
		
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改导航菜单管理信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editNavigatorForm").validate({
        submitHandler:function(form){
        	editNavigator();
        }    
    });
	
	//保存编辑的导航菜单管理信息
	$("#edit-saveNavigatorBtn").click(function(){
		var form = $("#editNavigatorForm");
		form.submit();
	});
	//编辑导航菜单管理信息
	$("#editNavigatorBtn").click(function(){
		var navId = $("#edit-navId").val();
		if(navId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除导航菜单管理信息
	$("#delNavigatorBtn").click(function(){
		//logicDelNavigator();//逻辑删除
		delNavigator()
	});
	
	//初始化导航菜单管理列表信息
	function initNavigatorListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/weiweb/navigator/getList";
		var params = "pageNumber="+currentPage+"&limit="+limit;
		//异步请求导航菜单管理列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#navigator-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#navigatorListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initNavigatorListInfo);
			}
		});
	}
	
	
	//新增导航菜单管理信息
	function addNavigator(){
		var url = contextPath + "/weiweb/navigator/insertNavigator";
		var params = $("#addNavigatorForm").serialize();
		//异步请求新增导航菜单管理信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("导航菜单管理新增成功!");
					//$("#alert-info").modal("show");
					alert("导航菜单管理新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initNavigatorListInfo();
					//清空新增导航菜单管理的弹窗信息
					$("#addNavigatorForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("导航菜单管理编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改导航菜单管理信息
	function editNavigator(){
		var navId = $("#edit-navId").val();
	
		if(navId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/weiweb/navigator/updateNavigator";
		var params = $("#editNavigatorForm").serialize();
		//异步请求修改导航菜单管理信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("导航菜单管理信息修改成功!");
					//编辑导航菜单管理信息清空
					$("#editNavigatorForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initNavigatorListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("导航菜单管理编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除导航菜单管理
	function delNavigator(){
		var navId = $("#edit-navId").val();
		if(navId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/weiweb/navigator/deleteNavigator";
			var params = $("#editNavigatorForm").serialize();
			//异步请求逻辑删除导航菜单管理信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("导航菜单管理删除成功!");
						//编辑导航菜单管理信息清空
						$("#editNavigatorForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initNavigatorListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除导航菜单管理
	function logicDelNavigator(){
		var navId = $("#edit-navId").val();
		if(navId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/weiweb/navigator/logicDeleteNavigator";
			var params = $("#editNavigatorForm").serialize();
			//异步请求逻辑删除导航菜单管理信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("导航菜单管理删除成功!");
						//编辑导航菜单管理信息清空
						$("#editNavigatorForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initNavigatorListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



