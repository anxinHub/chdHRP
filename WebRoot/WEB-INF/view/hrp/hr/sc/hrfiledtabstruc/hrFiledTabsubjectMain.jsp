<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select,dialog,grid" name="plugins" />
        </jsp:include>
        <script>
            var field_tab_name ;
            $(function () {
                loadDict();
                loadGrid();
            })

            function loadDict() {
                /* field_tab_name = $("#field_tab_name").etSelect({
                    url: '',
                    defaultValue: "none"
                }); */
            }

            function loadGrid() {
                var gridObj = {
                    editable: true,
                    checkbox: true,
                    height: '100%',
                    width:'90%',
                    showBottom:false,
                    inWindowHeight: true,
                    addRowByKey: true //  快捷键控制添加行
                };
                gridObj.numberCell = {
                    title: '#'
                };
                gridObj.columns = [{
                        display: "数据表名称",
                        align: "center",
                        width: 120,
                        name: "tab_name",
                        editable: false
                    },
                    {
                        display: '数据列名称',
                        align: 'center',
                        name: 'col_name',
                        width: 120,
                        editable: false/* ,
                        render: function (ui) { // 修改页打开
                            return '<a data-item=' + ui.rowIndx + ' class="td-a">' + ui.cellData +
                                '</a>'
                        } */
                    }
                ];
                gridObj.dataModel = { // 数据加载的有关属性
                    url: 'queryColAndTabName.do?isCheck=false&field_tab_code=${field_tab_code}',
                    recIndx: 'a'
                };
                /* gridObj.toolbar = {
                    items: [{
                            type: "button",
                            label: '查询',
                            icon: 'search',
                            id: 'search',
                            listeners: [{
                                click: search
                            }]
                        },
                        {
                            type: "button",
                            label: '添加',
                            icon: 'add',
                            listeners: [{
                                click: add
                            }]
                        }, {
                            type: "button",
                            label: '删除',
                            icon: 'delete',
                            listeners: [{
                                click: deleteData
                            }]
                        }, {
                            type: "button",
                            label: '保存',
                            icon: 'save',
                            listeners: [{
                                click: save
                            }]
                        }, {
                            type: "button",
                            label: '打印',
                            icon: 'print',
                            listeners: [{
                                click: print
                            }]
                        }, {
                            type: "button",
                            label: '导出',
                            icon: 'export',
                            listeners: [{
                                click: exportData
                            }]
                        }, {
                            type: "button",
                            label: '导入',
                            icon: 'import',
                            listeners: [{
                                click: importData
                            }]
                        }
                    ]
                }; */
                grid = $("#maingrid").etGrid(gridObj);
            }
        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label no-empty">代码表名称：</td>
                    <td class="ipt">
                        <input name="field_tab_name" id="field_tab_name" type="text" value="${field_tab_name}" disabled style="width:180px;">
                    </td>
                </tr>
            </table>
        </div>
        <div id="maingrid" style="margin:5px auto"></div>
    </body>

    </html>