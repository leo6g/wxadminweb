$(document).ready(function(){
	//global_limit=2;
	var id="";
	//菜单列表
	initMenuListInfo();

	//获取选中的菜单的ID 
	$("#menuTableList").delegate("tr","click",function(){
		id = $(this).find("td:eq(1)").html();
	});
	//添加菜单
	$("#addMenu").click(function(){
		C.load(contextPath+'/system/menu/add?menuId='+id);
	});
	//查看菜单
	$("#viewMenu").click(function(){
		if(id==null || id==''){
			alert("请选择需要查看的菜单");
			return false;
		}
		C.load(contextPath+"/system/menu/view?menuId="+id);
	});
	//编辑菜单
	$("#editMenu").click(function(){
		if(id==null || id==''){
			alert("请选择需要编辑的菜单");
			return false;
		}
		C.load(contextPath+"/system/menu/edit?menuId="+id)
	});
	//删除菜单信息
	$("#deleteMenu").click(function(){
		if(id==null || id==''){
			alert("请选择需要删除的菜单");
			return false;
		}
		var url = contextPath + "/system/menu/deleteMenu?id="+id;
		var params = "";
		//删除按钮信息
		confirm("确定删除吗？", function(){
				Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initMenuListInfo();
			           //window.location.href=contextPath+"/system/menu/list";
					}
				});
		});
	});
	

	//初始化菜单列表
	function initMenuListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/system/menu/queryMenusTree";
		var params = "pageNumber="+currentPage+"&limit="+limit;
		//异步请求职位列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#menu-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				var htmlStr = templet(data.beans);
				$("#menu-list-table").html(htmlStr);
				$("#menuTableList").treetable({expandable: true});
				//初始化分页数据(当前页码，总数，回调查询函数)
				//initPaginator(currentPage, data.bean.count, initMenuListInfo);
			}
		});
	}

	//输出菜单类型
	Handlebars.registerHelper("EmenuType",function(menuType){
		if(menuType=="1"){
			return "访问类型";
		}
		if(menuType=="2"){
			return "菜单类型";
		}
	});
	
	//查询
	$("#querySubmit").click(function(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var formdata=$("#queryForm").serialize();
		formdata=formdata+"&pageNumber="+currentPage+"&limit="+limit;
	  	if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/system/menu/queryMenusTree";
		//异步请求职位列表数据
		Util.ajax.postJson(url, formdata, function(data, flag){
			var source = $("#menu-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				var htmlStr = templet(data.beans);
				$("#menu-list-table").html(htmlStr);
				$("#menuTableList").treetable({expandable: true});
				//初始化分页数据(当前页码，总数，回调查询函数)
				//initPaginator(currentPage, data.bean.count, initMenuListInfo);
			}
		});
	});

	
});




