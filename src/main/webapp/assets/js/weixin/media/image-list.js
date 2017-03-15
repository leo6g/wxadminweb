$(document).ready(function(){
	
//列表分页每页显示多少条记录
 global_limit = 12 ;
var global_groupId='';
var materialId=null;
	//初始化图片列表信息
	initimageInfo();
	//查询图片组信息带数量
	queryImgGroup();
	//查询图片组信息
	queryImgGroup2();
	//初始化上传按钮
	uploadMaterial("uploadIcon","imagePath","picIcon","/wx/image/uploadImage",initimageInfo);	
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryForm input[type='text']").val("");
		initimageInfo();
	});
	//初始化图片列表信息
	function initimageInfo(currentPage, limit,groupId){
			currentPage = currentPage||1;
			limit = limit||global_limit;
			groupId= groupId||global_groupId;
		var url = contextPath + "/wx/image/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit+"&groupId="+groupId;
		//异步请求图片列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			console.log(data);
			var source = $("#image-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#imageTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initimageInfo);
			}
		});
	}
	
	
	

	
	
	
	//获取图片分组信息带数量
	function queryImgGroup(){
		var url = contextPath + "/wx/mtrlimggroup/getListInfoById";
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#group-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#groupList").html(htmlStr);
			}
		});
	}
	
	
	//获取图片分组信息
	function queryImgGroup2(){
		var url = contextPath + "/wx/mtrlimggroup/getAll";
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#group2-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$(".group2List").html(htmlStr);
			}
		});
	}
	
	
	//新增图片信息
	function addImage(){//暂时不用
		var url = contextPath + "/wx/image/insertImage";
		var params = $("#addImageForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("图片新增成功!");
					$("#myModal3").modal('hide');
					//重新请求列表数据
					initimageInfo();
					//清空信息
					$("#addImageForm input").val("");
				}
			}
		});
	}
	
	
	//删除图片信息
	$("body").on('click','.deleteImage',function(){
		var materialId=$(this).attr("materialId");
		var mediaId=$(this).attr("mediaId");
		var url = contextPath + "/wx/image/deleteImage?materialId="+materialId+"&mediaId="+mediaId;
		var params = "";
		//删除按钮信息
		confirm("确定删除吗？", function(){
				Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initimageInfo();
			           queryImgGroup();
			          
					}
					else{
						alert(data.returnMessage);
					}
				});
		});
	});
	
	
	//删除图片信息批量
	$("#deleteImageGroup").click(function(){
		var materialIds='';
		var mediaIds='';
		$("input[name='imgchecklist']").each(function(){
			if($(this).prop("checked")){
				materialIds=materialIds+$(this).attr("materialId")+",";
				mediaIds=mediaIds+$(this).attr("mediaId")+",";
			}
		});
		if(materialIds==null || materialIds==''){
			alert("请至少选择一张图片来删除");
			return false;
		}
		materialIds=materialIds.substring(0,materialIds.length-1);
		mediaIds=mediaIds.substring(0,mediaIds.length-1);
		var url = contextPath + "/wx/image/deleteImageGroup?materialId="+materialIds+"&mediaId="+mediaIds;
		var params = "";
		//删除按钮信息
		confirm("确定删除吗？", function(){
				Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initimageInfo();
			           queryImgGroup();
			           //window.location.href=contextPath+"/system/menu/list";
					}
					else{
						alert(data.returnMessage);
					}
				});
		});
	});
	
	
	//编辑图片信息
	$("body").on('click','#editImage',function(){
		var name=$("#imagename").val();
		var url = contextPath + "/wx/image/updateImage?materialId="+materialId;
		console.log(url);
		var params = {};
		params.name=name;
		//删除按钮信息
		Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
						$("#imagename").val('');
				       $("#myModal2").modal('hide');
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initimageInfo(0,global_limit,global_groupId);
			           //window.location.href=contextPath+"/system/menu/list";
					}
					else{
						alert(data.returnMessage);
					}
				});
	});
	
	
	//编辑图片分组信息
	$("body").on('click','#editImageGroup',function(){
		var imagegroupid=$("#imagegroupid").val();
		if(imagegroupid==null || imagegroupid==''){
			alert("请选择分组信息");
			return false;
		}
		var url = contextPath + "/wx/image/updateImage?materialId="+materialId;
		console.log(url);
		var params = {};
		params.groupId=imagegroupid;
		//删除按钮信息
		Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
					   $("#imagegroupid").val('');
				       $("#myModal1").modal('hide');
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initimageInfo(0,global_limit,global_groupId);
			           queryImgGroup();
			           //window.location.href=contextPath+"/system/menu/list";
					}
					else{
						alert(data.returnMessage);
					}
				});
	});
	
	
	
	//编辑图片分组信息 多条
	$("body").on('click','#editImageGroupAll',function(){
		var imagegroupid=$("#imagegroupidAll").val();
		if(imagegroupid==null || imagegroupid==''){
			alert("请选择分组信息");
			return false;
		}
		var materialIds='';
		$("input[name='imgchecklist']").each(function(){
			if($(this).prop("checked")){
				materialIds=materialIds+"'"+$(this).attr("materialId")+"',";
			}
		});
		if(materialIds==null || materialIds==''){
			alert("请至少选择一张图片来移动分组");
			return false;
		}
		materialIds=materialIds.substring(0,materialIds.length-1);
		var url = contextPath + "/wx/image/updateImageGroup?materialId="+materialIds;
		var params = {};
		params.groupId=imagegroupid;
		//删除按钮信息
		Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
					   $("#imagegroupidAll").val('');
				       $("#myModal0").modal('hide');
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initimageInfo(0,global_limit,global_groupId);
			           queryImgGroup();
			           //window.location.href=contextPath+"/system/menu/list";
					}
					else{
						alert(data.returnMessage);
					}
				});
	});
	
	
	
	//编辑图片信息
	$("body").on('click','.editImage',function(){
		materialId=$(this).attr("materialId");
		$("#myModal2").modal('show');
	});
	
	//编辑图片分组editImageGroup
	$("body").on('click','.editImageGroup',function(){
		materialId=$(this).attr("materialId");
		$("#myModal1").modal('show');
	});
	
	$(".popover-options a").popover({
		html: true
	});
	$("body").on("click", ".qx", function() {
		$(".popover").hide()
	});
	//添加分组信息
	$("body").on("click", ".qd", function() {
		var groupName=	$("#groupName").val();
		if(groupName==null || groupName==''){
			alert("分组名称不能为空");
			return false;
		}else{
			var params={};
			params.groupName=groupName;
			var url = contextPath + "/wx/mtrlimggroup/insertWXMtrlImgGroup";
			Util.ajax.postJson(url,params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert(data.returnMessage);
						//重新请求列表数据
						queryImgGroup();
						queryImgGroup2();
						$("#groupName").val("");
						$(".popover").hide()
					}
				}
			});
		}

	})
	
	//查询分组下的图片
	$("body").on("click", ".showGroupInfo", function() {
			global_groupId=$(this).attr("groupId");
			console.log(global_groupId);
			initimageInfo(0,global_limit,global_groupId);
	})
	//查询
	$("#querySubmit").click(function(){
		initimageInfo(0,global_limit,global_groupId);
	});
	//全选
	$("#checkALL").click(function(){
		if($(this).prop("checked")){
			$("input[name='imgchecklist']").prop("checked",true); 
		}else{
			$("input[name='imgchecklist']").prop("checked",false); 
		}
	});
	
	$("#syncImage").click(function(){
		$('body').showLoading();
		var url = contextPath + "/wx/image/syncImage";
		$.ajax({
			url:url,
			data:'',
			timeout :600000,
			type :'post',
			dataType:'json',
			success:function(data,flag){
				if(flag){
					if(data.returnCode=="0"){
						$('body').hideLoading();
						alert(data.returnMessage);
						//重新请求列表数据
						initimageInfo();
						queryImgGroup();
					}
				  }else{
					  $('body').hideLoading();
					  alert(data.returnMessage);
				  }
				}
		});
	});

});



