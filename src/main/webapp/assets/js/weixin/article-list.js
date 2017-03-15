$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微信文章内容列表信息
	initWXArticleListInfo();
	
	
	//新增微信文章内容信息时，validate验证，验证通过后调用保存方法 
	$("#addWXArticleForm").validate({
        submitHandler:function(form){
        	addWXArticle();
        }    
    });
	
	//新增微信文章内容信息
	$("#saveWXArticleBtn").click(function(){
		var form = $("#addWXArticleForm");
		$("#authState").val("1-WAITING");
		form.submit();
	});
	//新增微信文章内容信息
	$("#saveDraftWXArticleBtn").click(function(){
		var form = $("#addWXArticleForm");
		$("#authState").val("DRAFT");
		form.submit();
	});
	
	//修改微信文章内容信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXArticleForm").validate({
        submitHandler:function(form){
        	editWXArticle();
        }    
    });
	
	//保存编辑的微信文章内容信息
	$("#edit-saveWXArticleBtn").click(function(){
		var form = $("#editWXArticleForm");
		form.submit();
	});
	//编辑微信文章内容信息
	$("#editWXArticleBtn").click(function(){
		var newsTempId = $("#edit-newsTempId").val();
		if(newsTempId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var authState = $("#view-authState").val();
		if(authState != 'DRAFT' /*&& authState != '1-REJECTED' && authState != '2-REJECTED' 
			&& authState != '3-REJECTED'*/){
			alert("您无权编辑!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initWXArticleListInfo();
	});
	
	//页面发送按钮
	$("#sendWXArticleBtn").click(function(){
		
		var newsTempId = $("#edit-newsTempId").val();
		var sendState = $("#edit-sendState").val();
		var authState = $("#view-authState").val();
		if(newsTempId==""){
			alert("请选择待发送信息!",$.scojs_message.TYPE_ERROR);
			return false;
		};
		if(sendState=="T"){
			alert("该文章已发送，请重新选择!",$.scojs_message.TYPE_ERROR);
			return false;
		};
		if(authState!="COMPLETED"){
			alert("只能发送审核通过文章，请重新选择!",$.scojs_message.TYPE_ERROR);
			return false;
		};
	});
	//发送微信文章内容信息
	$("#sendBtn").click(function(){
		var newsTempId = $("#edit-newsTempId").val();
		var sendType = $("#sendType").val();
		if(sendType==""){
			alert("请选择待发送类型!",$.scojs_message.TYPE_ERROR);
			return false;
		};
		var groupId = $("#sendGroup").val();
		//执行发送文章函数
		sendArticle(newsTempId,sendType,groupId);
	});
	//
	$("#sendType").bind("change",function(){
		$("#sendGroup").empty();
		var sendType = $("#sendType").val();
		if(sendType == "2"){
			getGroup(); 
		}
	});
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryForm select").val("");
		$("#queryName").val("");
		$("#beginTime").val("");
		$("#endTime").val("");
		initWXArticleListInfo();
	});
	//详情
	$("#viewWXArticleBtn").click(function(){
		var newsTempId = $("#edit-newsTempId").val();
		if(newsTempId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var authState = $("#view-authState").val();
		var role = $("#role").val();
		C.load(contextPath+'/wx/article/artcView?newsTempId='+newsTempId+'&role='+role+'&authState='+authState);
	});
	//动态加载微信关注用户分组
	function getGroup(){
		var url = contextPath + "/wx/usergroup/getAll";
		//异步请求关注用户分组列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#group-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#sendGroup").html(htmlStr);
			}
		});
	}
	//新增微信文章内容信息
	function addWXArticle(){
		var name = $("#name").val();
		var type = $("#type").val();
		if(name==""){
			alert("请输入文章名称!");
			return false;
		}
		if(type==""){
			alert("请输入文章类型!");
			return false;
		}
		
		var url = contextPath + "/wx/article/insertWXArticle";
		var params = $("#addWXArticleForm").serialize();
		//异步请求新增微信文章内容信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					if($("#authState").val() == '1-WAITING'){
						var newsTempId = data.bean.newsTempId;
						saveBIZAuthInfo(newsTempId);
					}else{
						alert("微信文章草稿新增成功!");
						$("#myModal").modal('hide');
						//重新请求列表数据
						initWXArticleListInfo();
						//清空新增微信文章内容的弹窗信息
						$("#addWXArticleForm input").val("");
						$("#descn").val("");
					}
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信文章内容编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改微信文章内容信息
	function editWXArticle(){
		var newsTempId = $("#edit-newsTempId").val();
		var name = $("#edit-name").val();
		var type = $("#edit-type").val();
	
		if(newsTempId==""){
			alert("请选择一条信息!");
			return false;
		}
		if(name==""){
			alert("请输入文章名称!");
			return false;
		}
		if(type==""){
			alert("请输入文章类型!");
			return false;
		}
		var url = contextPath + "/wx/article/updateWXArticle";
		var params = $("#editWXArticleForm").serialize();
		//异步请求修改微信文章内容信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					if($("#edit-authState").val() == '1-WAITING'){
						saveBIZAuthInfo(newsTempId);
					}else{
						alert("微信文章内容信息修改成功!");
						//编辑微信文章内容信息清空
						$("#editWXArticleForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXArticleListInfo();
						$("#myModal1").modal('hide');
					}
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信文章内容编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	

	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 
	 $("#queryType").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 
	 //类型名称转化
	 Handlebars.registerHelper("transformat",function(value){
        if(value=="menu"){
       	  return "菜单";
	     }else if(value=="news"){
	          return "新闻";
        }else if(value=="push"){
	          return "推送";
        }else if(value=="subscribe"){
        	return "关注语";
        }else{
       	 return "";
        }
	 });
	//自动转换审批状态
	 Handlebars.registerHelper("transformatState",function(value){
		 var role = $("#role").val();
		 if (value=="DRAFT") {
			 	return "草稿";
			}else if (value=="1-WAITING") {
				return "待信息办审批";
			}else if (value=="1-REJECTED") {
				return "信息办否决";
			}else if (value=="2-WAITING") {
				return "待法规办审批";
			}else if (value=="2-REJECTED") {
				return "法规办否决";
			}else if (value=="3-WAITING") {
				return "待电子银行部审批";
			}else if (value=="3-REJECTED") {
				return "电子银行部否决";
			}else if (value=="COMPLETED") {
				return "通过";
			}
	 });
	
	 
	//发送微信文章内容信息
		function sendArticle(newsTempId,sendType,groupId){
			var url = "";
			if(sendType == "1"){
				url= contextPath + "/wx/sendmsg/sendMsgToAllUser?newsTempId="+newsTempId;
			}else if (sendType == "2"){
				url= contextPath + "/wx/sendmsg/sendMsgByGroup?newsTempId="+newsTempId+"&groupId="+groupId;
			}
			var params = "";
			//异步请求新增微信文章内容信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						editSendState(newsTempId);
						$("#sendType").val("");
						$("#sendGroup").val("");
					}
				}else{
					if(data.returnCode=="-1"){
						alert("微信文章内容编码已经存在，请修改!");
					}else{
						alert(data.returnMessage);
					}
				}
			});
		};
		
		//修改微信文章内容信息
		function editSendState(newsTempId){
			var oDate = new Date(); //实例一个时间对象
			var myDate = oDate.getFullYear() +"-"+(oDate.getMonth()+1)+"-"+oDate.getDate()+" "
			+oDate.getHours()+":"+oDate.getMinutes()+":"+oDate.getSeconds();
			var url = contextPath + "/wx/article/updateWXArticle?newsTempId="+newsTempId+
			"&sendState=T"+"&publishDate="+myDate;
			var params = "";
			//异步请求修改微信文章内容信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("发送成功!");
						//编辑微信文章内容信息清空
						$("#editWXArticleForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXArticleListInfo();
						$("#myModal3").modal('hide');
					}
				}else{
					if(data.returnCode=="-1"){
						alert("微信文章内容编码已经存在，请修改!");
					}else{
						alert(data.returnMessage);
					}
				}
			});
		};
		Handlebars.registerHelper('equalsout', function(v1, options) {
			var role = $("#role").val();
			console.info(v1+"----"+role);
			if(role == v1) {
				return options.fn(this);
			}
			return options.inverse(this);
			console.info(v1+"----"+role);
		});
		
		Handlebars.registerHelper('equalsor', function(v1, v2, v3, v4, v5,options) {
			if(v1 == v2 || v1 == v3 || v1 == v4 || v1 == v5) {
				return options.fn(this);
			}
			return options.inverse(this);
		});
		
////////////////////////////////////
});

//选中微信文章模板信息
function seltable(){
	var index=$("#wXArticleListTable tr").find("input:checked").parents("tr").index()+1;
	var name = $("#wXArticleListTable tr:eq(" +index+") td:eq(2)").text();
	var type = $("#wXArticleListTable tr:eq(" +index+") td:eq(3)").text();
	var weeklyNumber = $("#wXArticleListTable tr:eq(" +index+") td:eq(4)").text();
	var sendState = $("#wXArticleListTable tr:eq(" +index+") td:eq(5)").text();
	var publishDate = $("#wXArticleListTable tr:eq(" +index+") td:eq(7)").text();
	var newsTempId = $("#wXArticleListTable tr:eq(" +index+") td:eq(8)").text();
	var authState = $("#wXArticleListTable tr:eq(" +index+") td:eq(6)").text();
	var viewState = $("#wXArticleListTable tr:eq(" +index+") td:eq(12)").text();
	 if(type=="菜单"){
		 type= "menu";
	     }else if(type=="新闻"){
	    	 type= "news";
       }else if(type=="推送"){
    	   type= "push";
       }else if(type=="关注"){
    	   type= "subscribe";
       }else{
    	   type= "";
       }
	 if(sendState=="已发送"){
		 sendState= "T";
	     }else if(sendState=="未发送"){
	    	 sendState= "F";
       }
	 if(authState=="草稿"){
			authState = "DRAFT";
	   }
	$("#edit-name").val(name);
	$("#edit-type").val(type);
	$("#edit-weeklyNumber").val(weeklyNumber);
	$("#edit-publishDate").val(publishDate);
	$("#edit-newsTempId").val(newsTempId);
	$("#edit-sendState").val(sendState);
	$("#edit-authState").val(authState);
	$("#view-authState").val(viewState);
	return false;
};
//逻辑删除微信文章内容
function logicDelWXArticle(newsTempId){
	if(newsTempId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	confirm("确定删除吗？",function(){
		var url = contextPath + "/wx/article/deleteWXArticle";
		$("#edit-newsTempId").val(newsTempId);
		var params = $("#editWXArticleForm").serialize();
		//异步请求逻辑删除微信文章内容信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信文章内容删除成功!");
					//编辑微信文章内容信息清空
					$("#editWXArticleForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXArticleListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	});
}

//初始化微信文章内容列表信息
function initWXArticleListInfo(currentPage, limit){
	if(typeof currentPage == "undefined"){
		currentPage = 1;
	}
	if(typeof limit == "undefined"){
		limit = global_limit;
	}
	var role = $("#role").val();
	var url = contextPath + "/wx/article/getList";
	var params = $("#queryForm").serialize();
	params = params + "&pageNumber="+currentPage+"&limit="+limit+"&role="+role;
	//异步请求微信文章内容列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		for (var i = 0; i < data.beans.length; i++) {
			if (data.beans[i].sendState=="T") {
				data.beans[i].sendState="已发送";
			}else if (data.beans[i].sendState=="F") {
				data.beans[i].sendState="未发送";
			}
		}
		var source = $("#wXArticle-list-template").html();
		var templet = Handlebars.compile(source);
		if(flag && data.returnCode=="0"){
			//渲染列表数据
			var htmlStr = templet(data.beans);
			$("#wXArticleListTable").html(htmlStr);
			//初始化分页数据(当前页码，总数，回调查询函数)
			initPaginator(currentPage, data.bean.count, initWXArticleListInfo);
		}
	});
}


function fnPreview(tempId){
	$.ajax({
		type: "POST",
		url : contextPath+ '/wx/newsitems/getAll?newsTempId='+tempId,
		dataType: "json",
		async : false,
		success : function(json) {
			var html = "<div style='position: relative;width: 580px;height: 200px;border-bottom:solid 2px black' onclick=fnViewArticle('"+json.beans[0].itemId+"')>" +
						"<img src='"+json.beans[0].imagePath+"' width='100%' height='100%'>"+
						"<div style='position: absolute;bottom: 0;height: 50px;line-height: 50px;background-color: black;opacity: 0.6;width: 100%;color: white'>"+json.beans[0].title+"</div>" +
						"</div>";
			for(var i=1;i<json.beans.length;i++){
				html+="<div style='width: 580px;height: 100px;position: relative;background-color: white;border-bottom:solid 1px gray' onclick=fnViewArticle('"+json.beans[i].itemId+"')>"+
							"<p style='margin: 0;padding: 0;height: 100px;line-height: 100px'>"+
							json.beans[i].title +
							"</p><img src='"+json.beans[i].imagePath+"' style='position: absolute;right: 0;top: 0;' height='100%' width='178px'>"+
							"</div>";

			}
			$('#preViewDiv').html(html);
		}
	});
}


function fnViewArticle(itemId){
	window.open(contextPath+'/wx/article/outputArticle?articleId='+itemId); 
}
//信息保存至审核表
function saveBIZAuthInfo(newsTempId){
	var url=contextPath+"/biz/authinfo/insertBIZAuthInfo";
	var formdata = "type=TEMP&prodId="+newsTempId+"&operFlag=WAITING";
	  Util.ajax.postJson(url, formdata, function(data, flag){
		if(flag && data.returnCode=="0"){
			alert("微信文章更新成功!",$.scojs_message.TYPE_OK);
			$("#myModal").modal('hide');
			$("#myModal1").modal('hide');
			//重新请求列表数据
			initWXArticleListInfo();
			//清空新增微信文章内容的弹窗信息
			$("#addWXArticleForm input").val("");
			$("#descn").val("");
			//编辑微信文章内容清空
			$("#editWXArticleForm input").val("");
			$("#edit-descn").val("");
		}
	});
};
//按钮隐藏与显示
$(function(){
	var role = $("#role").val();
	 if(role == 'arti_ed'){
		 $("#addWXArticleBtn").css('display','');
		 $("#editWXArticleBtn").css('display','');
		 $("#viewWXArticleBtn").css('display','');
	 }else if(role == 'infor' || role == 'cardcheck' || role == 'law_spec'){
		 $("#viewWXArticleBtn").css('display','');
	 }
	 
})
/*//动态改变下拉框显示颜色
changeColor(){
	 alert("zzzzzzzzzzzzzzzzzzzzzz");
	var param = $("#queryType").val();
	alert(param);
	if("请选择" == param.trim()){
		this.style.color="#999";
	}else{
		this.style.color="black";
	}
 }*/