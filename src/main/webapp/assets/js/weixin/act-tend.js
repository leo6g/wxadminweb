
	$(document).ready(function(){
		//查询菜单详细信息
		queryNewsItemsById();
		
		//更新活动内容
		$('#submitData').click(function(){
		  var formdata=$("#formdata").serialize();
		  var url=contextPath+"/wx/newsitems/updateWXNewsItems";
		  Util.ajax.postJson(url, formdata, function(data, flag){
				if(flag && data.returnCode=="0"){
					alert("专属活动文章编辑成功!",$.scojs_message.TYPE_OK);
				}
			});
		});
		
		//初始化菜单信息
		function queryNewsItemsById(){
			var itemId = $("#itemId").val();
			var url = contextPath + "/wx/newsitems/getById?itemId="+itemId;
			var params = "";
			//异步请求职位列表数据
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					//基本信息
					var viewinfo = eval(data.object);
					$("#title").val(viewinfo.title);
					UE.getEditor('editorContent').execCommand('insertHtml', viewinfo.content);
					$("#itemId").val(itemId);
				}
			});
		};
		
	});
	
	//预览文章
	function viewBtn(){
		var itemId = $("#itemId").val();
		window.open(contextPath+'/wx/article/outputArticle?articleId='+itemId);
	}
