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
	var accgrid, budggrid;
	
	$(function() {
		
		loadHead(null); //加载数据
		loadDict();
		acc_query();
		budg_query();
	});
	//科目Grid
	function loadHead() {
		 
		accgrid = $("#accgrid").ligerGrid({
			columns : [ {
				display : '科目编码', name : 'subj_code', align : 'left', width : 200
			}, {
				display : '科目名称', name : 'subj_name_all', align : 'left', width : 400
			} ],
			dataAction : 'server', dataType : 'server',
			usePager : false, url : '../../queryAccTermendTemplateSubj.do?isCheck=false&template_id='+'${subjParms.template_id}',
			width : '98%', height : '100%',
			checkbox : true, rownumbers : false, delayLoad : true, selectRowButtonOnly : false,//heightDiff: -10,
		});
		 
		budggrid = $("#budggrid").ligerGrid({
			columns : [ {
				display : '科目编码', name : 'subj_code', align : 'left', width : 200
			}, {
				display : '科目名称', name : 'subj_name_all', align : 'left', width : 400
			} ],
			dataAction : 'server', dataType : 'server',
			usePager : false, url : '../../queryAccTermendTemplateSubj.do?isCheck=false&template_id='+'${subjParms.template_id}',
			width : '98%', height : '100%',
			checkbox : true, rownumbers : false, delayLoad : true, selectRowButtonOnly : false,//heightDiff: -10,
		});
		
	}

	function loadDict() {
		var paras ={
			subj_type_code : '04', 
			subj_type_code1 : '05',
		};
		autocomplete("#acc_subj_code", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "200", "", subjWidth);
		paras ={
			subj_type_code : '06', 
			subj_type_code1 : '07',
		};
		autocomplete("#budg_subj_code", "../../../querySubjAll.do?isCheck=false", "id", "text", true, true, paras, false, "", "200", "", subjWidth);
		
		$("#acc_query").ligerButton({click : acc_query, width : 90});
		$("#budg_query").ligerButton({click : budg_query, width : 90});
	}

	function acc_query() {
		 
		var subj_code_value = "";
		accgrid.options.parms = [];
		accgrid.options.newPage = 1;
		if(liger.get("acc_subj_code").getValue() != ""){
			subj_code_value = liger.get("acc_subj_code").getValue();
		}
		//根据表字段进行添加查询条件
		accgrid.options.parms.push({
			name : 'subj_code',
			value : subj_code_value
		});
		accgrid.options.parms.push({
			name : 'subj_type_code',
			value : '04'
		});
		accgrid.options.parms.push({
			name : 'subj_type_code1',
			value : '05'
		});
		
		//加载查询条件
		accgrid.loadData(accgrid.where);
	}

	function budg_query() {
		 
		var subj_code_value = "";
		budggrid.options.parms = [];
		budggrid.options.newPage = 1;
		if(liger.get("budg_subj_code").getValue() != ""){
			subj_code_value = liger.get("budg_subj_code").getValue();
		}
		//根据表字段进行添加查询条件
		budggrid.options.parms.push({
			name : 'subj_code',
			value : subj_code_value
		});
		budggrid.options.parms.push({
			name : 'subj_type_code',
			value : '06'
		});
		budggrid.options.parms.push({
			name : 'subj_type_code1',
			value : '07'
		});
		
		//加载查询条件
		budggrid.loadData(budggrid.where);
	}

	function getSelectRows() {
		var rows1 = accgrid.getCheckedRows();
		var rows2 = budggrid.getCheckedRows();

		return rows1.concat(rows2);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div class="l-clear"></div>
	<div style="float: left; width: 50%">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px; width: 60px;">财务科目：</td>
				<td align="left" class="l-table-edit-td" style="width: 80px;">
					<input name="acc_subj_code" type="text" id="acc_subj_code" ltype="text" validate="{required:false}" /></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<input type="button" id="acc_query" accessKey="Q" value="查询(Q)" />
				</td>
			</tr>
		</table>
		<div id="accgrid" style="margin-top: 0px; margin-left: 10px;"></div>
	</div>
	<div style="float: right; width: 50%">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px; width: 60px;">预算科目：</td>
				<td align="left" class="l-table-edit-td" style="width: 80px;">
					<input name="budg_subj_code" type="text" id="budg_subj_code" ltype="text" validate="{required:false}" /></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<input type="button" id="budg_query" accessKey="Q" value="查询(Q)" />
				</td>
			</tr>
		</table>
		<div id="budggrid" style="margin-top: 0px; margin-left: 10px;"></div>
	</div>
</body>
</html>
