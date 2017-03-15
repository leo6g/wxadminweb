$(function () {
    // 左侧导航切换
    $(".navbox>li").on("click",function () {
        $(this).children(".inner-menu").slideDown(300).end().siblings().children(".inner-menu").slideUp(300); 
    });
    // 点标题展开详情
	$(".outer-menu").on("click",function () {
	    if($(this).next().is(':hidden')){
	        $(this).next().slideDown(300);
	      }
	      else{
	        $(this).next().slideUp(300);
	      }
	    $(this).parents("li").siblings().find(".inner-menu").slideUp(300); 
	});
	 //让子菜单高亮
    $(".navbox .inner-menu li").on("click",function () {
       $(this).addClass("current").siblings().removeClass("current"); 
    });

    // 
    var winHeight=$(window).innerHeight();
    var globalTopheight=$(".globaltop-box ").innerHeight();
    var $mainLeft=$(".main-left");
    var $mainRight=$(".main-right");
    if($mainRight.height()>winHeight-globalTopheight){
    $mainLeft.height($mainRight.height());
    }
    else{
		$mainLeft.height(winHeight-globalTopheight);
	}
});
/*jiang-shoujitanchuang*/
$(function(){
	$(".yulan").on("click",function(){
		$(".jiangiphone").show();
	})
	$(".jiangsjclose").on("click",function(){
		$(".jiangiphone").hide()
	})
})
$(function(){
	$(".jiangiphone-left li").on("click",function(){
		var n=$(this).index();
		$(this).addClass("jiangactive").siblings().removeClass("jiangactive");
		$(".jiangiphoneview>div").eq(n).show().siblings().hide();		
	})
})
//ljq-yh
$(function () {
    $(".yh_delete").click(function (e) {
		e.preventDefault();
        $(this).parent().next().css("display","block");
    })
    $(".yh_confirm,.yh_cancel").click(function () {
        $(this).parent().css("display","none");
    })
})
//zl
$(function(){
	$(".zl_nav li").click(function(){
		$(this).addClass("zl_click").siblings().removeClass("zl_click");
	});
})
