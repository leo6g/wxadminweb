$(document).ready(function(){
	$("#showVideo").hide();
	uploadVideo("uploadIcon","videoPath","showVideo","/wx/video/uploadVideo");		
	//上传视频
	//提交按钮
	$('#submitData').click(function(e){
		
      	  var formdata=$("#formdata").serialize();
   	      var url=contextPath+"/wx/video/insertVideo";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert(data.returnMessage);
				C.load(contextPath+"/wx/video/list");
				e.preventDefault();
			}else{
				alert(data.returnMessage);
			}
		});

	});
});



