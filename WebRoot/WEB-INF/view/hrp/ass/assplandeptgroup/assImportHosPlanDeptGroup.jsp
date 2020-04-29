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
		$("#plan_years").ligerTextBox({
			width : 160
		});
		$("#plan_month1").ligerTextBox({
			width : 100
		});
		$("#plan_month2").ligerTextBox({
			width : 100
		});
		$("#hos_id").ligerTextBox({
			width : 160
		});
		$("#dept_ids").ligerTextBox({
			width : 160
		});
		$("#check_emp").ligerTextBox({
			width : 160
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
		query();
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		
		grid.options.parms.push({
			name : 'plan_no',
			value : $("#plan_no").val()
		});

		grid.options.parms.push({
			name : 'plan_year',
			value : $("#plan_year").val()
		});

		grid.options.parms.push({
			name : 'hos_id',
			value : liger.get("hos_id").getValue()
		});

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
			name : 'create_date1',
			value : $("#create_date1").val()
		});
		grid.options.parms.push({
			name : 'create_date2',
			value : $("#create_date2").val()
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
				display : '医院名称',
				name : 'hos_name',
				align : 'left',
				width : '90',
				frozen : true
			},{
				display : '计划号',
				name : 'plan_no',
				align : 'left',
				width : '110',
				frozen : true,
				render : function(rowdata, rowindex, value) {
					if(rowdata.note == "合计"){
						return '';
					}
					
					if(rowdata.is_add == 1){
						return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.plan_id  +"')>"+rowdata.plan_no+"&nbsp;&nbsp;<font color='red'><b>追</b></font></a>";
					}else{
						return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|" + rowdata.copy_code  + "|" + rowdata.plan_id  +"')>"+rowdata.plan_no+"</a>";
					}
				}
			}, {
				display : '摘要',
				name : 'note',
				align : 'left',
				width : '150',
				frozen : true
			},
			{
				display : '购置年度',
				name : 'plan_year',
				align : 'left',
				width : '60',
				frozen : true
			},
			{
				display : '编制科室名称',
				name : 'dept_no',
				align : 'left',
				width : '90',
				render : function(rowdata, rowindex, value) {
					return rowdata.dept_name;
				}
			},
			{
				display : '计划金额',
				name : 'plan_money',
				align : 'right',
				width : '120',
				render : function(rowdata, rowindex, value) {
					return formatNumber(
							rowdata.plan_money == null ? 0
									: rowdata.apply_money,
							'${ass_05005}',
							1);
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
				display : '是否追加计划',
				name : 'is_add',
				align : 'left',
				width : '90',
				render : function(rowdata, rowindex, value) {
					if (rowdata.is_add == 0) {
						return "否";
					}else if(rowdata.is_add == 1){
						return "是";
					}
				}
			}, {
				display : '状态',
				name : 'state',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					if (rowdata.state == 0) {
						return "新建";
					}else if(rowdata.state == 1){
						return "审核";
					}
				}
			}, {
				display : '采购方式',
				name : 'buy_code',
				align : 'left',
				width : '70',
				render : function(rowdata, rowindex, value) {
					if (rowdata.buy_code == 0) {
						return "自主采购";
					}else if(rowdata.buy_code == 1){
						return "集中采购";
					}
					
				}
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAssPlanDeptGroupHos.do?isCheck=false',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			checkBoxDisplay : isCheckDisplay,
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
			},
			onDblClickRow : function(rowdata, rowindex, value) {//双击行事件
				if(rowdata.note == '合计'){
					return;
				}else{
					openUpdate(rowdata.group_id + "|"
							+ rowdata.hos_id + "|"
							+ rowdata.copy_code + "|"
							+ rowdata.plan_id + "|" + rowdata.state);
				}

			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function isCheckDisplay(rowdata) {
		if (rowdata.note == "合计")
			return false;
		return true;
	}
	function save() {
		var plan_ids = [];
		var group_ids = [];
		var hos_ids = [];
		var copy_codes = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			if ($("#dept_ids").val() == "") {
				$.ligerDialog.error('申请科室不能为空');
				return;
			}
			if ($("#plan_year").val() == "") {
				$.ligerDialog.error('购置年度不能为空');
				return;
			}
			if ($("#is_add_plan").val() == "") {
				$.ligerDialog.error('是否追加计划不能为空');
				return;
			}
			if ($("#buy_code_add").val() == "") {
				$.ligerDialog.error('采购方式不能为空');
				return;
			}
			
			$(data).each(function() {
				plan_ids.push(this.plan_id);
				group_ids.push(this.group_id);
				hos_ids.push(this.hos_id);
				copy_codes.push(this.copy_code);
			});
			$.ligerDialog.confirm('确定要引入医院计划?', function(yes) {
				if (yes) {
					$.post(
							"importHosPlanGroupDept.do?isCheck=false", {
								plan_ids : plan_ids.toString(),
								group_ids : group_ids.toString(),
								hos_ids : hos_ids.toString(),
								copy_codes : copy_codes.toString(),
								dept_id : liger.get("dept_ids").getValue().split("@")[0],
								dept_no : liger.get("dept_ids").getValue().split("@")[1],
								is_add : $("#is_add_plan").val(),
								buy_code : $("#buy_code_add").val(),
								plan_year : $("#plan_year").val()
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
		
		autodate("#plan_years", "YYYY");
		//默认月
		autodate("#plan_month1", "mm");
		//默认月
		autodate("#plan_month2", "mm");
		//科室编码
		autocompleteAsync("#hos_id","../queryHosInfoDict.do?isCheck=false","id","text",true,true,null,false);
		
		ajaxJsonObjectByUrl("../queryDeptDictInitValue.do?isCheck=false",{},function(data){
			autocomplete("#dept_ids", "../queryDeptDict.do?isCheck=false", "id",
					"text", true, true, null, null,data == null || data == "" ? null : data[0].id);
		},false);
		
		//     	//制单人
		//     	autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true);
		//审核人
		autocompleteAsync("#check_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
		//审批人
		autocompleteAsync("#audit_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true);
		$("#plan_no").ligerTextBox({
			width : 160
		});

		$("#plan_year1").ligerTextBox({
			width : 94
		});

		$("#plan_year2").ligerTextBox({
			width : 94
		});

		$("#check_date1").ligerTextBox({
			width : 94
		});

		$("#check_date2").ligerTextBox({
			width : 94
		});
		
		$("#create_date1").ligerTextBox({
			width : 94
		});

		$("#create_date2").ligerTextBox({
			width : 94
		});
		
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
		$("#buy_code_add").ligerComboBox({
			width : 160
		});
		
    	$("#is_add_plan").ligerComboBox({
			width : 160
		});
	}
	function this_close(){
		frameElement.dialog.close();
	}
	
	function openUpdate(obj) {

		var vo = obj.split("|");

		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "plan_id=" + vo[3]

		parent.$.ligerDialog
				.open({
					title : '购置计划查看',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/ass/assplandeptgroup/assPlanDeptGroupViewPage.do?isCheck=false&'
							+ parm,
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				});

	}
	
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">购置年度：</td>
			<td align="left" class="l-table-edit-td" ><input
				name="plan_year1" type="text" id="plan_year1" ltype="text"
				validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_year2" type="text" id="plan_year2" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"  /></td>	
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;"
				width="60">计&nbsp;&nbsp;划&nbsp;&nbsp;号：</td>
			<td align="left" class="l-table-edit-td" ><input
				name="plan_no" type="text" id="plan_no" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">医院名称：</td>
			<td align="left" class="l-table-edit-td"><input name="hos_id" 
				type="text" id="hos_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td" width="5%"><input name="create_date1" type="text" id="create_date1" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="center" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input name="create_date2" type="text" id="create_date2" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">采购方式：</td>
			<td align="left" class="l-table-edit-td"><select id="buy_code"
				name="buy_code">
					<option value="1">集中采购</option>
			</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">审&nbsp;&nbsp;核&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td"><input name="check_emp"
				type="text" id="check_emp" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
		</tr>

		<tr>
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
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td align="left" class="l-table-edit-td"><select id="state"
				name="state">
					<option value="1">审核</option>
			</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 80px;">追加计划：</td>
			<td align="left" class="l-table-edit-td"><select id="is_add"
				name="is_add">
					<option value="">全部</option>
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
		</tr>

	</table>
	<hr size="1" width="1400" color="#A3COE8" align="left" style="" />
	<table cellpadding="0" cellspacing="0" width="100%" class="l-table-edit" bgcolor="#DDDDDD">
		<tr>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>购置年度：</td>
			<td align="left" class="l-table-edit-td"><input name="plan_year"
				type="text" id="plan_year" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><font
				color="red">*</font>编制科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_ids"
				type="text" id="dept_ids" ltype="text" 
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>是否追加计划：</td>
			<td align="left" class="l-table-edit-td"><select id="is_add_plan"
				name="is_add_plan">
					<option value="0">否</option>
					<option value="1">是</option>
			</select></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td"><b><font
					color="red">*</font></b>采购方式：</td>
			<td align="left" class="l-table-edit-td"><select id="buy_code_add"
				name="buy_code_add">
					<option value="1">集中采购</option>
					<option value="0">自主采购</option>
			</select></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
