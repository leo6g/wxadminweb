//列表分页每页显示多少条记录
$(document).ready(function(){
	var global_limit = 10 ;
	
	//初始化微信关注用户表列表信息
	initWXUserListInfo();
	initWXUserGroupListInfo();
	
	//新增微信用户组信息时，validate验证，验证通过后调用保存方法 
	$("#addWXUserGroupForm").validate({
        submitHandler:function(form){
        	addWXUserGroup();
        }    
    });
	
	//新增微信用户组信息
	$("#saveWXUserGroupBtn").click(function(){
		var name = $("#name").val();
		if(name==""){
			alert("输入不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		
		var form = $("#addWXUserGroupForm");
		form.submit();
	});
	
	//选中微信用户组信息
	$("#userGroupId").bind("change",function(){
		
		var name = $(this).find("option:selected").html();
		var groupId = $(this).val();
		$("#edit-name").val(name);
		$("#edit-groupId").val(groupId);
		initWXUserListInfo();
	});
	
	//选中移动组
	$("#userMoveGroupId").bind("change",function(){
		var name = $(this).find("option:selected").html();
		var groupId = $(this).val();
		$("#edit-name").val(name);
		$("#edit-groupId").val(groupId);
	});
	
	//修改微信用户组信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXUserGroupForm").validate({
        submitHandler:function(form){
        	editWXUserGroup();
        } 
    });
	
	//保存编辑的微信用户组信息
	$("#edit-saveWXUserGroupBtn").click(function(){
		var form = $("#editWXUserGroupForm");
		form.submit();
	});
	//编辑微信用户组信息
	$("#editWXUserGroupBtn").click(function(){
		var groupId = $("#userGroupId").val();
		if(groupId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信用户组信息
	$("#delWXUserGroupBtn").click(function(){
		//logicDelWXUserGroup();//逻辑删除
		delWXUserGroup()
	});
	
	//同步微信组信息到数据库
	$("#synWXUserGroupBtn").click(function(){
		synWXUserGroup();
	});
	
	
	//公众平台用户组同步到数据库
	function synWXUserGroup(){
		var url = contextPath + "/weixin/synWXUserGroup";
		//异步请求逻辑同步微信菜单信息
		Util.ajax.postJson(url,{}, function(data, flag){
			alert(data.returnMessage);
		});
	}
	
	//移动用户到某一个组
	$("#moveUser").click(function(){
		userGroup();
	});
	
	//
	function userGroup(){
		//obj = document.getElementsByName("selectUser");
		
		obj = $(":checked")
		check_val = [];
		for(k in obj){
			if(obj[k].checked)
				check_val.push(obj[k].value);
		}
		if(check_val.length==0){
			alert("请选择用户");
			return false;
		}
		var groupId = $("#userMoveGroupId").val();
		if(groupId==""||groupId==null){
			alert("请选择移动到的分组");
			return false;
		}
		var url = contextPath + "/weixin/moveUser";
		
		var params = "groupId="+$("#edit-groupId").val()+"&userID="+check_val
		//alert(check_val+$("#edit-groupId").val());
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("移动成功");
				initWXUserListInfo();
			}
		})
	}
	
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
	
	
//新增微信用户组信息
	function addWXUserGroup(){
		var url = contextPath + "/weixin/insertWXUserGroup";
		var params = $("#addWXUserGroupForm").serialize();
		//异步请求新增微信用户组信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信用户组新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initWXUserGroupListInfo();
					//清空新增微信用户组的弹窗信息
					$("#addWXUserGroupForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信用户组编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
//修改微信用户组信息
	function editWXUserGroup(){
		var url = contextPath + "/wx/usergroup/updateWXUserGroup";
		var params = $("#editWXUserGroupForm").serialize();
		//异步请求修改微信用户组信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信用户组信息修改成功!");
					//编辑微信用户组信息清空
					$("#editWXUserGroupForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXUserGroupListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信用户组编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
//删除微信用户组
	function delWXUserGroup(){
		var groupId = $("#userGroupId").val();
		if(groupId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/weixin/deleteWXUserGroup";
			var params = $("#editWXUserGroupForm").serialize();
			//异步请求逻辑删除微信用户组信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信用户组删除成功!");
						//编辑微信用户组信息清空
						$("#editWXUserGroupForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXUserGroupListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

	
	
	
//逻辑删除微信用户组
	function logicDelWXUserGroup(){
		var groupId = $("#userGroupId").val();
		if(groupId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/usergroup/logicDeleteWXUserGroup";
			var params = $("#editWXUserGroupForm").serialize();
			//异步请求逻辑删除微信用户组信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信用户组删除成功!");
						//编辑微信用户组信息清空
						$("#editWXUserGroupForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXUserGroupListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
});



//获取选中的微信用户
	function selectCheckbox(){
		obj = document.getElementsByName("selectUser");
		check_val = [];
		for(k in obj){
			if(obj[k].checked)
				check_val.push(obj[k].value);
		}
		alert(check_val);
	}

