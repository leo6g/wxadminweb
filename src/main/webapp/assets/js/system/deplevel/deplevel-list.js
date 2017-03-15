$(document).ready(function(){
	//列表分页每页显示多少条记录
	global_limit = 10 ;
	
	//初始化机构级别列表信息
	initDepLevelListInfo();
	
	
	//新增机构级别信息时，validate验证，验证通过后调用保存方法 
	$("#addDepLevelForm").validate({
        submitHandler:function(form){
        	addDepLevel();
        }    
    });
	
	//新增机构级别信息
	$("#saveDepLevelBtn").click(function(){
		var form = $("#addDepLevelForm");
		form.submit();
	});
	
	//选中机构级别信息
	$("#depLevelListTable").delegate("tr","click",function(){
		var id = $(this).find("td:eq(1)").html();
		var descn = $(this).find("td:eq(2)").html();
		var levOrder = $(this).find("td:eq(3)").html();
		var levName = $(this).find("td:eq(4)").html();
		var levCode = $(this).find("td:eq(5)").html();
		$("#edit-id").val(id);
		$("#edit-descn").val(descn);
		$("#edit-levOrder").val(levOrder);
		$("#edit-levName").val(levName);
		$("#edit-levCode").val(levCode);
	});
	
	
	//修改机构级别信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editDepLevelForm").validate({
        submitHandler:function(form){
        	editDepLevel();
        }    
    });
	
	//保存编辑的机构级别信息
	$("#edit-saveDepLevelBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var form = $("#editDepLevelForm");
		form.submit();
	});
	
	//删除机构级别信息
	$("#delDepLevelBtn").click(function(){
		delDepLevel();
	});
	
	
	//初始化机构级别列表信息
	function initDepLevelListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/system/deplevel/getDepLevelListByPage";
		var params = "pageNumber="+currentPage+"&limit="+limit;
		//异步请求职位列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#deplevel-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#depLevelListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initDepLevelListInfo);
			}
		});
	}


	//新增机构级别信息
	function addDepLevel(){
		var url = contextPath + "/system/deplevel/insertDepLevel";
		var params = $("#addDepLevelForm").serialize();
		//异步请求新增机构级别信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("机构级别添加成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initDepLevelListInfo();
					//清空新增职位的弹窗信息
					$("#addDepLevelForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("机构级别编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			}
		});
	}


	//修改机构级别信息
	function editDepLevel(){
		var id = $("#edit-id").val();
		var levName = $("#edit-levName").val();
		var levCode = $("#edit-levCode").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(levName.trim()==""){
			alert("级别名称不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(levCode.trim()==""){
			alert("级别编码不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var url = contextPath + "/system/deplevel/updateDepLevel";
		var params = $("#editDepLevelForm").serialize();
		//异步请求修改机构级别信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("机构级别信息修改成功!");
					//编辑机构级别信息清空
					$("#editDepLevelForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initDepLevelListInfo();
				}
			}else{
				if(data.returnCode=="-1"){
					alert("机构级别编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			}
		});
	}


	//逻辑删除机构级别
	function delDepLevel(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？", function(){
			var url = contextPath + "/system/deplevel/logicDelDepLevel";
			var params = $("#editDepLevelForm").serialize();
			//异步请求逻辑删除职位信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("机构级别删除成功!");
						//编辑机构级别信息清空
						$("#editDepLevelForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initDepLevelListInfo();
					}
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
			});
			
		});
	}
	
	
});


