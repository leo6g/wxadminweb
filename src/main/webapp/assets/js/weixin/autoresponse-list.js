//列表分页每页显示多少条记录
$(document).ready(function(){
	var global_limit = 10 ;
	//初始化微信自动回复信息模版列表信息
	initWXAutoResponseListInfo();
	//新增微信自动回复信息模版信息时，validate验证，验证通过后调用保存方法 
	$("#addWXAutoResponseForm").validate({
        submitHandler:function(form){
        	addWXAutoResponse();
        }    
    });
	
	//新增微信自动回复信息模版信息
	$("#saveWXAutoResponseBtn").click(function(){
		var form = $("#addWXAutoResponseForm");
		form.submit();
	});
	
	//选中微信自动回复信息模版信息
	$("#wXAutoResponseListTable").delegate("tr","click",function(){
		var msgType = $(this).find("td:eq(1)").html();
		var keyWord = $(this).find("td:eq(2)").html();
		var responseId = $(this).find("td:eq(3)").html();
		var textTempId = $(this).find("td:eq(4)").html();
		if(msgType=="文本消息"){
			msgType = "text";
		}else if(msgType=="图文消息"){
			msgType = "news";
		}
		if(msgType=="text") getTextTemplates();
		if(msgType=="news") getNewsTemplates();
		$("#edit-msgType").val(msgType);
		$("#edit-keyWord").val(keyWord);
		$("#edit-responseId").val(responseId);
		$("#edit-textTempId").val(textTempId);
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改微信自动回复信息模版信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXAutoResponseForm").validate({
        submitHandler:function(form){
        	editWXAutoResponse();
        }    
    });
	
	//保存编辑的微信自动回复信息模版信息
	$("#edit-saveWXAutoResponseBtn").click(function(){
		var form = $("#editWXAutoResponseForm");
		form.submit();
	});
	//编辑微信自动回复信息模版信息
	$("#editWXAutoResponseBtn").click(function(){
		var responseId = $("#edit-responseId").val();
		if(responseId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信自动回复信息模版信息
	$("#delWXAutoResponseBtn").click(function(){
		//logicDelWXAutoResponse();//逻辑删除
		delWXAutoResponse()
	});
	
//初始化微信自动回复信息模版列表信息
	function initWXAutoResponseListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/autoresponse/getList";
		var params = $("#queryForm").serialize();
		 params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微信自动回复信息模版列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#wXAutoResponse-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#wXAutoResponseListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initWXAutoResponseListInfo);
				
				//列表点击预览图文内容
				$(".previewBtn").click(function(){
					$("#showViewBtn").click();
					var tempId = $(this).attr("data-id");
					//预览内容
					fnPreview(tempId);
				});
			}
		});
	}
	
	//查询
	$("#querySubmit").click(function(){
		initWXAutoResponseListInfo();
	});
	//局部绑定回车事件
	 $("#queryKeyWord").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 
	//点击重置按钮
		$("#resetBtn").click(function(){
			$("#queryForm select").val("");
			$("#queryKeyWord").val("");
			initWXAutoResponseListInfo();
		});
//新增微信自动回复信息模版信息
	function addWXAutoResponse(){
		var url = contextPath + "/wx/autoresponse/insertWXAutoResponse";
		var params = $("#addWXAutoResponseForm").serialize();
		//异步请求新增微信自动回复信息模版信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("微信自动回复信息模版新增成功!");
					//$("#alert-info").modal("show");
					alert("微信自动回复信息模版新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initWXAutoResponseListInfo();
					//清空新增微信自动回复信息模版的弹窗信息
					$("#addWXAutoResponseForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信自动回复信息模版编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
//修改微信自动回复信息模版信息
	function editWXAutoResponse(){
		var responseId = $("#edit-responseId").val();
		
		if(responseId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		
		var url = contextPath + "/wx/autoresponse/updateWXAutoResponse";
		var params = $("#editWXAutoResponseForm").serialize();
		//异步请求修改微信自动回复信息模版信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信自动回复信息模版信息修改成功!");
					//编辑微信自动回复信息模版信息清空
					$("#editWXAutoResponseForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXAutoResponseListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信自动回复信息模版编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
//删除微信自动回复信息模版
	function delWXAutoResponse(){
		var responseId = $("#edit-responseId").val();
		if(responseId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/autoresponse/deleteWXAutoResponse";
			var params = $("#editWXAutoResponseForm").serialize();
			//异步请求逻辑删除微信自动回复信息模版信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信自动回复信息模版删除成功!");
						//编辑微信自动回复信息模版信息清空
						$("#editWXAutoResponseForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXAutoResponseListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
//逻辑删除微信自动回复信息模版
	function logicDelWXAutoResponse(){
		var responseId = $("#edit-responseId").val();
		if(responseId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/autoresponse/logicDeleteWXAutoResponse";
			var params = $("#editWXAutoResponseForm").serialize();
			//异步请求逻辑删除微信自动回复信息模版信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微信自动回复信息模版删除成功!");
						//编辑微信自动回复信息模版信息清空
						$("#editWXAutoResponseForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXAutoResponseListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	$("#msgType").bind("change",function(){
		if(this.value=="text") getTextTemplates();
		if(this.value=="news") getNewsTemplates();
	});
	$("#edit-msgType").bind("change",function(){
		if(this.value=="text") getTextTemplates();
		if(this.value=="news") getNewsTemplates();
	});
//获取文本消息模版列表
	function getTextTemplates(){
		var url = contextPath + "/wx/texttemplates/getAll";
		//异步请求文本模版列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#text-image-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#textTempId").html(htmlStr);
				$("#edit-textTempId").html(htmlStr);
			}
		});
	}
//获取图文消息模版列表
	function getNewsTemplates(){
		var url = contextPath + "/wx/newstemplates/getAll";
		//异步请求文本模版列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#news-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#textTempId").html(htmlStr);
				$("#edit-textTempId").html(htmlStr);
			}
		});
	}

Handlebars.registerHelper("transformat",function(value){
         if(value=="text"){
        	  return "文本消息";
	     }else if(value=="news"){
	          return "图文消息";
         }
});

function fnPreview(tempId){
	$.ajax({
		type: "POST",
		url : contextPath+ '/wx/newsitems/getAll?newsTempId='+tempId,
		dataType: "json",
		async : false,
		success : function(json) {
			var html = "<div style='position: relative;width: 580px;height: 200px;border-bottom:solid 2px black' onclick=fnViewContent('"+json.beans[0].itemId+"')>" +
						"<img src='"+json.beans[0].imagePath+"' width='100%' height='100%'>"+
						"<div style='position: absolute;bottom: 0;height: 50px;line-height: 50px;background-color: black;opacity: 0.6;width: 100%;color: white'>"+json.beans[0].title+"</div>" +
						"</div>";
			for(var i=1;i<json.beans.length;i++){
				html+="<div style='width: 580px;height: 100px;position: relative;background-color: white;border-bottom:solid 1px gray' onclick=fnViewContent('"+json.beans[i].itemId+"')>"+
							"<p style='margin: 0;padding: 0;height: 100px;line-height: 100px'>"+
							json.beans[i].title +
							"</p><img src='"+json.beans[i].imagePath+"' style='position: absolute;right: 0;top: 0;' height='100%' width='178px'>"+
							"</div>";

			}
			$('#preViewDiv').html(html);
		}
	});
}

});

//预览指定内容
function fnViewContent(itemId){
	window.open(contextPath+'/wx/article/outputArticle?articleId='+itemId); 
}