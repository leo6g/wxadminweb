$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化微信获取token列表信息
	initAccessTokenListInfo();
	
	//编辑微信获取token信息
	$("#editAccessTokenBtn").click(function(){
		editAccessToken();
	});
	
	//初始化微信获取token列表信息
	function initAccessTokenListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/accesstoken/getList";
		params = "pageNumber="+currentPage+"&limit="+limit;
		//异步请求微信获取token列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#accessToken-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#accessTokenListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initAccessTokenListInfo);
			}
		});
	}
	
	//生成token信息
	function editAccessToken(){
		
		var url = contextPath + "/wx/accesstoken/updateAccessToken";
		//异步请求修改微信获取token信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("微信token信息生成成功!");
					//重新请求列表数据
					initAccessTokenListInfo();
				}
			}else{
				if(data.returnCode=="-1"){
					alert("微信获取token编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}

});



