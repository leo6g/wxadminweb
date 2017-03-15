
$(document).ready(function(){
	
	//初始化树信息
	initTree();
	
	//新增信息时，validate验证，验证通过后调用保存方法 
	$("#addForm").validate({
        submitHandler:function(form){
        	insertObj();
        }    
    });
	
	//新增信息
	$("#saveBtn").click(function(){
		var form = $("#addForm");
		form.submit();
	});
	
	
	//修改信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editForm").validate({
        submitHandler:function(form){
        	editObj();
        }    
    });
	
	//保存编辑的信息
	$("#edit-saveBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var form = $("#editForm");
		form.submit();
	});
	
	//删除信息
	$("#delBtn").click(function(){
		delObj();
	});
	
	$("#returnBtn").click(function(){
		C.load(contextPath + "/system/dic/list");
	});
	
	
	//初始化机构树状信息
	function initTree(pageNumber, limit){
		var dicGroupId = $("#dicGroupId").val();
		var url = contextPath + "/system/dic/getDicTree?dicGroupId="+dicGroupId;
		var params = "";
		//异步请求树数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//树初始化数据
			    $('#init-tree').treeview({
			      color: "#333",
			      data: data.beans
			    });
			    //默认树状菜单全部合并
			    $('#init-tree').treeview('collapseAll', { silent: true });
				//选中某个机构信息
			    $('#init-tree').on('nodeSelected', function(event, data) {
			   	  	var id = data.tags.id;
			   	  	var dicCode = data.tags.dicCode;
			   	  	var parentId = data.tags.parentId;
			   	  	var dicName = data.tags.dicName;
			   	  	var dicGroupId = data.tags.dicGroupId;
			   	  	$("#edit-id").val(id);
			   	  	$("#edit-dicCode").val(dicCode);
			   	  	$("#edit-dicName").val(dicName);
				   	//新增弹窗中，将选中的字典名称及ID放入弹窗中
				   	$("#parentId").val(id);
				   	$("#parentDicName").html("父级："+dicName);
			    });
			    
			    //没有选中任何节点
			    $('#init-tree').on('nodeUnselected', function(event, data) {
                    //清空新增弹窗的字典名称及ID
			    	$("#parentId").val("0");
				   	$("#parentDicName").html("父级：---");
			    });
			    
			}
		});
	}


	//新增信息
	function insertObj(){
		var url = contextPath + "/system/dic/insertDic";
		var params = $("#addForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("新增成功!");
					$('#myModal').modal('hide');
					//重新请求树数据
					initTree();
					//清空弹窗的信息
					$("#addForm input[type='text']").val("");
					$("#parentId").val("0");
					$("#parentDicName").html("父级：---");
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			}else{
				if(data.returnCode=="-1"){
					alert("编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			}
		});
	}


	//修改信息
	function editObj(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var url = contextPath + "/system/dic/updateDic";
		var params = $("#editForm").serialize();
		//异步请求修改信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("修改成功!");
					//编辑信息清空
					$("#editForm input[type='text']").val("");
					$("#edit-id").val("");
					$("#edit-parentId").val("");
					//重新请求树数据
					initTree();
				}
			}else{
				if(data.returnCode=="-1"){
					alert("编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			}
		});
	}


	//逻辑删除信息
	function delObj(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？", function(){
			var url = contextPath + "/system/dic/deleteObj";
			var params = "id="+id;
			//异步请求逻辑删除信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("删除成功!");
						//编辑信息清空
						$("#editForm input[type='text']").val("");
						$("#edit-id").val("");
						$("#edit-parentId").val("");
						//重新请求树数据
						initTree();
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


