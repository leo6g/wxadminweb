
//列表分页每页显示多少条记录
$(document).ready(function(){
	var global_limit = 10 ;
	
	//初始化微信关注语列表信息
	initWXSubcribeMsgListInfo();
	
	
	//新增微信关注语信息时，validate验证，验证通过后调用保存方法 
	$("#addWXSubcribeMsgForm").validate({
        submitHandler:function(form){
        	addWXSubcribeMsg();
        }    
    });
	
	//新增微信关注语信息
	$("#saveWXSubcribeMsgBtn").click(function(){
		var form = $("#addWXSubcribeMsgForm");
		form.submit();
	});
	
	//选中微信关注语信息
	$("#wXSubcribeMsgListTable").delegate("tr","click",function(){
		var msgType = $(this).find("td:eq(2)").html();
		var templateId = $(this).find("td:eq(7)").html();
		var subId = $(this).find("td:eq(3)").html();
		var createUser = $(this).find("td:eq(6)").html();
		
		if (msgType=="图文消息") {
			msgType="news";
		}else if(msgType=="文本消息"){
			msgType="text";
		}
		getSelect(msgType);
		
		$("#edit-msgType").val(msgType);
		$("#edit-templateId").val(templateId);
		$("#edit-subId").val(subId);
		$("#edit-createUser").val(createUser);
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");		
		return false;
	});
	
	
	//修改微信关注语信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXSubcribeMsgForm").validate({
        submitHandler:function(form){
        	editWXSubcribeMsg();
        }    
    });
	
	//保存编辑的微信关注语信息
	$("#edit-saveWXSubcribeMsgBtn").click(function(){
		var form = $("#editWXSubcribeMsgForm");
		form.submit();
	});
	//编辑微信关注语信息
	$("#editWXSubcribeMsgBtn").click(function(){
		var subId = $("#edit-subId").val();
		if(subId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除微信关注语信息
	$("#delWXSubcribeMsgBtn").click(function(){
		//logicDelWXSubcribeMsg();//逻辑删除
		delWXSubcribeMsg()
	});



//查询
$("#querySubmit").click(function(){
	initWXSubcribeMsgListInfo();
});
//局部绑定回车事件
$("#queryMsgType").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   }); 

//初始化微信关注语列表信息
function initWXSubcribeMsgListInfo(currentPage, limit){
	if(typeof currentPage == "undefined"){
		currentPage = 1;
	}
	if(typeof limit == "undefined"){
		limit = global_limit;
	}
	var url = contextPath + "/wx/subcribemsg/getList";
	var params = $("#queryForm").serialize();
	params = params + "&pageNumber="+currentPage+"&limit="+limit;
	//异步请求微信关注语列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		for (var i = 0; i < data.beans.length; i++) {
			if (data.beans[i].msgType=="text") {
				data.beans[i].msgType="文本消息";
			}else if (data.beans[i].msgType=="news") {
				data.beans[i].msgType="图文消息";
			}
		}
		var source = $("#wXSubcribeMsg-list-template").html();
		var templet = Handlebars.compile(source);
		if(flag && data.returnCode=="0"){
			//渲染列表数据
			var htmlStr = templet(data.beans);
			$("#wXSubcribeMsgListTable").html(htmlStr);
//			初始化分页数据(当前页码，总数，回调查询函数)
			initPaginator(currentPage, data.bean.count, initWXSubcribeMsgListInfo);
			
			//列表点击预览图文
			$(".reviewBtn").click(function(){
				var tempId = $(this).attr("data-id");
				$.ajax({
					type: "POST",
					url : contextPath+ '/wx/newsitems/getAll?newsTempId='+tempId,
					dataType: "json",
					async : false,
					success : function(json) {
						for(var i=0;i<json.beans.length;i++){
							window.open(contextPath+'/wx/article/outputArticle?articleId='+json.beans[i].itemId);
						}
					}
				});
			});
		}
	});
}

//新增微信关注语信息
function addWXSubcribeMsg(){
	var msgType = $("#msgType").val();
	if(msgType==""){
		alert("请选择消息类型!");
		return false;
	}
	var templateId = $("#templateId").val();
	if(templateId==""){
		alert("请选择模版!");
		return false;
	}
	alert(msgType);
	var url = contextPath + "/wx/subcribemsg/insertWXSubcribeMsg";
	var params = $("#addWXSubcribeMsgForm").serialize();
	//异步请求新增微信关注语信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				//$("#alert-info-content").html("微信关注语新增成功!");
				//$("#alert-info").modal("show");
				alert("微信关注语新增成功!");
				$("#myModal").modal('hide');
				//重新请求列表数据
				initWXSubcribeMsgListInfo();
				//清空新增微信关注语的弹窗信息
				$("#addWXSubcribeMsgForm input").val("");
				$("#descn").val("");
			}
		}else{
			if(data.returnCode=="-1"){
				alert("微信关注语编码已经存在，请修改!");
			}else{
				alert(data.returnMessage);
			}
		}
	});
}


//修改微信关注语信息
function editWXSubcribeMsg(){
	var subId = $("#edit-subId").val();
	
	if(subId==""){
		alert("请选择一条信息!");
		return false;
	}
	var msgType = $("#edit-msgType").val();
	if(msgType==""){
		alert("请选择消息类型!");
		return false;
	}
	var templateId = $("#edit-templateId").val();
	if(templateId==""){
		alert("请选择模版!");
		return false;
	}
	var url = contextPath + "/wx/subcribemsg/updateWXSubcribeMsg";
	var params = $("#editWXSubcribeMsgForm").serialize();
	//异步请求修改微信关注语信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				alert("微信关注语信息修改成功!");
				//编辑微信关注语信息清空
				$("#editWXSubcribeMsgForm input").val("");
				$("#edit-descn").val("");
				//重新请求列表数据
				initWXSubcribeMsgListInfo();
				$("#myModal1").modal('hide');
			}
		}else{
			if(data.returnCode=="-1"){
				alert("微信关注语编码已经存在，请修改!");
			}else{
				alert(data.returnMessage);
			}
		}
	});
}
//删除微信关注语
function delWXSubcribeMsg(){
	var subId = $("#edit-subId").val();
	if(subId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	confirm("确定删除吗？",function(){
		var url = contextPath + "/wx/subcribemsg/deleteWXSubcribeMsg";
		var params = $("#editWXSubcribeMsgForm").serialize();
		//异步请求逻辑删除微信关注语信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信关注语删除成功!");
					//编辑微信关注语信息清空
					$("#editWXSubcribeMsgForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXSubcribeMsgListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	});
}

//逻辑删除微信关注语
function logicDelWXSubcribeMsg(){
	var subId = $("#edit-subId").val();
	if(subId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	var flag = confirm("确定删除吗？")
	if(flag){
		var url = contextPath + "/wx/subcribemsg/logicDeleteWXSubcribeMsg";
		var params = $("#editWXSubcribeMsgForm").serialize();
		//异步请求逻辑删除微信关注语信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信关注语删除成功!");
					//编辑微信关注语信息清空
					$("#editWXSubcribeMsgForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXSubcribeMsgListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	}
}

/////////////////////////////////////
});

function getSelect(msgType){
	if(msgType=="news"){
		var url = contextPath + "/wx/newstemplates/getAll?type=subscribe";
		//异步请求文本模版列表数据1
		var params = {};
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#news-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#edit-templateId").html(htmlStr);
				$("#templateId").html(htmlStr);
			}
		});
	}else if(msgType=="text"){
		var url = contextPath + "/wx/texttemplates/getAll";
		//异步请求文本模版列表数据1
		var params = {};
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#text-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#edit-templateId").html(htmlStr);
				$("#templateId").html(htmlStr);
			}
		});
	}
}

$("#msgType").bind("change",function(){
	getSelect($(this).val());
});
$("#edit-msgType").bind("change",function(){
	getSelect($(this).val());
});
