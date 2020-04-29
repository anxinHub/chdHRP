(function ($) {
    $.fn.ktLayer = function (option) {

        var defaultOption = {
            bgColor: "#E0EDFF",//背景颜色
            closeHeight: 50,//关闭状态高度
            speed: 100,//展开收起速度
            direction: "down",    //展开方向(向上还是向下)  up/down
            Descript: ["open", "close"],//展开收起描述
            BtnbgColor: "#81c0f2",
            BtnbgColorHover: "#FFBE76",
            zIndex: 999999, 
            BtnbgImg: { open: 'top.jpg', close: 'loading.gif' },    //展开(open)收起(close)背景图路径
            open: function () {
                //console.log("打开回调函数");
            },
            close: function () {
                //console.log("关闭回调函数");
            }
        };

        var settings = $.extend({}, defaultOption, option);
        var $self = this;
        var content = $self.html();//获取所有内容
        var Tpos = "", Bpos = "",         //初始化按钮的top与bottom值
            Tdir = "", Bdir = "";            //初始化内容容器的top与bottom值
        if (settings.direction == "down") { Tdir = 0; Bpos = -25; }
        else if (settings.direction == "up") { Bdir = 0; Tpos = -25; }
        $self.html("") //主容器
            .css({
                "position": "relative",
                "height": settings.closeHeight,
                "display": "block"
            });
        //内容容器
        var $ktContent = $("<div></div>")
            .html(content)
            .css({
                "backgroundColor": settings.bgColor,
                "position": "absolute",
                "width": "100%",
                "bottom": Bdir,
                "top": Tdir,
                "z-index": settings.zIndex

            })
            .appendTo($self);
        //按钮参数
        var bWidth=settings.bwidth?settings.bwidth:100;
        var $ktBtn = $("<button></button>")
            .text(settings.Descript[0]).width(bWidth).height(25).hover(function () {
                $(this).css("backgroundColor", settings.BtnbgColorHover)
            }, function () {$(this).css("backgroundColor", settings.BtnbgColor) })
            .css({
                "position": "absolute",
                "background": settings.BtnbgColor + " url(" + settings.BtnbgImg.open + ") no-repeat 10px center",
                "backgroundSize": "10px",
                "paddingLeft": "15px",
                "right": 0,
                "border": "1px solid #BED5F3",
                "borderRadius": "3px",
                "bottom": Bpos,
                "top": Tpos
            })
            .click(function () {
                var $content = $ktContent;
                var isClose = $content.children("div").is(":hidden");
                if (isClose) {
                    _open();
                } else {
                    _close();
                }
            })
            .appendTo($ktContent);
        
        var _open = _createOpen();
        function _createOpen() {
            var _content = $ktContent, _btn = $ktBtn, _settings = settings;
            return function () {
                _btn.css("backgroundImage", "url(" + _settings.BtnbgImg.close + ") ");
                _btn.text(_settings.Descript[1]);
                _content.children().not(_btn).slideDown(_settings.speed);
                _settings.open();
            }
        }
        var _close = _createClose();
        function _createClose() {
            var _content = $ktContent, _btn = $ktBtn, _settings = settings;
            return function () {
                _btn.css("backgroundImage", "url(" + _settings.BtnbgImg.open + ")");
                _btn.text(_settings.Descript[0]);
                _content.children().not(_btn).slideUp(_settings.speed);
                _settings.close();
            }
        }

        this.fn = {
            open:_open,
            close:_close
        }
        return this;
    }
})(jQuery)