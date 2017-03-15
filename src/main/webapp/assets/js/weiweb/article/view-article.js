
$(document).ready(function(){
var articleId = $("#articleId").val();
	//查询菜单详细信息
	queryModuleView();
	//初始化菜单列表
function queryModuleView(){
	var url = contextPath + "/weiweb/article/getById?articleId="+articleId;
	var params = "";
	//异步请求职位列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			//基本信息
			var viewinfo = eval(data.object);
			$("#title").text(viewinfo.title);
			$("#viewTimes").text(viewinfo.viewTimes);
			$("#createTime").text(viewinfo.createTime);
			$("#createUser").text(viewinfo.createUser);
			$("#content").html("<div style='margin:0px auto;'>"+viewinfo.content+"</div>");
			
		}
	});
};

});




