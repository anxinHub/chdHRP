var __extends = this.__extends || function (d, b) {
        for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
        function __() {
            this.constructor = d;
        }

        __.prototype = b.prototype;
        d.prototype = new __();
    };
var GC;
(function (GC) {
    (function (Spread) {
        (function (Sheets) {
            (function (designer) {
                var widgetName = "borderpicker";

                var BorderPicker = (function (_super) {
                    __extends(BorderPicker, _super);
                    function BorderPicker() {
                        _super.apply(this, arguments);
                    }

                    BorderPicker.prototype._create = function () {
                        this.element.addClass("gcui-borderpicker");
                        var styleDiv = $("<div></div>");
                        var styleLabel = $("<label></label>");
                        styleLabel.addClass("gcui-borderpicker-line-style-label").text(this.options.lineStyleTitle).appendTo(styleDiv);
                        this.element.append(styleDiv);
                        this._createLineStylePanel(this.element);
                        var colorDiv = $("<div></div>");
                        var colorLabel = $("<label></label>");
                        colorLabel.addClass("gcui-borderpicker-line-color-label").text(this.options.borderColorTitle).appendTo(colorDiv);
                        this.element.append(colorDiv);
                        this._createColorPicker(this.element);
                        this._addEvents();
                    };

                    BorderPicker.prototype._setOption = function (key, value) {
                        if (key === 'lineStyle') {
                            this.options.lineStyle = value;
                            this._updateLineStyle();
                        } else if (key === 'borderColor') {
                            this.options.borderColor = value;
                            this._updateLineColor();
                            this._updateColorPicker();
                        } else if (key === 'lineStyleTitle') {
                            this.options.lineStyleTitle = value;
                            this.element.find('.gcui-borderpicker-line-style-label').text(value);
                        } else if (key === 'borderColorTitle') {
                            this.options.lineStyleTitle = value;
                            this.element.find('.gcui-borderpicker-line-color-label').text(value);
                        } else if (key == 'colorOptions') {
                            this.options.colorOptions = value;
                            this._reDrawColorPicker();
                        } else {
                            throw "This key doesn't exist.";
                        }
                    };

                    BorderPicker.prototype._addEvents = function () {
                        var _this = this;
                        var self = this;
                        this.element.find('.line-style').on('click.borderpicker', function (evt) {
                            var srcElement = evt.target || evt.srcElement;
                            var style = $(srcElement).data('lineStyle');
                            var lineStyleEnum = Sheets.LineStyle;
                            _this.options.lineStyle = lineStyleEnum[style];
                            _this._setBorder($(srcElement));
                            _this._trigger('lineStyleChanged', evt, _this.options.lineStyle);
                        });
                    };

                    BorderPicker.prototype._createLineStylePanel = function (parent) {
                        var outDiv = $("<div class='line-style-out-div'></div>");
                        var backColorDiv = $("<div class='line-style-backcolor-div'></div>");
                        var emptyDiv = $("<div class='line-style line-style-empty' data-line-style='empty'>None</div>");
                        var dottedDiv = $("<div class='line-style line-style-dotted' data-line-style='dotted' ></div>");
                        var dashedDiv = $("<div class='line-style line-style-dashed' data-line-style='dashed' ></div>");
                        var dashDotDotDiv = $("<div class='line-style line-style-dash-dot-dot' data-line-style='dashDotDot' ></div>");
                        var dashDotDiv = $("<div class='line-style line-style-dash-dot' data-line-style='dashDot' ></div>");
                        var hairDiv = $("<div class='line-style line-style-hair' data-line-style='hair' ></div>");
                        var thinDiv = $("<div class='line-style line-style-thin' data-line-style='thin' ></div>");
                        var mediumDashDotDotDiv = $("<div class='line-style line-style-medium-dash-dot-dot' data-line-style='mediumDashDotDot' ></div>");
                        var slantedDashDotDiv = $("<div class='line-style line-style-slanted-dash-dot' data-line-style='slantedDashDot' ></div>");
                        var mediumDashDotDiv = $("<div class='line-style line-style-medium-dash-dot' data-line-style='mediumDashDot' ></div>");
                        var mediumDashedDiv = $("<div class='line-style line-style-medium-dashed' data-line-style='mediumDashed' ></div>");
                        var mediumDiv = $("<div class='line-style line-style-medium' data-line-style='medium' ></div>");
                        var thickDiv = $("<div class='line-style line-style-thick' data-line-style='thick' ></div>");
                        var doubleDiv = $("<div class='line-style line-style-double' data-line-style='double' ></div>");
                        emptyDiv.text(designer.res.borderPicker.none);
                        // this._setBorder($(thinDiv));
                        backColorDiv.append(emptyDiv).append(dottedDiv).append(dashedDiv).append(dashDotDotDiv).append(dashDotDiv).append(hairDiv).append(thinDiv).append(mediumDashDotDotDiv).append(slantedDashDotDiv).append(mediumDashDotDiv).append(mediumDashedDiv).append(mediumDiv).append(thickDiv).append(doubleDiv);
                        outDiv.append(backColorDiv);
                        parent.append(outDiv);
                    };

                    BorderPicker.prototype._createColorPicker = function (parent) {
                        var self = this;
                        var container = $('<div></div>').addClass('gcui-borderpicker-color');
                        var content = $('<span></span>').appendTo(container);
                        var preview = $('<span></span>').addClass('gcui-borderpicker-color-preview').appendTo(content);
                        var popup = $('<div></div>').addClass('gcui-borderpicker-color-popup').appendTo(container);
                        this._colorPicker = $('<div></div>').addClass('gcui-borderpicker-color-picker').colorpicker({
                            valueChanged: function (e, value) {
                                if (value.color === undefined) {
                                    self.options.borderColor = undefined;
                                    self._onChanged(e, undefined);
                                } else {
                                    self.options.borderColor = value;
                                    self._onChanged(e, value);
                                }
                            },
                            choosedColor: function (e, value) {
                                container.comboframe('close');
                            },
                            openColorDialog: function (e, value) {
                                container.comboframe('close');
                            }
                        }).appendTo(popup);
                        container.comboframe().click(function () {
                            container.comboframe('open');
                        });
                        this._colorPicker.colorpicker("option", this.options.colorOptions);
                        parent.append(container);
                    };

                    BorderPicker.prototype._reDrawColorPicker = function () {
                        this.element.find('.gcui-borderpicker-color').remove();
                        this._createColorPicker(this.element);
                    };

                    BorderPicker.prototype._onChanged = function (e, value) {
                        this._updatePreview();
                        this._updateLineColor();
                        this._colorPicker.colorpicker('option', 'selectedItem', this.options.borderColor);
                        this._trigger('colorChanged', e, value);
                    };

                    BorderPicker.prototype._updatePreview = function () {
                        if (this.options.borderColor) {
                            this.element.find('.gcui-borderpicker-color-preview').css('background-color', this.options.borderColor.color);
                        } else {
                            this.element.find('.gcui-borderpicker-color-preview').css('background-color', 'black');
                        }
                    };

                    BorderPicker.prototype._updateColorPicker = function () {
                        this._updatePreview();
                        this._colorPicker.colorpicker('option', 'selectedItem', this.options.borderColor);
                    };

                    BorderPicker.prototype._updateLineColor = function () {
                        if (this.options.borderColor) {
                            this.element.find('.line-style-backcolor-div').css('background-color', this.options.borderColor.color);
                        } else {
                            this.element.find('.line-style-backcolor-div').css('background-color', 'black');
                        }
                    };

                    BorderPicker.prototype._updateLineStyle = function () {
                        var self = this;
                        this.element.find('.line-style').each(function () {
                            var style = $(this).data('lineStyle');
                            var lineName;
                            switch (self.options.lineStyle) {
                                case 0:
                                    lineName = "empty";
                                    break;
                                case 1:
                                    lineName = "thin";
                                    break;
                                case 2:
                                    lineName = "medium";
                                    break;
                                case 3:
                                    lineName = "dashed";
                                    break;
                                case 4:
                                    lineName = "dotted";
                                    break;
                                case 5:
                                    lineName = "thick";
                                    break;
                                case 6:
                                    lineName = "double";
                                    break;
                                case 7:
                                    lineName = "hair";
                                    break;
                                case 8:
                                    lineName = "mediumDashed";
                                    break;
                                case 9:
                                    lineName = "dashDot";
                                    break;
                                case 10:
                                    lineName = "mediumDashDot";
                                    break;
                                case 11:
                                    lineName = "dashDotDot";
                                    break;
                                case 12:
                                    lineName = "mediumDashDotDot";
                                    break;
                            }
                            if (style == lineName) {
                                self._setBorder($(this));
                            }
                        });
                    };

                    BorderPicker.prototype._clearBorder = function () {
                        this.element.find(".line-style").removeClass("line-style-selected");
                    };

                    BorderPicker.prototype._setBorder = function (ele) {
                        this._clearBorder();
                        ele.addClass("line-style-selected");
                    };
                    BorderPicker.DefaultOptions = {
                        lineStyle: 1 /* thin */,
                        borderColor: {name: "Text 1", baseColor: "#000000", tint: 0, color: "rgb(0,0,0)"},
                        lineStyleTitle: designer.res.borderPicker.lineStyleTitle,
                        borderColorTitle: designer.res.borderPicker.borderColorTitle,
                        colorOptions: {showNoFill: false}
                    };
                    return BorderPicker;
                })(gcui.gcuiWidget);
                designer.BorderPicker = BorderPicker;

                BorderPicker.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, BorderPicker.DefaultOptions);
                $.gcui.registerWidget(widgetName, BorderPicker.prototype);
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
