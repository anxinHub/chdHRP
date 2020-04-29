var uiResource = {
    toolBar: {
        zoom: {
            title: "Zoom",
            zoomOption: {
                twentyFivePercentSize: "25%",
                fiftyPercentSize: "50%",
                seventyFivePercentSize: "75%",
                defaultSize: "100%",
                oneHundredTwentyFivePercentSize: "125%",
                oneHundredFiftyPercentSize: "150%",
                twoHundredPercentSize: "200%",
                threeHundredPercentSize: "300%",
                fourHundredPercentSize: "400%"
            }
        },
        clear: {
            title: "Clear",
            clearActions: {
                clearAll: "Clear All",
                clearFormat: "Clear Format"
            }
        },
        export: {
            title: "Export",
            exportActions: {
                exportJson: "Export JSON",
                exportExcel: "Export Excel"
            }
        },
        downloadTitle: "Save File",
        download: "Right Click To Download Linked File As...",
        showInspector: "Show Inspector",
        hideInspector: "Hide Inspector",
        importJson: "Import JSON",
        importFile: "Import File",
        insertTable: "Insert Table",
        insertPicture: "Insert Picture",
        insertComment: "Insert Comment",
        insertSparkline: "Insert Sparkline",
        insertSlicer: "Insert Slicer"
    },
    tabs: {
        spread: "Spread",
        sheet: "Sheet",
        cell: "Cell",
        table: "Table",
        data: "Data",
        comment: "Comment",
        picture: "Picture",
        sparklineEx: "Sparkline",
        slicer: "Slicer"
    },
    spreadTab: {
        general: {
            title: "General",
            allowDragDrop: "Allow Drag and Drop",
            allowDragFill: "Allow Drag and Fill",
            allowZoom: "Allow Zoom",
            allowOverfolow: "Allow Overflow",
            showDragFillSmartTag: "Show Drag Fill Smart Tag"
        },
        calculation: {
            title: "Calculation",
            referenceStyle: {
                title: "Reference style",
                r1c1: "R1C1",
                a1: "A1"
            }
        },
        scrollBar: {
            title: "Scroll Bar",
            showVertical: "Vertical ScrollBar",
            showHorizontal: "Horizontal ScrollBar",
            maxAlign: "Scrollbar Max Align",
            showMax: "Scrollbar Show Max",
            scrollIgnoreHidden: "Scroll Ignore Hidden Row or Column"
        },
        tabStip: {
            title: "TabStrip",
            visible: "TabStrip Visible",
            newTabVisible: "New Tab Visible",
            editable: "Tabstrip Editable",
            showTabNavigation: "Show Tab Navigation"
        },
        color: {
            title: "Color",
            spreadBackcolor: "Spread Backcolor",
            grayAreaBackcolor: "Gray Area Backcolor"
        },
        tip: {
            title: "Tip",
            showDragDropTip: "Show Drag Drop Tip",
            showDragFillTip: "Show Drag Fill Tip",
            scrollTip: {
                title: "Scroll Tip",
                values: {
                    none: "None",
                    horizontal: "Horizontal",
                    vertical: "Vertical",
                    both: "Both"
                }
            },
            resizeTip: {
                title: "Resize Tip",
                values: {
                    none: "None",
                    column: "Column",
                    row: "Row",
                    both: "Both"
                }
            }
        },
        sheets: {
            title: "Sheets",
            sheetName: "Sheet name",
            sheetVisible: "Sheet Visible"
        },
        cutCopy: {
            title: "Cut / Copy",
            cutCopyIndicator: {
                visible: "Show Indicator",
                borderColor: "Indicator Border Color"
            },
            allowCopyPasteExcelStyle: "allowCopyPasteExcelStyle",
            allowExtendPasteRange: "allowExtendPasteRange",
            copyPasteHeaderOptions: {
                title: "HeaderOptions",
                option: {
                    noHeaders: "No Headers",
                    rowHeaders: "Row Headers",
                    columnHeaders: "Column Headers",
                    allHeaders: "All Headers"
                }
            }
        },
        spreadTheme: {
            title: "Spread Theme",
            theme: {
                title: "Theme",
                option: {
                    spreadJS: "SpreadJS",
                    excel2013White: "Excel2013 White",
                    excel2013LightGray: "Excel2013 Light Gray",
                    excel2013DarkGray: "Excel2013 Dark Gray",
                    excel2016Colorful: "Excel2016 Colorful",
                    excel2016DarkGray: "Excel2016 Dark Gray"
                }
            }
        },
        resizeZeroIndicator: {
            title: "ResizeZeroIndicator",
            option: {
                defaultValue: "Default",
                enhanced: "Enhanced"
            }
        }
    },
    sheetTab: {
        general: {
            title: "General",
            rowCount: "Row",
            columnCount: "Column",
            name: "Name",
            tabColor: "Tab Color"
        },
        freeze: {
            title: "Freeze",
            frozenRowCount: "Header Rows",
            frozenColumnCount: "Header Columns",
            trailingFrozenRowCount: "Footer Rows",
            trailingFrozenColumnCount: "Footer Columns",
            frozenLineColor: "Color",
            freezePane: "Freeze",
            unfreeze: "Unfreeze"
        },
        gridLine: {
            title: "Grid Line",
            showVertical: "Vertical Visible",
            showHorizontal: "Horizontal Visible",
            color: "Color"
        },
        header: {
            title: "Header",
            showRowHeader: "Row Header Visible",
            showColumnHeader: "Column Header Visible"
        },
        selection: {
            title: "Selection",
            borderColor: "Border Color",
            backColor: "Backcolor",
            hide: "Hide Selection",
            policy: {
                title: "Policy",
                values: {
                    single: "Single",
                    range: "Range",
                    multiRange: "MultiRange"
                }
            },
            unit: {
                title: "Unit",
                values: {
                    cell: "Cell",
                    row: "Row",
                    column: "Column"
                }
            }
        },
        protection: {
            title: "Protection",
            protectSheet: "Protect Sheet",
            selectLockCells: "Select locked cells",
            selectUnlockedCells: "Select unlocked cells",
            sort: "Sort",
            useAutoFilter: "Use AutoFilter",
            resizeRows: "Resize rows",
            resizeColumns: "Resize columns",
            editObjects: "Edit objects"
        }
    },
    cellTab: {
        style: {
            title: "Style",
            fontFamily: "Font",
            fontSize: "Size",
            foreColor: "Forecolor",
            backColor: "Backcolor",
            waterMark: "Label",
            cellPadding: "Padding",
            cellLabel: {
                title: "Label Option",
                visibility: "Visibility",
                visibilityOption: {
                    auto: "Auto",
                    visible: "Visible",
                    hidden: "Hidden"
                },
                alignment: "Alignment",
                alignmentOption: {
                    topLeft: "Top Left",
                    topCenter: "Top Center",
                    topRight: "Top Right",
                    bottomLeft: "Bottom Left",
                    bottomCenter: "Bottom Center",
                    bottomRight: "Bottom Right"
                },
                fontFamily: "Font",
                fontSize: "Size",
                foreColor: "Forecolor",
                labelMargin: "Margin"
            },
            borders: {
                title: "Border",
                values: {
                    bottom: "Bottom Border",
                    top: "Top Border",
                    left: "Left Border",
                    right: "Right Border",
                    none: "No Border",
                    all: "All Border",
                    outside: "Outside Border",
                    thick: "Thick Box Border",
                    doubleBottom: "Bottom Double Border",
                    thickBottom: "Thick Bottom Border",
                    topBottom: "Top and Bottom Border",
                    topThickBottom: "Top and Thick Bottom Border",
                    topDoubleBottom: "Top and Double Bottom Border"
                }
            }
        },
        border: {
            title: "Border",
            rangeBorderLine: "Line",
            rangeBorderColor: "Color",
            noBorder: "None",
            outsideBorder: "Outside Border",
            insideBorder: "Inside Border",
            allBorder: "All Border",
            leftBorder: "Left Border",
            innerVertical: "Inner Vertical",
            rightBorder: "Right Border",
            topBorder: "Top Border",
            innerHorizontal: "Inner Horizontal",
            bottomBorder: "Bottom Border"
        },
        alignment: {
            title: "Alignment",
            top: "Top",
            middle: "Middle",
            bottom: "Bottom",
            left: "Left",
            center: "Center",
            right: "Right",
            wrapText: "Wrap Text",
            decreaseIndent: "Decrease Indent",
            increaseIndent: "Increase Indent"
        },
        format: {
            title: "Format",
            commonFormat: {
                option: {
                    general: "General",
                    number: "Number",
                    currency: "Currency",
                    accounting: "Accounting",
                    shortDate: "Short Date",
                    longDate: "Long Date",
                    time: "Time",
                    percentage: "Percentage",
                    fraction: "Fraction",
                    scientific: "Scientific",
                    text: "Text"
                }
            },
            percentValue: "0%",
            commaValue: "#,##0.00; (#,##0.00); \"-\"??;@",
            custom: "Custom",
            setButton: "Set"
        },
        merge: {
            title: "Merge Cells",
            mergeCells: "Merge",
            unmergeCells: "Unmerge"
        },
        cellType: {
            title: "Cell Type"
        },
        conditionalFormat: {
            title: "Conditional Formatting",
            useConditionalFormats: "Conditional Formats"
        },
        protection: {
            title: "Protection",
            lock: "Locked",
            sheetIsProtected: "Sheet is protected",
            sheetIsUnprotected: "Sheet is unprotected"
        }
    },
    tableTab: {
        tableStyle: {
            title: "Table Style",
            light: {
                light1: "light1",
                light2: "light2",
                light3: "light3",
                light7: "light7"
            },
            medium: {
                medium1: "medium1",
                medium2: "medium2",
                medium3: "medium3",
                medium7: "medium7"
            },
            dark: {
                dark1: "dark1",
                dark2: "dark2",
                dark3: "dark3",
                dark7: "dark7"
            }
        },
        general: {
            title: "General",
            tableName: "Name"
        },
        options: {
            title: "Options",
            filterButton: "Filter Button",
            headerRow: "Header Row",
            totalRow: "Total Row",
            bandedRows: "Banded Rows",
            bandedColumns: "Banded Columns",
            firstColumn: "First Column",
            lastColumn: "Last Column"
        }
    },
    dataTab: {
        sort: {
            title: "Sort & Filter",
            asc: "Sort A-Z",
            desc: "Sort Z-A",
            filter: "Filter"
        },
        group: {
            title: "Group",
            group: "Group",
            ungroup: "Ungroup",
            showDetail: "Show Detail",
            hideDetail: "Hide Detail",
            showRowOutline: "Show Row Outline",
            showColumnOutline: "Show Column Outline"
        },
        dataValidation: {
            title: "Data Validation",
            setButton: "Set",
            clearAllButton: "Clear All",
            circleInvalidData: "Circle Invalid Data",
            setting: {
                title: "Setting",
                values: {
                    validatorType: {
                        title: "Validator Type",
                        option: {
                            anyValue: "Any Value",
                            number: "Number",
                            list: "List",
                            formulaList: "FormulaList",
                            date: "Date",
                            textLength: "Text Length",
                            custom: "Custom"
                        }
                    },
                    ignoreBlank: "IgnoreBlank",
                    validatorComparisonOperator: {
                        title: "Operator",
                        option: {
                            between: "Between",
                            notBetween: "NotBetween",
                            equalTo: "EqualTo",
                            notEqualTo: "NotEqualTo",
                            greaterThan: "GreaterThan",
                            lessThan: "LessThan",
                            greaterThanOrEqualTo: "GreaterThanOrEqualTo",
                            lessThanOrEqualTo: "LessThanOrEqualTo"
                        }
                    },
                    number: {
                        minimum: "Minimum",
                        maximum: "Maximum",
                        value: "Value",
                        isInteger: "Is Integer"
                    },
                    source: "Source",
                    date: {
                        startDate: "Start Date",
                        endDate: "End Date",
                        value: "Value",
                        isTime: "Is Time"
                    },
                    formula: "Formula"
                }
            },
            inputMessage: {
                title: "Input Message",
                values: {
                    showInputMessage: "Show when cell is selected",
                    title: "Title",
                    message: "Message"
                }
            },
            errorAlert: {
                title: "Error Alert",
                values: {
                    showErrorAlert: "Show after invalid data is entered",
                    alertType: {
                        title: "Alert Type",
                        option: {
                            stop: "Stop",
                            warning: "Warning",
                            information: "Information"
                        }
                    },
                    title: "Title",
                    message: "Message"
                }
            }
        }
    },
    commentTab: {
        general: {
            title: "General",
            dynamicSize: "Dynamic Size",
            dynamicMove: "Dynamic Move",
            lockText: "Lock Text",
            showShadow: "Show Shadow"
        },
        font: {
            title: "Font",
            fontFamily: "Font",
            fontSize: "Size",
            fontStyle: {
                title: "Style",
                values: {
                    normal: "normal",
                    italic: "italic",
                    oblique: "oblique",
                    inherit: "inherit"
                }
            },
            fontWeight: {
                title: "Weight",
                values: {
                    normal: "normal",
                    bold: "bold",
                    bolder: "bolder",
                    lighter: "lighter"
                }
            },
            textDecoration: {
                title: "Decoration",
                values: {
                    none: "none",
                    underline: "underline",
                    overline: "overline",
                    linethrough: "linethrough"
                }
            }
        },
        border: {
            title: "Border",
            width: "Width",
            style: {
                title: "Style",
                values: {
                    none: "none",
                    hidden: "hidden",
                    dotted: "dotted",
                    dashed: "dashed",
                    solid: "solid",
                    double: "double",
                    groove: "groove",
                    ridge: "ridge",
                    inset: "inset",
                    outset: "outset"
                }
            },
            color: "Color"
        },
        appearance: {
            title: "Appearance",
            horizontalAlign: {
                title: "Horizontal",
                values: {
                    left: "left",
                    center: "center",
                    right: "right",
                    general: "general"
                }
            },
            displayMode: {
                title: "Display Mode",
                values: {
                    alwaysShown: "AlwaysShown",
                    hoverShown: "HoverShown"
                }
            },
            foreColor: "Forecolor",
            backColor: "Backcolor",
            padding: "Padding",
            zIndex: "Z-Index",
            opacity: "Opacity"
        }
    },
    pictureTab: {
        general: {
            title: "General",
            moveAndSize: "Move and size with cells",
            moveAndNoSize: "Move and don't size with cells",
            noMoveAndSize: "Don't move and size with cells",
            fixedPosition: "Fixed Position"
        },
        border: {
            title: "Border",
            width: "Width",
            radius: "Radius",
            style: {
                title: "Style",
                values: {
                    solid: "solid",
                    dotted: "dotted",
                    dashed: "dashed",
                    double: "double",
                    groove: "groove",
                    ridge: "ridge",
                    inset: "inset",
                    outset: "outset"
                }
            },
            color: "Color"
        },
        appearance: {
            title: "Appearance",
            stretch: {
                title: "Stretch",
                values: {
                    stretch: "Stretch",
                    center: "Center",
                    zoom: "Zoom",
                    none: "None"
                }
            },
            backColor: "Backcolor"
        }
    },
    sparklineExTab: {
        pieSparkline: {
            title: "PieSparkline Setting",
            values: {
                percentage: "Percentage",
                color: "Color ",
                setButton: "Set"
            }
        },
        areaSparkline: {
            title: "AreaSparkline Setting",
            values: {
                line1: "Line 1",
                line2: "Line 2",
                minimumValue: "Minimum Value",
                maximumValue: "Maximum Value",
                points: "Points",
                positiveColor: "Positive Color",
                negativeColor: "Negative Color",
                setButton: "Set"
            }
        },
        boxplotSparkline: {
            title: "BoxPlotSparkline Setting",
            values: {
                points: "Points",
                boxplotClass: "BoxPlotClass",
                scaleStart: "ScaleStart",
                scaleEnd: "ScaleEnd",
                acceptableStart: "AcceptableStart",
                acceptableEnd: "AcceptableEnd",
                colorScheme: "ColorScheme",
                style: "Style",
                showAverage: "Show Average",
                vertical: "Vertical",
                setButton: "Set"
            }
        },
        bulletSparkline: {
            title: "BulletSparkline Setting",
            values: {
                measure: "Measure",
                target: "Target",
                maxi: "Maxi",
                forecast: "Forecast",
                good: "Good",
                bad: "Bad",
                tickunit: "Tickunit",
                colorScheme: "ColorScheme",
                vertical: "Vertical",
                setButton: "Set"
            }
        },
        cascadeSparkline: {
            title: "CascadeSparkline Setting",
            values: {
                pointsRange: "PointsRange",
                pointIndex: "PointIndex",
                minimum: "Minimum",
                maximum: "Maximum",
                positiveColor: "ColorPositive",
                negativeColor: "ColorNegative",
                labelsRange: "LabelsRange",
                vertical: "Vertical",
                setButton: "Set"
            }
        },
        compatibleSparkline: {
            title: "CompatibleSparkline Setting",
            values: {
                data: {
                    title: "Data",
                    dataOrientation: "DataOrientation",
                    dateAxisData: "DateAxisData",
                    dateAxisOrientation: "DateAxisOrientation",
                    displayEmptyCellAs: "DisplayEmptyCellAs",
                    showDataInHiddenRowOrColumn: "Show data in hidden rows and columns"
                },
                show: {
                    title: "Show",
                    showFirst: "Show First",
                    showLast: "Show Last",
                    showHigh: "Show High",
                    showLow: "Show Low",
                    showNegative: "Show Negative",
                    showMarkers: "Show Markers"
                },
                group: {
                    title: "Group",
                    minAxisType: "MinAxisType",
                    maxAxisType: "MaxAxisType",
                    manualMin: "ManualMin",
                    manualMax: "ManualMax",
                    rightToLeft: "RightToLeft",
                    displayXAxis: "Display XAxis"
                },
                style: {
                    title: "Style",
                    negative: "Negative",
                    markers: "Markers",
                    axis: "Axis",
                    series: "Series",
                    highMarker: "High Marker",
                    lowMarker: "Low Marker",
                    firstMarker: "First Marker",
                    lastMarker: "Last Marker",
                    lineWeight: "Line Weight"
                },
                setButton: "Set"
            }
        },
        hbarSparkline: {
            title: "HbarSparkline Setting",
            values: {
                value: "Value",
                colorScheme: "ColorScheme",
                setButton: "Set"
            }
        },
        vbarSparkline: {
            title: "VarSparkline Setting",
            values: {
                value: "Value",
                colorScheme: "ColorScheme",
                setButton: "Set"
            }
        },
        paretoSparkline: {
            title: "ParetoSparkline Setting",
            values: {
                points: "Points",
                pointIndex: "PointIndex",
                colorRange: "ColorRange",
                highlightPosition: "HighlightPosition",
                target: "Target",
                target2: "Target2",
                label: "Label",
                vertical: "Vertical",
                setButton: "Set"
            }
        },
        pieSparkline: {
            title: "PieSparkline Setting",
            values: {
                percentage: "Percentage",
                color: "Color",
                setButton: "Set"
            }
        },
        scatterSparkline: {
            title: "ScatterSparkline Setting",
            values: {
                points1: "Points1",
                points2: "Points2",
                minX: "MinX",
                maxX: "MaxX",
                minY: "MinY",
                maxY: "MaxY",
                hLine: "HLine",
                vLine: "VLine",
                xMinZone: "XMinZone",
                xMaxZone: "XMaxZone",
                yMinZone: "YMinZone",
                yMaxZone: "YMaxZone",
                color1: "Color1",
                color2: "Color2",
                tags: "Tags",
                drawSymbol: "Draw Symbol",
                drawLines: "Draw Lines",
                dashLine: "Dash Line",
                setButton: "Set"
            }
        },
        spreadSparkline: {
            title: "SpreadSparkline Setting",
            values: {
                points: "Points",
                scaleStart: "ScaleStart",
                scaleEnd: "ScaleEnd",
                style: "Style",
                colorScheme: "ColorScheme",
                showAverage: "Show Average",
                vertical: "Vertical",
                setButton: "Set"
            }
        },
        stackedSparkline: {
            title: "StackedSparkline Setting",
            values: {
                points: "Points",
                colorRange: "ColorRange",
                labelRange: "LabelRange",
                maximum: "Maximum",
                targetRed: "TargetRed",
                targetGreen: "TargetGreen",
                targetBlue: "TargetBlue",
                targetYellow: "TargetYellow",
                color: "Color",
                highlightPosition: "HighlightPosition",
                textOrientation: "TextOrientation",
                textSize: "TextSize",
                vertical: "Vertical",
                setButton: "Set"
            }
        },
        variSparkline: {
            title: "VariSparkline Setting",
            values: {
                variance: "Variance",
                reference: "Reference",
                mini: "Mini",
                maxi: "Maxi",
                mark: "Mark",
                tickunit: "TickUnit",
                colorPositive: "ColorPositive",
                colorNegative: "ColorNegative",
                legend: "Legend",
                vertical: "Vertical",
                setButton: "Set"
            }
        },
        monthSparkline: {
            title: "MonthSparkline Setting"
        },
        yearSparkline: {
            title: "YearSparkline Setting"
        },
        monthYear: {
            data: "Data",
            month: "Month",
            year: "Year",
            emptyColor: "Empty Color",
            startColor: "Start Color",
            middleColor: "Middle Color",
            endColor: "End Color",
            colorRange: "Color Range",
            setButton: "set"
        },
        orientation: {
            vertical: "Vertical",
            horizontal: "Horizontal"
        },
        axisType: {
            individual: "Individual",
            custom: "Custom"
        },
        emptyCellDisplayType: {
            gaps: "Gaps",
            zero: "Zero",
            connect: "Connect"
        },
        boxplotClass: {
            fiveNS: "5NS",
            sevenNS: "7NS",
            tukey: "Tukey",
            bowley: "Bowley",
            sigma: "Sigma3"
        },
        boxplotStyle: {
            classical: "Classical",
            neo: "Neo"
        },
        paretoLabel: {
            none: "None",
            single: "Single",
            cumulated: "Cumulated"
        },
        spreadStyle: {
            stacked: "Stacked",
            spread: "Spread",
            jitter: "Jitter",
            poles: "Poles",
            stackedDots: "StackedDots",
            stripe: "Stripe"
        }
    },
    slicerTab: {
        slicerStyle: {
            title: "Slicer Style",
            light: {
                light1: "light1",
                light2: "light2",
                light3: "light3",
                light5: "light5",
                light6: "light6"
            },
            dark: {
                dark1: "dark1",
                dark2: "dark2",
                dark3: "dark3",
                dark5: "dark5",
                dark6: "dark6"
            }
        },
        general: {
            title: "General",
            name: "Name",
            captionName: "Caption Name",
            itemSorting: {
                title: "Item Sorting",
                option: {
                    none: "None",
                    ascending: "Ascending",
                    descending: "Descending"
                }
            },
            displayHeader: "Display Header"
        },
        layout: {
            title: "Layout",
            columnNumber: "Column Number",
            buttonHeight: "Button Height",
            buttonWidth: "Button Width"
        },
        property: {
            title: "Property",
            moveAndSize: "Move and size with cells",
            moveAndNoSize: "Move and don't size with cells",
            noMoveAndSize: "Don't move and size with cells",
            locked: "Locked"
        }
    },
    colorPicker: {
        themeColors: "Theme Colors",
        standardColors: "Standard Colors",
        noFills: "No Fills"
    },
    conditionalFormat: {
        setButton: "Set",
        ruleTypes: {
            title: "Type",
            highlightCells: {
                title: "Highlight Cells Rules",
                values: {
                    cellValue: "Cell Value",
                    specificText: "Specific Text",
                    dateOccurring: "Date Occurring",
                    unique: "Unique",
                    duplicate: "Duplicate"
                }
            },
            topBottom: {
                title: "Top/Bottom Rules",
                values: {
                    top10: "Top10",
                    average: "Average"
                }
            },
            dataBars: {
                title: "Data Bars",
                labels: {
                    minimum: "Minimum",
                    maximum: "Maximum",
                    type: "Type",
                    value: "Value",
                    appearance: "Appearance",
                    showBarOnly: "Show Bar Only",
                    useGradient: "Use Gradien",
                    showBorder: "Show Border",
                    barDirection: "Bar Direction",
                    negativeFillColor: "Negative Color",
                    negativeBorderColor: "Negative Border",
                    axis: "Axis",
                    axisPosition: "Position",
                    axisColor: "Color"
                },
                valueTypes: {
                    number: "Number",
                    lowestValue: "LowestValue",
                    highestValue: "HighestValue",
                    percent: "Percent",
                    percentile: "Percentile",
                    automin: "Automin",
                    automax: "Automax",
                    formula: "Formula"
                },
                directions: {
                    leftToRight: "Left-to-Right",
                    rightToLeft: "Right-to-Left"
                },
                axisPositions: {
                    automatic: "Automatic",
                    cellMidPoint: "CellMidPoint",
                    none: "None"
                }
            },
            colorScales: {
                title: "Color Scales",
                labels: {
                    minimum: "Minimum",
                    midpoint: "Midpoint",
                    maximum: "Maximum",
                    type: "Type",
                    value: "Value",
                    color: "Color"
                },
                values: {
                    twoColors: "2-Color Scale",
                    threeColors: "3-Color Scale"
                },
                valueTypes: {
                    number: "Number",
                    lowestValue: "LowestValue",
                    highestValue: "HighestValue",
                    percent: "Percent",
                    percentile: "Percentile",
                    formula: "Formula"
                }
            },
            iconSets: {
                title: "Icon Sets",
                labels: {
                    style: "Style",
                    showIconOnly: "Show Icon Only",
                    reverseIconOrder: "Reverse Icon Order",

                },
                types: {
                    threeArrowsColored: "ThreeArrowsColored",
                    threeArrowsGray: "ThreeArrowsGray",
                    threeTriangles: "ThreeTriangles",
                    threeStars: "ThreeStars",
                    threeFlags: "ThreeFlags",
                    threeTrafficLightsUnrimmed: "ThreeTrafficLightsUnrimmed",
                    threeTrafficLightsRimmed: "ThreeTrafficLightsRimmed",
                    threeSigns: "ThreeSigns",
                    threeSymbolsCircled: "ThreeSymbolsCircled",
                    threeSymbolsUncircled: "ThreeSymbolsUncircled",
                    fourArrowsColored: "FourArrowsColored",
                    fourArrowsGray: "FourArrowsGray",
                    fourRedToBlack: "FourRedToBlack",
                    fourRatings: "FourRatings",
                    fourTrafficLights: "FourTrafficLights",
                    fiveArrowsColored: "FiveArrowsColored",
                    fiveArrowsGray: "FiveArrowsGray",
                    fiveRatings: "FiveRatings",
                    fiveQuarters: "FiveQuarters",
                    fiveBoxes: "FiveBoxes"
                },
                valueTypes: {
                    number: "Number",
                    percent: "Percent",
                    percentile: "Percentile",
                    formula: "Formula"
                }
            },
            removeConditionalFormat: {
                title: "None"
            }
        },
        operators: {
            cellValue: {
                types: {
                    equalsTo: "EqualsTo",
                    notEqualsTo: "NotEqualsTo",
                    greaterThan: "GreaterThan",
                    greaterThanOrEqualsTo: "GreaterThanOrEqualsTo",
                    lessThan: "LessThan",
                    lessThanOrEqualsTo: "LessThanOrEqualsTo",
                    between: "Between",
                    notBetween: "NotBetween"
                }
            },
            specificText: {
                types: {
                    contains: "Contains",
                    doesNotContain: "DoesNotContain",
                    beginsWith: "BeginsWith",
                    endsWith: "EndsWith"
                }
            },
            dateOccurring: {
                types: {
                    today: "Today",
                    yesterday: "Yesterday",
                    tomorrow: "Tomorrow",
                    last7Days: "Last7Days",
                    thisMonth: "ThisMonth",
                    lastMonth: "LastMonth",
                    nextMonth: "NextMonth",
                    thisWeek: "ThisWeek",
                    lastWeek: "LastWeek",
                    nextWeek: "NextWeek"
                }
            },
            top10: {
                types: {
                    top: "Top",
                    bottom: "Bottom"
                }
            },
            average: {
                types: {
                    above: "Above",
                    below: "Below",
                    equalOrAbove: "EqualOrAbove",
                    equalOrBelow: "EqualOrBelow",
                    above1StdDev: "Above1StdDev",
                    below1StdDev: "Below1StdDev",
                    above2StdDev: "Above2StdDev",
                    below2StdDev: "Below2StdDev",
                    above3StdDev: "Above3StdDev",
                    below3StdDev: "Below3StdDev"
                }
            }
        },
        texts: {
            cells: "Format only cells with:",
            rankIn: "Format values that rank in the:",
            inRange: "values in the selected range.",
            values: "Format values that are:",
            average: "the average for selected range.",
            allValuesBased: "Format all cells based on their values:",
            all: "Format all:",
            and: "and",
            formatStyle: "use style",
            showIconWithRules: "Display each icon according to these rules:"
        },
        formatSetting: {
            formatUseBackColor: "BackColor",
            formatUseForeColor: "ForeColor",
            formatUseBorder: "Border"
        }
    },
    cellTypes: {
        title: "Cell Types",
        buttonCellType: {
            title: "ButtonCellType",
            values: {
                marginTop: "Margin-Top",
                marginRight: "Margin-Right",
                marginBottom: "Margin-Bottom",
                marginLeft: "Margin-Left",
                text: "Text",
                backColor: "BackColor"
            }
        },
        checkBoxCellType: {
            title: "CheckBoxCellType",
            values: {
                caption: "Caption",
                textTrue: "TextTrue",
                textIndeterminate: "TextIndeterminate",
                textFalse: "TextFalse",
                textAlign: {
                    title: "TextAlign",
                    values: {
                        top: "Top",
                        bottom: "Bottom",
                        left: "Left",
                        right: "Right"
                    }
                },
                isThreeState: "IsThreeState"
            }
        },
        comboBoxCellType: {
            title: "ComboBoxCellType",
            values: {
                editorValueType: {
                    title: "EditorValueType",
                    values: {
                        text: "Text",
                        index: "Index",
                        value: "Value"
                    }
                },
                itemsText: "Items Text",
                itemsValue: "Items Value"
            }
        },
        hyperlinkCellType: {
            title: "HyperlinkCellType",
            values: {
                linkColor: "LinkColor",
                visitedLinkColor: "VisitedLinkColor",
                text: "Text",
                linkToolTip: "LinkToolTip"
            }
        },
        clearCellType: {
            title: "None"
        },
        setButton: "Set"
    },
    sparklineDialog: {
        title: "SparklineEx Setting",
        sparklineExType: {
            title: "Type",
            values: {
                line: "Line",
                column: "Column",
                winLoss: "Win/Loss",
                pie: "Pie",
                area: "Area",
                scatter: "Scatter",
                spread: "Spread",
                stacked: "Stacked",
                bullet: "Bullet",
                hbar: "Hbar",
                vbar: "Vbar",
                variance: "Variance",
                boxplot: "BoxPlot",
                cascade: "Cascade",
                pareto: "Pareto",
                month: "Month",
                year: "Year"
            }
        },
        lineSparkline: {
            dataRange: "Data Range",
            locationRange: "Location Range",
            dataRangeError: "Data range is invalid!",
            singleDataRange: "Data range should be in a single row or column.",
            locationRangeError: "Location range is invalid!"
        },
        bulletSparkline: {
            measure: "Measure",
            target: "Target",
            maxi: "Maxi",
            forecast: "Forecast",
            good: "Good",
            bad: "Bad",
            tickunit: "Tickunit",
            colorScheme: "ColorScheme",
            vertical: "Vertical"
        },
        hbarSparkline: {
            value: "Value",
            colorScheme: "ColorScheme"
        },
        varianceSparkline: {
            variance: "Variance",
            reference: "Reference",
            mini: "Mini",
            maxi: "Maxi",
            mark: "Mark",
            tickunit: "TickUnit",
            colorPositive: "ColorPositive",
            colorNegative: "ColorNegative",
            legend: "Legend",
            vertical: "Vertical"
        },
        monthSparkline: {
            year: "Year",
            month: "Month",
            emptyColor: "Empty Color",
            startColor: "Start Color",
            middleColor: "Middle Color",
            endColor: "End Color",
            colorRange: "Color Range"
        },
        yearSparkline: {
            year: "Year",
            emptyColor: "Empty Color",
            startColor: "Start Color",
            middleColor: "Middle Color",
            endColor: "End Color",
            colorRange: "Color Range"
        }
    },
    slicerDialog: {
        insertSlicer: "Insert Slicer"
    },
    passwordDialog: {
        title: "Password",
        error: "Incorrect Password!"
    },
    tooltips: {
        style: {
            fontBold: "Mark text bold.",
            fontItalic: "Mark text italic",
            fontUnderline: "Underline text.",
            fontOverline: "Overline text.",
            fontLinethrough: "Strikethrough text."
        },
        alignment: {
            leftAlign: "Align text to the left.",
            centerAlign: "Center text.",
            rightAlign: "Align text to the right.",
            topAlign: "Align text to the top.",
            middleAlign: "Align text to the middle.",
            bottomAlign: "Align text to the bottom.",
            decreaseIndent: "Decrease the indent level.",
            increaseIndent: "Increase the indent level."
        },
        border: {
            outsideBorder: "Outside Border",
            insideBorder: "Inside Border",
            allBorder: "All Border",
            leftBorder: "Left Border",
            innerVertical: "Inner Vertical",
            rightBorder: "Right Border",
            topBorder: "Top Border",
            innerHorizontal: "Inner Horizontal",
            bottomBorder: "Bottom Border"
        },
        format: {
            percentStyle: "Percent Style",
            commaStyle: "Comma Style",
            increaseDecimal: "Increase Decimal",
            decreaseDecimal: "Decrease Decimal"
        }
    },
    defaultTexts: {
        buttonText: "Button",
        checkCaption: "Check",
        comboText: "United States,China,Japan",
        comboValue: "US,CN,JP",
        hyperlinkText: "LinkText",
        hyperlinkToolTip: "Hyperlink Tooltip"
    },
    messages: {
        invalidImportFile: "Invalid file, import failed.",
        duplicatedSheetName: "Duplicated sheet name.",
        duplicatedTableName: "Duplicated table name.",
        rowColumnRangeRequired: "Please select a range of row or column.",
        imageFileRequired: "The file must be image!",
        duplicatedSlicerName: "Duplicated slicer name.",
        invalidSlicerName: "Slicer name is not valid."
    },
    contextMenu: {
        cutItem: "Cut",
        copyItem: "Copy",
        pasteItem: "Paste",
        insertItem: "Insert",
        deleteItem: "Delete",
        mergeItem: "Merge",
        unmergeItem: "Unmerge"
    },
    dialog: {
        ok: "OK",
        cancel: "Cancel"
    }
};

