$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化邮学堂文章表列表信息
	initStudyArticleListInfo();
	//获取所有的邮学堂板块
	queryAllStudyCategory();
	
	//点击添加按钮
	$("#addBtn").click(function(){
		C.load(contextPath + "/biz/studyArticle/add");
	});
	
	
	
	//新增邮学堂文章表信息时，validate验证，验证通过后调用保存方法 
	$("#addStudyArticleForm").validate({
        submitHandler:function(form){
        	addStudyArticle();
        }    
    });
	
	//新增邮学堂文章表信息
	$("#saveStudyArticleBtn").click(function(){
		var form = $("#addStudyArticleForm");
		form.submit();
	});
	
	//选中邮学堂文章表信息
	$("#studyArticleListTable").delegate("tr","click",function(){
		var categoryId = $(this).find("td:eq(1)").html();
		var id = $(this).find("td:eq(2)").html();
		var articleId = $(this).find("td:eq(3)").html();
		var createUser = $(this).find("td:eq(5)").html();
		var content = $(this).find("td:eq(6)").html();
		var title = $(this).find("td:eq(7)").html();
		
		$("#edit-categoryId").val(categoryId);
		$("#edit-id").val(id);
		$("#edit-articleId").val(articleId);
		$("#edit-createUser").val(createUser);
		$("#edit-content").val(content);
		$("#edit-title").val(title);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改邮学堂文章表信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editStudyArticleForm").validate({
        submitHandler:function(form){
        	editStudyArticle();
        }    
    });
	
	//保存编辑的邮学堂文章表信息
	$("#edit-saveStudyArticleBtn").click(function(){
		var form = $("#editStudyArticleForm");
		form.submit();
	});
	//编辑邮学堂文章表信息
	$("#editStudyArticleBtn").click(function(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/biz/studyArticle/edit?id="+articleId);
	});
	
	
	//查看邮学堂文章表信息
	$("#viewBtn").click(function(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/biz/studyArticle/outputStudyArticleByArticleId?articleId="+articleId);
	});
	
	
	
	//删除邮学堂文章表信息
	$("#delStudyArticleBtn").click(function(){
		//logicDelStudyArticle();//逻辑删除
		delStudyArticle()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initStudyArticleListInfo();
	});
	
	//初始化邮学堂文章表列表信息
	function initStudyArticleListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/studyArticle/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求邮学堂文章表列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#studyArticle-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#studyArticleListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initStudyArticleListInfo);
			}
		});
	}
	
	
	//新增邮学堂文章表信息
	function addStudyArticle(){
		var url = contextPath + "/insertStudyArticle";
		var params = $("#addStudyArticleForm").serialize();
		//异步请求新增邮学堂文章表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("邮学堂文章表新增成功!");
					//$("#alert-info").modal("show");
					alert("邮学堂文章表新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initStudyArticleListInfo();
					//清空新增邮学堂文章表的弹窗信息
					$("#addStudyArticleForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("邮学堂文章表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改邮学堂文章表信息
	function editStudyArticle(){
		var articleId = $("#edit-articleId").val();
	
		if(articleId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/updateStudyArticle";
		var params = $("#editStudyArticleForm").serialize();
		//异步请求修改邮学堂文章表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("邮学堂文章表信息修改成功!");
					//编辑邮学堂文章表信息清空
					$("#editStudyArticleForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initStudyArticleListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("邮学堂文章表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除邮学堂文章表
	function delStudyArticle(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/studyArticle/deleteStudyArticle";
			$("#edit-articleId").val(articleId);
			var params = $("#editStudyArticleForm").serialize();
			//异步请求逻辑删除邮学堂文章表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("邮学堂文章表删除成功!");
						//编辑邮学堂文章表信息清空
						$("#editStudyArticleForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initStudyArticleListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除邮学堂文章表
	function logicDelStudyArticle(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/logicDeleteStudyArticle";
			var params = $("#editStudyArticleForm").serialize();
			//异步请求逻辑删除邮学堂文章表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("邮学堂文章表删除成功!");
						//编辑邮学堂文章表信息清空
						$("#editStudyArticleForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initStudyArticleListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

	//获取邮学堂文章类型
	function queryAllStudyCategory(){
		var url = contextPath + "/biz/studyArticle/getAllStudyCategory";
		var params = "";
		//异步请求所有职位信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#position-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#categoryId").html(htmlStr);
			}
		});
	}
	
});



