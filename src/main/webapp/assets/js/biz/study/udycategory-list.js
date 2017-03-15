$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化邮学堂板块表列表信息
	initStudyCategoryListInfo();
	
	
	//新增邮学堂板块表信息时，validate验证，验证通过后调用保存方法 
	$("#addStudyCategoryForm").validate({
        submitHandler:function(form){
        	addStudyCategory();
        }    
    });
	
	//新增邮学堂板块表信息
	$("#saveStudyCategoryBtn").click(function(){
		var form = $("#addStudyCategoryForm");
		form.submit();
	});
	
	//选中邮学堂板块表信息
	$("#studyCategoryListTable").delegate("tr","click",function(){
		var iconPath = $(this).find("td:eq(1)").html();
		var name = $(this).find("td:eq(2)").html();
		var id = $(this).find("td:eq(3)").html();
		var createUser = $(this).find("td:eq(5)").html();
		var bgColor = $(this).find("td:eq(6)").html();
		var sort = $(this).find("td:eq(7)").html();
		
		$("#edit-iconPath").val(iconPath);
		$("#edit-name").val(name);
		$("#edit-id").val(id);
		$("#edit-createUser").val(createUser);
		$("#edit-bgColor").val(bgColor);
		$("#edit-sort").val(sort);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改邮学堂板块表信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editStudyCategoryForm").validate({
        submitHandler:function(form){
        	editStudyCategory();
        }    
    });
	
	//保存编辑的邮学堂板块表信息
	$("#edit-saveStudyCategoryBtn").click(function(){
		var form = $("#editStudyCategoryForm");
		form.submit();
	});
	//编辑邮学堂板块表信息
	$("#editStudyCategoryBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除邮学堂板块表信息
	$("#delStudyCategoryBtn").click(function(){
		//logicDelStudyCategory();//逻辑删除
		delStudyCategory()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initStudyCategoryListInfo();
	});
	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	initStudyCategoryListInfo();
	    }
	   });
	
	//初始化邮学堂板块表列表信息
	function initStudyCategoryListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/studycategoty/getList";
		var params = $("#queryForm").serialize();
		console.info($("#queryName").val());
		console.info(params);
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求邮学堂板块表列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#studyCategory-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#studyCategoryListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initStudyCategoryListInfo);
			}
		});
	}
	
	
	//新增邮学堂板块表信息
	function addStudyCategory(){
		var url = contextPath + "/biz/studycategoty/insertStudyCategory";
		var params = $("#addStudyCategoryForm").serialize();
		//异步请求新增邮学堂板块表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("邮学堂板块表新增成功!");
					//$("#alert-info").modal("show");
					alert("邮学堂板块表新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initStudyCategoryListInfo();
					//清空新增邮学堂板块表的弹窗信息
					$("#addStudyCategoryForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("邮学堂板块表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改邮学堂板块表信息
	function editStudyCategory(){
		var id = $("#edit-id").val();
	
		if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	if($("#edit-iconPath").val().length>200){
		alert("图标路径的长度不能超过200字符！")
		return false;
	}
		var url = contextPath + "/biz/studycategoty/updateStudyCategory";
		var params = $("#editStudyCategoryForm").serialize();
		//异步请求修改邮学堂板块表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("邮学堂板块表信息修改成功!");
					//编辑邮学堂板块表信息清空
					$("#editStudyCategoryForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initStudyCategoryListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("邮学堂板块表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除邮学堂板块表
	function delStudyCategory(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/studycategoty/deleteStudyCategory";
			var params = $("#editStudyCategoryForm").serialize();
			//异步请求逻辑删除邮学堂板块表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("邮学堂板块表删除成功!");
						//编辑邮学堂板块表信息清空
						$("#editStudyCategoryForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initStudyCategoryListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除邮学堂板块表
	function logicDelStudyCategory(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/studycategoty/logicDeleteStudyCategory";
			var params = $("#editStudyCategoryForm").serialize();
			//异步请求逻辑删除邮学堂板块表信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("邮学堂板块表删除成功!");
						//编辑邮学堂板块表信息清空
						$("#editStudyCategoryForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initStudyCategoryListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

});



