
$(document).ready(function(){
	//global_limit=2;
	var roleId = $("#roleId").val();
	var checkMenuIds = [];//用户已选中的菜单ID
	var checkButtonIds = [];//用户已选中的菜单ID
	//角色基本信息
	initRoleInfo(roleId);
	//角色菜单关系表信息
	initMenuListInfo();
	//全选和取消全选
//	$("#menuTableList").on("click","[type='checkbox']",function(e){
//		if(this.checked){ 
//			var data_tt_id=$(this).attr("menuId");
//			$("[menuparentId='"+data_tt_id+"']").each(function(){
//				$(this).prop("checked","checked");
//			});
//
//		}else{
//			var data_tt_id=$(this).attr("menuId");
//			$("[menuparentId='"+data_tt_id+"']").each(function(){
//				$(this).removeProp("checked");
//			});
//		}
//
//
//	});
		//初始化角色基本信息
		function initRoleInfo(roleId){
			var url = contextPath + "/system/role/queryRoleById?id="+roleId;
			var params = "";
			//异步请求按钮数据
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					var viewinfo = eval(data.object);
					$("#roleName").text(viewinfo.roleName);
					$("#roleCode").text(viewinfo.roleCode);
				}
			});
		}

		//初始化菜单列表
		function initMenuListInfo(currentPage, limit){
			if(typeof currentPage == "undefined"){
				currentPage = 1;
			}
			if(typeof limit == "undefined"){
				limit = global_limit;
			}
			var url = contextPath + "/system/menu/queryMenusTree";
			var params = "pageNumber="+currentPage+"&limit="+limit+"&roleId="+roleId;
			//异步请求职位列表数据
			Util.ajax.postJson(url, params, function(data, flag){
				var source = $("#menu-list-template").html();
				var templet = Handlebars.compile(source);
				if(flag && data.returnCode=="0"){
					var htmlStr = templet(data.beans);
					$("#menu-list-table").html(htmlStr);
					$("#menuTableList").treetable({expandable: true});
					//给父子元素添加点击关联事件
					makeLinkClick(data.beans);
				}
			});
		}
		//遍历数组，给每个元素绑定事件
		function makeLinkClick(beans){
			$(beans).each(function(i,e){
				var id = e.id;
				var pid = e.parentId;
				var children = findChildren(id,beans);
				var parents = findParents(pid,beans);
				bindClick(id,children,beans);
				bindClick(id,parents,beans,"up");
			});
		}
		//绑定事件的动作
		function bindClick(singleId,multipleId,beans,flag){
			var expSource = "[menuId="+singleId+"]";
			var expTarget = buildExp(multipleId);
			$(expSource).click(function(){
				var checked = $(this).prop("checked");
				//如果是点击取消，并且是点击的子孙菜单，需要检查父辈的其他子辈有没有选中的，如果没有则取消选中，如果有则不取消
				if(!checked && flag === "up"){
					for(var i=0;i<multipleId.length;i++){
						//遍历该元素的父元素，如果父元素没有选中的子元素，取消选中，否则不取消
						var children = findChildren(multipleId[i],beans);
						var childExp = buildExp(children,":checked");
						if($(childExp).length === 0){
							$("[menuId="+multipleId[i]+"]").removeProp("checked");
						}
					}
				}else{
					$(expTarget).prop("checked",checked);
				}
			})
		}

		//组装选择器
		function buildExp(ids,str){
			str = str||"";
			var exp = "";
			for(var i=0;i<ids.length;i++){
				exp += "[menuId="+ids[i]+"]"+str+",";
			}
			return exp.substring(0,exp.length-1);
		}
		//递归寻找一个指定pid的父级和祖父元素，相对简单，通过父级元素level===1来结束递归
		function findParents(pid,beans,arr){
			arr = arr || [];
			$(beans).each(function(i,e){
				if(pid === e.id){
					arr.push(e.id);
					if(e.menuLevel !== 1){
						findParents(e.parentId,beans,arr);
					}
					
				}
			})
			return arr;
		}

		//递归寻找一个指定id元素的子孙元素
		function findChildren(id,beans,arr){
			arr = arr || [];
			if(!hasChildren(id,beans)){
				//这里是跳出循环
				return false;
			}
			$(beans).each(function(i,e){
				if(e.parentId === id){
					arr.push(e.id);
					findChildren(e.id,beans,arr);
				}
			})
			return arr;
		}
		
		//判断是否有子元素
		function hasChildren(id,beans){
			var has = false;
			$(beans).each(function(i,e){
				if(id === e.parentId){
					has = true;
					return false;
				}
			});
			return has;
		}

		//获取选中的菜单checkbox
		function getCheckMenuIds() { 
		    $("input:checkbox[name=menuId]:checked").each(function(i){  
		    	checkMenuIds.push($(this).attr("menuId"));  
		    });    
		}
		//获取选中的按钮checkbox
		function getCheckBtnIds() { 
		    $("input:checkbox[name=buttonId]:checked").each(function(i){  
		    	checkButtonIds.push($(this).attr("buttonId")); 
		    });  
		}

		//保存按钮
		$('#submitData').click(function(){
			//获取选中的菜单ID
			getCheckMenuIds();
			if(checkMenuIds.length == 0) {
				alert("请选择菜单权限");
				return false;
			}
			//获取选中的按钮ID
			getCheckBtnIds();
			 var rolePrivForm = new Array();
			 if(checkMenuIds.length>0){
				 for(var i=0;i<checkMenuIds.length;i++){
					 var menuId={};
					 menuId.roleId=roleId;
					 menuId.privId=checkMenuIds[i];
					 menuId.type=1;
					 rolePrivForm.push(menuId);
				 }
			 }
			 if(checkButtonIds.length>0){
				 for(var i=0;i<checkButtonIds.length;i++){
					 var btnId={};
					 btnId.roleId=roleId;
					 btnId.privId=checkButtonIds[i];
					 btnId.type=2;
					 rolePrivForm.push(btnId);
				 }
			 }
			var url=contextPath+"/system/role/insertRolePrivInfo";
		    $.ajax({ 
		        type:"POST", 
		        url:url, 
		        dataType:"json",      
		        contentType:"application/json",               
		        data:JSON.stringify(rolePrivForm), 
		        success:function(data){ 
		    		if(data.returnCode=="0"){
		    			alert(data.returnMessage);
		    			//添加成功后赋值null,避免重复提交
		    			checkMenuIds=null;
		    			checkButtonIds=null;
		    			C.load(contextPath+"/system/role/list");
		    		}else{
		    			alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
		    		}                     
		        } 
		     }); 
			
		});


		//判断是否关联角色
		Handlebars.registerHelper("compareR",function(isRelate,options){
			  if(isRelate){
			      //满足添加继续执行
			      return options.fn(this);
			  }else{
			     //不满足条件执行{{else}}部分
			     return options.inverse(this);
			 }
		});


	
	
});


