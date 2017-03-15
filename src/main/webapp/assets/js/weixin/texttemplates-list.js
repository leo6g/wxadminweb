//列表分页每页显示多少条记录
$(document).ready(function(){
	var global_limit = 10 ;
	
	//初始化微信文本模版列表信息
	initWXTextTemplatesListInfo();
	
	
	//新增微信文本模版信息时，validate验证，验证通过后调用保存方法 
	$("#addWXTextTemplatesForm").validate({
        submitHandler:function(form){
        	addWXTextTemplates();
        }    
    });
	
	//新增微信文本模版信息
	$("#saveWXTextTemplatesBtn").click(function(){
		var form = $("#addWXTextTemplatesForm");
		form.submit();
	});
	
	//选中微信文本模版信息
	$("#wXTextTemplatesListTable").delegate("tr","click",function(){
		var content = $(this).find("td:eq(1)").html();
		var name = $(this).find("td:eq(2)").html();
		var textTempId = $(this).find("td:eq(3)").html();
		var createUser = $(this).find("td:eq(5)").html();
		
		$("#edit-content").val(content);
		$("#edit-name").val(name);
		$("#edit-textTempId").val(textTempId);
		$("#edit-createUser").val(createUser);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改微信文本模版信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXTextTemplatesForm").validate({
        submitHandler:function(form){
        	editWXTextTemplates();
        }    
    });
	
	//保存编辑的微信文本模版信息
	$("#edit-saveWXTextTemplatesBtn").click(function(){
		var form = $("#editWXTextTemplatesForm");
		form.submit();
	});
	//编辑微信文本模版信息
	$("#editWXTextTemplatesBtn").click(function(){
		var textTempId = $("#edit-textTempId").val();
		if(textTempId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信文本模版信息
	$("#delWXTextTemplatesBtn").click(function(){
		//logicDelWXTextTemplates();//逻辑删除
		delWXTextTemplates()
	});
	
//初始化微信文本模版列表信息
	function initWXTextTemplatesListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/texttemplates/getList";
		var params = $("#queryForm").serialize();
		 params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微信文本模版列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#wXTextTemplates-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#wXTextTemplatesListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initWXTextTemplatesListInfo);
			}
		});
	}
	
	//查询
	$("#querySubmit").click(function(){
		initWXTextTemplatesListInfo();
	});
	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 
	
//新增微信文本模版信息
	function addWXTextTemplates(){
		var url = contextPath + "/wx/texttemplates/insertWXTextTemplates";
		var params = $("#addWXTextTemplatesForm").serialize();
		//异步请求新增微信文本模版信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("微信文本模版新增成功!");
					//$("#alert-info").modal("show");
					alert("微信文本模版新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initWXTextTemplatesListInfo();
					//清空新增微信文本模版的弹窗信息
					$("#addWXTextTemplatesForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信文本模版编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
//修改微信文本模版信息
	function editWXTextTemplates(){
		var textTempId = $("#edit-textTempId").val();
		
		if(textTempId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		
		var url = contextPath + "/wx/texttemplates/updateWXTextTemplates";
		var params = $("#editWXTextTemplatesForm").serialize();
		//异步请求修改微信文本模版信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信文本模版信息修改成功!");
					//编辑微信文本模版信息清空
					$("#editWXTextTemplatesForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXTextTemplatesListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信文本模版编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
//删除微信文本模版
	function delWXTextTemplates(){
		var textTempId = $("#edit-textTempId").val();
		if(textTempId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/texttemplates/deleteWXTextTemplates";
			var params = $("#editWXTextTemplatesForm").serialize();
			//异步请求逻辑删除微信文本模版信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信文本模版删除成功!");
						//编辑微信文本模版信息清空
						$("#editWXTextTemplatesForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXTextTemplatesListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
//逻辑删除微信文本模版
	function logicDelWXTextTemplates(){
		var textTempId = $("#edit-textTempId").val();
		if(textTempId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/texttemplates/logicDeleteWXTextTemplates";
			var params = $("#editWXTextTemplatesForm").serialize();
			//异步请求逻辑删除微信文本模版信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信文本模版删除成功!");
						//编辑微信文本模版信息清空
						$("#editWXTextTemplatesForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXTextTemplatesListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
});


