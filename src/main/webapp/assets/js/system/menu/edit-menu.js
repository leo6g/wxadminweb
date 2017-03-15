$(document).ready(function(){
	    var menuId = $("#menuId").val();
		//查询菜单详细信息
		queryMenuView();
		//初始化上传按钮
		getUpload("uploadIcon","menuIcon","picIcon");
		//初始化菜单信息
		function queryMenuView(){
			var url = contextPath + "/system/menu/queryMenu?id="+menuId;
			var params = "";
			//异步请求职位列表数据
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					//基本信息
					var viewinfo = data.object;
					$("#menuName").val(viewinfo.menuName);
					$("#menuUrl").val(viewinfo.menuUrl);
					$("#menuOrder").val(viewinfo.menuOrder);
					$("#menuType").val(viewinfo.menuType);
					$("#menuLevel").val(viewinfo.menuLevel);
					if(viewinfo.parentName!='' && viewinfo.parentName!=null && viewinfo.parentName!='undefined'){
						$("#parentName").val(viewinfo.parentName);
						$("#parentId").val(viewinfo.parentId);
						
					}
					$("#menuIcon").val(viewinfo.menuIcon);
					if(viewinfo.menuIcon!=null && viewinfo.menuIcon!=''){
						$("#picIcon").attr("src",contextPath +"/"+viewinfo.menuIcon);
					}
				}
			});
		}


		//上传按钮
		$('#submitData').click(function(){
			var menuName=$("#menuName").val();
			var menuUrl=$("#menuUrl").val();
			var menuIcon=$("#menuIcon").val();
			var menuLevel=$("#menuLevel").val();
			if(menuName==null || menuName.trim()==""){
				alert("菜单名称不能为空!");
				return false;
			}
			if($("#menuType").val() == "1"){
				if(menuUrl==null || menuUrl.trim()==""){
					alert("菜单路径不能为空!");
					return false;
				}
			}
			if(menuLevel == null || menuLevel == ""){
				alert("菜单等级不能为空!");
				return false;
			}
			var formdata=$("#formdata").serialize();
		    formdata=formdata+"&id="+menuId;
				  var url=contextPath+"/system/menu/updateMenu";
				  Util.ajax.postJson(url, formdata, function(data, flag){
						if(flag && data.returnCode=="0"){
							alert(data.returnMessage);
							C.load(contextPath+"/system/menu/list");
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
		initMenuTree();
		//初始化菜单树状信息
		function initMenuTree(){
			var url = contextPath + "/system/menu/queryMenuTree";
			var params = "";
			//异步请求机构树数据
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					//树初始化数据
				    $('#menu-tree').treeview({
				      color: "#333",
				      data: data.beans
				    });
				    //默认树状菜单全部合并
				    $('#menu-tree').treeview('collapseAll', { silent: true });
					//选中某个机构信息
				    $('#menu-tree').on('nodeSelected', function(event, data) {
				   	  	var id = data.tags.id;
				   	  	var menuName = data.tags.menuName;
						$("#parentName").val(menuName);
						$("#parentId").val(id);
				   	  	$("#myModal").modal("hide");
				   	  	//判断父菜单的menulevel
						var parentLevel = data.tags.menuLevel;
						refreshMenuLevel();
						$("#menuLevel option").each(function(){
							if(parentLevel && $(this).val() <= parentLevel) {
								$(this).remove();
							}
						});
				    });
				}
			});
		}
		
		function refreshMenuLevel(){
			$("#menuLevel").html('<option value="1">1级</option><option value="2">2级</option><option value="3">3级</option><option value="4">4级</option><option value="5">5级</option>');
		}
	});


	
