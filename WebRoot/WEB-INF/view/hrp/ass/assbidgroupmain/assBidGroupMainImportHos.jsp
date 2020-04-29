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
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);

		loadHotkeys();
		$("#ven_id").ligerTextBox({width:160});
		$("#ven").ligerTextBox({width:160});
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'bid_id',
			value : $("#bid_id").val()
		});
		grid.options.parms.push({
			name : 'bid_no',
			value : $("#bid_no").val()
		});
		grid.options.parms.push({
			name : 'bid_date1',
			value : $("#bid_date1").val()
		});
		grid.options.parms.push({
			name : 'bid_date2',
			value : $("#bid_date2").val()
		});
		grid.options.parms.push({
			name : 'ven_id',
			value : liger.get("ven_id").getValue().split("@")[0]
		});
		grid.options.parms.push({
			name : 'state',
			value : $("#state").val()
		});
		grid.options.parms.push({
			name : 'create_emp',
			value : liger.get("create_emp").getValue()
		}); //
		grid.options.parms.push({
			name : 'create_date1',
			value : $("#create_date1").val()
		});
		grid.options.parms.push({
			name : 'create_date2',
			value : $("#create_date2").val()
		});
		grid.options.parms.push({
			name : 'audit_emp',
			value : liger.get("audit_emp").getValue()
		});// 
		grid.options.parms.push({
			name : 'audit_date1',
			value : $("#audit_date1").val()
		});
		grid.options.parms.push({
			name : 'audit_date2',
			value : $("#audit_date2").val()
		});
		grid.options.parms.push({
			name : 'is_group_exists',
			value : '0'
		});

		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '招标编码',
				name : 'bid_no',
				align : 'left',
				frozen : true
			}, {
				display : '招标日期',
				name : 'bid_date',
				align : 'left',
				frozen : true
			}, {
				display : '供应商名称',
				name : 'ven_name',
				align : 'left'
			}, {
				display : '联系人',
				name : 'link_man',
				align : 'left'
			}, {
				display : '手机号码',
				name : 'tel_mobile',
				align : 'left'
			}, {
				display : '办公电话',
				name : 'tel_office',
				align : 'left'
			}, {
				display : '状态',
				name : 'state',
				align : 'left',
				render : function(rowdata, rowindex, value) {
					if (rowdata.state == 0) {
						return "新建";
					}
					return "审核";
				}
			}, {
				display : '制单人',
				name : 'create_emp_name',
				align : 'left'
			}, {
				display : '制单日期',
				name : 'create_date',
				align : 'left'
			}, {
				display : '审核人',
				name : 'audit_emp_name',
				align : 'left'
			}, {
				display : '审核日期',
				name : 'audit_date',
				align : 'left'
			}, {
				display : '备注',
				name : 'bid_note',
				align : 'left'
			} ],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryAssBidGroupHosMain.do?isCheck=false',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			//pageSize:10,
			selectRowButtonOnly : true,//heightDiff: -10,
			delayLoad : true,//初始化明细数据
			toolbar : {
				items : [ {
					text : '查询（<u>E</u>）',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				}, {
					text : '保存（<u>S</u>）',
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
		if (liger.get("ven").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		var ParamVo = [];
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			$(data).each(function() {
				ParamVo.push(this.bid_id);
			});
			$.ligerDialog.confirm('确定要引入医院招标?', function(yes) {
				if (yes) {
					$.post("importHosBidGroup.do?isCheck=false", {
						bid_ids : ParamVo.toString(),
						ven_id : liger.get("ven").getValue().split("@")[0],
						ven_no : liger.get("ven").getValue().split("@")[1]
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

		var param = {
			query_key : ''
		};
		//字典下拉框
		//供应商
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, param, true,null,"400");

		autocomplete("#ven", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true,null,null,null,"400");
		//制单人
		autocomplete("#create_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);
		//审核人
		autocomplete("#audit_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, param, true);

		$("#bid_no").ligerTextBox({
			width : 160
		});

		$("#bid_date1").ligerTextBox({
			width : 100
		});

		$("#bid_date2").ligerTextBox({
			width : 100
		});

		$("#create_date1").ligerTextBox({
			width : 100
		});

		$("#create_date2").ligerTextBox({
			width : 100
		});

		$("#audit_date1").ligerTextBox({
			width : 100
		});

		$("#audit_date2").ligerTextBox({
			width : 100
		});

		$("#create_date").ligerTextBox({
			width : 160
		});

		//状态
		$("#state").ligerComboBox({
			width : 160
		});

	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('S', save);

	}
	function this_close(){
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
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">招标编码：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_no"
				type="text" id="bid_no" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">招标日期：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_date1"
				type="text" id="bid_date1" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'bid_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input name="bid_date2"
				type="text" id="bid_date2" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'bid_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_emp" type="text" id="create_emp" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date1" type="text" id="create_date1" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'create_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date2" type="text" id="create_date2" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">状态：</td>
			<td align="left" class="l-table-edit-td"><select id="state"
				name="state">
					<option value="1">审核</option>
			</select></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核人：</td>
			<td align="left" class="l-table-edit-td"><input name="audit_emp"
				type="text" id="audit_emp" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="audit_date1" type="text" id="audit_date1" class="Wdate"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'audit_date2\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" class="l-table-edit-td">至：</td>
			<td align="left" class="l-table-edit-td"><input
				name="audit_date2" type="text" id="audit_date2" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date1\')}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<hr size="1" width="1400" color="#A3COE8" align="left" style="" />
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>

			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>供应商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven"
				type="text" id="ven" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
