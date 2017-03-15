var tagId=null;
var count=0;
var groupName=null;
var remark_openId;//点击修改备注按钮时对应的openid
var remark_wxId;//点击修改备注按钮时对应的用户id
var allTags = {};//所有分组及其名称
var maxTagSize = 0;//当页用户最大标签量(打标签的时候最多只能选择3-最大标签量) 
var openIds=[];//根据分组获取的用户opendId
var global_limit = 5;
var groupSize=14//分组个数
var allBlackUser = [];//存放所有黑名单
var blackTotal;//黑名单总量
//把标签id转换成标签名
Handlebars.registerHelper('parseTag', function(tag_list, options){
	var result = "";
	if(tag_list && tag_list.length > 0){
		for (var int = 0; int < tag_list.length; int++) {
			if(typeof allTags[tag_list[int]] != "undefined") {
				result += allTags[tag_list[int]] + " "
			}
		}
	} else {
		result = "无标签";
	}
	return result;
});

//把时间戳转为日期
Handlebars.registerHelper('parseDate', function(subscribeTime, options){
	var d = new Date(subscribeTime * 1000);
	return d.Format("yyyy-MM-dd hh:mm:ss");
});
$(document).ready(function(){
	//初始化微信关注用户表列表信息
	initWXUserListInfo();
	initWXGroupListInfo();
	initWXBlackUserList(1,global_limit,true);
	//关注下方绿色滑块
	$(".guanzhu").click(function() {
		$(".line").css({
			left: "0",
			top: "16px",
		});
	})
	//黑名单下方绿色滑块
	$(".blackname").click(function() {
		$(".line").css({
			left: "120px",
			top: "16px",
		});
	})
		//点击弹出框内确定或者取消 隐藏
	$(".btn-btn input").click(function(e) {
		if($(e.target).attr("id") != "saveTagBtn") {
			$(".out01").css({
				"display": "none"
			});
		}
	});
	//点击黑名单跳转
	$(".blackname").click(function() {
		$(".alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname,.alert-staruser1").css({
			"display": "none"
		});
		$(".user-guanzhu-list").css({
			"display": "none"
		});
		$(".user-black-list").css({
			"display": "block"
		});

	});
	//点击已关注 跳转
	$(".guanzhu").click(function() {
		$(".alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname").css({
			"display": "none"
		});
		$(".user-guanzhu-list").css({
			"display": "block"
		});
		$(".user-black-list, .remove-blacklist").css({
			"display": "none"
		});

	});
	//加入黑名单
	$(".blacklist").click(function() {
		$(".alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname,.remove-blacklist,.alert-staruser1").css({
			"display": "none"
		});
		$(".alert-addblackname").css({
			"display": "block"
		});

	});
	//打标签
	$(".label-label").click(function() {
		$(".alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname,.remove-blacklist,.alert-staruser1").css({
			"display": "none"
		});
		$(".alert-staruser").css({
			"display": "block"
		});
	});
	//打标签取消按钮
	$("#cancelTagBtn").click(function(){
		$("#wxUserTag .cb:checked").each(function(){
			$(this).prop("checked",false);
		});
		$("#tagInfo").hide();
		$(".alert-staruser").hide();
	});
	//移除黑名单
	$(".label-black").click(function() {
		$(".alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname,.remove-blacklist,.alert-staruser1").css({
			"display": "none"
		});
		var top = $(this).offset().top -50;
		$(".alert-blackname").css({
			"display": "block",
			"top": top,
			"left": "158px"
		});
	});
	//新建标签
	$(".username-right").click(function() {
		$("#groupName").val("");
		$("#saveGroup").hide();
		$("#updateGroup").hide();
		$("#saveGroup").show();
		$(".alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname,.remove-blacklist,.alert-staruser1").css({
			"display": "none"
		});
		$(".alert-biaoqian").css({
			"display": "block"
		});
	});
	//编辑标签
	$(".username-update").click(function() {
		$("#saveGroup").hide();
		$("#updateGroup").hide();
		if(groupName==null){
			alert("请选择分组");
			return;
		}
		$("#updateGroup").show();
		$(".alert-bianji, .alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname,.remove-blacklist,.alert-staruser1").css({
			"display": "none"
		});
		$(".alert-biaoqian").css({
			"display": "block"
		});
		$("#groupName").val(groupName);
	});
	//删除标签
	$(".username-delete").click(function() {
		if(tagId==null){
			alert("请选择分组");
			return;
		}
		confirm("确认删除分组吗？",function(){
			var url = contextPath + "/wx/usergroup/deleteWXUserGroup";
			var params = {};
			params.tagId=tagId;
			Util.ajax.postJson(url, params, function(data, flag){
				if(flag && data.returnCode=="0"){
					if(data.object == "SUCCESS"){
						alert("操作成功");
					} else {
						alert("操作失败",$.scojs_message.TYPE_ERROR);
					}
					tagId=null;
					$("#allUserP").click();
					initWXGroupListInfo();
				}
			});
		},function(){
			return false;
		});
	});
	//点击无标签部分
	$(" .user-list-allSelect div span:last-child").click(function(){
		var top = $(this).offset().top - 60;
			$(".alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname,.remove-blacklist,.alert-staruser1").css({
			"display": "none"
		});
		$(".alert-staruser1").css({
			"display": "block",
			"top": top,
			"left": "-90px"
		});
	});
	//点击移除黑名单
	$(".remove-blacklist-btn").click(function(){
			var top = $(".remove-blacklist-btn ").offset().top-45;
			$(".alert-blackname1").css({
				"display": "block",
				"top": top,
				 "left": "425px"
			});
	});
	//全选
	$("#allSelect").click(function() {
		var bol = $("#allSelect").prop("checked");
		if(bol){		
			$(".user-list-allSelect div input").prop({"checked":true});
		}else{
			$(".user-list-allSelect div input").prop({"checked":false});
		}
	});
	$("#allSelect1").click(function() {
		var bol = $("#allSelect1").prop("checked");
		if(bol ){		
			$(".user-list-allSelect1 div input").prop({"checked":true});
		}else{
			$(".user-list-allSelect1 div input").prop({"checked":false});
		}
	});
	//修改备注
	$(".btn-beizhu").keyup(function() {
		if($(".btn-beizhu").length!=" "){
			 num=$(".btn-beizhu").val().length;
		     $(".fenzi").html(num);	
		}
	});
	
	//查询
	$("#queryBtn").click(function(){
		initWXUserListInfo();
	});
	
	//回车事件
	$("body,#nickNameInput").keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#queryBtn').click();
            event.preventDefault();
            return;
        }
    });
	
	//新增分组
	$("#saveGroup").click(function() {
		var url = contextPath + "/wx/usergroup/insertWXUserGroup";
		var params = {};
		params.tagName=$("#groupName").val();
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				if(data.object.errcode == null){
					alert("操作成功");
				} else {
					alert(data.object.errmsg,$.scojs_message.TYPE_ERROR);
				}
				$("#saveGroup").hide();
				$("#updateGroup").hide();
				initWXGroupListInfo();
			}
		});
	});
	//编辑分组
	$("#updateGroup").click(function() {
		var url = contextPath + "/wx/usergroup/updateWXUserGroup";
		var params = {};
		params.tagId=tagId;
		params.newTagName=$("#groupName").val();
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				if(data.object == "SUCCESS"){
					alert("操作成功");
				} else {
					alert("操作失败",$.scojs_message.TYPE_ERROR);
				}
				groupName=null;
				$("#saveGroup").hide();
				$("#updateGroup").hide();
				$(".selected").click();;
				initWXGroupListInfo();
			}
		});
	});
	
	//修改备注
	$("#setRemark").click(function() {
		var url = contextPath + "/wx/usergroup/setUserRemark";
		var params = {};
		params.remark=$("#remark").val();
		params.openId=remark_openId;
		params.wxUserId=remark_wxId;
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				initWXUserListInfo();
			}
		});
	});
	
	//打标签按钮默认选中事件
	$("#addTagBtn").click(function(){
		var size = $("#userList .cb:checked").size();//选中的用户数量
		if(size == 1) {
			var tag_list = $("#userList .cb:checked").eq(0).attr("tl").split(",");
			$(tag_list).each(function(i,it){
				$("#wxUserTag .cb").each(function(j,it2){
					if(it == it2.value) {
						$(it2).prop("checked",true);
						return false;
					}
				});
			});
		}
	});
	
	//拉黑确定按钮点击事件
	$("#saveBlackBtn").click(function(){
		var url = contextPath + "/wx/user/insertBlackList";
		var openid = [];
		var params = {};
		$("#userList .cb:checked").each(function(){
			openid.push(this.value);
		});
		params.openid = JSON.stringify(openid);
		Util.ajax.postJson(url, params, function(data, flag){
			alert(data.returnMessage);
			$("#allSelect1").prop("checked",false);
			$("#removeBlackBtn").addClass("disableBtn").attr("disabled","disabled");
			initWXBlackUserList(1,global_limit,true);
		});
	});
	
	//移除拉黑确定按钮点击事件
	$("#deleteBlackBtn").click(function(){
		var url = contextPath + "/wx/user/deleteBlackList";
		var openid = [];
		var params = {};
		$("#blackUserList .cb:checked").each(function(){
			openid.push(this.value);
		});
		params.openid = JSON.stringify(openid);
		Util.ajax.postJson(url, params, function(data, flag){
			alert(data.returnMessage);
			$("#allSelect1").prop("checked",false);
			$("#removeBlackBtn").addClass("disableBtn").attr("disabled","disabled");
			initWXBlackUserList(1,global_limit,true);
		});
	});
	
	//同步用户
	$("#syncUser").click(function(){
		showMask();
		var url = contextPath + "/wx/user/syncWXUses";
		$.ajax({
		    url : url,
		    type : 'POST', //GET
		    async : true,    //或false,是否异步
		    timeout : 0,    //超时时间
		    dataType : 'text',
		    success : function(data, flag){
		    	hideMask();
				alert(data.returnMessage);
				//初始化微信关注用户表列表信息
				initWXUserListInfo();
				initWXGroupListInfo();
		    }
		});
	});
}); 

function selected(groupId,name,tem){
	$("p").removeClass("selected");
	$(tem).siblings(".selected").removeClass("selected");
	$(tem).addClass("selected");
	tagId=groupId;
	groupName=name;
	var url = contextPath + "/wx/usergroup/getListById";
	var params = {};
	params.tagId=groupId;
	Util.ajax.postJson(url, params, function(data, flag){
		if(data.bean.openIds!=undefined){
			openIds=data.bean.openIds;
			initWXUserListInfo()
			$(".page-box").show();
		}else{
			$("#userList").html("");
			$(".page-box").hide();
		}
	});
}

//给用户添加标签
function tagToUser() {
	var url = contextPath + "/wx/user/tagToUser";
	var param = {};//ajax参数
	var openid = [];//选中的用户openid
	var selectTag = [];//选中的tag
	var unselectTag = [];//未选中的tag
	$("#wxUserTag .cb:checked").each(function(){
		selectTag.push(this.value);
	});
	$("#wxUserTag .cb:not(:checked)").each(function(){
		unselectTag.push(this.value);
	});
	$("#userList .cb:checked").each(function(){
		openid.push(this.value);
	});
	param.openid = openid.join(",");
	param.selectTag = selectTag.join(",");
	param.unselectTag = unselectTag.join(",");
	Util.ajax.postJson(url, param, function(data, flag){
		alert(data.returnMessage);
		$("#addTagBtn,#addBlackBtn").addClass("disableBtn").attr("disabled","disabled");
		$("#cancelTagBtn").click();
		$("#allSelect").prop("checked",false);
		initWXUserListInfo();
		initWXGroupListInfo();
	});
}

function userAll(tem){
	tagId=null;
	groupName=null;
	openIds.length=0;
	$("p").removeClass("selected");
	$(tem).siblings(".selected").removeClass("selected");
	$(tem).addClass("selected");
	initWXUserListInfo();
}

//初始化微信关注用户表列表信息
function initWXUserListInfo(currentPage, limit){
	if(typeof currentPage == "undefined"){
		currentPage = 1;
	}
	limit = global_limit;
	var url = contextPath + "/wx/user/getList";
	var params = $("#queryForm").serialize();
	params = params + "&pageNumber="+currentPage+"&limit="+limit;
	if(openIds){
		params+="&openIds="+openIds;
	}
	//异步请求微信关注用户表列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		var source = $("#wXUser-list-template").html();
		var templet = Handlebars.compile(source);
		if(flag && data.returnCode=="0"){
			maxTagSize = data.bean.maxTagSize;
			count=data.bean.countAll;
			$("#allUser").text(count);
			//渲染列表数据
			var htmlStr = templet(data.beans);
			$("#userList").html(htmlStr);
			$(".page-box").show();
			//修改备注
			$(".modif-remark").click(function(){
				var top = $(this).offset().top - 45;
				$(".alert-addblackname, .alert-beizhu,.alert-staruser,.alert-biaoqian,.alert-blackname,.remove-blacklist,.alert-staruser1").css({
					"display": "none"
				});
				$(".alert-beizhu").css({"display": "block", "top": top});
				var ramark = $(this).attr("rm");
				remark_openId = $(this).attr("oid");
				remark_wxId = $(this).attr("wxid");
				$(".btn-beizhu").val(ramark);
				$(".fenzi").html(ramark.length);
			});
			//根据是否选中用户判断按钮是否可用
			$("#userList .cb,#allSelect").click(function(){
				var flag = false;
				$("#userList .cb").each(function(){
					if($(this).is(":checked")){
						flag = true;
					}
				});
				if(flag) {
					$("#addTagBtn,#addBlackBtn").removeClass("disableBtn").removeAttr("disabled");
				} else {
					$("#addTagBtn,#addBlackBtn").addClass("disableBtn").attr("disabled","disabled");
				}
			});
			//根据用户最大分组数量限制打标签
			$("#saveTagBtn").unbind();
			$("#saveTagBtn").click(function(){
				var size = $("#userList .cb:checked").size();//选中的用户数量
				//此处逻辑是这么地，如果直选中一个用户，则不能选中超过三个标签，如果多个用户
				//则加上本页用户最大的标签量即maxTagSize+选中的标签不能超过三个
				var tagSize;
				if(size == 1) {
					tagSize = $("#wxUserTag .cb:checked").size();
				} else {
					tagSize = maxTagSize + $("#wxUserTag .cb:checked").size();//当前页最大分组和选中分组
				}
				if(tagSize > 3) {
					$("#tagInfo").show();
					return false;
				} else {
					tagToUser();
				}
			});
			//初始化分页数据(当前页码，总数，回调查询函数)
			initPaginator(currentPage, data.bean.count, initWXUserListInfo);
		}
	});
}

//初始化微信标签列表信息
function initWXGroupListInfo(){
	var url = contextPath + "/wx/usergroup/getAll";
	var params = {};
	//异步请求微信关注用户表列表数据
	Util.ajax.postJson(url, params, function(data, flag){
		var source = $("#wXUser-groupList-template").html();
		var templet = Handlebars.compile(source);
		var source2 = $("#wxUserTag-template").html();//打标签按钮弹窗
		var templet2 = Handlebars.compile(source2);
		if(flag && data.returnCode=="0"){
			//填充所有分组及其名称
			$(data.object).each(function(){
				allTags[this.id] = this.name;
			});
			//渲染列表数据
			var htmlStr = templet(data.object);
			var htmlStr2 = templet2(data.object);
			$("#groupLi").html(htmlStr);
			$("#wxUserTag").html(htmlStr2);
			if(data.object.length>groupSize){
				var height=590+(data.object.length-groupSize)*60;
				$(".user-management-user").css("height",height+"px");
				$(".user-left").css("height",height-81+"px");
				$(".user-right").css("height",height-81+"px");
			}
		}
	});
}

//------拉黑列表------
//flag为true时重新加载数据
function initWXBlackUserList(currentPage,limit,flag) {
	var url = contextPath + "/wx/user/getBlackList";
	var params = {};
	var userList = [];//当前页的用户
	if(flag) {
		//同步请求拉黑列表
		Util.ajax.postJsonSync(url, params, function(data, flag){
			if(flag && data.returnCode=="0"){
				blackTotal = data.bean.total;//存放黑名单数据用于前台分页
				allBlackUser = data.object;
			}
		});
	}
	var source = $("#wXUser-blackList-template").html();
	var templet = Handlebars.compile(source);
	$("#blackTotal").text(blackTotal);
	//渲染列表数据
	userList = allBlackUser.slice((currentPage - 1) * limit,currentPage * limit);
	var htmlStr = templet(userList);
	$("#blackUserList").html(htmlStr);
	//根据是否选中用户判断按钮是否可用
	$("#blackUserList .cb,#allSelect1").click(function(){
		var flag = false;
		$("#blackUserList .cb").each(function(){
			if($(this).is(":checked")){
				flag = true;
			}
		});
		if(flag) {
			$("#removeBlackBtn").removeClass("disableBtn").removeAttr("disabled");
		} else {
			$("#removeBlackBtn").addClass("disableBtn").attr("disabled","disabled");
		}
	});
	initPaginator2(currentPage, blackTotal, initWXBlackUserList);
}

//黑名单用户列表分页
function initPaginator2(currentPage, total, queryListMethod){
	if(typeof total == "undefined"){
		total = 0;
	}
	var totalNumStr = "共" + total + "条";
	$("#totalNum2").html(totalNumStr);
	if(total != 0) {
		var limit = global_limit; //每页显示多少条记录
		var pagination = $("#pagination2");
		var totalPages = (total + limit -1) / limit;
		var options = {
		    bootstrapMajorVersion:3,	//分页版本
		    currentPage: currentPage,				//当前页码
		    numberOfPages: 5,			//显示多少页，默认显示 5 页
		    totalPages:totalPages,				//总页数
		    onPageClicked: function (event, originalEvent, type, page) {
		    	queryListMethod(page, limit, false);
		    }
		}
	    pagination.bootstrapPaginator(options);
	}
}

//鼠标划过头像显示详细信息
function showDetail(obj) {
	var top = $(obj).offset().top - 10;
	$(".remove-blacklist").css({
		"top": top,
	});
	var $this = $(obj);
	$("#dimg").attr("src",$this.attr("src"));
	$("#dname").text($this.attr("nn"));
	$("#dremark").text($this.attr("rm"));
	$("#darea").text($this.attr("area"));
	$("#dtime").text($this.attr("time"));
	$(".remove-blacklist").show();
}
//隐藏详情
function hideDetail() {
	$(".remove-blacklist").hide();
}
//显示遮罩层    
function showMask(){     
    $("#mask").css("height",$(document).height());     
    $("#mask").css("width",$(document).width());     
    $("#mask").show();     
}  
//隐藏遮罩层  
function hideMask(){     
    $("#mask").hide();     
}  
