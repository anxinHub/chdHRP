<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="dialog,hr.select,datepicker,grid,pageOffice" name="plugins" />
        </jsp:include>
        <script>
            var year_date, dept_code, title_code, duty_code, eval_code, grid, emp_id;
            $(function () {
            	initFrom();
                init();
                query();
            });
            var query = function () {
                var params = [
                    { name: 'emp_id', value: emp_id.getValue() },
                    { name: 'dept_code', value: dept_code.getValue() },
                    { name: 'title_code', value: title_code.getValue() },
                    { name: 'duty_code', value: duty_code.getValue() },
                    { name: 'eval_code', value: eval_code.getValue() },
                    { name: 'year', value: year.getValue() },
                ];
                grid.loadData(params,"queryAssessmentStatistical.do");
            };
            function init() {
            	
                initGrid();
                year = $("#year").etDatepicker({
                    view: "years",
                    minView: "years",
                    dateFormat: "yyyy",
                 //   onChange: query
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

                eval_code = $("#eval_code").etSelect({
                    url: 'queryEvalCode.do?isCheck=false',
                    defaultValue: 'none'
                });
                emp_id = $("#emp_id").etSelect({
                    url: "../queryEmpSelect.do?isCheck=false",
                    defaultValue: "none",
                    onChange: query
                });
            }

            function initGrid() {
                var main_columns = [
                    { display: '统计年度', name: 'year', width: 120, },
                    { display: '职工工号', name: 'emp_code', width: 120,  
                    	render: function (ui) {
                        var cellData = ui.cellData;
                        var state_name = ui.rowData.state_name;
                        var tip = '';

                        if (state_name == '不合格') {
                            tip = '<span style="color:red;"> 不合格</span>'
                        }

                        return cellData + tip;
                    } },
                    { display: '职工姓名', name: 'emp_name', width: 120 },
                    { display: '科室名称', name: 'dept_name', width: 120 },
                    { display: '成绩', name: 'score', width: 120 },
                    { display: '合格状态', name: 'state_name', width: 120}
                ];

                var main_paramObj = {
                    height: '100%',
                    inWindowHeight: true,
                    checkbox: true,
                    rowDblClick: function (event, ui) {
                        openUpdate(ui.rowData);
                    },
                    dataModel: {
                       // url: ''
                    },
                    columns: main_columns,
                    toolbar: {
                        items: [
                        	{ type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                            { type: 'button', label: '保存', listeners: [{ click: save }], icon: 'save' },
                            { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                        ]
                    }
                };

                grid = $("#grid").etGrid(main_paramObj);

            }

            function save() { }

            function print() { 
            	if(grid.getAllData()==null){
            		$.etDialog.error("请先查询数据！");
        			return;
        		}
            	var heads={
                		 /* "isAuto":true,//系统默认，页眉显示页码
                		"rows": [
            	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
                		]  */}; 
            	var printPara={
                    title: " 护理晋级申请表打印",//标题
                    columns: JSON.stringify(grid.getPrintColumns()),//表头
                    class_name: "com.chd.hrp.hr.service.nursingtraining.HrAssessmentStatisticalService",
                    method_name: "queryAssessmentByPrint",
                    bean_name: "hrAssessmentStatisticalService",
                    heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                    foots: '',//表尾需要打印的查询条件,可以为空 
                };
                $.each(grid.getUrlParms(),function(i,obj){
                    printPara[obj.name]=obj.value;
                }); 
                officeGridPrint(printPara);
            }
        </script>
    </head>

    <body>
        <!-- 考核统计主页 添加页面 URL addAssessmentStatisticalPage 修改页面跳转 URL updateAssessmentStatisticalPage -->
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
                    <td class="label">考核名称：</td>
                    <td class="ipt">
                        <select name="" id="eval_code" style="width:180px;"></select>
                    </td>
                    <td class="label"></td>
                    <td class="label"></td>
                </tr>
            </table>
            
        </div>
        <div id="grid"></div>
    </body>

    </html>