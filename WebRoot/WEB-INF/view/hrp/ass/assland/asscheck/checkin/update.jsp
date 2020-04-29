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
	var editor;
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
		if ('${state}' != 0) {//置黑不能点击
			toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('saveDetail');
			toobarmanage.setDisabled('save');
			toobarmanage.setDisabled('delete');
			toobarmanageItem =cardgridManager.toolbarManager;
			toobarmanageItem.setDisabled('buildCard');
			toobarmanageItem.setDisabled('delete');
		}
		
	});

	function save() {
		gridManager.endEdit();
		if ($("#create_date").val() == "") {
			$.ligerDialog.error('制单日期不能为空');
			return;
		}
		if (liger.get("store_id").getValue() == "") {
			$.ligerDialog.error('仓库不能为空');
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
			ass_in_no : $("#ass_in_no").val() == "" ? '0' : $("#ass_in_no").val(),
			store_id : liger.get("store_id").getValue().split("@")[0],
			store_no : liger.get("store_id").getValue().split("@")[1],
			dept_id : liger.get("dept_id").getValue().split("@")[0],
			dept_no : liger.get("dept_id").getValue().split("@")[1],
			proj_id : liger.get("proj_id").getValue().split("@")[0],
			proj_no : liger.get("proj_id").getValue().split("@")[1],
			bus_type_code : '05',
			note : $("#note").val(),
			ass_purpose : liger.get("ass_purpose").getValue(),
			create_date : $("#create_date").val(),
			ParamVo : JSON.stringify(data)
		};
		if (validateGrid()) {
			ajaxJsonObjectByUrl("saveAssChkInInassets.do", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					$("#ass_in_no").val(responseData.ass_in_no);
					$("#in_money").val(responseData.in_money)
					query();
					queryCard("0","0@0");
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
			name : 'ass_in_no',
			value : $("#ass_in_no").val() == "" ? "0" : $("#ass_in_no").val()
		});
		grid.loadData(grid.where);
		is_addRow();
	}
	function queryCard(ass_in_no,ass_id) {
		cardgrid.options.parms = [];
		cardgrid.options.newPage = 1;
		cardgrid.options.parms.push({
			name : 'ass_in_no',
			value : ass_in_no
		});
		cardgrid.options.parms.push({
			name : 'ass_id',
			value : ass_id.split("@")[0]
		});
		cardgrid.options.parms.push({
			name : 'ass_no',
			value : ass_id.split("@")[1]
		});
		
		cardgrid.loadData(cardgrid.where);
	}
	
	

	function loadHead() {
		grid = $("#maingrid")
				.ligerGrid(
						{
							columns : [
									{
										display : '资产编码',
										name : 'ass_code',
										align : 'left',
										width : '130'
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
												url : '../../../queryAssNoDictTable.do?isCheck=false&ass_naturs=05',
												pageSize : 30
											},
											keySupport : true,
											alwayShowInDown : false,
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
										},
										width : '130',
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
										display : '规格',
										name : 'ass_spec',
										align : 'left',
										width : '100'
									},
									{
										display : '型号',
										name : 'ass_model',
										align : 'left',
										width : '100'
									},
									{
										display : '品牌',
										name : 'ass_brand',
										align : 'left',
										editor : {
											type : 'text'
										},
										width : '100'
									},
									{
										display : '生产厂家',
										name : 'fac_id',
										textField : 'fac_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../../queryHosFacDict.do?isCheck=false',
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
										width : '190'
									},
									{
										display : '计量单位',
										name : 'unit_code',
										textField:'unit_name',
										editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../../queryHosUnitDict.do?isCheck=false',
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
										width : '100'
									},
									{
										display : '入库数量',
										name : 'in_amount',
										editor : {
											type : 'int'
										},
										align : 'left',
										width : '100'
									},
									{
										display : '资产原值',
										name : 'price',
										editor : {
											type : 'numberbox',
										},
										align : 'right',
										render : function(item) {
											if(item.price){
											return formatNumber(
													item.price, '${ass_05006}', 1);
											}
										},
										width : '100'

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
											if(item.add_depre){
											return formatNumber(
													item.add_depre, '${ass_05005}', 1);
											}
										},
										width : '100'
									},
									{
										display : '累计折旧月份',
										name : 'add_depre_month',
										editor : {
											type : 'int'
										},
										align : 'left',
										width : '100'
									},
									{
										display : '资产净值',
										name : 'cur_money',
										align : 'right',
										render : function(item) {
											if(item.cur_money){
											return formatNumber(
													item.cur_money, '${ass_05006}', 1);
											}
										},
										width : '100'
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
											if(item.fore_money){
											return formatNumber(
													item.fore_money, '${ass_05006}', 1);
											}
										},
										width : '100'
									},
									{
										display : '金额',
										name : 'total_price',
										align : 'right',
										totalSummary : {
											render: function (suminf, column, cell)
					                        {
												if(suminf.sum){
					                            return '<div>' + formatNumber(suminf.sum,'${ass_05005}',1) + '</div>';
												}
					                        }
										},
										render : function(item) {
											item.total_price = formatNumber(
													item.price
															* item.in_amount,
															'${ass_05005}', 1);
											return formatNumber(
													item.total_price, '${ass_05005}', 1);
										},
										width : '100'
									}, {
										display : '备注',
										name : 'note',
										editor : {
											type : 'text'
										},
										align : 'left',
										width : '100'
									} ],
							dataAction : 'server',
							dataType : 'server',
							url:'queryAssChkInDetailInassets.do',
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
								}, { text: '模板设置', id:'printSetDetail', click: printSetDetail, icon:'printSetDetail' },
		        				 { text: '批量打印（<u>P</u>）', id:'printDetail', click: printDetail, icon:'print' }
]
							},
							onCheckRow : function(checked, data, rowid, rowdata) {
								var ass_id = data.ass_id;
								if(ass_id == null || ass_id == ""){
									ass_id = "0@0"
								}
								var ass_in_no = data.ass_in_no;
								if(ass_in_no == null || ass_in_no == ""){
									ass_in_no = "0"
								}
								queryCard(ass_in_no,ass_id);
							}
						});

		gridManager = $("#maingrid").ligerGetGridManager();
		is_addRow();
		
		cardgrid = $("#cardgrid")
		.ligerGrid(
				{
					columns : [
							{
								display : '卡片编码',
								name : 'ass_card_no',
								align : 'left',
								render : function(rowdata, rowindex,
										value) {
									return "<a href=javascript:openCardUpdate('"+rowdata.group_id + "|" + rowdata.hos_id
									+ "|" + rowdata.copy_code + "|"
									+ rowdata.ass_card_no  +"')>"+rowdata.ass_card_no+"</a>";
								}
							},
							{
								display : '状态',
								name : 'use_state_name',
								align : 'left'
							}
							 ],
					dataAction : 'server',
					dataType : 'server',
					url:'queryAssChkInCardInassets.do?isCheck=false',
					usePager : false,
					width : '100%',
					height : '100%',
					checkbox : true,
					isScroll : true,
					rownumbers : true,
					delayLoad : true,//初始化明细数据
					selectRowButtonOnly : true,//heightDiff: -10,
					toolbar : {
						items : [ {
							text : '生成',
							id : 'buildCard',
							click : buildCard,
							icon : 'extend'
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : removeCard,
							icon : 'delete'
						}/* , {
							line : true
						}, {
							text : '条码打印',
							id : 'printBarcode',
							click : printBarcode,
							icon : 'print'
						} */]
					}
				});
		cardgridManager = $("#cardgrid").ligerGetGridManager();
		
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
	function buildCard(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_id = "0@0";
			var ass_in_no = "0";
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				ass_in_no = this.ass_in_no;
			});
			if(flag){
				
				ajaxJsonObjectByUrl(
						"initAssChkInCardInassets.do?isCheck=false",
						{
							ass_in_no:ass_in_no,
							ass_id:ass_id.split("@")[0],
							ass_no:ass_id.split("@")[1],
						},
						function(responseData) {
							if (responseData.state == "true") {
								queryCard(ass_in_no,ass_id);
							}
						});
				
			}
			
		}
	}
	
	function removeCard(){
		
		
		var data = gridManager.getCheckedRows();
		if (data.length == 0 || data.length > 1) {
			$.ligerDialog.warn('选择单个资产操作');
			return false;
		} else {
			var ass_id = "0@0";
			var ass_in_no = "0";
			var flag = true;
			$.each(data, function() {
				if (isnull(this.group_id)) {
					$.ligerDialog.warn('所选资产没有保存');
					flag = false;
				}
				ass_id = this.ass_id;
				ass_in_no = this.ass_in_no;
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
													+ this.ass_card_no + "@" + ass_in_no);
							});
					$.ligerDialog
							.confirm(
									'确定删除?',
									function(yes) {
										if (yes) {
											ajaxJsonObjectByUrl(
													"deleteAssChkInCardInassets.do?isCheck=false",
													{
														ParamVo : ParamVo.toString()
													},
													function(responseData) {
														if (responseData.state == "true") {
															queryCard(ass_in_no,ass_id);
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
		if (column_name == "ass_id") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(rowindex_id, {
					ass_code : data.ass_code,
					ass_name : data.ass_name,
					ass_model : data.ass_model,
					ass_spec : data.ass_spec,
					fac_id : data.fac_id,
					fac_name : data.fac_name,
					unit_code:data.ass_unit, 
					unit_name:data.ass_unit_name,
					ass_brand:data.ass_brand
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
		var in_amount = e.record.in_amount == null || e.record.in_amount == ""?0:e.record.in_amount;
		gridManager.updateCell('cur_money', price
				- add_depre - fore_money, e.record);
		gridManager.updateCell('total_price', price
				* in_amount, e.record);
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
			if (!isnull(v.ass_code)) {
				var key = v.ass_code;
				var value = "第" + (i + 1) + "行";

				if (isnull(v.in_amount)) {
					msg += "[入库数量]、";
				}
				if (isnull(v.price)) {
					msg += "[资产原值]";
				}
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
		var price=0;
		for(var i=0;i<data.length;i++){
            if(data[i].price!=undefined){
               price+=data[i].price;

             }
           }
           if(price<0){
              $.ligerDialog.warn("入库单价不能为负数");
               return false;
             }
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
											+ this.ass_in_no + "@"
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
											"deleteAssChkInDetailInassets.do",
											{
												ParamVo : ParamVo.toString()
											},
											function(responseData) {
												if (responseData.state == "true") {
													$("#in_money").val(responseData.in_money);
													query();
													queryCard("0","0@0");
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
		if('${ass_05071}'==1){
			//按用户打印
			useId='${user_id }';
		} 
		officeFormTemplate({template_code:"05071",use_id:useId});
	}
	
	//打印
	function printDetail(){
		var useId=0;//统一打印
		if('${ass_05071}'==1){
			//按用户打印
			useId='${user_id }';
		}
		//if($("#create_date_b").val())
			var ass_in_no ="'"+this.ass_in_no+"'"
			var para={
				class_name:"com.chd.hrp.ass.serviceImpl.change.AssChangeLandServiceImpl",
				method_name:"printAssChangeLandData",
				ass_in_no: ass_in_no,
				template_code:'05071',
				isPrintCount:false,//更新打印次数
				isPreview:true,//预览窗口，传绝对路径
				use_id:useId,
			}; 
			ajaxJsonObjectByUrl("queryAssChkInLandStates.do?isCheck=false",{ass_in_no: ass_in_no,,state:2},function(responseData){
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
		autocomplete("#store_id", "../../../queryHosStoreDict.do?naturs_code=06&isCheck=false",
				"id", "text", true, true, null, false, "${store_id}@${store_no}", "160");
		autocomplete("#dept_id", "../../../queryDeptDict.do?isCheck=false", "id",
				"text", true, true, null, false,"${dept_id}@${dept_no}","160");
		autocomplete("#ass_purpose", "../../../queryAssUsageDict.do?isCheck=false",
				"id", "text", true, true, null, false,"${ass_purpose}","160");
		autocomplete("#proj_id", "../../../queryAssProjDict.do?isCheck=false","id", "text",true,true,null,false,"${proj_id}@${proj_no}","160");
		autodate("#create_date");
		
		$("#ass_in_no,#in_money,#create_date").ligerTextBox({
			width : 160
		});

		$("#ass_in_no,#in_money").ligerComboBox({
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
					color="red">*</font></b>入库单号：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_in_no"
				type="text" id="ass_in_no" disabled="disabled" value="${ass_in_no }"/></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date" type="text" id="create_date" class="Wdate" value="${create_date }" 
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">项&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="proj_id" type="text" id="proj_id" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font
					color="red">*</font></b>仓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;库：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id"
				type="text" id="store_id" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">领用科室：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" /></td>
			<td align="left"></td>
			
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;途：</td>
			<td align="left" class="l-table-edit-td"><input
				name="ass_purpose" type="text" id="ass_purpose" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">入库金额：</td>
			<td align="left" class="l-table-edit-td"><input name="in_money"
				type="text" id="in_money"  value="${in_money }"/></td>
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
		<div position="center" title="资产列表">
			<div>
				<div id="maingrid"></div>
			</div>
		</div>
		<div position="right" title="卡片列表">
			<div>
				<div id="cardgrid"></div>
			</div>
		</div>
	</div>

</body>
</html>
