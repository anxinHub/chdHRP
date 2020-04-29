/*jshint -W030 */   // Expected an assignment or function call and instead saw an expression (a && a.fun1())
/*jshint -W004 */   // {a} is already defined (can use let instead of var in es6)

var spreadNS = GC.Spread.Sheets;
var DataValidation = spreadNS.DataValidation;
var ConditionalFormatting = spreadNS.ConditionalFormatting;
var ComparisonOperators = ConditionalFormatting.ComparisonOperators;
var Calc = GC.Spread.CalcEngine;
var ExpressionType = Calc.ExpressionType;
var SheetsCalc = spreadNS.CalcEngine;
var Sparklines = spreadNS.Sparklines;
var isSafari = (function () {
    var tem, M = navigator.userAgent.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
    if (!/trident/i.test(M[1]) && M[1] !== 'Chrome') {
        M = M[2] ? [M[1], M[2]] : [navigator.appName, navigator.appVersion, '-?'];
        if ((tem = navigator.userAgent.match(/version\/(\d+)/i)) != null) M.splice(1, 1, tem[1]);
        return M[0].toLowerCase() === "safari";
    }
    return false;
})();
var isIE = navigator.userAgent.toLowerCase().indexOf('compatible') < 0 && /(trident)(?:.*? rv ([\w.]+)|)/.exec(navigator.userAgent.toLowerCase()) !== null;
var DOWNLOAD_DIALOG_WIDTH = 300;

var spread, excelIO;
var tableIndex = 1, pictureIndex = 1;
var fbx, isShiftKey = false;
var resourceMap = {},
    conditionalFormatTexts = {};

function toggleState() {
    var $element = $(this),
        $parent = $element.parent(),
        $content = $parent.siblings(".insp-group-content"),
        $target = $parent.find("span.group-state"),
        collapsed = $target.hasClass("fa-caret-right");

    if (collapsed) {
        $target.removeClass("fa-caret-right").addClass("fa-caret-down");
        $content.slideToggle("fast");
    } else {
        $target.addClass("fa-caret-right").removeClass("fa-caret-down");
        $content.slideToggle("fast");
    }
}

function updateMergeButtonsState() {
    var sheet = spread.getActiveSheet();
    var sels = sheet.getSelections(),
        mergable = false,
        unmergable = false;

    sels.forEach(function (range) {
        var ranges = sheet.getSpans(range),
            spanCount = ranges.length;

        if (!mergable) {
            if (spanCount > 1 || (spanCount === 0 && (range.rowCount > 1 || range.colCount > 1))) {
                mergable = true;
            } else if (spanCount === 1) {
                var range2 = ranges[0];
                if (range2.row !== range.row || range2.col !== range.col ||
                    range2.rowCount !== range2.rowCount || range2.colCount !== range.colCount) {
                    mergable = true;
                }
            }
        }
        if (!unmergable) {
            unmergable = spanCount > 0;
        }
    });

    $("#mergeCells").attr("disabled", mergable ? null : "disabled");
    $("#unmergeCells").attr("disabled", unmergable ? null : "disabled");
}

function updateCellStyleState(sheet, row, column) {
    var style = sheet.getActualStyle(row, column);

    if (style) {
        var sfont = style.font;

        // Font
        var font
        if (sfont) {
            font = parseFont(sfont);

            setFontStyleButtonActive("bold", ["bold", "bolder", "700", "800", "900"].indexOf(font.fontWeight) !== -1);
            setFontStyleButtonActive("italic", font.fontStyle !== 'normal');
            setDropDownText($("#cellTab div.insp-dropdown-list[data-name='fontFamily']"), font.fontFamily.replace(/'/g, ""));
            setDropDownText($("#cellTab div.insp-dropdown-list[data-name='fontSize']"), parseFloat(font.fontSize));
        }

        var underline = spreadNS.TextDecorationType.underline,
            linethrough = spreadNS.TextDecorationType.lineThrough,
            overline = spreadNS.TextDecorationType.overline,
            textDecoration = style.textDecoration;
        setFontStyleButtonActive("underline", textDecoration && ((textDecoration & underline) === underline));
        setFontStyleButtonActive("strikethrough", textDecoration && ((textDecoration & linethrough) === linethrough));
        setFontStyleButtonActive("overline", textDecoration && ((textDecoration & overline) === overline));

        setColorValue("foreColor", style.foreColor || "#000");
        setColorValue("backColor", style.backColor || "#fff");

        // Alignment
        setRadioButtonActive("hAlign", style.hAlign);   // general (3, auto detect) without setting button just like Excel
        setRadioButtonActive("vAlign", style.vAlign);
        setCheckValue("wrapText", style.wordWrap);

        //cell padding
        var cellPadding = style.cellPadding;
        if (cellPadding) {
            setTextValue("cellPadding", cellPadding);
        } else {
            setTextValue("cellPadding", "");
        }
        //watermark
        var watermark = style.watermark;
        if (watermark) {
            setTextValue("watermark", watermark);
        } else {
            setTextValue("watermark", "");
        }
        //label options
        var labelOptions = style.labelOptions;
        if (labelOptions) {
            var lFont = labelOptions.font;
            if (lFont) {
                font = parseFont(lFont);
                setFontStyleButtonActive("labelBold", ["bold", "bolder", "700", "800", "900"].indexOf(font.fontWeight) !== -1);
                setFontStyleButtonActive("labelItalic", font.fontStyle !== 'normal');
                setDropDownText($("#cellTab div.insp-dropdown-list[data-name='labelFontFamily']"), font.fontFamily.replace(/'/g, ""));
                setDropDownText($("#cellTab div.insp-dropdown-list[data-name='labelFontSize']"), parseFloat(font.fontSize));
            } else {
                setFontStyleButtonActive("labelBold", ["bold", "bolder", "700", "800", "900"].indexOf(font.fontWeight) !== -1);
                setFontStyleButtonActive("labelItalic", font.fontStyle !== 'normal');
                setDropDownText($("#cellTab div.insp-dropdown-list[data-name='labelFontFamily']"), font.fontFamily.replace(/'/g, ""));
                setDropDownText($("#cellTab div.insp-dropdown-list[data-name='labelFontSize']"), parseFloat(font.fontSize));
            }
            setColorValue("labelForeColor", labelOptions.foreColor || "#000");
            setTextValue("labelMargin", labelOptions.margin || "");
            setDropDownValueByIndex($("#cellLabelVisibility"), labelOptions.visibility === undefined ? 2 : labelOptions.visibility);
            setDropDownValueByIndex($("#cellLabelAlignment"), labelOptions.alignment || 0);
        }
    }
}

function setFontStyleButtonActive(name, active) {
    var $target = $("div.group-container>span[data-name='" + name + "']");

    if (active) {
        $target.addClass("active");
    } else {
        $target.removeClass("active");
    }
}

function setRadioButtonActive(name, index) {
    var $items = $("div.insp-radio-button-group[data-name='" + name + "'] div>span");

    $items.removeClass("active");
    $($items[index]).addClass("active");
}

function parseFont(font) {
    var fontFamily = null,
        fontSize = null,
        fontStyle = "normal",
        fontWeight = "normal",
        fontVariant = "normal",
        lineHeight = "normal";

    var elements = font.split(/\s+/);
    var element;
    while ((element = elements.shift())) {
        switch (element) {
            case "normal":
                break;

            case "italic":
            case "oblique":
                fontStyle = element;
                break;

            case "small-caps":
                fontVariant = element;
                break;

            case "bold":
            case "bolder":
            case "lighter":
            case "100":
            case "200":
            case "300":
            case "400":
            case "500":
            case "600":
            case "700":
            case "800":
            case "900":
                fontWeight = element;
                break;

            default:
                if (!fontSize) {
                    var parts = element.split("/");
                    fontSize = parts[0];
                    if (fontSize.indexOf("px") !== -1) {
                        fontSize = px2pt(parseFloat(fontSize)) + 'pt';
                    }
                    if (parts.length > 1) {
                        lineHeight = parts[1];
                        if (lineHeight.indexOf("px") !== -1) {
                            lineHeight = px2pt(parseFloat(lineHeight)) + 'pt';
                        }
                    }
                    break;
                }

                fontFamily = element;
                if (elements.length)
                    fontFamily += " " + elements.join(" ");

                return {
                    "fontStyle": fontStyle,
                    "fontVariant": fontVariant,
                    "fontWeight": fontWeight,
                    "fontSize": fontSize,
                    "lineHeight": lineHeight,
                    "fontFamily": fontFamily
                };
        }
    }

    return {
        "fontStyle": fontStyle,
        "fontVariant": fontVariant,
        "fontWeight": fontWeight,
        "fontSize": fontSize,
        "lineHeight": lineHeight,
        "fontFamily": fontFamily
    };
}

var tempSpan = $("<span></span>");
function px2pt(pxValue) {
    tempSpan.css({
        "font-size": "96pt",
        "display": "none"
    });
    tempSpan.appendTo($(document.body));
    var tempPx = tempSpan.css("font-size");
    if (tempPx.indexOf("px") !== -1) {
        var tempPxValue = parseFloat(tempPx);
        return Math.round(pxValue * 96 / tempPxValue);
    }
    else {  // when browser have not convert pt to px, use 96 DPI.
        return Math.round(pxValue * 72 / 96);
    }
}

function processRadioButtonClicked(key, $item, $group) {
    var name = $item.data("name");

    // only need process when click on radio button or relate label like text
    if ($item.hasClass("radiobutton") || $item.hasClass("text")) {
        $group.find("div.radiobutton").removeClass("checked");
        $group.find("div.radiobutton[data-name='" + name + "']").addClass("checked");

        switch (key) {
            case "referenceStyle":
                setReferenceStyle(name);
                break;
            case "slicerMoveAndSize":
                setSlicerSetting("moveSize", name);
                break;
            case "pictureMoveAndSize":
                var picture = _activePicture;
                if (name === "picture-move-size") {
                    picture.dynamicMove(true);
                    picture.dynamicSize(true);
                }
                if (name === "picture-move-nosize") {
                    picture.dynamicMove(true);
                    picture.dynamicSize(false);
                }
                if (name === "picture-nomove-size") {
                    picture.dynamicMove(false);
                    picture.dynamicSize(false);
                }
                break;
        }
    }
}

function setReferenceStyle(name) {
    var referenceStyle, columnHeaderAutoText;

    if (name === "a1style") {
        referenceStyle = spreadNS.ReferenceStyle.a1;
        columnHeaderAutoText = spreadNS.HeaderAutoText.letters;
    } else {
        referenceStyle = spreadNS.ReferenceStyle.r1c1;
        columnHeaderAutoText = spreadNS.HeaderAutoText.numbers;
    }

    spread.options.referenceStyle = referenceStyle;
    spread.sheets.forEach(function (sheet) {
        sheet.options.colHeaderAutoText = columnHeaderAutoText;
    });
    updatePositionBox(spread.getActiveSheet());
}

function checkedChanged() {
    var $element = $(this),
        name = $element.data("name");

    if ($element.hasClass("disabled")) {
        return;
    }

    // radio buttons need special process
    switch (name) {
        case "referenceStyle":
        case "slicerMoveAndSize":
        case "pictureMoveAndSize":
            processRadioButtonClicked(name, $(event.target), $element);
            return;
    }


    var $target = $("div.button", $element),
        value = !$target.hasClass("checked");

    var sheet = spread.getActiveSheet();

    $target.toggleClass("checked");

    spread.suspendPaint();

    var options = spread.options;

    switch (name) {

        case  "allowCopyPasteExcelStyle":
            options.allowCopyPasteExcelStyle = value;
            break;

        case "allowExtendPasteRange":
            options.allowExtendPasteRange = value;
            break;

        case "referenceStyle":
            options.referenceStyle = (value ? spreadNS.ReferenceStyle.r1c1 : spreadNS.ReferenceStyle.a1);
            break;

        case "cutCopyIndicatorVisible":
            options.cutCopyIndicatorVisible = value;
            break;

        case "showVerticalScrollbar":
            options.showVerticalScrollbar = value;
            break;

        case "showHorizontalScrollbar":
            options.showHorizontalScrollbar = value;
            break;

        case "scrollIgnoreHidden":
            options.scrollIgnoreHidden = value;
            break;

        case "scrollbarMaxAlign":
            options.scrollbarMaxAlign = value;
            break;

        case "scrollbarShowMax":
            options.scrollbarShowMax = value;
            break;

        case "tabStripVisible":
            options.tabStripVisible = value;
            break;

        case "newTabVisible":
            options.newTabVisible = value;
            break;

        case "tabEditable":
            options.tabEditable = value;
            break;

        case "showTabNavigation":
            options.tabNavigationVisible = value;
            break;

        case "showDragDropTip":
            options.showDragDropTip = value;
            break;

        case "showDragFillTip":
            options.showDragFillTip = value;
            break;

        case "sheetVisible":
            var sheetIndex = $target.data("sheetIndex"),
                sheetName = $target.data("sheetName"),
                selectedSheet = spread.sheets[sheetIndex];

            // be sure related sheet not changed (such add / remove sheet, rename sheet)
            if (selectedSheet && selectedSheet.name() === sheetName) {
                selectedSheet.visible(value);
            } else {
                console.log("selected sheet' info was changed, please select the sheet and set visible again.");
            }
            break;

        case "allowUserDragDrop":
            spread.options.allowUserDragDrop = value;
            break;

        case "allowUserDragFill":
            spread.options.allowUserDragFill = value;
            break;

        case "allowZoom":
            spread.options.allowUserZoom = value;
            break;

        case "allowOverflow":
            spread.sheets.forEach(function (sheet) {
                sheet.options.allowCellOverflow = value;
            });
            break;

        case "showDragFillSmartTag":
            spread.options.showDragFillSmartTag = value;
            break;

        case "showVerticalGridline":
            sheet.options.gridline.showVerticalGridline = value;
            break;

        case "showHorizontalGridline":
            sheet.options.gridline.showHorizontalGridline = value;
            break;

        case "showRowHeader":
            sheet.options.rowHeaderVisible = value;
            break;

        case "showColumnHeader":
            sheet.options.colHeaderVisible = value;
            break;

        case "wrapText":
            setWordWrap(sheet);
            break;
        case "hideSelection":
            spread.options.hideSelection = value;
            break;

        case "showRowOutline":
            sheet.showRowOutline(value);
            break;

        case "showColumnOutline":
            sheet.showColumnOutline(value);
            break;

        case "highlightInvalidData":
            spread.options.highlightInvalidData = value;
            break;

        /* table realted items */
        case "tableFilterButton":
            _activeTable && _activeTable.filterButtonVisible(value);
            break;

        case "tableHeaderRow":
            _activeTable && _activeTable.showHeader(value);
            break;

        case "tableTotalRow":
            _activeTable && _activeTable.showFooter(value);
            break;

        case "tableBandedRows":
            _activeTable && _activeTable.bandRows(value);
            break;

        case "tableBandedColumns":
            _activeTable && _activeTable.bandColumns(value);
            break;

        case "tableFirstColumn":
            _activeTable && _activeTable.highlightFirstColumn(value);
            break;

        case "tableLastColumn":
            _activeTable && _activeTable.highlightLastColumn(value);
            break;
        /* table realted items (end) */

        /* comment related items */
        case "commentDynamicSize":
            _activeComment && _activeComment.dynamicSize(value);
            break;

        case "commentDynamicMove":
            _activeComment && _activeComment.dynamicMove(value);
            break;

        case "commentLockText":
            _activeComment && _activeComment.lockText(value);
            break;

        case "commentShowShadow":
            _activeComment && _activeComment.showShadow(value);
            break;
        /* comment related items (end) */

        /* picture related items */
        case "pictureDynamicSize":
            _activePicture && _activePicture.dynamicSize(value);
            break;

        case "pictureDynamicMove":
            _activePicture && _activePicture.dynamicMove(value);
            break;

        case "pictureFixedPosition":
            _activePicture && _activePicture.fixedPosition(value);
            break;
        /* picture related items (end) */

        /* protect sheet realted items */
        case "checkboxProtectSheet":
            syncProtectSheetRelatedItems(sheet, value);
            break;

        case "checkboxSelectLockedCells":
            setProtectionOption(sheet, "allowSelectLockedCells", value);
            break;

        case "checkboxSelectUnlockedCells":
            setProtectionOption(sheet, "allowSelectUnlockedCells", value);
            break;

        case "checkboxSort":
            setProtectionOption(sheet, "allowSort", value);
            break;

        case "checkboxUseAutoFilter":
            setProtectionOption(sheet, "allowFilter", value);
            break;

        case "checkboxResizeRows":
            setProtectionOption(sheet, "allowResizeRows", value);
            break;

        case "checkboxResizeColumns":
            setProtectionOption(sheet, "allowResizeColumns", value);
            break;

        case "checkboxEditObjects":
            setProtectionOption(sheet, "allowEditObjects", value);
            break;
        /* protect sheet realted items (end) */

        /* slicer related items */
        case "displaySlicerHeader":
            setSlicerSetting("showHeader", value);
            break;

        case "lockSlicer":
            setSlicerSetting("lock", value);
            break;
        /* slicer related items (end) */

        default:
            console.log("not added code for", name);
            break;

    }
    spread.resumePaint();
}

function updateNumberProperty() {
    var $element = $(this),
        $parent = $element.parent(),
        name = $parent.data("name"),
        value = parseInt($element.val(), 10);

    if (isNaN(value)) {
        return;
    }

    var sheet = spread.getActiveSheet();

    spread.suspendPaint();
    switch (name) {
        case "rowCount":
            sheet.setRowCount(value);
            break;

        case "columnCount":
            sheet.setColumnCount(value);
            break;

        case "frozenRowCount":
            sheet.frozenRowCount(value);
            break;

        case "frozenColumnCount":
            sheet.frozenColumnCount(value);
            break;

        case "trailingFrozenRowCount":
            sheet.frozenTrailingRowCount(value);
            break;

        case "trailingFrozenColumnCount":
            sheet.frozenTrailingColumnCount(value);
            break;

        case "commentBorderWidth":
            _activeComment && _activeComment.borderWidth(value);
            break;

        case "commentOpacity":
            _activeComment && _activeComment.opacity(value / 100);
            break;

        case "pictureBorderWidth":
            _activePicture && _activePicture.borderWidth(value);
            break;

        case "pictureBorderRadius":
            _activePicture && _activePicture.borderRadius(value);
            break;

        case "slicerColumnNumber":
            setSlicerSetting("columnCount", value);
            break;

        case "slicerButtonHeight":
            setSlicerSetting("itemHeight", value);
            break;

        case "slicerButtonWidth":
            setSlicerSetting("itemWidth", value);
            break;

        default:
            console.log("updateNumberProperty need add for", name);
            break;
    }
    spread.resumePaint();
}

function updateStringProperty() {
    var $element = $(this),
        $parent = $element.parent(),
        name = $parent.data("name"),
        value = $element.val();

    var sheet = spread.getActiveSheet();

    switch (name) {
        case "sheetName":
            if (value && value !== sheet.name()) {
                try {
                    sheet.name(value);
                } catch (ex) {
                    alert(getResource("messages.duplicatedSheetName"));
                    $element.val(sheet.name());
                }
            }
            break;

        case "tableName":
            if (value && _activeTable && value !== _activeTable.name()) {
                if (!sheet.tables.findByName(value)) {
                    _activeTable.name(value);
                } else {
                    alert(getResource("messages.duplicatedTableName"));
                    $element.val(_activeTable.name());
                }
            }
            break;

        case "commentPadding":
            setCommentPadding(value);
            break;

        case "customFormat":
            setFormatter(value);
            break;

        case "slicerName":
            setSlicerSetting("name", value);
            break;

        case "slicerCaptionName":
            setSlicerSetting("captionName", value);
            break;

        case "watermark":
            setWatermark(sheet, value);
            break;

        case "cellPadding":
            setCellPadding(sheet, value);
            break;

        case "labelmargin":
            setLabelOptions(sheet, value, "margin");
            break;
        default:
            console.log("updateStringProperty w/o process of ", name);
            break;
    }
}

function setCommentPadding(padding) {
    if (_activeComment && padding) {
        var para = padding.split(",");
        if (para.length === 1) {
            _activeComment.padding(new spreadNS.Comments.Padding(parseInt(para[0], 10)));
        } else if (para.length === 4) {
            _activeComment.padding(new spreadNS.Comments.Padding(parseInt(para[0], 10), parseInt(para[1], 10), parseInt(para[2], 10), parseInt(para[3], 10)));
        }
    }
}

function fillSheetNameList($container) {
    var html = "";

    // unbind event if present
    $container.find(".menu-item").off('click');

    spread.sheets.forEach(function (sheet, index) {
        html += '<div class="menu-item"><div class="image"></div><div class="text" data-value="' + index + '">' + sheet.name() + '</div></div>';
    });
    $container.html(html);

    // bind event for new added elements
    $container.find(".menu-item").on('click', itemSelected);
}

function syncSpreadPropertyValues() {
    var options = spread.options;
    // General
    setCheckValue("allowUserDragDrop", options.allowUserDragDrop);
    setCheckValue("allowUserDragFill", options.allowUserDragFill);
    setCheckValue("allowZoom", options.allowUserZoom);
    setCheckValue("allowOverflow", spread.getActiveSheet().options.allowCellOverflow);
    setCheckValue("showDragFillSmartTag", options.showDragFillSmartTag);
    setDropDownValue("resizeZeroIndicator", options.resizeZeroIndicator);

    // Calculation
    setRadioItemChecked("referenceStyle", options.referenceStyle === spreadNS.ReferenceStyle.r1c1 ? "r1c1style" : "a1style");

    // Scroll Bar
    setCheckValue("showVerticalScrollbar", options.showVerticalScrollbar);
    setCheckValue("showHorizontalScrollbar", options.showHorizontalScrollbar);
    setCheckValue("scrollbarMaxAlign", options.scrollbarMaxAlign);
    setCheckValue("scrollbarShowMax", options.scrollbarShowMax);
    setCheckValue("scrollIgnoreHidden", options.scrollIgnoreHidden);

    // TabStrip
    setCheckValue("tabStripVisible", options.tabStripVisible);
    setCheckValue("newTabVisible", options.newTabVisible);
    setCheckValue("tabEditable", options.tabEditable);
    setCheckValue("allowSheetReorder", options.allowSheetReorder);
    setCheckValue("showTabNavigation", options.tabNavigationVisible);

    // Color
    setColorValue("spreadBackcolor", options.backColor);
    setColorValue("grayAreaBackcolor", options.grayAreaBackColor);

    // Tip
    setDropDownValue($("div.insp-dropdown-list[data-name='scrollTip']"), options.showScrollTip);
    setDropDownValue($("div.insp-dropdown-list[data-name='resizeTip']"), options.showResizeTip);
    setCheckValue("showDragDropTip", options.showDragDropTip);
    setCheckValue("showDragFillTip", options.showDragFillTip);

    // Cut / Copy Indicator
    setCheckValue("cutCopyIndicatorVisible", options.cutCopyIndicatorVisible);
    setColorValue("cutCopyIndicatorBorderColor", options.cutCopyIndicatorBorderColor);

    // Data validation
    setCheckValue("highlightInvalidData", options.highlightInvalidData);
}

function syncForzenProperties(sheet) {
    setNumberValue("frozenRowCount", sheet.frozenRowCount());
    setNumberValue("frozenColumnCount", sheet.frozenColumnCount());
    setNumberValue("trailingFrozenRowCount", sheet.frozenTrailingRowCount());
    setNumberValue("trailingFrozenColumnCount", sheet.frozenTrailingColumnCount());
}

function syncSheetPropertyValues() {
    var sheet = spread.getActiveSheet(),
        options = sheet.options;

    // General
    setNumberValue("rowCount", sheet.getRowCount());
    setNumberValue("columnCount", sheet.getColumnCount());
    setTextValue("sheetName", sheet.name());
    setColorValue("sheetTabColor", options.sheetTabColor);

    // Grid Line
    setCheckValue("showVerticalGridline", options.gridline.showVerticalGridline);
    setCheckValue("showHorizontalGridline", options.gridline.showHorizontalGridline);
    setColorValue("gridlineColor", options.gridline.color);

    // Header
    setCheckValue("showRowHeader", options.rowHeaderVisible);
    setCheckValue("showColumnHeader", options.colHeaderVisible);

    // Freeze
    setColorValue("frozenLineColor", options.frozenlineColor);

    syncForzenProperties(sheet);

    // Selection
    setDropDownValue($("#sheetTab div.insp-dropdown-list[data-name='selectionPolicy']"), sheet.selectionPolicy());
    setDropDownValue($("#sheetTab div.insp-dropdown-list[data-name='selectionUnit']"), sheet.selectionUnit());
    setColorValue("selectionBorderColor", options.selectionBorderColor);
    setColorValue("selectionBackColor", options.selectionBackColor);
    setCheckValue("hideSelection", spread.options.hideSelection);

    // Protection
    var isProtected = options.isProtected;
    setCheckValue("checkboxProtectSheet", isProtected);
    syncProtectSheetRelatedItems(sheet, isProtected);
    getCurrentSheetProtectionOption(sheet);

    updateCellStyleState(sheet, sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());

    // Zoom
    setZoomFactor(sheet.zoom());

    // Group
    setCheckValue("showRowOutline", sheet.showRowOutline());
    setCheckValue("showColumnOutline", sheet.showColumnOutline());

    if (!$(sheet).data("bind")) {
        $(sheet).data("bind", true);
        sheet.bind(spreadNS.Events.UserZooming, function (event, args) {
            setZoomFactor(args.newZoomFactor);
        });
        sheet.bind(spreadNS.Events.RangeChanged, function (event, args) {
            if (args.action === spreadNS.RangeChangedAction.clear) {
                // check special type items and switch to cell tab (laze process)
                if (isSpecialTabSelected()) {
                    onCellSelected();
                }
            }
        });
        sheet.bind(spreadNS.Events.FloatingObjectRemoved, function (event, args) {
            // check special type items and switch to cell tab (laze process)
            if (isSpecialTabSelected()) {
                onCellSelected();
            }
        });

        sheet.bind(spreadNS.Events.CommentRemoved, function (event, args) {
            // check special type items and switch to cell tab (laze process)
            if (isSpecialTabSelected()) {
                onCellSelected();
            }
        });
    }
}

function setZoomFactor(zoom) {
    setDropDownText("#toolbar div.insp-dropdown-list[data-name='zoomSpread']", Math.round(zoom * 100) + "%");
}

function setNumberValue(name, value) {
    $("div.insp-number[data-name='" + name + "'] input.editor").val(value);
}

function getNumberValue(name) {
    return +$("div[data-name='" + name + "'] input.editor").val();
}

function setTextValue(name, value) {
    $("div.insp-text[data-name='" + name + "'] input.editor").val(value);
}

function getTextValue(name) {
    return $("div.insp-text[data-name='" + name + "'] input.editor").val();
}

function setCheckValue(name, value, options) {
    var $target = $("div.insp-checkbox[data-name='" + name + "'] div.button");
    if (value) {
        $target.addClass("checked");
    } else {
        $target.removeClass("checked");
    }
    if (options) {
        $target.data(options);
    }
}

function getCheckValue(name) {
    var $target = $("div.insp-checkbox[data-name='" + name + "'] div.button");

    return $target.hasClass("checked");
}

function setColorValue(name, value) {
    $("div.insp-color-picker[data-name='" + name + "'] div.color-view").css("background-color", value || "");
}

var _dropdownitem;
var _colorpicker;
var _needShow = true;

var _handlePopupCloseEvents = 'mousedown touchstart MSPointerDown pointerdown'.split(' ');

function processEventListenerHandleClosePopup(add) {
    if (add) {
        _handlePopupCloseEvents.forEach(function (value) {
            document.addEventListener(value, documentMousedownHandler, true);
        });
    } else {
        _handlePopupCloseEvents.forEach(function (value) {
            document.removeEventListener(value, documentMousedownHandler, true);
        });
    }
}

function showDropdown() {
    if (!_needShow) {
        _needShow = true;
        return;
    }

    var DROPDOWN_OFFSET = 10;
    var $element = $(this),
        $container = $element.parent(),
        name = $container.data("name"),
        targetId = $container.data("list-ref"),
        $target = $("#" + targetId);

    if ($target && !$target.hasClass("show")) {
        if (name === "sheetName") {
            fillSheetNameList($target);
        }

        $target.data("dropdown", this);
        _dropdownitem = $target[0];

        var $dropdown = $element,
            offset = $dropdown.offset();

        var height = $element.outerHeight(),
            targetHeight = $target.outerHeight(),
            width = $element.outerWidth(),
            targetWidth = $target.outerWidth(),
            top = offset.top + height;

        // adjust drop down' width to same
        if (targetWidth < width) {
            $target.width(width);
        }

        var $inspContainer = $(".insp-container"),
            maxTop = $inspContainer.height() + $inspContainer.offset().top;

        // adjust top when out of bottom range
        if (top + targetHeight + DROPDOWN_OFFSET > maxTop) {
            top = offset.top - targetHeight;
        }

        $target.css({
            top: top,
            left: offset.left - $target.width() + $dropdown.width() + 16
        });

        // select corresponding item
        if (name === "borderLine") {
            var text = $("#border-line-type").attr("class");
            $("div.image", $target).removeClass("fa-check");
            $("div.text", $target).filter(function () {
                return $(this).find("div").attr("class") === text;
            }).siblings("div.image").addClass("fa fa-check");
            $("div.image.nocheck", $target).removeClass("fa-check");
        }
        else {
            var text = $("span.display", $dropdown).text();
            $("div.image", $target).removeClass("fa-check");
            $("div.text", $target).filter(function () {
                return $(this).text() === text;
            }).siblings("div.image").addClass("fa fa-check");
            // remove check for special items mark with nocheck class
            $("div.image.nocheck", $target).removeClass("fa-check");
        }

        $target.addClass("show");

        processEventListenerHandleClosePopup(true);
    }
}

function documentMousedownHandler(event) {
    var target = event.target,
        container = _dropdownitem || _colorpicker || $("#clearActionList:visible")[0] || $("#exportActionList:visible")[0];

    if (container) {
        if (container === target || $.contains(container, target)) {
            return;
        }

        // click on related item popup the dropdown, close it
        var dropdown = $(container).data("dropdown");
        if (dropdown && $.contains(dropdown, target)) {
            hidePopups();
            _needShow = false;
            return false;
        }
    }

    hidePopups();
    $("#passwordError").hide();
}

function hidePopups() {
    hideDropdown();
    hideColorPicker();
    hideClearActionDropDown();
    hideExportActionDropDown();
}

function hideClearActionDropDown() {
    if ($("#clearActionList:visible").length > 0) {
        $("#clearActionList").hide();
        processEventListenerHandleClosePopup(false);
    }
}

function hideExportActionDropDown() {
    if ($("#exportActionList:visible").length > 0) {
        $("#exportActionList").hide();
        processEventListenerHandleClosePopup(false);
    }
}

function hideDropdown() {
    if (_dropdownitem) {
        $(_dropdownitem).removeClass("show");
        _dropdownitem = null;
    }

    processEventListenerHandleClosePopup(false);
}

function showColorPicker() {
    if (!_needShow) {
        _needShow = true;
        return;
    }

    var MIN_TOP = 30, MIN_BOTTOM = 4;
    var $element = $(this),
        $container = $element.parent(),
        name = $container.data("name"),
        $target = $("#colorpicker");

    if ($target && !$target.hasClass("colorpicker-visible")) {
        $target.data("dropdown", this);
        // save related name for later use
        $target.data("name", name);

        var $nofill = $target.find("div.nofill-color");
        if ($container.hasClass("show-nofill-color")) {
            $nofill.show();
        } else {
            $nofill.hide();
        }

        _colorpicker = $target[0];

        var $dropdown = $element,
            offset = $dropdown.offset();

        var height = $target.height(),
            top = offset.top - (height - $element.height()) / 2 + 3,   // 3 = padding (4) - border-width(1)
            yOffset = 0;

        if (top < MIN_TOP) {
            yOffset = MIN_TOP - top;
            top = MIN_TOP;
        } else {
            var $inspContainer = $(".insp-container"),
                maxTop = $inspContainer.height() + $inspContainer.offset().top;

            // adjust top when out of bottom range
            if (top + height > maxTop - MIN_BOTTOM) {
                var newTop = maxTop - MIN_BOTTOM - height;
                yOffset = newTop - top;
                top = newTop;
            }
        }

        $target.css({
            top: top,
            left: offset.left - $target.width() - 20
        });

        // v-center the pointer
        var $pointer = $target.find(".cp-pointer");
        $pointer.css({top: (height - 24) / 2 - yOffset});   // 24 = pointer height

        $target.addClass("colorpicker-visible");

        processEventListenerHandleClosePopup(true);
    }
}

function hideColorPicker() {
    if (_colorpicker) {
        $(_colorpicker).removeClass("colorpicker-visible");
        _colorpicker = null;
    }
    processEventListenerHandleClosePopup(false);
}

function itemSelected() {
    // get related dropdown item
    var dropdown = $(_dropdownitem).data("dropdown");

    hideDropdown();

    if (this.parentElement.id === "clearActionList") {
        processClearAction($(this.parentElement), $("div.text", this).data("value"));
        return;
    }

    if (this.parentElement.id === "exportActionList") {
        processExportAction($(this.parentElement), $("div.text", this).data("value"));
        return;
    }

    var sheet = spread.getActiveSheet();

    var name = $(dropdown.parentElement).data("name"),
        $text = $("div.text", this),
        dataValue = $text.data("value"),    // data-value includes both number value and string value, should pay attention when use it
        numberValue = +dataValue,
        text = $text.text(),
        value = text,
        nameValue = dataValue || text;

    var options = spread.options;

    switch (name) {
        case "scrollTip":
            options.showScrollTip = numberValue;
            break;

        case "resizeTip":
            options.showResizeTip = numberValue;
            break;

        case "fontFamily":
            setStyleFont(sheet, "font-family", false, [value], value);
            break;

        case "labelFontFamily":
            setStyleFont(sheet, "font-family", true, [value], value);
            break;

        case "fontSize":
            value += "pt";
            setStyleFont(sheet, "font-size", false, [value], value);
            break;

        case "labelFontSize":
            value += "pt";
            setStyleFont(sheet, "font-size", true, [value], value);
            break;

        case "cellLabelVisibility":
            setLabelOptions(sheet, nameValue, "visibility");
            break;

        case "cellLabelAlignment":
            setLabelOptions(sheet, nameValue, "alignment");
            break;

        case "selectionPolicy":
            sheet.selectionPolicy(numberValue);
            break;

        case "selectionUnit":
            sheet.selectionUnit(numberValue);
            break;

        case "sheetName":
            var selectedSheet = spread.sheets[numberValue];
            setCheckValue("sheetVisible", selectedSheet.visible(), {
                sheetIndex: numberValue,
                sheetName: selectedSheet.name()
            });
            break;

        case "commentFontFamily":
            _activeComment && _activeComment.fontFamily(value);
            break;

        case "commentFontSize":
            value += "pt";
            _activeComment && _activeComment.fontSize(value);
            break;

        case "commentDisplayMode":
            _activeComment && _activeComment.displayMode(numberValue);
            break;

        case "commentFontStyle":
            _activeComment && _activeComment.fontStyle(nameValue);
            break;

        case "commentFontWeight":
            _activeComment && _activeComment.fontWeight(nameValue);
            break;

        case "commentBorderStyle":
            _activeComment && _activeComment.borderStyle(nameValue);
            break;

        case "commentHorizontalAlign":
            _activeComment && _activeComment.horizontalAlign(numberValue);
            break;

        case "pictureBorderStyle":
            _activePicture && _activePicture.borderStyle(nameValue);
            break;

        case "pictureStretch":
            _activePicture && _activePicture.pictureStretch(numberValue);
            break;

        case "conditionalFormat":
            processConditionalFormatDetailSetting(nameValue);
            break;

        case "ruleType":
            updateEnumTypeOfCF(numberValue);
            break;

        case "comparisonOperator":
            processComparisonOperator(numberValue);
            break;

        case "iconSetType":
            updateIconCriteriaItems(numberValue);
            break;

        case "minType":
            processMinItems(numberValue, "minValue");
            break;

        case "midType":
            processMidItems(numberValue, "midValue");
            break;

        case "maxType":
            processMaxItems(numberValue, "maxValue");
            break;

        case "cellTypes":
            processCellTypeSetting(nameValue);
            break;

        case "validatorType":
            processDataValidationSetting(nameValue, value);
            break;

        case "numberValidatorComparisonOperator":
            processNumberValidatorComparisonOperatorSetting(numberValue);
            break;

        case "dateValidatorComparisonOperator":
            processDateValidatorComparisonOperatorSetting(numberValue);
            break;

        case "textLengthValidatorComparisonOperator":
            processTextLengthValidatorComparisonOperatorSetting(numberValue);
            break;

        case "sparklineExType":
            processSparklineSetting(nameValue, value);
            break;

        case "zoomSpread":
            processZoomSetting(nameValue, value);
            break;

        case "commomFormat":
            processFormatSetting(nameValue, value);
            break;

        case "borderLine":
            processBorderLineSetting(nameValue);
            break;

        case "minAxisType":
            updateManual(nameValue, "manualMin");
            break;

        case "maxAxisType":
            updateManual(nameValue, "manualMax");
            break;

        case "slicerItemSorting":
            processSlicerItemSorting(numberValue);
            break;

        case "spreadTheme":
            processChangeSpreadTheme(nameValue);
            break;

        case "resizeZeroIndicator":
            spread.options.resizeZeroIndicator = numberValue;
            break;

        case "copyPasteHeaderOptions":
            spread.options.copyPasteHeaderOptions = GC.Spread.Sheets.CopyPasteHeaderOptions[nameValue]
            break;

        default:
            console.log("TODO add itemSelected for ", name, value);
            break;
    }

    setDropDownText(dropdown, text);
}

function setDropDownText(container, value) {
    var refList = "#" + $(container).data("list-ref"),
        $items = $(".menu-item div.text", refList),
        $item = $items.filter(function () {
            return $(this).data("value") === value;
        });

    var text = $item.text() || value;

    $("span.display", container).text(text);
}

function setDropDownValue(container, value, host) {
    if (typeof container === "string") {
        host = host || document;

        container = $(host).find("div.insp-dropdown-list[data-name='" + container + "']");
    }

    var refList = "#" + $(container).data("list-ref");

    $("span.display", container).text($(".menu-item>div.text[data-value='" + value + "']", refList).text());
}

function setDropDownValueByIndex(container, index) {
    var refList = "#" + $(container).data("list-ref"),
        $item = $(".menu-item:eq(" + index + ") div.text", refList);

    $("span.display", container).text($item.text());

    return {text: $item.text(), value: $item.data("value")};
}

function getDropDownValue(name, host) {
    host = host || document;

    var container = $(host).find("div.insp-dropdown-list[data-name='" + name + "']"),
        refList = "#" + $(container).data("list-ref"),
        text = $("span.display", container).text();

    var value = $("div.text", refList).filter(function () {
        return $(this).text() === text;
    }).data("value");

    return value;
}

function colorSelected() {
    var themeColor = $(this).data("name");
    var value = $(this).css("background-color");

    var name = $(_colorpicker).data("name");
    var sheet = spread.getActiveSheet();

    $("div.color-view", $(_colorpicker).data("dropdown")).css("background-color", value);

    // No Fills need special process
    if ($(this).hasClass("auto-color-cell")) {
        if (name === "backColor") {
            value = undefined;
        }
    }

    var options = spread.options;

    spread.suspendPaint();
    switch (name) {
        case "spreadBackcolor":
            options.backColor = value;
            break;

        case "grayAreaBackcolor":
            options.grayAreaBackColor = value;
            break;

        case "cutCopyIndicatorBorderColor":
            options.cutCopyIndicatorBorderColor = value;
            break;

        case "sheetTabColor":
            sheet.options.sheetTabColor = value;
            break;

        case "frozenLineColor":
            sheet.options.frozenlineColor = value;
            break;

        case "gridlineColor":
            sheet.options.gridline.color = value;
            break;

        case "foreColor":
        case "backColor":
            setColor(sheet, name, themeColor || value);
            break;

        case "labelForeColor":
            setLabelOptions(sheet, value, "foreColor");
            break;

        case "selectionBorderColor":
            sheet.options.selectionBorderColor = value;
            break;

        case "selectionBackColor":
            // change to rgba (alpha: 0.2) to make cell content visible
            value = getRGBAColor(value, 0.2);
            sheet.options.selectionBackColor = value;
            $("div.color-view", $(_colorpicker).data("dropdown")).css("background-color", value);
            break;

        case "commentBorderColor":
            _activeComment && _activeComment.borderColor(value);
            break;

        case "commentForeColor":
            _activeComment && _activeComment.foreColor(value);
            break;

        case "commentBackColor":
            _activeComment && _activeComment.backColor(value);
            break;

        case "pictureBorderColor":
            _activePicture && _activePicture.borderColor(value);
            break;

        case "pictureBackColor":
            _activePicture && _activePicture.backColor(value);
            break;

        default:
            console.log("TODO colorSelected", name);
            break;
    }
    spread.resumePaint();
}

function getRGBAColor(color, alpha) {
    var result = color,
        prefix = "rgb(";

    // get rgb color use jquery
    if (color.substr(0, 4) !== prefix) {
        var $temp = $("#setfontstyle");
        $temp.css("background-color", color);
        color = $temp.css("background-color");
    }

    // adding alpha to make rgba
    if (color.substr(0, 4) === prefix) {
        var length = color.length;
        result = "rgba(" + color.substring(4, length - 1) + ", " + alpha + ")";
    }

    return result;
}

function setColor(sheet, method, value) {
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    sheet.suspendPaint();
    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount);
        sheet.getRange(sel.row, sel.col, sel.rowCount, sel.colCount)[method](value);
    }
    sheet.resumePaint();
}

function buttonClicked() {
    var $element = $(this),
        name = $element.data("name"),
        container;

    var sheet = spread.getActiveSheet();

    // get group
    if ((container = $element.parents(".insp-radio-button-group")).length > 0) {
        name = container.data("name");
        $element.siblings().removeClass("active");
        $element.addClass("active");
        switch (name) {
            case "vAlign":
            case "hAlign":
                setAlignment(sheet, name, $element.data("name"));
                break;
        }
    } else if ($element.parents(".insp-button-group").length > 0) {
        if (!$element.hasClass("no-toggle")) {
            $element.toggleClass("active");
        }

        switch (name) {
            case "bold":
                setStyleFont(sheet, "font-weight", false, ["700", "bold"], "normal");
                break;
            case "labelBold":
                setStyleFont(sheet, "font-weight", true, ["700", "bold"], "normal");
                break;
            case "italic":
                setStyleFont(sheet, "font-style", false, ["italic"], "normal");
                break;
            case "labelItalic":
                setStyleFont(sheet, "font-style", true, ["italic"], "normal");
                break;
            case "underline":
                setTextDecoration(sheet, spreadNS.TextDecorationType.underline);
                break;
            case "strikethrough":
                setTextDecoration(sheet, spreadNS.TextDecorationType.lineThrough);
                break;
            case "overline":
                setTextDecoration(sheet, spreadNS.TextDecorationType.overline);
                break;

            case "increaseIndent":
                setTextIndent(sheet, 1);
                break;

            case "decreaseIndent":
                setTextIndent(sheet, -1);
                break;

            case "percentStyle":
                setFormatter(uiResource.cellTab.format.percentValue);
                break;

            case "commaStyle":
                setFormatter(uiResource.cellTab.format.commaValue);
                break;

            case "increaseDecimal":
                increaseDecimal();
                break;

            case "decreaseDecimal":
                decreaseDecimal();
                break;

            case "comment-underline":
            case "comment-overline":
            case "comment-strikethrough":
                setCommentTextDecoration(+$element.data("value"));
                break;

            default:
                console.log("buttonClicked w/o process code for ", name);
                break;
        }
    }
}

function setCommentTextDecoration(flag) {
    if (_activeComment) {
        var textDecoration = _activeComment.textDecoration();
        _activeComment.textDecoration(textDecoration ^ flag);
    }
}

// Increase Decimal related items
function increaseDecimal() {
    var sheet = spread.getActiveSheet();
    execInSelections(sheet, "formatter", function (sheet, row, column) {
        var style = sheet.getStyle(row, column);
        if (!style) {
            style = new spreadNS.Style();
        }
        var activeCell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
        var activeCellValue = activeCell.value();
        var activeCellFormatter = activeCell.formatter();
        var activeCellText = activeCell.text();

        if (activeCellValue) {
            var formatString = null;
            var zero = "0";
            var numberSign = "#";
            var decimalPoint = ".";
            var zeroPointZero = "0" + decimalPoint + "0";

            var scientificNotationCheckingFormatter = getScientificNotationCheckingFormattter(activeCellFormatter);
            if (!activeCellFormatter || ((activeCellFormatter == "General" || (scientificNotationCheckingFormatter &&
                (scientificNotationCheckingFormatter.indexOf("E") >= 0 || scientificNotationCheckingFormatter.indexOf('e') >= 0))))) {
                if (!isNaN(activeCellValue)) {
                    var result = activeCellText.split('.');
                    if (result.length == 1) {
                        if (result[0].indexOf('E') >= 0 || result[0].indexOf('e') >= 0)
                            formatString = zeroPointZero + "E+00";
                        else
                            formatString = zeroPointZero;
                    }
                    else if (result.length == 2) {
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
            }
            else {
                formatString = activeCellFormatter;
                if (formatString) {
                    var formatters = formatString.split(';');
                    for (var i = 0; i < formatters.length && i < 2; i++) {
                        if (formatters[i] && formatters[i].indexOf("/") < 0 && formatters[i].indexOf(":") < 0 && formatters[i].indexOf("?") < 0) {
                            var indexOfDecimalPoint = formatters[i].lastIndexOf(decimalPoint);
                            if (indexOfDecimalPoint != -1) {
                                formatters[i] = formatters[i].slice(0, indexOfDecimalPoint + 1) + zero + formatters[i].slice(indexOfDecimalPoint + 1);
                            }
                            else {
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
    });
}

//This method is used to get the formatter which not include the string and color
//in order to not misleading with the charactor 'e' / 'E' in scientific notation.
function getScientificNotationCheckingFormattter(formatter) {
    if (!formatter) {
        return formatter;
    }
    var i;
    var signalQuoteSubStrings = getSubStrings(formatter, '\'', '\'');
    for (i = 0; i < signalQuoteSubStrings.length; i++) {
        formatter = formatter.replace(signalQuoteSubStrings[i], '');
    }
    var doubleQuoteSubStrings = getSubStrings(formatter, '\"', '\"');
    for (i = 0; i < doubleQuoteSubStrings.length; i++) {
        formatter = formatter.replace(doubleQuoteSubStrings[i], '');
    }
    var colorStrings = getSubStrings(formatter, '[', ']');
    for (i = 0; i < colorStrings.length; i++) {
        formatter = formatter.replace(colorStrings[i], '');
    }
    return formatter;
}

function getSubStrings(source, beginChar, endChar) {
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
}
// Increase Decimal related items (end)

// Decrease Decimal related items
function decreaseDecimal() {
    var sheet = spread.getActiveSheet();
    execInSelections(sheet, "formatter", function (sheet, row, column) {
        var style = sheet.getStyle(row, column);
        if (!style) {
            style = new spreadNS.Style();
        }
        var activeCell = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
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

                        if (sb !== null) {
                            result[1] = sb.toString();

                            formatString = result[0] + (result[1] !== "" ? decimalPoint + result[1] : "");
                        }
                    }
                }
            }
            else {
                formatString = activeCellFormatter;
                if (formatString) {
                    var formatters = formatString.split(';');
                    for (var i = 0; i < formatters.length && i < 2; i++) {
                        if (formatters[i] && formatters[i].indexOf("/") < 0 && formatters[i].indexOf(":") < 0 && formatters[i].indexOf("?") < 0) {
                            var indexOfDecimalPoint = formatters[i].lastIndexOf(decimalPoint);
                            if (indexOfDecimalPoint != -1 && indexOfDecimalPoint + 1 < formatters[i].length) {
                                formatters[i] = formatters[i].slice(0, indexOfDecimalPoint + 1) + formatters[i].slice(indexOfDecimalPoint + 2);
                                var tempString = indexOfDecimalPoint + 1 < formatters[i].length ? formatters[i].substr(indexOfDecimalPoint + 1, 1) : "";
                                if (tempString === "" || tempString !== "0")
                                    formatters[i] = formatters[i].slice(0, indexOfDecimalPoint) + formatters[i].slice(indexOfDecimalPoint + 1);
                            }
                            else {
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
    });
}
// Decrease Decimal related items (end)

function setAlignment(sheet, type, value) {
    var sels = sheet.getSelections(),
        rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount(),
        align;

    value = value.toLowerCase();

    if (value === "middle") {
        value = "center";
    }

    if (type === "hAlign") {
        align = spreadNS.HorizontalAlign[value];
    } else {
        align = spreadNS.VerticalAlign[value];
    }

    sheet.suspendPaint();
    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount);
        sheet.getRange(sel.row, sel.col, sel.rowCount, sel.colCount)[type](align);
    }
    sheet.resumePaint();
}

function setTextDecoration(sheet, flag) {
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    sheet.suspendPaint();
    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount),
            textDecoration = sheet.getCell(sel.row, sel.col).textDecoration();
        if ((textDecoration & flag) === flag) {
            textDecoration = textDecoration - flag;
        } else {
            textDecoration = textDecoration | flag;
        }
        sheet.getRange(sel.row, sel.col, sel.rowCount, sel.colCount).textDecoration(textDecoration);
    }
    sheet.resumePaint();
}

function setWordWrap(sheet) {
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    sheet.suspendPaint();
    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount),
            wordWrap = !sheet.getCell(sel.row, sel.col).wordWrap(),
            startRow = sel.row,
            endRow = sel.row + sel.rowCount - 1;

        sheet.getRange(startRow, sel.col, sel.rowCount, sel.colCount).wordWrap(wordWrap);

        for (var row = startRow; row <= endRow; row++) {
            sheet.autoFitRow(row);
        }
    }
    sheet.resumePaint();
}
function setTextIndent(sheet, step) {
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    sheet.suspendPaint();
    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount),
            indent = sheet.getCell(sel.row, sel.col).textIndent();

        if (isNaN(indent)) {
            indent = 0;
        }

        var value = indent + step;
        if (value < 0) {
            value = 0;
        }
        sheet.getRange(sel.row, sel.col, sel.rowCount, sel.colCount).textIndent(value);
    }
    sheet.resumePaint();
}

function divButtonClicked() {
    var sheet = spread.getActiveSheet(),
        id = this.id;

    spread.suspendPaint();
    switch (id) {
        case "mergeCells":
            mergeCells(sheet);
            updateMergeButtonsState();
            break;

        case "unmergeCells":
            unmergeCells(sheet);
            updateMergeButtonsState();
            break;

        case "freezePane":
            sheet.frozenRowCount(sheet.getActiveRowIndex());
            sheet.frozenColumnCount(sheet.getActiveColumnIndex());
            syncForzenProperties(sheet);
            break;

        case "unfreeze":
            sheet.frozenRowCount(0);
            sheet.frozenColumnCount(0);
            sheet.frozenTrailingRowCount(0);
            sheet.frozenTrailingColumnCount(0);
            syncForzenProperties(sheet);
            break;

        case "sortAZ":
        case "sortZA":
            sortData(sheet, id === "sortAZ");
            break;

        case "filter":
            updateFilter(sheet);
            break;

        case "group":
            addGroup(sheet);
            break;

        case "ungroup":
            removeGroup(sheet);
            break;

        case "showDetail":
            toggleGroupDetail(sheet, true);
            break;

        case "hideDetail":
            toggleGroupDetail(sheet, false);
            break;

        default:
            console.log("TODO add code for ", id);
            break;
    }
    spread.resumePaint();
}

function mergeCells(sheet) {
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount);
        sheet.addSpan(sel.row, sel.col, sel.rowCount, sel.colCount);
    }
}

function unmergeCells(sheet) {
    function removeSpan(range) {
        sheet.removeSpan(range.row, range.col);
    }

    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount);
        sheet.getSpans(sel).forEach(removeSpan);
    }
}

function sortData(sheet, ascending) {
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount);
        sheet.sortRange(sel.row, sel.col, sel.rowCount, sel.colCount, true,
            [
                {index: sel.col, ascending: ascending}
            ]);
    }
}

function updateFilter(sheet) {
    if (sheet.rowFilter()) {
        sheet.rowFilter(null);
    } else {
        var sels = sheet.getSelections();
        if (sels.length > 0) {
            var sel = sels[0];
            sheet.rowFilter(new spreadNS.Filter.HideRowFilter(sel));
        }
    }
}

function setCheckboxEnable($element, enable) {
    if (enable) {
        $element.removeClass("disabled");
        $element.find(".button").addClass("checked");
    } else {
        $element.addClass("disabled");
    }
}

function addGroup(sheet) {
    var sels = sheet.getSelections();
    var sel = sels[0];

    if (!sel) return;

    if (sel.col === -1) // row selection
    {
        spread.commandManager().execute({
            cmd: 'outlineRow',
            sheetName: sheet.name(),
            index: sel.row,
            count: sel.rowCount
        });
    }
    else if (sel.row === -1) // column selection
    {
        spread.commandManager().execute({
            cmd: 'outlineColumn',
            sheetName: sheet.name(),
            index: sel.col,
            count: sel.colCount
        });
    }
    else // cell range selection
    {
        alert(getResource("messages.rowColumnRangeRequired"));
    }
}

function removeGroup(sheet) {
    var sels = sheet.getSelections();
    var sel = sels[0];

    if (!sel) return;

    if (sel.col === -1 && sel.row === -1) // sheet selection
    {
        sheet.rowOutlines.ungroup(0, sheet.getRowCount());
        sheet.columnOutlines.ungroup(0, sheet.getColumnCount());
    }
    else if (sel.col === -1) // row selection
    {
        spread.commandManager().execute({
            cmd: 'removeRowOutline',
            sheetName: sheet.name(),
            index: sel.row,
            count: sel.rowCount
        });
    }
    else if (sel.row === -1) // column selection
    {
        spread.commandManager().execute({
            cmd: 'removeColumnOutline',
            sheetName: sheet.name(),
            index: sel.col,
            count: sel.colCount
        });
    }
    else // cell range selection
    {
        alert(getResource("messages.rowColumnRangeRequired"));
    }
}

function toggleGroupDetail(sheet, expand) {
    var sels = sheet.getSelections();
    var sel = sels[0];

    if (!sel) return;

    if (sel.col === -1 && sel.row === -1) // sheet selection
    {
    }
    else if (sel.col === -1) // row selection
    {
        for (var i = 0; i < sel.rowCount; i++) {
            var rgi = sheet.rowOutlines.find(sel.row + i, 0);
            if (rgi) {
                sheet.rowOutlines.expand(rgi.level, expand);
            }
        }
    }
    else if (sel.row === -1) // column selection
    {
        for (var i = 0; i < sel.colCount; i++) {
            var rgi = sheet.columnOutlines.find(sel.col + i, 0);
            if (rgi) {
                sheet.columnOutlines.expand(rgi.level, expand);
            }
        }
    }
    else // cell range selection
    {
    }
}

var MARGIN_BOTTOM = 4;

function adjustSpreadSize() {
    var height = $("#inner-content-container").height() - $("#formulaBar").height() - MARGIN_BOTTOM,
        spreadHeight = $("#ss").height();

    if (spreadHeight !== height) {
        $("#controlPanel").height(height);
        $("#ss").height(height);
        $("#ss").data("workbook").refresh();
    }
}

function screenAdoption() {
    hideSpreadContextMenu();
    adjustSpreadSize();

    // adjust toolbar items position
    var $toolbar = $("#toolbar"),
        sectionWidth = Math.floor($toolbar.width() / 3);

    $(".toolbar-left-section", $toolbar).width(sectionWidth);

    // + 2 to make sure the right section with enough space to show in same line
    if (sectionWidth > 375 + 2) {  // 340 = (380 + 300) / 2, where 380 is min-width of left section, 300 is the width of right section
        $(".toolbar-middle-section", $toolbar).width(sectionWidth);
    } else {
        $(".toolbar-middle-section", $toolbar).width("auto");
    }

    // explicit set formula box' width instead of 100% because it's contained in table
    var width = $("#inner-content-container").width() - $("#positionbox").outerWidth() - 1; // 1: border' width of td contains formulabox (left only)
    $("#formulabox").css({width: width});
}

function doPrepareWork() {
    /*
     1. expand / collapse .insp-group by checking expanded class
     */
    function processDisplayGroups() {
        $("div.insp-group").each(function () {
            var $group = $(this),
                expanded = $group.hasClass("expanded"),
                $content = $group.find("div.insp-group-content"),
                $state = $group.find("span.group-state");

            if (expanded) {
                $content.show();
                $state.addClass("fa-caret-down");
            } else {
                $content.hide();
                $state.addClass("fa-caret-right");
            }
        });
    }

    function addEventHandlers() {
        $("div.insp-group-title>span").click(toggleState);
        $("div.insp-checkbox").click(checkedChanged);
        $("div.insp-number>input.editor").blur(updateNumberProperty);
        $("div.insp-dropdown-list .dropdown").click(showDropdown);
        $("div.insp-menu .menu-item").click(itemSelected);
        $("div.insp-color-picker .picker").click(showColorPicker);
        $("li.color-cell").click(colorSelected);
        $(".insp-button-group span.btn").click(buttonClicked);
        $(".insp-radio-button-group span.btn").click(buttonClicked);
        $(".insp-buttons .btn").click(divButtonClicked);
        $(".insp-text input.editor").blur(updateStringProperty);
    }

    processDisplayGroups();

    addEventHandlers();

    $("input[type='number']:not('.not-min-zero')").attr("min", 0);

    // set default values
    var item = setDropDownValueByIndex($("#conditionalFormatType"), -1);
    processConditionalFormatDetailSetting(item.value, true);
    var cellTypeItem = setDropDownValueByIndex($("#cellTypes"), -1);
    processCellTypeSetting(cellTypeItem.value, true);                     // CellType Setting
    var validationTypeItem = setDropDownValueByIndex($("#validatorType"), 0);
    processDataValidationSetting(validationTypeItem.value);         // Data Validation Setting
    var sparklineTypeItem = setDropDownValueByIndex($("#sparklineExTypeDropdown"), 0);
    processSparklineSetting(sparklineTypeItem.value);               // SparklineEx Setting

    setDropDownValue("numberValidatorComparisonOperator", 0);       // NumberValidator Comparison Operator
    processNumberValidatorComparisonOperatorSetting(0);
    setDropDownValue("dateValidatorComparisonOperator", 0);         // DateValidator Comparison Operator
    processDateValidatorComparisonOperatorSetting(0);
    setDropDownValue("textLengthValidatorComparisonOperator", 0);   // TextLengthValidator Comparison Operator
    processTextLengthValidatorComparisonOperatorSetting(0);
    processBorderLineSetting("thin");                               // Border Line Setting

    setDropDownValue("minType", 1);                                 // LowestValue
    setDropDownValue("midType", 4);                                 // Percentile
    setDropDownValue("maxType", 2);                                 // HighestValue
    setDropDownValue("minimumType", 5);                             // Automin
    setDropDownValue("maximumType", 7);                             // Automax
    setDropDownValue("dataBarDirection", 0);                        // Left-to-Right
    setDropDownValue("axisPosition", 0);                            // Automatic
    setDropDownValue("iconSetType", 0);                             // ThreeArrowsColored
    setDropDownValue("checkboxCellTypeTextAlign", 3);               // Right
    setDropDownValue("comboboxCellTypeEditorValueType", 2);         // Value
    setDropDownValue("errorAlert", 0);                              // Data Validation Error Alert Type
    setDropDownValue("zoomSpread", 1);                              // Zoom Value
    setDropDownValueByIndex($("#commomFormatType"), 0);             // Format Setting
    setDropDownValueByIndex($("#boxplotClassType"), 0);             // BoxPlotSparkline Class
    setDropDownValue("boxplotSparklineStyleType", 0);               // BoxPlotSparkline Style
    setDropDownValue("dataOrientationType", 0);                     // CompatibleSparkline DataOrientation
    setDropDownValue("paretoLabelList", 0);                         // ParetoSparkline Label
    setDropDownValue("spreadSparklineStyleType", 4);                // SpreadSparkline Style
    setDropDownValue("stackedSparklineTextOrientation", 0);         // StackedSparkline TextOrientation
    setDropDownValueByIndex($("#spreadTheme"), 1);                  // Spread Theme
    setDropDownValue("resizeZeroIndicator", 1);                     // ResizeZeroIndicator
    setDropDownValueByIndex($("#copyPasteHeaderOptions"), 3);       // CopyPasteHeaderOptins
    setDropDownValueByIndex($("#cellLabelVisibility"), 0);          // CellLabelVisibility
    setDropDownValueByIndex($("#cellLabelAlignment"), 0);           // CellLabelAlignment
    conditionalFormatTexts = uiResource.conditionalFormat.texts;
}

function initSpread() {
    //formulabox
    fbx = new spreadNS.FormulaTextBox.FormulaTextBox(document.getElementById('formulabox'));
    fbx.workbook(spread);

    setCellContent();
    setFormulaContent();
    setConditionalFormatContent();
    setTableContent();
    setSparklineContent();
    setCommentContent();
    setPictureContent();
    setDataContent();
    setSlicerContent();
}

// Sample Content related items
function setFormulaContent() {
    var sheet = new spreadNS.Worksheet("Formula");
    spread.addSheet(spread.getSheetCount(), sheet);

    sheet.suspendPaint();
    sheet.setColumnCount(50);

    sheet.setColumnWidth(0, 100);
    sheet.setColumnWidth(2, 100);
    sheet.setColumnWidth(6, 103);
    sheet.setColumnWidth(8, 150);

    var row = 1, col = 2;                                       // basic function
    sheet.getCell(row, 0).value("Basic Function").font("700 11pt Calibri");
    sheet.getCell(row, col).value("Name");
    sheet.getCell(row, ++col).value("Age");
    row++, col = 2;
    sheet.getCell(row, col).value("Jack").hAlign(spreadNS.HorizontalAlign.right);
    sheet.getCell(row, ++col).value(17);
    row++, col = 2;
    sheet.getCell(row, col).value("Lily").hAlign(spreadNS.HorizontalAlign.right);
    sheet.getCell(row, ++col).value(23);
    row++, col = 2;
    sheet.getCell(row, col).value("Bob").hAlign(spreadNS.HorizontalAlign.right);
    sheet.getCell(row, ++col).value(30);
    row++, col = 2;
    sheet.getCell(row, col).value("Mary").hAlign(spreadNS.HorizontalAlign.right);
    sheet.getCell(row, ++col).value(25);
    row++, col = 2;
    sheet.getCell(row, col).value("Average Age:");
    sheet.getCell(row, ++col).formula("=AVERAGE(D3:D6)");
    row++, col = 2;
    sheet.getCell(row, col).value("Max Age:");
    sheet.getCell(row, ++col).formula("=MAX(D3:D6)");
    row++, col = 2;
    sheet.getCell(row, col).value("Min Age:");
    sheet.getCell(row, ++col).formula("=MIN(D3:D6)");

    row = 1, col = 8;                                           // indirect function
    sheet.getCell(row, 6).value("Indirect Function").font("700 11pt Calibri");
    sheet.getCell(row, col).value("J2");
    sheet.getCell(row, ++col).value(1);
    row++, col = 8;
    sheet.getCell(row, col).value("I");
    sheet.getCell(row, ++col).value(2);
    row++, col = 8;
    sheet.getCell(row, col).value("J");
    sheet.getCell(row, ++col).value(3);
    row = row + 2, col = 8;
    var formulaStr = "=INDIRECT(\"I2\")";
    sheet.getCell(row, col).value(formulaStr);
    sheet.getCell(row, ++col).formula(formulaStr);
    row++, col = 8;
    formulaStr = "=INDIRECT(I2)";
    sheet.getCell(row, col).value(formulaStr);
    sheet.getCell(row, ++col).formula(formulaStr);
    row++, col = 8;
    formulaStr = "=INDIRECT(\"I\"&(1+2))";
    sheet.getCell(row, col).value(formulaStr);
    sheet.getCell(row, ++col).formula(formulaStr);
    row++, col = 8;
    formulaStr = "=INDIRECT(I4&J3)";
    sheet.getCell(row, col).value(formulaStr);
    sheet.getCell(row, ++col).formula(formulaStr);
    row++, col = 8;
    formulaStr = "=INDIRECT(\"" + sheet.name() + "!\"&I2)";
    sheet.getCell(row, col).value(formulaStr);
    sheet.getCell(row, ++col).formula(formulaStr);
    row++, col = 8;
    formulaStr = "=INDIRECT(\"" + sheet.name() + "!I2\")";
    sheet.getCell(row, col).value(formulaStr);
    sheet.getCell(row, ++col).formula(formulaStr);

    row = 12;                                                   // array formula
    sheet.getCell(row, 0).value("Array Formula").font("700 11pt Calibri");
    sheet.addSpan(row, 2, 1, 6);
    sheet.getCell(row, 2).value("Calculation");
    sheet.setArray(13, 2, [
        ["", "Match", "Physical", "Chemistry", "", "Sum"],
        ["Alice", 97, 61, 53],
        ["John", 65, 76, 65],
        ["Bob", 55, 70, 64],
        ["Jack", 89, 77, 73]
    ]);
    sheet.setArrayFormula(14, 7, 4, 1, "=SUBTOTAL(9,OFFSET($D$15,ROW($D$15:$D$18)-ROW($D$15),,1,3))");

    row = 19;
    sheet.addSpan(row, 2, 1, 6);
    sheet.getCell(row, 2).value("Search");
    sheet.setArray(20, 2, [
        ["apple", "apple"],
        ["banana", "pear"],
        ["pear", "potato"],
        ["tomato", "potato"],
        ["potato", "dumpling"],
        ["cake"],
        ["noodel"]
    ]);
    sheet.addSpan(20, 6, 1, 5);
    sheet.getCell(20, 6).value("Find out the first value on D21:D25 that doesn't contain on D21:D27");
    sheet.addSpan(22, 6, 1, 2);
    sheet.getCell(22, 6).value("ArrayFormula Result:");
    sheet.addSpan(23, 6, 1, 2);
    sheet.getCell(23, 6).value("NomalFormula Result:");
    sheet.setArrayFormula(22, 8, 1, 1, "=INDEX(D21:D25,MATCH(TRUE,ISNA(MATCH(D21:D25,C21:C27,0)),0))");
    sheet.setFormula(23, 8, "=INDEX(D21:D25,MATCH(TRUE,ISNA(MATCH(D21:D25,C21:C27,0)),0))");

    row = 28;
    sheet.addSpan(row, 2, 1, 6);
    sheet.getCell(row, 2).value("Statistics");
    sheet.setArray(29, 2, [
        ["Product", "Salesman", "Units Sold"],
        ["Fax", "Brown", 1],
        ["Phone", "Smith", 10],
        ["Fax", "Jones", 20],
        ["Fax", "Smith", 30],
        ["Phone", "Jones", 40],
        ["PC", "Smith", 50],
        ["Fax", "Brown", 60],
        ["Phone", "Davis", 70],
        ["PC", "Jones", 80]
    ]);
    sheet.addSpan(29, 6, 1, 4);
    sheet.getCell(29, 6).value("Summing Sales: Faxes Sold By Brown");
    sheet.setArrayFormula(30, 6, 1, 1, "=SUM((C31:C39=\"Fax\")*(D31:D39=\"Brown\")*(E31:E39))");
    sheet.addSpan(31, 6, 1, 4);
    sheet.getCell(31, 6).value("Logical AND (Faxes And Brown)");
    sheet.setArrayFormula(32, 6, 1, 1, "=SUM((C31:C39=\"Fax\")*(D31:D39=\"Brown\"))");
    sheet.addSpan(33, 6, 1, 4);
    sheet.getCell(33, 6).value("Logical OR (Faxes Or Jones)");
    sheet.setArrayFormula(34, 6, 1, 1, "=SUM(IF((C31:C39=\"Fax\")+(D31:D39=\"Jones\"),1,0))");
    sheet.addSpan(35, 6, 1, 4);
    sheet.getCell(35, 6).value("Logical XOR (Fax Or Jones but not both)");
    sheet.setArrayFormula(36, 6, 1, 1, "=SUM(IF(MOD((C31:C39=\"Fax\")+(D31:D39=\"Jones\"),2),1,0))");
    sheet.addSpan(37, 6, 1, 4);
    sheet.getCell(37, 6).value("Logical NAND (All Sales Except Fax And Jones)");
    sheet.setArrayFormula(38, 6, 1, 1, "=SUM(IF((C31:C39=\"Fax\")+(D31:D39=\"Jones\")<>2,1,0))");

    sheet.resumePaint();
}

function setCellContent() {
    var sheet = new spreadNS.Worksheet("Cell");
    spread.removeSheet(0);
    spread.addSheet(spread.getSheetCount(), sheet);

    sheet.suspendPaint();
    sheet.setColumnCount(50);

    sheet.setColumnWidth(0, 100);
    sheet.setColumnWidth(1, 20);
    for (var col = 2; col < 11; col++) {
        sheet.setColumnWidth(col, 88);
    }

    var Range = spreadNS.Range;
    var row = 1, col = 0;                               // cell background
    sheet.getCell(row, col).value("Background").font("700 11pt Calibri");
    sheet.getCell(row, col + 2).backColor("#1E90FF");
    sheet.getCell(row, col + 4).backColor("#00ff00");

    row = row + 2;                                      // line border
    var borderColor = "red";
    var lineStyle = spreadNS.LineStyle;
    var lineBorder = spreadNS.LineBorder;
    var option = {all: true};
    sheet.getCell(row, 0).value("Border").font("700 11pt Calibri");
    col = 1;
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.empty), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.hair), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.dotted), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.dashDotDot), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.dashDot), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.dashed), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.thin), option);
    row = row + 2, col = 1;
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.mediumDashDotDot), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.slantedDashDot), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.mediumDashDot), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.mediumDashed), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.medium), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.thick), option);
    sheet.getRange(row, ++col, 1, 1).setBorder(new lineBorder(borderColor, lineStyle.double), option);
    row = row + 2, col = 1;
    sheet.getRange(row, ++col, 2, 2).setBorder(new lineBorder("blue", lineStyle.dashed), {all: true});
    sheet.getRange(row, col + 3, 2, 2).setBorder(new lineBorder("yellowgreen", lineStyle.double), {outline: true});
    sheet.getRange(row, col + 6, 2, 2).setBorder(new lineBorder("black", lineStyle.mediumDashed), {innerHorizontal: true});
    sheet.getRange(row, col + 6, 2, 2).setBorder(new lineBorder("black", lineStyle.slantedDashDot), {innerVertical: true});
    row = row + 3, col = 2;
    sheet.getRange(row, col, 3, 2).setBorder(new lineBorder("lightgreen", lineStyle.thick), {outline: true});
    sheet.getRange(row, col, 3, 2).setBorder(new lineBorder("lightgreen", lineStyle.thick), {innerHorizontal: true});
    col = col + 3;
    sheet.getRange(row, col, 3, 3).setBorder(new lineBorder("#CDCD00", lineStyle.thick), {outline: true});
    sheet.getRange(row, col, 3, 3).setBorder(new lineBorder("#CDCD00", lineStyle.thick), {innerVertical: true});

    row = row + 3, col = 1;                             // merge cell
    sheet.getCell(row + 1, 0).value("Span").font("700 11pt Calibri");
    sheet.addSpan(row + 1, ++col, 1, 2);
    sheet.addSpan(row, col + 3, 3, 1);
    sheet.addSpan(row, col + 5, 3, 2);

    row = row + 4, col = 1;                             // font
    var TextDecorationType = spreadNS.TextDecorationType;
    var fontText = "SPREADJS";
    sheet.getCell(row, 0).value("Font").font("700 11pt Calibri");
    sheet.getCell(row, ++col).value(fontText);
    sheet.getCell(row, ++col).value(fontText).font("13pt Calibri");
    sheet.getCell(row, ++col).value(fontText).font("11pt Arial");
    sheet.getCell(row, ++col).value(fontText).font("13pt Times New Roman");
    sheet.getCell(row, ++col).value(fontText).backColor("#FFD700");
    sheet.getCell(row, ++col).value(fontText).foreColor("#436EEE");
    row = row + 2, col = 1;
    sheet.getCell(row, ++col).value(fontText).foreColor("#FFD700").backColor("#436EEE");
    sheet.getCell(row, ++col).value(fontText).font("700 11pt Calibri");
    sheet.getCell(row, ++col).value(fontText).font("italic 11pt Calibri");
    sheet.getCell(row, ++col).value(fontText).textDecoration(TextDecorationType.underline);
    sheet.getCell(row, ++col).value(fontText).textDecoration(TextDecorationType.lineThrough);
    sheet.getCell(row, ++col).value(fontText).textDecoration(TextDecorationType.overline);

    row = row + 2, col = 1;                             // format
    var number = 0.25;
    sheet.getCell(row, 0).value("Format").font("700 11pt Calibri");
    sheet.getCell(row, ++col).value(number).formatter("0.00");
    sheet.getCell(row, ++col).value(number).formatter("$#,##0.00");
    sheet.getCell(row, ++col).value(number).formatter("$ #,##0.00;$ (#,##0.00);$ \"-\"??;@");
    sheet.getCell(row, ++col).value(number).formatter("0%");
    sheet.getCell(row, ++col).value(number).formatter("# ?/?");
    row = row + 2, col = 1;
    sheet.getCell(row, ++col).value(number).formatter("0.00E+00");
    sheet.getCell(row, ++col).value(number).formatter("@");
    sheet.getCell(row, ++col).value(number).formatter("h:mm:ss AM/PM");
    sheet.getCell(row, ++col).value(number).formatter("m/d/yyyy");
    sheet.getCell(row, ++col).value(number).formatter("dddd, mmmm dd, yyyy");

    row = row + 2, col = 1;                             // text alignment
    var HorizontalAlign = spreadNS.HorizontalAlign;
    var VerticalAlign = spreadNS.VerticalAlign;
    sheet.setRowHeight(row, 60);
    sheet.getCell(row, 0).value("Alignment").font("700 11pt Calibri");
    sheet.getCell(row, ++col).value("Top Left").vAlign(VerticalAlign.top).hAlign(HorizontalAlign.left);
    sheet.getCell(row, ++col).value("Top Center").vAlign(VerticalAlign.top).hAlign(HorizontalAlign.center);
    sheet.getCell(row, ++col).value("Top Right").vAlign(VerticalAlign.top).hAlign(HorizontalAlign.right);
    sheet.getCell(row, ++col).value("Center Left").vAlign(VerticalAlign.center).hAlign(HorizontalAlign.left);
    sheet.getCell(row, ++col).value("Center Center").vAlign(VerticalAlign.center).hAlign(HorizontalAlign.center);
    sheet.getCell(row, ++col).value("Center Right").vAlign(VerticalAlign.center).hAlign(HorizontalAlign.right);
    sheet.getCell(row, ++col).value("Bottom Left").vAlign(VerticalAlign.bottom).hAlign(HorizontalAlign.left);
    sheet.getCell(row, ++col).value("Bottom Center").vAlign(VerticalAlign.bottom).hAlign(HorizontalAlign.center);
    sheet.getCell(row, ++col).value("Bottom Right").vAlign(VerticalAlign.bottom).hAlign(HorizontalAlign.right);

    row = row + 2, col = 1;                             // lock cell
    sheet.getCell(row, 0).value("Locked").font("700 11pt Calibri");
    sheet.getCell(row, ++col).value("TRUE").locked(true);
    sheet.getCell(row, ++col).value("FALSE").locked(false);

    row = row + 2, col = 1;                             // word wrap
    sheet.setRowHeight(row, 60);
    sheet.getCell(row, 0).value("WordWrap").font("700 11pt Calibri");
    sheet.getCell(row, ++col).value("ABCDEFGHIJKLMNOPQRSTUVWXYZ").wordWrap(true);
    sheet.getCell(row, ++col).value("ABCDEFGHIJKLMNOPQRSTUVWXYZ").wordWrap(false);

    row = row + 2, col = 1;                             // celltype
    sheet.setRowHeight(row, 25);
    var cellType;
    sheet.getCell(row, 0).value("CellType").font("700 11pt Calibri");
    cellType = new spreadNS.CellTypes.Button();
    cellType.buttonBackColor("#FFFF00");
    cellType.text("I'm a button");
    sheet.getCell(row, ++col).cellType(cellType);

    cellType = new spreadNS.CellTypes.CheckBox();
    cellType.caption("caption");
    cellType.textTrue("true");
    cellType.textFalse("false");
    cellType.textIndeterminate("indeterminate");
    cellType.textAlign(spreadNS.CellTypes.CheckBoxTextAlign.right);
    cellType.isThreeState(true);
    sheet.getCell(row, ++col).cellType(cellType);

    cellType = new spreadNS.CellTypes.ComboBox();
    cellType.items(["apple", "banana", "cat", "dog"]);
    sheet.getCell(row, ++col).cellType(cellType);

    cellType = new spreadNS.CellTypes.HyperLink();
    cellType.linkColor("blue");
    cellType.visitedLinkColor("red");
    cellType.text("SpreadJS");
    cellType.linkToolTip("SpreadJS Web Site");
    sheet.getCell(row, ++col).cellType(cellType).value("http://spread.grapecity.com/Products/SpreadJS/");

    row = row + 2, col = 1;                             // celltype
    sheet.setRowHeight(row, 100);
    sheet.setColumnWidth(0, 150);
    sheet.getCell(row, 0).value("CellPadding&Label").font("700 11pt Calibri");
    sheet.getCell(row, ++col, GC.Spread.Sheets.SheetArea.viewport).watermark("User ID").cellPadding('20');
    sheet.getCell(row, col, GC.Spread.Sheets.SheetArea.viewport).labelOptions({
        foreColor: 'red',
        visibility: 2,
        font: 'bold 15px Arial'
    });

    var b = new GC.Spread.Sheets.CellTypes.Button();
    b.text("Click Me!");
    sheet.setColumnWidth(3, 200);
    sheet.setCellType(row, ++col, b, GC.Spread.Sheets.SheetArea.viewport);
    sheet.getCell(row, col, GC.Spread.Sheets.SheetArea.viewport).watermark("Button Cell Type").cellPadding('20 20');
    sheet.getCell(row, col, GC.Spread.Sheets.SheetArea.viewport).labelOptions({
        alignment: 2,
        visibility: 1,
        font: 'bold 15px Arial',
        foreColor: 'grey'
    });

    var c = new GC.Spread.Sheets.CellTypes.CheckBox();
    c.isThreeState(false);
    c.textTrue("Checked!");
    c.textFalse("Check Me!");
    sheet.setColumnWidth(4, 200);
    sheet.setCellType(row, ++col, c, GC.Spread.Sheets.SheetArea.viewport);
    sheet.getCell(row, col, GC.Spread.Sheets.SheetArea.viewport).watermark("CheckBox Cell Type").cellPadding('30');
    sheet.getCell(row, col, GC.Spread.Sheets.SheetArea.viewport).labelOptions({
        alignment: 5,
        visibility: 0,
        foreColor: 'green'
    });
    sheet.resumePaint();
}

function setConditionalFormatContent(sheet) {
    var sheet = new spreadNS.Worksheet("Conditional Format");
    spread.addSheet(spread.getSheetCount(), sheet);
    sheet.suspendPaint();
    sheet.setColumnCount(50);

    sheet.setColumnWidth(0, 20);
    sheet.setColumnWidth(1, 20);
    for (var col = 2; col < 11; col++) {
        sheet.setColumnWidth(col, 83);
    }
    for (var row = 1; row < 16; row++) {
        sheet.addSpan(row, 10, 1, 2);
    }

    var Range = spreadNS.Range;
    var row = 1, col = 1;
    var style = new spreadNS.Style();
    style.backColor = "red";
    var cfs = sheet.conditionalFormats;
    sheet.getCell(row, ++col).value("Cell Value").font("700 11pt Calibri");
    sheet.getCell(row, col + 2).value("Specific Text").font("700 11pt Calibri");
    sheet.getCell(row, col + 4).value("Unique").font("700 11pt Calibri");
    sheet.getCell(row, col + 6).value("Duplicate").font("700 11pt Calibri");
    sheet.getCell(row, col + 8).value("Date Occurring").font("700 11pt Calibri");

    var rowCount = 6;
    row++, col;
    sheet.getCell(row, col).value(0);
    sheet.getCell(row + 1, col).value(1);
    sheet.getCell(row + 2, col).value(2);
    sheet.getCell(row + 3, col).value(3);
    sheet.getCell(row + 4, col).value(4);
    sheet.getCell(row + 5, col).value(5);
    cfs.addCellValueRule(ComparisonOperators.between, 2, 4, style, [new Range(row, col, rowCount, 1)]);

    col = col + 2;
    sheet.getCell(row, col).value("test");
    sheet.getCell(row + 1, col).value("bad");
    sheet.getCell(row + 2, col).value("good");
    sheet.getCell(row + 3, col).value("testing");
    sheet.getCell(row + 4, col).value("tested");
    sheet.getCell(row + 5, col).value("general");
    cfs.addSpecificTextRule(ConditionalFormatting.TextComparisonOperators.contains, "test", style, [new Range(row, col, rowCount, 1)]);

    col = col + 2;
    sheet.getCell(row, col).value(50);
    sheet.getCell(row + 1, col).value(50);
    sheet.getCell(row + 2, col).value(11);
    sheet.getCell(row + 3, col).value(5);
    sheet.getCell(row + 4, col).value(50);
    sheet.getCell(row + 5, col).value(120);
    cfs.addUniqueRule(style, [new Range(row, col, rowCount, 1)]);

    col = col + 2;
    sheet.getCell(row, col).value(50);
    sheet.getCell(row + 1, col).value(50);
    sheet.getCell(row + 2, col).value(11);
    sheet.getCell(row + 3, col).value(5);
    sheet.getCell(row + 4, col).value(50);
    sheet.getCell(row + 5, col).value(120);
    cfs.addDuplicateRule(style, [new Range(row, col, rowCount, 1)]);

    col = col + 2;
    var date = new Date();
    sheet.getCell(row, col).value(date);
    sheet.getCell(row + 1, col).value(new Date(date.setDate(date.getDate() + 1)));
    sheet.getCell(row + 2, col).value(new Date(date.setDate(date.getDate() + 5)));
    sheet.getCell(row + 3, col).value(new Date(date.setDate(date.getDate() + 1)));
    sheet.getCell(row + 4, col).value(new Date(date.setDate(date.getDate() + 7)));
    sheet.getCell(row + 5, col).value(new Date(date.setDate(date.getDate() + 8)));
    cfs.addDateOccurringRule(ConditionalFormatting.DateOccurringType.nextWeek, style, [new Range(row, col, rowCount, 1)]);

    row = row + 7, col = 1;
    sheet.getCell(row, ++col).value("Top/Bottom").font("700 11pt Calibri");
    sheet.getCell(row, col + 2).value("Average").font("700 11pt Calibri");
    sheet.getCell(row, col + 4).value("2-Color Scale").font("700 11pt Calibri");
    sheet.getCell(row, col + 6).value("3-Color Scale").font("700 11pt Calibri");
    sheet.getCell(row, col + 8).value("Data Bar").font("700 11pt Calibri");

    row++;
    sheet.getCell(row, col).value(0);
    sheet.getCell(row + 1, col).value(1);
    sheet.getCell(row + 2, col).value(2);
    sheet.getCell(row + 3, col).value(3);
    sheet.getCell(row + 4, col).value(4);
    sheet.getCell(row + 5, col).value(5);
    cfs.addTop10Rule(ConditionalFormatting.Top10ConditionType.top, 4, style, [new Range(row, col, rowCount, 1)]);

    for (var c = col + 2; c < col + 7; c = c + 2) {
        sheet.getCell(row, c).value(1);
        sheet.getCell(row + 1, c).value(50);
        sheet.getCell(row + 2, c).value(100);
        sheet.getCell(row + 3, c).value(2);
        sheet.getCell(row + 4, c).value(60);
        sheet.getCell(row + 5, c).value(3);
    }
    cfs.addAverageRule(ConditionalFormatting.AverageConditionType.above, style, [new Range(row, col + 2, rowCount, 1)]);
    cfs.add2ScaleRule(1, 1, "red", 2, 100, "yellow", [new Range(row, col + 4, rowCount, 1)]);
    cfs.add3ScaleRule(1, 1, "red", 0, 50, "blue", 2, 100, "yellow", [new Range(row, col + 6, rowCount, 1)]);

    col = col + 8;
    sheet.getCell(row, col).value(1);
    sheet.getCell(row + 1, col).value(15);
    sheet.getCell(row + 2, col).value(25);
    sheet.getCell(row + 3, col).value(-1);
    sheet.getCell(row + 4, col).value(-15);
    sheet.getCell(row + 5, col).value(-25);
    var ScaleValueNumber = ConditionalFormatting.ScaleValueType.number;
    cfs.addDataBarRule(1, null, 2, null, "green", [new Range(row, col, rowCount, 1)]);

    row = row + 8, col = 1;
    sheet.getCell(row, ++col).value("Icon Set").font("700 11pt Calibri");
    sheet.addSpan(row, col, 1, 10);
    sheet.addSpan(row + 6, col, 1, 10);
    row++;
    for (var column = col; column < col + 10; column++) {
        sheet.getCell(row, column).value(-50);
        sheet.getCell(row + 1, column).value(-25);
        sheet.getCell(row + 2, column).value(0);
        sheet.getCell(row + 3, column).value(25);
        sheet.getCell(row + 4, column).value(50);
        sheet.getCell(row + 6, column).value(-50);
        sheet.getCell(row + 7, column).value(-25);
        sheet.getCell(row + 8, column).value(0);
        sheet.getCell(row + 9, column).value(25);
        sheet.getCell(row + 10, column).value(50);
    }
    rowCount = 5;
    cfs.addIconSetRule(0, [new Range(row, col, rowCount, 1)]);
    cfs.addIconSetRule(1, [new Range(row, ++col, rowCount, 1)]);
    cfs.addIconSetRule(2, [new Range(row, ++col, rowCount, 1)]);
    cfs.addIconSetRule(3, [new Range(row, ++col, rowCount, 1)]);
    cfs.addIconSetRule(4, [new Range(row, ++col, rowCount, 1)]);
    cfs.addIconSetRule(5, [new Range(row, ++col, rowCount, 1)]);
    cfs.addIconSetRule(6, [new Range(row, ++col, rowCount, 1)]);
    cfs.addIconSetRule(7, [new Range(row, ++col, rowCount, 1)]);
    cfs.addIconSetRule(8, [new Range(row, ++col, rowCount, 1)]);
    cfs.addIconSetRule(9, [new Range(row, ++col, rowCount, 1)]);
    col = 1;
    cfs.addIconSetRule(10, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(11, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(12, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(13, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(14, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(15, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(16, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(17, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(18, [new Range(row + 6, ++col, rowCount, 1)]);
    cfs.addIconSetRule(19, [new Range(row + 6, ++col, rowCount, 1)]);

    sheet.resumePaint();
}

function getRandomNumber() {
    var num = Math.random();
    if (num - 0.5 > 0) {
        return Math.round(Math.random() * 100);
    }
    else {
        return Math.round(Math.random() * (-100));
    }
}

function setTableContent() {
    var sheet = new spreadNS.Worksheet("Table");
    spread.addSheet(spread.getSheetCount(), sheet);
    sheet.suspendPaint();
    sheet.setColumnCount(50);

    // table
    var table, rowCount = 5, colCount = 5;
    var row = 0, col = 1;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Table Style - light7").font("700 11pt Calibri");
    sheet.tables.add("sampleTable0", ++row, col, rowCount, colCount, spreadNS.Tables.TableThemes.light7);

    sheet.addSpan(row + 7, col, 1, colCount);
    sheet.getCell(row + 7, col).value("Table Style - medium7").font("700 11pt Calibri");
    sheet.tables.add("sampleTable1", row + 8, col, rowCount, colCount, spreadNS.Tables.TableThemes.medium7);

    sheet.addSpan(row + 15, col, 1, colCount);
    sheet.getCell(row + 15, col).value("Table Style - dark7").font("700 11pt Calibri");
    sheet.tables.add("sampleTable2", row + 16, col, rowCount, colCount, spreadNS.Tables.TableThemes.dark7);

    sheet.addSpan(row + 23, col, 1, colCount);
    sheet.getCell(row + 23, col).value("Hide Filter Button").font("700 11pt Calibri");
    table = sheet.tables.add("sampleTable3", row + 24, col, rowCount, colCount);
    table.filterButtonVisible(false);

    row = 0, col = col + 7;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Hide Header Row").font("700 11pt Calibri");
    table = sheet.tables.add("sampleTable4", ++row, col, rowCount, colCount);
    table.showHeader(false);

    sheet.addSpan(row + 7, col, 1, colCount);
    sheet.getCell(row + 7, col).value("Show Total Row").font("700 11pt Calibri");
    table = sheet.tables.add("sampleTable5", row + 8, col, rowCount, colCount);
    table.showFooter(true);

    sheet.addSpan(row + 15, col, 1, colCount);
    sheet.getCell(row + 15, col).value("Don't display alternating row style").font("700 11pt Calibri");
    table = sheet.tables.add("sampleTable6", row + 16, col, rowCount, colCount);
    table.bandRows(false);

    sheet.addSpan(row + 23, col, 1, colCount);
    sheet.getCell(row + 23, col).value("Display alternating column style").font("700 11pt Calibri");
    table = sheet.tables.add("sampleTable7", row + 24, col, rowCount, colCount);
    table.bandRows(false);
    table.bandColumns(true);

    row = 32, col = 1;
    var data = [
        ["bob", "36", "man", "Beijing", "80"],
        ["Betty", "28", "woman", "Xi'an", "52"],
        ["Gary", "23", "man", "NewYork", "63"],
        ["Hunk", "45", "man", "Beijing", "80"],
        ["Cherry", "37", "woman", "Shanghai", "58"]];
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Highlight first column").font("700 11pt Calibri");
    table = sheet.tables.addFromDataSource("sampleTable8", row + 1, col, data);
    table.highlightFirstColumn(true);
    col = col + 7;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Highlight last column").font("700 11pt Calibri");
    table = sheet.tables.addFromDataSource("sampleTable9", row + 1, col, data);
    table.highlightLastColumn(true);

    sheet.resumePaint();
}

function getHBarFormula(range) {
    return "IF(" + range + ">=0.8,HBARSPARKLINE(" + range + ",\"green\"), " +
        "IF(" + range + ">=0.6,HBARSPARKLINE(" + range + ",\"blue\"), " +
        "IF(" + range + ">=0.4,HBARSPARKLINE(" + range + ",\"yellow\"), " +
        "IF(" + range + ">=0.2,HBARSPARKLINE(" + range + ",\"orange\"), " +
        "IF(" + range + ">=0,HBARSPARKLINE(" + range + ",\"red\"), HBARSPARKLINE(" + range + ",\"red\") " + ")))))";
}

function getVBarFormula(row) {
    return "=IF((Q3:W3>0)=(ROW(Q13:W14)=ROW($Q$13)),VBARSPARKLINE((Q3:W3)/MAX(ABS(Q3:W3)),Q12:W12),\"\")".replace(/(Q|W)3/g, "$1" + row);
}

function setSparklineContent() {
    var sheet = new spreadNS.Worksheet("Sparkline");
    spread.addSheet(spread.getSheetCount(), sheet);
    sheet.suspendPaint();
    sheet.setColumnCount(50);

    addCompatibleContent(sheet);
    addPieContent(sheet);
    addAreaContent(sheet);
    addScatterContent(sheet);
    addStackedContent(sheet);
    addBulletContent(sheet);
    addBoxPlotContent(sheet);
    addVariContent(sheet);
    addCascadeContent(sheet);
    addSpreadContent(sheet);
    addParetoContent(sheet);
    addHBarContent(sheet);
    addVBarContent(sheet);
    addMonthContent(sheet);
    addYearContent(sheet);
    sheet.resumePaint();
}

function addMonthContent(sheet) {
    sheet.addSpan(51, 3, 4, 2);
    sheet.addSpan(55, 3, 1, 2);
    var day = 1;
    for (var row = 51; row < 82; row++) {
        sheet.setValue(row, 0, new Date(2016, 0, day++));
        sheet.setValue(row, 1, Math.round(Math.random() * 100));
        sheet.setFormatter(row, 0, "MM/DD/YYYY");
    }
    sheet.setFormula(51, 3, '=MONTHSPARKLINE(2016, 1, A52:B82, "lightgray", "lightgreen", "green", "darkgreen")');
    sheet.setFormula(55, 3, '=TEXT(DATE(2016,1, 1),"mmmm")');
}

function addYearContent(sheet) {
    sheet.addSpan(51, 6, 4, 8);
    sheet.setFormula(51, 6, '=YEARSPARKLINE(2016, A52:B82, "lightgray", "lightgreen", "green", "darkgreen")');
}

function addCompatibleContent(sheet) {
    sheet.addSpan(0, 0, 1, 8);
    sheet.getCell(0, 0).value("The company revenue in 2014").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.addSpan(1, 2, 1, 2);
    sheet.addSpan(1, 4, 1, 2);
    sheet.addSpan(1, 6, 1, 2);
    sheet.setValue(1, 0, "Month");
    sheet.setValue(1, 1, "Revenue");
    sheet.setValue(1, 2, "Diagram 1");
    sheet.setValue(1, 4, "Diagram 2");
    sheet.setValue(1, 6, "Diagram 3");
    sheet.getRange(1, 0, 1, 7).backColor("Accent 4").foreColor("white");
    for (var i = 2; i < 5; i++) {
        sheet.setValue(i, 0, new Date(2014, i - 1, 1));
        sheet.setFormatter(i, 0, "mm/dd/yyyy");
    }
    sheet.setColumnWidth(0, 80);
    sheet.setValue(2, 1, 30);
    sheet.setValue(3, 1, -60);
    sheet.setValue(4, 1, 80);

    sheet.addSpan(2, 2, 3, 2);
    sheet.setFormula(2, 2, '=LINESPARKLINE(B3:B5,0,A3:A5,0,"{ac:#ffff00,fmc:brown,hmc:red,lastmc:blue,lowmc:green,mc:purple,nc:yellowgreen,sc:pink,dxa:true,sf:true,sh:true,slast:true,slow:true,sn:true,sm:true,lw:3,dh:false,deca:1,rtl:false,minat:1,maxat:1,mmax:5,mmin:-3}")');
    sheet.addSpan(2, 4, 3, 2);
    sheet.setFormula(2, 4, '=COLUMNSPARKLINE(B3:B5,0,A3:A5,0,"{ac:#ffff00,fmc:brown,hmc:red,lastmc:blue,lowmc:green,mc:purple,nc:yellowgreen,sc:pink,dxa:true,sf:true,sh:true,slast:true,slow:true,sn:true,sm:true,lw:3,dh:false,deca:1,rtl:false,minat:1,maxat:1,mmax:5,mmin:-3}")');
    sheet.addSpan(2, 6, 3, 2);
    sheet.setFormula(2, 6, '=WINLOSSSPARKLINE(B3:B5,0,A3:A5,0)');
}

function addPieContent(sheet) {
    sheet.addSpan(6, 0, 1, 5);
    sheet.getCell(6, 0).value("My Assets").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.addSpan(7, 2, 1, 2);
    sheet.addSpan(8, 2, 3, 2);
    sheet.setValue(7, 0, "Asset Type");
    sheet.setValue(7, 1, "Amount");
    sheet.setValue(7, 2, "Diagram");
    sheet.setValue(7, 4, "Note");
    sheet.setValue(8, 0, "Savings");
    sheet.getRange(7, 0, 1, 5).backColor("Accent 4").foreColor("white");
    sheet.getCell(8, 1).value(25000).formatter("$#,##0");
    sheet.setValue(9, 0, "401k");
    sheet.getCell(9, 1).value(55000).formatter("$#,##0");
    sheet.setValue(10, 0, "Stocks");
    sheet.getCell(10, 1).value(15000).formatter("$#,##0");
    sheet.setFormula(8, 2, '=PIESPARKLINE(B9:B11,"#919F81","#D7913E","CEA722")');
    sheet.getCell(8, 4).backColor("#919F81").formula("=B9/SUM(B9:B11)").formatter("0.00%");
    sheet.getCell(9, 4).backColor("#D7913E").formula("=B10/SUM(B9:B11)").formatter("0.00%");
    sheet.getCell(10, 4).backColor("#CEA722").formula("=B11/SUM(B9:B11)").formatter("0.00%");
}

function addAreaContent(sheet) {
    sheet.addSpan(12, 0, 1, 5);
    sheet.getCell(12, 0).value("Sales by State").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.addSpan(13, 2, 1, 3);
    sheet.addSpan(14, 2, 4, 3);
    sheet.setValue(13, 0, "State");
    sheet.setValue(13, 1, "Sales");
    sheet.setValue(13, 2, "Diagram");
    sheet.setValue(14, 0, "Idaho");
    sheet.getRange(13, 0, 1, 3).backColor("Accent 4").foreColor("white");
    sheet.getCell(14, 1).value(3500).formatter("$#,##0");
    sheet.setValue(15, 0, "Montana");
    sheet.getCell(15, 1).value(7000).formatter("$#,##0");
    sheet.setValue(16, 0, "Oregon");
    sheet.getCell(16, 1).value(2000).formatter("$#,##0");
    sheet.setValue(17, 0, "Washington");
    sheet.getCell(17, 1).value(5000).formatter("$#,##0");
    sheet.setFormula(14, 2, '=AREASPARKLINE(B15:B18,,,0,6000,"yellowgreen","red")');
}

function addScatterContent(sheet) {
    sheet.addSpan(19, 0, 1, 5);
    sheet.getCell(19, 0).value("Particulate Levels in Rainfall").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.addSpan(20, 2, 1, 3);
    sheet.addSpan(21, 2, 4, 3);
    sheet.setValue(20, 0, "Daily rainfall");
    sheet.setValue(20, 1, "Particulate level");
    sheet.setValue(20, 2, "Diagram");
    sheet.getRange(20, 0, 1, 3).backColor("Accent 4").foreColor("white");
    sheet.setValue(21, 0, 2.0);
    sheet.setValue(21, 1, 100);
    sheet.setValue(22, 0, 3.0);
    sheet.setValue(22, 1, 130);
    sheet.setValue(23, 0, 4.0);
    sheet.setValue(23, 1, 110);
    sheet.setValue(24, 0, 5.0);
    sheet.setValue(24, 1, 135);
    sheet.setFormula(21, 2, '=SCATTERSPARKLINE(A22:B25,,MIN(A22:A25),MAX(A22:A25),MIN(B22:B25),MAX(B22:B25),AVERAGE(B22:B25),AVERAGE(A22:A25),,,,,TRUE,TRUE,TRUE,"green",,TRUE)');
}

function addStackedContent(sheet) {
    sheet.addSpan(26, 0, 1, 5);
    sheet.getCell(26, 0).value("Sales by State").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.getRange(27, 0, 1, 6).foreColor("white").backColor("Accent 4").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.addSpan(27, 4, 1, 3);
    sheet.addSpan(28, 4, 1, 3);
    sheet.setRowHeight(28, 30);
    sheet.setValue(27, 0, "State");
    sheet.setValue(27, 1, "Product 1");
    sheet.setValue(27, 2, "Product 2");
    sheet.setValue(27, 3, "Product 3");
    sheet.setValue(27, 4, "Diagram");
    sheet.setValue(28, 0, "Idaho");
    sheet.getCell(28, 1).value(10000).formatter("$#,##0");
    sheet.getCell(28, 2).value(12000).formatter("$#,##0");
    sheet.getCell(28, 3).value(15000).formatter("$#,##0");
    sheet.setValue(29, 1, "orange");
    sheet.setValue(29, 2, "purple");
    sheet.setValue(29, 3, "yellowgreen");
    sheet.setFormula(28, 4, '=STACKEDSPARKLINE(B29:D29,B30:D30,B28:D28,40000)');
}

function addBulletContent(sheet) {
    sheet.addSpan(31, 0, 1, 5);
    sheet.getCell(31, 0).value("Employee KPI").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.getRange(32, 0, 1, 4).foreColor("white").backColor("Accent 4").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.HorizontalAlign.center);
    sheet.addSpan(32, 3, 1, 2);
    sheet.addSpan(33, 3, 1, 2);
    sheet.addSpan(34, 3, 1, 2);
    sheet.addSpan(35, 3, 1, 2);
    sheet.setValue(32, 0, "Name");
    sheet.setValue(32, 1, "Forecast");
    sheet.setValue(32, 2, "Actuality");
    sheet.setValue(32, 3, "Diagram");
    sheet.setValue(33, 0, "Employee 1");
    sheet.setValue(33, 1, 6);
    sheet.setValue(33, 2, 6);
    sheet.setValue(34, 0, "Employee 2");
    sheet.setValue(34, 1, 8);
    sheet.setValue(34, 2, 7);
    sheet.setValue(35, 0, "Employee 3");
    sheet.setValue(35, 1, 6);
    sheet.setValue(35, 2, 4);

    sheet.addSpan(38, 6, 1, 3);
    sheet.setValue(38, 6, "BULLETSPARKLINE Settings:");
    sheet.setValue(39, 6, "target");
    sheet.setValue(39, 7, 7);
    sheet.setValue(40, 6, "maxi");
    sheet.setValue(40, 7, 10);
    sheet.setValue(41, 6, "good");
    sheet.setValue(41, 7, 8);
    sheet.setValue(42, 6, "bad");
    sheet.setValue(42, 7, 5);
    sheet.setValue(43, 6, "color scheme");
    sheet.setValue(43, 7, "gray");

    sheet.setFormula(33, 3, '=BULLETSPARKLINE(C34,H40,H41,H42,H43,H34,1,H44)');
    sheet.setFormula(34, 3, '=BULLETSPARKLINE(C35,H40,H41,H42,H43,H34,1,H44)');
    sheet.setFormula(35, 3, '=BULLETSPARKLINE(C36,H40,H41,H42,H43,H34,1,H44)');
    sheet.setRowHeight(33, 28);
    sheet.setRowHeight(34, 28);
    sheet.setRowHeight(35, 28);
}

function addBoxPlotContent(sheet) {
    sheet.addSpan(31, 6, 1, 8);
    sheet.getCell(31, 6).value("The Company Sales in 2014 (Month)").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.addSpan(32, 12, 1, 2);
    sheet.addSpan(33, 12, 1, 2);
    sheet.addSpan(34, 12, 1, 2);
    sheet.addSpan(35, 12, 1, 2);
    sheet.setValue(32, 7, 1);
    sheet.setValue(32, 8, 2);
    sheet.setValue(32, 9, 3);
    sheet.setValue(32, 10, 4);
    sheet.setValue(32, 11, 5);
    sheet.setValue(32, 12, "Actual Sales");
    sheet.getRange(32, 7, 1, 7).hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center).wordWrap(true);
    sheet.setValue(32, 6, "Region");
    sheet.setValue(33, 6, "Alabama");
    sheet.setValue(34, 6, "Alaska");
    sheet.setValue(35, 6, "Arizona");
    var data = [[5268, 6281, 8921, 1069, 1239],
        [2837, 5739, 993, 4247, 9514],
        [6661, 4172, 9777, 1282, 9535]];
    sheet.setArray(33, 7, data);
    sheet.addSpan(38, 10, 1, 4);
    sheet.setValue(38, 10, "BOXPLOTSPARKLINE Settings:");
    sheet.setValue(39, 10, "Start scope of the sale:");
    sheet.setValue(40, 10, "End scope of the sale:");
    sheet.setValue(41, 10, "Start scope of expected sale:");
    sheet.setValue(42, 10, "End scope of expected sale:");
    sheet.addSpan(39, 10, 1, 3);
    sheet.addSpan(40, 10, 1, 3);
    sheet.addSpan(41, 10, 1, 3);
    sheet.addSpan(42, 10, 1, 3);
    sheet.setValue(39, 13, 0);
    sheet.setValue(40, 13, 10000);
    sheet.setValue(41, 13, 1000);
    sheet.setValue(42, 13, 8000);

    sheet.getRange(32, 6, 1, 7).backColor("Accent 4").foreColor("white");
    sheet.setFormula(33, 12, '=BOXPLOTSPARKLINE(H34:L34,"5ns",true,N40,N41,N42,N43,"#00FF7F",0,false)');
    sheet.setFormula(34, 12, '=BOXPLOTSPARKLINE(H35:L35,"5ns",true,N40,N41,N42,N43,"#00FF7F",0,false)');
    sheet.setFormula(35, 12, '=BOXPLOTSPARKLINE(H36:L36,"5ns",true,N40,N41,N42,N43,"#00FF7F",0,false)');
}

function addVariContent(sheet) {
    sheet.addSpan(0, 9, 1, 5);
    sheet.getCell(0, 9).value("Mobile Phone Contrast").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.getRange(1, 9, 1, 5).foreColor("white").backColor("Accent 4").hAlign(spreadNS.HorizontalAlign.center)
        .vAlign(spreadNS.VerticalAlign.center).wordWrap(true);
    sheet.addSpan(1, 12, 1, 2);
    sheet.addSpan(2, 12, 1, 2);
    sheet.addSpan(3, 12, 1, 2);
    sheet.addSpan(4, 12, 1, 2);
    sheet.setValue(1, 10, "Phone I");
    sheet.setValue(1, 11, "Phone II");
    sheet.setValue(1, 12, "Diagram");
    var data = [["Size(inch)", 5, 4.7],
        ["RAM(G)", 3, 1],
        ["Weight(g)", 149, 129]];
    sheet.setArray(2, 9, data);
    sheet.setFormula(2, 12, '=VARISPARKLINE(ROUND((K3-L3)/K3,2),0,,,,,TRUE)');
    sheet.setFormula(3, 12, '=VARISPARKLINE(ROUND((K4-L4)/K4,2),0,,,,,TRUE)');
    sheet.setFormula(4, 12, '=VARISPARKLINE(ROUND(-1*(K5-L5)/K5,2),0,,,,,TRUE)');
}

function addCascadeContent(sheet) {
    sheet.addSpan(6, 6, 1, 8);
    sheet.getCell(6, 6).value("Checkbook Register").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    for (var r = 7; r < 12; r++) {
        sheet.addSpan(r, 6, 1, 2);
        sheet.addSpan(r, 11, 1, 3);
    }
    sheet.setArray(7, 6, [
        ["", "", "InitialValue", 815.25, "\u03A3"],
        ["12/11/2012", "", "CVS", -200],
        ["12/12/2012", "", "Bank", 1000.12],
        ["12/13/2012", "", "Starbucks", -500.43],
        ["", "", "FinalValue"]
    ]);
    sheet.getRange(8, 6, 3, 1).formatter("MM/dd/yyyy");
    sheet.getRange(7, 9, 5, 1).formatter("#,###.00");
    sheet.getRange(8, 10, 3, 1).formatter("#,###.00");
    sheet.getCell(7, 10).hAlign(spreadNS.HorizontalAlign.center);
    sheet.getRange(7, 8, 1, 2).font("bold 14px Georgia");
    sheet.getRange(11, 8, 1, 2).font("bold 14px Georgia");

    sheet.setFormula(8, 10, "=J8 + J9");
    for (var r = 10; r <= 11; r++) {
        sheet.setFormula(r - 1, 10, "=J" + r + " + K" + (r - 1));
    }
    sheet.setFormula(11, 9, "=K11");
    sheet.getRange(7, 6, 1, 8).setBorder(new spreadNS.LineBorder("black", spreadNS.LineStyle.thin), {bottom: true});
    sheet.getRange(11, 6, 1, 8).setBorder(new spreadNS.LineBorder("black", spreadNS.LineStyle.medium), {top: true});
    sheet.setFormula(7, 11, '=CASCADESPARKLINE(J8:J12,1,I8:I12,,,"#8CBF64","#D6604D",false)');
    sheet.setFormula(8, 11, '=CASCADESPARKLINE(J8:J12,2,I8:I12,,,"#8CBF64","#D6604D",false)');
    sheet.setFormula(9, 11, '=CASCADESPARKLINE(J8:J12,3,I8:I12,,,"#8CBF64","#D6604D",false)');
    sheet.setFormula(10, 11, '=CASCADESPARKLINE(J8:J12,4,I8:I12,,,"#8CBF64","#D6604D",false)');
    sheet.setFormula(11, 11, '=CASCADESPARKLINE(J8:J12,5,I8:I12,,,"#8CBF64","#D6604D",false)');
}

function addSpreadContent(sheet) {
    sheet.addSpan(13, 6, 1, 7);
    sheet.getCell(13, 6).value("Student Grade Statistics").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.getRange(14, 6, 1, 8).foreColor("white").backColor("Accent 4").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.addSpan(15, 6, 2, 1);
    sheet.addSpan(14, 7, 1, 2);
    sheet.addSpan(14, 9, 1, 2);
    sheet.addSpan(14, 11, 1, 2);
    sheet.addSpan(15, 7, 2, 2);
    sheet.addSpan(15, 9, 2, 2);
    sheet.addSpan(15, 11, 2, 2);
    sheet.addSpan(15, 13, 2, 1);
    for (var r = 17; r <= 21; r++) {
        sheet.addSpan(r, 7, 1, 2);
        sheet.addSpan(r, 9, 1, 2);
        sheet.addSpan(r, 11, 1, 2);
    }
    sheet.setArray(14, 6, [["Name", "Chinese", "", "Math", "", "English", "", "Total"]]);
    sheet.setArray(17, 6, [
        ["Student 1", 70, "", 90, "", 51],
        ["Student 2", 99, "", 59, "", 63],
        ["Student 3", 89, "", 128, "", 74],
        ["Student 4", 93, "", 61, "", 53],
        ["Student 5", 106, "", 82, "", 80]
    ]);
    for (var i = 0; i <= 5; i++) {
        r = 17 + i;
        sheet.setFormula(r - 1, 13, "=Sum(H" + r + ":M" + r + ")");
    }
    sheet.setFormula(15, 7, "=SPREADSPARKLINE(H18:I22,TRUE,,,1,\"green\")");
    sheet.setFormula(15, 9, "=SPREADSPARKLINE(J18:K22,TRUE,,,3,\"green\")");
    sheet.setFormula(15, 11, "=SPREADSPARKLINE(L18:M22,TRUE,,,5,\"green\")");
    sheet.setFormula(15, 13, "=SPREADSPARKLINE(N18:N22,TRUE,,,6,\"green\")");
}

function addParetoContent(sheet) {
    sheet.addSpan(23, 8, 1, 6);
    sheet.getCell(23, 8).value("The Reason of Being Late").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.getRange(24, 8, 1, 6).foreColor("white").backColor("Accent 4").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    for (var r = 24; r < 30; r++) {
        sheet.addSpan(r, 11, 1, 3);
    }
    sheet.setArray(24, 8, [
        ["", "Points", "Color", "Diagram"],
        ["Traffic", 20, "#FF1493"],
        ["Child care", 15, "#FFE7BA"],
        ["Weather", 16, "#FFAEB9"],
        ["Overslept", 4, "#FF8C69"],
        ["Emergency", 1, "#FF83FA"]
    ]);
    sheet.addSpan(45, 6, 1, 3);
    sheet.setValue(45, 6, "PARETOSPARKLINE Settings:");
    sheet.setValue(46, 6, "target");
    sheet.setValue(46, 7, 0.5);
    sheet.setValue(47, 6, "target1");
    sheet.setValue(47, 7, 0.8);

    sheet.setFormula(25, 11, '=PARETOSPARKLINE(J26:J30,1,K26:K30,H47,H48,4,2,false)');
    sheet.setFormula(26, 11, '=PARETOSPARKLINE(J26:J30,2,K26:K30,H47,H48,4,2,false)');
    sheet.setFormula(27, 11, '=PARETOSPARKLINE(J26:J30,3,K26:K30,H47,H48,4,2,false)');
    sheet.setFormula(28, 11, '=PARETOSPARKLINE(J26:J30,4,K26:K30,H47,H48,4,2,false)');
    sheet.setFormula(29, 11, '=PARETOSPARKLINE(J26:J30,5,K26:K30,H47,H48,4,2,false)');
}

function addHBarContent(sheet) {
    row = 37, col = 0;
    sheet.addSpan(row, col, 1, 6);
    sheet.getCell(row, col).value("SPRINT 4").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.getRange(row + 1, 8, 1, 6).foreColor("white").backColor("Accent 4").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    for (var r = 38; r < 44; r++) {
        sheet.addSpan(r, 2, 1, 3);
    }
    sheet.getCell(++row, col).value("Name");
    sheet.getCell(++row, col).value("Employee1");
    sheet.getCell(++row, col).value("Employee2");
    sheet.getCell(++row, col).value("Employee3");
    sheet.getCell(++row, col).value("Employee4");
    sheet.getCell(++row, col).value("Employee5");
    row = 38, col++;
    sheet.getCell(row, col).value("Progress");
    sheet.getCell(++row, col).value(0.7);
    sheet.getCell(++row, col).value(0.1);
    sheet.getCell(++row, col).value(0.3);
    sheet.getCell(++row, col).value(1.1);
    sheet.getCell(++row, col).value(0.5);
    row = 38, col++;
    sheet.getCell(row, col).value("Diagram");
    sheet.getRange(38, 0, 1, 3).backColor("Accent 4").foreColor("white");
    sheet.setFormula(++row, col, getHBarFormula("B40"));
    sheet.setFormula(++row, col, getHBarFormula("B41"));
    sheet.setFormula(++row, col, getHBarFormula("B42"));
    sheet.setFormula(++row, col, getHBarFormula("B43"));
    sheet.setFormula(++row, col, getHBarFormula("B44"));
}

function addVBarContent(sheet) {
    sheet.setColumnWidth(15, 60);
    for (var c = 16; c < 23; c++) {
        sheet.setColumnWidth(c, 30);
    }
    sheet.addSpan(0, 15, 1, 8);
    sheet.getCell(0, 15).value("The Temperature Variation").font("20px Arial").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    sheet.getRange(1, 15, 1, 8).foreColor("white").backColor("Accent 4").hAlign(spreadNS.HorizontalAlign.center).vAlign(spreadNS.VerticalAlign.center);
    row = 2;
    sheet.addSpan(row, 15, 3, 1);
    sheet.addSpan(row + 3, 15, 3, 1);
    sheet.addSpan(row + 6, 15, 3, 1);
    sheet.setArray(1, 15, [["City", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"]]);
    var datas = [
        ["Austin", 5, 11, 19, 24, 21, 16, 6],
        ["Buffalo", -8, -3, -1, 3, 14, 6, -4],
        ["Chicago", -9, -2, 2, 18, 12, 5, -6]
    ];
    var colors = ["#0099FF", "#33FFFF", "#9E0142", "#D53E4F", "#F46D43", "#FDAE61", "#FEE08B"];
    sheet.setArray(11, 16, [colors]);
    for (var i = 0; i < datas.length; i++) {
        var row = 2 + 3 * i;
        sheet.setArray(row, 15, [datas[i]]);
        sheet.setArrayFormula(row + 1, 16, 2, 7, getVBarFormula(row + 1));
        sheet.setRowHeight(row + 1, 30);
        sheet.setRowHeight(row + 2, 30);
    }
}

function setCommentContent() {
    var sheet = new spreadNS.Worksheet("Comment");
    spread.addSheet(spread.getSheetCount(), sheet);
    sheet.suspendPaint();
    sheet.setColumnCount(50);

    for (var col = 2; col < 9; col++) {
        sheet.setColumnWidth(col, 100);
    }

    var Comment = spreadNS.Comments.Comment;
    var DisplayMode = spreadNS.Comments.DisplayMode;
    var commentText = "Hello, world!";
    var rowCount = 5, colCount = 4;
    var row = 2, col = 2;

    sheet.getCell(row, col).value("HoverShown").font("700 11pt Calibri");
    sheet.comments.add(row, col, commentText);
    sheet.getCell(row, col + colCount).value("AlwaysShown").font("700 11pt Calibri");
    sheet.comments.add(row, col + colCount, commentText)
        .displayMode(DisplayMode.alwaysShown);
    row = row + rowCount;
    sheet.getCell(row, col).value("Size").font("700 11pt Calibri");
    sheet.comments.add(row, col, commentText)
        .displayMode(DisplayMode.alwaysShown)
        .height(80)
        .width(200);
    sheet.getCell(row, col + colCount).value("Shadow").font("700 11pt Calibri");
    sheet.comments.add(row, col + colCount, commentText)
        .displayMode(DisplayMode.alwaysShown)
        .showShadow(true);
    row = row + rowCount;
    sheet.getCell(row, col).value("Font").font("700 11pt Calibri");
    sheet.comments.add(row, col, commentText)
        .displayMode(DisplayMode.alwaysShown)
        .fontFamily("Comic Sans MS")
        .fontSize("10pt")
        .fontStyle("italic")
        .fontWeight("bold");
    sheet.getCell(row, col + colCount).value("Color Opacity").font("700 11pt Calibri");
    sheet.comments.add(row, col + colCount, commentText)
        .displayMode(DisplayMode.alwaysShown)
        .foreColor("green")
        .backColor("yellow")
        .opacity(0.8);
    row = row + rowCount;
    sheet.getCell(row, col).value("Border").font("700 11pt Calibri");
    sheet.comments.add(row, col, commentText)
        .displayMode(DisplayMode.alwaysShown)
        .borderColor("green")
        .borderStyle("dotted")
        .borderWidth(2);
    sheet.getCell(row, col + colCount).value("Text Decoration").font("700 11pt Calibri");
    sheet.comments.add(row, col + colCount, commentText)
        .displayMode(DisplayMode.alwaysShown)
        .textDecoration(1)
        .horizontalAlign(1)
        .padding(new spreadNS.Comments.Padding(2));

    sheet.resumePaint();
}

function setPictureContent() {
    var sheet = new spreadNS.Worksheet("Picture");
    spread.addSheet(spread.getSheetCount(), sheet);
    sheet.suspendPaint();
    sheet.setColumnCount(50);

    sheet.setColumnWidth(0, 20);

    var url = "css/images/logo.png";
    var ImageLayout = spreadNS.ImageLayout;
    var row, col, rowCount = 11, colCount = 5,
        colWidth = sheet.getColumnWidth(1), rowHeight = sheet.getRowHeight(1),
        width = colCount * colWidth, height = rowCount * rowHeight,
        x = sheet.getColumnWidth(0) + colWidth, y = 2 * rowHeight,
        xOffset = (colCount + 2) * colWidth, yOffset = (rowCount + 2) * rowHeight;

    row = 1, col = 2;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Border").font("700 11pt Calibri");
    sheet.pictures.add("border_picture", url, x, y, width, height)
        .backColor("#000000")
        .borderColor("red")
        .borderWidth(4)
        .borderStyle("dotted")
        .borderRadius(5);

    col = col + colCount + 2;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Fixed Position").font("700 11pt Calibri");
    sheet.pictures.add("fixed_picture", url, x + xOffset, y, width, height)
        .backColor("#000000")
        .fixedPosition(true);

    row = row + rowCount + 2, col = 2;
    y += yOffset;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Stretch").font("700 11pt Calibri");
    sheet.pictures.add("stretch_picture", url, x, y, width, height)
        .backColor("#000000");

    col = col + colCount + 2;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Center").font("700 11pt Calibri");
    sheet.pictures.add("center_picture", url, x + xOffset, y, width, height)
        .backColor("#000000")
        .pictureStretch(ImageLayout.center);

    row = row + rowCount + 2, col = 2;
    y += yOffset;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("Zoom").font("700 11pt Calibri");
    sheet.pictures.add("zoom_picture", url, x, y, width, height)
        .backColor("#000000")
        .pictureStretch(ImageLayout.zoom);

    col = col + colCount + 2;
    sheet.addSpan(row, col, 1, colCount);
    sheet.getCell(row, col).value("None").font("700 11pt Calibri");
    sheet.pictures.add("none_picture", url, x + xOffset, y, width, height)
        .backColor("#000000")
        .pictureStretch(ImageLayout.none);

    sheet.resumePaint();
}

function setDataContent() {
    var sheet = new spreadNS.Worksheet("Data");
    spread.addSheet(spread.getSheetCount(), sheet);
    sheet.suspendPaint();
    sheet.setColumnCount(50);

    for (var col = 1; col < 6; col = col + 2) {
        for (var row = 2; row < 10; row++) {
            sheet.getCell(row, col).value(getRandomNumber());
        }
    }
    var row = 1, col = 1, rowCount = 8, colCount = 1;
    sheet.getCell(row, col).value("Sort A-Z").font("700 11pt Calibri");
    sheet.sortRange(row + 1, col, rowCount, colCount, true, [{index: col, ascending: true}]);

    col = col + 2;
    sheet.getCell(row, col).value("Sort Z-A").font("700 11pt Calibri");
    sheet.sortRange(row + 1, col, rowCount, colCount, true, [{index: col, ascending: false}]);

    col = col + 2;
    sheet.getCell(row, col).value("Filter").font("700 11pt Calibri");
    sheet.rowFilter(new spreadNS.Filter.HideRowFilter(new spreadNS.Range(row + 1, col, rowCount, colCount)));

    sheet.rowOutlines.group(12, 3);
    sheet.columnOutlines.group(8, 5);

    row = 12, col = 1;
    sheet.addSpan(row, col, 1, 9);
    sheet.getCell(row, col).value("Data Validation").vAlign(spreadNS.VerticalAlign.center).hAlign(spreadNS.HorizontalAlign.center).font("700 11pt Calibri");
    row = 13;
    sheet.getCell(row, col).value("List").font("700 11pt Calibri");
    sheet.getCell(row, col + 2).value("Number").font("700 11pt Calibri");
    sheet.getCell(row, col + 4).value("Date").font("700 11pt Calibri");
    sheet.getCell(row, col + 6).value("Formula").font("700 11pt Calibri");
    sheet.getCell(row, col + 8).value("TextLength").font("700 11pt Calibri");

    row = 14;
    var listValidator = DataValidation.createListValidator("Fruit,Vegetable,Food");
    listValidator.inputTitle("Please choose a category:");
    listValidator.inputMessage("Fruit, Vegetable, Food");
    sheet.getCell(row + 1, col).value("Vegetable");
    sheet.getCell(row + 2, col).value("Home");
    sheet.getCell(row + 3, col).value("Fruit");
    sheet.getCell(row + 4, col).value("Company");
    sheet.getCell(row + 5, col).value("Food");
    for (var r = row + 1; r < row + 6; r++) {
        sheet.setDataValidator(r, col, listValidator);
    }

    col = col + 2;
    var numberValidator = DataValidation.createNumberValidator(ComparisonOperators.between, 0, 100, true);
    numberValidator.inputMessage("Value should Between 0 ~ 100");
    numberValidator.inputTitle("Tip");
    sheet.getCell(row + 1, col).value(-12);
    sheet.getCell(row + 2, col).value(30);
    sheet.getCell(row + 3, col).value(80);
    sheet.getCell(row + 4, col).value(-35);
    sheet.getCell(row + 5, col).value(66);
    for (var r = row + 1; r < row + 6; r++) {
        sheet.setDataValidator(r, col, numberValidator);
    }

    col = col + 2;
    sheet.setColumnWidth(col, 100);
    var currentDate = new Date().toLocaleDateString().replace(/\u200E/g, ''); // this "replace" is just for IE, the date string contains some special characters
    var dateValidator = DataValidation.createDateValidator(ComparisonOperators.lessThan, currentDate, currentDate);
    dateValidator.inputMessage("Enter a date Less than " + currentDate);
    dateValidator.inputTitle("Tip");
    sheet.getCell(row + 1, col).value("2014/08/20");
    sheet.getCell(row + 2, col).value("2050/10/12");
    sheet.getCell(row + 3, col).value("1993/05/23");
    sheet.getCell(row + 4, col).value("2020/01/02");
    sheet.getCell(row + 5, col).value("2015/10/20");
    for (var r = row + 1; r < row + 6; r++) {
        sheet.setDataValidator(r, col, dateValidator);
    }

    col = col + 2;
    var formula = getCellPositionString(sheet, row + 6, col + 1) + "<100";
    var formulaValidator = DataValidation.createFormulaValidator(formula);
    formulaValidator.inputTitle("Tip");
    sheet.getCell(row + 1, col).value(20);
    sheet.getCell(row + 2, col).value(300);
    sheet.getCell(row + 3, col).value(2);
    sheet.getCell(row + 4, col).value(-35);
    var sumFormula = "=SUM(" + getCellPositionString(sheet, row + 2, col + 1)
        + ":" + getCellPositionString(sheet, row + 5, col + 1) + ")";
    sheet.getCell(row + 5, col).formula(sumFormula);
    formulaValidator.inputMessage("Be sure " + sumFormula.substr(1) + " less than 100");
    sheet.setDataValidator(row + 5, col, formulaValidator);

    col = col + 2;
    sheet.setColumnWidth(col, 120);
    var textLengthValidator = DataValidation.createTextLengthValidator(ComparisonOperators.lessThan, 6, 6);
    textLengthValidator.inputMessage("Text length should Less than 6");
    textLengthValidator.inputTitle("Tip");
    sheet.getCell(row + 1, col).value("Hello, SpreadJS");
    sheet.getCell(row + 2, col).value("God");
    sheet.getCell(row + 3, col).value("Word");
    sheet.getCell(row + 4, col).value("Warning");
    sheet.getCell(row + 5, col).value("Boy");
    for (var r = row + 1; r < row + 6; r++) {
        sheet.setDataValidator(r, col, textLengthValidator);
    }

    spread.options.highlightInvalidData = true;
    sheet.resumePaint();
}

function setSlicerContent() {
    var sheet = new spreadNS.Worksheet("Slicer");
    spread.addSheet(spread.getSheetCount(), sheet);
    sheet.suspendPaint();
    sheet.setColumnCount(50);

    var tableName = "slicerTable";
    var dataColumns = ["Name", "Age", "Sex", "Address", "Weight", "Height"];
    var data = [
        ["bob", "36", "man", "Beijing", "80", "180"],
        ["Betty", "28", "woman", "Xi'an", "52", "168"],
        ["Gary", "23", "man", "NewYork", "63", "175"],
        ["Hunk", "45", "man", "Beijing", "80", "171"],
        ["Cherry", "37", "woman", "Shanghai", "58", "161"],
        ["Eva", "30", "woman", "NewYork", "63", "180"]];
    sheet.tables.addFromDataSource(tableName, 6, 3, data);
    var table = sheet.tables.findByName(tableName);
    table.setColumnName(0, dataColumns[0]);
    table.setColumnName(1, dataColumns[1]);
    table.setColumnName(2, dataColumns[2]);
    table.setColumnName(3, dataColumns[3]);
    table.setColumnName(4, dataColumns[4]);
    table.setColumnName(5, dataColumns[5]);

    var slicer0 = sheet.slicers.add("slicer1", tableName, "Name");
    slicer0.position(new spreadNS.Point(50, 300));

    var slicer1 = sheet.slicers.add("slicer2", tableName, "Sex");
    slicer1.position(new spreadNS.Point(275, 300));

    var slicer2 = sheet.slicers.add("slicer3", tableName, "Height");
    slicer2.position(new spreadNS.Point(500, 300));

    sheet.resumePaint();
}
// Sample Content related items (end)

function getCellInfo(sheet, row, column) {
    var result = {type: ""}, object;

    if ((object = sheet.comments.get(row, column))) {
        result.type = "comment";
    } else if ((object = sheet.tables.find(row, column))) {
        result.type = "table";
    }

    result.object = object;

    return result;
}

var specialTabNames = ["table", "picture", "comment", "sparklineEx", "slicer"];
var specialTabRefs = specialTabNames.map(function (name) {
    return "#" + name + "Tab";
});

function isSpecialTabSelected() {
    var href = $(".insp-container ul.nav-tabs li.active a").attr("href");

    return specialTabRefs.indexOf(href) !== -1;
}

function getTabItem(tabName) {
    return $(".insp-container ul.nav-tabs a[href='#" + tabName + "Tab']").parent();
}

function setActiveTab(tabName) {
    // show / hide tabs
    var $target = getTabItem(tabName),
        $spreadTab = getTabItem("spread");

    if (specialTabNames.indexOf(tabName) >= 0) {
        if ($target.hasClass("hidden")) {
            hideSpecialTabs(false);

            $target.removeClass("hidden");
            $spreadTab.addClass("hidden");
            $("a", $target).tab("show");
        }
    } else {
        if ($spreadTab.hasClass("hidden")) {
            $spreadTab.removeClass("hidden");
            hideSpecialTabs(true);
        }
        if (!$target.hasClass("active")) {
            // do not switch from Data to Cell tab
            if (!(tabName === "cell" && getTabItem("data").hasClass("active"))) {
                $("a", $target).tab("show");
            }
        }
    }
}

function hideSpecialTabs(clearCache) {
    specialTabNames.forEach(function (name) {
        getTabItem(name).addClass("hidden");
    });

    if (clearCache) {
        clearCachedItems();
    }
}

function getActualRange(range, maxRowCount, maxColCount) {
    var row = range.row < 0 ? 0 : range.row;
    var col = range.col < 0 ? 0 : range.col;
    var rowCount = range.rowCount < 0 ? maxRowCount : range.rowCount;
    var colCount = range.colCount < 0 ? maxColCount : range.colCount;

    return new spreadNS.Range(row, col, rowCount, colCount);
}

function getActualCellRange(sheet, cellRange, rowCount, columnCount) {
    if (cellRange.row === -1 && cellRange.col === -1) {
        return new spreadNS.CellRange(sheet, 0, 0, rowCount, columnCount);
    }
    else if (cellRange.row === -1) {
        return new spreadNS.CellRange(sheet, 0, cellRange.col, rowCount, cellRange.colCount);
    }
    else if (cellRange.col === -1) {
        return new spreadNS.CellRange(sheet, cellRange.row, 0, cellRange.rowCount, columnCount);
    }
    return new spreadNS.CellRange(sheet, cellRange.row, cellRange.col, cellRange.rowCount, cellRange.colCount);
}

function setStyleFont(sheet, prop, isLabelStyle, optionValue1, optionValue2) {
    var styleEle = document.getElementById("setfontstyle"),
        selections = sheet.getSelections(),
        rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount(),
        defaultStyle = sheet.getDefaultStyle();

    function updateStyleFont(style) {
        if (!style.font) {
            style.font = defaultStyle.font || "11pt Calibri";
        }
        styleEle.style.font = style.font;
        var styleFont = $(styleEle).css(prop);
        if (styleFont === optionValue1[0] || styleFont === optionValue1[1]) {
            if (defaultStyle.font) {
                styleEle.style.font = defaultStyle.font;
                var defaultFontProp = $(styleEle).css(prop);
                styleEle.style.font = style.font;
                $(styleEle).css(prop, defaultFontProp);
            }
            else {
                $(styleEle).css(prop, optionValue2);
            }
        } else {
            $(styleEle).css(prop, optionValue1[0]);
        }
        style.font = styleEle.style.font;
    }

    sheet.suspendPaint();
    for (var n = 0; n < selections.length; n++) {
        var sel = getActualCellRange(sheet, selections[n], rowCount, columnCount);
        for (var r = sel.row; r < sel.row + sel.rowCount; r++) {
            for (var c = sel.col; c < sel.col + sel.colCount; c++) {
                var style = sheet.getStyle(r, c);
                if (!style) {
                    style = new spreadNS.Style();
                }
                // reset themeFont to make sure font be used
                style.themeFont = undefined;
                if (isLabelStyle) {
                    if (!style.labelOptions) {
                        style.labelOptions = {};
                    }
                    updateStyleFont(style.labelOptions);
                } else {
                    updateStyleFont(style)
                }
                sheet.setStyle(r, c, style);
            }
        }
    }
    sheet.resumePaint();
}


function attachEvents() {
    attachToolbarItemEvents();
    attachSpreadEvents();
    attachConditionalFormatEvents();
    attachDataValidationEvents();
    attachOtherEvents();
    attachCellTypeEvents();
    attachLockCellsEvent();
    attachBorderTypeClickEvents();
    attachSparklineSettingEvents();
}

// Border Type related items
function syncDisabledBorderType() {
    var sheet = spread.getActiveSheet();
    var selections = sheet.getSelections(), selectionsLength = selections.length;
    var isDisabledInsideBorder = true;
    var isDisabledHorizontalBorder = true;
    var isDisabledVerticalBorder = true;
    for (var i = 0; i < selectionsLength; i++) {
        var selection = selections[i];
        var col = selection.col, row = selection.row,
            rowCount = selection.rowCount, colCount = selection.colCount;
        if (isDisabledHorizontalBorder) {
            isDisabledHorizontalBorder = rowCount === 1;
        }
        if (isDisabledVerticalBorder) {
            isDisabledVerticalBorder = colCount === 1;
        }
        if (isDisabledInsideBorder) {
            isDisabledInsideBorder = rowCount === 1 || colCount === 1;
        }
    }
    [isDisabledInsideBorder, isDisabledVerticalBorder, isDisabledHorizontalBorder].forEach(function (value, index) {
        var $item = $("div.group-item:eq(" + (index * 3 + 1) + ")");
        if (value) {
            $item.addClass("disable");
        } else {
            $item.removeClass("disable");
        }
    });
}

function getBorderSettings(borderType, borderStyle) {
    var result = [];

    switch (borderType) {
        case "outside":
            result.push({lineStyle: borderStyle, options: {outline: true}});
            break;

        case "inside":
            result.push({lineStyle: borderStyle, options: {innerHorizontal: true}});
            result.push({lineStyle: borderStyle, options: {innerVertical: true}});
            break;

        case "all":
        case "none":
            result.push({lineStyle: borderStyle, options: {all: true}});
            break;

        case "left":
            result.push({lineStyle: borderStyle, options: {left: true}});
            break;

        case "innerVertical":
            result.push({lineStyle: borderStyle, options: {innerVertical: true}});
            break;

        case "right":
            result.push({lineStyle: borderStyle, options: {right: true}});
            break;

        case "top":
            result.push({lineStyle: borderStyle, options: {top: true}});
            break;

        case "innerHorizontal":
            result.push({lineStyle: borderStyle, options: {innerHorizontal: true}});
            break;

        case "bottom":
            result.push({lineStyle: borderStyle, options: {bottom: true}});
            break;
    }

    return result;
}

function setBorderlines(sheet, borderType, borderStyle, borderColor) {
    function setSheetBorder(setting) {
        var lineBorder = new spreadNS.LineBorder(borderColor, setting.lineStyle);
        sel.setBorder(lineBorder, setting.options);
        setRangeBorder(sheet, sel, setting.options);
    }

    var settings = getBorderSettings(borderType, borderStyle);
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    sheet.suspendPaint();
    var sels = sheet.getSelections();

    for (var n = 0; n < sels.length; n++) {
        var sel = getActualCellRange(sheet, sels[n], rowCount, columnCount);
        settings.forEach(setSheetBorder);
    }
    sheet.resumePaint();
}

function attachBorderTypeClickEvents() {
    var $groupItems = $(".group-item>div");
    $groupItems.bind("mousedown", function () {
        if ($(this).parent().hasClass("disable")) {
            return;
        }
        var name = $(this).data("name").split("Border")[0];
        applyBorderSetting(name);
    });
}

function applyBorderSetting(name) {
    var sheet = spread.getActiveSheet();
    var borderLine = getBorderLineType($("#border-line-type").attr("class"));
    var borderColor = getBackgroundColor("borderColor");
    setBorderlines(sheet, name, borderLine, borderColor);
}

function getBorderLineType(className) {
    switch (className) {
        case "no-border":
            return spreadNS.LineStyle.empty;

        case "line-style-hair":
            return spreadNS.LineStyle.hair;

        case "line-style-dotted":
            return spreadNS.LineStyle.dotted;

        case "line-style-dash-dot-dot":
            return spreadNS.LineStyle.dashDotDot;

        case "line-style-dash-dot":
            return spreadNS.LineStyle.dashDot;

        case "line-style-dashed":
            return spreadNS.LineStyle.dashed;

        case "line-style-thin":
            return spreadNS.LineStyle.thin;

        case "line-style-medium-dash-dot-dot":
            return spreadNS.LineStyle.mediumDashDotDot;

        case "line-style-slanted-dash-dot":
            return spreadNS.LineStyle.slantedDashDot;

        case "line-style-medium-dash-dot":
            return spreadNS.LineStyle.mediumDashDot;

        case "line-style-medium-dashed":
            return spreadNS.LineStyle.mediumDashed;

        case "line-style-medium":
            return spreadNS.LineStyle.medium;

        case "line-style-thick":
            return spreadNS.LineStyle.thick;

        case "line-style-double":
            return spreadNS.LineStyle.double;
    }
}

function processBorderLineSetting(name) {
    var $borderLineType = $('#border-line-type');
    $borderLineType.text("");
    $borderLineType.removeClass();
    switch (name) {
        case "none":
            $('#border-line-type').text(getResource("cellTab.border.noBorder"));
            $('#border-line-type').addClass("no-border");
            return;

        case "hair":
            $('#border-line-type').addClass("line-style-hair");
            break;

        case "dotted":
            $('#border-line-type').addClass("line-style-dotted");
            break;

        case "dash-dot-dot":
            $('#border-line-type').addClass("line-style-dash-dot-dot");
            break;

        case "dash-dot":
            $('#border-line-type').addClass("line-style-dash-dot");
            break;

        case "dashed":
            $('#border-line-type').addClass("line-style-dashed");
            break;

        case "thin":
            $('#border-line-type').addClass("line-style-thin");
            break;

        case "medium-dash-dot-dot":
            $('#border-line-type').addClass("line-style-medium-dash-dot-dot");
            break;

        case "slanted-dash-dot":
            $('#border-line-type').addClass("line-style-slanted-dash-dot");
            break;

        case "medium-dash-dot":
            $('#border-line-type').addClass("line-style-medium-dash-dot");
            break;

        case "medium-dashed":
            $('#border-line-type').addClass("line-style-medium-dashed");
            break;

        case "medium":
            $('#border-line-type').addClass("line-style-medium");
            break;

        case "thick":
            $('#border-line-type').addClass("line-style-thick");
            break;

        case "double":
            $('#border-line-type').addClass("line-style-double");
            break;

        default:
            console.log("processBorderLineSetting not add for ", name);
            break;
    }
}

function setRangeBorder(sheet, range, options) {
    var outline = options.all || options.outline,
        rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount(),
        startRow = range.row, endRow = startRow + range.rowCount - 1,
        startCol = range.col, endCol = startCol + range.colCount - 1;

    // update related borders for all cells arround the range

    // left side 
    if ((startCol > 0) && (outline || options.left)) {
        sheet.getRange(startRow, startCol - 1, range.rowCount, 1).borderRight(undefined);
    }
    // top side
    if ((startRow > 0) && (outline || options.top)) {
        sheet.getRange(startRow - 1, startCol, 1, range.colCount).borderBottom(undefined);
    }
    // right side
    if ((endCol < columnCount - 1) && (outline || options.right)) {
        sheet.getRange(startRow, endCol + 1, range.rowCount, 1).borderLeft(undefined);
    }
    // bottom side
    if ((endRow < rowCount - 1) && (outline || options.bottom)) {
        sheet.getRange(endRow + 1, startCol, 1, range.colCount).borderTop(undefined);
    }
}
// Border Type related items (end)

function attachOtherEvents() {
    $("div.table-format-item").click(changeTableStyle);
    $("div.slicer-format-item").click(changeSlicerStyle);
    $("#spreadContextMenu a").click(processContextMenuClicked);
    $("#fileSelector").change(processFileSelected);
    $("#sparklineextypes button").click(processAddSparklineEx);
}

function processFileSelected() {
    var file = this.files[0],
        action = $(this).data("action");

    if (!file) return false;

    // clear to make sure change event occures even when same file selected again
    $("#fileSelector").val("");

    if (action === "doImport") {
        return importFile(file);
    }

    if (!/image\/\w+/.test(file.type)) {
        alert(getResource("messages.imageFileRequired"));
        return false;
    }
    var reader = new FileReader();
    reader.onload = function () {
        switch (action) {
            case "addpicture":
                addPicture(this.result);
                break;
        }
    };
    reader.readAsDataURL(file);
}

var PICTURE_ROWCOUNT = 16, PICTURE_COLUMNCOUNT = 10;
function addPicture(pictureUrl) {
    var sheet = spread.getActiveSheet();
    var defaults = sheet.defaults, rowHeight = defaults.rowHeight, colWidth = defaults.colWidth;
    var sel = sheet.getSelections()[0];
    if (pictureUrl !== "" && sel) {
        sheet.suspendPaint();

        var cr = getActualRange(sel, sheet.getRowCount(), sheet.getColumnCount());
        var name = "Picture" + pictureIndex;
        pictureIndex++;

        // prepare and adjust the range for add picture
        var row = cr.row, col = cr.col,
            endRow = row + PICTURE_ROWCOUNT,
            endColumn = col + PICTURE_COLUMNCOUNT,
            rowCount = sheet.getRowCount(),
            columnCount = sheet.getColumnCount();

        if (endRow > rowCount) {
            endRow = rowCount - 1;
            row = endRow - PICTURE_ROWCOUNT;
        }

        if (endColumn > columnCount) {
            endColumn = columnCount - 1;
            col = endColumn - PICTURE_COLUMNCOUNT;
        }

        var picture = sheet.pictures.add(name, pictureUrl, col * colWidth, row * rowHeight, (endColumn - col) * colWidth, (endRow - row) * rowHeight)
            .backColor("#FFFFFF").borderColor("#000000")
            .borderStyle("solid").borderWidth(1).borderRadius(3);
        sheet.resumePaint();

        spread.focus();
        picture.isSelected(true);
    }
}

function updatePositionBox(sheet) {
    var selection = sheet.getSelections().slice(-1)[0];
    if (selection) {
        var position;
        if (!isShiftKey) {
            position = getCellPositionString(sheet,
                sheet.getActiveRowIndex() + 1,
                sheet.getActiveColumnIndex() + 1, selection);
        }
        else {
            position = getSelectedRangeString(sheet, selection);
        }

        $("#positionbox").val(position);
    }
}

function syncCellRelatedItems() {
    updateMergeButtonsState();
    syncDisabledLockCells();
    syncDisabledBorderType();

    // reset conditional format setting
    var item = setDropDownValueByIndex($("#conditionalFormatType"), -1);
    processConditionalFormatDetailSetting(item.value, true);
    // sync cell type related information
    syncCellTypeInfo();
}

function syncCellTypeInfo() {
    function updateButtonCellTypeInfo(cellType) {
        setNumberValue("buttonCellTypeMarginTop", cellType.marginTop());
        setNumberValue("buttonCellTypeMarginRight", cellType.marginRight());
        setNumberValue("buttonCellTypeMarginBottom", cellType.marginBottom());
        setNumberValue("buttonCellTypeMarginLeft", cellType.marginLeft());
        setTextValue("buttonCellTypeText", cellType.text());
        setColorValue("buttonCellTypeBackColor", cellType.buttonBackColor());
    }

    function updateCheckBoxCellTypeInfo(cellType) {
        setTextValue("checkboxCellTypeCaption", cellType.caption());
        setTextValue("checkboxCellTypeTextTrue", cellType.textTrue());
        setTextValue("checkboxCellTypeTextIndeterminate", cellType.textIndeterminate());
        setTextValue("checkboxCellTypeTextFalse", cellType.textFalse());
        setDropDownValue("checkboxCellTypeTextAlign", cellType.textAlign());
        setCheckValue("checkboxCellTypeIsThreeState", cellType.isThreeState());
    }

    function updateComboBoxCellTypeInfo(cellType) {
        setDropDownValue("comboboxCellTypeEditorValueType", cellType.editorValueType());
        var items = cellType.items(),
            texts = items.map(function (item) {
                return item.text || item;
            }).join(","),
            values = items.map(function (item) {
                return item.value || item;
            }).join(",");

        setTextValue("comboboxCellTypeItemsText", texts);
        setTextValue("comboboxCellTypeItemsValue", values);
    }

    function updateHyperLinkCellTypeInfo(cellType) {
        setColorValue("hyperlinkCellTypeLinkColor", cellType.linkColor());
        setColorValue("hyperlinkCellTypeVisitedLinkColor", cellType.visitedLinkColor());
        setTextValue("hyperlinkCellTypeText", cellType.text());
        setTextValue("hyperlinkCellTypeLinkToolTip", cellType.linkToolTip());
    }

    var sheet = spread.getActiveSheet(),
        index,
        cellType = sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex()).cellType();

    if (cellType instanceof spreadNS.CellTypes.Button) {
        index = 0;
        updateButtonCellTypeInfo(cellType);
    } else if (cellType instanceof spreadNS.CellTypes.CheckBox) {
        index = 1;
        updateCheckBoxCellTypeInfo(cellType);
    } else if (cellType instanceof spreadNS.CellTypes.ComboBox) {
        index = 2;
        updateComboBoxCellTypeInfo(cellType);
    } else if (cellType instanceof spreadNS.CellTypes.HyperLink) {
        index = 3;
        updateHyperLinkCellTypeInfo(cellType);
    } else {
        index = -1;
    }
    var cellTypeItem = setDropDownValueByIndex($("#cellTypes"), index);
    processCellTypeSetting(cellTypeItem.value, true);

    if (index >= 0) {
        var $group = $("#groupCellType");
        if ($group.find(".group-state").hasClass("fa-caret-right")) {
            $group.click();
        }
    }
}

function onCellSelected() {
    $("#addslicer").addClass("hidden");
    var sheet = spread.getActiveSheet(),
        row = sheet.getActiveRowIndex(),
        column = sheet.getActiveColumnIndex();
    if (showSparklineSetting(row, column)) {
        setActiveTab("sparklineEx");
        return;
    }
    var cellInfo = getCellInfo(sheet, row, column),
        cellType = cellInfo.type;

    syncCellRelatedItems();
    updatePositionBox(sheet);
    updateCellStyleState(sheet, row, column);

    var tabType = "cell";

    clearCachedItems();

    // add map from cell type to tab type here
    if (cellType === "table") {
        tabType = "table";
        syncTablePropertyValues(sheet, cellInfo.object);
        $("#addslicer").removeClass("hidden");
    } else if (cellType === "comment") {
        tabType = "comment";
        syncCommentPropertyValues(sheet, cellInfo.object);
    }

    setActiveTab(tabType);
}

var _activeComment;

function syncCommentPropertyValues(sheet, comment) {
    _activeComment = comment;

    // General
    setCheckValue("commentDynamicSize", comment.dynamicSize());
    setCheckValue("commentDynamicMove", comment.dynamicMove());
    setCheckValue("commentLockText", comment.lockText());
    setCheckValue("commentShowShadow", comment.showShadow());

    // Font
    setDropDownText($("#commentTab div.insp-dropdown-list[data-name='commentFontFamily']"), comment.fontFamily());
    setDropDownText($("#commentTab div.insp-dropdown-list[data-name='commentFontSize']"), parseFloat(comment.fontSize()));
    setDropDownText($("#commentTab div.insp-dropdown-list[data-name='commentFontStyle']"), comment.fontStyle());
    setDropDownText($("#commentTab div.insp-dropdown-list[data-name='commentFontWeight']"), comment.fontWeight());
    var textDecoration = comment.textDecoration();
    var TextDecorationType = spreadNS.TextDecorationType;
    setFontStyleButtonActive("comment-underline", (textDecoration & TextDecorationType.underline) === TextDecorationType.underline);
    setFontStyleButtonActive("comment-overline", (textDecoration & TextDecorationType.overline) === TextDecorationType.overline);
    setFontStyleButtonActive("comment-strikethrough", (textDecoration & TextDecorationType.lineThrough) === TextDecorationType.lineThrough);

    // Border
    setNumberValue("commentBorderWidth", comment.borderWidth());
    setDropDownText($("#commentTab div.insp-dropdown-list[data-name='commentBorderStyle']"), comment.borderStyle());
    setColorValue("commentBorderColor", comment.borderColor());

    // Appearance
    setDropDownValue($("#commentTab div.insp-dropdown-list[data-name='commentHorizontalAlign']"), comment.horizontalAlign());
    setDropDownValue($("#commentTab div.insp-dropdown-list[data-name='commentDisplayMode']"), comment.displayMode());
    setColorValue("commentForeColor", comment.foreColor());
    setColorValue("commentBackColor", comment.backColor());
    setTextValue("commentPadding", getPaddingString(comment.padding()));
    setNumberValue("commentOpacity", comment.opacity() * 100);
}

function getPaddingString(padding) {
    if (!padding) return "";

    return [padding.top, padding.right, padding.bottom, padding.left].join(", ");
}

function clearCachedItems() {
    _activePicture = null;
    _activeComment = null;
    _activeTable = null;
}

var _activeTable;
function syncTablePropertyValues(sheet, table) {
    _activeTable = table;

    setCheckValue("tableFilterButton", table.filterButtonVisible());

    setCheckValue("tableHeaderRow", table.showHeader());
    setCheckValue("tableTotalRow", table.showFooter());

    setCheckValue("tableFirstColumn", table.highlightFirstColumn());
    setCheckValue("tableLastColumn", table.highlightLastColumn());
    setCheckValue("tableBandedRows", table.bandRows());
    setCheckValue("tableBandedColumns", table.bandColumns());
    var tableStyle = table.style(),
        styleName = tableStyle && table.style().name();

    $("#tableStyles .table-format-item").removeClass("table-format-item-selected");
    if (styleName) {
        $("#tableStyles .table-format-item div[data-name='" + styleName.toLowerCase() + "']").parent().addClass("table-format-item-selected");
    }
    setTextValue("tableName", table.name());
}

function changeTableStyle() {
    if (_activeTable) {
        spread.suspendPaint();

        var styleName = $(">div", this).data("name");

        _activeTable.style(spreadNS.Tables.TableThemes[styleName]);

        $("#tableStyles .table-format-item").removeClass("table-format-item-selected");
        $(this).addClass("table-format-item-selected");

        spread.resumePaint();
    }
}

var _activePicture;
function syncPicturePropertyValues(sheet, picture) {
    _activePicture = picture;

    // General
    if (picture.dynamicMove()) {
        if (picture.dynamicSize()) {
            setRadioItemChecked("pictureMoveAndSize", "picture-move-size");
        }
        else {
            setRadioItemChecked("pictureMoveAndSize", "picture-move-nosize");
        }
    }
    else {
        setRadioItemChecked("pictureMoveAndSize", "picture-nomove-size");
    }
    setCheckValue("pictureFixedPosition", picture.fixedPosition());

    // Border
    setNumberValue("pictureBorderWidth", picture.borderWidth());
    setNumberValue("pictureBorderRadius", picture.borderRadius());
    setDropDownText($("#pictureTab div.insp-dropdown-list[data-name='pictureBorderStyle']"), picture.borderStyle());
    setColorValue("pictureBorderColor", picture.borderColor());

    // Appearance
    setDropDownValue($("#pictureTab div.insp-dropdown-list[data-name='pictureStretch']"), picture.pictureStretch());
    setColorValue("pictureBackColor", picture.backColor());

    $("#positionbox").val(picture.name());
}

var _floatInspector = false;

function adjustInspectorDisplay() {
    var $inspectorContainer = $(".insp-container"),
        $contentContainer = $("#inner-content-container"),
        toggleInspectorClasses;

    if (_floatInspector) {
        $inspectorContainer.draggable("enable");
        $inspectorContainer.addClass("float-inspector");
        $contentContainer.addClass("float-inspector");
        toggleInspectorClasses = ["fa-angle-down", "fa-angle-up"];
        $("#inner-content-container").css({right: 0});
    } else {
        $inspectorContainer.draggable("disable");
        $inspectorContainer.removeClass("float-inspector");
        $inspectorContainer.css({left: "auto", top: 0});
        $contentContainer.removeClass("float-inspector");
        toggleInspectorClasses = ["fa-angle-left", "fa-angle-right"];
    }

    // update toggleInspector
    var classIndex = ($(".insp-container:visible").length > 0) ? 1 : 0;
    $("#toggleInspector > span")
        .removeClass("fa-angle-left fa-angle-right fa-angle-up fa-angle-down")
        .addClass(toggleInspectorClasses[classIndex]);
}
function processMediaQueryResponse(mql) {
    if (mql.matches) {
        if (!_floatInspector) {
            _floatInspector = true;
            adjustInspectorDisplay();
        }
    } else {
        if (_floatInspector) {
            _floatInspector = false;
            adjustInspectorDisplay();
        }
    }
}

function checkMediaSize() {
    var mql = window.matchMedia("screen and (max-width: 768px)");
    processMediaQueryResponse(mql);
    adjustInspectorDisplay();
    mql.addListener(processMediaQueryResponse);
}

function toggleInspector() {
    if ($(".insp-container:visible").length > 0) {
        $(".insp-container").hide();
        if (!_floatInspector) {
            $("#inner-content-container").css({right: 0});
            $("span", this).removeClass("fa-angle-right fa-angle-up fa-angle-down").addClass("fa-angle-left");
        } else {
            $("#inner-content-container").css({right: 0});
            $("span", this).removeClass("fa-angle-right fa-angle-left fa-angle-up").addClass("fa-angle-down");
        }

        $(this).attr("title", uiResource.toolBar.showInspector);
    } else {
        $(".insp-container").show();
        if (!_floatInspector) {
            $("#inner-content-container").css({right: "301px"});
            $("span", this).removeClass("fa-angle-left fa-angle-up fa-angle-down").addClass("fa-angle-right");
        } else {
            $("#inner-content-container").css({right: 0});
            $("span", this).removeClass("fa-angle-right fa-angle-left fa-angle-down").addClass("fa-angle-up");
        }

        $(this).attr("title", uiResource.toolBar.hideInspector);
    }
    spread.refresh();
}

function attachToolbarItemEvents() {
    $("#addtable").click(function () {
        var sheet = spread.getActiveSheet(),
            row = sheet.getActiveRowIndex(),
            column = sheet.getActiveColumnIndex(),
            name = "Table" + tableIndex,
            rowCount = 1,
            colCount = 1;

        tableIndex++;

        var selections = sheet.getSelections();

        if (selections.length > 0) {
            var range = selections[0],
                r = range.row,
                c = range.col;

            rowCount = range.rowCount,
                colCount = range.colCount;

            // update row / column for whole column / row was selected
            if (r >= 0) {
                row = r;
            }
            if (c >= 0) {
                column = c;
            }
        }

        sheet.suspendPaint();
        try {
            // handle exception if the specified range intersect with other table etc.
            sheet.tables.add(name, row, column, rowCount, colCount, spreadNS.Tables.TableThemes.light2);
        } catch (e) {
            alert(e.message);
        }
        sheet.resumePaint();

        spread.focus();

        onCellSelected();
    });

    $("#addcomment").click(function () {
        var sheet = spread.getActiveSheet(),
            row = sheet.getActiveRowIndex(),
            column = sheet.getActiveColumnIndex(),
            comment;

        sheet.suspendPaint();
        comment = sheet.comments.add(row, column, new Date().toLocaleString());
        sheet.resumePaint();

        comment.commentState(spreadNS.Comments.CommentState.edit);
    });

    $("#addpicture, #doImport").click(function () {
        $("#fileSelector").data("action", this.id);
        $("#fileSelector").click();
    });

    $("#toggleInspector").click(toggleInspector);

    $("#doClear").click(function () {
        var $dropdown = $("#clearActionList"),
            $this = $(this),
            offset = $this.offset();

        $dropdown.css({left: offset.left, top: offset.top + $this.outerHeight()});
        $dropdown.show();
        processEventListenerHandleClosePopup(true);
    });

    $("#doExport").click(function () {
        var $dropdown = $("#exportActionList"),
            $this = $(this),
            offset = $this.offset();

        $dropdown.css({left: offset.left, top: offset.top + $this.outerHeight()});
        $dropdown.show();
        processEventListenerHandleClosePopup(true);
    });

    $("#addslicer").click(processAddSlicer);
}

// Protect Sheet related items
function getCurrentSheetProtectionOption(sheet) {
    var options = sheet.options.protectionOptions;
    if (options.allowSelectLockedCells || options.allowSelectLockedCells === undefined) {
        setCheckValue("checkboxSelectLockedCells", true);
    }
    else {
        setCheckValue("checkboxSelectLockedCells", false);
    }
    if (options.allowSelectUnlockedCells || options.allowSelectUnlockedCells === undefined) {
        setCheckValue("checkboxSelectUnlockedCells", true);
    }
    else {
        setCheckValue("checkboxSelectUnlockedCells", false);
    }
    if (options.allowSort) {
        setCheckValue("checkboxSort", true);
    }
    else {
        setCheckValue("checkboxSort", false);
    }
    if (options.allowFilter) {
        setCheckValue("checkboxUseAutoFilter", true);
    }
    else {
        setCheckValue("checkboxUseAutoFilter", false);
    }
    if (options.allowResizeRows) {
        setCheckValue("checkboxResizeRows", true);
    }
    else {
        setCheckValue("checkboxResizeRows", false);
    }
    if (options.allowResizeColumns) {
        setCheckValue("checkboxResizeColumns", true);
    }
    else {
        setCheckValue("checkboxResizeColumns", false);
    }
    if (options.allowEditObjects) {
        setCheckValue("checkboxEditObjects", true);
    }
    else {
        setCheckValue("checkboxEditObjects", false);
    }
}

function setProtectionOption(sheet, optionItem, value) {
    var options = sheet.options.protectionOptions;
    switch (optionItem) {
        case "allowSelectLockedCells":
            options.allowSelectLockedCells = value;
            break;
        case "allowSelectUnlockedCells":
            options.allowSelectUnlockedCells = value;
            break;
        case "allowSort":
            options.allowSort = value;
            break;
        case "allowFilter":
            options.allowFilter = value;
            break;
        case "allowResizeRows":
            options.allowResizeRows = value;
            break;
        case "allowResizeColumns":
            options.allowResizeColumns = value;
            break;
        case "allowEditObjects":
            options.allowEditObjects = value;
            break;
        default:
            console.log("There is no protection option:", optionItem);
            break;
    }
    setActiveTab("sheet");
}

function syncSheetProtectionText(isProtected) {
    var $protectSheetText = $("#protectSheetText");
    if (isProtected) {
        $protectSheetText.text(uiResource.cellTab.protection.sheetIsProtected);
    }
    else {
        $protectSheetText.text(uiResource.cellTab.protection.sheetIsUnprotected);
    }
}

function syncProtectSheetRelatedItems(sheet, value) {
    sheet.options.isProtected = value;
    syncSheetProtectionText(value);

    if (isAllSelectedSlicersLocked(sheet)) {
        setActiveTab("sheet");
    }
}

function isAllSelectedSlicersLocked(sheet) {
    var selectedSlicers = getSelectedSlicers(sheet);
    if (!selectedSlicers || selectedSlicers.length === 0) {
        return null;
    }
    var allLocked = true;
    for (var item in selectedSlicers) {
        allLocked = allLocked && selectedSlicers[item].isLocked();
        if (!allLocked) {
            break;
        }
    }
    return allLocked;
}
// Protect Sheet related items (end)

// Lock Cell related items
function getCellsLockedState() {
    var isLocked = false;
    var sheet = spread.getActiveSheet();
    var selections = sheet.getSelections(), selectionsLength = selections.length;
    var cell;
    var row, col, rowCount, colCount;
    if (selectionsLength > 0) {
        for (var i = 0; i < selectionsLength; i++) {
            var range = selections[i];
            row = range.row;
            rowCount = range.rowCount;
            colCount = range.colCount;
            if (row < 0) {
                row = 0;
            }
            for (row; row < range.row + rowCount; row++) {
                col = range.col;
                if (col < 0) {
                    col = 0;
                }
                for (col; col < range.col + colCount; col++) {
                    cell = sheet.getCell(row, col);
                    isLocked = isLocked || cell.locked();
                    if (isLocked) {
                        return isLocked;
                    }
                }
            }
        }
        return false;
    } else {
        return sheet.getCell(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex()).locked();
    }
}

function syncDisabledLockCells() {
    var cellsLockedState = getCellsLockedState();
    setCheckValue("checkboxLockCell", cellsLockedState);
}

function attachLockCellsEvent() {
    $("#lockCells").click(function () {
        var value = getCheckValue("checkboxLockCell");
        setSelectedCellsLock(value);
    });
}

function setSelectedCellsLock(value) {
    var sheet = spread.getActiveSheet();
    var selections = sheet.getSelections();
    var row, col, rowCount, colCount;
    for (var i = 0; i < selections.length; i++) {
        var range = selections[i];
        row = range.row;
        col = range.col;
        rowCount = range.rowCount;
        colCount = range.colCount;
        if (row < 0 && col < 0) {
            sheet.getDefaultStyle().locked = value;
        }
        else if (row < 0) {
            sheet.getRange(-1, col, -1, colCount).locked(value);
        }
        else if (col < 0) {
            sheet.getRange(row, -1, rowCount, -1).locked(value);
        }
        else {
            sheet.getRange(row, col, rowCount, colCount).locked(value);
        }
    }
}
// Lock Cell related items (end)

function attachSpreadEvents(rebind) {
    spread.bind(spreadNS.Events.EnterCell, onCellSelected);

    spread.bind(spreadNS.Events.ValueChanged, function (sender, args) {
        var row = args.row, col = args.col, sheet = args.sheet;

        if (sheet.getCell(row, col).wordWrap()) {
            sheet.autoFitRow(row);
        }
    });

    function shouldAutofitRow(sheet, row, col, colCount) {
        for (var c = 0; c < colCount; c++) {
            if (sheet.getCell(row, col++).wordWrap()) {
                return true;
            }
        }

        return false;
    }

    spread.bind(spreadNS.Events.RangeChanged, function (sender, args) {
        var sheet = args.sheet, row = args.row, rowCount = args.rowCount;

        if (args.action === spreadNS.RangeChangedAction.paste) {
            var col = args.col, colCount = args.colCount;
            for (var i = 0; i < rowCount; i++) {
                if (shouldAutofitRow(sheet, row, col, colCount)) {
                    sheet.autoFitRow(row);
                }
                row++;
            }
        }
    });

    spread.bind(spreadNS.Events.ActiveSheetChanged, function () {
        setActiveTab("sheet");
        syncSheetPropertyValues();
        syncCellRelatedItems();
        hideSpreadContextMenu();

        var sheet = spread.getActiveSheet(),
            picture;
        var slicers = sheet.slicers.all();
        for (var item in slicers) {
            slicers[item].isSelected(false);
        }

        if (sheet.getSelections().length === 0) {
            sheet.pictures.all().forEach(function (pic) {
                if (!picture && pic.isSelected()) {
                    picture = pic;
                }
            });
            // fix bug, make sure selection was shown after unselect slicer
            if (!picture) {
                sheet.setSelection(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex(), 1, 1);
            }
        }
        if (picture) {
            syncPicturePropertyValues(sheet, picture);
            setActiveTab("picture");
        } else {
            onCellSelected();
        }


        var value = $("div.button", $("div[data-name='allowOverflow']")).hasClass("checked");
        if (sheet.options.allowCellOverflow !== value) {
            sheet.options.allowCellOverflow = value;
        }
    });

    spread.bind(spreadNS.Events.SelectionChanging, function () {
        var sheet = spread.getActiveSheet();
        var selection = sheet.getSelections().slice(-1)[0];
        if (selection) {
            var position = getSelectedRangeString(sheet, selection);
            $("#positionbox").val(position);
        }
        syncDisabledBorderType();
    });

    spread.bind(spreadNS.Events.SelectionChanged, function () {
        syncCellRelatedItems();

        updatePositionBox(spread.getActiveSheet());
    });

    spread.bind(spreadNS.Events.PictureSelectionChanged, function (event, args) {
        var sheet = args.sheet, picture = args.picture;

        if (picture && picture.isSelected()) {
            syncPicturePropertyValues(sheet, picture);
            setActiveTab("picture");
        }
    });

    spread.bind(spreadNS.Events.CommentChanged, function (event, args) {
        var sheet = args.sheet, comment = args.comment, propertyName = args.propertyName;

        if (propertyName === "commentState" && comment) {
            if (comment.commentState() === spreadNS.Comments.CommentState.edit) {
                syncCommentPropertyValues(sheet, comment);
                setActiveTab("comment");
            }
        }
    });

    spread.bind(spreadNS.Events.ValidationError, function (event, data) {
        var dv = data.validator;
        if (dv) {
            alert(dv.errorMessage() || dv.inputMessage());
        }
    });

    spread.bind(spreadNS.Events.SlicerChanged, function (event, args) {
        bindSlicerEvents(args.sheet, args.slicer, args.propertyName);
    });

    $(document).bind("keydown", function (event) {
        if (event.shiftKey) {
            isShiftKey = true;
        }
    });
    $(document).bind("keyup", function (event) {
        if (!event.shiftKey) {
            isShiftKey = false;

            var sheet = spread.getActiveSheet(),
                position = getCellPositionString(sheet, sheet.getActiveRowIndex() + 1, sheet.getActiveColumnIndex() + 1);
            $("#positionbox").val(position);
        }
    });

    if (!rebind) {
        $("#ss").bind("contextmenu", processSpreadContextMenu);
        $("#ss").mouseup(function (e) {
            // hide context menu when the mouse down on SpreadJS
            if (e.button !== 2) {
                hideSpreadContextMenu();
            }
        });
    }
}

function setConditionalFormatSettingGroupVisible(groupName) {
    var $groupItems = $("#conditionalFormatSettingContainer .settingGroup .groupitem");

    $groupItems.hide();
    $groupItems.filter("[data-group='" + groupName + "']").show();
}

function processConditionalFormatSetting(groupName, listRef, rule) {
    $("#conditionalFormatSettingContainer div.details").show();
    setConditionalFormatSettingGroupVisible(groupName);

    var $ruleType = $("#highlightCellsRule"),
        $setButton = $("#setConditionalFormat");
    if (listRef) {
        $ruleType.data("list-ref", listRef);
        $setButton.data("rule-type", rule);
        var item = setDropDownValueByIndex($ruleType, 0);
        updateEnumTypeOfCF(item.value);
    } else {
        $setButton.data("rule-type", groupName);
    }
}

function processConditionalFormatDetailSetting(name, noAction) {
    switch (name) {
        case "highlight-cells-rules":
            $("#formatSetting").show();
            processConditionalFormatSetting("normal", "highlightCellsRulesList", 0);
            break;

        case "top-bottom-rules":
            $("#formatSetting").show();
            processConditionalFormatSetting("normal", "topBottomRulesList", 4);
            break;

        case "color-scales":
            $("#formatSetting").hide();
            processConditionalFormatSetting("normal", "colorScaleList", 8);
            break;

        case "data-bars":
            processConditionalFormatSetting("databar");
            break;

        case "icon-sets":
            processConditionalFormatSetting("iconset");
            updateIconCriteriaItems(0);
            break;

        case "remove-conditional-formats":
            $("#conditionalFormatSettingContainer div.details").hide();
            if (!noAction) {
                removeConditionFormats();
            }
            break;

        default:
            console.log("processConditionalFormatSetting not add for ", name);
            break;
    }
}

function getBackgroundColor(name) {
    return $("div.insp-color-picker[data-name='" + name + "'] div.color-view").css("background-color");
}

function addCondionalFormaterRule(rule) {
    var sheet = spread.getActiveSheet();
    var sels = sheet.getSelections();
    var style = new spreadNS.Style();

    if (getCheckValue("useFormatBackColor")) {
        style.backColor = getBackgroundColor("formatBackColor");
    }
    if (getCheckValue("useFormatForeColor")) {
        style.foreColor = getBackgroundColor("formatForeColor");
    }
    if (getCheckValue("useFormatBorder")) {
        var lineBorder = new spreadNS.LineBorder(getBackgroundColor("formatBorderColor"), spreadNS.LineStyle.thin);
        style.borderTop = style.borderRight = style.borderBottom = style.borderLeft = lineBorder;
    }
    var value1 = $("#value1").val();
    var value2 = $("#value2").val();
    var cfs = sheet.conditionalFormats;
    var operator = +getDropDownValue("comparisonOperator");

    var minType = +getDropDownValue("minType");
    var midType = +getDropDownValue("midType");
    var maxType = +getDropDownValue("maxType");
    var midColor = getBackgroundColor("midColor");
    var minColor = getBackgroundColor("minColor");
    var maxColor = getBackgroundColor("maxColor");
    var midValue = getNumberValue("midValue");
    var maxValue = getNumberValue("maxValue");
    var minValue = getNumberValue("minValue");

    switch (rule) {
        case "0":
            var doubleValue1 = parseFloat(value1);
            var doubleValue2 = parseFloat(value2);
            cfs.addCellValueRule(operator, isNaN(doubleValue1) ? value1 : doubleValue1, isNaN(doubleValue2) ? value2 : doubleValue2, style, sels);
            break;
        case "1":
            cfs.addSpecificTextRule(operator, value1, style, sels);
            break;
        case "2":
            cfs.addDateOccurringRule(operator, style, sels);
            break;
        case "4":
            cfs.addTop10Rule(operator, parseInt(value1, 10), style, sels);
            break;
        case "5":
            cfs.addUniqueRule(style, sels);
            break;
        case "6":
            cfs.addDuplicateRule(style, sels);
            break;
        case "7":
            cfs.addAverageRule(operator, style, sels);
            break;
        case "8":
            cfs.add2ScaleRule(minType, minValue, minColor, maxType, maxValue, maxColor, sels);
            break;
        case "9":
            cfs.add3ScaleRule(minType, minValue, minColor, midType, midValue, midColor, maxType, maxValue, maxColor, sels);
            break;
        default:
            var doubleValue1 = parseFloat(value1);
            var doubleValue2 = parseFloat(value2);
            cfs.addCellValueRule(operator, isNaN(doubleValue1) ? value1 : doubleValue1, isNaN(doubleValue2) ? value2 : doubleValue2, style, sels);
            break;
    }
    sheet.repaint();
}

function addDataBarRule() {
    var sheet = spread.getActiveSheet();
    sheet.suspendPaint();

    var selections = sheet.getSelections();
    if (selections.length > 0) {
        var ranges = [];
        $.each(selections, function (i, v) {
            ranges.push(new spreadNS.Range(v.row, v.col, v.rowCount, v.colCount));
        });
        var cfs = sheet.conditionalFormats;
        var dataBarRule = new ConditionalFormatting.DataBarRule();
        dataBarRule.ranges(ranges);
        dataBarRule.minType(+getDropDownValue("minimumType"));
        dataBarRule.minValue(getNumberValue("minimumValue"));
        dataBarRule.maxType(+getDropDownValue("maximumType"));
        dataBarRule.maxValue(getNumberValue("maximumValue"));
        dataBarRule.gradient(getCheckValue("gradient"));
        dataBarRule.color(getBackgroundColor("gradientColor"));
        dataBarRule.showBorder(getCheckValue("showBorder"));
        dataBarRule.borderColor(getBackgroundColor("barBorderColor"));
        dataBarRule.dataBarDirection(+getDropDownValue("dataBarDirection"));
        dataBarRule.negativeFillColor(getBackgroundColor("negativeFillColor"));
        dataBarRule.useNegativeFillColor(getCheckValue("useNegativeFillColor"));
        dataBarRule.negativeBorderColor(getBackgroundColor("negativeBorderColor"));
        dataBarRule.useNegativeBorderColor(getCheckValue("useNegativeBorderColor"));
        dataBarRule.axisPosition(+getDropDownValue("axisPosition"));
        dataBarRule.axisColor(getBackgroundColor("barAxisColor"));
        dataBarRule.showBarOnly(getCheckValue("showBarOnly"));
        cfs.addRule(dataBarRule);
    }

    sheet.resumePaint();
}

function addIconSetRule() {
    var sheet = spread.getActiveSheet();
    sheet.suspendPaint();

    var selections = sheet.getSelections();
    if (selections.length > 0) {
        var ranges = [];
        $.each(selections, function (i, v) {
            ranges.push(new spreadNS.Range(v.row, v.col, v.rowCount, v.colCount));
        });
        var cfs = sheet.conditionalFormats;
        var iconSetRule = new ConditionalFormatting.IconSetRule();
        iconSetRule.ranges(ranges);
        iconSetRule.iconSetType(+getDropDownValue("iconSetType"));
        var $divs = $("#iconCriteriaSetting .settinggroup:visible");
        var iconCriteria = iconSetRule.iconCriteria();
        $.each($divs, function (i, v) {
            var suffix = i + 1,
                isGreaterThanOrEqualTo = +getDropDownValue("iconSetCriteriaOperator" + suffix, this) === 1,
                iconValueType = +getDropDownValue("iconSetCriteriaType" + suffix, this),
                iconValue = $("input.editor", this).val();
            if (iconValueType !== ConditionalFormatting.IconValueType.formula) {
                iconValue = +iconValue;
            }
            iconCriteria[i] = new ConditionalFormatting.IconCriterion(isGreaterThanOrEqualTo, iconValueType, iconValue);
        });
        iconSetRule.reverseIconOrder(getCheckValue("reverseIconOrder"));
        iconSetRule.showIconOnly(getCheckValue("showIconOnly"));
        cfs.addRule(iconSetRule);
    }

    sheet.resumePaint();
}

function removeConditionFormats() {
    var sheet = spread.getActiveSheet();
    var cfs = sheet.conditionalFormats;
    var row = sheet.getActiveRowIndex(), col = sheet.getActiveColumnIndex();
    var rules = cfs.getRules(row, col);
    sheet.suspendPaint();
    $.each(rules, function (i, v) {
        cfs.removeRule(v);
    });
    sheet.resumePaint();
}

// Cell Type related items
function attachCellTypeEvents() {
    $("#setCellTypeBtn").click(function () {
        var currentCellType = getDropDownValue("cellTypes");
        applyCellType(currentCellType);
    });
}

function processCellTypeSetting(name, noAction) {
    $("#cellTypeSettingContainer").show();
    switch (name) {
        case "button-celltype":
            $("#celltype-button").show();
            $("#celltype-checkbox").hide();
            $("#celltype-combobox").hide();
            $("#celltype-hyperlink").hide();
            break;

        case "checkbox-celltype":
            $("#celltype-button").hide();
            $("#celltype-checkbox").show();
            $("#celltype-combobox").hide();
            $("#celltype-hyperlink").hide();
            break;

        case "combobox-celltype":
            $("#celltype-button").hide();
            $("#celltype-checkbox").hide();
            $("#celltype-combobox").show();
            $("#celltype-hyperlink").hide();
            break;

        case "hyperlink-celltype":
            $("#celltype-button").hide();
            $("#celltype-checkbox").hide();
            $("#celltype-combobox").hide();
            $("#celltype-hyperlink").show();
            break;

        case "clear-celltype":
            if (!noAction) {
                clearCellType();
            }
            $("#cellTypeSettingContainer").hide();
            return;

        default:
            console.log("processCellTypeSetting not process with ", name);
            return;
    }
}

function applyCellType(name) {
    var sheet = spread.getActiveSheet();
    var cellType;
    switch (name) {
        case "button-celltype":
            cellType = new spreadNS.CellTypes.Button();
            cellType.marginTop(getNumberValue("buttonCellTypeMarginTop"));
            cellType.marginRight(getNumberValue("buttonCellTypeMarginRight"));
            cellType.marginBottom(getNumberValue("buttonCellTypeMarginBottom"));
            cellType.marginLeft(getNumberValue("buttonCellTypeMarginLeft"));
            cellType.text(getTextValue("buttonCellTypeText"));
            cellType.buttonBackColor(getBackgroundColor("buttonCellTypeBackColor"));
            break;

        case "checkbox-celltype":
            cellType = new spreadNS.CellTypes.CheckBox();
            cellType.caption(getTextValue("checkboxCellTypeCaption"));
            cellType.textTrue(getTextValue("checkboxCellTypeTextTrue"));
            cellType.textIndeterminate(getTextValue("checkboxCellTypeTextIndeterminate"));
            cellType.textFalse(getTextValue("checkboxCellTypeTextFalse"));
            cellType.textAlign(getDropDownValue("checkboxCellTypeTextAlign"));
            cellType.isThreeState(getCheckValue("checkboxCellTypeIsThreeState"));
            break;

        case "combobox-celltype":
            cellType = new spreadNS.CellTypes.ComboBox();
            cellType.editorValueType(getDropDownValue("comboboxCellTypeEditorValueType"));
            var comboboxItemsText = getTextValue("comboboxCellTypeItemsText");
            var comboboxItemsValue = getTextValue("comboboxCellTypeItemsValue");
            var itemsText = comboboxItemsText.split(",");
            var itemsValue = comboboxItemsValue.split(",");
            var itemsLength = itemsText.length > itemsValue.length ? itemsText.length : itemsValue.length;
            var items = [];
            for (var count = 0; count < itemsLength; count++) {
                var t = itemsText.length > count && itemsText[0] !== "" ? itemsText[count] : undefined;
                var v = itemsValue.length > count && itemsValue[0] !== "" ? itemsValue[count] : undefined;
                if (t !== undefined && v !== undefined) {
                    items[count] = {text: t, value: v};
                }
                else if (t !== undefined) {
                    items[count] = {text: t};
                } else if (v !== undefined) {
                    items[count] = {value: v};
                }
            }
            cellType.items(items);
            break;

        case "hyperlink-celltype":
            cellType = new spreadNS.CellTypes.HyperLink();
            cellType.linkColor(getBackgroundColor("hyperlinkCellTypeLinkColor"));
            cellType.visitedLinkColor(getBackgroundColor("hyperlinkCellTypeVisitedLinkColor"));
            cellType.text(getTextValue("hyperlinkCellTypeText"));
            cellType.linkToolTip(getTextValue("hyperlinkCellTypeLinkToolTip"));
            break;
    }
    sheet.suspendPaint();
    sheet.suspendEvent();
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    for (var i = 0; i < sels.length; i++) {
        var sel = getActualCellRange(sheet, sels[i], rowCount, columnCount);
        for (var r = 0; r < sel.rowCount; r++) {
            for (var c = 0; c < sel.colCount; c++) {
                sheet.setCellType(sel.row + r, sel.col + c, cellType, spreadNS.SheetArea.viewport);
            }
        }
    }
    sheet.resumeEvent();
    sheet.resumePaint();
}

function clearCellType() {
    var sheet = spread.getActiveSheet();
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();
    sheet.suspendPaint();
    for (var i = 0; i < sels.length; i++) {
        var sel = getActualCellRange(sheet, sels[i], rowCount, columnCount);
        sheet.clear(sel.row, sel.col, sel.rowCount, sel.colCount, spreadNS.SheetArea.viewport, spreadNS.StorageType.style);
    }
    sheet.resumePaint();
}
// Cell Type related items (end)

function processComparisonOperator(value) {
    if ($("#ComparisonOperator").data("list-ref") === "cellValueOperatorList") {
        // between (6) and not between ( 7) with two values
        if (value === 6 || value === 7) {
            $("#andtext").show();
            $("#value2").show();
        }
    }
}

function updateEnumTypeOfCF(itemType) {
    var $operator = $("#ComparisonOperator"),
        $setButton = $("#setConditionalFormat");

    $setButton.data("rule-type", itemType);

    switch ("" + itemType) {
        case "0":
            $("#ruletext").text(conditionalFormatTexts.cells);
            $("#andtext").hide();
            $("#formattext").hide();
            $("#value1").show();
            $("#value1").val("");
            $("#value2").hide();
            $("#colorScale").hide();
            $operator.show();
            $operator.data("list-ref", "cellValueOperatorList");
            setDropDownValueByIndex($operator, 0);
            break;
        case "1":
            $("#ruletext").text(conditionalFormatTexts.cells);
            $("#andtext").hide();
            $("#formattext").hide();
            $("#value1").show();
            $("#value1").val("");
            $("#value2").hide();
            $("#colorScale").hide();
            $operator.show();
            $operator.data("list-ref", "specificTextOperatorList");
            setDropDownValueByIndex($operator, 0);
            break;
        case "2":
            $("#ruletext").text(conditionalFormatTexts.cells);
            $("#andtext").hide();
            $("#formattext").hide();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").hide();
            $operator.show();
            $operator.data("list-ref", "dateOccurringOperatorList");
            setDropDownValueByIndex($operator, 0);
            break;
        case "4":
            $("#ruletext").text(conditionalFormatTexts.rankIn);
            $("#andtext").hide();
            $("#formattext").hide();
            $("#value1").show();
            $("#value1").val("10");
            $("#value2").hide();
            $("#colorScale").hide();
            $operator.show();
            $operator.data("list-ref", "top10OperatorList");
            setDropDownValueByIndex($operator, 0);
            break;
        case "5":
        case "6":
            $("#ruletext").text(conditionalFormatTexts.all);
            $("#andtext").hide();
            $("#formattext").show();
            $("#formattext").text(conditionalFormatTexts.inRange);
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").hide();
            $operator.hide();
            break;
        case "7":
            $("#ruletext").text(conditionalFormatTexts.values);
            $("#andtext").hide();
            $("#formattext").show();
            $("#formattext").text(conditionalFormatTexts.average);
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").hide();
            $operator.show();
            $operator.data("list-ref", "averageOperatorList");
            setDropDownValueByIndex($operator, 0);
            break;
        case "8":
            $("#ruletext").text(conditionalFormatTexts.allValuesBased);
            $("#andtext").hide();
            $("#formattext").hide();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").show();
            $("#midpoint").hide();
            $("#minType").val("1");
            $("#maxType").val("2");
            $("#minValue").val("");
            $("#maxValue").val("");
            $("#minColor").css("background", "#F8696B");
            $("#maxColor").css("background", "#63BE7B");
            $operator.hide();
            break;
        case "9":
            $("#ruletext").text(conditionalFormatTexts.allValuesBased);
            $("#andtext").hide();
            $("#formattext").hide();
            $("#value1").hide();
            $("#value2").hide();
            $("#colorScale").show();
            $("#midpoint").show();
            $("#minType").val("1");
            $("#midType").val("4");
            $("#maxType").val("2");
            $("#minValue").val("");
            $("#midValue").val("50");
            $("#maxValue").val("");
            $("#minColor").css("background-color", "#F8696B");
            $("#midColor").css("background-color", "#FFEB84");
            $("#maxColor").css("background-color", "#63BE7B");
            $operator.hide();
            break;
        default:
            break;
    }
}

function attachConditionalFormatEvents() {
    $("#setConditionalFormat").click(function () {
        var ruleType = $(this).data("rule-type");

        switch (ruleType) {
            case "databar":
                addDataBarRule();
                break;

            case "iconset":
                addIconSetRule();
                break;

            default:
                addCondionalFormaterRule("" + ruleType);
                break;
        }
    });
}

// Data Validation related items
function processDataValidationSetting(name, title) {
    $("#dataValidationErrorAlertMessage").val("");
    $("#dataValidationErrorAlertTitle").val("");
    $("#dataValidationInputTitle").val("");
    $("#dataValidationInputMessage").val("");
    switch (name) {
        case "anyvalue-validator":
            $("#validatorNumberType").hide();
            $("#validatorListType").hide();
            $("#validatorFormulaListType").hide();
            $("#validatorDateType").hide();
            $("#validatorTextLengthType").hide();
            break;

        case "number-validator":
            $("#validatorNumberType").show();
            $("#validatorListType").hide();
            $("#validatorFormulaListType").hide();
            $("#validatorDateType").hide();
            $("#validatorTextLengthType").hide();
            processNumberValidatorComparisonOperatorSetting(getDropDownValue("numberValidatorComparisonOperator"));

            setTextValue("numberMinimum", 0);
            setTextValue("numberMaximum", 0);
            setTextValue("numberValue", 0);
            break;

        case "list-validator":
            $("#validatorNumberType").hide();
            $("#validatorListType").show();
            $("#validatorFormulaListType").hide();
            $("#validatorDateType").hide();
            $("#validatorTextLengthType").hide();

            setTextValue("listSource", "1,2,3");
            break;

        case "formulalist-validator":
            $("#validatorNumberType").hide();
            $("#validatorListType").hide();
            $("#validatorFormulaListType").show();
            $("#validatorDateType").hide();
            $("#validatorTextLengthType").hide();

            setTextValue("formulaListFormula", "E5:I5");
            break;

        case "date-validator":
            $("#validatorNumberType").hide();
            $("#validatorListType").hide();
            $("#validatorFormulaListType").hide();
            $("#validatorDateType").show();
            $("#validatorTextLengthType").hide();
            processDateValidatorComparisonOperatorSetting(getDropDownValue("dateValidatorComparisonOperator"));

            var date = getCurrentTime();
            setTextValue("startDate", date);
            setTextValue("endDate", date);
            setTextValue("dateValue", date);
            break;

        case "textlength-validator":
            $("#validatorNumberType").hide();
            $("#validatorListType").hide();
            $("#validatorFormulaListType").hide();
            $("#validatorDateType").hide();
            $("#validatorTextLengthType").show();
            processTextLengthValidatorComparisonOperatorSetting(getDropDownValue("textLengthValidatorComparisonOperator"));

            setNumberValue("textLengthMinimum", 0);
            setNumberValue("textLengthMaximum", 0);
            setNumberValue("textLengthValue", 0);
            break;

        case "formula-validator":
            $("#validatorNumberType").hide();
            $("#validatorListType").hide();
            $("#validatorFormulaListType").show();
            $("#validatorDateType").hide();
            $("#validatorTextLengthType").hide();

            setTextValue("formulaListFormula", "=ISERROR(FIND(\" \",A1))");
            break;

        default:
            console.log("processDataValidationSetting not process with ", name, title);
            break;
    }
}

function processNumberValidatorComparisonOperatorSetting(value) {
    if (value === ComparisonOperators.between || value === ComparisonOperators.notBetween) {
        $("#numberValue").hide();
        $("#numberBetweenOperator").show();
    }
    else {
        $("#numberBetweenOperator").hide();
        $("#numberValue").show();
    }
}

function processDateValidatorComparisonOperatorSetting(value) {
    if (value === ComparisonOperators.between || value === ComparisonOperators.notBetween) {
        $("#dateValue").hide();
        $("#dateBetweenOperator").show();
    }
    else {
        $("#dateBetweenOperator").hide();
        $("#dateValue").show();
    }
}

function processTextLengthValidatorComparisonOperatorSetting(value) {
    if (value === ComparisonOperators.between || value === ComparisonOperators.notBetween) {
        $("#textLengthValue").hide();
        $("#textLengthBetweenOperator").show();
    }
    else {
        $("#textLengthBetweenOperator").hide();
        $("#textLengthValue").show();
    }
}

function setDataValidator() {
    var validatorType = getDropDownValue("validatorType");
    var currentDataValidator = null;
    var dropDownValue;

    var formulaListFormula = getTextValue("formulaListFormula");

    switch (validatorType) {
        case "anyvalue-validator":
            currentDataValidator = new spreadNS.DataValidation.DefaultDataValidator();
            break;
        case "number-validator":
            var numberMinimum = getTextValue("numberMinimum");
            var numberMaximum = getTextValue("numberMaximum");
            var numberValue = getTextValue("numberValue");
            var isInteger = getCheckValue("isInteger");
            dropDownValue = getDropDownValue("numberValidatorComparisonOperator");
            if (dropDownValue !== ComparisonOperators.between && dropDownValue !== ComparisonOperators.notBetween) {
                numberMinimum = numberValue;
            }
            if (isInteger) {
                currentDataValidator = DataValidation.createNumberValidator(dropDownValue,
                    isNaN(numberMinimum) ? numberMinimum : parseInt(numberMinimum, 10),
                    isNaN(numberMaximum) ? numberMaximum : parseInt(numberMaximum, 10),
                    true);
            } else {
                currentDataValidator = DataValidation.createNumberValidator(dropDownValue,
                    isNaN(numberMinimum) ? numberMinimum : parseFloat(numberMinimum, 10),
                    isNaN(numberMaximum) ? numberMaximum : parseFloat(numberMaximum, 10),
                    false);
            }
            break;
        case "list-validator":
            var listSource = getTextValue("listSource");
            currentDataValidator = DataValidation.createListValidator(listSource);
            break;
        case "formulalist-validator":
            currentDataValidator = DataValidation.createFormulaListValidator(formulaListFormula);
            break;
        case "date-validator":
            var startDate = getTextValue("startDate");
            var endDate = getTextValue("endDate");
            var dateValue = getTextValue("dateValue");
            var isTime = getCheckValue("isTime");
            dropDownValue = getDropDownValue("dateValidatorComparisonOperator");
            if (dropDownValue !== ComparisonOperators.between && dropDownValue !== ComparisonOperators.notBetween) {
                startDate = dateValue;
            }
            if (isTime) {
                currentDataValidator = DataValidation.createDateValidator(dropDownValue,
                    isNaN(startDate) ? startDate : new Date(startDate),
                    isNaN(endDate) ? endDate : new Date(endDate),
                    true);
            } else {
                currentDataValidator = DataValidation.createDateValidator(dropDownValue,
                    isNaN(startDate) ? startDate : new Date(startDate),
                    isNaN(endDate) ? endDate : new Date(endDate),
                    false);
            }
            break;
        case "textlength-validator":
            var textLengthMinimum = getNumberValue("textLengthMinimum");
            var textLengthMaximum = getNumberValue("textLengthMaximum");
            var textLengthValue = getNumberValue("textLengthValue");
            dropDownValue = getDropDownValue("textLengthValidatorComparisonOperator");
            if (dropDownValue !== ComparisonOperators.between && dropDownValue !== ComparisonOperators.notBetween) {
                textLengthMinimum = textLengthValue;
            }
            currentDataValidator = DataValidation.createTextLengthValidator(dropDownValue, textLengthMinimum, textLengthMaximum);
            break;
        case "formula-validator":
            currentDataValidator = DataValidation.createFormulaValidator(formulaListFormula);
            break;
    }

    if (currentDataValidator) {
        currentDataValidator.errorMessage($("#dataValidationErrorAlertMessage").val());
        currentDataValidator.errorStyle(getDropDownValue("errorAlert"));
        currentDataValidator.errorTitle($("#dataValidationErrorAlertTitle").val());
        currentDataValidator.showErrorMessage(getCheckValue("showErrorAlert"));
        currentDataValidator.ignoreBlank(getCheckValue("ignoreBlank"));
        var showInputMessage = getCheckValue("showInputMessage");
        if (showInputMessage) {
            currentDataValidator.inputTitle($("#dataValidationInputTitle").val());
            currentDataValidator.inputMessage($("#dataValidationInputMessage").val());
        }

        setDataValidatorInRange(currentDataValidator);
    }
}

function setDataValidatorInRange(dataValidator) {
    var sheet = spread.getActiveSheet();
    sheet.suspendPaint();
    var sels = sheet.getSelections();
    var rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();

    for (var i = 0; i < sels.length; i++) {
        var sel = getActualCellRange(sheet, sels[i], rowCount, columnCount);
        for (var r = 0; r < sel.rowCount; r++) {
            for (var c = 0; c < sel.colCount; c++) {
                sheet.setDataValidator(sel.row + r, sel.col + c, dataValidator);
            }
        }
    }
    sheet.resumePaint();
}

function getCurrentTime() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();

    var strDate = year + "-";
    if (month < 10)
        strDate += "0";
    strDate += month + "-";
    if (day < 10)
        strDate += "0";
    strDate += day;

    return strDate;
}

function attachDataValidationEvents() {
    $("#setDataValidator").click(function () {
        var currentValidatorType = getDropDownValue("validatorType");
        setDataValidator(currentValidatorType);
    });
    $("#clearDataValidatorSettings").click(function () {
        // reset to default
        var validationTypeItem = setDropDownValueByIndex($("#validatorType"), 0);
        processDataValidationSetting(validationTypeItem.value);
        setDropDownValue("errorAlert", 0);
        setCheckValue("showInputMessage", true);
        setCheckValue("showErrorAlert", true);
    });
}
// Data Validation related items (end)

function updateIconCriteriaItems(iconStyleType) {
    var IconSetType = ConditionalFormatting.IconSetType,
        items = $("#iconCriteriaSetting .settinggroup"),
        values;

    if (iconStyleType <= IconSetType.threeSymbolsUncircled) {
        values = [33, 67];
    } else if (iconStyleType <= IconSetType.fourTrafficLights) {
        values = [25, 50, 75];
    } else {
        values = [20, 40, 60, 80];
    }

    items.each(function (index) {
        var value = values[index], $item = $(this), suffix = index + 1;

        if (value) {
            $item.show();
            setDropDownValue("iconSetCriteriaOperator" + suffix, 1, this);
            setDropDownValue("iconSetCriteriaType" + suffix, 4, this);
            $("input.editor", this).val(value);
        } else {
            $item.hide();
        }
    });
}

function processMinItems(type, name) {
    var value = "";
    switch (type) {
        case 0: // Number
        case 3: // Percent
            value = "0";
            break;
        case 4: // Percentile
            value = "10";
            break;
        default:
            value = "";
            break;
    }
    setTextValue(name, value);
}

function processMidItems(type, name) {
    var value = "";
    switch (type) {
        case 0: // Number
            value = "0";
            break;
        case 3: // Percent
        case 4: // Percentile
            value = "50";
            break;
        default:
            value = "";
            break;
    }
    setTextValue(name, value);
}

function processMaxItems(type, name) {
    var value = "";
    switch (type) {
        case 0: // Number
            value = "0";
            break;
        case 3: // Percent
            value = "100";
            break;
        case 4: // Percentile
            value = "90";
            break;
        default:
            value = "";
            break;
    }
    setTextValue(name, value);
}

// Sparkline related items
function processAddSparklineEx() {
    var sheet = spread.getActiveSheet();
    var selection = sheet.getSelections()[0];
    if (!selection) {
        return;
    }

    var id = this.id,
        sparklineType = id.toUpperCase();
    var $typeInfo = $(".menu-item>div.text[data-value='" + sparklineType + "']");
    if ($typeInfo.length > 0) {
        setDropDownValue("sparklineExType", sparklineType);
        processSparklineSetting(sparklineType);
    }
    else {
        processSparklineSetting(getDropDownValue("sparklineExType"));
    }
    setTextValue("txtLineDataRange", parseRangeToExpString(selection));
    setTextValue("txtLineLocationRange", "");

    var SPARKLINE_DIALOG_WIDTH = 360;               // sprakline dialog width
    showModal(uiResource.sparklineDialog.title, SPARKLINE_DIALOG_WIDTH, $("#sparklineexdialog").children(), addSparklineEvent);
}

function unParseFormula(expr, row, col) {
    if (!expr) {
        return "";
    }
    var sheet = spread.getActiveSheet();
    if (!sheet) {
        return null;
    }
    var calcService = sheet.getCalcService();
    return calcService.unparse(null, expr, row, col);
}

function processSparklineSetting(name, title) {
    //Show only when data range is illegal.
    $("#dataRangeError").hide();
    $("#singleDataRangeError").hide();
    //Show only when location range is illegal.
    $("#locationRangeError").hide();

    switch (name) {
        case "LINESPARKLINE":
        case "COLUMNSPARKLINE":
        case "WINLOSSSPARKLINE":
        case "PIESPARKLINE":
        case "AREASPARKLINE":
        case "SCATTERSPARKLINE":
        case "SPREADSPARKLINE":
        case "STACKEDSPARKLINE":
        case "BOXPLOTSPARKLINE":
        case "CASCADESPARKLINE":
        case "PARETOSPARKLINE":
            $("#lineContainer").show();
            $("#bulletContainer").hide();
            $("#monthContainer").hide();
            $("#hbarContainer").hide();
            $("#yearContainer").hide();
            $("#varianceContainer").hide();
            break;

        case "BULLETSPARKLINE":
            $("#lineContainer").hide();
            $("#monthContainer").hide();
            $("#bulletContainer").show();
            $("#hbarContainer").hide();
            $("#yearContainer").hide();
            $("#varianceContainer").hide();

            setTextValue("txtBulletMeasure", "");
            setTextValue("txtBulletTarget", "");
            setTextValue("txtBulletMaxi", "");
            setTextValue("txtBulletGood", "");
            setTextValue("txtBulletBad", "");
            setTextValue("txtBulletForecast", "");
            setTextValue("txtBulletTickunit", "");
            setCheckValue("checkboxBulletVertial", false);
            break;

        case "HBARSPARKLINE":
        case "VBARSPARKLINE":
            $("#lineContainer").hide();
            $("#bulletContainer").hide();
            $("#monthContainer").hide();
            $("#hbarContainer").show();
            $("#yearContainer").hide();
            $("#varianceContainer").hide();

            setTextValue("txtHbarValue", "");
            break;

        case "VARISPARKLINE":
            $("#lineContainer").hide();
            $("#bulletContainer").hide();
            $("#hbarContainer").hide();
            $("#monthContainer").hide();
            $("#yearContainer").hide();
            $("#varianceContainer").show();

            setTextValue("txtVariance", "");
            setTextValue("txtVarianceReference", "");
            setTextValue("txtVarianceMini", "");
            setTextValue("txtVarianceMaxi", "");
            setTextValue("txtVarianceMark", "");
            setTextValue("txtVarianceTickUnit", "");
            setCheckValue("checkboxVarianceLegend", false);
            setCheckValue("checkboxVarianceVertical", false);
            break;

        case "MONTHSPARKLINE":
            $("#lineContainer").show();
            $("#bulletContainer").hide();
            $("#hbarContainer").hide();
            $("#varianceContainer").hide();
            $("#yearContainer").show();
            $("#monthContainer").show();
            setTextValue("txtYearValue", "");
            setTextValue("txtMonthValue", "");
            setTextValue("txtEmptyColorValue", "");
            setTextValue("txtStartColorValue", "");
            setTextValue("txtMiddleColorValue", "");
            setTextValue("txtEndColorValue", "");
            setTextValue("txtColorRangeValue", "");
            break;

        case "YEARSPARKLINE":
            $("#lineContainer").show();
            $("#bulletContainer").hide();
            $("#hbarContainer").hide();
            $("#varianceContainer").hide();
            $("#monthContainer").hide();
            $("#yearContainer").show();
            setTextValue("txtYearValue", "");
            setTextValue("txtEmptyColorValue", "");
            setTextValue("txtStartColorValue", "");
            setTextValue("txtMiddleColorValue", "");
            setTextValue("txtEndColorValue", "");
            setTextValue("txtColorRangeValue", "");
            break;

        default:
            console.log("processSparklineSetting not process with ", name, title);
            break;
    }
}

function addSparklineEvent() {
    var sheet = spread.getActiveSheet(),
        selection = sheet.getSelections()[0],
        isValid = true;

    var sparklineExType = getDropDownValue("sparklineExType");
    if (selection) {
        var range = getActualRange(selection, sheet.getRowCount(), sheet.getColumnCount());
        var formulaStr = '', row = range.row, col = range.col, direction = 0;
        switch (sparklineExType) {
            case "BULLETSPARKLINE":
                var measure = getTextValue("txtBulletMeasure"),
                    target = getTextValue("txtBulletTarget"),
                    maxi = getTextValue("txtBulletMaxi"),
                    good = getTextValue("txtBulletGood"),
                    bad = getTextValue("txtBulletBad"),
                    forecast = getTextValue("txtBulletForecast"),
                    tickunit = getTextValue("txtBulletTickunit"),
                    colorScheme = getBackgroundColor("colorBulletColorScheme"),
                    vertical = getCheckValue("checkboxBulletVertial");
                formulaStr = '=' + sparklineExType + '(' + measure + ',' + target + ',' + maxi + ',' + good + ',' + bad + ',' + forecast + ',' + tickunit + ',' + '"' + colorScheme + '"' + ',' + vertical + ')';
                sheet.setFormula(row, col, formulaStr);
                break;
            case "HBARSPARKLINE":
                var value = getTextValue("txtHbarValue"),
                    colorScheme = getBackgroundColor("colorHbarColorScheme");
                formulaStr = '=' + sparklineExType + '(' + value + ',' + '"' + colorScheme + '"' + ')';
                sheet.setFormula(row, col, formulaStr);
                break;
            case "VBARSPARKLINE":
                var value = getTextValue("txtHbarValue"),
                    colorScheme = getBackgroundColor("colorHbarColorScheme");
                formulaStr = '=' + sparklineExType + '(' + value + ',' + '"' + colorScheme + '"' + ')';
                sheet.setFormula(row, col, formulaStr);
                break;
            case "VARISPARKLINE":
                var variance = getTextValue("txtVariance"),
                    reference = getTextValue("txtVarianceReference"),
                    mini = getTextValue("txtVarianceMini"),
                    maxi = getTextValue("txtVarianceMaxi"),
                    mark = getTextValue("txtVarianceMark"),
                    tickunit = getTextValue("txtVarianceTickUnit"),
                    colorPositive = getBackgroundColor("colorVariancePositive"),
                    colorNegative = getBackgroundColor("colorVarianceNegative"),
                    legend = getCheckValue("checkboxVarianceLegend"),
                    vertical = getCheckValue("checkboxVarianceVertical");
                formulaStr = '=' + sparklineExType + '(' + variance + ',' + reference + ',' + mini + ',' + maxi + ',' + mark + ',' + tickunit + ',' + legend + ',' + '"' + colorPositive + '"' + ',' + '"' + colorNegative + '"' + ',' + vertical + ')';
                sheet.setFormula(row, col, formulaStr);
                break;
            case "CASCADESPARKLINE":
            case "PARETOSPARKLINE":
                var dataRangeStr = getTextValue("txtLineDataRange"),
                    locationRangeStr = getTextValue("txtLineLocationRange"),
                    dataRangeObj = parseStringToExternalRanges(dataRangeStr, sheet),
                    locationRangeObj = parseStringToExternalRanges(locationRangeStr, sheet),
                    vertical = false,
                    dataRange, locationRange;
                if (dataRangeObj && dataRangeObj.length > 0 && dataRangeObj[0].range) {
                    dataRange = dataRangeObj[0].range;
                }
                if (locationRangeObj && locationRangeObj.length > 0 && locationRangeObj[0].range) {
                    locationRange = locationRangeObj[0].range;
                }
                if (locationRange && locationRange.rowCount < locationRange.colCount) {
                    vertical = true;
                }
                if (!dataRange) {
                    isValid = false;
                    $("#dataRangeError").show();
                }
                if (!locationRange) {
                    isValid = false;
                    $("#locationRangeError").show();
                }
                if (isValid) {
                    var pointCount = dataRange.rowCount * dataRange.colCount,
                        i = 1;
                    for (var r = locationRange.row; r < locationRange.row + locationRange.rowCount; r++) {
                        for (var c = locationRange.col; c < locationRange.col + locationRange.colCount; c++) {
                            if (i <= pointCount) {
                                formulaStr = '=' + sparklineExType + '(' + dataRangeStr + ',' + i + ',,,,,,' + vertical + ')';
                                sheet.setFormula(r, c, formulaStr);
                                sheet.setActiveCell(r, c);
                                i++;
                            }
                        }
                    }
                }
                break;
            case "MONTHSPARKLINE":
                var year = getTextValue("txtYearValue"),
                    month = getTextValue("txtMonthValue"),
                    emptyColor = getBackgroundColor("emptyColorValue"),
                    startColor = getBackgroundColor("startColorValue"),
                    middleColor = getBackgroundColor("middleColorValue"),
                    endColor = getBackgroundColor("endColorValue"),
                    dataRangeStr = getTextValue("txtLineDataRange"),
                    locationRangeStr = getTextValue("txtLineLocationRange"),
                    colorRangeStr = getTextValue("txtColorRangeValue"),
                    dataRangeObj = parseStringToExternalRanges(dataRangeStr, sheet),
                    locationRangeObj = parseStringToExternalRanges(locationRangeStr, sheet),
                    dataRange, locationRange;
                if (dataRangeObj && dataRangeObj.length > 0 && dataRangeObj[0].range) {
                    dataRange = dataRangeObj[0].range;
                }
                if (locationRangeObj && locationRangeObj.length > 0 && locationRangeObj[0].range) {
                    locationRange = locationRangeObj[0].range;
                }
                if (!dataRange) {
                    isValid = false;
                    $("#dataRangeError").show();
                }
                if (!locationRange) {
                    isValid = false;
                    $("#locationRangeError").show();
                }
                var row = locationRange.row, col = locationRange.col;
                if (isValid) {
                    if (!colorRangeStr) {
                        formulaStr = "=" + sparklineExType + "(" + year + "," + month + "," + dataRangeStr + "," + parseSparklineColorOptions(emptyColor) + "," + parseSparklineColorOptions(startColor) + "," + parseSparklineColorOptions(middleColor) + "," + parseSparklineColorOptions(endColor) + ")";
                    } else {
                        formulaStr = "=" + sparklineExType + "(" + year + "," + month + "," + dataRangeStr + "," + colorRangeStr + ")";
                    }
                    sheet.setFormula(row, col, formulaStr);
                }
                break;
            case "YEARSPARKLINE":
                var year = getTextValue("txtYearValue"),
                    emptyColor = getBackgroundColor("emptyColorValue"),
                    startColor = getBackgroundColor("startColorValue"),
                    middleColor = getBackgroundColor("middleColorValue"),
                    endColor = getBackgroundColor("endColorValue"),
                    dataRangeStr = getTextValue("txtLineDataRange"),
                    locationRangeStr = getTextValue("txtLineLocationRange"),
                    colorRangeStr = getTextValue("txtColorRangeValue"),
                    dataRangeObj = parseStringToExternalRanges(dataRangeStr, sheet),
                    locationRangeObj = parseStringToExternalRanges(locationRangeStr, sheet),
                    dataRange, locationRange;
                if (dataRangeObj && dataRangeObj.length > 0 && dataRangeObj[0].range) {
                    dataRange = dataRangeObj[0].range;
                }
                if (locationRangeObj && locationRangeObj.length > 0 && locationRangeObj[0].range) {
                    locationRange = locationRangeObj[0].range;
                }
                if (!dataRange) {
                    isValid = false;
                    $("#dataRangeError").show();
                }
                if (!locationRange) {
                    isValid = false;
                    $("#locationRangeError").show();
                }
                var row = locationRange.row, col = locationRange.col;
                if (isValid) {
                    if (!colorRangeStr) {
                        formulaStr = "=" + sparklineExType + "(" + year + "," + dataRangeStr + "," + parseSparklineColorOptions(emptyColor) + "," + parseSparklineColorOptions(startColor) + "," + parseSparklineColorOptions(middleColor) + "," + parseSparklineColorOptions(endColor) + ")";
                    } else {
                        formulaStr = "=" + sparklineExType + "(" + year + "," + dataRangeStr + "," + colorRangeStr + ")";
                    }
                    sheet.setFormula(row, col, formulaStr);
                }
                break;
            default:
                var dataRangeStr = getTextValue("txtLineDataRange"),
                    locationRangeStr = getTextValue("txtLineLocationRange"),
                    dataRangeObj = parseStringToExternalRanges(dataRangeStr, sheet),
                    locationRangeObj = parseStringToExternalRanges(locationRangeStr, sheet),
                    dataRange, locationRange;
                if (dataRangeObj && dataRangeObj.length > 0 && dataRangeObj[0].range) {
                    dataRange = dataRangeObj[0].range;
                }
                if (locationRangeObj && locationRangeObj.length > 0 && locationRangeObj[0].range) {
                    locationRange = locationRangeObj[0].range;
                }

                if (!dataRange) {
                    isValid = false;
                    $("#dataRangeError").show();
                }
                if (!locationRange) {
                    isValid = false;
                    $("#locationRangeError").show();
                }
                if (isValid) {
                    if (["LINESPARKLINE", "COLUMNSPARKLINE", "WINLOSSSPARKLINE"].indexOf(sparklineExType) >= 0) {
                        if (dataRange.rowCount === 1) {
                            direction = 1;
                        }
                        else if (dataRange.colCount === 1) {
                            direction = 0;
                        }
                        else {
                            $("#singleDataRangeError").show();
                            isValid = false;
                        }
                        if (isValid) {
                            formulaStr = '=' + sparklineExType + '(' + dataRangeStr + ',' + direction + ')';
                        }
                    }
                    else {
                        formulaStr = '=' + sparklineExType + '(' + dataRangeStr + ')';
                    }
                    if (isValid) {
                        row = locationRange.row;
                        col = locationRange.col;
                        sheet.setFormula(row, col, formulaStr);
                        sheet.setActiveCell(row, col);
                    }
                }
                break;
        }
    }
    if (!isValid) {
        return {canceled: true};
    }
    else {
        if (showSparklineSetting(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex())) {
            updateFormulaBar();
            setActiveTab("sparklineEx");
            return;
        }
        console.log("Added sparkline", sparklineExType);
    }
}

function parseSparklineColorOptions(str) {
    return '"' + str + '"';
}

function parseRangeToExpString(range) {
    return SheetsCalc.rangeToFormula(range, 0, 0, SheetsCalc.RangeReferenceRelative.allRelative);
}

function parseStringToExternalRanges(expString, sheet) {
    var results = [];
    var exps = expString.split(",");
    try {
        for (var i = 0; i < exps.length; i++) {
            var range = SheetsCalc.formulaToRange(sheet, exps[i]);
            results.push({"range": range});
        }
    }
    catch (e) {
        return null;
    }
    return results;
}

function parseFormulaSparkline(row, col) {
    var sheet = spread.getActiveSheet();
    if (!sheet) {
        return null;
    }
    var formula = sheet.getFormula(row, col);
    if (!formula) {
        return null;
    }
    var calcService = sheet.getCalcService();
    try {
        var expr = calcService.parse(null, formula, row, col);
        if (expr.type === ExpressionType.function) {
            var fnName = expr.functionName;
            if (fnName && spread.getSparklineEx(fnName)) {
                return expr;
            }
        }
    }
    catch (ex) {
        console.log("parse failed:", ex);
    }
    return null;
}

function parseColorExpression(colorExpression, row, col) {
    if (!colorExpression) {
        return null;
    }
    var sheet = spread.getActiveSheet();
    if (colorExpression.type === ExpressionType.string) {
        return colorExpression.value;
    }
    else if (colorExpression.type === ExpressionType.missingArgument) {
        return null;
    }
    else {
        var formula = null;
        try {
            formula = unParseFormula(colorExpression, row, col);
        }
        catch (ex) {
        }
        return SheetsCalc.evaluateFormula(sheet, formula, row, col);
    }
}

function getAreaSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {colorPositive: "#787878", colorNegative: "#CB0000"};
    if (formulaArgs[0]) {
        setTextValue("areaSparklinePoints", unParseFormula(formulaArgs[0], row, col));
    }
    else {
        setTextValue("areaSparklinePoints", "");
    }
    var inputList = ["areaSparklineMinimumValue", "areaSparklineMaximumValue", "areaSparklineLine1", "areaSparklineLine2"];
    var len = inputList.length;
    for (var i = 1; i <= len; i++) {
        if (formulaArgs[i]) {
            setNumberValue(inputList[i - 1], unParseFormula(formulaArgs[i], row, col));
        }
        else {
            setNumberValue(inputList[i - 1], "");
        }
    }
    var positiveColor = parseColorExpression(formulaArgs[5], row, col);
    if (positiveColor) {
        setColorValue("areaSparklinePositiveColor", positiveColor);
    }
    else {
        setColorValue("areaSparklinePositiveColor", defaultValue.colorPositive);
    }
    var negativeColor = parseColorExpression(formulaArgs[6], row, col);
    if (negativeColor) {
        setColorValue("areaSparklineNegativeColor", negativeColor);
    }
    else {
        setColorValue("areaSparklineNegativeColor", defaultValue.colorNegative);
    }
}

function getBoxPlotSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {boxplotClass: "5ns", style: 0, colorScheme: "#D2D2D2", vertical: false, showAverage: false};
    if (formulaArgs && formulaArgs.length > 0) {
        var pointsValue = unParseFormula(formulaArgs[0], row, col);
        var boxPlotClassValue = formulaArgs[1] && (formulaArgs[1].type === ExpressionType.string ? formulaArgs[1].value : null);
        var showAverageValue = formulaArgs[2] && (formulaArgs[2].type === ExpressionType.boolean ? formulaArgs[2].value : null);
        var scaleStartValue = unParseFormula(formulaArgs[3], row, col);
        var scaleEndValue = unParseFormula(formulaArgs[4], row, col);
        var acceptableStartValue = unParseFormula(formulaArgs[5], row, col);
        var acceptableEndValue = unParseFormula(formulaArgs[6], row, col);
        var colorValue = parseColorExpression(formulaArgs[7], row, col);
        var styleValue = formulaArgs[8] ? unParseFormula(formulaArgs[8], row, col) : null;
        var verticalValue = formulaArgs[9] && (formulaArgs[9].type === ExpressionType.boolean ? formulaArgs[9].value : null);

        setTextValue("boxplotSparklinePoints", pointsValue);
        setDropDownValue("boxplotClassType", boxPlotClassValue === null ? defaultValue.boxplotClass : boxPlotClassValue);
        setTextValue("boxplotSparklineScaleStart", scaleStartValue);
        setTextValue("boxplotSparklineScaleEnd", scaleEndValue);
        setTextValue("boxplotSparklineAcceptableStart", acceptableStartValue);
        setTextValue("boxplotSparklineAcceptableEnd", acceptableEndValue);
        setColorValue("boxplotSparklineColorScheme", colorValue === null ? defaultValue.colorScheme : colorValue);
        setDropDownValue("boxplotSparklineStyleType", styleValue === null ? defaultValue.style : styleValue);
        setCheckValue("boxplotSparklineShowAverage", showAverageValue === null ? defaultValue.showAverage : showAverageValue);
        setCheckValue("boxplotSparklineVertical", verticalValue === null ? defaultValue.vertical : verticalValue);
    }
    else {
        setTextValue("boxplotSparklinePoints", "");
        setDropDownValue("boxplotClassType", defaultValue.boxplotClass);
        setTextValue("boxplotSparklineScaleStart", "");
        setTextValue("boxplotSparklineScaleEnd", "");
        setTextValue("boxplotSparklineAcceptableStart", "");
        setTextValue("boxplotSparklineAcceptableEnd", "");
        setColorValue("boxplotSparklineColorScheme", defaultValue.colorScheme);
        setDropDownValue("boxplotSparklineStyleType", defaultValue.style);
        setCheckValue("boxplotSparklineShowAverage", defaultValue.showAverage);
        setCheckValue("boxplotSparklineVertical", defaultValue.vertical);
    }
}

function getBulletSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {vertical: false, colorScheme: "#A0A0A0"};
    if (formulaArgs && formulaArgs.length > 0) {
        var measureValue = unParseFormula(formulaArgs[0], row, col);
        var targetValue = unParseFormula(formulaArgs[1], row, col);
        var maxiValue = unParseFormula(formulaArgs[2], row, col);
        var goodValue = unParseFormula(formulaArgs[3], row, col);
        var badValue = unParseFormula(formulaArgs[4], row, col);
        var forecastValue = unParseFormula(formulaArgs[5], row, col);
        var tickunitValue = unParseFormula(formulaArgs[6], row, col);
        var colorSchemeValue = parseColorExpression(formulaArgs[7], row, col);
        var verticalValue = formulaArgs[8] && (formulaArgs[8].type === ExpressionType.boolean ? formulaArgs[8].value : null);

        setTextValue("bulletSparklineMeasure", measureValue);
        setTextValue("bulletSparklineTarget", targetValue);
        setTextValue("bulletSparklineMaxi", maxiValue);
        setTextValue("bulletSparklineForecast", forecastValue);
        setTextValue("bulletSparklineGood", goodValue);
        setTextValue("bulletSparklineBad", badValue);
        setTextValue("bulletSparklineTickUnit", tickunitValue);
        setColorValue("bulletSparklineColorScheme", colorSchemeValue ? colorSchemeValue : defaultValue.colorScheme);
        setCheckValue("bulletSparklineVertical", verticalValue ? verticalValue : defaultValue.vertical);
    }
    else {
        setTextValue("bulletSparklineMeasure", "");
        setTextValue("bulletSparklineTarget", "");
        setTextValue("bulletSparklineMaxi", "");
        setTextValue("bulletSparklineForecast", "");
        setTextValue("bulletSparklineGood", "");
        setTextValue("bulletSparklineBad", "");
        setTextValue("bulletSparklineTickUnit", "");
        setColorValue("bulletSparklineColorScheme", defaultValue.colorScheme);
        setCheckValue("bulletSparklineVertical", defaultValue.vertical);
    }
}

function getCascadeSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {colorPositive: "#8CBF64", colorNegative: "#D6604D", vertical: false};

    if (formulaArgs && formulaArgs.length > 0) {
        var pointsRangeValue = unParseFormula(formulaArgs[0], row, col);
        var pointIndexValue = unParseFormula(formulaArgs[1], row, col);
        var labelsRangeValue = unParseFormula(formulaArgs[2], row, col);
        var minimumValue = unParseFormula(formulaArgs[3], row, col);
        var maximumValue = unParseFormula(formulaArgs[4], row, col);
        var colorPositiveValue = parseColorExpression(formulaArgs[5], row, col);
        var colorNegativeValue = parseColorExpression(formulaArgs[6], row, col);
        var verticalValue = formulaArgs[7] && (formulaArgs[7].type === ExpressionType.boolean ? formulaArgs[7].value : null);

        setTextValue("cascadeSparklinePointsRange", pointsRangeValue);
        setTextValue("cascadeSparklinePointIndex", pointIndexValue);
        setTextValue("cascadeSparklineLabelsRange", labelsRangeValue);
        setTextValue("cascadeSparklineMinimum", minimumValue);
        setTextValue("cascadeSparklineMaximum", maximumValue);
        setColorValue("cascadeSparklinePositiveColor", colorPositiveValue ? colorPositiveValue : defaultValue.colorPositive);
        setColorValue("cascadeSparklineNegativeColor", colorNegativeValue ? colorNegativeValue : defaultValue.colorNegative);
        setCheckValue("cascadeSparklineVertical", verticalValue ? verticalValue : defaultValue.vertical);
    }
    else {
        setTextValue("cascadeSparklinePointsRange", "");
        setTextValue("cascadeSparklinePointIndex", "");
        setTextValue("cascadeSparklineLabelsRange", "");
        setTextValue("cascadeSparklineMinimum", "");
        setTextValue("cascadeSparklineMaximum", "");
        setColorValue("cascadeSparklinePositiveColor", defaultValue.colorPositive);
        setColorValue("cascadeSparklineNegativeColor", defaultValue.colorNegative);
        setCheckValue("cascadeSparklineVertical", defaultValue.vertical);
    }
}

function parseSetting(jsonSetting) {
    var setting = {}, inBracket = false, inProperty = true, property = "", value = "";
    if (jsonSetting) {
        jsonSetting = jsonSetting.substr(1, jsonSetting.length - 2);
        for (var i = 0, len = jsonSetting.length; i < len; i++) {
            var char = jsonSetting.charAt(i);
            if (char === ":") {
                inProperty = false;
            }
            else if (char === "," && !inBracket) {
                setting[property] = value;
                property = "";
                value = "";
                inProperty = true;
            }
            else if (char === "\'" || char === "\"") {
                // discard
            }
            else {
                if (char === "(") {
                    inBracket = true;
                }
                else if (char === ")") {
                    inBracket = false;
                }
                if (inProperty) {
                    property += char;
                }
                else {
                    value += char;
                }
            }
        }
        if (property) {
            setting[property] = value;
        }
        for (var p in setting) {
            var v = setting[p];
            if (v !== null && typeof (v) !== "undefined") {
                if (v.toUpperCase() === "TRUE") {
                    setting[p] = true;
                } else if (v.toUpperCase() === "FALSE") {
                    setting[p] = false;
                } else if (!isNaN(v) && isFinite(v)) {
                    setting[p] = parseFloat(v);
                }
            }
        }
    }
    return setting;
}

function updateManual(type, inputDataName) {
    var $manualDiv = $("div.insp-text[data-name='" + inputDataName + "']");
    var $manualInput = $manualDiv.find("input");
    if (type !== "custom") {
        $manualInput.attr("disabled", "disabled");
        $manualDiv.addClass("manual-disable");
    }
    else {
        $manualInput.removeAttr("disabled");
        $manualDiv.removeClass("manual-disable");
    }
}

function updateStyleSetting(settings) {
    var defaultValue = {
        negativePoints: "#A52A2A", markers: "#244062", highPoint: "#0000FF",
        lowPoint: "#0000FF", firstPoint: "#95B3D7", lastPoint: "#95B3D7",
        series: "#244062", axis: "#000000"
    };
    setColorValue("compatibleSparklineNegativeColor", settings.negativeColor ? settings.negativeColor : defaultValue.negativePoints);
    setColorValue("compatibleSparklineMarkersColor", settings.markersColor ? settings.markersColor : defaultValue.markers);
    setColorValue("compatibleSparklineAxisColor", settings.axisColor ? settings.axisColor : defaultValue.axis);
    setColorValue("compatibleSparklineSeriesColor", settings.seriesColor ? settings.seriesColor : defaultValue.series);
    setColorValue("compatibleSparklineHighMarkerColor", settings.highMarkerColor ? settings.highMarkerColor : defaultValue.highPoint);
    setColorValue("compatibleSparklineLowMarkerColor", settings.lowMarkerColor ? settings.lowMarkerColor : defaultValue.lowPoint);
    setColorValue("compatibleSparklineFirstMarkerColor", settings.firstMarkerColor ? settings.firstMarkerColor : defaultValue.firstPoint);
    setColorValue("compatibleSparklineLastMarkerColor", settings.lastMarkerColor ? settings.lastMarkerColor : defaultValue.lastPoint);
    setTextValue("compatibleSparklineLastLineWeight", settings.lineWeight || settings.lw);
}

function updateSparklineSetting(setting) {
    if (!setting) {
        return;
    }
    var defaultSetting = {
        rightToLeft: false,
        displayHidden: false,
        displayXAxis: false,
        showFirst: false,
        showHigh: false,
        showLast: false,
        showLow: false,
        showNegative: false,
        showMarkers: false
    };

    setDropDownValue("emptyCellDisplayType", setting.displayEmptyCellsAs ? setting.displayEmptyCellsAs : -1);
    setCheckValue("showDataInHiddenRowOrColumn", setting.displayHidden ? setting.displayHidden : defaultSetting.displayHidden);
    setCheckValue("compatibleSparklineShowFirst", setting.showFirst ? setting.showFirst : defaultSetting.showFirst);
    setCheckValue("compatibleSparklineShowLast", setting.showLast ? setting.showLast : defaultSetting.showLast);
    setCheckValue("compatibleSparklineShowHigh", setting.showHigh ? setting.showHigh : defaultSetting.showHigh);
    setCheckValue("compatibleSparklineShowLow", setting.showLow ? setting.showLow : defaultSetting.showLow);
    setCheckValue("compatibleSparklineShowNegative", setting.showNegative ? setting.showNegative : defaultSetting.showNegative);
    setCheckValue("compatibleSparklineShowMarkers", setting.showMarkers ? setting.showMarkers : defaultSetting.showMarkers);
    var minAxisType = Sparklines.SparklineAxisMinMax[setting.minAxisType];
    setDropDownValue("minAxisType", minAxisType ? minAxisType : -1);
    setTextValue("manualMin", setting.manualMin ? setting.manualMin : "");
    var maxAxisType = Sparklines.SparklineAxisMinMax[setting.maxAxisType];
    setDropDownValue("maxAxisType", maxAxisType ? maxAxisType : -1);
    setTextValue("manualMax", setting.manualMax ? setting.manualMax : "");
    setCheckValue("rightToLeft", setting.rightToLeft ? setting.rightToLeft : defaultSetting.rightToLeft);
    setCheckValue("displayXAxis", setting.displayXAxis ? setting.displayXAxis : defaultSetting.displayXAxis);

    var type = getDropDownValue("minAxisType");
    updateManual(type, "manualMin");
    type = getDropDownValue("maxAxisType");
    updateManual(type, "manualMax");
}

function getCompatibleSparklineSetting(formulaArgs, row, col) {
    var sparklineSetting = {};

    setTextValue("compatibleSparklineData", unParseFormula(formulaArgs[0], row, col));
    setDropDownValue("dataOrientationType", formulaArgs[1].value);
    if (formulaArgs[2]) {
        setTextValue("compatibleSparklineDateAxisData", unParseFormula(formulaArgs[2], row, col));
    }
    else {
        setTextValue("compatibleSparklineDateAxisData", "");
    }
    if (formulaArgs[3]) {
        setDropDownValue("dateAxisOrientationType", formulaArgs[3].value);
    }
    else {
        setDropDownValue("dateAxisOrientationType", -1);
    }
    var colorExpression = parseColorExpression(formulaArgs[4], row, col);
    if (colorExpression) {
        sparklineSetting = parseSetting(colorExpression);
    }
    updateSparklineSetting(sparklineSetting);
    updateStyleSetting(sparklineSetting);
}

function getScatterSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {
        tags: false,
        drawSymbol: true,
        drawLines: false,
        dash: false,
        color1: "#969696",
        color2: "#CB0000"
    };
    var inputList = ["scatterSparklinePoints1", "scatterSparklinePoints2", "scatterSparklineMinX", "scatterSparklineMaxX",
        "scatterSparklineMinY", "scatterSparklineMaxY", "scatterSparklineHLine", "scatterSparklineVLine",
        "scatterSparklineXMinZone", "scatterSparklineXMaxZone", "scatterSparklineYMinZone", "scatterSparklineYMaxZone"];
    for (var i = 0; i < inputList.length; i++) {
        var formula = "";
        if (formulaArgs[i]) {
            formula = unParseFormula(formulaArgs[i], row, col);
        }
        setTextValue(inputList[i], formula);
    }

    var color1 = parseColorExpression(formulaArgs[15], row, col);
    var color2 = parseColorExpression(formulaArgs[16], row, col);
    var tags = formulaArgs[12] && (formulaArgs[12].type === ExpressionType.boolean ? formulaArgs[12].value : null);
    var drawSymbol = formulaArgs[13] && (formulaArgs[13].type === ExpressionType.boolean ? formulaArgs[13].value : null);
    var drawLines = formulaArgs[14] && (formulaArgs[14].type === ExpressionType.boolean ? formulaArgs[14].value : null);
    var dashLine = formulaArgs[17] && (formulaArgs[17].type === ExpressionType.boolean ? formulaArgs[17].value : null);

    setColorValue("scatterSparklineColor1", (color1 !== null) ? color1 : defaultValue.color1);
    setColorValue("scatterSparklineColor2", (color2 !== null) ? color2 : defaultValue.color2);
    setCheckValue("scatterSparklineTags", tags !== null ? tags : defaultValue.tags);
    setCheckValue("scatterSparklineDrawSymbol", drawSymbol !== null ? drawSymbol : defaultValue.drawSymbol);
    setCheckValue("scatterSparklineDrawLines", drawLines !== null ? drawLines : defaultValue.drawLines);
    setCheckValue("scatterSparklineDashLine", dashLine !== null ? dashLine : defaultValue.dash);
}

function getHBarSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {colorScheme: "#969696"};

    var value = unParseFormula(formulaArgs[0], row, col);
    var colorScheme = parseColorExpression(formulaArgs[1], row, col);

    setTextValue("hbarSparklineValue", value);
    setColorValue("hbarSparklineColorScheme", (colorScheme !== null) ? colorScheme : defaultValue.colorScheme);
}

function getVBarSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {colorScheme: "#969696"};

    var value = unParseFormula(formulaArgs[0], row, col);
    var colorScheme = parseColorExpression(formulaArgs[1], row, col);

    setTextValue("vbarSparklineValue", value);
    setColorValue("vbarSparklineColorScheme", (colorScheme !== null) ? colorScheme : defaultValue.colorScheme);
}

function getParetoSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {label: 0, vertical: false};

    if (formulaArgs && formulaArgs.length > 0) {
        var pointsRangeValue = unParseFormula(formulaArgs[0], row, col);
        var pointIndexValue = unParseFormula(formulaArgs[1], row, col);
        var colorRangeValue = unParseFormula(formulaArgs[2], row, col);
        var targetValue = unParseFormula(formulaArgs[3], row, col);
        var target2Value = unParseFormula(formulaArgs[4], row, col);
        var highlightPositionValue = unParseFormula(formulaArgs[5], row, col);
        var labelValue = formulaArgs[6] && (formulaArgs[6].type === ExpressionType.number ? formulaArgs[6].value : null);
        var verticalValue = formulaArgs[7] && (formulaArgs[7].type === ExpressionType.boolean ? formulaArgs[7].value : null);

        setTextValue("paretoSparklinePoints", pointsRangeValue);
        setTextValue("paretoSparklinePointIndex", pointIndexValue);
        setTextValue("paretoSparklineColorRange", colorRangeValue);
        setTextValue("paretoSparklineHighlightPosition", highlightPositionValue);
        setTextValue("paretoSparklineTarget", targetValue);
        setTextValue("paretoSparklineTarget2", target2Value);
        setDropDownValue("paretoLabelType", labelValue === null ? defaultValue.label : labelValue);
        setCheckValue("paretoSparklineVertical", verticalValue === null ? defaultValue.vertical : verticalValue);
    }
    else {
        setTextValue("paretoSparklinePoints", "");
        setTextValue("paretoSparklinePointIndex", "");
        setTextValue("paretoSparklineColorRange", "");
        setTextValue("paretoSparklineHighlightPosition", "");
        setTextValue("paretoSparklineTarget", "");
        setTextValue("paretoSparklineTarget2", "");
        setDropDownValue("paretoLabelType", defaultValue.label);
        setCheckValue("paretoSparklineVertical", defaultValue.vertical);
    }
}

function getSpreadSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {showAverage: false, style: 4, colorScheme: "#646464", vertical: false};

    if (formulaArgs && formulaArgs.length > 0) {
        var pointsValue = unParseFormula(formulaArgs[0], row, col);
        var showAverageValue = formulaArgs[1] && (formulaArgs[1].type === ExpressionType.boolean ? formulaArgs[1].value : null);
        var scaleStartValue = unParseFormula(formulaArgs[2], row, col);
        var scaleEndValue = unParseFormula(formulaArgs[3], row, col);
        var styleValue = formulaArgs[4] ? unParseFormula(formulaArgs[4], row, col) : null;
        var colorSchemeValue = parseColorExpression(formulaArgs[5], row, col);
        var verticalValue = formulaArgs[6] && (formulaArgs[6].type === ExpressionType.boolean ? formulaArgs[6].value : null);

        setTextValue("spreadSparklinePoints", pointsValue);
        setCheckValue("spreadSparklineShowAverage", showAverageValue ? showAverageValue : defaultValue.showAverage);
        setTextValue("spreadSparklineScaleStart", scaleStartValue);
        setTextValue("spreadSparklineScaleEnd", scaleEndValue);
        setDropDownValue("spreadSparklineStyleType", styleValue ? styleValue : defaultValue.style);
        setColorValue("spreadSparklineColorScheme", colorSchemeValue ? colorSchemeValue : defaultValue.colorScheme);
        setCheckValue("spreadSparklineVertical", verticalValue ? verticalValue : defaultValue.vertical);
    }
    else {
        setTextValue("spreadSparklinePoints", "");
        setCheckValue("spreadSparklineShowAverage", defaultValue.showAverage);
        setTextValue("spreadSparklineScaleStart", "");
        setTextValue("spreadSparklineScaleEnd", "");
        setDropDownValue("spreadSparklineStyleType", defaultValue.style);
        setColorValue("spreadSparklineColorScheme", defaultValue.colorScheme);
        setCheckValue("spreadSparklineVertical", defaultValue.vertical);
    }
}

function getStackedSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {color: "#646464", vertical: false, textOrientation: 0};

    if (formulaArgs && formulaArgs.length > 0) {
        var pointsValue = unParseFormula(formulaArgs[0], row, col);
        var colorRangeValue = unParseFormula(formulaArgs[1], row, col);
        var labelRangeValue = unParseFormula(formulaArgs[2], row, col);
        var maximumValue = unParseFormula(formulaArgs[3], row, col);
        var targetRedValue = unParseFormula(formulaArgs[4], row, col);
        var targetGreenValue = unParseFormula(formulaArgs[5], row, col);
        var targetBlueValue = unParseFormula(formulaArgs[6], row, col);
        var targetYellowValue = unParseFormula(formulaArgs[7], row, col);
        var colorValue = parseColorExpression(formulaArgs[8], row, col);
        var highlightPositionValue = unParseFormula(formulaArgs[9], row, col);
        var verticalValue = formulaArgs[10] && (formulaArgs[10].type === ExpressionType.boolean ? formulaArgs[10].value : null);
        var textOrientationValue = unParseFormula(formulaArgs[11], row, col);
        var textSizeValue = unParseFormula(formulaArgs[12], row, col);

        setTextValue("stackedSparklinePoints", pointsValue);
        setTextValue("stackedSparklineColorRange", colorRangeValue);
        setTextValue("stackedSparklineLabelRange", labelRangeValue);
        setNumberValue("stackedSparklineMaximum", maximumValue);
        setNumberValue("stackedSparklineTargetRed", targetRedValue);
        setNumberValue("stackedSparklineTargetGreen", targetGreenValue);
        setNumberValue("stackedSparklineTargetBlue", targetBlueValue);
        setNumberValue("stackedSparklineTargetYellow", targetYellowValue);
        setColorValue("stackedSparklineColor", "stacked-color-span", colorValue ? colorValue : defaultValue.color);
        setNumberValue("stackedSparklineHighlightPosition", highlightPositionValue);
        setCheckValue("stackedSparklineVertical", verticalValue ? verticalValue : defaultValue.vertical);
        setDropDownValue("stackedSparklineTextOrientation", textOrientationValue ? textOrientationValue : defaultValue.textOrientation);
        setNumberValue("stackedSparklineTextSize", textSizeValue);
    }
    else {
        setTextValue("stackedSparklinePoints", "");
        setTextValue("stackedSparklineColorRange", "");
        setTextValue("stackedSparklineLabelRange", "");
        setNumberValue("stackedSparklineMaximum", "");
        setNumberValue("stackedSparklineTargetRed", "");
        setNumberValue("stackedSparklineTargetGreen", "");
        setNumberValue("stackedSparklineTargetBlue", "");
        setNumberValue("stackedSparklineTargetYellow", "");
        setColorValue("stackedSparklineColor", "stacked-color-span", defaultValue.color);
        setNumberValue("stackedSparklineHighlightPosition", "");
        setCheckValue("stackedSparklineVertical", defaultValue.vertical);
        setDropDownValue("stackedSparklineTextOrientation", defaultValue.textOrientation);
        setNumberValue("stackedSparklineTextSize", "");
    }
}

function getVariSparklineSetting(formulaArgs, row, col) {
    var defaultValue = {legend: false, colorPositive: "green", colorNegative: "red", vertical: false};

    if (formulaArgs && formulaArgs.length > 0) {
        var varianceValue = unParseFormula(formulaArgs[0], row, col);
        var referenceValue = unParseFormula(formulaArgs[1], row, col);
        var miniValue = unParseFormula(formulaArgs[2], row, col);
        var maxiValue = unParseFormula(formulaArgs[3], row, col);
        var markValue = unParseFormula(formulaArgs[4], row, col);
        var tickunitValue = unParseFormula(formulaArgs[5], row, col);
        var legendValue = formulaArgs[6] && (formulaArgs[6].type === ExpressionType.boolean ? formulaArgs[6].value : null);
        var colorPositiveValue = parseColorExpression(formulaArgs[7], row, col);
        var colorNegativeValue = parseColorExpression(formulaArgs[8], row, col);
        var verticalValue = formulaArgs[9] && (formulaArgs[9].type === ExpressionType.boolean ? formulaArgs[9].value : null);

        setTextValue("variSparklineVariance", varianceValue);
        setTextValue("variSparklineReference", referenceValue);
        setTextValue("variSparklineMini", miniValue);
        setTextValue("variSparklineMaxi", maxiValue);
        setTextValue("variSparklineMark", markValue);
        setTextValue("variSparklineTickUnit", tickunitValue);
        setColorValue("variSparklineColorPositive", colorPositiveValue ? colorPositiveValue : defaultValue.colorPositive);
        setColorValue("variSparklineColorNegative", colorNegativeValue ? colorNegativeValue : defaultValue.colorNegative);
        setCheckValue("variSparklineLegend", legendValue);
        setCheckValue("variSparklineVertical", verticalValue);
    }
    else {
        setTextValue("variSparklineVariance", "");
        setTextValue("variSparklineReference", "");
        setTextValue("variSparklineMini", "");
        setTextValue("variSparklineMaxi", "");
        setTextValue("variSparklineMark", "");
        setTextValue("variSparklineTickUnit", "");
        setColorValue("variSparklineColorPositive", defaultValue.colorPositive);
        setColorValue("variSparklineColorNegative", defaultValue.colorNegative);
        setCheckValue("variSparklineLegend", defaultValue.legend);
        setCheckValue("variSparklineVertical", defaultValue.vertical);
    }
}

function getMonthSparklineSetting(formulaArgs, row, col) {
    var year = "", month = "", dataRangeStr = "", emptyColor = "lightgray", startColor = "lightgreen", middleColor = "green", endColor = "darkgreen", colorRangeStr = "";
    if (formulaArgs) {
        if (formulaArgs.length === 7) {
            year = unParseFormula(formulaArgs[0], row, col);
            month = unParseFormula(formulaArgs[1], row, col);
            dataRangestr = unParseFormula(formulaArgs[2], row, col);
            emptyColor = parseColorExpression(formulaArgs[3], row, col);
            startColor = parseColorExpression(formulaArgs[4], row, col);
            middleColor = parseColorExpression(formulaArgs[5], row, col);
            endColor = parseColorExpression(formulaArgs[6], row, col);
            setTextValue("monthSparklineYear", year);
            setTextValue("monthSparklineMonth", month);
            setTextValue("monthSparklineData", dataRangestr);
            setColorValue("monthSparklineEmptyColor", emptyColor);
            setColorValue("monthSparklineStartColor", startColor);
            setColorValue("monthSparklineMiddleColor", middleColor);
            setColorValue("monthSparklineEndColor", endColor);
            setTextValue("monthSparklineColorRange", "");
        } else {
            year = unParseFormula(formulaArgs[0], row, col);
            month = unParseFormula(formulaArgs[1], row, col);
            dataRangestr = unParseFormula(formulaArgs[2], row, col);
            colorRangeStr = unParseFormula(formulaArgs[3], row, col);
            setTextValue("monthSparklineYear", year);
            setTextValue("monthSparklineMonth", month);
            setTextValue("monthSparklineData", dataRangestr);
            setColorValue("monthSparklineEmptyColor", emptyColor);
            setColorValue("monthSparklineStartColor", startColor);
            setColorValue("monthSparklineMiddleColor", middleColor);
            setColorValue("monthSparklineEndColor", endColor);
            setTextValue("monthSparklineColorRange", colorRangeStr);
        }
    } else {
        setTextValue("monthSparklineYear", year);
        setTextValue("monthSparklineMonth", month);
        setTextValue("monthSparklineData", dataRangestr);
        setColorValue("monthSparklineEmptyColor", emptyColor);
        setColorValue("monthSparklineStartColor", startColor);
        setColorValue("monthSparklineMiddleColor", middleColor);
        setColorValue("monthSparklineEndColor", endColor);
        setTextValue("monthSparklineColorRange", colorRangeStr);
    }
}

function getYearSparklineSetting(formulaArgs, row, col) {
    var year = "", month = "", dataRangeStr = "", emptyColor = "lightgray", startColor = "lightgreen", middleColor = "green", endColor = "darkgreen", colorRangeStr = "";
    if (formulaArgs) {
        if (formulaArgs.length === 6) {
            year = unParseFormula(formulaArgs[0], row, col);
            dataRangestr = unParseFormula(formulaArgs[1], row, col);
            emptyColor = parseColorExpression(formulaArgs[2], row, col);
            startColor = parseColorExpression(formulaArgs[3], row, col);
            middleColor = parseColorExpression(formulaArgs[4], row, col);
            endColor = parseColorExpression(formulaArgs[5], row, col);
            setTextValue("yearSparklineYear", year);
            setTextValue("yearSparklineData", dataRangestr);
            setColorValue("yearSparklineEmptyColor", emptyColor);
            setColorValue("yearSparklineStartColor", startColor);
            setColorValue("yearSparklineMiddleColor", middleColor);
            setColorValue("yearSparklineEndColor", endColor);
            setTextValue("yearSparklineColorRange", "");
        } else {
            year = unParseFormula(formulaArgs[0], row, col);
            dataRangestr = unParseFormula(formulaArgs[1], row, col);
            colorRangeStr = unParseFormula(formulaArgs[2], row, col);
            setTextValue("yearSparklineYear", year);
            setTextValue("yearSparklineData", dataRangestr);
            setColorValue("yearSparklineEmptyColor", emptyColor);
            setColorValue("yearSparklineStartColor", startColor);
            setColorValue("yearSparklineMiddleColor", middleColor);
            setColorValue("yearSparklineEndColor", endColor);
            setTextValue("yearSparklineColorRange", colorRangeStr);
        }
    } else {
        setTextValue("yearSparklineYear", year);
        setTextValue("yearSparklineData", dataRangestr);
        setColorValue("yearSparklineEmptyColor", emptyColor);
        setColorValue("yearSparklineStartColor", startColor);
        setColorValue("yearSparklineMiddleColor", middleColor);
        setColorValue("yearSparklineEndColor", endColor);
        setTextValue("yearSparklineColorRange", colorRangeStr);
    }
}

function addPieSparklineColor(count, color, isMinusSymbol) {
    var defaultColor = "rgb(237, 237, 237)";
    color = color ? color : defaultColor;
    var symbolFunClass, symbolClass;
    if (isMinusSymbol) {
        symbolFunClass = "remove-pie-color";
        symbolClass = "ui-pie-sparkline-icon-minus";
    }
    else {
        symbolFunClass = "add-pie-color";
        symbolClass = "ui-pie-sparkline-icon-plus";
    }
    var $pieSparklineColorContainer = $("#pieSparklineColorContainer");
    var pieColorDataName = "pieColorName";
    var $colorDiv = $("<div>" +
        "<div class=\"insp-row\">" +
        "<div>" +
        "<div class=\"insp-color-picker insp-inline-row\" data-name=\"" + pieColorDataName + count + "\">" +
        "<div class=\"title insp-inline-row-item insp-col-6 localize\">" + uiResource.sparklineExTab.pieSparkline.values.color + count + "</div>" +
        "<div class=\"picker insp-inline-row-item insp-col-4\">" +
        "<div style=\"width: 100%; height: 100%\">" +
        "<div class=\"color-view\" style=\"background-color: " + color + ";\"></div>" +
        "</div>" +
        "</div>" +
        "<div class=\"" + symbolFunClass + " insp-inline-row-item insp-col-2\"><span class=\"ui-pie-sparkline-icon " + symbolClass + "\"></span></div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>");
    $colorDiv.appendTo($pieSparklineColorContainer);
}

function addPieColor(count, color, isMinusSymbol) {
    var $colorSpanDiv = $(".add-pie-color");
    $colorSpanDiv.addClass("remove-pie-color").removeClass("add-pie-color");
    $colorSpanDiv.find("span").addClass("ui-pie-sparkline-icon-minus").removeClass("ui-pie-sparkline-icon-plus");
    addPieSparklineColor(count, color, isMinusSymbol);
    $(".add-pie-color").unbind("click");
    $(".add-pie-color").bind("click", function (evt) {
        var count = $("#pieSparklineColorContainer").find("span").length;
        addPieColor(count + 1);
    });
    $(".remove-pie-color").unbind("click");
    $(".remove-pie-color").bind("click", function (evt) {
        resetPieColor($(evt.target));
    });
    $("div.insp-color-picker .picker").click(showColorPicker);
}

function resetPieColor($colorSpanDiv) {
    if (!$colorSpanDiv.hasClass("ui-pie-sparkline-icon")) {
        return;
    }
    $colorDiv = $colorSpanDiv.parent().parent().parent().parent().parent();
    $colorDiv.remove();
    var $pieSparklineColorContainer = $("#pieSparklineColorContainer");
    var colorArray = [];
    $pieSparklineColorContainer.find(".color-view").each(function () {
        colorArray.push($(this).css("background-color"));
    });
    $pieSparklineColorContainer.empty();
    addMultiPieColor(colorArray);
}

function addMultiPieColor(colorArray) {
    if (!colorArray || colorArray.length === 0) {
        return;
    }
    var length = colorArray.length;
    var i = 0;
    for (i; i < length - 1; i++) {
        addPieSparklineColor(i + 1, colorArray[i], true);
    }
    addPieColor(i + 1, colorArray[i]);
}

function getPieSparklineSetting(formulaArgs, row, col) {
    var agrsLength = formulaArgs.length;
    if (formulaArgs && agrsLength > 0) {
        var range = unParseFormula(formulaArgs[0], row, col);
        setTextValue("pieSparklinePercentage", range);

        var actualLen = agrsLength - 1;
        if (actualLen === 0) {
            addPieColor(1);
        }
        else {
            var colorArray = [];
            for (var i = 1; i <= actualLen; i++) {
                var colorItem = null;
                var color = parseColorExpression(formulaArgs[i], row, col);
                colorArray.push(color);
            }
            addMultiPieColor(colorArray);
        }
    }
}

var sparklineName;
function showSparklineSetting(row, col) {
    var expr = parseFormulaSparkline(row, col);
    if (!expr || !expr.arguments) {
        return false;
    }
    var formulaSparkline = spread.getSparklineEx(expr.functionName);

    if (formulaSparkline) {
        var $sparklineSettingDiv = $("#sparklineExTab>div>div");
        var formulaArgs = expr.arguments;
        $sparklineSettingDiv.hide();
        if (formulaSparkline instanceof Sparklines.PieSparkline) {
            $("#pieSparklineSetting").show();
            $("#pieSparklineColorContainer").empty();
            getPieSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.AreaSparkline) {
            $("#areaSparklineSetting").show();
            getAreaSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.BoxPlotSparkline) {
            $("#boxplotSparklineSetting").show();
            getBoxPlotSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.BulletSparkline) {
            $("#bulletSparklineSetting").show();
            getBulletSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.CascadeSparkline) {
            $("#cascadeSparklineSetting").show();
            getCascadeSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.LineSparkline || formulaSparkline instanceof Sparklines.ColumnSparkline || formulaSparkline instanceof Sparklines.WinlossSparkline) {
            $("#compatibleSparklineSetting").show();
            if (expr.function.name) {
                sparklineName = expr.function.name;
            }
            getCompatibleSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.ScatterSparkline) {
            $("#scatterSparklineSetting").show();
            getScatterSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.HBarSparkline) {
            $("#hbarSparklineSetting").show();
            getHBarSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.VBarSparkline) {
            $("#vbarSparklineSetting").show();
            getVBarSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.ParetoSparkline) {
            $("#paretoSparklineSetting").show();
            getParetoSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.SpreadSparkline) {
            $("#spreadSparklineSetting").show();
            getSpreadSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.StackedSparkline) {
            $("#stackedSparklineSetting").show();
            getStackedSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.VariSparkline) {
            $("#variSparklineSetting").show();
            getVariSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.MonthSparkline) {
            $("#monthSparklineSetting").show();
            getMonthSparklineSetting(formulaArgs, row, col);
            return true;
        }
        else if (formulaSparkline instanceof Sparklines.YearSparkline) {
            $("#yearSparklineSetting").show();
            getYearSparklineSetting(formulaArgs, row, col);
            return true;
        }
    }
    return false;
}

function attachSparklineSettingEvents() {
    $("#setAreaSparkline").click(applyAreaSparklineSetting);
    $("#setBoxPlotSparkline").click(applyBoxPlotSparklineSetting);
    $("#setBulletSparkline").click(applyBulletSparklineSetting);
    $("#setCascadeSparkline").click(applyCascadeSparklineSetting);
    $("#setCompatibleSparkline").click(applyCompatibleSparklineSetting);
    $("#setScatterSparkline").click(applyScatterSparklineSetting);
    $("#setHbarSparkline").click(applyHbarSparklineSetting);
    $("#setVbarSparkline").click(applyVbarSparklineSetting);
    $("#setParetoSparkline").click(applyParetoSparklineSetting);
    $("#setSpreadSparkline").click(applySpreadSparklineSetting);
    $("#setStackedSparkline").click(applyStackedSparklineSetting);
    $("#setVariSparkline").click(applyVariSparklineSetting);
    $("#setPieSparkline").click(applyPieSparklineSetting);
    $("#setMonthSparkline").click(applyMonthSparklineSetting);
    $("#setYearSparkline").click(applyYearSparklineSetting);
}

function updateFormulaBar() {
    var sheet = spread.getActiveSheet();
    var formulaBar = $("#formulabox");
    if (formulaBar.length > 0) {
        var formula = sheet.getFormula(sheet.getActiveRowIndex(), sheet.getActiveColumnIndex());
        if (formula) {
            formula = "=" + formula;
            formulaBar.text(formula);
        }
    }
}

function removeContinuousComma(parameter) {
    var len = parameter.length;
    while (len > 0 && parameter[len - 1] === ",") {
        len--;
    }
    return parameter.substr(0, len);
}

function formatFormula(paraArray) {
    var params = "";
    for (var i = 0; i < paraArray.length; i++) {
        var item = paraArray[i];
        if (item !== undefined && item !== null) {
            params += item + ",";
        }
        else {
            params += ",";
        }
    }
    params = removeContinuousComma(params);
    return params;
}

function getFormula(params) {
    var len = params.length;
    while (len > 0 && params[len - 1] === "") {
        len--;
    }
    var temp = "";
    for (var i = 0; i < len; i++) {
        temp += params[i];
        if (i !== len - 1) {
            temp += ",";
        }
    }
    return "=AREASPARKLINE(" + temp + ")";
}

function setFormulaSparkline(formula) {
    var sheet = spread.getActiveSheet();
    var row = sheet.getActiveRowIndex();
    var col = sheet.getActiveColumnIndex();
    if (formula) {
        sheet.setFormula(row, col, formula);
    }
}

function applyAreaSparklineSetting() {
    var points = getTextValue("areaSparklinePoints");
    var mini = getNumberValue("areaSparklineMinimumValue");
    var maxi = getNumberValue("areaSparklineMaximumValue");
    var line1 = getNumberValue("areaSparklineLine1");
    var line2 = getNumberValue("areaSparklineLine2");
    var colorPositive = "\"" + getBackgroundColor("areaSparklinePositiveColor") + "\"";
    var colorNegative = "\"" + getBackgroundColor("areaSparklineNegativeColor") + "\"";
    var paramArr = [points, mini, maxi, line1, line2, colorPositive, colorNegative];
    var formula = getFormula(paramArr);
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyBoxPlotSparklineSetting() {
    var pointsValue = getTextValue("boxplotSparklinePoints");
    var boxPlotClassValue = getDropDownValue("boxplotClassType");
    var showAverageValue = getCheckValue("boxplotSparklineShowAverage");
    var scaleStartValue = getTextValue("boxplotSparklineScaleStart");
    var scaleEndValue = getTextValue("boxplotSparklineScaleEnd");
    var acceptableStartValue = getTextValue("boxplotSparklineAcceptableStart");
    var acceptableEndValue = getTextValue("boxplotSparklineAcceptableEnd");
    var colorValue = getBackgroundColor("boxplotSparklineColorScheme");
    var styleValue = getDropDownValue("boxplotSparklineStyleType");
    var verticalValue = getCheckValue("boxplotSparklineVertical");

    var boxplotClassStr = boxPlotClassValue ? "\"" + boxPlotClassValue + "\"" : null;
    var colorStr = colorValue ? "\"" + colorValue + "\"" : null;
    var paraPool = [
        pointsValue,
        boxplotClassStr,
        showAverageValue,
        scaleStartValue,
        scaleEndValue,
        acceptableStartValue,
        acceptableEndValue,
        colorStr,
        styleValue,
        verticalValue
    ];
    var params = formatFormula(paraPool);
    var formula = "=BOXPLOTSPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyBulletSparklineSetting() {
    var measureValue = getTextValue("bulletSparklineMeasure");
    var targetValue = getTextValue("bulletSparklineTarget");
    var maxiValue = getTextValue("bulletSparklineMaxi");
    var goodValue = getTextValue("bulletSparklineGood");
    var badValue = getTextValue("bulletSparklineBad");
    var forecastValue = getTextValue("bulletSparklineForecast");
    var tickunitValue = getTextValue("bulletSparklineTickUnit");
    var colorSchemeValue = getBackgroundColor("bulletSparklineColorScheme");
    var verticalValue = getCheckValue("bulletSparklineVertical");

    var colorSchemeString = colorSchemeValue ? "\"" + colorSchemeValue + "\"" : null;
    var paraPool = [
        measureValue,
        targetValue,
        maxiValue,
        goodValue,
        badValue,
        forecastValue,
        tickunitValue,
        colorSchemeString,
        verticalValue
    ];

    var params = formatFormula(paraPool);
    var formula = "=BULLETSPARKLINE(" + params + ")";
    var sheet = spread.getActiveSheet();
    var sels = sheet.getSelections();
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyCascadeSparklineSetting() {
    var pointsRangeValue = getTextValue("cascadeSparklinePointsRange");
    var pointIndexValue = getTextValue("cascadeSparklinePointIndex");
    var labelsRangeValue = getTextValue("cascadeSparklineLabelsRange");
    var minimumValue = getTextValue("cascadeSparklineMinimum");
    var maximumValue = getTextValue("cascadeSparklineMaximum");
    var colorPositiveValue = getBackgroundColor("cascadeSparklinePositiveColor");
    var colorNegativeValue = getBackgroundColor("cascadeSparklineNegativeColor");
    var verticalValue = getCheckValue("cascadeSparklineVertical");

    var colorPositiveStr = colorPositiveValue ? "\"" + colorPositiveValue + "\"" : null;
    var colorNegativeStr = colorNegativeValue ? "\"" + colorNegativeValue + "\"" : null;
    paraPool = [
        pointsRangeValue,
        pointIndexValue,
        labelsRangeValue,
        minimumValue,
        maximumValue,
        colorPositiveStr,
        colorNegativeStr,
        verticalValue
    ];

    var params = formatFormula(paraPool);
    var formula = "=CASCADESPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyCompatibleSparklineSetting() {
    var data = getTextValue("compatibleSparklineData");
    var dataOrientation = getDropDownValue("dataOrientationType");
    var dateAxisData = getTextValue("compatibleSparklineDateAxisData");
    var dateAxisOrientation = getDropDownValue("dateAxisOrientationType");
    if (dateAxisOrientation === undefined) {
        dateAxisOrientation = "";
    }

    var sparklineSetting = {}, minAxisType, maxAxisType;
    sparklineSetting.displayEmptyCellsAs = getDropDownValue("emptyCellDisplayType");
    sparklineSetting.displayHidden = getCheckValue("showDataInHiddenRowOrColumn");
    sparklineSetting.showFirst = getCheckValue("compatibleSparklineShowFirst");
    sparklineSetting.showLast = getCheckValue("compatibleSparklineShowLast");
    sparklineSetting.showHigh = getCheckValue("compatibleSparklineShowHigh");
    sparklineSetting.showLow = getCheckValue("compatibleSparklineShowLow");
    sparklineSetting.showNegative = getCheckValue("compatibleSparklineShowNegative");
    sparklineSetting.showMarkers = getCheckValue("compatibleSparklineShowMarkers");
    minAxisType = getDropDownValue("minAxisType");
    sparklineSetting.minAxisType = Sparklines.SparklineAxisMinMax[minAxisType];
    sparklineSetting.manualMin = getTextValue("manualMin");
    maxAxisType = getDropDownValue("maxAxisType");
    sparklineSetting.maxAxisType = Sparklines.SparklineAxisMinMax[maxAxisType];
    sparklineSetting.manualMax = getTextValue("manualMax");
    sparklineSetting.rightToLeft = getCheckValue("rightToLeft");
    sparklineSetting.displayXAxis = getCheckValue("displayXAxis");

    sparklineSetting.negativeColor = getBackgroundColor("compatibleSparklineNegativeColor");
    sparklineSetting.markersColor = getBackgroundColor("compatibleSparklineMarkersColor");
    sparklineSetting.axisColor = getBackgroundColor("compatibleSparklineAxisColor");
    sparklineSetting.seriesColor = getBackgroundColor("compatibleSparklineSeriesColor");
    sparklineSetting.highMarkerColor = getBackgroundColor("compatibleSparklineHighMarkerColor");
    sparklineSetting.lowMarkerColor = getBackgroundColor("compatibleSparklineLowMarkerColor");
    sparklineSetting.firstMarkerColor = getBackgroundColor("compatibleSparklineFirstMarkerColor");
    sparklineSetting.lastMarkerColor = getBackgroundColor("compatibleSparklineLastMarkerColor");
    sparklineSetting.lineWeight = getTextValue("compatibleSparklineLastLineWeight");

    var settingArray = [];
    for (var item in sparklineSetting) {
        if (sparklineSetting[item] !== undefined && sparklineSetting[item] !== "") {
            settingArray.push(item + ":" + sparklineSetting[item]);
        }
    }
    var settingString = "";
    if (settingArray.length > 0) {
        settingString = "\"{" + settingArray.join(",") + "}\"";
    }

    var formula = "";
    if (settingString !== "") {
        formula = "=" + sparklineName + "(" + data + "," + dataOrientation +
            "," + dateAxisData + "," + dateAxisOrientation + "," + settingString + ")";
    }
    else {
        if (dateAxisOrientation !== "") {
            formula = "=" + sparklineName + "(" + data + "," + dataOrientation +
                "," + dateAxisData + "," + dateAxisOrientation + ")";
        }
        else {
            if (dateAxisData !== "") {
                formula = "=" + sparklineName + "(" + data + "," + dataOrientation +
                    "," + dateAxisData + ")";
            }
            else {
                formula = "=" + sparklineName + "(" + data + "," + dataOrientation + ")";
            }
        }
    }

    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyScatterSparklineSetting() {
    var paraPool = [];
    var inputList = ["scatterSparklinePoints1", "scatterSparklinePoints2", "scatterSparklineMinX", "scatterSparklineMaxX",
        "scatterSparklineMinY", "scatterSparklineMaxY", "scatterSparklineHLine", "scatterSparklineVLine",
        "scatterSparklineXMinZone", "scatterSparklineXMaxZone", "scatterSparklineYMinZone", "scatterSparklineYMaxZone"];
    for (var i = 0; i < inputList.length; i++) {
        var textValue = getTextValue(inputList[i]);
        paraPool.push(textValue);
    }
    var tags = getCheckValue("scatterSparklineTags");
    var drawSymbol = getCheckValue("scatterSparklineDrawSymbol");
    var drawLines = getCheckValue("scatterSparklineDrawLines");
    var color1 = getBackgroundColor("scatterSparklineColor1");
    var color2 = getBackgroundColor("scatterSparklineColor2");
    var dashLine = getCheckValue("scatterSparklineDashLine");

    color1 = color1 ? "\"" + color1 + "\"" : null;
    color2 = color2 ? "\"" + color2 + "\"" : null;

    paraPool.push(tags);
    paraPool.push(drawSymbol);
    paraPool.push(drawLines);
    paraPool.push(color1);
    paraPool.push(color2);
    paraPool.push(dashLine);
    var params = formatFormula(paraPool);
    var formula = "=SCATTERSPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();

}

function applyHbarSparklineSetting() {
    var paraPool = [];
    var value = getTextValue("hbarSparklineValue");
    var colorScheme = getBackgroundColor("hbarSparklineColorScheme");

    colorScheme = "\"" + colorScheme + "\"";
    paraPool.push(value);
    paraPool.push(colorScheme);
    var params = formatFormula(paraPool);
    var formula = "=HBARSPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyVbarSparklineSetting() {
    var paraPool = [];
    var value = getTextValue("vbarSparklineValue");
    var colorScheme = getBackgroundColor("vbarSparklineColorScheme");

    colorScheme = "\"" + colorScheme + "\"";
    paraPool.push(value);
    paraPool.push(colorScheme);
    var params = formatFormula(paraPool);
    var formula = "=VBARSPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyParetoSparklineSetting() {
    var pointsRangeValue = getTextValue("paretoSparklinePoints");
    var pointIndexValue = getTextValue("paretoSparklinePointIndex");
    var colorRangeValue = getTextValue("paretoSparklineColorRange");
    var targetValue = getTextValue("paretoSparklineTarget");
    var target2Value = getTextValue("paretoSparklineTarget2");
    var highlightPositionValue = getTextValue("paretoSparklineHighlightPosition");
    var labelValue = getDropDownValue("paretoLabelType");
    var verticalValue = getCheckValue("paretoSparklineVertical");
    var paraPool = [
        pointsRangeValue,
        pointIndexValue,
        colorRangeValue,
        targetValue,
        target2Value,
        highlightPositionValue,
        labelValue,
        verticalValue
    ];
    var params = formatFormula(paraPool);
    var formula = "=PARETOSPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applySpreadSparklineSetting() {
    var pointsValue = getTextValue("spreadSparklinePoints");
    var showAverageValue = getCheckValue("spreadSparklineShowAverage");
    var scaleStartValue = getTextValue("spreadSparklineScaleStart");
    var scaleEndValue = getTextValue("spreadSparklineScaleEnd");
    var styleValue = getDropDownValue("spreadSparklineStyleType");
    var colorSchemeValue = getBackgroundColor("spreadSparklineColorScheme");
    var verticalValue = getCheckValue("spreadSparklineVertical");

    var colorSchemeString = colorSchemeValue ? "\"" + colorSchemeValue + "\"" : null;
    var paraPool = [
        pointsValue,
        showAverageValue,
        scaleStartValue,
        scaleEndValue,
        styleValue,
        colorSchemeString,
        verticalValue
    ];
    var params = formatFormula(paraPool);
    var formula = "=SPREADSPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyStackedSparklineSetting() {
    var pointsValue = getTextValue("stackedSparklinePoints");
    var colorRangeValue = getTextValue("stackedSparklineColorRange");
    var labelRangeValue = getTextValue("stackedSparklineLabelRange");
    var maximumValue = getNumberValue("stackedSparklineMaximum");
    var targetRedValue = getNumberValue("stackedSparklineTargetRed");
    var targetGreenValue = getNumberValue("stackedSparklineTargetGreen");
    var targetBlueValue = getNumberValue("stackedSparklineTargetBlue");
    var targetYellowValue = getNumberValue("stackedSparklineTargetYellow");
    var colorValue = getBackgroundColor("stackedSparklineColor");
    var highlightPositionValue = getNumberValue("stackedSparklineHighlightPosition");
    var verticalValue = getCheckValue("stackedSparklineVertical");
    var textOrientationValue = getDropDownValue("stackedSparklineTextOrientation");
    var textSizeValue = getNumberValue("stackedSparklineTextSize");

    var colorString = colorValue ? "\"" + colorValue + "\"" : null;
    var paraPool = [
        pointsValue,
        colorRangeValue,
        labelRangeValue,
        maximumValue,
        targetRedValue,
        targetGreenValue,
        targetBlueValue,
        targetYellowValue,
        colorString,
        highlightPositionValue,
        verticalValue,
        textOrientationValue,
        textSizeValue
    ];
    var params = formatFormula(paraPool);
    var formula = "=STACKEDSPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyVariSparklineSetting() {
    var varianceValue = getTextValue("variSparklineVariance");
    var referenceValue = getTextValue("variSparklineReference");
    var miniValue = getTextValue("variSparklineMini");
    var maxiValue = getTextValue("variSparklineMaxi");
    var markValue = getTextValue("variSparklineMark");
    var tickunitValue = getTextValue("variSparklineTickUnit");
    var colorPositiveValue = getBackgroundColor("variSparklineColorPositive");
    var colorNegativeValue = getBackgroundColor("variSparklineColorNegative");
    var legendValue = getCheckValue("variSparklineLegend");
    var verticalValue = getCheckValue("variSparklineVertical");

    var colorPositiveStr = colorPositiveValue ? "\"" + colorPositiveValue + "\"" : null;
    var colorNegativeStr = colorNegativeValue ? "\"" + colorNegativeValue + "\"" : null;
    var paraPool = [
        varianceValue,
        referenceValue,
        miniValue,
        maxiValue,
        markValue,
        tickunitValue,
        legendValue,
        colorPositiveStr,
        colorNegativeStr,
        verticalValue
    ];
    var params = formatFormula(paraPool);
    var formula = "=VARISPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}

function applyMonthSparklineSetting() {
    var dataRangeStr = getTextValue("monthSparklineData");
    var year = getTextValue("monthSparklineYear");
    var month = getTextValue("monthSparklineMonth");
    var emptyColor = getBackgroundColor("monthSparklineEmptyColor");
    var startColor = getBackgroundColor("monthSparklineStartColor");
    var middleColor = getBackgroundColor("monthSparklineMiddleColor");
    var endColor = getBackgroundColor("monthSparklineEndColor");
    var colorRangeStr = getTextValue("monthSparklineColorRange");
    var formulaStr;
    if (!colorRangeStr) {
        formulaStr = "=" + "MONTHSPARKLINE" + "(" + year + "," + month + "," + dataRangeStr + "," + parseSparklineColorOptions(emptyColor) + "," + parseSparklineColorOptions(startColor) + "," + parseSparklineColorOptions(middleColor) + "," + parseSparklineColorOptions(endColor) + ")";
    } else {
        formulaStr = "=" + "MONTHSPARKLINE" + "(" + year + "," + month + "," + dataRangeStr + "," + colorRangeStr + ")";
    }
    setFormulaSparkline(formulaStr);
    updateFormulaBar();
}

function applyYearSparklineSetting() {
    var dataRangeStr = getTextValue("yearSparklineData");
    var year = getTextValue("yearSparklineYear");
    var emptyColor = getBackgroundColor("yearSparklineEmptyColor");
    var startColor = getBackgroundColor("yearSparklineStartColor");
    var middleColor = getBackgroundColor("yearSparklineMiddleColor");
    var endColor = getBackgroundColor("yearSparklineEndColor");
    var colorRangeStr = getTextValue("yearSparklineColorRange");
    var formulaStr;
    if (!colorRangeStr) {
        formulaStr = "=" + "YEARSPARKLINE" + "(" + year + "," + dataRangeStr + "," + parseSparklineColorOptions(emptyColor) + "," + parseSparklineColorOptions(startColor) + "," + parseSparklineColorOptions(middleColor) + "," + parseSparklineColorOptions(endColor) + ")";
    } else {
        formulaStr = "=" + "YEARSPARKLINE" + "(" + year + "," + dataRangeStr + "," + colorRangeStr + ")";
    }
    setFormulaSparkline(formulaStr);
    updateFormulaBar();
}

function applyPieSparklineSetting() {
    var paraPool = [];
    var range = getTextValue("pieSparklinePercentage");
    paraPool.push(range);

    $("#pieSparklineColorContainer").find(".color-view").each(function () {
        var color = "\"" + $(this).css("background-color") + "\"";
        paraPool.push(color);
    });

    var params = formatFormula(paraPool);
    var formula = "=PIESPARKLINE(" + params + ")";
    setFormulaSparkline(formula);
    updateFormulaBar();
}
// Sparkline related items (end)

// Zoom related items
function processZoomSetting(value, title) {
    if (typeof value === 'number') {
        spread.getActiveSheet().zoom(value);
    }
    else {
        console.log("processZoomSetting not process with ", value, title);
    }
}
// Zoom related items (end)

function getResource(key) {
    key = key.replace(/\./g, "_");

    return resourceMap[key];
}

function getResourceMap(src) {
    function isObject(item) {
        return typeof item === "object";
    }

    function addResourceMap(map, obj, keys) {
        if (isObject(obj)) {
            for (var p in obj) {
                var cur = obj[p];

                addResourceMap(map, cur, keys.concat(p));
            }
        } else {
            var key = keys.join("_");
            map[key] = obj;
        }
    }

    addResourceMap(resourceMap, src, []);
}

$(document).ready(function () {
    function localizeUI() {
        function getLocalizeString(text) {
            var matchs = text.match(/(?:(@[\w\d\.]*@))/g);

            if (matchs) {
                matchs.forEach(function (item) {
                    var s = getResource(item.replace(/[@]/g, ""));
                    text = text.replace(item, s);
                });
            }

            return text;
        }

        $(".localize").each(function () {
            var text = $(this).text();

            $(this).text(getLocalizeString(text));
        });

        $(".localize-tooltip").each(function () {
            var text = $(this).prop("title");

            $(this).prop("title", getLocalizeString(text));
        });

        $(".localize-value").each(function () {
            var text = $(this).attr("value");

            $(this).attr("value", getLocalizeString(text));
        });
    }

    getResourceMap(uiResource);

    localizeUI();

    spread = new spreadNS.Workbook($("#ss")[0], {tabStripRatio: 0.88});
    excelIO = new GC.Spread.Excel.IO();
    getThemeColor();
    initSpread();

    //Change default allowCellOverflow the same with Excel.
    spread.sheets.forEach(function (sheet) {
        sheet.options.allowCellOverflow = true;
    });

    //window resize adjust
    $(".insp-container").draggable();
    checkMediaSize();
    screenAdoption();
    var resizeTimeout = null;
    $(window).bind("resize", function () {
        if (resizeTimeout === null) {
            resizeTimeout = setTimeout(function () {
                screenAdoption();
                clearTimeout(resizeTimeout);
                resizeTimeout = null;
            }, 100);
        }
    });

    doPrepareWork();

    $("ul.dropdown-menu>li>a").click(function () {
        var value = $(this).text(),
            $divhost = $(this).parents("div.btn-group"),
            groupName = $divhost.data("name"),
            sheet = spread.getActiveSheet();

        $divhost.find("button:first").text(value);

        switch (groupName) {
            case "fontname":
                setStyleFont(sheet, "font-family", false, [value], value);
                break;

            case "fontsize":
                setStyleFont(sheet, "font-size", false, [value], value);
                break;
        }
    });

    var toolbarHeight = $("#toolbar").height(),
        formulaboxDefaultHeight = $("#formulabox").outerHeight(true),
        verticalSplitterOriginalTop = formulaboxDefaultHeight - $("#verticalSplitter").height();
    $("#verticalSplitter").draggable({
        axis: "y",              // vertical only
        containment: "#inner-content-container",  // limit in specified range
        scroll: false,          // not allow container scroll
        zIndex: 100,            // set to move on top
        stop: function (event, ui) {
            var $this = $(this),
                top = $this.offset().top,
                offset = top - toolbarHeight - verticalSplitterOriginalTop;

            // limit min size
            if (offset < 0) {
                offset = 0;
            }
            // adjust size of related items
            $("#formulabox").css({height: formulaboxDefaultHeight + offset});
            var height = $("div.insp-container").height() - $("#formulabox").outerHeight(true);
            $("#controlPanel").height(height);
            $("#ss").height(height);
            spread.refresh();
            // reset
            $(this).css({top: 0});
        }
    });

    attachEvents();

    $(document).on("contextmenu", function (e) {
        var evt = window.event || e;
        if (!$(evt.target).data('contextmenu')) {
            evt.preventDefault();
            return false;
        }
    });

    $("#download").on("click", function (e) {
        e.preventDefault();
        return false;
    });

    spread.focus();

    syncSheetPropertyValues();
    syncSpreadPropertyValues();

    onCellSelected();

    updatePositionBox(spread.getActiveSheet());

    //fix bug 220484
    if (isIE) {
        $("#formulabox").css('padding', 0);
    }
});

// context menu related items
function getCellInSelections(selections, row, col) {
    var count = selections.length, range;
    for (var i = 0; i < count; i++) {
        range = selections[i];
        if (range.contains(row, col)) {
            return range;
        }
    }
    return null;
}
function getHitTest(pageX, pageY, sheet) {
    var offset = $("#ss").offset(),
        x = pageX - offset.left,
        y = pageY - offset.top;
    return sheet.hitTest(x, y);
}
function showMergeContextMenu() {
    // use the result of updateMergeButtonsState
    if ($("#mergeCells").attr("disabled")) {
        $(".context-merge").hide();
    } else {
        $(".context-cell.divider").show();
        $(".context-merge").show();
    }

    if ($("#unmergeCells").attr("disabled")) {
        $(".context-unmerge").hide();
    } else {
        $(".context-cell.divider").show();
        $(".context-unmerge").show();
    }
}

function processSpreadContextMenu(e) {
    // move the context menu to the position of the mouse point
    var sheet = spread.getActiveSheet(),
        target = getHitTest(e.pageX, e.pageY, sheet),
        hitTestType = target.hitTestType,
        row = target.row,
        col = target.col,
        selections = sheet.getSelections();

    var isHideContextMenu = false;

    if (hitTestType === spreadNS.SheetArea.colHeader) {
        if (getCellInSelections(selections, row, col) === null) {
            sheet.setSelection(-1, col, sheet.getRowCount(), 1);
        }
        if (row !== undefined && col !== undefined) {
            $(".context-header").show();
            $(".context-cell").hide();
        }
    } else if (hitTestType === spreadNS.SheetArea.rowHeader) {
        if (getCellInSelections(selections, row, col) === null) {
            sheet.setSelection(row, -1, 1, sheet.getColumnCount());
        }
        if (row !== undefined && col !== undefined) {
            $(".context-header").show();
            $(".context-cell").hide();
        }
    } else if (hitTestType === spreadNS.SheetArea.viewport) {
        if (getCellInSelections(selections, row, col) === null) {
            sheet.clearSelection();
            sheet.endEdit();
            sheet.setActiveCell(row, col);
            updateMergeButtonsState();
        }
        if (row !== undefined && col !== undefined) {
            $(".context-header").hide();
            $(".context-cell").hide();
            showMergeContextMenu();
        } else {
            isHideContextMenu = true;
        }
    } else if (hitTestType === spreadNS.SheetArea.corner) {
        sheet.setSelection(-1, -1, sheet.getRowCount(), sheet.getColumnCount());
        if (row !== undefined && col !== undefined) {
            $(".context-header").hide();
            $(".context-cell").show();
        }
    }

    var $contextMenu = $("#spreadContextMenu");
    $contextMenu.data("sheetArea", hitTestType);
    if (isHideContextMenu) {
        hideSpreadContextMenu();
    } else {
        $contextMenu.css({left: e.pageX, top: e.pageY});
        $contextMenu.show();

        $(document).on("mousedown.contextmenu", function () {
            if ($(event.target).parents("#spreadContextMenu").length === 0) {
                hideSpreadContextMenu();
            }
        });
    }
}

function hideSpreadContextMenu() {
    $("#spreadContextMenu").hide();
    $(document).off("mousedown.contextmenu");
}

function processContextMenuClicked() {
    var action = $(this).data("action");
    var sheet = spread.getActiveSheet();
    var sheetArea = $("#spreadContextMenu").data("sheetArea");

    hideSpreadContextMenu();

    switch (action) {
        case "cut":
            spread.commandManager().execute({cmd: "cut", sheetName: sheet.name()});
            break;
        case "copy":
            spread.commandManager().execute({cmd: "copy", sheetName: sheet.name()});
            break;
        case "paste":
            spread.commandManager().execute({cmd: "paste", sheetName: sheet.name()});
            break;
        case "insert":
            if (sheetArea === spreadNS.SheetArea.colHeader) {
                var insertColumnIndex = sheet.getActiveColumnIndex();
                var colCount = sheet.getSelections()[0].colCount;
                sheet.addColumns(insertColumnIndex, colCount);
                for (var i = 0; i < colCount; i++) {
                    sheet.copyTo(-1, insertColumnIndex - 1, -1, insertColumnIndex + i, -1, 1, GC.Spread.Sheets.CopyToOptions.style);
                }
            } else if (sheetArea === spreadNS.SheetArea.rowHeader) {
                var insertRowIndex = sheet.getActiveRowIndex();
                var rowCount = sheet.getSelections()[0].rowCount;
                sheet.addRows(insertRowIndex, rowCount);
                for (var i = 0; i < rowCount; i++) {
                    sheet.copyTo(insertRowIndex - 1, -1, insertRowIndex + i, -1, 1, -1, GC.Spread.Sheets.CopyToOptions.style);
                }
            }
            break;
        case "delete":
            if (sheetArea === spreadNS.SheetArea.colHeader) {
                sheet.deleteColumns(sheet.getActiveColumnIndex(), sheet.getSelections()[0].colCount);
            } else if (sheetArea === spreadNS.SheetArea.rowHeader) {
                sheet.deleteRows(sheet.getActiveRowIndex(), sheet.getSelections()[0].rowCount);
            }
            break;
        case "merge":
            var sel = sheet.getSelections();
            if (sel.length > 0) {
                sel = sel[sel.length - 1];
                sheet.addSpan(sel.row, sel.col, sel.rowCount, sel.colCount, spreadNS.SheetArea.viewport);
            }
            updateMergeButtonsState();
            break;
        case "unmerge":
            var sels = sheet.getSelections();
            for (var i = 0; i < sels.length; i++) {
                var sel = getActualCellRange(sheet, sels[i], sheet.getRowCount(), sheet.getColumnCount());
                for (var r = 0; r < sel.rowCount; r++) {
                    for (var c = 0; c < sel.colCount; c++) {
                        var span = sheet.getSpan(r + sel.row, c + sel.col, spreadNS.SheetArea.viewport);
                        if (span) {
                            sheet.removeSpan(span.row, span.col, spreadNS.SheetArea.viewport);
                        }
                    }
                }
            }
            updateMergeButtonsState();
            break;
        default:
            break;
    }
}
// context menu related items (end)

// import / export related items
function processExportAction($dropdown, action) {
    switch (action) {
        case "exportJson":
            exportToJSON();
            break;
        case "exportExcel":
            exportToExcel();
            break;
        default:
            break;
    }
    hideExportActionDropDown();
}

function importFile(file) {
    var fileName = file.name;
    var index = fileName.lastIndexOf('.');
    var fileExt = fileName.substr(index + 1).toLowerCase();
    if (fileExt === 'json' || fileExt === 'ssjson') {
        importSpreadFromJSON(file);
    } else if (fileExt === 'xlsx') {
        importSpreadFromExcel(file);
    } else {
        alert(getResource("messages.invalidImportFile"));
    }
}
function importSpreadFromExcel(file, options) {
    function processPasswordDialog() {
        importSpreadFromExcel(file, {password: getTextValue("txtPassword")});
        setTextValue("txtPassword", "");
    }

    var PASSWORD_DIALOG_WIDTH = 300;
    excelIO.open(file, function (json) {
        importJson(json);
    }, function (e) {
        if (e.errorCode === 0 || e.errorCode === 1) {
            alert(getResource("messages.invalidImportFile"));
        } else if (e.errorCode === 2) {
            $("#passwordError").hide();
            showModal(uiResource.passwordDialog.title, PASSWORD_DIALOG_WIDTH, $("#passwordDialog").children(), processPasswordDialog);
        } else if (e.errorCode === 3) {
            $("#passwordError").show();
            showModal(uiResource.passwordDialog.title, PASSWORD_DIALOG_WIDTH, $("#passwordDialog").children(), processPasswordDialog);
        }
    }, options);
}
function importSpreadFromJSON(file) {
    function importSuccessCallback(responseText) {
        var spreadJson = JSON.parse(responseText);
        importJson(spreadJson);
    }

    var reader = new FileReader();
    reader.onload = function () {
        importSuccessCallback(this.result);
    };
    reader.readAsText(file);
    return true;
}
function importJson(spreadJson) {
    function updateActiveCells() {
        for (var i = 0; i < spread.getSheetCount(); i++) {
            var sheet = spread.getSheet(i);
            columnIndex = sheet.getActiveColumnIndex(),
                rowIndex = sheet.getActiveRowIndex();
            if (columnIndex !== undefined && rowIndex !== undefined) {
                spread.getSheet(i).setActiveCell(rowIndex, columnIndex);
            } else {
                spread.getSheet(i).setActiveCell(0, 0);
            }
        }
    }

    if (spreadJson.version && spreadJson.sheets) {
        spread.unbindAll();
        spread.fromJSON(spreadJson);
        attachSpreadEvents(true);
        updateActiveCells();
        spread.focus();
        fbx.workbook(spread);
        onCellSelected();
        syncSpreadPropertyValues();
        syncSheetPropertyValues();
    } else {
        alert(getResource("messages.invalidImportFile"));
    }
}

function getFileName() {
    function to2DigitsString(num) {
        return ("0" + num).substr(-2);
    }

    var date = new Date();
    return [
        "export",
        date.getFullYear(), to2DigitsString(date.getMonth() + 1), to2DigitsString(date.getDate()),
        to2DigitsString(date.getHours()), to2DigitsString(date.getMinutes()), to2DigitsString(date.getSeconds())
    ].join("");
}

function exportToJSON() {
    var json = spread.toJSON({includeBindingSource: true}),
        text = JSON.stringify(json);
    var fileName = getFileName();
    if (isSafari) {
        showModal(uiResource.toolBar.downloadTitle, DOWNLOAD_DIALOG_WIDTH, $("#downloadDialog").children(), function () {
            $("#downloadDialog").hide();
        });
        var link = $("#download");
        link[0].href = "data:text/plain;" + text;
    } else {
        saveAs(new Blob([text], {type: "text/plain;charset=utf-8"}), fileName + ".json");
    }
}

function exportToExcel() {
    var fileName = getFileName();
    var json = spread.toJSON({includeBindingSource: true});
    excelIO.save(json, function (blob) {
        if (isSafari) {
            var reader = new FileReader();
            reader.onloadend = function () {
                showModal(uiResource.toolBar.downloadTitle, DOWNLOAD_DIALOG_WIDTH, $("#downloadDialog").children(), function () {
                    $("#downloadDialog").hide();
                });
                var link = $("#download");
                link[0].href = reader.result;
            };
            reader.readAsDataURL(blob);
        } else {
            saveAs(blob, fileName + ".xlsx");
        }
    }, function (e) {
        alert(e);
    });
}
// import / export related items (end)

// format related items
function processFormatSetting(name, title) {
    switch (name) {
        case "nullValue":
            name = null;
        case "0.00":
        case "$#,##0.00":
        case "$ #,##0.00;$ (#,##0.00);$ '-'??;@":
        case "m/d/yyyy":
        case "dddd, mmmm dd, yyyy":
        case "h:mm:ss AM/PM":
        case "0%":
        case "# ?/?":
        case "0.00E+00":
        case "@":
            setFormatter(name);
            break;

        default:
            console.log("processFormatSetting not process with ", name, title);
            break;
    }
}

function setFormatter(value) {
    var sheet = spread.getActiveSheet();
    execInSelections(sheet, "formatter", function (sheet, row, column) {
        var style = sheet.getStyle(row, column);
        if (!style) {
            style = new spreadNS.Style();
        }
        style.formatter = value;
        sheet.setStyle(row, column, style);
    });
}

function execInSelections(sheet, styleProperty, func) {
    var selections = sheet.getSelections();
    for (var k = 0; k < selections.length; k++) {
        var selection = selections[k];
        var col = selection.col, row = selection.row,
            rowCount = selection.rowCount, colCount = selection.colCount;
        if ((col === -1 || row === -1) && styleProperty) {
            var style, r, c;
            // whole sheet was selected, need set row / column' style one by one
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
            // Get actual range for whole rows / columns / sheet selection
            if (col === -1) {
                col = 0;
            }
            if (row === -1) {
                row = 0;
            }
            // set to each cell with style that in the adjusted selection range
            for (var i = 0; i < rowCount; i++) {
                r = row + i;
                for (var j = 0; j < colCount; j++) {
                    c = col + j;
                    if ((style = sheet.getStyle(r, c)) && style[styleProperty] !== undefined) {
                        func(sheet, r, c);
                    }
                }
            }
        }
        if (selection.col == -1 && selection.row == -1) {
            func(sheet, -1, -1);
        }
        else if (selection.row == -1) {
            for (var i = 0; i < selection.colCount; i++) {
                func(sheet, -1, selection.col + i);
            }
        }
        else if (selection.col == -1) {
            for (var i = 0; i < selection.rowCount; i++) {
                func(sheet, selection.row + i, -1);
            }
        }
        else {
            for (var i = 0; i < selection.rowCount; i++) {
                for (var j = 0; j < selection.colCount; j++) {
                    func(sheet, selection.row + i, selection.col + j);
                }
            }
        }
    }
}
// format related items (end)

// dialog related items
function showModal(title, width, content, callback) {
    var $dialog = $("#modalTemplate"),
        $body = $(".modal-body", $dialog);

    $(".modal-title", $dialog).text(title);
    $dialog.data("content-parent", content.parent());
    $body.append(content);

    // remove old and add new event handler since this modal is common used (reused)
    $("#dialogConfirm").off("click");
    $("#dialogConfirm").on("click", function () {
        var result = callback();

        // return an object with  { canceled: true } to tell not close the modal, otherwise close the modal
        if (!(result && result.canceled)) {
            $("#modalTemplate").modal("hide");
        }
    });

    if (!$dialog.data("event-attached")) {
        $dialog.on("hidden.bs.modal", function () {
            var $originalParent = $(this).data("content-parent");
            if ($originalParent) {
                $originalParent.append($(".modal-body", this).children());
            }
        });
        $dialog.data("event-attached", true);
    }

    // set width of the dialog
    $(".modal-dialog", $dialog).css({width: width});

    $dialog.modal("show");
}
// dialog related items (end)

// clear related items
function processClearAction($dropdown, action) {
    switch (action) {
        case "clearAll":
            doClear(255, true);   // Laze mark all types with 255 (0xFF)
            break;
        case "clearFormat":
            doClear(spreadNS.StorageType.style, true);
            break;
        default:
            break;
    }
    hideClearActionDropDown();
}

function clearSpansInSelection(sheet, selection) {
    if (sheet && selection) {
        var ranges = [],
            row = selection.row, col = selection.col,
            rowCount = selection.rowCount, colCount = selection.colCount;

        sheet.getSpans().forEach(function (range) {
            if (range.intersect(row, col, rowCount, colCount)) {
                ranges.push(range);
            }
        });
        ranges.forEach(function (range) {
            sheet.removeSpan(range.row, range.col);
        });
    }
}

function doClear(types, clearSpans) {
    var sheet = spread.getActiveSheet(),
        selections = sheet.getSelections();

    selections.forEach(function (selection) {
        sheet.clear(selection.row, selection.col, selection.rowCount, selection.colCount, spreadNS.SheetArea.viewport, types);
        if (clearSpans) {
            clearSpansInSelection(sheet, selection);
        }
    });
}
// clear related items (end)

// positionbox related items
function getSelectedRangeString(sheet, range) {
    var selectionInfo = "",
        rowCount = range.rowCount,
        columnCount = range.colCount,
        startRow = range.row + 1,
        startColumn = range.col + 1;

    if (rowCount == 1 && columnCount == 1) {
        selectionInfo = getCellPositionString(sheet, startRow, startColumn);
    }
    else {
        if (rowCount < 0 && columnCount > 0) {
            selectionInfo = columnCount + "C";
        }
        else if (columnCount < 0 && rowCount > 0) {
            selectionInfo = rowCount + "R";
        }
        else if (rowCount < 0 && columnCount < 0) {
            selectionInfo = sheet.getRowCount() + "R x " + sheet.getColumnCount() + "C";
        }
        else {
            selectionInfo = rowCount + "R x " + columnCount + "C";
        }
    }
    return selectionInfo;
}

function getCellPositionString(sheet, row, column) {
    if (row < 1 || column < 1) {
        return null;
    }
    else {
        var letters = "";
        switch (spread.options.referenceStyle) {
            case spreadNS.ReferenceStyle.a1: // 0
                while (column > 0) {
                    var num = column % 26;
                    if (num === 0) {
                        letters = "Z" + letters;
                        column--;
                    }
                    else {
                        letters = String.fromCharCode('A'.charCodeAt(0) + num - 1) + letters;
                    }
                    column = parseInt((column / 26).toString());
                }
                letters += row.toString();
                break;
            case spreadNS.ReferenceStyle.r1c1: // 1
                letters = "R" + row.toString() + "C" + column.toString();
                break;
            default:
                break;
        }
        return letters;
    }
}
// positionbox related items (end)

// theme color related items
function setThemeColorToSheet(sheet) {
    sheet.suspendPaint();

    sheet.getCell(2, 3).text("Background 1").themeFont("Body");
    sheet.getCell(2, 4).text("Text 1").themeFont("Body");
    sheet.getCell(2, 5).text("Background 2").themeFont("Body");
    sheet.getCell(2, 6).text("Text 2").themeFont("Body");
    sheet.getCell(2, 7).text("Accent 1").themeFont("Body");
    sheet.getCell(2, 8).text("Accent 2").themeFont("Body");
    sheet.getCell(2, 9).text("Accent 3").themeFont("Body");
    sheet.getCell(2, 10).text("Accent 4").themeFont("Body");
    sheet.getCell(2, 11).text("Accent 5").themeFont("Body");
    sheet.getCell(2, 12).text("Accent 6").themeFont("Body");

    sheet.getCell(4, 1).value("100").themeFont("Body");

    sheet.getCell(4, 3).backColor("Background 1");
    sheet.getCell(4, 4).backColor("Text 1");
    sheet.getCell(4, 5).backColor("Background 2");
    sheet.getCell(4, 6).backColor("Text 2");
    sheet.getCell(4, 7).backColor("Accent 1");
    sheet.getCell(4, 8).backColor("Accent 2");
    sheet.getCell(4, 9).backColor("Accent 3");
    sheet.getCell(4, 10).backColor("Accent 4");
    sheet.getCell(4, 11).backColor("Accent 5");
    sheet.getCell(4, 12).backColor("Accent 6");

    sheet.getCell(5, 1).value("80").themeFont("Body");

    sheet.getCell(5, 3).backColor("Background 1 80");
    sheet.getCell(5, 4).backColor("Text 1 80");
    sheet.getCell(5, 5).backColor("Background 2 80");
    sheet.getCell(5, 6).backColor("Text 2 80");
    sheet.getCell(5, 7).backColor("Accent 1 80");
    sheet.getCell(5, 8).backColor("Accent 2 80");
    sheet.getCell(5, 9).backColor("Accent 3 80");
    sheet.getCell(5, 10).backColor("Accent 4 80");
    sheet.getCell(5, 11).backColor("Accent 5 80");
    sheet.getCell(5, 12).backColor("Accent 6 80");

    sheet.getCell(6, 1).value("60").themeFont("Body");

    sheet.getCell(6, 3).backColor("Background 1 60");
    sheet.getCell(6, 4).backColor("Text 1 60");
    sheet.getCell(6, 5).backColor("Background 2 60");
    sheet.getCell(6, 6).backColor("Text 2 60");
    sheet.getCell(6, 7).backColor("Accent 1 60");
    sheet.getCell(6, 8).backColor("Accent 2 60");
    sheet.getCell(6, 9).backColor("Accent 3 60");
    sheet.getCell(6, 10).backColor("Accent 4 60");
    sheet.getCell(6, 11).backColor("Accent 5 60");
    sheet.getCell(6, 12).backColor("Accent 6 60");

    sheet.getCell(7, 1).value("40").themeFont("Body");

    sheet.getCell(7, 3).backColor("Background 1 40");
    sheet.getCell(7, 4).backColor("Text 1 40");
    sheet.getCell(7, 5).backColor("Background 2 40");
    sheet.getCell(7, 6).backColor("Text 2 40");
    sheet.getCell(7, 7).backColor("Accent 1 40");
    sheet.getCell(7, 8).backColor("Accent 2 40");
    sheet.getCell(7, 9).backColor("Accent 3 40");
    sheet.getCell(7, 10).backColor("Accent 4 40");
    sheet.getCell(7, 11).backColor("Accent 5 40");
    sheet.getCell(7, 12).backColor("Accent 6 40");

    sheet.getCell(8, 1).value("-25").themeFont("Body");

    sheet.getCell(8, 3).backColor("Background 1 -25");
    sheet.getCell(8, 4).backColor("Text 1 -25");
    sheet.getCell(8, 5).backColor("Background 2 -25");
    sheet.getCell(8, 6).backColor("Text 2 -25");
    sheet.getCell(8, 7).backColor("Accent 1 -25");
    sheet.getCell(8, 8).backColor("Accent 2 -25");
    sheet.getCell(8, 9).backColor("Accent 3 -25");
    sheet.getCell(8, 10).backColor("Accent 4 -25");
    sheet.getCell(8, 11).backColor("Accent 5 -25");
    sheet.getCell(8, 12).backColor("Accent 6 -25");

    sheet.getCell(9, 1).value("-50").themeFont("Body");

    sheet.getCell(9, 3).backColor("Background 1 -50");
    sheet.getCell(9, 4).backColor("Text 1 -50");
    sheet.getCell(9, 5).backColor("Background 2 -50");
    sheet.getCell(9, 6).backColor("Text 2 -50");
    sheet.getCell(9, 7).backColor("Accent 1 -50");
    sheet.getCell(9, 8).backColor("Accent 2 -50");
    sheet.getCell(9, 9).backColor("Accent 3 -50");
    sheet.getCell(9, 10).backColor("Accent 4 -50");
    sheet.getCell(9, 11).backColor("Accent 5 -50");
    sheet.getCell(9, 12).backColor("Accent 6 -50");
    sheet.resumePaint();
}

function getColorName(sheet, row, col) {
    var colName = sheet.getCell(2, col).text();
    var rowName = sheet.getCell(row, 1).text();
    return colName + " " + rowName;
}

function getThemeColor() {
    var sheet = spread.getActiveSheet();
    setThemeColorToSheet(sheet);                                            // Set current theme color to sheet

    var $colorUl = $("#default-theme-color");
    var $themeColorLi, cellBackColor;
    for (var col = 3; col < 13; col++) {
        var row = 4;
        cellBackColor = sheet.getActualStyle(row, col).backColor;
        $themeColorLi = $("<li class=\"color-cell seed-color-column\"></li>");
        $themeColorLi.css("background-color", cellBackColor).attr("data-name", sheet.getCell(2, col).text()).appendTo($colorUl);
        for (row = 5; row < 10; row++) {
            cellBackColor = sheet.getActualStyle(row, col).backColor;
            $themeColorLi = $("<li class=\"color-cell\"></li>");
            $themeColorLi.css("background-color", cellBackColor).attr("data-name", getColorName(sheet, row, col)).appendTo($colorUl);
        }
    }

    sheet.clear(2, 1, 8, 12, spreadNS.SheetArea.viewport, 255);      // Clear sheet theme color
}
// theme color related items (end)

// slicer related items
function processAddSlicer() {
    addTableColumns();                          // get table header data from table, and add them to slicer dialog

    var SLICER_DIALOG_WIDTH = 230;              // slicer dialog width
    showModal(uiResource.slicerDialog.insertSlicer, SLICER_DIALOG_WIDTH, $("#insertslicerdialog").children(), addSlicerEvent);
}

function addTableColumns() {
    var table = _activeTable;
    if (!table) {
        return;
    }
    var $slicerContainer = $("#slicer-container");
    $slicerContainer.empty();
    for (var col = 0; col < table.range().colCount; col++) {
        var columnName = table.getColumnName(col);
        var $slicerDiv = $(
            "<div>"
            + "<div class='insp-row'>"
            + "<div>"
            + "<div class='insp-checkbox insp-inline-row'>"
            + "<div class='button insp-inline-row-item'></div>"
            + "<div class='text insp-inline-row-item localize'>" + columnName + "</div>"
            + "</div>"
            + "</div>"
            + "</div>"
            + "</div>");
        $slicerDiv.appendTo($slicerContainer);
    }
    $("#slicer-container .insp-checkbox").click(checkedChanged);
}

function getSlicerName(sheet, columnName) {
    var autoID = 1;
    var newName = columnName;
    while (sheet.slicers.get(newName)) {
        newName = columnName + '_' + autoID;
        autoID++;
    }
    return newName;
}

function addSlicerEvent() {
    var table = _activeTable;
    if (!table) {
        return;
    }
    var checkedColumnIndexArray = [];
    $("#slicer-container div.button").each(function (index) {
        if ($(this).hasClass("checked")) {
            checkedColumnIndexArray.push(index);
        }
    });
    var sheet = spread.getActiveSheet();
    var posX = 100, posY = 200;
    spread.suspendPaint();
    for (var i = 0; i < checkedColumnIndexArray.length; i++) {
        var columnName = table.getColumnName(checkedColumnIndexArray[i]);
        var slicerName = getSlicerName(sheet, columnName);
        var slicer = sheet.slicers.add(slicerName, table.name(), columnName);
        slicer.position(new spreadNS.Point(posX, posY));
        posX = posX + 30;
        posY = posY + 30;
    }
    spread.resumePaint();
    slicer.isSelected(true);
    initSlicerTab();
}

function bindSlicerEvents(sheet, slicer, propertyName) {
    if (!slicer) {
        return;
    }
    if (propertyName === "isSelected") {
        if (slicer.isSelected()) {
            if (sheet.options.protectionOptions.allowEditObjects || !(sheet.options.isProtected && slicer.isLocked())) {
                setActiveTab("slicer");
                initSlicerTab();
            }
        }
        else {
            // setActiveTab("cell");

            // The events' execution sequence is different between V10 and V9.
            // In V9, EnterCell event will execute after SlicerChanged event. But in V10, SlicerChanged event will execute after EnterCell event.
            // So, when I move focus from table slicer to table cell, table tab will not be active.
            // In this situation, code above should be removed to make table be active.
        }
    }
    else {
        changeSlicerInfo(slicer, propertyName);
    }
}

function initSlicerTab() {
    var sheet = spread.getActiveSheet();
    var selectedSlicers = getSelectedSlicers(sheet);
    if (!selectedSlicers || selectedSlicers.length === 0) {
        return;
    }
    if (selectedSlicers.length > 1) {
        getMultiSlicerSetting(selectedSlicers);
        setTextDisabled("slicerName", true);
    }
    else if (selectedSlicers.length === 1) {
        getSingleSlicerSetting(selectedSlicers[0]);
        setTextDisabled("slicerName", false);
    }
}

function getSingleSlicerSetting(slicer) {
    if (!slicer) {
        return;
    }
    setTextValue("slicerName", slicer.name());
    setTextValue("slicerCaptionName", slicer.captionName());
    setDropDownValue("slicerItemSorting", slicer.sortState());
    setCheckValue("displaySlicerHeader", slicer.showHeader());
    setNumberValue("slicerColumnNumber", slicer.columnCount());
    setNumberValue("slicerButtonWidth", getSlicerItemWidth(slicer.columnCount(), slicer.width()));
    setNumberValue("slicerButtonHeight", slicer.itemHeight());
    if (slicer.dynamicMove()) {
        if (slicer.dynamicSize()) {
            setRadioItemChecked("slicerMoveAndSize", "slicer-move-size");
        }
        else {
            setRadioItemChecked("slicerMoveAndSize", "slicer-move-nosize");
        }
    }
    else {
        setRadioItemChecked("slicerMoveAndSize", "slicer-nomove-size");
    }
    setCheckValue("lockSlicer", slicer.isLocked());
    selectedCurrentSlicerStyle(slicer);
}

function getMultiSlicerSetting(selectedSlicers) {
    if (!selectedSlicers || selectedSlicers.length === 0) {
        return;
    }
    var slicer = selectedSlicers[0];
    var isDisplayHeader = false,
        isSameSortState = true,
        isSameCaptionName = true,
        isSameColumnCount = true,
        isSameItemHeight = true,
        isSameItemWidth = true,
        isSameLocked = true,
        isSameDynamicMove = true,
        isSameDynamicSize = true;

    var sortState = slicer.sortState(),
        captionName = slicer.captionName(),
        columnCount = slicer.columnCount(),
        itemHeight = slicer.itemHeight(),
        itemWidth = getSlicerItemWidth(columnCount, slicer.width()),
        dynamicMove = slicer.dynamicMove(),
        dynamicSize = slicer.dynamicSize();

    for (var item in selectedSlicers) {
        var slicer = selectedSlicers[item];
        isDisplayHeader = isDisplayHeader || slicer.showHeader();
        isSameLocked = isSameLocked && slicer.isLocked();
        if (slicer.sortState() !== sortState) {
            isSameSortState = false;
        }
        if (slicer.captionName() !== captionName) {
            isSameCaptionName = false;
        }
        if (slicer.columnCount() !== columnCount) {
            isSameColumnCount = false;
        }
        if (slicer.itemHeight() !== itemHeight) {
            isSameItemHeight = false;
        }
        if (getSlicerItemWidth(slicer.columnCount(), slicer.width()) !== itemWidth) {
            isSameItemWidth = false;
        }
        if (slicer.dynamicMove() !== dynamicMove) {
            isSameDynamicMove = false;
        }
        if (slicer.dynamicSize() !== dynamicSize) {
            isSameDynamicSize = false;
        }
        selectedCurrentSlicerStyle(slicer);
    }
    setTextValue("slicerName", "");
    if (isSameCaptionName) {
        setTextValue("slicerCaptionName", captionName);
    }
    else {
        setTextValue("slicerCaptionName", "");
    }
    if (isSameSortState) {
        setDropDownValue("slicerItemSorting", sortState);
    }
    else {
        setDropDownValue("slicerItemSorting", "");
    }
    setCheckValue("displaySlicerHeader", isDisplayHeader);
    if (isSameDynamicMove && isSameDynamicSize && dynamicMove) {
        if (dynamicSize) {
            setRadioItemChecked("slicerMoveAndSize", "slicer-move-size");
        }
        else {
            setRadioItemChecked("slicerMoveAndSize", "slicer-move-nosize");
        }
    }
    else {
        setRadioItemChecked("slicerMoveAndSize", "slicer-nomove-size");
    }
    if (isSameColumnCount) {
        setNumberValue("slicerColumnNumber", columnCount);
    }
    else {
        setNumberValue("slicerColumnNumber", "");
    }
    if (isSameItemHeight) {
        setNumberValue("slicerButtonHeight", Math.round(itemHeight));
    }
    else {
        setNumberValue("slicerButtonHeight", "");
    }
    if (isSameItemWidth) {
        setNumberValue("slicerButtonWidth", itemWidth);
    }
    else {
        setNumberValue("slicerButtonWidth", "");
    }
    setCheckValue("lockSlicer", isSameLocked);
}

function changeSlicerInfo(slicer, propertyName) {
    if (!slicer) {
        return;
    }
    switch (propertyName) {
        case "width":
            setNumberValue("slicerButtonWidth", getSlicerItemWidth(slicer.columnCount(), slicer.width()));
            break;
    }
}

function setSlicerSetting(property, value) {
    var sheet = spread.getActiveSheet();
    var selectedSlicers = getSelectedSlicers(sheet);
    if (!selectedSlicers || selectedSlicers.length === 0) {
        return;
    }
    for (var item in selectedSlicers) {
        setSlicerProperty(selectedSlicers[item], property, value);
    }
}

function setSlicerProperty(slicer, property, value) {
    switch (property) {
        case "name":
            var sheet = spread.getActiveSheet();
            var slicerPreName = slicer.name();
            if (!value) {
                alert(getResource("messages.invalidSlicerName"));
                setTextValue("slicerName", slicerPreName);
            }
            else if (value && value !== slicerPreName) {
                if (sheet.floatingObjects.get(value)) {
                    alert(getResource("messages.duplicatedSlicerName"));
                    setTextValue("slicerName", slicerPreName);
                }
                else {
                    slicer.name(value);
                }
            }
            break;
        case "captionName":
            slicer.captionName(value);
            break;
        case "sortState":
            slicer.sortState(value);
            break;
        case "showHeader":
            slicer.showHeader(value);
            break;
        case "columnCount":
            slicer.columnCount(value);
            break;
        case "itemHeight":
            slicer.itemHeight(value);
            break;
        case "itemWidth":
            slicer.width(getSlicerWidthFromItem(slicer.columnCount(), value));
            break;
        case "moveSize":
            if (value === "slicer-move-size") {
                slicer.dynamicMove(true);
                slicer.dynamicSize(true);
            }
            if (value === "slicer-move-nosize") {
                slicer.dynamicMove(true);
                slicer.dynamicSize(false);
            }
            if (value === "slicer-nomove-size") {
                slicer.dynamicMove(false);
                slicer.dynamicSize(false);
            }
            break;
        case "lock":
            slicer.isLocked(value);
            break;
        case "style":
            slicer.style(value);
            break;
        default:
            console.log("Slicer doesn't have property:", property);
            break;
    }
}

function setTextDisabled(name, isDisabled) {
    var $item = $("div.insp-text[data-name='" + name + "']");
    var $input = $item.find("input");
    if (isDisabled) {
        $item.addClass("disabled");
        $input.attr("disabled", true);
    }
    else {
        $item.removeClass("disabled");
        $input.attr("disabled", false);
    }
}

function setRadioItemChecked(groupName, itemName) {
    var $radioGroup = $("div.insp-checkbox[data-name='" + groupName + "']");
    var $radioItems = $("div.radiobutton[data-name='" + itemName + "']");

    $radioGroup.find(".radiobutton").removeClass("checked");
    $radioItems.addClass("checked");
}

function getSlicerItemWidth(count, slicerWidth) {
    if (count <= 0) {
        count = 1; //Column count will be converted to 1 if it is set to 0 or negative number.
    }
    var SLICER_PADDING = 6;
    var SLICER_ITEM_SPACE = 2;
    var itemWidth = Math.round((slicerWidth - SLICER_PADDING * 2 - (count - 1) * SLICER_ITEM_SPACE) / count);
    if (itemWidth < 0) {
        return 0;
    }
    else {
        return itemWidth;
    }
}

function getSlicerWidthFromItem(count, itemWidth) {
    if (count <= 0) {
        count = 1; //Column count will be converted to 1 if it is set to 0 or negative number.
    }
    var SLICER_PADDING = 6;
    var SLICER_ITEM_SPACE = 2;
    return Math.round(itemWidth * count + (count - 1) * SLICER_ITEM_SPACE + SLICER_PADDING * 2);
}

function getSelectedSlicers(sheet) {
    if (!sheet) {
        return null;
    }
    var slicers = sheet.slicers.all();
    if (!slicers || slicers.length === 0) {
        return null;
    }
    var selectedSlicers = [];
    for (var item in slicers) {
        if (slicers[item].isSelected()) {
            selectedSlicers.push(slicers[item]);
        }
    }
    return selectedSlicers;
}

function processSlicerItemSorting(sortValue) {
    switch (sortValue) {
        case 0:
        case 1:
        case 2:
            setSlicerSetting("sortState", sortValue);
            break;

        default:
            console.log("processSlicerItemSorting not process with ", name);
            return;
    }
}

function selectedCurrentSlicerStyle(slicer) {
    var slicerStyle = slicer.style(),
        styleName = slicerStyle && slicerStyle.name();
    $("#slicerStyles .slicer-format-item").removeClass("slicer-format-item-selected");
    styleName = styleName.split("SlicerStyle")[1];
    if (styleName) {
        $("#slicerStyles .slicer-format-item div[data-name='" + styleName.toLowerCase() + "']").parent().addClass("slicer-format-item-selected");
    }
}

function changeSlicerStyle() {
    spread.suspendPaint();

    var styleName = $(">div", this).data("name");
    setSlicerSetting("style", spreadNS.Slicers.SlicerStyles[styleName]());
    $("#slicerStyles .slicer-format-item").removeClass("slicer-format-item-selected");
    $(this).addClass("slicer-format-item-selected");

    spread.resumePaint();
}
// slicer related items (end)

// spread theme related items
function processChangeSpreadTheme(value) {
    $("link[title='spread-theme']").attr("href", value);

    setTimeout(
        function () {
            spread.refresh();
        }, 300);
}
// spread theme related items (end)

//cell label related item
function setLabelOptions(sheet, value, option) {
    var selections = sheet.getSelections(),
        rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();
    sheet.suspendPaint();
    for (var n = 0; n < selections.length; n++) {
        var sel = getActualCellRange(sheet, selections[n], rowCount, columnCount);
        for (var r = sel.row; r < sel.row + sel.rowCount; r++) {
            for (var c = sel.col; c < sel.col + sel.colCount; c++) {
                var style = sheet.getStyle(r, c);
                if (!style) {
                    style = new spreadNS.Style();
                }
                if (!style.labelOptions) {
                    style.labelOptions = {};
                }
                if (option === "foreColor") {
                    style.labelOptions.foreColor = value;
                } else if (option === "margin") {
                    style.labelOptions.margin = value;
                } else if (option === "visibility") {
                    style.labelOptions.visibility = GC.Spread.Sheets.LabelVisibility[value];
                } else if (option === "alignment") {
                    style.labelOptions.alignment = GC.Spread.Sheets.LabelAlignment[value];
                }
                sheet.setStyle(r, c, style);
            }
        }
    }
    sheet.resumePaint();
}

function setWatermark(sheet, value) {
    var selections = sheet.getSelections(),
        rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();
    sheet.suspendPaint();
    for (var n = 0; n < selections.length; n++) {
        var sel = getActualCellRange(sheet, selections[n], rowCount, columnCount);
        for (var r = sel.row; r < sel.row + sel.rowCount; r++) {
            for (var c = sel.col; c < sel.col + sel.colCount; c++) {
                var style = sheet.getStyle(r, c);
                if (!style) {
                    style = new spreadNS.Style();
                }
                style.watermark = value;
                sheet.setStyle(r, c, style);
            }
        }
    }
    sheet.resumePaint();
}

function setCellPadding(sheet, value) {
    var selections = sheet.getSelections(),
        rowCount = sheet.getRowCount(),
        columnCount = sheet.getColumnCount();
    sheet.suspendPaint();
    for (var n = 0; n < selections.length; n++) {
        var sel = getActualCellRange(sheet, selections[n], rowCount, columnCount);
        for (var r = sel.row; r < sel.row + sel.rowCount; r++) {
            for (var c = sel.col; c < sel.col + sel.colCount; c++) {
                var style = sheet.getStyle(r, c);
                if (!style) {
                    style = new spreadNS.Style();
                }
                style.cellPadding = value;
                sheet.setStyle(r, c, style);
            }
        }
    }
    sheet.resumePaint();
}

//cell label related item (end)