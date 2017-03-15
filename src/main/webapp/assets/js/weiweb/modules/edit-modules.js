$(document).ready(function(){
	    var moduleId = $("#moduleId").val();
		//查询菜单详细信息
	    queryModuleView();
		//初始化上传按钮
		getUpload("uploadIcon","iconPath","picIcon");
		//初始化菜单信息
		function queryModuleView(){
			var url = contextPath + "/weiweb/modules/getById?moduleId="+moduleId;
			var params = "";
			//异步请求职位列表数据
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					//基本信息
					var viewinfo = data.object;
					$("#name").val(viewinfo.name);
					$("#levels").val(viewinfo.levels);
					$("#leaf").val(viewinfo.leaf);
					if(viewinfo.parentId!='' && viewinfo.parentId!=null && viewinfo.parentId!='undefined'){
						$("#parentName").val(viewinfo.parentName);
						$("#parentId").val(viewinfo.parentId);
					}
					$("#iconPath").val(viewinfo.iconPath);
					if(viewinfo.iconPath!=null && viewinfo.iconPath!=''){
						$("#picIcon").attr("src",contextPath +"/"+viewinfo.iconPath);
					}
					//加载文章下拉
					if(viewinfo.leaf == 'T'){
						getArticle();
					}
					$("#article").val(viewinfo.articleId);
				}
			});
		}


		//上传按钮
		$('#submitData').click(function(){
			var name=$("#name").val();
			var iconPath=$("#iconPath").val();
			if(name==null || name.trim()==""){
				alert("模块名称不能为空!",$.scojs_message.TYPE_ERROR);
				return false;
			}
			var formdata=$("#formdata").serialize();
		    formdata=formdata+"&moduleId="+moduleId;
				  var url=contextPath+"/weiweb/modules/updateWWModules";
				  Util.ajax.postJson(url, formdata, function(data, flag){
						if(flag && data.returnCode=="0"){
							alert(data.returnMessage);
							C.load(contextPath+"/weiweb/modules/list");
						}else{
							alert(data.returnMessage);
						}
					});

		});
		//点击父级菜单，弹窗选菜单树信息
		$("#parentName").click(function(){
			$("#myModal").modal("show");
		});
		
		//初始化菜单树状信息
		initModuleTree();
		//初始化菜单树状信息
		function initModuleTree(){
			var url = contextPath + "/weiweb/modules/queryModuleTree";
			var params = "";
			//异步请求机构树数据
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					//树初始化数据
				    $('#module-tree').treeview({
				      color: "#333",
				      data: data.beans
				    });
				    //默认树状菜单全部合并
				    $('#module-tree').treeview('collapseAll', { silent: true });
					//选中某个机构信息
				    $('#module-tree').on('nodeSelected', function(event, data) {
				   	  	var moduleId = data.tags.moduleId;
				   	  	var name = data.tags.name;
						$("#parentName").val(name);
						$("#parentId").val(moduleId);
				   	  	$("#myModal").modal("hide");
				    });
				}
			});
		};
		
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
				var source = $("#edit-article-template").html();
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


	
