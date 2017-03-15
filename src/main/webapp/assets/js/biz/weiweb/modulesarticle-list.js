$(document).ready(function(){
	//初始化图片编辑上传按钮
	advGetUpload("edit-uploadIcon","edit-imgPath","edit-picIcon","/biz/banner/doUpload");
	//初始化图片新增上传按钮
	advGetUpload("add-uploadIcon","add-imgPath","add-picIcon","/biz/banner/doUpload");
	//初始化标题尾部图片编辑上传按钮
	advGetUpload("edit-taileUploadIcon","edit-taileImgPath","edit-tailePicIcon","/biz/banner/doUpload");
	//初始化标题尾部图片新增上传按钮
	advGetUpload("add-taileUploadIcon","add-taileImgPath","add-tailePicIcon","/biz/banner/doUpload");
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微网站文章信息列表信息
	initBizModulesArticleListInfo();
	getModulesList("query");
	
	
	//新增微网站文章信息信息时，validate验证，验证通过后调用保存方法 
	$("#addBizModulesArticleForm").validate({
        submitHandler:function(form){
        	addBizModulesArticle();
        }    
    });
	
	//新增微网站文章信息信息
	$("#saveBizModulesArticleBtn").click(function(){
		var form = $("#addBizModulesArticleForm");
		form.submit();
	});
	
	//选中微网站文章信息信息
	$("#bizModulesArticleListTable").delegate("tr","click",function(){
		var title = $(this).find("td:eq(1)").html();
		var moduleId = $(this).find("td:eq(2)").html();
		var sortNum = $(this).find("td:eq(4)").html();
		var id = $(this).find("td:eq(5)").html();
		var createUser = $(this).find("td:eq(6)").html();
		var taileImgPath = $(this).find("td:eq(11)").html();
		var url = $(this).find("td:eq(13)").html();
		var imgPath = $(this).find("td:eq(12)").html();
		
		$("#edit-title").val(title);
		getModulesList("edit");
		$("#edit-moduleId").val(moduleId);
		$("#edit-id").val(id);
		$("#edit-createUser").val(createUser);
		$("#edit-taileImgPath").val(taileImgPath);
		$("#edit-url").val(url);
		$("#edit-sortNum").val(sortNum);
		$("#edit-imgPath").val(imgPath);
		$("#edit-picIcon").attr("src",imgPath);
		$("#edit-imgPath").val(imgPath.substring(imgPath.lastIndexOf("/") + 1));
		$("#edit-tailePicIcon").attr("src",taileImgPath);
		$("#edit-taileImgPath").val(taileImgPath.substring(taileImgPath.lastIndexOf("/") + 1));
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改微网站文章信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editBizModulesArticleForm").validate({
        submitHandler:function(form){
        	editBizModulesArticle();
        }    
    });
	
	//保存编辑的微网站文章信息信息
	$("#edit-saveBizModulesArticleBtn").click(function(){
		var form = $("#editBizModulesArticleForm");
		form.submit();
	});
	//编辑微网站文章信息信息
	$("#editBizModulesArticleBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	$("#addBizModulesArticleBtn").click(function(){
		getModulesList("add");
	});
	
	//删除微网站文章信息信息
	$("#delBizModulesArticleBtn").click(function(){
		logicDelBizModulesArticle();//逻辑删除
		//delBizModulesArticle()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initBizModulesArticleListInfo();
	});
	
	//初始化微网站文章信息列表信息
	function initBizModulesArticleListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/modulearticle/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微网站文章信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#bizModulesArticle-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#bizModulesArticleListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initBizModulesArticleListInfo);
			}
		});
	}
	
	
	//新增微网站文章信息信息
	function addBizModulesArticle(){
		var url = contextPath + "/biz/modulearticle/insertBizModulesArticle";
		var params = $("#addBizModulesArticleForm").serialize();
		//异步请求新增微网站文章信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("微网站文章信息新增成功!");
					//$("#alert-info").modal("show");
					alert("微网站文章信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initBizModulesArticleListInfo();
					//清空新增微网站文章信息的弹窗信息
					$("#addBizModulesArticleForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微网站文章信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改微网站文章信息信息
	function editBizModulesArticle(){
		var id = $("#edit-id").val();
	
		if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/modulearticle/updateBizModulesArticle";
		var params = $("#editBizModulesArticleForm").serialize();
		//异步请求修改微网站文章信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微网站文章信息信息修改成功!");
					//编辑微网站文章信息信息清空
					$("#editBizModulesArticleForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initBizModulesArticleListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微网站文章信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除微网站文章信息
	function delBizModulesArticle(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/modulearticle/deleteBizModulesArticle";
			var params = $("#editBizModulesArticleForm").serialize();
			//异步请求逻辑删除微网站文章信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站文章信息删除成功!");
						//编辑微网站文章信息信息清空
						$("#editBizModulesArticleForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBizModulesArticleListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除微网站文章信息
	function logicDelBizModulesArticle(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/modulearticle/logicDeleteBizModulesArticle";
			var params = $("#editBizModulesArticleForm").serialize();
			//异步请求逻辑删除微网站文章信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站文章信息删除成功!");
						//编辑微网站文章信息信息清空
						$("#editBizModulesArticleForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBizModulesArticleListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

	//动态加载模板下拉列表
	function getModulesList(obj){
		var url = contextPath + "/biz/modules/getAll";
		//异步请求文本模版列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#modules-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				if(obj == 'query') {
					$("#queryModule").empty();
					$("#queryModule").html(htmlStr);
				}
				if(obj == 'edit') {
					$("#edit-moduleId").empty();
					$("#edit-moduleId").html(htmlStr);
				}
				if(obj == 'add') {
					$("#moduleId").empty();
					$("#moduleId").html(htmlStr);
				}
			}
		});
	}
	
});

function fnPreview(url){
	window.open(url); 
}

