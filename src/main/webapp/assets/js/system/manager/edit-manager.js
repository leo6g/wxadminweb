
$(document).ready(function(){
	

	//编辑信息时，validate验证，验证通过后调用保存方法 
	$("#editForm").validate({
        submitHandler:function(form){
        	editObj();
        }    
    });
	
	//编辑信息
	$("#saveBtn").click(function(){
		var form = $("#editForm");
		form.submit();
	});
	
	//获取所有职位信息
	queryAllPosition();
	
	//初始化部门树状信息
	initDepartTree();
	
	//点击部门，弹窗选部门树信息
	$("#departName").click(function(){
		$("#myModal").modal("show");
	});
	
	//点击返回按钮
	$("#returnBtn").click(function(){
		C.load(contextPath + "/system/manager/list?currPage="+Util.browser.getParameter("currPage"));
	});
	
	$("#returnBack").click(function(){
		C.load(contextPath + "/system/manager/list?currPage="+Util.browser.getParameter("currPage"));
	});
	//编辑信息
	function editObj(){
		var url = contextPath + "/system/manager/updateManager";
		var params = $("#editForm").serialize();
		//异步请求编辑信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("用户修改成功!");
					//重新请求列表数据
					C.load(contextPath + "/system/manager/list?currPage="+Util.browser.getParameter("currPage"));
				}else{
					alert(data.returnMessage);
				}
			}else{
				if(data.returnCode=="-1"){
					alert("用户名已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}


	//获取所有职位信息
	function queryAllPosition(){
		var url = contextPath + "/system/position/getAllPosition";
		var params = "";
		//异步请求所有职位信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#position-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#positionId").html(htmlStr);
				//选中编辑的信息
				$("#positionId").val($("#edit-positionId").val());
			}
		});
	}



	//初始化部门树状信息
	function initDepartTree(){
		var url = contextPath + "/system/depart/getDepartTree";
		var params = "";
		//异步请求机构树数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//树初始化数据
			    $('#depart-tree').treeview({
			      color: "#333",
			      data: data.beans
			    });
			    //默认树状菜单全部合并
			    $('#depart-tree').treeview('collapseAll', { silent: true });
				//选中某个机构信息
			    $('#depart-tree').on('nodeSelected', function(event, data) {
			   	  	var id = data.tags.id;
			   	  	var departName = data.tags.departName;
			   	  	$("#departId").val(id);
			   	  	$("#departName").val(departName);
			   	  	$("#myModal").modal("hide");
			    });
			}
		});
	}
	//密码验证
	$("#passWord").blur(function(){
		var pass=$(this).val();
		if(!checkPass(pass)){
			$(this).val('').focus();
		}
	});
	
});



