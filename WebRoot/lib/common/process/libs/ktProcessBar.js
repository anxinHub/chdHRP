
!function (t, e, n) { "use strict"; function r(t) { var e = /^#?([a-f\d])([a-f\d])([a-f\d])$/i; t = t.replace(e, function (t, e, n, r) { return e + e + n + n + r + r }); var n = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(t); return n ? [parseInt(n[1], 16), parseInt(n[2], 16), parseInt(n[3], 16)] : null } function i(t, e, n, r) { return Math.round(n + (r - n) * t / e) } function a(t, e, n, a, o) { var u = -1 != o.indexOf("#") ? r(o) : o.match(/\d+/g), l = -1 != a.indexOf("#") ? r(a) : a.match(/\d+/g), s = n - e, h = t - e; return u && l ? "rgb(" + i(h, s, l[0], u[0]) + "," + i(h, s, l[1], u[1]) + "," + i(h, s, l[2], u[2]) + ")" : null } function o() { for (var t = arguments, e = t[0], n = 1, r = t.length; r > n; n++) { var i = t[n]; for (var a in i) i.hasOwnProperty(a) && (e[a] = i[a]) } return e } function u(t) { return function (e) { if (!t) return e.toString(); e = e || 0; for (var n = e.toString().split("").reverse(), r = t.split("").reverse(), i = 0, a = 0, o = r.length; o > i && n.length; i++)"#" == r[i] && (a = i, r[i] = n.shift()); return r.splice(a + 1, r.lastIndexOf("#") - a, n.reverse().join("")), r.reverse().join("") } } function l(t, e) { e = e || {}, e = o({}, s.defaults, e), this.indOption = e, "string" == typeof t && (t = n.querySelector(t)), t.length && (t = t[0]), this.container = t; var r = n.createElement("canvas"); t.appendChild(r), this.canElm = r, this.ctx = r.getContext("2d"), this.current_value = e.initValue || e.minValue || 0 } function s(t, e) { var n = new l(t, e); return n.init(), n } var h = 2 * Math.PI, f = Math.PI / 2; l.prototype = { constructor: s, init: function () { var t = this.indOption, e = this.canElm, n = this.ctx, r = 2 * (t.radius + t.barWidth), i = r / 2; return this.formatter = "function" == typeof t.format ? t.format : u(t.format), this.maxLength = t.percentage ? 4 : this.formatter(t.maxValue).length, e.width = r, e.height = r, n.strokeStyle = t.barBgColor, n.lineWidth = t.barWidth, n.beginPath(), n.arc(i, i, t.radius, 0, 2 * Math.PI), n.stroke(), this.imgData = n.getImageData(0, 0, r, r), this.value(this.current_value), this }, value: function (t) { if (void 0 === t || isNaN(t)) return this.current_value; t = parseInt(t); var e = this.ctx, n = this.indOption, r = n.barColor, i = 2 * (n.radius + n.barWidth), o = n.minValue, u = n.maxValue, l = i / 2; t = o > t ? o : t > u ? u : t; var s = Math.round(100 * (t - o) / (u - o) * 100) / 100, c = n.percentage ? s + "%" : this.formatter(t); if (this.current_value = t, e.putImageData(this.imgData, 0, 0), "object" == typeof r) for (var d = Object.keys(r), m = 1, v = d.length; v > m; m++) { var g = d[m - 1], p = d[m], x = r[g], b = r[p], I = t == g ? x : t == p ? b : t > g && p > t ? n.interpolate ? a(t, g, p, x, b) : b : !1; if (0 != I) { r = I; break } } if (e.strokeStyle = r, n.roundCorner && (e.lineCap = "round"), e.beginPath(), e.arc(l, l, n.radius, -f, h * s / 100 - f, !1), e.stroke(), n.displayNumber) { var y = e.font.split(" "), C = n.fontWeight, O = n.fontSize || i / (this.maxLength - (Math.floor(1.4 * this.maxLength / 4) - 1)); y = n.fontFamily || y[y.length - 1], e.fillStyle = n.fontColor || r, e.font = C + " " + O + "px " + y, e.textAlign = "center", e.textBaseline = "middle", e.fillText(c, l, l) } return this }, animate: function (t) { var e = this.indOption, n = this.current_value || e.minValue, r = this, i = Math.ceil((e.maxValue - e.minValue) / (e.frameNum || (e.percentage ? 100 : 500))), a = n > t; return this.intvFunc && clearInterval(this.intvFunc), this.intvFunc = setInterval(function () { if (!a && n >= t || a && t >= n) { if (r.current_value == n) return void clearInterval(r.intvFunc); n = t } r.value(n), n != t && (n += a ? -i : i) }, e.frameTime), this }, option: function (t, e) { return void 0 === e ? this.option[t] : (-1 != ["radius", "barWidth", "barBgColor", "format", "maxValue", "percentage"].indexOf(t) && (this.indOption[t] = e, this.init().value(this.current_value)), void (this.indOption[t] = e)) } }, s.defaults = { radius: 50, barWidth: 5, barBgColor: "#eeeeee", barColor: "#99CC33", format: null, frameTime: 10, frameNum: null, fontColor: null, fontFamily: null, fontWeight: "bold", fontSize: null, interpolate: !0, percentage: !1, displayNumber: !0, roundCorner: !1, minValue: 0, maxValue: 100, initValue: 0 }, e.radialIndicator = s, t && (t.fn.radialIndicator = function (e) { return this.each(function () { var n = s(this, e); t.data(this, "radialIndicator", n) }) }) } (window.jQuery, window, document, void 0);


//引入的canvas画圆形百分比进度条
/**
 * Circle
 */
!(function ($) {
    $.fn.ktProcessCircle = function (option) {

        var defaultOption = {

        };
        var settings = $.extend({}, defaultOption, option);
        var $self = this;

        //构建圆形进度条
        function _initCircle() {
            var radialObj = $.fn.radialIndicator.call($self, {
                barColor: {
                    0: '#FF0000',
                    33: '#FFFF00',
                    66: '#0066FF',
                    100: '#33CC33'
                },
                percentage: true
            }).data('radialIndicator');
            return radialObj;
        }

        //构建设置百分比函数
        function _setCircle() {
            var radialObj = _initCircle();
            return function (percent) {
                radialObj.animate(percent);
            }
        }

        //返回函数
        this.fn = {
            setCircle: _setCircle()
        }

        return this;
    }
})(jQuery);


/**
 * Bar
 */
!(function ($) {
    $.fn.ktProcessBar = function (option) {

        var defaultOption = {
            data: [],
        };

        var settings = $.extend({}, defaultOption, option);
        var $self = this;

        //class名字
        var className = {
            start: 'step-start',
            active: 'step-active',
            done: 'step-done',
            undone: 'step-undone',
            error: 'step-error'
        };

        //构建步骤进度条
        function _initBar() {
            var $ul = $('<ul class="step clear"></ul>');
            var html = "";
            $(settings.data).each(function (i, v) {
                var liClass = i == 0 ? className.start : className.undone;
                html += `<li class="${liClass}">
                        <div>
                            <div class="process"></div>
                        </div>
                        <span>${this.name}</span>
                        <i></i>
                    </li>`;
            });
            $ul.html(html)
            $self.html("").append($ul);
        }


        /**
         * 单步执行
         */
        function execStep() {
            var mainStep = $('.step');
            var start = mainStep.find(".step-start").length > 0 ? mainStep.find(".step-start") : mainStep.find('.step-active');//当前步骤节点
            start.removeClass(className.start).removeClass(className.active).addClass(className.done);
            start.next().removeClass(className.undone).addClass(className.active);
        }

        /**
         * 单步报错
         */
        function execError() {
            var mainStep = $('.step');
            var start = mainStep.find(".step-start").length > 0 ? mainStep.find(".step-start") : mainStep.find('.step-active');//当前步骤节点
            start.removeClass(className.start).removeClass(className.active).addClass(className.done);
            start.next().removeClass(className.undone).addClass(className.error);
        }

        /**
         * 跳到指定步骤
         */
        function execStepTarget(num) {
            var mainStep = $('.step');
            var start = mainStep.find(".step-start").length > 0 ? mainStep.find(".step-start") : mainStep.find('.step-active');//当前步骤节点
            var curStep = start.parent().find('li').index(start);//当前步骤
            num = num - curStep;
            for (var i = 1; i <= num; i++) {
                if (i == 1) {
                    execStep();
                } else {
                    setTimeout(function () {
                        execStep();
                    }, 500 * (i - 1));
                }
            }
        }

        /**
         * 指定步骤报错
         */
        function execErrorTarget(num) {
            var mainStep = $('.step');
            var start = mainStep.find(".step-start").length > 0 ? mainStep.find(".step-start") : mainStep.find('.step-active');//当前节点
            var curStep = start.parent().find('li').index(start);//当前步骤
            num = num - curStep;
            for (var i = 1; i <= num; i++) {
                if (i == 1) {//首次
                    if (num == 1) {  //首次报错
                        execError();
                    } else {
                        execStep();
                    }
                } else {
                    if (i == num) { //报错步骤
                        setTimeout(function () {
                            execError();
                        }, 500 * (i - 1));
                    }else{ //其他步骤
                         setTimeout(function () {
                            execStep();
                        }, 500 * (i - 1));
                    }
                }
            }
        }

        //刷新步骤
        function refresh() {
            _initBar();
        }

        //对外接口
        this.fn = {
            refresh,
            execStep,
            execStepTarget,
            execError,
            execErrorTarget
        }

        //开始构造
        _initBar();
        return this;
    }
})(jQuery);