$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 12 ;
	//初始化短信发送日志列表信息
	initSmsLogListInfo();
	
	
	//新增短信发送日志信息时，validate验证，验证通过后调用保存方法 
	$("#addSmsLogForm").validate({
        submitHandler:function(form){
        	addSmsLog();
        }    
    });
	
	//新增短信发送日志信息
	$("#saveSmsLogBtn").click(function(){
		var form = $("#addSmsLogForm");
		form.submit();
	});
	
	//选中短信发送日志信息
	$("#smsLogListTable").delegate("tr","click",function(){
		var smsContent = $(this).find("td:eq(1)").html();
		var phoneNumber = $(this).find("td:eq(2)").html();
		var id = $(this).find("td:eq(3)").html();
		var sendTime = $(this).find("td:eq(4)").html();
		var smsCnt = $(this).find("td:eq(5)").html();
		
		$("#edit-smsContent").val(smsContent);
		$("#edit-phoneNumber").val(phoneNumber);
		$("#edit-id").val(id);
		$("#edit-sendTime").val(sendTime);
		$("#edit-smsCnt").val(smsCnt);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	//查询按钮
	$("#querySubmit ").click(function(){
		initSmsLogListInfo();
	});
	//局部绑定回车事件
	$("#queryForm input[type='text']").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 
	//初始化短信发送日志列表信息
	function initSmsLogListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/smslog/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求短信发送日志列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#smsLog-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#smsLogListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initSmsLogListInfo);
			}
		});
	}
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "/biz/smslog/exportData?";
		var formdata=$("#queryForm").serialize();
		window.location.href = url+ formdata;
	});

});



