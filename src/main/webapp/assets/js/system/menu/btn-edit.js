

$(document).ready(function(){

	var butid =$("#btnId").val();
	var menuId =$("#menuId").val();
	//初始化上传按钮
	getUpload("uploadIcon","butIcon","picIcon");

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
		  	  formdata=formdata+"&id="+butid;
			  var url=contextPath+"/system/button/updateButton";
			  Util.ajax.postJson(url, formdata, function(data, flag){
					if(flag && data.returnCode=="0"){
						alert(data.returnMessage);
						C.load(contextPath+"/system/button/list?menuId="+menuId);
					}else{
						alert(data.returnMessage);
					}
				});
	});
	var url = contextPath + "/system/button/queryButton?id="+butid;
	var params = "";
	//异步请求按钮数据
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			var viewinfo = eval(data.object);
			$("#menuName").text("菜单名称："+viewinfo.parentName);
			$("#butName").val(viewinfo.butName);
			$("#url").val(viewinfo.url);
			$("#butCode").val(viewinfo.butCode);
			$("#butIcon").val(viewinfo.butIcon);
			if(viewinfo.butIcon !=null && viewinfo.butIcon!=''){
				$("#picIcon").attr("src",contextPath +"/"+viewinfo.butIcon);
			}

		}
	});
});