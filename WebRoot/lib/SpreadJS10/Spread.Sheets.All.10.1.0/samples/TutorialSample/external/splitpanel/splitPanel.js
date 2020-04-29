(function($) {
    var ns_sample = ".sample",
        e_mousedown = "mousedown" + ns_sample,
        e_mousemove = "mousemove" + ns_sample,
        e_mouseup = "mouseup" + ns_sample,
        e_click = "click" + ns_sample,
        e_resize = "resize" + ns_sample,
        e_scroll = "scroll" + ns_sample,
        e_resized = "resized" + ns_sample,
        e_collapsable = "collapsable" + ns_sample,
        css_cursor = "cursor",
        CSS_CLASS_NONE_USER_SELECT = "sample-none-user-select";
    $(window).scrollTop(0);
    $.fn.splitterPanel = function() {
        var SplitterPanel = (function() {
            function SplitterPanel(panel) {
                this._panel = panel;
                var $panel = this.$panel = $(panel);
                this._firstElement = $panel.children()[0];
                this._secondElement = $panel.children()[1];
                var orientation = $panel.attr("orientation");
                if (orientation === "horizontal") {
                    this._isHorizontal = true;
                } else if (orientation === "vertical") {
                    this._isHorizontal = false;
                }
                this._splitScale = parseFloat($panel.attr("splitScale"));

                this._splitterSize = 10;
                this._isMouseCapture = false;
                this._isMousedownInSplitter = false;

                this._splitterMovingDiv = null;
                this._minOffsetSize = 40;
                this._minFirstSize = 300;
                this._minSecondSize = 450;

                this.refreshLayout();
            }
            SplitterPanel.prototype.getPanel = function () {
                return this._panel;
            };
            SplitterPanel.prototype.splitScale = function (value) {
                var self = this;
                if (arguments.length === 0) {
                    return self._splitScale;
                }
                if (typeof value === 'number') {
                    self._splitScale = value;
                    self.refreshLayout(true);
                }
                return self;
            };
            SplitterPanel.prototype.splitterSize = function (value) {
                var self = this;
                if (arguments.length === 0) {
                    return self._splitterSize;
                }
                if (typeof value === 'number' && value !== self._splitterSize) {
                    self._splitterSize = value;
                    self.refreshLayout();
                }
                return self;
            };
            SplitterPanel.prototype.refreshLayout = function(isAnimation) {
                var css_splitterDiv = "splitterPanel-horizontal-splitter",
                    css_firstDiv = "splitterPanel-horizontal-first",
                    css_secondDiv = "splitterPanel-horizontal-second",
                    $panel = this.$panel,
                    isHorizontal = this._isHorizontal,
                    css_size = "width",
                    firstSize = 0, secondSize = 0, panelSize = 0;
                if (isHorizontal) {
                    panelSize = $panel.width();
                } else {
                    css_splitterDiv = "splitterPanel-vertical-splitter";
                    css_firstDiv = "splitterPanel-vertical-first";
                    css_secondDiv = "splitterPanel-vertical-second";
                    css_size = "height";
                    panelSize = $panel.height();
                }
                firstSize = (panelSize - this._splitterSize) * this._splitScale;
                secondSize = panelSize - this._splitterSize - firstSize;

                // -1 to reduce fractional part
                firstSize -= 1;
                secondSize -= 1;

                var firstDiv = this._firstDiv, $firstDiv;
                if (!firstDiv) {
                    firstDiv = this._firstDiv = document.createElement('div');
                    $firstDiv = $(this._firstDiv);
                    $firstDiv.addClass(css_firstDiv);
                    $(this._firstElement).css({
                        width: '100%',
                        height: '100%'
                    });
                    $firstDiv.append(this._firstElement);
                }
                $firstDiv = $(firstDiv);

                if (isAnimation) {
                    if (isHorizontal) {
                        $firstDiv.animate({width: firstSize}, 'fast');
                    } else {
                        $firstDiv.animate({height: firstSize}, 'fast');
                    }
                } else {
                    $firstDiv.css(css_size, firstSize);
                }

                var splitterDiv = this._splitterDiv, $splitterDiv;
                if (!splitterDiv) {
                    splitterDiv = this._splitterDiv = document.createElement('div');
                    $splitterDiv = $(splitterDiv);
                    $splitterDiv.addClass(css_splitterDiv);
                    $splitterDiv.css(css_size, this._splitterSize);
                }
                $splitterDiv = $(splitterDiv);

                var secondDiv = this._secondDiv, $secondDiv;
                if (!secondDiv) {
                    secondDiv = this._secondDiv = document.createElement('div');
                    $secondDiv = $(secondDiv);
                    $secondDiv.addClass(css_secondDiv);
                    $(this._secondElement).css({
                        width: '100%',
                        height: '100%'
                    });
                    $secondDiv.append(this._secondElement);
                }
                $secondDiv = $(secondDiv);

                if (isAnimation) {
                    if (isHorizontal) {
                        $secondDiv.animate({ width: secondSize }, 'fast');
                    } else {
                        $secondDiv.animate({ height: secondSize }, 'fast');
                    }
                } else {
                    $secondDiv.css(css_size, secondSize);
                }

                if (isHorizontal) {
                    $splitterDiv.css("height", $panel.height());
                }
                if (!this._panelWrapper) {
                    var panelWrapper = document.createElement('div'),
                        $panelWrapper = $(panelWrapper);
                    this._panelWrapper = panelWrapper;
                    $panelWrapper.css({
                        position: 'relative',
                        width: '100%',
                        height: '100%'
                    });
                    $panel.append(panelWrapper);
                    $panelWrapper.append(firstDiv)
                        .append(splitterDiv)
                        .append(secondDiv);
                    this._initEvent();
                }
            };
            SplitterPanel.prototype._initEvent = function() {
                var self = this;
                $(this._splitterDiv).bind(e_mousedown, function(e) {
                    self.doMouseDown(e);
                });
                $(window).bind(e_resize, function(e) {
                    self.refreshLayout();
                });
            };
            SplitterPanel.prototype.handleDocumentMouseMove = function() {
                var self = this;
                if (!self._isMouseCapture) {
                    $(document).bind(e_mousemove, function (e) {
                        self.doMouseMove(e);
                    });
                    $(document).bind(e_mouseup, function (e) {
                        self.doMouseUp(e);
                    });
                    self._isMouseCapture = true;
                }
            };
            SplitterPanel.prototype.unhandleDocumentMouseMove = function() {
                if (this._isMouseCapture) {
                    this._isMouseCapture = false;
                    $(document).unbind(e_mousemove);
                    $(document).unbind(e_mouseup);
                }
            };
            SplitterPanel.prototype.doMouseDown = function(e) {
                if (e.button !== 0) {
                    return;
                }
                var splitterMovingDiv = document.createElement('div'),
                    splitterContainerDiv = document.createElement('div'),
                    $splitterContainerDiv = $(splitterContainerDiv),
                    $splitterDiv = $(this._splitterDiv),
                    $splitterMovingDiv = $(splitterMovingDiv),
                    $panel = this.$panel;
                this._splitterMovingDiv = splitterMovingDiv;
                this._splitterContainerDiv = splitterContainerDiv;
                var panelOffset = $panel.offset(),
                    posLeft = 0,
                    posTop = 0;
                if (this._isHorizontal) {
                    var posStartOffset = $splitterDiv.offset(),
                        verticalSplitterHeight = $splitterDiv.height();
                    if (panelOffset && posStartOffset) {
                        posLeft = posStartOffset.left - panelOffset.left;
                        posTop = posStartOffset.top - panelOffset.top;
                    }
                    $splitterMovingDiv.addClass("splitterPanel-verticalSplitter-moving")
                        .css({
                            left: posLeft,
                            top: posTop,
                            width: this._splitterSize,
                            height: verticalSplitterHeight
                        });
                    $splitterContainerDiv.css(css_cursor, 'e-resize');
                } else {
                    var posStartOffset = $splitterDiv.offset();
                    if (panelOffset && posStartOffset) {
                        posLeft = posStartOffset.left - panelOffset.left;
                        posTop = posStartOffset.top - panelOffset.top;
                    }
                    var splitterWidth = $splitterDiv.width();
                    $splitterMovingDiv.addClass("splitterPanel-horizontalSplitter-moving")
                        .css({
                            left: posLeft,
                            top: posTop,
                            height: this._splitterSize,
                            width: splitterWidth
                        });
                    $splitterContainerDiv.css(css_cursor, 'n-resize');
                }
                $splitterContainerDiv.addClass("splitterPanel-splitter-container");
                this.handleDocumentMouseMove();
                $splitterContainerDiv.append(splitterMovingDiv);
                $(this._panelWrapper).append(splitterContainerDiv);
                this._isMousedownInSplitter = true;
            };
            SplitterPanel.prototype.doMouseMove = function(e) {

                var splitterMovingDiv = $(this._splitterMovingDiv),
                    $panel = this.$panel;
                if (this._isMousedownInSplitter && splitterMovingDiv) {
                    var panelOffset = $panel.offset();
                    if (!panelOffset) {
                        panelOffset = {
                            left: 0,
                            top: 0
                        };
                    }
                    if (this._isHorizontal) {
                        var width = $panel.width(),
                            posLeft = e.pageX - panelOffset.left;
                        if (posLeft > width - this._minOffsetSize) {
                            posLeft = width - this._minOffsetSize;
                        } else if (posLeft < this._minOffsetSize) {
                            posLeft = this._minOffsetSize;
                        }
                        splitterMovingDiv.css("left", posLeft);
                    } else {
                        var height = $panel.height(),
                            posTop = e.pageY - panelOffset.top;
                        if (posTop > height - this._minOffsetSize - this._splitterSize) {
                            posTop = height - this._minOffsetSize - this._splitterSize;
                        } else if (posTop < this._minOffsetSize) {
                            posTop = this._minOffsetSize;
                        }
                        splitterMovingDiv.css("top", posTop);
                    }
                    $(document.body).addClass(CSS_CLASS_NONE_USER_SELECT);
                }
            };
            SplitterPanel.prototype.doMouseUp = function(e) {
                var $splitterMovingDiv = $(this._splitterMovingDiv),
                    $panel = this.$panel,
                    $firstDiv = $(this._firstDiv),
                    $secondDiv = $(this._secondDiv),
                    splitterSize = this._splitterSize;
                var args = {
                    firstSize: 0,
                    secondSize: 0
                }
                if (this._isHorizontal) {
                    var left = parseFloat($splitterMovingDiv.css('left'));
                    var width = $panel.width();
                    if (isNaN(left) || left < this._minFirstSize) {
                        left = this._minFirstSize;
                    }
                    var secondDivWidth = width - left - splitterSize;
                    if (secondDivWidth < this._minSecondSize) {
                        secondDivWidth = this._minSecondSize;
                        left = width - secondDivWidth - splitterSize;
                    }

                    // -1 to reduce fractional part
                    left -= 1;
                    secondDivWidth -= 1;

                    $firstDiv.width(left);
                    $secondDiv.width(secondDivWidth);
                    var firstDivHeight = $firstDiv.height(), secondDivHeight = $secondDiv.height();
                    var splitterDivHeight =  Math.max(firstDivHeight, secondDivHeight);
                    $(this._splitterDiv).css("height", splitterDivHeight);
                    args.firstSize = left;
                    args.secondSize = secondDivWidth;
                } else {
                    var top = parseFloat($splitterMovingDiv.css('top'));
                    var height = $panel.height();
                    if (isNaN(top)) {
                        top = 0;
                    }
                    $firstDiv.height(top);
                    $secondDiv.height(height -  splitterSize - top);
                    args.firstSize = top;
                    args.secondSize = height -  splitterSize - top;
                }
                this._splitScale = args.firstSize / (args.firstSize + args.secondSize);
                $panel.trigger(e_resized, args);
                $(document.body).removeClass(CSS_CLASS_NONE_USER_SELECT);
                this.unhandleDocumentMouseMove();
                this._isMousedownInSplitter = false;
                $splitterMovingDiv.remove();
                $splitterMovingDiv = null;
                $(this._splitterContainerDiv).remove();
                this._splitterContainerDiv = null;
            };
            return SplitterPanel;
        })();

        return this.each(function() {
            var element = $(this), panel = element.data('splitPanel');
            if (!panel) {
                var panel = new SplitterPanel(element.get(0));
                element.data('splitPanel', panel)
            }
        });
    };
    $.fn.collapse = function () {
        var Collapse = (function() {
            function Collapse(triggerEle) {
                this._triggerEle = triggerEle;
                var $triggerEle = $(triggerEle);
                var collapseBindClass = $triggerEle.attr("collapseBind");
                $triggerEle.addClass('collapse-unfold');
                this._collapseBind = $("." + collapseBindClass);
                this._minHeight = 0;
                this.collapseCallBack = null; //default call back.
                this.collapseWay = null; //collapse function.
                this.collapsed = false;
                this.initialize();
            }
            Collapse.prototype.getMinHeight = function () {
                return this._minHeight;
            };
            Collapse.prototype.getTriggerElement = function () {
                return this._triggerEle;
            };
            Collapse.prototype.getBindElement = function () {
                return this._collapseBind;
            };
            Collapse.prototype.initialize = function() {
                var self = this,
                    $triggerEle = $(this._triggerEle),
                    $collapseBind = this._collapseBind;
                this._cacheSize = $collapseBind.height();
                $triggerEle.bind(e_click, function(e) {
                    self.triggerEleClick(e);
                });
            };
            Collapse.prototype.triggerEleClick = function(e) {
                var self = this,
                    $triggerEle = $(this._triggerEle);
                if (typeof this.collapseWay === 'function') {
                    this.collapseWay();
                } else {
                    var $collapseBind = this._collapseBind,
                        collapseBindHeight = $collapseBind.height(),
                        minHeight = this._minHeight,
                        resultHeight = 0;
                    if (collapseBindHeight !== minHeight) {
                        this._cacheSize = collapseBindHeight;
                        $collapseBind.animate({height: minHeight + 'px'}, {
                            speed: 'fast',
                            esaing: 'linear',
                            queue: false,
                            complete: function() {
                                if (typeof self.collapseCallBack === 'function') {
                                    self.collapseCallBack(minHeight);
                                }
                            }
                        });
                        resultHeight = minHeight;
                        this.collapsed = true;
                    } else if (collapseBindHeight === minHeight) {
                        $collapseBind.animate({height: this._cacheSize + 'px'}, {
                            speed: 'fast',
                            esaing: 'linear',
                            queue: false,
                            complete: function() {
                                if (typeof self.collapseCallBack === 'function') {
                                    self.collapseCallBack(self._cacheSize);
                                }
                            }
                        });
                        this.collapsed = false;
                    }
                }
                if (this.collapsed === true) {
                    $triggerEle.removeClass('collapse-unfold');
                    $triggerEle.addClass('collapse-fold');
                } else {
                    $triggerEle.removeClass('collapse-fold');
                    $triggerEle.addClass('collapse-unfold');
                }
            };
            return Collapse;
        })();

        return this.each(function() {
            var element = $(this), panel = element.data('collapse');
            if (!panel) {
                var panel = new Collapse(element.get(0));
                element.data('collapse', panel);
            }
        });
    };
})(jQuery);
