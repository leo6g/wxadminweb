$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
var materialId='';
	//初始化音频列表信息
	initvoiceInfo();
		
	//查询按钮
	$("#querySubmit").click(function(){
		initvoiceInfo();
	});
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryForm input[type='text']").val("");
		initvoiceInfo();
	});
	//初始化音频列表信息
	function initvoiceInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/voice/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求音频列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			console.log(data);
			var source = $("#voice-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#voiceTable").html(htmlStr);
				$("#countSpan").text("微信音频"+data.bean.count+"/1000");
				//初始化分页数据(当前页码，总数，回调查询函数)
				//initPaginator(currentPage, data.bean.count, initvoiceInfo);
			}
		});
	}
	
	
	
	//上传音频
	$("#uploadBtn").click(function(){
		var url = contextPath + "/wx/voice/uploadVoice";
		var params = $("#queryForm").serialize();
		Util.ajax.postJson(url, params, function(data, flag){
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){

			}
		});
	});
	
	
	
	//添加音频页面
	$("#addVoice").click(function(){
		C.load(contextPath+'/wx/voice/add');
	});
	
	//删除图片信息
	$("body").on('click','.deleteVoice',function(){
		var materialId=$(this).attr("materialId");
		var mediaId=$(this).attr("mediaId");
		var url = contextPath + "/wx/voice/deleteVoice?materialId="+materialId+"&mediaId="+mediaId;
		var params = "";
		//删除按钮信息
		confirm("确定删除吗？", function(){
				Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initvoiceInfo();    
					}
					else{
						alert(data.returnMessage);
					}
				});
		});
	});
	
	
	
	//弹出编辑音频信息窗
	$("body").on('click','.editVoice',function(){
		materialId=$(this).attr("materialId");
		var name=$(this).attr("name");
		var subType=$(this).attr("subType");
		$("#editName").val(name);
		$("#editSubType").val(subType);
		$("#myModal1").modal('show');
	});
	
	
	//编辑音频信息
	$("body").on('click','#editVoiceInfo',function(){
		var url = contextPath + "/wx/voice/updateVoice?materialId="+materialId;
		var name=$("#editName").val();
		var subType=$("#editSubType").val();
		var params = {};
		params.name=name;
		params.subType=subType;
		//删除按钮信息
		Util.ajax.postJson(url, params, function(data,flag){
					if(flag && data.returnCode=="0"){
					   $("#editName").val('');
				       $("#myModal1").modal('hide');
			           alert(data.returnMessage);
			           //重新加载页面
			           initvoiceInfo();
					}
					else{
						alert(data.returnMessage);
					}
				});
	});
	
	//同步微信端音频信息
	$("#syncVoice").click(function(){
		$('body').showLoading();
		var url = contextPath + "/wx/voice/syncVoice";
		$.ajax({
			url:url,
			data:'',
			timeout :600000,
			type :'post',
			dataType:'json',
			success:function(data,flag){
				if(flag){
					if(data.returnCode=="0"){
						$('body').hideLoading();
						alert(data.returnMessage);
						//重新请求列表数据
						initvoiceInfo();
					}
				  }else{
					  $('body').hideLoading();
					  alert(data.returnMessage);
				  }
				}
		});
	});

});



