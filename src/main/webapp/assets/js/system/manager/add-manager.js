
$(document).ready(function(){
	
	//获取当前日期放入有效开始时间内
	var nowDateStr = Util.date.getDate();
	$("#validBeginTime").val(nowDateStr);
	
	
	//新增信息时，validate验证，验证通过后调用保存方法 
	$("#addForm").validate({
        submitHandler:function(form){
        	insertObj();
        }    
    });
	
	//新增信息
	$("#saveBtn").click(function(){
		var password = $("#passWord").val();
		if(!checkPass(password)){
			return;
		}
		var form = $("#addForm");
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
	
	//点击取消按钮
	$("#cancelBtn").click(function(){
		C.load(contextPath + "/system/manager/list");
	});
	
	$("#returnBack").click(function(){
		C.load(contextPath + "/system/manager/list");
	});
	//新增信息
	function insertObj(){
		var url = contextPath + "/system/manager/insertManager";
		var params = $("#addForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("用户新增成功!");
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
			   	//选中之后修改未选择时的提示样式
			   	    $("#departName").attr({"class":"valid","aria-invalid":"false"});
			   	    $("#departName").removeAttr("aria-invalid");
			   	    $("#departName").next().remove();
			   	    
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


