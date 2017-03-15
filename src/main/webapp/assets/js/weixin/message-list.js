var picIcon = $("#picIcon").attr("src");//上传默认图片
//列表分页每页显示多少条记录
$(document).ready(function(){
	var global_limit = 10 ;
	
	//初始化微信文本模版列表信息
	initMessageListInfo();
	//初始化新增模态框上传控件
	uploadImage("uploadIcon","imagePath","picIcon","/wx/message/uploadImage");
	//初始化编辑模态框上传控件
	uploadImage("uploadIcon2","imagePath2","picIcon2","/wx/message/uploadImage");	
	
	//------------------------新增----------------------------
	//新增微信文本模版信息时，validate验证，验证通过后调用保存方法 
	$("#addMessageForm").validate({
        submitHandler:function(form){
        	if($("#imagePath").val() == "") {
        		alert("请上传图片");
        		return false;
        	}
        	addMessage();
        }    
    });
	
	//新增微信文本模版信息
	$("#saveMessageBtn").click(function(){
		var form = $("#addMessageForm");
		form.submit();
	});
	
	
	//------------------------新增----------------------------
	
	//保存选中行id
	$("#wXArticleListTable").delegate("tr","click",function(){
		$("#selectId").val($(this).find("td :eq(1)").text());
		$(this).addClass("tr-color").siblings().removeClass("tr-color");
		return false;
	});
	
	//不选中行不显示编辑模态框
	$("#editWXArticleBtn").click(function(){
		var selectId = $("#selectId").val();
		if(selectId == ""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//------------------------编辑----------------------------
	//编辑模态框数据初始化
	$("#myModal1").on("show.bs.modal", function () {
		var selectId = $("#selectId").val();
		var url = contextPath + "/wx/message/edit";
		var params = {
			id : selectId
		};
		Util.ajax.postJson(url, params, function(data, flag){
			if(data.returnCode == "0") {
				var data = data.object;
				$("#title").val(data.title);
				$("#digest").val(data.digest);
				$("#picIcon2").attr("src",data.coverPic);
				$("#imagePath2").val(data.coverPic);
				$("#url").val(data.url);
				$("#sortIndex").val(data.sortIndex);
			}
		});
	});
	
	//修改，jqvalidate验证，验证通过后调用保存方法 
	$("#editMessageForm").validate({
        submitHandler:function(form){
        	if($("#imagePath2").val() == "") {
        		alert("请上传图片");
        		return false;
        	}
        	editMessage();
        }    
    });
	
	//保存编辑的微信文本模版信息
	$("#editMessageBtn").click(function(){
		var form = $("#editMessageForm");
		form.submit();
	});
	
	//------------------------编辑----------------------------
	
	
	
	//预览
	$("#myModal2").on("show.bs.modal", function () {
		var url = contextPath + "/wx/message/preview";
		Util.ajax.postJson(url, {}, function(data, flag){
			var source = $("#preview-template").html();
			var templet = Handlebars.compile(source);
			if(data.returnCode == "0") {
				var htmlStr = templet(data.bean);
				$("#preview").html(htmlStr);
			}
		});
	});
	
	//发送
	$("#sendWXArticleBtn").click(function(){
		var url = contextPath + "/wx/message/sendAll";
		confirm("确定群发消息吗？",function(){
			//异步请求
			Util.ajax.postJson(url, {}, function(data, flag){
				if(flag && data.returnCode=="0"){
					alert(data.returnMessage);
				} else {
					alert("发送失败",$.scojs_message.TYPE_ERROR);
				}
			});	
		},function(){})
	});
	
});

//新增消息
function addMessage(){
	var url = contextPath + "/wx/message/insertMessage";
	var params = $("#addMessageForm").serialize();
	//异步请求新增微信文本模版信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				alert("图文消息新增成功!");
				$("#myModal").modal('hide');
				//重新请求列表数据
				initMessageListInfo();
				//清空信息
				$("#addMessageForm input").val("");
				$("#imagePath").val("");
				$("#picIcon").attr("src",picIcon);
			}
		}else{
			alert(data.returnMessage);
		}
	});
}

//修改微信文本模版信息
function editMessage(){
	var url = contextPath + "/wx/message/updateMessage";
	var params = $("#editMessageForm").serialize();
	params += "&id=" + $("#selectId").val();
	//异步请求修改微信文本模版信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag){
			if(data.returnCode=="0"){
				alert("图文消息修改成功!");
				$("#myModal1").modal('hide');
				//编辑微信文本模版信息清空
				$("#editMessageForm input").val("");
				$("#imagePath2").val("");
				$("#picIcon2").attr("src",picIcon);
				//重新请求列表数据
				initMessageListInfo();
			}
		}else{
			alert(data.returnMessage);
		}
	});
}

//------------------------删除-----------
//删除消息
function delMessage(id){
	confirm("确定删除吗？",function(){
		var url = contextPath + "/wx/message/deleteMessage";
		//异步请求逻辑删除微信文本模版信息
		Util.ajax.postJson(url, {id : id}, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert(data.returnMessage);
					//重新请求列表数据
					initMessageListInfo();
				}
			}else{
				alert(data.returnMessage);
			}
		});
	});
}

//初始化
function initMessageListInfo(currentPage, limit){
	if(typeof currentPage == "undefined"){
		currentPage = 1;
	}
	if(typeof limit == "undefined"){
		limit = global_limit;
	}
	var url = contextPath + "/wx/message/getList";
	var params = "pageNumber="+currentPage+"&limit="+limit;
	//异步请求
	Util.ajax.postJson(url, params, function(data, flag){
		var source = $("#wXArticle-list-template").html();
		var templet = Handlebars.compile(source);
		if(flag && data.returnCode=="0"){
			//渲染列表数据
			var htmlStr = templet(data.beans);
			$("#wXArticleListTable").html(htmlStr);
			$("#selectId").val("");
			//初始化分页数据(当前页码，总数，回调查询函数)
			initPaginator(currentPage, data.bean.count, initMessageListInfo);
		}
	});
}

//上传图片
function uploadImage(btn, img, pic, uploadUrl) {
	var setting = {
		flash_url : contextPath+"/assets/swfupload/1.0.0/2.5/swfupload.swf",
		upload_url : contextPath+uploadUrl,
		post_params : {},
		use_query_string : true,
		file_size_limit : "2 MB",
		file_types : "*.jpg;*.png;*.gif;*.jpeg;*.bmp",
		file_types_description : "All Files",
		file_upload_limit : 0,
		file_queue_limit : 0,
		debug : false,
		// Button settings
		button_width : '60',
		button_height : "20",
		button_text : "<span class=\"fontStyle\">选择图片</span>",
		button_text_style : ".fontStyle{font-size: 12px;color:#A9A9A9;}", 
		button_placeholder_id : btn,
		button_text_left_padding : 0,
		button_text_top_padding : 6,
		button_cursor : SWFUpload.CURSOR.HAND,
		file_queued_handler : function(file) {
		},
		file_dialog_complete_handler : function(numFilesSelected,
				numFilesQueued) {
			this.startUpload();
		},
		upload_start_handler : function(file) {
			//console.log(file)
			return true;
		},
		upload_error_handler : function(file, errorCode, message) {
			try {
			} catch (ex) {
				this.debug(ex);
			}
		},
		upload_success_handler : function uploadSuccess(file,
				serverData) {
			var data = JSON.parse(serverData);
			console.log(data);
			if(data.bean.errorMessage !="" && data.bean.errorMessage !=null){
				alert(data.bean.errorMessage);
			}else if(data.returnCode=="0"){
				var url = data.bean.realPath;
				$("#" + img).val(url);
				$("#" + pic).attr("src", url);
				$("#returnMessage").text(data.returnMessage);
			}else{
				alert("系统异常");
			}

		}
	};
	return new SWFUpload(setting);
}