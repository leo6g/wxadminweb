$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化中奖信息表列表信息
	initLuckyDogsListInfo();
	
	
	//新增中奖信息表信息时，validate验证，验证通过后调用保存方法 
	$("#addLuckyDogsForm").validate({
        submitHandler:function(form){
        	addLuckyDogs();
        }    
    });
	
	//新增中奖信息表信息
	$("#saveLuckyDogsBtn").click(function(){
		var form = $("#addLuckyDogsForm");
		form.submit();
	});
	
	//选中中奖信息表信息
	$("#luckyDogsListTable").delegate("tr","click",function(){
		var awardsId = $(this).find("td:eq(1)").html();
		var activityId = $(this).find("td:eq(2)").html();
		var id = $(this).find("td:eq(3)").html();
		var phoneNumber = $(this).find("td:eq(4)").html();
		var openId = $(this).find("td:eq(6)").html();
		
		$("#edit-awardsId").val(awardsId);
		$("#edit-activityId").val(activityId);
		$("#edit-id").val(id);
		$("#edit-phoneNumber").val(phoneNumber);
		$("#edit-openId").val(openId);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改中奖信息表信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editLuckyDogsForm").validate({
        submitHandler:function(form){
        	editLuckyDogs();
        }    
    });
	
	//保存编辑的中奖信息表信息
	$("#edit-saveLuckyDogsBtn").click(function(){
		var form = $("#editLuckyDogsForm");
		form.submit();
	});
	//编辑中奖信息表信息
	$("#editLuckyDogsBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除中奖信息表信息
	$("#delLuckyDogsBtn").click(function(){
		//logicDelLuckyDogs();//逻辑删除
		delLuckyDogs()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initLuckyDogsListInfo();
	});
	
	//初始化中奖信息表列表信息
	function initLuckyDogsListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/lucky/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求中奖信息表列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			//console.info(data);
			var source = $("#luckyDogs-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#luckyDogsListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initLuckyDogsListInfo);
			}
		});
	}
	
	
	//新增中奖信息表信息
	function addLuckyDogs(){
		var url = contextPath + "/biz/lucky/insertLuckyDogs";
		var params = $("#addLuckyDogsForm").serialize();
		//异步请求新增中奖信息表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("中奖信息表新增成功!");
					//$("#alert-info").modal("show");
					alert("中奖信息表新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initLuckyDogsListInfo();
					//清空新增中奖信息表的弹窗信息
					$("#addLuckyDogsForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("中奖信息表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改中奖信息表信息
	function editLuckyDogs(){
		var awardsId = $("#edit-awardsId").val();
	var activityId = $("#edit-activityId").val();
	var id = $("#edit-id").val();
	
		if(awardsId==""){
		alert("中奖奖品ID不能为空!");
		return false;
	}
	if(activityId==""){
		alert("活动ID不能为空!");
		return false;
	}
	if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/lucky/updateLuckyDogs";
		var params = $("#editLuckyDogsForm").serialize();
		//异步请求修改中奖信息表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("中奖信息表信息修改成功!");
					//编辑中奖信息表信息清空
					$("#editLuckyDogsForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initLuckyDogsListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("中奖信息表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除中奖信息表
	function delLuckyDogs(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/lucky/deleteLuckyDogs";
			var params = $("#editLuckyDogsForm").serialize();
			//异步请求逻辑删除中奖信息表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("中奖信息表删除成功!");
						//编辑中奖信息表信息清空
						$("#editLuckyDogsForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initLuckyDogsListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除中奖信息表
	function logicDelLuckyDogs(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/lucky/logicDeleteLuckyDogs";
			var params = $("#editLuckyDogsForm").serialize();
			//异步请求逻辑删除中奖信息表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("中奖信息表删除成功!");
						//编辑中奖信息表信息清空
						$("#editLuckyDogsForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initLuckyDogsListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "//biz/lucky/exportData?";
		var formdata=$("#queryForm").serialize();
		window.location.href = url+ encodeURI(formdata);
	});

});



