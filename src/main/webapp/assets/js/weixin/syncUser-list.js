//列表分页每页显示多少条记录
$(document).ready(function(){
	var global_limit = 10 ;
	
	//初始化微信关注用户表列表信息
	initWXUserListInfo();
	initWXUserGroupListInfo();
	
	//同步微信用户信息
	$("#syncWXUser").click(function(){
		syncWXUser()
	});
	
	
	
	
	
	//查询
	$("#querySubmit").click(function(){
		initWXUserListInfo();
	});

	//局部绑定回车事件
	 $("#queryNickName").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 
//初始化微信关注用户表列表信息
	function initWXUserListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/user/getList";
		var groupId = $("#userGroupId").val();
		var params = $("#queryForm").serialize();
		if(groupId){
			params = params + "&pageNumber="+currentPage+"&limit="+limit+"&groupId="+groupId;
		}else{
			params = params + "&pageNumber="+currentPage+"&limit="+limit;
		}
		//var params = "pageNumber="+currentPage+"&limit="+limit+"&groupId="+groupId;
		//var params = "pageNumber="+currentPage+"&limit="+limit;
		//异步请求微信关注用户表列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#wXUser-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#wXUserListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initWXUserListInfo);
			}
		});
	}
//初始化微信用户组列表信息
	function initWXUserGroupListInfo(currentPage, limit){
		var url = contextPath + "/wx/usergroup/getAll";
		var params ="";
		//异步请求微信用户组列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#wxUserGroup-template").html();
			var source2 = $("#wxUserGroup-template2").html();
			var templet = Handlebars.compile(source);
			var templet2 = Handlebars.compile(source2);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				var htmlStr2 = templet2(data.beans);
				$("#userGroupId").html(htmlStr);
				$("#userMoveGroupId").html(htmlStr2);
				//初始化分页数据(当前页码，总数，回调查询函数)
			}
		});
	}
	
	
//删除微信用户组
	function syncWXUser(){
		confirm("确定同步吗？",function(){
			var url = contextPath + "/wx/user/syncWXUses";
			//异步请求逻辑删除微信用户组信息
			var params = $("#editWXUserGroupForm").serialize();
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="1"){
						alert(data.returnMessage);
						initWXUserGroupListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
});

