
$(document).ready(function(){
		//查询菜单详细信息
		queryLoanById();
		
		//初始化审核信息列表
		initBIZAuthInfoListInfo();
		
		//初始化功能按钮
		showBtn();
		
		//新增审核意见信息信息
		$("#okBtn").click(function(){
			addBIZAuthInfo();
		});
		
		//返回
		$("#backBtn").click(function(){
			backHome();
		});
		
		//初始化贷款审核列表信息
		function initBIZAuthInfoListInfo(currentPage, limit){
			if(typeof currentPage == "undefined"){
				currentPage = 1;
			}
			if(typeof limit == "undefined"){
				limit = global_limit;
			}
			var prodId = $("#prodId").val();
			var url = contextPath + "/biz/authinfo/getList";
			var params = "prodId="+prodId+"&pageNumber="+currentPage+"&limit="+limit;
			//异步请求信用卡信息发布表列表数据
			Util.ajax.postJson(url, params, function(datas, flag){
				for (var i = 0; i < datas.beans.length; i++) {
					if (datas.beans[i].operFlag=="1-WAITING" || datas.beans[i].operFlag=="2-WAITING") {
						datas.beans[i].operFlag="待审批";
					}else if (datas.beans[i].operFlag=="COMPLETED") {
						datas.beans[i].operFlag="已发布";
					}else if (datas.beans[i].operFlag=="1-REJECTED" || datas.beans[i].operFlag=="2-REJECTED") {
						datas.beans[i].operFlag="审核不通过";
					}
				}
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
	
	
	//初始化菜单信息
	function queryLoanById(){
		var prodId = $("#prodId").val();
		var url = contextPath + "/biz/loanprodinfo/getById?prodId="+prodId;
		var params = "";
		//异步请求职位列表数据
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				//基本信息
				var viewinfo = eval(data.object);
				$("#name").val(viewinfo.name);
				$("#type").val(viewinfo.type);
				$("#comments").val(viewinfo.comments);
				var state = viewinfo.state;
				if(state == 'onSale'){
					state = '上架';
				}else if(state == 'offSale'){
					state = '下架';
				}
				
				$("#state").val(state);
				$("#authState").val(viewinfo.authState);
				$("#imagePath").val(viewinfo.imagePath);
				$('#preview').html($( '<img src="/wxadminweb/viewImage/viewImage?imgPath='+viewinfo.imagePath+'" style="width:380px;height:200px;" alt="正在加载..." />'));
				$("#prodId").val(viewinfo.prodId);
				$("#useage").val(viewinfo.useage);
			}
		});
	};
	
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
							updateWXCardInfoState("2-WAITING",prodId);
						}else if(role == "cardcheck"){
							updateWXCardInfoState("COMPLETED",prodId);
						}
					}else if(operFlag == "REJECTED"){
						if(role == "infor"){
							updateWXCardInfoState("1-REJECTED",prodId);
						}else if(role == "cardcheck"){
							updateWXCardInfoState("2-REJECTED",prodId);
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
	
	//返回贷款管理页面
	function backHome(){
		C.load(contextPath + "/biz/loanprodinfo/list");
	}
	
	//修改贷款审核状态
	function updateWXCardInfoState(authState,prodId){
		var url=contextPath+"/biz/loanprodinfo/updateWXLoanProdInfo";
		var formdata = "authState="+authState+"&prodId="+prodId;
		Util.ajax.postJson(url, formdata, function(data, flag){
			if(flag && data.returnCode=="0"){
				alert("审核意见信息新增成功!");
				//重新请求列表数据
				C.load(contextPath+'/biz/loanprodinfo/list');
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
	
////////////////////////////////////////////////////////////	
	});
	
		
	
	
	
	