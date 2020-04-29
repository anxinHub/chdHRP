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
        var year, <%--dept_code, title_code, duty_code,--%>level_code, prize;
        var grid;
        var initFrom = function () {
            year = $("#year").etDatepicker({
                view: "years",
                minView: "years",
                dateFormat: "yyyy",
                defaultDate: ['yyyy'],
                onChange: query
            });
            <%--dept_code = $("#dept_code").etSelect({
                url: "../queryHosDeptSelect.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
            title_code = $("#title_code").etSelect({
                url: "../queryHrTitle.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
            duty_code = $("#duty_code").etSelect({
                url: "../queryDuty.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });--%>
            level_code = $("#level_code").etSelect({
                url: "../queryDicLevel.do?isCheck=false",
                defaultValue: "none",
                onChange: query
            });
            prize = $("#prize").etSelect({
                url: "queryPrize.do?isCheck=false",
                defaultValue: "none",
                onChange: query
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
                { name: 'emp_id', value: emp_id.getValue()},
                <%--{ name: 'dept_id', value:dept_code.getValue().split('@')[1] },
                { name: 'dept_no', value:dept_code.getValue().split('@')[0] },
                { name: 'title_code', value: title_code.getValue() },
                { name: 'duty_code', value: duty_code.getValue() },--%>
                { name: 'level_code', value: level_code.getValue() },
                { name: 'imtheme', value: $("#improve_theme").val() },
                { name: 'prize', value: prize.getValue() },
            ];
            grid.loadData(params,"queryAdministrationStatistics.do");
        };
        
        var print = function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":$('#dept_name').val()},
        	          {"cell":1,"value":dept_code},
            		] */  }; 
        	var printPara={
              		title: " 年度行政能力统计打印",//标题
              		columns: JSON.stringify(grid.getPrintColumns()),//表头
              		class_name: "com.chd.hrp.hr.service.nursing.HrAdministrationStatisticsService",
           			method_name: "queryAdministrationStatisticsByPrint",
           			bean_name: "hrAdministrationStatisticsService",
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
              /*   { display: '教学日期', name: 'year', width: 120 }, */
                { display: '职工工号', name: 'emp_code', width: 120 },
                { display: '职工名称', name: 'emp_name', width: 120 },
                { display: '护理等级', name: 'level_name', width: 120 },
                { display: '改善主题', name: 'imtheme', width: 120 },
                { display: '职务', name: 'title_code', width: 120 },
                { display: '获奖情况', name: 'field_col_name', width: 120 }
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                /* dataModel: {
                    // url: '.do?isCheck=false'
                    url: 'queryTeachingAbility.do'
                }, */
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
                <td class="label">统计年度：</td>
                <td class="ipt">
                    <input id="year" type="text" />
                </td>
                <td class="label">职工：</td>
                <td class="ipt">
                    <select id="emp_id" style="width:180px;"></select>
                </td>
                 <td class="label">护理等级：</td>
                <td class="ipt">
                    <select id="level_code" style="width:180px;"></select>
                </td>
            </tr>
            <tr>
                <td class="label">改善主题：</td>
                <td class="ipt">
                    <input type="text" id="improve_theme">
                </td>
                <td class="label">获奖情况：</td>
                <td class="ipt">
                    <select id="prize" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>