
var xh=0;
    $(document).ready(function () {
        htmlshow();
    });


    //ҳ����Ч���صķ���
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


