
$(document).ready(function(){
	initBIZAuthInfoListInfo();
	showBtn();
	//点击返回按钮
	$("#returnBtn").click(function(){
		C.load(contextPath + "/biz/ficprod/list");
	});
	$("#returnBack").click(function(){
		C.load(contextPath + "/biz/ficprod/list");
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
		var fincId = $("#fincId").val();
		var url = contextPath + "/biz/authinfo/getList";
		var params = "prodId="+fincId+"&pageNumber="+currentPage+"&limit="+limit;
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
          return "不通过";
     }
});

//新增审核意见信息信息
function addBIZAuthInfo(){
	var url = contextPath + "/biz/authinfo/insertBIZAuthInfo";
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
	
	var params = "operFlag="+operFlag+"&comments="+comments+"&prodId="+prodId
	+"&type="+type;
	//异步请求新增审核意见信息信息
	Util.ajax.postJson(url, params, function(data, flag){
		if(flag && data.returnCode=="0"){
				if(operFlag == "APPROVED"){
					if(role == 'infor'){
						updateFicProdState("2-WAITING",prodId);
					}else if(role == "cardcheck"){
						updateFicProdState("COMPLETED",prodId);
					}
				}else if(operFlag == "REJECTED"){
					if(role == "infor"){
						updateFicProdState("1-REJECTED",prodId);
					}else if(role == "cardcheck"){
						updateFicProdState("2-REJECTED",prodId);
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

//修改信用卡表状态
function updateFicProdState(authState,fincId){
	var url=contextPath+"/biz/ficprod/updateFinanceProd?authState="+authState+"&fincId="+fincId;
	var formdata = "";
	Util.ajax.postJson(url, formdata, function(data, flag){
		if(flag && data.returnCode=="0"){
			alert("审核意见信息新增成功!");
			$("#myModal").modal('hide');
			//重新请求列表数据
			C.load(contextPath+'/biz/ficprod/list');
			//清空新增审核意见信息的弹窗信息
			$("#addBIZAuthInfoForm input").val("");
			$("#descn").val("");
		}
	})
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
		 if(authState == '2-WAITING'){
			 $("#bIZAuthInfoDiv").css('display','');
			 $("#okBtn").css('display','');
		 }
	 }
}

/////////////////////////////////////////////
});

$(function(){
	var type = $("#type").val();
	if(type == "国债产品"){
		$("#gz").css('display','');
		$("#jj").css('display','none');
		$("#lc").css('display','none');
		$("#bx").css('display','none');
		$("#gjs").css('display','none');
	}else if(type == "理财产品"){
		$("#lc").css('display','');
		$("#gz").css('display','none');
		$("#jj").css('display','none');
		$("#bx").css('display','none');
		$("#gjs").css('display','none');
	}else if(type == "基金产品"){
		$("#jj").css('display','');
		$("#gz").css('display','none');
		$("#lc").css('display','none');
		$("#bx").css('display','none');
		$("#gjs").css('display','none');
	}else if(type == "保险产品"){
		$("#bx").css('display','');
		$("#gz").css('display','none');
		$("#jj").css('display','none');
		$("#lc").css('display','none');
		$("#gjs").css('display','none');
	}else if(type == "贵金属产品"){
		$("#gjs").css('display','');
		$("#bx").css('display','none');
		$("#gz").css('display','none');
		$("#jj").css('display','none');
		$("#lc").css('display','none');
	}else if(type == ""){
		$("#gjs").css('display','none');
		$("#bx").css('display','none');
		$("#gz").css('display','none');
		$("#jj").css('display','none');
		$("#lc").css('display','none');
	}
})
