
	$(document).ready(function(){
		//查询菜单详细信息
		queryArticleView();
		//修改微网站文章信息管理信息时，jqvalidate验证，验证通过后调用保存方法 
		$("#formdata").validate({
	        submitHandler:function(form){
	        	editWWArticle();
	        }    
	    });
		
		//保存编辑的微网站文章信息管理信息
		$("#edit-saveWWArticleBtn").click(function(){
			var form = $("#formdata");
			form.submit();
		});
		
		//修改微网站文章信息管理信息
		function editWWArticle(){
			var articleId = $("#edit-articleId").val();
		
			var url = contextPath + "/weiweb/article/updateWWArticle";
			var params = $("#formdata").serialize();
			//异步请求修改微网站文章信息管理信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站文章信息管理信息修改成功!");
						//编辑微网站文章信息管理信息清空
						$("#formdata input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						C.load(contextPath+"/weiweb/article/list");
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
	});
	//初始化菜单信息
	function queryArticleView(){
		var articleId = $("#articleId").val();
		var url = contextPath + "/weiweb/article/getById?articleId="+articleId;
		var params = "";
		//异步请求职位列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = eval(data.object);
				$("#title").val(viewinfo.title);
				UE.getEditor('edit-content').execCommand('insertHtml', viewinfo.content);
			}
		});
	};
	