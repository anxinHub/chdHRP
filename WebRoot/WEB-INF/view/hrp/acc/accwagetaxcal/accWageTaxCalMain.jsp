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
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css"
	rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.3.2.min.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var grid;

	var rightgrid;

	var gridManager = null;

	var rightgridManager = null;

	var userUpdateStr;

	var para;

	$(function() {
		loadDict(null);

		loadHead(null);

		$("#note").ligerTextBox({
			width : 475
		});

		$("#layout1").ligerLayout({
			leftWidth : 500,
			allowLeftCollapse : false,
			allowRightCollapse : false
		});

	});

	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		var acc_year = $("#acc_year").val();

		var wage_code = liger.get("wage_code1").getValue();

		if (acc_year == "") {

			$.ligerDialog.error('会计年度为必填项！');

			return;

		}
		grid.options.parms.push({
			name : 'acc_year',
			value : acc_year
		});

		grid.options.parms.push({
			name : 'wage_code',
			value : wage_code
		});

		grid.options.parms.push({
			name : 'kind_code',
			value : liger.get("emp_kind1").getValue()
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {

		grid = $("#maingrid")
				.ligerGrid(
						{

							columns : [
									{
										display : '工资套编码',
										name : 'wage_code',
										align : 'left',
										width : '7%'
									},
									{
										display : '工资套名称',
										name : 'wage_name',
										align : 'left',
										width : '13%'
									},
									{
										display : '职工分类',
										name : 'kind_name',
										align : 'left',
										width : '10%'
									},
									{
										display : '应税项',
										name : 'rate',
										align : 'center',
										width : '10%',
										render : function(rowdata) {
											if (rowdata.wage_code != null) {
												return "<a href=javascript:set_wage_cal('"
														+ rowdata.kind_code
														+ "|"
														+ rowdata.wage_code
														+ "|"
														+ rowdata.wage_name
														+ "|"
														+ rowdata.kind_name
														+ "|"
														+ rowdata.cal_id
														+ "|"
														+ $.trim(rowdata.note)
														+ "')>编辑<a/>";
											}
										}
									} ],

							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryAccWageTaxCal.do',

							width : '100%',
							height : '100%',
							checkbox : true,
							rownumbers : true,delayLoad:true,

							selectRowButtonOnly : true,

							toolbar : {
								items : [ {
									text : '查询',
									id : 'search',
									click : query,
									icon : 'search'
								}, {
									line : true
								}, {
									text : '删除',
									id : 'del',
									click : remove,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '继承',
									id : 'extend',
									click : extend,
									icon : 'extend'
								}, ]
							}

						});

		gridManager = $("#maingrid").ligerGetGridManager();

	}

	function remove() {

		var data = gridManager.getCheckedRows();

		if (data.length == 0) {

			$.ligerDialog.error('请选择行');

		} else {

			var ParamVo = [];

			$(data).each(function() {

				ParamVo.push(
				//表的主键
				this.cal_id)
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAccWageTaxCal.do", {
						ParamVo : ParamVo
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	function extend() {

		var paramData = {

			wage_code : liger.get("wage_code1").getValue(),//liger.get("wage_code").getValue(),

			acc_year : $("#acc_year").val()

		}

		ajaxJsonObjectByUrl("extendAccWageTaxCal.do", paramData, function(
				responseData) {
			if (responseData.state == "true") {
				query();
			}
		});

	}

	function clearContent() {

		liger.get("wage_code").setValue("");
		liger.get("wage_code").setText("");

		liger.get("emp_kind").setText("");
		liger.get("emp_kind").setValue("");

		$("#note").val("");

		$("#cal_name").val("");

		$("#emp_kind").ligerTextBox({
			disabled : false,
			cancelable : false
		});

		$("#wage_code").ligerTextBox({
			disabled : false,
			cancelable : false
		});

		$("#emp_kind").removeAttr("disabled", 'disabled');

		$("#wage_code").removeAttr("disabled", 'disabled');

	}

	function loadDict() {
		//字典下拉框

		autocomplete("#wage_code1", "../queryAccWage.do?isCheck=false", "id",
				"text", true, true);

		autocomplete("#emp_kind1",
				"../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0", "id",
				"text", true, true);

		autocomplete("#wage_code", "../queryAccWage.do?isCheck=false", "id",
				"text", true, true, '', true);

		//autocomplete("#emp_kind","../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true,'',true);

		$("#emp_kind").ligerComboBox({

			url : '../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0',

			emptyText : '全部',

			valueField : 'id',

			textField : 'text',

			selectBoxWidth : 160,

			autocomplete : true,

			width : 160

		});

		var year_Month = '${wage_year_month }';

		$("#acc_year").val(year_Month.substring(0, 4));

	}

	function showWindow() {

		var cal_name = $("#cal_name").val();

		$.ligerDialog.open({
			url : 'accWageTaxCalSetPage.do?isCheck=false&cal_name='
					+ escape(encodeURIComponent(cal_name)) + "&wage_code="
					+ liger.get("wage_code").getValue()+"&acc_year="+ $("#acc_year").val(),
			data : {},
			height : 520,
			width : 1150,
			title : '修改',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					
					var cal_name = dialog.frame.saveAccWageTaxCal();

					$("#cal_name").val(cal_name.split("#")[0]);
					$("#cal_eng").val(cal_name.split("#")[1]);
					dialog.close();
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
	//设置应税项公式
	function set_wage_cal(obj) {

		var res = obj.split("|");

		liger.get("emp_kind").setValue(res[0]);

		liger.get("emp_kind").setText(res[3]);

		liger.get("wage_code").setText(res[2]);

		liger.get("wage_code").setValue(res[1]);

		$("#note").val(res[5] == "null" ? " " : res[5]);

		$("#emp_kind").ligerTextBox({
			disabled : true,
			cancelable : false
		});

		$("#wage_code").ligerTextBox({
			disabled : true,
			cancelable : false
		});

		$("#emp_kind").attr("disabled", 'disabled');

		$("#wage_code").attr("disabled", 'disabled');

		var formData = {

			cal_id : res[4]
		};

		ajaxJsonObjectByUrl("queryAccWageTaxCalById.do?isCheck=false",
				formData, function(responseData) {

					$("#cal_name").val(responseData.rest);

				});

	}

	function saveData() {

		var kind_code = liger.get("emp_kind").getValue();

		var wage_code = liger.get("wage_code").getValue();

		var kind_name = $("#emp_kind").val();

		if (kind_name == "全部") {

			kind_code = "0";

		}

		if (kind_code == "" || wage_code == ""
				|| $.trim($("#cal_name").val()) == "") {

			$.ligerDialog.error('请完善信息！');

			return;

		}

		var formPara = {

			wage_code : wage_code,

			kind_code : kind_code,

			cal_name : $.trim($("#cal_name").val()),

			note : $.trim($("#note").val()),

			cal_eng : $.trim($("#cal_eng").val())

		};

		ajaxJsonObjectByUrl("addAccWageTaxCal.do", formPara, function(
				responseData) {

			if (responseData.state == "true") {

				query();
			}
		});

	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="layout1">
		<div position="left" title="  ">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 10px;">工资套名称：</td>
					<td align="left" class="l-table-edit-td"><input
						name="wage_code1" type="text" id="wage_code1" ltype="text"
						validate="{required:true,maxlength:18}" /></td>

					<td align="right" class="l-table-edit-td"
						style="padding-left: 10px;">会计年度：</td>
					<td align="left" class="l-table-edit-td"><input class="Wdate"
						name="acc_year" type="text" id="acc_year"
						onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
					<td align="left"></td>
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 10px;">职工分类：</td>
					<td align="left" class="l-table-edit-td"><input
						name="emp_kind1" type="text" id="emp_kind1" ltype="text"
						validate="{required:true,maxlength:18}" /></td>
					<td align="left"></td>
				</tr>
			</table>
			<div id="maingrid"></div>
		</div>
		<div position="center" title="  ">
			<table cellpadding="0" cellspacing="0" class="l-table-edit">
				<tr>
					<td><input name="cal_eng" type="hidden" id="cal_eng"
						ltype="text" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;"><b><font color="red">*</font></b>工资套名称：</td>
					<td align="left" class="l-table-edit-td" colspan="2"><input
						name="wage_code" type="text" id="wage_code" ltype="text" /></td>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;"><b><font color="red">*</font></b>职
						工 分 类：</td>
					<td align="left" class="l-table-edit-td"><input
						name="emp_kind" type="text" id="emp_kind" ltype="text" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;">公 式 说 明：</td>
					<td align="left" class="l-table-edit-td" colspan="6"><input
						name="note" type="text" id="note" ltype="text"
						validate="{required:true,maxlength:50}" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"
						style="padding-left: 20px; padding-bottom: 400px">计 算 公 式：</td>
					<td align="left" class="l-table-edit-td" colspan="6"><textarea
							class="liger-textarea"
							style="height: 420px; width: 100%; resize: none;"
							validate="{required:true}" name="cal_name" id="cal_name"
							disabled="disabled"></textarea></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="left" style="padding: 10px 0px 10px 140px;" colspan="2"
						class="l-table-edit-td"><input class="liger-button"
						style="width: 120px" type="button" value="重置"
						onClick="clearContent();"></td>
					<td colspan="2" style="padding: 10px 0px 10px 80px;"><input
						class="liger-button" style="width: 120px" type="button"
						value="公式设置" onClick="showWindow();"></td>
					<td colspan="2" style="padding: 10px 0px 10px 0px;"><input
						class="liger-button" style="width: 120px" type="button" value="保存"
						onClick="saveData();"></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
