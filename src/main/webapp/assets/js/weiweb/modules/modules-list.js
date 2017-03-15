$(document).ready(function(){
	global_limit=10;
	var id="";
	//模块信息列表
	initWWModulesListInfo();

	//获取选中的模块信息的ID 
	$("#modulesTableList").delegate("tr","click",function(){
		id = $(this).find("td:eq(7)").html();
	});
	//添加模块信息
	$("#addModules").click(function(){
		C.load(contextPath+'/weiweb/modules/add?moduleId='+id);
	});
	//查看模块信息
	$("#viewModules").click(function(){
		if(id==null || id==''){
			alert("请选择需要查看的模块信息",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath+"/weiweb/modules/view?moduleId="+id);
	});
	//编辑模块信息
	$("#editModules").click(function(){
		if(id==null || id==''){
			alert("请选择需要编辑的模块信息",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath+"/weiweb/modules/edit?moduleId="+id);
	});
	//删除模块信息信息
	$("#deleteModules").click(function(){
		if(id==null || id==''){
			alert("请选择需要删除的模块信息",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var url = contextPath + "/weiweb/modules/logicDeleteWWModules?moduleId="+id;
		var params = "";
		//删除按钮信息
		confirm("确定删除吗？", function(){
				Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initWWModulesListInfo();
			           //window.location.href=contextPath+"/weiweb/modules/list";
					}
				});
		});
	});
	
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryForm select").val("");
		initWWModulesListInfo();
	});
	
	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 

	//初始化模块信息列表
	function initWWModulesListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/weiweb/modules/queryModulesTree";
		var params = "pageNumber="+currentPage+"&limit="+limit;
		//异步请求职位列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#wWModules-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				var htmlStr = templet(data.beans);
				$("#wWModulesListTable").html(htmlStr);
				$("#modulesTableList").treetable({expandable: true});
				//初始化分页数据(当前页码，总数，回调查询函数)
				//initPaginator(currentPage, data.bean.count, initMenuListInfo);
			}
		});
	}
	
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
		var url = contextPath + "/weiweb/modules/queryModulesTree";
		//异步请求职位列表数据
		Util.ajax.postJson(url, formdata, function(data, flag){
			var source = $("#wWModules-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				var htmlStr = templet(data.beans);
				$("#wWModulesListTable").html(htmlStr);
				$("#modulesTableList").treetable({expandable: true});
				//初始化分页数据(当前页码，总数，回调查询函数)
				//initPaginator(currentPage, data.bean.count, initMenuListInfo);
			}
		});
	});
	
	//输出是否为叶子节点
	Handlebars.registerHelper("transformat",function(leaf){
		if(leaf=="T"){
			return "是";
		}
		if(leaf=="F"){
			return "否";
		}
	});
	
});




