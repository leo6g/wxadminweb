var responseType= "";//回复类型
var endKey="";//添加时需存入库的关键字
var keyName;//展示的关键字/文字回复的位置
var respLoc;//获取当前需要填充回复语的位置
var saveLoc;////获取当前规则保存按钮所在规则的位置
var mediaId="";
var articleHtml = '<div style="display:flex;"><div class="jiangbangititem"><div class="jiangbangthumbnails"><img src="$imgUrl$" style="height:150px"/><a class="jiangbangauthor">$digest$</a></div><hr /></div></div>';
var videoHtml = '<div class="zl_each" style="margin-top:10px;width:300px"><p>$name$</p><p><video src="$videoUrl$" controls="controls" class="showVideo" style="width:270px;height:130px;margin-top:10px;">Your browser does not support the video element.</video></p></div>';
var audioHtml = '<div class="zl_each" style="margin-top:10px;width:300px""><p>$name$</p><p><audio src="$audioUrl$" controls="controls" class="showVoice" style="width:270px;height:130px;margin-top:10px;">Your browser does not support the audio element.</audio></p></div>';
var imgHtml = '<figure><img src="$imgUrl$" alt="" style="width:160px;height:160px;"/><div style="width:300px;height: 35px;line-height: 35px;padding-left: 10px;"><span style="margin-left: 1px;width: 147px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;height: 26px;">$name$</span></div></figure>';
$(document).ready(function(){
	
	var addKey=null;//当前点击的添加关键字按钮的位置
	var tmpVoice=null;
	//关键字
	var keywords="";//添加关键字时临时保存的
	//列表分页每页显示多少条记录
	var global_limit =10 ;
		//初始化微信文章内容列表信息
		initWXKeywordRespListInfo();
		
		//查询按钮
		$("#querySubmit").click(function(){
			initWXKeywordRespListInfo();
		});
		
		//点击重置按钮
		$("#resetBtn").click(function(){
			$("#queryKey").val("");
			initWXKeywordRespListInfo();
		});
		//列表折叠与显示
		$("table").on('click','.ruleDiv',function(){
			$(this).next().collapse('toggle');
		});
		
        //注册一个Handlebars Helper,用来将索引+1，因为默认是从0开始的
        Handlebars.registerHelper("addOne",function(index,options){
           return parseInt(index)+1;
        });
	// 添加多个关键字时 ，回车事件
		$("#addKeywords").bind('keyup',function(event) {
			event = document.all ? window.event
					: event;
			if ((event.keyCode || event.which) == 13) {
				var keyword = $.trim($("#addKeywords").val());
				if (keyword == "") {
					alert("您尚未输入关键字！");
					return false;
				} else if (keyword.length > 80) {
					alert("当前关键字已经超过80个字符，请慎重！");
					return false;
				}else{
				$('#addKeywords').val("");
				$("#keywordsxianshi")
						.append(
								"<div style='margin-right:auto;display:inline;'><em><input type='text' disabled  value="+ keyword+
								"></em><button type='button' class='btn-link btn-xs' onclick='delTmpkey();'>X</button><hr></div>");
				keywords = keywords + ","+ keyword;
				}}
		});
		//关键字保存按钮
		$(".container").on("click","#saveKeyBtn",function(){
			
			var keyword=$.trim($("#addKeywords").val());
			
			if (keywords=="" & keyword == "") {
				alert("您尚未输入关键字！");
				return false;
			} else if (keyword.length > 80) {
				alert("当前关键字已经超过80个字符，请慎重！");
				return false;
			}else{
			
			if(keyword !=""){
				/*var keyListtmp=keywords.split(",");
				for(i=1;i<keyListtmp.length;i++){
					if(keyListtmp[i]==keyword){
						alert("您输入了重复关键字");
						return false;
					}
				}*/
				keywords=keywords+","+keyword;
			}
			var keyList=keywords.split(",");
			for(i=1;i<keyList.length;i++){
				addKey.parent().next().append("<div class='key'>" +
						"<span class='anniu glyphicon glyphicon-trash' onclick='delKey();'></span>" +
						"<span class='anniu addKeyEdit  glyphicon glyphicon-pencil' data-toggle='modal' data-target='#modalEditKey'></span>" +
						"<span class='addKeyName'>"+keyList[i]+"</span>" +
						"<a class='pipei' value=0>未全匹配<a/></div>");
			}
			keywords="";
			$("#keywordsxianshi").html("");
			}
			//编辑关键字
			$(".addKeyEdit").click(function(){
				keyName=$(this).next(".addKeyName");
				editKey(keyName);
			});
			
			$("#modalAddKey").modal("hide");
			$("#addKeywords").val("");
		})
		

		//删除规则
		$("table").on('click','.delRule',function(){
			var id=$(this).parent().attr("ruleid");
			confirm("确定删除此条规则吗？",function(){
				var url = contextPath + "/wx/keywordResp/delKeyRespRule";
				var params = "ruleId="+id;
				Util.ajax.postJson(url, params, function(data, flag){
					if(flag){
						if(data.returnCode=="0"){
								alert("规则删除成功!");
								//重新请求列表数据
								initWXKeywordRespListInfo();
								//清空
								mediaId="";
								tmpVoice=null;
								//关键字
								keywords="";
								//文本回复内容
								txtResp=null;
								//回复类型
								responseType="";
						}
					}else{
							alert(data.returnMessage);
					}
				});
			},function(){return false;});
			
		});
		
			//初始化
			function initWXKeywordRespListInfo(currentPage, limit){
				if(typeof currentPage == "undefined"){
					currentPage = 1;
				}
				if(typeof limit == "undefined"){
					limit = global_limit;
				}
				var url = contextPath + "/wx/keywordResp/getList";
				var params = $("#queryForm").serialize();
				params = params + "&pageNumber="+currentPage+"&limit="+limit;
				//异步请求微信文章内容列表数据
				Util.ajax.postJson(url, params, function(data, flag){
					var source = $("#rule-list-template").html();
					var templet = Handlebars.compile(source);
					if(flag && data.returnCode=="0"){
						
						var articles = [];
						$(data.beans).each(function(i, e) {
							if(data.beans[i].responseType=="article"){
							var normalArticles = [];
							if(e.articleContent){
								$(e.articleContent).each(function(index, ele) {
									if (index === 0) {
										//假设一个图文有四篇文章，这里是第一篇，也就是缩略图比较大的那个
										e.bigArticle = ele;
									} else {
										normalArticles.push(ele);
									}
								});
							}
							e.normalArticles = normalArticles;
							data.beans[i] = e;
							}
						});
						//渲染列表数据
						var htmlStr = templet(data.beans);
						$("#wXKeyRespListTable").html(htmlStr);
						//初始化分页数据(当前页码，总数，回调查询函数)
						if(data.bean.count>0){
						thisInitPaginator(data.bean.totalPages,currentPage, data.bean.count, initWXKeywordRespListInfo);
						}else{
							$("#thistotalNum").html("");
							$("#thispagination").html("");
							$("#wXKeyRespListTable").html("暂无数据");
						}
						//关键字是否全匹配
						$(".keywordList").on('click',".pipei",function(){
							if($(this).text()=="已全匹配"){
								$(this).text("未全匹配");
								$(this).attr("value","0");
							}else{
								$(this).text("已全匹配");
								$(this).attr("value","1");
							}
						})
						//获取当前点击的“添加关键字”的位置
						$(".container").on("click",".addKey",function(){
							addKey=$(this);
						});
						//编辑关键字（列表中为异步加载）
						$(".addKeyEdit").click(function(){
							keyName=$(this).next(".addKeyName");
							editKey(keyName);
						});
						//编辑文本回复
						$(".TxtRespedit").click(function(){
							$("#modalEditKey").find("h4").text("修改回复文字");
							keyName=$(this).prev().prev();
							editKey(keyName);
						});
						
						
						
						$(".saveRule").unbind('click').click(function(){
							var ruleName =saveLoc.children("div.ruleHead").find(".ruleName").val();
							if(ruleName==""){
								alert("规则名不能为空!");
								return false;
							}
							if(ruleName.length>60){
								alert("规则名最多60个字!");
								return false;
							}
							saveLoc.children("div.ruleHead").find(".key").each(function(){
								var keyInfo=$(this).find(".addKeyName").text()+"_"+$(this).find(".pipei").attr("value");
								endKey=endKey+","+keyInfo;
							});
							endKey=endKey.substr(1);
							if(endKey==""){
								alert("请至少输入一个关键词!");
								return false;
							}
							
							var txtResp=saveLoc.children("div.resp").children().children("span:first").text();
							if(txtResp=="" & mediaId==null){
								alert("请至少输入一个回复!");
								endKey="";
								return false;
							}
								
							var id="";
							id=saveLoc.attr("ruleid")?saveLoc.attr("ruleid"):id;
							var url = contextPath + "/wx/keywordResp/saveRule";
							var params = "ruleName="+ruleName +"&words="+endKey+"&txtContent="+
							txtResp+"&responseType="+responseType+"&materialId="+mediaId+"&ruleId="+id;//参数名是materialId，其实是mediaId
							
							Util.ajax.postJson(url, params, function(data, flag){
								if(flag){
									
									if(data.returnCode=="0"){
											alert("关键字回复规则保存成功!");
											$("#addRule").collapse("hide");
											//重新请求列表数据
											initWXKeywordRespListInfo();
											//清空新增窗口
											$("#addkeyList").html("");
											$("#addRule").find("input").val("");
											if(respLoc){respLoc.html("");}
											//清空
											tmpVoice=null;
											//关键字
											keywords="";
											endKey="";
											mediaId="";
											//文本回复内容
											txtResp=null;
											//回复类型
											responseType="";
									}
								}else{
										alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
								}
							});
						});
						
						
					}
				});
			}
			
			//保存文本回复
			$("#saveTxtResp").click(function(){
				txtRespTmp=$.trim($("#txtResp").val());
				//最外层的div是为了与关键字的结构一致从而共用js
				respLoc.html("<div class='resparea'><span>"+txtRespTmp+"</span>"+
						"<span class='anniu glyphicon glyphicon-trash' onclick='delKey();'></span>" +	
						"<span class='anniu TxtRespedit  glyphicon glyphicon-pencil' data-toggle='modal'  data-target='#modalEditKey'></span><div>");
				$("#modalTxtResp").modal("hide");
				$("#txtResp").val("");
				
				//编辑文本回复
				$(".TxtRespedit").click(function(){
					$("#modalEditKey").find("h4").text("修改回复文字");
					keyName=$(this).prev().prev();
					editKey(keyName);
				});
			});
			//文本回复点击事件
			$('#modalTxtResp').on('shown.bs.modal', function (e) {
				responseType="text";
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
					respLoc.html(html);
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
					respLoc.html(html);
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
					respLoc.html(html);
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
					respLoc.html(html);
				}
			});
			
			
	});






//选中加样式 
function selected(obj){
	$(document).find(".selected").removeClass("selected");
	$(obj).addClass("selected").siblings().removeClass("selected");
} 

//---------------回复图片 开始
	function initImage(){
		//设置回复类型为图片
		responseType="image";
		initimageInfo();
		queryImgGroup();
		//初始化上传按钮
		uploadMaterial("uploadIcon","imagePath","picIcon","/wx/image/uploadImage",initimageInfo);
	}

//-------------回复音频 开始
	function initVoice(){
		responseType="voice";
		initvoiceInfo();
	}

//-------------回复音频 结束
	
//---------------视频回复开始
	function initAudio(){
		responseType="video";
		initvideoInfo();
	}

//---------------视频回复结束

//---------------图文回复开始
	function initArticle(){
		responseType="article";
		//初始化微信文章内容列表信息
		queryArticleList();
	}
	//---------------图文回复结束
	
	function editKey(keyName){// 编辑关键字/文字回复
	$("#modalEditKey").find("#editKeyword").val(keyName.text());
	}
	
	function saveEditKey(){// 保存编辑的关键字
	keyName.text($("#editKeyword").val());
	$("#modalEditKey").modal("hide");}
	//列表中回复旁边的删除图标
	function delKey(){
		$(event.target).parent().remove();
		responseType= "";
		mediaId="";
	}
	
	//获取当前需要填充回复语的位置
	function getRespLoc(){
		respLoc=$(event.target).parent().parent().next();
	}
	//列表中当前需要保存的内容
	function getSaveLoc() {
	saveLoc = $(event.target).parent();// 获取当前规则保存按钮所在规则的位置
	var tmp=saveLoc.children("div.resp").children();
	if (responseType == "") {//未进行编辑操作而点保存按钮时，赋值responseType和mediaId
		if (typeof (tmp.children("span[type='text']").html()) != "undefined") {//文本
			responseType = tmp.children("span[type='text']").attr("type");
		} else if (typeof(tmp.children("img.tuyiinshi").html()) != "undefined") {//图片
			responseType =tmp.children("img.tuyiinshi").attr("type");
			mediaId = tmp.children("img.tuyiinshi").attr("mediaid");
		}else if (typeof(tmp.children("audio").html()) != "undefined") {//音频
			responseType = tmp.children("audio").attr("type");
			mediaId = tmp.children("audio").attr("mediaid");
		}else if (typeof(tmp.children("video").html()) != "undefined") {//视频
			responseType = tmp.children("video").attr("type");
			mediaId = tmp.children("video").attr("mediaid");
		}else if (typeof (tmp.children().children("div.jiangbangititem").html()) != 'undefined') {//图文
			responseType = tmp.children().children("div.jiangbangititem").attr("type");
			mediaId = tmp.children().children("div.jiangbangititem").attr("mediaid");
		}
	}
}
	//“添加关键字”弹框中添加多个关键字，删除关键字的方法
	function delTmpkey(){
		$(event.target).parent().remove();
	}
	

	//当前分页赋值
	function thisInitPaginator(totalPages,currentPage, count, queryListMethod){
		if(typeof count == "undefined"){
			count = 0;
		}
		var totalNumStr = "共" + count + "条";
		$("#thistotalNum").html(totalNumStr);
		var limit = global_limit; //每页显示多少条记录
		var pagination = $("#thispagination");
		var options = {
		    bootstrapMajorVersion:3,	//分页版本
		    currentPage: currentPage,				//当前页码
		    numberOfPages: 5,			//显示多少页，默认显示 5 页
		    totalPages:totalPages,				//总页数
		    onPageClicked: function (event, originalEvent, type, page) {
		    	//放入页面中当前页码的隐藏域中
		    	$("#thiscurrPage").val(page);
		    	queryListMethod(page, limit);
		    }
		}
	    pagination.bootstrapPaginator(options);
	}	
	