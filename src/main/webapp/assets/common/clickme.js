/**
 * clickme.js
 * checkbox的关联选中插件，为这样的场景设置：选中某个checkbox，同时选中其他checkbox
 * 使用方法:如果要实现点击checkbox A，关联选中checkbox B C
 * 则需要在checkbox A上添加属性click-rel = 'g1'
 * 然后在B C上添加属性click-group = 'g1'
 * eg.
 * <input type="checkbox" click-rel="g1"> 
 * <input type="checkbox" click-group="g1"> 
 * <input type="checkbox" click-group="g1">
 * 
 * jquery API:
 * $("父元素选择器").simpleClickTarget($('子元素选择器'));
 * 或者：
 * $.link_click($("父元素选择器"),$('子元素选择器'));
 */
;(function($){
	
	"use strict";
	
	var pluginName = "link_click";
	
	//定义不重复的随机数，用时间戳和10位随机数组成，来标识组名
	var getRandom = function(){
		var rand = Math.round(Math.random()*100000000000).toString();
		return new Date().valueOf().toString()+rand;
	};
	
	//以后扩展用
	var defaultOption = {
	};
	$.extend($.fn,{
		//定义点击源
		clickSource : function(opt){
			var group = 'group'+getRandom();
			opt = $.extend(defaultOption,opt);
		    this.attr('click-rel',group);
			return group;
		},
		//定义点击目标
		clickTarget : function(opt){
			this.attr('click-group',opt.group);
			return this;
		},
		//定义最简单的可满足最基本使用的联动方法，一般情况下使用此方法即可
		simpleClickTarget : function(target){
			target.clickTarget({group:$(this).clickSource()});
			return this;
		}
	});
	
	$[pluginName] = function(source,target){
		source.simpleClickTarget(target);
		return this;
	}
	
	$(document).on('click.'+pluginName,'[click-rel]',function(e){
		e = e || window.event;
		var self = $(this);
		var checked = self.prop("checked");
		var group = self.attr('click-rel');
//		$('[click-group='+group+']').trigger('click.'+pluginName);
		$('[click-group='+group+']').prop("checked",checked);
	})
	
})(jQuery);