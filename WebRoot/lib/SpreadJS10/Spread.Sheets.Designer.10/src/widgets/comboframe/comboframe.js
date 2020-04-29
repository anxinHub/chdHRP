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
            var ComboFrame = (function (_super) {
                __extends(ComboFrame, _super);
                function ComboFrame() {
                    _super.apply(this, arguments);
                }
                ComboFrame.prototype._create = function () {
                    var self = this;
                    this._id = ComboFrame._currentId++;
                    this.element.addClass('gcui-comboframe').addClass('ui-state-default');

                    var content = $(this.element.children()[0]).addClass('gcui-comboframe-content');

                    this._popup = $(this.element.children()[1]).addClass('gcui-comboframe-dropdown');
                    this._popup.gcuipopup({
                        autoHide: true,
                        ensureOutermost: true,
                        hidden: function () {
                            self.element.removeClass('ui-state-active');
                            self._button.removeClass('ui-state-active');
                        }
                    });

                    this._button = $('<button></button>').addClass('gcui-comboframe-button').appendTo(this.element).button({
                        icons: {
                            secondary: "ui-icon-triangle-1-s"
                        }
                    });

                    this.element.on('mouseenter.gcui-comboframe', function () {
                        self.element.addClass('ui-state-hover');
                    }).on('mouseleave.gcui-comboframe', function () {
                        self.element.removeClass('ui-state-hover');
                    });
                    this._button.on('click.gcui-comboframe', function () {
                        self._button.addClass('ui-state-active');
                        self.open();
                    });
                    content.on('focus.gcui-comboframe', function () {
                        self.element.addClass('ui-state-focus');
                    }).on('blur.gcui-comboframe', function () {
                        self.element.removeClass('ui-state-focus');
                    });
                };

                ComboFrame.prototype.open = function () {
                    this._popup.gcuipopup('show', {
                        of: this.element,
                        my: 'left top',
                        at: 'left bottom'
                    });
                    this.element.addClass('ui-state-active');
                };
                ComboFrame.prototype.close = function () {
                    this._popup.gcuipopup('hide');
                };
                ComboFrame.widgetName = "comboframe";
                ComboFrame.defaultOptions = {};

                ComboFrame._currentId = 0;
                return ComboFrame;
            })(gcui.gcuiWidget);
            designer.ComboFrame = ComboFrame;

            ComboFrame.prototype.options = $.extend(true, {}, gcui.gcuiWidget.prototype.options, ComboFrame.defaultOptions);
            $.gcui.registerWidget(ComboFrame.widgetName, ComboFrame.prototype);
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
