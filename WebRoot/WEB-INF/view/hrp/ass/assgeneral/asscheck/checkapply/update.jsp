<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var editor;
	$(function() {
		loadDict();//加载下拉框
		loadHead(null);
		query();
		loadHotkeys();
		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('delete');
		}
	});

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		var data = gridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {

			if (data[i].ass_code) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		var formPara = {
			check_ap_no : $("#check_ap_no").val() == "" ? '0' : $("#check_ap_no").val(),
			summary : $("#summary").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssCheckApGeneral.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#check_ap_no").val(responseData.check_ap_no);
					query();
					parentFrameUse().query();
				}
			});
		}
	}
	function this_close() {
		frameElement.dialog.close();
	}

	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'check_ap_no',
			value : $("#check_ap_no").val() == "" ? "0" : $("#check_ap_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
				{
					display : '资产编码',
					name : 'ass_code',
					align : 'left',
					width : '130'
				},
				{
					display : '资产名称',
					name : 'ass_id',
					align : 'left',
					textField : 'ass_name',
					editor : {
						type : 'select',
						valueField : 'ass_id_no',
						textField : 'ass_name',
						selectBoxWidth : 800,
						selectBoxHeight : 240,
						grid : {
							columns : [  {
								display : '资产编码',
								name : 'ass_code',
								align : 'left'
							}, {
								display : '资产名称',
								name : 'ass_name',
								align : 'left'
							}, {
								display : '资产分类',
								name : 'ass_type_name',
								align : 'left'
							}, {
								display : '规格',
								name : 'ass_spec',
								align : 'left'
							}, {
								display : '型号',
								name : 'ass_model',
								align : 'left'
							}, {
								display : '品牌',
								name : 'ass_brand',
								align : 'left'
							},{
								display : '计量单位',
								name : 'ass_unit_name',
								align : 'left'
							},{
								display : '资产ID',
								name : 'ass_id',
								hide : true
							} ],
							switchPageSizeApplyComboBox : false,
							onSelectRow : f_onSelectRow_detail,
							url : '../../../queryAssNoDictTable.do?isCheck=false&ass_naturs=03',
							pageSize : 30
						},
						keySupport : true,
						alwayShowInDown : false,
						keySupport : true,
						autocomplete : true,
						onSuccess : function() {
							this.parent("tr").next(
									".l-grid-row").find(
									"td:first").focus();
						}
					},
					render : function(rowdata, rowindex,
							value) {
						return rowdata.ass_name;
					},
					width : '130',
                    totalSummary:
                    {
                        render: function (suminf, column, cell)
                        {
                            return '<div></div>';
                        },
                        align: 'center'
                    }

				},
				{
					display : '规格',
					name : 'ass_spec',
					align : 'left',
					width : '100'
				},
				{
					display : '型号',
					name : 'ass_model',
					align : 'left',
					width : '100'
				},
				{
					display : '品牌',
					name : 'ass_brand',
					align : 'left',
					width : '100'
				},
				{
					display : '生产厂家',
					name : 'fac_id',
					textField : 'fac_name',
					editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../../queryHosFacDict.do?isCheck=false',
						keySupport : true,
						autocomplete : true,
						onSuccess : function(data) {
							if (initvalue != undefined
									&& initvalue != "") {
								this.setValue(initvalue);
								initvalue = "";
							}
						}
					},
					align : 'left',
					width : '190'
				},
				{
					display : '数量',
					name : 'acc_amount',
					editor : {
						type : 'int'
					},
					align : 'left',
					width : '100'
				},
				{
					display : '资产原值',
					name : 'price',
					editor : {
						type : 'float',
						precision : 2
					},
					align : 'right',
					render : function(item) {
						if(item.price){
						return formatNumber(
								item.price, '${ass_05006}', 1);
						}
					},
					width : '100'

				},
				{
					display : '累计折旧',
					name : 'add_depre',
					editor : {
						type : 'float',
						precision : 2
					},
					align : 'right',
					render : function(item) {
						if(item.add_depre){
						return formatNumber(
								item.add_depre, '${ass_05005}', 1);
						}
					},
					width : '100'
				},
				{
					display : '累计折旧月份',
					name : 'add_depre_month',
					editor : {
						type : 'int'
					},
					align : 'left',
					width : '100'
				},
				{
					display : '资产净值',
					name : 'cur_money',
					align : 'right',
					render : function(item) {
						if(item.cur_money){
						return formatNumber(
								item.cur_money, '${ass_05006}', 1);
						}
					},
					width : '100'
				},
				{
					display : '预留残值',
					name : 'fore_money',
					editor : {
						type : 'float',
						precision : 2
					},
					align : 'right',
					render : function(item) {
						if(item.fore_money){
						return formatNumber(
								item.fore_money, '${ass_05006}', 1);
						}
					},
					width : '100'
				}, {
					display : '盘盈原因',
					name : 'p_reason',
					editor : {
						type : 'text'
					},
					align : 'left',
					width : '150'
				} 
			],
			dataAction : 'server',
			dataType : 'server',
			url:'queryAssCheckApDetailGeneral.do',
			usePager : false,
			width : '100%',
			height : '100%',
			checkbox : true,
			enabledEdit : true,
			alternatingRow : true,
			onBeforeEdit : f_onBeforeEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitEdit,
			onAfterEdit : f_onAfterEdit,
			isScroll : true,
			rownumbers : true,
			delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '保存',
					id : 'save',
					click : save,
					icon : 'save'
				}, {
					line : true
			    }, {
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				}, {
					line : true
				}, {
					text : '关闭',
					id : 'close',
					click : this_close,
					icon : 'candle'
				}]
			},
			onCheckRow : function(checked, data, rowid, rowdata) {
				var ass_id = data.ass_id;
				if(ass_id == null || ass_id == ""){
					ass_id = "0@0"
				}
				var check_ap_no = data.check_ap_no;
				if(check_ap_no == null || check_ap_no == ""){
					check_ap_no = "0"
				}
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		is_addRow();
		
	}
	
	function printBarcode(){
		
	}
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec,
					ass_brand : data.ass_brand,
					fac_id : data.fac_id,
					fac_name : data.fac_name
				});

			}
		}
		return true;
	}
	function f_onSelectRow(data, rowindex, rowobj) {
		return true;
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		var price = e.record.price == null || e.record.price == ""?0:e.record.price;
		var add_depre = e.record.add_depre == null || e.record.add_depre == ""?0:e.record.add_depre;
		var fore_money = e.record.fore_money == null || e.record.fore_money == ""?0:e.record.fore_money;
		gridManager.updateCell('cur_money', price
				- add_depre - fore_money, e.record);
		grid.updateTotalSummary();
	}
	function initCard(){
		
	}
	
	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code + "|" +v.ass_ori_card_no;
				var value = "第" + (i + 1) + "行";

				if (isnull(v.acc_amount) ||  v.acc_amount < 0) {
					msg += "[数量]";
				}
				
				if (isnull(v.price) ||  v.price < 0) {
					msg += "[原值]";
				}
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r",
					value;
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		return true;
	}
	function remove() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var i = 0;
			$(data).each(
					function() {
						if (isnull(this.group_id)) {
							gridManager.deleteSelectedRow();
						} else {
							ParamVo
							.push(this.group_id + "@" 
									+ this.hos_id+ "@" 
									+ this.copy_code + "@"
									+ this.check_ap_no + "@"
									+ this.ass_id.split("@")[0] + "@"
									+ this.ass_no);
						}
						i++;
					});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog.confirm('确定删除?',	function(yes) {
				if (yes) {
							ajaxJsonObjectByUrl(
									"deleteAssCheckApDetailGeneral.do",
									{
										ParamVo : ParamVo.toString()
									},
									function(responseData) {
										if (responseData.state == "true") {
											query();
										}
									});
						}
			});
		}
	}


	//键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', save);
		hotkeys('D', remove);

	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function loadDict() {
		autodate("#create_date");
		$("#check_ap_no").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>盘盈申报单号：</td>
			<td align="left" class="l-table-edit-td"><input name="check_ap_no"
				type="text" id="check_ap_no" disabled="disabled" value="${check_ap_no }"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9">
				<textarea rows="3" cols="70" id="summary" name="summary">${summary}</textarea>
			</td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
