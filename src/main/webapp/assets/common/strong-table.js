/**
 * 使tr点击后可以变色
 * 用法：给tr所在的table添加样式 clickable-table即可
 */
;(function($){
	var pluginName = "increase_table";
	$(document).on("click."+pluginName,'.clickable-table tr',function(e){
		//只有当点击td才变色。如果是th，不变色
		if(e.target.tagName.toLowerCase() === "td"){
			$(this).addClass("gray-tr").siblings('tr').removeClass("gray-tr");
		}
	})
})(jQuery);