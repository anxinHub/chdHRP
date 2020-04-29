<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="hr,dialog,grid,select,tree,datepicker,pageOffice" name="plugins" />
    </jsp:include>
    <script>
        var year, state_name, dept_id, duty_code, level_code, emp_id;
        var grid;
        var initFrom = function () {
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: ['yyyy'],
                onChange: query,
            });
            state_name = $("#state_name").etSelect({
                options: [
                    { id: '合格', text: '合格' },
                    { id: '不合格', text: '不合格' },
                ],
                defaultValue: "none",
                onChange: query,
            });
            dept_id = $("#dept_id").etSelect({
                url: "../queryHosDeptSelect.do?isCheck=fasle",
                defaultValue: "none",
                onChange: query,
            });
            duty_code = $("#duty_code").etSelect({
                url: "../queryDuty.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            level_code = $("#level_code").etSelect({
                url: "../queryDicLevel.do?isCheck=false",
                defaultValue: "none",
                onChange: query,
            });
            emp_id = $("#emp_id").etSelect({
                url: "../queryEmpSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
        };
        var query = function () {
            params = [
                { name: 'year', value: year.getValue() },
                { name: 'emp_id', value: emp_id.getValue() },
                { name: 'state_name', value: state_name.getValue() },
                { name: 'dept_id', value: dept_id.getValue().split("@")[1] },
                { name: 'duty_code', value: duty_code.getValue() },
                { name: 'col_code', value: level_code.getText().split(' ')[0] },
                { name: 'col_name', value: level_code.getText().split(' ')[1] },
            ];
            
            grid.loadData(params,"queryInserviceStatistics.do");
        };

        var print = function () {
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
              		title: "年度在职教育培训学时统计打印",//标题
              		columns: JSON.stringify(grid.getPrintColumns()),//表头
              		class_name: "com.chd.hrp.hr.service.nursing.HrInserviceStatisticsService",
           			method_name: "queryByPrint",
           			bean_name: "hrInserviceStatisticsService",
           			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
           			foots: '',//表尾需要打印的查询条件,可以为空 
               	};
             	$.each(grid.getUrlParms(),function(i,obj){
           			printPara[obj.name]=obj.value;
            	}); 
             	//console.log(printPara);
            	officeGridPrint(printPara);
        };
        var initGrid = function () {
            var columns = [
               /*  { display: '年度', name: 'edu_date', width: 90,align:'left' }, */
                { display: '职工工号', name: 'emp_code', width: 120,align:'left',
                    render: function (ui) {
                        var cellData = ui.cellData;
                        var state_name = ui.rowData.state_name;
                        var tip = '';

                        if (state_name == '不合格') {
                            tip = '<span style="color:red;"> 不合格</span>'
                        }

                        return cellData + tip;
                    }
                },
                { display: '职工姓名', name: 'emp_name', width: 120,align:'left' },
                { display: '部门名称', name: 'dept_name', width: 120,align:'left' },
                { display: '职务', name: 'duty_name', width: 120,align:'left' },
                { display: '岗位', name: 'title_name', width: 120,align:'left' },
                { display: '护理等级', name: 'col_name', width: 90,align:'left' },
                { display: '学时标准', name: 'education', width: 90 ,align:'left'},
                { display: '实际学时数', name: 'hours', width: 90,align:'right' },
                { display: '是否合格', name: 'state_name', width: 90,align:'left' },
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                dataModel: {
                   
                },
                columns: columns,
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
            initGrid();
            query();
        })
    </script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">培训日期：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
                <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
                <td class="label">合格状态：</td>
                <td class="ipt">
                    <select id="state_name" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">部门名称：</td>
                <td class="ipt">
                    <select id="dept_id" style="width:180px;"></select>
                </td>
                <td class="label">职务：</td>
                <td class="ipt">
                    <select id="duty_code" style="width:180px;"></select>
                </td>
                <td class="label">护理等级：</td>
                <td class="ipt">
                    <select id="level_code" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>