<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;

	var template_id;
	var year_month;
	$(function() {
		loadHead(null); //加载数据
		loadDict();
		query();
	});
	
	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '申领单号',
				name : 'apply_no',
				align : 'left',
				width : 110
			}, {
				display : '申请科室',
				name : 'dept_name',
				align : 'left',
				width : 200
			}, {
				display : '待处理数量',
				name : 'app_amount',
				align : 'left',
				width : 90
			}, {
				display : '处理数量',
				name : 'do_amount',
				align : 'right',
				width : 90,
				editor : {type : 'float'},
				render : function(rowdata){
					return formatNumber(rowdata.rate == null?0:rowdata.rate, 2, 0);
				}
			} ],
			dataAction : 'server', dataType : 'server', usePager : false,
			url : 'queryAccFundExtractDept.do?isCheck=false',
			width : '100%', height : '100%', enabledEdit: true,
			checkbox : false, rownumbers : false, delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: -10,
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadDict() {
    	$("#inv_code").ligerTextBox({width:160,disabled: true }); 
    	$("#inv_name").ligerTextBox({width:160,disabled: true }); 
    	
		$("#save").ligerButton({click: save, width: 90});
		$("#close").ligerButton({click: this_close, width: 90});
	}
	
	function this_close(){
 		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" >
				药品编码：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_code" type="text" id="inv_code" disabled="disabled" ltype="text" validate="{required:false}" />
			</td>
			<td align="right" class="l-table-edit-td">
				药品编码：
			</td>
			<td align="left" class="l-table-edit-td">
				<input name="inv_name" type="text" id="inv_name" disabled="disabled" ltype="text" validate="{required:false}" />
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
		<tr>
			<td align="center" class="l-table-edit-td">
				<button id="save" accessKey="S"><b>保存（<u>S</u>）</b></button> 
				&nbsp;&nbsp;				
				<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
			</td>
		</tr>
	</table>
</body>
</html>
