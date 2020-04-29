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
		query();

		$("#check_plan_no").ligerTextBox({
			disabled : true,
			cancelable : false
		});
		$("#check_no").ligerTextBox({
			disabled : true,
			cancelable : false
		});
		$("#store_id").ligerComboBox({
			disabled : true,
			cancelable : false
		});
	});

	function save() {
		gridManager.endEdit();
		var data = gridManager.getData();
		var num = 0;
		for (var i = 0; i < data.length; i++) {

			if (data[i].ass_code) {
				num++;
			}
		}
		if (!num) {
			$.ligerDialog.error('数据不能为空');
			return false;
		}
		var formPara = {
				check_plan_no : $("#check_plan_no").val(),
				check_no : $("#check_no").val(),
				store_id : liger.get("store_id").getValue().split("@")[0],
				store_no : liger.get("store_id").getValue().split("@")[1],
				ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssCheckSpDetailGeneral.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					query();
					parent.query();
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
			name : 'check_plan_no',
			value : '${check_plan_no}'
		});
		grid.options.parms.push({
			name : 'check_no',
			value : '${check_no}'
		});
		grid.options.parms.push({
			name : 'store_id',
			value : '${store_id}'
		});
	  	grid.options.parms.push({
			name : 'ass_card_no',
			value : '${ass_card_no}'
		});
		grid.options.parms.push({
			name : 'store_no',
			value : '${store_no}'
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	
	

	function loadHead() {
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
										editor : {
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
												}],
												switchPageSizeApplyComboBox : false,
												onSelectRow : f_onSelectRow_detail,
												url : '../../../queryAssNoDictTable.do?isCheck=false&ass_naturs=03',
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
											return rowdata.ass_name;
										}

									},
									{
										display : '卡片编码',
										name : 'ass_card_no',
										editor : {
											type : 'text'
										},
										align : 'left'
									},
									{
										display : '数量',
										name : 'acc_amount',
										editor : {
											type : 'int'
										},
										align : 'left'
									},
									{
										display : '资产原值',
										name : 'price',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.price, '${ass_05006}', 1);
										}

									},
									{
										display : '累计折旧',
										name : 'add_depre',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.add_depre, '${ass_05005}', 1);
										}
									},
									{
										display : '累计折旧月份',
										name : 'add_depre_month',
										editor : {
											type : 'int'
										},
										align : 'left'
									},
									{
										display : '资产净值',
										name : 'cur_money',
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.cur_money, '${ass_05006}', 1);
										}
									},
									{
										display : '预留残值',
										name : 'fore_money',
										editor : {
											type : 'float',
											precision : 2
										},
										align : 'right',
										render : function(item) {
											return formatNumber(
													item.fore_money, '${ass_05006}', 1);
										}
									},
									 {
										display : '盘盈原因',
										name : 'p_reason',
										editor : {
											type : 'text'
										},
										align : 'left'
									} ],
							dataAction : 'server',
							dataType : 'server',
							url:'queryAssCheckSpDetailGeneral.do',
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
								},  {
									text : '打印模板设置（<u>M</u>）',
									id : 'printSet',
									click : printSet,
									icon : 'settings'
								}, {
									line : true
								},
							  {
									text : '批量打印（<u>P</u>）',
									id : 'print',
									click : printDate,
									icon : 'print'
								},{
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
		
	}
	
	//打印模板设置 最新版
    function printSet(){
	  
    	var useId=0;//统一打印
		if('${ass_05092}'==1){
			//按用户打印
			useId='${user_id }';
		}else if('${ass_05092}'==2){
			//按仓库打印
			if(liger.get("store_code").getValue()==""){
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId=liger.get("store_code").getValue().split(",")[0];
		}
    	
		officeFormTemplate({template_code:"05092",use_id : useId})
    }
  
  //打印 最新版
    function printDate(){
    	
    	 var useId=0;//统一打印
 		if('${ass_05092}'==1){
 			//按用户打印
 			useId='${user_id }';
 		}else if('${ass_05092}'==2){
 			//按仓库打印
 			if(liger.get("store_code").getValue()==""){
 				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
 				return;
 			}
 			useId=liger.get("store_code").getValue().split(",")[0];
 		}

 		
			
			 var para={
					 
					template_code:'05092',
					class_name:"com.chd.hrp.ass.serviceImpl.check.general.AssCheckSpDetailGeneralServiceImpl", 
					method_name:"queryAssInSpecialByPrintTemlatePrints",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
					check_no:$("#check_no").val()  ,
	    			isPrintCount:false,//更新打印次数
	    			use_id:useId,
	    			p_num:1
	    			//isSetPrint:flag
	    	}; 
			/* ajaxJsonObjectByUrl("queryAssInSpecialState.do?isCheck=false",{check_no:$("#check_no").val(),state:2},function(responseData){
				if (responseData.state == "true") {
					
				}
			}); */
			 officeFormPrint(para);
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
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name
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
		var price = e.record.price == null || e.record.price == ""?0:e.record.price;
		var add_depre = e.record.add_depre == null || e.record.add_depre == ""?0:e.record.add_depre;
		var fore_money = e.record.fore_money == null || e.record.fore_money == ""?0:e.record.fore_money;
		gridManager.updateCell('cur_money', price
				- add_depre - fore_money, e.record);
		grid.updateTotalSummary();
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
											+ this.check_plan_no + "@"
											+ this.check_no + "@"
											+ this.store_id + "@"
											+ this.store_no + "@"
											+ this.ass_id.split("@")[0] + "@"+
											+ this.ass_no);
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
											"deleteAssCheckSpDetailGeneral.do",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													query();
												}
											});
								}
							});
		}
	}

	function is_addRow() {
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 1000);
	}
	function loadDict() {
		autocomplete("#store_id", "../../../queryHosStoreDict.do?naturs_code=03&isCheck=false", "id",
					"text", true, true, null, null,null);
		
		liger.get("store_id").setValue("${store_id}@${store_no}");
		liger.get("store_id").setText("${store_code} ${store_name}");
		
		
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<input type="hidden" name="ass_card_no" value="${ass_card_no}"/>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">任务单号：</td>
			<td align="left" class="l-table-edit-td"><input name="check_plan_no" value="${check_plan_no }"
				type="text" id="check_plan_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">盘点单号：</td>
			<td align="left" class="l-table-edit-td"><input value="${check_no }"
				name="check_no" type="text" id="check_no" disabled="disabled" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">仓库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id"
				type="text" id="store_id" /></td>
			<td align="left"></td>
		</tr>
	</table>
	<div id="maingrid"></div>

</body>
</html>
