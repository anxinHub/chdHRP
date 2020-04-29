var GC;
(function (GC) {
    (function (Spread) {
        (function (Sheets) {
            (function (designer) {
                (function (contextMenu) {
                    'use strict';

                    function initContextMenu() {
                        var _this = this;
                        var isCol = false;
                        var selectItem = function (evt, ui) {
                            var spread = designer.wrapper.spread;
                            var activeSheet = spread.getActiveSheet();
                            switch (ui.item[0].id) {
                                case "cut":
                                    designer.actions.doAction("cut", spread);
                                    break;
                                case "copy":
                                    designer.actions.doAction("copy", spread);
                                    break;
                                case "pasteAll":
                                    designer.actions.doAction("paste", spread, 0 /* All */);
                                    break;
                                case "pasteFormulas":
                                    designer.actions.doAction("paste", spread, 3 /* Formulas */);
                                    break;
                                case "pasteValues":
                                    designer.actions.doAction("paste", spread, 1 /* Values */);
                                    break;
                                case "pasteFormatting":
                                    designer.actions.doAction("paste", spread, 2 /* Formatting */);
                                    break;
                                case "insertDialog":
                                    var selectionType = designer.spreadActions.getSelectionType(spread);
                                    switch (selectionType) {
                                        case 0 /* Sheet */
                                        :
                                            designer.MessageBox.show(designer.res.insertCellInSheet, designer.res.title, 2 /* warning */);
                                            break;
                                        case 2 /* OnlyColumn */
                                        :
                                            designer.spreadActions.insertColumns(spread);
                                            break;
                                        case 1 /* OnlyRow */
                                        :
                                            designer.spreadActions.insertRows(spread);
                                            break;
                                        case 4 /* Mixture */
                                        :
                                            designer.MessageBox.show(designer.res.insertCellInMixtureRange, designer.res.title, 2 /* warning */);
                                            break;
                                        case 3 /* OnlyCells */
                                        :
                                            if (insertCellsDialog === undefined) {
                                                insertCellsDialog = new designer.InsertCellsDialog();
                                            }
                                            insertCellsDialog.open();
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case "deleteDialog":
                                    var selectionType = designer.spreadActions.getSelectionType(spread);
                                    switch (selectionType) {
                                        case 0 /* Sheet */
                                        :
                                            _this._sheet.clear(0, 0, _this._sheet.getRowCount(), _this._sheet.getColumnCount(), 3 /* viewport */, 2 /* Style */ | 1 /* Data */ | 16 /* Sparkline */ | 32 /* Axis */);
                                            break;
                                        case 2 /* OnlyColumn */
                                        :
                                            designer.spreadActions.deleteColumns(spread);
                                            break;
                                        case 1 /* OnlyRow */
                                        :
                                            designer.spreadActions.deleteRows(spread);
                                            break;
                                        case 4 /* Mixture */
                                        :
                                            designer.MessageBox.show(designer.res.insertCellInMixtureRange, designer.res.title, 2 /* warning */);
                                            break;
                                        case 3 /* OnlyCells */
                                        :
                                            if (deleteCellsDialog === undefined) {
                                                deleteCellsDialog = new designer.DeleteCellsDialog();
                                            }
                                            deleteCellsDialog.open();
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case "clearcontents":
                                    designer.actions.doAction("clearContent", spread);
                                    break;
                                case "filter":
                                    designer.actions.doAction("setRowFilter", spread, false);
                                    break;
                                case "sortAToZ":
                                    designer.actions.doAction("sortRange", spread, true);
                                    break;
                                case "sortZToA":
                                    designer.actions.doAction("sortRange", spread, false);
                                    break;
                                case "customSort":
                                    if (!sortDialog) {
                                        sortDialog = new designer.SortDialog();
                                    }
                                    sortDialog.open();
                                    break;
                                case "insertComment":
                                    designer.actions.doAction("insertComment", spread);
                                    var comment = activeSheet.comments.get(activeSheet.getActiveRowIndex(), activeSheet.getActiveColumnIndex());
                                    if (comment) {
                                        comment.commentState(2 /* Edit */);
                                    }
                                    break;
                                case "editComment":
                                    var comment = activeSheet.comments.get(activeSheet.getActiveRowIndex(), activeSheet.getActiveColumnIndex());
                                    if (comment) {
                                        comment.commentState(2 /* Edit */);
                                    }
                                    break;
                                case "deleteComment":
                                    designer.actions.doAction("deleteComment", spread);
                                    break;
                                case "hideComment":
                                    var comment = activeSheet.comments.get(activeSheet.getActiveRowIndex(), activeSheet.getActiveColumnIndex());
                                    if (comment) {
                                        comment.displayMode(2 /* HoverShown */);
                                    }
                                    break;
                                case "unHideComment":
                                    var comment = activeSheet.comments.get(activeSheet.getActiveRowIndex(), activeSheet.getActiveColumnIndex());
                                    if (comment) {
                                        comment.displayMode(1 /* AlwaysShown */);
                                    }
                                    break;
                                case "formatComment":
                                    if (fcDialog === undefined) {
                                        fcDialog = new designer.FormatCommentDialog();
                                    }
                                    var row = activeSheet.getActiveRowIndex();
                                    var col = activeSheet.getActiveColumnIndex();
                                    var comment = activeSheet.comments.get(row, col);
                                    var option = {row: row, col: col};
                                    if (comment) {
                                        fcDialog.open(option);
                                    }
                                    break;
                                case "formatCells":
                                    if (formatCellDialog === undefined) {
                                        formatCellDialog = new designer.FormatDialog();
                                    }
                                    formatCellDialog.open('number');
                                    break;
                                case "defineName":
                                    if (newNameDialog === undefined) {
                                        newNameDialog = new designer.NewNameDialog();
                                    }
                                    newNameDialog.open("new", undefined);
                                    break;
                                case "rowHeight":
                                    if (setRowHeightDialog === undefined) {
                                        setRowHeightDialog = new designer.RowHeightDialog();
                                    }
                                    setRowHeightDialog.open();
                                    break;
                                case "columnWidth":
                                    if (setColumnWidthDialog === undefined) {
                                        setColumnWidthDialog = new designer.ColumnWidthDialog();
                                    }
                                    setColumnWidthDialog.open();
                                    break;
                                case "hide":
                                    var selectionType = designer.spreadActions.getSelectionType(spread);
                                    switch (selectionType) {
                                        case 2 /* OnlyColumn */
                                        :
                                            designer.actions.doAction("hideColumns", spread);
                                            break;
                                        case 1 /* OnlyRow */
                                        :
                                            designer.actions.doAction("hideRows", spread);
                                            break;
                                        case 0 /* Sheet */
                                        :
                                            if (isCol) {
                                                designer.actions.doAction("hideColumns", spread);
                                            } else {
                                                designer.actions.doAction("hideRows", spread);
                                            }
                                            break;
                                    }
                                    break;
                                case "unhide":
                                    var selectionType = designer.spreadActions.getSelectionType(spread);
                                    switch (selectionType) {
                                        case 2 /* OnlyColumn */
                                        :
                                            designer.actions.doAction("unHideColumns", spread);
                                            break;
                                        case 1 /* OnlyRow */
                                        :
                                            designer.actions.doAction("unHideRows", spread);
                                            break;
                                        case 0 /* Sheet */
                                        :
                                            if (isCol) {
                                                designer.actions.doAction("unHideColumns", spread);
                                            } else {
                                                designer.actions.doAction("unHideRows", spread);
                                            }
                                            break;
                                    }
                                    break;
                                case "headers":
                                    if (headerCellsDialog === undefined) {
                                        headerCellsDialog = new designer.HeaderCellsDialog();
                                    }
                                    var selectionType = designer.spreadActions.getSelectionType(spread);
                                    switch (selectionType) {
                                        case 2 /* OnlyColumn */
                                        :
                                            headerCellsDialog.open("col");
                                            break;
                                        case 1 /* OnlyRow */
                                        :
                                            headerCellsDialog.open("row");
                                            break;
                                    }
                                    break;

                                case "insert":
                                    designer.actions.doAction("insertSheet", spread);
                                    break;
                                case "delete":
                                    designer.actions.doAction("deleteSheet", spread);
                                    break;
                                case "protectsheet":
                                    var isProtected = spread.getActiveSheet().options.isProtected;
                                    if (!isProtected) {
                                        var protectionOptionDialog = new designer.ProtectionOptionDialog();
                                        protectionOptionDialog.open();
                                    } else {
                                        designer.actions.doAction("protectSheet", spread, false);
                                    }
                                    break;
                                case "hideSheet":
                                    designer.actions.doAction("hideSheet", spread);
                                    break;
                                case "unhideSheet":
                                    designer.actions.doAction("unhideSheet", spread);
                                    break;

                                case "slicerCut":
                                    designer.actions.doAction("floatingObjectCut", spread);
                                    break;
                                case "slicerCopy":
                                    designer.actions.doAction("floatingObjectCopy", spread);
                                    break;
                                case "slicerPasteAll":
                                    designer.actions.doAction("paste", spread, 0 /* All */);
                                    break;
                                case "slicerSortAToZ":
                                    designer.actions.doAction("sortSlicerItems", spread, 1 /* Ascending */);
                                    break;
                                case "slicerSortZToA":
                                    designer.actions.doAction("sortSlicerItems", spread, 2 /* Descending */);
                                    break;
                                case "removeSlicer":
                                    designer.actions.doAction("deleteSlicer", spread);
                                    break;
                                case "slicerProperty":
                                    if (!formatSlicerDialog) {
                                        formatSlicerDialog = new designer.FormatSlicerDialog();
                                    }
                                    if (designer.util.isSlicerSelected(activeSheet)) {
                                        formatSlicerDialog.open();
                                    }
                                    break;
                                case "slicerSetting":
                                    if (!designer.ribbon.slicerSettingDialog) {
                                        designer.ribbon.slicerSettingDialog = new designer.SlicerSettingDialog();
                                    }
                                    if (designer.util.isSlicerSelected(activeSheet)) {
                                        designer.ribbon.slicerSettingDialog.open();
                                    }
                                    break;
                            }
                        };

                        $("#sheetcolor-picker").colorpicker({
                            valueChanged: function (e, value) {
                                if (value.name) {
                                    designer.actions.doAction("setSheetTabColor", designer.wrapper.spread, value.name);
                                } else {
                                    designer.actions.doAction("setSheetTabColor", designer.wrapper.spread, value.color);
                                }
                            }
                        });
                        $("#sheetcolor-picker").colorpicker("option", "themeColors", designer.wrapper.getThemeColors());

                        $("#sheet-menu").menu({
                            select: selectItem
                        });
                        $("#tabstrip-menu").menu({
                            select: selectItem
                        });
                        $("#slicer-menu").menu({
                            select: selectItem
                        });

                        $(document).bind("mouseup", function (evt) {
                            if (evt.button !== 2) {
                                var $link = $(evt.target).closest("a");
                                if (!$(".context-menu").hasClass("hidden")) {
                                    // do not hide context-menu when click on items with sub contents / disabled items
                                    if ($link && ($link.attr("aria-haspopup") || $link.hasClass("link-disable"))) {
                                        return false;
                                    }
                                    $(".context-menu").addClass("hidden");
                                }
                            }
                        });

                        designer.wrapper.spreadElement.bind("mouseup", function (evt) {
                            if (evt.button !== 2) {
                                if (!$(".context-menu").hasClass("hidden")) {
                                    $(".context-menu").addClass("hidden");
                                }
                            }
                        });

                        $(document).bind("contextmenu", function (evt) {
                            designer.util.cancelDefault(evt);
                        });

                        designer.wrapper.spreadElement.bind("contextmenu", function (evt) {
                            var activeSheet = designer.wrapper.spread.getActiveSheet();
                            var isShowContextMenu = false;
                            var t;
                            var left;
                            var top;
                            setSelectedSlicersFalse();
                            var canvas = designer.util.getCanvas(designer.wrapper.spread);
                            t = $(canvas).offset();
                            left = evt.pageX - t.left;
                            top = evt.pageY - t.top;
                            var bound = canvas.getBoundingClientRect();
                            if (left > bound.width || top > bound.height) {
                                return;
                            }
                            var hitTestInfo = activeSheet.hitTest(left, top);
                            if (hitTestInfo.x >= 0 && hitTestInfo.y >= 0 && hitTestInfo.row !== void 0 && hitTestInfo.col !== void 0) {
                                //Inside spread
                                if (hitTestInfo.commentHitInfo) {
                                    if (!$(".context-menu").hasClass("hidden")) {
                                        $(".context-menu").addClass("hidden");
                                    }

                                    var comment = hitTestInfo.commentHitInfo.comment;
                                    var commentTextArea = $("div.gc-spread-floatBlockCanvas-container textArea");
                                    if (commentTextArea.length) {
                                        var canvasContainer = commentTextArea.parents("div.gc-spread-floatBlockCanvas-container")[0];
                                        if (canvasContainer) {
                                            addCommentEvents(canvasContainer);
                                        }
                                    }
                                } else {
                                    // deactive comment if present
                                    activeSheet.comments.all().forEach(function (comment) {
                                        comment.commentState(3 /* Normal */);
                                    });

                                    var col = hitTestInfo.col, row = hitTestInfo.row;

                                    switch (hitTestInfo.hitTestType) {
                                        case 1 /* colHeader */
                                        :
                                            if (designer.util.findSelection(activeSheet, row, col) === null) {
                                                activeSheet.setSelection(-1, col, activeSheet.getRowCount(), 1);
                                            }

                                            if (row !== undefined && col !== undefined) {
                                                isCol = true;
                                                sheetMenuType("col");
                                                isShowContextMenu = true;
                                            }
                                            break;
                                        case 2 /* rowHeader */
                                        :
                                            if (designer.util.findSelection(activeSheet, row, col) === null) {
                                                activeSheet.setSelection(row, -1, 1, activeSheet.getColumnCount());
                                            }

                                            if (row !== undefined && col !== undefined) {
                                                isCol = false;
                                                sheetMenuType("row");
                                                isShowContextMenu = true;
                                            }
                                            break;
                                        case 0 /*corner*/
                                        :
                                        case 3 /* viewport */
                                        :
                                            if (designer.util.findSelection(activeSheet, row, col) === null) {
                                                // not support action on floating object such as picture, not show context menu
                                                if (hitTestInfo.floatingObjectHitInfo) {
                                                    $(".context-menu").addClass("hidden");
                                                    return false;
                                                }

                                                activeSheet.clearSelection();
                                                activeSheet.endEdit();
                                                activeSheet.setActiveCell(row, col);
                                                designer.util.triggerSheetEvent(activeSheet, Sheets.Events.EnterCell, {
                                                    sheet: activeSheet,
                                                    sheetName: activeSheet.name(),
                                                    row: hitTestInfo.row,
                                                    col: hitTestInfo.col
                                                });
                                            }
                                            if (row !== undefined && col !== undefined && !activeSheet.isEditing()) {
                                                sheetMenuType("cell");
                                                commentMenu(activeSheet);
                                                isShowContextMenu = true;
                                            }
                                            break;
                                        default:
                                            if (designer.util.findSelection(activeSheet, row, col) === null) {
                                                activeSheet.setSelection(-1, -1, activeSheet.getRowCount(), activeSheet.getColumnCount());
                                            }
                                            if (row !== undefined && col !== undefined) {
                                                sheetMenuType("cell");
                                                isShowContextMenu = true;
                                            }
                                            break;
                                    }
                                }

                                if (isShowContextMenu) {
                                    $(".context-menu").removeClass("hidden");
                                    $("#sheet-menu").removeClass("hidden");
                                    $("#tabstrip-menu").addClass("hidden");
                                    $("#slicer-menu").addClass("hidden");
                                    $("#comment-menu").addClass("hidden");
                                    var devicePixelRatio = window.devicePixelRatio || window.screen.deviceXDPI / window.screen.logicalXDPI;
                                    //Norris fix a bug that the position of the contextMenu is incorrect when the designer is in hight DPI.
                                    //When designer in high DPI,the "devicePixelRatio" is not 1, the values of some css properties such as "top" and "left" in QtWebkit are
                                    //affected by the "devicePixelRatio",and this also will affect some properties in jquery.So,when the "devicePixelRatio"is not 1,we change
                                    //the positon of contextMenu by the protogenic css style,but no use the jquery.
                                    var $sheetMenu = $("#sheet-menu");
                                    if (devicePixelRatio === 1) {
                                        $sheetMenu.position({
                                            my: "left top",
                                            of: evt
                                        });
                                    } else {
                                        $sheetMenu.css("position", "absolute");
                                        if (evt) {
                                            var smWidth = $sheetMenu.width(), smHeight = $sheetMenu.height();
                                            var smLeft = evt.pageX, smTop = evt.pageY;
                                            if (evt.pageX + smWidth > document.body.clientWidth && evt.pageX - smWidth > 0) {
                                                smLeft = evt.pageX - smWidth;
                                            }
                                            if (evt.pageY + smHeight > document.body.clientHeight && evt.pageY - smHeight > 0) {
                                                smTop = evt.pageY - smHeight;
                                            }
                                            $sheetMenu[0].style.left = smLeft + "px";
                                            $sheetMenu[0].style.top = smTop + "px";
                                        }
                                    }
                                    hideShowContextmenuPasteItem();
                                }
                            }
                        });

                        //add code to deal with the CSS of paste option
                        adjustPasteItemStyle();
                    }

                    function getIsProected(sheet) {
                        return sheet.options.isProtected;
                    }

                    function addTabStripEvents() {
                        var tabStripCanvas = designer.util.getTabStripCanvas();
                        $(tabStripCanvas).bind("contextmenu", function (evt) {
                            if (evt.button === 2) {
                                var t;
                                var left;
                                var top;
                                var wrapperSpread = designer.wrapper.spread;
                                t = $(tabStripCanvas).offset();
                                left = evt.pageX - t.left;
                                top = evt.pageY - t.top;
                                var tabstrip_hitTestInfo = wrapperSpread.getTab().hitTest(left, top), tabstrip_hitTestInfo_index = tabstrip_hitTestInfo.index;
                                if (tabstrip_hitTestInfo_index !== undefined && tabstrip_hitTestInfo.element !== "navButton") {
                                    designer.wrapper.spread.getActiveSheet().endEdit(true);
                                    designer.wrapper.spread.setActiveSheetIndex(tabstrip_hitTestInfo_index);
                                    _initUnhideButton();
                                    _switchPropertectSheet();
                                    $(".context-menu").removeClass("hidden");
                                    $("#tabstrip-menu").removeClass("hidden");
                                    $("#sheet-menu").addClass("hidden");
                                    $("#comment-menu").addClass("hidden");
                                    $("#slicer-menu").addClass("hidden");
                                    $("#tabstrip-menu").position({
                                        my: "left top",
                                        of: evt
                                    });
                                }
                                designer.util.cancelDefault(evt);
                            }
                        });

                        $(tabStripCanvas).bind("mouseup", function (evt) {
                            if (evt.button !== 2) {
                                if (!$(".context-menu").hasClass("hidden")) {
                                    $(".context-menu").addClass("hidden");
                                }
                            }
                        });
                    }

                    contextMenu.addTabStripEvents = addTabStripEvents;

                    function addCommentEvents(container) {
                        var activeSheet = designer.wrapper.spread.getActiveSheet();
                        var commentHost = $(container).find(".gc-spread-host-container");
                        if (commentHost.length !== 0) {
                            commentHost.unbind(".comment");
                            commentHost.bind("click.comment", function (evt) {
                                if (evt.button === 2) {
                                    designer.util.cancelDefault(evt);
                                }
                            });
                        }
                    }

                    contextMenu.addCommentEvents = addCommentEvents;
                    var insertCellsDialog;
                    var deleteCellsDialog;
                    var sortDialog;
                    var formatCellDialog;
                    var fcDialog;
                    var setRowHeightDialog;
                    var setColumnWidthDialog;
                    var headerCellsDialog;
                    var newNameDialog;
                    var formatSlicerDialog;

                    var commentClipBoard = "";

                    function _initUnhideButton() {
                        var sheets = designer.wrapper.spread.sheets;
                        var unhideSheetLink = $("#unhideSheet").find("a");
                        var len = sheets.length;
                        if (len <= 0) {
                            return;
                        }
                        for (var i = 0; i < len; i++) {
                            if (!sheets[i].visible()) {
                                if (unhideSheetLink.hasClass("link-disable")) {
                                    unhideSheetLink.removeClass("link-disable");
                                }
                                return;
                            }
                        }
                        unhideSheetLink.addClass("link-disable");
                    }

                    function _switchPropertectSheet() {
                        var activeSheet = designer.wrapper.spread.getActiveSheet();
                        var protectTextControl = $("#protectsheet").children().children()[1];
                        if (getIsProected(activeSheet)) {
                            protectTextControl.textContent = designer.res.contextMenu.unprotectsheet;
                        } else {
                            protectTextControl.textContent = designer.res.contextMenu.protectsheet;
                        }
                    }

                    function sheetMenuType(type) {
                        var insertTextControl = $("#insertDialog").children().children()[1];
                        var deleteTextControl = $("#deleteDialog").children().children()[1];
                        var insertText = insertTextControl.textContent.split(".")[0];
                        var deleteText = deleteTextControl.textContent.split(".")[0];

                        switch (type) {
                            case "col":
                                insertTextControl.textContent = insertText;
                                deleteTextControl.textContent = deleteText;
                                $(".row-menu").addClass("hidden");
                                $(".cell-menu").addClass("hidden");
                                $(".column-menu").removeClass("hidden");
                                break;
                            case "row":
                                insertTextControl.textContent = insertText;
                                deleteTextControl.textContent = deleteText;
                                $(".cell-menu").addClass("hidden");
                                $(".column-menu").addClass("hidden");
                                $(".row-menu").removeClass("hidden");
                                break;
                            case "cell":
                                insertTextControl.textContent = insertText + "...";
                                deleteTextControl.textContent = deleteText + "...";
                                $(".column-menu").addClass("hidden");
                                $(".row-menu").addClass("hidden");
                                $(".cell-menu").removeClass("hidden");
                        }
                    }

                    function commentMenu(sheet) {
                        if (!sheet) {
                            return;
                        }
                        var row = sheet.getActiveRowIndex();
                        var col = sheet.getActiveColumnIndex();
                        var isProtected = getIsProected(sheet);
                        var isLocked = sheet.getCell(row, col).locked();

                        var editComment = sheet.comments.get(row, col);
                        if (editComment) {
                            $(".comment-create").addClass("hidden");
                            if (isProtected && isLocked) {
                                $("#editComment").addClass("hidden");
                                $("#deleteComment").addClass("hidden");
                                $("#formatComment").addClass("hidden");
                            } else {
                                $(".comment-edit").removeClass("hidden");
                            }
                            if (editComment.displayMode() === 2 /* HoverShown */) {
                                $("#hideComment").addClass("hidden");
                                $("#unHideComment").removeClass("hidden");
                            } else if (editComment.displayMode() === 1 /* AlwaysShown */) {
                                $("#hideComment").removeClass("hidden");
                                $("#unHideComment").addClass("hidden");
                            }
                        } else {
                            $(".comment-edit").addClass("hidden");
                            if (isProtected && isLocked) {
                                $(".comment-create").addClass("hidden");
                                $(".comment-separator").addClass("hidden");
                            } else {
                                $(".comment-create").removeClass("hidden");
                                if (showDeleteComment(sheet)) {
                                    $("#deleteComment").removeClass("hidden");
                                }
                                $(".comment-separator").removeClass("hidden");
                            }
                        }
                    }

                    function showDeleteComment(sheet) {
                        var selections = sheet.getSelections();
                        var len = selections.length;
                        var comments = sheet.comments.all();
                        var count = comments.length;
                        if (len <= 0 || count <= 0) {
                            return false;
                        }
                        for (var index = 0; index < len; index++) {
                            var selection = selections[index];
                            var row = selection.row;
                            var col = selection.col;
                            var endRow = selection.row + selection.rowCount;
                            var endCol = selection.col + selection.colCount;
                            for (var i = row; i < endRow; i++) {
                                for (var j = col; j < endCol; j++) {
                                    if (sheet.comments.get(i, j)) {
                                        return true;
                                    }
                                }
                            }
                        }
                        return false;
                    }

                    //#region slicer contextmenu
                    function setSelectedSlicersFalse() {
                        var sheet = designer.wrapper.spread.getActiveSheet();
                        var selectedSlicers = designer.util.getSelectedSlicers(sheet);
                        if (!selectedSlicers || selectedSlicers.length === 0) {
                            return;
                        } else {
                            for (var item in selectedSlicers) {
                                var slicer = selectedSlicers[item];
                                slicer.isSelected(false);
                            }
                        }
                    }

                    //#endregion
                    //#region paste option
                    function adjustPasteItemStyle() {
                        $(".paste-option-li").removeClass("ui-menu-item");
                        var slicerOption = $(".paste-option-li").find("ul:first");
                        $(slicerOption).removeAttr("style");
                        $(slicerOption).removeClass();
                        $(slicerOption).addClass("paste-option-menu");
                    }

                    function hideShowContextmenuPasteItem() {
                        var activeSheet = designer.wrapper.spread.getActiveSheet();
                        var sheet = activeSheet;
                        if (sheet.isPasteFloatingObject()) {
                            $("#pasteFormulas").hide();
                            $("#pasteValues").hide();
                            $("#pasteFormatting").hide();
                        } else {
                            $("#pasteFormulas").show();
                            $("#pasteValues").show();
                            $("#pasteFormatting").show();
                        }
                    }

                    //endregion
                    //#region Loader.Ready
                    designer.loader.ready(function () {
                        if (!designer.wrapper.spread.notWorking) {
                            initContextMenu();
                        }
                    });
                })(designer.contextMenu || (designer.contextMenu = {}));
                var contextMenu = designer.contextMenu;
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
