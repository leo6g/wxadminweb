$(document).ready(function(){
	//列表分页每页显示多少条记录
	global_limit = 10 ;
	getList();
	getFile("uploadBtn","filePath","/system/manager/importUserInfo","5 MB","*.xls;*.xlsx;","100","20",getList);
	getAllRoles();
	
	//点击添加按钮
	$("#addBtn").click(function(){
		C.load(contextPath + "/system/manager/add");
	});
	
	//点击编辑按钮
	$("#editBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/system/manager/edit?id="+id);
	});
	
	//点击删除按钮
	$("#delBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		deleteObj(id);
	});
	
	//点击查看按钮
	$("#viewBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/system/manager/view?id="+id);
	});
	
	
	
	
	//URL传入的页码，如果有值，以URL页码为准。
	var currPage = Util.browser.getParameter("currPage");
	var reg = new RegExp("^[0-9]*$");
	if(currPage != null && reg.test(currPage)){
		currentPage = parseInt(currPage);
		$("#currPage").val(currentPage);
	}else{
		currPage = 1;
	}
	//列表数据请求
	getList(currPage, global_limit);
	
	//点击查询按钮
	$("#searchBtn").click(function(){
		getList();
	});
	
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#searchForm input[type='text']").val("");
		getList();
	});
	

	//列表数据请求
	function getList(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/system/manager/getList";
		var params = "pageNumber="+currentPage + "&limit=" + limit + "&" + $("#searchForm").serialize();
		//异步请求列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#listTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, getList);
				
				
				//table列点击事件
				$("#listTable tr").click(function(){
					var id = $(this).attr("data-id");
					$("#edit-id").val(id);
				});
				
				//冻结按钮
				$(".freeze").click(function(){
					var id = $(this).attr("data-id");
					freeze(id);
				});
				
				//解冻按钮
				$(".unfreeze").click(function(){
					var id = $(this).attr("data-id");
					unfreeze(id);
				});
				
			}
		});
		//清空隐藏域信息
		$("#edit-id").val("");
	}



	/**
	 * 冻结
	 */
	function freeze(id){
		confirm("确定冻结该用户吗？", function(){
			var url = contextPath + "/system/manager/freezeManager";
			var params = "id="+id;
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("冻结成功!");
						//重新请求列表数据
						getList();
					}
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			});
		});
	}

	/**
	 * 解冻
	 */
	function unfreeze(id){
		confirm("确定解冻该用户吗？", function(){
			var url = contextPath + "/system/manager/unfreezeManager";
			var params = "id="+id;
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("解冻成功!");
						//重新请求列表数据
						getList();
					}
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			});
		});
	}

	/**
	 * 逻辑删除
	 */
	function deleteObj(id){
		confirm("确定删除该用户吗？",function(){
			var url = contextPath + "/system/manager/deleteManager";
			var params = "id="+id;
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("用户删除成功!");
						//重新请求列表数据
						getList();
					}
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			});
			
		});
	}
	
	//获取所有角色
	function getAllRoles(){
		var url = contextPath + "/system/role/selectAllRoles";
		//异步请求列表数据
		Util.ajax.postJson(url, {}, function(data, flag){
			var source = $("#role-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#roleTable").html(htmlStr);
			}
		});
	}
	
	//插入角色用户关联系信息
	$("#saveRoleBtn").click(function(){
		var url = contextPath + "/system/manager/insertRoleUserInfo";
		var userId = $("#edit-id").val();
		var roleUserForm = [];
		$("#roleTable input").each(function(){
			if($(this).is(':checked')) {
				var ru={};
				ru.roleId=$(this).val();
				ru.userId=userId;
				roleUserForm.push(ru);
			}
		});
		//异步请求按钮数据
	    $.ajax({ 
	        type:"POST", 
	        url:url, 
	        dataType:"json",      
	        contentType:"application/json",               
	        data:JSON.stringify(roleUserForm), 
	        success:function(data){
	    		if(data.returnCode=="0"){
	    			$('#myModal5').modal('hide');
	    			alert(data.returnMessage);
	    		}else{
	    			alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
	    		}                         
	        } 
	     });
	});
	
	//根据点击的用户默认选中拥有的权限
	$('#myModal5').on('show.bs.modal', function (e) {
		//清空已经选中的权限
		$("#roleTable input").each(function(){
			$(this).prop("checked",false);
		});
		var url = contextPath + "/system/manager/getRolesByUserId";
		var userId = $("#edit-id").val();
		if(userId) {
			//异步请求列表数据
			Util.ajax.postJson(url, {userId:userId}, function(data, flag){
				if(flag && data.returnCode=="0"){
					$(data.beans).each(function(j,item2){
						$("#roleTable input").each(function(i,item){
							if(item2.roleId == $(item).val()) {
								$(item).prop("checked",true);
								return false;
							}
						});
					});
				}
			});
		} else {
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	
	//用户点击重置密码
	$('#myModal6').on('show.bs.modal', function (e) {
		var url = contextPath + "/system/manager/getRolesByUserId";
		var userId = $("#edit-id").val();
		if(!userId) {
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//重置密码时，validate验证，验证通过后调用保存方法 
	$("#resetPwdForm").validate({
        submitHandler:function(form){
        	resetPassword();
        }    
    });
	
	
	//点击重置密码按钮
	$("#resetPwdBtn").click(function(){
		var password = $("#newPassWord").val();
		if(!checkPass(password)){
			return;
		}
		var form = $("#resetPwdForm");
		form.submit();
	});
	
	
	
	//调取后台方法，重置密码
	function resetPassword(){
		var newPassWord=$("#newPassWord").val();
		var userId = $("#edit-id").val();
		var url = contextPath + "/system/manager/editPassWord";
		var params = {};
		params.passWord=newPassWord;
		params.id=userId;
		//添加电话号码这个参数，并且设置为空，原因是后台editPassWord方法中有个可以修改用户电话号码的业务逻辑，并且如果不传这个参数的话会报错，只能先设置为空了。
		params.phoneNumber="";
		//异步请求修改密码信息
		Util.ajax.postJsonSync(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
	    	    	alert("密码修改成功.");
					$("#myModal6").modal('hide');
					//清空修改密码弹窗信息
					$("#resetPwdForm input").val("");
				}
			}else{
				alert(data.returnMessage);
			}
		});
	}
	
	
	
	
});

