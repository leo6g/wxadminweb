
	$(document).ready(function(){
		var menuId = $("#menuId").val();
		//初始化父级菜单信息
		queryMenuView();
		//初始化上传按钮
		getUpload("uploadIcon","menuIcon","picIcon");	
		//初始化菜单信息
		function queryMenuView(){
			var url = contextPath + "/system/menu/queryMenu?id="+menuId;
			var params = "";
			//异步请求数据
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					//基本信息
					var viewinfo = data.object;
					if(viewinfo !=null)
					{
						$("#parentName").text("父级菜单："+viewinfo.menuName);
						//判断父菜单的menulevel
						$("select[name=menuLevel] option").each(function(){
							if($(this).val() <= viewinfo.menuLevel) {
								$(this).remove();
							}
						});
					}
				}
			});
		}

		//提交按钮
		$('#submitData').click(function(){
			var menuName=$("#menuName").val();
			var menuUrl=$("#menuUrl").val();
			var menuIcon=$("#menuIcon").val();
			var menuLevel=$("#menuLevel").val();
			if(menuName==null || menuName.trim() == ""){
				alert("菜单名称不能为空!");
				return false;
			}
			if($("#menuType").val() == "1"){
				if(menuUrl==null || menuUrl.trim()==""){
					alert("菜单路径不能为空!");
					return false;
				}
			}
			if(menuLevel == null || menuLevel.trim() == ""){
				alert("菜单等级不能为空!");
				return false;
			}
	      	  var formdata=$("#formdata").serialize();
	      	  formdata=formdata+"&parentId="+menuId;
	   	      var url=contextPath+"/system/menu/insertMenu";
			  Util.ajax.postJson(url, formdata, function(data, flag){
				if(flag && data.returnCode=="0"){
					alert(data.returnMessage);
					C.load(contextPath+"/system/menu/list");
				}else{
					alert(data.returnMessage);
				}
			});
		});
		
	});
