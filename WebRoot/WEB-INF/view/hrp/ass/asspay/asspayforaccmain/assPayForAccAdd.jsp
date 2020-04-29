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
	var cardgrid;
	var cardgridManager = null;
	var userUpdateStr;
	var bill_money_b = 0;
	var editor;
	$(function() {
		$("#layout1").ligerLayout({
			rightWidth : '500',
			//heightDiff: -8,
			//每展开左边树即刷新一次表格，以免结构混乱
			onRightToggle : function() {
				grid._onResize();
				cardgrid._onResize();
			},
			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
			onEndResize : function(a, b) {
				grid._onResize();
				cardgrid._onResize();
			}
		});
		
		loadDict();//加载下拉框
		loadHead(null);
		loadHotkeys();
		$("#ven_id").change(function(){
			var ven_id = liger.get("ven_id").getValue().split("@")[0];
			var ven_no = liger.get("ven_id").getValue().split("@")[1];

			if (ven_no == null || ven_id == null || ven_id == ""
					|| ven_no == "") {
				ven_no = "";
				ven_id = "";
			}
			
			editor.grid.url = '../../queryBillDetailGrid.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no+'&in_date='+$("#create_date").val();
		});
	});
	
	function changeCreateDate() {
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}
		
		editor.grid.url = '../../queryBillDetailGrid.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no+'&in_date='+$("#create_date").val();
	}

	function save() {
		gridManager.endEdit();
		if ($("#pay_date").val() == "") {
			$.ligerDialog.error('付款日期不能为空');
			return;
		}
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		var data = gridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {

			if (data[i].bill_no) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('明细数据不能为空');
			return false;
		}
		var formPara = {
			pay_no : $("#pay_no").val() == "" ? '0' : $("#pay_no").val(),
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			ven_no : liger.get("ven_id").getValue().split("@")[1],
			note : $("#note").val(),
			pay_date : $("#pay_date").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssPayMain.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#pay_no").val(responseData.pay_no);
					query();
					queryPayStage("0","0");
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
			name : 'pay_no',
			value : $("#pay_no").val() == "" ? "0" : $("#pay_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	function queryPayStage(pay_no,bill_no) {
		cardgrid.options.parms = [];
		cardgrid.options.newPage = 1;
		cardgrid.options.parms.push({
			name : 'pay_no',
			value : pay_no
		});
		cardgrid.options.parms.push({
			name : 'bill_no',
			value : bill_no
		});
		
		cardgrid.loadData(cardgrid.where);
		is_addPayStageRow();
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
				valueField : 'bill_no',
				textField : 'bill_no',
				selectBoxWidth : 500,
				selectBoxHeight : 240,
				grid : {
					columns : [ {
						display : '发票流水号',
						name : 'bill_no',
						align : 'left'
					}, {
						display : '发票号',
						name : 'invoice_no',
						align : 'left'
					}, {
						display : '发票金额',
						name : 'bill_money',
						align : 'right',
						render : function(item) {
							return formatNumber(
									item.bill_money, '${ass_05005}', 1);
						}
					}],
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_detail,
					url : '../../queryBillDetailGrid.do?isCheck=false&ven_id='+ven_id+'&ven_no='+ven_no+'&in_date='+$("#create_date").val(),
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
										display : '发票流水号',
										name : 'bill_no',
										align : 'left',
										width : '150',
										editor : editor

									},
									{
										display : '发票号',
										name : 'invoice_no',
										align : 'left',width: 150
									},
									{
										display : '发票金额',
										name : 'bill_money_b',
										align : 'right',
										width: 150,
										render : function(item) {
											return formatNumber(
													item.bill_money_b, '${ass_05005}', 1);
										}
									},
									
									{
										display : '付款金额',
										name : 'bill_money',
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.bill_money, '${ass_05005}', 1);
										},width: 150
									},
									{
										display : '备注',
										name : 'note',
										align : 'left',
										editor : {
											type : 'text'
										},width: 160
									} ],
							dataAction : 'server',
							dataType : 'server',
							url:'queryAssPayDetail.do',
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
							detail: { onShowDetail: f_showOrder ,reload:true},
							frozen:false,
							toolbar : {
								items : [ {
									text : '保存',
									id : 'save',
									click : save,
									icon : 'save'
								},{
									line : true
								},{
									text : '引入',
									id : 'import',
									click : importPay,
									icon : 'up'
								},{
									line : true
								},{
									text : '批量增加支付方式',
									id : 'add',
									click : add_pay,
									icon : 'add'
								},{
									line : true
								},{
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
								var bill_no = data.bill_no;
								if(bill_no == null || bill_no == ""){
									bill_no = "0"
								}
								var pay_no = data.pay_no;
								if(pay_no == null || pay_no == ""){
									pay_no = "0"
								}
								bill_money_b = data.bill_money_b;
								if(bill_money_b == null || bill_money_b == ""){
									bill_money_b = 0;
								}
								queryPayStage(pay_no,bill_no);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		is_addRow();
		
		cardgrid = $("#cardgrid")
		.ligerGrid(
				{
					columns : [
							{
								display : '支付方式',
								name : 'pay_code',
								textField : 'pay_name',
								align : 'left',
								editor : {
									type : 'select',
									valueField : 'id',
									textField : 'text',
									url : '../../queryAccPayType.do?isCheck=false',
									keySupport : true,
									autocomplete : true,
									onSuccess : function(data) {
										if (initvalue != undefined
												&& initvalue != "") {
											this.setValue(initvalue);
											initvalue = "";
										}
									}
								}
							},
							{
								display : '付款金额',
								name : 'pay_money',
								align : 'right',
								editor : {
									type : 'float',
									precision : 2
								},
								render : function(item) {
									return formatNumber(
											item.pay_money, 2, 1);
								}
							},
							{
								display : '说明',
								name : 'note',
								align : 'left',
								editor : {
									type : 'text'
								}
								
							}
							 ],
					dataAction : 'server',
					dataType : 'server',
					url:'queryAssAccPayStage.do',
					usePager : false,
					width : '100%',
					height : '100%',
					checkbox : true,
					isScroll : true,
					enabledEdit : true,
					rownumbers : true,
					delayLoad : true,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '保存',
							id : 'savePayStage',
							click : savePayStage,
							icon : 'save'
						},{
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : removePayStage,
							icon : 'delete'
						}
						]
					}
				});
		cardgridManager = $("#cardgrid").ligerGetGridManager();
	}
	
	//批量增加支付方式
	function add_pay(){
		//删除空行
		/* var emptyRowIndexArr = [];
		var gridData = grid.getData();
		gridData.forEach(function (item, index) {
			if (!item.bill_no) {
				emptyRowIndexArr.push(index)
			}
		})
		for (var i = emptyRowIndexArr.length - 1; i >= 0; i--) {
			grid.deleteRow(emptyRowIndexArr[i])
		}*/
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0 ) {
			$.ligerDialog.warn('选择数据操作');
			return false;
		}  
			
		
		var flag = true;
		$.each(data, function() {
			if(!this.bill_no){
				return;
			}
			if (isnull(this.group_id )) {
				$.ligerDialog.warn('所选记录没有保存');
				flag = false;
			} 
			});
	
		if(flag){
		$.ligerDialog.open({
			title: '支付方式',
			url: 'assAddPayPage.do?isCheck=false', 
			height: 400,
			width: null, 
			
			buttons: [ { text: '确定', 
						onclick: function (item, dialog) {
							
							saveDialog(item, dialog);
							
							} }, { text: '取消', onclick: 
								function (item, dialog) { 
								dialog.close(); } } ] });
		}
	}
	
	function saveDialog(item, dialog){
		var data = gridManager.getCheckedRows();
		var bill_no = "0";
		var pay_no = "0";
		var fn = dialog.frame.f_select || dialog.frame.window.f_select; 
		var data2 = fn();
		var paraData = [];
		
		$.each(data, function() {
			if(this.bill_no){
				paraData.push(this)
			    pay_no = this.pay_no;
			}
			
		}); 
			ajaxJsonObjectByUrl(
					"saveAssAccPayPage.do?isCheck=false",
					{
						pay_no:pay_no,
						pay_code:data2, 
						ParamVo : JSON.stringify(paraData)
					},
					function(responseData) {
						 if (responseData.state == "true") {
							dialog.close();
							$("#pay_money").val(responseData.pay_money)
							queryPayStage(pay_no,bill_no);
							
							query();
			                
							parentFrameUse().query();
						} 
					});
	}
	//引入
	function importPay(){
		
		if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		var param =  
			"ven_id="+liger.get("ven_id").getValue() +"&"+
			"ven_code="+liger.get("ven_id").getText().split(" ")[0] +"&"+
			"ven_name="+liger.get("ven_id").getText().split(" ")[1] +"&"+
			"pay_date="+liger.get("pay_date").getValue();
		$.ligerDialog.open({
			title: '引入付款发票',
			height: $(window).height(),
			width: $(window).width(),
			url: 'assImportPayPage.do?isCheck=false&'+param,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
}

	
	 //显示预付发票明细
    function f_showOrder(row, detailPanel,callback)
    {
   	 if(row.bill_no ==null){
   		 return;
   	 }
   	 var parm = "group_id=" + row.group_id + "&" + "hos_id=" + row.hos_id + "&"
			+ "copy_code=" +row.copy_code + "&" + "bill_no=" + row.bill_no;
        var grid = document.createElement('div'); 
        $(detailPanel).append(grid);
        $(grid).css('margin',10).ligerGrid({
            columns:
                        [
                        { display: '卡片编码', name: 'ass_card_no',width: 120},
                        { display: '资产名称', name: 'ass_name',width: 120},
                        { display: '资产性质', name: 'naturs_name',width: 120},
                        { display: '规格', name: 'ass_spec',width: 120},
                        { display: '型号', name: 'ass_model',width: 120},
                        { display: '付款期号', name: 'stage_no',width: 120},
                        { display: '摘要', name: 'stage_name',width: 120},
                        { display: '发票金额', name: 'pay_plan_money', width: 120,align:'right',type:'float',precision : 2 }
                        ], dataAction : 'server',
    					dataType : 'server',
   					url:'queryAssBillStageByDetail.do?'+parm.toString(),
   					usePager : true,
   					width : '90%',
   					allowAdjustColWidth : false ,
   					fixedCellHeight:true, 
   					isScroll : true,
   					rownumbers : true,
   					delayLoad : false,//初始化明细数据
   					selectRowButtonOnly : true//heightDiff: -10,
        });  
    }
	
	function savePayStage(){
		cardgridManager.endEdit();
		var selected = gridManager.getSelected();
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单条记录操作');
			return false;
		} else {
			var pay_no = "0";
			var bill_no = "0";
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选记录没有保存');
					flag = false;
				}
				pay_no = this.pay_no;
				bill_no = this.bill_no;
			});
			if(flag){
				var data = cardgridManager.getData();
				//console.log(data)
				if(validatePayStageGrid()){
				ajaxJsonObjectByUrl(
						"saveAssAccPayStage.do",
						{
							pay_no:pay_no,
							bill_no:bill_no,
							ParamVo : JSON.stringify(data)
						},
						function(responseData) {
							if (responseData.state == "true") {
								$("#pay_money").val(responseData.pay_money)
								queryPayStage(pay_no,bill_no);
								gridManager.updateRow(selected,{
									bill_money: responseData.bill_money
				                });
								parentFrameUse().query();
							}
						});
				}
			}
			
		}
	}
	
	function removePayStage(){
		var selected = gridManager.getSelected();
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单条记录操作');
			return false;
		} else {
			var pay_no = "0";
			var bill_no = "0";
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选记录没有保存');
					flag = false;
				}
				pay_no = this.pay_no;
				bill_no = this.bill_no;
			});
			
			if (flag) {
				var data = cardgridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(
							function() {
									ParamVo
											.push(this.group_id + "@" + this.hos_id
													+ "@" + this.copy_code + "@"
													+ this.pay_no + "@" + bill_no + "@"
													+ this.pay_code);
							});
					$.ligerDialog
							.confirm(
									'确定删除?',
									function(yes) {
										if (yes) {
											ajaxJsonObjectByUrl(
													"deleteAssAccPayStage.do",
													{
														ParamVo : ParamVo.toString()
													},
													function(responseData) {
														if (responseData.state == "true") {
															$("#pay_money").val(responseData.pay_money);
															queryPayStage(pay_no,bill_no);
															gridManager.updateRow(selected,{
																bill_money: responseData.bill_money
											                });
															parentFrameUse().query();
														}
													});
										}
									});
				}
			}
		}
		
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
		if (column_name == "bill_no") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					bill_no : data.bill_no,
					ass_name : data.ass_name,
					bill_money_b : data.bill_money,
					invoice_no : data.invoice_no
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
			if (!isnull(v.bill_no)) {
				var key = v.bill_no;
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
		return true;
	}
	function validatePayStageGrid() {
		var data = cardgridManager.getData();
		var msg = "";
		var rows = 0;
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行
		var total_pay_money = 0;
		$.each(data, function(i, v) {
			if (!isnull(v.pay_code)) {
				var key = v.pay_code;
				var value = "第" + (i + 1) + "行";

				if (v.pay_money < 0) {
					msg += "[付款金额不能小于0]、";
				}
				total_pay_money = total_pay_money + v.pay_money;
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
				rows ++;
			}
			
		});
		if(rows == 0){
 			$.ligerDialog.warn("请添加支付方式明细数据！");  
			return false;  
 		}
		if(total_pay_money != bill_money_b){
			$.ligerDialog.warn("付款金额和发票金额必须相等!");
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
											+ this.pay_no + "@"
											+ this.bill_no);
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
											"deleteAssPayDetail.do",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													$("#pay_money").val(responseData.pay_money);
													query();
													queryPayStage("0","0");
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
	
	function is_addPayStageRow(){
		setTimeout(function() { //当数据为空时 默认新增一行
			cardgrid.addRow();
		}, 1000);
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#ven_id", "../../queryHosSupDictNo.do?isCheck=false", "id",
				"text", true, true, null, false, null, "160");
		autodate("#pay_date");
		
		$("#pay_no,#pay_money,#pay_date").ligerTextBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#pay_money").val(0);
		$("#pay_no,#pay_money").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		autodate("#create_date");
		autodate("#pay_date");
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>付款单号：</td>
			<td align="left" class="l-table-edit-td"><input name="pay_no"
				type="text" id="pay_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>付款日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="pay_date" type="text" id="pay_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ven_id" type="text" id="ven_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',onpicked:changeCreateDate})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款金额：</td>
			<td align="left" class="l-table-edit-td"><input name="pay_money" disabled="disabled"
				type="text" id="pay_money" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note"></textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="layout1">
		<div position="center" title="发票列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="支付方式">
			<div>
				<div id="cardgrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
