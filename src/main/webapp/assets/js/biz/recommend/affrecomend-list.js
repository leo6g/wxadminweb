$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化员工推荐列表信息
	initStaffRecomendListInfo();
	
	
	//新增员工推荐信息时，validate验证，验证通过后调用保存方法 
	$("#addStaffRecomendForm").validate({
        submitHandler:function(form){
        	addStaffRecomend();
        }    
    });
	
	//新增员工推荐信息
	$("#saveStaffRecomendBtn").click(function(){
		var form = $("#addStaffRecomendForm");
		form.submit();
	});
	
	//选中员工推荐信息
	$("#staffRecomendListTable").delegate("tr","click",function(){
		var openId = $(this).find("td:eq(1)").html();
		var staffCode = $(this).find("td:eq(2)").html();
		var id = $(this).find("td:eq(3)").html();
		var cancelTime = $(this).find("td:eq(4)").html();
		var subscribeTime = $(this).find("td:eq(5)").html();
		var headImg = $(this).find("td:eq(6)").html();
		var nickName = $(this).find("td:eq(7)").html();
		var curState = $(this).find("td:eq(8)").html();
		var resubTime = $(this).find("td:eq(9)").html();
		
		$("#edit-openId").val(openId);
		$("#edit-staffCode").val(staffCode);
		$("#edit-id").val(id);
		$("#edit-cancelTime").val(cancelTime);
		$("#edit-subscribeTime").val(subscribeTime);
		$("#edit-headImg").val(headImg);
		$("#edit-nickName").val(nickName);
		$("#edit-curState").val(curState);
		$("#edit-resubTime").val(resubTime);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改员工推荐信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editStaffRecomendForm").validate({
        submitHandler:function(form){
        	editStaffRecomend();
        }    
    });
	
	//保存编辑的员工推荐信息
	$("#edit-saveStaffRecomendBtn").click(function(){
		var form = $("#editStaffRecomendForm");
		form.submit();
	});
	//编辑员工推荐信息
	$("#editStaffRecomendBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除员工推荐信息
	$("#delStaffRecomendBtn").click(function(){
		//logicDelStaffRecomend();//逻辑删除
		delStaffRecomend()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initStaffRecomendListInfo();
	});
	//局部绑定回车事件
	 $("#queryNickName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   });
	
	//自动转换类型
	 Handlebars.registerHelper("transformat",function(value){
        if(value=="T"){
       	   return "关注";
	     }else if(value=="F"){
	       return "取消关注";
        }else if(value=="R"){
	       return "重复关注";
        }
	 });
	//初始化员工推荐列表信息
	function initStaffRecomendListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/recommend/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求员工推荐列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#staffRecomend-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#staffRecomendListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initStaffRecomendListInfo);
			}
		});
	}
	
	
	//新增员工推荐信息
	function addStaffRecomend(){
		var url = contextPath + "/biz/recommend/insertStaffRecomend";
		var params = $("#addStaffRecomendForm").serialize();
		//异步请求新增员工推荐信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("员工推荐新增成功!");
					//$("#alert-info").modal("show");
					alert("员工推荐新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initStaffRecomendListInfo();
					//清空新增员工推荐的弹窗信息
					$("#addStaffRecomendForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("员工推荐编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改员工推荐信息
	function editStaffRecomend(){
		var id = $("#edit-id").val();
	
		if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/recommend/updateStaffRecomend";
		var params = $("#editStaffRecomendForm").serialize();
		//异步请求修改员工推荐信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("员工推荐信息修改成功!");
					//编辑员工推荐信息清空
					$("#editStaffRecomendForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initStaffRecomendListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("员工推荐编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除员工推荐
	function delStaffRecomend(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/recommend/deleteStaffRecomend";
			var params = $("#editStaffRecomendForm").serialize();
			//异步请求逻辑删除员工推荐信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("员工推荐删除成功!");
						//编辑员工推荐信息清空
						$("#editStaffRecomendForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initStaffRecomendListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除员工推荐
	function logicDelStaffRecomend(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/recommend/logicDeleteStaffRecomend";
			var params = $("#editStaffRecomendForm").serialize();
			//异步请求逻辑删除员工推荐信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("员工推荐删除成功!");
						//编辑员工推荐信息清空
						$("#editStaffRecomendForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initStaffRecomendListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



