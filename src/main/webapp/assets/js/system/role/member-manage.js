
$(document).ready(function(){
	//global_limit=2;
	var roleId =  $("#roleId").val();
	var currentPage=1;
	var limit=global_limit;
	var departId='';
	var map = {};
	var choosenUserId = [];//用户已选中的用户ID
	var dbChoosenUserId = [];//数据库查询出来的已选中的
	//角色基本信息
	initRoleInfo(roleId);
	//初始化所有的机构信息
	initDepartTree();
	//初始化已经关联的用户
	initRUInfo(roleId);
	//默认选择第一个节点机构下的用户信息,用模拟点击实现
//	initUserListInfo(currentPage, limit,departId);
	$("#depart-tree .list-group li:first").click();
	//将选中的用户添加到div
	$("#user-list-table").on("click.first",".userinfo",function(e){
		var checked = $(this).is(':checked');
		var userId = $(this).attr('userid');
		var userName = $(this).attr("userName");
		//把userId和对应选中状态先存放到map中，后续再用
		map[userId] = checked;
		makeArr(map);
		//更改选中用户box的显示状态和内容
		displayUserBox();
		if(checked && $("#"+userId).length === 0){
			addUserSpan(userId,userName);
		}else{
			delUserSpan(userId);
		}
	});
	
	function makeArr(map){
		//每次要清空一次容器
		choosenUserId = [];
		for(var k in map){
			if(map[k]){
				choosenUserId.push(k);
			}
		}
	}
	//插入角色用户关联系信息
	$("#insertRoleUser").click(function(){
			var url = contextPath + "/system/role/insertRoleUserInfo?id="+roleId;
			var params = "";
			if(choosenUserId.length<=0){
				alert("请至少选中一个用户");
			}
			 var roleUserForm = new Array();
			 if(choosenUserId.length>0){
				 for(var i=0;i<choosenUserId.length;i++){
					 var ru={};
					 ru.roleId=roleId;
					 ru.userId=choosenUserId[i];
					 roleUserForm.push(ru);
				 }
			 }
			//异步请求按钮数据
			    $.ajax({ 
			        type:"POST", 
			        url:url, 
			        dataType:"json",      
			        contentType:"application/json",               
			        data:JSON.stringify(roleUserForm), 
			        success:function(data){ 
			    		if(data.returnCode=="0"){
			    			alert(data.returnMessage);
			    			//添加成功后赋值null,避免重复提交
			    			checkMenuIds=null;
			    			checkButtonIds=null;
			    			C.load(contextPath+"/system/role/list");
			    		}else{
			    			alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
			    		}                         
			        } 
			     }); 
	});
	
	
	//初始化角色基本信息
	function initRoleInfo(roleId){
		var url = contextPath + "/system/role/queryRoleById?id="+roleId;
		var params = "";
		//异步请求按钮数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				var viewinfo = eval(data.object);
				$("#roleName").text(viewinfo.roleName);
				$("#roleCode").text(viewinfo.roleCode);
			}
		});
	}
	//初始化机构树状信息
	function initDepartTree(pageNumber, limit){
		var url = contextPath + "/system/depart/getDepartTree";
		var params = "";
		//异步请求机构树数据
		Util.ajax.postJsonSync(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				departId=data.beans[0].tags.id;
				//树初始化数据
			    $('#depart-tree').treeview({
			      color: "#333",
			      data: data.beans
			    });
			    //默认树状菜单全部合并
			    $('#depart-tree').treeview('collapseAll', { silent: true });
				//选中某个机构信息
			    $('#depart-tree').on('nodeSelected', function(event, data) {
			   	  	var departId = data.tags.id;
			   	  	//查询选择机构的上级机构以及机构级别信息
			   	 initUserListInfo(currentPage, limit,departId);
			    });
			}
		});
	}


	//初始化机构对应的用户列表
	function initUserListInfo(currentPage, limit,departId){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		  var url = contextPath + "/system/role/queryUserByDepartId";
	   	  var params= "departId="+departId;
		//异步请求职位列表数据
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#user-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				if(data.beans.length != 0) {
					var htmlStr = templet(data.beans);
					$("#user-list-table").html(htmlStr);
					//选中checkbox按钮
					$(".userinfo").removeAttr("checked");
					for(var i=0;i<choosenUserId.length;i++){
						$("input[userid="+choosenUserId[i]+"]").prop('checked',true);
					}
					//初始化分页数据(当前页码，总数，回调查询函数)
					initPaginator(currentPage, data.bean.count, initUserListInfo);
				} else {
					$("#user-list-table").html("");
					$(".page-box").hide();
				}
			}
		});
	}
	//初始化已经选中的用户信息
	function initRUInfo(roleId){
		var url = contextPath + "/system/role/queryRUById?id="+roleId;
		var params = "";
		//异步请求按钮数据
		Util.ajax.postJsonSync(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				var viewinfo = data.beans;
				//将用户id放入ID列表，模拟维护列表和按钮选中状态对象
				choosenUserId = [];
				for(var i = 0; i < viewinfo.length; i++){
					choosenUserId.push(viewinfo[i].userId);
					map[viewinfo[i].userId] = true;
					addUserSpan(viewinfo[i].userId,viewinfo[i].userName);
				}
				//更改用户列表盒子的显示状态和内容
				displayUserBox();
			}
		});
	}

	//移除选中的用户
	$(".removeUser").click(function(){
		var data_id=$(this).attr("data-id");
		var spanId="#"+data_id;
		$("input[userid="+data_id+"]").click();
		$(spanId).remove();
	});

	//显示或者隐藏用户名列表box
	function displayUserBox(){
		var userBox = $("#choosen-user-box");
		if(choosenUserId.length>0){
			userBox.show();
		}else{
			userBox.hide();
		}
	}
	//显示一个用户名列表
	function addUserSpan(userId,userName){
		var span = "<span style='margin-left:10px' id="+userId+">"+userName+"&nbsp;<a class='removeUser' data-id='"+userId+"' href='javascript:void(0)'>x</a></span>";
		$("#choosen-user-box").append(span);
	}
	//删除一个用户名列表
	function delUserSpan(userId){
		$("#"+userId).remove();
	}

});






