//列表分页每页显示多少条记录
$(document).ready(function(){
	var global_limit = 10 ;
	
	//初始化微信素材表列表信息
	initWXMaterialListInfo();
	
	
	//新增微信素材表信息时，validate验证，验证通过后调用保存方法 
	$("#addWXMaterialForm").validate({
        submitHandler:function(form){
        	addWXMaterial();
        }    
    });
	
	//新增微信素材表信息
	$("#saveWXMaterialBtn").click(function(){
		var form = $("#addWXMaterialForm");
		form.submit();
	});
	
	//选中微信素材表信息
	$("#wXMaterialListTable").delegate("tr","click",function(){
		var outerLink = $(this).find("td:eq(1)").html();
		var name = $(this).find("td:eq(2)").html();
		var materialId = $(this).find("td:eq(3)").html();
		var createUser = $(this).find("td:eq(5)").html();
		var description = $(this).find("td:eq(6)").html();
		var innerLink = $(this).find("td:eq(7)").html();
		
		$("#edit-outerLink").val(outerLink);
		$("#edit-name").val(name);
		$("#edit-materialId").val(materialId);
		$("#edit-createUser").val(createUser);
		$("#edit-description").val(description);
		$("#edit-innerLink").val(innerLink);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改微信素材表信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXMaterialForm").validate({
        submitHandler:function(form){
        	editWXMaterial();
        }    
    });
	
	//保存编辑的微信素材表信息
	$("#edit-saveWXMaterialBtn").click(function(){
		var form = $("#editWXMaterialForm");
		form.submit();
	});
	//编辑微信素材表信息
	$("#editWXMaterialBtn").click(function(){
		var materialId = $("#edit-materialId").val();
		if(materialId==""){
			alert("请选择一条信息!");
			return false;
		}
	});
	
	//查询
	$("#querySubmit").click(function(){
		initWXMaterialListInfo();
	});
	
	//删除微信素材表信息
	$("#delWXMaterialBtn").click(function(){
		//logicDelWXMaterial();//逻辑删除
		delWXMaterial()
	});
	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 
});


//初始化微信素材表列表信息
function initWXMaterialListInfo(currentPage, limit){
	if(typeof currentPage == "undefined"){
		currentPage = 1;
	}
	if(typeof limit == "undefined"){
		limit = global_limit;
	}
	var url = contextPath + "/wx/material/getList";
	var params = $("#queryForm").serialize();
	params = params + "&pageNumber="+currentPage+"&limit="+limit;
	//异步请求微信素材表列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		var source = $("#wXMaterial-list-template").html();
		var templet = Handlebars.compile(source);
		if(flag && data.returnCode=="0"){
			//渲染列表数据
			var htmlStr = templet(data.beans);
			$("#wXMaterialListTable").html(htmlStr);
			//初始化分页数据(当前页码，总数，回调查询函数)
			initPaginator(currentPage, data.bean.count, initWXMaterialListInfo);
		}
	});
}


//新增微信素材表信息
function addWXMaterial(){
	var url = contextPath + "/wx/material/insertWXMaterial";
	var params = $("#addWXMaterialForm").serialize();
	//异步请求新增微信素材表信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				//$("#alert-info-content").html("微信素材表新增成功!");
				//$("#alert-info").modal("show");
				alert("微信素材表新增成功!");
				$("#myModal").modal('hide');
				//重新请求列表数据
				initWXMaterialListInfo();
				//清空新增微信素材表的弹窗信息
				$("#addWXMaterialForm input").val("");
				$("#descn").val("");
			}
		}else{
			if(data.returnCode=="-1"){
				alert("微信素材表编码已经存在，请修改!");
			}else{
				alert(data.returnMessage);
			}
		}
	});
}


//修改微信素材表信息
function editWXMaterial(){
	var materialId = $("#edit-materialId").val();
	
	if(materialId==""){
		alert("请选择一条信息!");
		return false;
	}
	
	var url = contextPath + "/wx/material/updateWXMaterial";
	var params = $("#editWXMaterialForm").serialize();
	//异步请求修改微信素材表信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				alert("微信素材表信息修改成功!");
				//编辑微信素材表信息清空
				$("#editWXMaterialForm input").val("");
				$("#edit-descn").val("");
				//重新请求列表数据
				initWXMaterialListInfo();
				$("#myModal1").modal('hide');
			}
		}else{
			if(data.returnCode=="-1"){
				alert("微信素材表编码已经存在，请修改!");
			}else{
				alert(data.returnMessage);
			}
		}
	});
}
//删除微信素材表
function delWXMaterial(){
	var materialId = $("#edit-materialId").val();
	if(materialId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	confirm("确定删除吗？",function(){
		var url = contextPath + "/wx/material/deleteWXMaterial";
		var params = $("#editWXMaterialForm").serialize();
		//异步请求逻辑删除微信素材表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信素材表删除成功!");
					//编辑微信素材表信息清空
					$("#editWXMaterialForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXMaterialListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	});
}

//逻辑删除微信素材表
function logicDelWXMaterial(){
	var materialId = $("#edit-materialId").val();
	if(materialId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	var flag = confirm("确定删除吗？")
	if(flag){
		var url = contextPath + "/wx/material/logicDeleteWXMaterial";
		var params = $("#editWXMaterialForm").serialize();
		//异步请求逻辑删除微信素材表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信素材表删除成功!");
					//编辑微信素材表信息清空
					$("#editWXMaterialForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXMaterialListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	}
}


