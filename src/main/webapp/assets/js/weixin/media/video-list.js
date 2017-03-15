$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
var materialId='';
	//初始化音频列表信息
	initvideoInfo();
		
	//查询按钮
	$("#querySubmit").click(function(){
		initvideoInfo();
	});
	//点击重置按钮
	$("#resetBtn").click(function(){
		$("#queryForm input[type='text']").val("");
		initvideoInfo();
	});
	//初始化音频列表信息
	function initvideoInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/wx/video/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求音频列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			console.log(data);
			var source = $("#video-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#videoTable").html(htmlStr);
				$("#countSpan").text("微信视频"+data.bean.count+"/1000");
				//初始化分页数据(当前页码，总数，回调查询函数)
				//initPaginator(currentPage, data.bean.count, initvideoInfo);
			}
		});
	}
	
	
	
	//上传音频
	$("#uploadBtn").click(function(){
		var url = contextPath + "/wx/video/uploadVideo";
		var params = $("#queryForm").serialize();
		Util.ajax.postJson(url, params, function(data, flag){
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){

			}
		});
	});
	
	
	
	//添加音频页面
	$("#addVideo").click(function(){
		C.load(contextPath+'/wx/video/add');
	});
	
	//删除图片信息
	$("body").on('click','.deleteVideo',function(){
		var materialId=$(this).attr("materialId");
		var mediaId=$(this).attr("mediaId");
		var url = contextPath + "/wx/video/deleteVideo?materialId="+materialId+"&mediaId="+mediaId;
		var params = "";
		//删除按钮信息
		confirm("确定删除吗？", function(){
				Util.ajax.postJson(url, params, function(data, flag){
					if(flag && data.returnCode=="0"){
			           alert(data.returnMessage);
			           //重新加载列表页面
			           initvideoInfo();    
					}
					else{
						alert(data.returnMessage);
					}
				});
		});
	});
	
	
	
	//弹出编辑音频信息窗
	$("body").on('click','.editVideo',function(){
		materialId=$(this).attr("materialId");
		var name=$(this).attr("name");
		var subType=$(this).attr("subType");
		var videoTags=$(this).attr("videoTags");
		var videoInstru=$(this).attr("videoInstru");
		$("#editName").val(name);
		$("#editSubType").val(subType);
		$("#editvideoTags").val(videoTags);
		$("#editvideoInstru").val(videoInstru);
		$("#myModal1").modal('show');
	});
	
	
	//编辑音频信息
	$("body").on('click','#editVideoInfo',function(){
		var url = contextPath + "/wx/video/updateVideo?materialId="+materialId;
		var name=$("#editName").val();
		var subType=$("#editSubType").val();
		var videoTags=$("#editvideoTags").val();
		var videoInstru=$("#editvideoInstru").val();
		var params = {};
		params.name=name;
		params.subType=subType;
		params.videoTags=videoTags;
		params.videoInstru=videoInstru;
		//删除按钮信息
		Util.ajax.postJson(url, params, function(data,flag){
					if(flag && data.returnCode=="0"){
					   $("#editName").val('');
					   $("#editSubType").val('');
					   $("#editvideoTags").val('');
					   $("#editvideoInstru").val('');
				       $("#myModal1").modal('hide');
			           alert(data.returnMessage);
			           //重新加载页面
			           initvideoInfo();
					}
					else{
						alert(data.returnMessage);
					}
				});
	});
	//同步微信端视频信息
	$("#syncVideo").click(function(){
		$('body').showLoading();
		var url = contextPath + "/wx/video/syncVideo";
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
						initvideoInfo();
					}
				  }else {
					  $('body').hideLoading();
					  alert(data.returnMessage);
				  }
				}
		});
	});

});



