var GC;
(function (GC) {
    (function (Spread) {
        (function (Sheets) {
            (function (designer) {
                (function (en_res) {
                    en_res.title = "Spread.Sheets Designer";
                    en_res.defaultFont = "10pt Arial";
                    en_res.ok = "OK";
                    en_res.yes = "Yes";
                    en_res.no = "No";
                    en_res.apply = "Apply";
                    en_res.cancel = "Cancel";
                    en_res.close = "Close";

                    en_res.saveFileDialogTitle = "Save As";
                    en_res.openFileDialogTitle = "Open";
                    en_res.allSpreadFileFilter = 'All Spreadsheet files (*.ssjson *.xlsx *.csv)';
                    en_res.spreadFileFilter = 'SpreadJS files (*.ssjson)';
                    en_res.ssJSONToJSFilter = 'Javascript files (*.js)';
                    en_res.excelFileFilter = 'Excel Workbook (*.xlsx)';
                    en_res.csvFileFilter = "CSV Files (*.csv)";
                    en_res.pdfFileFilter = "PDF Files (*.pdf)";
                    en_res.allFileFilter = 'All Files (*.*)';
                    en_res.importFileDialogTitle = "Import";
                    en_res.exportFileDialogTitle = "Export";

                    en_res.insertCellInSheet = "Cannot shift cells off of whole sheet";
                    en_res.insertCellInMixtureRange = "This command cannot be used with selections that contain entire rows, or columns,and also other cells. Try select only entire rows, entire columns, or just groups of cells.";
                    en_res.NotExecInMultiRanges = "The command you chose cannot be performed with multiple selections. Select a single range and click the command again";
                    en_res.unsavedWarning = "The file has not been saved. Save it? ";

                    en_res.requestTempalteFail = "Template file request error.";
                    en_res.requestTemplateConfigFail = "Template config file request error.";
                    en_res.openFileFormatError = "File format is not correct.";

                    en_res.closingNotification = "Warning: Current file has been modified.\nDo you want to save your changes to the file?";

                    en_res.sameSlicerName = "Slicer name already in use. Please enter a unique name.";
                    en_res.nullSlicerName = "Slicer name is not valid.";

                    en_res.changePartOfArrayWarning = "Can not change part of an array formula.";
                    en_res.exportCsvSheetIndexError = "The Sheet index is invalid.";

                    en_res.fontPicker = {
                        familyLabelText: 'Font:',
                        styleLabelText: 'Font style:',
                        sizeLabelText: 'Size:',
                        weightLabelText: 'Weight:',
                        colorLabelText: 'Color:',
                        normalFontLabelText: 'Normal font',
                        previewLabelText: 'Preview',
                        previewText: 'AaBbCcYyZz',
                        effects: "Effects",
                        underline: "Underline",
                        strikethrough: "Strikethrough",
                        //
                        // Fonts shown in font selector.
                        //
                        // the property's name means the font family name.
                        // the property's value means the text shown in drop down list.
                        //
                        fontFamilies: {
                            "Arial": "Arial",
                            "'Arial Black'": "Arial Black",
                            "Calibri": "Calibri",
                            "Cambria": "Cambria",
                            "Candara": "Candara",
                            "Century": "Century",
                            "'Courier New'": "Courier New",
                            "'Comic Sans MS'": "Comic Sans MS",
                            "Garamond": "Garamond",
                            "Georgia": "Georgia",
                            "'Malgun Gothic'": "Malgun Gothic",
                            "Mangal": "Mangal",
                            "Tahoma": "Tahoma",
                            "Times": "Times",
                            "'Times New Roman'": "Times New Roman",
                            "'Trebuchet MS'": "Trebuchet MS",
                            "Verdana": "Verdana",
                            "Wingdings": "Wingdings",
                            "Meiryo": "Meiryo",
                            "'MS Gothic'": "MS Gothic",
                            "'MS Mincho'": "MS Mincho",
                            "'MS PGothic'": "MS PGothic",
                            "'MS PMincho'": "MS PMincho"
                        },
                        fontStyles: {
                            'normal': 'Normal',
                            'italic': 'Italic',
                            'oblique': 'Oblique'
                        },
                        fontWeights: {
                            'normal': 'Normal',
                            'bold': 'Bold',
                            'bolder': 'Bolder',
                            'lighter': 'Lighter'
                        },
                        alternativeFonts: "Arial,'Segoe UI',Thonburi,Verdana,Sans-Serif",
                        defaultSize: '10'
                    };

                    en_res.commonFormats = {
                        Number: {
                            format: "0.00",
                            label: "Number"
                        },
                        Currency: {
                            format: "$#,##0.00",
                            label: "Currency"
                        },
                        Accounting: {
                            format: "$ #,##0.00;$ (#,##0.00);$ \"-\"??;@",
                            label: "Accounting"
                        },
                        ShortDate: {
                            format: "m/d/yyyy",
                            label: "Short Date"
                        },
                        LongDate: {
                            format: "dddd, mmmm dd, yyyy",
                            label: "Long Date"
                        },
                        Time: {
                            format: "h:mm:ss AM/PM",
                            label: "Time"
                        },
                        Percentage: {
                            format: "0%",
                            label: "Percentage"
                        },
                        Fraction: {
                            format: "# ?/?",
                            label: "Fraction"
                        },
                        Scientific: {
                            format: "0.00E+00",
                            label: "Scientific"
                        },
                        Text: {
                            format: "@",
                            label: "Text"
                        },
                        Comma: {
                            format: " #,##0.00; (#,##0.00); \"-\"??;@",
                            label: "Comma"
                        }
                    };
                    en_res.customFormat = "Custom";
                    en_res.generalFormat = "General";

                    en_res.colorPicker = {
                        themeColorsTitle: "Theme Colors",
                        standardColorsTitle: "Standard Colors",
                        noFillText: "No Color",
                        moreColorsText: "More Colors...",
                        colorDialogTitle: "Color",
                        red: "Red: ",
                        green: "Green: ",
                        blue: "Blue: ",
                        newLabel: "New",
                        currentLabel: "Current"
                    };

                    en_res.formatDialog = {
                        title: "Format Cells",
                        number: 'Number',
                        alignment: 'Alignment',
                        font: 'Font',
                        border: 'Border',
                        fill: 'Fill',
                        protection: 'Protection',
                        category: 'Category:',
                        backColor: 'Background Color',
                        textAlignment: 'Text alignment',
                        horizontalAlignment: 'Horizontal:',
                        verticalAlignment: 'Vertical:',
                        indent: 'Indent:',
                        textControl: 'Text control',
                        wrapText: 'Wrap text',
                        shrink: 'Shrink to fit',
                        merge: 'Merge cells',
                        top: 'Top',
                        bottom: 'Bottom',
                        left: 'Left',
                        right: 'Right',
                        center: 'Center',
                        general: 'General',
                        sampleText: 'Text',
                        cantMergeMessage: 'Cannot merge overlapping ranges.',
                        lock: "Locked",
                        lockComments: "Locking cells has no effect until you protect the worksheet(Home tab, Cells group, 'Protect Sheet' button in Format drop-down list).",
                        backGroundColor: "Background Color:",
                        moreColorsText: "More Colors",
                        sample: "Sample"
                    };

                    en_res.formatComment = {
                        title: "Format Comment",
                        protection: "Protection",
                        commentLocked: "Locked",
                        commentLockText: "Lock text",
                        commentLockComments: "Locking objects has no effect unless the sheet is protected. To help protect the sheet, choose Format on the Home tab, and then choose Protect Sheet.",
                        properties: "Properties",
                        positioning: "Object positioning",
                        internalMargin: "Internal margin",
                        moveSize: "Move and size with cells",
                        moveNoSize: "Move but don't size with cells",
                        noMoveSize: "Don't move or size with cells",
                        automatic: "Automatic",
                        autoSize: "Automatic size",
                        colors: "Colors and Lines",
                        size: "Size",
                        fill: "Fill",
                        line: "Line",
                        height: "Height",
                        width: "Width",
                        lockRatio: "Lock aspect ratio",
                        color: "Color",
                        transparency: "Transparency",
                        style: "Style",
                        dotted: "Dotted",
                        dashed: "Dashed",
                        solid: "Solid",
                        double: "Double",
                        none: "None",
                        groove: "Groove",
                        ridge: "Ridge",
                        inset: "Inset",
                        outset: "Outset"
                    };

                    en_res.categories = {
                        general: "General",
                        numbers: "Number",
                        currency: "Currency",
                        accounting: "Accounting",
                        date: "Date",
                        time: "Time",
                        percentage: "Percentage",
                        fraction: "Fraction",
                        scientific: "Scientific",
                        text: "Text",
                        special: "Special",
                        custom: "Custom"
                    };

                    en_res.formatNumberComments = {
                        generalComments: "General format cells have no specific number format.",
                        numberComments: "Number is used for general display of numbers. Currency and Accounting offer specialized formatting for monetary values.",
                        currencyComments: "Currency formats are used for general monetary values. Use Accounting formats to align decimal points in a column.",
                        accountingComments: "Accounting formats line up the currency symbols and decimal points in a column.",
                        dateComments: "Date formats display date and time serial numbers as date values.",
                        timeComments: "Time formats display date and time serial numbers as date values.",
                        percentageComments: "Percentage formats multiply the cell value by 100 and display the result with a percent symbol.",
                        textComments: "Text format cells are treated as text even when a number is in the cell. The cell is displayed exactly as entered.",
                        specialComments: "Special formats are useful for tracking list and database values.",
                        customComments: "Type the number format code, using one of the existing codes as a starting point."
                    };

                    en_res.formatNumberPickerSetting = {
                        type: "Type:",
                        decimalPlaces: "Decimal places:",
                        symbol: "Symbol:",
                        negativeNumber: "Negative numbers:",
                        separator: "Use 1000 Separator(,)",
                        deleted: "Delete",
                        locale: "locale (location):",
                        calendar: "Calendar type:"
                    };

                    en_res.localeType = {
                        en_us: "English(U.S.)",
                        ja_jp: "Japanese"
                    };

                    en_res.calendarType = {
                        western: "Western",
                        JER: "Japanese Emperor Reign"
                    };

                    en_res.fractionFormats = [
                        "# ?/?",
                        "# ??/??",
                        "# ???/???",
                        "# ?/2",
                        "# ?/4",
                        "# ?/8",
                        "# ??/16",
                        "# ?/10",
                        "# ??/100"
                    ];

                    en_res.numberFormats = [
                        "0",
                        "0;[Red]0",
                        "0_);(0)",
                        "0_);[Red](0)",
                        "#,##0",
                        "#,##0;[Red]#,##0",
                        "#,##0_);(#,##0)",
                        "#,##0_);[Red](#,##0)"
                    ];

                    en_res.dateFormats = [
                        "m/d/yyyy",
                        "[$-F800]dddd, mmmm dd, yyyy",
                        "m/d;@",
                        "m/d/yy;@",
                        "mm/dd/yy;@",
                        "[$-409]d-mmm;@",
                        "[$-409]d-mmm-yy;@",
                        "[$-409]dd-mmm-yy;@",
                        "[$-409]mmm-yy;@",
                        "[$-409]mmmm-yy;@",
                        "[$-409]mmmm d, yyyy;@",
                        "[$-409]m/d/yy h:mm AM/PM;@",
                        "m/d/yy h:mm;@",
                        "[$-409]mmmmm;@",
                        "[$-409]mmmmm-yy;@",
                        "m/d/yyyy;@",
                        "[$-409]d-mmm-yyyy;@"
                    ];

                    en_res.japanWesternDateFormat = [
                        "yyyy'年'm'月'd'日';@",
                        "yyyy'年'm'月';@",
                        "m'月'd'日';@",
                        "yyyy/m/d;@",
                        "[$-409]yyyy/m/d h:mm AM/PM;@",
                        "yyyy/m/d h:mm;@",
                        "m/d;@",
                        "m/d/yy;@",
                        "mm/dd/yy;@",
                        "[$-409]d-mmm;@",
                        "[$-409]d-mmm-yy;@",
                        "[$-409]dd-mmm-yy;@",
                        "[$-409]mmm-yy;@",
                        "[$-409]mmmm-yy;@",
                        "[$-409]mmmmm;@",
                        "[$-409]mmmmm-yy;@"
                    ];

                    en_res.japanEmperorReignDateFormat = [
                        "[$-411]ge.m.d;@",
                        "[$-411]ggge'年'm'月'd'日';@"
                    ];

                    en_res.timeFormats = [
                        "[$-F400]h:mm:ss AM/PM",
                        "h:mm;@",
                        "[$-409]h:mm AM/PM;@",
                        "h:mm:ss;@",
                        "[$-409]h:mm:ss AM/PM;@",
                        "mm:ss.0;@",
                        "[h]:mm:ss;@",
                        "[$-409]m/d/yy h:mm AM/PM;@",
                        "m/d/yy h:mm;@"
                    ];

                    en_res.japanTimeFormats = [
                        "h:mm;@",
                        "[$-409]h:mm AM/PM;@",
                        "h:mm:ss;@",
                        "[$-409]h:mm:ss AM/PM;@",
                        "[$-409]yyyy/m/d h:mm AM/PM;@",
                        "yyyy/m/d h:mm;@",
                        "h'時'mm'分';@",
                        "h'時'mm'分'ss'秒';@"
                    ];

                    en_res.textFormats = [
                        "@"
                    ];

                    en_res.specialFormats = [
                        "00000",
                        "00000-0000",
                        "[<=9999999]###-####;(###) ###-####",
                        "000-00-0000"
                    ];

                    en_res.specialJapanFormats = [
                        "[<=999]000;[<=9999]000-00;000-0000",
                        "[<=99999999]####-####;(00) ####-####",
                        "'△' #,##0;'▲' #,##0",
                        "[DBNum1][$-411]General",
                        "[DBNum2][$-411]General",
                        "[DBNum3][$-411]0",
                        "[DBNum3][$-411]#,##0"
                    ];

                    en_res.currencyFormats = [
                        "#,##0",
                        "#,##0;[Red]#,##0",
                        "#,##0;-#,##0",
                        "#,##0;[Red]-#,##0"
                    ];

                    en_res.percentageFormats = [
                        "0%"
                    ];

                    en_res.scientificFormats = [
                        "0E+00"
                    ];

                    en_res.accountingFormats = [
                        "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)"
                    ];

                    en_res.customFormats = [
                        "General",
                        "0",
                        "0.00",
                        "#,##0",
                        "#,##0.00",
                        "#,##0;(#,##0)",
                        "#,##0;[Red](#,##0)",
                        "#,##0.00;(#,##0.00)",
                        "#,##0.00;[Red](#,##0.00)",
                        "$#,##0;($#,##0)",
                        "$#,##0;[Red]($#,##0)",
                        "$#,##0.00;($#,##0.00)",
                        "$#,##0.00;[Red]($#,##0.00)",
                        "0%",
                        "0.00%",
                        "0.00E+00",
                        "##0.0E+0",
                        "# ?/?",
                        "# ??/??",
                        "m/d/yyyy",
                        "d-mmm-yy",
                        "d-mmm",
                        "mmm-yy",
                        "h:mm AM/PM",
                        "h:mm:ss AM/PM",
                        "hh:mm",
                        "hh:mm:ss",
                        "m/d/yyyy hh:mm",
                        "mm:ss",
                        "mm:ss.0",
                        "@",
                        "[h]:mm:ss",
                        "$ #,##0;$ (#,##0);$ \"-\";@",
                        " #,##0; (#,##0); \"-\";@",
                        "$ #,##0.00;$ (#,##0.00);$ \"-\"??;@",
                        " #,##0.00; (#,##0.00); \"-\"??;@",
                        "hh:mm:ss",
                        "00000",
                        "# ???/???",
                        "000-00-0000",
                        "[$-4]dddd, mmmm dd, yyyy",
                        "m/d;@",
                        "[<=9999999]###-####;(###) ###-####",
                        "# ?/8"
                    ];

                    en_res.accountingSymbol = [
                        ["None", null, null],
                        ["$", "", "en-US"]
                    ];

                    en_res.specialType = [
                        "Zip Code",
                        "Zip Code + 4",
                        "Phone Number",
                        "Social Security Number"
                    ];

                    en_res.specialJapanType = [
                        "郵便番号",
                        "電話番号（東京)",
                        "正負記号 （+ = △; - = ▲)",
                        "漢数字（十二万三千四百）",
                        "大字 (壱拾弐萬参阡四百)",
                        "全角 (１２３４５)",
                        "全角 桁区切り（１２,３４５）"
                    ];

                    en_res.fractionType = [
                        "Up to one digit (1/4)",
                        "Up to two digits (21/25)",
                        "Up to three digits (312/943)",
                        "As halves (1/2)",
                        "As quarters (2/4)",
                        "As eighths (4/8)",
                        "As sixteenths (8/16)",
                        "As tenths (3/10)",
                        "As hundredths (30/100)"
                    ];

                    en_res.negativeNumbers = {
                        "-1234.10": "-1234.10",
                        "red:1234.10": "1234.10",
                        "(1234.10)": "(1234.10)",
                        "red:(1234.10)": "(1234.10)"
                    };

                    en_res.currencyNegativeNumbers = {
                        "number1": "-1,234.10",
                        "red:number2": "1,234.10",
                        "number3": "-1,234.10",
                        "red:number4": "-1,234.10"
                    };

                    en_res.passwordDialog = {
                        title: "Password",
                        passwordLabel: "Password:"
                    };
                    en_res.rowHeightDialog = {
                        title: "Row Height",
                        rowHeight: "Row height:",
                        exception: "The row height must be a double.",
                        exception2: "The row height must between 0 and 9999999."
                    };
                    en_res.columnWidthDialog = {
                        title: "Column Width",
                        columnWidth: "Column width:",
                        exception: "The column width must be a double.",
                        exception2: "The column width must between 0 and 9999999."
                    };
                    en_res.standardWidthDialog = {
                        title: "Standard Width",
                        columnWidth: "Standard column width:",
                        exception: "Your entry cannot be used. An integer or decimal number may be required."
                    };
                    en_res.standardHeightDialog = {
                        title: "Standard Height",
                        rowHeight: "Standard row height:",
                        exception: "Your entry cannot be used. An integer or decimal number may be required."
                    };
                    en_res.insertCellsDialog = {
                        title: "Insert",
                        shiftcellsright: "Shift cells right",
                        shiftcellsdown: "Shift cells down",
                        entirerow: "Entire row",
                        entirecolumn: "Entire column"
                    };
                    en_res.deleteCellsDialog = {
                        title: "Delete",
                        shiftcellsleft: "Shift cells left",
                        shiftcellsup: "Shift cells up",
                        entirerow: "Entire row",
                        entirecolumn: "Entire column"
                    };
                    en_res.groupDialog = {
                        title: "Group",
                        rows: "Rows",
                        columns: "Columns"
                    };
                    en_res.ungroupDialog = {
                        title: "Ungroup"
                    };
                    en_res.findDialog = {
                        title: "Find",
                        findwhat: "Find what:",
                        within: "Within:",
                        matchcase: "Match case",
                        search: "Search:",
                        matchexactly: "Match exactly",
                        lookin: "Look in:",
                        usewildcards: "Use wildcards",
                        option: "Option",
                        findall: "Find All",
                        findnext: "Find Next",
                        exception: "Spread cannot find the data you are searching for."
                    };
                    en_res.gotoDialog = {
                        title: "Goto",
                        goto: "Go to:",
                        reference: "Reference:",
                        exception: "The text you entered is not a valid reference or defined name.",
                        wrongName: "Executing operation failed."
                    };
                    en_res.nameManagerDialog = {
                        title: "Name Manager",
                        newName: "New...",
                        edit: "Edit...",
                        deleteName: "Delete",
                        filterWith: {
                            title: "Filter with:",
                            clearFilter: "Clear Filter",
                            nameScopedToWorkbook: "Name Scoped to Workbook",
                            nameScopedToWorksheet: "Name Scoped to Worksheet",
                            nameWithErrors: "Name With Errors",
                            nameWithoutErrors: "Name Without Errors"
                        },
                        nameColumn: "Name",
                        valueColumn: "Value",
                        refersToColumn: "Refers To",
                        scopeColumn: "Scope",
                        exception1: "The name you entered is not valid.",
                        exception2: "The name you entered already exists. Enter a unique name.",
                        deleteAlert: "Are you sure you want to delete the name {0}?"
                    };
                    en_res.newNameDialog = {
                        titleNew: "New Name",
                        titleEdit: "Edit Name",
                        name: "Name:",
                        scope: {
                            title: "Scope:",
                            workbook: "Workbook"
                        },
                        referTo: "Refer to:",
                        wrongName: "Executing operation failed."
                    };
                    en_res.insertFunctionDialog = {
                        title: "Insert Function",
                        functionCategory: "Function Category:",
                        functionList: "Function List:",
                        formula: "Formula:",
                        functionCategorys: "All,Database,Date and Time,Engineering,Financial,Information,Logical,Lookup and Reference,Math and Trigonometry,Statistical,Text"
                    };
                    en_res.buttonCellTypeDialog = {
                        title: "Button CellType",
                        marginGroup: "Margin:",
                        left: "Left:",
                        top: "Top:",
                        right: "Right:",
                        bottom: "Bottom:",
                        text: "Text:",
                        backcolor: "BackColor:",
                        other: "Other:"
                    };
                    en_res.checkBoxCellTypeDialog = {
                        title: "CheckBox CellType",
                        textGroup: "Text:",
                        "true": "True:",
                        indeterminate: "Indeterminate:",
                        "false": "False:",
                        align: "Align:",
                        other: "Other:",
                        caption: "Caption:",
                        isThreeState: "IsThreeState",
                        checkBoxTextAlign: {
                            top: "Top",
                            bottom: "Bottom",
                            left: "Left",
                            right: "Right"
                        }
                    };
                    en_res.comboBoxCellTypeDialog = {
                        title: "ComboBox CellType",
                        editorValueTypes: "EditorValueType:",
                        items: "Items:",
                        itemProperties: "ItemProperties:",
                        text: "Text:",
                        value: "Value:",
                        add: "Add",
                        remove: "Remove",
                        editorValueType: {
                            text: "Text",
                            index: "Index",
                            value: "Value"
                        },
                        editable: "Editable",
                        itemHeight: "Items Height"
                    };
                    en_res.hyperLinkCellTypeDialog = {
                        title: "HyperLink CellType",
                        link: "Link:",
                        visitedlink: "VisitedLink:",
                        text: "Text:",
                        linktooltip: "LinkToolTip:",
                        color: "Color:",
                        other: "Other:"
                    };
                    en_res.headerCellsDialog = {
                        title: "Header Cells",
                        rowHeader: "Row Header",
                        columnHeader: "Column Header",
                        backColor: "BackColor",
                        borderBottom: "BorderBottom",
                        borderLeft: "BorderLeft",
                        borderRight: "BorderRight",
                        borderTop: "BorderTop",
                        font: "Font",
                        foreColor: "ForeColor",
                        formatter: "Formatter",
                        hAlign: "HAlign",
                        height: "Height",
                        locked: "Locked",
                        resizable: "Resizable",
                        shrinkToFit: "ShrinkToFit",
                        value: "Value",
                        textIndent: "TextIndent",
                        vAlign: "VAlign",
                        visible: "Visible",
                        width: "Width",
                        wordWrap: "WordWrap",
                        popUp: "...",
                        merge: "Merge",
                        unmerge: "Unmerge",
                        insertRows: "Insert Rows",
                        addRows: "Add Rows",
                        deleteRows: "Delete Rows",
                        insertColumns: "Insert Columns",
                        addColumns: "Add Columns",
                        deleteColumns: "Delete Columns",
                        clear: "Clear",
                        top: 'Top',
                        bottom: 'Bottom',
                        left: 'Left',
                        right: 'Right',
                        center: 'Center',
                        general: 'General',
                        exception: "The setting is invalid. Please check the red parts."
                    };
                    en_res.fontPickerDialog = {
                        title: "Font"
                    };
                    en_res.fillDialog = {
                        title: "Series"
                    };

                    en_res.zoomDialog = {
                        title: "Zoom",
                        double: "200%",
                        normal: "100%",
                        threeFourths: "75%",
                        half: "50%",
                        quarter: "25%",
                        fitSelection: "Fit selection",
                        custom: "Custom:",
                        exception: "Your entry cannot be used. An integer or decimal number may be required.",
                        magnification: "Magnification",
                        percent: "%"
                    };
                    en_res.contextMenu = {
                        cut: "Cut",
                        copy: "Copy",
                        paste: "Paste Options:",
                        insertDialog: "Insert...",
                        deleteDialog: "Delete...",
                        clearcontents: "Clear Contents",
                        filter: "Filter",
                        sort: "Sort",
                        sortAToZ: "Sort A to Z",
                        sortZToA: "sort Z to A",
                        customSort: "Custom Sort...",
                        formatCells: "Format Cells...",
                        defineName: "Define Name...",
                        rowHeight: "Row Height...",
                        columnWidth: "Column Width...",
                        hide: "Hide",
                        unhide: "Unhide",
                        headers: "Headers...",
                        insert: "Insert",
                        "delete": "Delete",
                        protectsheet: "Protect Sheet...",
                        unprotectsheet: "Unprotect Sheet...",
                        comments: "A workbook must contain at least one visible worksheet.",
                        insertComment: "Insert Comment",
                        editComment: "Edit Comment",
                        deleteComment: "Delete Comment",
                        hideComment: "Hide Comment",
                        editText: "Edit Text",
                        exitEditText: "Exit Edit Text",
                        formatComment: "Format Comment",
                        unHideComment: "Show/Hide Comments",
                        sheetTabColor: "Tab Color",
                        remove: "Remove",
                        slicerProperty: "Size and Properties...",
                        slicerSetting: "Slicer Settings..."
                    };
                    en_res.borderPicker = {
                        lineStyleTitle: "Line:",
                        borderColorTitle: "Color:",
                        none: "None"
                    };
                    en_res.borderDialog = {
                        border: "Border",
                        presets: "Presets",
                        none: "None",
                        outline: "Outline",
                        inside: "Inside",
                        line: "Line",
                        text: "Text",
                        comments: "The selected border style can be applied by clicking the presets, preview diagram, or the buttons above."
                    };

                    en_res.conditionalFormat = {
                        highlightCellsRules: "Highlight Cells Rules",
                        topBottomRules: "Top/Bottom Rules",
                        dataBars: "Data Bars",
                        colorScales: "Color Scales",
                        iconSets: "Icon Sets",
                        newRule: "New Rule...",
                        clearRules: "Clear Rules...",
                        manageRules: "Manage Rules...",
                        greaterThan: "Greater Than...",
                        lessThan: "Less Than...",
                        between: "Between...",
                        equalTo: "Equal To...",
                        textThatContains: "Text that Contains...",
                        aDateOccurring: "A Date Occurring...",
                        duplicateValues: "Duplicate Values...",
                        moreRules: "More Rules...",
                        top10Items: "Top 10 Items...",
                        bottom10Items: "Bottom 10 Items...",
                        aboveAverage: "Above Average...",
                        belowAverage: "Below Average...",
                        gradientFill: "Gradient Fill",
                        solidFill: "Solid Fill",
                        directional: "Directional",
                        shapes: "Shapes",
                        indicators: "Indicators",
                        ratings: "Ratings",
                        clearRulesFromSelectedCells: "Clear Rules from Selected Cells",
                        clearRulesFromEntireSheet: "Clear Rules from Entire Sheet"
                    };

                    en_res.fileMenu = {
                        _new: "New",
                        open: "Open",
                        save: "Save",
                        saveAs: "Save As",
                        _export: "Export",
                        _import: "Import",
                        exit: "Exit",
                        recentWorkbooks: "Recent Workbooks",
                        computer: "Computer",
                        currentFolder: "Current Folder",
                        recentFolders: "Recent Folders",
                        browse: "Browse",
                        spreadSheetJsonFile: "SpreadSheet File (JSON)",
                        excelFile: "Excel File",
                        csvFile: "CSV File",
                        pdfFile: "PDF File",
                        importSpreadSheetFile: "Import SSJSON File",
                        importExcelFile: "Import Excel File",
                        importCsvFile: "Import CSV File",
                        exportSpreadSheetFile: "Export SSJSON File",
                        exportExcelFile: "Export Excel File",
                        exportCsvFile: "Export CSV File",
                        exportPdfFile: "Export PDF File",
                        exportJSFile: "Export Javascript File",
                        openFlags: "Open Flags",
                        importDataOnly: "Data only",
                        importDataAndFormulasOnly: "Data and formulas only",
                        importDoNotRecalculateAfterLoad: "Do not auto-calculate formulas after importing",
                        importRowAndColumnHeaders: "Import both frozen columns and rows as headers",
                        importRowHeaders: "Import frozen rows as column headers",
                        importColumnHeaders: "Import frozen columns as row headers",
                        importPassword: "Password",
                        importIncludeRowHeader: "Import row header",
                        importIncludeColumnHeader: "Import column header",
                        importUnformatted: "Leave the data unformatted",
                        importImportFormula: "Import cell formulas",
                        importRowDelimiter: "Row Delimiter",
                        importColumnDelimiter: "Column Delimiter",
                        importCellDelimiter: "Cell Delimiter",
                        importEncoding: "File Encoding",
                        saveFlags: "Save Flags",
                        exportDataOnly: "Data only",
                        exportNoFormulas: "Do not export formulas",
                        exportAutoRowHeight: "Autofit row height",
                        exportSaveAsFiltered: "Export as filtered",
                        exportSaveAsViewed: "Export as viewed",
                        exportSaveBothCustomRowAndColumnHeaders: "Export row header as Excel frozen columns and column header as Excel frozen rows",
                        exportSaveCustomRowHeaders: "Export row header as Excel frozen columns",
                        exportSaveCustomColumnHeaders: "Export column header as Excel frozen rows",
                        exportPassword: "Password",
                        exportExcel97_2003File: "Export Excel 97-2003 File",
                        exportIncludeRowHeader: "Include row headers",
                        exportIncludeColumnHeader: "Include column headers",
                        exportUnFormatted: "Do not include any style information",
                        exportFormula: "Include formulas",
                        exportAsViewed: "Export as viewed",
                        exportSheetIndex: "Sheet Index",
                        exportEncoding: "Encoding",
                        exportRow: "Row Index",
                        exportColumn: "Column Index",
                        exportRowCount: "Row Count",
                        exportColumnCount: "Column Count",
                        exportRowDelimiter: "Row Delimiter",
                        exportColumnDelimiter: "Column Delimiter",
                        exportCellDelimiter: "Cell Delimiter",
                        exportVisibleRowCol: "Only include visible rows and columns",
                        pdfBasicSetting: "Basic Settings",
                        pdfTitle: "Title:",
                        pdfAuthor: "Author:",
                        pdfApplication: "Application:",
                        pdfSubject: "Subject:",
                        pdfKeyWords: "Key words:",
                        pdfExportSetting: "Export Settings",
                        exportSheetLabel: "Choose the sheet to export:",
                        allSheet: "All",
                        pdfDisplaySetting: "Display Settings",
                        centerWindowLabel: "Center window",
                        showTitleLabel: "Show title",
                        showToolBarLabel: "Show toolbar",
                        fitWindowLabel: "Fit window",
                        showMenuBarLabel: "Show menu bar",
                        showWindowUILabel: "Show window UI",
                        destinationTypeLabel: "Destination type:",
                        destinationType: {
                            autoDestination: "Auto",
                            fitPageDestination: "FitPage",
                            fitWidthDestination: "FitWidth",
                            fitHeightDestination: "FitHeight",
                            fitBoxDestination: "FitBox"
                        },
                        openTypeLabel: "Open type:",
                        openType: {
                            autoOpen: "Auto",
                            useNoneOpen: "UseNone",
                            useOutlinesOpen: "UseOutlines",
                            useThumbsOpen: "UseThumbs",
                            fullScreenOpen: "FullScreen",
                            useOCOpen: "UseOC",
                            useAttachmentsOpen: "UseAttachments"
                        },
                        pdfPageSetting: "Page Settings",
                        openPageNumberLabel: "Open page number:",
                        pageLayoutLabel: "Page layout:",
                        pageLayout: {
                            autoLayout: "Auto",
                            singlePageLayout: "SinglePage",
                            oneColumnLayout: "OneColumn",
                            twoColumnLeftLayout: "TwoColumnLeft",
                            twoColumnRightLayout: "TwoColumnRight",
                            twoPageLeftLayout: "TwoPageLeft",
                            twoPageRight: "TwoPageRight"
                        },
                        pageDurationLabel: "Page duration:",
                        pageTransitionLabel: "Page transition:",
                        pageTransition: {
                            defaultTransition: "Default",
                            splitTransition: "Split",
                            blindsTransition: "Blinds",
                            boxTransition: "Box",
                            wipeTransition: "Wipe",
                            dissolveTransition: "Dissolve",
                            glitterTransition: "Glitter",
                            flyTransition: "Fly",
                            pushTransition: "Push",
                            coverTransition: "Cover",
                            uncoverTransition: "Uncover",
                            fadeTransition: "Fade"
                        },
                        printerSetting: "Printer Setting...",
                        printerSettingDialogTitle: "Printer Setting",
                        copiesLabel: "Copies:",
                        scalingTypeLabel: "Scaling Type:",
                        scalingType: {
                            appDefaultScaling: "AppDefault",
                            noneScaling: "None"
                        },
                        duplexModeLabel: "Duplex Mode:",
                        duplexMode: {
                            defaultDuplex: "Default",
                            simplexDuplex: "Simplex",
                            duplexFlipShortEdge: "DuplexFlipShortEdge",
                            duplexFlipLongEdge: "DuplexFlipLongEdge"
                        },
                        choosePaperSource: "Choose page source by pdf page size",
                        printRanges: "Print ranges",
                        indexLabel: "Index",
                        countLabel: "Count",
                        addRange: "Add",
                        removeRange: "Remove",
                        addRangeException: "Invalid value, the index must be greater than or equal to 0 and the count must be greater than 0.",
                        noRecentWorkbooks: "No recent workbooks. Please open a workbook first.",
                        noRecentFolders: "No recent folders. Please open a workbook first."
                    };

                    en_res.formatTableStyle = {
                        name: "Name:",
                        tableElement: "Table Element:",
                        preview: "Preview",
                        format: "Format",
                        tableStyle: "Table Style",
                        clear: "Clear",
                        stripeSize: "Stripe Size",
                        light: "Light",
                        medium: "Mediumn",
                        dark: "Dark",
                        newTableStyle: "New Table Style...",
                        custom: "Custom",
                        exception: "This style name is already exists.",
                        title: "SpreadJS Designer"
                    };
                    en_res.tableElement = {
                        wholeTableStyle: "Whole Table",
                        firstColumnStripStyle: "First Column Strip",
                        secondColumnStripStyle: "Second Column Strip",
                        firstRowStripStyle: "First Row Strip",
                        secondRowStripStyle: "Second Row Strip",
                        highlightLastColumnStyle: "Last Column",
                        highlightFirstColumnStyle: "First Column",
                        headerRowStyle: "Header Row",
                        footerRowStyle: "Total Row",
                        firstHeaderCellStyle: "First Header Cell",
                        lastHeaderCellStyle: "Last Header Cell",
                        firstFooterCellStyle: "First Footer Cell",
                        lastFooterCellStyle: "Last Footer Cell"
                    };
                    en_res.conditionalFormatting = {
                        common: {
                            'with': "with",
                            selectedRangeWith: "for the selected range with",
                            and: "and"
                        },
                        greaterThan: {
                            title: "Greater Than",
                            description: "Format cells that are GREATER THAN:"
                        },
                        lessThan: {
                            title: "Less Than",
                            description: "Format cells that are LESS THAN:"
                        },
                        between: {
                            title: "Between",
                            description: "Format cells that are BETWEEN:"
                        },
                        equalTo: {
                            title: "Equal To",
                            description: "Format cells that are EQUAL TO:"
                        },
                        textThatCotains: {
                            title: "Text That Contains",
                            description: "Format cells that contain the text:"
                        },
                        dateOccurringFormat: {
                            title: "A Date Occurring",
                            description: "Format cells that contain a date occurring:",
                            date: {
                                yesterday: "Yesterday",
                                today: "Today",
                                tomorrow: "Tomorrow",
                                last7days: "In the last 7 days",
                                lastweek: "Last week",
                                thisweek: "This week",
                                nextweek: "Next week",
                                lastmonth: "Last month",
                                thismonth: "This month",
                                nextmonth: "Next month"
                            }
                        },
                        duplicateValuesFormat: {
                            title: "Duplicate Values",
                            description: "Format cells that contain:",
                            type: {
                                duplicate: "Duplicate",
                                unique: "Unique"
                            },
                            valueswith: "values with"
                        },
                        top10items: {
                            title: "Top 10 Items",
                            description: "Format cells that rank in the TOP:"
                        },
                        bottom10items: {
                            title: "Bottom 10 Items",
                            description: "Format cells that rank in the BOTTOM:"
                        },
                        aboveAverage: {
                            title: "Above Average",
                            description: "Format cells that are ABOVE AVERAGE:"
                        },
                        belowAverage: {
                            title: "Below Average",
                            description: "Format cells that are BELOW AVERAGE:"
                        },
                        newFormattingRule: {
                            title: "New Formatting Rule",
                            title2: "Edit Formatting Rule",
                            description1: "Select a Rule Type:",
                            description2: "Edit the Rule Description:",
                            ruleType: {
                                formatOnValue: "►Format all cells based on their values",
                                formatContain: "►Format only cells that contain",
                                formatRankedValue: "►Format only top or bottom ranked values",
                                formatAbove: "►Format only values that are above or below average",
                                formatUnique: "►Format only unique or duplicate values",
                                useFormula: "►Use a formula to determine which cells to format"
                            },
                            formatOnValue: {
                                description: "Format all cells based on their values:",
                                formatStyle: "Format style:",
                                formatStyleSelector: {
                                    color2: "2-Color Scale",
                                    color3: "3-Color Scale",
                                    dataBar: "Data Bar",
                                    iconSets: "Icon Sets"
                                },
                                color2: {
                                    min: "Minimum",
                                    max: "Maximum",
                                    type: "Type:",
                                    value: "Value:",
                                    color: "Color:",
                                    preview: "Preview",
                                    minSelector: {
                                        lowest: "Lowest Value"
                                    },
                                    maxSelector: {
                                        highest: "Highest Value"
                                    }
                                },
                                color3: {
                                    mid: "MidPoint"
                                },
                                dataBar: {
                                    showBarOnly: "Show Bar Only",
                                    auto: "Automatic",
                                    description2: "Bar Appearance:",
                                    fill: "Fill",
                                    color: "Color",
                                    border: "Border",
                                    fillSelector: {
                                        solidFill: "Solid Fill",
                                        gradientFill: "Gradient Fill"
                                    },
                                    borderSelector: {
                                        noBorder: "No Border",
                                        solidBorder: "Solid Border"
                                    },
                                    negativeBtn: "Negative value and Axis...",
                                    barDirection: "Bar Direction:",
                                    barDirectionSelector: {
                                        l2r: "Left-to-Right",
                                        r2l: "Right-to-left"
                                    },
                                    preview: "Preview",
                                    negativeDialog: {
                                        title: "Negative Value and Axis Settings",
                                        group1: {
                                            title: "Negative bar fill color",
                                            fillColor: "Fill Color:",
                                            apply: "Apply same fill color as positive bar"
                                        },
                                        group2: {
                                            title: "Negative bar border color",
                                            borderColor: "Border Color:",
                                            apply: "Apply same fill color as positive bar"
                                        },
                                        group3: {
                                            title: "Axis settings",
                                            description: "Select axis position in cell to change the appearance of bars for negative values",
                                            radio: {
                                                auto: "Automatic(display at variable position based on negative values)",
                                                cell: "Cell midpoint",
                                                none: "None(show negative value bars in same direction as positive)"
                                            },
                                            axisColor: "Axis color:"
                                        }
                                    }
                                },
                                iconSets: {
                                    iconStyle: "Icon style:",
                                    showIconOnly: "Show Icon Only",
                                    reverseIconOrder: "Reverse Icon Order",
                                    display: "Display each icon according to these rules:",
                                    icon: "Icon",
                                    value: "Value",
                                    type: "Type",
                                    description1: "when value is",
                                    description2: "when < ",
                                    operator: {
                                        largeOrEqu: "> =",
                                        large: ">"
                                    }
                                },
                                commonSelector: {
                                    num: "Number",
                                    percent: "Percent",
                                    formula: "Formula",
                                    percentile: "Percentile"
                                }
                            },
                            formatContain: {
                                description: "Format only cells with:",
                                type: {
                                    cellValue: "Cell Value",
                                    specificText: "Specific Text",
                                    dateOccurring: "Dates Occurring",
                                    blanks: "Blanks",
                                    noBlanks: "No Blanks",
                                    errors: "Errors",
                                    noErrors: "No Errors"
                                },
                                operator_cellValue: {
                                    between: "between",
                                    notBetween: "not between",
                                    equalTo: "equal to",
                                    notEqualTo: "not equal to",
                                    greaterThan: "greater than",
                                    lessThan: "less than",
                                    greaterThanOrEqu: "greater than or equal to",
                                    lessThanOrEqu: "less than or equal to"
                                },
                                operator_specificText: {
                                    containing: "containing",
                                    notContaining: "not Containing",
                                    beginningWith: "beginning with",
                                    endingWith: "ending with"
                                }
                            },
                            formatRankedValue: {
                                description: "Format cells that rank in the:",
                                type: {
                                    top: "Top",
                                    bottom: "Bottom"
                                }
                            },
                            formatAbove: {
                                description: "Format cells that are:",
                                type: {
                                    above: "above",
                                    below: "below",
                                    equalOrAbove: "equal or above",
                                    equalOrBelow: "equal or below",
                                    std1Above: "1 std dev above",
                                    std1Below: "1 std dev below",
                                    std2Above: "2 std dev above",
                                    std2Below: "2 std dev below",
                                    std3Above: "3 std dev above",
                                    std3Below: "3 std dev below"
                                },
                                description2: "the average for the selected range"
                            },
                            formatUnique: {
                                description: "Format all:",
                                type: {
                                    duplicate: "duplicate",
                                    unique: "unique"
                                },
                                description2: "values in the selected range"
                            },
                            useFormula: {
                                description: "Format values where this formula is true:"
                            },
                            preview: {
                                description: "Preview:",
                                buttonText: "Format...",
                                noFormat: "No Format Set",
                                hasFormat: "AaBbCcYyZz"
                            }
                        },
                        withStyle: {
                            lightRedFill_DarkRedText: "Light Red Fill with Dark Red Text",
                            yellowFill_DrakYellowText: "Yellow Fill with Dark Yellow Text",
                            greenFill_DarkGreenText: "Green Fill with Dark Green Text",
                            lightRedFill: "Light Red Fill",
                            redText: "Red Text",
                            redBorder: "Red Border",
                            customFormat: "Custom Format..."
                        },
                        exceptions: {
                            e1: "The value you entered is not a valid number, date, time, or string.",
                            e2: "Enter a value.",
                            e3: "Enter a whole number between 1 and 1000.",
                            e4: "The value you entered cannot be empty.",
                            e5: "The type of reference cannot be used in a Conditional Formatting formula.\nChange the reference to a single cell, or use the reference with a worksheet function, such as = SUM(A1:E5).",
                            e6: "The source range of a formula rule can only be a single range!"
                        }
                    };

                    en_res.formattingRulesManagerDialog = {
                        title: "Conditional Formatting Rules Manager",
                        rulesScopeLabel: "Formatting rules for this worksheet: ",
                        rulesScopeForSelection: "Current Selection",
                        rulesScopeForWorksheet: "This Worksheet",
                        newRule: "New Rule...",
                        editRule: "Edit Rule...",
                        deleteRule: "Delete Rule...",
                        gridTitleRule: "Rule (applied in order shown)",
                        gridTitleFormat: "Format",
                        gridTitleAppliesTo: "Applies to",
                        gridTitleStopIfTrue: "Stop If True",
                        ruleDescriptions: {
                            valueBetween: 'Cell value between {0} and {1}',
                            valueNotBetween: 'Cell value no between {0} and {1}',
                            valueEquals: 'Cell value = {0}',
                            valueNotEquals: 'Cell value <> {0}',
                            valueGreateThan: 'Cell value > {0}',
                            valueGreateThanOrEquals: 'Cell value >= {0}',
                            valueLessThan: 'Cell value < {0}',
                            valueLessThanOrEquals: 'Cell value <= {0}',
                            valueContains: 'Cell value contains "{0}"',
                            valueNotContains: 'Cell value does not contains "{0}"',
                            valueBeginsWith: 'Cell value begins with "{0}"',
                            valueEndsWith: 'Cell value ends with "{0}"',
                            last7Days: 'Last 7 days',
                            lastMonth: 'Last Month',
                            lastWeek: 'Last week',
                            nextMonth: 'Next month',
                            nextWeek: 'Next week',
                            thisMonth: 'This month',
                            thisWeek: 'This week',
                            today: 'Today',
                            tomorrow: 'Tomorrow',
                            yesterday: 'Yesterday',
                            duplicateValues: 'Duplicate Values',
                            uniqueValues: 'Unique Values',
                            top: 'Top {0}',
                            bottom: 'Bottom {0}',
                            above: 'Above Average',
                            above1StdDev: '1 std dev above Average',
                            above2StdDev: '2 std dev above Average',
                            above3StdDev: '3 std dev above Average',
                            below: 'Below Average',
                            below1StdDev: '1 std dev below Average',
                            below2StdDev: '2 std dev below Average',
                            below3StdDev: '3 std dev below Average',
                            equalOrAbove: 'Equals to or above Average',
                            equalOrBelow: 'Equals to or below Average',
                            dataBar: 'Data Bar',
                            twoScale: 'Graded Color Scale',
                            threeScale: 'Graded Color Scale',
                            iconSet: 'Icon Set',
                            formula: 'Formula: {0}'
                        },
                        previewText: 'AaBbCcYyZz'
                    };

                    en_res.cellStylesDialog = {
                        cellStyles: "Cell Styles",
                        custom: "Custom",
                        goodBadAndNeutral: "Good, Bad and Neutral",
                        dataAndModel: "Data And Model",
                        titlesAndHeadings: "Titles and Headings",
                        themedCellStyle: "Themed Cell Style",
                        numberFormat: "Number Format",
                        goodBadAndNeutralContent: {
                            "Normal": "Normal",
                            "Bad": "Bad",
                            "Good": "Good",
                            "Neutral": "Neutral"
                        },
                        dataAndModelContent: {
                            "Calculation": "Calculation",
                            "Check Cell": "Check Cell",
                            "Explanatory Text": "Explanatory...",
                            "Input": "Input",
                            "Linked Cell": "Linked Cell",
                            "Note": "Note",
                            "Output": "Output",
                            "Warning Text": "Warning Text"
                        },
                        titlesAndHeadingsContent: {
                            "Heading 1": "Heading 1",
                            "Heading 2": "Heading 2",
                            "Heading 3": "Heading 3",
                            "Heading 4": "Heading 4",
                            "Title": "Title",
                            "Total": "Total"
                        },
                        themedCellStyleContent: {
                            "20% - Accent1": "20% - Accent1",
                            "20% - Accent2": "20% - Accent2",
                            "20% - Accent3": "20% - Accent3",
                            "20% - Accent4": "20% - Accent4",
                            "20% - Accent5": "20% - Accent5",
                            "20% - Accent6": "20% - Accent6",
                            "40% - Accent1": "40% - Accent1",
                            "40% - Accent2": "40% - Accent2",
                            "40% - Accent3": "40% - Accent3",
                            "40% - Accent4": "40% - Accent4",
                            "40% - Accent5": "40% - Accent5",
                            "40% - Accent6": "40% - Accent6",
                            "60% - Accent1": "60% - Accent1",
                            "60% - Accent2": "60% - Accent2",
                            "60% - Accent3": "60% - Accent3",
                            "60% - Accent4": "60% - Accent4",
                            "60% - Accent5": "60% - Accent5",
                            "60% - Accent6": "60% - Accent6",
                            "Accent1": "Accent1",
                            "Accent2": "Accent2",
                            "Accent3": "Accent3",
                            "Accent4": "Accent4",
                            "Accent5": "Accent5",
                            "Accent6": "Accent6"
                        },
                        numberFormatContent: {
                            "Comma": "Comma",
                            "Comma [0]": "Comma [0]",
                            "Currency": "Currency",
                            "Currency [0]": "Currency [0]",
                            "Percent": "Percent"
                        },
                        newCellStyle: "New Cell Style..."
                    };

                    en_res.newCellStyleDialog = {
                        style: "Style",
                        styleName: "Style name:",
                        defaultStyleName: "Style 1",
                        format: "Format...",
                        message: "This style name already exists"
                    };

                    en_res.cellStyleContextMenu = {
                        "delete": "Delete",
                        modify: "Modify"
                    };

                    en_res.insertPictureDialogTitle = "Insert Picture";
                    en_res.pictureFormatFilter = {
                        jpeg: "JPEG File Interchange Format(*.jpg;*.jpeg)",
                        png: "Portable Network Graphics(*.png)",
                        bmp: "Windows Bitmap(*.bmp)",
                        allFiles: "All Files(*.*)"
                    };

                    en_res.ribbon = {
                        accessBar: {
                            undo: "Undo",
                            redo: "Redo",
                            save: "Save",
                            New: "New",
                            open: "Open",
                            active: "Active"
                        },
                        home: {
                            file: "FILE",
                            home: "HOME",
                            clipboard: "Clipboard",
                            fonts: "Fonts",
                            alignment: "Alignment",
                            numbers: "Numbers",
                            cellType: "CellType",
                            styles: "Styles",
                            cells: "Cells",
                            editing: "Editing",
                            paste: "Paste",
                            all: "All",
                            formulas: "Formulas",
                            values: "Values",
                            formatting: "Formatting",
                            cut: "Cut",
                            copy: "Copy",
                            fontFamily: "Font Family",
                            fontSize: "Font Size",
                            increaseFontSize: "Increase Font Size",
                            decreaseFontSize: "Decrease Font Size",
                            bold: "Bold",
                            italic: "Italic",
                            underline: "Underline",
                            border: "Border",
                            bottomBorder: "Bottom Border",
                            topBorder: "Top Border",
                            leftBorder: "Left Border",
                            rightBorder: "Right Border",
                            noBorder: "No Border",
                            allBorder: "All Border",
                            outsideBorder: "Outside Border",
                            thickBoxBorder: "Thick Box Border",
                            bottomDoubleBorder: "Bottom Double Border",
                            thickBottomBorder: "Thick Bottom Border",
                            topBottomBorder: "Top and Bottom Border",
                            topThickBottomBorder: "Top and Thick Bottom Border",
                            topDoubleBottomBorder: "Top and Double Bottom Border",
                            moreBorders: "More Borders...",
                            backColor: "Background Color",
                            fontColor: "Font Color",
                            topAlign: "Top Align",
                            middleAlign: "Middle Align",
                            bottomAlign: "Bottom Align",
                            leftAlign: "Left Align",
                            centerAlign: "Center Align",
                            rightAlign: "Right Align",
                            increaseIndent: "Increase Indent",
                            decreaseIndent: "Decrease Indent",
                            wrapText: "Wrap Text",
                            mergeCenter: "Merge & Center",
                            mergeAcross: "Merge Across",
                            mergeCells: "Merge Cells",
                            unMergeCells: "Unmerge Cells",
                            numberFormat: "Number Format",
                            general: "General",
                            Number: "Number",
                            currency: "Currency",
                            accounting: "Accounting",
                            shortDate: "Short Date",
                            longDate: "Long Date",
                            time: "Time",
                            percentage: "Percentage",
                            fraction: "Fraction",
                            scientific: "Scientific",
                            text: "Text",
                            moreNumberFormat: "More Number Formats...",
                            percentStyle: "Percent Style",
                            commaStyle: "CommaStyle",
                            increaseDecimal: "Increase Decimal",
                            decreaseDecimal: "Decrease Decimal",
                            buttonCellType: "Button CellType",
                            checkboxCellType: "CheckBox CellType",
                            comboBoxCellType: "ComboBox CellType",
                            hyperlinkCellType: "HyperLink CellType",
                            clearCellType: "Clear CellType",
                            clearCellType1: "Clear CellType",
                            conditionFormat: "Condition Format",
                            conditionFormat1: "Condition Format",
                            formatTable: "Format Table",
                            formatTable1: "Format Table",
                            insert: "Insert",
                            insertCells: "Insert Cells...",
                            insertSheetRows: "Insert Sheet Rows",
                            insertSheetColumns: "Insert Sheet Columns",
                            insertSheet: "Insert Sheet",
                            Delete: "Delete",
                            deleteCells: "Delete Cells...",
                            deleteSheetRows: "Delete Sheet Rows",
                            deleteSheetColumns: "Delete Sheet Columns",
                            deleteSheet: "Delete Sheet",
                            format: "Format",
                            rowHeight: "Row Height...",
                            autofitRowHeight: "AutoFit Row Height",
                            defaultHeight: "Default Height...",
                            columnWidth: "Column Width...",
                            autofitColumnWidth: "AutoFit Column Width",
                            defaultWidth: "Default Width...",
                            hideRows: "Hide Rows",
                            hideColumns: "Hide Columns",
                            unHideRows: "Unhide Rows",
                            unHideColumns: "Unhide Columns",
                            protectSheet: "Protect Sheet...",
                            unProtectSheet: "Unprotect Sheet...",
                            lockCells: "Lock Cells",
                            unLockCells: "Unlock Cells",
                            autoSum: "Auto Sum",
                            sum: "Sum",
                            average: "Average",
                            countNumbers: "Count Numbers",
                            max: "Max",
                            min: "Min",
                            fill: "Fill",
                            down: "Down",
                            right: "Right",
                            up: "Up",
                            left: "Left",
                            series: "Series...",
                            clear: "Clear",
                            clearAll: "Clear All",
                            clearFormat: "Clear Format",
                            clearContent: "Clear Content",
                            clearComments: "Clear Comments",
                            sortFilter: "Sort & Filter",
                            sortFilter1: "Sort & Filter",
                            sortAtoZ: "Sort A to Z",
                            sortZtoA: "Sort Z to A",
                            customSort: "Custom Sort...",
                            filter: "Filter",
                            clearFilter: "Clear Filter",
                            reapply: "Reapply",
                            find: "Find",
                            find1: "Find...",
                            goto: "Go to..."
                        },
                        insert: {
                            insert: "INSERT",
                            table: "Table",
                            sparklines: "Sparklines",
                            line: "Line",
                            column: "Column",
                            winloss: "Win/Loss",
                            insertTable: "Insert Table",
                            insertPicture: "Insert Picture",
                            insertLineSparkline: "Insert Line Sparkline",
                            insertColumnSparkline: "Insert Column Sparkline",
                            insertWinlossSparkline: "Insert Win/Loss Sparkline",
                            picture: "Picture",
                            illustrations: "Illustr..",
                            insertPieSparkline: "Insert Pie Sparkline",
                            insertAreaSparkline: "Insert Area Sparkline",
                            insertScatterSparkline: "Insert Scatter Sparkline",
                            pie: "Pie",
                            area: "Area",
                            scatter: "Scatter",
                            insertBulletSparkline: "Insert Bullet Sparkline",
                            bullet: "Bullet",
                            insertSpreadSparkline: "Insert Spread Sparkline",
                            spread: "Spread",
                            insertStackedSparkline: "Insert Stacked Sparkline",
                            stacked: "Stacked",
                            insertHbarSparkline: "Insert Hbar Sparkline",
                            hbar: "Hbar",
                            insertVbarSparkline: "Insert Vbar Sparkline",
                            vbar: "Vbar",
                            insertVariSparkline: "Insert Variance Sparkline",
                            variance: "Variance",
                            insertBoxPlotSparkline: "Insert BoxPlot Sparkline",
                            boxplot: "BoxPlot",
                            insertCascadeSparkline: "Insert Cascade Sparkline",
                            cascade: "Cascade",
                            insertParetoSparkline: "Insert Pareto Sparkline",
                            pareto: "Pareto"
                        },
                        formulas: {
                            formulas: "FORMULAS",
                            insertFunction: "Insert Function",
                            insertFunction1: "Insert Function",
                            functions: "Functions",
                            names: "Names",
                            nameManager: "Name Manager",
                            nameManager1: "Name Manager"
                        },
                        data: {
                            data: "DATA",
                            sortFilter: "Sort & Filter",
                            dataTools: "Data Tools",
                            outline: "Outline",
                            sortAtoZ: "Sort A to Z",
                            sortZtoA: "Sort Z to A",
                            sort: "Sort",
                            customSort: "Custom Sort...",
                            filter: "Filter",
                            clear: "Clear",
                            clearFilter: "Clear Filter",
                            reapply: "Reapply",
                            dataValidation: "Data Validation",
                            dataValidation1: "Data Validation",
                            circleInvalidData: "Circle Invalid Data",
                            clearInvalidCircles: "Clear Invalid Circles",
                            group: "Group",
                            unGroup: "Ungroup",
                            showDetail: "Show Detail",
                            hideDetail: "Hide Detail",
                            designMode: "Design Mode",
                            enterTemplate: "Enter Template Design Mode",
                            template: "Template",
                            bindingPath: "BindingPath",
                            loadSchemaTitle: "Load schema to get a tree view",
                            loadSchema: "Load Schema",
                            loadDataSourceFilter: {
                                json: "JSON File(*.json)",
                                txt: "Normal text file(*.txt)"
                            },
                            saveDataSourceFilter: {
                                json: "JSON File(*.json)"
                            },
                            saveSchemaTitle: "Save schema into a json file",
                            saveSchema: "Save Schema",
                            autoGenerateColumns: "AutoGenerateColumns",
                            columns: "Columns",
                            name: "Name",
                            details: "Details",
                            ok: "Ok",
                            cancel: "Cancel",
                            loadDataError: "Please load a json file.",
                            addNode: "add node",
                            remove: "remove",
                            rename: "rename",
                            table: "Table",
                            selectOptions: "select options",
                            clearBindingPath: "Clear BindingPath",
                            dataField: "DataField",
                            warningTable: "The column count of the table will change. Do you want to continue?",
                            warningDataField: "Would you like to change the \"autoGenerateColumns\" to 'false' and set the datafield anyway?",
                            checkbox: "CheckBox",
                            hyperlink: "HyperLink",
                            combox: "Combox",
                            button: "Button",
                            text: "Text",
                            autoGenerateLabel: "AutoGenerateLabel",
                            autoGenerateLabelTip: "Automatically Generate Data Label",
                            unallowableTableBindingTip: "DataField only can be set on table. Please select a table.",
                            overwriteCellTypeWarning: "Some cellTypes in the sheet will be overwrite or change, would you like to continue?",
                            removeNodeWarning: "The removing node has some children nodes. Would you like to remove them?",
                            unallowComboxBindingTip: "Combox items only can be set in Combox. Please select a combox."
                        },
                        view: {
                            view: "VIEW",
                            showHide: "Show/Hide",
                            zoom: "Zoom",
                            viewport: "Viewport",
                            rowHeader: "Row Header",
                            columnHeader: "Column Header",
                            verticalGridline: "Vertical Gridline",
                            horizontalGridline: "Horizontal Gridline",
                            tabStrip: "TabStrip",
                            newTab: "NewTab",
                            rowHeaderTip: "Show/Hide RowHeader",
                            columnHeaderTip: "Show/Hide ColumnHeader",
                            verticalGridlineTip: "Show/Hide Vertical Gridline",
                            horizontalGridlineTip: "Show/Hide Horizontal Gridline",
                            tabStripTip: "Show/Hide TabStrip",
                            newTabTip: "Show/Hide NewTab",
                            zoomToSelection: "ZoomTo Selection",
                            zoomToSelection1: "ZoomTo Selection",
                            freezePane: "Freeze Pane",
                            freezePane1: "Freeze Pane",
                            freezeTopRow: "Freeze Top Row",
                            freezeFirstColumn: "Freeze First Column",
                            freezeBottomRow: "Freeze Bottom Row",
                            freezeLastColumn: "Freeze Last Column",
                            unFreezePane: "Unfreeze Pane",
                            unFreezePane1: "Unfreeze Pane"
                        },
                        setting: {
                            setting: "SETTINGS",
                            spreadSetting: "Spread Settings",
                            sheetSetting: "Sheet Settings",
                            general: "General",
                            generalTip: "General Setting",
                            scrollBars: "ScrollBars",
                            tabStrip: "TabStrip",
                            gridLines: "GridLines",
                            calculation: "Calculation",
                            headers: "Headers"
                        },
                        sparkLineDesign: {
                            design: "DESIGN",
                            type: "Type",
                            show: "Show",
                            style: "Style",
                            groups: "Groups",
                            line: "Line",
                            column: "Column",
                            winLoss: "Win/Loss",
                            lineTip: "Line Sparkline",
                            columnTip: "Column Sparkline",
                            winLossTip: "Win/Loss Sparkline",
                            highPoint: "High Point",
                            lowPoint: "Low Point",
                            negativePoint: "Negative Point",
                            firstPoint: "First Point",
                            lastPoint: "Last Point",
                            markers: "Markers",
                            highPointTip: "Toggle Sparkline High Point",
                            lowPointTip: "Toggle Sparkline Low Point",
                            negativePointTip: "Toggle Sparkline Negative Point",
                            firstPointTip: "Toggle Sparkline First Point",
                            lastPointTip: "Toggle Sparkline Last Point",
                            markersTip: "Toggle Sparkline Marker Point ",
                            sparklineColor: "Sparkline Color",
                            markerColor: "Marker Color",
                            sparklineWeight: "Sparkline Weight",
                            customWeight: "Custom Weight...",
                            group: "Group",
                            groupTip: "Group Selected Sparkline",
                            unGroupTip: "Ungroup Selected Sparkline",
                            unGroup: "Ungroup",
                            clear: "Clear",
                            clearSparkline: "Clear Selected Sparkline",
                            clearSparklineGroup: "Clear Selected Sparkline Groups"
                        },
                        formulaSparklineDesign: {
                            design: "DESIGN",
                            argument: "Argument",
                            settings: "Settings"
                        },
                        tableDesign: {
                            design: "DESIGN",
                            tableName: "Table Name",
                            resizeTable: "Resize Table",
                            tableOption: "Table Style Options",
                            property: "Properties",
                            headerRow: "Header Row",
                            totalRow: "Total Row",
                            bandedRows: "Banded Rows",
                            firstColumn: "First Column",
                            lastColumn: "Last Column",
                            bandedColumns: "Banded Columns",
                            filterButton: "Filter Button",
                            tableStyle: "Table Styles",
                            style: "Styles",
                            tools: "Tools",
                            insertSlicer: "Insert Slicer"
                        },
                        fontFamilies: {
                            ff1: {name: "Arial", text: "Arial"},
                            ff2: {name: "Arial Black", text: "Arial Black"},
                            ff3: {name: "Calibri", text: "Calibri"},
                            ff4: {name: "Cambria", text: "Cambria"},
                            ff5: {name: "Candara", text: "Candara"},
                            ff6: {name: "Century", text: "Century"},
                            ff7: {name: "Courier New", text: "Courier New"},
                            ff8: {name: "Comic Sans MS", text: "Comic Sans MS"},
                            ff9: {name: "Garamond", text: "Garamond"},
                            ff10: {name: "Georgia", text: "Georgia"},
                            ff11: {name: "Malgun Gothic", text: "Malgun Gothic"},
                            ff12: {name: "Mangal", text: "Mangal"},
                            ff13: {name: "Meiryo", text: "Meiryo"},
                            ff14: {name: "MS Gothic", text: "MS Gothic"},
                            ff15: {name: "MS Mincho", text: "MS Mincho"},
                            ff16: {name: "MS PGothic", text: "MS PGothic"},
                            ff17: {name: "MS PMincho", text: "MS PMincho"},
                            ff18: {name: "Tahoma", text: "Tahoma"},
                            ff19: {name: "Times", text: "Times"},
                            ff20: {name: "Times New Roman", text: "Times New Roman"},
                            ff21: {name: "Trebuchet MS", text: "Trebuchet MS"},
                            ff22: {name: "Verdana", text: "Verdana"},
                            ff23: {name: "Wingdings", text: "Wingdings"}
                        },
                        slicerOptions: {
                            options: "OPTIONS",
                            slicerCaptionShow: "Slicer Caption:",
                            slicerCaption: "Slicer Caption",
                            slicerSettings: "Slicer Settings",
                            slicer: "Slicer",
                            styles: "Styles",
                            slicerStyles: "Slicer Styles",
                            columnsShow: "Columns:",
                            heightShow: "Height:",
                            widthShow: "Width:",
                            columns: "Columns",
                            height: "Height",
                            width: "Width",
                            buttons: "Buttons",
                            size: "Size",
                            shapeHeight: "Shape Height",
                            shapeWidth: "Shape Width"
                        }
                    };

                    en_res.seriesDialog = {
                        series: "Series",
                        seriesIn: "Series in",
                        rows: "Rows",
                        columns: "Columns",
                        type: "Type",
                        linear: "Linear",
                        growth: "Growth",
                        date: "Date",
                        autoFill: "AutoFill",
                        dateUnit: "Date unit",
                        day: "Day",
                        weekday: "Weekday",
                        month: "Month",
                        year: "Year",
                        trend: "Trend",
                        stepValue: "Step Value",
                        stopValue: "Stop Value"
                    };

                    en_res.customSortDialog = {
                        sort: "Sort",
                        addLevel: "Add Level",
                        deleteLevel: "Delete Level",
                        copyLevel: "Copy Level",
                        options: "Options...",
                        sortBy: "SortBy",
                        sortBy2: "Sort by",
                        thenBy: "Then by",
                        sortOn: "SortOn",
                        sortOrder: "SortOrder",
                        sortOptions: "Sort Options",
                        orientation: "Orientation",
                        sortTopToBottom: "Sort top to bottom",
                        sortLeftToRight: "Sort left to right"
                    };

                    en_res.createTableDialog = {
                        createTable: "Create Table",
                        whereYourTable: "Where is the data for your table?"
                    };

                    en_res.createSparklineDialog = {
                        createSparkline: "Create Sparklines",
                        dataRange: "Data Range",
                        locationRange: "Location Range",
                        chooseData: "Choose the data that you want",
                        chooseWhere: "Choose where you want the sparkline to be placed",
                        warningText: "Location reference is not valid because the cells are not all in the same column or row. Select cells that are all in a single column or row"
                    };

                    en_res.dataValidationDialog = {
                        dataValidation: "Data Validation",
                        settings: "Settings",
                        validationCriteria: "Validation Criteria",
                        allow: "Allow",
                        anyValue: "Any value",
                        wholeNumber: "Whole number",
                        decimal: "Decimal",
                        list: "List",
                        date: "Date",
                        textLength: "Text length",
                        custom: "Custom",
                        data: "Data",
                        dataLabel: "Data:",
                        between: "between",
                        notBetween: "not between",
                        equalTo: "eaual to",
                        notEqualTo: "not equal to",
                        greaterThan: "greater than",
                        lessThan: "less Than",
                        greaterEqual: "greater than or equal to",
                        lessEqual: "less than or equal to",
                        minimum: "Minimum:",
                        maximum: "Maximum:",
                        value: "Value:",
                        startDate: "Start Date:",
                        endDate: "End Date:",
                        dateLabel: "Date:",
                        length: "Length:",
                        source: "Source:",
                        formula: "Formula:",
                        ignoreBlank: "Ignore Blank",
                        inCellDropDown: "In-cell dropdown",
                        inputMessage: "Input Message",
                        errorAlert: "Error Alert",
                        showMessage: "Show input message when cell is selected",
                        showMessage2: "When cell is selected, show this input message: ",
                        title: "Title",
                        showError: "Show error alert after invalid data is entered",
                        showError2: "When user enters invalid data, show this error alert:",
                        style: "Style:",
                        stop: "Stop",
                        warning: "Warning",
                        information: "Information",
                        errorMessage: "Error Message",
                        clearAll: "Clear All",
                        valueEmptyMessage: "Value cannot be empty.",
                        minimumMaximumMessage: "The Maximum must be greater than or equal to the Minimum.",
                        errorMessage1: "The Value you entered is not valid.\r\n A user has restricted values that can be entered into this cell.",
                        errorMessage2: "The Value you entered is not valid.\r\n A user has restricted values that can be entered into this cell.\r\nContinue?"
                    };

                    en_res.spreadSettingDialog = {
                        spreadSetting: "Spread Settings",
                        general: "General",
                        settings: "Settings",
                        allowDragDrop: "Allow Drag and Drop",
                        allowFormula: "Allow User to Enter Formulas",
                        allowDragFill: "Allow Drag and Fill",
                        allowZoom: "Allow Zoom",
                        allowUndo: "Allow Undo",
                        allowOverflow: "Allow Overflow",
                        scrollBars: "ScrollBars",
                        visibility: "Visibility",
                        verticalScrollBar: "Vertical ScrollBar",
                        horizontalScrollBar: "Horizontal ScrollBar",
                        scrollbarShowMax: "ScrollBar ShowMax",
                        scrollbarMaxAlign: "ScrollBar MaxAlign",
                        tabStrip: "Tab Strip",
                        tabStripVisible: "Tab Strip Visible",
                        tabStripEditable: "Tab Strip Editable",
                        newTabVisible: "New Tab Visible",
                        tabStripRatio: "Tab Strip Ratio(as percentage)"
                    };

                    en_res.sheetSettingDialog = {
                        sheetSetting: "Sheet Settings",
                        general: "General",
                        columnCount: "Column Count",
                        rowCount: "Row Count",
                        frozenColumnCount: "Frozen Column Count",
                        frozenRowCount: "Frozen Row Count",
                        trailingFrozenColumnCount: "Trailing Frozen Column Count",
                        trailingFrozenRowCount: "Trailing Frozen Row Count",
                        selectionPolicy: "Selection Policy",
                        singleSelection: "Single Select",
                        rangeSelection: "Range Select",
                        multiRangeSelection: "MultiRange Select",
                        protect: "Protect",
                        gridlines: "Grid Lines",
                        horizontalGridline: "Horizontal Gridline",
                        verticalGridline: "Vertical Gridline",
                        gridlineColor: "Gridline Color",
                        calculation: "Calculation",
                        referenceStyle: "Reference Style",
                        a1: "A1",
                        r1c1: "R1C1",
                        headers: "Headers",
                        columnHeaders: "Column Headers",
                        rowHeaders: "Row Headers",
                        columnHeaderRowCount: "Column Header Row Count",
                        columnHeaderAutoText: "Column Header Auto Text",
                        columnHeaderAutoIndex: "Column Header Auto Index",
                        defaultRowHeight: "Default Row Height",
                        columnHeaderVisible: "Column Header Visible",
                        blank: "Blank",
                        numbers: "Numbers",
                        letters: "Letters",
                        rowHeaderColumnCount: "Row Header Column Count",
                        rowHeaderAutoText: "Row Header Auto Text",
                        rowHeaderAutoIndex: "Row Header Auto Index",
                        defaultColumnWidth: "Default Column Width",
                        rowHeaderVisible: "Row Header Visible",
                        sheetTab: "SheetTab",
                        sheetTabColor: "Sheet Tab Color:"
                    };

                    en_res.groupDirectionDialog = {
                        settings: "Settings",
                        direction: "Direction",
                        rowDirection: "Summary rows below detail",
                        columnDirection: "Summary columns to right of detail"
                    };

                    en_res.insertSparklineDialog = {
                        createSparklines: "Create Sparklines",
                        dataRange: "Data Range:",
                        dataRangeTitle: "Choose the data that you want",
                        locationRange: "Location Range",
                        locationRangeTitle: "Choose where you want the sparkline to be placed",
                        errorDataRangeMessage: "Please input a valid range",
                        isFormulaSparkline: "isFormulaSparkline"
                    };

                    en_res.sparklineWeightDialog = {
                        sparklineWeight: "Sparkline Weight",
                        inputWeight: "Enter the Sparkline weight(pt)",
                        errorMessage: "Please input a valid weight."
                    };

                    en_res.sparklineMarkerColorDialog = {
                        sparklineMarkerColor: "Sparkline Marker Color:",
                        negativePoints: "Negative Points:",
                        markers: "Markers:",
                        highPoint: "High Point:",
                        lowPoint: "Low Point:",
                        firstPoint: "First Point:",
                        lastPoint: "Last Point:"
                    };

                    en_res.resizeTableDialog = {
                        title: "Resize Table",
                        dataRangeTitle: "Select the new data range for your table:",
                        note: "Note: The headers must remain in the same row,\r\nand the resulting table range must overlap \r\nthe original table range."
                    };

                    en_res.saveAsDialog = {
                        title: "Save As",
                        fileNameLabel: "File name:"
                    };

                    en_res.statusBar = {
                        ready: "READY",
                        enter: "ENTER",
                        edit: "EDIT"
                    };

                    en_res.pieSparklineDialog = {
                        percentage: "Percentage",
                        color: "Color",
                        addColor: "Add Color",
                        pieSparklineSetting: "PieSparkline Setting"
                    };

                    en_res.areaSparklineDialog = {
                        title: "AreaSparkline Formula",
                        points: "Points",
                        min: "Minimum Value",
                        max: "Maximum Value",
                        line1: "Line 1",
                        line2: "Line 2",
                        positiveColor: "Positive Color",
                        negativeColor: "Negative Color",
                        areaSparklineSetting: "AreaSparkline Setting"
                    };

                    en_res.scatterSparklineDialog = {
                        points1: "Points1",
                        points2: "Points2",
                        minX: "MinX",
                        maxX: "MaxX",
                        minY: "MinY",
                        maxY: "MaxY",
                        hLine: "HLine",
                        vLine: "VLine",
                        xMinZone: "XMinZone",
                        yMinZone: "YMinZone",
                        xMaxZone: "XMaxZone",
                        yMaxZone: "YMaxZone",
                        tags: "Tags",
                        drawSymbol: "Draw Symbol",
                        drawLines: "Draw Lines",
                        color1: "Color 1",
                        color2: "Color 2",
                        dash: "Dash Line",
                        scatterSparklineSetting: "ScatterSparkline Setting"
                    };

                    en_res.compatibleSparklineDialog = {
                        title: "CompatibleSparkline Formula",
                        style: "Style",
                        show: "Show",
                        group: "Group",
                        data: "Data",
                        dataOrientation: "DataOrientation",
                        dateAxisData: "DateAxisData",
                        dateAxisOrientation: "DateAxisOrientation",
                        settting: "Setting",
                        axisColor: "Axis",
                        firstMarkerColor: "First Marker",
                        highMarkerColor: "High Marker",
                        lastMarkerColor: "Last Marker",
                        lowMarkerColor: "Low Marker",
                        markersColor: "Markers",
                        negativeColor: "Negative",
                        seriesColor: "Series",
                        displayXAxis: "Display XAxis",
                        showFirst: "Show First",
                        showHigh: "Show High",
                        showLast: "Show Last",
                        showLow: "Show Low",
                        showNegative: "Show Negative",
                        showMarkers: "Show Markers",
                        lineWeight: "Line Weight",
                        displayHidden: "Show data in hidden rows and columns",
                        displayEmptyCellsAs: "DisplayEmptyCellsAs",
                        rightToLeft: "RightToLeft",
                        minAxisType: "MinAxisType",
                        maxAxisType: "MaxAxisType",
                        manualMax: "ManualMax",
                        manualMin: "ManualMin",
                        gaps: "Gaps",
                        zero: "Zero",
                        connect: "Connect",
                        vertical: "Vertical",
                        horizontal: "Horizontal",
                        stylesetting: "Style Setting",
                        individual: "Individual",
                        custom: "Custom",
                        compatibleSparklineSetting: "CompatibleSparkline Setting",
                        styleSetting: "Style Setting",
                        errorMessage: "Please input a valid range."
                    };

                    en_res.bulletSparklineDialog = {
                        bulletSparklineSetting: "BulletSparkline Setting",
                        measure: "Measure",
                        target: "Target",
                        maxi: "Maxi",
                        good: "Good",
                        bad: "Bad",
                        forecast: "Forecast",
                        tickunit: "Tickunit",
                        colorScheme: "ColorScheme",
                        vertical: "Vertical"
                    };

                    en_res.spreadSparklineDialog = {
                        spreadSparklineSetting: "SpreadSparkline Setting",
                        points: "Points",
                        showAverage: "ShowAverage",
                        scaleStart: "ScaleStart",
                        scaleEnd: "ScaleEnd",
                        style: "Style",
                        colorScheme: "ColorScheme",
                        vertical: "Vertical",
                        stacked: "Stacked",
                        spread: "Spread",
                        jitter: "Jitter",
                        poles: "Poles",
                        stackedDots: "StackedDots",
                        stripe: "Stripe"
                    };

                    en_res.stackedSparklineDialog = {
                        stackedSparklineSetting: "StackedSparkline Setting",
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
                        vertical: "Vertical",
                        textOrientation: "TextOrientation",
                        textSize: "TextSize",
                        textHorizontal: "Horizontal",
                        textVertical: "Vertical",
                        px: "px"
                    };

                    en_res.barbaseSparklineDialog = {
                        hbarSparklineSetting: "HbarSparkline Setting",
                        vbarSparklineSetting: "VbarSparkline Setting",
                        value: "Value",
                        colorScheme: "ColorScheme"
                    };

                    en_res.variSparklineDialog = {
                        variSparklineSetting: "VariSparkline Setting",
                        variance: "Variance",
                        reference: "Reference",
                        mini: "Mini",
                        maxi: "Maxi",
                        mark: "Mark",
                        tickunit: "TickUnit",
                        legend: "Legend",
                        colorPositive: "ColorPositive",
                        colorNegative: "ColorNegative",
                        vertical: "Vertical"
                    };
                    en_res.boxplotSparklineDialog = {
                        boxplotSparklineSetting: "BoxPlotSparkline Setting",
                        points: "Points",
                        boxPlotClass: "BoxPlotClass",
                        showAverage: "Show Average",
                        scaleStart: "ScaleStart",
                        scaleEnd: "ScaleEnd",
                        acceptableStart: "AcceptableStart",
                        acceptableEnd: "AcceptableEnd",
                        colorScheme: "ColorScheme",
                        style: "Style",
                        vertical: "Vertical",
                        fiveNS: "5NS",
                        sevenNS: "7NS",
                        tukey: "Tukey",
                        bowley: "Bowley",
                        sigma: "Sigma3",
                        classical: "Classical",
                        neo: "Neo"
                    };
                    en_res.cascadeSparklineDialog = {
                        cascadeSparklineSetting: "CascadeSparkline Setting",
                        pointsRange: "PointsRange",
                        pointIndex: "PointIndex",
                        labelsRange: "LabelsRange",
                        minimum: "Minimum",
                        maximum: "Maximum",
                        colorPositive: "ColorPositive",
                        colorNegative: "ColorNegative",
                        vertical: "Vertical"
                    };

                    en_res.multiCellFormula = {
                        warningText: "It may contain different formula type in selected range. Please select a new range."
                    };

                    en_res.paretoSparklineDialog = {
                        paretoSparklineSetting: "ParetoSparkline Setting",
                        points: "Points",
                        pointIndex: "PointIndex",
                        colorRange: "ColorRange",
                        target: "Target",
                        target2: "Target2",
                        highlightPosition: "HighlightPosition",
                        label: "Label",
                        vertical: "Vertical",
                        none: "None",
                        cumulated: "Cumulated",
                        single: "Single"
                    };

                    en_res.sliderPanel = {
                        title: "Field List"
                    };

                    en_res.protectionOptionDialog = {
                        title: "Protect Sheet",
                        label: "Allow all users of this worksheet to:",
                        allowSelectLockedCells: "Select locked cells",
                        allowSelectUnlockedCells: "Select unlocked cells",
                        allowSort: "Sort",
                        allowFilter: "Use AutoFilter",
                        allowResizeRows: "Resize rows",
                        allowResizeColumns: "Resize columns",
                        allowEditObjects: "Edit objects"
                    };
                    en_res.activateToolNotFound = "The activate tool can not be found, please reinstall SpreadJS Designer then try again.";

                    en_res.insertSlicerDialog = {
                        insertSlicer: "Insert Slicer"
                    };

                    en_res.formatSlicerStyle = {
                        custom: "Custom",
                        light: "Light",
                        dark: "Dark",
                        other: "Other",
                        newSlicerStyle: "New Slicer Style...",
                        slicerStyle: "Slicer Style",
                        name: "Name",
                        slicerElement: "Slicer Element",
                        format: "Format",
                        clear: "Clear",
                        preview: "Preview",
                        exception: "This style name is already exists."
                    };

                    en_res.slicerElement = {
                        wholeSlicer: "Whole Slicer",
                        header: "Header",
                        selectedItemWithData: "Selected Item With Data",
                        selectedItemWithNoData: "Selected Item With No Data",
                        unselectedItemWithData: "Unselected Item With Data",
                        unselectedItemWithNoData: "Unselected Item With No Data",
                        hoveredSelectedItemWithData: "Hover Selected Item With Data",
                        hoveredSelectedItemWithNoData: "Hover Selected Item With No Data",
                        hoveredUnselectedItemWithData: "Hover Unselected Item With Data",
                        hoveredUnselectedItemWithNoData: "Hover Unselected Item With No Data"
                    };

                    en_res.slicerSettingDialog = {
                        slicerSetting: "Slicer Settings",
                        sourceName: "Source Name:",
                        name: "Name:",
                        header: "Header",
                        display: "Display header",
                        caption: "Caption:",
                        items: "Item Sorting and Filtering",
                        ascending: "Ascending(A to Z)",
                        descending: "Descending(Z to A)",
                        customList: "Use Custom Lists when sorting",
                        hideItem: "Hide items with no data",
                        visuallyItem: "Visually indicate items with no data",
                        showItem: "Show items with no data last"
                    };

                    en_res.slicerPropertyDialog = {
                        formatSlicer: "Format Slicer",
                        position: "Position and Layout",
                        size: "Size",
                        properties: "Properties",
                        pos: "Position",
                        horizontal: "Horizontal:",
                        vertial: "Vertial:",
                        disableResizingMoving: "Disable resizing and moving",
                        layout: "Layout",
                        numberColumn: "Number of columns:",
                        buttonHeight: "Button height:",
                        buttonWidth: "Button width:",
                        height: "Height:",
                        width: "Width:",
                        scaleHeight: "Scale Height:",
                        scaleWidth: "Scale Width:",
                        moveSize: "Move and size with cells",
                        moveNoSize: "Move and don't size with cells",
                        noMoveSize: "Don't move and size with cells",
                        locked: "Locked"
                    };

                    en_res.name = "en";
                    
                    designer.res = en_res;
                })(designer.en_res || (designer.en_res = {}));
                var en_res = designer.en_res;
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));