
$(document).ready(function(){
	
	//点击返回按钮
	$("#returnBtn").click(function(){
		C.load(contextPath + "/system/manager/list?currPage="+Util.browser.getParameter("currPage"));
	});
	
	$("#returnBack").click(function(){
		C.load(contextPath + "/system/manager/list?currPage="+Util.browser.getParameter("currPage"));
	});
});


