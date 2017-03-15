$(document).ready(function(){
		 //实例化一个plupload上传对象
	    var uploader = new plupload.Uploader({ 
			browse_button : 'imagePath',
			url : contextPath+'/wx/newstemplates/doUpload',
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
		    //预览图片函数
		    previewImage(files[0], function (imgsrc) {
	            $('#preview').html($(
	            '<img width="380" height="200" src="' + imgsrc + '" name="' + files[0].name + '" />'));
	        })
	        var file_name = files[0].name;
	        $("#imagePath").val(file_name);
		});

		
		
		//当上传队列中一个文件上传成功后，多个文件上传用UploadComplete
		uploader.bind('FileUploaded',function(uploader,file,responseObject){
	          var data=jQuery.parseJSON(responseObject.response);
	          var imagePath=data.bean.imagePath;
	          $("#imagePath").val(imagePath);
	          //$("#content").val(UE.getEditor('editor').getContent());
	      	  var formdata=$("#formdata").serialize();
	   	      var url=contextPath+"/wx/newstemplates/insertWXNewsItems";
			  Util.ajax.postJson(url, formdata, function(data, flag){
				if(flag && data.returnCode=="0"){
					alert("微信图文详情添加成功!",$.scojs_message.TYPE_OK);
					backPageByAdd();
				}
			});
		});
		

		//上传按钮
		$('#saveNewsItem').click(function(){
			var title=$("#title").val();
			var imagePath=$("#imagePath").val();
			var sort=$("#sort").val();
			if(title==null || title.trim()==""){
				alert("标题不能为空!",$.scojs_message.TYPE_ERROR);
				return false;
			}
			if(imagePath==null || imagePath.trim()==""){
				alert("图片链接不能为空!",$.scojs_message.TYPE_ERROR);
				return false;
			}
			if(sort==null || sort.trim()==""){
				alert("顺序不能为空!",$.scojs_message.TYPE_ERROR);
				return false;
			}
			uploader.start(); //开始上传
		});
		
		//图片上传预览
		function previewImage(file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数
	        if (!file || !/image\//.test(file.type)) return; //确保文件是图片
	        if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
	            var fr = new mOxie.FileReader();
	            fr.onload = function () {
	                callback(fr.result);
	                fr.destroy();
	                fr = null;
	            }
	            fr.readAsDataURL(file.getSource());
	        } else {
	            var preloader = new mOxie.Image();
	            preloader.onload = function () {
	                var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
	                callback && callback(imgsrc); //callback传入的参数为预览图片的url
	                preloader.destroy();
	                preloader = null;
	            };
	            preloader.load(file.getSource());
	        }
	    };
		
		
		
	});
	
//选择返回跳转页面
function backPageByAdd(){
	var param = $("#htmlHostTittle").val();
	if("微信文章" == param.trim()){
		C.load(contextPath+"/wx/article/list");
	}else if("菜单内容" == param.trim()){
		C.load(contextPath+"/wx/newstemplates/list");
	}
}

/*$(function(){
	var htmlHostTittle = $("#htmlHostTittle").val();
	if(htmlHostTittle == "微信文章"){
		$("#adverTr").css('display','');
		$("#praiseTr").css('display','');
		$("#RemarkTr").css('display','');
		$("#praiseTr input").removeAttr("disabled");
		$("#adverTr input").removeAttr("disabled");
		$("#RemarkTr input").removeAttr("disabled");
	}else{
		$("#adverTr").css('display','none');
		$("#praiseTr").css('display','none');
		$("#RemarkTr").css('display','none');
		$("#praiseTr input").attr("disabled","disabled");
		$("#adverTr input").attr("disabled","disabled");
		$("#RemarkTr input").attr("disabled","disabled");
	}
});*/
	
	
