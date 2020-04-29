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
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var subj_type_code;
	var code_like;
	var subj_type_code1;
	
	$(function() {
		subj_type_code = '${subjParms.subj_type_code}';
		subj_type_code1 = '${subjParms.subj_type_code1}' || "";
		code_like = '${subjParms.code_like}' || "";
		loadHead(null); //加载数据
		loadDict();
		query();
	});
	//科目Grid
	function loadHead() {
		grid = $("#subjgrid").ligerGrid({
			columns : [ 
			            {display : '科目编码', name : 'subj_code', align : 'left', width : 200}, 
			            {display : '科目名称', name : 'subj_name_all', align : 'left', width : 400} 
			          ],
			dataAction : 'server', dataType : 'server',
			usePager : false, url : 'queryAccTermendTemplateSubj.do?isCheck=false',
			width : '650px', height : '100%',
			checkbox : true, rownumbers : false, delayLoad : true, selectRowButtonOnly : false,//heightDiff: -10,
		});

		gridManager = $("#subjgrid").ligerGetGridManager();
	}

	function loadDict() {
		var param ={
			SUBJ_NATURE_CODE1 : '04',
			SUBJ_NATURE_CODE2 : '05',
			is_last : 1
		};
		$("#subj_code").ligerComboBox({
			parms : param,
			url : 'queryAllSubj.do?isCheck=false',
			valueField : 'subj_code_hz',
			textField : 'subj_code_gl',
			selectBoxWidth : 300,
			autocomplete : true,
			width : 300
		});
		
		$("#but_query").ligerButton({click : query, width : 90});
	}

	function query() {
		var subj_code_value = "";
		grid.options.parms = [];
		grid.options.newPage = 1;
		if(liger.get("subj_code").getValue() != ""){
			subj_code_value = liger.get("subj_code").getValue();
		}
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'subj_code',
			value : subj_code_value
		});
		grid.options.parms.push({
			name : 'SUBJ_NATURE_CODE1',
			value : '04'
		});
		grid.options.parms.push({
			name : 'SUBJ_NATURE_CODE2',
			value : '05'
		});
		grid.options.parms.push({
			name : 'code_like',
			value : code_like
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}

	function getSelectRows() {
		var rows = grid.getCheckedRows();

		return rows;
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div class="l-clear"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td"
				style="padding-left: 20px; width: 40px;">科目：</td>
			<td align="left" class="l-table-edit-td" style="width: 120px;">
				<input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:false}" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="button" id="but_query" accessKey="Q" value="查询(Q)" />
			</td>
		</tr>
	</table>
	<div id="subjgrid" style="margin-top: 0px; margin-left: 10px;"></div>
</body>
</html>
