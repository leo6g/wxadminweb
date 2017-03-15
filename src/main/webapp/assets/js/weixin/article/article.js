$(document).ready(function(){
	queryArticleList(1,9);
	function queryArticleList(currentPage,limit){
		var param = {
		    	"pageNumber": currentPage||1,	//页码(必需)
		    	"limit": limit||global_limit||10,		//每页显示数量(必需)
		    	"keyword":$("#keywords").val()		//标题|作者|摘要(需)
		   	};
			var tpl = $("#article-list-template").html();
			var handler = Handlebars.compile(tpl);
			$.post(contextPath+"/wx/artmtl/getList",param,function(data){
				if(data && data.returnCode === "0"){
					//velocity和handlebars有一定冲突（velocity中不能用@first），因此将第一条图文和后面的图文拿出来放到新的容器
					var articles = [];
					$(data.beans).each(function(i,e){
						var normalArticles = [];
						$(e.articles).each(function(index,ele){
							if(index === 0){
								//假设一个图文有四篇文章，这里是第一篇，也就是缩略图比较大的那个
								e.bigArticle = ele;
							}else{
								normalArticles.push(ele);
							}
						});
						e.normalArticles = normalArticles;
						data.beans[i] = e;
					});
					var html = handler(data);
					console.log(data);
					$(".jiangbangitlist").html(html);
					initPaginator(currentPage, data.bean.count, queryArticleList);
					
					//编辑事件
					$(".edit-btn").click(function(){
						var materialId = $(this).attr("data-materialId");
						var mediaId = $(this).attr("data-mediaId");
						editObj(materialId,mediaId);
					})
					//点击删除按钮事件
					$(".del-btn").click(function(){
						var materialId = $(this).attr("data-materialId");
						var mediaId = $(this).attr("data-mediaId");
						delObj(materialId, mediaId);
					});
					
				}
			},"json");
	}
	//搜索按钮绑定事件
	$("#search-bt").click(function(){
		queryArticleList(1,9);
	})
	//回车键
	$(window).on("keyup",function(e){
		if(e.keyCode === 13){
			$("#search-bt").click();
		}
	})
	//新建按钮
	$("#article-add-bt").click(function(){
		//因为之前在$中新建了$.editArticleMaterialId变量，如果有则会查询出对应信息显示，所以这里要先清除
		delete $.editArticleMaterialId;
		C.load(contextPath+"/wx/artmtl/add");
	})
	/**
	 * 编辑按钮
	 */
	function editObj(materialId,mediaId){
		//利用jquery全局变量来传递参数
		$.extend({editArticleMaterialId:materialId,editArticleRemoteMediaId:mediaId});
		C.load(contextPath+"/wx/artmtl/edit");
	}
	
	
	/**
	 * 点击删除按钮，删除文章
	 */
	function delObj(materialId, mediaId){
		confirm("确定删除吗？",function(){
			var url = contextPath + "/wx/artmtl/deleteWXMaterial";
			var params = {
				  "materialId": materialId,		//图文素材ID(必需)
				  "mediaId": mediaId			//微信媒体ID(必需)
			};
			//异步请求删除图文素材
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("图文删除成功!");
						//重新请求列表数据
						queryArticleList(1,9);
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	
	//同步微信端图文信息
	$("#syncNews").click(function(){
		$('body').showLoading();
		var url = contextPath + "/wx/artmtl/syncNews";
		$.ajax({
			url:url,
			data:'',
			timeout :600000,
			type :'post',
			dataType:'json',
			success:function(data,flag){
				if(flag){
					if(data.returnCode=="0"){
						$('body').hideLoading();
						alert(JSON.stringify("code="+data.returnCode+"***message="+data.returnMessage));
						//重新请求列表数据
						queryArticleList(1,9);
					}
				  }else {
					  $('body').hideLoading();
					  alert(data.returnMessage);
				  }
				}
		});
	});
	
});