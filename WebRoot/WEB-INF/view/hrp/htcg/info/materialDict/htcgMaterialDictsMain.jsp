<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
			name : 'inv_code',
			value : $("#inv_code").val()
		});
		grid.options.parms.push({
			name : 'mat_type_code',
			value : $("#mat_type_code").val()
		});
		grid.options.parms.push({
			name : 'fac_code',
			value : $("#fac_code").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '材料编码',
				name : 'mate_code',
				align : 'left'
			}, {
				display : '材料名称',
				name : 'mate_name',
				align : 'left'
			}, {
				display : '材料类别',
				name : 'mate_type_name',
				align : 'left'
			}, {
				display : '规格',
				name : 'mate_mode',
				align : 'left'
			}, {
				display : '计量单位',
				name : 'meas_name',
				align : 'left'
			}, {
				display : '生产厂商',
				name : 'fac_name',
				align : 'left'
			}, {
				display : '单价',
				name : 'price',
				align : 'left',
				render : function(rowdata, rowindex, value) {
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
			url : 'queryHtcgMaterialDicts.do',
			width : '100%',
			height : '100%',
			delayLoad : true,
			checkbox : true,
			rownumbers : true,
			selectRowButtonOnly : true,
			//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function loadDict() {

		$("#inv_code").ligerTextBox({
			width : 160
		});
		$("#mat_type_code").ligerTextBox({
			width : 160
		});
		$("#fac_code").ligerTextBox({
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				材料：</td>
			<td align="left" class="l-table-edit-td"><input name="inv_code"
				type="text" id="inv_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">材料类别：</td>
			<td align="left" class="l-table-edit-td"><input
				name="mate_type_code" type="text" id="mat_type_code" ltype="text" /></td>
			</td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">生产厂家：</td>
			<td align="left" class="l-table-edit-td"><input
				name="fac_code" type="text" id="fac_code" ltype="text" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
