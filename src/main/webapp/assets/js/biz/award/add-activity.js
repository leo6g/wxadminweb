
$(document).ready(function(){
	
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
//		alert("ddddd");
//		var length = $("#zy tr").length;
//		alert(length);
//		var detail=[];
//		for(var i=0;i<length;i++){
//			var obj={};
//			 var name = $("#zy tr").eq(i).find("#name").val();
//			 var rank = $("#zy tr").eq(i).find("#rank").val();
//			 var amount = $("#zy tr").eq(i).find("#amount").val();
//			 obj.name=name;
//			 obj.rank=rank;
//			 obj.amount=amount;
//			 detail.push(obj);
//		}
//		var strJson=JSON.stringify(detail);
//		$("#test").val(strJson);
//		console.info(strJson);
		form.submit();
	});
	
	$("#counts").blur(function(){
		var counts=$("#counts").val();
		var show=""
		for (var i=0;i<counts;i++){
			var level = parseInt(i)+1;
			
			show += '<tr>'+
            '<td class="">奖品名称：'+
                '<br>'+
                '<div class="input-wrap">'+
                    '<input type="text" id="name" name="name" value=""  placeholder="奖品名称" />'+
                '</div>'+
            '</td>'+
            '<td class="">奖品等级：'+
                '<br>'+
                '<div class="input-wrap">'+
                    '<input type="text" id="rank" name="rank" value="'+level+'" placeholder="奖品等级" />'+
                '</div>'+
           ' </td>'+
          '  <td class="">奖品数量：'+
              '  <br>'+
               ' <div class="input-wrap">'+
                '    <input type="text" id="amount" name="amount" value=""  placeholder="奖品数量" />'+
               ' </div>'+
           ' </td>'+
        '</tr>';
		}
		$("#zy").html(show);
		
	})
	
	
	
	
	
	
	//获取所有职位信息
	queryAllPosition();
	
	
	
	//点击部门，弹窗选部门树信息
	$("#departName").click(function(){
		$("#myModal").modal("show");
	});
	
	//点击取消按钮
	$("#cancelBtn").click(function(){
		C.load(contextPath + "/biz/awardActivity/list");
	});
	
//	//counts
//	$("#zhaoy").onblur(function(){
//		
//	})
	
	
	
	
	//新增信息
	function insertObj(){
		var url = contextPath + "/biz/awardActivity/insertAwardActivity";
		var params = $("#addForm").serialize();
		//异步请求新增信息
		Util.ajax.postJson(url, params, function(data, flag){
			if(flag){
				if(data.returnCode=="0"){
					alert("用户新增成功!");
					//重新请求列表数据
					C.load(contextPath + "/biz/awardActivity/list?currPage="+Util.browser.getParameter("currPage"));
				}else{
					alert(data.returnMessage);
				}
			}else{
				if(data.returnCode=="-1"){
					alert("用户名已经存在，请修改!");
				}else{
					alert(data.returnMessage);
				}
			}
		});
	}


	//获取所有职位信息
	function queryAllPosition(){
		var url = contextPath + "/system/position/getAllPosition";
		var params = "";
		//异步请求所有职位信息数据
		Util.ajax.postJson(url, params, function(data, flag){
			var source = $("#position-list-template").html();
			var templet = Handlebars.compile(source);
			if(flag && data.returnCode=="0"){
				//渲染数据
				var htmlStr = templet(data.beans);
				$("#positionId").html(htmlStr);
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	   
	
});


