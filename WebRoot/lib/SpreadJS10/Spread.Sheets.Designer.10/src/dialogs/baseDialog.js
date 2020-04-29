var GC;
(function (GC) {
    (function (Spread) {
        (function (Sheets) {
            (function (designer) {
                var DefaultDialogViewModel = (function () {
                    function DefaultDialogViewModel() {
                        this.res = designer.res;
                    }

                    DefaultDialogViewModel.Instance = new DefaultDialogViewModel();
                    return DefaultDialogViewModel;
                })();
                var BaseDialog = (function () {
                    function BaseDialog(url, selector) {
                        this._options = this._initOptions();
                        if (this._options.autoOpen) {
                            throw "AutoOpen is not support.";
                        }
                        this.updateOptions();

                        this._element = $('<div></div>');
                        if (url !== undefined) {
                            this._element.load(url + ' ' + selector, this.onLoad.bind(this));
                        } else {
                            this._isReady = true;
                        }
                    }

                    BaseDialog.prototype._initOptions = function () {
                        //The derived dialog must overwrite this function to provide options.
                        return null;
                    };

                    BaseDialog.prototype.open = function () {
                        var args = [];
                        for (var _i = 0; _i < arguments.length; _i++) {
                            args[_i] = arguments[_i];
                        }
                        if (this._isShown)
                            return;

                        this._openArgs = args;

                        this._isShown = true;
                        if (this._isReady) {
                            this.openReally();
                        }
                    };
                    BaseDialog.prototype.close = function () {
                        if (!this._isShown)
                            return;

                        this._isShown = false;
                        if (this._isReady) {
                            this._element.dialog('close');
                        }
                    };
                    BaseDialog.prototype.destroy = function () {
                        if (this._isReady) {
                            this._element.dialog('destroy');
                        }
                    };

                    /**
                     * Calling before the dialog open.
                     *
                     * @overridable
                     */
                    BaseDialog.prototype._beforeOpen = function (args) {
                    };

                    /**
                     * Calling after the dialog closed.
                     *
                     * You can use it to do some clear actions after the dialog close.
                     *
                     * @overridable
                     */
                    BaseDialog.prototype._afterClose = function () {
                    };

                    /**
                     * Create and initialize viewModel that is used by Knockout to binding into html page.
                     *
                     * @returns An object that is the binding context for current page. By default, it return
                     * a instance that include a 'res' reference to global resource object.
                     *
                     * @overrideable
                     */
                    BaseDialog.prototype._createViewModel = function () {
                        return DefaultDialogViewModel.Instance;
                    };

                    /**
                     * Calling before the first time dialog showing.
                     *
                     * The _element variable was be initialized, and the content is loaded if you set
                     * 'url' parameter in constructor.
                     *
                     * You can override this method to initialize the _element
                     */
                    BaseDialog.prototype._init = function () {
                    };

                    BaseDialog.prototype.openReally = function () {
                        if (!this._isInited) {
                            this.onInit();
                        }
                        this._beforeOpen.call(this, this._openArgs);
                        this._element.dialog('open');
                    };
                    BaseDialog.prototype.onLoad = function () {
                        this._isReady = true;
                        if (this._isShown) {
                            this.openReally();
                        }
                    };
                    BaseDialog.prototype.onInit = function () {
                        var self = this;
                        this._isInited = true;
                        this._viewModel = this._createViewModel();
                        this._element.dialog(this._options);
                        this._init();
                        if (this._viewModel !== undefined) {
                            ko.applyBindings(self._viewModel, self._element[0]);
                        }

                        this._element.parent().on('keypress', function (e) {
                            if (e.keyCode === 13) {
                                var buttonELement = self._element.parent().find("button[dialogToBeFocused='true']");
                                if (self._options.buttons && self._options.buttons[0] && self._options.buttons[0].click && !buttonELement.is(":focus")) {
                                    self._options.buttons[0].click.call(self);
                                }
                            }
                        });

                        this._element.dialog({
                            open: function () {
                                self._element.parent().trigger("mousedown");
                                self._element.parent().find("button[dialogToBeFocused='true']").focus();
                            }
                        });
                    };
                    BaseDialog.prototype.onClose = function (event, ui) {
                        this._isShown = false;
                        if (this._closeCallback !== undefined) {
                            this._closeCallback.call(this._element, event, ui);
                        }
                        this._afterClose();
                    };
                    BaseDialog.prototype.updateOptions = function () {
                        this._options.autoOpen = false;
                        this._options.closeText = designer.res.close;
                        this._options.resizable = false;
                        this._closeCallback = this._options.close;
                        this._options.close = this.onClose.bind(this);

                        if (this._options.buttons && this._options.buttons[0]) {
                            this._options.buttons[0].dialogToBeFocused = 'true';
                        }
                    };

                    BaseDialog.prototype.getActiveSelection = function (activeSheet) {
                        // should be case by case for operation (like Excel ?!)
                        return activeSheet.getSelections()[0];
                        //return activeSheet._getActiveSelectedRange();
                    };

                    BaseDialog.prototype._unparse = function (source, expr, row, col) {
                        if (!this._calcService) {
                            this._calcService = designer.wrapper.spread.getActiveSheet().getCalcService();
                        }
                        return this._calcService.unparse(source, expr, row, col);
                    };
                    BaseDialog.prototype._parse = function (source, formula, row, col, ignoreError, forceA1) {
                        if (!this._calcService) {
                            this._calcService = designer.wrapper.spread.calcService;
                        }
                        return this._calcService.parse(source, formula, row, col, ignoreError, forceA1);
                    };
                    return BaseDialog;
                })();
                designer.BaseDialog = BaseDialog;
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
