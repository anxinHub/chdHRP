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
	var flag;
	var editor;
	$(function() {

		loadDict();//加载下拉框
		loadHead(null);
		loadHotkeys();
		is_addRow();
		$("#ass_back_no").ligerTextBox({
			width : 160
		});
		$("#store_id").ligerTextBox({
			width : 160
		});
		$("#bus_type_code").ligerTextBox({
			width : 160
		});
		$("#back_money").ligerTextBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});

		$("#ass_back_no").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		$("#back_money").val(0);
		$("#back_money").ligerComboBox({
			disabled : true,
			cancelable : false
		});

		$("#bus_type_code").change(function() {
			var store_id = liger.get("store_id").getValue().split("@")[0];
			var store_no = liger.get("store_id").getValue().split("@")[1];
			
			if(store_no == null || store_id == null || store_id == "" || store_no == ""){
				store_no = "";
				store_id = "";
			}
			editor.grid.url='../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&bus_type_code='
				+ liger
				.get(
						"bus_type_code")
				.getValue()+'&in_date='+$("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no+'&is_dept=0';
		});
		
		$("#store_id").change(function() {
			var store_id = liger.get("store_id").getValue().split("@")[0];
			var store_no = liger.get("store_id").getValue().split("@")[1];
			
			if(store_no == null || store_id == null || store_id == "" || store_no == ""){
				store_no = "";
				store_id = "";
			}
			editor.grid.url='../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&bus_type_code='
				+ liger
				.get(
						"bus_type_code")
				.getValue()+'&in_date='+$("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no+'&is_dept=0';
		});
		
	});

	function cardSelect(){
		var data = gridManager.getData();
		var ass_card_nos = [];
		$.each(data, function(i, v) {
			if (!isnull(v.ass_card_no)) {
				ass_card_nos.push("'"+v.ass_card_no+"'");
			}
		});
		var store_id = liger.get("store_id").getValue().split("@")[0];
		var store_no = liger.get("store_id").getValue().split("@")[1];
		
		if(store_no == null || store_id == null || store_id == "" || store_no == ""){
			store_no = "";
			store_id = "";
		}
		editor.grid.url='../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&bus_type_code='
			+ liger
			.get(
					"bus_type_code")
			.getValue()+'&in_date='+$("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no+'&ass_card_not_exists='+ass_card_nos.toString()+'&is_dept=0';


}
	
	function changeCreateDate() {
		var store_id = liger.get("store_id").getValue().split("@")[0];
		var store_no = liger.get("store_id").getValue().split("@")[1];
		
		if(store_no == null || store_id == null || store_id == "" || store_no == ""){
			store_no = "";
			store_id = "";
		}
		editor.grid.url='../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&bus_type_code='
			+ liger
			.get(
					"bus_type_code")
			.getValue()+'&in_date='+$("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no+'&is_dept=0';
	}

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("store_id").getValue() == "") {
			$.ligerDialog.error('仓库不能为空');
			return;
		}
		if (liger.get("bus_type_code").getValue() == "") {
			$.ligerDialog.error('业务类型不能为空');
			return;
		}
		var data = gridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {

			if (data[i].ass_card_no) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		var formPara = {
			ass_back_no : $("#ass_back_no").val() == "" ? '0' : $(
					"#ass_back_no").val(),
			store_id : liger.get("store_id").getValue().split("@")[0],
			store_no : liger.get("store_id").getValue().split("@")[1],
			store_code : liger.get("store_id").getText().split(" ")[0],
			bus_type_code : liger.get("bus_type_code").getValue(),
			note : $("#note").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssBackRestMainInassets.do", formPara,
					function(responseData) {
						if (responseData.state == "true") {
							$("#ass_back_no").val(responseData.ass_back_no);
							$("#back_money").val(responseData.back_money)
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
			name : 'ass_back_no',
			value : $("#ass_back_no").val() == "" ? "0" : $("#ass_back_no")
					.val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}

	function loadHead() {
		var store_id = liger.get("store_id").getValue().split("@")[0];
		var store_no = liger.get("store_id").getValue().split("@")[1];
		
		if(store_no == null || store_id == null || store_id == "" || store_no == ""){
			store_no = "";
			store_id = "";
		}
		editor = {
				type : 'select',
				valueField : 'ass_card_no',
				textField : 'ass_card_no',
				selectBoxWidth : 500,
				selectBoxHeight : 240,
				grid : {
					columns : [ 
					{
						display : '卡片编码',
						name : 'ass_card_no',
						align : 'left'
					}, {
						display : '资产编码',
						name : 'ass_code',
						align : 'left'
					}, {
						display : '资产名称',
						name : 'ass_name',
						align : 'left'
					}, {
						display : '资产规格',
						name : 'ass_spec',
						align : 'left'
					}, {
						display : '资产型号',
						name : 'ass_model',
						align : 'left'
					} , {
						display : '资产品牌',
						name : 'ass_brand',
						align : 'left'
					}, {
						display : '计量单位',
						name : 'ass_unit_name',
						align : 'left'
					} , {
						display : '生产厂商',
						name : 'fac_name',
						align : 'left'
					} , {
						display : '供应商',
						name : 'ven_name',
						align : 'left'
					}, {
						display : '资产原值',
						name : 'price',
						align : 'left',
						render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.price == null ? 0: rowdata.price,'${ass_05006}',1);
						}
						
					}, {
						display : '累计折旧',
						name : 'depre_money',
						align : 'left',
						render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.depre_money == null ? 0: rowdata.depre_money,'${ass_05005}',1);
						}
						
					}, {
						display : '累计分摊',
						name : 'manage_depre_money',
						align : 'left',
						render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.manage_depre_money == null ? 0: rowdata.manage_depre_money,'${ass_05005}',1);
						}
						
					}, {
						display : '资产净值',
						name : 'cur_money',
						align : 'left',
						render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.cur_money == null ? 0: rowdata.cur_money,'${ass_05005}',1);
						}
						
					}, {
						display : '预留残值',
						name : 'fore_money',
						align : 'left',
						render : function(rowdata, rowindex,value) {
						return formatNumber(
						rowdata.fore_money == null ? 0: rowdata.fore_money,'${ass_05005}',1);
						}
						
					}  ,
					 {
						display : '累计折旧月份',
						name : 'add_depre_month',
						align : 'left'
					} ,
					 {
						display : '累计分摊月份',
						name : 'add_manage_month',
						align : 'left'
					}],
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_detail,
					url : '../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&bus_type_code='
						+ liger
						.get(
								"bus_type_code")
						.getValue()+'&in_date='+$("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no+'&is_dept=0',
					pageSize : 30
				},
				alwayShowInDown : false,
				keySupport : true,
				autocomplete : true,
				onSuccess : function() {
					this.parent("tr").next(
							".l-grid-row").find(
							"td:first").focus();
				},
				onBeforeOpen: cardSelect
			};
		
		
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '卡片编码',
										name : 'ass_card_no',
										align : 'left',
										textField : 'ass_card_no',
										valueField : 'ass_card_no',
										editor : editor,
										width : '180',
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.ass_card_no == null || rowdata.ass_card_no == ""){
												return "";
											}
											return "<a href=javascript:openCardUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
											+ "|" + rowdata.copy_code + "|"
											+ rowdata.ass_card_no  +"')>"+rowdata.ass_card_no+"</a>";
										}
									}, {
										display : '资产编码',
										name : 'ass_code',
										align : 'left',
										width : '150'
									}, {
										display : '资产名称',
										name : 'ass_name',
										align : 'left',
										width : '150'
									}, {
										display : '资产原值',
										name : 'price',
										align : 'left',
										width : '120',
										render : function(item) {
											return formatNumber(
													item.price, '${ass_05006}', 1);
										}
									}, {
										display : '累计摊销',
										name : 'add_depre',
										align : 'left',
										width : '120',
										render : function(item) {
											return formatNumber(
													item.add_depre, '${ass_05005}', 1);
										}
									},
									{
										display : '累计分摊',
										name : 'manage_depre_money',
										align : 'left',
										width : '120',
										render : function(item) {
											return formatNumber(
													item.manage_depre_money, '${ass_05005}', 1);
										}
									}, {
										display : '资产净值',
										name : 'cur_money',
										align : 'left',
										width : '120',
										render : function(item) {
											return formatNumber(
													item.cur_money, '${ass_05006}', 1);
										}
									}, {
										display : '预留残值',
										name : 'fore_money',
										align : 'left',
										width : '120',
										render : function(item) {
											return formatNumber(
													item.fore_money, '${ass_05006}', 1);
										}
									}, {
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '180'
									} ],
							dataAction : 'server',
							dataType : 'server',
							url : 'queryAssBackRestDetailInassets.do',
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
								},{
									text : '退货确认',
									id : 'backConfirm',
									click : backConfirm,
									icon : 'right'
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
	
	function backConfirm(){
		
		if ($("#ass_back_no").val() == "") {
			$.ligerDialog.error('没有保存的单据不能确认');
			return;
		}
		var ParamVo = [];
		ParamVo.push('${group_id}' + "@" + '${hos_id}' + "@"
				+ '${copy_code}' + "@" + $("#ass_back_no").val());
		
		$.ligerDialog.confirm('确认退库?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("updateConfirmBackRestInassets.do", {
					ParamVo : ParamVo.toString()
				}, function(responseData) {
					if (responseData.state == "true") {
						query();
						parentFrameUse().query();
					}
				});
			}
		});	
		
	}
	
	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature=05";
		parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
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
		if (column_name == "ass_card_no") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					price : data.price,
					add_depre : data.depre_money,
					manage_depre_money : data.manage_depre_money,
					cur_money : data.cur_money,
					fore_money : data.fore_money,
					add_depre_month : data.add_depre_month
				});
				$("#back_money").val(data.price);
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
		grid.updateTotalSummary();
	}
	function initCard() {

	}

	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.ass_card_no)) {
				var key = v.ass_card_no;
				var value = "第" + (i + 1) + "行";
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = targetMap.get(key) + "与" + value + "重复！\n\r", value;
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
							ParamVo.push(this.group_id + "@" + this.hos_id
									+ "@" + this.copy_code + "@"
									+ this.ass_back_no + "@" + this.ass_id
									+ "@" + +this.ass_no);
						}
						i++;
					});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAssBackRestDetailInassets.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							$("#back_money").val(responseData.back_money);
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
		//字典下拉框
		autocomplete("#store_id", "../../queryHosStoreDict.do?naturs_code=05&isCheck=false",
				"id", "text", true, true, null, true, null, "300");
		autocomplete("#bus_type_code",
				"../../queryAssBusType.do?isCheck=false&", "id", "text", true,
				true, {is_menu:3}, true, null, "160");
		autodate("#create_date");

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit"
		width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>退货单号：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ass_back_no" type="text" id="ass_back_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',onpicked:changeCreateDate})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id"
				type="text" id="store_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>业务类型：</td>
			<td align="left" class="l-table-edit-td"><input
				name="bus_type_code" type="text" id="bus_type_code" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">退货金额：</td>
			<td align="left" class="l-table-edit-td"><input
				name="back_money" type="text" id="back_money" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note"></textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
