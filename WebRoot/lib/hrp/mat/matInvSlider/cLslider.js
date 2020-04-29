;(function($){
    $.fn.clSlide = function(options){
     var defaultOptions = {
        wrapperWidth:750,           //最外层宽高度
        WrapperHeight:500,
        banner2Height:83,            //微缩图列表高度
        banner2ImgWidth:60,         //微缩图片宽度
        clearBtnShow:true,       //删除按钮是否显示
        btn1Size:40,                 //删除按钮大小
        onClearBtnClick:function(){

        }
     }
            var settings = $.extend({}, defaultOptions, options);
            var $self = this;
            $self.css({
                width: settings.wrapperWidth,
                height: settings.wrapperHeight
            });

            var banner1 = $self.find(".banner");
            var btn1 = banner1.find(".btn1");
            var banner2 = $self.find(".banner2");

            var imgSmall = banner2.find('.imgs');
            var clearBtn = banner1.find('.clear');
            if(settings.clearBtnShow) clearBtn.show();
            else clearBtn.hide();
            banner1.css({
                "height":settings.WrapperHeight - settings.banner2Height,

            });

            btn1.css({
                "height": settings.btn1Size,
                "width": settings.btn1Size,
                "top": "50%",
                "margin-top": -settings.btn1Size/2
            });



            banner2.css({
                "height": settings.banner2Height
            })

            imgSmall.css({
                "width": banner2.height() - 10,

            });
           var index = 0;

            var item1 = $(".imgbig:first").clone();
            $self.find(".imgList").append(item1);
             var imgBig = banner1.find('.imgbig');

            imgBig.css({
                "width": settings.wrapperWidth,
            });

            $self.find(".imgList").width(imgBig.length*(imgBig.width()));
            $self.find(".imgList2").width(imgSmall.length*(imgSmall.width()+40));     //生成imglist2的宽度


            imgSmall.eq(index).addClass("active");
            $self.find(".imgList").css("left",0);

            function change(){                    //图片移动函数

                if(index == imgBig.length)
                {

                    $self.find(".imgList").css("left",0);
                    index = 1;
                }
                else if(index == -1)
                {
                     $self.find(".imgList").css("left",-imgBig.width()*(imgBig.length-1));
                     index = imgBig.length-2 ;
                }

                $self.find(".imgList").stop().animate({
                    left: -imgBig.width()*index,
                 }, 300)

                banner2.scrollLeft(imgSmall.width()*index);
            }

            function showScroll(){
                var t = setTimeout(function(){
                    clearTimeout(t);
                banner2.toggleClass("scroll")
                },800)
            }

            function reMove(){          //移除函数
                if(index ==0 || index == imgBig.length-1)       //当删除第一张图时，去掉最后一张与第一张
                    {
                        index = 0;
                        imgBig.eq(imgBig.length-1).remove();
                        var item1 = imgBig.eq(1).clone();
                        $self.find(".imgList").append(item1);
                    }
                    imgSmall.eq(index).remove();
                    imgBig.eq(index).remove();
            }
            $self.find(".next").on("click",function(event) {          //  左右翻页键

                 index++;
                 change();
                 if(index == imgSmall.length) selectChange.call(imgSmall.eq(0));
                 selectChange.call(imgSmall.eq(index));
            });
            $self.find(".prev").on("click",function(event) {
                index--;
                change();
                selectChange.call(imgSmall.eq(index));
            });

            $self.find(".next2").on("click",function(event) {          //微缩图左右翻页键

                if(index == imgSmall.length-1)
                   {
                    return ;
                }
                index++;
                change();
                selectChange.call(imgSmall.eq(index));

            });

            $self.find(".prev2").on("click",function(event) {
                if(index == 0){
                    return ;
                }
                index--;
                change();
                selectChange.call(imgSmall.eq(index))
            });

            imgSmall.click(function(event) {           //点击小图片跳转大图片
                index = $(this).index();
                change();
                selectChange.call(this)
             })

             banner2.mouseover(function(event) {      //控制banner2滚动条出现
                    showScroll();
             }).mouseout(function(){
                    showScroll();
             });



             $self.find(".clear").click(function(event) {        //清除键
                    if(imgSmall.length == 1){         //删除最后剩下的一张时，全部清空
                         // $self.trigger('ClearBtnClick',[index]);
                         settings.onClearBtnClick(index,function(){
                            imgBig.empty();
                            $self.find(".imgList2").empty();
                         });

                        return ;
                    }
                    else if (index == imgBig.length-2 ) {
                         // $self.trigger('ClearBtnClick',[index]);
                         settings.onClearBtnClick(index,function(){

                            $self.find(".imgList").css("left",0);
                            index = 0;
                         });
                        // reMove();
                    }
                    else if(index == 0 ){         //若删除第一个图片即删除与它相同的最后一张图片，并复制第二张图片
                         // $self.trigger('ClearBtnClick',[index]);
                         settings.onClearBtnClick(index);
                         // reMove();

                    }
                    else if(index == imgBig.length-1){     //  删除最后一张时
                         // $self.trigger('ClearBtnClick',[0]);
                         settings.onClearBtnClick(0,function(){
                            $self.find(".imgList").css("left",-index*imgBig.width());
                         });
                     // reMove();

                    }
                    else {
                        // reMove();
                         // $self.trigger('ClearBtnClick',[index]);
                         settings.onClearBtnClick(index);
                    };
                     // imgBig = banner1.find('.imgbig');
                     // imgSmall = banner2.find('.imgs');
                     // selectChange.call(imgSmall.eq(index));

             });

        }
             function selectChange(){                   //选择框改变函数
                $(this).addClass("active").siblings().removeClass('active');
             }
})(jQuery);