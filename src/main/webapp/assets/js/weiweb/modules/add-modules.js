
	$(document).ready(function(){
		var moduleId = $("#moduleId").val();
		//初始化父级菜单信息
		queryMenuView();
		//初始化上传按钮
		getUpload("uploadIcon","iconPath","picIcon");	
		//初始化菜单信息
		function queryMenuView(){
			var url = contextPath + "/weiweb/modules/getById?moduleId="+moduleId;
			var params = "";
			//异步请求数据
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					//基本信息
					var viewinfo = data.object;
					if(viewinfo !=null)
					{
						$("#parentName").text("父级菜单："+viewinfo.name);
					}

				}
			});
		}

		//提交按钮
		$('#submitData').click(function(){
			var name=$("#name").val();
			var levels=$("#levels").val();
			var iconPath=$("#iconPath").val();
			if(name==null){
				alert("名称不能为空!",$.scojs_message.TYPE_ERROR);
				return false;
			}
			if(levels==null){
				alert("模板级别不能为空!",$.scojs_message.TYPE_ERROR);
				return false;
			}
	      	  var formdata=$("#formdata").serialize();
	      	  formdata=formdata+"&parentId="+moduleId;
	   	      var url=contextPath+"/weiweb/modules/insertWWModules";
			  Util.ajax.postJson(url, formdata, function(data, flag){
				if(flag && data.returnCode=="0"){
					alert(data.returnMessage);
					C.load(contextPath+"/weiweb/modules/list");
				}else{
					alert(data.returnMessage);
				}
			});
		});
		
		//叶子节点选择事件
		$("#leaf").bind("change",function(){
			$("#article").empty();
			var leaf = $("#leaf").val();
			if(leaf == 'T'){
				getArticle();
			}
		});
		
		//动态从数据库获取微网站文章
		function getArticle(){
			var url = contextPath + "/weiweb/article/getAll";
			//异步请求文本模版列表数据
			var params = "";
			Util.ajax.postJsonSync(url, params, function(data, flag){
				var source = $("#article-template").html();
				var templet = Handlebars.compile(source);
				if(flag && data.returnCode=="0"){
					//渲染列表数据
					var htmlStr = templet(data.beans);
					$("#article").html(htmlStr);
					//$("#addBIZInterestRateForm select").val("");
				}
			});
		}
		
	});
