$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微网站列表信息
	initBannerListInfo();
	
	
	//新增微网站信息时，validate验证，验证通过后调用保存方法 
	$("#addBannerForm").validate({
        submitHandler:function(form){
        	addBanner();
        }    
    });
	$("#type").change(function(){
		if($(this).val() == "banner"){
			$(".select-url").removeClass("dis-no");
		}else{
			$(".select-url").addClass("dis-no");
		}
	})
	$("#edit-type").change(function(){
		if($(this).val() == "banner"){
			$(".select-url1").removeClass("dis-no");
		}else{
			$(".select-url1").addClass("dis-no");
		}
	})
	
	//新增微网站信息
	$("#saveBannerBtn").click(function(){
		var imagePath=$("#path").val();
		var sort = $("#sort").val();
		if(imagePath==null || imagePath.trim()==""){
			alert("图片链接不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(isNaN(sort)){
			alert("请输入数字!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		uploader.start();
	});
	
	//选中微网站信息
	$("#bannerListTable").delegate("tr","click",function(){
		var path = $(this).find("td:eq(1)").html();
		var name = $(this).find("td:eq(2)").html();
		var bannerId = $(this).find("td:eq(3)").html();
		var createUser = $(this).find("td:eq(4)").html();
		var type = $(this).find("td:eq(5)").html();
		var url = $(this).find("td:eq(6)").html();
		var sort = $(this).find("td:eq(7)").html();
		if (type=="轮播广告") {
			type="banner";
		}else if(type=="背景图片"){
			type="background";
		}
		var src = contextPath + "/weiweb/banner/getById";
		var params={"bannerId":bannerId}
		//异步请求贷款信息列表数据
		Util.ajax.postJson(src, params, function(data, flag){
			$("#oldPath").val(data.object.path);
		});
		
		$("#edit-path").val(path);
		$("#edit-name").val(name);
		$("#edit-bannerId").val(bannerId);
		$("#edit-createUser").val(createUser);
		$("#edit-sort").val(sort);
		$("#edit-url").val(url);
		$("#edit-type").val(type);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		if($("#edit-type").val() == "banner"){
			$(".select-url1").removeClass("dis-no");
		}else{
			$(".select-url1").addClass("dis-no");
		}
		return false;
	});
	
	//修改微网站信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editBannerForm").validate({
        submitHandler:function(form){
        	editBanner();
        }    
    });
	
	//保存编辑的微网站信息
	$("#edit-saveBannerBtn").click(function(){
		var imagePath = $("#edit-path").val();
		var oldImagePath=$("#oldPath").val();
		var sort = $("#edit-sort").val();
		if(isNaN(sort)){
			alert("请输入数字!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(imagePath==null || imagePath.trim()==""){
			$("#edit-path").val(oldImagePath);
			var formdata=$("#editBannerForm").serialize();
			var url=contextPath+"/weiweb/banner/updateBanner";
			Util.ajax.postJson(url, formdata, function(data, flag){
				  if(flag && data.returnCode=="0"){
					alert("网点信息更新成功!",$.scojs_message.TYPE_OK);
					initBannerListInfo();
					$("#myModal1").modal('hide');
				  }
				});
		}else{
			editBannerUploader.start();
		}
	});
	//编辑微网站信息
	$("#editBannerBtn").click(function(){
		var bannerId = $("#edit-bannerId").val();
		if(bannerId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		$("#edit-path").val("");
		var imagePath = $("#oldPath").val();
		$('#edit-preview').html($( '<img src="/wxadminweb/viewImage/viewImage?imgPath='+imagePath+'" style="width:380px;height:200px;" alt="正在加载..." />'));
	});
	
	//删除微网站信息
	$("#delBannerBtn").click(function(){
		//logicDelBanner();//逻辑删除
		delBanner()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initBannerListInfo();
	});
	//局部绑定回车事件
//	 $("#queryName").bind('keyup',function(event){
//		    event=document.all?window.event:event;
//		    if((event.keyCode || event.which)==13){
//		    	document.getElementById("querySubmit").click();
//		    }
//		   }); 
	//初始化微网站列表信息
	function initBannerListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/weiweb/banner/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求微网站列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			for (var i = 0; i < data.beans.length; i++) {
				if (data.beans[i].type=="banner") {
					data.beans[i].type="轮播广告";
				}else if (data.beans[i].type=="background") {
					data.beans[i].type="背景图片";
				}
			}
			var source = $("#Banner-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#bannerListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initBannerListInfo);
			}
		});
	}
	
	
	//新增微网站信息
	function addBanner(){
		var url = contextPath + "/weiweb/banner/insertBanner";
		var params = $("#addBannerForm").serialize();
		//异步请求新增微网站信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("微网站新增成功!");
					//$("#alert-info").modal("show");
					alert("微网站新增成功!",$.scojs_message.TYPE_OK);
					$("#myModal").modal('hide');
					//重新请求列表数据
					initBannerListInfo();
					//清空新增微网站的弹窗信息
					$("#addBannerForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微网站编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改微网站信息
	function editBanner(){
		var bannerId = $("#edit-bannerId").val();
	
		if(bannerId==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/weiweb/banner/updateBanner";
		var params = $("#editBannerForm").serialize();
		//异步请求修改微网站信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微网站信息修改成功!");
					//编辑微网站信息清空
					$("#editBannerForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initBannerListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微网站编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除微网站
	function delBanner(){
		var bannerId = $("#edit-bannerId").val();
		if(bannerId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/weiweb/banner/deleteBanner";
			var params = $("#editBannerForm").serialize();
			//异步请求逻辑删除微网站信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站删除成功!");
						//编辑微网站信息清空
						$("#editBannerForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBannerListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除微网站
	function logicDelBanner(){
		var bannerId = $("#edit-bannerId").val();
		if(bannerId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/weiweb/banner/logicDeleteBanner";
			var params = $("#editBannerForm").serialize();
			//异步请求逻辑删除微网站信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("微网站删除成功!");
						//编辑微网站信息清空
						$("#editBannerForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBannerListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	//实例化一个plupload上传对象
	var uploader = new plupload.Uploader({ //实例化一个plupload上传对象
		browse_button : 'path',
		url : contextPath+'/weiweb/banner/doUpload',
	    flash_swf_url : '${req.contextPath}/assets/plupload/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
	    silverlight_xap_url : '${req.contextPath}/assets/plupload/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
	    filters: {
	   	  mime_types : [ //只允许上传图片和zip文件
	   	    { title : "Image files", extensions : "jpg,jpeg,gif,png" }, 
	   	    { title : "Zip files", extensions : "zip" }
	   	  ],
	   	  max_file_size : '10mb', //最大只能上传2M的文件
	   	  prevent_duplicates : true //不允许选取重复文件
	    },
	    multi_selection: true
	});

	uploader.init(); //初始化

	//绑定文件添加进队列事件
	uploader.bind('FilesAdded',function(uploader,files){
		  $.each(uploader.files, function (i, file) {
		        if (uploader.files.length <= 1) {
		            return;
		        }
		        uploader.removeFile(file);
		    });
		  //预览图片函数
		previewImage(files[0], function (imgsrc) {
	        $('#preview').html($( '<img width="380" height="200" src="' + imgsrc + '" name="' + files[0].name + '" />'));
	    });
	    var file_name = files[0].name;
	    $("#path").val(file_name);
	});
	
	//当上传队列中一个文件上传成功后，多个文件上传用UploadComplete
	uploader.bind('FileUploaded',function(uploader,file,responseObject){
	      var data=jQuery.parseJSON(responseObject.response);
	      var imagePath=data.bean.imagePath;
	      $("#path").val(imagePath);
	  	  var formdata=$("#addBannerForm").serialize();
		  var url=contextPath+"/weiweb/banner/insertBanner";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("微网站信息新增成功!",$.scojs_message.TYPE_OK);
				initBannerListInfo();
				$("#myModal").modal('hide');
				$("#addBannerForm input").val("");
				$('#preview').html("");
				$("#descn").val("");
			}
		});
	});
	//绑定文件上传进度事件
	uploader.bind('UploadProgress',function(uploader,file){
		$('#file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
	});
	
	
	//实例化一个plupload上传对象
	var editBannerUploader = new plupload.Uploader({ //实例化一个plupload上传对象
		browse_button : 'edit-path',
		url : contextPath+'/weiweb/banner/doUpload',
	    flash_swf_url : '${req.contextPath}/assets/plupload/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
	    silverlight_xap_url : '${req.contextPath}/assets/plupload/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
	    filters: {
	   	  mime_types : [ //只允许上传图片和zip文件
	   	    { title : "Image files", extensions : "jpg,jpeg,gif,png" }, 
	   	    { title : "Zip files", extensions : "zip" }
	   	  ],
	   	  max_file_size : '10mb', //最大只能上传2M的文件
	   	  prevent_duplicates : true //不允许选取重复文件
	    },
	    multi_selection: true
	});

	editBannerUploader.init(); //初始化

	//绑定文件添加进队列事件
	editBannerUploader.bind('FilesAdded',function(uploader,files){
		  $.each(uploader.files, function (i, file) {
		        if (uploader.files.length <= 1) {
		            return;
		        }
		        uploader.removeFile(file);
		    });
		  //预览图片函数
		previewImage(files[0], function (imgsrc) {
	        $('#edit-preview').html($( '<img width="380" height="200" src="' + imgsrc + '" name="' + files[0].name + '" />'));
	    });
	    var file_name = files[0].name;
	    $("#edit-path").val(file_name);
	});

	//当上传队列中一个文件上传成功后，多个文件上传用UploadComplete
	editBannerUploader.bind('FileUploaded',function(uploader,file,responseObject){
	      var data=jQuery.parseJSON(responseObject.response);
	      var imagePath=data.bean.imagePath;
	      $("#edit-path").val(imagePath);
	  	  var formdata=$("#editBannerForm").serialize();
		  var url=contextPath+"/weiweb/banner/updateBanner";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("微网站信息更新成功!",$.scojs_message.TYPE_OK);
				initBannerListInfo();
				$("#myModal1").modal('hide');
			}
		});
	});

	//绑定文件上传进度事件
	editBannerUploader.bind('UploadProgress',function(uploader,file){
		$('#file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
	});

	//图片上传预览
	function previewImage(file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
	    if (!file || !/image\//.test(file.type)) return; //确保文件是图片
	    if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
	        var fr = new mOxie.FileReader();
	        fr.onload = function () {
	            callback(fr.result);
	            fr.destroy();
	            fr = null;
	        }
	        fr.readAsDataURL(file.getSource());
	    } else {
	        var preloader = new mOxie.Image();
	        preloader.onload = function () {
	            var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
	            callback && callback(imgsrc); //callback传入的参数为预览图片的url
	            preloader.destroy();
	            preloader = null;
	        };
	        preloader.load(file.getSource());
	    }
	}
});



