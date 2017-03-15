var mediaId;
var orgHtml;//原始html
var type = "text";//提交类型
var articleHtml = '<div class="jiangbangitlist"><div class="jiangbangititem"><div class="jiangbangthumbnails"><img src="$imgUrl$" style="height:150px"/><a class="jiangbangauthor">$digest$</a></div><hr /><a onclick="deleteHtml();return false;" style="float:right;color: #337ab7;cursor: pointer;">删除<a/></div></div>';
var videoHtml = '<div class="zl_each" style="margin-top:10px;width:300px;height:250px"><p>$name$</p><p><video src="$videoUrl$" controls="controls" class="showVideo" style="width:270px;height:130px;margin-top:10px;">Your browser does not support the video element.</video></p><a onclick="deleteHtml();return false;" style="float:right;color: #337ab7;cursor: pointer;">删除<a/></div>';
var audioHtml = '<div class="zl_each" style="margin-top:10px;width:300px""><p>$name$</p><p><audio src="$audioUrl$" controls="controls" class="showVoice" style="width:270px;height:130px;margin-top:10px;">Your browser does not support the audio element.</audio></p><a onclick="deleteHtml();return false;" style="float:right;color: #337ab7;cursor: pointer;">删除<a/></div>';
var imgHtml = '<figure><img src="$imgUrl$" alt="" style="width:160px;height:160px;"/><div style="width:300px;height: 35px;line-height: 35px;padding-left: 10px;"><span style="margin-left: 1px;width: 147px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;height: 26px;">$name$</span><a onclick="deleteHtml();return false;" style="float:right;color: #337ab7;cursor: pointer;">删除<a/></div></figure>';
$(function () {
		var spans=$(".gaonew-left span");
		spans.click(function(){
			var x=$(this).index();
			$("#line").animate({left:x*100+'px'});
		});
		
		//新建消息和已发送切换
		$(".gaonew-left .fasong").click(function(){
			$(".gaoruxue-model").css({"display":"none"});
			$(".massTexting").css({"display":"block"});
			
			
		});
		$(".gaonew-left .xinjian").click(function(){

			$(".massTexting").css({"display":"none"});;	
			$(".gaoruxue-model").css({"display":"block"});
			
		});
		$(".gadd-left input").change(function(){

				$(".gadd-left i").html(	$(".gadd-left input").val());
		});
		$(".gadd-right input").change(function(){
				$(".gadd-right i").html(	$(".gadd-right input").val());
		});
	$("#textA").click(function(){
		$(this).parent().siblings().css("color","");
		$(this).parent().css("color","#1cccc5");
		$("#textDiv").show();
		$("#otherDiv").hide();
		$("#otherDiv .navContant").html(orgHtml);
		mediaId = "";
		type = "text";
	});
	$("#imgA,#voiceA,#videoA,#articleA").click(function(){
		$(this).parent().siblings().css("color","");
		$(this).parent().css("color","#1cccc5");
		$("#textDiv").hide();
		$("#otherDiv").show();
		$("#showModal").attr("data-target",$(this).attr("data-target"));
		$("#uploadMsg").text($(this).attr("msg")).attr("onclick",$(this).attr("fun"));
	});
	//视频按钮点击事件
	$('#myModalVideo').on('shown.bs.modal', function (e) {
		console.log(e)
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
		initArticle('news');
	});
	//图文隐藏模态框处理
	$('#myModalArticle').on('hidden.bs.modal', function (e) {
		var selectedDiv = $(".jiangbangitlist div.selected");
		if(selectedDiv.size() > 0) {
			mediaId = selectedDiv.attr("mediaId");
			type = "news";
			var imgurl = selectedDiv.attr("img");
			var digest = selectedDiv.attr("digest");
			var html = articleHtml.replace("$imgUrl$",imgurl).replace("$digest$",digest);
			orgHtml = $("#otherDiv .navContant").html();
			$("#otherDiv .navContant").html(html);
		}
	})
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
			type = "voice";
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

//删除html 把当前data-target属性再回传
function deleteHtml() {
	$("#otherDiv .navContant").html(orgHtml);
	type="text";
}
//选择发送人群
function selectType(){
	if($("select :selected").val()==1){
		var url = contextPath + "/wx/usergroup/getAll";
		Util.ajax.postJson(url, {}, function(data, flag){
			var source = $("#tagSelect-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.object);
				$("#tagSelect").html(htmlStr);
				$("#tagSelect").show();
			}
		});
	}else{
		$("#tagSelect").hide();
	}
}
function sendMessage(){
	if(type=="text"&&$("#content").text().trim()==""){
		alert("群发内容不能为空",$.scojs_message.TYPE_ERROR);
	}
	var url = contextPath + "/weixin/batchSend";
	parms={};
	parms.type=type;
	parms.materialId=mediaId;
	parms.content=$("#content").text();
	if($("select :selected").val()==1){
		parms.isToAll=1;
		parms.tagId=$("select :selected :eq(1)").val();
	}else{
		parms.isToAll=0;
	}
	Util.ajax.postJson(url, parms, function(data, flag){
		if(data.errcode=="0"){
			$("#content").text("");
			deleteHtml();
			alert("发送成功，请耐心等待！");
		}else{
			alert("发送失败！",$.scojs_message.TYPE_ERROR);
		}
	});
}