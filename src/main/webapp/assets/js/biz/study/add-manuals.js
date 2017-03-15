
$(document).ready(function(){
	//初始化上传按钮
	getArticleStudyUpload("uploadIcon","imgPath","picIcon");
	//获取当前日期放入有效开始时间内
	var nowDateStr = Util.date.getDate();
	$("#validBeginTime").val(nowDateStr);
	
	
	//新增信息时，validate验证，验证通过后调用保存方法 
	$("#addForm").validate({
        submitHandler:function(form){
        	insertObj();
        }    
    });
	
	//新增信息
	$("#saveBtn").click(function(){
		var form = $("#addForm");
		form.submit();
	});
	
	queryAllStudyCategory();
	
	
	
	//点击取消按钮
	$("#cancelBtn").click(function(){
		C.load(contextPath + "/biz/studyArticle/manualsList");
	});
	
	$("#returnBack").click(function(){
		C.load(contextPath + "/biz/studyArticle/manualsList");
	});
	
	
	//新增文章信息
	function insertObj(){
		var url = contextPath + "/biz/studyArticle/insertStudyArticle";
		var params = $("#addForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("操作手册文章新增成功!");
					//重新请求列表数据
					C.load(contextPath + "/biz/studyArticle/manualsList?currPage="+Util.browser.getParameter("currPage"));
				}else{
					alert(data.returnMessage);
				}
			}else{
				if(data.returnCode=="-1"){
					alert("操作手册文章已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}


	//获取所有职位信息
	function queryAllStudyCategory(){
		/*var url = contextPath + "/biz/studyArticle/getAllStudyCategory";
		var params = "";
		//异步请求所有职位信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#position-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#categoryId").html(htmlStr);
			}
		});*/
		var htmlStr = ' <option value="" >请选择</option><option value="user">用户</option><option value="staff">员工</option>';
		$("#subCategory").html(htmlStr);
	}

});


