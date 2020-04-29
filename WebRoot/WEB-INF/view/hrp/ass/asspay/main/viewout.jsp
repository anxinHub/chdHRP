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
	var stagegrid;
	var stagegridManager = null;
	var userUpdateStr;
	var editor;
	var stageEditor;
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
		loadHotkeys();

		$("#bill_no").ligerTextBox({
			width : 160,
			disabled : true,
			cancelable : false
		});
		$("#bill_date").ligerTextBox({
			width : 160
		});
		$("#create_date").ligerTextBox({
			width : 160
		});
		$("#bill_money").ligerTextBox({
			disabled : true,
			cancelable : false,
			width : 160
		});
		$("#invoice_no").ligerTextBox({
			width : 160
		});
		$("#pact_code").ligerTextBox({
			width : 160
		});
		$("#ven_id").ligerTextBox({
			width : 160
		});
		query();
		$("#pact_code").change(function(){
			var pact_code = liger.get("pact_code").getValue();

			if (pact_code == null || pact_code == "") {
				pact_code = "";
			}
			
			var ven_id = liger.get("ven_id").getValue().split("@")[0];
			var ven_no = liger.get("ven_id").getValue().split("@")[1];

			if (ven_no == null || ven_id == null || ven_id == ""
					|| ven_no == "") {
				ven_no = "";
				ven_id = "";
			}
			
			editor.grid.url = '../../queryAssCardTable.do?isCheck=false&is_payment=y&use_state=1,2,3,4,5&in_date='
				+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no+'&pact_code='+pact_code;
		});
		
		$("#ven_id").change(function(){
			var pact_code = liger.get("pact_code").getValue();

			if (pact_code == null || pact_code == "") {
				pact_code = "";
			}
			
			var ven_id = liger.get("ven_id").getValue().split("@")[0];
			var ven_no = liger.get("ven_id").getValue().split("@")[1];

			if (ven_no == null || ven_id == null || ven_id == ""
					|| ven_no == "") {
				ven_no = "";
				ven_id = "";
			}
			
			editor.grid.url = '../../queryAssCardTable.do?isCheck=false&is_payment=y&use_state=1,2,3,4,5&in_date='
				+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no+'&pact_code='+pact_code;
		});
	});
	
	function changeCreateDate() {
		var pact_code = liger.get("pact_code").getValue();

		if (pact_code == null || pact_code == "") {
			pact_code = "";
		}
		
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}
		
		editor.grid.url = '../../queryAssCardTable.do?isCheck=false&is_payment=y&use_state=1,2,3,4,5&in_date='
			+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no+'&pact_code='+pact_code;
	}

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if ($("#bill_date").val() == "") {
			$.ligerDialog.error('开票日期不能为空');
			return;
		}
		if ($("#invoice_no").val() == "") {
			$.ligerDialog.error('发票号不能为空');
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
			bill_no : $("#bill_no").val() == "" ? '0' : $("#bill_no").val(),
			ven_id : liger.get("ven_id").getValue().split("@")[0],
			ven_no : liger.get("ven_id").getValue().split("@")[1],
			pact_code : liger.get("pact_code").getValue(),
			note : $("#note").val(),
			bill_date : $("#bill_date").val(),
			invoice_no : $("#invoice_no").val(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssBillMain.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#bill_no").val(responseData.bill_no);
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
			name : 'bill_no',
			value : $("#bill_no").val() == "" ? "0" : $("#bill_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	function queryStage(bill_no,ass_card_no,naturs_code) {
		stagegrid.options.parms = [];
		stagegrid.options.newPage = 1;
		stagegrid.options.parms.push({
			name : 'bill_no',
			value : bill_no
		});
		stagegrid.options.parms.push({
			name : 'ass_card_no',
			value : ass_card_no
		});
		stagegrid.options.parms.push({
			name : 'naturs_code',
			value : naturs_code
		});
		
		stagegrid.loadData(stagegrid.where);
		
		setTimeout(function() { //当数据为空时 默认新增一行
			stagegrid.addRow();
		}, 1000);
	}
	
	function cardSelect(){
		var data = gridManager.getData();
		var ass_card_nos = [];
		$.each(data, function(i, v) {
			if (!isnull(v.ass_card_no)) {
				ass_card_nos.push("'"+v.ass_card_no+"'");
			}
		});
		var pact_code = liger.get("pact_code").getValue();

		if (pact_code == null || pact_code == "") {
			pact_code = "";
		}
		
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];

		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}
		
		editor.grid.url = '../../queryAssCardTable.do?isCheck=false&is_payment=y&use_state=1,2,3,4,5&in_date='
			+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no+'&pact_code='+pact_code+'&ass_card_not_exists='+ass_card_nos.toString();
	}

	function loadHead() {
		
		var ven_id = liger.get("ven_id").getValue().split("@")[0];
		var ven_no = liger.get("ven_id").getValue().split("@")[1];
		var pact_code = liger.get("pact_code").getValue();
		if (ven_no == null || ven_id == null || ven_id == ""
				|| ven_no == "") {
			ven_no = "";
			ven_id = "";
		}
		if(pact_code == null || pact_code == ""){
			pact_code = "";
		}
		
		editor = {
				type : 'select',
				valueField : 'ass_card_no',
				textField : 'ass_card_no',
				selectBoxWidth : 500,
				selectBoxHeight : 240,
				grid : {
					columns : [ {
						display : '资产性质',
						name : 'ass_nature',
						align : 'left'
					}, {
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
					}  ],
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_detail,
					url : '../../queryAssCardTable.do?isCheck=false&is_payment=y&use_state=1,2,3,4,5&in_date='
						+ $("#create_date").val()+'&ven_id='+ven_id+'&ven_no='+ven_no+'&pact_code='+pact_code,
					pageSize : 30
				},
				alwayShowInDown : true,
				keySupport : true,
				autocomplete : true,
				onSuccess : function() {
					this.parent("tr").next(
							".l-grid-row").find(
							"td:first").focus();
				},
				onBeforeOpen: cardSelect
			};
		
		stageEditor = {
				type : 'select',
				valueField : 'stage_no',
				textField : 'stage_no',
				selectBoxWidth : 500,
				selectBoxHeight : 240,
				grid : {
					columns : [ {
						display : '期号',
						name : 'stage_no',
						align : 'left'
					}, {
						display : '摘要',
						name : 'stage_name',
						align : 'left'
					}, {
						display : '比例',
						name : 'pay_plan_percent',
						align : 'left'
					}, {
						display : '付款金额',
						name : 'pay_plan_money',
						align : 'left',
						render: function(item)
			            {
			                    return formatNumber(item.pay_plan_money,'${ass_05005}',1);
			            }
					}, {
						display : '已付金额',
						name : 'pay_money',
						align : 'left',
						render: function(item)
			            {
			                    return formatNumber(item.pay_money,'${ass_05005}',1);
			            }
					}, {
						display : '未付金额',
						name : 'unpay_money',
						align : 'left',
						render: function(item)
			            {
			                    return formatNumber(item.unpay_money,'${ass_05005}',1);
			            }
					} , {
						display : '预计付款时间',
						name : 'pay_plan_date',
						align : 'left'
					} ],
					switchPageSizeApplyComboBox : false,
					onSelectRow : f_onSelectRow_stage,
					url : '../../queryPayStageGrid.do?isCheck=false&is_payment=y',
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
			};
		
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									 {  name: 'naturs_code', hide:true,width:130
							 		},
							 		{
										display : '卡片号',
										name : 'ass_card_no',
										align : 'left',
										width : '120',
										render : function(rowdata, rowindex,
												value) {
											if(rowdata.ass_card_no == "" || rowdata.ass_card_no == null){
												return "";
											}
											return "<a href=javascript:openCardUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
											+ "|" + rowdata.copy_code + "|"
											+ rowdata.ass_card_no  + "|"
											+ rowdata.naturs_code+"')>"+rowdata.ass_card_no+"</a>";
										},editor :editor,width:130
									},
							 		{
										display : '资产编码',
										name : 'ass_code',
										align : 'left',width:130
									},
									{
										display : '资产名称',
										name : 'ass_name',
										align : 'left',width:130
									},
									{
										display : '开票金额',
										name : 'bill_money',
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.bill_money, '${ass_05005}', 1);
										},width:130
									},
									{
										display : '卡片原值',
										name : 'price',
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.price, '${ass_05006}', 1);
										},width:130
									}, 
									{
										display : '已开票金额',
										name : 'nc_d',
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.nc_d, '${ass_05005}', 1);
										},width:130
									},  
									{
										display : '未开发票金额',
										name : 'nc_c',
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.nc_c, '${ass_05005}', 1);
										},width:130
									},  
									{
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left',width:130
									} ],
							dataAction : 'server',
							dataType : 'server',
							url:'queryAssBillDetail.do',
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
								items : [{
									text : '关闭',
									id : 'close',
									click : this_close,
									icon : 'candle'
								}
								]
							},
							onCheckRow : function(checked, data, rowid, rowdata) {
								var ass_card_no = data.ass_card_no;
								if(ass_card_no == null || ass_card_no == ""){
									ass_card_no = "0"
								}
								var bill_no = data.bill_no;
								if(bill_no == null || bill_no == ""){
									bill_no = "0"
								}
								
								var naturs_code = data.naturs_code;
								if(naturs_code == null || naturs_code == ""){
									naturs_code = "0"
								}
								stageEditor.grid.url='../../queryPayStageGrid.do?isCheck=false&is_payment=y&ass_card_no='+ass_card_no;
								queryStage(bill_no,ass_card_no,naturs_code);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		
		stagegrid = $("#stagegrid")
		.ligerGrid(
				{
					columns : [
							{
								display : '付款期号',
								name : 'stage_no',
								align : 'center',
								textField : 'stage_no',
								valueField : 'stage_no',
								editor : stageEditor,width:170
							},
							{
								display : '摘要',
								name : 'stage_name',
								align : 'left',width:170
							},
							{
								display : '本次付款',
								name : 'pay_plan_money',
								align : 'right',
								render : function(item) {
									return formatNumber(
											item.pay_plan_money, '${ass_05005}', 1);
								},
								editor : {
									type : 'float',
									precision : 2
								},width:170
							},
							{
								display : '付款金额',
								name : 'plan_money',
								hide:true
							},
							{
								display : '已付金额',
								name : 'pay_money',
								hide:true
							},
							{
								display : '未付金额',
								name : 'unpay_money',
								hide:true
							}
							 ],
					dataAction : 'server',
					dataType : 'server',
					url:'queryAssBillStage.do',
					usePager : false,
					width : '100%',
					height : '100%',
					checkbox : true,
					isScroll : true,
					rownumbers : true,
					enabledEdit : true,
					alternatingRow : true,
					delayLoad : true,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
					onBeforeEdit : f_onBeforeEdit_stage
				});
		stagegridManager = $("#stagegrid").ligerGetGridManager();
		
	}
	
	function openCardUpdate(obj) {
		var vo = obj.split("|");
		var parm = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
		+ " copy_code=" + vo[2] + "&" + "ass_card_no=" + vo[3] + "&"
		+ "ass_nature="+vo[4];
		parent.parent.$.ligerDialog.open({
			title: '资产卡片维护',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/ass/asscard/assInCardUpdatePage.do?isCheck=false&'+parm,
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	function importPayStage(){
			
	}
	
	function validateStageGrid() {
		var data = stagegridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {
			if (data[i].stage_no) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('付款不能为空');
			return false;
		}
		
		var msg = "";
		var targetMap = new HashMap();
		var msgMap = new HashMap();
		//删除空行

		$.each(data, function(i, v) {
			if (!isnull(v.stage_no)) {
				var key = v.stage_no + "|" + v.stage_name;
				var value = "第" + (i + 1) + "行";
				if(v.pay_plan_money < 0){
					msg += "[本次付款不能小于0]、";
				}
					if(v.pay_plan_money > v.plan_money){
						msg += "[本次付款不能大于付款金额]、";
					}
					if(v.pay_plan_money > v.unpay_money){
						msg += "[本次付款不能大于未付金额]、";
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
	
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		clicked = 0;
		column_name = e.column.name;
	}
	
	var stagerowindex_id = "";
	var stagecolumn_name = "";
	function f_onBeforeEdit_stage(e) {
		stagerowindex_id = e.rowindex;
		clicked = 0;
		stagecolumn_name = e.column.name;
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
					naturs_code : data.naturs_code,
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_card_no : data.ass_card_no
				});

			}
		}
		return true;
	}
	//选中回充数据
	function f_onSelectRow_stage(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		//alert(JSON.stringify(data)); 
		if (stagecolumn_name == "stage_no") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				stagegrid.updateRow(stagerowindex_id, {
					stage_no : data.stage_no,
					stage_name : data.stage_name,
					ass_name : data.ass_name,
					pay_plan_money : data.unpay_money,
					plan_money : data.pay_plan_money,
					pay_money : data.pay_money,
					unpay_money : data.unpay_money
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
		autocomplete("#pact_code", "../../queryContractMain.do?isCheck=false","id", "text",true,true,'',false,null,"200");
		liger.get("pact_code").setValue("${pact_code}");
		liger.get("pact_code").setText("${contract_no}");
    	
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false","id", "text",true,true,null,false,null,"220");
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
					color="red">*</font></b>发票流水号：</td>
			<td align="left" class="l-table-edit-td"><input name="bill_no" value="${bill_no }"
				type="text" id="bill_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>发票号：</td>
			<td align="left" class="l-table-edit-td"><input value="${invoice_no }"
				name="invoice_no" type="text" id="invoice_no"  /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>开票日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="bill_date" type="text" id="bill_date" class="Wdate" value="${bill_date }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate" value="${create_date }"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd',onpicked:changeCreateDate})" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">合同：</td>
			<td align="left"  class="l-table-edit-td"><input name="pact_code"
				type="text" id="pact_code" 
				 /></td>
			<td align="left"></td>
			<td align="right"  class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
			<td align="left"  class="l-table-edit-td"><input name="ven_id"
				type="text" id="ven_id" 
				 /></td>
			<td align="left"></td>	 	
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">发票金额：</td>
			<td align="left" class="l-table-edit-td"><input name="bill_money" value="${bill_money }"
				type="text" id="bill_money" /></td>
			<td align="left"></td> 
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">摘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" colspan="9"><textarea
					rows="3" cols="70" id="note" name="note">${note }</textarea></td>
			<td align="left"></td>

		</tr>
	</table>
	<div id="layout1">
		<div position="center" title="卡片列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="付款列表">
			<div>
				<div id="stagegrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
