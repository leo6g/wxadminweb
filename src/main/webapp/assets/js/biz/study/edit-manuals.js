
$(document).ready(function(){
	//初始化上传按钮
	getArticleStudyUpload("uploadIcon","imgPath","picIcon");
	
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
	
	//UE.getEditor('editorContent').execCommand('insertHtml', $('#edit-content').val());
	
	//获取类型
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
		var url = contextPath + "/biz/studyArticle/updateStudyArticle";
		var params = $("#addForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("文章编辑成功!");
					//重新请求列表数据
					C.load(contextPath + "/biz/studyArticle/manualsList?currPage="+Util.browser.getParameter("currPage"));
				}else{
					alert(data.returnMessage);
				}
			}else{
				if(data.returnCode=="-1"){
					alert("已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}


	//获取所有类型信息
	function queryAllStudyCategory(){
		var articleId = $("#articleId").val();
		var url = contextPath + "/biz/studyArticle/getManualsById?articleId="+articleId;
		var params = "";
		//异步请求所有职位信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//渲染数据
				var obj = data.object;
				$("#title").val(obj.title);
				$("#sortNum").val(obj.sortNum);
				var htmlStr = ' <option value="" >请选择</option><option value="user">用户</option><option value="staff">员工</option>';
				$("#subCategory").html(htmlStr);
				$("#subCategory").val(obj.subCategory);
				UE.getEditor('editorContent').execCommand('insertHtml', obj.content);
			}
		});
		
	}

});


