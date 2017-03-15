$(document).ready(function(){
	var contextPath = $("#contextPath").val();
	//加载广告
	var showAdver = $("#showAdver").val();
	if(showAdver == 'T'){
		$("#adverDiv").css("display","");
		getAdvertise();
	}else{
		$("#adverDiv").css("display","none");
	}
	
	//阅读点赞区域
	/*var showPraise = $("#showPraise").val();
	if(showPraise == 'T'){
		$("#praiseDiv").css("display","");
	}else{
		$("#praiseDiv").css("display","none");
	}*/
	
	var showRemark = $("#showRemark").val();
	if(showRemark == 'T'){
		$("#remarkDiv").css("display","");
	}else{
		$("#remarkDiv").css("display","none");
	}
	//根据点赞状态隐藏点赞或已点赞
	state();
	
	//点赞操作
	$("#dianzan").bind("click",function(){
		var itemId = $("#itemId").val();
		var openId = $("#openId").val();
		/*if(openId==""){
			alert("请登录!");
			return false;
		}*/
		$("#dianzan").css("display","none");
		$("#dianzan1").css("display","");
		clickPraise(itemId,openId);
	});
	
	//评论操作
	$("#remarkBtn").bind("click",function(){
		var title = $("#title").val();
		var openId = $("#openId").val();
		var itemId = $("#itemId").val();
		window.location.href = contextPath + "/wx/articleremark/remark?itemId="+itemId+"&openId="+openId
		+"&title="+title;
	})
	
	function state(){
		var praiseState = $("#praiseState").val();
		if(praiseState == 'T'){
			$("#dianzan").css("display","none");
			$("#dianzan1").css("display","");
		}else if(praiseState == 'F'){
			$("#dianzan1").css("display","none");
			$("#dianzan").css("display","");
		}
	};
	//点赞同步数据库
	function clickPraise(itemId,openId){
		var praiseTimes = $("#praiseTimes").val();
		var url = contextPath + "/wx/article/insertArticlePraise?itemId="+itemId+"&openId="+openId+"&state=T";
		//异步请求关注用户分组列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				$("#praise").text((parseInt(praiseTimes) + 1));
			}
		});
	}
	
	//获取广告信息
	function getAdvertise(){
		var url = contextPath + "/biz/advertisement/getAll";
		//异步请求关注用户分组列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(source, flag){
			if(flag){
				//渲染列表数据
				var data = source.beans;
				var size = source.beans.length;
				var index = Math.ceil(Math.random()*(size-1));
				result ='<center><a href="'+data[index].url+'"><img src="'+data[index].imgPath+'" style="width: 100%;height: 165px;" /></a></center>';
	            $('#advertise').html(result);
			}
		});
	}
	
	var secs = $("#articlCon section");
	for(var i = 0;i<secs.length;i++){
	    if((getStyle(secs[i],'display') == "inline-block") && (secs[i].parentElement.children.length>1)){
	    	secs[i].parentElement.style.wordSpacing = "-8px";
            secs[i].style.wordSpacing = "0px";
		}
	}

	function getStyle(obj, attr) {
        if (obj.currentStyle) {
            return obj.currentStyle[attr];
        }
        else {
            return getComputedStyle(obj, false)[attr];
        }
    }

////////////////////////////////////
});

