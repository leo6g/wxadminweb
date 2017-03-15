$(document).ready(function() {
	//最终形成的图文入参
	var param = {};
	var paramDataArr=[];
	//文章顺序
	var index = 0;
	//当前点击的文章index
	var currentIndex = 0;
	//服务器硬盘上的图片前缀,有些图片地址只返回了名称，只能在这里写死。以后要改的话请在这里改。
	var localImgUrlPrefix = "/wxadminweb/viewImage/viewImage?imgPath=material/";
	var uploadimgLists=0;
	// 搜索按钮绑定事件
	$("#search-bt").click(function() {
		queryArticleList(1, 9);
	})
	// 回车键
	$(window).on("keyup", function(e) {
		if (e.keyCode === 13) {
			$("#search-bt").click();
		}
	});
	
	// 新建按钮
	$("#article-add-bt").click(function() {
		C.load(contextPath + "/wx/artmtl/add");
	})
	// 给ueditor添加事件，当粘贴进入外链图片时，自动上传至微信服务器，并替换地址为上传后返回的图片地址
	// 已经上传的图片数组
	var uploadedImgs = {};
	var editor = UE.getEditor('container');

	// 获取编辑器内容
	function getContent() {
		editor.getContent();
	}

	// 清空编辑器内容
	function clearDoc() {
		editor.execCommand('cleardoc');
	}
	//清空所有表单内容
	function clearForm(){
		$("#current-mediaid,#title,#author,#author,#digest,#media-id,#articleaddSelect1,#articleaddSelect2,#txtBeginDate,#txtEndDate").val("");
		clearDoc();
	}
	// 检验并装配所有表单内容
	function buildArticle() {
		var article = {};
		//封面缩略图mediaid
		var mediaId = $("#media-id").val();
		if (!mediaId) {
			alert("请先上传缩略图！", $.scojs_message.TYPE_ERROR);
			// return false;
		}
		article.thumbMediaId = mediaId;
		//标题
		var title = $("#title").val();
		if (!title) {
			alert("请填写标题", $.scojs_message.TYPE_ERROR);
			return false;
		}
		article.title = title;
		//作者
		var author = $("#author").val();
		if (!author) {
			alert("请填写作者", $.scojs_message.TYPE_ERROR);
			return false;
		}
		article.author = author;
		//摘要
		var digest = $("#digest").val();
		if(!digest){
			digest = UE.getEditor('container').getPlainTxt().substr(0,64);
			if(!digest){
				alert("请填写正文", $.scojs_message.TYPE_ERROR);
				return false;
			}
		}
		article.digest = digest;
		//正文
		var content = UE.getEditor('container').getContent();
		if(!content){
			alert("请填写正文", $.scojs_message.TYPE_ERROR);
			return false;
		}
		article.content = content;
		
		//原文链接
		var sourceUrl = $("#source-url").val();
		article.sourceUrl = sourceUrl;
		$("#source-url").val('');
		//是否显示封面，固定为显示
		article.showCoverPic = 0;
	
		// 类型
		var type=$("#articleaddSelect1").val();
		article.type=type;
		// 所属城市
		var belongCity=$("#articleaddSelect2").val();
		article.belongCity=belongCity;
		// 开始日期
		var actStartDate=$("#txtBeginDate").val();
		if(type=="article"){
			actStartDate = "";
		}
		article.actStartDate=actStartDate;
		// 结束日期
		var actEndDate=$("#txtEndDate").val();
		if(type=="article"){
			actEndDate = "";
		}
		article.actEndDate=actEndDate;
		// mediaid

	    return article;
	}
	// 原文链接按钮
	$("#source-bt").click(function() {
		if ($(this).prop("checked")) {
			$("#source-url").show();
		} else {
			$("#source-url").hide().val("");
		}
	});




	//加号按钮
	$("#add-bt").click(function(){
		var article = buildArticle();
		if(article.thumbMediaId=="" || article.title=="" || article.author=="" || article.digest=="" || article.content==""){
			console.log('kong');
			return false;
		}
		else{
		console.log(param);
		var handle = Handlebars.compile($("#article-add-template").html());
		var html1 = handle({indexNum:++index});
		$("#add-bt").before(html1);
		$(".article-container").last().trigger("click");
		//把上一个article的内容放到param中
		param[index-1] = article;
		//清空表单
		clearForm();
		}
		

	});
	//编辑页面的加号按钮
	$("#editAddbtn").click(function(){
		var article = buildArticle();
		if(article.thumbMediaId=="" || article.title=="" || article.author=="" || article.digest=="" || article.content==""){
			console.log('kong');
			return false;
		}
		else{
		console.log(param);
		var paramArr=[];
				for(j in param){
					paramArr.push(parseInt(j));
		}
		var sortParam=paramArr.sort(
			function(a,b){
				return b - a}
			);  
		var handle = Handlebars.compile($("#article-add-template222").html());
		var html1 = handle({"indexNum":++sortParam[0]});
		$("#editAddbtn").before(html1);
		$(".article-container").last().trigger("click");
		//把上一个article的内容放到param中
		// param[index-1] = article;
		param[paramArr[paramArr.length-1]] = article;
		//清空表单
		clearForm();
		}
		

	});
		// 弹出删除按钮
		$(document).on('mouseenter mouseleave','.article-container',function(ev){
    			if(ev.type=="mouseenter"){
					$(this).find(".del-box").fadeIn(200);
    			}
    			else{
					$(this).find(".del-box").fadeOut(200);
    			}
		});
		var imgdatasInfo=[];
		var currentPage=1;
		var global_limit = 12;
		var global_groupId='';
		var listCount=0;
 		//初始化图片列表信息,以及列表交互
		function initimageInfo(currentPage, limit,groupId){
			currentPage = currentPage||1;
			limit = limit||global_limit;
			groupId= groupId||global_groupId;
			var url = contextPath + "/wx/image/getList";
			params = "&pageNumber="+currentPage+"&limit="+limit+"&groupId="+groupId;
		//异步请求图片列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var imgdatasInfo=data.beans;

			var source = $("#image-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				listCount=data.bean.count;
				console.log(listCount);
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$(".yunlupop-content1").html(htmlStr);
				// 选择了复选框之后，其他section里面的复选框处于非选状态
				var chooseImgSrc="";
				$(".yunlupop-content1 section").on("click",function () {
						var index=$(this).index();
						$(this).find("b").addClass("yunl-iconright").end().siblings().find("b").removeClass("yunl-iconright");
						chooseImgSrc=$(this).find("img").attr("src");
						// 获取mediaid

						var Imgmediaid=imgdatasInfo[index].mediaId;
						$("#media-id").val(Imgmediaid);

				});
				//点击选中不同图文时候触发的动作
					$("div").on("click",".article-container",function(){
						var index = parseInt($(this).find(".index-num").val());
						uploadimgLists=$(this).index();
						//当前选中的图文消息边框变绿色
						$(this).addClass("green-border").siblings().removeClass("green-border");
						var currentImgSrc=$(this).find(".thumb-box").attr("src");
						$(".yunlu-imgiconbg").attr("src",currentImgSrc);
						//首先保存上个文章的内容
						param[currentIndex] = buildArticle();
						// 如果数组的值为false，清空表单
						if(param[index]==false){
							clearForm();
						}
						currentIndex = index;
						//改变表单的内容
						setFromContent(index);
						// return false;

					});
				// 点了确定之后把图片地址给左侧列表图和封面缩略图
				$("#yunlupopConfirmbtn1").on("click",function () {
					if(!$("#media-id").val()){
						alert("请选择图片！");
					}
					else{
					$(this).parents(".yunlu-mask").fadeOut(300);
					if(uploadimgLists){
						$(".article-container").eq(uploadimgLists).find(".sm-thumb").attr("src",chooseImgSrc);
					}
					else{
						$(".big-thumb").attr("src",chooseImgSrc);
					}
					$(".yunlu-imgiconbg").attr("src",chooseImgSrc);
					}
						
				});
				// 关闭选择图片弹窗
				$(".closeYunlupopBtn").on("click",function () {
					// 如果有mediaid,但是左侧列表图片没有变
					if($("#media-id").val() && !$(".article-container").eq(uploadimgLists).find(".sm-thumb").attr("src")){
						alert("点确定将选的图放到左侧吧！");
					}
					else{
						$(this).parents(".yunlu-mask").fadeOut(300);
				}
	});
				// 全局搜索关键词
				// serrchKeyword();
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initimageInfo);
			}
		});
	}
	
initimageInfo();

	//删除按钮
	$("div").on("click",".del-btn",function(){
		var self = $(this);
		confirm("确定删除吗？",function(){
			//从param中删除此文章
			var index = self.parents(".article-container").find(".index-num").val();
			self.parents(".article-container").remove();
			//第一篇图文选中
			// $(".index0").click();
			delete param[index];
		})
	});
	
	
	//设置表单内容
	function setFromContent(index){
		var article = param[index];
		if(article){
			$("#title").val(article.title);
			$("#author").val(article.author);
			$("#digest").val(article.digest);
			$("#media-id").val(article.thumbMediaId);
			//原文链接
			if(article.sourceUrl){
				$("#source-bt").prop("checked",true);
				$("#source-url").val(article.sourceUrl).show();
			}else{
				$("#source-bt").prop("checked",false);
				$("#source-url").val("").hide();
			}
			editor.setContent(article.content);
		}else{
			// clearForm();
			// console.log("内容不全！");
		}
		
	}
	// 选择开始日期
	$("#txtBeginDate").calendar({
            controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
            speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200
            complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true
            readonly: true                                   // 目标对象是否设为只读，默认：true
        });
	// 选择结束日期
    $("#txtEndDate").calendar();
   
	//上传缩略图按钮
	$(".upload-bt").click(function(){
		$("#upload-modal").fadeIn(300);
		//由于接口上传有次数限制，这里先用测试数据
		//var imgUrl = getRandomImgUrl();
		//$(".green-border .thumb-box").attr("src","http://read.html5.qq.com/image?src=forum&q=5&r=0&imgflag=7&imageUrl="+imgUrl);
		//设置mediaid值 o3EVDxgiHGvahAI8it2OJbc0oYyUVhqGUac-cA6cNAM
		//$("#media-id").val(Math.random());
		
		// alert("hahahahah");

	})
	//初始化上传按钮
	// uploadMaterial("uploadIcon","imagePath","picIcon","/wx/image/uploadImage",uploadCallback);
	//这里和红星公用一个方法，他用前三个参数，我用后两个参数
	/*function uploadCallback(a,b,c,file,data){
		console.log("图片上传完成，返回参数：",file,data);
		if(data.returnCode === "0"){
			alert("上传成功");
			var imgUrl = data.bean.realPath;
			$(".green-border .thumb-box").attr("src",imgUrl);
			//设置mediaid值
			$("#media-id").val(data.bean.mediaId);
			$("#upload-modal").modal("hide");
		}else{
			alert("上传失败，请重新上传",$.scojs_message.TYPE_ERROR);
		}
	};*/
	//点击保存按钮，还要保存一次文章。这是为了适应这种情况：点击加号后，直接点保存按钮。
	//测试方法，返回一个随机的图片地址
	function getRandomImgUrl(){
		var arr = ["https://mmbiz.qlogo.cn/mmbiz_png/WHPs8YkiaianDhS953pX9ibLanGibfaDggtMibBjjLmFnwB8rEBg1ib8D44yDxXNLyRZ0RsDRVrG0WhwmTlKf3PwS1sQ/0?wx_fmt=png",
		           "https://mmbiz.qlogo.cn/mmbiz_jpg/WHPs8YkiaianDhS953pX9ibLanGibfaDggtMX8D5R9U4j9gonffHyreyJ3fIXiahml9Kn062GhFtaSC4KxzgzOrRjng/0?wx_fmt=jpeg",
		           "https://mmbiz.qlogo.cn/mmbiz_jpg/WHPs8YkiaianDBb0UKm3JgLX3XXl3wrzQ0wBH6a0NeAx9W73Zibkl60g2zDoWSuibDyxksz6b2YT1iboANYG6BOv07Q/0?wx_fmt=jpeg",
		           "https://mmbiz.qlogo.cn/mmbiz_png/WHPs8YkiaianDBb0UKm3JgLX3XXl3wrzQ0l9Rq5o5E50tXXjQ3xf1Jp78GrCtJmUeoEJmqBqCQicefNq3xlSemoicQ/0?wx_fmt=png"];
		var index = Math.floor(Math.random()*4);
		console.log("随机数：",index);
		return arr[index];
	}
	//标题文字联动
	$("#title").change(function(){
		$(".article-container ").eq(uploadimgLists).find(".leftimgTitle").text($(this).val());
	});
	// 选择类型
	$("#articleaddSelect1").change(function () {
		if($(this).val()==="activity"){
			$("#articleaddType").slideDown(300);
			dateLocation();
		}
		else{
			$("#articleaddType").slideUp(300);	
		}
	});
// ajax搜索关键词
/*function serrchKeyword(currentPage, limit,groupId){
		params2 = "&pageNumber=1"+"&limit="+listCount+"&groupId="+"";
		//异步请求图片列表数据
		Util.ajax.postJson(contextPath + "/wx/image/getList", params2, function(data, flag){
			var imgdatasInfo2=data.beans;
			console.log(imgdatasInfo2);
			var searchimgNameArr=[];
			for(i=0;i<imgdatasInfo2.length;i++){
				searchimgNameArr.push(imgdatasInfo2[i].localUrl);
			}
			console.log(searchimgNameArr);
		})
	}
*/
	// 在弹窗中搜索图片
	$("#yunlupopSearchbtn1").on("click",function () {
		$(".yunlupop-content1 section").removeClass("yunlu-mrper4 yunlu-mrper0").removeAttr("style");
		var val=$("#yunlupopinput1").val().toLowerCase();
		var valReg=eval("/"+val+"/g");
		var showNum=0;
		var imgshowArr=[];
		if(!val){
		$(".yunlupop-content1 section").removeClass("yunlu-mrper4 yunlu-mrper0").removeAttr("style");
				$(".yunlupop-c1tip1").remove();
		}
		else{
			$(".yunlupop-c1tip1").remove();
			$.each($(".yunlupop-cinnerof1 span"),function (index,val) {
			var html=$(this).html().toLowerCase();
			if(valReg.test(html)){
				$(this).parents("section").show();
				showNum++;
				imgshowArr.push($(this).parents("section").index());
			}
			else{
				$(this).parents("section").hide();
			}
		});
			// 如果没有匹配的
			if(showNum==0){
				var yunlupopC1tip1="<p class='yunlupop-c1tip1'>没有和您输入匹配的结果。</p>";
				$(".yunlupop-content1").append(yunlupopC1tip1);
			};
			// 重新排版
		for(i=1;i<=showNum;i++){
			if(i%4==0){
				$(".yunlupop-content1 section").eq(imgshowArr[i-1]).addClass("yunlu-mrper0");
			}
			else{
				$(".yunlupop-content1 section").eq(imgshowArr[i-1]).addClass("yunlu-mrper4");
			}
		}
		}		


		});


	// 在搜索里面按下回车键，等于按下搜索按钮
	$("#yunlupopinput1").on("keyup",function (event) {
		var keycode=event.which;
		if(keycode==13){
			$("#yunlupopSearchbtn1").trigger("click");
		}
	});

	// 日历的位置
	function dateLocation() {
		 //开始日历弹窗的位置    
		    var startDateInputTop=$("#txtBeginDate").offset().top;
		    var startDateInputLeft=$("#txtBeginDate").offset().left;
		    $("#divDate").css({"left":startDateInputLeft+"px","top":(startDateInputTop+30)+"px"});
		    //结束日历弹窗的位置    
		    var endDateInputTop=$("#txtEndDate").offset().top;
		    var endDateInputLeft=$("#txtEndDate").offset().left;
		    $("#txtEndDateCalendar").css({"left":endDateInputLeft+"px","top":(endDateInputTop+30)+"px"});
	}
	
	//保存按钮
	$("#save-bt").click(function (){
		param[currentIndex] = buildArticle();
		// console.log(param[currentIndex]);
		//遍历param，看是否有未编辑完成的文章。如果有，则不能保存
		for(var k in param){
			if(!param[k]){
				alert("请补充完整文章信息",$.scojs_message.TYPE_ERROR);
				return false;
			}
		}
		console.log("开始保存，参数：",param);
		$.post(contextPath+"/wx/artmtl/insertWXMaterial",convertParam(param),function(data){
			if(data && data.returnCode === "0"){
				alert("添加成功");
				C.load(contextPath+"/wx/artmtl/list");
			}else{
				alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
			}
		},"json");
		
	});
	//把最终参数结构改变为ajax的入参
	function convertParam(param){
		var finalParam = {};
		var arr = [];
		for(var k in param){
			arr.push(param[k]);
		}
		finalParam.articles = arr;
		return finalParam;
	}
	
	//如果是编辑页面，包含$.editArticleMaterialId参数
	var editArticleMaterialId = $.editArticleMaterialId;
	if(editArticleMaterialId){
		//查询信息并显示到页面
		$.post(contextPath+"/wx/artmtl/getById",{materialId:editArticleMaterialId},function(data){
			if(data && data.returnCode === "0"){
				//把返回data组装为param结构的数据
				param = convertData2Param(data);
				console.log(param);

				buildPage(param);
				// console.log(param[9]);
				var paramArr=[];
				for(j in param){
					paramArr.push(j);
					paramDataArr.push(param[j]);

				}
				console.log(paramDataArr);
				// 如果选择了类型是活动，展开所属城市那一行
				if(param[parseInt(paramArr[0])].type==="activity"){
					$("#articleaddType").fadeIn(30);
					dateLocation();
				}
				$(".yunlu-imgiconbg").attr("src",$(".big-thumb").attr("src"));
			}else{
				alert("图文信息查询失败，请重试",$.scojs_message.TYPE_ERROR);
			}
			
		},"json");
	}
	//把接口返回的data结构改变为param格式的结构
	function convertData2Param(data){
		$.each(data.object.articles,function(index,e){
			param[e.indexNum] = {};
			param[e.indexNum]["author"] = e.author;
			param[e.indexNum]["title"] = e.title;
			param[e.indexNum]["content"] = e.content;
			param[e.indexNum]["showCoverPic"] = 0;
			param[e.indexNum]["digest"] = e.digest;
			param[e.indexNum]["sourceUrl"] = e.sourceUrl;
			param[e.indexNum]["imgUrl"] = e.imgUrl;
			param[e.indexNum]["articleId"] = e.articleId;
			param[e.indexNum]["type"] = e.type;
			param[e.indexNum]["belongCity"] = e.belongCity;
			param[e.indexNum]["actStartDate"] = e.actStartDate;
			param[e.indexNum]["actEndDate"] = e.actEndDate;
			//硬盘图片路径，这里只有名称："localUrl": "81fc8269bf0642a499ecead73278da80.png"，需要加上前缀localImgUrlPrefix
			param[e.indexNum]["localUrl"] = localImgUrlPrefix + e.localUrl;
			param[e.indexNum]["thumbMediaId"] = e.thumbMediaId;
			
		});
		return param;
	}
	//根据已有param来初始化构建页面
	function buildPage(param){
		var arr = [];
		var paramArr=[];
		for(j in param){
			paramArr.push(j);
		}
		for(var k in param){
			//第一篇文章特殊对待
			if(k === paramArr[0]){
				$(".big-thumb").attr("src",param[k].localUrl);
				$(".big-article-title").text(param[k].title);
				$(".big-article-title-ipt").val(param[k].title);
				$("#media-id").val(param[k].thumbMediaId);
				$("#author").val(param[k].author);
				$("#digest").val(param[k].digest);
				$("#articleaddSelect1").val(param[k].type);
				$("#articleaddSelect2").val(param[k].belongCity);
				$("#txtBeginDate").val(param[k].actStartDate);
				$("#txtEndDate").val(param[k].actEndDate);
				UE.getEditor("container").ready(function(){
					this.setContent(param[k].content);
				});
				var sourceUrl = param[k].sourceUrl;
				if(sourceUrl){
					$("#source-bt").prop("checked",true);
					$("#source-url").val(sourceUrl);
				}
			}else{
				param[k].indexNum = k;
				arr.push(param[k]);
				console.log(arr);
				var handle = Handlebars.compile($("#article-add-template").html());
				var html = handle(arr);
				$(".jiangweixinmw-left-listof1").after(html);
			}
		}
		console.log(param);
		
	}
	//点击编辑页面的保存按钮
	$("#edit-save-bt").click(function(){
		//这里不能直接替换，要用继承覆盖的方式。因为有些参数要一直传递
		$.extend(param[currentIndex],buildArticle());
//		param[currentIndex] = buildArticle();
		//遍历param，看是否有未编辑完成的文章。如果有，则不能保存
		for(var k in param){
			if(!param[k]){
				alert("请补充完整文章信息",$.scojs_message.TYPE_ERROR);
				return false;
			}
		}
		var mediaId = $.editArticleRemoteMediaId;
		//构建编辑页面的入参数据
		var finalParam = [];
		for(var k in param){
			var ele = {};
			var article = {};
			article.title = param[k].title;
			article.thumbMediaId = param[k].thumbMediaId;
			article.author = param[k].author;
			article.digest = param[k].digest;
			article.showCoverPic = 0;
			article.content = param[k].content;
			article.sourceUrl = param[k].sourceUrl||"";
			article.articleId = param[k].articleId;
			article.type=param[k].type;
			article.belongCity=param[k].belongCity;
			article.actStartDate=param[k].actStartDate;
			article.actEndDate=param[k].actEndDate;

			ele.mediaId = mediaId;
			ele.index = k;
			ele.article = article;
			finalParam.push(ele);
		}
		console.log("修改页面的最终入参：",finalParam);
		$.ajax({ 
	        type:"POST", 
	        url:contextPath+"/wx/artmtl/updateWXMaterial", 
	        dataType:"json",  
	        data:JSON.stringify(finalParam),
	        contentType:"application/json",               
	        success:function(data){
	        	if(data && data.returnCode === "0"){
					//成功后跳转到列表页面
					C.load(contextPath+"/wx/artmtl/list");
				}else{
					alert(data.returnMessage,$.scojs_message.TYPE_ERROR);
				}
	        } 
	     });
	})
	pp = param;
});
var pp;
