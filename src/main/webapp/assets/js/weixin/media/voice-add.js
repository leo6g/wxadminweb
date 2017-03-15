$(document).ready(function(){
	$("#showVoice").hide();
	//初始化上传按钮
	uploadVoice("uploadIcon","voicePath","showVoice","/wx/voice/uploadVoice");	
	
	

	//提交按钮
	$('#submitData').click(function(e){
		
      	  var formdata=$("#formdata").serialize();
   	      var url=contextPath+"/wx/voice/insertVoice";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert(data.returnMessage);
				C.load(contextPath+"/wx/voice/list");
				e.preventDefault();
			}else{
				alert(data.returnMessage);
			}
		});

	});

});



