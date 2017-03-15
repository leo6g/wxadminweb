//列表分页每页显示多少条记录
$(document).ready(function(){
	var global_limit = 10 ;
	
	//初始化信用卡信息发布表列表信息
	initWXCardInfoListInfo();
	
	
	//新增信用卡信息发布表信息时，validate验证，验证通过后调用保存方法 
	$("#addWXCardInfoForm").validate({
        submitHandler:function(form){
        	addWXCardInfo();
        }    
    });
	
	//新增信用卡信息待审核信息
	$("#saveWXCardInfoBtn").click(function(){
		var imagePath=$("#imagePath").val();
		$("#authState").val("1-WAITING");
		if(imagePath==null || imagePath.trim()==""){
			alert("图片链接不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		uploader.start();
	});
	//新增信用卡草稿信息
	$("#saveDraftWXCardInfoBtn").click(function(){
		var imagePath=$("#imagePath").val();
		$("#authState").val("DRAFT");
		if(imagePath==null || imagePath.trim()==""){
			alert("图片链接不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		uploader.start();
	});
	
	//选中信用卡信息发布表信息
	$("#wXCardInfoListTable").delegate("tr","click",function(){
		var name = $(this).find("td:eq(1)").html();
		var type = $(this).find("td:eq(2)").html();
		var cardId = $(this).find("td:eq(3)").html();
		var comments = $(this).find("td:eq(4)").html();
		var state = $(this).find("td:eq(5)").html();
		var authState = $(this).find("td:eq(6)").html();
		var imagePath = $(this).find("td:eq(7)").html();
		var hotFlag = $(this).find("td:eq(8)").html();
		var createUser = $(this).find("td:eq(10)").html();
		var viewState = $(this).find("td:eq(11)").html();
		var rights = $(this).find("td:eq(12)").html();
		rights=rights.replace(new RegExp("&lt;br/&gt;","gm"),"<br/>");
		if (state=="上架") {
			state="onSale";
		}else if(state=="下架"){
			state="offSale";
		}
		if (hotFlag=="是") {
			hotFlag="T";
		}else if(hotFlag=="否"){
			hotFlag="F";
		}
		var url = contextPath + "/biz/cardinfo/getById";
		var params={"cardId":cardId}
		//异步请求信用卡信息发布表列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			$("#oldImagePath").val(data.object.imagePath);
		});
		$("#edit-name").val(name);
		$("#edit-type").val(type);
		$("#edit-cardId").val(cardId);
		$("#edit-comments").val(comments);
		$("#edit-state").val(state);
		$("#edit-authState").val(authState);
		$("#edit-imagePath").val(imagePath);
		$("#edit-hotFlag").val(hotFlag);
		$("#edit-createUser").val(createUser);
		$("#view-authState").val(viewState);
		$("#edit-rights").val(rights);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改信用卡信息发布表信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editWXCardInfoForm").validate({
        submitHandler:function(form){
        	editWXCardInfo();
        }    
    });
	
	//保存编辑的信用卡信息发布表信息
	$("#edit-saveWXCardInfoBtn").click(function(){
		var imagePath=$("#edit-imagePath").val();
		var oldImagePath=$("#oldImagePath").val();
		if(imagePath==null || imagePath.trim()==""){
			$("#edit-imagePath").val(oldImagePath);
			var formdata=$("#editWXCardInfoForm").serialize();
			var url=contextPath+"/biz/cardinfo/updateWXCardInfo";
			Util.ajax.postJson(url, formdata, function(data, flag){
			  if(flag && data.returnCode=="0"){
				  if($("#edit-authState").val() == '1-WAITING'){
						var cardId = $("#edit-cardId").val();
						saveBIZAuthInfo(cardId);
					}else{
						alert("信用卡信息发布表信息更新成功!",$.scojs_message.TYPE_OK);
						initWXCardInfoListInfo();
						$("#myModal1").modal('hide');
					}
			  }
			});
		}else{
			editUploader.start(); 
		}
	});
	//编辑信用卡信息发布表信息
	$("#editWXCardInfoBtn").click(function(){
		var cardId = $("#edit-cardId").val();
		var authState = $("#view-authState").val();
		if(authState != 'DRAFT'){
			alert("只能编辑草稿!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(cardId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		
		$("#edit-imagePath").val("");
		var imagePath = $("#oldImagePath").val();
		$('#edit-preview').html($( '<img src="/wxadminweb/viewImage/viewImage?imgPath='+"card/"+imagePath+'" style="width:380px;height:200px;" alt="正在加载..." />'));
	});
	
	//删除信用卡信息发布表信息
	$("#delWXCardInfoBtn").click(function(){
		var cardId = $("#edit-cardId").val();
		if(cardId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		//logicDelWXCardInfo();//逻辑删除
			delWXCardInfo();
	});
	//详情
	$("#viewCardBtn").click(function(){
		var cardId = $("#edit-cardId").val();
		if(cardId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var authState = $("#view-authState").val();
		var role = $("#role").val();
		C.load(contextPath+'/biz/cardinfo/cardView?cardId='+cardId+'&role='+role+'&authState='+authState);
	});
	
	//查询
	 $("#querySubmit").click(function(){
		initWXCardInfoListInfo();
	 });
	//局部绑定回车事件
	 $("#queryName").bind('keyup',function(event){
	    event=document.all?window.event:event;
	    if((event.keyCode || event.which)==13){
	    	document.getElementById("querySubmit").click();
	    }
	   }); 
	
//初始化信用卡信息发布表列表信息
function initWXCardInfoListInfo(currentPage, limit){
	if(typeof currentPage == "undefined"){
		currentPage = 1;
	}
	if(typeof limit == "undefined"){
		limit = global_limit;
	}
	var url = contextPath + "/biz/cardinfo/getList";
	var params=$("#queryForm").serialize();
	params = params + "&pageNumber="+currentPage+"&limit="+limit;
	//异步请求信用卡信息发布表列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		for (var i = 0; i < data.beans.length; i++) {
			if (data.beans[i].state=="onSale") {
				data.beans[i].state="上架";
			}else if (data.beans[i].state=="offSale") {
				data.beans[i].state="下架";
			}
			
			if (data.beans[i].hotFlag=="T") {
				data.beans[i].hotFlag="是";
			}else if (data.beans[i].hotFlag=="F") {
				data.beans[i].hotFlag="否";
			}
			data.beans[i].viewState = data.beans[i].authState;
			var role = $("#role").val();
			if (data.beans[i].authState=="DRAFT") {
				data.beans[i].authState="草稿";
			}else if (data.beans[i].authState=="1-WAITING") {
				data.beans[i].authState="待信息部审核";
				/*if(role=='business'){
					data.beans[i].authState="已提交";
				}else if(role=='infor'){
					data.beans[i].authState="待审批";
				}*/
			}else if (data.beans[i].authState=="1-REJECTED") {
				data.beans[i].authState="不通过";
			}else if (data.beans[i].authState=="2-WAITING") {
				data.beans[i].authState="待电子银行部审核";
				/*if(role=='infor'){
					data.beans[i].authState="已审批";
				}else if(role=='cardcheck'){
					data.beans[i].authState="待审批";
				}else if(role=='business'){
					data.beans[i].authState="已提交";
				}*/
			}else if (data.beans[i].authState=="2-REJECTED") {
				data.beans[i].authState="不通过";
			}else if (data.beans[i].authState=="COMPLETED") {
				data.beans[i].authState="通过";
			}
			
			data.beans[i].hotFlag="是";
		}
		var source = $("#wXCardInfo-list-template").html();
		var templet = Handlebars.compile(source);
		if(flag && data.returnCode=="0"){
			//渲染列表数据
			var htmlStr = templet(data.beans);
			$("#wXCardInfoListTable").html(htmlStr);
			//清空新增信用卡信息发布表的弹窗信息
			$("#addWXCardInfoForm input").val("");
			$("#descn").val("");
			$('#preview').html("");
			//初始化分页数据(当前页码，总数，回调查询函数)
			initPaginator(currentPage, data.bean.count, initWXCardInfoListInfo);
		}
	});
}


//新增信用卡信息发布表信息
function addWXCardInfo(){
	var url = contextPath + "/biz/cardinfo/insertWXCardInfo";
	var params = $("#addWXCardInfoForm").serialize();
	//异步请求新增信用卡信息发布表信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				alert("信用卡信息发布表新增成功!");
				$("#myModal").modal('hide');
				//重新请求列表数据
				initWXCardInfoListInfo();
				//清空新增信用卡信息发布表的弹窗信息
				$("#addWXCardInfoForm input").val("");
				$("#descn").val("");
			}
		}else{
			if(data.returnCode=="-1"){
				alert("信用卡信息发布表编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
			}else{
				alert(data.returnMessage);
			}
		}
	});
}


//修改信用卡信息发布表信息
function editWXCardInfo(){
	var cardId = $("#edit-cardId").val();
	
	if(cardId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	
	var url = contextPath + "/biz/cardinfo/updateWXCardInfo";
	var params = $("#editWXCardInfoForm").serialize();
	//异步请求修改信用卡信息发布表信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				alert("信用卡信息发布表信息修改成功!");
				//编辑信用卡信息发布表信息清空
				$("#editWXCardInfoForm input").val("");
				$("#edit-descn").val("");
				//重新请求列表数据
				initWXCardInfoListInfo();
				$("#myModal1").modal('hide');
			}
		}else{
			if(data.returnCode=="-1"){
				alert("信用卡信息发布表编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
			}else{
				alert(data.returnMessage);
			}
		}
	});
}
//删除信用卡信息发布表
function delWXCardInfo(){
	var cardId = $("#edit-cardId").val();
	if(cardId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	confirm("确定删除吗？",function(){
		var url = contextPath + "/biz/cardinfo/deleteWXCardInfo";
		var params = $("#editWXCardInfoForm").serialize();
		//异步请求逻辑删除信用卡信息发布表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("信用卡信息发布表删除成功!");
					//编辑信用卡信息发布表信息清空
					$("#editWXCardInfoForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXCardInfoListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	});
}

//逻辑删除信用卡信息发布表
function logicDelWXCardInfo(){
	var cardId = $("#edit-cardId").val();
	if(cardId==""){
		alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
		return false;
	}
	var flag = confirm("确定删除吗？")
	if(flag){
		var url = contextPath + "/biz/cardinfo/logicDeleteWXCardInfo";
		var params = $("#editWXCardInfoForm").serialize();
		//异步请求逻辑删除信用卡信息发布表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("信用卡信息发布表删除成功!");
					//编辑信用卡信息发布表信息清空
					$("#editWXCardInfoForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initWXCardInfoListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	}
}

//实例化一个plupload上传对象
var uploader = new plupload.Uploader({ //实例化一个plupload上传对象
	browse_button : 'imagePath',
	url : contextPath+'/biz/cardinfo/doUpload',
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
    $("#imagePath").val(file_name);
});

//当上传队列中一个文件上传成功后，多个文件上传用UploadComplete
uploader.bind('FileUploaded',function(uploader,file,responseObject){
      var data=jQuery.parseJSON(responseObject.response);
      var imagePath=data.bean.imagePath;
      $("#imagePath").val(imagePath);
  	  var formdata=$("#addWXCardInfoForm").serialize();
	  var url=contextPath+"/biz/cardinfo/insertWXCardInfo";
	  Util.ajax.postJson(url, formdata, function(data, flag){
		if(flag && data.returnCode=="0"){
			if($("#authState").val() == '1-WAITING'){
				var cardId = data.bean.cardId;
				saveBIZAuthInfo(cardId);
			}else{
				alert("信用卡信息发布表信息添加成功!",$.scojs_message.TYPE_OK);
				initWXCardInfoListInfo();
				$("#myModal").modal('hide');
			}
		}
	});
});


//绑定文件上传进度事件
uploader.bind('UploadProgress',function(uploader,file){
	$('#file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
});

//实例化一个plupload上传对象
var editUploader = new plupload.Uploader({ //实例化一个plupload上传对象
	browse_button : 'edit-imagePath',
	url : contextPath+'/biz/cardinfo/doUpload',
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


editUploader.init(); //初始化

//绑定文件添加进队列事件
editUploader.bind('FilesAdded',function(uploader,files){
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
    $("#edit-imagePath").val(file_name);
});
//当上传队列中一个文件上传成功后，多个文件上传用UploadComplete
editUploader.bind('FileUploaded',function(uploader,file,responseObject){
      var data=jQuery.parseJSON(responseObject.response);
      var imagePath=data.bean.imagePath;
      $("#edit-imagePath").val(imagePath);
  	  var formdata=$("#editWXCardInfoForm").serialize();
	  var url=contextPath+"/biz/cardinfo/updateWXCardInfo";
	  Util.ajax.postJson(url, formdata, function(data, flag){
		if(flag && data.returnCode=="0"){
			if($("#edit-authState").val() == '1-WAITING'){
				var cardId = $("#edit-cardId").val();
				saveBIZAuthInfo(cardId);
			}else{
				alert("信用卡信息发布表信息更新成功!",$.scojs_message.TYPE_OK);
				initWXCardInfoListInfo();
				$("#myModal1").modal('hide');
			}
		}
	});
});

//绑定文件上传进度事件
editUploader.bind('UploadProgress',function(uploader,file){
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
};
	//信息保存至审核表
	function saveBIZAuthInfo(cardId){
		var url=contextPath+"/biz/authinfo/insertBIZAuthInfo";
		var formdata = "type=CARD&prodId="+cardId+"&operFlag=WAITING";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("信用卡信息发布表信息成功!",$.scojs_message.TYPE_OK);
				initWXCardInfoListInfo();
				$("#myModal1").modal('hide');
				$("#myModal").modal('hide');
			}
		});
	};
/////////////////////////////////
}); 

//按钮隐藏与显示
$(function(){
	var role = $("#role").val();
	 if(role == 'business'){
		 $("#addWXCardInfoBtn").css('display','');
		 $("#editWXCardInfoBtn").css('display','');
		 $("#viewCardBtn").css('display','');
		 $("#delWXCardInfoBtn").css('display','');
	 }else if(role == 'infor' || role == 'cardcheck'){
		 $("#viewCardBtn").css('display','');
		 $("#delWXCardInfoBtn").css('display','');
	 }
	 
})