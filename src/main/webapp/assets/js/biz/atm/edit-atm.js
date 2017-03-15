$(document).ready(function(){
	
	//上传组件
	getUpload("uploadBtn","imagePath","preview");
	
	//修改信息时，validate验证，验证通过后调用保存方法 
	$("#editForm").validate({
        submitHandler:function(form){
        	editObj();
        }    
    });
	
	//修改信息
	$("#saveBtn").click(function(){
		var form = $("#editForm");
		form.submit();
	});
	
	
	//修改信息方法
	function editObj(){
		var url = contextPath + "/biz/atm/updateObj";
		var params = $("#editForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("修改成功!");
					C.load(contextPath + "/biz/atm/list");
				}
			}else{
				/*if(data.returnCode=="-1"){
					alert("代码已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}*/
				alert(data.returnMessage);
			}
		});
	}
	
	
	//点击取消按钮
	$("#cancelBtn").click(function(){
		C.load(contextPath + "/biz/atm/list");
	});
	$("#returnBack").click(function(){
		C.load(contextPath + "/biz/atm/list");
	});
	
	//初始化设备种类模版
	initDicByCode('atmType');
	
	//初始化地址类型模版
	initDicByCode('atmAddrType');

	/**
	 * 根据数据字典组编码，渲染数据页面数据，编码和页面ID规则要一致
	 */
	function initDicByCode(dicCode){
		var url = contextPath + "/system/dic/queryDicsByGCode";
		var params = "dicGroupCode="+dicCode;
		//请求数据字典信息
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#"+dicCode+"-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染列表数据
				var htmlStr = templet(data.beans);
				$("#"+dicCode).html(htmlStr);
				
				//设备种类-下拉框默认值
				if(dicCode=='atmType'){
					$("#"+dicCode).val(atmTmpObj.type);
				}
				//地址类型-下拉框默认值
				if(dicCode=='atmAddrType'){
					$("#"+dicCode).val(atmTmpObj.addrType);
				}
				
			}
		});
	}
	
	
	//初始化设备状态
	$("#state").val(atmTmpObj.state);
	
	
	//获取所有二分信息
	queryAllCity();
	

	//获取所有二分信息
	function queryAllCity(){
		var url = contextPath + "/biz/netpoint/getOrgInfo?type=city";
		var params = "";
		//异步请求所有职位信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#city-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#cityList").html(htmlStr);
				
				//得到编辑的值
				$("#cityList").val(atmTmpObj.city);
				
				//选择改变
				$("#cityList").change(function(){
					var cityId=$(this).val();
					if(cityId != null && cityId !=''){
						$("#townList").html("");
						$("#countryList").html("");
						queryAllTown(cityId);
					}else{
						$("#townList").html("");
						$("#countryList").html("");
					}
				}).change();
				
			}
		});
	}
	
	//获取所有一支信息
	function queryAllTown(cityId){
		var url = contextPath + "/biz/netpoint/getOrgInfo?type=town&parentId="+cityId;
		var params = "";
		//异步请求所有职位信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#town-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#townList").html(htmlStr);
				
				//得到编辑的值
				$("#townList").val(atmTmpObj.town);
				
				//选择改变
				$("#townList").change(function(){
					var townId=$(this).val();
					if(townId !=null && townId !=""){
						$("#countryList").html("");
						queryAllCountry(townId);
					}else{
						$("#countryList").html("");
					}

				}).change();
			}
		});
	}
	
	
	//获取所有二支信息
	function queryAllCountry(townId){
		var url = contextPath + "/biz/netpoint/getOrgInfo?type=country&parentId="+townId;
		var params = "";
		//异步请求所有职位信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#country-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#countryList").html(htmlStr);
				
				//得到编辑的值
				$("#countryList").val(atmTmpObj.orgShtId);
			}
		});
	}
	
	
	
});



