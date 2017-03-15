
   //实例化一个plupload上传对象
    var uploader = new plupload.Uploader({ 
		browse_button : 'imagePath',
		url : contextPath+'/wx/newsitems/doUpload',
        flash_swf_url : '/wxadminweb/assets/plupload/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
        silverlight_xap_url : '/wxadminweb/assets/plupload/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
        filters: {
       	  mime_types : [ //只允许上传图片和zip文件
       	    { title : "Image files", extensions : "jpg,jpeg,gif,png" }, 
       	    { title : "Zip files", extensions : "zip" }
       	  ],
       	  max_file_size : '10mb', //最大只能上传2M的文件
       	  prevent_duplicates : true //不允许选取重复文件
        },
        multi_selection: false
    });
    
    
	uploader.init(); //初始化

	//绑定文件添加进队列事件
	uploader.bind('FilesAdded',function(uploader,files){
	    $.each(uploader.files, function (i, file) {
	        if (uploader.files.length <= 1) {
	            return;
	        }
	        uploader.removeFile(file);
	    });
        var file_name = files[0].name;
        $("#imagePath").val(file_name);
	});

	
	
	//当上传队列中一个文件上传成功后，多个文件上传用UploadComplete
	uploader.bind('FileUploaded',function(uploader,file,responseObject){
          var data=jQuery.parseJSON(responseObject.response);
          var imagePath=data.bean.imagePath;
      	  $("#imagePath").val(imagePath);
      	  alert($("#imagePath").val());
      	  var formdata=$("#formdata").serialize();
      	  var newsTempId=$("#newsTempId").val();
   	      var url=contextPath+"/wx/newsitems/updateWXNewsItems";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("微信图文详情编辑成功!",$.scojs_message.TYPE_OK);
				C.load(contextPath+"/wx/newstemplates/newsItemEdit?newsTempId="+newsTempId);
				
			}
		});
	});
	

	//上传按钮
	$('#submitData').click(function(){
		var oldImagePath=$("#oldImagePath").val();
		var imagePath=$("#imagePath").val();
		var newsTempId=$("#newsTempId").val();
		//图标没有修改 直接更新信息
		if(oldImagePath==imagePath){
			  var formdata=$("#formdata").serialize();
	      	  var itemId = Util.browser.getParameter("itemId");
	      	  //formdata=formdata+"&imagePath="+oldImagePath;
			  var url=contextPath+"/wx/newsitems/updateWXNewsItems";
			  Util.ajax.postJson(url, formdata, function(data, flag){
					if(flag && data.returnCode=="0"){
						alert("微信图文详情编辑成功!",$.scojs_message.TYPE_OK);
						C.load(contextPath+"/wx/newstemplates/newsItemEdit?newsTempId="+newsTempId);
					}
				});
		}else{
			//图标有修改的话，需要先上传图标然后再更新信息
			uploader.start(); //开始上传
		}
	});

	$(document).ready(function(){
		//查询菜单详细信息
		queryNewsItemsById();
	});
	//初始化菜单信息
	function queryNewsItemsById(){
		var itemId = $("#itemId").val();
		var url = contextPath + "/wx/newsitems/getById?itemId="+itemId;
		var params = "";
		//异步请求职位列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = eval(data.object);
				$("#title").val(viewinfo.title);
				$("#createUser").val(viewinfo.createUser);
				$("#imagePath").val(viewinfo.imagePath);
				$("#oldImagePath").val(viewinfo.imagePath);
				$("#type").val(viewinfo.type);
				$("#url").val(viewinfo.url);
				if(viewinfo.showAdver == 'T'){
					$("#okAdver").prop('checked', true);
				}else{
					$("#noAdver").prop('checked', true);
				}
				
				if(viewinfo.showPraise == 'T'){
					$("#okPraise").prop('checked', true);
				}else{
					$("#noPraise").prop('checked', true);
				}
				
				if(viewinfo.showRemark == 'T'){
					$("#okRemark").prop('checked', true);
				}else{
					$("#noRemark").prop('checked', true);
				}
				UE.getEditor('editorContent').execCommand('insertHtml', viewinfo.content);
				/*$("#editorContent").val(viewinfo.content);*/
				$("#sort").val(viewinfo.sort);
				$("#newsTempId").val(viewinfo.newsTempId);
				$("#itemId").val(itemId);
				$("#author").val(viewinfo.author);
			}
		});
	};
	
	function backNewsItems(){
		var newsTempId=$("#newsTempId").val();
		var htmlHostTittle = $("#htmlHostTittle").val();
		var htmlTittle = $("#htmlTittle").val();
		C.load(contextPath+"/wx/newstemplates/newsItemEdit?newsTempId="+newsTempId+"&htmlTittle="+htmlTittle+"&htmlHostTittle="+htmlHostTittle);
	}
	
	/*$(function(){
		var htmlHostTittle = $("#htmlHostTittle").val();
		if(htmlHostTittle == "微信文章"){
			$("#articleTr").css('display','');
			$("#articleTr input").removeAttr("disabled");
		}else{
			$("#articleTr").css('display','none');
			$("#articleTr input").attr("disabled","disabled");
		}
	});*/