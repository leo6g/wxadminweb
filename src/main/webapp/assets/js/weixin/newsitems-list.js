
$(document).ready(function(){
	//列表分页每页显示多少条记录
	var global_limit = 10 ;
	
	//初始化微信图文详情列表信息
	initWXNewsItemsListInfo();
	
	
	//新增微信图文详情信息时，validate验证，验证通过后调用保存方法 
	$("#addWXNewsItemsForm").validate({
        submitHandler:function(form){
        	addWXNewsItems();
        }    
    });
	
	//新增微信图文详情信息
	$("#saveWXNewsItemsBtn").click(function(){
		var form = $("#addWXNewsItemsForm");
		form.submit();
	});
	
	//修改微信图文详情信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXNewsItemsForm").validate({
        submitHandler:function(form){
        	editWXNewsItems();
        }    
    });
	
	//保存编辑的微信图文详情信息
	$("#edit-saveWXNewsItemsBtn").click(function(){
		var form = $("#editWXNewsItemsForm");
		form.submit();
	});
	//编辑微信图文详情信息
	$("#editWXNewsItemsBtn").click(function(){
		var itemId = $("#edit-itemId").val();
		if(itemId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信图文详情信息
	$("#delWXNewsItemsBtn").click(function(){
		//logicDelWXNewsItems();//逻辑删除
		delWXNewsItems()
	});
	
	//动态改变广告及点赞状态
	Handlebars.registerHelper("transformat",function(value){
        if(value=="T"){
       	  return "是";
	     }else if(value=="F"){
	          return "否";
        }
	});
});


//初始化微信图文详情列表信息
function initWXNewsItemsListInfo(currentPage, limit){
	if(typeof currentPage == "undefined"){
		currentPage = 1;
	}
	if(typeof limit == "undefined"){
		limit = global_limit;
	}
	var newsTempId = $("#edit-newsTempId").val();
	var url = contextPath + "/wx/newsitems/getList";
	var params = "&pageNumber="+currentPage+"&limit="+limit+"&newsTempId="+newsTempId;
	//异步请求微信图文详情列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		var source = $("#wXNewsItems-list-template").html();
		var templet = Handlebars.compile(source);
		if(flag && data.returnCode=="0"){
			//渲染列表数据
			var htmlStr = templet(data.beans);
			$("#wXNewsItemsListTable").html(htmlStr);
			//初始化分页数据(当前页码，总数，回调查询函数)
			initPaginator(currentPage, data.bean.count, initWXNewsItemsListInfo);
		}
	});
}


//新增微信图文详情信息
function addWXNewsItems(){
	var url = contextPath + "/wx/newsitems/insertWXNewsItems";
	var params = $("#addWXNewsItemsForm").serialize();
	//异步请求新增微信图文详情信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				//$("#alert-info-content").html("微信图文详情新增成功!");
				//$("#alert-info").modal("show");
				alert("微信图文详情新增成功!",$.scojs_message.TYPE_OK);
				$("#myModal").modal('hide');
				//重新请求列表数据
				initWXNewsItemsListInfo();
				//清空新增微信图文详情的弹窗信息
				$("#addWXNewsItemsForm input").val("");
				$("#descn").val("");
			}
		}else{
			if(data.returnCode=="-1"){
				alert("微信图文详情编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
			}else{
				alert(data.returnMessage);
			}
		}
	});
}


//修改微信图文详情信息
function editWXNewsItems(){
	var itemId = $("#edit-itemId").val();
	
	if(itemId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	var url = contextPath + "/wx/newsitems/updateWXNewsItems";
	var params = $("#editWXNewsItemsForm").serialize();
	//异步请求修改微信图文详情信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				alert("微信图文详情信息修改成功!",$.scojs_message.TYPE_OK);
				//编辑微信图文详情信息清空
				$("#editWXNewsItemsForm input").val("");
				$("#edit-descn").val("");
				//重新请求列表数据
				initWXNewsItemsListInfo();
				$("#myModal1").modal('hide');
			}
		}else{
			if(data.returnCode=="-1"){
				alert("微信图文详情编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
			}else{
				alert(data.returnMessage);
			}
		}
	});
}

//删除微信图文详情
function delWXNewsItems(itemId,newsTempId){
	if(itemId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	confirm("确定删除吗？",function(){
		var url = contextPath + "/wx/newsitems/deleteWXNewsItems";
		$("#edit-itemId").val(itemId);
		var params = $("#editWXNewsItemsForm").serialize();
		//异步请求逻辑删除微信图文详情信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信图文详情删除成功!",$.scojs_message.TYPE_OK);
					//编辑微信图文详情信息清空
					$("#editWXNewsItemsForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					C.load(contextPath+"/wx/newstemplates/newsItemEdit?newsTempId="+newsTempId);
				}
			}else{
				alert(data.returnMessage);
			}
		});
	});
}

//选择返回跳转页面
function backPageByNewsList(){
	var param = $("#htmlHostTittle").val();
	if("微信文章" == param.trim()){
		C.load(contextPath+"/wx/article/list");
	}else if("菜单内容" == param.trim()){
		C.load(contextPath+"/wx/newstemplates/list");
	}
}
