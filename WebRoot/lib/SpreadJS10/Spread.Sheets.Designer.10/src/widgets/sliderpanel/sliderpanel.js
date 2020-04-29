var __extends = this.__extends || function (d, b) {
    for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
    function __() { this.constructor = d; }
    __.prototype = b.prototype;
    d.prototype = new __();
};
var GC;
(function (GC) {
    (function (Spread) {
    (function (Sheets) {
        (function (designer) {
            var widgetName = "sliderpanel", SPREADMINWIDTH = 30;

            var SliderPanel = (function (_super) {
                __extends(SliderPanel, _super);
                function SliderPanel() {
                    _super.apply(this, arguments);
                }
                SliderPanel.prototype._create = function () {
                    this._spreadPanel = designer.wrapper.spreadElement;
                    this._sliderHandleDOM = $(".design-mode-panel-handler");
                    this._spreadResizeStartEvent = "mousedown.resizeStart";
                    this._spreadResizeEndEvent = "mouseup.resizeEnd";
                    this._spreadContinueResizeEvent = "mousemove.continueResize";
                    this._isCapture = false;
                    this._gap = 0;
                    this._xOffset = 0;
                    var container = this.element;

                    //header
                    var header = $("<div>").css({
                        "background": "-webkit-gradient(linear, left top, left bottom, color-stop(15%,#E4E5E8), color-stop(85%, rgb(211, 211, 211)))",
                        "color": "rgba(0, 0, 0, 0.6)"
                    });
                    $("<label>").text(this.options.title).css({
                        font: "Bold 12pt Calibri"
                    }).appendTo(header);
                    header.appendTo(container);

                    //content
                    var headerHeight = header.height();
                    var containerHeight = container.height();
                    var sliderPanelHeight = 0;
                    if (!isNaN(headerHeight) && !isNaN(containerHeight)) {
                        sliderPanelHeight = containerHeight - headerHeight - 18;
                    }
                    this._sliderPanelContent = $("<div>").css({
                        "height": sliderPanelHeight + "px",
                        "width": "100%",
                        "overflow": "auto"
                    }).addClass("slider-panel-content").appendTo(container);

                    //guides line
                    this._guidesLine = $("<div>").addClass("slider-panel-guidesline").appendTo(container);

                    //event
                    this._addEvents();
                };

                SliderPanel.prototype._setOption = function (key, value) {
                };

                SliderPanel.prototype._addEvents = function () {
                    var self = this;
                    var totalWidth = $(window).width();
                    this._sliderHandleDOM.bind(this._spreadResizeStartEvent, function (event) {
                        self._xOffset = event.pageX;
                        if (!self._isCapture) {
                            $(document).bind(self._spreadContinueResizeEvent, function (event) {
                                var canvasOffset = $(designer.util.getCanvas(designer.wrapper.spread)).offset();
                                var xPos = event.pageX;
                                var yPos = canvasOffset.top;
                                self._guidesLine.css("display", "inline-block");
                                self._guidesLine.offset({
                                    left: event.pageX,
                                    top: canvasOffset.top
                                });
                            }).bind(self._spreadResizeEndEvent, function (event) {
                                $(document).unbind(self._spreadContinueResizeEvent);
                                $(document).unbind(self._spreadResizeEndEvent);
                                self._guidesLine.css("display", "none");
                                self._xOffset = event.pageX - self._xOffset;
                                self._refreshSize(self._xOffset);
                                self._isCapture = false;
                            });
                            self._isCapture = true;
                        }
                    });

                    $(window).resize(function () {
                        self._refreshSize();
                    });
                };

                SliderPanel.prototype._refreshSize = function (xOffset) {
                    var content = $(".content");
                    var designModePanel = this.element;
                    var spreadHeight = $(".content").children('.fill-spread-content').height();
                    if (designModePanel.is(":visible")) {
                        if (!xOffset) {
                            xOffset = 0;
                        }
                        var designModeWidth = parseInt(designModePanel.css("width"));
                        if (isNaN(designModeWidth)) {
                            return;
                        }
                        var designModeWidth = designModeWidth - xOffset;
                        if (designModeWidth < 0) {
                            designModeWidth = 0;
                        }
                        var spreadTargetWidth = $(window).width() - designModeWidth - this._gap;
                        if (spreadTargetWidth < SPREADMINWIDTH) {
                            spreadTargetWidth = SPREADMINWIDTH;
                            designModeWidth = $(window).width() - spreadTargetWidth - this._gap;
                        }
                        content.css("width", spreadTargetWidth);
                        designer.wrapper.spreadElement.css("width", spreadTargetWidth);
                        designModePanel.css("width", designModeWidth);
                        designer.wrapper.spread.refresh();
                    }
                    content.css('height', spreadHeight);
                };

                SliderPanel.prototype.open = function () {
                    if (this.element.is(":visible")) {
                        return;
                    }
                    var totalWidth = $(window).width();
                    if (!totalWidth || isNaN(totalWidth)) {
                        return;
                    }
                    this._gap = 0;
                    var sliderHandleDOMBorderWidth = parseInt(this._sliderHandleDOM.css("border-width"));
                    if (!isNaN(sliderHandleDOMBorderWidth)) {
                        this._gap += 2 * sliderHandleDOMBorderWidth;
                    }
                    var sliderPanelBorderWidth = 1;
                    this._gap += 2 * sliderPanelBorderWidth;
                    var sliderWidth = 0;
                    if (!isNaN(this.options.width)) {
                        sliderWidth = this.options.width;
                    }
                    this._spreadPanel.css({
                        "display": "inline-block",
                        "width": totalWidth - this._gap - sliderWidth + "px"
                    });
                    designer.wrapper.spread.refresh();
                    this.element.css({
                        "display": "inline-block",
                        "width": sliderWidth + "px",
                        "border": sliderPanelBorderWidth + "px solid lightgray"
                    });
                    this.element.show();
                    this._sliderHandleDOM.css({
                        "display": "inline-block"
                    });
                    this._sliderHandleDOM.show();
                };

                SliderPanel.prototype.close = function () {
                    if (!this.element.is(":visible")) {
                        return;
                    }
                    this.element.hide();
                    var content = $(".content");
                    content.css("width", "100%");
                    this._spreadPanel.css("width", "100%");
                    designer.wrapper.spread.refresh();
                };

                SliderPanel.prototype.getContent = function () {
                    if (this._sliderPanelContent) {
                        return this._sliderPanelContent;
                    }
                };
                SliderPanel.DefaultOptions = {
                    title: designer.res.sliderPanel.title,
                    width: 250,
                    backColor: "white"
                };
                return SliderPanel;
            })(gcui.gcuiWidget);
            designer.SliderPanel = SliderPanel;

            SliderPanel.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, SliderPanel.DefaultOptions);
            $.gcui.registerWidget(widgetName, SliderPanel.prototype);
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
