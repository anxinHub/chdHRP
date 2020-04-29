var GC;
(function (GC) {
    (function (Spread) {
        (function (Sheets) {
            (function (designer) {
                (function (wrapper) {
                    'use strict';
                    var element;

                    function getThemeColors() {
                        var themeColor = wrapper.spread.getActiveSheet().currentTheme().colors();
                        return [
                            {
                                name: 'Background 1',
                                baseColor: themeColor.getColor('Background 1')
                            },
                            {
                                name: 'Text 1',
                                baseColor: themeColor.getColor('Text 1')
                            },
                            {
                                name: 'Background 2',
                                baseColor: themeColor.getColor('Background 2')
                            },
                            {
                                name: 'Text 2',
                                baseColor: themeColor.getColor('Text 2')
                            },
                            {
                                name: 'Accent 1',
                                baseColor: themeColor.getColor('Accent 1')
                            },
                            {
                                name: 'Accent 2',
                                baseColor: themeColor.getColor('Accent 2')
                            },
                            {
                                name: 'Accent 3',
                                baseColor: themeColor.getColor('Accent 3')
                            },
                            {
                                name: 'Accent 4',
                                baseColor: themeColor.getColor('Accent 4')
                            },
                            {
                                name: 'Accent 5',
                                baseColor: themeColor.getColor('Accent 5')
                            },
                            {
                                name: 'Accent 6',
                                baseColor: themeColor.getColor('Accent 6')
                            }
                        ];
                    }

                    wrapper.getThemeColors = getThemeColors;

                    wrapper.shouldModifiedHandlerExecute = true;

                    function reset(init) {
                        designer.spreadActions.clearRegisteredCommands();

                        if (typeof init === "undefined") {
                            init = false;
                        }
                        if (!init) {
                            wrapper.spread.destroy();
                            element.empty();
                            wrapper.spread = null;
                        }
                        try {
                            wrapper.spread = new Sheets.Workbook(element[0]);
                            wrapper.spread.setSheetCount(2);
                            wrapper.spreadElement = element;
                            designer.formulaBar.initFormulaBar(init);
                            designer.contextMenu.addTabStripEvents();
                        } catch (ex) {
                            console.log('exeption in reset', ex);
                            wrapper.spread.notWorking = true;
                            return;
                        }

                        wrapper.shouldModifiedHandlerExecute = true;

                        var fileModifiedCallback = function () {
                            designer.actions.isFileModified = true;
                            if (wrapper.shouldModifiedHandlerExecute) {
                                designer.actions.updateWindowTitle();
                                wrapper.shouldModifiedHandlerExecute = false;
                            }
                        };
                        wrapper.spread.bind(Sheets.Events.CellChanged, fileModifiedCallback);
                        wrapper.spread.bind(Sheets.Events.RowChanged, fileModifiedCallback);
                        wrapper.spread.bind(Sheets.Events.ColumnChanged, fileModifiedCallback);
                        wrapper.spread.bind(Sheets.Events.ActiveSheetChanged, fileModifiedCallback);
                        wrapper.spread.bind(Sheets.Events.InvalidOperation, function (e, args) {
                            if (args.invalidType === 5 /* ChangeSheetName */) {
                                designer.MessageBox.show(args.message, designer.res.title, 3 /* error */);
                            }
                        });

                        wrapper.spread.bind(GC.Spread.Sheets.Events.FloatingObjectLoaded, function (event, args) {
                            bindSlicerContextmenu(wrapper.spread, args.floatingObject, args.element);
                        });

                        wrapper.spread.bind(GC.Spread.Sheets.Events.RangeChanged, function (event, args) {
                            var sheet = wrapper.spread.getActiveSheet();
                            var row = sheet.getActiveRowIndex();
                            var col = sheet.getActiveColumnIndex();
                            if (args.action === 0 /* DragDrop */) {
                                $(".ribbon-bar").gcuiribbon("setTabPageVisible", "tableTab", sheet.tables.find(row, col));
                            }
                            if (args.action === 2 /* Clear */) {
                                hideRibbonDesignTab();

                                if (sheet.tables.find(row, col)) {
                                    // If sparkline is in table, table tab should be selected when sparkline is deleted.
                                    $(".ribbon-bar").gcuiribbon("setTabPageVisible", "tableTab", true);
                                    $(".ribbon-bar").gcuitabs("select", "tableTab");
                                    return;
                                }

                                // When table is deleted, insert table button should be enabled.
                                $(".ribbon-bar").gcuiribbon("setButtonDisabled", "insert-table", false);

                                // Change to home tab when table or sparkline is deleted.
                                $(".ribbon-bar").gcuiribbon("setTabPageVisible", "homeTab", true);
                                $(".ribbon-bar").gcuitabs("select", "homeTab");
                            }
                        });
                        wrapper.spread.bind(GC.Spread.Sheets.Events.FloatingObjectRemoved, function (event, args) {
                            if (args.floatingObject instanceof Sheets.Slicers.Slicer) {
                                hideRibbonDesignTab();
                                var sheet = wrapper.spread.getActiveSheet();
                                var row = sheet.getActiveRowIndex();
                                var col = sheet.getActiveColumnIndex();

                                // Change ribbon tab when slicer is deleted.
                                if (sheet.tables.find(row, col)) {
                                    $(".ribbon-bar").gcuiribbon("setTabPageVisible", "tableTab", true);
                                    $(".ribbon-bar").gcuitabs("select", "tableTab");
                                } else {
                                    $(".ribbon-bar").gcuiribbon("setTabPageVisible", "homeTab", true);
                                    $(".ribbon-bar").gcuitabs("select", "homeTab");
                                }
                            }
                        });

                        // init table custom style and slicer custom style
                        initCustomStyle();

                        designer.ribbon.attachEvent(!init);
                        setFocusToSpread();
                        designer.ribbon.resetSpreadNamedStyle();
                    }

                    wrapper.reset = reset;

                    function hideRibbonDesignTab() {
                        // File tab will show in electron designer, so the first hidden tab number is 6.
                        var LAST_SHOW_TAB_NUMBER = 6;
                        $(".ribbon-bar>ul").find("li:gt(" + LAST_SHOW_TAB_NUMBER + ")").css("display", "none");
                        $(".ribbon-bar>div:gt(" + LAST_SHOW_TAB_NUMBER + ")").attr("aria-hidden", true);
                    }

                    function initCustomStyle() {
                        $(".custom-format-table").css("display", "none");
                        $(".custom-preview").find(".table-format-preview").css("display", "none");
                        designer.FormatTableDialog.customTableStyle = {};
                        designer.FormatTableDialog.currentId = 1;

                        $(".custom-format-slicer").css("display", "none");
                        $(".slicer-custom-preview").find(".slicer-format-preview").css("display", "none");
                        designer.SlicerStyleDialog.customSlicerStyle = {};
                        designer.SlicerStyleDialog.currentId = 1;
                    }

                    function setSelectedSlicerContextmenuName() {
                        var selectedSlicerName;
                        var sheet = wrapper.spread.getActiveSheet();
                        var selectedSlicers = designer.util.getSelectedSlicers(sheet);
                        if (!selectedSlicers || selectedSlicers.length === 0) {
                            return;
                        } else if (selectedSlicers.length === 1) {
                            var slicer = selectedSlicers[0];
                            selectedSlicerName = slicer.captionName();
                            selectedSlicerName = "\"" + selectedSlicerName + "\"";
                        } else {
                            selectedSlicerName = "Slicers";
                        }
                        return selectedSlicerName;
                    }

                    function bindSlicerContextmenu(spread, slicer, element) {
                        var customFloatingObject = slicer;
                        var $contextmenuWidth = $(".slicer-contextmenu-width");
                        var $nameContainer = $("#name-container");
                        var BASIC_WIDTH_TO_CHANGE = 100;
                        var MINIMUM_CONTEXTMENU_WIDTH = 175;
                        var MAX_NAME_SHOW_WIDTH = 400;
                        if (!(customFloatingObject instanceof Sheets.Slicers.Slicer)) {
                            return;
                        }
                        var htmlElement = element;
                        $(htmlElement).bind("contextmenu", function (evt) {
                            var sheet = spread.getActiveSheet();
                            if (sheet.options.isProtected && customFloatingObject.isLocked() && !sheet.options.protectionOption.allowEditObjects) {
                                designer.util.cancelDefault(evt);
                                return;
                            }
                            var nameWidth;
                            var newCaptionName = setSelectedSlicerContextmenuName();
                            if (newCaptionName === undefined) {
                                return;
                            }
                            $contextmenuWidth.removeClass("hidden");
                            $nameContainer.text(newCaptionName);
                            nameWidth = $nameContainer.width();
                            if (nameWidth > MAX_NAME_SHOW_WIDTH) {
                                nameWidth = MAX_NAME_SHOW_WIDTH;
                                newCaptionName = formatCaptionName($contextmenuWidth, $nameContainer, newCaptionName, MAX_NAME_SHOW_WIDTH);
                            }
                            $("#removedSlicerName").text(designer.res.contextMenu.remove + " " + newCaptionName);
                            var contextmenuWidth = nameWidth + BASIC_WIDTH_TO_CHANGE;
                            if (contextmenuWidth < MINIMUM_CONTEXTMENU_WIDTH) {
                                $("#slicer-menu").width(MINIMUM_CONTEXTMENU_WIDTH);
                            } else {
                                $("#slicer-menu").width(contextmenuWidth);
                            }
                            $contextmenuWidth.addClass("hidden");
                            $(".context-menu").removeClass("hidden");
                            $("#slicer-menu").removeClass("hidden");
                            $("#tabstrip-menu").addClass("hidden");
                            $("#sheet-menu").addClass("hidden");
                            $("#comment-menu").addClass("hidden");
                            $("#slicer-menu").position({
                                my: "left top",
                                of: evt
                            });
                            designer.util.cancelDefault(evt);
                        });
                    }

                    function formatCaptionName(nameSelector, containerSelector, name, maxWidth) {
                        if (!name) {
                            return;
                        }
                        if (name.length < 20) {
                            return;
                        }
                        var STEP = 5;
                        if (nameSelector.hasClass("hidden")) {
                            nameSelector.removeClass("hidden");
                        }
                        var length = name.length;
                        var nameWidth;
                        while (length > 0) {
                            name = name.substring(0, length - STEP) + "...";
                            containerSelector.text(name);
                            nameWidth = containerSelector.width();
                            if (nameWidth < maxWidth) {
                                break;
                            }
                            length = name.length;
                        }
                        name = "\"" + name + "\"";
                        return name;
                    }

                    function setFocusToSpread() {
                        if (wrapper.spread.getActiveSheet()) {
                            wrapper.spread.focus(true);
                        }
                    }

                    wrapper.setFocusToSpread = setFocusToSpread;
                    designer.loader.ready(function () {
                        element = $(".spreadWrapper .ss");
                        reset(true);
                    });
                })(designer.wrapper || (designer.wrapper = {}));
                var wrapper = designer.wrapper;
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));