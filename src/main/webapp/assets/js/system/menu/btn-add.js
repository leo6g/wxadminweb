

$(document).ready(function(){
	var menuId = $("#menuId").val();
	var url = contextPath + "/system/button/list?menuId="+menuId;
	var params = "";
	//初始化上传按钮
	getUpload("uploadIcon","butIcon","picIcon");
	//异步请求菜单数据
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			var viewinfo = eval(data.object);
			$("#menuName").text("菜单名称："+viewinfo.menuName);
		}
	});
	//上传按钮
	$('#submitData').click(function(){
		var butName=$("#butName").val();
		var url=$("#url").val();
		var butCode=$("#butCode").val();
		var butIcon=$("#butIcon").val();
		if(butName==null || butName.trim()==""){
			alert("按钮名称不能为空!");
			return false;
		}
		if(url==null || url.trim()==""){
			alert("按钮路径不能为空!");
			return false;
		}
		if(butCode==null || butCode.trim()==""){
			alert("按钮编码不能为空!");
			return false;
		}
		  	var formdata=$("#formdata").serialize();
			var url=contextPath+"/system/button/insertButton";
			Util.ajax.postJson(url, formdata, function(data, flag){
					if(flag && data.returnCode=="0"){
						alert(data.returnMessage);
						C.load(contextPath+"/system/button/list?menuId="+menuId);
					}else{
						alert(data.returnMessage);
					}
				});	

	});
	
});