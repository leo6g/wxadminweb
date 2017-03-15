$(document).ready(function(){
	
	//列表分页每页显示多少条记录
	global_limit = 10 ;
	//初始化理财产品管理列表信息
	getList();
	getFile("uploadBtn","filePath",getList);
	//点击添加按钮
	$("#addBtn").click(function(){
		C.load(contextPath + "/biz/atm/add");
	});
	
	//点击编辑按钮
	$("#editBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/biz/atm/edit?id="+id);
	});
	
	//点击删除按钮
	$("#delBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		delObj(id);
	});
	
	//点击查看按钮
	$("#viewBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/biz/atm/view?id="+id);
	});
	
	
	//点击查询按钮
	$("#searchBtn").click(function(){
		getList();
	});
	//局部绑定回车事件
	 $("#address").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("searchBtn").click();
	    }
	   });
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#address").val("");
		getList();
	});
	
	
	
	//列表数据查询
	function getList(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/atm/getList";
		var params = "pageNumber="+currentPage + "&limit=" + limit + "&address=" + $("#address").val();
		//异步请求理财产品管理列表数据
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
				});
				
			}
		});
		//清空隐藏域信息
		$("#edit-id").val("");
	}
	
	
	//逻辑删除实体
	function delObj(id){
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/atm/logicDelObj";
			var params = "atmId="+id;
			//异步请求逻辑删除信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("删除成功!")					//重新请求列表数据
						getList();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



