<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,grid,select" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var grid, dept_id, emp_id;
	$(function(){
		initSelect();
		initGrid();
		refreshHead();
		query();
	});
	
	var initSelect = function(){
		dept_id = $("#dept_id").etSelect({
			url: '../../../queryHosDeptSelect.do?isCheck=false',
            defaultValue: 'none',
            onChange: query
		});
		emp_id = $("#emp_id").etSelect({
			url: '../../../queryPerson.do?isCheck=false',
            defaultValue: 'none',
            onChange: query
		});
	}
	
	var columns_1 = [
		{ display : '部门名称', name : 'dept_name', width : 120 },
		{ display : '员工编号', name : 'emp_code', width : 120 },
		{ display : '姓名', name : 'emp_name', width : 120 }
	];
	var columns_2 = [
		{ display : '合计', columns:[
				{ display :'单位', name: 'unit_amount_count', width:100, align: "center" },
				{ display :'个人', name: 'individual_amount_count', width:100, align: "center"  }
			]
		}
	];
	var columns = columns_1.concat(columns_2);
	var initGrid = function(){
   		var paramObj = {
   			height : '100%',
   			inWindowHeight : true,
   			checkbox : true,
   			columns : columns,
   			toolbar : {
   				items : [
   					{ type : 'button', label : '查询', listeners : [ { click : query } ], icon : 'search' }
   				]
   			}
   		};
   		grid = $("#mainGrid").etGrid(paramObj);
	}
	
	var refreshHead = function(){
		ajaxPostData({url:'queryBehoovePayInsurHead.do?isCheck=false', success:function (res) {// 获取数据
			columns = columns_1.concat(res.columns, columns_2);
			grid.option('columns', columns);
			
			grid.refreshView();

        }});
	}
	
	// 主查询
	var query = function(){
// 		alert("查询");
		var param = [
			{ name: "emp_id", value: emp_id.getValue() },
			{ name: "dept_id", value: dept_id.getValue() }
		];
		grid.loadData(param, "queryBehoovePayInsur.do");
	}
</script>
</head>
<body>
	<div class="main">
		<table class="table-layout">
			<tr>
				<td class="label">部门：</td>
				<td class="ipt"><select id="dept_id" style="width: 180px;"/></td>
				<td class="label">职工：</td>
				<td class="ipt"><select id="emp_id" style="width: 180px;"/></td>
			</tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>
</html>