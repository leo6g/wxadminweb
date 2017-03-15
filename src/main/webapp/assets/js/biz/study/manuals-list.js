$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化操作手册文章表列表信息
	initStudyArticleListInfo();
	//获取类型
	queryAllStudyCategory();
	
	//点击添加按钮
	$("#addBtn").click(function(){
		C.load(contextPath + "/biz/studyArticle/manualsAdd");
	});
	
	//编辑操作手册文章表信息
	$("#editStudyArticleBtn").click(function(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/biz/studyArticle/manualsEdit?id="+articleId);
	});
	
	
	//查看操作手册文章表信息
	$("#viewBtn").click(function(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/biz/studyArticle/outputStudyArticleByArticleId?articleId="+articleId);
	});
	
	//选中操作手册文章表信息
	$("#studyArticleListTable").delegate("tr","click",function(){
		var articleId = $(this).find("td:eq(3)").html();
		
		$("#edit-articleId").val(articleId);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	//删除操作手册文章表信息
	$("#delStudyArticleBtn").click(function(){
		//logicDelStudyArticle();//逻辑删除
		delStudyArticle()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initStudyArticleListInfo();
	});
	
	//动态类型
	Handlebars.registerHelper("transformat",function(value){
        if(value=="user"){
       	  return "用户";
	     }else if(value=="staff"){
	          return "员工";
        }
	});
	
	//初始化操作手册文章表列表信息
	function initStudyArticleListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/studyArticle/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&categoryId=e03ce5164f0c40e784baa87fad0756ad&limit="+limit;
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
	
	//删除操作手册文章表
	function delStudyArticle(){
		var articleId = $("#edit-articleId").val();
		if(articleId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/studyArticle/deleteStudyArticle";
			//$("#edit-articleId").val(articleId);
			//var params = $("#editStudyArticleForm").serialize();
			var params = 'articleId='+articleId;
			//异步请求逻辑删除邮学堂文章表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("邮学堂文章表删除成功!");
						//编辑邮学堂文章表信息清空
						$("#subCategory").empty(); 
						//重新请求列表数据
						initStudyArticleListInfo();
						queryAllStudyCategory();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//获取操作手册文章类型
	function queryAllStudyCategory(){
		/*var url = contextPath + "/biz/studyArticle/getAllStudyCategory?categoryId=e03ce5164f0c40e784baa87fad0756ad";
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
		});*/
		var htmlStr = ' <option value="" >请选择</option><option value="user">用户</option><option value="staff">员工</option>';
		$("#subCategory").html(htmlStr);
	}
	
});



