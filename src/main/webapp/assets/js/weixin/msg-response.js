var mediaId;
var orgHtml;//原始html
var type = "text";//提交类型
var articleHtml = '<div style="display:flex;"><div class="jiangbangititem"><div class="jiangbangthumbnails"><img src="$imgUrl$" style="height:150px"/><a class="jiangbangauthor">$digest$</a></div><hr /><a onclick="deleteHtml();return false;" style="float:right;color: #337ab7;cursor: pointer;">删除<a/></div></div>';
var videoHtml = '<div class="zl_each" style="margin-top:10px;width:300px"><p>$name$</p><p><video src="$videoUrl$" controls="controls" class="showVideo" style="width:270px;height:130px;margin-top:10px;">Your browser does not support the video element.</video></p><a onclick="deleteHtml();return false;" style="float:right;color: #337ab7;cursor: pointer;">删除<a/></div>';
var audioHtml = '<div class="zl_each" style="margin-top:10px;width:300px""><p>$name$</p><p><audio src="$audioUrl$" controls="controls" class="showVoice" style="width:270px;height:130px;margin-top:10px;">Your browser does not support the audio element.</audio></p><a onclick="deleteHtml();return false;" style="float:right;color: #337ab7;cursor: pointer;">删除<a/></div>';
var imgHtml = '<figure><img src="$imgUrl$" alt="" style="width:160px;height:160px;"/><div style="width:300px;height: 35px;line-height: 35px;padding-left: 10px;"><span style="margin-left: 1px;width: 147px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;height: 26px;">$name$</span><a onclick="deleteHtml();return false;" style="float:right;color: #337ab7;cursor: pointer;">删除<a/></div></figure>';
$(function () {
	initMsgResponse();
	$("#textA").click(function(){
		$("#textDiv").show();
		$("#otherDiv").hide();
		$("#otherDiv .navContant").html(orgHtml);//点击文字清除otherDiv的html
		mediaId = "";
		type = "text";
	});
	$("#imgA,#voiceA,#videoA,#articleA").click(function(){
		$("#textDiv").hide();
		$("#otherDiv").show();
		$("#showModal").attr("data-target",$(this).attr("data-target"));
		$("#uploadMsg").text($(this).attr("msg")).attr("onclick",$(this).attr("fun"));
	});
	//视频按钮点击事件
	$('#myModalVideo').on('shown.bs.modal', function (e) {
		initAudio('video');
	});
	//音频按钮点击事件
	$('#myModalVoice').on('shown.bs.modal', function (e) {
		initVoice('voice');
	});
	//图片按钮点击事件
	$('#myModalImage').on('shown.bs.modal', function (e) {
		initImage('image');
	});
	//图文按钮点击事件
	$('#myModalArticle').on('shown.bs.modal', function (e) {
		initArticle('article');
	});
	
	//图文模态框隐藏事件
	$('#myModalArticle').on('hidden.bs.modal', function (e) {
		var selectedDiv = $(".jiangbangitlist div.selected");
		if(selectedDiv.size() > 0) {
			mediaId = selectedDiv.attr("mediaId");
			type = "article";
			var imgurl = selectedDiv.attr("img");
			var digest = selectedDiv.attr("digest");
			var html = articleHtml.replace("$imgUrl$",imgurl).replace("$digest$",digest);
			orgHtml = $("#otherDiv .navContant").html();//模态框隐藏后先备份之前的html代码，用于删除后替换
			$("#otherDiv .navContant").html(html);
		}
	});
	
	//视频模态框隐藏
	$('#myModalVideo').on('hidden.bs.modal', function (e) {
		var selectedDiv = $("#videoTable div.selected");
		if(selectedDiv.size() > 0) {
			mediaId = selectedDiv.attr("mediaId");
			type = "video";
			var videoUrl = selectedDiv.attr("vUrl");
			var name = selectedDiv.attr("name");
			var html = videoHtml.replace("$videoUrl$",videoUrl).replace("$name$",name);
			orgHtml = $("#otherDiv .navContant").html();//模态框隐藏后先备份之前的html代码，用于删除后替换
			$("#otherDiv .navContant").html(html);
		}
	});
	
	//音频模态框隐藏
	$('#myModalVoice').on('hidden.bs.modal', function (e) {
		var selectedDiv = $("#voiceTable div.selected");
		if(selectedDiv.size() > 0) {
			mediaId = selectedDiv.attr("mediaId");
			type = "audio";
			var audioUrl = selectedDiv.attr("aUrl");
			var name = selectedDiv.attr("name");
			var html = audioHtml.replace("$audioUrl$",audioUrl).replace("$name$",name);
			orgHtml = $("#otherDiv .navContant").html();//模态框隐藏后先备份之前的html代码，用于删除后替换
			$("#otherDiv .navContant").html(html);
		}
	});
	
	//图片模态框隐藏
	$('#myModalImage').on('hidden.bs.modal', function (e) {
		var checkedRadio = $("input[name=imgcheck]:checked");
		if(checkedRadio.size() > 0) {
			mediaId = checkedRadio.attr("mediaId");
			type = "image";
			var imgUrl = checkedRadio.attr("imgUrl");
			var name = checkedRadio.attr("imgName");
			var html = imgHtml.replace("$imgUrl$",imgUrl).replace("$name$",name);
			orgHtml = $("#otherDiv .navContant").html();//模态框隐藏后先备份之前的html代码，用于删除后替换
			$("#otherDiv .navContant").html(html);
		}
	});
	
	//保存按钮
	$("#saveBtn").click(function(){
		var txtMsg = "";
		if(type == "text") {
			txtMsg = $("#textDiv").text().trim();
			if(txtMsg == null || txtMsg == "") {
				alert("请添加文字或素材",$.scojs_message.TYPE_ERROR);
				return false;
			}
		} else {
			if(mediaId == null || mediaId == "") {
				alert("请添加文字或素材",$.scojs_message.TYPE_ERROR);
				return false;
			}
		}
		var url = contextPath + "/wx/Resp/saveNewsReply";
		var params = {
			msgType : type,
			txtMsg : txtMsg,
			mediaId : mediaId,
		};
		Util.ajax.postJson(url,params,function(data){
			if(data.returnCode == 0) {
				alert("添加成功");
				C.load(contextPath + '/wx/Resp/newsRespSet');
			} else {
				alert("添加失败",$.scojs_message.TYPE_ERROR);
			}
		});
	});
	
	$("#deleteBtn").click(function(){
		if(info == null){
			$("#deleteBtn").attr("disabled","disabled");
			return;
		}
		confirm("确定删除吗?",function(){
		var url = contextPath + "/wx/Resp/deleteNews";
		Util.ajax.postJson(url,{},function(data){
			if(data.returnCode == 0) {
				alert("删除成功");
				C.load(contextPath + '/wx/Resp/newsRespSet');
			} else {
				alert("删除失败",$.scojs_message.TYPE_ERROR);
			}
		});
		},function(){return false;});
	});
});

//---------------回复图片 开始
function initImage(type){
	//设置回复类型为图片
	initimageInfo();
	queryImgGroup();
}

//-------------回复音频 开始
function initVoice(type){
	//设置回复类型为图片
	initvoiceInfo();
}

//-------------回复音频 结束

//---------------视频回复开始
function initAudio(type){
	//设置回复类型为图片
	initvideoInfo();
}

//---------------视频回复结束

//---------------图文回复开始
function initArticle(type){
	//初始化微信文章内容列表信息
	queryArticleList();
}

//---------------图文回复结束

//选中加样式 
function selected(obj){
	$(obj).addClass("selected");
	$(obj).siblings().removeClass("selected");
}

//删除html
function deleteHtml() {
	//type = "text";
	mediaId = "";
	$("#otherDiv .navContant").html(orgHtml);
}

function initMsgResponse() {
	if(info != null) {
		console.log(info)
		type = info.msgType;//获取类型
		mediaId = info.mediaId;
		if(type=="text") {
			$("#contentDiv").html(info.txtMsg);
		} else if(type=="image") {
			getImgInfo(mediaId);
		} else if(type=="audio") {
			getAudioInfo(mediaId);
		} else if(type=="video") {
			getVideoInfo(mediaId);
		} else {
			getArticlesInfo(mediaId);
		}
	}
}

//获取图片信息
//mid mediaId
function getImgInfo(mid){
	var url = contextPath + "/wx/image/getList";
	var params = {
		mediaId : mid,
		pageNumber : 1,
		limit : 1
	};
	Util.ajax.postJson(url, params, function(data){
		if(data.returnCode == 0) {
			data = data.beans[0];//根据media获得唯一的图片数据
			$("#imgA").click();//第一次点击把imgA的参数放在虚线div里
			orgHtml = $("#otherDiv .navContant").html();//模态框隐藏后先备份之前的html代码，用于删除后替换
			var html = imgHtml.replace("$imgUrl$",data.localUrl).replace("$name$",data.name);
			$("#otherDiv .navContant").html(html);//替换html
			$("#imgA").click();//第二次点击跳到图片页面
		}
	});
}

function getAudioInfo(mid){
	var url = contextPath + "/wx/voice/getList";
	var params = {
		mediaId : mid,
		pageNumber : 1,
		limit : 1
	};
	Util.ajax.postJson(url, params, function(data){
		if(data.returnCode == 0) {
			data = data.beans[0];//根据media获得唯一的图片数据
			$("#voiceA").click();//第一次点击把imgA的参数放在虚线div里
			orgHtml = $("#otherDiv .navContant").html();//模态框隐藏后先备份之前的html代码，用于删除后替换
			var html = audioHtml.replace("$audioUrl$",data.localUrl).replace("$name$",data.name);
			$("#otherDiv .navContant").html(html);//替换html
			$("#voiceA").click();//第二次点击跳到图片页面
		}
	});
}

function getVideoInfo(mid){
	var url = contextPath + "/wx/video/getList";
	var params = {
		mediaId : mid,
		pageNumber : 1,
		limit : 1
	};
	Util.ajax.postJson(url, params, function(data){
		if(data.returnCode == 0) {
			data = data.beans[0];//根据media获得唯一的图片数据
			$("#videoA").click();//第一次点击把imgA的参数放在虚线div里
			orgHtml = $("#otherDiv .navContant").html();//模态框隐藏后先备份之前的html代码，用于删除后替换
			var html = videoHtml.replace("$videoUrl$",data.localUrl).replace("$name$",data.name);
			$("#otherDiv .navContant").html(html);//替换html
			$("#videoA").click();//第二次点击跳到图片页面
		}
	});
}

function getArticlesInfo(mid){
	var url = contextPath + "/wx/artmtl/getList";
	var params = {
		mediaId : mid,
		pageNumber : 1,
		limit : 1
	};
	Util.ajax.postJson(url, params, function(data){
		if(data.returnCode == 0) {
			console.log(data);
			data = data.beans[0];//根据media获得唯一的图片数据
			var article = data.articles[0];
			var imgUrl = "/wxadminweb/viewImage/viewImage?imgPath=material/" + article.localUrl;
			$("#articleA").click();//第一次点击把imgA的参数放在虚线div里
			orgHtml = $("#otherDiv .navContant").html();//模态框隐藏后先备份之前的html代码，用于删除后替换
			var html = articleHtml.replace("$imgUrl$",imgUrl).replace("$digest$",article.title);
			$("#otherDiv .navContant").html(html);//替换html
			$("#articleA").click();//第二次点击跳到图片页面
		}
	});
}