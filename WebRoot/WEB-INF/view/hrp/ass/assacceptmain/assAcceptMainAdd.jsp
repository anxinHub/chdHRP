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
	var ass_id = 0;
	var editor,ass_naturs;
	var is_well_dict = {
		Rows : [ {
			"id" : 0,
			"text" : "通过"
		}, {
			"id" : 1,
			"text" : "未通过"
		} ],
		Total : 2
	};
	$(function() {
		$("#layout1").ligerLayout({
			rightWidth : '600',
			//heightDiff: -8,
			//每展开左边树即刷新一次表格，以免结构混乱
			onRightToggle : function() {
				grid._onResize();
			},
			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
			onEndResize : function(a, b) {
				grid._onResize();
			}
		});

		loadDict();//加载下拉框
		loadHead(null);
		loadHeadItem(null);
		loadHotkeys();
		$("#accept_no").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#ass_year").ligerTextBox({
			width : 160
		});
		$("#ass_month").ligerTextBox({
			width : 160
		});
		$("#accept_date").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		$("#contract_id").ligerTextBox({
			width : 160
		});
		
		$("#ven_id").change(function(){
			var ven_id = liger.get("ven_id").getValue().split("@")[0];
			var ven_no = liger.get("ven_id").getValue().split("@")[1];
			if (ven_no == null || ven_id == null || ven_id == ""
					|| ven_no == "") {
				ven_no = "";
				ven_id = "";
			}
			
			editor.grid.url = '../queryAssNoDictTable.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no;
		});
	});

	function query(accept_id) {
		grid.options.parms = [];
		grid.options.newPage = 1;
		grid.options.parms.push({
			name : 'accept_id',
			value : accept_id == "" || accept_id == null
					|| accept_id == "undefined" ? "0" : accept_id
		});
		grid.loadData(grid.where);
	}

	function queryItem(accept_id, accept_detail_id) {

		gridItem.options.parms = [];
		gridItem.options.newPage = 1;
		gridItem.options.parms.push({
			name : 'accept_id',
			value : accept_id == "" || accept_id == null
					|| accept_id == "undefined" ? "0" : accept_id
		});

		gridItem.options.parms.push({
			name : 'accept_detail_id',
			value : accept_detail_id == "" || accept_detail_id == null
					|| accept_detail_id == "undefined" ? "0" : accept_detail_id
		});
		gridItem.loadData(gridItem.where);
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
					},{
						display : '资产性质',
						name : 'ass_naturs',
						hide : true
					} ],
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_detail,
					url : '../queryAssNoDictTable.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no,
					pageSize : 30
				},
				keySupport : true,
				alwayShowInDown : true,
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
										align : 'left',
										width : '90',
										render : function(rowdata, rowindex,
												value) {
											if (rowdata.is_well == 0) {
												return rowdata.ass_code
														+ "&nbsp;&nbsp;<font color='red'><b>验</b></font>"
											}
											return rowdata.ass_code;
										}
									},
									{
										display : '资产名称',
										width : 100,
										name : 'ass_id',
										align : 'left',
										textField : 'ass_name',
										editor : editor,
										render : function(rowdata, rowindex,
												value) {
											return rowdata.ass_name;
										},
										totalSummary : {
											render : function(suminf, column,
													cell) {
												return '<div>合计</div>';
											},
											align : 'center'
										}

									},
									{
										display : '型号',
										name : 'ass_model',
										align : 'left',
										width : 100,
										editor : {
											type : 'text'
										}
									},
									{
										display : '规格',
										name : 'ass_spec',
										align : 'left',
										width : 100,
										editor : {
											type : 'text'
										}
									},
									{
										display : '计量单位',
										name : 'unit_code',
										textField:'unit_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryHosUnitDict.do?isCheck=false',
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
										width : '50'
									},
									{
										display : '品牌',
										name : 'ass_brand',
										align : 'left',
										width : 100,
										editor : {
											type : 'text'
										}
									},
									{
										display : '生产厂家',
										name : 'fac_id',
										textField : 'fac_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../queryHosFacDict.do?isCheck=false',
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
										width : 200
									},
									{
										display : '折旧年限',
										name : 'depre_years',
										align : 'left',
										width : 100,
									},
									{
										display : '折旧方法',
										name : 'ass_depre_code',
										textField: 'ass_depre_name',
										align : 'left',
										width : 100,
									},
									{
										display : '残值率',
										name : 'remain_value',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										width : '90',
										render : function(item) {
											return formatNumber(
													item.remain_value == null ? 0
															: item.remain_value,'${ass_05006}',1);
										}
									},
									{
										display : '保修日期',
										name : 'warranty_date',
										align : 'left',
										width : 100,
										type: 'date',
										format: 'yyyy-MM-dd',
										editor: {type: 'date',showSelectBox:false}
									},
									{
										display : '折旧开始日期',
										name : 'depre_begin_date',
										align : 'left',
										width : 100,
										type: 'date',
										format: 'yyyy-MM-dd',
										editor: {type: 'date',showSelectBox:false}
									},
									{
										display : '验收数量',
										name : 'accept_amount',
										type : 'int',
										width : 100,
										editor : {
											type : 'int'
										},
										align : 'left',
										width : '90',
									},
									{
										display : '验收单价',
										name : 'accept_price',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										width : '90',
										render : function(item) {
											return formatNumber(
													item.accept_price == null ? 0
															: item.accept_price,
													'${ass_05006}',
													1);
										}
									},
									{
										display : '验收金额',
										name : 'accept_money',
										align : 'right',
										width : 180,
										totalSummary : {
											render : function(suminf, column,
													cell) {
												return '<div>'
														+ formatNumber(
																suminf.sum, 2,
																1) + '</div>';
											}
										},
										render : function(item) {
											item.accept_money = item.accept_amount
													* item.accept_price;
											return formatNumber(
													item.accept_money == null ? 0
															: item.accept_money,
													'${ass_05005}',
													1);
										}
									}, {
										display : '验收结果',
										name : 'is_well',
										align : 'left',
										width : 100,
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											data : is_well_dict.Rows,
											keySupport : true,
											autocomplete : true
										},
										render : function(item) {
											if (item.is_well == 1) {
												return '未通过';
											} else if (item.is_well == 0) {
												return '通过';
											} else {
												return "";
											}
										}
									}, {
										display : '验收说明',
										name : 'accept_desc',
										align : 'left',
										width : 120,
										editor : {
											type : 'text'
										}
									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryAssAcceptDetail.do',
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
								} ,{
									line : true
								},{
									text :'图片信息',
									id   :'photo',
									click:photo,
									icon : ''
								}]
							},
							onCheckRow : function(checked, data, rowid, rowdata) {
								ass_id = data.ass_id.split("@")[0];
								queryItem(data.accept_id, data.accept_detail_id);
								loadHeadItem();
								isItem_addRow();
							}
						});
		gridManager = $("#maingrid").ligerGetGridManager();
	}

	function loadHeadItem() {
		gridItem = $("#acceptItemGrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '项目编码',
										name : 'item_code',
										align : 'left',
										width : 120
									},
									{
										display : '项目名称',
										name : 'item_name',
										align : 'left',
										textField : 'text',
										width : 260,
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											selectBoxWidth : 350,
											selectBoxHeight : 240,
											grid : {
												columns : [ {
													display : '项目编码',
													name : 'id',
													align : 'left'
												}, {
													display : '项目名称',
													name : 'text',
													align : 'left'
												} ],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_item,
												url : '../queryAssAcceptItem.do?isCheck=false&ass_id='
														+ ass_id,
												pageSize : 30
											},
											alwayShowInDown : true,
											keySupport : true,
											autocomplete : true,
											onSuccess : function() {
												this.parent("tr").next(
														".l-grid-row").find(
														"td:first").focus();

											}
										}
									},
									{
										display : '验收结果',
										name : 'is_normal',
										align : 'left',
										width : 150,
										type : 'text',
										render : function(rowdata, index, value) {
											var str1;
											var str2;
											var str3;

											if (rowdata.is_normal == 1) {
												str1 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='1' id='is_normal_yes"
														+ index
														+ "' name='is_normal"
														+ index
														+ "' checked='checked' />";
												str2 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='2' id='is_normal_yno"
														+ index
														+ "' name='is_normal"
														+ index + "'/>";
												str3 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='9' id='is_normal_null"
														+ index
														+ "' name='is_normal"
														+ index + "'/>";
											} else if (rowdata.is_normal == 2) {
												str1 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='1' id='is_normal_yes"
														+ index
														+ "' name='is_normal"
														+ index + "'/>";
												str2 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='2' id='is_normal_yno"
														+ index
														+ "' name='is_normal"
														+ index
														+ "' checked='checked' />";
												str3 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='9' id='is_normal_null"
														+ index
														+ "' name='is_normal"
														+ index + "'/>";
											} else if (rowdata.is_normal == 9) {
												str1 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='1' id='is_normal_yes"
														+ index
														+ "' name='is_normal"
														+ index + "' />";
												str2 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='2' id='is_normal_yno"
														+ index
														+ "' name='is_normal"
														+ index + "' />";
												str3 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='9' id='is_normal_null"
														+ index
														+ "' name='is_normal"
														+ index
														+ "' checked='checked'/>";
											} else {
												str1 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='1' id='is_normal_yes"
														+ index
														+ "' name='is_normal"
														+ index + "' />";
												str2 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='2' id='is_normal_yno"
														+ index
														+ "' name='is_normal"
														+ index + "'/>";
												str3 = "<input type='radio' onChange='getRadioValue("
														+ index
														+ ")' value='9' id='is_normal_null"
														+ index
														+ "' name='is_normal"
														+ index + "'/>";
											}
											return ""
													+ "<table class='l-table-edit'>"
													+ "<tr>"
													+ "<td style='padding-top: 6px;'>"
													+ str1
													+ "</td>"
													+ "<td style='padding-top: 5px;'><label for='is_normal_yes"+index+"'>通过</label></td>"
													+ "<td style='padding-top: 6px;'>"
													+ str2
													+ "</td>"
													+ "<td style='padding-top: 5px;'><label for='is_normal_yno"+index+"'>未通过</label></td>"
													+ "<td style='padding-top: 6px;'>"
													+ str3
													+ "</td>"
													+ "<td style='padding-top: 5px;'><label for='is_normal_null"+index+"'>不适用</label></td>"
													+ "</tr>" + "</table>" + "";
										}

									} ],
							dataAction : 'server',
							dataType : 'server',
							usePager : false,
							url : 'queryAssAcceptItem.do',
							width : '100%',
							height : '100%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEditItem,
							onBeforeSubmitEdit : f_onBeforeSubmitEditItem,
							onAfterEdit : f_onAfterEditItem,
							isScroll : true,
							checkbox : true,
							rownumbers : true,
							delayLoad : true,
							selectRowButtonOnly : true,//heightDiff: -10,
							toolbar : {
								items : [ {
									text : '生成',
									id : 'copy',
									click : copy,
									icon : 'copy'
								}, {
									line : true
								}, {
									text : '保存',
									id : 'saveItem',
									click : saveItem,
									icon : 'save'
								}, {
									line : true
								}, {
									text : '删除',
									id : 'deleteItem',
									click : deleteItem,
									icon : 'delete'
								}, {
									line : true
								}, {
									text : '全部通过',
									id : 'allDdopt',
									click : allDdopt,
									icon : 'audit'
								}, {
									line : true
								}, {
									text : '全部取消',
									id : 'allNotDdopt',
									click : allNotDdopt,
									icon : 'bcancle'
								} ]
							}
						});

		gridManagerItem = $("#acceptItemGrid").ligerGetGridManager();
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

	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	var rowindex_id_item = "";
	var column_name_item = "";
	function f_onBeforeEditItem(e) {
		rowindex_id_item = e.rowindex;
		clicked = 0;
		column_name_item = e.column.name;
	}

	function f_onSelectRow_item(data, rowindex, rowobj) {
		selectData = "";
		selectData = data;
		if (column_name_item == "item_name") {
			if (selectData != "" || selectData != null) {
				var data1 = gridManager.getCheckedRows();
				if (data1.length == 0 || data1.length > 1) {
					$.ligerDialog.warn('选择单个资产操作');
					return false;
				} else {
					var accept_id = 0;
					var accept_detail_id = 0;
					$.each(data1, function() {
						if (isnull(this.group_id)) {
							$.ligerDialog.error('所选资产没有保存');
							return;
						}
						accept_id = this.accept_id;
						accept_detail_id = this.accept_detail_id;
					});
					gridItem.updateRow(rowindex_id_item, {
						item_code : data.id,
						item_name : data.text,
						is_normal : 0,
						accept_id : accept_id,
						accept_detail_id : accept_detail_id
					});
				}

			}
		}
		return true;
	}

	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {
		if(liger.get("ass_naturs").getValue() ==null || liger.get("ass_naturs").getValue()==""){
			$.ligerDialog.error('请选择行资产性质');
			return;
		}
		selectData = "";
		selectData = data;
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					fac_id : data.fac_id,
					fac_name : data.fac_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec,
					ass_brand : data.ass_brand,
					ass_depre_code : data.ass_depre_code,
					ass_depre_name : data.ass_depre_name,
					depre_years : data.depre_years,
					ass_naturs : data.naturs_code,
					unit_code:data.ass_unit,
					unit_name:data.ass_unit_name
				});

			}
		}
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
		grid.updateCell('accept_money', e.record.accept_price
				* e.record.accept_amount, e.record);
		grid.updateTotalSummary();
		return true;
	}
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					var i = 0;
					$(data).each(
							function() {
								if (isnull(this.accept_detail_id)) {
									gridManager.deleteSelectedRow();
								} else {
									ParamVo.push(this.group_id + "@"
											+ this.hos_id + "@"
											+ this.copy_code + "@"
											+ this.accept_id + "@"
											+ this.accept_detail_id);
								}
								i++;
							});
					if (ParamVo == "") {
						is_addRow();
						return;
					}
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAssAcceptDetail.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query($("#accept_id").val());
									queryItem(accept_id, "");
									queryItem(accept_id, accept_detail_id);
								}
							});
						}
					});
				}
				return;
			}
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

				/* if (v.fac_id == '@' || isnull(v.fac_id)) {
					msg += "[生产厂家]、";
				} */
				if (isnull(v.accept_amount)) {
					msg += "[验收数量]、";
				}
				if (isnull(v.accept_price)) {
					msg += "[验收单价]、";
				}
				if (isnull(v.accept_money)) {
					msg += "[验收金额]、";
				}
				if (v.is_well != 0 && v.is_well != 1) {
					msg += "[验收结果]";
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
		var price=0;
		for(var i=0;i<data.length;i++){
            if(data[i].accept_price!=undefined){
               price+=data[i].accept_price;

             }
           }
           if(price<0){
              $.ligerDialog.warn("单价不能为负数");
               return false;
             }
           var accept_price=0;
   		for(var i=0;i<data.length;i++){
               if(data[i].accept_money!=undefined){
            	   accept_price+=data[i].accept_money;

                }
              }
              if(accept_price<0){
                 $.ligerDialog.warn("验收金额不能为负数");
                  return false;
                }
		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
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
			if (!isnull(v.item_code)) {
				var key = v.item_code;
				var value = "第" + (i + 1) + "行";
				if (isnull(targetMap.get(key))) {
					targetMap.put(key, value);
				} else {
					msg = "1";
					msgMap.put(targetMap.get(key) + "与" + value + "重复！\n\r",
							value);
				}
			}
		});

		if (msg != "") {
			$.ligerDialog.warn(msgMap.keySet());
			return false;
		}
		if (data.length == 0) {
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
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_id = 0;
			var accept_id = 0;
			var accept_detail_id = 0;
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				accept_id = this.accept_id;
				accept_detail_id = this.accept_detail_id;
			});
			if (flag) {
				gridManagerItem.endEdit();
				var dataItem = gridManagerItem.getData();
				if (validateGridItem()) {
					ajaxJsonObjectByUrl("saveAssAcceptItem.do", {
						accept_item_data : JSON.stringify(dataItem)
					}, function(responseData) {
						if (responseData.state == "true") {
							queryItem(accept_id, accept_detail_id);
						}
					});
				}
			}
		}

	}

	function deleteItem() {
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_id = 0;
			var accept_id = 0;
			var accept_detail_id = 0;
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				accept_id = this.accept_id;
				accept_detail_id = this.accept_detail_id;
			});
			if (flag) {
				var dataItem = gridManagerItem.getCheckedRows();
				if (dataItem.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(dataItem).each(
							function() {
								if (isnull(this.group_id)) {
									gridManagerItem.deleteSelectedRow();
								} else {
									ParamVo.push(this.group_id + "@"
											+ this.hos_id + "@"
											+ this.copy_code + "@"
											+ this.accept_id + "@"
											+ this.accept_detail_id + "@"
											+ this.item_code);
								}
							});
					if (ParamVo == "") {
						isItem_addRow();
						return false;
					}
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAssAcceptItem.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									queryItem(accept_id, accept_detail_id);
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
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.error('只能选择单个资产');
			return;
		} else {
			var ass_id = 0;
			var accept_id = 0;
			var accept_detail_id = 0;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.error('所选资产没有保存');
					return;
				}
				ass_id = this.ass_id;
				accept_id = this.accept_id;
				accept_detail_id = this.accept_detail_id;
			});
			var itemDataLength = gridItem.getData().length;//获取行数
			for (var i = itemDataLength; i >= 0; i--) {
				gridItem.deleteRow(i);//删除行
			}

			var formPara = {
				'ass_id' : ass_id.split("@")[0]
			};
			//gridItem.set("url","buildAssAcceptItem.do?ass_id="+ass_id.split("@")[0]);
			ajaxJsonObjectByUrl("buildAssAcceptItem.do", formPara, function(
					responseData) {
				$.each(responseData.Rows, function(i, v) {
					gridItem.addRows({
						item_code : v.accept_item_code,
						item_name : v.accept_item_name,
						accept_id : accept_id,
						accept_detail_id : accept_detail_id,
						is_normal : 0
					});

				});

			}, false);
		}
	}

	function save() {
		gridManager.endEdit();
		if ($("#ass_year").val == "") {
			$.ligerDialog.error('统计年度不能为空');
			return;
		}
		if ($("#ass_month").val() == "") {
			$.ligerDialog.error('统计月份不能为空');
			return;
		}
		if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		if (liger.get("dept_id").getValue() == "") {
			$.ligerDialog.error('验收科室不能为空');
			return;
		}
		if ($("#accept_emp").val() == "") {
			$.ligerDialog.error('验收人不能为空');
			return;
		}
		if ($("#accept_date").val() == "") {
			$.ligerDialog.error('验收日期不能为空');
			return;
		}
		ass_naturs=liger.get("ass_naturs").getValue(); 
		var nat='';
		var data = gridManager.getData();
		//alert(JSON.stringify(data));return;
		var num = 0;
		for (var i = 0; i < data.length-1; i++) {

			if (data[i].ass_code) {
				num++;
			}
			if(ass_naturs != data[i].ass_naturs){
				nat+='第'+(i+1) + '、'
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		if(nat.length>0){
			$.ligerDialog.error(nat+"行资产性质与上面不一致！");
			return;
		}
		var str=liger.get("source_code").getText();
		var formPara = {

			accept_id : $("#accept_id").val() == "" ? 0 : $("#accept_id").val(),

			accept_no : $("#accept_no").val(),

			ass_year : $("#ass_year").val(),

			ass_month : $("#ass_month").val(),

			contract_id : liger.get("contract_id").getValue().split("@")[0],

			ven_id : liger.get("ven_id").getValue().split("@")[0],

			ven_no : liger.get("ven_id").getValue().split("@")[1],

			dept_id : liger.get("dept_id").getValue().split("@")[0],

			dept_no : liger.get("dept_id").getValue().split("@")[1],

			accept_emp : liger.get("accept_emp").getValue(),

			accept_date : $("#accept_date").val(),

			accept_desc : $("#accept_desc").val(),
			
			device_code : liger.get("device_code").getValue(),
			buy_code : liger.get("buy_code").getValue(),
			source_code : str.substring(0,str.indexOf(" ")),
			ass_naturs:liger.get("ass_naturs").getValue(),
			ParamVo : JSON.stringify(data)

		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("addAssAcceptMain.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					parentFrameUse().query();
					$("#accept_id").val(responseData.accept_id);
					$("#accept_no").val(responseData.accept_no);
					query(responseData.accept_id);
				}
			}, false);
		}
	}
	function loadDict() {
		//字典下拉框

		autocomplete("#accept_emp",
				"../../../hrp/sys/queryUserDict.do?isCheck=false", "id",
				"text", true, true, null, null, '${user_id}');
		//获取当前用户科室默认值
		autocomplete("#dept_id","../queryDeptDict.do?isCheck=false", "id", "text",
							true, true, null, null);
		ajaxJsonObjectByUrl("../queryDeptDictInitValue.do?isCheck=false", {},function(data) {
			var id = data == null || data == "" ? null : data[0].id;
			var text = data == null || data == "" ? null : data[0].text;
			liger.get("dept_id").setValue(id);
			liger.get("dept_id").setText(text);
		}, false)

		autocomplete("#contract_id", "../queryContractMain.do?isCheck=false",
				"id", "text", true, true, null, false, null, "300");
		autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, null, false, null, "400");
		autocomplete("#source_code", "../querySourceDict.do?isCheck=false", "id",
				"text", true, true, null, true, null, "160");
		autocomplete("#buy_code", "../queryBuyDict.do?isCheck=false", "id",
				"text", true, true, null, false, null, "160");
		autocomplete("#device_code", "../queryDeviceDict.do?isCheck=false", "id",
				"text", true, true, null, false, null, "160");
		autocomplete("#ass_naturs", "../queryAssNaturs.do?isCheck=false", "id",
				"text", true, true, null, true, null, "160");
		autodate("#ass_year", "YYYY");
		autodate("#ass_month", "MM");
		autodate("#accept_date");
		//$("#photo").ligerButton({click: photo, width:80});
	}
	function photo(){
		if($("#accept_no").val() == ""){
			$.ligerDialog.error('请先保存单据！');
			return ;
		}else{
			parent.$.ligerDialog
			.open({
				url : 'hrp/ass/assacceptmain/acceptphoto/assAcceptPhotoMainPage.do?isCheck=false&accept_no='
						+ $("#accept_no").val(),
				height : $(window).height(),
				width : $(window).width(),
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : true,
				isResize : true,
				parentframename : window.name,
				title : '验收图片信息'
			});
		}
		
	}
	//键盘事件
	function loadHotkeys() {

		hotkeys('A', add);
		hotkeys('D', remove);
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
	function loadRows() {
		is_addRow();
		isItem_addRow();
	}
</script>

</head>

<body onload="loadRows()">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input type="hidden" id="accept_id" name="accept_id" />
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>验收单号：</td>
				<td align="left" class="l-table-edit-td"><input
					name="accept_no" disabled="disabled" type="text" id="accept_no" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>统计年度：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_year"
					type="text" id="ass_year" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>统计月份：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_month" type="text" id="ass_month" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>验收日期：</td>
				<td align="left" class="l-table-edit-td"><input
					name="accept_date" type="text" id="accept_date" class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
				<td align="left" class="l-table-edit-td"><input name="ven_id"
					type="text" id="ven_id" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>验收科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id"
					type="text" id="dept_id" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;"><b><font color="red">*</font></b>验&nbsp;&nbsp;收&nbsp;&nbsp;人：</td>
				<td align="left" class="l-table-edit-td"><input
					name="accept_emp" type="text" id="accept_emp" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;同：</td>
				<td align="left" class="l-table-edit-td"><input
					name="contract_id" type="text" id="contract_id" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
						style="padding-left: 20px;"><b><font color="red">*</font></b>资产性质：</td>
				<td align="left" class="l-table-edit-td"><input
					name="ass_naturs" type="text" id="ass_naturs" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">设备来源：</td>
				<td align="left" class="l-table-edit-td"><input
					name="contract_id" type="text" id="device_code" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">申购类别：</td>
				<td align="left" class="l-table-edit-td"><input
					name="contract_id" type="text" id="buy_code" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">资金来源：</td>
				<td align="left" class="l-table-edit-td"><input
					name="contract_id" type="text" id="source_code" /></td>
				<td align="left"></td>
				
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">摘要：</td>
				<td align="left" class="l-table-edit-td" colspan="9"><textarea
						name="accept_desc" id="accept_desc" rows="3" cols="50"  style="border-color: #aecaf0;"></textarea>
				</td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

	<div id="layout1">
		<div position="center" title="资产列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="验收项目列表">
			<div>
				<div id="acceptItemGrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
