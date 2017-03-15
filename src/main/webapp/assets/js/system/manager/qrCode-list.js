$(document).ready(function(){
	
	if($("#qrCode").attr('src')){
		$("#searchBtn").hide();
	}
	
	
	
	$("#searchBtn").click(function(){
		
		var url = contextPath + "/system/manager/createQrAndSave";
		$("#searchForm").serialize();
		var params = "id="+$("#edit-id").val()+ "&userName=" + $("#userName").val();
		//异步请求列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(data.returnCode=="0"){
				$("#qrCode").attr('src',data.object);
				$("#searchBtn").hide();
			}
		});
		
		
		
	})
	
});

