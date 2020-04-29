<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
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
			name : 'charge_item_code',
			value : $("#charge_item_code").val()
		});
		grid.options.parms.push({
			name : 'charge_kind_code',
			value : liger.get("charge_kind_code").getValue()
		});
		//加载查询条件
		grid.loadData(grid.where);
	}
	//获取查询条件的数值

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '诊疗项目编码',
				name : 'charge_item_code',
				align : 'left'
			}, {
				display : '诊疗项目名称',
				name : 'charge_item_name',
				align : 'left'
			}, {
				display : '诊疗项目类别编码',
				name : 'charge_kind_code',
				align : 'left'
			}, {
				display : '诊疗项目类别名称',
				name : 'charge_kind_name',
				align : 'left'
			}, {
				display : '单价',
				name : 'price',
				align : 'left',
				render : function(rowdata, rowindex,value) {
					return formatNumber(rowdata.price, 2, 1);
				}
			}, {
				display : '是否停用',
				name : 'is_stop',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					return rowdata.is_stop == 0 ? "否" : "是"
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgChargeItemDicts.do',
			width : '100%',
			height : '100%',
			checkbox : false,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,
			//heightDiff : -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {
		//字典下拉框
		$("#charge_item_code").ligerTextBox({
			width : 160
		});
		$("#charge_kind_code").ligerTextBox({
			width : 160
		});
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">诊疗项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="charge_item_code" type="text" id="charge_item_code"
				ltype="text"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">诊疗项目类别：</td>
			<td align="left" class="l-table-edit-td"><input
				name="charge_kind_code" type="text" id="charge_kind_code"
				ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
