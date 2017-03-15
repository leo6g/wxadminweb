$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化图文审批流程信息列表信息
	initAudiProcessListInfo();
	
	
	//新增图文审批流程信息信息时，validate验证，验证通过后调用保存方法 
	$("#addAudiProcessForm").validate({
        submitHandler:function(form){
        	addAudiProcess();
        }    
    });
	
	$("#addAudiProcessBtn").click(function(){
		$("#materialId").val("");
	});
	
	$("#viewAudiProcessBtn").click(function(){
		queryViewById();
	});
	
	//新增图文审批流程信息信息
	$("#saveAudiProcessBtn").click(function(){
		var bizType = $("#bizType").val();
		var recommendType = $("#recommendType").val();
		var departLevel = $("#departLevel").val();
		var departType = $("#departType").val();
		
		if(bizType==""){
			alert("请选择流程业务类型!");
			return false;
		}
		if(bizType=="hotpoint"){
			if(recommendType==""){
				alert("请选择推荐类型!");
				return false;
			}
		}
		if(departLevel==""){
			alert("请选择流程等级!");
			return false;
		}
		if(departType==""){
			alert("请选择部门业务类型!");
			return false;
		}
		
		var form = $("#addAudiProcessForm");
		form.submit();
	});
	
	//选中图文审批流程信息信息
	$("#audiProcessListTable").delegate("tr","click",function(){
		var processId = $(this).find("td:eq(0)").html();
		/*var title = $(this).find("td:eq(1)").html();
		var materialId = $(this).find("td:eq(2)").html();
		var departLevel = $(this).find("td:eq(3)").html();*/
		/*var currentNode = $(this).find("td:eq(5)").html();
		var state = $(this).find("td:eq(6)").html();
		var initiatorId = $(this).find("td:eq(8)").html();
		var departType = $(this).find("td:eq(9)").html();
		var initDepartCode = $(this).find("td:eq(10)").html();
		var recommendType = $(this).find("td:eq(11)").html();
		var publishDate = $(this).find("td:eq(12)").html();
		var departCode = $(this).find("td:eq(13)").html();
		var bizType = $(this).find("td:eq(14)").html();*/
		
		$("#edit-processId").val(processId);
		$("#view-processId").val(processId);
		/*$("#edit-title").val(title);
		$("#edit-materialId").val(materialId);
		$("#edit-departLevel").val(departLevel);
		$("#edit-currentNode").val(currentNode);
		$("#edit-state").val(state);
		$("#edit-initiatorId").val(initiatorId);
		$("#edit-departType").val(departType);
		$("#edit-initDepartCode").val(initDepartCode);
		$("#edit-recommendType").val(recommendType);
		$("#edit-publishDate").val(publishDate);
		$("#edit-departCode").val(departCode);
		$("#edit-bizType").val(bizType);*/
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改图文审批流程信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editAudiProcessForm").validate({
        submitHandler:function(form){
        	editAudiProcess();
        }    
    });
	
	//保存编辑的图文审批流程信息信息
	$("#edit-saveAudiProcessBtn").click(function(){
		var form = $("#editAudiProcessForm");
		form.submit();
	});
	//编辑图文审批流程信息信息
	$("#editAudiProcessBtn").click(function(){
		var processId = $("#edit-processId").val();
		if(processId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		queryAudiProcessById();
	});
	
	//查看图文审批流程信息信息
	$("#viewAudiProcessBtn").click(function(){
		var processId = $("#view-processId").val();
		if(processId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除图文审批流程信息信息
	$("#delAudiProcessBtn").click(function(){
		//logicDelAudiProcess();//逻辑删除
		delAudiProcess()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initAudiProcessListInfo();
	});
	
	//初始化图文审批流程信息列表信息
	function initAudiProcessListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/proc/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求图文审批流程信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#audiProcess-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#audiProcessListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initAudiProcessListInfo);
			}
		});
	}
	
	
	//新增图文审批流程信息信息
	function addAudiProcess(){
		var url = contextPath + "/biz/proc/insertAudiProcess";
		var params = $("#addAudiProcessForm").serialize();
		//异步请求新增图文审批流程信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("图文审批流程信息新增成功!");
					//$("#alert-info").modal("show");
					alert("图文审批流程信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initAudiProcessListInfo();
					//清空新增图文审批流程信息的弹窗信息
					$("#addAudiProcessForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("图文审批流程信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改图文审批流程信息信息
	function editAudiProcess(){
//	var title = $("#edit-title").val();
//	var materialId = $("#edit-materialId").val();
//	var departLevel = $("#edit-departLevel").val();
//	var processId = $("#edit-processId").val();
//	var currentNode = $("#edit-currentNode").val();
//	var state = $("#edit-state").val();
//	var initiatorId = $("#edit-initiatorId").val();
//	var departType = $("#edit-departType").val();
//	var initDepartCode = $("#edit-initDepartCode").val();
//	var publishDate = $("#edit-publishDate").val();
//	var departCode = $("#edit-departCode").val();
//	var bizType = $("#edit-bizType").val();
//	
//		if(title==""){
//		alert("流程标题不能为空!");
//		return false;
//	}
//	if(materialId==""){
//		alert("图文素材ID不能为空!");
//		return false;
//	}
//	if(departLevel==""){
//		alert("流程等级(city:地市发起|province:省分行发起)不能为空!");
//		return false;
//	}
//	if(processId==""){
//		alert("请选择一条信息!");
//		return false;
//	}
//	if(currentNode==""){
//		alert("当前节点状态不能为空!");
//		return false;
//	}
//	if(state==""){
//		alert("流程状态(running:在程|closed:已关闭)不能为空!");
//		return false;
//	}
//	if(initiatorId==""){
//		alert("流程发起人(关联T_S_USER 表ID)不能为空!");
//		return false;
//	}
//	if(departType==""){
//		alert("部门业务类型(credit:信用卡部业务|loan:信贷部业务|ebank:电子银行部业务)不能为空!");
//		return false;
//	}
//	if(initDepartCode==""){
//		alert("发起部门编码不能为空!");
//		return false;
//	}
//	if(publishDate==""){
//		alert("推文发布日期不能为空!");
//		return false;
//	}
//	if(departCode==""){
//		alert("流程启动部门编码不能为空!");
//		return false;
//	}
//	if(bizType==""){
//		alert("流程业务类型(activity:热点导航活动|article:每周推文|hotpoint:热门推荐)不能为空!");
//		return false;
//	}
	
		var url = contextPath + "/biz/proc/updateAudiProcess";
		var params = $("#editAudiProcessForm").serialize();
		//异步请求修改图文审批流程信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				console.info(flag+"--------------------");
				if(data.returnCode=="0"){
					alert("图文审批流程信息信息修改成功!");
					//编辑图文审批流程信息信息清空
					$("#editAudiProcessForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initAudiProcessListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("图文审批流程信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除图文审批流程信息
	function delAudiProcess(){
		var processId = $("#edit-processId").val();
		if(processId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/proc/deleteAudiProcess";
			var params = $("#editAudiProcessForm").serialize();
			//异步请求逻辑删除图文审批流程信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("图文审批流程信息删除成功!");
						//编辑图文审批流程信息信息清空
						$("#editAudiProcessForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initAudiProcessListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除图文审批流程信息
	function logicDelAudiProcess(){
		var processId = $("#edit-processId").val();
		if(processId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/proc/logicDeleteAudiProcess";
			var params = $("#editAudiProcessForm").serialize();
			//异步请求逻辑删除图文审批流程信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("图文审批流程信息删除成功!");
						//编辑图文审批流程信息信息清空
						$("#editAudiProcessForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initAudiProcessListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	
	$("#bizType").change(function(){
		var bizType = $("#bizType").val();
		if(bizType=="hotpoint"){
			$("#recommend").css("display","block");
		}else{
			$("#recommendType").val("");
			$("#recommend").css("display","none");
		}
	});
	
	$("#edit-bizType").change(function(){
		var bizType = $("#edit-bizType").val();
		if(bizType=="hotpoint"){
			$("#edit-recommend").css("display","block");
		}else{
			$("#edit-recommendType").val("");
			$("#edit-recommend").css("display","none");
		}
	});
	
	$("#departLevel").change(function(){
		var bizType = $("#departLevel").val();
		if(bizType=="city"){
			var str='<option value="41000122">郑州市分行</option>'+
					'<option value="41022510">鹤壁市分行</option>'+
					'<option value="41022458">驻马店市分行</option>'+
					'<option value="41022496">许昌市分行</option>'+
					'<option value="41000096">焦作市分行</option>'+
					'<option value="41022484">濮阳市分行</option>'+
					'<option value="41022421">漯河市分行</option>'+
					'<option value="41000019">济源市直属支行</option>'+
					'<option value="41000072">洛阳市分行</option>'+
					'<option value="41022433">新乡市分行</option>'+
					'<option value="41022522">开封市分行</option>'+
					'<option value="41022472">平顶山市分行</option>'+
					'<option value="41000021">安阳市分行</option>'+
					'<option value="41000108">商丘市分行</option>'+
					'<option value="41022460">周口市分行</option>'+
					'<option value="41000084">南阳市分行</option>'+
					'<option value="41000110">信阳市分行</option>'+
					'<option value="41022508">三门峡市分行</option>';
			$("#initDepartCode").empty();
			$("#initDepartCode").html(str);
		}else if(bizType=="province"){
			$("#initDepartCode").empty();
			$("#initDepartCode").html('<option value="41022445">河南省分行</option>');
		}
	});
	
	$("#edit-departLevel").change(function(){
		var bizType = $("#edit-departLevel").val();
		if(bizType=="city"){
			var str='<option value="41000122">郑州市分行</option>'+
			'<option value="41022510">鹤壁市分行</option>'+
			'<option value="41022458">驻马店市分行</option>'+
			'<option value="41022496">许昌市分行</option>'+
			'<option value="41000096">焦作市分行</option>'+
			'<option value="41022484">濮阳市分行</option>'+
			'<option value="41022421">漯河市分行</option>'+
			'<option value="41000019">济源市直属支行</option>'+
			'<option value="41000072">洛阳市分行</option>'+
			'<option value="41022433">新乡市分行</option>'+
			'<option value="41022522">开封市分行</option>'+
			'<option value="41022472">平顶山市分行</option>'+
			'<option value="41000021">安阳市分行</option>'+
			'<option value="41000108">商丘市分行</option>'+
			'<option value="41022460">周口市分行</option>'+
			'<option value="41000084">南阳市分行</option>'+
			'<option value="41000110">信阳市分行</option>'+
			'<option value="41022508">三门峡市分行</option>';
			$("#edit-initDepartCode").empty();
			$("#edit-initDepartCode").html(str);
		}else if(bizType=="province"){
			$("#edit-initDepartCode").empty();
			$("#edit-initDepartCode").html('<option value="41022445">河南省分行</option>');
		}
	});
	
	
	
	$("#publishDate").on("click",function(){
		dateLocation();
	});
	// 选择开始日期
	$("#publishDate").calendar({
            controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
            speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200
            complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true
            readonly: true                                   // 目标对象是否设为只读，默认：true
   });

});

//日历的位置
function dateLocation() {
	 //开始日历弹窗的位置    
	    var startDateInputTop=$("#publishDate").offset().top;
	    var startDateInputLeft=$("#publishDate").offset().left;
	    $("#divDate").css({"left":startDateInputLeft+"px","top":(startDateInputTop+30)+"px","z-index":10000});
	   
}

/*$("#edit-publishDate").on("click",function(){
	dateLocation2();
});
// 选择开始日期
$("#edit-publishDate").calendar({
	controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"
	speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200
	complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true
	readonly: true                                   // 目标对象是否设为只读，默认：true
});

});

//日历的位置
function dateLocation2() {
	//开始日历弹窗的位置    
	var startDateInputTop=$("#edit-publishDate").offset().top;
	var startDateInputLeft=$("#edit-publishDate").offset().left;
	$("#divDate").css({"left":startDateInputLeft+"px","top":(startDateInputTop+30)+"px","z-index":10000});
	
}*/

//根据ID获取对应的信息
function queryAudiProcessById(){
	var processId = $("#edit-processId").val();
	var url=contextPath+"/biz/proc/getById?processId="+processId;
	var params = "";
	//异步请求信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			//基本信息
			var info = data.object;
			if(info !=null)
			{
				$("#edit-title").val(info.title);
				$("#edit-publishDate").val(info.publishDate.substring(0,10));
				$("#edit-bizType").val(info.bizType);
				if(info.recommendType!='' && info.recommendType!=null){
					$("#edit-recommendType").val(info.recommendType);
				}
				$("#edit-departLevel").val(info.departLevel);
				$("#edit-departType").val(info.departType);
				$("#edit-initDepartCode").val(info.initDepartCode);
				$("#edit-materialId").val(info.materialId);
				
			}

		}
	})
}


//根据ID获取对应的信息
function queryViewById(){
	var processId = $("#view-processId").val();
	var url=contextPath+"/biz/proc/getViewById?processId="+processId;
	var params = "";
	//异步请求信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			//基本信息
			var info = data.object;
			var depart ="";
			var state="";
			if(info !=null)
			{	
				if(info.currentNode=="city_office_f"){
					depart="地市办公室";
					state="待审批";
				}
				$("#ViewTitle").html("文章名称："+info.title);
				$("#ViewTime").html("发布时间："+info.publishDate);
				$("#ViewUser").html("创建人："+info.name);
				$("#ViewState").html("推送状态："+info.state);
				var str='<tr><td>1</td><td>'+depart+'</td><td>'+info.taskerName+'</td><td>'
						+state+'</td><td>'+info.procResult+'</td><td>'+info.createTime.substring(0,10)+'</td></tr>';
				$("#ViewTable").html(str);
			}
			
		}
	})
}


