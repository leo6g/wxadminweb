$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化文章评论信息列表信息
	initWXArticleRemarkListInfo();
	
	
	//文章评论信息审核通过
	$("#saveOkBtn").click(function(){
		var authFlag = 'T';
		editWXArticleRemark(authFlag);
	});

	//文章评论信息审核不通过
	$("#saveNoBtn").click(function(){
		var authFlag = 'F';
		editWXArticleRemark(authFlag);
	});
	//选中文章评论信息信息
	$("#wXArticleRemarkListTable").delegate("tr","click",function(){
		var openId = $(this).find("td:eq(8)").html();
		var remarkId = $(this).find("td:eq(7)").html();
		var authFlag = $(this).find("td:eq(3)").html();
		
		$("#edit-openId").val(openId);
		$("#edit-remarkId").val(remarkId);
		$("#edit-authFlag").val(authFlag);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//编辑文章评论信息信息
	$("#checkWXArticleRemarkBtn").click(function(){
		var remarkId = $("#edit-remarkId").val();
		if(remarkId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var authFlag = $("#edit-authFlag").val();
		if(authFlag != "待审核"){
			alert("该条评论已审核!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除文章评论信息信息
	$("#delWXArticleRemarkBtn").click(function(){
		//logicDelWXArticleRemark();//逻辑删除
		delWXArticleRemark()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initWXArticleRemarkListInfo();
	});
	//局部绑定回车事件
	 $("#queryNickName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	 });
	
	//初始化文章评论信息列表信息
	function initWXArticleRemarkListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/articleremark/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求文章评论信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#wXArticleRemark-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#wXArticleRemarkListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initWXArticleRemarkListInfo);
			}
		});
	}
	
	//修改文章评论信息信息
	function editWXArticleRemark(authFlag){
		var remarkId = $("#edit-remarkId").val();
		var openId = $("#edit-openId").val();
		
		var url = contextPath + "/wx/articleremark/updateWXArticleRemark?remarkId="+remarkId+"&openId="+openId
		+"&authFlag="+authFlag;
		var params = "";
		//异步请求修改文章评论信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("文章评论信息审核成功!");
					//编辑文章评论信息信息清空
					$("#edit-remarkId").val("");
					$("#edit-openId").val("");
					//重新请求列表数据
					initWXArticleRemarkListInfo();
					$("#myModal").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("文章评论信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除文章评论信息
	function delWXArticleRemark(){
		var remarkId = $("#edit-remarkId").val();
		if(remarkId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/articleremark/deleteWXArticleRemark";
			var params = $("#editWXArticleRemarkForm").serialize();
			//异步请求逻辑删除文章评论信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("文章评论信息删除成功!");
						//编辑文章评论信息信息清空
						$("#editWXArticleRemarkForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXArticleRemarkListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除文章评论信息
	function logicDelWXArticleRemark(){
		var remarkId = $("#edit-remarkId").val();
		if(remarkId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/articleremark/logicDeleteWXArticleRemark";
			var params = $("#editWXArticleRemarkForm").serialize();
			//异步请求逻辑删除文章评论信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("文章评论信息删除成功!");
						//编辑文章评论信息信息清空
						$("#editWXArticleRemarkForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWXArticleRemarkListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//动态改变审核状态
	Handlebars.registerHelper("transformat",function(value){
        if(value=="P"){
       	  return "待审核";
	     }else if(value=="F"){
	          return "未通过";
        }else if(value=="T"){
	          return "通过";
        }
	});
/////////////////////////////////////////////////////
});
//按钮隐藏与显示
$(function(){
	var role = $("#role").val();
	 if(role == 'remarkCheck'){
		 $("#checkWXArticleRemarkBtn").css('display','');
	 }else{
		 $("#checkWXArticleRemarkBtn").css('display','none');
	 }
	 
})



