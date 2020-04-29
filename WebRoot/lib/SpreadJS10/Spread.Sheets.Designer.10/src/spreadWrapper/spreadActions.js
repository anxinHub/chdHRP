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
            var keyword_undefined = undefined;
            var rowHigh = {};
            (function (designer) {
                (function (spreadActions) {
                    'use strict';

                    //#endregion
                    //#region DesignerActionBase
                    //All designer action is inherited from this action
                    var DesignerActionBase = (function (_super) {
                        __extends(DesignerActionBase, _super);
                        function DesignerActionBase(spread) {
                            _super.call(this);
                            this._spread = spread;
                            var sheet = this._sheet = spread && spread.getActiveSheet();
                            this._selections = sheet && sheet.getSelections();
                            this.canUndo = false;
                        };
                        DesignerActionBase.prototype.spread = function () {
                            return this._spread;
                        };
                        DesignerActionBase.prototype.activeSheet = function () {
                            return this._spread.getActiveSheet();
                        };
                        DesignerActionBase.prototype.executeImp = function (context, options, isUndo) {
                        };
                        DesignerActionBase.prototype.execute = function () {
                            var self = this;
                            if (self.canExecute()) {
                                self.executeImp.apply(self, arguments);
                            } else {
                                return false;
                            }
                        };

                        //Implement ralated function for all selected ranges in active sheet
                        //Event range is mini unit range: one Cell, one Column, one Row and one Sheet
                        DesignerActionBase.prototype.execInSelections = function (sheet, styleProperty, func, selections) {
                            var i, j;
                            var selections = selections || sheet.getSelections();
                            for (var k = 0; k < selections.length; k++) {
                                var selection = selections[k];
                                var col = selection.col, row = selection.row, rowCount = selection.rowCount, colCount = selection.colCount;
                                if ((col === -1 || row === -1) && styleProperty) {
                                    var style, r, c;
                                    if (col === -1 && row === -1) {
                                        for (r = 0; r < rowCount; r++) {
                                            if ((style = sheet.getStyle(r, -1)) && style[styleProperty] !== undefined) {
                                                func(sheet, r, -1);
                                            }
                                        }
                                        for (c = 0; c < colCount; c++) {
                                            if ((style = sheet.getStyle(-1, c)) && style[styleProperty] !== undefined) {
                                                func(sheet, -1, c);
                                            }
                                        }
                                    }
                                    if (col === -1) {
                                        col = 0;
                                    }
                                    if (row === -1) {
                                        row = 0;
                                    }
                                    for (i = 0; i < rowCount; i++) {
                                        r = row + i;
                                        for (j = 0; j < colCount; j++) {
                                            c = col + j;
                                            if ((style = sheet.getStyle(r, c)) && style[styleProperty] !== undefined) {
                                                func(sheet, r, c);
                                            }
                                        }
                                    }
                                }
                                if (col == -1 && row == -1) {
                                    func(sheet, -1, -1);
                                }
                                else if (row == -1) {
                                    for (i = 0; i < colCount; i++) {
                                        func(sheet, -1, col + i);
                                    }
                                }
                                else if (col == -1) {
                                    for (i = 0; i < rowCount; i++) {
                                        func(sheet, row + i, -1);
                                    }
                                }
                                else {
                                    for (i = 0; i < rowCount; i++) {
                                        for (j = 0; j < colCount; j++) {

                                            func(sheet, row + i, col + j);
                                        }
                                    }
                                }
                            }
                        };
                        //Implement related function for all selected ranges in active sheet
                        //Based on row, for example, set all selection ranges' row height, or hide rows etc.
                        DesignerActionBase.prototype.execInSelectionsForRow = function (sheet, func) {
                            var selections = sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                var row = selection.row == -1 ? 0 : selection.row;
                                for (var j = 0; j < selection.rowCount; j++) {
                                    func(sheet, row + j);
                                }
                            }
                        };

                        //Implement related function for all selected ranges in active sheet
                        //Based on column, for example, set all selection ranges' columns width, or hide columns etc.
                        DesignerActionBase.prototype.execInSelectionsForCol = function (sheet, func) {
                            var selections = sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                var col = selection.col == -1 ? 0 : selection.col;
                                for (var j = 0; j < selection.colCount; j++) {
                                    func(sheet, col + j);
                                }
                            }
                        };

                        //Implement related function for all selected ranges in active sheet
                        //Based on single cell, for example, set property for every cell
                        DesignerActionBase.prototype.execInSelectionsForCell = function (sheet, func) {
                            var selections = sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                var col = selection.col == -1 ? 0 : selection.col;
                                var row = selection.row == -1 ? 0 : selection.row;
                                for (var r = 0; r < selection.rowCount; r++) {
                                    for (var c = 0; c < selection.colCount; c++) {
                                        func(sheet, row + r, col + c);
                                    }
                                }
                            }
                        };

                        // get actual font of the cell in the specifed sheet' position, use default if not set yet
                        DesignerActionBase.prototype.getActualFont = function (sheet, row, column) {
                            return sheet.getActualStyle(row, column).font || designer.res.defaultFont;
                        };

                        DesignerActionBase.prototype.clearSpansInSelection = function (sheet, selection) {
                            if (sheet && selection) {
                                var ranges = new Array(), row = selection.row, col = selection.col, rowCount = selection.rowCount, colCount = selection.colCount;

                                sheet.getSpans().forEach(function (range) {
                                    if (range.intersect(row, col, rowCount, colCount)) {
                                        ranges.push(range);
                                    }
                                });
                                ranges.forEach(function (range) {
                                    sheet.removeSpan(range.row, range.col);
                                });
                            }
                        };

                        //used to support undoRedo, record properties
                        DesignerActionBase.prototype.getKey = function (row, column) {
                            return [row, column].join(',');
                        };
                        DesignerActionBase.prototype.getPosition = function (key) {
                            var items = key && key.split(',');
                            if (items && items.length === 2) {
                                return {row: +items[0], column: +items[1]};
                            }
                            return null;
                        };

                        DesignerActionBase.prototype.setFontStyle = function (style, attribute, value, row, column) {
                            var self = this, sheet = self._sheet;
                            if (!style.font) {
                                style.font = self.getActualFont(sheet, row, column);
                            }
                            var fontElement = $("<span></span>");
                            fontElement.css("font", style.font);
                            fontElement.css(attribute, value);
                            style.font = fontElement.css("font");
                            sheet.setStyle(row, column, style);
                        };
                        DesignerActionBase.prototype.undoSetStyle = function (property, options) {
                            var self = this, sheet = self._sheet, spread = self._spread, styles = options._styles;
                            if (styles) {
                                spread.suspendPaint();
                                for (var key in styles) {
                                    var pos = self.getPosition(key),
                                        oldValue = styles[key];
                                    if (pos) {
                                        var row = pos.row, column = pos.column;
                                        var style = sheet.getStyle(row, column);
                                        if (property == 'backColor') {
                                            style[property] = oldValue || undefined;    // replace null with undefined to avoid border been effected
                                        }
                                        else {
                                            style[property] = oldValue;
                                        }
                                        sheet.setStyle(row, column, style);
                                    }
                                }
                                spread.resumePaint();
                            }
                            return true;
                        };
                        DesignerActionBase.prototype.setStyle = function (attribute, property, value, options, adjustValue, additionArgs) {
                            var self = this, sheet = self._sheet, spread = self._spread;
                            options._styles = {}; // prepare object to save related original information
                            spread.suspendPaint();
                            self.execInSelections(sheet, attribute, function (sheet, row, column) {
                                var style = sheet.getStyle(row, column);
                                options._styles[self.getKey(row, column)] = style && style[property];     // save related information, use row, column as the key
                                if (!style) {
                                    style = new GC.Spread.Sheets.Style();
                                }
                                if (adjustValue) {
                                    value = adjustValue.apply(self, [sheet, row, column, style, value].concat(additionArgs || []));
                                    style[property] = value;
                                    sheet.setStyle(row, column, style);
                                }
                                if (["font-size", "font-family", "font-weight", "font-style"].indexOf(attribute) >= 0) {
                                    self.setFontStyle(style, attribute, value, row, column);
                                } else {
                                    style[property] = value;
                                    sheet.setStyle(row, column, style);
                                    if (property === 'wordWrap' && !rowHigh[row] && self._sheet.defaults.rowHeight === self._sheet.getRowHeight(row)) {
                                        sheet.autoFitRow(row);
                                    }
                                }
                            }, options._selections);
                            spread.resumePaint();
                        };
                        DesignerActionBase.prototype.updateRibbon = function () {
                            $(designer).trigger("updateRibbon");
                        };

                        DesignerActionBase.prototype.getTextDecoration = function (sheet, row, column, style, value, flag) {
                            var self = this;
                            if (!style.textDecoration) {
                                style.textDecoration = self.getActualDecoration(sheet, row, column);
                            }
                            var textDecoration = style.textDecoration;
                            if (value) {
                                textDecoration |= flag;
                            } else {
                                textDecoration ^= flag;
                            }
                            return textDecoration;
                        };

                        DesignerActionBase.prototype.getActualDecoration = function (sheet, row, column) {
                            return sheet.getActualStyle(row, column).textDecoration;
                        };
                        return DesignerActionBase;
                    })(Sheets.Commands.ActionBase);
                    spreadActions.DesignerActionBase = DesignerActionBase;

                    //#endregion
                    //#region CutCopyPaste Actions
                    //copy
                    function copy(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        var command = {};
                        command.cmd = "copy"
                        command.sheetName = sheetName;
                        command.ignoreClipboard = true;
                        var callbackNative = function () {
                            app.setClipboardData({text: command.copyData.copyText, html: command.copyData.copyHtml});
                        };
                        var callback = function () {
                            designer.util.clipboard.copyText = command.copyData.copyText;
                            designer.util.clipboard.copyHtml = command.copyData.copyHtml;
                        };
                        if (app.isNative) {
                            command.callback = callbackNative;
                        } else {
                            command.callback = callback;
                        }
                        spread.commandManager().execute(command);
                    }

                    spreadActions.copy = copy;

                    //cut
                    function cut(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        var command = {};
                        command.cmd = "cut"
                        command.sheetName = sheetName;
                        command.ignoreClipboard = true;
                        var callbackNative = function () {
                            app.setClipboardData({text: command.cutData.copyText, html: command.cutData.copyHtml});
                        };
                        var callback = function () {
                            designer.util.clipboard.copyText = command.cutData.copyText;
                            designer.util.clipboard.copyHtml = command.cutData.copyHtml;
                        };
                        if (app.isNative) {
                            command.callback = callbackNative;
                        } else {
                            command.callback = callback;
                        }
                        spread.commandManager().execute(command);
                    }

                    spreadActions.cut = cut;

                    var commands_paste = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new PasteAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function paste(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "paste", commands_paste, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.paste = paste;
                    var PasteAction = (function (_super) {
                        __extends(PasteAction, _super);
                        function PasteAction(spread) {
                            _super.call(this, spread);
                        }

                        PasteAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var oldClipOption = sheet.options.clipBoardOptions;
                            sheet.options.clipBoardOptions = self._value;
                            if (sheet.isPasteFloatingObject()) {
                                self._spread.commandManager().execute({
                                    cmd: "pasteFloatingObjects",
                                    sheetName: sheet.name()
                                });
                            } else {
                                var result, clipboardHtml;
                                if (!app.isNative) {
                                    result = designer.util.clipboard.copyText;
                                    clipboardHtml = designer.util.clipboard.copyHtml;
                                } else {
                                    var clipboardText = app.getClipboardText();
                                    clipboardHtml = app.getClipboardHTML();
                                    var result = "";
                                    if (clipboardText) {
                                        var cellDelimiter = "\"";
                                        var dl = cellDelimiter.length;
                                        var inCell = false;
                                        for (var i = 0; i < clipboardText.length; i++) {
                                            var tempChar = clipboardText[i];
                                            if (!inCell && tempChar === "\n" && clipboardText[i - 1] && clipboardText[i - 1] !== "\r") {
                                                tempChar = "\r\n";
                                            }
                                            result += tempChar;
                                            if (result.length >= dl && cellDelimiter === result.substr(result.length - dl, dl)) {
                                                inCell = !inCell;
                                            }
                                        }
                                    }

                                }
                                var callback = function () {
                                    sheet.options.clipBoardOptions = oldClipOption;
                                }
                                options.spread.commandManager().execute({
                                    cmd: "paste",
                                    sheetName: sheet.name(),
                                    pasteText: result,
                                    pasteHtml: clipboardHtml,
                                    callback: callback
                                });
                            }

                        };
                        return PasteAction;
                    })(DesignerActionBase);

                    var registeredCommands = {};

                    function clearRegisteredCommands() {
                        // reset to clear cache for new / update spread instance
                        registeredCommands = {};
                    }

                    spreadActions.clearRegisteredCommands = clearRegisteredCommands;

                    function runCommand(spread, name, command, options) {
                        var commandManager = spread.commandManager();
                        var commandName = "designer." + name;
                        if (!registeredCommands[commandName]) {
                            commandManager.register(commandName, command, null, false, false, false, false);
                            registeredCommands[commandName] = true;
                        }
                        options.cmd = commandName;
                        commandManager.execute(options);
                    }

                    //#endregion
                    //#region Font Actions

                    var commands_setFontFamily = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetFontFamilytAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is a font family string, for example, "Arial", "Verdana" etc
                    function setFontFamily(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setFontFamily", commands_setFontFamily, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setFontFamily = setFontFamily;
                    var SetFontFamilytAction = (function (_super) {
                        __extends(SetFontFamilytAction, _super);

                        //value parameter is a font family string, for example, "Arial", "Verdana" etc
                        function SetFontFamilytAction(spread) {
                            _super.call(this, spread);
                        }

                        SetFontFamilytAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("font-family", "font", options.value, options);
                            self.updateRibbon();
                        };
                        return SetFontFamilytAction;
                    })(DesignerActionBase);


                    var commands_setFontSize = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetFontSizeAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    //value parameter is a font size string with pt unit, for example, "11pt", "20pt"
                    function setFontSize(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setFontSize", commands_setFontSize, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setFontSize = setFontSize;
                    var SetFontSizeAction = (function (_super) {
                        __extends(SetFontSizeAction, _super);
                        //value parameter is a font size string with pt unit, for example, "11pt", "20pt"
                        function SetFontSizeAction(spread) {
                            _super.call(this, spread);
                        }

                        SetFontSizeAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("font-size", "font", options.value, options);
                            self.updateRibbon();
                        };
                        return SetFontSizeAction;
                    })(DesignerActionBase);

                    var commands_setFontWeight = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetFontWeightAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is a font weight string including 2 memebers, "bold" and "normal"
                    function setFontWeight(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setFontWeigth", commands_setFontWeight, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setFontWeight = setFontWeight;
                    var SetFontWeightAction = (function (_super) {
                        __extends(SetFontWeightAction, _super);
                        //value parameter is a font weight string including 2 memebers, "bold" and "normal"
                        function SetFontWeightAction(spread) {
                            _super.call(this, spread);
                        }

                        SetFontWeightAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            if (isUndo) {
                                //var ret = self.undoSetStyle("font", options);
                                //self.updateRibbon();
                                //return ret;
                                return true;
                            } else {
                                options._selections = options._selections || self._selections;
                                self.setStyle("font-weight", "font", options.value, options);
                                self.updateRibbon();
                            }
                        };
                        return SetFontWeightAction;
                    })(DesignerActionBase);


                    var commands_setUnderline = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetUnderlineAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setUnderline(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setUnderline", commands_setUnderline, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setUnderline = setUnderline;
                    var SetUnderlineAction = (function (_super) {
                        __extends(SetUnderlineAction, _super);
                        function SetUnderlineAction(spread) {
                            _super.call(this, spread);
                        }

                        SetUnderlineAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            var flag = GC.Spread.Sheets.TextDecorationType.underline;
                            var ret = self.setStyle("textDecoration", "textDecoration", options.value, options, self.getTextDecoration, [flag]);
                            self.updateRibbon();
                        };
                        return SetUnderlineAction;
                    })(DesignerActionBase);


                    var commands_setFontStyle = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetFontStyleAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is a font weight string including 2 memebers, "italic" and "normal"
                    function setFontStyle(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setFontStyle", commands_setFontStyle, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setFontStyle = setFontStyle;
                    var SetFontStyleAction = (function (_super) {
                        __extends(SetFontStyleAction, _super);
                        //value parameter is a font weight string including 2 memebers, "italic" and "normal"
                        function SetFontStyleAction(spread) {
                            _super.call(this, spread);
                        }

                        SetFontStyleAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("font-style", "font", options.value, options);
                            self.updateRibbon();
                        };
                        return SetFontStyleAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region BackColor/ForeColor Actions
                    var commands_setBackColor = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetBackColorAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is a color string, for example, "red","blue","rgb(100,100,100)" etc
                    function setBackColor(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setBackColor", commands_setBackColor, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setBackColor = setBackColor;
                    var SetBackColorAction = (function (_super) {
                        __extends(SetBackColorAction, _super);
                        //value parameter is a color string, for example, "red","blue","rgb(100,100,100)" etc
                        function SetBackColorAction(spread) {
                            _super.call(this, spread);
                        }

                        SetBackColorAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("backColor", "backColor", options.value, options);
                            self.updateRibbon();
                        };
                        return SetBackColorAction;
                    })(DesignerActionBase);


                    var commands_setForeColor = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetForeColorAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is a color string, for example, "red","blue","rgb(100,100,100)" etc
                    function setForeColor(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setForeColor", commands_setForeColor, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setForeColor = setForeColor;
                    var SetForeColorAction = (function (_super) {
                        __extends(SetForeColorAction, _super);
                        //value parameter is a color string, for example, "red","blue","rgb(100,100,100)" etc
                        function SetForeColorAction(spread) {
                            _super.call(this, spread);
                        }

                        SetForeColorAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("foreColor", "foreColor", options.value, options);
                            self.updateRibbon();
                        };
                        return SetForeColorAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Set SheetTab Color Actions
                    var commands_setSheetTabColor = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetSheetTabColorAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is a color string, for example, "red","blue","rgb(100,100,100)" etc
                    function setSheetTabColor(spread, value) {
                        var action = new SetSheetTabColorAction(spread, value);
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setSheetTabColor", commands_setSheetTabColor, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setSheetTabColor = setSheetTabColor;
                    var SetSheetTabColorAction = (function (_super) {
                        __extends(SetSheetTabColorAction, _super);
                        //value parameter is a color string, for example, "red","blue","rgb(100,100,100)" etc
                        function SetSheetTabColorAction(spread) {
                            _super.call(this, spread);
                        }

                        SetSheetTabColorAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.options.sheetTabColor = options.value;
                        };
                        return SetSheetTabColorAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region HorizontalAlign/VerticalAlign Actions
                    var commands_setHAlign = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetHAlignAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is HorizontalAlign enum, including left,center and right.
                    function setHAlign(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setHAlign", commands_setHAlign, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setHAlign = setHAlign;

                    var SetHAlignAction = (function (_super) {
                        __extends(SetHAlignAction, _super);
                        //value parameter is HorizontalAlign enum, including left,center and right.
                        function SetHAlignAction(spread) {
                            _super.call(this, spread);
                        }

                        SetHAlignAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("hAlign", "hAlign", options.value, options);
                            self.updateRibbon();
                        };
                        return SetHAlignAction;
                    })(DesignerActionBase);


                    var commands_setVAlign = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetVAlignAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is VerticalAlign enum, including left,center and right.
                    function setVAlign(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setVAlign", commands_setVAlign, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setVAlign = setVAlign;
                    var SetVAlignAction = (function (_super) {
                        __extends(SetVAlignAction, _super);
                        //value parameter is Vertical enum, including top,center and bottom.
                        function SetVAlignAction(spread) {
                            _super.call(this, spread);
                        }

                        SetVAlignAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("vAlign", "vAlign", options.value, options);
                            self.updateRibbon();
                        };
                        return SetVAlignAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Number Format Actions
                    var commands_setFormatter = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetFormatterAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //Set cell's formatter
                    function setFormatter(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setFormatter", commands_setFormatter, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setFormatter = setFormatter;
                    var SetFormatterAction = (function (_super) {
                        __extends(SetFormatterAction, _super);
                        function SetFormatterAction(spread) {
                            _super.call(this, spread);
                        }

                        SetFormatterAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("formatter", "formatter", options.value, options);
                            self.updateRibbon();
                        };
                        return SetFormatterAction;
                    })(DesignerActionBase);

                    //Increase Decimal
                    var commands_increaseDecimal = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new IncreaseDecimal(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function increaseDecimal(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "increaseDecimal", commands_increaseDecimal, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.increaseDecimal = increaseDecimal;

                    var IncreaseDecimal = (function (_super) {
                        __extends(IncreaseDecimal, _super);
                        function IncreaseDecimal(spread) {
                            _super.call(this, spread);
                        }

                        IncreaseDecimal.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("formatter", options);
                        };
                        IncreaseDecimal.prototype.setStyle = function (attribute, options) {
                            var self = this;
                            self.execInSelections(self._sheet, attribute, function (sheet, row, column) {
                                var style = sheet.getStyle(row, column);
                                if (!style) {
                                    style = new Sheets.Style();
                                }

                                var activeCell = self._sheet.getCell(self._sheet.getActiveRowIndex(), self._sheet.getActiveColumnIndex());
                                var activeCellValue = activeCell.value();
                                var activeCellFormatter = activeCell.formatter();
                                var activeCellText = activeCell.text();

                                if (activeCellValue) {
                                    var formatString = null;
                                    var zero = "0";
                                    var numberSign = "#";
                                    var decimalPoint = ".";
                                    var zeroPointZero = "0" + decimalPoint + "0";

                                    var scientificNotationCheckingFormatter = self.getScientificNotationCheckingFormattter(activeCellFormatter);
                                    if (!activeCellFormatter || ((activeCellFormatter == "General" || (scientificNotationCheckingFormatter && (scientificNotationCheckingFormatter.indexOf("E") >= 0 || scientificNotationCheckingFormatter.indexOf('e') >= 0))))) {
                                        if (!isNaN(activeCellValue)) {
                                            var result = activeCellText.split('.');
                                            if (result.length == 1) {
                                                if (result[0].indexOf('E') >= 0 || result[0].indexOf('e') >= 0)
                                                    formatString = zeroPointZero + "E+00";
                                                else
                                                    formatString = zeroPointZero;
                                            } else if (result.length == 2) {
                                                result[0] = "0";
                                                var isScience = false;
                                                var sb = "";
                                                for (var i = 0; i < result[1].length + 1; i++) {
                                                    sb = sb + '0';
                                                    if (i < result[1].length && (result[1].charAt(i) == 'e' || result[1].charAt(i) == 'E')) {
                                                        isScience = true;
                                                        break;
                                                    }
                                                }

                                                if (isScience)
                                                    sb = sb + "E+00";

                                                if (sb) {
                                                    result[1] = sb.toString();
                                                    formatString = result[0] + decimalPoint + result[1];
                                                }
                                            }
                                        }
                                    } else {
                                        formatString = activeCellFormatter;
                                        if (formatString) {
                                            var formatters = formatString.split(';');
                                            for (var i = 0; i < formatters.length && i < 2; i++) {
                                                if (formatters[i] && formatters[i].indexOf("/") < 0 && formatters[i].indexOf(":") < 0 && formatters[i].indexOf("?") < 0) {
                                                    var indexOfDecimalPoint = formatters[i].lastIndexOf(decimalPoint);
                                                    if (indexOfDecimalPoint != -1) {
                                                        formatters[i] = formatters[i].slice(0, indexOfDecimalPoint + 1) + zero + formatters[i].slice(indexOfDecimalPoint + 1);
                                                    } else {
                                                        var indexOfZero = formatters[i].lastIndexOf(zero);
                                                        var indexOfNumberSign = formatters[i].lastIndexOf(numberSign);
                                                        var insertIndex = indexOfZero > indexOfNumberSign ? indexOfZero : indexOfNumberSign;
                                                        if (insertIndex >= 0)
                                                            formatters[i] = formatters[i].slice(0, insertIndex + 1) + decimalPoint + zero + formatters[i].slice(insertIndex + 1);
                                                    }
                                                }
                                            }
                                            formatString = formatters.join(";");
                                        }
                                    }
                                    style.formatter = formatString;
                                    sheet.setStyle(row, column, style);
                                }
                            }, options._selections);
                        }

                        //This method is used to get the formatter which not include the string and color
                        //in order to not misleading with the charactor 'e' / 'E' in scientific notation.
                        IncreaseDecimal.prototype.getScientificNotationCheckingFormattter = function (formatter) {
                            if (!formatter) {
                                return formatter;
                            }

                            var self = this, i;
                            var signalQuoteSubStrings = self.getSubStrings(formatter, '\'', '\'');
                            for (i = 0; i < signalQuoteSubStrings.length; i++) {
                                formatter = formatter.replace(signalQuoteSubStrings[i], '');
                            }
                            var doubleQuoteSubStrings = self.getSubStrings(formatter, '\"', '\"');
                            for (i = 0; i < doubleQuoteSubStrings.length; i++) {
                                formatter = formatter.replace(doubleQuoteSubStrings[i], '');
                            }
                            var colorStrings = this.getSubStrings(formatter, '[', ']');
                            for (i = 0; i < colorStrings.length; i++) {
                                formatter = formatter.replace(colorStrings[i], '');
                            }
                            return formatter;
                        };

                        IncreaseDecimal.prototype.getSubStrings = function (source, beginChar, endChar) {
                            if (!source) {
                                return [];
                            }

                            var subStrings = [], tempSubString = '', inSubString = false;
                            for (var index = 0; index < source.length; index++) {
                                if (!inSubString && source[index] === beginChar) {
                                    inSubString = true;
                                    tempSubString = source[index];
                                    continue;
                                }
                                if (inSubString) {
                                    tempSubString += source[index];
                                    if (source[index] === endChar) {
                                        subStrings.push(tempSubString);
                                        tempSubString = "";
                                        inSubString = false;
                                    }
                                }
                            }
                            return subStrings;
                        };
                        return IncreaseDecimal;
                    })(DesignerActionBase);

                    //Decrease Decimal
                    var commands_decreaseDecimal = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new DecreaseDecimal(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function decreaseDecimal(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "decreaseDecimal", commands_decreaseDecimal, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.decreaseDecimal = decreaseDecimal;
                    var DecreaseDecimal = (function (_super) {
                        __extends(DecreaseDecimal, _super);
                        function DecreaseDecimal(spread) {
                            _super.call(this, spread);
                        }

                        DecreaseDecimal.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("formatter", options);
                        };
                        DecreaseDecimal.prototype.setStyle = function (attribute, options) {
                            var self = this;
                            self.execInSelections(self._sheet, "formatter", function (sheet, row, column) {
                                var style = sheet.getStyle(row, column);
                                if (!style) {
                                    style = new Sheets.Style();
                                }
                                var activeCell = self._sheet.getCell(self._sheet.getActiveRowIndex(), self._sheet.getActiveColumnIndex());
                                var activeCellValue = activeCell.value();
                                var activeCellFormatter = activeCell.formatter();
                                var activeCellText = activeCell.text();
                                var decimalPoint = ".";
                                if (activeCellValue) {
                                    var formatString = null;
                                    if (!activeCellFormatter || activeCellFormatter == "General") {
                                        if (!isNaN(activeCellValue)) {
                                            var result = activeCellText.split('.');
                                            if (result.length == 2) {
                                                result[0] = "0";
                                                var isScience = false;
                                                var sb = "";
                                                for (var i = 0; i < result[1].length - 1; i++) {
                                                    if ((i + 1 < result[1].length) && (result[1].charAt(i + 1) == 'e' || result[1].charAt(i + 1) == 'E')) {
                                                        isScience = true;
                                                        break;
                                                    }
                                                    sb = sb + ('0');
                                                }

                                                if (isScience)
                                                    sb = sb + ("E+00");

                                                if (sb != null) {
                                                    result[1] = sb.toString();

                                                    formatString = result[0] + (result[1] !== "" ? decimalPoint + result[1] : "");
                                                }
                                            }
                                        }
                                    } else {
                                        formatString = activeCellFormatter;
                                        if (formatString) {
                                            var formatters = formatString.split(';');
                                            for (var i = 0; i < formatters.length && i < 2; i++) {
                                                if (formatters[i] && formatters[i].indexOf("/") < 0 && formatters[i].indexOf(":") < 0 && formatters[i].indexOf("?") < 0) {
                                                    var indexOfDecimalPoint = formatters[i].lastIndexOf(decimalPoint);
                                                    if (indexOfDecimalPoint != -1 && indexOfDecimalPoint + 1 < formatters[i].length) {
                                                        formatters[i] = formatters[i].slice(0, indexOfDecimalPoint + 1) + formatters[i].slice(indexOfDecimalPoint + 2);
                                                        var tempString = indexOfDecimalPoint + 1 < formatters[i].length ? formatters[i].substr(indexOfDecimalPoint + 1, 1) : "";
                                                        if (tempString == "" || tempString != "0")
                                                            formatters[i] = formatters[i].slice(0, indexOfDecimalPoint) + formatters[i].slice(indexOfDecimalPoint + 1);
                                                    } else {
                                                        //do nothing.
                                                    }
                                                }
                                            }
                                            formatString = formatters.join(";");
                                        }
                                    }
                                    style.formatter = formatString;
                                    sheet.setStyle(row, column, style);
                                }
                            }, options._selections);
                        }
                        return DecreaseDecimal;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Border Actions
                    //Set selection ranges' border. Value is a object with {lineborder:LineBorder, options:any}
                    var commands_setBorder = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetBorderAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //options include:all,top,left,bottom,top,outline,inside, innerHorizontal, and innerVertical, for example, {left:true, right:true}
                    function setBorder(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setBorder", commands_setBorder, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setBorder = setBorder;
                    var SetBorderAction = (function (_super) {
                        __extends(SetBorderAction, _super);
                        function SetBorderAction(spread) {
                            _super.call(this, spread);
                        }

                        SetBorderAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                self.setRangeBorder(selection, options.value);
                            }
                        };

                        SetBorderAction.prototype.setRangeBorder = function (range, value) {
                            var sheet = this._sheet;
                            var startRow = range.row, startCol = range.col, rowCount = range.rowCount, colCount = range.colCount;
                            sheet.getRange(startRow, startCol, rowCount, colCount).setBorder(value.lineborder, value.options);
                        }
                        return SetBorderAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Indent Actions
                    var commands_setIndent = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetIndentAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is a number, if number is greater than 0, that means to increase indent, otherwise, to decrease indent.
                    function setIndent(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setIndent", commands_setIndent, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setIndent = setIndent;
                    var SetIndentAction = (function (_super) {
                        __extends(SetIndentAction, _super);
                        //value parameter is a number, if number is greater than 0, that means to increase indent, otherwise, to decrease indent.
                        //In most of case, please pass 1 or -1 to value parameter.
                        function SetIndentAction(spread) {
                            _super.call(this, spread);
                        }

                        SetIndentAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setIndent(options);
                            self.updateRibbon();
                        };
                        SetIndentAction.prototype.setIndent = function (options) {
                            var self = this;
                            self.execInSelections(self._sheet, "textIndent", function (sheet, row, column) {
                                var style = sheet.getStyle(row, column);
                                if (!style) {
                                    style = new Sheets.Style();
                                }
                                var oldIndent = style.textIndent;
                                if (isNaN(oldIndent))
                                    oldIndent = 0;
                                var newIndent = oldIndent + options.value;
                                if (newIndent < 0) {
                                    newIndent = 0;
                                }
                                style.textIndent = newIndent;
                                sheet.setStyle(row, column, style);
                            }, options._selections);
                        }
                        return SetIndentAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region WordWrap Actions
                    var commands_setWordWrap = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetWordWrapAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };
                    //value parameter is bool type. True means wraping text
                    function setWordWrap(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setWordWrap", commands_setWordWrap, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setWordWrap = setWordWrap;

                    var SetWordWrapAction = (function (_super) {
                        __extends(SetWordWrapAction, _super);
                        //value parameter is bool type. True means wraping text
                        function SetWordWrapAction(spread) {
                            _super.call(this, spread);
                        }

                        SetWordWrapAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("wordWrap", "wordWrap", options.value, options);
                            self.updateRibbon();
                        };
                        return SetWordWrapAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Merge Actions
                    var commands_mergeCenter = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new MergeCenterAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function mergeCenter(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "mergeCenter", commands_mergeCenter, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.mergeCenter = mergeCenter;

                    var MergeCenterAction = (function (_super) {
                        __extends(MergeCenterAction, _super);
                        function MergeCenterAction(spread) {
                            _super.call(this, spread);
                        }

                        MergeCenterAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            var selections = options._selections;
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                var row = selection.row == -1 ? 0 : selection.row;
                                var col = selection.col == -1 ? 0 : selection.col;
                                self._sheet.addSpan(row, col, selection.rowCount, selection.colCount);
                                var style = self._sheet.getStyle(row, col);
                                if (!style) {
                                    style = new Sheets.Style();
                                }
                                style.hAlign = 1 /* center */;
                                self._sheet.setStyle(row, col, style);
                            }
                        };
                        return MergeCenterAction;
                    })(DesignerActionBase);


                    var commands_mergeAcross = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new MergeAcrossAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function mergeAcross(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "mergeAcross", commands_mergeAcross, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.mergeAcross = mergeAcross;

                    var MergeAcrossAction = (function (_super) {
                        __extends(MergeAcrossAction, _super);
                        function MergeAcrossAction(spread) {
                            _super.call(this, spread);
                        }

                        MergeAcrossAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                var row = selection.row == -1 ? 0 : selection.row;
                                var col = selection.col == -1 ? 0 : selection.col;

                                for (var m = 0; m < selection.rowCount; m++) {
                                    for (var n = 0; n < selection.colCount; n++) {
                                        self._sheet.removeSpan(row + m, col + n);
                                    }
                                }

                                for (var j = 0; j < selection.rowCount; j++) {
                                    self._sheet.addSpan(row + j, col, 1, selection.colCount);
                                }
                            }
                        };
                        return MergeAcrossAction;
                    })(DesignerActionBase);


                    var commands_mergeCells = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new MergeCellsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function mergeCells(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "mergeCells", commands_mergeCells, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.mergeCells = mergeCells;

                    var MergeCellsAction = (function (_super) {
                        __extends(MergeCellsAction, _super);
                        function MergeCellsAction(spread) {
                            _super.call(this, spread);
                        }

                        MergeCellsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                var row = selection.row == -1 ? 0 : selection.row;
                                var col = selection.col == -1 ? 0 : selection.col;
                                self._sheet.addSpan(row, col, selection.rowCount, selection.colCount);
                            }
                        };
                        return MergeCellsAction;
                    })(DesignerActionBase);


                    var commands_unmergeCells = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new UnMergeCellsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function unmergeCells(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "unmergeCells", commands_unmergeCells, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.unmergeCells = unmergeCells;

                    var UnMergeCellsAction = (function (_super) {
                        __extends(UnMergeCellsAction, _super);
                        function UnMergeCellsAction(spread) {
                            _super.call(this, spread);
                        }

                        UnMergeCellsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            var selections = options._selections;
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                var row = selection.row == -1 ? 0 : selection.row;
                                var col = selection.col == -1 ? 0 : selection.col;
                                for (var m = 0; m < selection.rowCount; m++) {
                                    for (var n = 0; n < selection.colCount; n++) {
                                        self._sheet.removeSpan(row + m, col + n);
                                    }
                                }
                            }
                        };
                        return UnMergeCellsAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Insert/Delete Actions
                    //#region Insert/Delete ActionBase
                    var InsertAndDeleteBase = (function (_super) {
                        __extends(InsertAndDeleteBase, _super);
                        function InsertAndDeleteBase(spread) {
                            _super.call(this, spread);
                        }

                        InsertAndDeleteBase.prototype.canDoInsertOrDeleteRow = function () {
                            return true;
                        };

                        InsertAndDeleteBase.prototype.canDoInsertOrDeleteColumn = function () {
                            return true;
                        };

                        InsertAndDeleteBase.prototype.getSortedRowSelections = function () {
                            var sortedRanges = this.activeSheet().getSelections();
                            for (var i = 0; i < sortedRanges.length - 1; i++) {
                                for (var j = i + 1; j < sortedRanges.length; j++) {
                                    if (sortedRanges[i].row < sortedRanges[j].row) {
                                        var temp = sortedRanges[i];
                                        sortedRanges[i] = sortedRanges[j];
                                        sortedRanges[j] = temp;
                                    }
                                }
                            }
                            return sortedRanges;
                        };

                        InsertAndDeleteBase.prototype.getSortedColumnSelections = function () {
                            var sortedRanges = this.activeSheet().getSelections();
                            for (var i = 0; i < sortedRanges.length - 1; i++) {
                                for (var j = i + 1; j < sortedRanges.length; j++) {
                                    if (sortedRanges[i].col < sortedRanges[j].col) {
                                        var temp = sortedRanges[i];
                                        sortedRanges[i] = sortedRanges[j];
                                        sortedRanges[j] = temp;
                                    }
                                }
                            }
                            return sortedRanges;
                        };
                        return InsertAndDeleteBase;
                    })(DesignerActionBase);
                    spreadActions.InsertAndDeleteBase = InsertAndDeleteBase;

                    //#endregion
                    //#region Insert/Delete Cells Actions
                    //Insert right
                    var commands_insertRightCells = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new InsertRightCellsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function insertRightCells(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "insertRightCells", commands_insertRightCells, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.insertRightCells = insertRightCells;

                    var InsertRightCellsAction = (function (_super) {
                        __extends(InsertRightCellsAction, _super);
                        function InsertRightCellsAction(spread) {
                            _super.call(this, spread);
                        }

                        InsertRightCellsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            if (getSelectionType(self.spread()) == 3 /* OnlyCells */) {
                                var sortedRanges = self.getSortedColumnSelections();
                                var colCount = self._sheet.getColumnCount();
                                if (!self.canInsertRight(sortedRanges)) {
                                    designer.MessageBox.show(designer.res.changePartOfArrayWarning, designer.res.title, 2 /* warning */, 0 /* ok */, function () {
                                        $(".fill-content").focus();
                                    });
                                    return false;
                                }
                                for (var i = 0; i < sortedRanges.length; i++) {
                                    var r = sortedRanges[i];
                                    var option = 256 /* BindingPath */ | 2 /* Formula */ | 8 /* RangeGroup */ | 32 /* Span */ | 16 /* Sparkline */ | 64 /* Style */ | 128 /* Tag */ | 1 /* Value */;
                                    self._sheet.addColumns(colCount, r.colCount);
                                    self._sheet.moveTo(r.row, r.col, r.row, r.col + r.colCount, r.rowCount, colCount - r.col, option);
                                }
                            }
                        };

                        InsertRightCellsAction.prototype.canInsertRight = function (sortedRanges) {
                            var sheet = this._sheet;
                            var result = true;
                            for (var i = 0; i < sortedRanges.length; i++) {
                                var range = sortedRanges[i];
                                var arrayFormulaRanges = [];
                                var row = range.row;
                                var col = range.col;
                                var endRow = range.row + range.rowCount;
                                var endCol = range.col + range.colCount;
                                //find if the selection has a arrayFormula, if it has, push it's range to an Array
                                for (var r = row; r < endRow; r++) {
                                    for (var c = col; c < endCol; c++) {
                                        var index;
                                        for (index = 0; index < arrayFormulaRanges.length; index++) {
                                            if (arrayFormulaRanges[index].contains(r, c)) {
                                                break;
                                            }
                                        }
                                        if (index === arrayFormulaRanges.length) {
                                            if (sheet.getFormulaInformation(r, c).isArrayFormula) {
                                                arrayFormulaRanges.push(sheet.getFormulaInformation(r, c).baseRange)
                                            }
                                        }
                                    }
                                }
                                if (arrayFormulaRanges.length === 0) {
                                    return result;
                                }
                                //find the border of all the arrayFormula Ranges.
                                var left = arrayFormulaRanges[0].col;
                                var top = arrayFormulaRanges[0].row;
                                var bottom = arrayFormulaRanges[0].row + arrayFormulaRanges[0].rowCount;
                                for (var j = 1; j < arrayFormulaRanges.length; j++) {
                                    if (arrayFormulaRanges[j].col < left) {
                                        left = arrayFormulaRanges[j].col;
                                    }
                                    if (arrayFormulaRanges[j].row < top) {
                                        top = arrayFormulaRanges[j].row;
                                    }
                                    if (arrayFormulaRanges[j].row + arrayFormulaRanges[j].rowCount < bottom) {
                                        bottom = arrayFormulaRanges[j].row + arrayFormulaRanges[j].rowCount;
                                    }
                                }
                                //if meet these conditions, then can't insert and shift cells right
                                if (left < range.col || top < range.row || bottom > range.row + range.rowCount) {
                                    result = false;
                                    break;
                                }
                            }
                            return result;
                        };
                        return InsertRightCellsAction;
                    })(InsertAndDeleteBase);

                    //Insert Down
                    var commands_insertDownCells = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new InsertDownCellsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function insertDownCells(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "insertDownCells", commands_insertDownCells, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.insertDownCells = insertDownCells;

                    var InsertDownCellsAction = (function (_super) {
                        __extends(InsertDownCellsAction, _super);
                        function InsertDownCellsAction(spread) {
                            _super.call(this, spread);
                        }

                        InsertDownCellsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            if (getSelectionType(self.spread()) == 3 /* OnlyCells */) {
                                var sortedRanges = self.getSortedRowSelections();
                                var rowCount = self._sheet.getRowCount();
                                if (!self.canInsertDown(sortedRanges)) {
                                    designer.MessageBox.show(designer.res.changePartOfArrayWarning, designer.res.title, 2 /* warning */, 0 /* ok */, function () {
                                        $(".fill-content").focus();
                                    });
                                    return false;
                                }
                                for (var i = 0; i < sortedRanges.length; i++) {
                                    var r = sortedRanges[i];
                                    var option = 256 /* BindingPath */ | 2 /* Formula */ | 8 /* RangeGroup */ | 32 /* Span */ | 16 /* Sparkline */ | 64 /* Style */ | 128 /* Tag */ | 1 /* Value */;
                                    self._sheet.addRows(rowCount, r.rowCount);
                                    self._sheet.moveTo(r.row, r.col, r.row + r.rowCount, r.col, rowCount - r.row, r.colCount, option);
                                }
                            }
                        };

                        InsertDownCellsAction.prototype.canInsertDown = function (sortedRanges) {
                            var sheet = this._sheet;
                            var result = true;
                            for (var i = 0; i < sortedRanges.length; i++) {
                                var range = sortedRanges[i];
                                var arrayFormulaRanges = [];
                                var row = range.row;
                                var col = range.col;
                                var endRow = range.row + range.rowCount;
                                var endCol = range.col + range.colCount;
                                //find if the selection has a arrayFormula, if it has, push it's range to an Array
                                for (var r = row; r < endRow; r++) {
                                    for (var c = col; c < endCol; c++) {
                                        var index;
                                        for (index = 0; index < arrayFormulaRanges.length; index++) {
                                            if (arrayFormulaRanges[index].contains(r, c)) {
                                                break;
                                            }
                                        }
                                        if (index === arrayFormulaRanges.length) {
                                            if (sheet.getFormulaInformation(r, c).isArrayFormula) {
                                                arrayFormulaRanges.push(sheet.getFormulaInformation(r, c).baseRange)
                                            }
                                        }
                                    }
                                }
                                if (arrayFormulaRanges.length === 0) {
                                    return result;
                                }
                                //find the border of all the arrayFormula Ranges.
                                var left = arrayFormulaRanges[0].col;
                                var top = arrayFormulaRanges[0].row;
                                var right = arrayFormulaRanges[0].col + arrayFormulaRanges[0].colCount;
                                for (var j = 1; j < arrayFormulaRanges.length; j++) {
                                    if (arrayFormulaRanges[j].col < left) {
                                        left = arrayFormulaRanges[j].col;
                                    }
                                    if (arrayFormulaRanges[j].row < top) {
                                        top = arrayFormulaRanges[j].row;
                                    }
                                    if (arrayFormulaRanges[j].col + arrayFormulaRanges[j].colCount < right) {
                                        right = arrayFormulaRanges[j].row + arrayFormulaRanges[j].rowCount;
                                    }
                                }
                                //if meet these conditions, then can't insert and shift cells down
                                if (left < range.col || top < range.row || right > range.col + range.colCount) {
                                    result = false;
                                    break;
                                }
                            }
                            return result;
                        };
                        return InsertDownCellsAction;
                    })(InsertAndDeleteBase);

                    //Delete left
                    var commands_deleteLeftCells = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new DeleteLeftCellsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function deleteLeftCells(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "deleteLeftCells", commands_deleteLeftCells, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.deleteLeftCells = deleteLeftCells;

                    var DeleteLeftCellsAction = (function (_super) {
                        __extends(DeleteLeftCellsAction, _super);
                        function DeleteLeftCellsAction(spread) {
                            _super.call(this, spread);
                        }

                        DeleteLeftCellsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var sortedRanges = self.getSortedColumnSelections();
                            for (var i = 0; i < sortedRanges.length; i++) {
                                var r = sortedRanges[i];
                                var option = 256 /* BindingPath */ | 2 /* Formula */ | 8 /* RangeGroup */ | 32 /* Span */ | 16 /* Sparkline */ | 64 /* Style */ | 128 /* Tag */ | 1 /* Value */;
                                self._sheet.moveTo(r.row, r.col + r.colCount, r.row, r.col, r.rowCount, self._sheet.getColumnCount() - (r.col + r.colCount), option);
                            }
                        };
                        return DeleteLeftCellsAction;
                    })(InsertAndDeleteBase);

                    //Delete UP
                    var commands_deleteUpCells = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new DeleteUpCellsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function deleteUpCells(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "deleteUpCells", commands_deleteUpCells, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.deleteUpCells = deleteUpCells;
                    var DeleteUpCellsAction = (function (_super) {
                        __extends(DeleteUpCellsAction, _super);
                        function DeleteUpCellsAction(spread) {
                            _super.call(this, spread);
                        }

                        DeleteUpCellsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var sortedRanges = self.getSortedRowSelections();
                            for (var i = 0; i < sortedRanges.length; i++) {
                                var r = sortedRanges[i];
                                var option = 256 /* BindingPath */ | 2 /* Formula */ | 8 /* RangeGroup */ | 32 /* Span */ | 16 /* Sparkline */ | 64 /* Style */ | 128 /* Tag */ | 1 /* Value */;
                                self._sheet.moveTo(r.row + r.rowCount, r.col, r.row, r.col, self._sheet.getRowCount() - (r.row + r.rowCount), r.colCount, option);
                            }
                        };
                        return DeleteUpCellsAction;
                    })(InsertAndDeleteBase);

                    //#endregion
                    //#region Insert/Delete Row Actions
                    var commands_insertRows = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new InsertRowsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function insertRows(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "insertRows", commands_insertRows, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.insertRows = insertRows;

                    var InsertRowsAction = (function (_super) {
                        __extends(InsertRowsAction, _super);
                        function InsertRowsAction(spread) {
                            _super.call(this, spread);
                        }

                        InsertRowsAction.prototype.canExecute = function () {
                            return this.canDoInsertOrDeleteRow();
                        };
                        InsertRowsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var sortedRanges = self.getSortedRowSelections();
                            for (var i = 0; i < sortedRanges.length; i++) {
                                self._sheet.addRows(sortedRanges[i].row, sortedRanges[i].rowCount);
                                designer.util.setSpanStyleWhenInsertRowsColumns(self._sheet, sortedRanges[i].row, -1, sortedRanges[i].rowCount);
                            }
                        };

                        return InsertRowsAction;
                    })(InsertAndDeleteBase);


                    var commands_deleteRows = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new DeleteRowsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function deleteRows(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "deleteRows", commands_deleteRows, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.deleteRows = deleteRows;

                    var DeleteRowsAction = (function (_super) {
                        __extends(DeleteRowsAction, _super);
                        function DeleteRowsAction(spread) {
                            _super.call(this, spread);
                        }

                        DeleteRowsAction.prototype.canExecute = function () {
                            return this.canDoInsertOrDeleteRow() && !this._isTableHeaderInRange();
                        };

                        DeleteRowsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var sortedRanges = self.getSortedRowSelections();
                            for (var i = 0; i < sortedRanges.length; i++) {
                                self._sheet.deleteRows(sortedRanges[i].row, sortedRanges[i].rowCount);
                            }
                        };

                        DeleteRowsAction.prototype._isTableHeaderInRange = function () {
                            var self = this;
                            var sortedRanges = self.getSortedRowSelections();
                            var tables = self._sheet.tables.all();
                            for (var tableItem in tables) {
                                for (var rangeItem in sortedRanges) {
                                    if (tables[tableItem].headerIndex() === sortedRanges[rangeItem].row) {
                                        return true;
                                    }
                                }
                            }
                            return false;
                        };
                        return DeleteRowsAction;
                    })(InsertAndDeleteBase);

                    //#endregion
                    //#region Insert/Delete Column Actions
                    var commands_insertColumns = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new InsertColumnsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function insertColumns(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "insertColumns", commands_insertColumns, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.insertColumns = insertColumns;

                    var InsertColumnsAction = (function (_super) {
                        __extends(InsertColumnsAction, _super);
                        function InsertColumnsAction(spread) {
                            _super.call(this, spread);
                        }

                        InsertColumnsAction.prototype.canExecute = function () {
                            return this.canDoInsertOrDeleteColumn();
                        };
                        InsertColumnsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var sortedRanges = self.getSortedColumnSelections();
                            for (var i = 0; i < sortedRanges.length; i++) {
                                self._sheet.addColumns(sortedRanges[i].col, sortedRanges[i].colCount);
                                designer.util.setSpanStyleWhenInsertRowsColumns(self._sheet, -1, sortedRanges[i].col, sortedRanges[i].colCount);
                            }
                        };

                        return InsertColumnsAction;
                    })(InsertAndDeleteBase);


                    var commands_deleteColumns = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new DeleteColumnsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function deleteColumns(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "deleteColumns", commands_deleteColumns, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.deleteColumns = deleteColumns;

                    var DeleteColumnsAction = (function (_super) {
                        __extends(DeleteColumnsAction, _super);
                        function DeleteColumnsAction(spread) {
                            _super.call(this, spread);
                        }

                        DeleteColumnsAction.prototype.canExecute = function () {
                            return this.canDoInsertOrDeleteColumn();
                        };

                        DeleteColumnsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var sortedRanges = self.getSortedColumnSelections();
                            for (var i = 0; i < sortedRanges.length; i++) {
                                self._sheet.deleteColumns(sortedRanges[i].col, sortedRanges[i].colCount);
                            }
                        };
                        return DeleteColumnsAction;
                    })(InsertAndDeleteBase);

                    //#endregion
                    //#region Insert/Delete Sheet Actions
                    var commands_insertSheet = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new InsertSheetAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function insertSheet(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "insertSheet", commands_insertSheet, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.insertSheet = insertSheet;

                    var InsertSheetAction = (function (_super) {
                        __extends(InsertSheetAction, _super);
                        function InsertSheetAction(spread) {
                            _super.call(this, spread);
                        }

                        InsertSheetAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.spread().addSheet(self.spread().getActiveSheetIndex());
                            designer.actions.isFileModified = true;
                        };
                        return InsertSheetAction;
                    })(DesignerActionBase);


                    var commands_deleteSheet = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new DeleteSheetAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function deleteSheet(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "deleteSheet", commands_deleteSheet, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.deleteSheet = deleteSheet;

                    var DeleteSheetAction = (function (_super) {
                        __extends(DeleteSheetAction, _super);
                        function DeleteSheetAction(spread) {
                            _super.call(this, spread);
                        }

                        DeleteSheetAction.prototype.canExecute = function () {
                            return this.spread().getSheetCount() > 1;
                        };

                        DeleteSheetAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            if (self.spread().getSheetCount() > 1) {
                                self.spread().removeSheet(self.spread().getActiveSheetIndex());
                                designer.actions.isFileModified = true;
                            } else {
                                designer.MessageBox.show(designer.res.contextMenu.comments, designer.res.title, 2 /* warning */, 0 /* ok */);
                            }
                        };
                        return DeleteSheetAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Insert picture
                    var commands_insertPicture = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new InsertPictureAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function insertPicture(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "insertPicture", commands_insertPicture, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.insertPicture = insertPicture;
                    var InsertPictureAction = (function (_super) {
                        __extends(InsertPictureAction, _super);
                        function InsertPictureAction(spread) {
                            _super.call(this, spread);
                        }

                        InsertPictureAction.prototype._getPictureName = function () {
                            var self = this;
                            var pictures = self._sheet.pictures.all();
                            var len = pictures.length;
                            var name = "";
                            if (len === 0) {
                                name = "picture0";
                            }
                            var names = [];
                            for (var j = 0; j < len; j++) {
                                names.push(pictures[j].name());
                            }
                            for (var i = 0; i < len; i++) {
                                name = "picture" + i;
                                if ($.inArray(name, names) < 0) {
                                    return name;
                                }
                            }
                            name = "picture" + len;
                            return name;
                        };

                        InsertPictureAction.prototype._open = function () {
                            var self = this;
                            var dialogInfo = {
                                title: designer.res.insertPictureDialogTitle,
                                action: "insertPicture",
                                filters: "image/*",
                                nameFilters: [
                                    designer.res.pictureFormatFilter.png,
                                    designer.res.pictureFormatFilter.jpeg,
                                    designer.res.pictureFormatFilter.bmp,
                                    designer.res.pictureFormatFilter.allFiles
                                ]
                            };
                            var callback = function (result) {
                                if (result.status === 'cancelled') {
                                    return;
                                }
                                var name = self._getPictureName(),
                                    sheet = self._sheet,
                                    defaults = sheet.defaults, rowHeight = defaults.rowHeight, colWidth = defaults.colWidth;
                                try {
                                    var path = result.data;
                                    var startRow = sheet.getActiveRowIndex();
                                    var startCol = sheet.getActiveColumnIndex();
                                    self._sheet.pictures.add(name, path, startCol * colWidth, startRow * rowHeight);
                                    designer.actions.isFileModified = true;
                                } catch (e) {
                                }
                            };
                            app.showOpenDialog(dialogInfo, callback);
                        };

                        InsertPictureAction.prototype.executeImp = function (context, options, isUndo) {
                            this._open();
                        };
                        return InsertPictureAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Hide/UnHide Sheet Actions
                    var hideSheetsStack = [];


                    var commands_hideSheet = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new HideSheetAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function hideSheet(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "hideSheet", commands_hideSheet, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.hideSheet = hideSheet;
                    var HideSheetAction = (function (_super) {
                        __extends(HideSheetAction, _super);
                        function HideSheetAction(spread) {
                            _super.call(this, spread);
                        }

                        HideSheetAction.prototype.canExecute = function () {
                            var self = this;
                            var sheets = self.spread().sheets;
                            var visibleSheetCount = 0;
                            for (var i = 0; i < sheets.length; i++) {
                                if (sheets[i].visible()) {
                                    visibleSheetCount++;
                                }
                            }
                            var result = visibleSheetCount >= 2 ? true : false;
                            if (!result) {
                                designer.MessageBox.show(designer.res.contextMenu.comments, designer.res.title, 2 /* warning */, 0 /* ok */);
                            } else {
                                return result;
                            }
                        };

                        HideSheetAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            if (self._sheet) {
                                hideSheetsStack.push(self.spread().getActiveSheet());
                                self._sheet.visible(false);
                                designer.wrapper.setFocusToSpread();
                                designer.actions.isFileModified = true;
                            }
                        };
                        return HideSheetAction;
                    })(DesignerActionBase);


                    var commands_unhideSheet = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new UnhideSheetAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function unhideSheet(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "unhideSheet", commands_unhideSheet, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.unhideSheet = unhideSheet;
                    var UnhideSheetAction = (function (_super) {
                        __extends(UnhideSheetAction, _super);
                        function UnhideSheetAction(spread) {
                            _super.call(this, spread);
                            this._sheets = this.spread().sheets;
                        }

                        UnhideSheetAction.prototype.canExecute = function () {
                            var self = this;
                            var len = self._sheets.length;
                            for (var i = 0; i < len; i++) {
                                if (!self._sheets[i].visible()) {
                                    return true;
                                }
                            }
                            return false;
                        };

                        UnhideSheetAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var spread = self.spread();
                            if (hideSheetsStack.length === 0) {
                                var sheets = spread.sheets;
                                for (var i = 0; i < sheets.length; i++) {
                                    if (!sheets[i].visible()) {
                                        hideSheetsStack.push(sheets[i]);
                                    }
                                }
                            }
                            if (hideSheetsStack.length > 0) {
                                var tempSheet = hideSheetsStack.pop();
                                tempSheet.visible(true);
                                spread.setActiveSheet(tempSheet.name());
                                designer.actions.isFileModified = true;
                            }
                            designer.wrapper.setFocusToSpread();
                        };
                        return UnhideSheetAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#endregion
                    //#region Row Operation Actions
                    //Set Row Height
                    var commands_setRowsHeight = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetRowsHeightAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setRowsHeight(spread, height) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setRowsHeight", commands_setRowsHeight, {
                            sheetName: sheetName,
                            height: height,
                            spread: spread
                        });
                    }

                    spreadActions.setRowsHeight = setRowsHeight;

                    var SetRowsHeightAction = (function (_super) {
                        __extends(SetRowsHeightAction, _super);
                        function SetRowsHeightAction(spread) {
                            _super.call(this, spread);
                        }

                        SetRowsHeightAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForRow(self._sheet, function (sheet, row) {
                                self._sheet.setRowHeight(row, options.height);
                                rowHigh[row] = true;
                            });
                        };
                        return SetRowsHeightAction;
                    })(DesignerActionBase);

                    //AutoFit Row
                    var commands_autofitRows = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new AutoFitRowsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function autofitRows(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "autofitRows", commands_autofitRows, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.autofitRows = autofitRows;

                    var AutoFitRowsAction = (function (_super) {
                        __extends(AutoFitRowsAction, _super);
                        function AutoFitRowsAction(spread) {
                            _super.call(this, spread);
                        }

                        AutoFitRowsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForRow(self._sheet, function (sheet, row) {
                                self._sheet.autoFitRow(row);
                                rowHigh[row] = false;
                            });
                        };
                        return AutoFitRowsAction;
                    })(DesignerActionBase);

                    //Hide Row
                    var commands_hideRows = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new HideRowsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function hideRows(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "hideRows", commands_hideRows, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.hideRows = hideRows;

                    var HideRowsAction = (function (_super) {
                        __extends(HideRowsAction, _super);

                        function HideRowsAction(spread) {
                            _super.call(this, spread);
                        }

                        HideRowsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForRow(self._sheet, function (sheet, row) {
                                self._sheet.setRowVisible(row, false);
                            });
                        };
                        return HideRowsAction;
                    })(DesignerActionBase);

                    //UnHide Row
                    var commands_unHideRows = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new UnHideRowsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function unHideRows(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "unHideRows", commands_unHideRows, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.unHideRows = unHideRows;

                    var UnHideRowsAction = (function (_super) {
                        __extends(UnHideRowsAction, _super);

                        function UnHideRowsAction(spread) {
                            _super.call(this, spread);
                        }

                        UnHideRowsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForRow(self._sheet, function (sheet, row) {
                                self._sheet.setRowVisible(row, true);
                            });
                        };
                        return UnHideRowsAction;
                    })(DesignerActionBase);

                    //Default Row Height
                    var commands_defaultRowHeight = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetDefaultRowHeightAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function defaultRowHeight(spread, height) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "defaultRowHeight", commands_defaultRowHeight, {
                            sheetName: sheetName,
                            height: height,
                            spread: spread
                        });
                    }

                    spreadActions.defaultRowHeight = defaultRowHeight;

                    var SetDefaultRowHeightAction = (function (_super) {
                        __extends(SetDefaultRowHeightAction, _super);
                        function SetDefaultRowHeightAction(spread) {
                            _super.call(this, spread);
                        }

                        SetDefaultRowHeightAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.defaults.rowHeight = options.height;
                        };
                        return SetDefaultRowHeightAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Column Operation Actions
                    //Set Column Width
                    var commands_setColumnsWidth = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetColumnsWidthtAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setColumnsWidth(spread, width) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setColumnsWidth", commands_setColumnsWidth, {
                            sheetName: sheetName,
                            width: width,
                            spread: spread
                        });
                    }

                    spreadActions.setColumnsWidth = setColumnsWidth;

                    var SetColumnsWidthtAction = (function (_super) {
                        __extends(SetColumnsWidthtAction, _super);
                        function SetColumnsWidthtAction(spread) {
                            _super.call(this, spread);
                        }

                        SetColumnsWidthtAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForCol(self._sheet, function (sheet, column) {
                                self._sheet.setColumnWidth(column, options.width);
                            });
                        };
                        return SetColumnsWidthtAction;
                    })(DesignerActionBase);

                    //AutoFit Column
                    var commands_autofitColumns = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new AutoFitColumnsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function autofitColumns(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "autofitColumns", commands_autofitColumns, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.autofitColumns = autofitColumns;

                    var AutoFitColumnsAction = (function (_super) {
                        __extends(AutoFitColumnsAction, _super);
                        function AutoFitColumnsAction(spread) {
                            _super.call(this, spread);
                        }

                        AutoFitColumnsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForCol(self._sheet, function (sheet, column) {
                                self._sheet.autoFitColumn(column);
                            });
                        };
                        return AutoFitColumnsAction;
                    })(DesignerActionBase);

                    //Hide Column
                    var commands_hideColumns = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new HideColumnsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function hideColumns(spread) {
                        var action = new HideColumnsAction(spread);
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "hideColumns", commands_hideColumns, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.hideColumns = hideColumns;

                    var HideColumnsAction = (function (_super) {
                        __extends(HideColumnsAction, _super);
                        function HideColumnsAction(spread) {
                            _super.call(this, spread);
                        }

                        HideColumnsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForCol(self._sheet, function (sheet, column) {
                                self._sheet.setColumnVisible(column, false);
                            });
                        };
                        return HideColumnsAction;
                    })(DesignerActionBase);

                    //UnHide Column
                    var commands_unHideColumns = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new UnHideColumnsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function unHideColumns(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "unHideColumns", commands_unHideColumns, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.unHideColumns = unHideColumns;

                    var UnHideColumnsAction = (function (_super) {
                        __extends(UnHideColumnsAction, _super);
                        function UnHideColumnsAction(spread) {
                            _super.call(this, spread);
                        }

                        UnHideColumnsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForCol(self._sheet, function (sheet, column) {
                                self._sheet.setColumnVisible(column, true);
                            });
                        };
                        return UnHideColumnsAction;
                    })(DesignerActionBase);

                    //Default Column Width
                    var commands_defaultColumnWidth = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetDefaultColumnWidthAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function defaultColumnWidth(spread, width) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "defaultColumnWidth", commands_defaultColumnWidth, {
                            sheetName: sheetName,
                            width: width,
                            spread: spread
                        });
                    }

                    spreadActions.defaultColumnWidth = defaultColumnWidth;

                    var SetDefaultColumnWidthAction = (function (_super) {
                        __extends(SetDefaultColumnWidthAction, _super);
                        function SetDefaultColumnWidthAction(spread) {
                            _super.call(this, spread);
                        }

                        SetDefaultColumnWidthAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.defaults.colWidth = options.width;
                        };
                        return SetDefaultColumnWidthAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Protect Sheet/Lock Cell Actions
                    //Protect Sheet
                    var commands_protectSheet = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ProtectSheetAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function protectSheet(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "protectSheet", commands_protectSheet, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.protectSheet = protectSheet;

                    var ProtectSheetAction = (function (_super) {
                        __extends(ProtectSheetAction, _super);

                        function ProtectSheetAction(spread) {
                            _super.call(this, spread);
                        }

                        ProtectSheetAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.options.isProtected = options.value;
                            designer.actions.isFileModified = true;
                        };
                        return ProtectSheetAction;
                    })(DesignerActionBase);

                    //Lock Cells
                    var commands_LockCells = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new LockCellsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function LockCells(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "LockCells", commands_LockCells, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.LockCells = LockCells;

                    var LockCellsAction = (function (_super) {
                        __extends(LockCellsAction, _super);
                        function LockCellsAction(spread, value) {
                            _super.call(this, spread);
                        }

                        LockCellsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.execInSelections(self._sheet, "locked", function (sheet, row, column) {
                                var style = sheet.getStyle(row, column);
                                if (!style) {
                                    style = new Sheets.Style();
                                }
                                style.locked = options.value;
                                sheet.setStyle(row, column, style);
                            }, options._selections);
                        };
                        return LockCellsAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region AutoFill Actions
                    //Fill by direction, value is a
                    var commands_autoFillByDirection = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new AutoFillByDirectionAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function autoFillByDirection(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "autoFillByDirection", commands_autoFillByDirection, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.autoFillByDirection = autoFillByDirection;

                    var AutoFillByDirectionAction = (function (_super) {
                        __extends(AutoFillByDirectionAction, _super);
                        function AutoFillByDirectionAction(spread) {
                            _super.call(this, spread);
                        }

                        AutoFillByDirectionAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                var startRange;
                                switch (options.value) {
                                    case 3 /* Down */
                                    :
                                        startRange = new Sheets.Range(selection.row, selection.col, 1, selection.colCount);
                                        break;
                                    case 2 /* Up */
                                    :
                                        var startRow = selection.row;
                                        if (startRow == -1) {
                                            startRow = self._sheet.getRowCount() - 1;
                                        } else {
                                            startRow = selection.row + selection.rowCount - 1;
                                        }
                                        startRange = new Sheets.Range(startRow, selection.col, 1, selection.colCount);
                                        break;
                                    case 1 /* Right */
                                    :
                                        startRange = new Sheets.Range(selection.row, selection.col, selection.rowCount, 1);
                                        break;
                                    case 0 /* Left */
                                    :
                                        var startCol = selection.col;
                                        if (startCol == -1) {
                                            startCol = self._sheet.getColumnCount() - 1;
                                        } else {
                                            startCol = selection.col + selection.colCount - 1;
                                        }
                                        startRange = new Sheets.Range(selection.row, startCol, selection.rowCount, 1);
                                        break;
                                }
                                self._sheet.fillAuto(startRange, selection, {fillType: 0, direction: options.value});
                            }
                        };
                        return AutoFillByDirectionAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Clear Actions
                    //Clear All
                    var commands_clearAll = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearAllAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearAll(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearAll", commands_clearAll, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.clearAll = clearAll;

                    var ClearAllAction = (function (_super) {
                        __extends(ClearAllAction, _super);
                        function ClearAllAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearAllAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                self._sheet.clear(selection.row, selection.col, selection.rowCount, selection.colCount, 3 /* viewport */, 32 /* Axis */ | 64 /* BindingPath */ | 1 /* Data */ | 16 /* Sparkline */ | 2 /* Style */ | 4 /* Comment */);
                                self.clearSpansInSelection(self._sheet, selection);
                            }
                        };
                        return ClearAllAction;
                    })(DesignerActionBase);

                    //Clear Format
                    var commands_clearFormat = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearFormatAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearFormat(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearFormat", commands_clearFormat, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.clearFormat = clearFormat;

                    var ClearFormatAction = (function (_super) {
                        __extends(ClearFormatAction, _super);
                        function ClearFormatAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearFormatAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                self._sheet.clear(selection.row, selection.col, selection.rowCount, selection.colCount, 3 /* viewport */, 2 /* Style */);
                                self.clearSpansInSelection(self._sheet, selection);
                            }
                        };
                        return ClearFormatAction;
                    })(DesignerActionBase);

                    //Clear Content
                    var commands_clearContent = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearContentAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearContent(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearContent", commands_clearContent, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.clearContent = clearContent;

                    var ClearContentAction = (function (_super) {
                        __extends(ClearContentAction, _super);
                        function ClearContentAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearContentAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                self._sheet.clear(selection.row, selection.col, selection.rowCount, selection.colCount, 3 /* viewport */, 1 /* Data */);
                            }
                        };
                        return ClearContentAction;
                    })(DesignerActionBase);

                    //Clear Comments
                    var commands_clearComments = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearCommentsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearComments(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearComments", commands_clearComments, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.clearComments = clearComments;
                    var ClearCommentsAction = (function (_super) {
                        __extends(ClearCommentsAction, _super);
                        function ClearCommentsAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearCommentsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._sheet.getSelections();
                            for (var i = 0; i < selections.length; i++) {
                                var selection = selections[i];
                                self._sheet.clear(selection.row, selection.col, selection.rowCount, selection.colCount, 3 /* viewport */, 4 /* Comment */);
                            }
                        };
                        return ClearCommentsAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Sort Actions
                    //The value is a bool parameter, "true" means ascending, "false" means descending.
                    var commands_sortRange = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SortRangeAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function sortRange(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "sortRange", commands_sortRange, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.sortRange = sortRange;

                    var SortRangeAction = (function (_super) {
                        __extends(SortRangeAction, _super);

                        function SortRangeAction(spread) {
                            _super.call(this, spread);
                        }

                        SortRangeAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._selections;
                            if (!selections || selections.length === 0) {
                                return;
                            }
                            if (selections.length > 1) {
                                designer.MessageBox.show(designer.res.NotExecInMultiRanges, designer.res.title, 3 /* error */);
                                return;
                            }
                            var selection = selections[0];
                            self._sheet.sortRange(selection.row, selection.col, selection.rowCount, selection.colCount, true, [{
                                index: selection.col == -1 ? 0 : selection.col,
                                ascending: options.value
                            }]);
                        };
                        return SortRangeAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Filter Actions
                    //Set and Remove filter
                    //The value is a bool parameter, "true" is ribbon filter behavior, "false" is contextmenu filter behavior.
                    var commands_setRowFilter = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetRowFilterAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setRowFilter(spread, isRibbonFilter) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setRowFilter", commands_setRowFilter, {
                            sheetName: sheetName,
                            isRibbonFilter: isRibbonFilter,
                            spread: spread
                        });
                    }

                    spreadActions.setRowFilter = setRowFilter;

                    var SetRowFilterAction = (function (_super) {
                        __extends(SetRowFilterAction, _super);
                        function SetRowFilterAction(spread) {
                            _super.call(this, spread);
                        }

                        SetRowFilterAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var selections = self._selections;
                            if (selections.length > 1) {
                                designer.MessageBox.show(designer.res.NotExecInMultiRanges, designer.res.title, 3 /* error */);
                                return;
                            }
                            var selection = selections[0];
                            if (!self._sheet.rowFilter()) {
                                self._sheet.rowFilter(new Sheets.Filter.HideRowFilter(selection));
                                return;
                            }
                            if (options.isRibbonFilter) {
                                self._sheet.rowFilter().unfilter();
                                self._sheet.rowFilter(null);
                            } else {
                                self._sheet.rowFilter().unfilter();
                                self._sheet.rowFilter(new Sheets.Filter.HideRowFilter(selection));
                            }
                        };
                        return SetRowFilterAction;
                    })(DesignerActionBase);

                    //Clear Filter
                    var commands_clearRowFilter = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearRowFilterAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearRowFilter(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearRowFilter", commands_clearRowFilter, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.clearRowFilter = clearRowFilter;

                    var ClearRowFilterAction = (function (_super) {
                        __extends(ClearRowFilterAction, _super);
                        function ClearRowFilterAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearRowFilterAction.prototype.canExecute = function () {
                            var self = this;
                            if (self._sheet.rowFilter() && self._sheet.rowFilter().isFiltered()) {
                                return true;
                            }
                            return false;
                        };
                        ClearRowFilterAction.prototype.executeImp = function (context, options, isUndo) {
                            this._sheet.rowFilter().unfilter();
                        };
                        return ClearRowFilterAction;
                    })(DesignerActionBase);

                    //Reapply Filter
                    var commands_reApplyRowFilter = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ReApplyRowFilterAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function reApplyRowFilter(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "reApplyRowFilter(", commands_reApplyRowFilter, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.reApplyRowFilter = reApplyRowFilter;

                    var ReApplyRowFilterAction = (function (_super) {
                        __extends(ReApplyRowFilterAction, _super);
                        function ReApplyRowFilterAction(spread) {
                            _super.call(this, spread);
                        }

                        ReApplyRowFilterAction.prototype.canExecute = function () {
                            var self = this;
                            if (self._sheet.rowFilter() && self._sheet.rowFilter().isFiltered()) {
                                return true;
                            }
                            return false;
                        };
                        ReApplyRowFilterAction.prototype.executeImp = function (context, options, isUndo) {
                            this._sheet.rowFilter().filter();
                        };
                        return ReApplyRowFilterAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Group Actions
                    //Group Rows
                    function groupRows(spread) {
                        var sheet = spread.getActiveSheet();
                        var sheetName = sheet.name();
                        var _commandManager = spread.commandManager();
                        var selections = sheet.getSelections();
                        if (!selections || selections.length === 0) {
                            return;
                        }
                        var selection = selections[0];
                        _commandManager.execute({
                            cmd: "outlineRow",
                            sheetName: sheetName,
                            index: selection.row,
                            count: selection.rowCount
                        });
                    }

                    spreadActions.groupRows = groupRows;

                    //Group Columns
                    function groupColumns(spread) {
                        var sheet = spread.getActiveSheet();
                        var sheetName = sheet.name();
                        var _commandManager = spread.commandManager();
                        var selections = sheet.getSelections();
                        if (!selections || selections.length === 0) {
                            return;
                        }
                        var selection = selections[0];
                        _commandManager.execute({
                            cmd: "outlineColumn",
                            sheetName: sheetName,
                            index: selection.col,
                            count: selection.colCount
                        });
                    }

                    spreadActions.groupColumns = groupColumns;

                    //UnGroup Rows
                    function ungroupRows(spread) {
                        var sheet = spread.getActiveSheet();
                        var sheetName = sheet.name();
                        var _commandManager = spread.commandManager();
                        var selections = sheet.getSelections();
                        if (!selections || selections.length === 0) {
                            return;
                        }
                        var selection = selections[0];
                        _commandManager.execute({
                            cmd: "removeRowOutline",
                            sheetName: sheetName,
                            index: selection.row,
                            count: selection.rowCount
                        });
                    }

                    spreadActions.ungroupRows = ungroupRows;

                    //UnGroup Columns
                    function ungroupColumns(spread) {
                        var sheet = spread.getActiveSheet();
                        var sheetName = sheet.name();
                        var _commandManager = spread.commandManager();
                        var selections = sheet.getSelections();
                        if (!selections || selections.length === 0) {
                            return;
                        }
                        var selection = selections[0];
                        _commandManager.execute({
                            cmd: "removeColumnOutline",
                            sheetName: sheetName,
                            index: selection.col,
                            count: selection.colCount
                        });
                    }

                    spreadActions.ungroupColumns = ungroupColumns;

                    // Show Details, expand groups
                    var GroupBase = (function (_super) {
                        __extends(GroupBase, _super);
                        var self;

                        function GroupBase() {
                            _super.apply(this, arguments);
                            self = this;
                        }

                        GroupBase.prototype.expandRowGroup = function (index, level, isCollapse) {
                            self._spread.commandManager().execute({
                                cmd: "expandRowOutline",
                                sheetName: self._sheet.name(),
                                index: index,
                                level: level,
                                collapsed: isCollapse
                            });
                        };

                        GroupBase.prototype.expandColumnGroup = function (index, level, isCollapse) {
                            self._spread.commandManager().execute({
                                cmd: "expandColumnOutline",
                                sheetName: self._sheet.name(),
                                index: index,
                                level: level,
                                collapsed: isCollapse
                            });
                        };

                        GroupBase.prototype.exec = function (isCollapse) {
                            if (this._sheet.getSelections().length > 1) {
                                designer.MessageBox.show("The command cannot be used on multiple selections", "Spread Designer", 1 /* info */, 0 /* ok */);
                                return;
                            }
                            var range = this._sheet.getSelections()[0];
                            if (!range) {
                                return;
                            }

                            for (var i = 0; i < range.rowCount; i++) {
                                var index = range.row + i;
                                if (isCollapse) {
                                    var level = this._getExpandLevel(this._sheet.rowOutlines, index);
                                } else {
                                    var level = this._getCollapsedLevel(this._sheet.rowOutlines, index);
                                }

                                if (level >= 0) {
                                    var rangeGroupInfo = this._sheet.rowOutlines.find(index, level);
                                    if (rangeGroupInfo != null) {
                                        var summaryIndex = this._sheet.rowOutlines.direction() == 1 /* Forward */ ? rangeGroupInfo.end + 1 : rangeGroupInfo.start - 1;
                                        this.expandRowGroup(summaryIndex, level, isCollapse);
                                        break;
                                    }
                                }
                            }

                            for (var j = 0; j < range.colCount; j++) {
                                var index = range.col + j;
                                if (isCollapse) {
                                    var level = this._getExpandLevel(this._sheet.columnOutlines, index);
                                } else {
                                    var level = this._getCollapsedLevel(this._sheet.columnOutlines, index);
                                }
                                if (level >= 0) {
                                    var rangeGroupInfo = this._sheet.columnOutlines.find(index, level);
                                    if (rangeGroupInfo != null) {
                                        var summaryIndex = this._sheet.columnOutlines.direction() == 1 /* Forward */ ? rangeGroupInfo.end + 1 : rangeGroupInfo.start - 1;
                                        self.expandColumnGroup(summaryIndex, level, isCollapse);
                                        break;
                                    }
                                }
                            }
                        };

                        return GroupBase;
                    })(DesignerActionBase);


                    var commands_expandGroup = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ExpandGroup(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function expandGroup(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "expandGroup", commands_expandGroup, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.expandGroup = expandGroup;

                    var ExpandGroup = (function (_super) {
                        __extends(ExpandGroup, _super);
                        function ExpandGroup(spread) {
                            _super.call(this, spread);
                        }

                        ExpandGroup.prototype.executeImp = function (context, options, isUndo) {
                            this.exec(false);
                        };
                        ExpandGroup.prototype._getCollapsedLevel = function (rangeGroup, index) {
                            var level = rangeGroup.getLevel(index);
                            if (level <= 0) {
                                return level;
                            } else {
                                var finalLevel = -1;
                                for (var i = level; i >= 0; i--) {
                                    var info = rangeGroup.find(index, i);
                                    if (info.state() == 1 /* Collapsed */) {
                                        finalLevel = i;
                                    } else {
                                        info = info.parent;
                                    }
                                }
                                return finalLevel;
                            }
                        };
                        return ExpandGroup;
                    })(GroupBase);

                    //Hide detail, collapse group
                    var commands_collapseGroup = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new CollapseGroup(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function collapseGroup(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "collapseGroup", commands_collapseGroup, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.collapseGroup = collapseGroup;

                    var CollapseGroup = (function (_super) {
                        __extends(CollapseGroup, _super);
                        function CollapseGroup(spread) {
                            _super.call(this, spread);
                        }

                        CollapseGroup.prototype.executeImp = function (context, options, isUndo) {
                            this.exec(true);
                        };

                        CollapseGroup.prototype._getExpandLevel = function (rangeGroup, index) {
                            var level = rangeGroup.getLevel(index);
                            if (level <= 0) {
                                return level;
                            } else {
                                for (var i = level; i >= 0; i--) {
                                    var info = rangeGroup.find(index, i);
                                    if (info.state() == 0 /* Expanded */) {
                                        return i;
                                    } else {
                                        info = info.parent;
                                    }
                                }
                                return -1;
                            }
                        };
                        return CollapseGroup;
                    })(GroupBase);

                    //#endregion
                    //#region Show/Hide Actions
                    //Show/Hide Row Header
                    var commands_showHideRowHeader = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ShowHideRowHeaderAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function showHideRowHeader(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "showHideRowHeader", commands_showHideRowHeader, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.showHideRowHeader = showHideRowHeader;

                    var ShowHideRowHeaderAction = (function (_super) {
                        __extends(ShowHideRowHeaderAction, _super);

                        function ShowHideRowHeaderAction(spread) {
                            _super.call(this, spread);
                        }

                        ShowHideRowHeaderAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.options.rowHeaderVisible = !self._sheet.options.rowHeaderVisible;
                        };
                        return ShowHideRowHeaderAction;
                    })(DesignerActionBase);

                    //Show/Hide Column Header
                    var commands_showHideColumnHeader = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ShowHideColumnHeaderAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function showHideColumnHeader(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "showHideColumnHeader", commands_showHideColumnHeader, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.showHideColumnHeader = showHideColumnHeader;

                    var ShowHideColumnHeaderAction = (function (_super) {
                        __extends(ShowHideColumnHeaderAction, _super);
                        function ShowHideColumnHeaderAction(spread) {
                            _super.call(this, spread);
                        }

                        ShowHideColumnHeaderAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.options.colHeaderVisible = !self._sheet.options.colHeaderVisible;
                        };
                        return ShowHideColumnHeaderAction;
                    })(DesignerActionBase);

                    //Show/Hide Vertical GridLine
                    var commands_showHideVGridLine = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ShowHideVGridLineAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function showHideVGridLine(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "showHideVGridLine", commands_showHideVGridLine, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.showHideVGridLine = showHideVGridLine;

                    var ShowHideVGridLineAction = (function (_super) {
                        __extends(ShowHideVGridLineAction, _super);

                        function ShowHideVGridLineAction(spread) {
                            _super.call(this, spread);
                        }

                        ShowHideVGridLineAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.options.gridline.showVerticalGridline = !self._sheet.options.gridline.showVerticalGridline;
                        };
                        return ShowHideVGridLineAction;
                    })(DesignerActionBase);

                    //Show/Hide Horizontal GridLine
                    var commands_showHideHGridLine = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ShowHideHGridLineAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function showHideHGridLine(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "showHideHGridLine", commands_showHideHGridLine, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.showHideHGridLine = showHideHGridLine;

                    var ShowHideHGridLineAction = (function (_super) {
                        __extends(ShowHideHGridLineAction, _super);
                        function ShowHideHGridLineAction(spread) {
                            _super.call(this, spread);
                        }

                        ShowHideHGridLineAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.options.gridline.showHorizontalGridline = !self._sheet.options.gridline.showHorizontalGridline;
                        };
                        return ShowHideHGridLineAction;
                    })(DesignerActionBase);

                    //Show/Hide TabStrip
                    var commands_showHideTabStrip = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ShowHideTabStripAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function showHideTabStrip(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "showHideTabStrip", commands_showHideTabStrip, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.showHideTabStrip = showHideTabStrip;

                    var ShowHideTabStripAction = (function (_super) {
                        __extends(ShowHideTabStripAction, _super);
                        function ShowHideTabStripAction(spread) {
                            _super.call(this, spread);
                        }

                        ShowHideTabStripAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._spread.options.tabStripVisible = !self._spread.options.tabStripVisible;
                        };
                        return ShowHideTabStripAction;
                    })(DesignerActionBase);

                    //Show/Hide New Tab
                    var commands_showHideNewTab = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ShowHideNewTabAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function showHideNewTab(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "showHideNewTab", commands_showHideNewTab, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.showHideNewTab = showHideNewTab;

                    var ShowHideNewTabAction = (function (_super) {
                        __extends(ShowHideNewTabAction, _super);

                        function ShowHideNewTabAction(spread) {
                            _super.call(this, spread);
                        }

                        ShowHideNewTabAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._spread.options.newTabVisible = !self._spread.options.newTabVisible;
                        };
                        return ShowHideNewTabAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Zoom Actions
                    //Zoom to a special value
                    function zoom(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        spread.commandManager().execute({cmd: "zoom", sheetName: sheetName, zoomFactor: value});
                    }

                    spreadActions.zoom = zoom;

                    //Zoom to default: 100%
                    function zoomDefault(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        spread.commandManager().execute({cmd: "zoom", sheetName: sheetName, zoomFactor: 1});
                    }

                    spreadActions.zoomDefault = zoomDefault;

                    //Zoom to selection
                    function zoomSelection(spread) {
                        var sheet = spread.getActiveSheet();
                        var sheetName = sheet.name();
                        var zoomInfo = designer.actions._getPreferredZoomInfo();
                        if (zoomInfo) {
                            sheet.showCell(zoomInfo.topRow, zoomInfo.leftCol, 0 /* top */, 0 /* left */);
                            spread.commandManager().execute({
                                cmd: "zoom",
                                sheetName: sheetName,
                                zoomFactor: zoomInfo.zoom
                            });
                        }
                    }

                    spreadActions.zoomSelection = zoomSelection;

                    //#endregion
                    //#region Freeze

                    var commands_freeze = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new FreezeAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function freeze(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "freeze", commands_freeze, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.freeze = freeze;

                    var FreezeAction = (function (_super) {
                        __extends(FreezeAction, _super);
                        function FreezeAction(spread) {
                            _super.call(this, spread);
                        }

                        FreezeAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            if (options.value.row !== undefined) {
                                self._sheet.frozenRowCount(Number(options.value.row));
                            }
                            if (options.value.col !== undefined) {
                                self._sheet.frozenColumnCount(Number(options.value.col));
                            }
                            if (options.value.trailingRow !== undefined) {
                                self._sheet.frozenTrailingRowCount(Number(options.value.trailingRow));
                            }
                            if (options.value.trailingCol !== undefined) {
                                self._sheet.frozenTrailingColumnCount(Number(options.value.trailingCol));
                            }
                        };
                        return FreezeAction;
                    })(DesignerActionBase);


                    var commands_unfreeze = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new UnFreezeAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function unfreeze(spread) {
                        var action = new UnFreezeAction(spread);
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "unfreeze", commands_unfreeze, {sheetName: sheetName, spread: spread});
                    }

                    spreadActions.unfreeze = unfreeze;

                    var UnFreezeAction = (function (_super) {
                        __extends(UnFreezeAction, _super);
                        function UnFreezeAction(spread) {
                            _super.call(this, spread);
                        }

                        UnFreezeAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.frozenRowCount(0);
                            self._sheet.frozenColumnCount(0);
                            self._sheet.frozenTrailingColumnCount(0);
                            self._sheet.frozenTrailingRowCount(0);
                        };
                        return UnFreezeAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Formula
                    //Calculate Now
                    function calculateNow(spread) {
                        //V1 runtime doesn't support it
                    }

                    spreadActions.calculateNow = calculateNow;

                    //Set calculation to Auto or Manual
                    function autoCalculate(spread, value) {
                        //V1 runtime doesn't support it
                    }

                    spreadActions.autoCalculate = autoCalculate;

                    //#endregion
                    //#region Style
                    var commands_setStyle = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetStyleAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setStyle(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setStyle", commands_setStyle, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setStyle = setStyle;
                    var SetStyleAction = (function (_super) {
                        __extends(SetStyleAction, _super);
                        //value parameter is HorizontalAlign enum, including left,center and right.
                        function SetStyleAction(spread) {
                            _super.call(this, spread);
                        }

                        SetStyleAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.setStyle("", options.value, options);
                            self.updateRibbon();
                        };
                        SetStyleAction.prototype.setStyle = function (attribute, value, options) {
                            var self = this;
                            var self = this, sheet = self._sheet, spread = self._spread;
                            spread.suspendPaint();
                            self.execInSelections(sheet, attribute, function (sheet, row, column) {
                                var newStyle = value.clone();

                                var style = sheet.getStyle(row, column);
                                if (style) {
                                    for (var loop in style) {
                                        if (loop !== "_id" && newStyle[loop] === keyword_undefined) {
                                            newStyle[loop] = style[loop];
                                        }
                                    }
                                }


                                sheet.setStyle(row, column, newStyle);
                            }, options._selections);
                            spread.resumePaint();
                        };
                        return SetStyleAction;
                    })(DesignerActionBase);


                    var commands_setStyleByName = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetStyleObjectAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setStyleByName(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setStyleByName", commands_setStyleByName, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setStyleByName = setStyleByName;
                    var SetStyleObjectAction = (function (_super) {
                        __extends(SetStyleObjectAction, _super);
                        function SetStyleObjectAction(spread, value) {
                            _super.call(this, spread);
                        }

                        SetStyleObjectAction.prototype.executeImp = function (condex, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.execInSelections(self._sheet, "", function (sheet, row, column) {
                                sheet.setStyleName(row, column, options.value);
                            }, options._selections);
                        };
                        return SetStyleObjectAction;
                    })(DesignerActionBase);

                    var commands_addNamedStyle = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new AddNamedStyleAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function addNamedStyle(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "addNameStyle", commands_addNamedStyle, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.addNamedStyle = addNamedStyle;
                    var AddNamedStyleAction = (function (_super) {
                        __extends(AddNamedStyleAction, _super);
                        function AddNamedStyleAction(spread, value) {
                            _super.call(this, spread);
                        }

                        AddNamedStyleAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options.spread.addNamedStyle(options.value);
                        };
                        return AddNamedStyleAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region DataValidation
                    var commands_setDataValidation = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetDataValidation(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setDataValidation(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setDataValidation", commands_setDataValidation, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setDataValidation = setDataValidation;
                    var SetDataValidation = (function (_super) {
                        __extends(SetDataValidation, _super);
                        function SetDataValidation(spread) {
                            _super.call(this, spread);
                        }

                        SetDataValidation.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelections(self._sheet, "validator", function (sheet, row, column) {
                                var style = sheet.getStyle(row, column);
                                if (!style) {
                                    style = new Sheets.Style();
                                }
                                style.validator = options.value;
                                sheet.setStyle(row, column, style);
                            }, self._selections);
                        };
                        return SetDataValidation;
                    })(DesignerActionBase);

                    var commands_circleInvalidData = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new CircleInvalidData(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function circleInvalidData(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "circleInvalidData", commands_circleInvalidData, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.circleInvalidData = circleInvalidData;
                    var CircleInvalidData = (function (_super) {
                        __extends(CircleInvalidData, _super);
                        function CircleInvalidData(spread) {
                            _super.call(this, spread);
                        }

                        CircleInvalidData.prototype.executeImp = function (context, options, isUndo) {
                            options.spread.options.highlightInvalidData = true;
                        };
                        return CircleInvalidData;
                    })(DesignerActionBase);

                    var commands_unCircleInvalidData = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new UnCircleInvalidData(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function unCircleInvalidData(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "unCircleInvalidData", commands_unCircleInvalidData, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.unCircleInvalidData = unCircleInvalidData;
                    var UnCircleInvalidData = (function (_super) {
                        __extends(UnCircleInvalidData, _super);
                        function UnCircleInvalidData(spread) {
                            _super.call(this, spread);
                        }

                        UnCircleInvalidData.prototype.executeImp = function (context, options, isUndo) {
                            options.spread.options.highlightInvalidData = false;
                        };
                        return UnCircleInvalidData;
                    })(DesignerActionBase);

                    //#endregion
                    //#region Enum
                    (function (SelectionRangeType) {
                        SelectionRangeType[SelectionRangeType["Sheet"] = 0] = "Sheet";
                        SelectionRangeType[SelectionRangeType["OnlyRow"] = 1] = "OnlyRow";
                        SelectionRangeType[SelectionRangeType["OnlyColumn"] = 2] = "OnlyColumn";
                        SelectionRangeType[SelectionRangeType["OnlyCells"] = 3] = "OnlyCells";
                        SelectionRangeType[SelectionRangeType["Mixture"] = 4] = "Mixture";
                    })(spreadActions.SelectionRangeType || (spreadActions.SelectionRangeType = {}));
                    var SelectionRangeType = spreadActions.SelectionRangeType;

                    //#endregion
                    //#region Help function
                    //Get the selection area's type
                    function getSelectionType(spread) {
                        var selections = spread.getActiveSheet().getSelections();
                        var selectionType;
                        for (var i = 0; i < selections.length; i++) {
                            var selection = selections[i];
                            if (selection.col == -1 && selection.row == -1) {
                                return 0 /* Sheet */;
                            } else if (selection.row == -1) {
                                if (selectionType == undefined) {
                                    selectionType = 2 /* OnlyColumn */;
                                } else if (selectionType != 2 /* OnlyColumn */) {
                                    return 4 /* Mixture */;
                                }
                            } else if (selection.col == -1) {
                                if (selectionType == undefined) {
                                    selectionType = 1 /* OnlyRow */;
                                } else if (selectionType != 1 /* OnlyRow */) {
                                    return 4 /* Mixture */;
                                }
                            } else {
                                if (selectionType == undefined) {
                                    selectionType = 3 /* OnlyCells */;
                                } else if (selectionType != 3 /* OnlyCells */) {
                                    return 4 /* Mixture */;
                                }
                            }
                        }
                        return selectionType;
                    }

                    spreadActions.getSelectionType = getSelectionType;

                    //#endregion
                    //#region CellType
                    var commands_setCellType = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetCellTypeAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setCellType(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setCellType", commands_setCellType, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setCellType = setCellType;

                    var SetCellTypeAction = (function (_super) {
                        __extends(SetCellTypeAction, _super);
                        function SetCellTypeAction(spread) {
                            _super.call(this, spread);
                        }

                        SetCellTypeAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.execInSelections(self._sheet, "cellType", function (sheet, row, column) {
                                var style = sheet.getStyle(row, column);
                                if (!style) {
                                    style = new Sheets.Style();
                                }
                                style.cellType = options.value;
                                sheet.setStyle(row, column, style);
                            }, options._selections);
                        };
                        return SetCellTypeAction;
                    })(DesignerActionBase);

                    //#endregion
                    //#region clearCellType
                    var commands_clearCellType = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearCellTypeAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearCellType(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearCellType", commands_clearCellType, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.clearCellType = clearCellType;

                    var ClearCellTypeAction = (function (_super) {
                        __extends(ClearCellTypeAction, _super);
                        function ClearCellTypeAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearCellTypeAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            options._selections = options._selections || self._selections;
                            self.execInSelections(self._sheet, "", function (sheet, row, column) {
                                var style = sheet.getStyle(row, column);
                                if (!style) {
                                    style = new Sheets.Style();
                                }

                                //undefined will as inherit.
                                style.cellType = undefined;
                                sheet.setStyle(row, column, style);
                            }, options._selections);
                        };
                        return ClearCellTypeAction;
                    })(DesignerActionBase);

                    //#endregion
                    var commands_setAutoFormula = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetAutoFormulaAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setAutoFormula(spread, formulaName) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setAutoFormula", commands_setAutoFormula, {
                            sheetName: sheetName,
                            formulaName: formulaName,
                            spread: spread
                        });
                    }

                    spreadActions.setAutoFormula = setAutoFormula;

                    var SetAutoFormulaAction = (function (_super) {
                        __extends(SetAutoFormulaAction, _super);
                        var self;

                        function SetAutoFormulaAction(spread) {
                            _super.call(this, spread);
                        }

                        SetAutoFormulaAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sheet.suspendCalcService();
                            if (getSelectionType(self.spread()) == 3 /* OnlyCells */) {
                                var selections = self._sheet.getSelections();
                                for (var i = 0; i < selections.length; i++) {
                                    //Validate whether the range's last row reach the sheet's bottom
                                    if (selections[i].row + selections[i].rowCount == self._sheet.getRowCount()) {
                                        continue;
                                    } else {
                                        self._setFormulaForSingleRange(selections[i], options.formulaName);
                                    }
                                }
                            }
                            self._sheet.resumeCalcService();
                        };
                        SetAutoFormulaAction.prototype._setFormulaForSingleRange = function (range, formulaName) {
                            var self = this;
                            for (var i = 0; i < range.colCount; i++) {
                                var formula = GC.Spread.Sheets.CalcEngine.rangeToFormula(range, 0, 0, GC.Spread.Sheets.CalcEngine.RangeReferenceRelative.allAbsolute);
                                formula = "=" + formulaName + "(" + formula + ")";
                                self._sheet.setFormula(range.row + range.rowCount, range.col + i, formula);
                            }
                        };
                        return SetAutoFormulaAction;
                    })(DesignerActionBase);


                    var commands_setFormulaSparklineWithoutDatarange = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetFormulaSparklineWithoutDatarangeAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setFormulaSparklineWithoutDatarange(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setFormulaSparklineWithoutDatarange", commands_setFormulaSparklineWithoutDatarange, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });

                    }

                    spreadActions.setFormulaSparklineWithoutDatarange = setFormulaSparklineWithoutDatarange;
                    var SetFormulaSparklineWithoutDatarangeAction = (function (_super) {
                        __extends(SetFormulaSparklineWithoutDatarangeAction, _super);
                        function SetFormulaSparklineWithoutDatarangeAction(spread) {
                            _super.call(this, spread);
                        }

                        SetFormulaSparklineWithoutDatarangeAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var locationRange = options.value.locationRange;
                            var sparklineType = options.value.type;
                            var formula = options.value.formula;
                            if (locationRange) {
                                if (locationRange.rowCount === 1 && locationRange.colCount === 1) {
                                    self._sheet.setFormula(locationRange.row, locationRange.col, formula);
                                } else {
                                    designer.MessageBox.show(designer.res.createSparklineDialog.warningText, designer.res.title, 2 /* warning */, 0 /* ok */);
                                    return;
                                }
                            } else {
                                var row = self._sheet.getActiveRowIndex();
                                var col = self._sheet.getActiveColumnIndex();
                                if (formula) {
                                    self._sheet.setFormula(row, col, formula);
                                }
                            }
                        };
                        return SetFormulaSparklineWithoutDatarangeAction;
                    })(DesignerActionBase);


                    var commands_setFormulaSparkline = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetFormulaSparklineAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setFormulaSparkline(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setFormulaSparkline", commands_setFormulaSparkline, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setFormulaSparkline = setFormulaSparkline;
                    var SetFormulaSparklineAction = (function (_super) {
                        __extends(SetFormulaSparklineAction, _super);
                        function SetFormulaSparklineAction(spread) {
                            _super.call(this, spread);
                        }

                        SetFormulaSparklineAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var locationRange = options.value.locationRange;
                            var sparklineType = options.value.type;
                            var formula = options.value.formula;
                            var sheet = self._sheet;
                            var dataRange;
                            var dataRangeObj = designer.CEUtility.parseStringToExternalRanges(self._value.dataRange, sheet);
                            if (dataRangeObj && dataRangeObj.length > 0 && dataRangeObj[0]) {
                                dataRange = dataRangeObj[0].range;
                            } else {
                                designer.MessageBox.show(designer.res.compatibleSparklineDialog.errorMessage, designer.res.title, 2 /* warning */, 0 /* ok */);
                                return;
                            }
                            if (!locationRange) {
                                if (designer.MultiRangeFormulaType[designer.FormulaSparklineType[sparklineType]] !== undefined) {
                                    var parameterSets = self._value.parameterSets;
                                    if (!parameterSets) {
                                        return;
                                    }
                                    var selections = sheet.getSelections();
                                    if (selections && selections.length > 0) {
                                        var allowEdit = self._allowMultiCellFormulaEdit(selections);
                                        if (!allowEdit) {
                                            designer.MessageBox.show(designer.res.multiCellFormula.warningText, designer.res.title, 2 /* warning */, 0 /* ok */);
                                            return;
                                        }
                                        var len = selections.length;
                                        var isSingleCell = (len === 1 && selections[0].rowCount === 1 && selections[0].colCount === 1) ? true : false;
                                        self.execInSelections(sheet, "", function (sheet, row, col) {
                                            var newFormula = self._reCalcMultiRangeFormula(row, col, sparklineType, parameterSets, isSingleCell);
                                            if (newFormula) {
                                                sheet.setFormula(row, col, newFormula);
                                            }
                                        });
                                    }
                                } else {
                                    var row = sheet.getActiveRowIndex();
                                    var col = sheet.getActiveColumnIndex();
                                    if (sparklineType === 3 /* compatible */) {
                                        if (dataRange.rowCount !== 1 && dataRange.colCount !== 1) {
                                            designer.MessageBox.show(designer.res.createSparklineDialog.warningText, designer.res.title, 2 /* warning */, 0 /* ok */);
                                            return;
                                        }
                                    }
                                    if (formula) {
                                        sheet.setFormula(row, col, formula);
                                    }
                                }
                            } else {
                                if (sparklineType === 3 /* compatible */) {
                                    if (locationRange.colCount === 1 && locationRange.rowCount === dataRange.rowCount) {
                                        for (var i = 0; i < dataRange.rowCount; i++) {
                                            sheet.setFormula(locationRange.row + i, locationRange.col, self._createFormula(i));
                                        }
                                    } else if (locationRange.rowCount === 1 && locationRange.colCount === dataRange.colCount) {
                                        for (var i = 0; i < dataRange.colCount; i++) {
                                            sheet.setFormula(locationRange.row, locationRange.col + i, self._createFormula(i));
                                        }
                                    } else {
                                        designer.MessageBox.show(designer.res.createSparklineDialog.warningText, designer.res.title, 2 /* warning */, 0 /* ok */);
                                        return;
                                    }
                                } else if (designer.MultiRangeFormulaType[designer.FormulaSparklineType[sparklineType]] !== undefined) {
                                    self._setMultiRangeFormula(dataRange, locationRange);
                                } else {
                                    if (locationRange.rowCount === 1 && locationRange.colCount === 1) {
                                        sheet.setFormula(locationRange.row, locationRange.col, self._createFormula());
                                    } else {
                                        designer.MessageBox.show(designer.res.createSparklineDialog.warningText, designer.res.title, 2 /* warning */, 0 /* ok */);
                                        return;
                                    }
                                }
                                sheet.setSelection(locationRange.row, locationRange.col, locationRange.rowCount, locationRange.colCount);
                            }
                        };

                        SetFormulaSparklineAction.prototype._getFormulaSparklineType = function (formula) {
                            var formulaName;
                            var equalIndex = formula.indexOf("=");
                            var bracketIndex = formula.indexOf("(");
                            if (bracketIndex === -1) {
                                return null;
                            }
                            var startIndex = 0;
                            if (bracketIndex < equalIndex) {
                                startIndex = 0;
                                formulaName = formula.substr(startIndex, bracketIndex + 1);
                            } else {
                                startIndex = equalIndex === -1 ? 0 : 1;
                                formulaName = formula.substr(startIndex, bracketIndex - startIndex);
                            }
                            formulaName = formulaName.toLowerCase();
                            switch (formulaName) {
                                case "piesparkline":
                                    return 0 /* pie */;
                                case "areasparkline":
                                    return 1 /* area */;
                                case "scattersparkline":
                                    return 2 /* scatter */;
                                case "linesparkline":
                                case "columnsparkline":
                                case "winlosssparkline":
                                    return 3 /* compatible */;
                                case "bulletsparkline":
                                    return 4 /* bullet */;
                                case "spreadsparkline":
                                    return 5 /* spread */;
                                case "stackedsparkline":
                                    return 6 /* stacked */;
                                case "hbarsparkline":
                                    return 7 /* hbar */;
                                case "vbarsparkline":
                                    return 8 /* vbar */;
                                case "variancesparkline":
                                    return 9 /* variance */;
                                case "boxplotsparkline":
                                    return 10 /* boxplot */;
                                case "cascadesparkline":
                                    return 11 /* cascade */;
                                case "paretosparkline":
                                    return 12 /* pareto */;
                                default:
                                    return null;
                            }
                        };

                        SetFormulaSparklineAction.prototype._allowMultiCellFormulaEdit = function (selections) {
                            var self = this;
                            var firstInfo, firstType;
                            var hasFirstInfo = false;
                            self.execInSelections(self._sheet, "", function (sheet, row, col) {
                                var info = sheet.getFormulaInformation(row, col);
                                if (info.hasFormula) {
                                    if (!hasFirstInfo) {
                                        firstInfo = info;
                                        firstType = self._getFormulaSparklineType(firstInfo.formula);
                                        hasFirstInfo = true;
                                    } else {
                                        var type = self._getFormulaSparklineType(info.formula);
                                        if (firstType !== type || firstInfo.isArrayFormula !== info.isArrayFormula) {
                                            return false;
                                        }
                                    }
                                }
                            });
                            return hasFirstInfo;
                        };

                        SetFormulaSparklineAction.prototype._reCalcMultiRangeFormula = function (row, col, type, parameters, isSingleCell) {
                            var expr = designer.util.parseFormulaSparkline(row, col);
                            var formulaArgs;
                            if (!expr || !expr.arguments) {
                                return null;
                            } else {
                                formulaArgs = expr.arguments;
                                if (!formulaArgs || formulaArgs.length <= 0) {
                                    return null;
                                }
                            }
                            var formula = "";
                            switch (type) {
                                case 11 /* cascade */
                                :
                                    formula += "CASCADESPARKLINE";
                                    if (!isSingleCell) {
                                        parameters[1] = designer.util.unParseFormula(formulaArgs[1], row, col);
                                    }
                                    break;
                                case 12 /* pareto */
                                :
                                    formula += "PARETOSPARKLINE";
                                    if (!isSingleCell) {
                                        parameters[1] = designer.util.unParseFormula(formulaArgs[1], row, col);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            var params = "";
                            for (var i = 0; i < parameters.length; i++) {
                                var item = parameters[i];
                                if (item !== undefined && item !== null) {
                                    params += item + ",";
                                } else {
                                    params += ",";
                                }
                            }
                            params = this._removeContinuousComma(params);
                            formula += "(" + params + ")";
                            return formula;
                        };

                        SetFormulaSparklineAction.prototype._removeContinuousComma = function (parameter) {
                            var len = parameter.length;
                            while (len > 0 && parameter[len - 1] === ",") {
                                len--;
                            }
                            return parameter.substr(0, len);
                        };

                        SetFormulaSparklineAction.prototype._setMultiRangeFormula = function (dataRange, locationRange) {
                            var self = this;
                            var count = 0;
                            var index = 1;
                            var vertical = false;
                            var base = 0;
                            var formula = "";
                            var totalNumbers = dataRange.rowCount * dataRange.colCount;
                            if (locationRange.rowCount < locationRange.colCount) {
                                vertical = true;
                                base = locationRange.col;
                                count = Math.min(totalNumbers, locationRange.colCount);
                            } else {
                                vertical = false;
                                base = locationRange.row;
                                count = Math.min(totalNumbers, locationRange.rowCount);
                            }
                            if (vertical) {
                                for (var c = base; c < base + count; c++) {
                                    formula = this._createMultiRangeFormula(index, vertical);
                                    self._sheet.setFormula(locationRange.row, c, formula);
                                    index++;
                                }
                            } else {
                                for (var r = base; r < base + count; r++) {
                                    formula = this._createMultiRangeFormula(index, vertical);
                                    self._sheet.setFormula(r, locationRange.col, formula);
                                    index++;
                                }
                            }
                        };

                        SetFormulaSparklineAction.prototype._createMultiRangeFormula = function (index, vertical) {
                            var dataRangeString = this._value.dataRange;
                            var dataRangeObj = designer.CEUtility.parseStringToExternalRanges(dataRangeString, this._sheet);
                            var resourceName;
                            if (!dataRangeObj || dataRangeObj.length === 0) {
                                return null;
                            }
                            if (dataRangeObj[0].resource) {
                                resourceName = dataRangeObj[0].resource.name();
                            }
                            if (resourceName) {
                                dataRangeString = resourceName + dataRangeString;
                            }

                            var sparklineType = this._value.type;
                            var formula;
                            switch (sparklineType) {
                                case 11 /* cascade */
                                :
                                    if (vertical) {
                                        formula = "=CASCADESPARKLINE(" + dataRangeString + "," + index + ",,,,,,true" + ")";
                                    } else {
                                        formula = "=CASCADESPARKLINE(" + dataRangeString + "," + index + ",,,,,,false" + ")";
                                    }
                                    break;
                                case 12 /* pareto */
                                :
                                    if (vertical) {
                                        formula = "=PARETOSPARKLINE(" + dataRangeString + "," + index + ",,,,,,true" + ")";
                                    } else {
                                        formula = "=PARETOSPARKLINE(" + dataRangeString + "," + index + ",,,,,,false" + ")";
                                    }
                                    break;
                            }
                            return formula;
                        };

                        SetFormulaSparklineAction.prototype._createFormula = function (index) {
                            var compatibleType = this._value.compatibleType;
                            var locationRange = this._value.locationRange;
                            var dataRangeString = this._value.dataRange;
                            var dataRangeObj = designer.CEUtility.parseStringToExternalRanges(dataRangeString, this._sheet);
                            var resourceName;
                            var dataRange;
                            if (!dataRangeObj || dataRangeObj.length === 0) {
                                return null;
                            }
                            if (dataRangeObj[0].resource) {
                                resourceName = dataRangeObj[0].resource.name();
                            }
                            if (dataRangeObj[0]) {
                                dataRange = dataRangeObj[0].range;
                            }

                            var sparklineType = this._value.type;
                            var formula;
                            var range;

                            if (sparklineType === 3 /* compatible */) {
                                var dataOrientation = dataRange.colCount === 1 ? 0 /* Vertical */ : 1 /* Horizontal */;
                                if (dataRange.colCount !== 1 && dataRange.rowCount !== 1) {
                                    dataOrientation = locationRange.colCount === 1 ? 1 /* Horizontal */ : 0 /* Vertical */;
                                }
                                if (locationRange.rowCount === 1) {
                                    range = new Sheets.Range(dataRange.row, dataRange.col + index, 1, dataRange.colCount);
                                } else {
                                    range = new Sheets.Range(dataRange.row + index, dataRange.col, dataRange.rowCount, 1);
                                }
                                var rangeString = designer.CEUtility.parseExternalRangeToString({"range": range});
                                if (resourceName) {
                                    rangeString = "'" + resourceName + "'" + "!" + rangeString;
                                }
                                switch (compatibleType) {
                                    case "line":
                                        formula = "=LINESPARKLINE(" + rangeString + "," + dataOrientation + ")";
                                        break;
                                    case "column":
                                        formula = "=COLUMNSPARKLINE(" + rangeString + "," + dataOrientation + ")";
                                        break;
                                    case "winloss":
                                        formula = "=WINLOSSSPARKLINE(" + rangeString + "," + dataOrientation + ")";
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                if (resourceName) {
                                    dataRangeString = resourceName + dataRangeString;
                                }
                                switch (sparklineType) {
                                    case 0 /* pie */
                                    :
                                        formula = "=PIESPARKLINE(" + dataRangeString + ")";
                                        break;
                                    case 1 /* area */
                                    :
                                        formula = "=AREASPARKLINE(" + dataRangeString + ")";
                                        break;
                                    case 2 /* scatter */
                                    :
                                        formula = "=SCATTERSPARKLINE(" + dataRangeString + ")";
                                        break;
                                    case 5 /* spread */
                                    :
                                        formula = "=SPREADSPARKLINE(" + dataRangeString + ")";
                                        break;
                                    case 6 /* stacked */
                                    :
                                        formula = "=STACKEDSPARKLINE(" + dataRangeString + ")";
                                        break;
                                    case 10 /* boxplot */
                                    :
                                        formula = "=BOXPLOTSPARKLINE(" + dataRangeString + ")";
                                        break;
                                    default:
                                        break;
                                }
                            }
                            return formula;
                        };
                        return SetFormulaSparklineAction;
                    })(DesignerActionBase);


                    var commands_setDefaultSparkline = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetDefaultSparklineAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setDefaultSparkline(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setDefaultSparkline", commands_setDefaultSparkline, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setDefaultSparkline = setDefaultSparkline;

                    var SetDefaultSparklineAction = (function (_super) {
                        __extends(SetDefaultSparklineAction, _super);
                        function SetDefaultSparklineAction(spread, value) {
                            _super.call(this, spread);
                        }

                        SetDefaultSparklineAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sparklines = [];
                            var dataRange = self._value.dataRange;
                            var locationRange = self._value.locationRange;
                            var sparklineType = self._value.sparklineType;

                            if (locationRange.colCount == 1 && locationRange.rowCount == dataRange.rowCount) {
                                for (var i = 0; i < dataRange.rowCount; i++) {
                                    sparklines.push(self._sheet.setSparkline(locationRange.row + i, locationRange.col, new Sheets.Range(dataRange.row + i, dataRange.col, 1, dataRange.colCount), 1 /* Horizontal */, self._value.sparklineType, new Sheets.Sparklines.SparklineSetting()));
                                }
                            } else if (locationRange.rowCount == 1 && locationRange.colCount == dataRange.colCount) {
                                for (var i = 0; i < dataRange.colCount; i++) {
                                    sparklines.push(self._sheet.setSparkline(locationRange.row, locationRange.col + i, new Sheets.Range(dataRange.row, dataRange.col + i, dataRange.rowCount, 1), 0 /* Vertical */, self._value.sparklineType, new Sheets.Sparklines.SparklineSetting()));
                                }
                            } else {
                                designer.MessageBox.show(designer.res.createSparklineDialog.warningText, designer.res.title, 2 /* warning */, 0 /* ok */);
                                return;
                            }

                            self._sheet.groupSparkline(sparklines);
                            self._sheet.setSelection(locationRange.row, locationRange.col, locationRange.rowCount, locationRange.colCount);
                        };
                        return SetDefaultSparklineAction;
                    })(DesignerActionBase);


                    var commands_setSparklineType = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetSparklineTypeAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setSparklineType(spread, sparklineType) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setSparklineType", commands_setSparklineType, {
                            sheetName: sheetName,
                            sparklineType: sparklineType,
                            spread: spread
                        });
                    }

                    spreadActions.setSparklineType = setSparklineType;

                    var SetSparklineTypeAction = (function (_super) {
                        __extends(SetSparklineTypeAction, _super);
                        function SetSparklineTypeAction(spread) {
                            _super.call(this, spread);
                        }

                        SetSparklineTypeAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._sparklineType = options.sparklineType;
                            self.execInSelectionsForCell(self._sheet, function (sheet, row, column) {
                                sheet.getSparkline(row, column).sparklineType(self._sparklineType);
                            });
                        };
                        return SetSparklineTypeAction;
                    })(DesignerActionBase);


                    var commands_setSparklineSetting = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetSparklineSettingAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setSparklineSetting(spread, value, property) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setSparklineSetting", commands_setSparklineSetting, {
                            sheetName: sheetName,
                            value: value,
                            property: property,
                            spread: spread
                        });
                    }

                    spreadActions.setSparklineSetting = setSparklineSetting;

                    var SetSparklineSettingAction = (function (_super) {
                        __extends(SetSparklineSettingAction, _super);
                        function SetSparklineSettingAction(spread) {
                            _super.call(this, spread);
                        }

                        SetSparklineSettingAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForCell(self._sheet, function (sheet, row, column) {
                                sheet.getSparkline(row, column).setting().options[options.property] = options.value;
                            });
                        };
                        return SetSparklineSettingAction;
                    })(DesignerActionBase);


                    var commands_groupSparkline = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new GroupSparklineAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function groupSparkline(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "groupSparkline", commands_groupSparkline, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.groupSparkline = groupSparkline;

                    var GroupSparklineAction = (function (_super) {
                        __extends(GroupSparklineAction, _super);
                        function GroupSparklineAction(spread) {
                            _super.call(this, spread);
                        }

                        GroupSparklineAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var sparklines = [];
                            self.execInSelectionsForCell(self._sheet, function (sheet, row, column) {
                                sparklines.push(sheet.getSparkline(row, column));
                            });
                            if (sparklines !== null && sparklines.length > 1) {
                                self._sheet.groupSparkline(sparklines);
                            }
                        };
                        return GroupSparklineAction;
                    })(DesignerActionBase);


                    var commands_unGroupSparkline = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new UnGroupSparklineAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function unGroupSparkline(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "unGroupSparkline", commands_unGroupSparkline, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.unGroupSparkline = unGroupSparkline;

                    var UnGroupSparklineAction = (function (_super) {
                        __extends(UnGroupSparklineAction, _super);
                        function UnGroupSparklineAction(spread) {
                            _super.call(this, spread);
                        }

                        UnGroupSparklineAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForCell(self._sheet, function (sheet, row, column) {
                                sheet.ungroupSparkline(sheet.getSparkline(row, column).group());
                            });
                        };
                        return UnGroupSparklineAction;
                    })(DesignerActionBase);


                    var commands_clearSparkline = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearSparklineAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearSparkline(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearSparkline", commands_clearSparkline, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.clearSparkline = clearSparkline;

                    var ClearSparklineAction = (function (_super) {
                        __extends(ClearSparklineAction, _super);
                        function ClearSparklineAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearSparklineAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForCell(self._sheet, function (sheet, row, column) {
                                sheet.removeSparkline(row, column);
                            });
                        };
                        return ClearSparklineAction;
                    })(DesignerActionBase);


                    var commands_clearSparklineGroup = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearSparklineGroupAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearSparklineGroup(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearSparklineGroup", commands_clearSparklineGroup, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.clearSparklineGroup = clearSparklineGroup;

                    var ClearSparklineGroupAction = (function (_super) {
                        __extends(ClearSparklineGroupAction, _super);
                        function ClearSparklineGroupAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearSparklineGroupAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self.execInSelectionsForCell(self._sheet, function (sheet, row, column) {
                                var selectedSparkline = sheet.getSparkline(row, column);
                                var group = selectedSparkline ? selectedSparkline.group() : null;
                                if (group !== null && group.count() > 1) {
                                    var sparklines = group.remove(selectedSparkline);
                                    self._sheet.removeSparkline(selectedSparkline.row, selectedSparkline.column);
                                    while (sparklines.length > 0) {
                                        self._sheet.removeSparkline(sparklines[sparklines.length - 1].row, sparklines[sparklines.length - 1].column);
                                    }
                                }
                            });
                        };
                        return ClearSparklineGroupAction;
                    })(DesignerActionBase);

                    var TableActionBase = (function (_super) {
                        __extends(TableActionBase, _super);
                        function TableActionBase() {
                            _super.apply(this, arguments);
                        }

                        TableActionBase.prototype.getNewTableName = function () {
                            var suffix = 0;
                            for (var sheetIndex = 0; sheetIndex < this.spread().sheets.length; sheetIndex++) {
                                var tables = this.spread().sheets[sheetIndex].tables.all();
                                for (var tableIndex = 0; tableIndex < tables.length; tableIndex++) {
                                    var tableName = tables[tableIndex].name();
                                    if (tableName && tableName.length > 5 && tableName.substring(0, 5).toLowerCase() == 'table') {
                                        var temp = tableName.substring(5, tableName.length);
                                        if ($.isNumeric(temp) && Number(temp) >= suffix) {
                                            suffix = Number(temp);
                                        }
                                    }
                                }
                            }
                            return 'Table' + (suffix + 1).toString();
                        };

                        TableActionBase.prototype.clearMergeInRange = function (range) {
                            var sheet = this._sheet;
                            var col = range.col == -1 ? 0 : range.col;
                            var row = range.row == -1 ? 0 : range.row;
                            for (var i = 0; i < range.rowCount; i++) {
                                for (var j = 0; j < range.colCount; j++) {
                                    sheet.removeSpan(row + i, col + j);
                                }
                            }
                        };
                        return TableActionBase;
                    })(DesignerActionBase);


                    var commands_createDefaultTable = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new CreateDefaultTableAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function createDefaultTable(spread, range) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "createDefaultTable", commands_createDefaultTable, {
                            sheetName: sheetName,
                            range: range,
                            spread: spread
                        });
                    }

                    spreadActions.createDefaultTable = createDefaultTable;

                    var CreateDefaultTableAction = (function (_super) {
                        __extends(CreateDefaultTableAction, _super);
                        function CreateDefaultTableAction(spread) {
                            _super.call(this, spread);
                        }

                        CreateDefaultTableAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._range = options.range;
                            var row = self._range.row == -1 ? 0 : self._range.row;
                            var col = self._range.col == -1 ? 0 : self._range.col;
                            self.clearMergeInRange(self._range);
                            self._sheet.tables.add(self.getNewTableName(), row, col, self._range.rowCount, self._range.colCount);
                        };
                        return CreateDefaultTableAction;
                    })(TableActionBase);


                    var commands_formatAsTable = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new FormatAsTableAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function formatAsTable(spread, tableStyle) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "formatAsTable", commands_formatAsTable, {
                            sheetName: sheetName,
                            tableStyle: tableStyle,
                            spread: spread
                        });
                    }

                    spreadActions.formatAsTable = formatAsTable;

                    var FormatAsTableAction = (function (_super) {
                        __extends(FormatAsTableAction, _super);

                        function FormatAsTableAction(spread) {
                            _super.call(this, spread);
                        }

                        FormatAsTableAction.prototype.executeImp = function (context, options, isUndo) {
                            //var sm = (<any>this._sheet)._selectionModel;
                            // Should support only for one selection (Excel disabled when multi selections)
                            var self = this;
                            self._tableStyle = options.tableStyle;
                            var range = self._sheet.getSelections()[0];
                            if (!range) {
                                return;
                            }
                            var row = range.row == -1 ? 0 : range.row;
                            var col = range.col == -1 ? 0 : range.col;
                            var table = self._sheet.tables.find(self._sheet.getActiveRowIndex(), self._sheet.getActiveColumnIndex());
                            if (table instanceof Sheets.Tables.Table) {
                                table.style(self._tableStyle);
                            } else {
                                self.clearMergeInRange(range);
                                self._sheet.tables.add(self.getNewTableName(), row, col, range.rowCount, range.colCount, self._tableStyle);
                            }
                        };
                        return FormatAsTableAction;
                    })(TableActionBase);


                    var commands_setTableHeaderRow = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetTableHeaderRowAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setTableHeaderRow(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setTableHeaderRow", commands_setTableHeaderRow, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setTableHeaderRow = setTableHeaderRow;

                    var SetTableHeaderRowAction = (function (_super) {
                        __extends(SetTableHeaderRowAction, _super);
                        function SetTableHeaderRowAction(spread) {
                            _super.call(this, spread);
                        }

                        SetTableHeaderRowAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                activeTable.showHeader(self._value);
                            }
                        };
                        return SetTableHeaderRowAction;
                    })(DesignerActionBase);


                    var commands_setTableTotalRow = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetTableTotalRowAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setTableTotalRow(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setTableTotalRow", commands_setTableTotalRow, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setTableTotalRow = setTableTotalRow;

                    var SetTableTotalRowAction = (function (_super) {
                        __extends(SetTableTotalRowAction, _super);
                        function SetTableTotalRowAction(spread) {
                            _super.call(this, spread);
                        }

                        SetTableTotalRowAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                activeTable.showFooter(self._value);
                            }
                        };
                        return SetTableTotalRowAction;
                    })(DesignerActionBase);


                    var commands_setTableBandedRows = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetTableBandedRowsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setTableBandedRows(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setTableBandedRows", commands_setTableBandedRows, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setTableBandedRows = setTableBandedRows;

                    var SetTableBandedRowsAction = (function (_super) {
                        __extends(SetTableBandedRowsAction, _super);
                        function SetTableBandedRowsAction(spread) {
                            _super.call(this, spread);
                        }

                        SetTableBandedRowsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                activeTable.bandRows(self._value);
                            }
                        };
                        return SetTableBandedRowsAction;
                    })(DesignerActionBase);


                    var commands_setTableFirstColumn = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetTableFirstColumnAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setTableFirstColumn(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setTableFirstColumn", commands_setTableFirstColumn, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setTableFirstColumn = setTableFirstColumn;

                    var SetTableFirstColumnAction = (function (_super) {
                        __extends(SetTableFirstColumnAction, _super);
                        var self;

                        function SetTableFirstColumnAction(spread) {
                            _super.call(this, spread);
                        }

                        SetTableFirstColumnAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                activeTable.highlightFirstColumn(self._value);
                            }
                        };
                        return SetTableFirstColumnAction;
                    })(DesignerActionBase);


                    var commands_setTableLastColumn = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetTableLastColumnAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setTableLastColumn(spread, value) {
                        var action = new SetTableLastColumnAction(spread, value);
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setTableLastColumn", commands_setTableLastColumn, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setTableLastColumn = setTableLastColumn;

                    var SetTableLastColumnAction = (function (_super) {
                        __extends(SetTableLastColumnAction, _super);
                        function SetTableLastColumnAction(spread) {
                            _super.call(this, spread);
                        }

                        SetTableLastColumnAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                activeTable.highlightLastColumn(self._value);
                            }
                        };
                        return SetTableLastColumnAction;
                    })(DesignerActionBase);


                    var commands_setTableBandedColumns = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetTableBandedColumnsAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setTableBandedColumns(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setTableBandedColumns", commands_setTableBandedColumns, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setTableBandedColumns = setTableBandedColumns;

                    var SetTableBandedColumnsAction = (function (_super) {
                        __extends(SetTableBandedColumnsAction, _super);
                        function SetTableBandedColumnsAction(spread) {
                            _super.call(this, spread);
                        }

                        SetTableBandedColumnsAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                activeTable.bandColumns(self._value);
                            }
                        };
                        return SetTableBandedColumnsAction;
                    })(DesignerActionBase);


                    var commands_setTableFilterButton = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetTableFilterButtonAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setTableFilterButton(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setTableFilterButton", commands_setTableFilterButton, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setTableFilterButton = setTableFilterButton;

                    var SetTableFilterButtonAction = (function (_super) {
                        __extends(SetTableFilterButtonAction, _super);
                        function SetTableFilterButtonAction(spread) {
                            _super.call(this, spread);
                        }

                        SetTableFilterButtonAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                activeTable.filterButtonVisible(self._value);
                            }
                        };
                        return SetTableFilterButtonAction;
                    })(DesignerActionBase);


                    var commands_setTableName = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new SetTableNameAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function setTableName(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "setTableName", commands_setTableName, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.setTableName = setTableName;

                    var SetTableNameAction = (function (_super) {
                        __extends(SetTableNameAction, _super);
                        function SetTableNameAction(spread) {
                            _super.call(this, spread);
                        }

                        SetTableNameAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                activeTable.name(self._value);
                            }
                        };
                        return SetTableNameAction;
                    })(DesignerActionBase);


                    var commands_resizeTable = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ResizeTableAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function resizeTable(spread, value) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "resizeTable", commands_resizeTable, {
                            sheetName: sheetName,
                            value: value,
                            spread: spread
                        });
                    }

                    spreadActions.resizeTable = resizeTable;

                    var ResizeTableAction = (function (_super) {
                        __extends(ResizeTableAction, _super);
                        function ResizeTableAction(spread) {
                            _super.call(this, spread);
                        }

                        ResizeTableAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.value;
                            var sheet = self._sheet;
                            var activeTable = sheet.tables.find(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
                            if (activeTable instanceof Sheets.Tables.Table) {
                                sheet.tables.resize(activeTable, self._value);
                            }
                        };
                        return ResizeTableAction;
                    })(DesignerActionBase);


                    var commands_addRule = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new AddRuleAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function addRule(spread, rule) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "addRule", commands_addRule, {
                            sheetName: sheetName,
                            rule: rule,
                            spread: spread
                        });
                    }

                    spreadActions.addRule = addRule;

                    var AddRuleAction = (function (_super) {
                        __extends(AddRuleAction, _super);
                        function AddRuleAction(spread) {
                            _super.call(this, spread);
                        }

                        AddRuleAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._rule = options.rule;
                            var cfs = self._sheet.conditionalFormats;
                            cfs.addRule(self._rule);
                        };
                        return AddRuleAction;
                    })(DesignerActionBase);


                    var commands_clearRule = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new ClearRuleAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function clearRule(spread, isFromEntireSheet) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "clearRule", commands_clearRule, {
                            sheetName: sheetName,
                            isFromEntireSheet: isFromEntireSheet,
                            spread: spread
                        });
                    }

                    spreadActions.clearRule = clearRule;

                    var ClearRuleAction = (function (_super) {
                        __extends(ClearRuleAction, _super);

                        function ClearRuleAction(spread) {
                            _super.call(this, spread);
                        }

                        ClearRuleAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            self._value = options.isFromEntireSheet;
                            var ranges = self._sheet.getSelections();
                            if (self._value) {
                                self._sheet.conditionalFormats.clearRule();
                            } else {
                                for (var i = 0; i < ranges.length; i++) {
                                    self._sheet.conditionalFormats.removeRuleByRange(ranges[i].row, ranges[i].col, ranges[i].rowCount, ranges[i].colCount);
                                }
                            }
                        };
                        return ClearRuleAction;
                    })(DesignerActionBase);


                    var commands_insertComment = {
                        canUndo: true,
                        execute: function (context, options, isUndo) {
                            var cmd = new InsertCommentAction(options.spread);
                            return cmd.execute(context, options, isUndo);
                        }
                    };

                    function insertComment(spread) {
                        var sheetName = spread.getActiveSheet().name();
                        runCommand(spread, "insertComment", commands_insertComment, {
                            sheetName: sheetName,
                            spread: spread
                        });
                    }

                    spreadActions.insertComment = insertComment;

                    var InsertCommentAction = (function (_super) {
                        __extends(InsertCommentAction, _super);
                        function InsertCommentAction(spread) {
                            _super.call(this, spread);
                        }

                        InsertCommentAction.prototype.executeImp = function (context, options, isUndo) {
                            var self = this;
                            var sheet = self._sheet;
                            if (!sheet) {
                                return;
                            }
                            var row = sheet.getActiveRowIndex();
                            var col = sheet.getActiveColumnIndex();
                            sheet.comments.add(row, col, '');
                        };
                        return InsertCommentAction;
                    })(DesignerActionBase);


                    function deleteComment(spread) {
                        var sheet = spread.getActiveSheet();
                        var selections = sheet.getSelections();
                        var length = selections.length;
                        if (length <= 0) {
                            return;
                        }
                        for (var i = 0; i < length; i++) {
                            var sel = selections[i];
                            for (var r = sel.row; r < sel.row + sel.rowCount; r++) {
                                for (var c = sel.col; c < sel.col + sel.colCount; c++) {
                                    var comment = sheet.comments.get(r, c);
                                    if (comment) {
                                        spread.commandManager().execute({
                                            cmd: "deleteComment",
                                            sheetName: sheet.name(),
                                            comment: comment
                                        });
                                    }
                                }
                            }
                        }
                    }

                    spreadActions.deleteComment = deleteComment;

                    function setCommentProperty(spread, value) {
                        var activeSheet = spread.getActiveSheet();
                        var comment = activeSheet.comments.get(activeSheet.getActiveRowIndex(), activeSheet.getActiveColumnIndex());
                        if (comment) {
                            spread.commandManager().execute({
                                cmd: "changeComment",
                                sheetName: activeSheet.name(),
                                comment: comment,
                                oldValue: value.oldValue,
                                newValue: value.newValue,
                                propertyName: value.propertyName
                            })
                        }
                    }

                    spreadActions.setCommentProperty = setCommentProperty;

                    //#region Slicer Actions
                    //Delete Slicer
                    function deleteSlicer(spread) {
                        var slicerNameArray = new Array();
                        var slicer;
                        var sheet = spread.getActiveSheet();
                        spread.suspendPaint();
                        var selectedSlicers = designer.util.getSelectedSlicers(sheet);
                        if (!selectedSlicers || selectedSlicers.length === 0) {
                            return;
                        } else {
                            for (var item in selectedSlicers) {
                                slicer = selectedSlicers[item];
                                slicerNameArray.push(slicer.name());
                            }
                        }
                        if (slicerNameArray.length > 0) {
                            spread.commandManager().execute({
                                cmd: "deleteFloatingObjects",
                                sheetName: sheet.name(),
                                floatingObjects: slicerNameArray
                            });
                        }
                        spread.resumePaint();
                    }

                    spreadActions.deleteSlicer = deleteSlicer;

                    //Slicer cut action
                    function floatingObjectCut(spread) {
                        // TODO: slicer cut support
                        //app.setClipboardText(spread.getActiveSheet()._doFloatingObjectCut(true));
                        spread.commandManager().execute({
                            cmd: "cutFloatingObjects",
                            sheetName: spread.getActiveSheet().name(),
                        });
                    }

                    spreadActions.floatingObjectCut = floatingObjectCut;

                    //Slicer copy action
                    function floatingObjectCopy(spread) {
                        // TODO: slicer copy support
                        //app.setClipboardText(spread.getActiveSheet()._doFloatingObjectCopy(true));
                        spread.commandManager().execute({
                            cmd: "copyFloatingObjects",
                            sheetName: spread.getActiveSheet().name(),
                        });
                    }

                    spreadActions.floatingObjectCopy = floatingObjectCopy;

                    function sortSlicerItems(spread, value) {
                        var sheet = spread.getActiveSheet();
                        var selectedSlicers = designer.util.getSelectedSlicers(sheet);
                        if (!selectedSlicers || selectedSlicers.length === 0) {
                            return;
                        } else {
                            for (var item in selectedSlicers) {
                                selectedSlicers[item].sortState(value);
                            }
                        }
                    }

                    spreadActions.sortSlicerItems = sortSlicerItems;
                })(designer.spreadActions || (designer.spreadActions = {}));
                var spreadActions = designer.spreadActions;
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})
(GC || (GC = {}));
