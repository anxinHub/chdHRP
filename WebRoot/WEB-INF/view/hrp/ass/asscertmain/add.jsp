<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var selectData = "";
	var clicked = 0;
	var editor;
	$(function() {
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();

	});
	//查询

	function loadHead() {
		
		editor = {
				type : 'select',
				valueField : 'ass_id_no',
				textField : 'ass_name',
				selectBoxWidth : 800,
				selectBoxHeight : 240,
				grid : {
					columns : [ {
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
					url : '../queryAssNoDictTable.do?isCheck=false',
					pageSize : 30
				},
				keySupport : true,
				alwayShowInDown : true,
				keySupport : true,
				autocomplete : true,
				onSuccess : function() {
					this.parent("tr").next(
							".l-grid-row").find(
							"td:first").focus();
				}
			};
		
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资产编码',
										name : 'ass_code',
										align : 'left'
									},
									{
										display : '资产名称',
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										editor : editor

									},
									{
										display : '型号',
										name : 'ass_model',
										align : 'left'
									},
									{
										display : '规格',
										name : 'ass_spec',
										align : 'left'
									},
									{
										display : '品牌',
										name : 'ass_brand',
										align : 'left'
									}],
							dataAction : 'server',
							dataType : 'server',
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
							checkbox : true,
							rownumbers : true,
							delayLoad : true,
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
									click : itemclick,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								}
								]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
	}
	function this_close() {
		frameElement.dialog.close();
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
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('contract_id', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec,
					ass_brand : data.ass_brand,
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
		return true;
	}
	//保存主表
	function save() {
		gridManager.endEdit();
		if ($("#cert_code").val() == "") {
			$.ligerDialog.error('证件编号不能为空');
			return;
		}
		if ($("#cert_inv_name").val() == "") {
			$.ligerDialog.error('证件名称不能为空');
			return;
		}
		if (liger.get("fac_id").getValue() == "") {
			$.ligerDialog.error('生产厂商不能为空');
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

				cert_code : $("#cert_code").val(),

				cert_inv_name : $("#cert_inv_name").val(),

				cert_date : $("#cert_date").val(),

				issuing_authority : $("#issuing_authority").val(),

				start_date : $("#start_date").val(),
				
				end_date : $("#end_date").val(),
				
				link_person : $("#link_person").val(),
				
				link_tel : $("#link_tel").val(),
				
				link_mobile : $("#link_mobile").val(),
				
				cert_memo : $("#cert_memo").val(),
				
				file_address : $("#file_address").val(),

				fac_id : liger.get("fac_id").getValue().split("@")[0],

				cert_state : liger.get("cert_state").getValue(),
				
				state : '0',

			    ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("addAssCert.do", formPara, function(responseData) {
				if (responseData.state == "true") {
					parentFrameUse().query();
					$.ligerDialog.confirm('添加成功 。是否继续添加单据?', function(yes) {
						if (yes) {
							$("#cert_code").val("");
							$("#cert_inv_name").val("");
							$("#cert_date").val("");
							$("#issuing_authority").val("");
							$("#start_date").val("");
							$("#end_date").val("");
							$("#link_person").val("");
							$("#link_tel").val("");
							$("#link_mobile").val("");
							$("#cert_memo").val("");
							$("#file_address").val("");
							liger.get("cert_state").setValue("");
							liger.get("cert_state").setText("");
							liger.get("fac_id").setValue("");
							liger.get("fac_id").setText("");
							grid.loadData();
							is_addRow();
						} else {
							this_close();
						}
					});
				}
			});
		}
	}
	function validateGrid() {
		var data = gridManager.getData();
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		$.each(data, function(i, v) {
			if (!isnull(v.ass_code)) {
				var key = v.ass_code;
				var value = "第" + (i + 1) + "行";
				if (msg != "") {
					msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
				}
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
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
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
				gridManager.deleteSelectedRow();
				return;
			}
		}
	}

	function loadDict() {
		//字典下拉框
		autocomplete("#fac_id", "../queryHosFacDict.do?isCheck=false&is_read=2","id", "text",true,true,null,false,null,"160");
		$("#cert_code").ligerTextBox({
			width : 160
		});
		$("#cert_inv_name").ligerTextBox({
			width : 160
		});
		$("#cert_date").ligerTextBox({
			width : 160
		});
		$("#issuing_authority").ligerTextBox({
			width : 160
		});
		$("#start_date").ligerTextBox({
			width : 160
		});
		$("#end_date").ligerTextBox({
			width : 160
		});
		$("#link_person").ligerTextBox({
			width : 160
		});
		$("#link_tel").ligerTextBox({
			width : 160
		});
		$("#link_mobile").ligerTextBox({
			width : 160
		});
		$("#file_address").ligerTextBox({
			width : 160
		});
		$("#cert_memo").ligerTextBox({
			width : 710
		});
		
		$('#cert_state').ligerComboBox({
			data:[{id:0,text:'未使用'},{id:1,text:'使用中'},{id:2,text:'已作废'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		});

		liger.get("cert_state").setValue("1");
		liger.get("cert_state").setText("使用中");
	}

	//键盘事件
	function loadHotkeys() {

		hotkeys('A', save);

		hotkeys('B', itemclick, [ {
			id : 'save'
		} ]);

	}

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;" onload="is_addRow()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" id="ins_id" name="ins_id" />
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>证件编码：</td>
			<td align="left" class="l-table-edit-td"><input name="cert_code"
				type="text" id="cert_code"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>证件名称：</td>
			<td align="left" class="l-table-edit-td"><input name="cert_inv_name"
				type="text" id="cert_inv_name"   /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>生产厂商：</td>
			<td align="left" class="l-table-edit-td"><input
				name="fac_id" type="text" id="fac_id"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">发证日期：</td>
			<td align="left" class="l-table-edit-td"><input name="cert_date"
				type="text" id="cert_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>

		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">发证机关：</td>
			<td align="left" class="l-table-edit-td"><input name="issuing_authority"
				type="text" id="issuing_authority"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">起始日期：</td>
			<td align="left" class="l-table-edit-td"><input name="start_date"
				type="text" id="start_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">结束日期：</td>
			<td align="left" class="l-table-edit-td"><input name="end_date"
				type="text" id="end_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">联系人：</td>
			<td align="left" class="l-table-edit-td"><input name="link_person"
				type="text" id="link_person"  /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">联系电话：</td>
			<td align="left" class="l-table-edit-td"><input name="link_tel"
				type="text" id="link_tel"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">手机：</td>
			<td align="left" class="l-table-edit-td"><input name="link_mobile"
				type="text" id="link_mobile"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">存档位置：</td>
			<td align="left" class="l-table-edit-td"><input name="file_address"
				type="text" id="file_address"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">使用状态：</td>
			<td align="left" class="l-table-edit-td"><input name="cert_state"
				type="text" id="cert_state"  /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">证件描述：</td>
			<td align="left" class="l-table-edit-td" colspan="6"><input name="cert_memo"
				type="text" id="cert_memo"  /></td>
			<td align="left"></td>
		</tr>
	</table>

	<div id="maingrid"></div>
</body>
</html>
