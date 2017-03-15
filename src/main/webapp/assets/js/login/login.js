
$(document).ready(function(){
	var contextPath = "/wxadminweb";
	//点击修改验证码图片
	$("#codeImg").click(function(){
		refreshCode();
	});
	
	//回车事件
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	//用户登录
	    	login();
	     }
	}
	
	//点击登录
	$("#loginBtn").click(function(){
		login();
	});
	
	//输入后提示消失
	$("#userName,#passWord,#validateCode").focus(function(){
		$("#errorSpan").hide();
	});
	
	

	/**
	 * 刷新验证码
	 */
	function refreshCode(){
		var date = new Date();
		var srcUrl = contextPath + "/servlet/validateCodeServlet?random="+date.getTime();
		$("#codeImg").attr("src", srcUrl);
	}

		           
	/**
	 * 用户登录
	 * @returns
	 */	           
	function login(){
		var userName = $("#userName").val();
		var passWord = $("#passWord").val();
		var validateCode = $("#validateCode").val();
		if(userName==""){
			$("#errorSpan").html("<i class='iconfont icon-guanbi1'></i> 请输入用户名.").show();
			return;
		}
		if(passWord==""){
			$("#errorSpan").html("<i class='iconfont icon-guanbi1'></i> 请输入密码.").show();
			return;
		}
		/*if(validateCode==""){
			$("#errorSpan").html("<i class='iconfont icon-guanbi1'></i> 请输入验证码.").show();
			return;
		}*/
		var url = contextPath + "/loginCheck";
		//var params = "userName="+userName+"&passWord="+passWord+"&validateCode="+validateCode;
		var params = "userName="+userName+"&passWord="+passWord;
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					location.href = contextPath + "/index";
				}else{
					$("#errorSpan").html("<i class='iconfont icon-guanbi1'></i> "+data.returnMessage).show();
					//刷新验证码
					//refreshCode();
				}
			}else{
				$("#errorSpan").html("<i class='iconfont icon-guanbi1'></i> "+data.returnMessage).show();
				//刷新验证码
				//refreshCode();
			}
		});
	}	     
	
});

	     


