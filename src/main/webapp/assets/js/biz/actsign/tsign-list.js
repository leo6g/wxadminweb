$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 12 ;
	//初始化活动报名列表信息
	initActSignListInfo();
	
	
	//新增活动报名信息时，validate验证，验证通过后调用保存方法 
	$("#addActSignForm").validate({
        submitHandler:function(form){
        	addActSign();
        }    
    });
	
	//新增活动报名信息
	$("#saveActSignBtn").click(function(){
		var form = $("#addActSignForm");
		form.submit();
	});
	
	//选中活动报名信息
	$("#actSignListTable").delegate("tr","click",function(){
		var name = $(this).find("td:eq(1)").html();
		var phoneNumber = $(this).find("td:eq(2)").html();
		var id = $(this).find("td:eq(3)").html();
		var extStr1 = $(this).find("td:eq(4)").html();
		var belongCity = $(this).find("td:eq(5)").html();
		var createTime = $(this).find("td:eq(6)").html();
		var openId = $(this).find("td:eq(7)").html();
		
		$("#edit-name").val(name);
		$("#edit-phoneNumber").val(phoneNumber);
		$("#edit-id").val(id);
		$("#edit-extStr1").val(extStr1);
		$("#edit-belongCity").val(belongCity);
		$("#edit-createTime").val(createTime);
		$("#edit-openId").val(openId);
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改活动报名信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editActSignForm").validate({
        submitHandler:function(form){
        	editActSign();
        }    
    });
	
	//保存编辑的活动报名信息
	$("#edit-saveActSignBtn").click(function(){
		var form = $("#editActSignForm");
		form.submit();
	});
	//编辑活动报名信息
	$("#editActSignBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除活动报名信息
	$("#delActSignBtn").click(function(){
		//logicDelActSign();//逻辑删除
		delActSign()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initActSignListInfo();
	});
	//重置
	$("#resetBtn").click(function(){
		$("#queryForm input[type='text']").val("");
		$("#queryForm select").val("");
		initActSignListInfo();
	});
	//初始化活动报名列表信息
	function initActSignListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/actsign/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求活动报名列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#actSign-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#actSignListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initActSignListInfo);
			}
		});
	}
	
	
	//新增活动报名信息
	function addActSign(){
		var url = contextPath + "/biz/actsign/insertActSign";
		var params = $("#addActSignForm").serialize();
		//异步请求新增活动报名信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("活动报名新增成功!");
					//$("#alert-info").modal("show");
					alert("活动报名新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initActSignListInfo();
					//清空新增活动报名的弹窗信息
					$("#addActSignForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("活动报名编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	//导出
	$("#exportBtn").click(function(){
		var url = contextPath + "/biz/actsign/exportData?";
		var formdata=$("#queryForm").serialize();
		window.location.href = url+ formdata;
	});
});



