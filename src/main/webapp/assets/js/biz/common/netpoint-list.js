$(document).ready(function(){
	
//列表分页每页显示多少条记录
var global_limit = 10 ;
	//初始化网点信息列表信息
	initNetPointListInfo();
	queryAllCity();
	
	//新增网点信息信息时，validate验证，验证通过后调用保存方法 
	$("#addNetPointForm").validate({
        submitHandler:function(form){
        	addNetPoint();
        }    
    });
	
	//新增网点信息信息
	$("#saveNetPointBtn").click(function(){
		var imagePath=$("#imagePath").val();
		if(imagePath==null || imagePath.trim()==""){
			alert("图片链接不能为空!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		uploader.start();
	});
	
	//选中网点信息信息
	$("#netPointListTable").delegate("tr","click",function(){
		var latitude = $(this).find("td:eq(1)").html();
		var orgAblib = $(this).find("td:eq(2)").html();
		var longitude = $(this).find("td:eq(3)").html();
		var agencySort = $(this).find("td:eq(4)").html();
		var orgAblia = $(this).find("td:eq(5)").html();
		var city = $(this).find("td:eq(6)").html();
		var town = $(this).find("td:eq(7)").html();
		var rglmCode = $(this).find("td:eq(8)").html();
		var areaAttr = $(this).find("td:eq(9)").html();
		var orgShtName = $(this).find("td:eq(10)").html();
		var imagePath = $(this).find("td:eq(11)").html();
		var orgName = $(this).find("td:eq(12)").html();
		var newOrgId = $(this).find("td:eq(13)").html();
		var orgFlag = $(this).find("td:eq(14)").html();
		var orgAbli = $(this).find("td:eq(15)").html();
		var orgLvl = $(this).find("td:eq(16)").html();
		var oldOrgId = $(this).find("td:eq(17)").html();
		var pagencyId = $(this).find("td:eq(18)").html();
		var orgStat = $(this).find("td:eq(19)").html();
		var orgAttr = $(this).find("td:eq(20)").html();
		var addr = $(this).find("td:eq(21)").html();
		var bizTypes = $(this).find("td:eq(23)").html();
		
		var url = contextPath + "/biz/netpoint/getById";
		var params={"newOrgId":newOrgId}
		//异步请求网点数据
		
		Util.ajax.postJson(url, params, function(data, flag){
			$("#oldImagePath").val(data.object.imagePath);
		});
		$("#edit-latitude").val(latitude);
		$("#edit-orgAblib").val(orgAblib);
		$("#edit-longitude").val(longitude);
		$("#edit-agencySort").val(agencySort);
		$("#edit-orgAblia").val(orgAblia);
		$("#edit-city").val(city);
		$("#edit-town").val(town);
		$("#edit-rglmCode").val(rglmCode);
		$("#edit-areaAttr").val(areaAttr);
		$("#edit-orgShtName").val(orgShtName);
		$("#edit-imagePath").val(imagePath);
		$("#edit-orgName").val(orgName);
		$("#edit-newOrgId").val(newOrgId);
		$("#edit-orgFlag").val(orgFlag);
		$("#edit-orgAbli").val(orgAbli);
		$("#edit-orgLvl").val(orgLvl);
		$("#edit-oldOrgId").val(oldOrgId);
		$("#edit-pagencyId").val(pagencyId);
		$("#edit-orgStat").val(orgStat);
		$("#edit-orgAttr").val(orgAttr);
		$("#edit-addr").val(addr);
		$("#edit-bizTypes").val(bizTypes);
		
		$("tr").removeClass("tr-color");
		$(this).addClass("tr-color");
		return false;
	});
	
	
	//修改网点信息信息时，jqvalidate验证，验证通过后调用保存方法 
	$("#editNetPointForm").validate({
        submitHandler:function(form){
        	editNetPoint();
        }    
    });
	
	//保存编辑的网点信息信息
	$("#edit-saveNetPointBtn").click(function(){
		
		var latitude = $("#edit-latitude").val();
		var longitude = $("#edit-longitude").val();
		var imagePath = $("#edit-imagePath").val();
		var oldImagePath=$("#oldImagePath").val();
		
		if(isNaN(latitude) || isNaN(longitude)){
			alert("请输入数字!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		

		
		//if(imagePath==null || imagePath.trim()==""){

			$("#edit-imagePath").val(oldImagePath);
			var formdata=$("#editNetPointForm").serialize();
			console.info(formdata);
			var url=contextPath+"/biz/netpoint/updateNetPoint";
			Util.ajax.postJson(url, formdata, function(data, flag){
				  if(flag && data.returnCode=="0"){
					alert("网点信息更新成功!",$.scojs_message.TYPE_OK);
					initNetPointListInfo();
					$("#myModal1").modal('hide');
				  }else{
					  alert("网点信息更新成功!",$.scojs_message.TYPE_OK);
				  }
				});
		//}
		//editNetPointUploader.start(); 
	});
				
	//编辑网点信息信息
	$("#editNetPointBtn").click(function(){
		var newOrgId = $("#edit-newOrgId").val();
		if(newOrgId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		$("#edit-imagePath").val("");
		var imagePath = $("#oldImagePath").val();
		$('#edit-preview').html($( '<img src="/wxadminweb/viewImage/viewImage?imgPath='+imagePath+'" style="width:380px;height:200px;" alt="正在加载..." />'));
	});
	
	//删除网点信息信息
	$("#delNetPointBtn").click(function(){
		//logicDelNetPoint();//逻辑删除
		delNetPoint()
	});
	
	//查询按钮
	$("#querySubmit").click(function(){
		initNetPointListInfo();
	});
	//局部绑定回车事件
	 $("#queryOrgShtName").bind('keyup',function(event){
		    event=document.all?window.event:event;
		    if((event.keyCode || event.which)==13){
		    	document.getElementById("querySubmit").click();
		    }
		   }); 
	//初始化网点信息列表信息
	function initNetPointListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var url = contextPath + "/biz/netpoint/getList";
		var params = $("#queryForm").serialize();
		params = params + "&pageNumber="+currentPage+"&limit="+limit;
		//异步请求网点信息列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#netPoint-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#netPointListTable").html(htmlStr);
				//初始化分页数据(当前页码，总数，回调查询函数)
				initPaginator(currentPage, data.bean.count, initNetPointListInfo);
			}
		});
	}
	
	
	//新增网点信息信息
	function addNetPoint(){
		var url = contextPath + "/biz/netpoint/insertNetPoint";
		var params = $("#addNetPointForm").serialize();
		//异步请求新增网点信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					//$("#alert-info-content").html("网点信息新增成功!");
					//$("#alert-info").modal("show");
					alert("网点信息新增成功!");
					$("#myModal").modal('hide');
					//重新请求列表数据
					initNetPointListInfo();
					//清空新增网点信息的弹窗信息
					$("#addNetPointForm input").val("");
					$("#descn").val("");
				}
			}else{
				if(data.returnCode=="-1"){
					alert("网点信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	
	
	//修改网点信息信息
	function editNetPoint(){
		var url = contextPath + "/biz/netpoint/updateNetPoint";
		var params = $("#editNetPointForm").serialize();
		//异步请求修改网点信息信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("网点信息信息修改成功!");
					//编辑网点信息信息清空
					$("#editNetPointForm input").val("");
					$("#edit-descn").val("");
					//重新请求列表数据
					initNetPointListInfo();
					$("#myModal1").modal('hide');
				}
			}else{
				if(data.returnCode=="-1"){
					alert("网点信息编码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}
	//删除网点信息
	function delNetPoint(){
		var newOrgId = $("#edit-newOrgId").val();
		if(newOrgId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/netpoint/deleteNetPoint";
			var params = $("#editNetPointForm").serialize();
			//异步请求逻辑删除网点信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("网点信息删除成功!");
						//编辑网点信息信息清空
						$("#editNetPointForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initNetPointListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//逻辑删除网点信息
	function logicDelNetPoint(){
		var newOrgId = $("#edit-newOrgId").val();
		if(newOrgId==""){
			alert("请选择一条信息!",$.scojs_message.TYPE_ERROR);
			return false;
		}
		confirm("确定删除吗？",function(){
			var url = contextPath + "/biz/netpoint/logicDeleteNetPoint";
			var params = $("#editNetPointForm").serialize();
			//异步请求逻辑删除网点信息信息
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag){
					if(data.returnCode=="0"){
						alert("网点信息删除成功!");
						//编辑网点信息信息清空
						$("#editNetPointForm input").val("");
						$("#edit-descn").val("");
						//重新请求列表数据
						initNetPointListInfo();
					}
				}else{
					alert(data.returnMessage);
				}
			});
		});
	}
	
	//实例化一个plupload上传对象
	var editNetPointUploader = new plupload.Uploader({ 
		browse_button : 'edit-imagePath',
		url : contextPath+'/biz/netpoint/doUpload',
	    flash_swf_url : '${req.contextPath}/assets/plupload/Moxie.swf', //swf文件，当需要使用swf方式进行上传时需要配置该参数
	    silverlight_xap_url : '${req.contextPath}/assets/plupload/Moxie.xap', //silverlight文件，当需要使用silverlight方式进行上传时需要配置该参数
	    filters: {
	   	  mime_types : [ //只允许上传图片和zip文件
	   	    { title : "Image files", extensions : "jpg,jpeg,gif,png" }, 
	   	    { title : "Zip files", extensions : "zip" }
	   	  ],
	   	  max_file_size : '10mb', //最大只能上传2M的文件
	   	  prevent_duplicates : true //不允许选取重复文件
	    },
	    multi_selection: true
	});
	
	editNetPointUploader.init(); //初始化

	//绑定文件添加进队列事件
	editNetPointUploader.bind('FilesAdded',function(uploader,files){
		  $.each(uploader.files, function (i, file) {
		        if (uploader.files.length <= 1) {
		            return;
		        }
		        uploader.removeFile(file);
		    });
		  //预览图片函数
		previewImage(files[0], function (imgsrc) {
	        $('#edit-preview').html($( '<img width="380" height="200" src="' + imgsrc + '" name="' + files[0].name + '" />'));
	    });
	    var file_name = files[0].name;
	    $("#edit-imagePath").val(file_name);
	});
	
	//当上传队列中一个文件上传成功后，多个文件上传用UploadComplete
	editNetPointUploader.bind('FileUploaded',function(uploader,file,responseObject){
	      var data=jQuery.parseJSON(responseObject.response);
	      var imagePath=data.bean.imagePath;
	      
	      $("#edit-imagePath").val(imagePath);
	  	  var formdata=$("#editNetPointForm").serialize();
		  var url=contextPath+"/biz/netpoint/updateNetPoint";
		  Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("网点信息更新成功!",$.scojs_message.TYPE_OK);
				window.location.href=contextPath+"/biz/netpoint/list";
			}
		});
	});
	//绑定文件上传进度事件
	editNetPointUploader.bind('UploadProgress',function(uploader,file){
		$('#file-'+file.id+' .progress').css('width',file.percent + '%');//控制进度条
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
	}
	
	//获取所有二分信息
	function queryAllCity(){
		var url = contextPath + "/biz/netpoint/netGetOrgInfo?type=city";
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#city-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#cityList").html(htmlStr);
				$("#cityList").change(function(){
					var city=$(this).val();
					if(city != null && city !=''){
						queryAllTown(city);
					}else{
						$("#townList").val("");
					}
				});
				queryAllTown(data.beans[0].newOrgId);
			}
		});
	}
	
	//获取所有一支信息
	function queryAllTown(cityId){
		var url = contextPath + "/biz/netpoint/netGetOrgInfo?type=town&city="+cityId;
		var params = "";
		//异步请求所有信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#town-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#townList").html(htmlStr);
			}
		});
	}
});



