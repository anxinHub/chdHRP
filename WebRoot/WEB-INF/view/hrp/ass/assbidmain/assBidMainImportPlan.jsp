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

		$("#plan_year").ligerTextBox({
			width : 160
		});
		$("#plan_month1").ligerTextBox({
			width : 110
		});
		$("#plan_month2").ligerTextBox({
			width : 110
		});

		$("#dept_id").ligerTextBox({
			width : 160
		});
		$("#dept_no").ligerTextBox({
			width : 160
		});

		$("#create_date1").ligerTextBox({
			width : 110
		});
		$("#create_date2").ligerTextBox({
			width : 110
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});

	});
	//查询
	function query(obj) {

		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件

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
			value : liger.get("dept_id").getValue()
		});

		grid.options.parms.push({
			name : 'create_date1',
			value : $("#create_date1").val()
		});
		grid.options.parms.push({
			name : 'create_date2',
			value : $("#create_date2").val()
		});

		//加载查询条件
		grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
	}

	// 	function addNewRow() {
	// 		var manager = $("#maingrid").ligerGetGridManager();
	// 		manager.addEditRow();
	// 	}
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
			url : '../assplandept/queryAssPlanDepts.do?isCheck=false',
			width : '100%',
			height : '100%',
			alternatingRow : true,
			enabledEdit : true,
			isScroll : true,
			checkbox : true,
			rownumbers : true,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '保 存',
					id : 'save',
					click : save,
					icon : 'save'
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

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			if ($("#ven_id").val() == "") {
				$.ligerDialog.error('供应商不能为空');
				return;
			}
			var row = gridManager.getSelectedRow();
			var ParamVo = [];
			$(data).each(function() {

				ParamVo.push(this.plan_id);
			});
			$.ligerDialog.confirm('确定要引入购置计划?', function(yes) {
				if (yes) {
					$.post("addAssBidMainImport.do?isCheck=false", {
						plan_ids : ParamVo.toString(),
						ven_id : liger.get("ven_id").getValue().split("@")[0],
						ven_no : liger.get("ven_id").getValue().split("@")[1]
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

		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id",
				"text", true, true);
		//autocomplete("#apply_emp","../../../hrp/sys/queryUserDict.do?isCheck=false","id","text",true,true);
		//供应商
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true,null,null,null,"400");
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">购置年度：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_year"
				type="text" id="plan_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">购置月份：</td>
			<td align="left" class="l-table-edit-td"><input
				name="plan_month1" type="text" id="plan_month1" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'plan_month2\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="plan_month2" type="text" id="plan_month2" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'plan_month1\')}',isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
			<td align="left"></td>
		</tr>

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">申请科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date1" type="text" id="create_date1" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'create_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date2" type="text" id="create_date2" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>

	</table>
	<hr size="1" width="1400" color="#A3COE8" align="left" style="" />
	</table>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>供应商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display: none">
		<table width="100%">
			<thead>

				<tr>
					<th width="200">申请ID</th>
					<th width="200">申请单号</th>
					<th width="200">购置年度</th>
					<th width="200">购置月份</th>
					<th width="200">申请科室ID</th>
					<th width="200">申请科室NO</th>
					<th width="200">申请人</th>
					<th width="200">申请日期</th>
					<th width="200">申请金额</th>
					<th width="200">摘要</th>
					<th width="200">制单人</th>
					<th width="200">制单日期</th>
					<th width="200">审核人</th>
					<th width="200">审核日期</th>
					<th width="200">是否追加申请</th>
					<th width="200">状态</th>
					<th width="200">备注</th>

				</tr>
			</thead>
			<tbody></tbody>
		</table>
	</div>
</body>
</html>
