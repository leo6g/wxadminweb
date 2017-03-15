$(document).ready(function(){
	//初始化关联文章
	queryNewsItemsById();
	//初始化上传按钮
	advGetUpload("uploadIcon","imagePath","picIcon","/biz/ficprod/doUpload");
	//修改理财产品管理信息时，validate验证，验证通过后调用保存方法 
	$("#editForm").validate({
        submitHandler:function(form){
        	editObj();
        }    
    });
	
	//修改金融产品管理信息
	$("#saveBtn").click(function(){
		if( $("#hidden-type").val() == "国债产品"){
			var rate = $("#nll").val();
			if(rate != ''){
				//验证年利率为0~4位的正实数
				 var reg = new RegExp("^[0-9]+(.[0-9]{0,4})?$");
				 if(!reg.test(rate)){  
				        alert("请输入合法年利率!",$.scojs_message.TYPE_ERROR);
						return false;  
				   }  
			}
		}
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
	
	//修改理财产品管理信息
	function editObj(){
		var url = contextPath + "/biz/ficprod/updateFinanceProd";
		var params = $("#editForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("修改成功!");
					C.load(contextPath + "/biz/ficprod/list");
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
		C.load(contextPath + "/biz/ficprod/list");
	});
	
	
	//初始化产品类型模版
	initDicByCode('ficProdType');
	
	//初始化关联文章模版
	initAticle();

	//初始化理财期限模版
	initDicByCode('ficPeriodType');
	
	
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
				if(dicCode=='ficProdType'){
					$("#"+dicCode).val($("#hidden-type").val());
				}
				//理财期限-下拉框默认值
				if(dicCode=='ficPeriodType'){
					$("#"+dicCode).val($("#hidden-periodType").val());
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
//////////////////////////////////////////////////
});

$(function(){
	var type = $("#hidden-type").val();
	$("[name='subType']").val($("#hidden-subType").val());
	$("[name='extStr1']").val($("#hidden-extStr1").val());
	$("[name='extStr2']").val($("#hidden-extStr2").val());
	$("[name='extStr3']").val($("#hidden-extStr3").val());
	
	if(type == "国债产品"){
		$("#gz").css('display','');
		$("#jj").css('display','none');
		$("#lc").css('display','none');
		$("#lc_ext").css('display','none');
		$("#bx").css('display','none');
		$("#gjs").css('display','none');
		$("#gz select").removeAttr("disabled");
		$("#gz input").removeAttr("disabled");
		$("#jj select").attr("disabled","disabled");
		$("#jj input").attr("disabled","disabled");
		$("#lc select").attr("disabled","disabled");
		$("#lc input").attr("disabled","disabled");
		$("#bx select").attr("disabled","disabled");
		$("#bx input").attr("disabled","disabled");
		$("#gjs select").attr("disabled","disabled");
	}else if(type == "理财产品"){
		$("#lc").css('display','');
		$("#lc_ext").css('display','');//只有产品类型是  理财产品时，才有 是否热门理财产品  下拉选     zhaoyan 添加
		$("#gz").css('display','none');
		$("#jj").css('display','none');
		$("#bx").css('display','none');
		$("#gjs").css('display','none');
		$("#lc select").removeAttr("disabled");
		$("#lc input").removeAttr("disabled");
		$("#jj select").attr("disabled","disabled");
		$("#jj input").attr("disabled","disabled");
		$("#gz select").attr("disabled","disabled");
		$("#gz input").attr("disabled","disabled");
		$("#bx select").attr("disabled","disabled");
		$("#bx input").attr("disabled","disabled");
		$("#gjs select").attr("disabled","disabled");
	}else if(type == "基金产品"){
		$("#jj").css('display','');
		$("#gz").css('display','none');
		$("#lc").css('display','none');
		$("#lc_ext").css('display','none');
		$("#bx").css('display','none');
		$("#gjs").css('display','none');
		$("#jj select").removeAttr("disabled");
		$("#jj input").removeAttr("disabled");
		$("#lc select").attr("disabled","disabled");
		$("#lc input").attr("disabled","disabled");
		$("#gz select").attr("disabled","disabled");
		$("#gz input").attr("disabled","disabled");
		$("#bx select").attr("disabled","disabled");
		$("#bx input").attr("disabled","disabled");
		$("#gjs select").attr("disabled","disabled");
	}else if(type == "保险产品"){
		$("#bx").css('display','');
		$("#gz").css('display','none');
		$("#jj").css('display','none');
		$("#lc").css('display','none');
		$("#lc_ext").css('display','none');
		$("#gjs").css('display','none');
		$("#bx select").removeAttr("disabled");
		$("#bx input").removeAttr("disabled");
		$("#lc select").attr("disabled","disabled");
		$("#lc input").attr("disabled","disabled");
		$("#gz select").attr("disabled","disabled");
		$("#gz input").attr("disabled","disabled");
		$("#jj select").attr("disabled","disabled");
		$("#jj input").attr("disabled","disabled");
		$("#gjs select").attr("disabled","disabled");
	}else if(type == "贵金属产品"){
		$("#gjs").css('display','');
		$("#bx").css('display','none');
		$("#gz").css('display','none');
		$("#jj").css('display','none');
		$("#lc").css('display','none');
		$("#lc_ext").css('display','none');
		$("#gjs select").removeAttr("disabled");
		$("#gjs input").removeAttr("disabled");
		$("#lc select").attr("disabled","disabled");
		$("#lc input").attr("disabled","disabled");
		$("#gz select").attr("disabled","disabled");
		$("#gz input").attr("disabled","disabled");
		$("#jj select").attr("disabled","disabled");
		$("#jj input").attr("disabled","disabled");
		$("#bx input").attr("disabled","disabled");
		$("#bx select").attr("disabled","disabled");
	}else if(type == ""){
		$("#gjs").css('display','none');
		$("#bx").css('display','none');
		$("#gz").css('display','none');
		$("#jj").css('display','none');
		$("#lc").css('display','none');
		$("#lc_ext").css('display','none');
	}
	 
})

