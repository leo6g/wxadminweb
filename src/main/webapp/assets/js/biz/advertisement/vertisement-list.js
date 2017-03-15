$(document).ready(function(){
	var adverId='' ;
	//初始化上传按钮
	advGetUpload("edit-uploadIcon","edit-imgPath","edit-picIcon","/biz/advertisement/doUpload");
	//初始化上传按钮
	advGetUpload("add-uploadIcon","add-imgPath","add-picIcon","/biz/advertisement/doUpload");
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化文章植入广告管理列表信息
	initAdvertisementListInfo();
	
	
	//新增文章植入广告管理信息时，validate验证，验证通过后调用保存方法 
	$("#addAdvertisementForm").validate({
        submitHandler:function(form){
        	addAdvertisement();
        }    
    });
	
	//选中文章植入信息
	$("#advertisementListTable").delegate("tr","click",function(){
		adverId = $(this).find("td:eq(1)").html();
	});
	
	//新增文章植入广告管理信息
	$("#saveAdvertisementBtn").click(function(){
		var form = $("#addAdvertisementForm");
		form.submit();
	});

	//修改文章植入广告管理信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editAdvertisementForm").validate({
        submitHandler:function(form){
        	editAdvertisement();
        }    
    });
	
	//保存编辑的文章植入广告管理信息
	$("#edit-saveAdvertisementBtn").click(function(){
		var form = $("#editAdvertisementForm");
		form.submit();
	});
	//编辑文章植入广告管理信息
	$("#editAdvertisementBtn").click(function(){
		if(adverId==""){
			alert("请选择一条信息!");
			return false;
		}
		queryAdversementInfo();
	});
	
	//删除文章植入广告管理信息
	$("#delAdvertisementBtn").click(function(){
		logicDelAdvertisement();//逻辑删除
	});
	//查询
	$("#querySubmit").click(function(){
		initAdvertisementListInfo();
	});
	//局部绑定回车事件
	 $("#queryFirmName").bind('keyup',function(event){
		 event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
	 });
	 $("#queryFirmPhone").bind('keyup',function(event){
		 event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
	 });
	//初始化文章植入广告管理列表信息
	function initAdvertisementListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/advertisement/getList";
		var formdata=$("#queryForm").serialize();
		formdata=formdata +"&pageNumber="+currentPage+"&limit="+limit;
		//异步请求文章植入广告管理列表数据
		Util.ajax.postJson(url, formdata, function(data, flag){
			var source = $("#advertisement-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				console.log(data.beans);
				$("#advertisementListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initAdvertisementListInfo);
			}
		});
	}
	
	
	//新增文章植入广告管理信息
	function addAdvertisement(){
		var url = contextPath + "/biz/advertisement/insertAdvertisement";
		var params = $("#addAdvertisementForm").serialize();
		//异步请求新增文章植入广告管理信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("文章植入广告管理新增成功!");
					//$("#alert-info").modal("show");
					alert("文章植入广告管理新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initAdvertisementListInfo();
					//清空新增文章植入广告管理的弹窗信息
					$("#addAdvertisementTable input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("文章植入广告管理编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改文章植入广告管理信息
	function editAdvertisement(){	
		var url = contextPath + "/biz/advertisement/updateAdvertisement";
		var params = $("#editAdvertisementForm").serialize();
		//异步请求修改文章植入广告管理信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("文章植入广告管理信息修改成功!");
					//编辑文章植入广告管理信息清空
					$("#editAdvertisementTable input").val("");
					//重新请求列表数据
					initAdvertisementListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("文章植入广告管理编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}	
	//逻辑删除文章植入广告管理
	function logicDelAdvertisement(){
		if(adverId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/advertisement/logicDeleteAdvertisement";
			//异步请求逻辑删除文章植入广告管理信息
			var params={};
			params.adverId=adverId;
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("文章植入广告管理删除成功!");
						//重新请求列表数据
						initAdvertisementListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	//编辑前根据ID查询信息
	function queryAdversementInfo(){
		var url = contextPath + "/biz/advertisement/getById?adverId="+adverId;
		var params = "";
		//异步请求信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = data.object;
				if(viewinfo !=null)
				{
					$("#edit-url").val(viewinfo.url);
					$("#edit-name").val(viewinfo.name);
					$("#edit-startDate").val(viewinfo.startDate);
					$("#edit-endDate").val(viewinfo.endDate);
					$("#edit-firmPhone").val(viewinfo.firmPhone);
					$("#edit-firmName").val(viewinfo.firmName);
					$("#edit-adverId").val(adverId);
					$("#edit-imgPath").val(viewinfo.imgPath);
					$("#edit-picIcon").attr("src",contextPath+"/viewImage/viewImage?imgPath=advertisement/"+viewinfo.imgPath);
					
				}

			}
		});
	}

});



