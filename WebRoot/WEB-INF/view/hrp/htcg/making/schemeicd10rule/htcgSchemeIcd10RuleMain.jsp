<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<style type="text/css">
html {
	overflow-x: hidden;
}
</style>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid, gridManager;
	var grid2_1, gridManager2_1;
	var grid2_2, gridManager2_2;
	var grid3, gridManager3;
	var Parms, para_scheme_code, para_drgs_code, para_icd10_code, para_icd9_code;
	$(function() {
		init();
		loadDict();
		loadHead();
		loadHead2_1();
		loadHead2_2();
		loadHead3();
	});

	function query_zd() {
		loadHead2_1();
	}

	function query_ss() {
		loadHead2_2();
	}

	function query_zdy() {
		loadHead3();
	}

	function init() {
		$("#query").ligerButton({
			click : function() {
				loadHead();
			}
		});

		$("#query_zd").ligerButton({});
		$("#add_zd").ligerButton({});
		$("#delete_zd").ligerButton({});
		$("#query_ss").ligerButton({});
		$("#add_ss").ligerButton({});
		$("#delete_ss").ligerButton({});
		$("#query_zdy").ligerButton({});
		$("#add_zdy").ligerButton({});
		$("#delete_zdy").ligerButton({});
	
		$("#panel1").ligerPanel({
			title : '方案病种信息',
			width : '100%',
			height : 400,
			showToggle:false,
		});
		$("#panel2-1").ligerPanel({
			title : '诊断规则信息',
			width : '49.7%',
			height : 400,
			showToggle:false,
		});
		$("#panel2-2").ligerPanel({
			title : '手术规则信息',
			width : '49.7%',
			height : 400,
			showToggle:false,
		});
		$("#panel3").ligerPanel({
			title : '自定义规则信息',
			width : '100%',
			height : 400,
			showToggle:false,
		});
	}

	function loadHead() {
		para_scheme_code = liger.get("scheme_code").getValue();
		para_drgs_code = $("#drgs_code").val();
		Parms = {
			scheme_code : para_scheme_code,
			drgs_code : para_drgs_code
		}
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '核算方案编码',
				name : 'scheme_code',
				width : '10%',
				align : 'left'
			}, {
				display : '核算方案名称',
				name : 'scheme_name',
				width : '10%',
				align : 'left'
			}, {
				display : '病种编码',
				name : 'drgs_code',
				width : '10%',
				align : 'left'
			}, {
				display : '病种名称',
				name : 'drgs_name',
				width : '10%',
				align : 'left'
			}, {
				display : '入组规则',
				name : 'icd_rule_name',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgSchemeDrgsRule.do',
			parms : Parms,
			width : '100%',
			height : 400,
			enabledEdit : true,
			checkbox : true,
			isSingleCheck : true,
			rownumbers : true,
			selectRowButtonOnly : true,
			heightDiff:-20,
			onCheckRow : function(checked, data, rowindex, rowobj) {
				var index = gridManager.getCheckedRows()
				if (index.length == 0) {
					para_scheme_code = ''
					para_drgs_code = ''
					loadHead2_1();
					loadHead2_2();

				} else {
					para_scheme_code = data.scheme_code
					para_drgs_code = data.drgs_code
					loadHead2_1();
					loadHead2_2();
				}
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadHead2_1() {
		Parms = {
			scheme_code : para_scheme_code,
			drgs_code : para_drgs_code,
			icd10_code : $("#icd10_code").val()
		}
		grid2_1 = $("#maingrid2_1").ligerGrid({
			columns : [ {
				display : '诊断编码',
				name : 'icd10_code',
				align : 'left'
			}, {
				display : '诊断名称',
				name : 'icd10_name',
				align : 'left'
			}, {
				display : '入组规则编码',
				name : 'icd_rule_code',
				align : 'left'
			}, {
				display : '入组规则名称',
				name : 'icd_rule_name',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgSchemeIcd10Rule.do?isCheck=false',
			parms : Parms,
			width : '100%',
			height : 400,
			enabledEdit : true,
			checkbox : true,
			rownumbers : true,
			heightDiff:-40,
			selectRowButtonOnly : true,

		});

		gridManager2_1 = $("#maingrid2_1").ligerGetGridManager();
	}
	function loadHead2_2() {
		Parms = {
			scheme_code : para_scheme_code,
			drgs_code : para_drgs_code,
			icd9_code : $("#icd9_code").val()
		}
		grid2_2 = $("#maingrid2_2").ligerGrid({
			columns : [ {
				display : '手术编码',
				name : 'icd9_code',
				align : 'left'
			}, {
				display : '手术名称',
				name : 'icd9_name',
				align : 'left'
			}, {
				display : '入组规则编码',
				name : 'icd_rule_code',
				align : 'left'
			}, {
				display : '入组规则名称',
				name : 'icd_rule_name',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgSchemeIcd9Rule.do?isCheck=false',
			parms : Parms,
			width : '100%',
			height : 400,
			enabledEdit : true,
			checkbox : true,
			rownumbers : true,
			heightDiff:-40,
			selectRowButtonOnly : true,

		});

		gridManager2_2 = $("#maingrid2_2").ligerGetGridManager();
	}

	function loadHead3() {
		grid3 = $("#maingrid3").ligerGrid({
			columns : [ {
				display : '自定义编码',
				name : 'general_rule_code',
				align : 'left'
			}, {
				display : '自定义名称',
				name : 'general_rule_name',
				align : 'left'
			}, {
				display : '自定义说明',
				name : 'general_rule_note',
				align : 'left'
			}, {
				display : '入组规则',
				name : 'general_rule_chs',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcgSchemeGeneralRule.do?isCheck=false',
			width : '100%',
			height : 400,
			enabledEdit : true,
			checkbox : true,
			rownumbers : true,
			heightDiff:-10,
			selectRowButtonOnly : true

		});

		gridManager3 = $("#maingrid3").ligerGetGridManager();
	}

	function loadDict() {
		//字典下拉框
		autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false", "id", "text", true,true);

		$("#drgs_code").ligerTextBox({
			width : 160
		});
		$("#icd10_code").ligerTextBox({
			width : 160
		});
		$("#icd9_code").ligerTextBox({
			width : 160
		});
		$("#clp_step_code").ligerTextBox({
			width : 160
		});
	}

	function add_zd() {

		var data = gridManager.getCheckedRows();
		if (!data.length) {
			$.ligerDialog.error('请选择方案病种信息');
			return;
		}
		var drgs_code = data[0].drgs_code;
		var scheme_code = data[0].scheme_code;
		$.ligerDialog.open({
			url : 'htcgSchemeIcd10RuleAddPage.do?isCheck=false&drgs_code='
					+ drgs_code + '&scheme_code=' + scheme_code + '&flag=1',
			top : 100,
			height : 300,
			width : 400,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveRule();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	function delete_zd() {

		var data = gridManager.getCheckedRows();
		var data2_1 = gridManager2_1.getCheckedRows();
		if (!data.length) {
			$.ligerDialog.error('请选择方案病种信息');
			return;
		}

		var drgs_code = data[0].drgs_code;
		var scheme_code = data[0].scheme_code;

		var ParamVo = [];
		$(data2_1).each(
				function() {
					ParamVo.push(drgs_code + "@" + scheme_code + "@"
							+ this.icd10_code);//实际代码中temp替换主键
				});
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl(
						"deleteHtcgSchemeIcd10Rule.do?isCheck=false", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								loadHead2_1();
							}
						});
			}
		});

	}

	function add_ss() {

		var data = gridManager.getCheckedRows();
		if (!data.length) {
			$.ligerDialog.error('请选择方案病种信息');
			return;
		}

		var drgs_code = data[0].drgs_code;
		var scheme_code = data[0].scheme_code;

		$.ligerDialog.open({
			url : 'htcgSchemeIcd10RuleAddPage.do?isCheck=false&drgs_code='
					+ drgs_code + '&scheme_code=' + scheme_code + '&flag=2',
			top : 100,
			height : 300,
			width : 400,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.saveRule();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
		return;

	}

	function delete_ss() {

		var data = gridManager.getCheckedRows();
		var data2_2 = gridManager2_2.getCheckedRows();
		if (!data.length) {
			$.ligerDialog.error('请选择方案病种信息');
			return;
		}

		var drgs_code = data[0].drgs_code;
		var scheme_code = data[0].scheme_code;

		var ParamVo = [];
		$(data2_2).each(function() {
			ParamVo.push(drgs_code + "@" + scheme_code + "@" + this.icd9_code);//实际代码中temp替换主键
		});
		$.ligerDialog.confirm('确定删除?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl(
						"deleteHtcgSchemeIcd9Rule.do?isCheck=false", {
							ParamVo : ParamVo.toString()
						}, function(responseData) {
							if (responseData.state == "true") {
								loadHead2_2();
							}
						});
			}
		});

	}

	function add_zdy() {

	}

	function delete_zdy() {

	}
</script>

</head>

<body>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案：</td>
			<td align="left" class="l-table-edit-td"><input
				name="scheme_code" type="text" id="scheme_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">病种：</td>
			<td align="left" class="l-table-edit-td"><input name="drgs_code"
				type="text" id="drgs_code" ltype="text"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
			<td align="center"><div id="query">查 询</div></td>
			<td align="left"></td>
		</tr>
	</table>
	<div style="width: 100%;">
		<div id="panel1" style="width: 100%;">
			<div id="maingrid"></div>
		</div>
		<div class="l-clear"></div>
	</div>
	<div style="width: 100%;">
		<div id="panel2-1" style="float: left">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">诊断信息：</td>
					<td align="left" class="l-table-edit-td"><input name="icd10_code" type="text" id="icd10_code" ltype="text"/></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
					<td align="center"><div id="query_zd" onclick="query_zd()">检索</div></td>
					<td align="left"></td>
					<td align="center" style="padding-left: 20px;"><div id="add_zd" onclick="add_zd()">添加</div></td>
					<td align="left"></td>
					<td align="center" style="padding-left: 20px;"><div id="delete_zd" onclick="delete_zd()">删除</div></td>
					<td align="left"></td>
				</tr>
			</table>
			<div id="maingrid2_1"></div>
		</div>
		<div id="panel2-2" style="float: right; margin-right: 0px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;">手术信息：</td>
					<td align="left" class="l-table-edit-td"><input name="icd9_code" type="text" id="icd9_code" ltype="text"/></td>
					<td align="left"></td>
					<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
					<td align="center"><div id="query_ss" onclick="query_ss()">检索</div></td>
					<td align="center" style="padding-left: 20px;"><div id="add_ss" onclick="add_ss()">添加</div></td>
					<td align="center" style="padding-left: 20px;"><div id="delete_ss" onclick="delete_ss()">删除</div></td>
					<td align="left"></td>
				</tr>
			</table>
			<div id="maingrid2_2"></div>
		</div>
		<div class="l-clear"></div>
	</div>
	<div id="panel3" style="margin-top: 10px; clear: both;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">自定义规则：</td>
				<td align="left" class="l-table-edit-td"><input name="clp_step_code" type="text" id="clp_step_code" ltype="text" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"></td>
				<td align="center"><div id="query_zdy" onclick="query_zdy()">检索</div></td>
				<td align="center" style="padding-left: 20px;"><div id="add_zdy" onclick="add_zdy()">添加</div></td>
				<td align="center" style="padding-left: 20px;"><div id="delete_zdy" onclick="delete_zdy()">删除</div></td>
				<td align="left"></td>
			</tr>
		</table>
		<div id="maingrid3"></div>
	</div>
</body>
</html>
