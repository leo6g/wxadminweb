
$(document).ready(function(){
	
	//初始化机构树状信息
	initDepartTree();
	
	//获取机构所有机构级别信息
//	queryAllDepLevelInfo();
	
	//新增机构信息时，validate验证，验证通过后调用保存方法 
	$("#addDepartForm").validate({
        submitHandler:function(form){
        	addDepart();
        }    
    });
	
	//新增机构信息
	$("#saveDepartBtn").click(function(){
		var form = $("#addDepartForm");
		form.submit();
	});
	
	
	//修改机构信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editDepartForm").validate({
        submitHandler:function(form){
        	editDepart();
        }    
    });
	
	//保存编辑的机构信息
	$("#edit-saveDepartBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var form = $("#editDepartForm");
		form.submit();
	});
	
	//删除机构信息
	$("#delDepartBtn").click(function(){
		delDepart();
	});
	
	
	//初始化机构树状信息
	function initDepartTree(pageNumber, limit){
		var url = contextPath + "/system/depart/getDepartTree";
		var params = "";
		//异步请求机构树数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//树初始化数据
			    $('#depart-tree').treeview({
			      data: data.beans
			    });
			    //默认树状菜单全部合并
			    $('#depart-tree').treeview('collapseAll', { silent: true });
				//选中某个机构信息
			    $('#depart-tree').on('nodeSelected', function(event, data) {
			   	  	var id = data.tags.id;
			   	  	var descn = data.tags.descn;
			   	  	var parentId = data.tags.parentId;
			   	  	var departOrder = data.tags.departOrder;
			   	  	var departName = data.tags.departName;
			   	  	var departCode = data.tags.departCode;
			   	  	var levelRank = data.tags.levelRank;
			   	  	
			   	  	$("#edit-id").val(id);
			   	  	$("#edit-parentId").val(parentId);
			   	  	$("#edit-departName").val(departName);
			   	  	$("#edit-departCode").val(departCode);
			   	  	$("#edit-departOrder").val(departOrder);
			   	  	$("#edit-descn").val(descn);
			   	  	//查询选择机构的上级机构以及机构级别信息
			   	  	url = contextPath + "/system/depart/getDepartById";
			   	  	params = "id="+id;
				   	Util.ajax.postJson(url, params, function(data, flag){
				 		if(flag && data.returnCode=="0"){
				 			//选中机构级别
				 			$("#edit-levelRank").val(data.object.levelRank);
				 			//上级机构名称
				 			$("#edit-parentDepName").html("上级机构:"+data.object.parentDepName);
				 		}
				 	});
				   	//新增弹窗中，将选中的机构名称及ID放入弹窗中
				   	$("#parentId").val(id);
				   	$("#parentDepName").html("上级机构："+departName);
			    });
			}
		});
	}


	//获取所有机构级别信息
//	function queryAllDepLevelInfo(){
//		var url = contextPath + "/system/deplevel/getAllDepLevel";
//		var params = "";
//		//异步请求所有机构级别信息数据
//		Util.ajax.postJson(url, params, function(data, flag){
//			var source = $("#deplevel-list-template").html();
//			var templet = Handlebars.compile(source);
//			if(flag && data.returnCode=="0"){
//				//渲染列表数据，添加和编辑都需要展示
//				var htmlStr = templet(data.beans);
//				$("#edit-depLevelList").html(htmlStr);
//				$("#depLevelList").html(htmlStr);
//			}
//		});
//	}



	//新增机构信息
	function addDepart(){
		var url = contextPath + "/system/depart/insertDepart";
		var params = $("#addDepartForm").serialize();
		//异步请求新增机构信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("机构新增成功!");
					$("#myModal").modal('hide');
					//重新请求树数据
					initDepartTree();
					//清空新增职位的弹窗信息
					$("#addDepartForm input").val("");
					$("#descn").val("");
					$("#depLevelList").val("");
					$("#parentDepName").val("上级机构：---");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("职位编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			}
		});
	}


	//修改机构信息
	function editDepart(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var url = contextPath + "/system/depart/updateDepart";
		var params = $("#editDepartForm").serialize();
		//异步请求修改机构信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("机构信息修改成功!");
					//编辑机构信息清空
					$("#editDepartForm input").val("");
					$("#edit-descn").val("");
					$("#edit-levelRank").val("");
					$("#edit-parentDepName").val("");
					//重新请求树数据
					initDepartTree();
				}
			}else{
				if(data.returnCode=="-1"){
					alert("机构编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			}
		});
	}


	//逻辑删除机构
	function delDepart(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？", function(){
			var url = contextPath + "/system/depart/logicDelDepart";
			var params = "id="+id;
			//异步请求逻辑删除职位信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("机构删除成功!");
						//编辑机构信息清空
						$("#editDepartForm input").val("");
						$("#edit-descn").val("");
						$("#edit-levelRank").val("");
						$("#edit-parentDepName").val("");
						//重新请求树数据
						initDepartTree();
					}else{
						alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
					}
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			});
			
		});
	}
	
	
	
});


