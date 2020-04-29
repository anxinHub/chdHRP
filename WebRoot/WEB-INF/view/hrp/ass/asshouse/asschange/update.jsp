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
	var sourcegrid;
	var sourcegridManager = null;
	var userUpdateStr;
	var editor;
	$(function() {
		$("#layout1").ligerLayout({
			rightWidth : '600',
			//heightDiff: -8,
			//每展开左边树即刷新一次表格，以免结构混乱
			onRightToggle : function() {
				grid._onResize();
				sourcegrid._onResize();
			},
			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
			onEndResize : function(a, b) {
				grid._onResize();
				sourcegrid._onResize();
			}
		});
		
		loadDict();//加载下拉框
		loadHead(null);
		loadHotkeys();
		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('saveDetail');
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('delete');
			toobarmanageItem =sourcegridManager.toolbarManager;
			toobarmanageItem.setDisabled('saveSource');
			toobarmanageItem.setDisabled('removeSource');
		}
		$("#change_no").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
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
			editor.grid.url = '../../queryAssCardTable.do?isCheck=false&ass_nature=01&use_state=1,2,3,4,5&in_date='
				+ $("#create_date").val();
		});
		query();
	});
	function changeCreateDate(){
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}
		editor.grid.url = '../../queryAssCardTable.do?isCheck=false&ass_nature=01&use_state=1,2,3,4,5&in_date='
			+ $("#create_date").val();
	}

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
			+ $("#create_date").val()+'&ass_card_not_exists='+ass_card_nos.toString();

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

			if (data[i].ass_card_no) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		var formPara = {
			change_no : $("#change_no").val() == "" ? '0' : $("#change_no").val(),
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			ven_no : liger.get("ven_id").getValue().split("@")[1],
			note : $("#note").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssChangeHouse.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#change_no").val(responseData.change_no);
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
			name : 'change_no',
			value : $("#change_no").val() == "" ? "0" : $("#change_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	function querySource(change_no,ass_card_no) {
		sourcegrid.options.parms = [];
		sourcegrid.options.newPage = 1;
		sourcegrid.options.parms.push({
			name : 'change_no',
			value : change_no
		});
		sourcegrid.options.parms.push({
			name : 'ass_card_no',
			value : ass_card_no
		});
		sourcegrid.loadData(sourcegrid.where);
		
		setTimeout(function() { //当数据为空时 默认新增一行
			sourcegrid.addRow({ass_card_no:ass_card_no});
		}, 1000);
		
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
					display : '入库日期',
					name : 'in_date',
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
				url : '../../queryAssCardTable.do?isCheck=false&ass_nature=01&use_state=1,2,3,4,5&in_date='
					+ $("#create_date").val(),
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
										var red = "";
										if(rowdata.change_price < 0){
											red = "&nbsp;&nbsp;<font color='red'>减</font>"
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
												+ ""+red+"</a>";
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
								},  {
									display : '变动前原值',
									name : 'old_price',
									align : 'right',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.old_price, '${ass_05006}', 1);
									}
								}, {
									display : '变动原值',
									name : 'change_price',
									width : '120',
									align : 'right',
									render : function(item) {
										return formatNumber(
												item.change_price, '${ass_05006}', 1);
									}
									
								}, {
									display : '变动后原值',
									name : 'price',
									align : 'right',
									width : '120',
									render : function(item) {
										return formatNumber(
												item.price, '${ass_05006}', 1);
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
							url:'queryAssChangeDetailHouse.do',
							usePager : false,
							width : '100%',
							height : '100%',
							checkbox : true,
							enabledEdit : true,
							alternatingRow : true,
							onBeforeEdit : f_onBeforeEdit,
							onAfterEdit : f_onAfterEdit,
							onBeforeSubmitEdit : f_onBeforeSubmitEdit,
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
								}, { text: '模板设置', id:'printSetDetail', click: printSetDetail, icon:'settings' },
		        				 { text: '批量打印（<u>P</u>）', id:'printDetail', click: printDetail, icon:'print' }
]
							},
							onCheckRow : function(checked, data, rowid, rowdata) {
								var ass_card_no = data.ass_card_no;
								if(ass_card_no == null || ass_card_no == ""){
									ass_card_no = "0"
								}
								var change_no = data.change_no;
								if(change_no == null || change_no == ""){
									change_no = "0"
								}
								querySource(change_no,ass_card_no);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		sourcegrid = $("#sourcegrid").ligerGrid({
			columns : [{
				display : '卡片编码',
				name : 'ass_card_no',
				align : 'left',
				width : '120'
			},{
				display : '资金来源',
				name : 'source_id',
				textField : 'source_name',
				editor : {
					type : 'select',
					valueField : 'id',
					textField : 'text',
					url : '../../querySourceDict.do?isCheck=false',
					keySupport : true,
					autocomplete : true,
				},
				width : '120'
			},  {
				display : '变动前原值',
				name : 'old_price',
				align : 'right',
				width : '120',
				render : function(item) {
					return formatNumber(
							item.old_price, '${ass_05006}', 1);
				}
			}, {
				display : '变动原值',
				name : 'change_price',
				width : '120',
				editor : {
					type : 'float',
					precision : 2
				},
				align : 'right',
				render : function(item) {
					return formatNumber(
							item.change_price, '${ass_05006}', 1);
				},
				
			}, {
				display : '变动后原值',
				name : 'price',
				align : 'right',
				width : '120',
				render : function(item) {
					return formatNumber(
							item.price, '${ass_05006}', 1);
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
			url:'queryAssChangeSourceHouse.do',
			usePager : false,
			width : '100%',
			height : '100%',
			checkbox : true,
			enabledEdit : true,
			alternatingRow : true,
			onBeforeEdit : f_onBeforeSourceEdit,
			onAfterEdit : f_onAfterSourceEdit,
			onBeforeSubmitEdit : f_onBeforeSubmitSourceEdit,
			isScroll : true,
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			isAddRow : true,
			selectRowButtonOnly : true,//heightDiff: -10,
			toolbar : {
				items : [ {
					text : '保存',
					id : 'saveSource',
					click : saveSource,
					icon : 'save'
				}, {
					line : true
				}, {
					text : '删除',
					id : 'removeSource',
					click : removeSource,
					icon : 'delete'
				} ]
			}
		});

		sourcegridManager = $("#sourcegrid").ligerGetGridManager();
		
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
	
	function saveSource(){
		sourcegridManager.endEdit();
		var data = gridManager.getCheckedRows();
		var sourceData = sourcegridManager.getData();
		var selected = gridManager.getSelected();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个卡片操作');
			return false;
		} else {
			var ass_card_no = "0";
			var change_no = "0";
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选卡片没有保存');
					flag = false;
				}
				ass_card_no = this.ass_card_no;
				change_no = this.change_no;
			});
			if (flag) {
				if(validateSourceGrid()){
					ajaxJsonObjectByUrl(
							"saveAssChangeSourceHouse.do",
							{
								ass_card_no : ass_card_no,
								change_no : change_no,
								ParamVo : JSON.stringify(sourceData)
							},
							function(responseData) {
								if (responseData.state == "true") {
									querySource(change_no,ass_card_no);
									gridManager.updateRow(selected,{
										change_price: responseData.change_price,
										price:responseData.price
					               });
								}
							});
				}
			}
		}
	}
	
	function removeSource(){
		var data = gridManager.getCheckedRows();
		var selected = gridManager.getSelected();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个卡片操作');
			return false;
		} else {
			var ass_card_no = "0";
			var change_no = "0";
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选卡片没有保存');
					flag = false;
				}
				ass_card_no = this.ass_card_no;
				change_no = this.change_no;
			});
			
			if (flag) {
				var sourceData = sourcegridManager.getCheckedRows();
				if (sourceData.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(sourceData).each(
							function() {
								if (isnull(this.group_id)) {
									sourcegridManager.deleteSelectedRow();
								} else {
									ParamVo
											.push(this.group_id + "@" + this.hos_id
													+ "@" + this.copy_code + "@"
													+ this.change_no + "@" + this.ass_card_no + "@" + this.source_id);
								}
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
													"deleteAssChangeSourceHouse.do",
													{
														ParamVo : ParamVo.toString()
													},
													function(responseData) {
														if (responseData.state == "true") {
															querySource(change_no,ass_card_no);
															gridManager.updateRow(selected,{
																change_price: responseData.change_price,
																price:responseData.price
											               });
														}
													});
										}
									});
				}
			}
		}
		
	}
	
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	
	var rowindexSource_id = "";
	var columnSource_name = "";
	function f_onBeforeSourceEdit(e) {
		rowindexSource_id = e.rowindex;
		clicked = 0;
		columnSource_name = e.column.name;
	}
	
	
	//选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (column_name == "ass_card_no") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				grid.updateRow(rowindex_id, {
					ass_card_no : data.ass_card_no,
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					old_price : data.price
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
	
	function f_onBeforeSubmitSourceEdit(e) {
		return true;
	}
	
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
	
		grid.updateTotalSummary();
	}
	
	function f_onAfterSourceEdit(e) {
		var old_price = e.record.old_price == null || e.record.old_price == ""?0:e.record.old_price;
		var change_price = e.record.change_price == null || e.record.change_price == ""?0:e.record.change_price;
		sourcegridManager.updateCell('price', old_price + change_price, e.record);
				
		sourcegrid.updateTotalSummary();
		grid.updateTotalSummary();
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

				if (v.ass_card_no == '@' || isnull(v.ass_card_no)) {
					msg += "[卡片编码]、";
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
	
	
	function validateSourceGrid() {
		var data = sourcegridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {
			if (data[i].source_id) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('资金来源不能为空');
			return false;
		}
		
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.source_id)) {
				var key = v.source_id;
				var value = "第" + (i + 1) + "行";

				if (v.source_id == '@' || isnull(v.source_id)) {
					msg += "[资金来源]、";
				}
				/* if(v.old_price > 0){
					if(v.change_price > v.old_price){
						msg += "[变动原值不能大于原有原值]、";
					}
				}else{
					if(v.change_price < 0){
						msg += "[变动原值不能小于0]、";
					}
				} */
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
									+ this.change_no + "@"
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
											"deleteAssChangeDetailHouse.do",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													query();
													querySource("0","0");
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
		hotkeys('P', printDetail);

	}
	//打印模板设置
	function printSetDetail(){
		  
		var useId=0;//统一打印
		if('${ass_05061}'==1){
			//按用户打印
			useId='${user_id }';
		} 
		officeFormTemplate({template_code:"05061",use_id:useId});
	}
	
	//打印
	function printDetail(){
		var useId=0;//统一打印
		if('${ass_05061}'==1){
			//按用户打印
			useId='${user_id }';
		}
		//if($("#create_date_b").val())
			var change_no = "'"+ $("#change_no").val()+"'"
			var para={
				class_name:"com.chd.hrp.ass.serviceImpl.change.AssChangeHouseServiceImpl",
				method_name:"printAssChangeHouseData",
				change_no: change_no,
				template_code:'05061',
				isPrintCount:false,//更新打印次数
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId,
			}; 
			ajaxJsonObjectByUrl("queryAssChangeHouseStates.do?isCheck=false",{change_no: change_no,state:2},function(responseData){
				if (responseData.state == "true") {
					officeFormPrint(para);
				}
			});
		 
	}
	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function loadDict() {
		//字典下拉框
    	
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false","id", "text",true,true,null,false,null,"300");
		
		liger.get("ven_id").setValue("${ven_id}@${ven_no}");
		liger.get("ven_id").setText("${ven_code} ${ven_name}");
		
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>变动单号：</td>
			<td align="left" class="l-table-edit-td"><input name="change_no" value="${change_no }"
				type="text" id="change_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate" value="${create_date }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',onpicked:changeCreateDate})" /></td>
			<td align="left"></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			<td align="left"  class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" 
				 /></td>
			<td align="left"></td>	
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note">${note  }</textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="layout1">
		<div position="center" title="明细">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="资金来源">
			<div>
				<div id="sourcegrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
