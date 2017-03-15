//图片路径
Handlebars.registerHelper("EimageUrl",function(imageUrl){
	if(imageUrl==null || imageUrl==''){
		return contextPath+'/assets/img/default.png';
	}else{
	      return contextPath+"/"+imageUrl;
	}

});

//按钮图片路径
Handlebars.registerHelper("EbtnUrl",function(btnUrl){
	if(btnUrl==null || btnUrl==''){
		  return '';
	}else{
	      return contextPath+"/"+btnUrl;
	}

});
//菜单图片路径
Handlebars.registerHelper("EmenuUrl",function(menuUrl){
		if(menuUrl==null || menuUrl==''){
			return contextPath+'/assets/img/default-icon.png';
		}else{
		      return contextPath+"/"+menuUrl;
		}
	});
//注册索引+1的Helper
Handlebars.registerHelper("addOne", function(data){
	//返回+1之后的结果
    return data + 1;
});


//注册一个是否相等的Helper
Handlebars.registerHelper("equals", function(v1, v2, options) {
	if (v1 == v2) {
		// 满足添加继续执行
		return options.fn(this);
	} else {
		// 不满足条件执行{{else}}部分
		return options.inverse(this);
	}
});

//注册一个判断状态值的Helper
Handlebars.registerHelper("eqstate", function(data) {
	if(data=="1"){
		return "正常";
	}else{
		return "冻结";
	}
}); 


//判断是否抽奖
Handlebars.registerHelper("IsAward", function(data) {
	if(data=="T"){
		return "是";
	}else{
		return "否";
	}
});

//密码规则：密码长度最少9位，不能超过20位，并且必须包含大写字母，小写字母和数字。
function checkPass(pass){ 
	if(pass.length > 20 || pass.length < 9){  
		
		alert("密码长度须在9到20位之间，请重新设置。",$.scojs_message.TYPE_ERROR); 
		return false;
	}else if(!pass.match(/([a-z])+/)){ 
		
		alert("密码必须包含大写字母，小写字母和数字，请重新设置。",$.scojs_message.TYPE_ERROR); 
		return false;
		
	}else if(!pass.match(/([0-9])+/)){  
		
		alert("密码必须包含大写字母，小写字母和数字，请重新设置。",$.scojs_message.TYPE_ERROR); 
		return false;
	}else if(!pass.match(/([A-Z])+/)){  
		
		alert("密码必须包含大写字母，小写字母和数字，请重新设置。",$.scojs_message.TYPE_ERROR); 
		return false;
		
	}else{
        return true;
	}  		
}  
(function(){
	/**
	 * 更改原生alert的样式，用sco.message.js插件代替
	 * type的值可以为：
	 * $.scojs_message.TYPE_OK
	 * $.scojs_message.TYPE_ERROR
	 * 分别代表成功和错误提示
	 * 默认为成功提示
	 */
	window.alert = function(msg,type){
		$.scojs_message(msg, type || $.scojs_message.TYPE_OK);
	}
	
	/**
	 * 更改原生的confirm样式，用sco.confirm.js插件代替
	 * 新的confirm方法无法阻塞浏览器运行并返回true或false,所以只能修改代码，将点击确定和取消对应所执行的函数，以参数的形式传入
	 * 作为回调函数来执行
	 * 
	 * 使用此函数必须传入至少连个参数，cancelCallback为可选。如果不传入calcelCallback，则点击取消的时候什么也不做
	 * 如果没有传入必要的msg和okCallback参数，将抛出异常和提示信息
	 */
	window.confirm = function(msg,okCallback,cancelCallback){
		//先检查参数，使用此函数必须传入至少连个参数，cancelCallback为可选
		if(arguments.length<2){
			throw new Error("本项目修改了confirm的实现方式。新的confirm方法需要至少两个参数，请参考public.js获取帮助");
		}
		var options = {
				content: msg,
				action:function(){
					okCallback && okCallback();
					this.close();
				},
				cancelAction:function(){
					cancelCallback && cancelCallback();
				}
		};
		$.scojs_confirm(options).show();
	}
	
	/**
	 * 页面加载完成后发生
	 */
	window.onload = function(){
//		resizePage();
	}
	/**
	 * 页面大小调整时候发生
	 */
	window.onresize = function(){
		resizePage();
	}
	
	/**
	 * 调整页面大小。将此函数绑定在加载事件和窗口调整事件下，可调整页面适配
	 */
	function resizePage(){
		var height = $(window).height();
		var headerHeight = 55;
		var footerHeight = 51;
		var mainHeight = $(".container").height();
	}
	
	
	
	/**
	 * 数组添加IndexOf方法
	 */
	if (!Array.prototype.indexOf){
	  Array.prototype.indexOf = function(elt /*, from*/){
	    var len = this.length >>> 0;

	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;

	    for (; from < len; from++){
	      if (from in this && this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
	
	/**
	 * 给数组添加contains方法
	 */
	Array.prototype.contains = function(v){
		for (var i = 0; i < this.length; i++) {
			if(this[i] === v){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 字符串扩展trim方法
	 */
	String.prototype.trim = function(){
		return this.replace(/(^\s+)|(\s+$)/g,'')
	}
	
	
})();
