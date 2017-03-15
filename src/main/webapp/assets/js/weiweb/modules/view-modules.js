
$(document).ready(function(){
var moduleId = $("#moduleId").val();
	//查询菜单详细信息
	queryModuleView();
	showArticle();
	//初始化菜单列表
function queryModuleView(){
	var url = contextPath + "/weiweb/modules/getById?moduleId="+moduleId;
	var params = "";
	//异步请求职位列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			//基本信息
			var viewinfo = eval(data.object);
			$("#name").text(viewinfo.name);
			$("#levels").text(viewinfo.levels+"级");
			if(viewinfo.leaf=="T"){
				$("#leaf").text("是");
			}
			if(viewinfo.leaf=="F"){
				$("#leaf").text("否");
			}
			$("#parentName").text(viewinfo.parentName);
			$("#iconPath").html("<img src='"+viewinfo.iconPath+"' style='width: 100px;height: 80px;' alt=''>");
			//文章信息列表
			getArticle(viewinfo.articleId);
		}
	});
};

//动态从数据库获取微网站文章
function getArticle(articleId){
	var url = contextPath + "/weiweb/article/getAll?articleId="+articleId;
	//异步请求文本模版列表数据
	var params = "";
	Util.ajax.postJsonSync(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			if(data.beans.length > 0){
				showArticle("T");
			}else{
				showArticle("F");
			}
			//渲染列表数据
			$("#show-title").text(data.beans[0].title);
			$("#show-content").html(data.beans[0].content);
		}
	});
};


});

//按钮隐藏与显示
function showArticle(temp){
	 if(temp == 'T'){
		 $("#articleDiv").css('display','');
	 }else if(temp == 'F'){
		 $("#articleDiv").css('display','none');
	 }
}


