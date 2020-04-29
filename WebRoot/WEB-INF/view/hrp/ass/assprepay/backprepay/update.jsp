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
	$(function() {
		$("#layout1").ligerLayout({
			rightWidth : '400',
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
		query();
		loadHotkeys();

	});

	function save() {
		gridManager.endEdit();
		if ($("#pay_date").val() == "") {
			$.ligerDialog.error('付款日期不能为空');
			return;
		}
		if (liger.get("ven_id").getValue() == "") {
			$.ligerDialog.error('供应商不能为空');
			return;
		}
		if (liger.get("operator_emp").getValue() == "") {
			$.ligerDialog.error('经办人不能为空');
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
			ParamVo : JSON.stringify(data),
			operator_emp : liger.get("operator_emp").getValue()
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssBackPrePay.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#pay_no").val(responseData.pay_no);
					//$("#pay_money").val(responseData.pay_money)
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
	}
	
	

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '发票流水号',
										name : 'bill_no',
										align : 'left',
										editor : {
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
														if(item.bill_money){
														return formatNumber(
																item.bill_money, '${ass_05005}', 1);
														}
													}
												}, {
													display : '供应商',
													name : 'ven_name',
													align : 'left'
												}],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_detail,
												url : 'queryAssBackPreBill.do?isCheck=false',
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
										},
										render : function(rowdata, rowindex,
												value) {
											return rowdata.bill_no;
										},
					                    totalSummary:
					                    {
					                        render: function (suminf, column, cell)
					                        {
					                            return '<div>合计</div>';
					                        },
					                        align: 'center'
					                    }

									},
									{
										display : '发票金额',
										name : 'bill_money',
										align : 'right',
										render : function(item) {
											if(item.bill_money){
											return formatNumber(
													item.bill_money, '${ass_05005}', 1);
											}
										},
										totalSummary:{render: function (suminf, column, cell)
							                   {
							                   return '<div>' + formatNumber(suminf.sum,'${ass_05005}',1) + '</div>';
							               },
							               align: 'right'}
									},
									{
										display : '付款金额',
										name : 'pay_money',
										align : 'right',
										render : function(item) {
											if(item.pay_money){
											return formatNumber(
													item.pay_money, '${ass_05005}', 1);
											}
										},
										totalSummary:{render: function (suminf, column, cell)
							                   {
							                   return '<div>' + formatNumber(suminf.sum,'${ass_05005}',1) + '</div>';
							               },
							               align: 'right'}
									},
									{
										display : '备注',
										name : 'note',
										align : 'left',
										editor : {
											type : 'text'
										},
									} ],
							dataAction : 'server',
							dataType : 'server',
							url:'queryAssBackPrePayDetail.do?isCheck=false',
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
								}
								,{ line:true },{
									text : '批量打印（<u>P</u>）',
									id : 'print',
									click : printDate,
									icon : 'print'
								},{ 
									line:true 
									},
								{
										text: '模板设置',
										id:'printSet', 
										click: printSet, 
										icon:'print' }]
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
								queryPayStage(pay_no,bill_no);
								is_addPayStageRow();
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
									url : 'queryAccPayType.do?isCheck=false',
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
									if(item.pay_money){
									return formatNumber(
											item.pay_money,'${ass_05005}', 1);
									}
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
					url:'queryAssBackPrePayStage.do?isCheck=false',
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
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : removePayStage,
							icon : 'delete'
						}]
					}
				});
		cardgridManager = $("#cardgrid").ligerGetGridManager();
	}
	
	//打印模板设置
	  
	  function printSet(){
		  
		  var useId=0;//统一打印
			if('${ass_05085}'==1){
				//按用户打印
				useId='${user_id }';
			}
			
	//POBrowser.openWindow('../../../../PageOffice/printFormSet.do?isCheck=false&template_code=01001&userid=${user_id}', 'fullscreen=yes');
	officeFormTemplate({template_code:"05085",use_id:useId});
	}
	
	//打印
	    function printDate(){
		    	
		    	 var useId=0;//统一打印
		 		if('${ass_05085}'==1){
		 			//按用户打印
		 			useId='${user_id }';
		 		}
		 		/* var data = gridManager.getCheckedRows();
				if (data.length == 0){
					$.ligerDialog.error('请选择行');
				}else{ */
					
					var pay_no ="" ;
					/* var ASS_NO = "";  */
					/* $(data).each(function (){		 */
						/*  if(this.state != 2){
							 ASS_NO = ASS_NO + this.ASS_NO + "<br>";
						} */
						 
						pay_no += "'"+this.pay_no+"',"
						 
							
					/* });
				} */
		    	var para={
		    		
		       
		    			template_code:'05085',
		    			class_name:"com.chd.hrp.ass.serviceImpl.prepay.AssBackPrePayMainServiceImpl",
		    			method_name:"queryAssBackPrePayMainDY",
		    			pay_no:$(pay_no).val(),
		    			paraId :pay_no.substring(0,pay_no.length-1),
		    			isPrintCount:false,//更新打印次数
		    			isPreview:false,//预览窗口，传绝对路径
		    			use_id:useId,
		    			p_num:0
		    	};
		    	officeFormPrint(para);
	}
	 //显示预退发票明细
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
                        { display: '资产名称', name: 'ass_name',width: 150},
                        { display: '发票金额', name: 'bill_money', width: 150,align:'right',type:'float',precision : 2 },
                        { display: '备注', name: 'note',width: 150 }
                        ], dataAction : 'server',
    					dataType : 'server',
   					url:'queryAssBackPreBillDetail.do?isCheck=false&'+parm.toString(),
   					usePager : false,
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
				console.log(data)
				if(validatePayStageGrid()){
				ajaxJsonObjectByUrl(
						"saveAssBackPrePayStage.do?isCheck=false",
						{
							pay_no:pay_no,
							bill_no:bill_no,
							data : JSON.stringify(data)
						},
						function(responseData) {
							if (responseData.state == "true") {
								$("#pay_money").val(responseData.pay_money)
								queryPayStage(pay_no,bill_no);
								query();
								parentFrameUse().query();
							}
						});
				}
			}
			
		}
	}
	
	function removePayStage(){
		
		
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
													"deleteAssBackPrePayStage.do?isCheck=false",
													{
														ParamVo : ParamVo.toString()
													},
													function(responseData) {
														if (responseData.state == "true") {
															$("#pay_money").val(responseData.pay_money);
															queryPayStage(pay_no,bill_no);
															query();
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
					bill_money : data.bill_money,
					//pay_money : data.bill_money,
					note : data.note
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
		/* var price = e.record.price == null || e.record.price == ""?0:e.record.price;
		var add_depre = e.record.add_depre == null || e.record.add_depre == ""?0:e.record.add_depre;
		var fore_money = e.record.fore_money == null || e.record.fore_money == ""?0:e.record.fore_money;
		var in_amount = e.record.in_amount == null || e.record.in_amount == ""?0:e.record.in_amount;
		gridManager.updateCell('cur_money', price
				- add_depre - fore_money, e.record);
		gridManager.updateCell('total_price', price
				* in_amount, e.record);
		grid.updateTotalSummary(); */
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

				if (isnull(v.pay_money)) {
					//msg += "[付款金额]、";
				}
				//if (isnull(v.price)) {
				//	msg += "[资产原值]";
				//}
				//if (isnull(v.unit_code)) {  
				//str += "第"+(i+1)+"[计量单位]、";  
				//is_flag = false;
				//} 
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
		
		$.each(data, function(i, v) {
			if (!isnull(v.pay_code)) {
				var key = v.pay_code;
				var value = "第" + (i + 1) + "行";

				if (isnull(v.pay_money)) {
					msg += "[付款金额]、";
				}
				//if (isnull(v.price)) {
				//	msg += "[资产原值]";
				//}
				//if (isnull(v.unit_code)) {  
				//str += "第"+(i+1)+"[计量单位]、";  
				//is_flag = false;
				//} 
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
											"deleteAssBackPrePayDetail.do?isCheck=false",
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
		autocomplete("#ven_id", "../../queryHosSupDict.do?isCheck=false", "id",
				"text", true, true, null, false, "${ven_id}@${ven_no}", "160");
		autocomplete("#operator_emp","../../queryMatStockEmp.do?isCheck=false","id","text",true,true);   
		autodate("#pay_date");
		
		$("#pay_no,#pay_money,#pay_date").ligerTextBox({
			width : 160
		});

		$("#pay_no,#pay_money").ligerComboBox({
			disabled : true,
			cancelable : false
		});
		
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
				type="text" id="pay_no" disabled="disabled" value="${pay_no }"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>付款日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="pay_date" type="text" id="pay_date" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" value="${pay_date }"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ven_id" type="text" id="ven_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>经&nbsp;&nbsp;办&nbsp;&nbsp;人：</td>
			<td align="left" class="l-table-edit-td">
            	<input  name="operator_emp" type="text" id="operator_emp"/>
            </td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">付款金额：</td>
			<td align="left" class="l-table-edit-td"><input name="pay_money"
				type="text" id="pay_money" value="${pay_money }"/></td>
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
		<div position="center" title="预付发票列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="预付款支付方式">
			<div>
				<div id="cardgrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
