<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,grid,tab,from" name="plugins" />
</jsp:include>
</head>
<script type="text/javascript">
	var grid;
	var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
	var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
	var data = parentWindow.detailData;
	
	var query = function (queryFor) {
		params = [ {
			name: 'year_month', value: data.year_month 
		}, {
			name: 'dept_id_c', value: data.dept_id_c 
		}, {
			name: 'emp_id', value: data.emp_id 
		}, {
			name: 'attend_code', value: data.attend_code 
		} ]
		
		grid.loadData(params, "queryAttendResultManageJb.do?isCheck=false");
	};
	
	var initGrid = function () {
		var columns = [ {
			display: '加班编号', name: 'attend_overtime_code', width: 120, 
		}, {
			display: '加班类型', name: 'attend_name', width: 120,
		},{
			display: '加班日期', name: 'overtime_date', width: 120,
		}, {
			display: '加班开始时间', name: 'begin_date', width: 120,
		}, {
			display: '加班结束时间', name: 'end_date', width: 120,
		}, {
			display: '加班天数', name: 'attend_accchadays', width: 120, align: "right", 
		} ];
		
		var paramObj = {
			height: '100%',
			inWindowHeight: true,
			checkbox: false,
			usePager: false,
			columns: columns,
			//flexHeight: true, 
		};
		
		grid = $("#mainGrid").etGrid(paramObj);
	};

	var initForm = function () {
		$("#year_month").text(data.year_month);
		$("#dept_name_c").text(data.dept_name_c);
		$("#emp_name").text(data.emp_name);
	};

	$(function () {
		initForm();
		initGrid();
		query();
	})
</script>

<body>
	<div class="center">
		<table class="table-layout">
			<tr>
				<td class="label">考勤周期：</td>
				<td class="ipt" >
					<span id="year_month"></span>
				</td>

				<td class="label">出勤科室：</td>
				<td class="ipt">
					<span id="dept_name_c"></span>
				</td>
				
				<td class="label">职工：</td>
				<td class="ipt">
					<span id="emp_name"></span>
				</td>
			</tr>
		</table>
		<div id="mainGrid"></div>
	</div>
</body>

</html>