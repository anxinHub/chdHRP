<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="dialog,check" name="plugins" />
    </jsp:include>
    <style>
        body {
            overflow: hidden;
        }
        .button-group {
            text-align: left;
            background: #ecf0f3;
        }
        .button-group button {
            margin: 6px 12px;
        }
    </style>
    <script type="text/javascript">
        var spreadNS;

        // 获取打开层的windows对象
        var parentFrameName = parent.$.etDialog.parentFrameName;
        var parentWindow = parent.window[parentFrameName];
        // 数据对象
        var dataForImport = parentWindow.dataForImport;
        
        // 参数
        var para = dataForImport.para;
        var isUpdate = para.isUpdate;
        var columns = para.column;
        var url = dataForImport.url;

        

        //初始化值
        var requireJson = [];
        var initSpreadData = function () {
            var spread = spreadNS.designer.wrapper.spread;
            var sheet = spread.getSheet(0);

            var index = 0;
            sheet.suspendPaint();

            sheet.setColumnCount(columns.length, spreadNS.SheetArea.viewport);
            $.each(columns, function (i, obj) {
                if (obj.width) {
                    sheet.setColumnWidth(index, obj.width);
                } else {
                    sheet.setColumnWidth(index, 150);
                }
                if (obj.require) {
                    sheet.setText(0, index, obj.display);
                    requireJson.push(index);
                } else {
                    sheet.setText(0, index, obj.display)
                }
                index++;
            });
            sheet.getRange(0, 0, 1, 10).hAlign(spreadNS.HorizontalAlign.center);
            sheet.frozenRowCount(1);
            sheet.clearSelection();
            sheet.resumePaint();
            spread.removeSheet(1);
            // var table = sheet.addTable('tableRecords', 4, 1,1, 3);
            // // table.showHeader(false);
            // table.bandRows(false);
            // table.autoGenerateColumns(false);
            	
            // names.forEach(function (name, index) {
            //      var tableColumn = new GcSpread.Sheets.TableColumnInfo();
            //      tableColumn.name(displays[index]);
            //      tableColumn.dataField(name);
            //      tableColumns.push(tableColumn);
            // });
            	
            	
            // table.bindColumns(tableColumns);
            // //	table.bindingPath('sales');
            // source = new GcSpread.Sheets.CellBindingSource(data);
            // sheet.setDataSource(source);
        };
        var initSpreadTable = function () {
            var $spreadFrame = $(window.frames["spreadFrame"].document);
            var $content = $spreadFrame.find(".content .fill-spread-content");
            $spreadFrame.find(".header").css("height", 0);
            $content.css({ top: 2 });
            $content.parent().css({ height: $content.height() });
            var spread = spreadNS.designer.wrapper.spread;
            spread.options.newTabVisible = false;
            initSpreadData();
            spread.refresh();
            
            // 高度计算？
            $("#spreadFrame").css("height", $(window).height() - 50);
        };

        // 确定
        var row = null;
        var cell = null;
        
        // 选择文件
        var openFileDialog = function () {
            var spread = spreadNS.designer.wrapper.spread;
            document.getElementById('spreadFrame').contentWindow.openFileDialog(spread, { isSavedWarning: false });
        };
        // 导出excel
        var exportExcel = function () {
            document.getElementById('spreadFrame').contentWindow.exportExcel(spreadNS.designer.wrapper.spread, null);
        };
        // 是否覆盖
        // var isUpdateData = function () {};
        // 确定保存
        var savePage = function () {
            var spread = spreadNS.designer.wrapper.spread;
            //删除所有其他的sheet，值导入第1个sheet
            if (spread.getSheetCount() > 1) {
                for (var i = 1; i <= spread.getSheetCount(); i++) {
                    spread.removeSheet(1);
                }
            }
            var sheet = spread.getSheet(0);
            for (var i = 1; i < sheet.getRowCount(); i++) {
                var isEmpty = true;//判断是否空行
                for (var c = 0; c < sheet.getColumnCount(); c++) {
                    if (sheet.getText(i, c) != "") {
                        isEmpty = false;
                        break;
                    }
                }
                if (isEmpty) {
                    continue;
                }
                for (var j = 0; j < requireJson.length; j++) {
                    if (sheet.getText(i, requireJson[j]) == "") {
                        $.etDialog.warn("第" + (parseInt(requireJson[j]) + 1) + "列，不能为空！");
                        return false;
                    }
                }
            }

            var isUpdateCk = false;
            if (isUpdate && $("#isUpdate").is(':checked')) {
                isUpdateCk = true;
            }
            para["isUpdate"] = isUpdateCk;

            var importPara = {
                content: JSON.stringify(spread.toJSON()),
                para: JSON.stringify(para)
            };
            console.log(importPara)
            ajaxPostData({
                url: "..//" + url,
                data: importPara,
                success: function (responseData) {
                    if (row != null) {
                        var whiteStyle = new spreadNS.Style();
                        whiteStyle.backColor = "white";
                        whiteStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4", spreadNS.LineStyle.medium);
                        whiteStyle.borderTop = new spreadNS.LineBorder("#D4D4D4", spreadNS.LineStyle.medium);
                        whiteStyle.borderRight = new spreadNS.LineBorder("#D4D4D4", spreadNS.LineStyle.medium);
                        whiteStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4", spreadNS.LineStyle.medium);
                        sheet.setStyle(row, cell, whiteStyle, spreadNS.SheetArea.viewport);
                    }

                    if (responseData.state == "false" && responseData.row_cell != undefined) {
                        row = parseInt(responseData.row_cell.split("：")[0]) - 1;
                        cell = parseInt(responseData.row_cell.split("：")[1]) - 1;
                        var redStyle = new spreadNS.Style();
                        redStyle.backColor = "red";
                        /* cellStyle.name = 'cellStyle';
                        sheet.addNamedStyle(cellStyle); */
                        redStyle.borderLeft = new spreadNS.LineBorder("#D4D4D4", spreadNS.LineStyle.medium);
                        redStyle.borderTop = new spreadNS.LineBorder("#D4D4D4", spreadNS.LineStyle.medium);
                        redStyle.borderRight = new spreadNS.LineBorder("#D4D4D4", spreadNS.LineStyle.medium);
                        redStyle.borderBottom = new spreadNS.LineBorder("#D4D4D4", spreadNS.LineStyle.medium);
                        sheet.setStyle(row, cell, redStyle, spreadNS.SheetArea.viewport);
                        sheet.setActiveCell(row, cell);
                    }
                }
            })
        };
        // 关闭
        var closePage = function () {
            var curIndex = parent.$.etDialog.getFrameIndex(window.name);
            parent.$.etDialog.close(curIndex);
        };

        var initToolbar = function () {
            $("#openFileDialog").on("click", openFileDialog);
            $("#down").on("click", exportExcel);
            $("#save").on("click", savePage);
            $("#candle").on("click", closePage);
            // if (isUpdate) {
            //     item.push({ line: true });
            //     item.push({
            //         text: '是否覆盖：<input type="checkbox" id="isUpdate"/>',
            //         id: 'isUpdate',
            //         icon: '',
            //         click: isUpdateData });
            // }
        };

        $(function () {
            initToolbar();
            $("#spreadFrame").css("height", $(window).height() - 50);
        });
    </script>
</head>

<body>
    <div class="button-group">
        <button id="openFileDialog">选择文件</button>
        <button id="down">导出Excel</button>
        <!-- <label for="isUpdate">是否覆盖</label>
        <input id="isUpdate" type="checkbox" /> -->
        <button id="save">确定</button>
        <button id="candle">关闭</button>
    </div>
    <iframe src="<%=path%>/lib/SpreadJS10/Spread.Sheets.Designer.10/src/index/index.html" width="100%" id="spreadFrame" name="spreadFrame"></iframe>
</body>

</html>