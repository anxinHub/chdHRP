<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="grid, dialog, hr" name="plugins" />
        </jsp:include>
        <script>
            var main_script;

            $(function () {
                init();
            });

            function init() {
                initGrid();
            }

            function initGrid() {
                // 基础表格参数
                var main_toolbar = {
                    items: [
                        { type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
                        { type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] },
                        { type: "button", label: '打印设置', icon: 'settings', listeners: [{ click: null }] },
                        { type: "button", label: '打印', icon: 'print', listeners: [{ click: null }] },
                        { type: "button", label: '导出', icon: 'import', listeners: [{ click: null }] },
                        { type: "button", label: '导入', icon: 'export', listeners: [{ click: null }] }
                    ]
                };
                var main_columns = [
                    {
                        display: '事务编号', name: 'a', width: 120
                    },
                    {
                        display: '数据表名称', name: 'b', width: 120
                    },
                    {
                        display: '数据列名称', name: 'c', width: 120
                    },
                    {
                        display: '事务频率', name: 'd', width: 120
                    },
                    {
                        display: '执行时机', name: 'e', width: 120
                    },
                    {
                        display: '事务方法', name: 'f', width: 120
                    },
                    {
                        display: '状态', name: 'g', width: 120
                    },
                    {
                        display: '备注', name: 'h', width: 120
                    }
                ];
                var main_obj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    toolbar: main_toolbar,
                    columns: main_columns,
                    showBottom:false,
                    dataModel: {
                        url: 'http://118.178.184.131:9090/static_column/grid'
                    }
                };
                main_grid = $("#main_grid").etGrid(main_obj);
            }

            function add() {
                $.etDialog.open({
                    url: 'hrCaltransfunSetAddPage.do?isCheck=false',
                    isMax: true,
                    title: '取值函数维护添加页',
                    btn: ['确定', '取消'],
                    btn1: function (index, el) {
                        var iframeWindow = window[el.find('iframe').get(0).name];
                        iframeWindow.saveData();
                    }
                });
            }
            function update() {
                $.etDialog.open({
                    title: '修改',
                    url: 'hrCaltransfunSetUpdatePage.do?isCheck=false',
                    isMax: true
                });
            }

            function del() {
                ajaxPostData({
                    url: 'http://118.178.184.131:9090/delete',
                    data: {},
                    success: function (res) {
                        console.log(res);
                        main_grid.deleteSelectedRows();
                    }
                });
            }
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                </tr>
                <tr>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                </tr>
                <tr>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                    <td class="label">数据字段1：</td>
                    <td class="ipt">
                        <input type="text">
                    </td>
                </tr>
            </table>
        </div>
        <div id="main_grid"></div>
    </body>

    </html>