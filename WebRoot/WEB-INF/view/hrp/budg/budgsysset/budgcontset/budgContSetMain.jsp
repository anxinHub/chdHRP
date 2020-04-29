<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/static_resource.jsp">
        <jsp:param value="grid,dialog" name="plugins" />
    </jsp:include>
    <script>
        var grid;
        var control_way_select_options;

        var initGrid = function () {
            var columns = [
                { display: '模块编码', name: 'mod_code', editable: false, width: 120 },
                { display: '模块名称', name: 'mod_name', editable: false, width: 150 },
                { display: '执行系统名称', name: 'exe_sys_name', editable: false, width: 200 },
                { display: '执行页面名称', name: 'exe_page_name', editable: false, width: 200 },
                { display: '控制节点', name: 'cont_node_code', editable: false, width: 120,
                    render: function (ui) {
                    	if (!ui.rowData.noteJson) {
                    		return;
                    	}
                    	var cont_node_select_options = JSON.parse(ui.rowData.noteJson).Rows;
                        var cont_node_code = ui.cellData;
                        var selectHtml = '<select row-index="' + ui.rowIndx + '" class="cont_node_select" style="width: 100px;"><option value=""></option>';
                        cont_node_select_options.forEach(function (item) {
                            selectHtml +=
                                '<option value="' + item.id + '" ' +
                                (cont_node_code == item.id ? 'selected' : '') +
                                '>' + item.text + '</option>'
                        })
                        selectHtml += '</select>';

                        return selectHtml;
                    }
                },
                { display: '反向节点', name: 're_node_name', editable: false, width: 120 },
                { display: '控制方式', name: 'control_way', editable: false, width: 150,
                    render: function (ui) {
                        var control_way = ui.cellData;
                        var selectHtml = '<select row-index="' + ui.rowIndx + '" class="control_way_select" style="width: 100px;"><option value=""></option>';
                        control_way_select_options.forEach(function (item) {
                            selectHtml +=
                                '<option value="' + item.id + '" ' +
                                (control_way == item.id ? 'selected' : '') +
                                '>' + item.text + '</option>'
                        })
                        selectHtml += '</select>';

                        return selectHtml;
                    }
                },
                { display: '是否回传', name: 'is_postback', editable: false, width: 120,
                    render: function (ui) {
                        var is_postback = ui.cellData;
                        var selectHtml = '<select row-index="' + ui.rowIndx + '" class="is_postback_select" style="width: 80px;">'+
                        '<option value=""></option>'+
                        '<option value="1" ' + (is_postback == '1' ? 'selected' : '') + '>是</option>'+
                        '<option value="0" ' + (is_postback == '0' ? 'selected' : '') + '>否</option>'+
                        '</select>';

                        return selectHtml;
                    }
                },
                { display: '操作', name: 'edit', editable: false, width: 135,
                    render: function (ui) {
                        return '<button row-index="' + ui.rowIndx + '" class="save_button">保存</button> ' +
                            '<button row-index="' + ui.rowIndx + '" class="cancel_button">取消</button>'
                    }
                }
            ];
            var paramObj = {
                height: '100%',
                checkbox: false,
                editable: true,
                dataModel: {
                    url: 'queryBudgContSet.do?isCheck=false'
                },
                columns: columns,
               /*  toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                    ]
                } */
            };
            grid = $("#mainGrid").etGrid(paramObj);
            grid.addRow({
                cont_node_code: '1',
                control_way: '1',
            });
            
            // 单行保存按钮
            $("#mainGrid").on('click', '.save_button', function () {
                var rowIndex = $(this).attr('row-index');
                var rowData = grid.getAllData()[rowIndex];
                var param = {
                    mod_code: rowData.mod_code,
                    exe_sys_code: rowData.exe_sys_code,
                    exe_page_code: rowData.exe_page_code,
                    cont_node_code: rowData.cont_node_code,
                    re_node_code: rowData.re_node_code,
                    control_way: rowData.control_way,
                    is_postback: rowData.is_postback
                };
                ajaxPostData({
                    url: 'saveBudgContSet.do?isCheck=false',
                    data: param,
                    success: function () {
                        grid.refreshDataAndView();
                    }
                })
            })
            // 单行取消按钮
            $("#mainGrid").on('click', '.cancel_button', function () {
                var rowIndex = $(this).attr('row-index');
                var rowData = grid.getAllData()[rowIndex];
                var param = {
                    mod_code: rowData.mod_code,
                    exe_sys_code: rowData.exe_sys_code,
                    exe_page_code: rowData.exe_page_code,
                    cont_node_code: rowData.cont_node_code,
                    re_node_code: rowData.re_node_code,
                    control_way: rowData.control_way,
                    is_postback: rowData.is_postback
                };
                ajaxPostData({
                    url: 'deleteBudgContSet.do?isCheck=false',
                    data: param,
                    success: function () {
                    	grid.refreshDataAndView();
                    }
                })
            })
             // 控制节点 选择框
            $("#mainGrid").on('change', '.cont_node_select', function () {
                var rowIndex = $(this).attr('row-index');
                var rowData = grid.getAllData()[rowIndex];
                var cont_node_code = $(this).val();

                // 级联反向节点
                ajaxPostData({
                    url: 'queryReNodeByCode.do?isCheck=false',
                    data: {
                        mod_code: rowData.mod_code,
                        exe_sys_code: rowData.exe_sys_code,
                        exe_page_code: rowData.exe_page_code,
                        cont_node_code: cont_node_code
                    },
                    success: function (res) {
                        grid.updateRow(rowIndex, {
                            re_node_name: res.re_node_name,
                            re_node_code: res.re_node_code,
                        })
                    }
                })
                grid.updateRow(rowIndex, {
                    cont_node_code: cont_node_code,
                })
            })
            // 控制方式 选择框
            $("#mainGrid").on('change', '.control_way_select', function () {
                var rowIndex = $(this).attr('row-index');
                grid.updateRow(rowIndex, {
                    control_way: $(this).val()
                })
                // 更新行问题
            })
            // 是否回传 选择框
            $("#mainGrid").on('change', '.is_postback_select', function () {
                var rowIndex = $(this).attr('row-index');
                grid.updateRow(rowIndex, {
                    is_postback: $(this).val()
                })
            })
        };

        $(function () {
            // 等待 控制方式请求
            function wait_control_way () {
                var dtd = $.Deferred();
                $.post('../../queryBudgSysDict.do?isCheck=false&f_code=CONTROL_WAY', function (res) {
                    control_way_select_options = res;
                    dtd.resolve();
                }, 'json');

                return dtd.promise();
            }
            
            $.when(wait_control_way()).done(initGrid);
        })
    </script>
</head>

<body>
    <div id="mainGrid"></div>
</body>

</html>