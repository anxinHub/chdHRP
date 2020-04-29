var GC;
(function (GC) {
    (function (Spread) {
        (function (Sheets) {
            (function (designer) {
                (function (cn_res) {
                    cn_res.title = "Spread.Sheets 设计器";
                    cn_res.defaultFont = "11pt Calibri";
                    cn_res.ok = "确定";
                    cn_res.yes = "是";
                    cn_res.no = "否";
                    cn_res.apply = "适用";
                    cn_res.cancel = "取消";
                    cn_res.close = "关闭";
                    cn_res.fileAPINotSupported = "当前浏览器不提供API读取本地文件，请用其他现代浏览器来使用读取本地文件相关功能。";
                    cn_res.blobNotSupported = "当前浏览器不支持Blob，无法保存本地文件，请用其他现代浏览器/格式来完成保存本地文件相关功能。";
                    cn_res.saveFileDialogTitle = "另存为";
                    cn_res.openFileDialogTitle = "打开";
                    cn_res.allSpreadFileFilter = '所有 Spreadsheet 文件(*.ssjson *.xlsx *.csv)';
                    cn_res.spreadFileFilter = 'SpreadJS 文件(*.ssjson)';
                    cn_res.ssJSONToJSFilter = 'Javascript 文件(*.js)';
                    cn_res.excelFileFilter = 'Excel 工作薄 (*.xlsx)';
                    cn_res.csvFileFilter = "CSV 文件 (*.csv)";
                    cn_res.pdfFileFilter = "PDF 文件 (*.pdf)";
                    cn_res.allFileFilter = '所有文件 (*.*)';
                    cn_res.importFileDialogTitle = "导入";
                    cn_res.exportFileDialogTitle = "导出";

                    cn_res.insertCellInSheet = "无法移动整个工作表的单元格";
                    cn_res.insertCellInMixtureRange = "不能对同时包含整行或整列和其他单元格的选择区域使用此命令。请尝试只选择整行或整列或一组单元格。";
                    cn_res.NotExecInMultiRanges = "不能对多重选择区域使用此命令。请选择单个区域再次尝试此命令。";
                    cn_res.unsavedWarning = "此文件未保存，是否保存？ ";

                    cn_res.requestTempalteFail = "模板文件请求错误。";
                    cn_res.requestTemplateConfigFail = "模板配置文件请求错误。";
                    cn_res.openFileFormatError = "文件格式不正确。";

                    cn_res.closingNotification = "警告：当前文件以及被修改。\n是否保存对此文件的更改？";

                    cn_res.sameSlicerName = "切片器名称已经被使用。请输入一个唯一的名称。 ";
                    cn_res.nullSlicerName = "切片器名称不合法。";

                    cn_res.changePartOfArrayWarning = "数组公式不允许更改部分内容！";
                    cn_res.exportCsvSheetIndexError = "无效的工作表索引。";

                    cn_res.fontPicker = {
                        familyLabelText: '字体:',
                        styleLabelText: '字形:',
                        sizeLabelText: '字号:',
                        weightLabelText: '字体粗细:',
                        colorLabelText: '颜色:',
                        normalFontLabelText: '常规字体',
                        previewLabelText: '预览',
                        previewText: 'AaBbCcYyZz',
                        effects: "特殊效果",
                        underline: "下划线",
                        strikethrough: "删除线",
                        //
                        // Fonts shown in font selector.
                        //
                        // the property's name means the font family name.
                        // the property's value means the text shown in drop down list.
                        //
                        fontFamilies: {
                            "宋体": "宋体",
                            "楷体": "楷体",
                            "仿宋": "仿宋",
                            "黑体": "黑体",
                            "新宋体": "新宋体",
                            "SimSun": "SimSun",
                            "KaiTi": "KaiTi",
                            "FangSong": "FangSong",
                            "SimHei": "SimHei",
                            "NSimSun": "NSimSun",
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
                            'normal': '常规',
                            'italic': '斜体',
                            'oblique': '倾斜'
                        },
                        fontWeights: {
                            'normal': '常规',
                            'bold': '加粗',
                            'bolder': '更粗',
                            'lighter': '更细'
                        },
                        //alternativeFonts: "Arial,'Segoe UI',Thonburi,Verdana,Sans-Serif",
                        // for zh version
                        alternativeFonts: "'Microsoft Yahei UI', 'Microsoft Yahei', 微软雅黑, SimSun, 宋体, sans-serif",
                        // for jp version
                        // alternativeFonts: "'Meiryo UI', 'MS UI Gothic', 'MS PGothic', 'ＭＳ Ｐゴシック', 'MS Gothic', 'ＭＳ ゴシック', sans-serif"
                        defaultSize: '10'
                    };

                    cn_res.commonFormats = {
                        Number: {
                            format: "0.00",
                            label: "数值"
                        },
                        Currency: {
                            format: "$#,##0.00",
                            label: "货币"
                        },
                        Accounting: {
                            format: "$ #,##0.00;$ (#,##0.00);$ \"-\"??;@",
                            label: "会计专用"
                        },
                        ShortDate: {
                            format: "m/d/yyyy",
                            label: "短日期"
                        },
                        LongDate: {
                            format: "dddd, mmmm dd, yyyy",
                            label: "长日期"
                        },
                        Time: {
                            format: "h:mm:ss AM/PM",
                            label: "时间"
                        },
                        Percentage: {
                            format: "0%",
                            label: "百分比"
                        },
                        Fraction: {
                            format: "# ?/?",
                            label: "分数"
                        },
                        Scientific: {
                            format: "0.00E+00",
                            label: "科学记数"
                        },
                        Text: {
                            format: "@",
                            label: "文本"
                        },
                        Comma: {
                            format: " #,##0.00; (#,##0.00); \"-\"??;@",
                            label: "千分符"
                        }
                    };
                    cn_res.customFormat = "自定义";
                    cn_res.generalFormat = "常规";

                    cn_res.colorPicker = {
                        themeColorsTitle: "自定义",
                        standardColorsTitle: "标准",
                        noFillText: "无颜色",
                        moreColorsText: "其他颜色...",
                        colorDialogTitle: "颜色",
                        red: "红色: ",
                        green: "绿色: ",
                        blue: "蓝色: ",
                        newLabel: "新增",
                        currentLabel: "当前"
                    };

                    cn_res.formatDialog = {
                        title: "设置单元格格式",
                        number: '数字',
                        alignment: '对齐',
                        font: '字体',
                        border: '边框',
                        fill: '填充',
                        protection: '保护',
                        category: '分类:',
                        backColor: '背景色',
                        textAlignment: '文本对齐方式',
                        horizontalAlignment: '水平对齐:',
                        verticalAlignment: '垂直对齐:',
                        indent: '缩进:',
                        textControl: '文本控制',
                        wrapText: '自动换行',
                        shrink: '缩小字体填充',
                        merge: '合并单元格',
                        top: '靠上',
                        bottom: '靠下',
                        left: '靠左',
                        right: '靠右',
                        center: '居中',
                        general: '常规',
                        sampleText: '文本',
                        cantMergeMessage: '重叠范围无法合并。',
                        lock: "锁定",
                        lockComments: "只有在工作表被保护时，锁定单元格才有效（在“主页”选项卡上“单元格”区域中的“格式”下拉列表中点击“保护工作表”）。",
                        backGroundColor: "背景色:",
                        moreColorsText: "其他颜色",
                        sample: "示例"
                    };

                    cn_res.formatComment = {
                        title: "格式",
                        protection: "保护",
                        commentLocked: "锁定",
                        commentLockText: "锁定文本",
                        commentLockComments: "只有在工作表被保护时，锁定对象才有效。要保护工作表，可以在“主页”选项卡上“单元格”区域中的“格式”下拉列表中点击“保护工作表”。",
                        properties: "属性",
                        positioning: "对象位置",
                        internalMargin: "内边距",
                        moveSize: "大小和位置随单元格而变",
                        moveNoSize: "大小固定，位置随单元格而变",
                        noMoveSize: "大小和位置均固定",
                        automatic: "自动",
                        autoSize: "自动调整大小",
                        colors: "颜色和线条",
                        size: "大小",
                        fill: "填充",
                        line: "线条",
                        height: "高度",
                        width: "宽度",
                        lockRatio: "锁定纵横比",
                        color: "颜色",
                        transparency: "透明度",
                        style: "样式",
                        dotted: "点线",
                        dashed: "虚线",
                        solid: "实线",
                        double: "双线",
                        none: "无线条",
                        groove: "常规",
                        ridge: "脊线",
                        inset: "内凹",
                        outset: "外凸"
                    };

                    cn_res.categories = {
                        general: "常规",
                        numbers: "数值",
                        currency: "货币",
                        accounting: "会计专用",
                        date: "日期",
                        time: "时间",
                        percentage: "百分比",
                        fraction: "分数",
                        scientific: "科学记数",
                        text: "文本",
                        special: "特殊",
                        custom: "自定义"
                    };

                    cn_res.formatNumberComments = {
                        generalComments: "常规单元格格式不包含任何特定的数字格式。",
                        numberComments: "数值格式用于一般数字的表示。货币和会计格式则提供货币值计算的专用格式。",
                        currencyComments: "货币格式用于表示一般货币数值。会计格式可以对一列数值进行小数点对齐。",
                        accountingComments: "会计格式可对一列数值进行货币符号和小数点对齐。",
                        dateComments: "日期格式将日期和时间系列数值显示为日期值。",
                        timeComments: "时间格式将日期和时间系列数值显示为时间值。",
                        percentageComments: "百分比格式将单元格中数值乘以100，并以百分数形式显示。",
                        textComments: "在文本单元格格式中，数字作为文本处理。单元格显示的内容与输入的内容完全一致。",
                        specialComments: "特殊格式可用于跟踪数据列表及数据库的值。",
                        customComments: "以现有格式为基础，生成自定义的数字格式。"
                    };

                    cn_res.formatNumberPickerSetting = {
                        type: "类型:",
                        decimalPlaces: "小数位数:",
                        symbol: "货币符号:",
                        negativeNumber: "负数:",
                        separator: "使用千位分隔符(,)",
                        deleted: "删除",
                        locale: "区域设置（国家/地区）:",
                        calendar: "日历类型:"
                    };

                    cn_res.localeType = {
                        en_us: "英语(美国)",
                        ja_jp: "日语"
                    };

                    cn_res.calendarType = {
                        western: "公历",
                        JER: "日本年号"
                    };

                    cn_res.fractionFormats = [
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

                    cn_res.numberFormats = [
                        "0",
                        "0;[Red]0",
                        "0_);(0)",
                        "0_);[Red](0)",
                        "#,##0",
                        "#,##0;[Red]#,##0",
                        "#,##0_);(#,##0)",
                        "#,##0_);[Red](#,##0)"
                    ];

                    cn_res.dateFormats = [
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

                    cn_res.japanWesternDateFormat = [
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

                    cn_res.japanEmperorReignDateFormat = [
                        "[$-411]ge.m.d;@",
                        "[$-411]ggge'年'm'月'd'日';@"
                    ];

                    cn_res.timeFormats = [
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

                    cn_res.japanTimeFormats = [
                        "h:mm;@",
                        "[$-409]h:mm AM/PM;@",
                        "h:mm:ss;@",
                        "[$-409]h:mm:ss AM/PM;@",
                        "[$-409]yyyy/m/d h:mm AM/PM;@",
                        "yyyy/m/d h:mm;@",
                        "h'時'mm'分';@",
                        "h'時'mm'分'ss'秒';@"
                    ];

                    cn_res.textFormats = [
                        "@"
                    ];

                    cn_res.specialFormats = [
                        "00000",
                        "00000-0000",
                        "[<=9999999]###-####;(###) ###-####",
                        "000-00-0000"
                    ];

                    cn_res.specialJapanFormats = [
                        "[<=999]000;[<=9999]000-00;000-0000",
                        "[<=99999999]####-####;(00) ####-####",
                        "'△' #,##0;'▲' #,##0",
                        "[DBNum1][$-411]General",
                        "[DBNum2][$-411]General",
                        "[DBNum3][$-411]0",
                        "[DBNum3][$-411]#,##0"
                    ];

                    cn_res.currencyFormats = [
                        "#,##0",
                        "#,##0;[Red]#,##0",
                        "#,##0;-#,##0",
                        "#,##0;[Red]-#,##0"
                    ];

                    cn_res.percentageFormats = [
                        "0%"
                    ];

                    cn_res.scientificFormats = [
                        "0E+00"
                    ];

                    cn_res.accountingFormats = [
                        "_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)"
                    ];

                    cn_res.customFormats = [
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

                    cn_res.accountingSymbol = [
                        ["无", null, null],
                        ["$", "", "en-US"]
                    ];

                    cn_res.specialType = [
                        "Zip Code",
                        "Zip Code + 4",
                        "Phone Number",
                        "Social Security Number"
                    ];

                    cn_res.specialJapanType = [
                        "郵便番号",
                        "電話番号（東京)",
                        "正負記号 （+ = △; - = ▲)",
                        "漢数字（十二万三千四百）",
                        "大字 (壱拾弐萬参阡四百)",
                        "全角 (１２３４５)",
                        "全角 桁区切り（１２,３４５）"
                    ];

                    cn_res.fractionType = [
                        "分母为一位数(1/4)",
                        "分母为两位数(21/25)",
                        "分母为三位数(312/943)",
                        "以2为分母(1/2)",
                        "以4为分母(2/4)",
                        "以8为分母(4/8)",
                        "以16为分母(8/16)",
                        "以10为分母(3/10)",
                        "百分之几(30/100)"
                    ];

                    cn_res.negativeNumbers = {
                        "-1234.10": "-1234.10",
                        "red:1234.10": "1234.10",
                        "(1234.10)": "(1234.10)",
                        "red:(1234.10)": "(1234.10)"
                    };

                    cn_res.currencyNegativeNumbers = {
                        "number1": "-1,234.10",
                        "red:number2": "1,234.10",
                        "number3": "-1,234.10",
                        "red:number4": "-1,234.10"
                    };

                    cn_res.passwordDialog = {
                        title: "密码",
                        passwordLabel: "密码:"
                    };

                    cn_res.rowHeightDialog = {
                        title: "行高",
                        rowHeight: "行高:",
                        exception: "行高必须为整数或小数。",
                        exception2: "行高必须在0至9999999之间。"
                    };
                    cn_res.columnWidthDialog = {
                        title: "列宽",
                        columnWidth: "列宽:",
                        exception: "列宽必须为整数或小数。",
                        exception2: "列宽必须在0至9999999之间。"
                    };
                    cn_res.standardWidthDialog = {
                        title: "标准列宽",
                        columnWidth: "标准列宽:",
                        exception: "输入不正确。要求输入内容为整数或小数。"
                    };
                    cn_res.standardHeightDialog = {
                        title: "标准行高",
                        rowHeight: "标准行高:",
                        exception: "输入不正确。要求输入内容为整数或小数。"
                    };
                    cn_res.insertCellsDialog = {
                        title: "插入",
                        shiftcellsright: "活动单元格右移",
                        shiftcellsdown: "活动单元格下移",
                        entirerow: "整行",
                        entirecolumn: "整列"
                    };
                    cn_res.deleteCellsDialog = {
                        title: "删除",
                        shiftcellsleft: "右侧单元格左移",
                        shiftcellsup: "下方单元格上移",
                        entirerow: "整行",
                        entirecolumn: "整列"
                    };
                    cn_res.groupDialog = {
                        title: "创建组",
                        rows: "行",
                        columns: "列"
                    };
                    cn_res.ungroupDialog = {
                        title: "取消组合"
                    };
                    cn_res.findDialog = {
                        title: "查找",
                        findwhat: "查找内容:",
                        within: "范围:",
                        matchcase: "区分大小写",
                        search: "搜索:",
                        matchexactly: "单元格匹配",
                        lookin: "查找范围:",
                        usewildcards: "使用通配符",
                        option: "选项",
                        findall: "查找全部",
                        findnext: "查找下一个",
                        exception: "无法找到您所查找的内容。"
                    };
                    cn_res.gotoDialog = {
                        title: "定位",
                        goto: "定位:",
                        reference: "引用位置:",
                        exception: "您输入的引用无效或名称为定义。",
                        wrongName: "操作执行失败。"
                    };
                    cn_res.nameManagerDialog = {
                        title: "名称管理器",
                        newName: "新建...",
                        edit: "编辑...",
                        deleteName: "删除",
                        filterWith: {
                            title: "筛选:",
                            clearFilter: "清除筛选",
                            nameScopedToWorkbook: "名称扩展到工作簿范围",
                            nameScopedToWorksheet: "名称扩展到工作表范围",
                            nameWithErrors: "有错误的名称",
                            nameWithoutErrors: "没有错误的名称"
                        },
                        nameColumn: "名称",
                        valueColumn: "数值",
                        refersToColumn: "引用位置",
                        scopeColumn: "范围",
                        exception1: "输入的名称无效。",
                        exception2: "输入的名称已经存在。请输入唯一的名称。",
                        deleteAlert: "是否确实删除名称{0}?"
                    };
                    cn_res.newNameDialog = {
                        titleNew: "新建名称",
                        titleEdit: "编辑名称",
                        name: "名称:",
                        scope: {
                            title: "范围:",
                            workbook: "工作薄"
                        },
                        referTo: "引用位置:",
                        wrongName: "操作执行失败。"
                    };
                    cn_res.insertFunctionDialog = {
                        title: "插入函数",
                        functionCategory: "函数类别:",
                        functionList: "选择函数:",
                        formula: "公式:",
                        functionCategorys: "全部,数据库,日期与时间,工程,财务,信息,逻辑,查找与引用,数学与三角函数,统计,文本"
                    };
                    cn_res.buttonCellTypeDialog = {
                        title: "按钮",
                        marginGroup: "边距:",
                        left: "左:",
                        top: "上:",
                        right: "右:",
                        bottom: "下:",
                        text: "文本:",
                        backcolor: "背景色:",
                        other: "其他:"
                    };
                    cn_res.checkBoxCellTypeDialog = {
                        title: "复选框",
                        textGroup: "文字:",
                        "true": "已选择:",
                        indeterminate: "不确定:",
                        "false": "未选择:",
                        align: "对齐:",
                        other: "其他:",
                        caption: "标题:",
                        isThreeState: "三态",
                        checkBoxTextAlign: {
                            top: "靠上",
                            bottom: "靠下",
                            left: "靠左",
                            right: "靠右"
                        }
                    };
                    cn_res.comboBoxCellTypeDialog = {
                        title: "组合框",
                        editorValueTypes: "编辑值类型:",
                        items: "项目:",
                        itemProperties: "项目属性:",
                        text: "文本:",
                        value: "值:",
                        add: "添加",
                        remove: "删除",
                        editorValueType: {
                            text: "文本",
                            index: "索引",
                            value: "值"
                        },
                        editable: "可编辑",
                        itemHeight: "项目高度"
                    };
                    cn_res.hyperLinkCellTypeDialog = {
                        title: "超链接",
                        link: "链接:",
                        visitedlink: "已访问:",
                        text: "文本:",
                        linktooltip: "屏幕提示:",
                        color: "颜色:",
                        other: "其他:"
                    };
                    cn_res.headerCellsDialog = {
                        title: "标题单元格Header Cells",
                        rowHeader: "行标题",
                        columnHeader: "列标题",
                        backColor: "背景色",
                        borderBottom: "下边框",
                        borderLeft: "左边框",
                        borderRight: "右边框",
                        borderTop: "上边框",
                        font: "字体",
                        foreColor: "前景色",
                        formatter: "格式",
                        hAlign: "垂直对齐方式",
                        height: "高度",
                        locked: "锁定",
                        resizable: "可调整大小",
                        shrinkToFit: "缩小字体填充",
                        value: "值",
                        textIndent: "缩进",
                        vAlign: "垂直对齐",
                        visible: "可见",
                        width: "宽度",
                        wordWrap: "自动换行",
                        popUp: "...",
                        merge: "合并单元格",
                        unmerge: "取消单元格合并",
                        insertRows: "插入行",
                        addRows: "添加行",
                        deleteRows: "删除行",
                        insertColumns: "插入列",
                        addColumns: "添加列",
                        deleteColumns: "删除列",
                        clear: "清除",
                        top: '靠上',
                        bottom: '靠下',
                        left: '靠左',
                        right: '靠右',
                        center: '居中',
                        general: '常规',
                        exception: "设定不合法。请检查红色区域。"
                    };
                    cn_res.fontPickerDialog = {
                        title: "字体"
                    };
                    cn_res.fillDialog = {
                        title: "系列"
                    };

                    cn_res.zoomDialog = {
                        title: "缩放",
                        double: "200%",
                        normal: "100%",
                        threeFourths: "75%",
                        half: "50%",
                        quarter: "25%",
                        fitSelection: "调整为合适大小",
                        custom: "自定义:",
                        exception: "输入不正确。要求输入内容为整数或小数。",
                        magnification: "放大",
                        percent: "%"
                    };
                    cn_res.contextMenu = {
                        cut: "剪切",
                        copy: "复制",
                        paste: "粘贴选项:",
                        insertDialog: "插入...",
                        deleteDialog: "删除...",
                        clearcontents: "清除内容",
                        filter: "筛选",
                        sort: "排序",
                        sortAToZ: "升序",
                        sortZToA: "降序",
                        customSort: "自定排序...",
                        formatCells: "设置单元格格式...",
                        defineName: "定义名称...",
                        rowHeight: "行高...",
                        columnWidth: "列宽...",
                        hide: "隐藏",
                        unhide: "取消隐藏",
                        headers: "标题...",
                        insert: "插入",
                        "delete": "删除",
                        protectsheet: "保护工作表...",
                        unprotectsheet: "取消工作表保护...",
                        comments: "工作簿内至少含一张可视工作表。",
                        insertComment: "插入批注",
                        editComment: "编辑批注",
                        deleteComment: "删除批注",
                        hideComment: "隐藏批注",
                        editText: "编辑文字",
                        exitEditText: "退出文本编辑",
                        formatComment: "格式化批注",
                        unHideComment: "显示/隐藏批注",
                        sheetTabColor: "工作表标签颜色",
                        remove: "删除",
                        slicerProperty: "大小和属性...",
                        slicerSetting: "切片器设置..."
                    };
                    cn_res.borderPicker = {
                        lineStyleTitle: "线条:",
                        borderColorTitle: "颜色:",
                        none: "无"
                    };
                    cn_res.borderDialog = {
                        border: "边框",
                        presets: "预置",
                        none: "无",
                        outline: "外边框",
                        inside: "内部",
                        line: "边框",
                        text: "文本",
                        comments: "单击预置选项，预览草图及上面的按钮可以添加边框样式。"
                    };

                    cn_res.conditionalFormat = {
                        highlightCellsRules: "突出显示单元格规则",
                        topBottomRules: "项目选取规则",
                        dataBars: "数据条",
                        colorScales: "色阶",
                        iconSets: "图标集",
                        newRule: "新建规则...",
                        clearRules: "清除规则...",
                        manageRules: "管理规则...",
                        greaterThan: "大于...",
                        lessThan: "小于...",
                        between: "介于...",
                        equalTo: "等于...",
                        textThatContains: "文本包含...",
                        aDateOccurring: "发生日期...",
                        duplicateValues: "重复值...",
                        moreRules: "其他规则...",
                        top10Items: "值最大的10项...",
                        bottom10Items: "值最小的10项...",
                        aboveAverage: "高于平均值...",
                        belowAverage: "低于平均值...",
                        gradientFill: "渐变填充",
                        solidFill: "实心填充",
                        directional: "方向",
                        shapes: "形状",
                        indicators: "标记",
                        ratings: "等级",
                        clearRulesFromSelectedCells: "清除所选单元格的规则",
                        clearRulesFromEntireSheet: "清除整个工作表的规则"
                    };

                    cn_res.fileMenu = {
                        _new: "新建",
                        open: "打开",
                        save: "保存",
                        saveAs: "另存为",
                        _export: "导出",
                        _import: "导入",
                        exit: "关闭",
                        recentWorkbooks: "最近使用的工作薄",
                        computer: "计算机",
                        currentFolder: "当前文件夹",
                        recentFolders: "最近使用的文件夹",
                        browse: "浏览",
                        spreadSheetJsonFile: "SpreadSheet 文件(JSON)",
                        excelFile: "Excel文件",
                        csvFile: "CSV文件",
                        pdfFile: "PDF文件",
                        importSpreadSheetFile: "导入 SSJSON 文件",
                        importExcelFile: "导入 Excel 文件",
                        importCsvFile: "导入 CSV 文件",
                        exportSpreadSheetFile: "导出 SSJSON 文件",
                        exportExcelFile: "导出 Excel 文件",
                        exportCsvFile: "导出 CSV 文件",
                        exportPdfFile: "导出 PDF 文件",
                        exportJSFile: "导出 Javascript 文件",
                        openFlags: "打开选项",
                        importDataOnly: "只导入数据",
                        importDataAndFormulasOnly: "只导入数据和公式",
                        importDoNotRecalculateAfterLoad: "导入后不自动计算公式",
                        importRowAndColumnHeaders: "导入作为标题的冻结列和行",
                        importRowHeaders: "导入作为列标题的冻结行",
                        importColumnHeaders: "导入作为行标题的冻结列",
                        importPassword: "密码",
                        importIncludeRowHeader: "导入行标题",
                        importIncludeColumnHeader: "导入列标题",
                        importUnformatted: "保留没有格式化的数据",
                        importImportFormula: "导入单元格公式",
                        importRowDelimiter: "行分隔符",
                        importColumnDelimiter: "列分隔符",
                        importCellDelimiter: "单元格分隔符",
                        importEncoding: "文件编码",
                        saveFlags: "保存选项",
                        exportDataOnly: "只导出数据",
                        exportNoFormulas: "不导出公式",
                        exportAutoRowHeight: "行高自动调整",
                        exportSaveAsFiltered: "作为过滤导出",
                        exportSaveAsViewed: "作为预览导出",
                        exportSaveBothCustomRowAndColumnHeaders: "将行标题和列标题作为Excel的冻结列和冻结行导出",
                        exportSaveCustomRowHeaders: "将行标题作为Excel的冻结列导出",
                        exportSaveCustomColumnHeaders: "将列标题作为Excel的冻结行导出",
                        exportPassword: "密码",
                        exportExcel97_2003File: "导出 Excel 97-2003 文件",
                        exportIncludeRowHeader: "包含行标题",
                        exportIncludeColumnHeader: "包含列标题",
                        exportUnFormatted: "不包含任何样式信息",
                        exportFormula: "包含公式",
                        exportAsViewed: "作为预览导出",
                        exportSheetIndex: "工作表索引",
                        exportEncoding: "编码",
                        exportRow: "行索引",
                        exportColumn: "列索引",
                        exportRowCount: "行数",
                        exportColumnCount: "列数",
                        exportRowDelimiter: "行分隔符",
                        exportColumnDelimiter: "列分隔符",
                        exportCellDelimiter: "单元格分隔符",
                        exportVisibleRowCol: "只包含可见行和可见列",
                        pdfBasicSetting: "基本设定",
                        pdfTitle: "标题:",
                        pdfAuthor: "作者:",
                        pdfApplication: "应用程序:",
                        pdfSubject: "主题:",
                        pdfKeyWords: "关键字:",
                        pdfExportSetting: "导出设定",
                        exportSheetLabel: "选择要导出的工作表:",
                        allSheet: "全部",
                        pdfDisplaySetting: "显示设定",
                        centerWindowLabel: "置于窗口中心",
                        showTitleLabel: "显示标题",
                        showToolBarLabel: "显示工具条",
                        fitWindowLabel: "最适化窗口",
                        showMenuBarLabel: "显示菜单栏",
                        showWindowUILabel: "显示窗口界面",
                        destinationTypeLabel: "目标类型:",
                        destinationType: {
                            autoDestination: "自动",
                            fitPageDestination: "页面自适应",
                            fitWidthDestination: "宽度自适应",
                            fitHeightDestination: "高度自适应",
                            fitBoxDestination: "盒子自适应"
                        },
                        openTypeLabel: "打开类型:",
                        openType: {
                            autoOpen: "自动",
                            useNoneOpen: "未使用",
                            useOutlinesOpen: "使用目录",
                            useThumbsOpen: "使用缩图",
                            fullScreenOpen: "全屏",
                            useOCOpen: "使用 OC",
                            useAttachmentsOpen: "使用附件"
                        },
                        pdfPageSetting: "页面设定",
                        openPageNumberLabel: "打开页码:",
                        pageLayoutLabel: "页面布局:",
                        pageLayout: {
                            autoLayout: "自动",
                            singlePageLayout: "单页",
                            oneColumnLayout: "单栏",
                            twoColumnLeftLayout: "左侧两列",
                            twoColumnRightLayout: "右侧两列",
                            twoPageLeftLayout: "左侧两页",
                            twoPageRight: "右侧两页"
                        },
                        pageDurationLabel: "页面范围:",
                        pageTransitionLabel: "页面过渡:",
                        pageTransition: {
                            defaultTransition: "默认",
                            splitTransition: "劈裂",
                            blindsTransition: "百叶窗",
                            boxTransition: "方格",
                            wipeTransition: "掀开",
                            dissolveTransition: "融化",
                            glitterTransition: "闪烁",
                            flyTransition: "飞入",
                            pushTransition: "推入",
                            coverTransition: "盖上",
                            uncoverTransition: "揭开",
                            fadeTransition: "淡出"
                        },
                        printerSetting: "打印机设定...",
                        printerSettingDialogTitle: "打印机设定",
                        copiesLabel: "份数:",
                        scalingTypeLabel: "缩放类型:",
                        scalingType: {
                            appDefaultScaling: "程序默认",
                            noneScaling: "无"
                        },
                        duplexModeLabel: "双面模式:",
                        duplexMode: {
                            defaultDuplex: "默认",
                            simplexDuplex: "简单",
                            duplexFlipShortEdge: "翻转短边",
                            duplexFlipLongEdge: "翻转长边"
                        },
                        choosePaperSource: "通过pdf页面大小选择纸张源",
                        printRanges: "打印范围",
                        indexLabel: "索引",
                        countLabel: "数量",
                        addRange: "添加",
                        removeRange: "删除",
                        addRangeException: "值不合法，索引必须大于等于0，数量必须大于0。",
                        noRecentWorkbooks: "最近没有打开任何工作薄。请先打开一个浏览工作薄。",
                        noRecentFolders: "最近没有打开任何文件夹。请先打开一个浏览工作薄。"
                    };

                    cn_res.formatTableStyle = {
                        name: "名称:",
                        tableElement: "表元素:",
                        preview: "预览",
                        format: "格式",
                        tableStyle: "表样式",
                        clear: "清除",
                        stripeSize: "条纹大小",
                        light: "浅色",
                        medium: "中等深浅",
                        dark: "深色",
                        newTableStyle: "新建表格样式...",
                        custom: "自定义",
                        exception: "此样式名已经存在。",
                        title: "SpreadJS 设计器"
                    };
                    cn_res.tableElement = {
                        wholeTableStyle: "整个表",
                        firstColumnStripStyle: "第一列条纹",
                        secondColumnStripStyle: "第二列条纹",
                        firstRowStripStyle: "第一行条纹",
                        secondRowStripStyle: "第二行条纹",
                        highlightLastColumnStyle: "最后一列",
                        highlightFirstColumnStyle: "第一列",
                        headerRowStyle: "标题行",
                        footerRowStyle: "汇总行",
                        firstHeaderCellStyle: "第一个标题单元格",
                        lastHeaderCellStyle: "最后一个标题单元格",
                        firstFooterCellStyle: "第一个汇总单元格",
                        lastFooterCellStyle: "最后一个汇总单元格"
                    };
                    cn_res.conditionalFormatting = {
                        common: {
                            'with': "设置为",
                            selectedRangeWith: "针对选定区域，设置为",
                            and: "与"
                        },
                        greaterThan: {
                            title: "大于",
                            description: "为大于以下值的单元格设置格式:"
                        },
                        lessThan: {
                            title: "小于",
                            description: "为小于以下值的单元格设置格式:"
                        },
                        between: {
                            title: "介于",
                            description: "为介于以下值之间的单元格设置格式:"
                        },
                        equalTo: {
                            title: "等于",
                            description: "为等于以下值的单元格设置格式:"
                        },
                        textThatCotains: {
                            title: "文本中包含",
                            description: "为包含以下文本的单元格设置格式:"
                        },
                        dateOccurringFormat: {
                            title: "发生日期",
                            description: "为包含以下日期的单元格设置格式:",
                            date: {
                                yesterday: "昨天",
                                today: "今天",
                                tomorrow: "明天",
                                last7days: "最近7天",
                                lastweek: "上周",
                                thisweek: "本周",
                                nextweek: "下周",
                                lastmonth: "上个月",
                                thismonth: "本月",
                                nextmonth: "下个月"
                            }
                        },
                        duplicateValuesFormat: {
                            title: "重复值",
                            description: "为包含以下类型值的单元格设置格式:",
                            type: {
                                duplicate: "重复",
                                unique: "唯一"
                            },
                            valueswith: "值"
                        },
                        top10items: {
                            title: "前10项",
                            description: "为值最大的那些单元格设置单元格:"
                        },
                        bottom10items: {
                            title: "最后10项",
                            description: "为值最小的那些单元格设置格式:"
                        },
                        aboveAverage: {
                            title: "高于平均值",
                            description: "为高于平均值的单元格设置格式:"
                        },
                        belowAverage: {
                            title: "低于平均值",
                            description: "为低于平均值的单元格设置格式:"
                        },
                        newFormattingRule: {
                            title: "新建格式规则",
                            title2: "编辑格式规则",
                            description1: "选择规则类型:",
                            description2: "编辑规则说明:",
                            ruleType: {
                                formatOnValue: "►基于各自值设置所有单元格的格式",
                                formatContain: "►只为包含以下内容的单元格设置格式",
                                formatRankedValue: "►仅对排名靠前或靠后的数值设置格式",
                                formatAbove: "►仅对高于或低于平均值的数值设置格式",
                                formatUnique: "►仅对唯一值或重复值设置格式",
                                useFormula: "►使用公式确定要设置格式的单元格"
                            },
                            formatOnValue: {
                                description: "基于各自值设置所有单元格的格式:",
                                formatStyle: "格式样式:",
                                formatStyleSelector: {
                                    color2: "双色刻度",
                                    color3: "三色刻度",
                                    dataBar: "数据条",
                                    iconSets: "图标集"
                                },
                                color2: {
                                    min: "最小值",
                                    max: "最大值",
                                    type: "类型:",
                                    value: "值:",
                                    color: "颜色:",
                                    preview: "预览",
                                    minSelector: {
                                        lowest: "最低值"
                                    },
                                    maxSelector: {
                                        highest: "最高值"
                                    }
                                },
                                color3: {
                                    mid: "中间值"
                                },
                                dataBar: {
                                    showBarOnly: "仅显示数据条",
                                    auto: "自动",
                                    description2: "条形图外观Bar Appearance:",
                                    fill: "填充",
                                    color: "颜色",
                                    border: "边框",
                                    fillSelector: {
                                        solidFill: "实心填充",
                                        gradientFill: "渐变填充"
                                    },
                                    borderSelector: {
                                        noBorder: "无边框",
                                        solidBorder: "实心边框"
                                    },
                                    negativeBtn: "负值和坐标轴...",
                                    barDirection: "条形图方向:",
                                    barDirectionSelector: {
                                        l2r: "从左到右",
                                        r2l: "从右到左"
                                    },
                                    preview: "预览",
                                    negativeDialog: {
                                        title: "负值和坐标轴设置",
                                        group1: {
                                            title: "负值条形图填充颜色",
                                            fillColor: "填充颜色:",
                                            apply: "应用与正值条形图相同的填充颜色"
                                        },
                                        group2: {
                                            title: "负值条形图边框颜色",
                                            borderColor: "边框颜色:",
                                            apply: "应用与正值条形图相同的边框颜色"
                                        },
                                        group3: {
                                            title: "坐标轴设置",
                                            description: "选择单元格的坐标轴位置可更改负值条形图的外观",
                                            radio: {
                                                auto: "自动(基于负值显示在可变位置)",
                                                cell: "单元格中点值",
                                                none: "无(按正值条形图的相同方向显示负值条形图)"
                                            },
                                            axisColor: "坐标轴颜色:"
                                        }
                                    }
                                },
                                iconSets: {
                                    iconStyle: "图标样式:",
                                    showIconOnly: "仅显示图标",
                                    reverseIconOrder: "反转图标次序",
                                    display: "根据以下规则显示各个图标:",
                                    icon: "图标",
                                    value: "值",
                                    type: "类型",
                                    description1: "当值是",
                                    description2: "当 < ",
                                    operator: {
                                        largeOrEqu: "> =",
                                        large: ">"
                                    }
                                },
                                commonSelector: {
                                    num: "数字",
                                    percent: "百分比",
                                    formula: "公式",
                                    percentile: "百分点值"
                                }
                            },
                            formatContain: {
                                description: "只为满足以下条件的单元格设置格式:",
                                type: {
                                    cellValue: "单元格值",
                                    specificText: "特定文本",
                                    dateOccurring: "发生日期",
                                    blanks: "空值",
                                    noBlanks: "无空值",
                                    errors: "错误",
                                    noErrors: "无错误"
                                },
                                operator_cellValue: {
                                    between: "介于",
                                    notBetween: "未介于",
                                    equalTo: "等于",
                                    notEqualTo: "不等于",
                                    greaterThan: "大于",
                                    lessThan: "小于",
                                    greaterThanOrEqu: "大于或等于",
                                    lessThanOrEqu: "小于或等于"
                                },
                                operator_specificText: {
                                    containing: "包含",
                                    notContaining: "不包含",
                                    beginningWith: "始于",
                                    endingWith: "止于"
                                }
                            },
                            formatRankedValue: {
                                description: "为以下排名内的值设置格式:",
                                type: {
                                    top: "前",
                                    bottom: "后"
                                }
                            },
                            formatAbove: {
                                description: "为满足以下条件的值设置格式:",
                                type: {
                                    above: "高于",
                                    below: "低于",
                                    equalOrAbove: "等于或高于",
                                    equalOrBelow: "等于或低于",
                                    std1Above: "标准偏差高于 1",
                                    std1Below: "标准偏差低于 1",
                                    std2Above: "标准偏差高于 2",
                                    std2Below: "标准偏差低于 2",
                                    std3Above: "标准偏差高于 3",
                                    std3Below: "标准偏差低于 3"
                                },
                                description2: "选定范围的平均值"
                            },
                            formatUnique: {
                                description: "全部设置格式:",
                                type: {
                                    duplicate: "重复",
                                    unique: "唯一"
                                },
                                description2: "选定范围中的数值"
                            },
                            useFormula: {
                                description: "为符合此公式的值设置格式:"
                            },
                            preview: {
                                description: "预览:",
                                buttonText: "格式...",
                                noFormat: "未设定格式",
                                hasFormat: "AaBbCcYyZz"
                            }
                        },
                        withStyle: {
                            lightRedFill_DarkRedText: "浅红填充色深红色文本",
                            yellowFill_DrakYellowText: "黄填充色深黄色文本",
                            greenFill_DarkGreenText: "绿填充色深绿色文本",
                            lightRedFill: "浅红色填充",
                            redText: "红色文本",
                            redBorder: "红色边框",
                            customFormat: "自定义格式..."
                        },
                        exceptions: {
                            e1: "输入值不是有效的数字、日期、时间或字符串。",
                            e2: "输入值。",
                            e3: "输入在1和1000之间的整数。",
                            e4: "输入值不能为空。",
                            e5: "不能在条件格式公式中直接引用工作表区域。\n请将引用更改为对单个单元格的引用，或使用工作表函数进行引用，如 = SUM(A1:E5)。",
                            e6: "公式规则的源范围只能是单一范围!"
                        }
                    };

                    cn_res.formattingRulesManagerDialog = {
                        title: "条件格式规则管理器",
                        rulesScopeLabel: "显示其格式规则: ",
                        rulesScopeForSelection: "当前选择",
                        rulesScopeForWorksheet: "当前工作表",
                        newRule: "新建规则...",
                        editRule: "编辑规则...",
                        deleteRule: "删除规则...",
                        gridTitleRule: "规则(按所示顺序应用)",
                        gridTitleFormat: "格式",
                        gridTitleAppliesTo: "应用于",
                        gridTitleStopIfTrue: "如果为真则停止",
                        ruleDescriptions: {
                            valueBetween: '单元格值介于{0}和{1}之间',
                            valueNotBetween: '单元格值不介于{0}和{1}之间',
                            valueEquals: '单元格值 = {0}',
                            valueNotEquals: '单元格值 <> {0}',
                            valueGreateThan: '单元格值 > {0}',
                            valueGreateThanOrEquals: '单元格值 >= {0}',
                            valueLessThan: '单元格值 < {0}',
                            valueLessThanOrEquals: '单元格值 <= {0}',
                            valueContains: '单元格值包含 "{0}"',
                            valueNotContains: '单元格值不包含 "{0}"',
                            valueBeginsWith: '单元格值开头是 "{0}"',
                            valueEndsWith: '单元格值结尾是 "{0}"',
                            last7Days: '最近 7 天',
                            lastMonth: '上月',
                            lastWeek: '上周',
                            nextMonth: '下月',
                            nextWeek: '下周N',
                            thisMonth: '本月',
                            thisWeek: '本周',
                            today: '今天',
                            tomorrow: '明天',
                            yesterday: '昨天',
                            duplicateValues: '重复值',
                            uniqueValues: '唯一值',
                            top: '前 {0}个',
                            bottom: '后 {0}个',
                            above: '高于平均值',
                            above1StdDev: '标准偏差高于平均值1',
                            above2StdDev: '标准偏差高于平均值2',
                            above3StdDev: '标准偏差高于平均值3',
                            below: '低于平均值',
                            below1StdDev: '标准偏差低于平均值1',
                            below2StdDev: '标准偏差低于平均值2',
                            below3StdDev: '标准偏差低于平均值3',
                            equalOrAbove: '等于或高于平均值',
                            equalOrBelow: '等于或低于平均值',
                            dataBar: '数据条',
                            twoScale: '渐变颜色刻度',
                            threeScale: '渐变颜色刻度',
                            iconSet: '图标集',
                            formula: '公式: {0}'
                        },
                        previewText: 'AaBbCcYyZz'
                    };

                    cn_res.cellStylesDialog = {
                        cellStyles: "单元格样式",
                        custom: "自定义",
                        goodBadAndNeutral: "好、差和适中",
                        dataAndModel: "数据和模型",
                        titlesAndHeadings: "标题",
                        themedCellStyle: "主题单元格样式",
                        numberFormat: "数字格式",
                        goodBadAndNeutralContent: {
                            "Normal": "常规",
                            "Bad": "差",
                            "Good": "好",
                            "Neutral": "适中"
                        },
                        dataAndModelContent: {
                            "Calculation": "计算",
                            "Check Cell": "检查单元格",
                            "Explanatory Text": "解释性文本...",
                            "Input": "输入",
                            "Linked Cell": "链接单元格",
                            "Note": "注释",
                            "Output": "输出",
                            "Warning Text": "警告文本"
                        },
                        titlesAndHeadingsContent: {
                            "Heading 1": "标题 1",
                            "Heading 2": "标题 2",
                            "Heading 3": "标题 3",
                            "Heading 4": "标题 4",
                            "Title": "标题",
                            "Total": "汇总"
                        },
                        themedCellStyleContent: {
                            "20% - Accent1": "20% - 强调文字颜色 1",
                            "20% - Accent2": "20% - 强调文字颜色 2",
                            "20% - Accent3": "20% - 强调文字颜色 3",
                            "20% - Accent4": "20% - 强调文字颜色 4",
                            "20% - Accent5": "20% - 强调文字颜色 5",
                            "20% - Accent6": "20% - 强调文字颜色 6",
                            "40% - Accent1": "40% - 强调文字颜色 1",
                            "40% - Accent2": "40% - 强调文字颜色 2",
                            "40% - Accent3": "40% - 强调文字颜色 3",
                            "40% - Accent4": "40% - 强调文字颜色 4",
                            "40% - Accent5": "40% - 强调文字颜色 5",
                            "40% - Accent6": "40% - 强调文字颜色 6",
                            "60% - Accent1": "60% - 强调文字颜色 1",
                            "60% - Accent2": "60% - 强调文字颜色 2",
                            "60% - Accent3": "60% - 强调文字颜色 3",
                            "60% - Accent4": "60% - 强调文字颜色 4",
                            "60% - Accent5": "60% - 强调文字颜色 5",
                            "60% - Accent6": "60% - 强调文字颜色 6",
                            "Accent1": "强调文字颜色 1",
                            "Accent2": "强调文字颜色 2",
                            "Accent3": "强调文字颜色 3",
                            "Accent4": "强调文字颜色 4",
                            "Accent5": "强调文字颜色 5",
                            "Accent6": "强调文字颜色 6"
                        },
                        numberFormatContent: {
                            "Comma": "千位分隔",
                            "Comma [0]": "千位分隔 [0]",
                            "Currency": "货币",
                            "Currency [0]": "货币 [0]",
                            "Percent": "百分比"
                        },
                        newCellStyle: "新建单元格样式..."
                    };

                    cn_res.newCellStyleDialog = {
                        style: "样式",
                        styleName: "样式名:",
                        defaultStyleName: "样式 1",
                        format: "格式...",
                        message: "此样式名已存在。"
                    };

                    cn_res.cellStyleContextMenu = {
                        "delete": "删除",
                        modify: "修改"
                    };

                    cn_res.insertPictureDialogTitle = "插入图片";
                    cn_res.pictureFormatFilter = {
                        jpeg: "JPEG 文件交换格式(*.jpg;*.jpeg)",
                        png: "可移植网络图形(*.png)",
                        bmp: "Windows 位图(*.bmp)",
                        allFiles: "所有文件(*.*)"
                    };

                    cn_res.ribbon = {
                        accessBar: {
                            undo: "撤销",
                            redo: "恢复",
                            save: "保存",
                            New: "新建",
                            open: "打开",
                            active: "激活"
                        },
                        home: {
                            file: "文件",
                            home: "开始",
                            clipboard: "剪贴板",
                            fonts: "字体",
                            alignment: "对齐方式",
                            numbers: "数字",
                            cellType: "单元格类型",
                            styles: "样式",
                            cells: "单元格",
                            editing: "编辑",
                            paste: "粘贴",
                            all: "全部",
                            formulas: "公式",
                            values: "值",
                            formatting: "格式",
                            cut: "剪切",
                            copy: "复制",
                            fontFamily: "字体",
                            fontSize: "字号",
                            increaseFontSize: "增大字号",
                            decreaseFontSize: "减小字号",
                            bold: "加粗",
                            italic: "倾斜",
                            underline: "下划线",
                            border: "边框",
                            bottomBorder: "下边框",
                            topBorder: "上边框",
                            leftBorder: "左边框",
                            rightBorder: "右边框",
                            noBorder: "无框线",
                            allBorder: "所有框线",
                            outsideBorder: "外侧框线",
                            thickBoxBorder: "粗匣框线",
                            bottomDoubleBorder: "双底框线",
                            thickBottomBorder: "粗底框线",
                            topBottomBorder: "上下框线",
                            topThickBottomBorder: "上框线和粗下框线",
                            topDoubleBottomBorder: "上框线和双下框线",
                            moreBorders: "其他边框...",
                            backColor: "填充颜色",
                            fontColor: "字体颜色",
                            topAlign: "顶端对齐",
                            middleAlign: "垂直居中",
                            bottomAlign: "底端对齐",
                            leftAlign: "文本左对齐",
                            centerAlign: "居中",
                            rightAlign: "文本右对齐",
                            increaseIndent: "增加缩进量",
                            decreaseIndent: "减少缩进量",
                            wrapText: "自动换行",
                            mergeCenter: "合并后居中",
                            mergeAcross: "跨越合并",
                            mergeCells: "合并单元格",
                            unMergeCells: "取消单元格合并",
                            numberFormat: "数字格式",
                            general: "常规",
                            Number: "数字",
                            currency: "货币",
                            accounting: "会计专用",
                            shortDate: "短日期",
                            longDate: "长日期",
                            time: "时间",
                            percentage: "百分比",
                            fraction: "分数",
                            scientific: "科学记数",
                            text: "文本",
                            moreNumberFormat: "其他数字格式...",
                            percentStyle: "百分比样式",
                            commaStyle: "千分分隔样式",
                            increaseDecimal: "增加小数位数",
                            decreaseDecimal: "减少小数位数",
                            buttonCellType: "按钮单元格类型",
                            checkboxCellType: "复选框单元格类型",
                            comboBoxCellType: "组合框单元格类型",
                            hyperlinkCellType: "超链接单元格类型",
                            clearCellType: "清除<br>单元格<br>类型",
                            clearCellType1: "清除单元格类型",
                            conditionFormat: "条件格式",
                            conditionFormat1: "条件格式",
                            formatTable: "套用<br>表格样式",
                            formatTable1: "套用表格样式",
                            insert: "插入",
                            insertCells: "插入单元格...",
                            insertSheetRows: "插入工作表行",
                            insertSheetColumns: "插入工作表列",
                            insertSheet: "插入工作表",
                            Delete: "删除",
                            deleteCells: "删除单元格...",
                            deleteSheetRows: "删除工作表行",
                            deleteSheetColumns: "删除工作表列",
                            deleteSheet: "删除工作表",
                            format: "格式",
                            rowHeight: "行高...",
                            autofitRowHeight: "自动调整行高",
                            defaultHeight: "默认行高...",
                            columnWidth: "列宽...",
                            autofitColumnWidth: "自动调整列宽",
                            defaultWidth: "默认列宽...",
                            hideRows: "隐藏行",
                            hideColumns: "隐藏列",
                            unHideRows: "取消隐藏行",
                            unHideColumns: "取消隐藏列",
                            protectSheet: "保护工作表...",
                            unProtectSheet: "撤销工作表保护...",
                            lockCells: "锁定单元格",
                            unLockCells: "撤销单元格锁定",
                            autoSum: "自动求和",
                            sum: "求和",
                            average: "平均值",
                            countNumbers: "计数",
                            max: "最大值",
                            min: "最小值",
                            fill: "填充",
                            down: "向下",
                            right: "向右",
                            up: "向上",
                            left: "向左",
                            series: "系列...",
                            clear: "清除",
                            clearAll: "全部清除",
                            clearFormat: "清除格式",
                            clearContent: "清除内容",
                            clearComments: "清除批注",
                            sortFilter: "排序和<br>筛选",
                            sortFilter1: "排序和筛选",
                            sortAtoZ: "升序",
                            sortZtoA: "降序",
                            customSort: "自定义排序...",
                            filter: "筛选",
                            clearFilter: "清除筛选",
                            reapply: "重新应用",
                            find: "查找",
                            find1: "查找...",
                            goto: "转到..."
                        },
                        insert: {
                            insert: "插入",
                            table: "表格",
                            sparklines: "迷你图",
                            line: "折线图",
                            column: "柱形图",
                            winloss: "盈亏图",
                            insertTable: "插入表",
                            insertPicture: "插入图片",
                            insertLineSparkline: "插入折线图",
                            insertColumnSparkline: "插入柱形图",
                            insertWinlossSparkline: "插入盈亏图",
                            picture: "图片",
                            illustrations: "插图",
                            insertPieSparkline: "插入饼图",
                            insertAreaSparkline: "插入面积图",
                            insertScatterSparkline: "插入散点图",
                            pie: "饼图",
                            area: "面积图",
                            scatter: "散点图",
                            insertBulletSparkline: "插入子弹图",
                            bullet: "子弹图",
                            insertSpreadSparkline: "插入散布图",
                            spread: "散布图",
                            insertStackedSparkline: "插入堆积图",
                            stacked: "堆积图",
                            insertHbarSparkline: "插入水平条形图",
                            hbar: "水平<br>条形图",
                            insertVbarSparkline: "插入垂直条形图",
                            vbar: "垂直<br>条形图",
                            insertVariSparkline: "插入方差图",
                            variance: "方差图",
                            insertBoxPlotSparkline: "插入箱线图",
                            boxplot: "箱线图",
                            insertCascadeSparkline: "插入组成瀑布图",
                            cascade: "组成<br>瀑布图",
                            insertParetoSparkline: "插入阶梯瀑布图",
                            pareto: "阶梯<br>瀑布图"
                        },
                        formulas: {
                            formulas: "公式",
                            insertFunction: "插入<br>函数",
                            insertFunction1: "插入函数",
                            functions: "函数库",
                            names: "定义的名称",
                            nameManager: "名称<br>管理器",
                            nameManager1: "名称管理器"
                        },
                        data: {
                            data: "数据",
                            sortFilter: "排序和筛选",
                            dataTools: "数据工具",
                            outline: "分级显示",
                            sortAtoZ: "升序",
                            sortZtoA: "降序",
                            sort: "排序",
                            customSort: "排序...",
                            filter: "筛选",
                            clear: "清除",
                            clearFilter: "清除筛选",
                            reapply: "重新应用",
                            dataValidation: "数据验证",
                            dataValidation1: "数据验证",
                            circleInvalidData: "圈释无效数据",
                            clearInvalidCircles: "清除无效数据标识圈",
                            group: "创建组",
                            unGroup: "取消组合",
                            showDetail: "显示明细数据",
                            hideDetail: "隐藏明细数据",
                            designMode: "设计模式",
                            enterTemplate: "输入设计模式模板",
                            template: "模板",
                            bindingPath: "绑定路径",
                            loadSchemaTitle: "加载模板结构获取树图",
                            loadSchema: "加载模板结构",
                            loadDataSourceFilter: {
                                json: "JSON 文件(*.json)",
                                txt: "文本文件(*.txt)"
                            },
                            saveDataSourceFilter: {
                                json: "JSON 文件(*.json)"
                            },
                            saveSchemaTitle: "保存模板结构信息到 json 文件",
                            saveSchema: "保存模板结构",
                            autoGenerateColumns: "自动生成列",
                            columns: "列",
                            name: "名称",
                            details: "明细数据",
                            ok: "确定",
                            cancel: "取消",
                            loadDataError: "请加载 json 文件。",
                            addNode: "添加节点",
                            remove: "删除",
                            rename: "重命名",
                            table: "表格",
                            selectOptions: "选择选项",
                            clearBindingPath: "清除绑定路径",
                            dataField: "数据字段",
                            warningTable: "表的列数将发生变化。是否继续执行?",
                            warningDataField: "是否无论如何都要将自动生成列设置为否和设置数据字段？",
                            checkbox: "复选框",
                            hyperlink: "超链接",
                            combox: "组合框",
                            button: "按钮",
                            text: "文本",
                            autoGenerateLabel: "自动生成标签",
                            autoGenerateLabelTip: "自动生成数据标签",
                            unallowableTableBindingTip: "数据字段只能设置在表上，请选择一个表。",
                            overwriteCellTypeWarning: "工作表上的某些单元格类型将被覆盖或修改，是否继续执行？",
                            removeNodeWarning: "删除的节点包含子节点，是否要删除？",
                            unallowComboxBindingTip: "组合框的集合只能在组合框中设置，请选择一个组合框。"
                        },
                        view: {
                            view: "视图",
                            showHide: "显示/隐藏",
                            zoom: "显示比例",
                            viewport: "窗口",
                            rowHeader: "行标题",
                            columnHeader: "列标题",
                            verticalGridline: "垂直网格线",
                            horizontalGridline: "水平网格线",
                            tabStrip: "工作表选项卡",
                            newTab: "新建工作表",
                            rowHeaderTip: "显示/隐藏行标题",
                            columnHeaderTip: "显示/隐藏列标题",
                            verticalGridlineTip: "显示/隐藏垂直网格线",
                            horizontalGridlineTip: "显示/隐藏水平网格线",
                            tabStripTip: "显示/隐藏工作表选项卡",
                            newTabTip: "显示/隐藏新建工作表",
                            zoomToSelection: "缩放到选定区域",
                            zoomToSelection1: "缩放到选定区域",
                            freezePane: "冻结窗格",
                            freezePane1: "冻结窗格",
                            freezeTopRow: "冻结首行",
                            freezeFirstColumn: "冻结首列",
                            freezeBottomRow: "冻结底端行",
                            freezeLastColumn: "冻结最后列",
                            unFreezePane: "取消<br>冻结窗格",
                            unFreezePane1: "取消冻结窗格"
                        },
                        setting: {
                            setting: "设置",
                            spreadSetting: "Spread 设置",
                            sheetSetting: "工作表设置",
                            general: "常规",
                            generalTip: "常规设置",
                            scrollBars: "滚动条",
                            tabStrip: "选项卡",
                            gridLines: "网格线",
                            calculation: "计算",
                            headers: "标题"
                        },
                        sparkLineDesign: {
                            design: "设计",
                            type: "类型",
                            show: "显示",
                            style: "样式",
                            groups: "分组",
                            line: "线条",
                            column: "柱形图",
                            winLoss: "盈亏",
                            lineTip: "折线图",
                            columnTip: "柱形图",
                            winLossTip: "盈亏迷你图",
                            highPoint: "高点",
                            lowPoint: "低点",
                            negativePoint: "负点",
                            firstPoint: "首点",
                            lastPoint: "尾点",
                            markers: "标记",
                            highPointTip: "切换迷你图高点",
                            lowPointTip: "切换迷你图低点",
                            negativePointTip: "切换迷你图负点",
                            firstPointTip: "切换迷你图首点",
                            lastPointTip: "切换迷你图尾点",
                            markersTip: "切换迷你图标记",
                            sparklineColor: "迷你图颜色",
                            markerColor: "标记颜色",
                            sparklineWeight: "迷你图粗细",
                            customWeight: "自定义粗细...",
                            group: "组合",
                            groupTip: "组合所选迷你图",
                            unGroupTip: "取消组合所选迷你图",
                            unGroup: "取消组合",
                            clear: "清除",
                            clearSparkline: "清除所选的迷你图",
                            clearSparklineGroup: "清除所选的迷你图组"
                        },
                        formulaSparklineDesign: {
                            design: "设计",
                            argument: "参数",
                            settings: "设置"
                        },
                        tableDesign: {
                            design: "设计",
                            tableName: "表名称",
                            resizeTable: "调整表格大小",
                            tableOption: "表式样选项",
                            property: "属性",
                            headerRow: "标题行",
                            totalRow: "汇总行",
                            bandedRows: "镶边行",
                            firstColumn: "第一列",
                            lastColumn: "最后一列",
                            bandedColumns: "镶边列",
                            filterButton: "筛选按钮",
                            tableStyle: "表式样",
                            style: "式样",
                            tools: "工具",
                            insertSlicer: "插入切片器"
                        },
                        fontFamilies: {
                            cnff1: {name: "宋体", text: "宋体"},
                            cnff2: {name: "楷体", text: "楷体"},
                            cnff3: {name: "仿宋", text: "仿宋"},
                            cnff4: {name: "黑体", text: "黑体"},
                            cnff5: {name: "新宋体", text: "新宋体"},
                            cnff6: {name: "SimSun", text: "SimSun"},
                            cnff7: {name: "KaiTi", text: "KaiTi"},
                            cnff8: {name: "FangSong", text: "FangSong"},
                            cnff9: {name: "SimHei", text: "SimHei"},
                            cnff10: {name: "NSimSun", text: "NSimSun"},
                            ff1: { name: "Arial", text: "Arial" },
                            ff2: { name: "Arial Black", text: "Arial Black" },
                            ff3: { name: "Calibri", text: "Calibri" },
                            ff4: { name: "Cambria", text: "Cambria" },
                            ff5: { name: "Candara", text: "Candara" },
                            ff6: { name: "Century", text: "Century" },
                            ff7: { name: "Courier New", text: "Courier New" },
                            ff8: { name: "Comic Sans MS", text: "Comic Sans MS" },
                            ff9: { name: "Garamond", text: "Garamond" },
                            ff10: { name: "Georgia", text: "Georgia" },
                            ff11: { name: "Malgun Gothic", text: "Malgun Gothic" },
                            ff12: { name: "Mangal", text: "Mangal" },
                            ff13: { name: "Meiryo", text: "Meiryo" },
                            ff14: { name: "MS Gothic", text: "MS Gothic" },
                            ff15: { name: "MS Mincho", text: "MS Mincho" },
                            ff16: { name: "MS PGothic", text: "MS PGothic" },
                            ff17: { name: "MS PMincho", text: "MS PMincho" },
                            ff18: { name: "Tahoma", text: "Tahoma" },
                            ff19: { name: "Times", text: "Times" },
                            ff20: { name: "Times New Roman", text: "Times New Roman" },
                            ff21: { name: "Trebuchet MS", text: "Trebuchet MS" },
                            ff22: { name: "Verdana", text: "Verdana" },
                            ff23: { name: "Wingdings", text: "Wingdings" }
                        },
                        slicerOptions: {
                            options: "选项",
                            slicerCaptionShow: "切片器标题:",
                            slicerCaption: "切片器标题",
                            slicerSettings: "切片器设置",
                            slicer: "切片器",
                            styles: "样式",
                            slicerStyles: "切片器样式",
                            columnsShow: "列:",
                            heightShow: "高度:",
                            widthShow: "宽度:",
                            columns: "列",
                            height: "高度",
                            width: "宽度",
                            buttons: "按钮",
                            size: "大小",
                            shapeHeight: "形状高度",
                            shapeWidth: "形状宽度"
                        }
                    };

                    cn_res.seriesDialog = {
                        series: "序列",
                        seriesIn: "序列产生在",
                        rows: "行",
                        columns: "列",
                        type: "类型",
                        linear: "等差序列",
                        growth: "等比序列",
                        date: "日期",
                        autoFill: "自动填充",
                        dateUnit: "日期单位",
                        day: "日",
                        weekday: "工作日",
                        month: "月",
                        year: "年",
                        trend: "预测趋势",
                        stepValue: "步长值",
                        stopValue: "终止值"
                    };

                    cn_res.customSortDialog = {
                        sort: "排序",
                        addLevel: "添加条件",
                        deleteLevel: "删除条件",
                        copyLevel: "复制条件",
                        options: "选项...",
                        sortBy: "列",
                        sortBy2: "主要关键字",
                        thenBy: "次要关键字",
                        sortOn: "排序依据",
                        sortOrder: "次序",
                        sortOptions: "排序选项",
                        orientation: "方向",
                        sortTopToBottom: "按列排序",
                        sortLeftToRight: "按行排序"
                    };

                    cn_res.createTableDialog = {
                        createTable: "创建表",
                        whereYourTable: "表数据的来源"
                    };

                    cn_res.createSparklineDialog = {
                        createSparkline: "创建图表",
                        dataRange: "图表数据区域",
                        locationRange: "图表位置区域",
                        chooseData: "选择数据源",
                        chooseWhere: "选择放置图表的位置",
                        warningText: "位置不合法因为单元格没有在同一列或者同一行上。请选择同一列的单元格或者同一行。"
                    };

                    cn_res.dataValidationDialog = {
                        dataValidation: "数据有效性",
                        settings: "设置",
                        validationCriteria: "有效性条件",
                        allow: "允许",
                        anyValue: "任何值",
                        wholeNumber: "整数",
                        decimal: "小数",
                        list: "序列",
                        date: "日期",
                        textLength: "文本长度",
                        custom: "自定义",
                        data: "数据",
                        dataLabel: "数据:",
                        between: "介于",
                        notBetween: "未介于",
                        equalTo: "等于",
                        notEqualTo: "不等于",
                        greaterThan: "大于",
                        lessThan: "小于",
                        greaterEqual: "大于或等于",
                        lessEqual: "小于或等于",
                        minimum: "最小值:",
                        maximum: "最大值:",
                        value: "数值:",
                        startDate: "开始日期:",
                        endDate: "结束日期:",
                        dateLabel: "日期:",
                        length: "长度:",
                        source: "来源:",
                        formula: "公式:",
                        ignoreBlank: "忽略空值",
                        inCellDropDown: "提供下拉箭头",
                        inputMessage: "输入信息",
                        errorAlert: "出错警告",
                        showMessage: "选定单元格时显示输入信息",
                        showMessage2: "选定单元格时显示下列输入信息: ",
                        title: "标题",
                        showError: "输入无效数据时显示出错警告",
                        showError2: "输入无效数据时显示下列出错警告:",
                        style: "样式:",
                        stop: "停止",
                        warning: "警告",
                        information: "信息",
                        errorMessage: "错误信息",
                        clearAll: "全部清除",
                        valueEmptyMessage: "必须输入数值。",
                        minimumMaximumMessage: "最大值必须大于或等于最小值.",
                        errorMessage1: "输入值不合法。\r\n 用户已经限制了此单元格输入值。",
                        errorMessage2: "输入值不合法。\r\n 用户已经限制了次单元格输入值。\r\n是否继续?"
                    };

                    cn_res.spreadSettingDialog = {
                        spreadSetting: "Spread 设置",
                        general: "常规",
                        settings: "设置",
                        allowDragDrop: "允许拖拽",
                        allowFormula: "允许用户输入公式",
                        allowDragFill: "运行拖动和填充",
                        allowZoom: "允许缩放",
                        allowUndo: "允许撤销",
                        allowOverflow: "允许溢出",
                        scrollBars: "滚动条",
                        visibility: "可见性",
                        verticalScrollBar: "垂直滚动条",
                        horizontalScrollBar: "水平滚动条",
                        scrollbarShowMax: "滚动条最大显示",
                        scrollbarMaxAlign: "滚动条最大对齐",
                        tabStrip: "工作表标签",
                        tabStripVisible: "工作表标签可见",
                        tabStripEditable: "工作表标签可编辑",
                        newTabVisible: "新建工作表可见",
                        tabStripRatio: "工作表标签比例(百分比)"
                    };

                    cn_res.sheetSettingDialog = {
                        sheetSetting: "工作表设置",
                        general: "常规",
                        columnCount: "列数",
                        rowCount: "行数",
                        frozenColumnCount: "冻结列数",
                        frozenRowCount: "冻结行数",
                        trailingFrozenColumnCount: "最后面冻结列数",
                        trailingFrozenRowCount: "最后面冻结行数",
                        selectionPolicy: "选择策略",
                        singleSelection: "单选",
                        rangeSelection: "区域选择",
                        multiRangeSelection: "多区域选择",
                        protect: "保护",
                        gridlines: "网格线",
                        horizontalGridline: "水平网格线",
                        verticalGridline: "垂直网格线",
                        gridlineColor: "网格线颜色",
                        calculation: "计算",
                        referenceStyle: "引用样式",
                        a1: "A1",
                        r1c1: "R1C1",
                        headers: "标题",
                        columnHeaders: "列标题",
                        rowHeaders: "行标题",
                        columnHeaderRowCount: "列标题行数",
                        columnHeaderAutoText: "列标题自动生成文本",
                        columnHeaderAutoIndex: "列标题自动索引",
                        defaultRowHeight: "默认行高",
                        columnHeaderVisible: "列标题可见",
                        blank: "空白",
                        numbers: "数字",
                        letters: "字母",
                        rowHeaderColumnCount: "行标题列数",
                        rowHeaderAutoText: "行标题自动生成文本",
                        rowHeaderAutoIndex: "行标题自动索引",
                        defaultColumnWidth: "默认列宽",
                        rowHeaderVisible: "行标题可见",
                        sheetTab: "工作表标签",
                        sheetTabColor: "工作表标签颜色:"
                    };

                    cn_res.groupDirectionDialog = {
                        settings: "设置",
                        direction: "方向",
                        rowDirection: "明细数据的下方",
                        columnDirection: "明细数据的右侧"
                    };

                    cn_res.insertSparklineDialog = {
                        createSparklines: "插入图表",
                        dataRange: "图表数据区域:",
                        dataRangeTitle: "选择数据源",
                        locationRange: "放置图表的位置",
                        locationRangeTitle: "选择放置图表的位置",
                        errorDataRangeMessage: "请输入合法的区域",
                        isFormulaSparkline: "公式图表"
                    };

                    cn_res.sparklineWeightDialog = {
                        sparklineWeight: "图表线宽",
                        inputWeight: "输入图表线宽(pt)",
                        errorMessage: "请输入合法的线宽。"
                    };

                    cn_res.sparklineMarkerColorDialog = {
                        sparklineMarkerColor: "图表标记颜色:",
                        negativePoints: "负数:",
                        markers: "标记:",
                        highPoint: "最大值:",
                        lowPoint: "最小值:",
                        firstPoint: "第一个值:",
                        lastPoint: "最后一个值:"
                    };

                    cn_res.resizeTableDialog = {
                        title: "调整表格大小",
                        dataRangeTitle: "为表选择新的数据区域:",
                        note: "注释:  标题必须保留在同一行上，\r\n结果表区域必须重叠原始表区域。"
                    };

                    cn_res.saveAsDialog = {
                        title: "保存文件",
                        fileNameLabel: "文件名:"
                    };

                    cn_res.statusBar = {
                        ready: "准备",
                        enter: "输入",
                        edit: "编辑"
                    };

                    cn_res.pieSparklineDialog = {
                        percentage: "百分比",
                        color: "颜色",
                        addColor: "添加颜色",
                        pieSparklineSetting: "饼图设置"
                    };

                    cn_res.areaSparklineDialog = {
                        title: "面积图公式",
                        points: "点",
                        min: "最小值",
                        max: "最大值",
                        line1: "线条 1",
                        line2: "线条 2",
                        positiveColor: "正数颜色",
                        negativeColor: "负数颜色",
                        areaSparklineSetting: "面积图设置"
                    };

                    cn_res.scatterSparklineDialog = {
                        points1: "点1",
                        points2: "点2",
                        minX: "X轴最小值",
                        maxX: "X轴最大值",
                        minY: "Y轴最小值",
                        maxY: "Y轴最大值",
                        hLine: "水平轴",
                        vLine: "垂直轴",
                        xMinZone: "X轴最小区域",
                        yMinZone: "Y轴最小区域",
                        xMaxZone: "X轴最大区域",
                        yMaxZone: "Y轴最大区域",
                        tags: "标签",
                        drawSymbol: "绘制符号",
                        drawLines: "绘制线",
                        color1: "颜色 1",
                        color2: "颜色 2",
                        dash: "划线",
                        scatterSparklineSetting: "散点图设置"
                    };

                    cn_res.compatibleSparklineDialog = {
                        title: "compatibleSparkline公式",
                        style: "样式",
                        show: "显示",
                        group: "分组",
                        data: "数据",
                        dataOrientation: "数据方向",
                        dateAxisData: "日期轴数据",
                        dateAxisOrientation: "日期轴方向",
                        settting: "设置",
                        axisColor: "轴",
                        firstMarkerColor: "首标记",
                        highMarkerColor: "最高标记",
                        lastMarkerColor: "尾标记",
                        lowMarkerColor: "最低标记",
                        markersColor: "标记",
                        negativeColor: "负数",
                        seriesColor: "系列",
                        displayXAxis: "显示X轴",
                        showFirst: "显示首",
                        showHigh: "显示最高",
                        showLast: "显示尾",
                        showLow: "显示最低",
                        showNegative: "显示负数",
                        showMarkers: "显示标记",
                        lineWeight: "线粗细",
                        displayHidden: "在隐藏行和列中显示数据",
                        displayEmptyCellsAs: "空单元格显示为",
                        rightToLeft: "从右到左",
                        minAxisType: "最小轴类型",
                        maxAxisType: "最大轴类型",
                        manualMax: "手动最大",
                        manualMin: "手动最小",
                        gaps: "空白",
                        zero: "0",
                        connect: "连接",
                        vertical: "垂直",
                        horizontal: "水平",
                        stylesetting: "样式设置",
                        individual: "个别的",
                        custom: "自定义",
                        compatibleSparklineSetting: "compatibleSparkline设置",
                        styleSetting: "样式设置",
                        errorMessage: "请输入合法区域。"
                    };

                    cn_res.bulletSparklineDialog = {
                        bulletSparklineSetting: "子弹图设置",
                        measure: "测量",
                        target: "目标",
                        maxi: "Maxi",
                        good: "好",
                        bad: "坏",
                        forecast: "预测",
                        tickunit: "单位",
                        colorScheme: "主题颜色",
                        vertical: "垂直"
                    };

                    cn_res.spreadSparklineDialog = {
                        spreadSparklineSetting: "SpreadSparkline配置",
                        points: "点",
                        showAverage: "显示平均值",
                        scaleStart: "刻度起始",
                        scaleEnd: "刻度终止",
                        style: "样式",
                        colorScheme: "主题颜色",
                        vertical: "垂直",
                        stacked: "堆叠",
                        spread: "分散",
                        jitter: "抖动",
                        poles: "极点",
                        stackedDots: "堆积点",
                        stripe: "条纹"
                    };

                    cn_res.stackedSparklineDialog = {
                        stackedSparklineSetting: "StackedSparkline设置",
                        points: "点",
                        colorRange: "颜色范围",
                        labelRange: "标签范围",
                        maximum: "最大值",
                        targetRed: "目标红色",
                        targetGreen: "目标绿色",
                        targetBlue: "目标蓝色",
                        targetYellow: "目标黄色",
                        color: "颜色",
                        highlightPosition: "高亮位置",
                        vertical: "垂直",
                        textOrientation: "文本方向",
                        textSize: "文本大小",
                        textHorizontal: "水平",
                        textVertical: "垂直",
                        px: "px"
                    };

                    cn_res.barbaseSparklineDialog = {
                        hbarSparklineSetting: "HbarSparkline设置",
                        vbarSparklineSetting: "VbarSparkline设置",
                        value: "值",
                        colorScheme: "主题颜色"
                    };

                    cn_res.variSparklineDialog = {
                        variSparklineSetting: "VariSparkline设置",
                        variance: "方差",
                        reference: "参考",
                        mini: "最小",
                        maxi: "最大",
                        mark: "标记",
                        tickunit: "刻度单位",
                        legend: "图例",
                        colorPositive: "正数颜色",
                        colorNegative: "负数颜色",
                        vertical: "垂直"
                    };
                    cn_res.boxplotSparklineDialog = {
                        boxplotSparklineSetting: "BoxPlotSparkline设置",
                        points: "点",
                        boxPlotClass: "方块图类",
                        showAverage: "显示平均",
                        scaleStart: "刻度起始",
                        scaleEnd: "刻度终止",
                        acceptableStart: "可接受起始",
                        acceptableEnd: "可接受终止",
                        colorScheme: "主题颜色",
                        style: "样式",
                        vertical: "垂直",
                        fiveNS: "5NS",
                        sevenNS: "7NS",
                        tukey: "Tukey",
                        bowley: "Bowley",
                        sigma: "西格玛3",
                        classical: "传统",
                        neo: "新"
                    };
                    cn_res.cascadeSparklineDialog = {
                        cascadeSparklineSetting: "CascadeSparkline设置",
                        pointsRange: "点范围",
                        pointIndex: "点指数",
                        labelsRange: "标签范围",
                        minimum: "最小值",
                        maximum: "最大值",
                        colorPositive: "正数颜色",
                        colorNegative: "负数颜色",
                        vertical: "垂直"
                    };

                    cn_res.multiCellFormula = {
                        warningText: "选择区域中可能包含不同的公式类别。请选择一个新区域。"
                    };

                    cn_res.paretoSparklineDialog = {
                        paretoSparklineSetting: "ParetoSparkline设置",
                        points: "点",
                        pointIndex: "点指数",
                        colorRange: "颜色范围",
                        target: "目标",
                        target2: "目标2",
                        highlightPosition: "高亮位置",
                        label: "标签",
                        vertical: "垂直",
                        none: "无",
                        cumulated: "堆叠",
                        single: "单个"
                    };

                    cn_res.sliderPanel = {
                        title: "字段列表"
                    };

                    cn_res.protectionOptionDialog = {
                        title: "包含工作表",
                        label: "允许此工作表的所有用户进行:",
                        allowSelectLockedCells: "选择锁定单元格",
                        allowSelectUnlockedCells: "选择未锁定的单元格",
                        allowSort: "排序",
                        allowFilter: "使用自动筛选",
                        allowResizeRows: "调整行大小",
                        allowResizeColumns: "调整列大小",
                        allowEditObjects: "编辑对象"
                    };
                    cn_res.activateToolNotFound = "激活工具没有找到，请尝试重新安装SpreadJS设计器。";

                    cn_res.insertSlicerDialog = {
                        insertSlicer: "插入切片器"
                    };

                    cn_res.formatSlicerStyle = {
                        custom: "自定义",
                        light: "浅色",
                        dark: "深色",
                        other: "其他",
                        newSlicerStyle: "新建切片器样式...",
                        slicerStyle: "切片器样式",
                        name: "名称",
                        slicerElement: "切片器元素",
                        format: "格式",
                        clear: "清除",
                        preview: "预览",
                        exception: "此样式名已存在。"
                    };

                    cn_res.slicerElement = {
                        wholeSlicer: "整个切片器",
                        header: "页眉",
                        selectedItemWithData: "已选择带有数据的项目",
                        selectedItemWithNoData: "已选择无数据的项目",
                        unselectedItemWithData: "已取消选择带有数据的项目",
                        unselectedItemWithNoData: "已取消选择无数据的项目",
                        hoveredSelectedItemWithData: "悬停已选择的带有数据的项目",
                        hoveredSelectedItemWithNoData: "悬停已选择的无数据的项目",
                        hoveredUnselectedItemWithData: "悬停已取消选择的带有数据的项目",
                        hoveredUnselectedItemWithNoData: "悬停已取消选择的无数据的项目"
                    };

                    cn_res.slicerSettingDialog = {
                        slicerSetting: "切片器设置",
                        sourceName: "源名称:",
                        name: "名称:",
                        header: "页眉",
                        display: "显示页眉",
                        caption: "标题:",
                        items: "项目排序和筛选",
                        ascending: "升序(最小到最大)",
                        descending: "降序(最大到最小)",
                        customList: "排序时使用自定义列表",
                        hideItem: "隐藏没有数据的项",
                        visuallyItem: "直观地指示空数据项",
                        showItem: "最后显示空数据项"
                    };

                    cn_res.slicerPropertyDialog = {
                        formatSlicer: "格式切片器",
                        position: "位置和布局",
                        size: "大小",
                        properties: "属性",
                        pos: "位置",
                        horizontal: "水平:",
                        vertial: "垂直:",
                        disableResizingMoving: "禁用调整大小和移动",
                        layout: "框架",
                        numberColumn: "列数:",
                        buttonHeight: "按钮高度:",
                        buttonWidth: "按钮宽度:",
                        height: "高度:",
                        width: "宽度:",
                        scaleHeight: "缩放高度:",
                        scaleWidth: "缩放宽度:",
                        moveSize: "大小和位置随单元格而变",
                        moveNoSize: "大小固定，位置随单元格而变",
                        noMoveSize: "大小和位置均固定",
                        locked: "锁定"
                    };

                    cn_res.name = "cn";

                    designer.res = cn_res;
                })(designer.cn_res || (designer.cn_res = {}));
                var cn_res = designer.cn_res;
            })(Sheets.designer || (Sheets.designer = {}));
            var designer = Sheets.designer;
        })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
