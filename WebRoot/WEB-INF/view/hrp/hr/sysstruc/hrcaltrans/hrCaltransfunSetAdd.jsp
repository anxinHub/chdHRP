<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>函数添加页</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="grid, dialog, hr" name="plugins" />
        </jsp:include>
        <style>
            .inlineBlock {
                display: inline-block;
                height: 345px;
                vertical-align: top;
            }
        </style>
        <script>
            var main_grid;
            $(function () {
                init();
            });

            function init() {
                initGrid();
                initEvent();
            }

            function initGrid() {
                // 基础表格参数
                var main_toolbar = {
                    items: [
                        { type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
                        { type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] }
                    ]
                };
                var main_columns = [
                    {
                        display: '函数参数', name: 'param', width: 120
                    },
                    {
                        display: '参数类型', name: 'type', width: 120
                    },
                    {
                        display: '序号', name: 'number', width: 120
                    }
                ];
                var main_obj = {
                    height: '300',
                    title: '参数',
                    showTitle: true,
                    width: 500,
                    editable: true,
                    inWindowHeight: true,
                    checkbox: true,
                    toolbar: main_toolbar,
                    columns: main_columns,
                    usePager: false
                };
                main_grid = $("#main_grid").etGrid(main_obj);
            }

            function initEvent() {
             
            }

            function add() {
                main_grid.addRow({});
            }

            function del() {
                main_grid.deleteSelectedRows();
            }

            function save() {

            }
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">数据字段1</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                </tr>
                <tr>
                    <td class="label">数据字段1</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                </tr>
            </table>
        </div>
        <div class="inlineBlock">
            <div id="main_grid"></div>
        </div>
        <div class="inlineBlock">
            <textarea style="height:300px;width:400px;" placeholder="函数体"></textarea>
        </div>
     
    </body>

    </html>