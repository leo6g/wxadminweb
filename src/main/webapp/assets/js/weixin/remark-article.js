$(document).ready(function(){
	var contextPath = $("#contextPath").val();
	//用户
	var openId = $("#openId").val();
	var itemId = $("#itemId").val();
	//加载评论
	remarkInfo();
	
	//提交评论
	$("#submitBtn").bind("click",function(){
		saveArticleRemark();
	});
	
	//查询品论信息函数
	function remarkInfo(){
		
		var url = contextPath + "/wx/articleremark/getAll?openId="+openId+"&itemId="+itemId;
		//异步请求关注用户分组列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				var viewinfo = data.beans;
				if(viewinfo.length != '0'){
					queryUserInfo(viewinfo);
				}
			}
		});
	};
	//点赞同步数据库
	function queryUserInfo(obj){
		var url = contextPath + "/wx/user/getByOpenId?openId="+openId;
		//异步请求关注用户分组列表数据
		var params = "";
		Util.ajax.postJsonSync(url, params, function(data, flag){
				//渲染列表数据
				var result = "";
				for (var i = 0; i < obj.length; i++) {
					if(obj[i].remark == null){
						obj[i].remark = '';
					}
					if(obj[i].createTime == null){
						obj[i].createTime = '';
					}
					result += '<div style="padding-top: 5px;"><div><img src="'+data.headerImage
					+'" alt="加载中..." height="25px" width="25px"><span>'+data.nickName+'</span></div><div style="width: 100%;">'
					+obj[i].remark+'</div><div style="color: #AAAAAA;left :60%;">'+obj[i].createTime+'</div></div>';
					
				}
				$("#remarkContent").html(result);
		});
		//显示我的评论
		$("#remarkDiv").css("display","");
	}
	
	//获取广告信息
	function saveArticleRemark(){
		//var content = $("#content").val();
		var params = $("#dataForm").serialize();
		var url = contextPath + "/wx/articleremark/insertWXArticleRemark";
		//异步请求关注用户分组列表数据
		Util.ajax.postJsonSync(url, params, function(source, flag){
			if(flag){
				//重新加载历史评论数据
				remarkInfo();
			}
		});
	}
////////////////////////////////////
});
