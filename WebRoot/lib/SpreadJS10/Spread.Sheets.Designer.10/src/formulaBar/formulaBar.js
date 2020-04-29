var GC;
(function (GC) {
    (function (Spread) {
        (function (Sheets) {
            (function (designer) {
                (function (formulaBar) {
                    'use strict';
                    var _oldValue;
                    var _oldFormula;
                    var _oldCellPositon;
                    var _justShowing;
                    var insertFunctionDialog;
                    var isShiftKey = false;
                    var hasError = false;
                    var ftb;
                    var ExpressionType = GC.Spread.CalcEngine.ExpressionType;

                    //#region init
                    function initFormulaBar(init) {
                        if (init) {
                            ftb = new Sheets.FormulaTextBox.FormulaTextBox($("#formulaBarText").get(0));
                        }
                        ftb.workbook(designer.wrapper.spread);
                        setButtonStatus(undefined, $(".ok-btn"), false);
                        setButtonStatus(undefined, $(".cancel-btn"), false);

                        addEvents(init);

                        //Following code is used when spread be recreated.
                        var activeSheet = designer.wrapper.spread.getActiveSheet();
                        var col = activeSheet.getActiveColumnIndex();
                        var row = activeSheet.getActiveRowIndex();
                        designer.util.triggerSheetEvent(activeSheet, Sheets.Events.EnterCell, {
                            sheet: activeSheet,
                            sheetName: activeSheet.name(),
                            row: row,
                            col: col
                        });
                        designer.util.triggerSheetEvent(activeSheet, Sheets.Events.SelectionChanged, null);
                    }

                    formulaBar.initFormulaBar = initFormulaBar;

                    function resetFormulaBar() {
                        if (ftb) {
                            ftb.destroy();
                            ftb = null;
                        }
                    }
                    formulaBar.resetFormulaBar = resetFormulaBar;

                    function addEvents(init) {
                        var spread = designer.wrapper.spread,
                            spreadElement = designer.wrapper.spreadElement;

                        spread.bind(Sheets.Events.InvalidOperation, function (evt, args) {
                            if (args.invalidType === 0 /* SetFormula */ || args.invalidType === 4 /* ChangePartOfArrayFormula */) {
                                designer.MessageBox.show(args.message, designer.res.title, 2 /* warning */, 0 /* ok */, function () {
                                    $(".fill-content").focus();
                                });
                                hasError = true;
                            }
                        });
                        
                        function onSpreadEnterCell (evt, data) {
                            var activeSheet = designer.wrapper.spread.getActiveSheet();
                            var col = activeSheet.getActiveColumnIndex();
                            var row = activeSheet.getActiveRowIndex();
                            var activeCell = activeSheet.getCell(row, col);
                            setButtonStatus(true, $(".functions-btn"));

                            _oldValue = activeCell.value();
                            _oldFormula = activeCell.formula();
                            _oldCellPositon = {
                                sheetIndex: designer.wrapper.spread.getActiveSheetIndex(),
                                row: activeCell.row,
                                col: activeCell.col
                            };
                            _justShowing = true;
                        };

                        spread.bind(Sheets.Events.EnterCell, onSpreadEnterCell);
                        spreadElement.bind(Sheets.Events.EnterCell, onSpreadEnterCell);

                        function onSpreadSelectionChanging (evt, data) {
                            //keep syn with namedRangeSelector.
                            var activeSheet = designer.wrapper.spread.getActiveSheet();
                            var selection = activeSheet.getSelections()[activeSheet.getSelections().length - 1];
                            if (selection !== null && selection !== undefined) {
                                var selectionInfo = GetSelectedRangeString(selection);
                                var namedRangeSelector = $("[name=namedRangeSelector]");
                                namedRangeSelector.val(selectionInfo);
                            }
                        };

                        spread.bind(Sheets.Events.SelectionChanging, onSpreadSelectionChanging);
                        spreadElement.bind(Sheets.Events.SelectionChanging, onSpreadSelectionChanging);

                        function onSpreadSelectionChanged (evt, data) {
                            var namedRangeSelector = $("[name=namedRangeSelector]");
                            var activeSheet = designer.wrapper.spread.getActiveSheet();
                            var selection = activeSheet.getSelections()[activeSheet.getSelections().length - 1];
                            if (selection !== null && selection !== undefined) {
                                var selectionInfo;
                                if (!isShiftKey) {
                                    selectionInfo = GetCellPositionString(designer.wrapper.spread, designer.wrapper.spread.getActiveSheet().getActiveRowIndex() + 1, designer.wrapper.spread.getActiveSheet().getActiveColumnIndex() + 1, selection);
                                } else {
                                    selectionInfo = GetSelectedRangeString(selection);
                                }
                                namedRangeSelector.val(selectionInfo);
                            }
                        };

                        spread.bind(Sheets.Events.SelectionChanged, onSpreadSelectionChanged);
                        spreadElement.bind(Sheets.Events.SelectionChanged, onSpreadSelectionChanged);

                        designer.wrapper.spread.bind(Sheets.Events.ActiveSheetChanged, function (evt, data) {
                            var sheet = designer.wrapper.spread.getActiveSheet();
                            designer.util.triggerSheetEvent(sheet, Sheets.Events.SelectionChanging, undefined);
                            designer.util.triggerSheetEvent(sheet, Sheets.Events.SelectionChanged, undefined);
                            designer.util.triggerSheetEvent(sheet, Sheets.Events.EnterCell, undefined);
                        });
                        if (init) {
                            designer.wrapper.spreadElement.bind("FileOpened", function (evt, data) {
                                var sheet = designer.wrapper.spread.getActiveSheet();
                                designer.util.triggerSheetEvent(sheet, Sheets.Events.SelectionChanging, undefined);
                                designer.util.triggerSheetEvent(sheet, Sheets.Events.SelectionChanged, undefined);
                                designer.util.triggerSheetEvent(sheet, Sheets.Events.EnterCell, undefined);

                                ftb.text(getEditText(sheet, sheet.getActiveRowIndex(), sheet.getActiveColumnIndex()));
                            });
                            $(document).bind("keydown", function (evt) {
                                if (evt.shiftKey) {
                                    isShiftKey = true;
                                }
                            });
                            $(document).bind("keyup", function (evt) {
                                if (!evt.shiftKey) {
                                    isShiftKey = false;
                                    var namedRangeSelector = $("[name=namedRangeSelector]");
                                    var activeSheet = designer.wrapper.spread.getActiveSheet();
                                    if (activeSheet) {
                                        var selectionInfo;
                                        var selection = activeSheet.getSelections()[activeSheet.getSelections().length - 1];
                                        selectionInfo = GetCellPositionString(designer.wrapper.spread, designer.wrapper.spread.getActiveSheet().getActiveRowIndex() + 1, designer.wrapper.spread.getActiveSheet().getActiveColumnIndex() + 1, selection);
                                        namedRangeSelector.val(selectionInfo);
                                    }
                                }
                            });

                            $(".fill-content").bind("keyup", function (e) {
                                var fillContent = $(".fill-content");
                                var fillText = fillContent.text();
                                if (fillText !== "" && fillText.substring(0, 1) !== "=") {
                                    setButtonStatus(false, $(".functions-btn"));
                                } else {
                                    setButtonStatus(true, $(".functions-btn"));
                                }
                            });

                            $(".fill-content").focus(function (e) {
                                _justShowing = false;
                                setButtonStatus(undefined, $(".ok-btn"), true);
                                setButtonStatus(undefined, $(".cancel-btn"), true);
                            });

                            $(".functions-btn").click(function () {
                                if (!insertFunctionDialog) {
                                    insertFunctionDialog = new designer.InsertFunctionDialog();
                                }
                                insertFunctionDialog.open();
                            });
                            $(".cancel-btn").click(function () {
                                CancelFormula();
                                designer.wrapper.setFocusToSpread();
                            });
                            $(".ok-btn").click(function () {
                                designer.wrapper.setFocusToSpread();
                                CommitFormula(null);
                            });

                            //#region Resize splitter
                            var dragStartX;
                            var isResizing = false;
                            $(".splitter").mousedown(function (evt) {
                                dragStartX = evt.pageX;
                                isResizing = true;
                            });
                            $(document).mousemove(function (evt) {
                                if (isResizing) {
                                    var widthChanged = evt.pageX - dragStartX;
                                    var leftControl = $(".named-range-selector");
                                    var leftContainer = $(".union-container");
                                    var rightControl = $("#formulaBarText");
                                    if (leftControl.width() + widthChanged < 0) {
                                        widthChanged = leftControl.width();
                                    }
                                    if (rightControl.width() - widthChanged < 0) {
                                        widthChanged = rightControl.width();
                                    }
                                    leftControl.width(leftControl.width() + widthChanged);

                                    //rightControl.width(rightControl.width() - widthChanged);
                                    rightControl.css("left", parseFloat(rightControl.css("left")) + widthChanged);
                                    leftContainer.width(leftContainer.width() + widthChanged);

                                    dragStartX = evt.pageX;
                                    adjustFormulaBarTextWidth();
                                }
                            });
                            $(document).mouseup(function (evt) {
                                isResizing = false;
                            });

                            //#endregion
                            $(window).resize(adjustFormulaBarTextWidth);
                            $(window).resize();
                        }
                    }

                    formulaBar.addEvents = addEvents;

                    /*
                     use to set formulabar text' width since the style 'width:100%' not work as expected in table:
                     the total width of colorful text made by span can make the div wider than 100% of the expected width
                     It's fine when the div not in table then don't need do this

                     Set css as following seems also works :
                     span.gcsj-func-color-text {
                     display:inline-block;
                     }
                     */
                    function adjustFormulaBarTextWidth() {
                        var $formulaBar = $(".formulaBar"), $target = $("#formulaBarText"), $tds = $formulaBar.find("td"), fullWidth = $formulaBar.width(), actualWidth = 0, itemWidths = [];

                        $tds.each(function (n, e) {
                            var width = $(e).outerWidth();
                            actualWidth += width;
                            itemWidths.push(width);
                        });

                        var diff = fullWidth - actualWidth - 10;

                        $target.css({width: diff + itemWidths[itemWidths.length - 1] - 10}); // 10 = formulaBarText parents' total padding + borders
                    }

                    //#endregion
                    //#region Hand select range change
                    function GetSelectedRangeString(range) {
                        //得到选择区域的行列数描述字符串
                        var selectionInfo = "";
                        var rowCount = range.rowCount;
                        var columnCount = range.colCount;
                        var startRow = range.row + 1;
                        var startColumn = range.col + 1;
                        if (rowCount == 1 && columnCount == 1) {
                            selectionInfo = GetCellPositionString(designer.wrapper.spread, startRow, startColumn);
                        } else {
                            if (rowCount < 0 && columnCount > 0) {
                                selectionInfo = columnCount + "C";
                            } else if (columnCount < 0 && rowCount > 0) {
                                selectionInfo = rowCount + "R";
                            } else if (rowCount < 0 && columnCount < 0) {
                                selectionInfo = designer.wrapper.spread.getActiveSheet().getRowCount() + "R x " + designer.wrapper.spread.getActiveSheet().getColumnCount() + "C";
                            } else {
                                selectionInfo = rowCount + "R x " + columnCount + "C";
                            }
                        }
                        return selectionInfo;
                    }

                    formulaBar.GetSelectedRangeString = GetSelectedRangeString;
                    function GetCellPositionString(spread, row, column, selection) {
                        if (row < 1 || column < 1) {
                            return null;
                        } else {
                            var letters = "";
                            var activeSheetName = spread.getActiveSheet().name();

                            if (selection) {
                                var isSpanCell;
                                var spans = spread.getActiveSheet().getSpans();
                                if (spans !== null) {
                                    for (var i = 0; i < spans.length; i++) {
                                        if (spans[i].row === selection.row && spans[i].col === selection.col && spans[i].rowCount === selection.rowCount && spans[i].colCount === selection.colCount) {
                                            isSpanCell = true;
                                        }
                                    }
                                }
                                var names;
                                names = spread.getCustomNames();
                                var range;
                                if (names !== undefined) {
                                    for (var j = 0; j < names.length; j++) {
                                        var expression = names[j].getExpression();
                                        if (expression.type === ExpressionType.reference && activeSheetName === expression.source.getName()) {
                                            range = expression.getRange(0, 0);
                                            if (range !== null && range !== undefined) {
                                                if ((range.row === selection.row && range.column === selection.col && range.row + range.rowCount === selection.row + selection.rowCount && range.column + range.columnCount === selection.col + selection.colCount) || (range.row === selection.row && range.column === selection.col && isSpanCell)) {
                                                    letters = names[j].getName();
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (letters === "") {
                                    names = spread.getActiveSheet().getCustomNames();
                                    if (names !== undefined) {
                                        for (var j = 0; j < names.length; j++) {
                                            var expression = names[j].getExpression();
                                            if (expression.type === ExpressionType.reference) {
                                                range = expression.getRange(0, 0);
                                                if ((range.row === selection.row && range.column === selection.col && range.row + range.rowCount === selection.row + selection.rowCount && range.column + range.columnCount === selection.col + selection.colCount) || (range.row === selection.row && range.column === selection.col && isSpanCell)) {
                                                    letters = names[j].getName();
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (letters === "") {
                                switch (spread.options.referenceStyle) {
                                    case 0:
                                        while (column > 0) {
                                            var num = column % 26;
                                            if (num == 0) {
                                                letters = "Z" + letters;
                                                column--;
                                            } else {
                                                letters = String.fromCharCode('A'.charCodeAt(0) + num - 1) + letters;
                                            }
                                            column = parseInt((column / 26).toString());
                                        }
                                        letters += row.toString();
                                        break;
                                    case 1:
                                        //ReferenceStyle.R1C1
                                        letters = "R" + row.toString() + "C" + column.toString();
                                        break;
                                    default:
                                        break;
                                }
                            }
                            return letters;
                        }
                    }

                    formulaBar.GetCellPositionString = GetCellPositionString;

                    //#endregion
                    //#region Hand formula
                    function CommitFormula(cellPosition) {
                        var activeSheet = designer.wrapper.spread.getActiveSheet();
                        var col = activeSheet.getActiveColumnIndex();
                        var row = activeSheet.getActiveRowIndex();
                        var activeCell = activeSheet.getCell(row, col);

                        var cell;
                        var spread = designer.wrapper.spread; 
                        if (cellPosition != undefined) {
                            cell = spread.sheets[cellPosition.sheetIndex].getCell(cellPosition.row, cellPosition.col);
                        } else if (activeCell != null) {
                            cell = activeCell;
                        } else {
                            return false;
                        }

                        if (!_justShowing) {
                            var fillContent = $(".fill-content");
                            var s = fillContent.text().toString();
                            if (s !== "") {
                                if (s.substring(0, 1) == '=') {
                                    var cellEditCmd = { cmd: 'editCell', sheetName: cell.sheet.name(), row: cell.row, col: cell.col, newValue: s, autoFormat: true };
                                    spread.commandManager().execute(cellEditCmd);

                                    if (hasError === true) {
                                        hasError = false;
                                        return false;
                                    }
                                }
                            } else {
                                cell.value("");
                                var cellEditCmd = { cmd: 'editCell', sheetName: cell.sheet.name(), row: cell.row, col: cell.col, newValue: "", autoFormat: true };
                                spread.commandManager().execute(cellEditCmd);
                            }
                        }
                        setButtonStatus(true, $(".functions-btn"));
                        setButtonStatus(undefined, $(".ok-btn"), false);
                        setButtonStatus(undefined, $(".cancel-btn"), false);

                        _oldValue = cell.value();
                        _oldFormula = cell.formula();

                        _justShowing = true;
                        return true;
                    }

                    formulaBar.CommitFormula = CommitFormula;
                    function CancelFormula() {
                        var fillContent = $(".fill-content");

                        var activeSheet = designer.wrapper.spread.getActiveSheet();
                        var col = activeSheet.getActiveColumnIndex();
                        var row = activeSheet.getActiveRowIndex();
                        var activeCell = activeSheet.getCell(row, col);

                        if (activeCell != null) {
                            if (_justShowing) {
                                return;
                            }
                        } else {
                            return;
                        }
                        setButtonStatus(true, $(".functions-btn"));
                        setButtonStatus(undefined, $(".ok-btn"), false);
                        setButtonStatus(undefined, $(".cancel-btn"), false);

                        activeCell.value(_oldValue);
                        activeCell.formula(_oldFormula);
                        _justShowing = true;
                        designer.wrapper.spread.focus(true);
                        fillContent.text(GetTextOfFormulaTextBox(activeCell, _oldFormula));
                    }

                    formulaBar.CancelFormula = CancelFormula;

                    //#endregion
                    function GetTextOfFormulaTextBox(cell, formula) {
                        // 根据value与formula的值得到formulatextbox显示的字符串
                        var text = "";

                        //wedywang fixed bug 1718 at 2012/3/13
                        //-----------------
                        if (formula === "" || formula === null) {
                            var value = cell.value();
                            if (value != null)
                                if (value instanceof Date) {
                                    text = value.toLocaleString();
                                } else {
                                    text = value.toString();
                                }
                            else
                                text = cell.text();
                        } else {
                            if (formula.substring(0, 1) != '=') {
                                text = "=" + formula;
                            } else {
                                text = formula;
                            }
                        }
                        if (text == null) {
                            return "";
                        }
                        return text;
                    }

                    formulaBar.GetTextOfFormulaTextBox = GetTextOfFormulaTextBox;

                    function setButtonStatus(isEnabled, button, isVisible) {
                        if (isEnabled !== undefined) {
                            if (isEnabled) {
                                button.prop('disabled', false);
                                button.fadeTo('faster', 1);
                            } else {
                                button.prop('disabled', true);
                                button.fadeTo('faster', 0.2);
                            }
                        } else {
                            if (isVisible) {
                                button.fadeTo('faster', 1);
                            } else {
                                button.fadeTo('faster', 0);
                            }
                        }
                    }

                    formulaBar.setButtonStatus = setButtonStatus;

                    function getEditText(sheet, row, col) {
                        var text = "";
                        if (sheet) {
                            text = sheet.getFormula(row, col);
                            if (text) {
                                text = "=" + text;
                            } else {
                                text = sheet.getText(row, col);
                                var canUserEditFormula = sheet.parent ? sheet.parent.options.allowUserEditFormula : true;
                                if (text[0] === "=" && canUserEditFormula) {
                                    text = "'" + text;
                                }
                            }
                        }
                        return text;
                    }
                })(designer.formulaBar || (designer.formulaBar = {}));
                var formulaBar = designer.formulaBar;
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));