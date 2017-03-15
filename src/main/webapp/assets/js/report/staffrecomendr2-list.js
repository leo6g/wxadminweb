$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化劳动竞赛地区排名列表信息
	initTBStaffRecomendR2ListInfo();
	//查询按钮
	$("#querySubmit").click(function(){
		initTBStaffRecomendR2ListInfo();
	});
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryForm input[type='text']").val("");
		initTBStaffRecomendR2ListInfo();
	});
	//初始化劳动竞赛地区排名列表信息
	function initTBStaffRecomendR2ListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/report/tBRecomendR2/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求劳动竞赛地区排名列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#tBStaffRecomendR2-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#tBStaffRecomendR2ListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initTBStaffRecomendR2ListInfo);
			}
		});
	}
	
	
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "/report/tBRecomendR2/exportData?";
		var formdata=$("#queryForm").serialize();
		window.location.href = url+ encodeURI(formdata);
	});

});



