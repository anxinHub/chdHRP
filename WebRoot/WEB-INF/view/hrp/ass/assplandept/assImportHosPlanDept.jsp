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
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		$("#plan_id").ligerTextBox({
			width : 160
		});
		$("#plan_no").ligerTextBox({
			width : 160
		});
		$("#plan_year").ligerTextBox({
			width : 160
		});
		$("#plan_month1").ligerTextBox({
			width : 100
		});
		$("#plan_month2").ligerTextBox({
			width : 100
		});
		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#dept_no").ligerTextBox({
			width : 160
		});
		$("#check_emp").ligerTextBox({
			width : 160
		});
		$("#check_date1").ligerTextBox({
			width : 100
		});
		$("#check_date2").ligerTextBox({
			width : 100
		});
		$("#audit_emp").ligerTextBox({
			width : 160
		});
		$("#audit_date1").ligerTextBox({
			width : 100
		});
		$("#audit_date2").ligerTextBox({
			width : 100
		});
		$("#is_add").ligerTextBox({
			width : 160
		});
		$("#state").ligerTextBox({
			width : 160
		});
		$("#buy_code").ligerTextBox({
			width : 160
		});

	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'plan_id',
			value : $("#plan_id").val()
		});
		grid.options.parms.push({
			name : 'plan_no',
			value : $("#plan_no").val()
		});
		grid.options.parms.push({
			name : 'plan_year',
			value : $("#plan_year").val()
		});
		grid.options.parms.push({
			name : 'plan_month1',
			value : $("#plan_month1").val()
		});
		grid.options.parms.push({
			name : 'plan_month2',
			value : $("#plan_month2").val()
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_id").getValue().split("@")[0]
		});
		//     	  grid.options.parms.push({name:'dept_no',value:$("#dept_no").val()}); 
		grid.options.parms.push({
			name : 'check_emp',
			value : liger.get("check_emp").getValue()
		});
		grid.options.parms.push({
			name : 'check_date1',
			value : $("#check_date1").val()
		});
		grid.options.parms.push({
			name : 'check_date2',
			value : $("#check_date2").val()
		});
		grid.options.parms.push({
			name : 'audit_emp',
			value : liger.get("audit_emp").getValue()
		});
		grid.options.parms.push({
			name : 'audit_date1',
			value : $("#audit_date1").val()
		});
		grid.options.parms.push({
			name : 'audit_date2',
			value : $("#audit_date2").val()
		});
		grid.options.parms.push({
			name : 'is_add',
			value : $("#is_add").val()
		});
		grid.options.parms.push({
			name : 'state',
			value : $("#state").val()
		});
		grid.options.parms.push({
			name : 'buy_code',
			value : $("#buy_code").val()
		});
		grid.options.parms.push({
			name : 'is_exists',
			value : 'true'
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '计划号',
				name : 'plan_no',
				align : 'left',
				width : '110',
				frozen : true
			}, {
				display : '购置年度',
				name : 'plan_year',
				align : 'left',
				width : '60',
				frozen : true
			}, {
				display : '购置月份',
				name : 'plan_month',
				align : 'left',
				width : '60',
				frozen : true
			}, {
				display : '编制科室名称',
				name : 'dept_no',
				align : 'left',
				width : '90',
				render : function(rowdata, rowindex, value) {
					return rowdata.dept_name;
				}
			}, {
				display : '计划金额',
				name : 'plan_money',
				align : 'right',
				width : '120',
				render : function(rowdata, rowindex, value) {
					return formatNumber(
							rowdata.plan_money == null ? 0
									: rowdata.plan_money,
							'${ass_05005}',
							1);
				},
				totalSummary : {
					type : 'Sum'
				}
			}, {
				display : '制单人',
				name : 'create_emp',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					return rowdata.create_emp_name;
				}
			}, {
				display : '制单日期',
				name : 'create_date',
				align : 'left',
				width : '90'
			}, {
				display : '审核人',
				name : 'check_emp',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					return rowdata.check_emp_name;
				}
			}, {
				display : '审核日期',
				name : 'check_date',
				align : 'left',
				width : '90'
			}, {
				display : '审批人',
				name : 'audit_emp',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					return rowdata.audit_emp_name;
				}
			}, {
				display : '审批日期',
				name : 'audit_date',
				align : 'left',
				width : '90'
			}, {
				display : '审批意见',
				name : 'audit_rem',
				align : 'left',
				width : '150'
			}, {
				display : '是否追加计划',
				name : 'is_add',
				align : 'left',
				width : '90',
				render : function(rowdata, rowindex, value) {
					if (rowdata.is_add == 0) {
						return "否";
					}
					return "是";
				}
			}, {
				display : '状态',
				name : 'state',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					if (rowdata.state == 0) {
						return "新建";
					}
					return "审核";
				}
			}, {
				display : '采购方式',
				name : 'buy_code',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					if (rowdata.buy_code == 0) {
						return "自主采购";
					}
					return "集中采购";
				}
			}, {
				display : '摘要',
				name : 'summary',
				align : 'left',
				width : '150'
			}, {
				display : '备注',
				name : 'note',
				align : 'left',
				width : '150'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAssPlanDept.do?isCheck=false',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '保存',
					id : 'save',
					click : save,
					icon : 'add'
				}, {
					line : true
				}, {
					text : '关闭',
					id : 'close',
					click : this_close,
					icon : 'candle'
				} ]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function save() {
		if (liger.get("dept_no").getValue() == "") {
			$.ligerDialog.error('编制科室不能为空');
			return;
		}
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if ($("#summary").val() == "") {
			$.ligerDialog.error('摘要不能为空');
			return;
		}
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(function() {
				ParamVo.push(this.plan_id);
			});
			$.ligerDialog.confirm('确定要导入合同单?', function(yes) {
				if (yes) {
					$.post("importHosPlanDept.do?isCheck=false",
							{
								plan_ids : ParamVo.toString(),
								dept_id : liger.get("dept_no").getValue()
										.split("@")[0],
								dept_no : liger.get("dept_no").getValue()
										.split("@")[1],
								create_date : $("#create_date").val(),
								summary : $("#summary").val()
							}, function(responseData) {
								if (responseData.state == "true") {
									parentFrameUse().query();
									parentFrameUse().openUpdate(responseData.update_para);
				        			this_close();
								}
							},"json");
				}
			});
		}
	}

	function loadDict() {
		//字典下拉框
		//默认年
		autodate("#plan_year", "YYYY");
		//默认月
		autodate("#plan_month1", "mm");
		//默认月
		autodate("#plan_month2", "mm");
		//科室编码
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true);
		//     	//制单人
		//     	autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
		//审核人
		autocomplete("#check_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
		//审批人
		autocomplete("#audit_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
		//是否追加计划
		$("#is_add").ligerComboBox({
			width : 160
		});
		//状态
		$("#state").ligerComboBox({
			width : 160
		});
		//采购方式
		$("#buy_code").ligerComboBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});

		autocomplete("#dept_no", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true);
	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计划号：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_no"
				type="text" id="plan_no" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">购置年度：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_year"
				type="text" id="plan_year" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">购置月份：</td>
			<td align="left" class="l-table-edit-td"><input
				name="plan_month1" type="text" id="plan_month1" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'plan_month2\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="plan_month2" type="text" id="plan_month2" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'plan_month1\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">编制科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核人：</td>
			<td align="left" class="l-table-edit-td"><input name="check_emp"
				type="text" id="check_emp" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="check_date1" type="text" id="check_date1" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'check_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="check_date2" type="text" id="check_date2" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'check_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否追加计划：</td>
			<td align="left" class="l-table-edit-td"><select id="is_add"
				name="is_add">
					<option value=""></option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审批人：</td>
			<td align="left" class="l-table-edit-td"><input name="audit_emp"
				type="text" id="audit_emp" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审批日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="audit_date1" type="text" id="audit_date1" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'audit_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="audit_date2" type="text" id="audit_date2" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td"><select id="state"
				name="state">
					<option value="1">审核</option>
			</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">采购方式：</td>
			<td align="left" class="l-table-edit-td"><select id="buy_code"
				name="buy_code">
					<option value="1">集中采购</option>
			</select></td>
			<td align="left"></td>
		</tr>

	</table>
	<hr size="1" width="1400" color="#A3COE8" align="left" style="" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>编制科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_no"
				type="text" id="dept_no" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="100" name="summary" id="summary"></textarea></td>
			<td align="left"></td>
		</tr>

	</table>
	<div id="maingrid"></div>
</body>
</html>
