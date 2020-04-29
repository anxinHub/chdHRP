<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var show_detail=0;
	var isUseAffiStore = '${p04044 }' == 1 ? true : false;
 
	

	$(function() {
		loadDict()//加载下拉框
		//加载数据
		//loadHead(null);	
		loadHotkeys();
		showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		//query();
	});
	//查询
	function query() {

		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件

		grid.options.parms.push({
			name : 'begin_in_date',
			value : $("#begin_in_date").val()
		});
		grid.options.parms.push({
			name : 'end_in_date',
			value : $("#end_in_date").val()
		});
		grid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue()
		});
		grid.options.parms.push({
			name : 'state',
			value : liger.get("state").getValue() == null ? "" : liger.get(
					"state").getValue()
		});
		grid.options.parms.push({
			name : 'begin_confirm_date',
			value : $("#begin_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'end_confirm_date',
			value : $("#end_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger
					.get("store_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get(
					"sup_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'proj_id',
			value : liger.get("proj_id").getValue()
		});
		// 		grid.options.parms.push({name:'is_dir',value:liger.get("is_dir").getValue()});
		grid.options.parms.push({
			name : 'in_no',
			value : $("#in_no").val()
		});
		grid.options.parms.push({
			name : 'invoice_no',
			value : $("#invoice_no").val()
		});
		grid.options.parms.push({
			name : 'not_invoice',
			value : $("#not_invoice").prop("checked") ? 1 : 0
		});

		grid.options.parms.push({
			name : 'brief',
			value : $("#brief").val()
		});

		grid.options.parms.push({
			name : 'bill_state',
			value : liger.get("bill_state").getValue()
		});

		grid.options.parms.push({
			name : 'is_create_bill',
			value : liger.get("is_create_bill").getValue()
		});
		 grid.options.parms.push({name:'maker',value:liger.get("maker").getValue()});
		 grid.options.parms.push({name:'affirm',value:liger.get("affirm").getValue()});
		  grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		var reg = new RegExp("^\\d+(\\.\\d+)?$");
		var begin_amount_money = $.trim($("#begin_amount_money").val());
		var end_amount_money = $.trim($("#end_amount_money").val());

		if (begin_amount_money == '' && end_amount_money != '') {
			$.ligerDialog.warn('金额开始范围不能为空 ')
			return;
		}

		if (begin_amount_money != '' && end_amount_money == '') {
			$.ligerDialog.warn('金额结束范围不能为空 ')
			return;
		}

		if (begin_amount_money != '' && end_amount_money != '') {
			if (!reg.test(begin_amount_money)) {
				$.ligerDialog.warn('金额开始范围格式错误 ');
				return;
			}

			if (!reg.test(end_amount_money)) {
				$.ligerDialog.warn('金额结束范围格式错误 ');
				return;
			}
		}

		//2017-06-05 台州 增加金额范围查询条件
		grid.options.parms.push({
			name : 'begin_amount_money',
			value : begin_amount_money
		});
		grid.options.parms.push({
			name : 'end_amount_money',
			value : end_amount_money
		});

		
			// 	grid.options.parms.push({name:'inv_code',value:liger.get("inv_code").getText().split(" ")[0]});
			grid.options.parms.push({
				name : 'inv_code',
				value : $("#inv_code").val()
			});
			grid.options.parms.push({
				name : 'inv_model',
				value : $("#inv_model").val()
			});
		
			grid.options.parms.push({
				name : 'batch_no',
				value : $("#batch_no").val()
			});
			grid.options.parms.push({
				name : 'bar_code',
				value : $("#bar_code").val()
			});
		//加载查询条件
		grid.loadData(grid.where);
	}

	function loadHead() {
		if (show_detail == "1") {
			grid = $("#maingrid")
					.ligerGrid(
							{
								columns : [
										{
											display : '入库单号',
											name : 'in_no',
											align : 'left',
											width : '150',
											frozen :true,
											render : function(rowdata,
													rowindex, value) {
												if (value == '合计') {
													return value;
												}
												return '<a href=javascript:update_open("'
														+ rowdata.group_id
														+ ','
														+ rowdata.hos_id
														+ ','
														+ rowdata.copy_code
														+ ','
														+ rowdata.in_id
														+ '")>'
														+ rowdata.in_no
														+ '</a>';

											}
										/* },{ display: '单据来源', name: 'field_desc', align: 'left', width: '60' */
										},
										{
											display : '仓库',
											name : 'store_name',
											align : 'left',
											width : '150'
										/* },{ display: '摘要', name: 'brief', align: 'left', width: '200' */
										/* },{ display: '供应商', name: 'sup_name', align: 'left', width: '200' */
										},
										{
											display : '货单号',
											name : 'delivery_code',
											align : 'left',
											width : '120'
										},
										{
											display : '交易编码',
											name : 'bid_code',
											align : 'left',
											width : '100'
										},
										{
											display : '材料编码',
											name : 'inv_code',
											align : 'left',
											width : '120'
										},
										{
											display : '材料名称',
											name : 'inv_name',
											align : 'left',
											width : '120'
										},
										{
											display : '物资类别',
											name : 'mat_type_name',
											align : 'left',
											width : '120'
										},
										{
											display : '计量单位',
											name : 'unit_name',
											align : 'left',
											width : '60'
										},
										{
											display : '规格型号',
											name : 'inv_model',
											align : 'left',
											width : '120'
										},
										{
											display : '单价',
											name : 'price',
											align : 'right',
											width : '80',
											render : function(rowdata,
													rowindex, value) {
												return formatNumber(
														value,
														'${p04006 }',
														1);
											}
										},
										{
											display : '数量',
											name : 'amount',
											align : 'right',
											width : '80'
										},
										{
											display : '金额',
											name : 'amount_money',
											align : 'right',
											width : '100',
											render : function(rowdata,
													rowindex, value) {
												return formatNumber(
														rowdata.amount_money == null ? 0
																: rowdata.amount_money,
														'${p04005 }',
														1);
											}
										},
										{
											display : '有效日期',
											name : 'inva_date',
											align : 'left',
											width : '80'
										},
										{
											display : '批号',
											name : 'batch_no',
											align : 'left',
											width : '80'
										},
										{
											display : '条形码',
											name : 'bar_code', 
											align : 'left',
											width : '80'
										},
										{ display: '供应商', name: 'sup_name', align: 'left', width: '200'
											
										},
										{
											display : '生产厂商',
											name : 'fac_name',
											align : 'left',
											width : '80'
										},
										{
											display : '生产日期',
											name : 'fac_date',
											align : 'left',
											width : '80'
										},
										
										{
											display : '状态',
											name : 'state_name',
											align : 'left',
											width : '60'
										},
										{
											display : '发票号',
											name : 'bill_no',
											align : 'left',
											width : '80'
										},
									     {
											display : '制单人',
											name : 'maker_name',
											align : 'left',
											width : '60'
										}, {
											display : '编制日期',
											name : 'in_date',
											align : 'left',
											width : '90'
										}, {
											display : '入库日期',
											name : 'confirm_date',
											align : 'left',
											width : '90'
										},  {
											display : '业务类型',
											name : 'bus_type_name',
											align : 'left',
											width : '80'
										} ],
								dataAction : 'server',
								dataType : 'server',
								usePager : true,
								gid: 'detailGrid', 
								url : 'queryMatStorageIn.do?isCheck=true&show_detail=1',
								width : '100%', 
								height : '100%',
								checkbox : false,
								rownumbers : true,
								delayLoad : true,//初始化不加载，默认false
								checkBoxDisplay : isCheckDisplay,
								selectRowButtonOnly : true,//heightDiff: -10,
								onsuccess : function() {

								},
								toolbar : {
									items : [ {
										text : '查询(<u>Q</u>)',
										id : 'search',
										click : query,
										icon : 'search'
									}, {
										line : true
									}, {
										text : '添加(<u>A</u>)',
										id : 'add',
										click : add_open,
										icon : 'add'
									}, {
										line : true
									}, {
										text : '冲账(<u>O</u>)',
										id : 'offset',
										click : offset,
										icon : 'offset'
									}, {
										line : true
									}, {
										text : '删除(<u>D</u>)',
										id : 'delete',
										click : remove,
										icon : 'delete'
									}, {
										line : true
									}, {
										text : '审核(<u>S</u>)',
										id : 'audit',
										click : audit,
										icon : 'audit'
									}, {
										line : true
									}, {
										text : '消审(<u>U</u>)',
										id : 'unAudit',
										click : unAudit,
										icon : 'unaudit'
									}, {
										line : true
									}, {
										text : '入库确认(<u>F</u>)',
										id : 'confirm',
										click : confirmData,
										icon : 'account'
									}, {
										line : true
									}, {
										text : '批量打印',
										id : 'print',
										click : print,
										icon : 'print'
									}, {
										line : true
									}, {
										text : '送货单入库(<u>M</u>)',
										id : 'generateMain',
										click : generateMain,
										icon : 'add'
									},{
										line : true
									}, {
										text : '模板设置',
										id : 'printSet',
										click : printSet,
										icon : 'print',
										hide : true
									}, {
										line : true,
										hide : true
									}, {
										text : '根据订单生成（<u>R</u>）',
										id : 'genByOrder',
										click : genByOrder,
										icon : 'add'
									}, {
										line : true
									/* }, {
										text : '根据送货单生成（<u>I</u>）',
										id : 'generate',
										click : generate,
										icon : 'add'
									} , {
										line : true */
									}, {
										text : '复制(<u>C</u>)',
										id : 'copy',
										click : copy_no,
										icon : 'copy'
									
									} ]
								},
								onDblClickRow : function(rowdata, rowindex,
										value) {
									if (rowdata.in_id == null) {
										$.ligerDialog.warn('请选择数据 ');
										return;
									}
									update_open(rowdata.group_id + ","
											+ rowdata.hos_id + ","
											+ rowdata.copy_code + ","
											+ rowdata.in_id + ","
											+ rowdata.in_no);
								}
							});
		} else {
			grid = $("#maingrid")
					.ligerGrid(
							{
								columns : [
										{
											display : '入库单号',
											name : 'in_no',
											align : 'left',
											width : '150',
											frozen :true, 
											render : function(rowdata,
													rowindex, value) {
												if (value == '合计') {
													return value;
												} else {
													return '<a href=javascript:update_open("'
															+ rowdata.group_id
															+ ','
															+ rowdata.hos_id
															+ ','
															+ rowdata.copy_code
															+ ','
															+ rowdata.in_id
															+ '")>'
															+ rowdata.in_no
															+ '</a>';
												}
											}
										},
										{
											display : '单据来源',
											name : 'field_desc',
											align : 'left',
											width : '60'
										},
										{
											display : '摘要',
											name : 'brief',
											align : 'left',
											width : '200'
										},
										{
											display : '货单号',
											name : 'delivery_code',
											align : 'left',
											width : '120'
										},
										{
											display : '验收员',
											name : 'examiner_name',
											align : 'left',
											width : '120'
										},
										{
											display : '制单人',
											name : 'maker_name',
											align : 'left',
											width : '60'
										},
										{
											display : '金额',
											name : 'amount_money',
											align : 'right',
											width : '100',
											render : function(rowdata,
													rowindex, value) {
												return formatNumber(
														rowdata.amount_money == null ? 0
																: rowdata.amount_money,
														'${p04005 }',
														1);
											}
										},
										
										{
											display : '状态',
											name : 'state_name',
											align : 'left',
											width : '60'
										},
										{
											display : '是否冲账',
											name : 'ra_state',
											align : 'left',
											width : '60',
											render : function(rowdata,
													rowindex, value) {
												if(rowdata.in_no != '合计')
												{
												if (rowdata.ra_state > 0) {
													return '是';
												} else {
													return '否';
												}
												}
											}
										},
										{
											display : '发票号',
											name : 'bill_no',
											align : 'left',
											width : '80'
										},
										{
											display : '发票状态',
											name : 'bill_state_name',
											align : 'left',
											width : '80'
										},
										{
											display : '是否生成发票',
											name : 'is_create_bill',
											align : 'left',
											width : '80',
											render : function(rowdata,
													rowindex, value) {
												if(rowdata.in_no != '合计')
												{
												if (rowdata.is_create_bill > 0) {
													return '是';
												} else {
													return '否';
												}
												}
											}
										}, {
											display : '供应商',
											name : 'sup_name',
											align : 'left',
											width : '200'
										}, {
											display : '采购员',
											name : 'stocker_name',
											align : 'left',
											width : '60'
										}, {
											display : '编制日期',
											name : 'in_date',
											align : 'left',
											width : '90'
										}, { 
											display : '入库日期',
											name : 'confirm_date',
											align : 'left',
											width : '90'
										}, {
											display : '审核人',
											name : 'check_name',
											align : 'left',
											width : '60'
										},{
											display : '确认人',
											name : 'confirmer_name',
											align : 'left',
											width : '60'
										}, 
										 {
											display : '仓库',
											name : 'store_name',
											align : 'left',
											width : '150'
										}, {
											display : '业务类型',
											name : 'bus_type_name',
											align : 'left',
											width : '80'
										} ],
								rowAttrRender:function(rowdata,rowid){
				    	 			if(parseInt(rowdata.ra_state)>0){
				    	 				return "style='background:rgba(255, 0, 0, 0.69)'";
				    	 			}
				     			},
								dataAction : 'server',
								dataType : 'server',
								usePager : true,
								url : 'queryMatStorageIn.do?isCheck=true&show_detail=0',
								width : '100%',
								height : '100%',
								checkbox : true,
								rownumbers : true,
								delayLoad : true,//初始化不加载，默认false
								checkBoxDisplay : isCheckDisplay,
								selectRowButtonOnly : true,//heightDiff: -10,
								onsuccess : function() {

									//is_addRow();
								},
								alternatingRow:false,//是否隔行变色,false 否
								toolbar : {
									items : [ {
										text : '查询(<u>Q</u>)',
										id : 'search',
										click : query,
										icon : 'search'
									}, {
										line : true
									}, {
										text : '添加(<u>A</u>)',
										id : 'add',
										click : add_open,
										icon : 'add'
									}, {
										line : true
									}, {
										text : '冲账(<u>O</u>)',
										id : 'offset',
										click : offset,
										icon : 'offset'
									}, {
										line : true
									}, {
										text : '删除(<u>D</u>)',
										id : 'delete',
										click : remove,
										icon : 'delete'
									}, {
										line : true
									}, {
										text : '审核(<u>S</u>)',
										id : 'audit',
										click : audit,
										icon : 'audit'
									}, {
										line : true
									}, {
										text : '消审(<u>U</u>)',
										id : 'unAudit',
										click : unAudit,
										icon : 'unaudit'
									}, {
										line : true
									}, {
										text : '入库确认(<u>F</u>)',
										id : 'confirm',
										click : confirmData,
										icon : 'account'
									}, {
										line : true
									}, {
										text : '批量打印',
										id : 'print',
										click : print,
										icon : 'print'
									}, {
										line : true
									}, {
										text : '模板设置',
										id : 'printSet',
										click : printSet,
										icon : 'settings',
										hide : true
									}, {
										line : true,
										hide : true
									}, {
										text : '送货单入库(<u>M</u>)',
										id : 'generateMain',
										click : generateMain,
										icon : 'add'
									},{
										line : true
									}, {
										text : '根据订单生成（<u>R</u>）',
										id : 'genByOrder',
										click : genByOrder,
										icon : 'add'
									}, {
										line : true
									/* }, {
										text : '根据送货单生成（<u>I</u>）',
										id : 'generate',
										click : generate,
										icon : 'add'
									}, {
										line : true */
									}, {
										text : '复制(<u>C</u>)',
										id : 'copy',
										click : copy_no,
										icon : 'copy'
									}, {
										line : true
									}, {
										text : '生成发票(<u>G</u>)',
										id : 'create_bill',
										click : create_bill,
										icon : 'edit'
									},]
								},
								onDblClickRow : function(rowdata, rowindex,
										value) {
									if (rowdata.in_id == null) {
										$.ligerDialog.warn('请选择数据 ');
										return;
									}
									update_open(rowdata.group_id + ","
											+ rowdata.hos_id + ","
											+ rowdata.copy_code + ","
											+ rowdata.in_id + ","
											+ rowdata.in_no);
								},
								
							});
		}

		gridManager = $("#maingrid").ligerGetGridManager();

	}

	

	//是否显示复选框
	function isCheckDisplay(rowdata) {
		if (rowdata.in_id == null)
			return false;
		return true;
	}

	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', copy_no);
		hotkeys('O', offset);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		//hotkeys('P', print);
		hotkeys('I', generate);
		hotkeys('M', generateMain);
		hotkeys('R', genByOrder);
		hotkeys('G',create_bill);
	}
	//生成发票
	function create_bill(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
			return;
		} else {
			var ParamVo = [];
			var in_nos = "";
			var billNos = "";
			var billDate = "";
			var stateNos = "";
			$(data).each(function() {
				if(this.state != 3){
					stateNos = stateNos + this.in_no + "<br>";
				}
				
				if (this.bill_no == "" || this.bill_no == null || this.bill_no == 'undefined') {
					billNos = billNos + this.in_no + "<br>";
				}
				
				if(this.bill_date == "" || this.bill_date == null || this.bill_date == 'undefined') {
					billDate = billDate + this.in_no + "<br>";
				}
				
				ParamVo.push(
					this.group_id + "@" + 
					this.hos_id + "@"+ 
					this.copy_code + "@" + 
					this.in_id + "@" +
					this.sup_id + "@" +
					this.bill_date + "@" +
					this.bill_no
				)
				
			});
			
			if(stateNos!=""){
				$.ligerDialog.error("以下单据没有入库确认，生成发票功能不能使用！：<br>"+ stateNos);
				return;
			}
			
			if(billNos!=""){
				$.ligerDialog.error("请先维护以下单据的发票号：<br>"+ billNos);
				return;
			}
			
			if(billDate!=""){
				$.ligerDialog.error("请先维护以下单据的发票日期：<br>"+ billDate);
				return;
			}
			
			$.ligerDialog.confirm('确定要生成发票吗?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("updateBatchMatStorageInInvoice.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}
	function btn_saveColumn() {

		var path = window.location.pathname + "/maingrid";
		var url = '../../../sys/addBatchSysTableStyle.do?isCheck=false';
		saveColHeader({
			grid : grid,
			path : path,
			url : url,
			callback : function(data) {
				$.ligerDialog.success("保存成功");
			}
		});

		return false;
	}

	//打开添加页面
	function add_open() {
		parent.$.ligerDialog.open({
			title : '入库单添加',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/in/addPage.do?isCheck=false',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}

	//根据订单生成
	function genByOrder() {
		parent.$.ligerDialog.open({
			title : '根据订单生成',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/in/matOrderImpPage.do?isCheck=false&',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name
		});
	}

	//按送货单生成
	function generate() {
		//var para = "sup_id=" + '${sup_id},${sup_no}' + "&sup_text="+ '${sup_code} ${sup_name}';
		parent.$.ligerDialog.open({
			title : '根据送货单生成',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/in/deliveryOrderImpPage.do?isCheck=false&',
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : false,
			isResize : true,
			parentframename : window.name
		});
	}

	//按送货单主表生成
	function generateMain() {
		//var para = "sup_id=" + '${sup_id},${sup_no}' + "&sup_text="+ '${sup_code} ${sup_name}';
		parent.$.ligerDialog
				.open({
					title : '送货单入库',
					height : $(window).height(),
					width : $(window).width(),
					url : 'hrp/mat/storage/in/deliveryOrderImpMainPage.do?isCheck=false&',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : false,
					isResize : true,
					parentframename : window.name
				});
	}

	//打开修改页面
	function update_open(obj) {
		var vo = obj.split(",");
		var paras = "group_id=" + vo[0] + "&" + "hos_id=" + vo[1] + "&"
				+ "copy_code=" + vo[2] + "&" + "in_id=" + vo[3] + "&"
				+ "in_no=" + vo[4];
		parent.$.ligerDialog.open({
			title : '入库单修改',
			height : $(window).height(),
			width : $(window).width(),
			url : 'hrp/mat/storage/in/updatePage.do?isCheck=false&'
					+ paras.toString(),
			modal : true,
			showToggle : false,
			showMax : true,
			showMin : true,
			isResize : true,
			parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
		});
	}

	//批量删除
	function remove() {
		var is_delete = "${p04011 }";//是否可删除他人单据
		var user_id = "${sessionScope.user_id}";//当前操作用户

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var in_nos = "";
			$(data).each(
					function() {
						//is_delete：1不能删除他人单据
						if (is_delete == 1) {
							//判断是否本人制单
							if (user_id != this.maker) {
								in_nos = in_nos + this.in_no + "<br>";
							} else {
								//判断单据状态
								if (this.state > 1) {
									in_nos = in_nos + this.in_no + "<br>";
								}
							}
						} else {
							//判断单据状态
							if (this.state > 1) {
								in_nos = in_nos + this.in_no + "<br>";
							}
						}
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.in_id)
					});
			if (in_nos != "") {
				if (is_delete == 1) {
					$.ligerDialog.error("删除失败！<br>以下单据不是未审核状态或为他人制单：<br>"
							+ in_nos);
					return;
				} else {
					$.ligerDialog.error("删除失败！<br>以下单据不是未审核状态：<br>" + in_nos);
					return;
				}
			}
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteMatStorageIn.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	//批量复制
	function copy_no() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var in_nos = "";
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.in_id)
					});
			$.ligerDialog.confirm('确定复制?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("copyMatStorageIn.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	//合并弹出页面冲账
	function offset() {

		var group_id = "";
		var hos_id = "";
		var copy_code = "";
		var store_id = "";
		var ParamVo = [];
		var v_store = [];
		var v_sup = [];
		var flag = true;
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择要冲账的数据');
			return;
		} else {
			var nos = "";
			var nos_btc = "";
			var nos_store = "";
			var nos_dept = "";
			var nos_sup = "";
			//2 采购入库,4 有偿调入,6 无偿调入,8 盘盈入库,10 其它入库,16 自制品入库,18 委托加工入库,22 捐赠入库
			$(data).each(
					function() {
						if (this.state != 3) {
							nos = nos + this.in_no + ",";
						}
						if (v_store.length == 0) {
							v_store.push(this.store_id);
						} else {
							if (v_store.indexOf(this.store_id) < 0) {
								nos_store = nos_store + this.in_no + ",";
							}
						}

						if (v_sup.length == 0) {
							v_sup.push(this.sup_id);
						} else {
							if (v_sup.indexOf(this.sup_id) < 0) {
								nos_sup = nos_sup + this.in_no + ",";
							}
						}

						if (this.bus_type_code != '2'
								&& this.bus_type_code != '4'
								&& this.bus_type_code != '6'
								&& this.bus_type_code != '8'
								&& this.bus_type_code != '10'
								&& this.bus_type_code != '16'
								&& this.bus_type_code != '18'
								&& this.bus_type_code != '22') {
							nos_btc = nos_btc + this.in_no + ",";
						}
						group_id = this.group_id;
						hos_id = this.hos_id;
						copy_code = this.copy_code;
						store_id = this.store_id;
						sup_id = this.sup_id;
						ParamVo.push(this.in_id + "@" + this.in_no)
					});

			if (nos != "") {
				$.ligerDialog.error("冲账失败！" + nos + "单据不是入库确认状态");
				flag = false;
				return;
			}
			if (nos_btc != "") {
				$.ligerDialog.error("冲账失败！" + nos_btc + "单据业务类型不允许冲账");
				flag = false;
				return;
			}
			if (nos_store != "") {
				$.ligerDialog.error("冲账失败！" + nos_store + "仓库不同不允许汇总冲账");
				flag = false;
				return;
			}
			if (nos_sup != "") {
				$.ligerDialog.error("冲账失败！" + nos_dept + "供应商不同不允许汇总冲账");
				flag = false;
				return;
			}

			if (flag) {
				var paras = "group_id=" + group_id + "&" + "hos_id=" + hos_id
						+ "&" + "copy_code=" + copy_code + "&" + "store_id="
						+ store_id + "&" + "sup_id=" + sup_id + "&" + "in_ids="
						+ ParamVo.toString();

				parent.$.ligerDialog
						.open({
							title : '采购入库冲账单',
							height : $(window).height(),
							width : $(window).width(),
							url : 'hrp/mat/storage/in/mergeBalanceMatInMain.do?isCheck=false&'
									+ paras.toString(),
							modal : true,
							showToggle : false,
							showMax : true,
							showMin : false,
							isResize : true,
							parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
						});
			}
		}

		/* var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
		}else{
			var ParamVo =[];
			var in_nos = "";
			$(data).each(function (){		
				if(this.state != 3){
					in_nos = in_nos + this.in_no + "<br>";
				}
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.in_id 
				) 
			});
			if(in_nos != ""){
				$.ligerDialog.error("冲账失败！<br>以下单据不是已确认状态：<br>"+in_nos);
				return;
			}
			$.ligerDialog.confirm('确定冲账?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("offsetMatStorageIn.do",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		} */
	}

	//批量审核
	function audit() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var in_nos = "";
			$(data).each(
					function() {
						if (this.state != 1) {
							in_nos = in_nos + this.in_no + "<br>";
						}
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.in_id)
					});
			if (in_nos != "") {
				$.ligerDialog.error("审核失败！<br>以下单据不是未审核状态：<br>" + in_nos);
				return;
			}
			$.ligerDialog.confirm('确定审核?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("auditMatStorageInBatch.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	//批量消审
	function unAudit() {

		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			var in_nos = "";
			$(data).each(
					function() {
						if (this.state != 2) {
							in_nos = in_nos + this.in_no + "<br>";
						}
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.in_id)
					});
			if (in_nos != "") {
				$.ligerDialog.error("消审失败！<br>以下单据不是已审核状态：<br>" + in_nos);
				return;
			}
			$.ligerDialog.confirm('确定消审?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("unAuditMatStorageInBatch.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
	}

	//批量入库确认
	function confirm() {
		var is_store='${p04045 }';
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var todayDate = new Date();
			var todayYear = todayDate.getFullYear();
			var todayMonth = todayDate.getMonth() + 1;
			var todayDate = todayDate.getDate();
			todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
			todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
			var today = todayYear + '-' + todayMonth + '-' + todayDate;
			var confirmDate;
			if('${p04047 }'==0){
				confirmData(today);
			}else{
				$.ligerDialog.open({
					content: "确认日期:<input id='confirmDate' value=" + today + " style='text-align:center; border: 1px solid blue; height: 18px;' onFocus='WdatePicker({isShowClear:true,readOnly:true,dateFmt:\"yyyy-MM-dd\"})' />",
					width: 300,
					height: 150,
					buttons: [
						{ 
							text: '确定',
							onclick: function (item, dialog) {
								confirmDate = $("#confirmDate").val();
							
								if (confirmDate) {
									dialog.close();
									confirmData(confirmDate)
								}
							}
						},
		                { text: '取消', onclick: function (item, dialog) { dialog.close(); } }
					]
				})
			}
				
		}
	}

	// 
	function confirmData() {
		
			var is_store='${p04045 }';
			var data = gridManager.getCheckedRows();
			
			if (data.length == 0) {
				$.ligerDialog.error('请选择行');
				
				return false;
			} 
			
			/* 1.开始   说明：后期可用于维护用户输入日期操作*/
			var todayDate = new Date();
			var todayYear = todayDate.getFullYear();
			var todayMonth = todayDate.getMonth() + 1;
			var todayDate = todayDate.getDate();
			todayMonth = todayMonth < 10 ? '0' + todayMonth : todayMonth;
			todayDate = todayDate < 10 ? '0' + todayDate : todayDate;
			var confirmDate = todayYear + '-' + todayMonth + '-' + todayDate; 
			/* 1.结束 */
			
			var ParamVo = [];
			var in_nos = "";
			data.forEach(function (item) {
				if (item.state != 2) {
					in_nos = in_nos + this.in_no + "<br>";
				}
				ParamVo.push(
					item.group_id + "@" +
					item.hos_id + "@" +
					item.copy_code + "@" +
					item.in_id + "@" + 
					item.in_date + "@" + //confirmDate+ "@" + 
					is_store+ "@" +
					item.store_id+"@"+
					item.in_no
				)
			});
			if (in_nos != "") {
				$.ligerDialog.error("入库确认失败！<br>以下单据不是已审核状态：<br>" + in_nos);
				return;
			}
			$.ligerDialog.confirm('确定入库确认?', function(yes) {
				if (yes) {
					//判断所选确认日期是否已经结账
					ajaxJsonObjectByUrl("verifyMatClosingDate.do?isCheck=false", {ParamVo : ParamVo.toString()}, function(responseData) {
						if (responseData.state == "true") {
							
							ajaxJsonObjectByUrl("confirmMatStorageInBatch.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					},false);
					
				}
		})
	}
	//打印模板设置
	function printSet() {

		var useId = 0;//统一打印
		if ('${p04017 }' == 1) {
			//按用户打印
			useId = '${sessionScope.user_id }';
		} else if ('${p04017 }' == 2) {
			//按仓库打印
			if (liger.get("store_code").getValue() == "") {
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId = liger.get("store_code").getValue().split(",")[0];
		}
		parent.$.ligerDialog.open({
			url : 'hrp/mat/storage/in/storageInPrintSetPage.do?template_code=04010&use_id='
					+ useId,
			data : {},
			height : $(parent).height(),
			width : $(parent).width(),
			title : '打印模板设置',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : false,
			isResize : true
		});
	}

	//打印
	function print() {

		/*  var start_date = $("#begin_in_date").val() + " 00:00"; 
		 var end_date = $("#end_in_date").val() + " 00:00"; 
		 start_date = new Date(start_date.replace(/-/g, "/")); 
		 end_date = new Date(end_date.replace(/-/g, "/")); 
		 if(start_date.getMonth() != end_date.getMonth()) { 
			  $.ligerDialog.error("不支持跨月打印！"); 
		      return false;  
		 }  */

		var useId = 0;//统一打印
		if ('${p04017 }' == 1) {
			//按用户打印
			useId = '${sessionScope.user_id }';
		} else if ('${p04017 }' == 2) {
			//按仓库打印
			if (liger.get("store_code").getValue() == "") {
				$.ligerDialog.error('当前打印模式是按仓库打印，请选择仓库！');
				return;
			}
			useId = liger.get("store_code").getValue().split(",")[0];
		}

		//if($("#create_date_b").val())
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {

			var in_id = "";
			var in_nos = "";
			$(data).each(function() {
				if (this.state != 2) {
					in_nos = in_nos + this.in_no + "<br>";
				}

				in_id += this.in_id + ","

			});

			// 			if(in_nos != ""){
			// 				$.ligerDialog.error("打印失败！<br>以下单据不是已审核状态：<br>"+in_nos);
			// 				return;
			// 			} 

			/* var para = {
				paraId : in_id.substring(0, in_id.length - 1),

				template_code : '04010',
				isPrintCount : false,//更新打印次数
				isPreview : true,//预览窗口，传绝对路径
				use_id : useId,
				p_num : 1
			//isSetPrint:flag
			}; */

			//alert(JSON.stringify(para));

			/* printTemplate(
					"hrp/mat/storage/in/queryMatInByPrintTemlate.do?isCheck=false",
					para); 
 */
 
			var para={
					template_code:'04010',
					class_name:"com.chd.hrp.mat.serviceImpl.storage.in.MatStorageInServiceImpl",
					method_name:"queryMatInByPrintTemlateNewPrint",
					isSetPrint:false,//是否套打，默认非套打
					isPreview:true,//是否预览，默认直接打印
					print_title: "材料入库单",
					paraId : in_id.substring(0, in_id.length - 1),
					use_id : useId,
					p_num : 1
			}; 
			officeFormPrint(para);
			
		}

	}

	function loadDict() {
		//字典下拉框
		autoCompleteByData("#state", matInMain_state.Rows, "id", "text", true,
				true);
		//alert(liger.get("bus_type_code").getValue());
		autocomplete("#proj_id", "../../queryMatProj.do?isCheck=false", "id",
				"text", true, true, "", false, false, 160, false, 600);
		autocompleteAsync("#bus_type_code",
				"../../queryMatBusType.do?isCheck=false", "id", "text", true,
				true, {
					codes : '2,4,6,8,10,16,18,22,47', 
					read_or_write:1,
				}, true);
		autocomplete("#maker", "../../../sys/queryUserDict.do?isCheck=false",  "id", "text", true, true, "", false);
		autocomplete("#affirm", "../../../sys/queryUserDict.do?isCheck=false",  "id", "text", true, true, "", false);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false);
		//autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false","id", "text", true, true, isUseAffiStore ? "" : {is_com : 0,is_write:'1'}, false, false, 222);
	  //库房处只能选到‘是否采购库房’为是的仓库
		autocomplete("#store_code", "../../queryMatStoreDictPro.do?isCheck=false","id", "text", true, true, isUseAffiStore ? {is_write:'1'} : {is_com : 0,is_write:'1'}, true);
		 
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false",
				"id", "text", true, true, "", false, false, 300, false, 300);

		autoCompleteByData("#bill_state", [{id: "0", text: "货到票未到"}, {id: "1", text: "货票同到"}], "id", "text", true, true);
		autoCompleteByData("#is_create_bill", yes_or_no.Rows, "id", "text", true, true);

		$("#begin_in_date").ligerTextBox({
			width : 110
		});
		$("#end_in_date").ligerTextBox({
			width : 110
		});
		autodate("#begin_in_date", "yyyy-mm-dd", "month_first");
		autodate("#end_in_date", "yyyy-mm-dd", "month_last");
		$("#begin_confirm_date").ligerTextBox({
			width : 110
		});
		$("#end_confirm_date").ligerTextBox({
			width : 110
		});
		$("#in_no").ligerTextBox({
			width : 160
		});
		$("#proj_id").ligerTextBox({
			width : 242
		});
		$("#invoice_no").ligerTextBox({
			width : 242
		});
		$("#batch_no").ligerTextBox({
			width : 242
		});
		$("#brief").ligerTextBox({
			width : 160
		});
		$("#genByOrder").ligerButton({
			click : genByOrder,
			width : 140
		});
		$("#sup_code").ligerTextBox({
			width : 160
		});
		$("#inv_code").ligerTextBox({
			width : 160
		});

		$("#begin_amount_money").ligerTextBox({
			width : 110
		});
		$("#end_amount_money").ligerTextBox({
			width : 110
		});
		$("#inv_model").ligerTextBox({
			width : 160 
		});
		$("#bar_code").ligerTextBox({
			width : 160
		});
	}

	function showDetail() {
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if (show_detail == 0) {
			$("#batch_no").val();
		}
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
			grid.bind('contextmenu', grid.options.onContextmenu);
		}
		loadHead();
	}

	function show(){

	
		       if($("#in_div").css("height")=="0px"){
			
			      $("#in_div").css("height","auto")

			 }else{
				   
				   $("#in_div").css("height","0px")
				 }
		
		grid._onResize();
		//$(".l-bar-btnload").click();
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>


	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	制单日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
			<td align="right" class="l-table-edit-td"  width="10%">
				业务类型：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				状态：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">
            	入库日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td"  width="10%">
				供应商：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<table>
            		<tr>
            			<td>
            				<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            			</td>
            			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">
            				<a href="javascript:show()">高级查询</a>
            			</td>
            		</tr>
            	</table>
            </td>
        </tr> 
    </table>
    <div id="in_div"  style="height:0px; overflow:hidden" >
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
      <tr>
        	<td align="right" class="l-table-edit-td" >
				项目：
			</td>
            <td align="left" class="l-table-edit-td" >
            	<input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" width="10%">
				单据号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
                        
            <td align="right" class="l-table-edit-td" width="10%">摘要：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
        <tr>
			<td align="right" class="l-table-edit-td" width="10%">发票号：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="invoice_no" type="text" id="invoice_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%">
            	发票状态：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input id="bill_state" type="text"/>
            </td>
			
			<td align="right" class="l-table-edit-td  demo" width="10%">是否生成发票：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="is_create_bill" id="is_create_bill" type="text"/>
            </td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
            	金额范围：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input name="begin_amount_money" type="text" id="begin_amount_money" ltype="text" />
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input name="end_amount_money" type="text" id="end_amount_money" ltype="text" />
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
                          <td align="right" class="l-table-edit-td" width="10%">规格型号：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
        <tr>
             <td align="right" class="l-table-edit-td  demo" width="10%">批号：</td>
            <td align="left" class="l-table-edit-td  demo" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%">条形码：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bar_code" type="text" id="bar_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
          	<td align="right" class="l-table-edit-td" >物资类别：</td>
			<td align="left" class="l-table-edit-td"  ><input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"></td>
        	<td align="left" class="l-table-edit-td">
          		<input name="not_invoice" type="checkbox" id="not_invoice" />&nbsp;&nbsp;无发票单据&nbsp;&nbsp;
          		<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
          	</td>
          	
          	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
          	<td align="left" class="l-table-edit-td"><input name="maker" type="text" id="maker" ltype="text" validate="{maxlength:20}" /></td>
          	
          	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">确认人：</td>
          	<td align="left" class="l-table-edit-td"><input name="affirm" type="text" id="affirm" ltype="text" validate="{maxlength:20}" /></td>
        </tr>
    </table>
    </div>
   
    
	<div id="maingrid"></div>
</body>
</html>
