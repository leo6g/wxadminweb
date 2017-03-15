$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微信抽奖活动列表信息
	initAwardActivityListInfo();
	
	//点击添加按钮
	$("#addBtn").click(function(){
		C.load(contextPath + "/biz/awardActivity/add");
	});
	
	//查询
//	$("#searchBtn").click(function(){
//		initAwardActivityListInfo();
//	});
	
	
	
	
	//新增微信抽奖活动信息时，validate验证，验证通过后调用保存方法 
	$("#addAwardActivityForm").validate({
        submitHandler:function(form){
        	addAwardActivity();
        }    
    });
	
	//新增微信抽奖活动信息
	$("#saveAwardActivityBtn").click(function(){
		var form = $("#addAwardActivityForm");
		form.submit();
	});
	
	//选中微信抽奖活动信息
	$("#awardActivityListTable").delegate("tr","click",function(){
		var startDate = $(this).find("td:eq(1)").html();
		var activityName = $(this).find("td:eq(2)").html();
		var activityId = $(this).find("td:eq(3)").html();
		var comments = $(this).find("td:eq(4)").html();
		var endDate = $(this).find("td:eq(5)").html();
		
		$("#edit-startDate").val(startDate);
		$("#edit-activityName").val(activityName);
		$("#edit-activityId").val(activityId);
		$("#edit-comments").val(comments);
		$("#edit-endDate").val(endDate);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改微信抽奖活动信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editAwardActivityForm").validate({
        submitHandler:function(form){
        	editAwardActivity();
        }    
    });
	
	//保存编辑的微信抽奖活动信息
	$("#edit-saveAwardActivityBtn").click(function(){
		var form = $("#editAwardActivityForm");
		form.submit();
	});
	//编辑微信抽奖活动信息
	$("#editAwardActivityBtn").click(function(){
		var activityId = $("#edit-activityId").val();
		if(activityId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信抽奖活动信息
	$("#delBtn").click(function(){
		delAwardActivity()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initAwardActivityListInfo('1');
	});
	
	//初始化微信抽奖活动列表信息
	function initAwardActivityListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/awardActivity/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit+"&"+$("#queryForm").serialize();
		//异步请求微信抽奖活动列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			console.info(data);
			var source = $("#awardActivity-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#awardActivityListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initAwardActivityListInfo);
			}
		});
	}
	
	
	//新增微信抽奖活动信息
	function addAwardActivity(){
		var url = contextPath + "/insertAwardActivity";
		var params = $("#addAwardActivityForm").serialize();
		//异步请求新增微信抽奖活动信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("微信抽奖活动新增成功!");
					//$("#alert-info").modal("show");
					alert("微信抽奖活动新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initAwardActivityListInfo();
					//清空新增微信抽奖活动的弹窗信息
					$("#addAwardActivityForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信抽奖活动编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改微信抽奖活动信息
	function editAwardActivity(){
		var activityId = $("#edit-activityId").val();
	
		if(activityId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/updateAwardActivity";
		var params = $("#editAwardActivityForm").serialize();
		//异步请求修改微信抽奖活动信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信抽奖活动信息修改成功!");
					//编辑微信抽奖活动信息清空
					$("#editAwardActivityForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initAwardActivityListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信抽奖活动编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除微信抽奖活动
	function delAwardActivity(){
		var activityId = $("#edit-activityId").val();
		if(activityId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/awardActivity/deleteAwardActivity";
			var params = $("#editAwardActivityForm").serialize();
			//异步请求逻辑删除微信抽奖活动信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信抽奖活动删除成功!");
						//编辑微信抽奖活动信息清空
						$("#editAwardActivityForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initAwardActivityListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除微信抽奖活动
	function logicDelAwardActivity(){
		var activityId = $("#edit-activityId").val();
		if(activityId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/logicDeleteAwardActivity";
			var params = $("#editAwardActivityForm").serialize();
			//异步请求逻辑删除微信抽奖活动信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信抽奖活动删除成功!");
						//编辑微信抽奖活动信息清空
						$("#editAwardActivityForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initAwardActivityListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



