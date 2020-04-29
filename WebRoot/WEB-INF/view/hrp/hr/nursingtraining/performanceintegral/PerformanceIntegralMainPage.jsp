<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="dialog,hr.select,datepicker,grid" name="plugins" />
        </jsp:include>
        <script>
            var year, dept_select, duty_select, title_select, level_select, grid, emp_id;
            $(function () {
                init();
                initFrom();
            });

            function init() {
                initGrid();
                year = $("#year").etDatepicker({
                    view: 'years',
                    minView: 'years',
                    dateFormat: 'yyyy'
                });
            }

            var initFrom = function () {
                dept_code = $("#dept_code").etSelect({
                    url: "../queryHosDeptSelect.do?isCheck=false",
                    defaultValue: "none",
                });
                title_code = $("#title_code").etSelect({
                    url: "../queryHrTitle.do?isCheck=false",
                    defaultValue: "none",
                });
                duty_code = $("#duty_code").etSelect({
                    url: "../queryDuty.do?isCheck=false",
                    defaultValue: "none",
                });
                level_code = $("#level_code").etSelect({
                    url: "../queryDicLevel.do?isCheck=false",
                    defaultValue: "none",
                });
                emp_id = $("#emp_id").etSelect({
                    url: "../queryEmpSelect.do?isCheck=false",
                    defaultValue: "none",
                    onChange: query
                });
            };
            var query = function () {
                var params = [
                        { name: 'emp_id', value: emp_id.getValue() },
                        { name: 'year', value: year.getValue() },
                        { name: 'dept_code', value: dept_code.getValue() },
                        { name: 'title_code', value: title_code.getValue() },
                        { name: 'duty_code', value: duty_code.getValue() },
                        { name: 'level_code', value: level_code.getValue() },
                    ];
                grid.loadData(params);
            };
            var save = function () {
                if (!grid.validateTest({})) {
                    return;
                }
                var gridAllData = grid.getAllData();
                if (!gridAllData || gridAllData.length === 0) {
                    return;
                }
                ajaxPostData({
                    url: 'addPerformanceIntegral.do',
                    data: {
                        paramVo: JSON.stringify(gridAllData)
                    },
                    success: function () {
                        query();
                    }
                })
            };
            var add = function () {
                grid.addRow();
            };
            var del = function () {
                var selectData = grid.selectGet();
                if (selectData.length === 0) {
                    $.etDialog.error('请选择行');
                    return;
                }

                var param = [];
                selectData.forEach(function (item) {
                    param.push({
                        year: item.rowData.year,
                        emp_id: item.rowData.emp_id,
                    });
                })
                
                ajaxPostData({
                     url: 'deletePerformanceIntegral.do',
                    data: { paramVo: JSON.stringify(param) },
                    success: function () {
                        grid.deleteRows(selectData);
                    }
                })
            };
            function initGrid() {
            	 var yearEditor = getRecentYearForSelect();
                var main_columns = [
                    { display: '日期', name: 'year', width: 120,
                        editor: yearEditor
                    },
                    { display: '职工工号', name: 'emp_code', width: 120,
                        editor: {
                            type: 'grid',
                            columns: [
                            	{ display: '职工工号', name: 'emp_code', width: 120 },
                                { display: '职工名称', name: 'emp_name', width: 120 },
                                { display: '职务', name: 'duty_name', width: 120 },
                                { display: '职称', name: 'title_name', width: 120 },
                                { display: '护理等级', name: 'level_name', width: 120 },
                                { display: '', name: 'duty_code', hidden: true },
                                { display: '', name: 'title_code', hidden: true },
                                { display: '', name: 'level_code', hidden: true },
                                { display: '', name: 'emp_id', hidden: true },
                            ],
                            width: '700px',
                            height: '205px',
                            dataModel: {
                                url: '../queryHrEmpData.do?isCheck=false',
                            },
                        }, 
                    },
                    { display: '职工名称', name: 'emp_name', width: 120, editable: false, },
                    { display: '', name: 'emp_id', hidden: true, },
                    { display: '职称', name: 'title_name', width: 120 },
                    { display: '', name: 'title_code', width: 120 , hidden: true,},
                    { display: '职务', name: 'duty_name', width: 120 },
                    { display: '', name: 'duty_code', width: 120 , hidden: true,},
                    { display: '护理等级', name: 'level_name', width: 120 },
                    { display: '', name: 'level_code', hidden: true, },
                    { display: '护士素养', name: 'morality', width: 120 },
                    { display: '护理质量', name: 'quality', width: 120 },
                    { display: '安全护理', name: 'safety', width: 120 },
                    { display: '科研教学', name: 'resteach', width: 120 },
                    { display: '加分项', name: 'accessory', width: 120 },
                    /* { display: '备注', name: 'note', width: 120 } */
                ];

                var main_paramObj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    editable: true,
                    dataModel: {
                        url: 'queryPerformanceIntegral.do'
                    },
                    columns: main_columns,
                    toolbar: {
                        items: [
                            { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                            { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                            { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                            { type: 'button', label: '删除', listeners: [{ click: del }], icon: 'delete' },
                            { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' },
                            { type: 'button', label: '导入', listeners: [{ click: importData }], icon: 'import' },
                            { type: 'button', label: '导出', listeners: [{ click: exportData }], icon: 'export' }
                        ]
                    }
                };

                grid = $("#grid").etGrid(main_paramObj);

            }


            function print() { }

            function importData() { }

            function exportData() { }
        </script>
    </head>

    <body>
        <!-- 员工年终绩效积分考核主页            添加页面 URL addPerformanceIntegralPage       修改页面跳转 URL    updatePerformanceIntegralPage -->
        <div class="main">
            <table class="table-layout">
                <tr>
                    <td class="label">统计年度：</td>
                    <td class="ipt">
                        <input type="text" id="year">
                    </td>
                    <td class="label">职工：</td>
	                <td class="ipt">
	                    <select id="emp_id" style="width:180px;"></select>
	                </td>
                    <td class="label">科室名称：</td>
                    <td class="ipt">
                        <select name="" id="dept_code" style="width:180px;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="label">职务：</td>
                    <td class="ipt">
                        <select name="" id="title_code" style="width:180px;"></select>
                    </td>
                    <td class="label">职称：</td>
                    <td class="ipt">
                        <select name="" id="duty_code" style="width:180px;"></select>
                    </td>
                    <td class="label">级别：</td>
                    <td class="ipt">
                        <select name="" id="level_code" style="width:180px;"></select>
                    </td>
                    <td class="label"></td>
                    <td class="label"></td>
                </tr>
            </table>

        </div>
        <div id="grid"></div>
    </body>

    </html>