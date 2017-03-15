$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化文章推荐列表信息
	initRecommendFinanceListInfo();
	//初始化上传按钮
	advGetUpload("uploadIcon","hotImgPath","picIcon","/biz/recfinance/doUpload");
	//初始化上传按钮
	advGetUpload("edit-uploadIcon","edit-hotImgPath","edit-picIcon","/biz/recfinance/doUpload");
	
	//新增文章推荐信息时，validate验证，验证通过后调用保存方法 
	$("#addRecommendFinanceForm").validate({
        submitHandler:function(form){
        	addRecommendFinance();
        }    
    });
	
	$("#addRecommendFinanceBtn").click(function(){
		$("#articleId").val("");
	});
	
	//新增文章推荐信息
	$("#saveRecommendFinanceBtn").click(function(){
		var form = $("#addRecommendFinanceForm");
		form.submit();
	});
	
	//选中文章推荐信息
	$("#recommendFinanceListTable").delegate("tr","click",function(){
		var id = $(this).find("td:eq(1)").html();
		/*var articleId = $(this).find("td:eq(2)").html();
		var createUser = $(this).find("td:eq(3)").html();
		var title = $(this).find("td:eq(5)").html();
		var localUrl = $(this).find("td:eq(6)").html();
		var levels = $(this).find("td:eq(7)").html();
		var isHot = $(this).find("td:eq(8)").html();
		var hotImgPath = $(this).find("td:eq(9)").html();*/
		
		$("#edit-id").val(id);
		/*$("#edit-articleId").val(articleId);
		$("#edit-createUser").val(createUser);
		$("#edit-levels").val(levels);
		$("#edit-isHot").val(isHot);
		$("#edit-hotImgPath").val(hotImgPath);
		
		$("#edit-articleId").val(articleId);*/
		
		$(this).css("background","#DFEBF2").siblings().css("background","#FFFFFF");
		return false;
	});
	
	
	//修改文章推荐信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editRecommendFinanceForm").validate({
        submitHandler:function(form){
        	editRecommendFinance();
        }    
    });
	
	//保存编辑的文章推荐信息
	$("#edit-saveRecommendFinanceBtn").click(function(){
		var form = $("#editRecommendFinanceForm");
		form.submit();
	});
	//编辑文章推荐信息
	$("#editRecommendFinanceBtn").click(function(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		queryRecommendFinanceById();
	});
	
	//删除文章推荐信息
	$("#delRecommendFinanceBtn").click(function(){
		//logicDelRecommendFinance();//逻辑删除
		delRecommendFinance()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initRecommendFinanceListInfo();
	});
	
	//初始化文章推荐列表信息
	function initRecommendFinanceListInfo(currentPage, limit){
		alert
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/recfinance/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求文章推荐列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			//console.info(data.beans);
			var source = $("#recommendFinance-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#recommendFinanceListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initRecommendFinanceListInfo);
			}
		});
	}
	
	
	//新增文章推荐信息
	function addRecommendFinance(){
		var url = contextPath + "/biz/recfinance/insertRecommendFinance";
		var params = $("#addRecommendFinanceForm").serialize();
		//异步请求新增文章推荐信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("文章推荐新增成功!");
					//$("#alert-info").modal("show");
					alert("文章推荐新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initRecommendFinanceListInfo();
					//清空新增文章推荐的弹窗信息
					$("#addRecommendFinanceForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("文章推荐编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改文章推荐信息
	function editRecommendFinance(){
		var id = $("#edit-id").val();
		if(id==""){
		alert("请选择一条信息!");
		return false;
	}
	
		var url = contextPath + "/biz/recfinance/updateRecommendFinance";
		var params = $("#editRecommendFinanceForm").serialize();
		//异步请求修改文章推荐信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("文章推荐信息修改成功!");
					//编辑文章推荐信息清空
					$("#editRecommendFinanceForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initRecommendFinanceListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("文章推荐编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除文章推荐
	function delRecommendFinance(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/recfinance/deleteRecommendFinance";
			var params = $("#editRecommendFinanceForm").serialize();
			//异步请求逻辑删除文章推荐信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("文章推荐删除成功!");
						//编辑文章推荐信息清空
						$("#editRecommendFinanceForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initRecommendFinanceListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除文章推荐
	function logicDelRecommendFinance(){
		var id = $("#edit-id").val();
		if(id==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/recfinance/logicDeleteRecommendFinance";
			var params = $("#editRecommendFinanceForm").serialize();
			//异步请求逻辑删除文章推荐信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("文章推荐删除成功!");
						//编辑文章推荐信息清空
						$("#editRecommendFinanceForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initRecommendFinanceListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//根据ID获取对应的信息
	function queryRecommendFinanceById(){
		var url=contextPath+"/biz/recfinance/getById?id="+$("#edit-id").val();
		var params = "";
		//异步请求信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var info = data.object;
				if(info !=null)
				{
					$("#edit-id").val(info.id);
					$("#edit-articleId").val(info.articleId);
					$("#edit-isHot").val(info.isHot);
					$("#edit-hotImgPath").val(info.hotImgPath);
					if(info.hotImgPath!='' && info.hotImgPath!=null){
						$("#edit-picIcon").attr("src",contextPath+"/viewImage/viewImage?imgPath=financeRec/"+info.hotImgPath);
					}
					var levels = info.levels;
					var levelArr = levels.split(",");
					//$("input[type='checkbox']").removeAttr("checked");
					$("input[name='levels']").prop("checked", false);
					for (var j = 0; j < levelArr.length; j++) {
						for (var i = 1; i < 7; i++) {
							var le=$("#edit-levels"+i).val();
							if(le==levelArr[j]){
								//$("#edit-levels"+i).attr("checked",true);
								$("#edit-levels"+i).prop("checked", true);
							}
						}
					}
					
				}

			}
		})
	}
	
	
	/*Handlebars.registerHelper("tranLevels",function(value){
        if(value=="success"){
       	  return "申办成功";
	     }else if(value=="fail"){
	          return "申办失败";
        }
	});*/
	
	Handlebars.registerHelper("tranHot",function(value){
        if(value=="0"){
       	  return "是";
	     }else if(value=="1"){
	      return "否";
        }
	});

});



