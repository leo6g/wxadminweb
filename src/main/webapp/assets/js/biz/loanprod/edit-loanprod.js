$(document).ready(function(){
	//初始化关联文章
	queryNewsItemsById();
	//初始化上传按钮
	advGetUpload("uploadIcon","imagePath","picIcon","/biz/loan/doUpload");
	//修改贷款产品管理信息时，validate验证，验证通过后调用保存方法 
	$("#editForm").validate({
        submitHandler:function(form){
        	editObj();
        }    
    });
	
	$("#picIcon").attr("src", $("#hidden-imagePath").val());
	//修改金融产品管理信息
	$("#saveBtn").click(function(){
		//获取新增关联文章ID
		var itemId = $("#itemId").val();
		$("#articleId").val(itemId);
		var form = $("#editForm");
		form.submit();
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
	
	//修改贷款产品管理信息
	function editObj(){
		var url = contextPath + "/biz/loan/updateFinanceProd";
		var params = $("#editForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("修改成功!");
					C.load(contextPath + "/biz/loan/list");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("产品代码已经存在，请修改!" ,$.scojs_message.TYPE_ERROR);
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
				
				//产品类型-下拉框默认值
				if(dicCode=='loan'){
					$("#"+dicCode).val($("#hidden-type").val());
				}
			}
		});
	}
	
	/**
	 * 获得相关文章
	 */
	function initAticle(){
		var url = contextPath + "/wx/newsitems/getAll";
		var params = "";
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#article-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#articleId").html(htmlStr);
				//文章-下拉框默认值
				$("#articleId").val($("#hidden-articleId").val());
			}
		});
	}
	
	//初始化关联文章信息
	function queryNewsItemsById(){
		var itemId = $("#articleId").val();
		var extStr1 = $("#hidden-extStr1").val();
		var loanType = $("#hidden-loanType").val();
		$("#loanType").val(loanType);
		$("#extStr1").val(extStr1);
		var url = contextPath + "/wx/newsitems/getById?itemId="+itemId;
		var params = "";
		//异步请求职位列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = eval(data.object);
				$("#title").val(viewinfo.title);
				$("#type").val(viewinfo.type);
				$("#url").val(viewinfo.url);
				UE.getEditor('editorContent').execCommand('insertHtml', viewinfo.content);
				$("#itemId").val(itemId);
				$("#author").val(viewinfo.author);
			}
		});
	};

});

