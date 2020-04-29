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

		$("#allot_out_no").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#in_store_id").ligerComboBox({
			width : 160
		});
		$("#out_store_id").ligerComboBox({
			width : 160
		});
		$("#in_hos_id").ligerComboBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#price").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#add_depre").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#cur_money").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#fore_money").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		
		$("#out_store_id").change(function(){
			var store_id = liger.get("out_store_id").getValue().split("@")[0];
			var store_no = liger.get("out_store_id").getValue().split("@")[1];

			if (store_no == null || store_id == null || store_id == ""
					|| store_no == "") {
				store_no = "";
				store_id = "";
			}
			
			editor.grid.url = '../../queryAssCardTable.do?isCheck=false&ass_nature=05&&use_state=1,2,3,4,5&in_date='
				+ $("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no;
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
		var store_id = liger.get("out_store_id").getValue().split("@")[0];
		var store_no = liger.get("out_store_id").getValue().split("@")[1];

		if (store_no == null || store_id == null || store_id == ""
				|| store_no == "") {
			store_no = "";
			store_id = "";
		}
		
		editor.grid.url = '../../queryAssCardTable.do?isCheck=false&ass_nature=05&&use_state=1,2,3,4,5&in_date='
			+ $("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no+'&ass_card_not_exists='+ass_card_nos.toString();

}
	
	function changeCreateDate() {
		var store_id = liger.get("out_store_id").getValue().split("@")[0];
		var store_no = liger.get("out_store_id").getValue().split("@")[1];

		if (store_no == null || store_id == null || store_id == ""
				|| store_no == "") {
			store_no = "";
			store_id = "";
		}
		
		editor.grid.url = '../../queryAssCardTable.do?isCheck=false&ass_nature=05&&use_state=1,2,3,4,5&in_date='
			+ $("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no;
	}

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("in_store_id").getValue() == "") {
			$.ligerDialog.error('调入仓库不能为空');
			return;
		}
		if (liger.get("in_group_id").getValue() == "") {
			$.ligerDialog.error('调入集团不能为空');
			return;
		}
		if (liger.get("in_hos_id").getValue() == "") {
			$.ligerDialog.error('调入医院不能为空');
			return;
		}
		if (liger.get("out_store_id").getValue() == "") {
			$.ligerDialog.error('调出仓库不能为空');
			return;
		}
		if (liger.get("out_store_id").getValue() == liger.get("in_store_id")
				.getValue()) {
			$.ligerDialog.error('调出仓库和调入仓库不能相同');
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
			allot_out_no : $("#allot_out_no").val() == "" ? '0' : $("#allot_out_no").val(),
			in_store_id : liger.get("in_store_id").getValue().split("@")[0],
			in_store_no : liger.get("in_store_id").getValue().split("@")[1],
			in_group_id : liger.get("in_group_id").getValue(),
			in_hos_id : liger.get("in_hos_id").getValue(),
			out_store_id : liger.get("out_store_id").getValue().split("@")[0],
			out_store_no : liger.get("out_store_id").getValue().split("@")[1],
			store_code : liger.get("out_store_id").getText().split(" ")[0],
			note : $("#note").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssAllotOutInassets.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#allot_out_no").val(responseData.allot_out_no);
					$("#price").val(responseData.price);
					$("#add_depre").val(responseData.add_depre);
					$("#cur_money").val(responseData.cur_money);
					$("#fore_money").val(responseData.fore_money);
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
			name : 'allot_out_no',
			value : $("#allot_out_no").val() == "" ? "0" : $("#allot_out_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	

	function loadHead() {
		var store_id = liger.get("out_store_id").getValue().split("@")[0];
		var store_no = liger.get("out_store_id").getValue().split("@")[1];

		if (store_no == null || store_id == null || store_id == ""
				|| store_no == "") {
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
				columns : [ {
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
				}, {
					display : '资产品牌',
					name : 'ass_brand',
					align : 'left'
				}, {
					display : '计量单位',
					name : 'ass_unit_name',
					align : 'left'
				}, {
					display : '生产厂商',
					name : 'fac_name',
					align : 'left'
				}, {
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
				url : '../../queryAssCardTable.do?isCheck=false&ass_nature=05&use_state=1,2,3,4,5&in_date='
					+ $("#create_date").val()+'&store_id='+store_id+'&store_no='+store_no,
				pageSize : 30
			},
			alwayShowInDown : false,
			keySupport : true,
			autocomplete : true,
			onSuccess : function() {
				this.parent("tr").next(".l-grid-row").find("td:first").focus();
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
										if (rowdata.ass_card_no == null
												|| rowdata.ass_card_no == "") {
											return "";
										}
										return "<a href=javascript:openCardUpdate('"
												+ rowdata.group_id
												+ "|"
												+ rowdata.hos_id
												+ "|"
												+ rowdata.copy_code
												+ "|"
												+ rowdata.ass_card_no
												+ "')>"
												+ rowdata.ass_card_no
												+ "</a>";
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
									display : '本期折旧',
									name : 'now_depre',
									align : 'left',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.now_depre, '${ass_05005}', 1);
									}
								}, {
									display : '本期分摊',
									name : 'now_manage_depre',
									align : 'left',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.now_manage_depre, '${ass_05005}', 1);
									}
								}, {
									display : '累计折旧',
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
							url:'queryAssAllotOutDetailInassets.do',
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
									text : '出库确认（<u>B</u>）',
									id : 'card',
									click : initCard,
									icon : 'right'
							     }, {
									line : true
								  },{
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								}]
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		is_addRow();
		
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
				$("#price").val(data.price);
				$("#add_depre").val(data.add_depre);
				$("#cur_money").val(data.cur_money);
				$("#fore_money").val(data.fore_money);
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
		if ($("#allot_out_no").val() == "") {
			$.ligerDialog.error('没有保存的单据不能确认');
			return;
		}
		var ParamVo = [];
		ParamVo.push('${group_id}' + "@" + '${hos_id}' + "@"
				+ '${copy_code}' + "@" + $("#allot_out_no").val());
		
		$.ligerDialog.confirm('确认出库?', function(yes) {
			if (yes) {
				ajaxJsonObjectByUrl("updateConfirmAllotOutInassets.do", {
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
							ParamVo
									.push(this.group_id + "@" + this.hos_id
											+ "@" + this.copy_code + "@"
											+ this.allot_out_no + "@"+this.ass_card_no);
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
											"deleteAssAllotOutDetailInassets.do",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													$("#price").val(responseData.price);
													$("#add_depre").val(responseData.add_depre);
													$("#cur_money").val(responseData.cur_money);
													$("#fore_money").val(responseData.fore_money);
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
		autocomplete("#out_store_id", "../../queryHosStoreDict.do?naturs_code=05&isCheck=false","id", "text",true,true,null,false,null,"300");
    	
		autocomplete("#in_group_id", "../../queryGroupDict.do?isCheck=false","id", "text",true,true,null,false,null,"160");
		
		$("#in_group_id").change(function(){
			autocomplete("#in_hos_id", "../../queryHosInfoDict.do?isCheck=false","id", "text",true,true,{group_id:liger.get("in_group_id").getValue()},false,null,"160");
			$("#in_hos_id").change(function(){
				autocomplete("#in_store_id", "../../queryHosStoreDict.do?naturs_code=05&isCheck=false","id", "text",true,true,{group_id:liger.get("in_group_id").getValue(),hos_id:liger.get("in_hos_id").getValue()},false,null,"160");
			});
		});
		autodate("#create_date");
		
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调剂单号：</td>
			<td align="left" class="l-table-edit-td"><input name="allot_out_no"
				type="text" id="allot_out_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',onpicked:changeCreateDate})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调出仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="out_store_id"
				type="text" id="out_store_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调入集团：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_group_id"
				type="text" id="in_group_id" 
				 /></td>
			<td align="left"></td>	 	
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调入单位：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_hos_id"
				type="text" id="in_hos_id" 
				 /></td>
			<td align="left"></td>	 
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>调入仓库：</td>
			<td align="left"  class="l-table-edit-td"><input name="in_store_id"
				type="text" id="in_store_id" 
				 /></td>
			<td align="left"></td>	 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产原值：</td>
			<td align="left" class="l-table-edit-td"><input name="price"
				type="text" id="price" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">累计折旧：</td>
			<td align="left" class="l-table-edit-td"><input name="add_depre"
				type="text" id="add_depre" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资产净值：</td>
			<td align="left" class="l-table-edit-td"><input name="cur_money"
				type="text" id="cur_money" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">预留残值：</td>
			<td align="left" class="l-table-edit-td"><input name="fore_money"
				type="text" id="fore_money" /></td>
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
