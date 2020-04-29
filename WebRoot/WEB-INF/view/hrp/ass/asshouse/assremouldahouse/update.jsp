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
		loadHotkeys();
		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('delete');
		}
		
		$("#apply_no").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		$("#back_money").ligerTextBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});

		$("#apply_no").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		$("#back_money").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		
		
		$("#ven_id").change(function(){
		/* 	
			var ven_id = liger.get("ven_id").getValue().split("@")[0];
			var ven_no = liger.get("ven_id").getValue().split("@")[1];

			if (ven_no == null || ven_id == null || ven_id == ""
					|| ven_no == "") {
				ven_no = "";
				ven_id = "";
			}
			 */
			editor.grid.url = '../../queryAssCardTable.do?isCheck=false&ass_nature=01&use_state=1,2,3,4,5&in_date='
				+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no;
		});
		
		query();
	});
	
	function cardSelect(){
		var data = gridManager.getData();
		var ass_card_nos = [];
		$.each(data, function(i, v) {
			if (!isnull(v.ass_card_no)) {
				ass_card_nos.push("'"+v.ass_card_no+"'");
			}
		});
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}

		editor.grid.url = '../../queryAssCardTable.do?isCheck=false&ass_nature=01&use_state=1,2,3,4,5&in_date='
				+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no+'&ass_card_not_exists='+ass_card_nos.toString();

}
	function changeCreateDate() {
		
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}

		editor.grid.url = '../../queryAssCardTable.do?isCheck=false&ass_nature=01&use_state=1,2,3,4,5&in_date='
				+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no;
	}

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		/* if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		} */
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
			apply_no : $("#apply_no").val() == "" ? '0' : $("#apply_no").val(),
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			ven_no : liger.get("ven_id").getValue().split("@")[1],
			note : $("#note").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssRemouldAHouse.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#apply_no").val(responseData.apply_no);
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
			name : 'apply_no',
			value : $("#apply_no").val() == "" ? "0" : $("#apply_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	
	

	function loadHead() {
		
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
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
							},
							{
								display : '资产编码',
								name : 'ass_code',
								align : 'left'
							},
							{
								display : '资产名称',
								name : 'ass_name',
								align : 'left'
							},
							{
								display : '资产规格',
								name : 'ass_spec',
								align : 'left'
							},
							{
								display : '资产型号',
								name : 'ass_model',
								align : 'left'
							},
							{
								display : '资产品牌',
								name : 'ass_brand',
								align : 'left'
							},
							{
								display : '计量单位',
								name : 'ass_unit_name',
								align : 'left'
							},
							{
								display : '生产厂商',
								name : 'fac_name',
								align : 'left'
							},
							{
								display : '供应商',
								name : 'ven_name',
								align : 'left'
							},
							{
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
					url : '../../queryAssCardTable.do?isCheck=false&ass_nature=01&use_state=1,2,3,4,5&in_date='
						+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no,
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
								{ display: '卡片编码', name: 'ass_card_no', align: 'left',
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
						 		},
							 		{
										display : '资产编码',
										name : 'ass_code',
										align : 'left',
										width : '150'
									}, {
										display : '资产名称',
										name : 'ass_name',
										align : 'left',
										width : '150'
									},
									 {
										display : '资产原值',
										name : 'price',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.price == null ? 0
																: rowdata.price,
														'${ass_05006}',
														1);
										}
									}, {
										display : '本期折旧',
										name : 'now_depre',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.now_depre == null ? 0
																: rowdata.now_depre,
														'${ass_05005}',
														1);
										}
									}, {
										display : '本期分摊',
										name : 'now_manage_depre',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.now_manage_depre == null ? 0
																: rowdata.now_manage_depre,
														'${ass_05005}',
														1);
										}
									}, {
										display : '累计折旧',
										name : 'add_depre',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.add_depre == null ? 0
																: rowdata.add_depre,
														'${ass_05005}',
														1);
										}
									}, 
									{
										display : '累计分摊',
										name : 'manage_depre_money',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.manage_depre_money == null ? 0
																: rowdata.manage_depre_money,
														'${ass_05005}',
														1);
										}
									}, {
										display : '资产净值',
										name : 'cur_money',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.cur_money == null ? 0
																: rowdata.cur_money,
														'${ass_05006}',
														1);
										}
									}, {
										display : '预留残值',
										name : 'fore_money',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,
												value) {
											 return formatNumber(
														rowdata.fore_money == null ? 0
																: rowdata.fore_money,
														'${ass_05006}',
														1);
										}
									},
									{
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
							url:'queryAssRemouldADict.do?isCheck=false',
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
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
				+ "ass_nature=01";
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
	function initCard(){
		
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
									.push(this.group_id + "@" + this.hos_id
											+ "@" + this.copy_code + "@"
											+ this.apply_no + "@"
											+ this.ass_card_no);
						}
						i++;
					});
			if (ParamVo == "") {
				return;
			}
			$.ligerDialog
					.confirm(
							'确定删除?',
							function(yes) {
								if (yes) {
									ajaxJsonObjectByUrl(
											"deleteAssRemouldDetailHouse.do?isCheck=false",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
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
		autocomplete("#create_emp","../../../../hrp/sys/queryUserDict.do?isCheck=false", "id","text", true, true, null, null,${create_emp});
		
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false", "id","text", true, true, null,false, '', "400");
		liger.get("ven_id").setValue("${ven_id}@${ven_no}");
		liger.get("ven_id").setText("${sup_code} ${sup_name}");
		autocomplete("#bus_type_code", "../../queryBusType.do?isCheck=false", "id","text", true, true, null, true, ${bus_type_code});
		autodate("#create_date");
		$("#create_emp").ligerComboBox({disabled : true,cancelable : false});
		
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit"
		width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>改造申请单号：</td>
			<td align="left" class="l-table-edit-td"><input
				name="apply_no" type="text" id="apply_no" disabled="disabled" value="${apply_no}"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单人：</td>
			<td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',onpicked:changeCreateDate})"  value="${create_date}" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">业务类型：</td>
			<td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" /></td>
			<td align="left"></td>
			
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note"> ${note}</textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
