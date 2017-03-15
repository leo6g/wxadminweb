$(document).ready(function(){
	//列表分页每页显示多少条记录
	global_limit = 10 ;
	
	//初始化职位列表信息
	initPosListInfo();
	
	
	//新增职位信息时，validate验证，验证通过后调用保存方法 
	$("#addPosForm").validate({
        submitHandler:function(form){
        	addPosition();
        }    
    });
	
	//新增职位信息
	$("#savePosBtn").click(function(){
		var form = $("#addPosForm");
		form.submit();
	});
	
	//选中职位信息
	$("#posListTable").delegate("tr","click",function(){
		var id = $(this).find("td:eq(1)").html();
		var descn = $(this).find("td:eq(2)").html();
		var pozOrder = $(this).find("td:eq(3)").html();
		var pozName = $(this).find("td:eq(4)").html();
		var pozCode = $(this).find("td:eq(5)").html();
		$("#edit-id").val(id);
		$("#edit-descn").val(descn);
		$("#edit-pozOrder").val(pozOrder);
		$("#edit-pozName").val(pozName);
		$("#edit-pozCode").val(pozCode);
	});
	
	
	//修改职位信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editPosForm").validate({
        submitHandler:function(form){
        	editPosition();
        }    
    });
	
	//保存编辑的职位信息
	$("#edit-savePosBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var form = $("#editPosForm");
		form.submit();
	});
	
	//删除职位信息
	$("#delPosBtn").click(function(){
		delPosition();
	});
	
	

	//初始化职位列表信息
	function initPosListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/system/position/getPositionListByPage";
		var params = "pageNumber="+currentPage+"&limit="+limit;
		//异步请求职位列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#position-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#posListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initPosListInfo);
			}
		});
		
		
		
		
		
	}


	//新增职位信息
	function addPosition(){
		var url = contextPath + "/system/position/insertPosition";
		var params = $("#addPosForm").serialize();
		//异步请求新增职位信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("职位新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initPosListInfo();
					//清空新增职位的弹窗信息
					$("#addPosForm input").val("");
					$("#descn").val("");
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


	//修改职位信息
	function editPosition(){
		var id = $("#edit-id").val();
		var pozName = $("#edit-pozName").val();
		var pozCode = $("#edit-pozCode").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(pozName.trim()==""){
			alert("职位名称不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(pozCode.trim()==""){
			alert("职位编码不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var url = contextPath + "/system/position/updatePosition";
		var params = $("#editPosForm").serialize();
		//异步请求修改职位信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("职位信息修改成功!");
					//编辑职位信息清空
					$("#editPosForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initPosListInfo();
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


	//逻辑删除职位
	function delPosition(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？", function(){
			var url = contextPath + "/system/position/logicDelPosition";
			var params = $("#editPosForm").serialize();
			//异步请求逻辑删除职位信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("职位删除成功!");
						//编辑职位信息清空
						$("#editPosForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initPosListInfo();
					}
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			});
			
		});
	}

	
});

