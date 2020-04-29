<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>排班统计</title>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,select,grid,datepicker,pageOffice" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid, start_date, end_date, dept_id;
	
	$(function (){
		initSelect();
		initGrid();
		query();
	});
	
	// 初始化表格
	var initGrid = function(){
		var columns = [
			{ display: '年度', name: 'pb_year', width: 50 },
			{ display: '月份', name: 'pb_month', width: 50 },
			{ display: '职工姓名', name: 'emp_name', width: 100 },
			{ display: '部门名称', name: 'dept_name', width: 100 },
		];
		
		for(var i = 1; i <= 31; i++){
			columns.push({
				display: i, columns: [
					{ display: '排班部门', name: 'pb_dept_' + i, width: 100},
					{ display: '出勤部门', name: 'cq_dept_' + i, width: 100}
				]
			});
		}
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			editable: false,
			usePager: true,
			columns: columns,
			pageModel: {
		        type: 'remote'
		    },
			freezeCols: 4,
			toolbar: {
				items: [
					{ type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
					{ type: 'button', label: '打印', listeners: [{ click: print }], icon: 'print' }
				]
			}
		};
		grid = $("#mainGrid").etGrid(paramObj);
	};
	
	// 初始化时间控件与下拉框
	var initSelect = function(){
		start_date = $("#start_date").etDatepicker({
			clearButton : false,
			dateFormat : 'yyyy-mm-dd',
			defaultDate : moment().startOf('month').format('YYYY-MM-DD'),// 默认本月初
			onChange: query
		});
		
		end_date = $("#end_date").etDatepicker({
			clearButton : false,
			dateFormat : 'yyyy-mm-dd',
			defaultDate : moment().endOf('month').format('YYYY-MM-DD'),// 默认本月末
			onChange: query
		});
		
		dept_id = $("#dept_id").etSelect({
			url: '../../queryHosDeptSelect.do?isCheck=false',
			defaultValue: "none",
			onChange: query
		});
	};
	
	// 查询
	var query = function(){
		var params = [
			{ name: 'start_date', value: start_date.getValue() },
			{ name: 'end_date', value: end_date.getValue() },
			{ name: 'emp_name', value: $("#emp_name").val() },
			{ name: 'dept_id', value: dept_id.getValue() }
        ];
        grid.loadData(params, 'queryStatistics.do');
	};
	
	// 打印
	var print = function(){
		if(grid.getAllData()==null){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads={}; 
    	var printPara={
            title: " 排班统计打印",//标题
            columns: JSON.stringify(grid.getPrintColumns()),//表头
            class_name: "com.chd.hrp.hr.service.attendancemanagement.scheduling.HrSchedulingService",
            method_name: "queryPBStatisticsPrint",
            bean_name: "hrSchedulingService",
            heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
            foots: '',//表尾需要打印的查询条件,可以为空 
        };
        $.each(grid.getUrlParms(),function(i, obj){
            printPara[obj.name]=obj.value;
        }); 

        officeGridPrint(printPara);
	};
</script>
</head>
<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">开始日期：</td>
            	<td class="ipt"><input id="start_date" type="text" style="width:140px;"></td>
				<td class="label">结束日期：</td>
            	<td class="ipt"><input id="end_date" type="text" style="width:140px;"></td>
				<td class="label">部门：</td>
            	<td class="ipt"><input id="dept_id" type="text" style="width:140px;"></td>
				<td class="label">职工：</td>
				<td class="ipt"><input id="emp_name" type="text" style="width:140px;"></td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>
</html>