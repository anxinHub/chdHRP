
var xh=0;
    $(document).ready(function () {
        htmlshow();
    });


    //页面特效加载的方法
    function htmlshow() {
        $("section#rightside .promenu li.m1 a").addClass("current");
        $(".promenu li a.current").css("background-position", "left bottom");

       	$(".pro_menu dl dt").click(function(){
		
		
			$(this).next().slideDown().parent().siblings().children('dd').slideUp();
			$(this).addClass("current").parent().siblings().children('dt').removeClass("current");
			
		
		})
        $(".promenu li a").hover(function () {
            $(this).css("background-position", "left bottom");

        }, function () {

            if ($(this).is(".current")) {
            }
            else {
                $(this).css("background-position", "left top");
            }
        })

    }


