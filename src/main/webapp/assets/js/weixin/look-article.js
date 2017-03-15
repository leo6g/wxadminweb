
$(document).ready(function(){
	//加载模板信息
	queryWXArticleById();
	//加载审批信息
	initBIZAuthInfoListInfo();
	showBtn();
	//点击返回按钮
	$("#returnBtn").click(function(){
		C.load(contextPath + "/wx/article/list");
	});
	
	$("#returnBack").click(function(){
		C.load(contextPath + "/wx/article/list");
	});
	//点击确定按钮
	$("#okBtn").click(function(){
		addBIZAuthInfo();
	});
	
	//初始化信用卡审核列表信息
	function initBIZAuthInfoListInfo(currentPage, limit){
		if(typeof currentPage == "undefined"){
			currentPage = 1;
		}
		if(typeof limit == "undefined"){
			limit = global_limit;
		}
		var newsTempId = $("#newsTempId").val();
		var url = contextPath + "/biz/authinfo/getList";
		var params = "prodId="+newsTempId+"&pageNumber="+currentPage+"&limit="+limit;
		//异步请求信用卡信息发布表列表数据
		Util.ajax.postJson(url, params, function(datas, flag){
			var source = $("#bIZAuthInfo-list-template").html();
			var templet = Handlebars.compile(source);
			
			if(flag && datas.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(datas.beans);
				
				$("#bIZAuthInfoListTable").html(htmlStr);
			}
		});
	};
//动态替换审批详情审批状态
Handlebars.registerHelper("transformat",function(value){
     if(value=="WAITING"){
    	  return "已提交";
     }else if(value=="APPROVED"){
          return "通过";
     }else if(value=="REJECTED"){
          return "驳回";
     }
});

//新增审核意见信息信息
function addBIZAuthInfo(){
	var operFlag = $("#add-operFlag").val();
	var comments = $("#add-comments").val();
	var prodId = $("#add-prodId").val();
	var type = $("#add-type").val();
	var role = $("#role").val();
	if(comments == ''){
		if(operFlag == "APPROVED"){
			comments = "同意。。。";
		}else if(operFlag == "REJECTED"){
			comments = "驳回。。。";
		}
	}
	var url = contextPath + "/biz/authinfo/insertBIZAuthInfo";
	var params = "operFlag="+operFlag+"&comments="+comments+"&prodId="+prodId
	+"&type="+type;
	//异步请求新增审核意见信息信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			//根据角色确定审批信息表状态
				if(operFlag == "APPROVED"){
					if(role == 'infor'){//信息办
						updateWXNewsTempState("2-WAITING",prodId);
					}else if(role == "cardcheck"){//电子银行部
						updateWXNewsTempState("COMPLETED",prodId);
					}else if(role == "law_spec"){//法规办
						updateWXNewsTempState("3-WAITING",prodId);
					}
				}else if(operFlag == "REJECTED"){
					if(role == "infor"){//信息办
						updateWXNewsTempState("1-REJECTED",prodId);
					}else if(role == "cardcheck"){//电子银行部
						updateWXNewsTempState("3-REJECTED",prodId);
					}else if(role == "law_spec"){//法规办
						updateWXNewsTempState("2-REJECTED",prodId);
					}
				}
		}else{
			if(data.returnCode=="-1"){
				alert("审核意见信息编码已经存在，请修改!",$.scojs_message.TYPE_ERROR);
			}else{
				alert(data.returnMessage);
			}
		}
	});
};

//按钮隐藏与显示
function showBtn(){
	var role = $("#role").val();
	var authState = $("#authState").val();
	 if(role == 'infor'){
		 if(authState == '1-WAITING'){
			 $("#bIZAuthInfoDiv").css('display','');
			 $("#okBtn").css('display','');
		 }
	 }else if(role == 'cardcheck'){
		 if(authState == '3-WAITING'){
			 $("#bIZAuthInfoDiv").css('display','');
			 $("#okBtn").css('display','');
		 }
	 }else if(role == 'law_spec'){
		 if(authState == '2-WAITING'){
			 $("#bIZAuthInfoDiv").css('display','');
			 $("#okBtn").css('display','');
		 }
	 }
}

//初始化菜单信息
function queryWXArticleById(){
	var newsTempId = $("#newsTempId").val();
	var url = contextPath + "/wx/article/getById?newsTempId="+newsTempId;
	var params = "";
	//异步请求职位列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
			//基本信息
			var viewinfo = eval(data.object);
			$("#name").text(viewinfo.name);
			var type = viewinfo.type;
			var sendState = viewinfo.sendState;
			if(type == 'menu'){
				type = '菜单';
			}else if(type == 'subscribe'){
				type = '关注语';
			}else if(type =="news"){
				type =  "新闻";
	        }else if(type =="push"){
	        	type =  "推送";
	        }
			if(sendState=="T"){
				 sendState= "已发送";
			     }else if(sendState=="F"){
			    	 sendState= "未发送";
		       }
			$("#type").html(type);
			$("#publishDate").html(viewinfo.publishDate);
			$("#weeklyNumber").html(viewinfo.weeklyNumber);
			$("#sendState").html(sendState);
		}
	});
};

//修改模板表状态
function updateWXNewsTempState(authState,newsTempId){
	
	var url=contextPath+"/wx/article/updateWXArticle";
	var formdata = "authState="+authState+"&newsTempId="+newsTempId;
	Util.ajax.postJson(url, formdata, function(data, flag){
		if(flag && data.returnCode=="0"){
			alert("审核成功!");
			//重新请求列表数据
			C.load(contextPath+'/wx/article/list');
		}
	})
};
/////////////////////////////////////////////
});


