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
            grid.loadData(params, 'queryScientificStatistics.do');
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
                title: " 学术论文发表统计打印",//标题
                columns: JSON.stringify(grid.getPrintColumns()),//表头
                class_name: "com.chd.hrp.hr.service.scientificresearch.HrSRStatisticsService",
                method_name: "queryScientificStatisticsByPrint",
                bean_name: "hrSRStatisticsService",
                heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
                foots: '',//表尾需要打印的查询条件,可以为空 
            };
            $.each(grid.getUrlParms(),function(i,obj){
                printPara[obj.name]=obj.value;
            }); 
            //console.log(printPara);
            officeGridPrint(printPara);};
        	
        var initColumns = function () {
            ajaxPostData({
                url: 'queryScientificDynamicTabHead.do?isCheck=false&field_tab_code=DIC_PROJ_LEVEL',
                data: { year: year.getValue() },
                success: function (res) {
                    var gridColumns = [
                        { display: '职工工号', name: 'EMP_CODE', width: 80 },
                        { display: '职工名称', name: 'EMP_NAME', width: 80 },
                        { display: '科室信息', name: 'DEPT_NAME', width: 150 },
                        { display: '科研项目与成果', columns: res.columns },
                        { display: '科研项目与成果积分', name: 'SCORE', width: 120, align: 'center' },
                    ];
                    grid.option('columns', gridColumns);
                    grid.option('title','科研项目与成果统计（满分：'+res.fullScore.proj+'分）')
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
                    { display: '职工工号', name: 'EMP_CODE', width: 80 },
                    { display: '职工名称', name: 'EMP_NAME', width: 80 },
                    { display: '科室信息', name: 'DEPT_NAME', width: 150 },
                    { display: '科研项目与成果', columns: [
                        { display: '科研项目立项', columns: [
                            { display: '国家级(5分)', name: 'a', width: 120 },
                            { display: '省部级(4分)', name: 'b', width: 120 },
                            { display: '卫生厅级(3分)', name: 'c', width: 120 },
                            { display: '地厅级(2分)', name: 'd', width: 120 }
                        ]},
                        { display: '科研项目验收', columns: [
                            { display: '国家级(5分)', name: 'e', width: 120 },
                            { display: '省部级(4分)', name: 'f', width: 120 },
                            { display: '地厅级(3分)', name: 'g', width: 120 },
                            { display: '普通验收(2分)', name: 'h', width: 120 }
                        ]},
                        { display: '科研成果奖励', columns: [
                            { display: '省部级二等奖及以上(5分)', name: 'i', width: 160 },
                            { display: '省部级三等奖(4分)', name: 'j', width: 120 },
                            { display: '地厅级二等奖以上(3分)', name: 'k', width: 150 },
                            { display: '地厅级三等奖(2分)', name: 'l', width: 120 },
                            { display: '东阳市科技奖(1分)', name: 'm', width: 120 }
                        ]},
                        { display: '专利', columns: [
                            { display: '国家发明专利(2分)', name: 'n', width: 120 },
                            { display: '实用新型专利(1分)', name: 'o', width: 120 },
                            { display: '外观专利或软件著作权(0.5分)', name: 'p', width: 180 },
                        ]},
                    ]},
                    { display: '科研项目与成果积分', name: 'SCORE', width: 130 },
                ],
                toolbar: {
                    items: [
                        { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
                        /* { type: 'button', label: '导出', listeners: [{ click: putOut }], icon: 'export' }, */
                        { type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
                    ]
                }
            };
            grid = $("#mainGrid").etGrid(paramObj);
        };

        $(function () {
            initFrom();
            initGrid();
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