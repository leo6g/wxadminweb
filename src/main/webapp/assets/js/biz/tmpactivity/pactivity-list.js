$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化预约中奖信息设置列表信息
	initTmpActivityListInfo();
	
	
	//新增预约中奖信息设置信息时，validate验证，验证通过后调用保存方法 
	$("#addTmpActivityForm").validate({
        submitHandler:function(form){
        	addTmpActivity();
        }    
    });
	
	//新增预约中奖信息设置信息
	$("#saveTmpActivityBtn").click(function(){
		var form = $("#addTmpActivityForm");
		form.submit();
	});
	
	//选中预约中奖信息设置信息
	$("#tmpActivityListTable").delegate("tr","click",function(){
		var openId = $(this).find("td:eq(1)").html();
		var id = $(this).find("td:eq(3)").html();
		var extStr2 = $(this).find("td:eq(5)").html();
		var extStr1 = $(this).find("td:eq(4)").html();
		
		$("#edit-type").val(type);
		$("#edit-openId").val(openId);
		$("#edit-id").val(id);
		$("#edit-extStr4").val(extStr4);
		$("#edit-extStr3").val(extStr3);
		$("#edit-extStr2").val(extStr2);
		$("#edit-extStr1").val(extStr1);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改预约中奖信息设置信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editTmpActivityForm").validate({
        submitHandler:function(form){
        	editTmpActivity();
        }    
    });
	
	//保存编辑的预约中奖信息设置信息
	$("#edit-saveTmpActivityBtn").click(function(){
		var form = $("#editTmpActivityForm");
		form.submit();
	});
	//编辑预约中奖信息设置信息
	$("#editTmpActivityBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除预约中奖信息设置信息
	$("#delTmpActivityBtn").click(function(){
		//logicDelTmpActivity();//逻辑删除
		delTmpActivity()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initTmpActivityListInfo();
	});
	
	//初始化预约中奖信息设置列表信息
	function initTmpActivityListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/tmpactivity/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求预约中奖信息设置列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#tmpActivity-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#tmpActivityListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initTmpActivityListInfo);
			}
		});
	}
	
	
	//新增预约中奖信息设置信息
	function addTmpActivity(){
		var url = contextPath + "/biz/tmpactivity/insertTmpActivity";
		var params = $("#addTmpActivityForm").serialize();
		//异步请求新增预约中奖信息设置信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("预约中奖信息设置新增成功!");
					//$("#alert-info").modal("show");
					alert("预约中奖信息设置新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initTmpActivityListInfo();
					//清空新增预约中奖信息设置的弹窗信息
					$("#addTmpActivityForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("预约中奖信息设置编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改预约中奖信息设置信息
	function editTmpActivity(){
		var id = $("#edit-id").val();
	
		if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/tmpactivity/updateTmpActivity";
		var params = $("#editTmpActivityForm").serialize();
		//异步请求修改预约中奖信息设置信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("预约中奖信息设置信息修改成功!");
					//编辑预约中奖信息设置信息清空
					$("#editTmpActivityForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initTmpActivityListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("预约中奖信息设置编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除预约中奖信息设置
	function delTmpActivity(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/tmpactivity/deleteTmpActivity";
			var params = $("#editTmpActivityForm").serialize();
			//异步请求逻辑删除预约中奖信息设置信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("预约中奖信息设置删除成功!");
						//编辑预约中奖信息设置信息清空
						$("#editTmpActivityForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initTmpActivityListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除预约中奖信息设置
	function logicDelTmpActivity(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/tmpactivity/logicDeleteTmpActivity";
			var params = $("#editTmpActivityForm").serialize();
			//异步请求逻辑删除预约中奖信息设置信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("预约中奖信息设置删除成功!");
						//编辑预约中奖信息设置信息清空
						$("#editTmpActivityForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initTmpActivityListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



