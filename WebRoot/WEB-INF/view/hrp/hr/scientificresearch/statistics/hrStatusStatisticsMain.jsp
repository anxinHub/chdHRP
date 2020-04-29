<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,select,grid,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var year, dept_id;
        var grid;
        var initFrom = function () {
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                // minDate: data[data.length - 1].text,
                // maxDate: data[0].text,
                onChange: function () {
                    setTimeout(function () {
                        initColumns();
                    }, 10);
                },
                defaultDate: true
            });
            dept_id = $("#dept_id").etSelect({
                url: "../../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
        };
        var query = function () {
            var params = [
            	{ name: 'year', value: year.getValue() },
            	{ name: 'emp_name', value: $('#emp_name').val() },
                { name: 'dept_id', value: dept_id.getValue().split("@")[1] }
            ];
            grid.loadData(params, 'queryStatusStatistics.do');
        };
        /* var putOut = function () {	
        	exportGrid(grid);
        }; */
        
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
                title: " 个人学术地位统计打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.scientificresearch.HrSRStatisticsService",
                method_name: "queryStatusStatisticsByPrint",
                bean_name: "hrSRStatisticsService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);
        }
        
        var initColumns = function () {
            ajaxPostData({
                url: 'queryDynamicTabHead.do?isCheck=false&field_tab_code=DIC_ACADE_STATUS',
                data: { year: year.getValue() },
                success: function (res) {
                    var gridColumns = [
                        { display: '年度', name: 'YEAR', width: 80 },
                        { display: '职工工号', name: 'EMP_CODE', width: 80 },
                        { display: '职工名称', name: 'EMP_NAME', width: 80 },
                        { display: '科室信息', name: 'DEPT_NAME', width: 150 },
                        { display: '个人学术地位', columns: res.columns },
                        { display: '个人学术地位积分', name: 'SCORE', width: 120, align: 'center' },
                    ];
                    grid.option('columns', gridColumns);
                    grid.option('title','学术地位统计（满分：'+res.fullScore.acade_status+'分）')
                    grid.refreshView();

                    query();
                }            
            })
        }
        
        var initGrid = function () {
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                // checkbox: true,
                title: '',
                showTitle: true,
                dataModel: {},
                columns: [
                	{ display: '年度', name: 'YEAR', width: 80 },
                    { display: '职工工号', name: 'EMP_CODE', width: 80 },
                    { display: '职工名称', name: 'EMP_NAME', width: 80 },
                    { display: '科室信息', name: 'DEPT_NAME', width: 150 },
                    { display: '个人学术地位', columns: []},
                    { display: '个人学术地位积分', name: 'SCORE', width: 120 },
                ],
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                       /*  { type: 'button', label: '导出', listeners: [{ click: putOut }], icon: 'export' }, */
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
            initGrid();
            initColumns();
        })
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
                <td class="label">职工姓名：</td>
                <td class="ipt">
                    <input id="emp_name" type="text" />
                </td>
                <td class="label">科室名称：</td>
                <td class="ipt">
                    <select id="dept_id" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>