/**
 * 本js为网页打印封装
 * 调用插件printThis.js
 * 
 */

/**
 * spread打印
 */
function spreadPrint(param) {
    
    //打印前执行
    if (typeof param.beforePrint === "function") {
        param.beforePrint();
    }


    var spread = param.spread;
    var sheet;
    for (var name in spread.sheets) {
        if (spread.sheets.hasOwnProperty(name)) {
            sheet = spread.sheets[name];
            break;
        }
    }
    var namedStyles = spread.namedStyles;
    var user, date;
    var foot = param.foot;
    if (foot && foot.user) {
        user = foot.user;
    }
    if (foot && foot.date) {
        date = foot.date;
    }
    var printNum = param.pageCount === undefined ? 1 : param.pageCount;
    var tableWidth = 0;
    var style = "<style>table {border-collapse: collapse;}table td,table th {border: 0px solid #000000;word-break:break-all; word-wrap:break-word;white-space:nowrap;overflow:hidden} .pageBreak{page-break-after:always;}</style>";
    var tablePages = createTable(sheet, namedStyles);
    var printHTML = "";
    for (var page = 0; page < tablePages.length; page++) {
        printHTML += tablePages[page] + "<div style='height:0'></div>";
        if (tablePages[page + 1] === undefined || (page + 1) % printNum === 0) {
            //分页符
            printHTML += "<div class='pageBreak'></div>";
        }
    }
    var printPage = style + printHTML;
    $(printPage).printThis({
        complete: param.complete//打印完成
    });


    function createTable(sheet, namedStyles) {
        var vAlign = ["top", "middle", "bottom"];
        var hAlign = ["left", "center", "right"];
        if (!sheet) {//为空则返回
            return;
        }
        var columnCount = sheet.columnCount;
        var rowCount = sheet.rowCount;
        var data = sheet.data.dataTable;
        var spans = sheet.spans || [];
        var columns = sheet.columns;
        var rows = sheet.rows;
        var namedStyles = namedStyles || sheet.namedStyles || [];
        var namedStylesObject = {};
        //重新构造样式表
        for (var i = 0; i < namedStyles.length; i++) {
            var value = namedStyles[i];
            namedStylesObject[value.name] = value;
        }

        var table = document.createElement("table");
        table.cellPadding = 0;
        table.cellSpacing = 0;
        var tableHTML = "";
        for (var i = 0; i < rowCount; i++) {
            var tr = document.createElement("tr");
            var trSize = (rows && rows[i] ? rows[i].size : "20") + "px";
            var trHTML = "";
            tr.style.height = trSize;
            if (rows && rows[i] && rows[i].visible === false) {
                tr.style.display = "none";
            }
            for (var j = 0; j < columnCount; j++) {
                if (!data[i]) {
                    break;
                }
                var column = data[i][j];
                var td = document.createElement("td");
                var tdSize = (columns && columns[j] ? columns[j].size : "120") + "px";
                //td.style.width = tdSize;
                td.style.maxWidth = tdSize;
                td.style.minWidth = tdSize;
                var style;
                if (column) {
                    if (typeof column.style === "string") {
                        style = namedStylesObject[column.style];
                    } else if (typeof column.style === "object") {
                        style = column.style;
                    }
                    var wordWrap = style.wordWrap;
                    var font_style = style ? style.font : "";
                    var align = style ? hAlign[style.hAlign] : "left"; //默认值
                    var valign = style ? vAlign[style.vAlign] : "top"; //默认值
                    var backColor = style ? style.backColor : "#ffffff";
                    var border = {
                        top: style && style.borderTop ? style.borderTop.style : "",
                        left: style && style.borderLeft ? style.borderLeft.style : "",
                        bottom: style && style.borderBottom ? style.borderBottom.style : "",
                        right: style && style.borderRight ? style.borderRight.style : ""
                    }
                    //td.style.width = tdSize;
                    td.style.font = font_style;

                    td.style.borderTopWidth = border.top + "px";
                    td.style.borderLeftWidth = border.right + "px";//最左侧单元格没有左边框
                    td.style.borderBottomWidth = border.bottom + "px";
                    td.style.borderRightWidth = border.right + "px";
                    //边框背景色
                    td.style.borderTopColor = style && style.borderTop ? style.borderTop.color : "";
                    td.style.borderLeftColor = style && style.borderLeft ? style.borderLeft.color : "";
                    td.style.borderBottomColor = style && style.borderBottom ? style.borderBottom.color : "";
                    td.style.borderRightColor = style && style.borderRight ? style.borderRight.color : "";

                    td.style.backgroundColor = backColor;
                    if (wordWrap) {
                        td.style.whiteSpace = "normal";
                    }
                    td.align = align || "left";
                    td.vAlign = valign || "top";
                    td.innerHTML = formattValue(column.value, style).toString();
                }
                trHTML += td.outerHTML;

                if (i === 0) {
                    tableWidth += parseInt(td.style.width);
                }
            }
            tr.innerHTML = trHTML;
            tableHTML += tr.outerHTML;
            table.style.width = tableWidth + "px";
        }
        table.innerHTML = tableHTML;
        //根据数据合并单元格
        for (var index = 0; index < spans.length; index++) {
            var v = spans[index];
            var cell = table.getElementsByTagName("tr")[v.row].getElementsByTagName("td")[v.col];
            cell.rowSpan = v.rowCount;
            cell.colSpan = v.colCount;
            //根据合并列隐藏单元格
            for (var i = 0; i < v.colCount; i++) {
                for (var j = 0; j < v.rowCount; j++) {
                    var mergeCell = table.getElementsByTagName("tr")[v.row + j].getElementsByTagName("td")[v.col + i];
                    if (i != 0 || j != 0) {
                        mergeCell.style.display = "none";
                    }
                }
            }
        }
        //计算合并列宽度 删除隐藏单元格 因为删除会改变索引 所以再次循环
        var colSpanCell = table.getElementsByTagName("td");
        for (var index = 0; index < colSpanCell.length; index++) {
            var cell = colSpanCell[index];
            var number = 1;
            if (cell.colSpan) {
                number = parseInt(cell.colSpan);
            } else {
                break;
            }
            var cellWidth = parseInt(cell.style.width);
            for (var i = 0; i < number - 1; i++) {
                var element = cell.nextSibling;
                cellWidth += parseInt(element.style.width);
                element.parentNode.removeChild(element);
            }
            cell.style.width = cellWidth + "px";
        }

        return newPage(table, rows);


        //格式化
        function formattValue(value, style) {
            var formatter = "";
            if (!SSF) {
                console.error("Spread Sheet Format插件未引入 打印数据无法格式化");
                return value;
            }
            if (style && style.formatter) {
                formatter = style.formatter;
            }
            value = spreadType(value);

            if (typeof value === "string") {
                try {
                    var func = value.replace(/\//g, "").replace(/\(.*?\)/g, '');
                    if (typeof eval(func) === "function") {
                        value = eval(value.replace(/\//g, ""));//执行spread内置函数 在下面创建函数逻辑
                    }
                } catch (error) {

                }
                if (formatter) {
                    return SSF.format(formatter, value);
                }
                return value;
            } else if (typeof value === "number") {
                if (formatter) {
                    return SSF.format(formatter, value);
                }
                return value;
            } else {
                return value;
            }
        }
        function spreadType(value) {
            var result;
            if (typeof value === "number") { //纯数字
                result = value;
            } else {
                if (isNaN(value)) { //不是数字字符串
                    result = value || "";
                } else {
                    if (value >= 0) { // "0001"这种形式
                        result = value;
                    } else { // "0.01"这种形式
                        result = value * 1;
                    }
                }
            }
            return result;
        }
        //spread内部日期函数
        function OADate(value) {
            var times = value * 24 * 60 * 60 * 1000 + (-2209190400000);
            var date = new Date(times);
            //console.log(date.toLocaleDateString());
            var result = {
                year: date.getFullYear(),
                month: date.getMonth() + 1,
                day: date.getDay()
            }
            //日期格式待定
            return result.year + "-" + result.month + "-" + result.day;
        }

        //分页联打
        function newPage(table, rows) {
            var trList = [];
            var tableWidth = table.style.width;
            var tableList = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                var tr = table.getElementsByTagName("tr")[i];
                if (row && row.pageBreak) {
                    var tableDom = document.createElement("table");
                    tableDom.innerHTML = trList.join("");
                    tableDom.style.width = tableWidth + "px";
                    tableList.push(tableDom.outerHTML);
                    // tableList.push($("<table style='width:" + tableWidth + "'>").append(trList.join("")));
                    trList.length = 0;
                }
                trList.push(tr.outerHTML);
                if (i === rows.length - 1) {
                    //tableList.push($("<table style='width:" + tableWidth + "'>").append(trList.join("")));
                    var tableDom = document.createElement("table");
                    tableDom.innerHTML = trList.join("");
                    tableDom.style.width = tableWidth + "px";
                    tableList.push(tableDom.outerHTML);
                }
            }
            return tableList;
        }
    }
}

/**
 * 列表打印
 */
function tablePrint(param) {
    //打印前执行
    if (typeof param.beforePrint === "function") {
        param.beforePrint();
    }

    var data = param.data;
    var title = param.title;
    var column = param.column;
    var fn = param.fn || {};
    var head = param.head;
    var group = param.group;
    var user, date;
    var foot = param.foot;
    if (foot && foot.user) {
        user = foot.user;
    }
    if (foot && foot.date) {
        date = foot.date;
    }

    var style = "<style>table{border:0;border-collapse:collapse;font-size:12px;} table tr{height:26px;} table td,table th{border:1px solid black;padding-top:3px;padding-bottom:3px;} h1{text-align:center;} table.head td,table.head th{border:none;} </style>"
    var tableHTML = createHTMLByTable(param);

    var printPage = style + tableHTML;
    var $div = $("<div>").append(printPage);
    $div.printThis({ printContainer: false, complete: param.complete });
}

/**
 * 列表打印转化函数
 * 根据html数据转化成table格式
 */
function createHTMLByTable(param) {
    var data = param.data;
    var title = param.title;
    var column = param.column;
    var fn = param.fn || {};
    var group = param.group;
    var head = param.head;
    var tableWidth = 0;

    var table = document.createElement("table");
    table.cellPadding = 0;
    table.cellSpacing = 0;
    table.borderColor = "#000";

    var thead = document.createElement("thead");
    table.appendChild(thead);


    var colObject = {};//列对象 用于数据渲染 格式以及处理函数
    renderColumns(column);


    return table.outerHTML;


    //根据列头创建表格
    function renderColumns(columns, dataHeader) {
        var col, th;
        var columnsArray = [];
        var headers = dataHeader || [];
        var tr = document.createElement("tr");
        var trHTML = "";
        var resultTr = "";
        for (var i = 0; i < columns.length; i++) {
            col = columns[i];
            //判断如果不是行号列 且 不是隐藏列
            if (!col.isrownumber && !col._hide && !col.ischeckbox) {
                var colSpan = 1;
                //创建表头单元格
                if (col.columns) {
                    colSpan = 0;
                    $(col.columns).each(function (i, v) {
                        if (!v._hide) {
                            colSpan++;
                        }
                    });
                }
                th = document.createElement("th");
                th.colSpan = colSpan;
                th.rowSpan = col.__rowSpan;
                th.align = "center";
                th.width = col._width * 1 + "px";
                th.innerHTML = col.display;
                trHTML += th.outerHTML;

                tableWidth += col._width * 1 || 0;
                //根据列名name 添加列处理
                colObject[col.name] = { align: col.align };

                //创建渲染数据表头  
                //用于处理多表头时 数据根据最底层表头进行渲染
                if (col.__pid === -1) {
                    var colName = col.columns ? col.__id : col.name;
                    headers.push(colName);
                }
                if (col.columns) {
                    for (var j = 0, len = col.columns.length; j < len; j++) {
                        if (!col.columns[j]._hide) {
                            var childCol = col.columns[j];
                            var childColName = childCol.columns ? childCol.__id : childCol.name;
                            //判断是否最后一个 如果最后一个则直接替换 不是最后一个 则添加在索引前面
                            if (j === len - 1) {
                                headers.splice(headers.indexOf(childCol.__pid), 1, childColName);
                            } else {
                                headers.splice(headers.indexOf(childCol.__pid), 0, childColName);
                            }
                        }
                    }
                    columnsArray = columnsArray.concat(col.columns);
                }
            }
        }
        tr.innerHTML = trHTML;

        table.style.width = tableWidth + "px";

        thead.appendChild(tr);

        if (columnsArray.length > 0) {
            createTable(columnsArray, headers);
        } else { //表头渲染结束 开始渲染数据
            //添加title在thead
            var headDom = document.createElement("thead");
            var titleDom = document.createElement("tr");
            titleDom.innerHTML = "<th style='border:none;padding-bottom:5px;' colSpan=" + headers.length + "><h1>" + (param.title || "") + "</h1></th>";
            var searchDom = document.createElement("tr");
            searchDom.innerHTML = "<th></th>"

            if (title) {
                headDom.appendChild(titleDom);
            }
            if (head) {
                var thDom = searchDom.getElementsByTagName("th")[0]
                thDom.innerHTML = head;
                thDom.colSpan = headers.length;
                thDom.style.border = "none";
                headDom.appendChild(searchDom);
            }
            $(thead).prepend(headDom.innerHTML);

            //渲染数据 根据分组信息执行不同渲染方式
            if (group) {
                resultTr = renderRowsByGroup(headers, colObject).innerHTML;
                $(table).append(resultTr);
            } else {
                resultTr = renderRows(headers, colObject).innerHTML;
                $(table).append(resultTr);
            }
        }
    }

    //渲染行
    function renderRows(column, colObject) {
        var table = document.createElement("table");
        var tableHTML = "";
        for (var i = 0; i < data.length; i++) {
            var d = data[i];
            var tr = document.createElement("tr");
            var trHTML = "";
            for (var j = 0; j < column.length; j++) {
                var colHeader = column[j];
                var formatt = fn[colHeader];
                var txt = d[colHeader] || "";

                var td = document.createElement("td");
                td.style.paddingLeft = "5px";
                td.align = colObject[colHeader] ? colObject[colHeader].align : "";

                if (typeof formatt === "function") {
                    txt = formatt(txt);
                }
                td.innerHTML = txt;
                trHTML += td.outerHTML;
            }
            tr.innerHTML = trHTML;
            tableHTML += tr.outerHTML;
        }
        table.innerHTML = tableHTML;
        return table;
    }

    //渲染行 包含分组信息
    function renderRowsByGroup(column, colObject) {
        var rowArr = {};
        var groupNameArr = [];
        var table = document.createElement("table");
        var tableHTML = "";
        //构造分组数据
        for (var i = 0; i < data.length; i++) {
            var group_name = data[i][group.name];
            //如果rowArr[group_name]未赋值 则创建一个数组
            if (!rowArr[group_name]) {
                rowArr[group_name] = [];
            }
            rowArr[group_name].push(data[i]);
            if (groupNameArr.indexOf(group_name) === -1) {
                groupNameArr.push(group_name);
            }
        }

        //渲染分组数据
        for (var j = 0; j < groupNameArr.length; j++) {
            var groupName = groupNameArr[j];
            var groupData = rowArr[groupName];

            var trDom = document.createElement("tr");
            var tdDom = document.createElement("td");
            tdDom.colSpan = column.length;
            tdDom.innerHTML = group.display + ":" + groupName;
            trDom.appendChild(tdDom);

            tableHTML += trDom.outerHTML;

            $(groupData).each(function (i, v) {
                trDom = document.createElement("tr");
                var trHTML = "";
                for (var i = 0; i < column.length; i++) {
                    var colHeader = column[i];
                    var formatt = fn[colHeader];
                    var txt = v[colHeader] || "";

                    var tdDom = document.createElement("td");
                    tdDom.style.paddingLeft = "5px";
                    tdDom.align = colObject[colHeader] ? colObject[colHeader].align : "";

                    if (typeof formatt === "function") {
                        txt = formatt(txt);
                    }
                    tdDom.innerHTML = txt;
                    trHTML += tdDom.outerHTML;
                }
                trDom.innerHTML = trHTML;
                tableHTML += trDom.outerHTML;
            });
        }
        table.innerHTML = tableHTML;
        return table;
    }
}