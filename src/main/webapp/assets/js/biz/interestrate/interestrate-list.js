$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化存贷款利率信息列表信息
	initBIZInterestRateListInfo();
	
	
	//新增存贷款利率信息信息时，validate验证，验证通过后调用保存方法 
	$("#addBIZInterestRateForm").validate({
        submitHandler:function(form){
        	addBIZInterestRate();
        }    
    });
	
	//新增存贷款利率信息信息
	$("#saveBIZInterestRateBtn").click(function(){
		var form = $("#addBIZInterestRateForm");
		form.submit();
	});
	
	//选中存贷款利率信息信息
	$("#bIZInterestRateListTable").delegate("tr","click",function(){
		var type = $(this).find("td:eq(1)").html();
		var subType = $(this).find("td:eq(2)").html();
		var period = $(this).find("td:eq(4)").html();
		var rate = $(this).find("td:eq(5)").html();
		var periodCn = $(this).find("td:eq(6)").html();
		var rateId = $(this).find("td:eq(8)").html();
		if(type=="贷款"){
			type = "loan";
	     }else if(type=="存款"){
	    	 type = "deposit";
        };
		$("#edit-subType").empty();
		getLoanOrDepositSubTypes(type,"edit");
		$("#edit-subType").val(subType);
		$("#edit-type").val(type);
		$("#edit-rateId").val(rateId);
		$("#edit-periodCn").val(periodCn);
		$("#edit-rate").val(rate);
		$("#edit-period").val(period);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改存贷款利率信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editBIZInterestRateForm").validate({
        submitHandler:function(form){
        	editBIZInterestRate();
        }    
    });
	
	//保存编辑的存贷款利率信息信息
	$("#edit-saveBIZInterestRateBtn").click(function(){
		var form = $("#editBIZInterestRateForm");
		form.submit();
	});
	//编辑存贷款利率信息信息
	$("#editBIZInterestRateBtn").click(function(){
		var rateId = $("#edit-rateId").val();
		if(rateId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
	});
	
	//删除存贷款利率信息信息
	$("#delBIZInterestRateBtn").click(function(){
		logicDelBIZInterestRate();//逻辑删除
		//delBIZInterestRate()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initBIZInterestRateListInfo();
	});
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryForm select").val("");
		initBIZInterestRateListInfo();
	});
	//初始化存贷款利率信息列表信息
	function initBIZInterestRateListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/interestrate/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求存贷款利率信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#bIZInterestRate-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#bIZInterestRateListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initBIZInterestRateListInfo);
			}
		});
	}
	
	
	//新增存贷款利率信息信息
	function addBIZInterestRate(){
		var url = contextPath + "/biz/interestrate/insertBIZInterestRate";
		var params = $("#addBIZInterestRateForm").serialize();
		//异步请求新增存贷款利率信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("存贷款利率信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initBIZInterestRateListInfo();
					//清空新增存贷款利率信息的弹窗信息
					$("#addBIZInterestRateForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("存贷款利率信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改存贷款利率信息信息
	function editBIZInterestRate(){
		var rateId = $("#edit-rateId").val();
	
		if(rateId==""){
		alert("请选择一条信息!");
		return false;
		}
		
		var url = contextPath + "/biz/interestrate/updateBIZInterestRate";
		var params = $("#editBIZInterestRateForm").serialize();
		//异步请求修改存贷款利率信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("存贷款利率信息信息修改成功!");
					//编辑存贷款利率信息信息清空
					$("#editBIZInterestRateForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initBIZInterestRateListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("存贷款利率信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除存贷款利率信息
	function delBIZInterestRate(){
		var rateId = $("#edit-rateId").val();
		if(rateId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/interestrate/deleteBIZInterestRate";
			var params = $("#editBIZInterestRateForm").serialize();
			//异步请求逻辑删除存贷款利率信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("存贷款利率信息删除成功!");
						//编辑存贷款利率信息信息清空
						$("#editBIZInterestRateForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBIZInterestRateListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除存贷款利率信息
	function logicDelBIZInterestRate(){
		var rateId = $("#edit-rateId").val();
		if(rateId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/interestrate/logicDeleteBIZInterestRate";
			var params = $("#editBIZInterestRateForm").serialize();
			//异步请求逻辑删除存贷款利率信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("存贷款利率信息删除成功!");
						//编辑存贷款利率信息信息清空
						$("#editBIZInterestRateForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initBIZInterestRateListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	};
	
	$("#queryType").bind("change",function(){
		$("#querySubType").empty();
		getLoanOrDepositSubTypes(this.value,"query");
	});
	
	$("#edit-type").bind("change",function(){
		$("#edit-subType").empty();
		getLoanOrDepositSubTypes(this.value,"edit"); 
	});
	
	$("#type").bind("change",function(){
		$("#subType").empty();
		getLoanOrDepositSubTypes(this.value,"add"); 
	});
	//动态从数据库获取类型及子类型
	function getLoanOrDepositSubTypes(type,obj){
		var url = contextPath + "/biz/interestrate/getSubTypeByType";
		//异步请求文本模版列表数据
		$("#type").val(type);
		var params = $("#addBIZInterestRateForm").serialize();
		Util.ajax.postJsonSync(url, params, function(data, flag){
			var source = $("#subType-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				if(obj == 'query') $("#querySubType").html(htmlStr);
				if(obj == 'edit') $("#edit-subType").html(htmlStr);
				if(obj == 'add') $("#subType").html(htmlStr);
				//$("#addBIZInterestRateForm select").val("");
			}
		});
	}
	//新增根据类型填充子类型
	/*$("#type").bind("change",function(){
		$("#subType").empty();
		if(this.value=="loan") {
			$("#subType").append("<option value=''>---请选择---</option>");
			$("#subType").append("<option value='个人住房贷款'>个人住房贷款</option>");
			$("#subType").append("<option value='个人消费贷款'>个人消费贷款</option>");
			$("#subType").append("<option value='汽车消费贷款'>汽车消费贷款</option>");
		};
		if(this.value=="deposit") {
			$("#subType").append("<option value=''>---请选择---</option>");
			$("#subType").append("<option value='活期'>活期</option>");
			$("#subType").append("<option value='整存整取'>整存整取</option>");
			$("#subType").append("<option value='零存零取'>零存零取</option>");
			$("#subType").append("<option value='整存零取'>整存零取</option>");
		}; 
	});*/
	//将type转化为中文
	Handlebars.registerHelper("transformat",function(value){
        if(value=="loan"){
       	  return "贷款";
	     }else if(value=="deposit"){
	          return "存款";
        }
});
	
	
});



