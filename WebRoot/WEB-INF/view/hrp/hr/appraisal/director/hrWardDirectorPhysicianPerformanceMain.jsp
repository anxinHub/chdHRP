<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/CHD-HRP/lib/et_assets/common.css">
<script src="/CHD-HRP/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="/CHD-HRP/lib/et_assets/common.js"></script>
<script src="/CHD-HRP/lib/et_assets/moment.min.js"></script>
<link rel="stylesheet" href="/CHD-HRP/lib/et_assets/hr/common.css">
<script src="/CHD-HRP/lib/et_assets/hr/common.js"></script>
<script src="/CHD-HRP/lib/et_components/etGrid/js/jquery-ui-1.9.2.min.js"></script>
<script src="/CHD-HRP/lib/et_components/et_plugins/etGrid.min.js"></script>
<script src="/CHD-HRP/lib/et_components/et_plugins/etDatepicker.min.js"></script>
<script src="/CHD-HRP/lib/et_components/et_plugins/etSelect.min.js"></script>
<script src="/CHD-HRP/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="/CHD-HRP/lib/et_components/et_plugins/etValidate.min.js"></script>
<script src="/CHD-HRP/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script src="/CHD-HRP/lib/hrp.js" type="text/javascript"></script>

        <script>
            var grid, record_select, profession_select, dept_select,year;
            var initGrid = function () {
                var columns = [
                    { display: '姓名', name: 'year', width: 100 },
                    { display: '部门', name: 'month', width: 100},    
                    { display: '职务', name: 'emp_code', width: 100},
                    { display: '职称', name: 'emp_name', width: 100},
                    { display: '任职年限', name: 'dept_id', width: 100},
                    { display: '等级', name: 'dept_name', width: 100},
                    { display: '总分', name: 'rotate', width: 100 },
                    { display: '指示灯', name: 'stud_eval', width: 100 }
                ];
                var paramObj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    columns: columns,
                    toolbar: {
                        items: [
                            { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                            { type: 'button', label: '审核', listeners: [{ click: '' }], icon: 'save' },
                            { type: 'button', label: '消审', listeners: [{ click: '' }], icon: 'add' }
                        ]
                    }
                };
                grid = $("#mainGrid").etGrid(paramObj);
            };

            var initSelect = function () {
                emp_id = $("#emp_id").etSelect({
                    url: "../../queryEmpSelect.do?isCheck=false",
                    defaultValue: "none",
                    onChange: query
                });

                dept_select = $("#dept_select").etSelect({
                    url: '../../queryHosDeptSelect.do?isCheck=false',
                    defaultValue: 'none',
                    onChange: query
                });
                
            };

            var initFrom = function () {
                year = $("#year").etDatepicker({
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                    defaultDate: true, 
                    onChange:query
                    })
                    };
            var query = function () {
                var params = [
                    { name: 'emp_id', value: emp_id.getValue() },
                    { name: 'emp_name', value: $("#emp_name").val() },
                    { name: 'dept_id', value: dept_select.getValue() },
                    { name: 'year', value: $("#year").val() },
                ];
                grid.loadData(params,'queryHrWithTeach.do');
            };
            
            $(function () {
                initSelect();
                initGrid();
                initFrom();
                initValidate();
                query();
            });



        </script>
    </head>

    <body>
        <div class="main">
            <table class="table-layout">
                <tr>
                	<td class="label">年度：</td>
               	 	<td class="ipt">
                    	<input id="year" type="text" />
                	</td>
                     <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
				<td class="label">轮转科室：</td>
                    <td class="ipt">
                        <select name="" id="dept_select" style="width:180px;"></select>
                    </td>
                </tr>
                	
                
            </table>
        </div>
        <div id="mainGrid"></div>
    </body>

    </html>