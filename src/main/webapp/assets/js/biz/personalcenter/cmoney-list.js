$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化个人中心列表信息
	initFicMoneyListInfo();
	
	
	//新增个人中心信息时，validate验证，验证通过后调用保存方法 
	$("#addFicMoneyForm").validate({
        submitHandler:function(form){
        	addFicMoney();
        }    
    });
	
	//新增个人中心信息
	$("#saveFicMoneyBtn").click(function(){
		var form = $("#addFicMoneyForm");
		form.submit();
	});
	
	//选中个人中心信息
	$("#ficMoneyListTable").delegate("tr","click",function(){
		var type = $(this).find("td:eq(1)").html();
		var openId = $(this).find("td:eq(2)").html();
		var ficId = $(this).find("td:eq(3)").html();
		var amount = $(this).find("td:eq(5)").html();
		
		$("#edit-type").val(type);
		$("#edit-openId").val(openId);
		$("#edit-ficId").val(ficId);
		$("#edit-amount").val(amount);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改个人中心信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editFicMoneyForm").validate({
        submitHandler:function(form){
        	editFicMoney();
        }    
    });
	
	//保存编辑的个人中心信息
	$("#edit-saveFicMoneyBtn").click(function(){
		var form = $("#editFicMoneyForm");
		form.submit();
	});
	//编辑个人中心信息
	$("#editFicMoneyBtn").click(function(){
		var ficId = $("#edit-ficId").val();
		if(ficId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除个人中心信息
	$("#delFicMoneyBtn").click(function(){
		//logicDelFicMoney();//逻辑删除
		delFicMoney()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initFicMoneyListInfo();
	});
	//自动转换类型
	 Handlebars.registerHelper("transformat",function(value){
       if(value=="signIn"){
      	  return "签到";
	     }else if(value=="praise"){
	          return "点赞文章";
       }else if(value=="remark"){
	          return "文章评论";
       }else if(value=="payment"){
	          return "缴费";
       }
	 });
	//初始化个人中心列表信息
	function initFicMoneyListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/ficmoney/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求个人中心列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#ficMoney-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#ficMoneyListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initFicMoneyListInfo);
			}
		});
	}
	
	
	//新增个人中心信息
	function addFicMoney(){
		var url = contextPath + "/biz/ficmoney/insertFicMoney";
		var params = $("#addFicMoneyForm").serialize();
		//异步请求新增个人中心信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("个人中心新增成功!");
					//$("#alert-info").modal("show");
					alert("个人中心新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initFicMoneyListInfo();
					//清空新增个人中心的弹窗信息
					$("#addFicMoneyForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("个人中心编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改个人中心信息
	function editFicMoney(){
		var ficId = $("#edit-ficId").val();
	
		if(ficId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/ficmoney/updateFicMoney";
		var params = $("#editFicMoneyForm").serialize();
		//异步请求修改个人中心信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("个人中心信息修改成功!");
					//编辑个人中心信息清空
					$("#editFicMoneyForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initFicMoneyListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("个人中心编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除个人中心
	function delFicMoney(){
		var ficId = $("#edit-ficId").val();
		if(ficId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/ficmoney/deleteFicMoney";
			var params = $("#editFicMoneyForm").serialize();
			//异步请求逻辑删除个人中心信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("个人中心删除成功!");
						//编辑个人中心信息清空
						$("#editFicMoneyForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initFicMoneyListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除个人中心
	function logicDelFicMoney(){
		var ficId = $("#edit-ficId").val();
		if(ficId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/ficmoney/logicDeleteFicMoney";
			var params = $("#editFicMoneyForm").serialize();
			//异步请求逻辑删除个人中心信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("个人中心删除成功!");
						//编辑个人中心信息清空
						$("#editFicMoneyForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initFicMoneyListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



