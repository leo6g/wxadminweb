$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化理财产品管理列表信息
	getList();
	
	//点击添加按钮
	$("#addBtn").click(function(){
		C.load(contextPath + "/biz/ficprod/add");
	});
	
	//点击编辑按钮
	$("#editBtn").click(function(){
		var id = $("#edit-id").val();
		var authState = $("#edit-authState").val();
		if(authState != 'DRAFT'){
			alert("只能编辑草稿状态!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		C.load(contextPath + "/biz/ficprod/edit?fincId="+id);
	});
	
	//点击删除按钮
	$("#delBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		delObj(id);
	});
	
	//点击查看按钮
	$("#viewBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var role = $("#role").val();
		C.load(contextPath + "/biz/ficprod/view?fincId="+id+"&role="+role);
	});
	
	
	//点击查询按钮
	$("#searchBtn").click(function(){
		getList();
	});
	
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#searchForm input[type='text']").val("");
		$("#type").val("");
		$("#authState").val("");
		getList();
	});
	
	
	//初始化产品类型模版
	initDicByCode('ficProdType');
	
	
	/**
	 * 根据数据字典组编码，渲染数据页面数据，编码和页面ID规则要一致
	 */
	function initDicByCode(dicCode){
		var url = contextPath + "/system/dic/queryDicsByGCode";
		var params = "dicGroupCode="+dicCode;
		//请求数据字典信息
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#"+dicCode+"-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#"+dicCode).html(htmlStr);
			}
		});
	}
	
	
	//初始化理财产品管理列表信息
	function getList(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/ficprod/getList";
		var params = "pageNumber="+currentPage + "&limit=" + limit + "&" + $("#searchForm").serialize();
		//异步请求理财产品管理列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#listTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, getList);
				
				
				//table列点击事件
				$("#listTable tr").click(function(){
					var id = $(this).attr("data-id");
					var authState = $(this).attr("data-authState");
					$("#edit-id").val(id);
					$("#edit-authState").val(authState);
				});
				
				//列表点击提交审核
				$(".reviewBtn").click(function(){
					var id = $(this).attr("data-id");
					var role = $("#role").val();
					if(role != 'business'){
						alert("您无权操作!",$.scojs_message.TYPE_ERROR);
						return false;
					}
					var url = contextPath + "/biz/ficprod/reviewFinanceProd";
					var params = "fincId="+id+"&authState=1-WAITING";
					//异步请求提交审核
					Util.ajax.postJson(url, params, function(data, flag){
						if(flag){
							if(data.returnCode=="0"){
								saveBIZAuthInfo(id);
							}
						}else{
							alert(data.returnMessage);
						}
					});
				});
			}
		});
		//清空隐藏域信息
		$("#edit-id").val("");
	}
	
	//中文化显示列表 处理状态;不同的用户已通过的状态查看为已审核
	/*Handlebars.registerHelper("tranState",function(value){
		var role = $("#role").val();
        if(role=="business"){
       	  return "已审核";
	     }else if(role=="infor"){
	          return "待审核";
        }
	});
	Handlebars.registerHelper("tranState2",function(value){
		var role = $("#role").val();
		if(role=='infor'){
        	return "已审核";
		}else if(role=='cardcheck'){
			return "待审核";
		}
	});*/
	//逻辑删除理财产品管理
	function delObj(id){
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/ficprod/logicDelFinanceProd";
			var params = "fincId="+id;
			//异步请求逻辑删除理财产品管理信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("删除成功!")					//重新请求列表数据
						getList();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}

   
	//信息保存至审核表
	function saveBIZAuthInfo(fincId){
		var url=contextPath+"/biz/authinfo/insertBIZAuthInfo";
		var formdata = "type=FINANCE&prodId="+fincId+"&operFlag=WAITING";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("提交审核成功!")					
				//重新请求列表数据
				getList();
			}
		});
	};
	//自动转换类型
	 Handlebars.registerHelper("transformat",function(value){
		 var name = "";
		 var url = contextPath + "/wx/article/getById?newsTempId="+value;
		 var params = "";
		//异步请求微信文章内容列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				$("#tempname").val(data.object.name);
			}else{
				$("#tempname").val("");
			}
		});
		alert($("#tempname").val());
		return $("#tempname").val();
	 });
	
/////////////////////////////	
});

//按钮隐藏与显示
$(function(){
	var role = $("#role").val();
	 if(role == 'business'){
		 $("#addBtn").css('display','');
		 $("#editBtn").css('display','');
		 $("#delBtn").css('display','');
		 $("#viewBtn").css('display','');
	 }else if(role == 'infor' || role == 'cardcheck'){
		 $("#viewBtn").css('display','');
		 $("#delBtn").css('display','');
	 }
	 
})
//点击产品名称查看文章
function fnPreview(tempId){
	$.ajax({
		type: "POST",
		url : contextPath+ '/wx/newsitems/getById?itemId='+tempId,
		dataType: "json",
		async : false,
		success : function(json) {
			var html = "<div style='position: relative;width: auto;height: 200px;border-bottom:solid 2px black' onclick=fnViewArticle('"+json.bean.itemId+"')>" +
			"<img src='"+json.bean.imagePath+"' width='100%' height='100%'>"+
			"<div style='position: absolute;bottom: 0;height: 50px;line-height: 50px;background-color: black;opacity: 0.6;width: 100%;color: white'>"+json.bean.title+"</div>" +
			"</div>";
			$('#preViewDiv').html(html);
		}
	});
}
function fnViewArticle(itemId){
	window.open(contextPath+'/wx/article/outputArticle?articleId='+itemId); 
}



