
$(document).ready(function(){
	var menuId = $("#menuId").val();
	var id="";
	//查询按钮列表信息
	queryBtnView();
	//获取选中的按钮的ID 
	$("#btnTableList").delegate("tr","click",function(){
		id = $(this).find("td:eq(1)").html();
	});
	
	//删除按钮信息
	$("#deleteBtnInfo").click(function(){
		var url = contextPath + "/system/button/deleteButton?id="+id;
		var params = "";
		//删除按钮信息
		confirm("确定删除吗？", function(){
				Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
			           alert(data.returnMessage);
			           //重新加载列表页面
			           queryBtnView();
					}
				});
		});
	});
	//去往更新按钮信息页面goUpdateButtonInfo
	$("#goUpdateButtonInfo").click(function(){
		C.load(contextPath +"/system/button/edit?btnId="+id+"&menuId="+menuId);
	});
	//初始化按钮列表
	function queryBtnView(){
		var url = contextPath + "/system/button/queryButtonList?menuId="+menuId;
		var params = "";
		//异步请求按钮列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//按钮列表
				var source = $("#btn-list-template").html();
				var templet = Handlebars.compile(source);
				var htmlStr = templet(data.beans);
				$("#btn-list-table").html(htmlStr);
				var viewinfo = eval(data.object);
				$("#menuName").text("菜单名称："+viewinfo.menuName);
			}
		});
	}
	//去添加按钮页面
	$("#goAddBtnInfo").click(function(){
		C.load(contextPath +"/system/button/add?menuId="+menuId);
	});

});

