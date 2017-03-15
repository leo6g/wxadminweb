$(document).ready(function(){
//列表分页每页显示多少条记录
var global_limit = 10 ;
    var id='';
   //初始化上传信息
    importAwardPlayer("uploadBtn","filePath");
	//初始化抽奖人员资格信息表列表信息
	initTBizAwardPlayerListInfo();
	//获取抽奖期次
	getSeriesNumList();
	
	//新增抽奖人员资格信息表信息时，validate验证，验证通过后调用保存方法 
	$("#addTBizAwardPlayerForm").validate({
        submitHandler:function(form){
        	addTBizAwardPlayer();
        }    
    });
	
	//新增抽奖人员资格信息表信息
	$("#saveTBizAwardPlayerBtn").click(function(){
		var form = $("#addTBizAwardPlayerForm");
		form.submit();
	});
	
	//选中抽奖人员资格信息表信息
	$("#tBizAwardPlayerListTable").delegate("tr","click",function(){
		id = $(this).find("td:eq(3)").html();		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改抽奖人员资格信息表信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editTBizAwardPlayerForm").validate({
        submitHandler:function(form){
        	editTBizAwardPlayer();
        }    
    });
	
	//保存编辑的抽奖人员资格信息表信息
	$("#edit-saveTBizAwardPlayerBtn").click(function(){
		var form = $("#editTBizAwardPlayerForm");
		form.submit();
	});
	//编辑抽奖人员资格信息表信息
	$("#editTBizAwardPlayerBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除抽奖人员资格信息表信息
	$("#delTBizAwardPlayerBtn").click(function(){
		delTBizAwardPlayer()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initTBizAwardPlayerListInfo();
	});
	
	//初始化抽奖人员资格信息表列表信息
	function initTBizAwardPlayerListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/awardPlayer/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求抽奖人员资格信息表列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#tBizAwardPlayer-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				console.log(data.beans);
				var htmlStr = templet(data.beans);
				$("#tBizAwardPlayerListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initTBizAwardPlayerListInfo);
			}
		});
	}
	
	
	//新增抽奖人员资格信息表信息
	function addTBizAwardPlayer(){
		var url = contextPath + "/insertTBizAwardPlayer";
		var params = $("#addTBizAwardPlayerForm").serialize();
		//异步请求新增抽奖人员资格信息表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("抽奖人员资格信息表新增成功!");
					//$("#alert-info").modal("show");
					alert("抽奖人员资格信息表新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initTBizAwardPlayerListInfo();
					//清空新增抽奖人员资格信息表的弹窗信息
					$("#addTBizAwardPlayerForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("抽奖人员资格信息表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改抽奖人员资格信息表信息
	function editTBizAwardPlayer(){
		var id = $("#edit-id").val();
	
		if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/updateTBizAwardPlayer";
		var params = $("#editTBizAwardPlayerForm").serialize();
		//异步请求修改抽奖人员资格信息表信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("抽奖人员资格信息表信息修改成功!");
					//编辑抽奖人员资格信息表信息清空
					$("#editTBizAwardPlayerForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initTBizAwardPlayerListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("抽奖人员资格信息表编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除抽奖人员资格信息表
	function delTBizAwardPlayer(){
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/awardPlayer/deleteTBizAwardPlayer?id="+id;
			//异步请求逻辑删除抽奖人员资格信息表信息
			Util.ajax.postJson(url, null, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("抽奖人员资格信息表删除成功!");
						//重新请求列表数据
						initTBizAwardPlayerListInfo();
						//获取抽奖期次
						getSeriesNumList();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	$("#delTBizAwardPlayerSomeBtn").click(function(){
		var seriesNum=$("#seriesSomeNum").val();
		if(seriesNum==null || seriesNum==''){
			alert("请选择抽奖期次");
			return false;			
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/awardPlayer/deleteTBizAwardPlayer?seriesNum="+seriesNum;
			//异步请求逻辑删除抽奖人员资格信息表信息
			Util.ajax.postJson(url, null, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("抽奖人员资格信息表删除成功!");
						//重新请求列表数据
						initTBizAwardPlayerListInfo();
						//获取抽奖期次
						getSeriesNumList();
						
						$("#myModal2").modal('hide');
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	});
	
	//查询抽奖期次
	function getSeriesNumList(){
		var url = contextPath + "/biz/awardPlayer/getSeriesNumList";
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#seriesnum-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$(".seriesNumList").html(htmlStr);
			}
		});
	}
	
	$("#insertAward").click(function(){
		var seriesNum=$("#seriesNum").val();
		if(seriesNum==null || seriesNum==''){
			alert("请输入抽奖期次");
			return false;			
		}
		var url = contextPath + "/biz/awardPlayer/insertAwardPlayer?seriesNum="+seriesNum;
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("数据导入成功");
				//初始化抽奖人员资格信息表列表信息
				initTBizAwardPlayerListInfo();
				//获取抽奖期次
				getSeriesNumList();
				$("#myModal1").modal('hide');
				$("#seriesNum").val('');
				$("#filePath").val('');
			}else{
				alert(data.returnMessage);
			}
		});
	});
	
	

});



