$(document).ready(function(){
	//列表分页每页显示多少条记录
	global_limit = 10 ;
	
	
	//点击添加按钮
	$("#addBtn").click(function(){
		$("#myModal").modal("show");
	});
	
	//点击编辑按钮
	$("#editBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var code = $("#edit-code").val();
		var name = $("#edit-name").val();
		editDic(id, code, name);
	});
	
	//点击删除按钮
	$("#delBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		deleteDic(id);
	});
	
	//点击查看按钮
	$("#viewBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/system/dic/detail?id="+id);
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
		$("#searchForm input").val("");
	});
	
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
	
	//修改信息时，validate验证，验证通过后调用保存方法 
	$("#editForm").validate({
        submitHandler:function(form){
        	editObj();
        }    
    });
	
	//修改信息
	$("#edit-saveBtn").click(function(){
		var form = $("#editForm");
		form.submit();
	});
	
	

	//列表数据请求
	function getList(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/system/dic/getList";
		var params = "pageNumber="+currentPage + "&limit=" + limit + "&dicGroupName=" + $("#dicGroupName").val();
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
					var code = $(this).attr("data-code");
					$("#edit-code").val(code);
					var name = $(this).attr("data-name");
					$("#edit-name").val(name);
				});
				

				//查看按钮
				$(".view").click(function(){
					var id = $(this).attr("data-id");
					C.load(contextPath + "/system/dic/detail?id="+id);
				});
				
			}
		});
		//清空隐藏域信息
		$("#edit-id").val("");
		$("#edit-code").val("");
		$("#edit-name").val("");
	}


	//新增信息
	function insertObj(){
		var url = contextPath + "/system/dic/insertDicGroup";
		var params = $("#addForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("新增成功!");
					$('#myModal').modal('hide');
					//重新请求列表数据
					getList();
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


	//编辑信息
	function editObj(){
		var url = contextPath + "/system/dic/updateDicGroup";
		var params = $("#editForm").serialize();
		//异步请求编辑信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("修改成功!");
					$('#edit-Modal').modal('hide');
					//重新请求列表数据
					getList();
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


	/**
	 * 编辑信息
	 */
	function editDic(id, code, name){
		$("#edit-id").val(id);
		$("#edit-dicGroupCode").val(code);
		$("#edit-dicGroupName").val(name);
		$('#edit-Modal').modal('show');
	}


	/**
	 * 删除信息
	 */
	function deleteDic(id){
		confirm("确定删除吗？", function(){
			var url = contextPath + "/system/dic/deleteDicGroup";
			var params = "id="+id;
			//异步请求编辑信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("删除成功!");
						//重新请求列表数据
						getList();
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


