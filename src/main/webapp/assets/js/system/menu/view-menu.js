
$(document).ready(function(){
var menuId = $("#menuId").val();
	//查询菜单详细信息
	queryMenuView();
	
	//初始化菜单列表
function queryMenuView(){
	var url = contextPath + "/system/menu/queryMenuView?id="+menuId;
	var params = "";
	//异步请求职位列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			//基本信息
			var viewinfo = eval(data.object);
			$("#menuName").text(viewinfo.menuName);
			$("#menuUrl").text(viewinfo.menuUrl);
			$("#menuOrder").text(viewinfo.menuOrder);
			if(viewinfo.menuType=="1"){
				$("#menuType").text("访问类型");
			}
			if(viewinfo.menuType=="2"){
				$("#menuType").text("菜单类型");
			}
			$("#menuLevel").text(viewinfo.menuLevel+"级");
			$("#parentName").text(viewinfo.parentName);
			$("#menuIcon").html("<img src='"+viewinfo.menuIcon+"' class='folder02' alt=''>");
			//按钮列表
			var source = $("#btn-list-template").html();
			var templet = Handlebars.compile(source);
			var htmlStr = templet(data.beans);
			$("#btn-list-table").html(htmlStr);
		}
	});
}
});




