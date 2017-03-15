Util = {
	/**
	 * 取消事件冒泡
	 * @param {Object}
	 *            e 事件对象
	 */
	stopBubble : function(e) {
		if (e && e.stopPropagation) {
			e.stopPropagation();
		} else {
			// ie
			window.event.cancelBubble = true;
		}
	},
	
	getTargetName : function(e){
		e = e || window.event;
		if(e.srcElement){
			return e.srcElement.tagName.toLowerCase();
		}else{
			return e.target.localName.toLowerCase();
		}
	},
	
	/**
	 * 取消默认行为
	 * @param e
	 */
	preventDefault : function(e){
		e = e||window.event;
		if(e.preventDefault){
			e.preventDefault()
		}else{
			//ie
			e.returnValue = false;
		}
	},
	/**
	 * 入参转码
	 * @param {string}
	 * 		json格式
	 */
	transCoding : function(json){
		var temp=encodeURIComponent(json);
		temp=CryptoJS.enc.Utf8.parse(temp);
		temp=CryptoJS.enc.Base64.stringify(temp);
		return temp;
	},
	/**
	 * 入参转码
	 * @param {string}
	 * 		json格式
	 */
	transDecoding : function(objStr){
		var words = CryptoJS.enc.Base64.parse(objStr);
		words = words.toString(CryptoJS.enc.Utf8);
		words = decodeURIComponent(words)
		return words;
	}
};
/**
 * 日期时间处理工具
 * 
 * @namespace Util
 * @class date
 */
Util.date = {
	/**
	 * 格式化日期时间字符串
	 * 
	 * @method dateTime2str
	 * @param {Date}
	 *            dt 日期对象
	 * @param {String}
	 *            fmt 格式化字符串，如：'yyyy-MM-dd hh:mm:ss'
	 * @return {String} 格式化后的日期时间字符串
	 */
	dateTime2str : function(dt, fmt) {
		var z = {
			M : dt.getMonth() + 1,
			d : dt.getDate(),
			h : dt.getHours(),
			m : dt.getMinutes(),
			s : dt.getSeconds()
		};
		fmt = fmt.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
			return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1)))
					.slice(-2);
		});
		return fmt.replace(/(y+)/g, function(v) {
			return dt.getFullYear().toString().slice(-v.length);
		});
	},
	/**
	 * 根据日期时间格式获取获取当前日期时间
	 * 
	 * @method dateTimeWrapper
	 * @param {String}
	 *            fmt 日期时间格式，如："yyyy-MM-dd hh:mm:ss";
	 * @return {String} 格式化后的日期时间字符串
	 */
	dateTimeWrapper : function(fmt) {
		if (arguments[0])
			fmt = arguments[0];
		return this.dateTime2str(new Date(), fmt);
	},
	/**
	 * 获取当前日期时间
	 * 
	 * @method getDatetime
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd hh:mm:ss'] 日期时间格式。
	 * @return {String} 格式化后的日期时间字符串
	 */
	getDatetime : function(fmt) {
		return this.dateTimeWrapper(fmt || 'yyyy-MM-dd hh:mm:ss');
	},
	/**
	 * 获取当前日期时间+毫秒
	 * 
	 * @method getDatetimes
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd hh:mm:ss'] 日期时间格式。
	 * @return {String} 格式化后的日期时间字符串
	 */
	getDatetimes : function(fmt) {
		var dt = new Date();
		return this.dateTime2str(dt, fmt || 'yyyy-MM-dd hh:mm:ss') + '.'
				+ dt.getMilliseconds();
	},
	/**
	 * 获取当前日期（年-月-日）
	 * 
	 * @method getDate
	 * @param {String}
	 *            fmt [optional,default='yyyy-MM-dd'] 日期格式。
	 * @return {String} 格式化后的日期字符串
	 */
	getDate : function(fmt) {
		return this.dateTimeWrapper(fmt || 'yyyy-MM-dd');
	},
	/**
	 * 获取当前时间（时:分:秒）
	 * 
	 * @method getTime
	 * @param {String}
	 *            fmt [optional,default='hh:mm:ss'] 日期格式。
	 * @return {String} 格式化后的时间字符串
	 */
	getTime : function(fmt) {
		return this.dateTimeWrapper(fmt || 'hh:mm:ss');
	},
	/**
	 * 获取时间差
	 */
	getTimeDiff : function(startTime, endTime){
		startTime = parseInt(startTime/1000);
		endTime = parseInt(endTime/1000);
		var diff = endTime - startTime;
		var unit = '秒';
		if(diff >= 60){
			diff = parseInt(diff/60);
			unit = '分钟';
			if(diff >= 60){
				diff = parseInt(diff/60);
				unit = '小时';
				if(diff >= 24){
					diff = parseInt(diff/24);
					unit = '天';
					if(diff >= 365){
						diff = parseInt(diff/365);
						unit = '年';
					}
				}
			}
		}
		return diff + unit;
	}
};
/**
 * 通过 HTTP 请求加载远程数据，底层依赖jQuery的AJAX实现。当前接口实现了对jQuery AJAX接口的进一步封装。
 */
Util.ajax = {
	/**
	 * 请求状态码
	 * 
	 * @type {Object}
	 */
	reqCode : {
		/**
		 * 成功返回码 0000
		 * 
		 * @type {Number} 1
		 * @property SUCC
		 */
		SUCC : 0000
	},
	/**
	 * 请求的数据类型
	 * 
	 * @type {Object}
	 * @class reqDataType
	 */
	dataType : {
		/**
		 * 返回html类型
		 * 
		 * @type {String}
		 * @property HTML
		 */
		HTML : "html",
		/**
		 * 返回json类型
		 * 
		 * @type {Object}
		 * @property JSON
		 */
		JSON : "json",
		/**
		 * 返回text字符串类型
		 * 
		 * @type {String}
		 * @property TEXT
		 */
		TEXT : "text"
	},
	/**
	 * 超时,默认超时30000ms
	 * 
	 * @type {Number} 10000ms
	 * @property TIME_OUT
	 */
	TIME_OUT : 60000,
	/**
	 * 显示请求成功信息
	 * 
	 * @type {Boolean} false
	 * @property SHOW_SUCC_INFO
	 */
	SHOW_SUCC_INFO : false,
	/**
	 * 显示请求失败信息
	 * 
	 * @type {Boolean} false
	 * @property SHOW_ERROR_INFO
	 */
	SHOW_ERROR_INFO : false,
	/**
	 * GetJson是对Util.ajax的封装,为创建 "GET" 请求方式返回 "JSON"(text) 数据类型
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getJson : function(url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.TEXT;
		// var _this = this;
		// setTimeout( function(){_this.ajax(url, 'GET', cmd, dataType,
		// callback)},1000);
		this.ajax(url, 'GET', cmd, dataType, callback);
	},
	/**
	 * PostJsonAsync是对Util.ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型,
	 * 采用同步阻塞的方式调用ajax
	 * @param {String}
	 *            url HTTP(POST)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] POST请求成功回调函数
	 */
	postJsonSync : function(url, cmd, callback) {
		dataType = this.dataType.TEXT;
		this.ajax(url, 'POST', cmd, dataType, callback, true);
	},
	/**
	 * PostJson是对Util.ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型
	 * @param {String}
	 *            url HTTP(POST)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] POST请求成功回调函数
	 * @param {Object}
	 *			  loading{start:{Function},complete:{Function}} 载入开始及结束的回调函数
	 */
	postJson : function(url, cmd, callback,flag,loading) {
        // if(!flag){Util.loading.showLoading();}
		dataType = this.dataType.TEXT;
		// var _this = this;
		// setTimeout( function(){_this.ajax(url, 'POST', cmd, dataType,
		// callback)},1000);
		this.ajax(url, 'POST', cmd, dataType, callback,'',flag,loading);
	},
	/**
	 * loadHtml是对Ajax load的封装,为载入远程 HTML 文件代码并插入至 DOM 中
	 * @param {Object}
	 *            obj Dom对象
	 * @param {String}
	 *            url HTML 网页网址
	 * @param {Function}
	 *            callback [optional,default=undefined] 载入成功时回调函数
	 */
	loadHtml : function(obj, url, data, callback) {
		$(obj).load(url, data, function(response, status, xhr) {
			callback = callback ? callback : function() {
			};
			status == "success" ? callback(true) : callback(false);
		});
	},
	/**
	 * loadTemp是对handlebars 的封装,请求模版加载数据
	 * @param {Object}
	 *            obj Dom对象
	 * @param {Object}
	 *            temp 模版
	 * @param {Object}
	 *            data 数据
	 */
	loadTemp : function(obj, temp, data) {
		var template = Handlebars.compile(temp.html());
		$(obj).html(template(data));
	},
	/**
	 * GetHtml是对Util.ajax的封装,为创建 "GET" 请求方式返回 "hmtl" 数据类型
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getHtml : function(url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.HTML;
		this.ajax(url, 'GET', cmd, dataType, callback);
	},
	/**
	 * GetHtmlSync是对Util.ajax的封装,为创建 "GET" 请求方式返回 "hmtl" 数据类型
	 * 采用同步阻塞的方式调用ajax
	 * @param {String}
	 *            url HTTP(GET)请求地址
	 * @param {Object}
	 *            cmd json对象参数
	 * @param {Function}
	 *            callback [optional,default=undefined] GET请求成功回调函数
	 */
	getHtmlSync : function(url, cmd, callback) {
		if (arguments.length !== 3)
			callback = cmd, cmd = '';
		dataType = this.dataType.HTML;
		this.ajax(url, 'GET', cmd, dataType, callback,true);
	},
	/**
	 * 基于jQuery ajax的封装，可配置化
	 * 
	 * @method ajax
	 * @param {String}
	 *            url HTTP(POST/GET)请求地址
	 * @param {String}
	 *            type POST/GET
	 * @param {Object}
	 *            cmd json参数命令和数据
	 * @param {String}
	 *            dataType 返回的数据类型
	 * @param {Function}
	 *            callback [optional,default=undefined] 请求成功回调函数,返回数据data和isSuc
	 * @param {Object}
	 *			  loading{start:{Function},complete:{Function}} 载入开始及结束的回调函数
	 */
	ajax : function(url, type, cmd, dataType, callback, sync, flag, loading) {
		/*var param = "";
		if (typeof (cmd) == "object"){
			param = JSON.stringify(cmd);
		}else if(typeof(cmd)=="string"){
			param = cmd;
		}*/
		//cmd = this.jsonToUrl(cmd);
		
		/*var srvName = null;
		//支持传api名称，从srvMap中获取url
		if(srvMap && srvMap.get(url)){
			srvName = url;
			url = srvMap.get(url);
		}else if(srvMap && srvMap.getName && srvMap.getName(url)){//也可以从url获取api名称
			srvName = srvMap.getName(url)
		}*/
		
		async = sync ? false : true;
		var thiz = Util.ajax;
		var cache = (dataType == "html") ? true : false;
		$.ajax({
			url : url,
			type : type,
			data : cmd,
			/*processData: false,  	// 告诉jQuery不要去处理发送的数据
			contentType: false,		// 告诉jQuery不要去设置Content-Type请求头*/
			cache : cache,
			dataType : dataType,
			async : async,
			timeout : thiz.TIME_OUT,
			beforeSend : function(xhr) {
				xhr.overrideMimeType("text/plain; charset=utf-8");
				if(flag && loading && loading.start){
					loading.start();
				}
			},
			success : function(data) {
				if (!data) {
					return;
				}
				if (dataType == "html") {
					callback(data, true);
					return;
				}
				try {
					data = eval('(' + data + ')');
					if (data.returnCode=='PAGEFRAME-9527') {
						alert("登陆凭证过期，请重新登陆");
						window.location.reload();
						return;
					}
				} catch (e) {
					//console.log("JSON Format Error:" + e.toString());
				}
				var isSuc = thiz.printReqInfo(data);
				if (callback && data) {
					
					//当已经对某接口定义结果转化时，对返回数据进行转化，无需在回调函数中再显式调用
					/*
					if(srvName && $.DATA_CONVERTER && $.DATA_CONVERTER.get && $.DATA_CONVERTER.get(srvName) && $.DATA_CONVERTER.convert){
						$.DATA_CONVERTER.convert(srvName, data);
					}
					*/
					callback(data || {}, isSuc);
				}
			},
			error : function() {
			    var retErr ={};
			    retErr['returnCode']="SCRM-404";
			    retErr['returnMessage']="网络异常或超时，请稍候再试！"; 
				callback(retErr, false);
			},
            complete:function(){
                //if(!flag){Util.loading.hideLoading();}
				if(flag && loading && loading.complete){
					loading.complete();
				}
            }
		});
	},
	/**
	 * 打开请求返回代码和信息
	 * 
	 * @method printRegInfo
	 * @param {Object}
	 *            data 请求返回JSON数据
	 * @return {Boolean} true-成功; false-失败
	 */
	printReqInfo : function(data) {
		if (!data)
			return false;
		var code = data.returnCode, msg = data.returnMessage, succ = this.reqCode.SUCC;
		if (code == succ) {
			if (this.SHOW_SUCC_INFO) {
				// Util.msg.infoCorrect([ msg, ' [', code, ']' ].join(''));
				Util.msg.infoCorrect(msg);
			}
		} else {
			// Util.msg.infoAlert([ msg, ' [', code, ']' ].join(''));
			if (this.SHOW_ERROR_INFO) {
				art.dialog.tips(msg);
			}
		}
		return !!(code == succ);
	},
	/**
	 * JSON对象转换URL参数
	 * 
	 * @method printRegInfo
	 * @param {Object}
	 *            json 需要转换的json数据
	 * @return {String} url参数字符串
	 */
	jsonToUrl : function(json) {
		var temp = [];
		for ( var key in json) {
			if (json.hasOwnProperty(key)) {
				var _key = json[key] + "";
				_key = _key.replace(/\+/g, "%2B");
				_key = _key.replace(/\&/g, "%26");
				temp.push(key + '=' + _key);
			}
		}
		return temp.join("&");
	},
	msg : {
		"suc" : function(obj, text) {
			var _text = text || "数据提交成功！";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-icon hint-suc-s"></i>' + _text
							+ '</h3>' + '</div>').show();
		},
		"war" : function(obj, text) {
			var _text = text || "数据异常，请稍后尝试!";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-icon hint-war-s"></i>' + _text
							+ '</h3>' + '</div>').show();
		},
		"err" : function(obj, text) {
			var _text = text || "数据提交失败!";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-icon hint-err-s"></i>' + _text
							+ '</h3>' + '</div>').show();
		},
		"load" : function(obj, text) {
			var _text = text || "正在加载中，请稍候...";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-loader"></i>' + _text + '</h3>'
							+ '</div>').show();
		},
		"inf" : function(obj, text) {
			var _text = text || "数据提交中，请稍等...";
			$(obj).html(
					'<div class="msg-hint">' + '<h3 title=' + _text
							+ '><i class="hint-icon hint-inf-s"></i>' + _text
							+ '</h3>' + '</div>').show();
		},
		"errorInfo" : function(obj, text) {
			var _text = text || "数据提交失败!";
			$(obj)
					.html(
							'<div class="ui-tiptext-container ui-tiptext-container-message"><p class="ui-tiptext ui-tiptext-message">'
									+ '<i class="ui-tiptext-icon icon-message" title="阻止"></i>'
									+ _text + '</p>' + '</div>').show();
		}
	}
};

Util.browser = {
	/**
	 * 获取URL地址栏参数值
	 * name 参数名
	 * url [optional,default=当前URL]URL地址
	 * @return {String} 参数值
	 */
	getParameter : function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    	var r = window.location.search.substr(1).match(reg);
    	if (r != null) return unescape(r[2]);
    	return null;	
	},
	getParamFromString :function(source,key){
	    if(source){
	        var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
	        var r = source.substr(1).match(reg);
	        if (r != null) return unescape(r[2]);
	        return null;
	    }
	    return null;
	}
};
/**
 * 常用正则表达式
 */
Util.validate = {
	/**
	 * 格式校验方法
	 * 
	 * @method Check
	 * @param {String}
	 *            type 验证类型
	 * @param {String}
	 *            value 验证值
	 */
	Check : function(type, value) {
		var _reg = this.regexp[type];
		if (_reg == undefined) {
			alert("Type " + type + " is not in the data");
			return false;
		}
		var reg;
		if (typeof _reg == "string") {
			reg = new RegExp(_reg);
		} else if ((typeof _reg) == "function") {
			return _reg(value);
		} else {
			reg = _reg[type];
		}
		return reg.test(value);
	}
};
Util.sms = {};
Util.sms.formatStr = function(value) {
    if (value) {	
        if (arguments.length > 1) {
            for (var i = 1; i < arguments.length; i++) {
                value = value.replace(new RegExp("\\{" + (i - 1) + "\\}", 'g'), arguments[i]);
            }
        }
    }
    return value;
};

 // 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//全局属性，列表分页每页显示多少条记录
var global_limit = 12;
/*
 * 分页初始化方法
 * currentPage:当前页码(必须)
 * count:总数量(必须)
 * queryListMethod:查询列表数据回调函数，回调函数参数格式为(page, limit),page:点击的页码，limit：每页数量(必须)
 * pagin:页面分页插件div的id
 * totalNum:页面总数量html的id
 * currPage:页面当前页html的id
 */
function initPaginator(currentPage, count, queryListMethod, pagin, totalNum, currPage){
	
	if(typeof count == "undefined"){
		count = 0;
	}
	var totalNumStr = "共" + count + "条";
	if(typeof totalNum != "undefined"){
		$("#"+totalNum).html(totalNumStr);
	}else{
		$("#totalNum").html(totalNumStr);
	}
	var limit = global_limit; //每页显示多少条记录
	var pagination ;
	if(typeof pagin != "undefined"){
		pagination = $("#"+pagin);
	}else{
		pagination = $("#pagination");
	}
	var totalPages = (count + limit -1) / limit;
	var options = {
	    bootstrapMajorVersion:3,	//分页版本
	    currentPage: currentPage,				//当前页码
	    numberOfPages: 5,			//显示多少页，默认显示 5 页
	    totalPages:totalPages,				//总页数
	    onPageClicked: function (event, originalEvent, type, page) {
	    	//放入页面中当前页码的隐藏域中
	    	if(typeof currPage != "undefined"){
	    		$("#"+currPage).val(page);
	    	}else{
	    		$("#currPage").val(page);
	    	}
	    	queryListMethod(page, limit);
	    }
	}
    pagination.bootstrapPaginator(options);
}

