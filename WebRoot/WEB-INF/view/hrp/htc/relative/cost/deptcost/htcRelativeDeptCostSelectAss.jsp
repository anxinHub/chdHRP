<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var group_id = '${group_id}'
	var hos_id = '${hos_id}'
	var copy_code = '${copy_code}'
	var acc_year = '${acc_year}'
	var acc_month = '${acc_month}'
	var dept_no = '${dept_no}'
	var dept_id = '${dept_id}'
	var dept_name = '${dept_name}'
	var parm = "group_id="+group_id+"&"+ "hos_id="+hos_id+"&"+ "copy_code="+copy_code+"&"+ "acc_year="+acc_year+"&"+ 
	"acc_month="+acc_month+"&"+ "dept_id="+dept_id
	$(function() {
		loadDict()//加载下拉框
		//加载数据

		loadHead(null);

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'acct_month',
			value : $(acct_month)
		});
		grid.loadServerData(grid.options.parms);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '成本项目',
				name : 'cost_item_name',
				align : 'left'
			}, {
				display : '医辅分摊成本',
				name : 'tsum',
				align : 'right',
				render: function(rowdata, rowindex, value) {
					return  formatNumber(rowdata.tsum, 2, 1);
					//return amountInWords(rowdata.money);
				}
			}, {
				display : '自筹资金',
				name : 't1',
				align : 'right',
				render: function(rowdata, rowindex, value) {
					return  formatNumber(rowdata.t1, 2, 1);
					//return amountInWords(rowdata.money);
				}
			}, {
				display : '财政资金',
				name : 't2',
				align : 'right',
				render: function(rowdata, rowindex, value) {
					return  formatNumber(rowdata.t2, 2, 1);
					//return amountInWords(rowdata.money);
				}
			}, {
				display : '科研资金',
				name : 't3',
				align : 'right',
				render: function(rowdata, rowindex, value) {
					return  formatNumber(rowdata.t3, 2, 1);
					//return amountInWords(rowdata.money);
				}
			}, {
				display : '教学资金',
				name : 't4',
				align : 'right',
				render: function(rowdata, rowindex, value) {
					return  formatNumber(rowdata.t4, 2, 1);
					//return amountInWords(rowdata.money);
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcRelativeDeptCostAssAmount.do?isCheck=false&' + parm,
			width : '100%',
			height : '100%',
			//checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true//heightDiff: -10,
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div>医辅分摊成本</div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年月：${acc_year}${acc_month}</td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本科室：${dept_name}</td>
		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
