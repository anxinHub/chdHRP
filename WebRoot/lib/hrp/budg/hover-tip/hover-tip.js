/**
 * 鼠标悬浮提示 wsj 2017.5.26
 * @param  {obj} $ [属性对象, 不填则默认]
 * 
 * example.
 *
 * $("#example").hoverTip({...})
 */
(function ($) {
    $.fn.hoverTip = function (paramObj) {
        var _this = this,
            timmer = null,
            timeFlag = true, // 避免重复添加tip，增加一个tip
            tipEl, // 提示框对象
            tipElWidth; // 提示框宽度
        var showTimes = 0;

        var DEFAULT = {
            tip: "提示", // 提示的描述字符
            showTime: 3000, // 显示时间,毫秒
            width: "auto",
            height: "auto"
        }

        $.extend(DEFAULT, paramObj);

        /**
         * 鼠标移入
         * @return {[type]} [description]
         */
        var mouseEnter = function () {
            if (!timeFlag) {
                return;
            }
            timeFlag = false;
            showTimes++;  // 显示三次后。不再执行
            _this.addClass("hover-relative"); // 先添加相对定位，便于提示框绝对定位
            tipEl = $("<div class='hover-tip-default'>" + DEFAULT.tip + "</div>");
            _this.append(tipEl);

            tipEl.css({
                width: DEFAULT.width,
                height: DEFAULT.height
            })

            tipElWidth = tipEl.width();

            timmer = setTimeout(function () {
                tipEl.remove();
                timeFlag = true;
            }, DEFAULT.showTime)
        }
        /**
         * 鼠标移动时间
         */
        var mouseMove = function (mouseX, mouseY) {
            // 每次移动获取鼠标位置，设置tip的位置
            var top,
                left,
                offset = 10,

            top = mouseY + offset;
            left = mouseX + offset;

            // 当接近右边时。让tip靠左显示
            if ((mouseX + tipElWidth + 40) >= _this.width()) {
                left = mouseX - tipElWidth - (offset * 3)
            }

            tipEl.css({
                top: top,
                left: left
            })
        }
        /**
         * 鼠标离开，参数重置
         */
        var mouseLeave = function () {
            _this.removeClass("hover-relative");
            tipEl.remove();
            clearTimeout(timmer);
            timeFlag = true;
        }

        
        _this
            .on("mouseenter", function () {
                if (showTimes > 3) {
                    return;
                }
                mouseEnter();
            })
            .on("mousemove", function (e) {
                if (showTimes > 3) {
                    return;
                }
                var mouseX = e.pageX;
                var mouseY = e.pageY;
                mouseMove(mouseX, mouseY);
            })
            .on("mouseleave", function () {
                if (showTimes > 3) {
                    return;
                }
                mouseLeave();
            })
    }
})(jQuery)