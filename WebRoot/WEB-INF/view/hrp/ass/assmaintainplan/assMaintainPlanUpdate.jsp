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
			var gridItem;
			var gridManagerItem = null;
			var userUpdateStr;
			var selectData = "";
			var clicked = 0;
			var editor;
			var editorItem;
			var ass_card_no = 0;
			$(function() {
				$("#layout1").ligerLayout({
					rightWidth: '600',
					//heightDiff: -8,
					//每展开左边树即刷新一次表格，以免结构混乱
					onRightToggle: function() {
						grid._onResize();
					},
					//每调整左边树宽度大小即刷新一次表格，以免结构混乱
					onEndResize: function(a, b) {
						grid._onResize();
					}
				});

				loadDict(); //加载下拉框
				loadHead(null);
				loadHeadItem(null);
				if('${state}' != 0) {
					toobarmanage = gridManager.toolbarManager;
					toobarmanageItem = gridManagerItem.toolbarManager;
					toobarmanage.setDisabled('saveDetail');
					toobarmanage.setDisabled('save');
					toobarmanage.setDisabled('delete');
					toobarmanage.setDisabled('create');
					toobarmanageItem.setDisabled('copy');
					toobarmanageItem.setDisabled('saveItem');
					toobarmanageItem.setDisabled('deleteItem');
				}
				query();

			});

			function query() {
				grid.options.parms = [];
				grid.options.newPage = 1;
				grid.options.parms.push({
					name: 'plan_id',
					value: $("#plan_id").val() == "" ? "0" : $("#plan_id").val()
				});
				//grid.options.parms.push({name : 'detail_id',value : $("#detail_id").val() == ""?"0":$("#detail_id").val()});
				grid.loadData(grid.where);
			}

			function queryItem(plan_id, detail_id) {

				gridItem.options.parms = [];
				gridItem.options.newPage = 1;
				gridItem.options.parms.push({
					name: 'plan_id',
					value: plan_id == "" || plan_id == null ||
						plan_id == "undefined" ? "0" : plan_id
				});

				gridItem.options.parms.push({
					name: 'detail_id',
					value: detail_id == "" || detail_id == null ||
						detail_id == "undefined" ? "0" : detail_id
				});
				gridItem.loadData(gridItem.where);
			}

			function loadHead() {

				editor = {
					type: 'select',
					valueField: 'ass_card_no',
					textField: 'ass_card_no',
					selectBoxWidth: 800,
					selectBoxHeight: 240,
					grid: {
						columns: [{
							display: '资产卡片号',
							name: 'ass_card_no',
							align: 'left'
						}, {
							display: '资产编码',
							name: 'ass_code',
							align: 'left'
						}, {
							display: '资产名称',
							name: 'ass_name',
							align: 'left'
						}, {
							display: '规格',
							name: 'ass_spec',
							align: 'left'
						}, {
							display: '序列号',
							name: 'ass_seq_no',
							align: 'left'
						}, {
							display: '型号',
							name: 'ass_model',
							align: 'left'
						}, {
							display: '品牌',
							name: 'ass_brand',
							align: 'left'
						}, {
							display: '分类名称',
							name: 'ass_type_name',
							align: 'left'
						}, {
							display: '是否停用',
							name: 'is_stop',
							align: 'left'
						}, {
							display: '生产厂家',
							name: 'fac_name',
							align: 'left'
						}],
						switchPageSizeApplyComboBox: false,
						onSelectRow: f_onSelectRow_detail,
						url: '../assmaintainplan/choseAssCardNo.do?isCheck=false&use_state=1,2,3,4,5&ass_nature=' + liger.get("ass_nature").getValue(),
						pageSize: 30
					},
					keySupport: true,
					alwayShowInDown: true,
					keySupport: true,
					autocomplete: true,
					onSuccess: function() {
						this.parent("tr").next(
							".l-grid-row").find(
							"td:first").focus();
					}
				};

				grid = $("#maingrid")
					.ligerGrid({
						columns: [{
							display: '资产卡片号',
							name: 'ass_card_no',
							align: 'left',
							editor: editor
						}, {
							display: '资产编码',
							name: 'ass_code',
							align: 'left'
						}, {
							display: '资产名称',
							name: 'ass_name',
							align: 'left'
						}, {
							display: '规格',
							name: 'ass_spec',
							align: 'left'
						}, {
							display: '序列号',
							name: 'ass_seq_no',
							align: 'left'
						}, {
							display: '型号',
							name: 'ass_model',
							align: 'left'
						}, {
							display: '品牌',
							name: 'ass_brand',
							align: 'left'
						}, {
							display: '生产厂家',
							name: 'fac_name',
							align: 'left'
						}],
						dataAction: 'server',
						dataType: 'server',
						usePager: false,
						url: '../assmaintainplanass/queryAssMaintainPlanAss.do?isCheck=false&ass_nature=' + liger.get("ass_nature").getValue(),
						width: '100%',
						height: '100%',
						checkbox: true,
						enabledEdit: true,
						alternatingRow: true,
						onBeforeEdit: f_onBeforeEdit,
						onBeforeSubmitEdit: f_onBeforeSubmitEdit,
						onAfterEdit: f_onAfterEdit,
						isScroll: true,
						checkbox: true,
						rownumbers: true,
						delayLoad: true,
						selectRowButtonOnly: true, //heightDiff: -10,
						toolbar: {
							items: [{
									text: '保存',
									id: 'save',
									click: save,
									icon: 'save'
								}, {
									line: true
								},
								{
									text: '删除',
									id: 'delete',
									click: itemclick,
									icon: 'delete'
								}, {
									line: true
								},
								{
									text: '引入资产卡片',
									id: 'create',
									click: itemclick,
									icon: 'save'
								}, {
									line: true
								},
								{
									text: '关闭',
									id: 'close',
									click: this_close,
									icon: 'candle'   
								}
							]
						},
						onCheckRow: function(checked, data, rowid, rowdata) {
							plan_id = data.plan_id;
							detail_id = data.detail_id;
							queryItem(data.plan_id, data.detail_id);
							loadHeadItem();
							isItem_addRow();
							if ('${state}' != 0) {//置黑不能点击
								toobarmanage = gridManager.toolbarManager;
								toobarmanageItem = gridManagerItem.toolbarManager;
								toobarmanage.setDisabled('saveDetail');
								toobarmanage.setDisabled('save');
								toobarmanage.setDisabled('delete');
								toobarmanage.setDisabled('create');
								toobarmanageItem.setDisabled('copy');
								toobarmanageItem.setDisabled('saveItem');
								toobarmanageItem.setDisabled('deleteItem');
							}
						}
					});
				gridManager = $("#maingrid").ligerGetGridManager();
}

function loadHeadItem() {
	editorItem = {
		type: 'select',
		valueField: 'id',
		textField: 'text',
		selectBoxWidth: 350,
		selectBoxHeight: 240,
		grid: {
			columns: [{
				display: '项目编码',
				name: 'id',
				align: 'left'
			}, {
				display: '项目名称',
				name: 'text',
				align: 'left'
			}],
			switchPageSizeApplyComboBox: false,
			onSelectRow: f_onSelectRow_item,
			url: '../queryAssMaintainItem.do?isCheck=false&maintain_degree=' + liger.get("maintain_degree").getValue() + '&ass_name=' + $("#ass_name").val(),
			pageSize: 30
		},
		alwayShowInDown: true,
		keySupport: true,
		autocomplete: true,
		onSuccess: function() {
			this.parent("tr").next(
				".l-grid-row").find(
				"td:first").focus();

		}
	};
	gridItem = $("#planItemGrid")
					.ligerGrid({
						columns: [{
								display: '项目编码',
								name: 'item_code',
								align: 'left',
								width: 120
							},
							{
								display: '项目名称',
								name: 'item_name',
								align: 'left',
								textField: 'text',
								width: 260,
								editor: editorItem,
									
							},
							{
								display: '保养结果',
								name: 'is_normal',
								align: 'left',
								width: 150,
								type: 'text',
								render: function(rowdata, index, value) {
									var str1;
									var str2;
									var str3;

									if(rowdata.is_normal == 1) {
										str1 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='1' id='is_normal_yes" +
											index +
											"' name='is_normal" +
											index +
											"' checked='checked' />";
										str2 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='2' id='is_normal_yno" +
											index +
											"' name='is_normal" +
											index + "'/>";
										str3 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='9' id='is_normal_null" +
											index +
											"' name='is_normal" +
											index + "'/>";
									} else if(rowdata.is_normal == 2) {
										str1 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='1' id='is_normal_yes" +
											index +
											"' name='is_normal" +
											index + "'/>";
										str2 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='2' id='is_normal_yno" +
											index +
											"' name='is_normal" +
											index +
											"' checked='checked' />";
										str3 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='9' id='is_normal_null" +
											index +
											"' name='is_normal" +
											index + "'/>";
									} else if(rowdata.is_normal == 9) {
										str1 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='1' id='is_normal_yes" +
											index +
											"' name='is_normal" +
											index + "' />";
										str2 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='2' id='is_normal_yno" +
											index +
											"' name='is_normal" +
											index + "' />";
										str3 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='9' id='is_normal_null" +
											index +
											"' name='is_normal" +
											index +
											"' checked='checked'/>";
									} else {
										str1 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='1' id='is_normal_yes" +
											index +
											"' name='is_normal" +
											index + "' />";
										str2 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='2' id='is_normal_yno" +
											index +
											"' name='is_normal" +
											index + "'/>";
										str3 = "<input type='radio' onChange='getRadioValue(" +
											index +
											")' value='9' id='is_normal_null" +
											index +
											"' name='is_normal" +
											index + "'/>";
									}
									return "" +
										"<table class='l-table-edit'>" +
										"<tr>" +
										"<td style='padding-top: 6px;'>" +
										str1 +
										"</td>" +
										"<td style='padding-top: 5px;'><label for='is_normal_yes" + index + "'>通过</label></td>" +
										"<td style='padding-top: 6px;'>" +
										str2 +
										"</td>" +
										"<td style='padding-top: 5px;'><label for='is_normal_yno" + index + "'>未通过</label></td>" +
										"<td style='padding-top: 6px;'>" +
										str3 +
										"</td>" +
										"<td style='padding-top: 5px;'><label for='is_normal_null" + index + "'>不适用</label></td>" +
										"</tr>" + "</table>" + "";
								}

							}
						],
						dataAction: 'server',
						dataType: 'server',
						usePager: false,
						url: 'queryAssMaintainItem.do?isCheck=false',
						width: '100%',
						height: '100%',
						checkbox: true,
						enabledEdit: true,
						alternatingRow: true,
						onBeforeEdit: f_onBeforeEditItem,
						onBeforeSubmitEdit: f_onBeforeSubmitEditItem,
						onAfterEdit: f_onAfterEditItem,
						isScroll: true,
						checkbox: true,
						rownumbers: true,
						delayLoad: true,
						selectRowButtonOnly: true, //heightDiff: -10,
						toolbar: {
							items: [{
								text: '生成',
								id: 'copy',
								click: copy,
								icon: 'copy'
							}, {
								line: true
							}, {
								text: '保存',
								id: 'saveItem',
								click: saveItem,
								icon: 'save'
							}, {
								line: true
							}, {
								text: '删除',
								id: 'deleteItem',
								click: deleteItem,
								icon: 'delete'
							}, {
								line: true
							}, {
								text: '全部通过',
								id: 'allDdopt',
								click: allDdopt,
								icon: 'audit'
							}, {
								line: true
							}, {
								text: '全部取消',
								id: 'allNotDdopt',
								click: allNotDdopt,
								icon: 'bcancle'
							}]
						}
					});

				gridManagerItem = $("#planItemGrid").ligerGetGridManager();
			}

			function f_onBeforeEditItem(e) {
				rowindex_id_item = e.rowindex;
				clicked = 0;
				column_name_item = e.column.name;
			}

			function f_onSelectRow_item(data, rowindex, rowobj) {
				selectData = "";
				selectData = data;
				if(column_name_item == "item_name") {
					if(selectData != "" || selectData != null) {
						var data1 = gridManager.getCheckedRows();
						if(data1.length == 0 || data1.length > 1) {
							$.ligerDialog.warn('选择单个资产操作');
							return false;
						} else {
							var plan_id = 0;
							var detail_id = 0;
							$.each(data1, function() {
								if(isnull(this.group_id)) {
									$.ligerDialog.error('所选资产没有保存');
									return;
								}
								plan_id = this.plan_id;
								detail_id = this.detail_id;
							});
							gridItem.updateRow(rowindex_id_item, {
								item_code: data.id,
								item_name: data.text,
								is_normal: 0,
								plan_id: plan_id,
								detail_id: detail_id
							});
						}

					}
				}
				return true;
			}

			var rowindex_id = "";
			var column_name = "";

			function f_onBeforeEdit(e) {

				rowindex_id = e.rowindex;

				clicked = 0;

				column_name = e.column.name;

			}

			function allDdopt() {
				var data = gridItem.getData();
				$.each(data, function(i, v) {
					var row = gridItem.getRow(i);
					row.is_normal = 1;
					$("input[name='is_normal" + i + "'][value=2]").attr('checked',
						false);
					$("input[name='is_normal" + i + "'][value=9]").attr('checked',
						false);
					$("input[name='is_normal" + i + "'][value=1]")
						.attr('checked', true);
				});
				gridItem.reRender();
			}

			function allNotDdopt() {
				var data = gridItem.getData();
				$.each(data, function(i, v) {
					var row = gridItem.getRow(i);
					row.is_normal = 0;
					$("input[name='is_normal" + i + "']").attr('checked', false);

				});
			}

			//选中回充数据
			function f_onSelectRow_detail(data, rowindex, rowobj) {

				selectData = "";
				selectData = data;
				if(column_name == "ass_card_no") {
					if(selectData != "" || selectData != null) {
						grid.updateRow(rowindex_id, {
							ass_card_no: data.ass_card_no,
							ass_code: data.ass_code,
							ass_name: data.ass_name,
							fac_id: data.fac_id,
							fac_no: data.fac_no,
							fac_code: data.fac_code,
							fac_name: data.fac_name,
							ass_model: data.ass_model,
							ass_spec: data.ass_spec,
							ass_seq_no: data.ass_seq_no

						});

					}
				}

				/* if(column_name == "maintain_item_name"){
	     			
	     			if (selectData != "" || selectData != null) {
	     				//回充数据 
	     				//grid.updateCell('apply_emp', 100, e.record);
	     				grid.updateRow(rowindex_id, {
	     					maintain_item_code : data.maintain_item_code,
	     					maintain_item_name : data.maintain_item_name,
	     					
	     					
	     					
	     				});

	     			}
	     		} */
				return true;
			}

			function f_onSelectRow(data, rowindex, rowobj) {
				return true;
			}

			// 编辑单元格提交编辑状态之前作判断限制
			function f_onBeforeSubmitEditItem(e) {
				return true;
			}
			// 跳转到下一个单元格之前事件
			function f_onAfterEditItem(e) {
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

			function is_addRow() {
				setTimeout(function() { //当数据为空时 默认新增一行
					grid.addRow();
				}, 1000);

			}

			function this_close() {
				frameElement.dialog.close();
			}

			function itemclick(item) {
				if(item.id) {
					switch(item.id) {
						case "delete":
							var data = gridManager.getCheckedRows();

							if(data.length == 0) {

								$.ligerDialog.error('请选择行');

							} else {

								var ParamVo = [];
								var i = 0;
								$(data).each(function() {
									if(isnull(this.detail_id)) {
										gridManager.deleteSelectedRow();
									} else {
										ParamVo.push(
											this.group_id + "@" +
											this.hos_id + "@" +
											this.copy_code + "@" +
											this.plan_id + "@" +
											this.ass_card_no + "@" +
											this.detail_id
										);
									}
									i++;
								});
								if(ParamVo == "") {
									is_addRow();
									return;
								}
								$.ligerDialog.confirm('确定删除?', function(yes) {
									if(yes) {
										ajaxJsonObjectByUrl("../assmaintainplanitem/deleteAssMaintainPlanItem.do?isCheck=false", {
												ParamVo: ParamVo.toString()
											},
											function(responseData) {

												if(responseData.state == "true") {

													query();
													is_addRow();

												}
											});
									}
								});
							}

							return;
						case "create":
							//grid.deleteSelectedRow();
							var dept_id = liger.get("dept_id").getValue().split("@")[0];
							var dept_no = liger.get("dept_id").getValue().split("@")[1];
							if(dept_id == null || dept_no == null || dept_id == "" ||
								dept_no == "") {
								dept_no = "";
								dept_id = "";
							}
							var fn = $.ligerui.getPopupFn({
								top: 80,
								onSelect: function(e) {
									grid.addRows(e.data);
									grid.moveRange(e.data, 0);
								},
								grid: {
									columns: [

										{
											display: '资产卡片号',
											name: 'ass_card_no',
											align: 'left',
										},

										{
											display: '资产编码',
											name: 'ass_code',
											align: 'left',
										},

										{
											display: '资产名称',
											name: 'ass_name',
											align: 'left',
										},

										{
											display: '型号',
											name: 'ass_model',
											align: 'left',
										},

										{
											display: '规格',
											name: 'ass_spec',
											align: 'left',
										},

										{
											display: '品牌',
											name: 'ass_brand',
											align: 'left',
										},

										{
											display: '生产厂家',
											name: 'fac_name',
											align: 'left',
										},

									],
									dataAction: 'server',
									dataType: 'server',
									usePager: true,
									url: '../assmaintainplan/choseAssCardNo.do?isCheck=false&use_state=1,2,3,4,5&ass_nature=' + liger.get("ass_nature").getValue() + '&ass_card_no=' + liger.get("ass_card_no").getValue() + '&ass_name=' + liger.get("ass_name").getValue() + '&dept_id=' + dept_id + '&dept_no=' + dept_no,
									width: '100%',
									height: '100%',
									checkbox: true,
									rownumbers: true,
								}
							});

							fn();

					}
				}
			}

			function validateGrid() {
				var data = gridManager.getData();
				var msg = "";
				var targetMap = new HashMap();
				var msgMap = new HashMap();
				$.each(data, function(i, v) {
					var key = v.ass_card_no + "|" + v.ass_code + "|" + v.ass_model + "|" + v.ass_spec;
					var value = "第" + (i + 1) + "行";
					if(isnull(v.ass_card_no)) {
						gridManager.deleteRow(i);
						return;
					}
					if(isnull(v.ass_card_no)) {
						msg += "[卡片编号]、";
					}

					if(msg != "") {
						msgMap.put(value + msg + "不能为空或不能为零！\n\r", "");
					}
					if(isnull(targetMap.get(key))) {
						targetMap.put(key, value);
					} else {
						msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r", value);
					}
				});
				if(msg != "") {
					$.ligerDialog.warn(msgMap.keySet());
					return false;
				}
				if(data.length == 0) {
					$.ligerDialog.warn("无数据保存");
					return false;
				}
				return true;
			}

			function validateGridItem() {
				var data = gridManagerItem.getData();
				var msg = "";
				var targetMap = new HashMap();
				var msgMap = new HashMap();
				//删除空行
				$.each(data, function(i, v) {
					if(!isnull(v.item_code)) {
						var key = v.item_code;
						var value = "第" + (i + 1) + "行";
						if(isnull(targetMap.get(key))) {
							targetMap.put(key, value);
						} else {
							msg = "1";
							msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
								value);
						}
					}
				});

				if(msg != "") {
					$.ligerDialog.warn(msgMap.keySet());
					return false;
				}
				if(data.length == 0) {
					$.ligerDialog.warn("无数据保存");
					return false;
				}
				return true;
			}

			function getRadioValue(index) {
				var row = gridItem.getRow(index);
				row.is_normal = $("input[name='is_normal" + index + "']:checked").val();
			}

			function saveItem() {
				var data = gridManager.getCheckedRows();
				if(data.length == 0 || data.length > 1) {
					$.ligerDialog.warn('选择单个资产操作');
					return false;
				} else {
					var ass_card_no = 0;
					var plan_id = 0;
					var detail_id = 0;
					var flag = true;
					$.each(data, function() {
						if(isnull(this.group_id)) {
							$.ligerDialog.error('所选资产没有保存');
							flag = false;
						}
						ass_card_no = this.ass_card_no;
						plan_id = this.plan_id;
						detail_id = this.detail_id;
						/* ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.ass_card_no + "@"
								+ this.plan_id + "@" + this.detail_id 
						);*/

					});

					if(flag) {
						gridManagerItem.endEdit();
						var dataItem = gridManagerItem.getData();
						$.each(dataItem, function() {
							this.ass_card_no = ass_card_no;
						})
						if(validateGridItem()) {
							ajaxJsonObjectByUrl("saveAssMaintainItem.do?isCheck=false", {
								accept_item_data: JSON.stringify(dataItem)
							}, function(responseData) {
								if(responseData.state == "true") {
									queryItem(plan_id, detail_id);
								}
							});
						}
					}
				}

			}

			function deleteItem() {
				var data = gridManager.getCheckedRows();
				if(data.length == 0 || data.length > 1) {
					$.ligerDialog.warn('选择单个资产操作');
					return false;
				} else {
					var ass_card_no = 0;
					var plan_id = 0;
					var detail_id = 0;
					var flag = true;
					$.each(data, function() {
						if(isnull(this.group_id)) {
							$.ligerDialog.error('所选资产没有保存');
							flag = false;
						}
						ass_card_no = this.ass_card_no;
						plan_id = this.plan_id;
						detail_id = this.detail_id;
					});
					if(flag) {
						var dataItem = gridManagerItem.getCheckedRows();
						if(dataItem.length == 0) {
							$.ligerDialog.error('请选择行');
						} else {
							var ParamVo = [];
							$(dataItem).each(
								function() {
									if(isnull(this.group_id)) {
										gridManagerItem.deleteSelectedRow();
									} else {
										ParamVo.push(this.group_id + "@" +
											this.hos_id + "@" +
											this.copy_code + "@" +
											this.plan_id + "@" +
											this.detail_id + "@" +
											this.item_code);
									}
								});
							if(ParamVo == "") {
								isItem_addRow();
								return false;
							}
							$.ligerDialog.confirm('确定删除?', function(yes) {
								if(yes) {
									ajaxJsonObjectByUrl("deleteAssMaintainItem.do?isCheck=false", {
										ParamVo: ParamVo.toString()
									}, function(responseData) {
										if(responseData.state == "true") {
											queryItem(plan_id, detail_id);
										}
									});
								}
							});
						}
					}

				}
			}

			function copy() {
				var data = gridManager.getCheckedRows();
				if(data.length == 0 || data.length > 1) {
					$.ligerDialog.error('只能选择单个资产');
					return;
				} else {
					var ass_card_no = 0;
					var plan_id = 0;
					var detail_id = 0;
					$.each(data, function() {
						if(isnull(this.group_id)) {
							$.ligerDialog.error('所选资产没有保存');
							return;
						}
						ass_card_no = this.ass_card_no;
						plan_id = this.plan_id;
						detail_id = this.detail_id;
					});
					var itemDataLength = gridItem.getData().length; //获取行数
					for(var i = itemDataLength; i >= 0; i--) {
						gridItem.deleteRow(i); //删除行
					}

					var formPara = {
						maintain_degree: liger.get("maintain_degree").getValue(),
						ass_name: $("#ass_name").val(),
						'ass_card_no': ass_card_no
					};
					//gridItem.set("url","buildAssAcceptItem.do?ass_id="+ass_id.split("@")[0]);
					ajaxJsonObjectByUrl("buildAssMaintainItem.do?isCheck=false", formPara, function(
						responseData) {
						$.each(responseData.Rows, function(i, v) {
							gridItem.addRows({
								item_code: v.maintain_item_code,
								item_name: v.maintain_item_name,
								plan_id: plan_id,
								detail_id: detail_id,
								is_normal: 0
							});

						});

					}, false);
				}
			}

			function save() {

				if($("#plan_name").val() == "") {
					$.ligerDialog.error('计划名称不能为空');
					return;
				}
				if(liger.get("ass_year").getValue() == "") {
					$.ligerDialog.error('统计年度不能为空');
					return;
				}
				if(liger.get("ass_month").getValue() == "") {
					$.ligerDialog.error('统计月份不能为空');
					return;
				}
				if(liger.get("ass_nature").getValue() == "") {
					$.ligerDialog.error('资产性质不能为空');
					return;
				}
				if(liger.get("maintain_degree").getValue() == "") {
					$.ligerDialog.error('保养等级不能为空');
					return;
				}
				if(liger.get("plan_cycle").getValue() == "") {
					$.ligerDialog.error('保养周期不能为空');
					return;
				}
				if(liger.get("cycle_unit").getValue() == "") {
					$.ligerDialog.error('周期单位不能为空');
					return;
				}
				var zq = liger.get("plan_cycle").getValue();
				if($("#last_exec_date").val() != "" && $("#next_exec_date").val() != "") {
					if($("#last_exec_date").val() >= $("#next_exec_date").val()) {
						$.ligerDialog.error('上次保养日期必须小于下次保养日期');
						return;
					}
				}

				/* if ($("#plan_start_date").val() != "" && $("#plan_end_date").val() ){
		 	 		if($("#plan_start_date").val() >= $("#plan_end_date").val() ){
		 	 			$.ligerDialog.error('计划开始日期必须小于计划结束日期');
		 	 			return;
		 	 		}
		  		} */
				var data = gridManager.getData();
				var num = 0;
				for(var i = 0; i < data.length; i++) {

					if(data[i].ass_card_no) {
						num++;
					}
				}
				if(!num) {
					$.ligerDialog.error('明细数据不能为空');
					return false;
				}
				var formPara = {

					plan_id: $("#plan_id").val(),

					plan_no: $("#plan_no").val(),

					plan_name: $("#plan_name").val(),

					ass_year: $("#ass_year").val(),

					ass_month: $("#ass_month").val(),

					ass_nature: liger.get("ass_nature").getValue(),

					maintain_degree: liger.get("maintain_degree").getValue(),

					cycle_unit: liger.get("cycle_unit").getValue(),

					plan_cycle: $("#plan_cycle").val(),

					plan_exec_emp: liger.get("plan_exec_emp").getValue(),

					maintain_desc: $("#maintain_desc").val(),

					last_exec_date: $("#last_exec_date").val(),

					next_exec_date: $("#next_exec_date").val(),

					plan_start_date: $("#plan_start_date").val(),

					plan_end_date: $("#plan_end_date").val(),

					ParamVo: JSON.stringify(data)

				};
				if(validateGrid()) {
					ajaxJsonObjectByUrl("addAssMaintainPlan.do", formPara, function(responseData) {
						if(responseData.state == "true") {
							parentFrameUse().query();
							query();
							is_addRow();
						}
					});
				}
			}

			function loadDict() {
				//字典下拉框
				//资产性质
				autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false", "id", "text", true, true, "", false, "${ass_nature}");

				liger.get("ass_nature").setValue("${ass_nature}");

				liger.get("ass_nature").setText("${naturs_name}");
				liger.get("ass_nature").setDisabled();

				//计划执行人
				autocomplete("#plan_exec_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true, "", false, "${plan_exec_emp}");

				liger.get("plan_exec_emp").setValue("${plan_exec_emp}");

				liger.get("plan_exec_emp").setText("${plan_exec_emp_name}");
				/* //制单人
				autocomplete("#create_emp","../../../hrp/sys/queryUserDict.do?isCheck=false","id", "text", true, true, "", false,"${create_emp}");

				liger.get("create_emp").setValue("${create_emp}");

				liger.get("create_emp").setText("${create_emp_name}"); */

				//保养等级
				/* $("#maintain_degree").ligerTextBox({
					
					width : 160
					
				}); */
				$('#maintain_degree').ligerComboBox({
					data: [{
						id: 0,
						text: '一级保养'
					}, {
						id: 1,
						text: '二级保养'
					}, {
						id: 2,
						text: '三级保养'
					}, {
						id: 3,
						text: '四级保养'
					}],
					valueField: 'id',
					textField: 'text',
					cancelable: true,
					width: 160
				});

				liger.get("maintain_degree").setValue("${maintain_degree}");

				$("#plan_no").ligerTextBox({
					disabled: true,
					cancelable: false,
					width: 160
				});

				$("#plan_name").ligerTextBox({
					width: 160
				});

				$("#ass_year").ligerTextBox({
					width: 160
				});

				$("#ass_month").ligerTextBox({
					width: 160
				});

				$("#maintain_degree").ligerTextBox({
					width: 160
				});

				/*  $("#cycle_unit").ligerTextBox({width:160}); */
				/*    $("#cycle_unit").ligerTextBox({ width:160,digits: true }); */
				//$("#plan_cycle").ligerTextBox({width:160});

				/*  $("#cycle_unit").ligerComboBox({
						width : 160
					}); */
				/*  liger.get("cycle_unit").setValue("${cycle_unit}") */
				$('#cycle_unit').ligerComboBox({
					data: [{
						id: 1,
						text: '月'
					}, {
						id: 0,
						text: '年'
					}, {
						id: 2,
						text: '日'
					}],
					valueField: 'id',
					textField: 'text',
					width: 160,
					cancelable: true
				})
				liger.get("cycle_unit").setValue("${cycle_unit}");
				$("#maintain_desc").ligerTextBox({
					width: 160
				});

				$("#last_exec_date").ligerTextBox({
					width: 160
				});

				$("#next_exec_date").ligerTextBox({
					width: 160
				});

				$("#plan_start_date").ligerTextBox({
					width: 160
				});

				$("#plan_end_date").ligerTextBox({
					width: 160
				});

				$("#plan_cycle").ligerTextBox({
					width: 160,
					digits: true
				});

				$("#ass_name").ligerTextBox({
					width: 160 
				});

				$("#dept_id").ligerComboBox({
					url: '../queryDeptDict.do?isCheck=false',
					valueField: 'id',
					textField: 'text',
					selectBoxWidth: 160,
					autocomplete: true,
					width: 160,
					onSelected: function(id, text) {
						//loadHead();
					}
				});

				$("#ass_card_no").ligerTextBox({
					cancelable: false,
					width: 160
				});

			}

			function is_addRow() {
				setTimeout(function() { //当数据为空时 默认新增一行
					grid.addRow();
				}, 1000);
			}

			function isItem_addRow() {
				setTimeout(function() { //当数据为空时 默认新增一行
					gridItem.addRow();
				}, 1000);
			}

			function this_close() {
				frameElement.dialog.close();
			}
			
			function queryDict(){
				var data = gridManager.getCheckedRows();
				if(data.length == 0 || data.length > 1) {
					return;
				} else {
					editorItem.grid.url = '../queryAssMaintainItem.do?isCheck=false&maintain_degree=' + liger.get("maintain_degree").getValue() + '&ass_name=' + $("#ass_name").val();
					/*console.log(gridItem);
					gridItem.reRender();
					console.log($("#ass_name").val());
					console.log("进来了");*/
				}
			}
		</script>

	</head>

	<body onload="is_addRow()">
		<div id="pageloading" class="l-loading" style="display: none"></div>

		<input name="plan_id" type="hidden" id="plan_id" value="${plan_id}" />
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>计划编号：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_no" disabled="disabled" type="text" id="plan_no" value="${plan_no}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>计划名称：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_name" type="text" id="plan_name" value="${plan_name}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>统计年度：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_year" type="text" id="ass_year" value="${ass_year}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>统计月份：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_month" type="text" id="ass_month" value="${ass_month}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>资产性质：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature" value="${ass_nature}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>保养等级：</td>
				<td align="left" class="l-table-edit-td">
					<!-- <select id="maintain_degree" name="maintain_degree"> 
		            			<option value=""></option>
		                		<option value="0">一级保养</option>
		                		<option value="1">二级保养</option>
		                		<option value="2">三级保养</option>
		                		<option value="3">四级保养</option>
		                	</select> -->
					<input name="maintain_degree" type="text" id="maintain_degree" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>周期单位：</td>
				<td align="left" class="l-table-edit-td">
					<!-- <select id="cycle_unit" name="cycle_unit"> 
		            			<option value=""></option>
		                		<option value="0">年</option>
		                		<option value="1">月</option>
		                		<option value="2">日</option>
		                	</select> -->
					<input name="cycle_unit" type="text" id="cycle_unit" />
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;"><b><font color="red">*</font></b>保养周期：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_cycle" type="text" id="plan_cycle" value="${plan_cycle}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">计划执行人：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_exec_emp" type="text" id="plan_exec_emp" value="${plan_exec_emp}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">保养说明：</td>
				<td align="left" class="l-table-edit-td"><input name="maintain_desc" type="text" id="maintain_desc" value="${maintain_desc}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">上次保养日期：</td>
				<td align="left" class="l-table-edit-td"><input name="last_exec_date" type="text" id="last_exec_date" value="${last_exec_date}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">下次保养日期：</td>
				<td align="left" class="l-table-edit-td"><input name="next_exec_date" type="text" id="next_exec_date" value="${next_exec_date}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">计划开始日期：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_start_date" type="text" id="plan_start_date" value="${plan_start_date}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">计划结束日期：</td>
				<td align="left" class="l-table-edit-td"><input name="plan_end_date" type="text" id="plan_end_date" value="${plan_end_date}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">资产卡片号：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_card_no" disabled="disabled" type="text" id="ass_card_no" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left:20px;">资产名称：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_name"  type="text" id="ass_name" onchange="queryDict()" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" /></td>
			</tr>
		</table>
		<div id="layout1">
			<div position="center" title="资产列表">
				<div>
					<div id="maingrid"></div>
				</div>
			</div>
			<div position="right" title="保养项目列表">
				<div>
					<div id="planItemGrid"></div>
				</div>
			</div>
		</div>
	</body>

</html>