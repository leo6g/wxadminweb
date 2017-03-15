$(document).ready(function(){
	
	//列表分页每页显示多少条记录
	var global_limit = 10 ;
	//初始化微网站文章信息管理列表信息
	initWWArticleListInfo();
	
	
	//新增微网站文章信息管理信息时，validate验证，验证通过后调用保存方法 
	$("#addWWArticleForm").validate({
        submitHandler:function(form){
        	addWWArticle();
        }    
    });
	
	//新增微网站文章信息管理信息
	$("#saveWWArticleBtn").click(function(){
		var form = $("#addWWArticleForm");
		form.submit();
	});
	
	//修改微网站文章信息管理信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWWArticleForm").validate({
        submitHandler:function(form){
        	editWWArticle();
        }    
    });
	
	//保存编辑的微网站文章信息管理信息
	$("#edit-saveWWArticleBtn").click(function(){
		var form = $("#editWWArticleForm");
		form.submit();
	});
	//编辑微网站文章信息管理信息
	$("#editWWArticleBtn").click(function(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		};
		C.load(contextPath+"/weiweb/article/edit?articleId="+articleId);
	});
	
	//查看微网站文章信息管理信息
	$("#viewWWArticleBtn").click(function(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		};
		C.load(contextPath+"/weiweb/article/view?articleId="+articleId);
	});
	
	//选中微网站文章信息管理信息
	$("#wWArticleListTable").delegate("tr","click",function(){
		var content = $(this).find("td:eq(2)").html();
		var title = $(this).find("td:eq(1)").html();
		var articleId = $(this).find("td:eq(3)").html();
		var createUser = $(this).find("td:eq(5)").html();
		var viewTimes = $(this).find("td:eq(4)").html();
		
		$("#edit-content").val(content);
		$("#edit-title").val(title);
		$("#edit-articleId").val(articleId);
		$("#edit-createUser").val(createUser);
		$("#edit-viewTimes").val(viewTimes);
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//删除微网站文章信息管理信息
	$("#delWWArticleBtn").click(function(){
		logicDelWWArticle();//逻辑删除
		//delWWArticle()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initWWArticleListInfo();
	});
	
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryTitle").val("");
		initWWArticleListInfo();
	});
	
	//局部绑定回车事件
	 $("#queryTitle").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	  }); 
	 
	//初始化微网站文章信息管理列表信息
	function initWWArticleListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/weiweb/article/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微网站文章信息管理列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#wWArticle-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#wWArticleListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initWWArticleListInfo);
			}
		});
	}
	
	
	//新增微网站文章信息管理信息
	function addWWArticle(){
		var url = contextPath + "/weiweb/article/insertWWArticle";
		var params = $("#addWWArticleForm").serialize();
		//异步请求新增微网站文章信息管理信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微网站文章信息管理新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initWWArticleListInfo();
					//清空新增微网站文章信息管理的弹窗信息
					$("#addWWArticleForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微网站文章信息管理编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	//删除微网站文章信息管理
	function delWWArticle(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/weiweb/article/deleteWWArticle";
			var params = $("#editWWArticleForm").serialize();
			//异步请求逻辑删除微网站文章信息管理信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站文章信息管理删除成功!");
						//编辑微网站文章信息管理信息清空
						$("#editWWArticleForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWWArticleListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除微网站文章信息管理
	function logicDelWWArticle(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/weiweb/article/logicDeleteWWArticle";
			var params = $("#editWWArticleForm").serialize();
			//异步请求逻辑删除微网站文章信息管理信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站文章信息管理删除成功!");
						//编辑微网站文章信息管理信息清空
						$("#editWWArticleForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initWWArticleListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});




