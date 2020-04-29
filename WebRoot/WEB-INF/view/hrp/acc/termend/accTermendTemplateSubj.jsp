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
			columns : [ {
				display : '科目编码', name : 'subj_code', align : 'left', width : 120
			}, {
				display : '科目名称', name : 'subj_name_all', align : 'left'
			} ],
			dataAction : 'server', dataType : 'server',
			usePager : false, url : 'queryAccTermendTemplateSubj.do?isCheck=false&template_id='+'${subjParms.template_id}',
			width : '100%', height : '100%',
			checkbox : true, rownumbers : false, delayLoad : true, selectRowButtonOnly : false,//heightDiff: -10,
		});

		gridManager = $("#subjgrid").ligerGetGridManager();
	}

	function loadDict() {
		var paras ={
			subj_type_code : subj_type_code, 
			subj_type_code1 : subj_type_code1,
			code_like : code_like
		};
		autocomplete("#subj_code", "../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "200");
		
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
			name : 'subj_type_code',
			value : subj_type_code
		});
		grid.options.parms.push({
			name : 'subj_type_code1',
			value : subj_type_code1
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
			<td align="left" class="l-table-edit-td" style="width: 80px;">
				<input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:false}" /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="button" id="but_query" accessKey="Q" value="查询(Q)" />
			</td>
		</tr>
	</table>
	<div id="subjgrid" style="margin-top: 0px; margin-left: 10px;"></div>
</body>
</html>
