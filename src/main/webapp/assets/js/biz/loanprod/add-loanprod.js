$(document).ready(function(){
	//初始化上传按钮
	advGetUpload("uploadIcon","imagePath","picIcon","/biz/loan/doUpload");
	//新增贷款产品管理信息时，validate验证，验证通过后调用保存方法 
	$("#addForm").validate({
        submitHandler:function(form){
        	addObj();
        }    
    });
	
	//动态选择基本信息与文章信息
	$("#jbTab").click(function(){
		$("#articleTab").removeClass("active");
		$("#articleDiv").removeClass("active");
		$("#jbTab").addClass("active");
		$("#jbDiv").addClass("active");
	});
	$("#articleTab").click(function(){
		$("#jbTab").removeClass("active");
		$("#jbDiv").removeClass("active");
		$("#articleTab").addClass("active");
		$("#articleDiv").addClass("active");
	});
	//新增并提交审核贷款产品管理信息
	$("#saveAndCheckBtn").click(function(){
		$("#authState").val('1-WAITING');
		var title = $("#title").val();
		if(title == ''){
			alert("请完善关联文章信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var form = $("#addForm");
		form.submit();
	});
	
	//新增审核贷款产品管理信息并存为草稿
	$("#saveAndDreafBtn").click(function(){
		$("#authState").val('DRAFT');
		var title = $("#title").val();
		if(title == ''){
			alert("请完善关联文章信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		var form = $("#addForm");
		form.submit();
	});
	
	
	//新增贷款产品管理信息
	function addObj(){
		var url = contextPath + "/biz/loan/insertFinanceProd";
		var params = $("#addForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					if($("#authState").val() == '1-WAITING'){
						var fincId = data.bean.fincId;
						saveBIZAuthInfo(fincId);
					}else{
						alert("更新成功!");
						C.load(contextPath + "/biz/loan/list");
					}
				}
			}else{
				if(data.returnCode=="-1"){
					alert("产品代码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//点击取消按钮
	$("#cancelBtn").click(function(){
		C.load(contextPath + "/biz/loan/list");
	});
	
	
	//初始化产品类型模版
	initDicByCode('loan');
	
	//初始化关联文章模版
	initAticle();
	
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
	
	/**
	 * 获得相关文章
	 */
	function initAticle(){
		var url = contextPath + "/wx/newsitems/getAll";
		var params = "";
		//请求数据字典信息
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#article-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#articleId").html(htmlStr);
			}
		});
	}
	//信息保存至审核表
	function saveBIZAuthInfo(fincId){
		var url=contextPath+"/biz/authinfo/insertBIZAuthInfo";
		var formdata = "type=FINANCE&prodId="+fincId+"&operFlag=WAITING";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("更新成功!");
				C.load(contextPath + "/biz/loan/list");
			}
		});
	};

	
/////////////////////////////
});



