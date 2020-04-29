<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<!-- jsp:include page="${path}/inc.jsp"/-->
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
	<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
	<script type="text/javascript">   
	var grid; 
	var gridManager = null;
	var userUpdateStr;
	var by_sup_inv = '${p04021 }';
	var by_batch_price = '${p04030 }';
	var state = '${p04033}'== 1 ? 2 : 1;
	var isUseAffiStore = '${p04044 }' == 1 ? true : false;
	$(function() {
		loadDict();//加载下拉框
		//加载数据
		loadHead(null);
		loadHotkeys();
	
		$("#store_code").bind("change", function () {
			charge_store();
			//loadHead();
			grid.reRender();
			
		});
		/* $("#sup_code").bind("change", function () {
			liger.get("stocker").setValue("");
			liger.get("stocker").setText("");
			
			liger.get("store_code").setValue("");
			liger.get("store_code").setText("");
			
		}); */
		
		
	});
	//查询
	function query() {
		
		if (!liger.get("sup_code").getValue()) {
			$.ligerDialog.warn("供应商不能为空！");
			return false;
		}
		
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]}); 
		//grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1]}); 
		//grid.options.parms.push({name : 'stocker',value : liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue()}); 
		grid.options.parms.push({name : 'order_code',value : $("#order_code").val()});
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()}); 
		grid.options.parms.push({name : 'brif',value : $("#brif").val()});
		grid.options.parms.push({name : 'state',value : state});
		grid.options.parms.push({name : 'is_closed',value : 0});
		
		grid.options.parms.push({name : 'store_id',value : liger.get("req_store_code").getValue() == null ? "" : liger.get("req_store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("req_store_code").getValue() == null ? "" : liger.get("req_store_code").getValue().split(",")[1]});
		//加载查询条件
		grid.loadData(grid.where);
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
			            {display : '材料变更号',name : 'inv_no',width:80,hide : true},
					    {display : '订单ID',name : 'order_id',width:80,hide : true},
					    {display : '是否线上',name : 'is_good',width:80,hide : true},
					    {display : '订单明细ID',name : 'order_detail_id',width:80,hide : true},
					    {display : '材料ID',name : 'inv_id',width:80,hide : true},
					    {display : '材料NO',name : 'inv_no',width:80,hide : true},
					    {display : '订单号',name : 'order_code',align : 'left',width : '120'},
					    {display : '需求库房',name : 'store_name',align : 'left',width : '150'},
					    {display : '摘要', name: 'brif', align: 'left',width:'150'},
					    /* {display : '申请部门', name: 'dept_name', align: 'left',width:'80'}, */
					    {display : '编制日期', name: 'order_date', align: 'left',width:'80'}, 
					    {display : '制单人', name: 'maker_name', align: 'left',width:'80'}, 
					    /* {display : '供应商', name: 'sup_name', align: 'left',width:'200'}, */
						{display : '材料编码',name : 'inv_code',align : 'left',width : '120'},
						{display : '材料名称',name : 'inv_name',align : 'left',width : '220'},
						{display : '规格型号',name : 'inv_model',width : 120,align : 'left'},
						{display : '计量单位',name : 'unit_name',width : 80,align : 'left'},
						{display : '单价', name: 'price', align: 'right',width:'80',
							render: function (rowdata, rowindex, value) {
								rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
								return value == null ? "" : formatNumber(value, '${p04006 }', 1);
							}
				 		}, 
				 		{display: '订单数量', name: 'order_amount', align: 'right',width:'80',
				 			totalSummary : {
								align : 'right',
								render : function(suminf, column,cell) {
									return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
								}
							}	
				 		},{display : '已入库数量',name : 'already_amount',width : 100,align : 'right',hide: true
						}, 
						{display : '入库数量(E)',name : 'amount',width : 90,type : 'float',align : 'right',editor : {type : 'float',},
							totalSummary : {
								align : 'right',
								render : function(suminf, column,cell) {
										return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
								}
							}
						},{display : '金额(E)',name : 'amount_money',width : 90,align : 'right',
							render : function(rowdata, rowindex,value) {
								rowdata.amount_money = value == null ? "": formatNumber(value,'${p04005 }',0);
								return value == null ? "": formatNumber(value,'${p04005 }',1);
							},
							totalSummary : {
								align : 'right',
								render : function(suminf, column,cell) {
									return '<div>'+ formatNumber(suminf.sum == null ? 0	: suminf.sum,'${p04005 }',1) + '</div>';
								}
							}
						},{display: '注册证号(E)', name : 'cert_id',  textField : 'cert_code',width : 300, align : 'left',
							 render : function(rowdata, rowindex, value) {
							 	return rowdata.cert_code;
							 },
							 editor : {
									type : 'select',
									valueField : 'code',
									textField : 'name',
									selectBoxWidth : 250,
									selectBoxHeight : 240,
									keySupport : true,
									autocomplete : true,
									onSelected : function (data){	
								    	if(typeof (data) === "string"){
								    		var formPara="";
										 	formPara = {												 			
										 		cert_id : data
										 	}			 	
								    	}
									}
								}
							
						},{display: '生产日期(E)', name: 'fac_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 90,
							editor: {
								type: 'date',
							}
						}, {display : '生产批号(E)',name : 'batch_no',width : 120,align : 'left',editor : {type : 'text',}
						}, {display : '有效日期(E)',name : 'inva_date',type : 'date',align : 'left',format : 'yyyy-MM-dd',width : 90,
							editor : {type : 'date',}
						}, {  display : '灭菌批号(E)',name : 'disinfect_no',width : 80,align : 'left',
							editor : {type : 'text',}
						},{display : '条形码(E)',name : 'sn',width : 80,align : 'left',
							editor : {type : 'text',}
						},/*  {display : '灭菌日期(E)',name : 'disinfect_date',type : 'date',align : 'left',format : 'yyyy-MM-dd',width : 90,
							editor : {type : 'date',},hide: true
						}, */ 
						  {display: '批发单价(E)', name: 'sale_price', width: 80, align: 'right',
							editor: {
								type: 'numberbox',
								precision: '${p04006 }'
							},
							render: function (rowdata, rowindex, value) {
								rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
								return value == null ? "" : formatNumber(value, '${p04006 }', 1);
							}
						}, {display: '批发金额', name: 'sale_money', width: 80, align: 'right',
							render: function (rowdata, rowindex, value) {
								rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
								return value == null ? "" : formatNumber(value, '${p04005 }', 1);
							},
							totalSummary: {
								align: 'right',
								render: function (suminf, column, cell) {
									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04005 }', 1) + '</div>';
								}
							}
						}, {display: '零售单价(E)', name: 'sell_price', width: 80, align: 'right',
							editor: {
								type: 'numberbox',
								precision: '${p04072 }'
							},
							render: function (rowdata, rowindex, value) {
								rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
								return value == null ? "" : formatNumber(value, '${p04072 }', 1);
							}
						}, {display: '零售金额', name: 'sell_money', width: 80, align: 'right',
							render: function (rowdata, rowindex, value) {
								//rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
								return value == null ? "" : formatNumber(value, '${p04073 }', 1);
							},
							totalSummary: {
								align: 'right',
								render: function (suminf, column, cell) {
									return '<div>' + formatNumber(suminf.sum == null ? 0 : suminf.sum, '${p04073 }', 1) + '</div>';
								}
							}
						},{display : '生产厂商',name : 'fac_name',width : 180,align : 'left'
						},{display : '备注(E)',name : 'note',width : 80,align : 'left',editor : {type : 'text',}} 
					],
					dataAction : 'server',dataType : 'server',usePager : true,width : '98%',height : '85%',
					url : 'queryMatStorageInOrderN.do?isCheck=false',pageSize:200,
					checkbox : true,rownumbers : true,isAddRow: false,
					enabledEdit : true,delayLoad : true,//初始化不加载，默认false
					selectRowButtonOnly : true,//heightDiff: -10,
					onBeforeEdit: f_onBeforeEdit, onBeforeSubmitEdit: f_onBeforeSubmitEdit, onAfterEdit: f_onAfterEdit,
					//groupColumnName : 'inv_name',groupColumnDisplay : '材料名称    ',
					toolbar: {
						items: [{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
								{ line:true },
								{text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete'}, 
								{ line:true },
								{ text: '生成入库单（<u>A</u>）', id:'save', click: save, icon:'add' },
								{ line:true },
								{ text: '复制材料（<u>M</u>）', id:'copy', click: copy, icon:'copy' },
								{ line:true },
								{ text: '关闭材料（<u>B</u>）', id:'closeInv', click: closeInv, icon:'close' },
								{ line:true },
								{ text: '已关闭材料列表（<u>E</u>）', id:'closeInvInfo', click: closeInvInfo, icon:'search' },
								{ line:true },
								{ text: '关闭（<u>L</u>）', id:'close', click: this_close, icon:'close' }
								
						]},
						onDblClickRow : function (rowdata, rowindex, value){
							changeStore(rowdata);
						}
				});

			gridManager = $("#maingrid").ligerGetGridManager();
			grid.toggleCol("inv_no", false);
	}
	
	//双击给仓库赋值
	function changeStore(rowdata){
		if(rowdata!=null){
			//alert(rowdata.store_id+","+rowdata.store_no+"  "+rowdata.store_code+" "+rowdata.store_name);
			liger.get("store_code").setValue(rowdata.store_id+","+rowdata.store_no+","+rowdata.stock_emp+","+rowdata.emp_code+" "+rowdata.emp_name);
			liger.get("store_code").setText(rowdata.store_code+" "+rowdata.store_name);
			
			liger.get("stocker").setValue(rowdata.stock_emp);
			liger.get("stocker").setText(rowdata.emp_code+" "+rowdata.emp_name);
			
			liger.get("examiner").setValue(rowdata.manager);
			liger.get("examiner").setText(rowdata.manager_code+" "+rowdata.manager_name);
		}
	}
	//关闭材料列表
	function closeInvInfo(){
		var sup_id = liger.get("sup_code").getValue();
    	var sup_text = liger.get("sup_code").getText();
		if(!sup_id){
    		$.ligerDialog.warn('请选择供应商！');
    		return false;
    	}
		var paras = sup_id+"@"+sup_text;
		$.ligerDialog.open({
			url: "matOrderImpCloseInvPage.do?isCheck=false&paras="+paras, 
			height: 500,width: 900, title:'关闭材料列表',
			modal:true,showToggle:false,showMax:true,showMin: false,isResize:true
		});
		
    }
	//关闭材料
	function closeInv(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
	 		/*校验grid---begin-----*/
	 		var targetMap = new HashMap();
	 		var ParamVo =[];
	 		/* var msgInv="";
	 		$.each(data,function(i, v){
				if(v.already_amount != 0 ){
					msgInv = msgInv + v.inv_code+",";
					return;
				}
	 		});
	 		
	 		if (msgInv != "") {
				$.ligerDialog.warn( msgInv +"  部分到货，不能关闭！");
				return false;
			} */
	 		
			$(data).each(function (){		
				ParamVo.push(
					this.group_id   +"@"+ 
					this.hos_id   +"@"+ 
					this.copy_code   +"@"+ 
					this.order_id +"@"+ 
					this.order_detail_id
				) 
			});
			$.ligerDialog.confirm('确定要关闭选中的材料？', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("updateMatStorageInCloseInv.do?isCheck=true", {ParamVo : ParamVo.toString()}, function(responseData){
			            if(responseData.state=="true"){
							query();
			            }
					});
				}
			});
		}
	}
	
	function copy () {
		var data_copys = grid.getCheckedRows();
		if (data_copys.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}else{
			for(var i = 0, length = data_copys.length; i<length; i++){         // 把数组的每个data都复制一遍，即深复制
				data_copys[i] = $.extend({},data_copys[i]) ;
				data_copys[i].amount = '1';
			}
			grid.addRows(data_copys);
		}
	}

	function remove() {
		grid.deleteSelectedRow();
	}
	
	var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;
		
		if(column_name=='cert_id'){
			var certId = grid.getColumnByName("cert_id");
			certId.editor.url='../../queryMatInvCertIdWithDate.do?isCheck=false&inv_id='+e.record.inv_id;
		}
	}
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		return true;
	}
	// 跳转到下一个单元格之前事件
	function f_onAfterEdit(e) {
		//查看入库数量是否和订单数量相符
		/* if(e.column.name == "amount"){
			alert(e.record.order_amount);
			if(checkValue(e.record.inv_id,e.record.order_amount) == '1'){
				$.ligerDialog.warn("入库数量大于订单数量！",'','',{closeWhenEnter:false});
				return;
			}
		} */
		
		if (e.value != "") {
			if (e.column.name == "inv_id") {
				//判断是否为自动有效日期
				if ('${p04009 }' != 0) {
					grid.updateCell('inva_date', getDateAddDay(new Date(), '${p04009 }'), e.rowindex);
				}
			} else if (e.column.name == "amount") {
				
				//自动计算金额
				if (e.record.price != undefined && e.record.price != "") {
					grid.updateCell('amount_money', e.value * e.record.price, e.rowindex);
				}
				//自动计算零售金额
				if (e.record.sell_price != undefined && e.record.sell_price != "") {
					grid.updateCell('sell_money', e.value * e.record.sell_price, e.rowindex);
				}
				//自动计算批发金额
				if (e.record.sale_price != undefined && e.record.sale_price != "") {
					grid.updateCell('sale_money', e.value * e.record.sale_price, e.rowindex);
				}
				
				 
			} else if (e.column.name == "price") {
				//自动计算金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('amount_money', e.record.amount ? e.value * e.record.amount : 0, e.rowindex);
				}
				
			}else if (e.column.name == "sell_price") {
				//自动计算零售金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sell_money', e.value * e.record.amount, e.rowindex);
				}
			} else if (e.column.name == "sale_price") {
				//自动计算批发金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sale_money', e.value * e.record.amount, e.rowindex);
				}
			} else if (e.column.name == "sale_money") {
				//自动计算批发单价
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('sale_price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
				}
			} else if (e.column.name == "amount_money") {
				//自动计算金额
				if (e.record.amount != undefined && e.record.amount != "") {
					grid.updateCell('price', e.record.amount ? e.value / e.record.amount : 0, e.rowindex);
				}
				
			}
		}
		grid.updateTotalSummary();
		return true;
	}
	
	//验证数量
	function checkValue(inv_id,order_amount){
		var sumAmt = 0.00;
		
		var data = gridManager.getData();
		$.each(data, function (i, v) {
			if(v.inv_id == inv_id){
				sumAmt = parseInt(sumAmt)+parseInt(v.amount);
			}
		});

		if((order_amount != sumAmt) && (order_amount < sumAmt)){
			return "1";
		}else{
			return "0";
		}
		
		
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('L', this_close);
	}


	function save() {
		if (validateGrid()) {
			var formPara = {
				in_no: $("#in_no").val(),
				bus_type_code: liger.get("bus_type_code").getValue(),
				store_id: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
				store_no: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
				in_date: $("#in_date").val(),
				//stocker : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[2],
				stocker: liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
				sup_id: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
				sup_no: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
				stock_type_code: liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
				app_dept: liger.get("app_dept").getValue() == null ? "" : liger.get("app_dept").getValue(),
				protocol_code: liger.get("protocol_code").getValue() == null ? "" : liger.get("protocol_code").getValue().split(",")[0],
				proj_id: liger.get("proj_code").getValue() == null ? "" : liger.get("proj_code").getValue(),
				brief: $("#brief").val(),
				delivery_code: $("#delivery_code").val(),
				examiner: liger.get("examiner").getValue() == null ? "" : liger.get("examiner").getValue(),
				come_from : 2,
				detailData: JSON.stringify(gridManager.getCheckedRows())
			};
			//alert(JSON.stringify(gridManager.getData()));
			ajaxJsonObjectByUrl("addMatStorageInOrder.do?isCheck=true", formPara, function (responseData) {
				if (responseData.state == "true") {
					parentFrameUse().query();
					parentFrameUse().update_open(responseData.update_para);
					this_close();
				}
			});
		}
 	} 

	function validateGrid() {
		//主表
		if (!liger.get("bus_type_code").getValue()) {
			$.ligerDialog.warn("业务类型不能为空");
			return false;
		}
		if (!liger.get("store_code").getValue()) {
			$.ligerDialog.warn("仓库不能为空");
			return false;
		}

		if (!$("#in_date").val()) {
			$.ligerDialog.warn("制单日期不能为空");
			return false;
		}
		
		if (liger.get("bus_type_code").getValue() && liger.get("bus_type_code").getValue() == 2) {
			/* if (!liger.get("stocker").getValue()) {
				$.ligerDialog.warn("采购员不能为空");
				return false;
			} */
			if (!liger.get("stock_type_code").getValue()) {
				$.ligerDialog.warn("采购类型不能为空");
				return false;
			}
			if (!liger.get("sup_code").getValue()) {
				$.ligerDialog.warn("供应商不能为空");
				return false;
			}
		}
		
		//明细
		var rowm = "";
		var msg = "";
		var priceMsg = "";
		var rows = 0;
		var store_inv = "";  //用于判断是否属于该仓库
		var sup_inv = ""; //用于判断唯一供应商
		var certMsg = "";
		var v_store_id  = liger.get("store_code").getValue().split(",")[0];  //用于判断列表中的库房是否与需生成入库单的库房一致
		var is_store_error = false;
		var data = gridManager.getCheckedRows();
		//alert(JSON.stringify(data));
		//判断grid 中的数据是否重复或者为空
		var targetMap = new HashMap();
		$.each(data, function (i, v) {
			rowm = "";
			if (v.inv_id) {
				//判断列表中的库房是否与需生成入库单的库房一致
				if(v.store_id && v.store_id != v_store_id){
					is_store_error = true;
					return false;
				}
				//如果批号为空给默认批号用于判断
				if (!v.batch_no) {
					v.batch_no = '-';
				}
				if (!v.amount) {
					rowm += "[数量]、";
				}
				if(v.amount<0){
					rowm += "[数量]、";
				}
				if (v.price == "" || v.price == null || v.price == 'undefined') {
					rowm += "[单价]、";
				}
				if (rowm != "") {
					rowm = "第" + (i + 1) + "行" + rowm.substring(0, rowm.length - 1) + "不能为空并且数量不能小于或等于0。" + "<br>";
				}
				msg += rowm;
				if (v.is_bar == 1 && !v.sn) {
					msg += "第" + (i + 1) + "行按条码管理的材料必须输入条形码。<br>";
				}
				if (v.is_quality == 1) {
					if (!v.inva_date) {
						msg += "第" + (i + 1) + "行按保质期管理的材料必须输入有效日期。<br>";
					} else {
						if (v.batch_no != "-") {
							//如果材料按保质期管理，则判断有效日期是否与上一批号一致
							var para = {
								inv_id: v.inv_id,
								batch_no: v.batch_no,
								inva_date: v.inva_date
							}
						/* 	ajaxJsonObjectByUrl("../../queryMatInvBatchInva.do?isCheck=false", para, function (responseData) {
								if (responseData.state == "false") {
									msg += "第" + (i + 1) + "行批号" + v.batch_no + "对应的有效日期应为" + responseData.inva_date + "<br>";
								}
							}, false); */
						}
					}
				}
				/* if (v.is_disinfect == 1) {
					if (!v.disinfect_date) {
						msg += "第" + (i + 1) + "行灭菌材料必须输入灭菌日期。<br>";
					} else {
						if (v.batch_no != "-") {
							//如果材料是灭菌材料，则判断灭菌日期是否与上一批号一致
							var para = {
								inv_id: v.inv_id,
								batch_no: v.batch_no,
								disinfect_date: v.disinfect_date
							}
							ajaxJsonObjectByUrl("../../queryMatInvBatchDisinfect.do?isCheck=false", para, function (responseData) {
								if (responseData.state == "false") {
									msg += "第" + (i + 1) + "行批号" + v.batch_no + "对应的灭菌日期应为" + responseData.disinfect_date + "<br>";
								}
							}, false);
						}
					}
				} */
				if (v.batch_no != "-") {
					//同一批号的单价必须一致
					var para = {
						inv_id: v.inv_id,
						batch_no: v.batch_no,
						price: v.price
					}
					ajaxJsonObjectByUrl("../../queryMatInvBatchPrice.do?isCheck=false", para, function (responseData) {
						if (responseData.state == "false") {
							priceMsg += "第" + (i + 1) + "行批号" + v.batch_no + "对应的单价应为" + responseData.price + ", ";// + ", <br>";
						}
					}, false);
				}
				if(v.cert_id != null || v.cert_id != ''){
					
					//如果所选材料带有注册证 判断注册证是否过期并提示
					var para = {
						cert_id: v.cert_id
						
					}
					ajaxJsonObjectByUrl("../../queryMatCertDate.do?isCheck=false", para, function (responseData){
						if(responseData.state=="false"){
							certMsg += "第"+(i+1)+"行【证件号："+v.cert_code+"到期日期为"+responseData.end_date+",已过期"+Math.abs(responseData.days)+"天】;";
						}
					}, false);
				}
				//如果条码为空给默认条码用于判断
				if (!v.sn) {
					v.sn = '-';
					v.bar_code = '-';
				}
				var key = v.batch_no == '-' ? v.inv_id + "|" + v.price + "|" + v.batch_no + "|" + v.sn + "|" + v.location_id + "|" + v.bar_code : v.inv_id + "|" + v.batch_no + "|" + v.sn + "|" + v.location_id + "|" + v.bar_code;

				var value = "第" + (i + 1) + "行";
				if (!targetMap.get(key)) {
					targetMap.put(key, value);
				} else {
					msg += targetMap.get(key) + "与" + value + v.batch_no == '-' ? "材料编码、单价、生产批号、条形码、货位不能重复" : "材料编码、生产批号、条形码、货位不能重复" + "<br>";
				}
				rows = rows + 1;
				store_inv += v.inv_id + ",";
				if (v.is_single_ven == 1) {
					sup_inv += v.inv_id + ",";
				}
			}
		});
		if(is_store_error){
			$.ligerDialog.warn("所选订单的库房与生成入库单的库房需一致！");
			return false;
		}
		if (rows == 0) {
			$.ligerDialog.warn("请先添加材料！");
			return false;
		}
		//判断仓库材料关系
		if (store_inv.length > 0) {
			//仓库材料对应关系
			var para = {
				inv_ids: store_inv.substring(0, store_inv.length - 1),
				store_id: liger.get("store_code").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../existsMatStoreIncludeInv.do?isCheck=false", para, function (responseData) {
				if (responseData.state == "false") {
					//$.ligerDialog.warn("材料"+responseData.inv_ids+"不在该仓库中！");  
					//return false;
					msg += "材料" + responseData.inv_ids + "不在该仓库中！<br>";
				}
			}, false);

			if (by_sup_inv == 1) {
				//供应商材料对应关系
				para = {
					inv_ids: store_inv.substring(0, store_inv.length - 1),
					sup_id: liger.get("sup_code").getValue().split(",")[0]
				}
				ajaxJsonObjectByUrl("../../existsMatSupIncludeInv.do?isCheck=false", para, function (responseData) {
					if (responseData.state == "false") {
						//$.ligerDialog.warn("材料"+responseData.inv_ids+"不属于该供应商！");  
						//return false;
						msg += "材料" + responseData.inv_ids + "不属于该供应商！<br>";
					}
				}, false);
			}
		}
		//如果存在唯一供应商的材料则判断是否是唯一供应商
		if (sup_inv.length > 0) {
			var para = {
				inv_ids: sup_inv.substring(0, sup_inv.length - 1),
				sup_id: liger.get("sup_code").getValue().split(",")[0]
			}
			ajaxJsonObjectByUrl("../../existsMatInvOnlySup.do?isCheck=false", para, function (responseData) {
				if (responseData.state == "false") {
					//$.ligerDialog.warn("材料"+responseData.inv_ids+"不符合唯一供应商要求！");  
					//return false;
					msg += "材料" + responseData.inv_ids + "不符合唯一供应商要求！<br>";
				}
			}, false);
		};
		
		if(certMsg != ""){
       		if(!confirm(certMsg+'是否继续保存？')){
       			
       			return false;
       		}
 		}
		if (msg != "") {
			$.ligerDialog.warn(msg);
			return false;
		}
		if(priceMsg){
			//提示单价不同是否继续保存
			if(by_batch_price  == 0){
				/* $.ligerDialog.confirm(priceMsg+'确定继续保存单据?', function (yes){
					if(!yes){
						return false;
					}
				}); */
				if(!confirm(priceMsg+'确定继续保存单据?')){
					return false;
				}
			}else{//提示单价不同并中断保存操作
				$.ligerDialog.warn(priceMsg);
				return false;
			}
		}
		//alert("校验通过")
		return true;
	}
	
	//获取仓库采购员
	function charge_store(){
		
    	var store_id = liger.get("store_code").getValue().split(",")[0];  
		autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);
		
	}
	
	function this_close() {
		frameElement.dialog.close(); 
	}

	function loadDict() { 
		//字典下拉框
		
        autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, { sel_flag: 'in' }, true);
		autocompleteAsync("#store_code", "../../queryMatStoreStocker.do?isCheck=false", "id", "text", true, true, {is_com : 0}, true);
		var store_id = liger.get("store_code").getValue().split(",")[0];
		
		autocompleteAsync("#req_store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_com : 0}, false);
		
		autocompleteAsync("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true, "", false, false, 160);
		autocomplete("#stocker", "../../queryMatStockEmpByStore.do?isCheck=false", "id", "text", true, true,{store_id:store_id}, true);
		$("#stocker").ligerTextBox({ width: 160 });
		$("#begin_date").ligerTextBox({width : 100});
		
		//autodate("#begin_date", "yyyy-mm-dd", "month_first");
		$("#end_date").ligerTextBox({width : 100});
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		$("#order_code").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:160});
        $("#brif").ligerTextBox({width:160});
		autocomplete("#stock_type_code", "../../queryMatStockType.do?isCheck=false", "id", "text", true, true, '', true);
		autocomplete("#app_dept", "../../queryHosDeptByPerm.do?isCheck=false", "id", "text", true, true, { is_last: '1' });
		autocompleteAsync("#protocol_code", "../../queryMatProtocolMain.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		autocomplete("#proj_code", "../../queryMatProj.do?isCheck=false", "id", "text", true, true, "", false, false, 160, false, 240);
		$("#in_no").ligerTextBox({ width: 160 });
		$("#in_date").ligerTextBox({ width: 160 });
		autodate("#in_date");//默认当前日期
		$("#bus_type_code").ligerTextBox({ width: 160 });
		$("#store_code").ligerTextBox({ width: 160 });
		$("#stock_type_code").ligerTextBox({ width: 160 });
		$("#brief").ligerTextBox({width:160});
		
		$("#delivery_code").ligerTextBox({width:160});
		autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true, '', false);
		
		$("#examiner").ligerTextBox({width:160});	
		
		/* //格式化按钮
		$("#query").ligerButton({click : query,width : 90});
		//格式化按钮
		$("#save").ligerButton({click : save,width : 90});
		$("#close").ligerButton({click : this_close,width : 90}); */
	}
	
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border='0'>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">订单日期：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<table>
					<tr>
						<td align="left"><input class="Wdate" name="begin_date"
							id="begin_date" type="text" value="${start_date}"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
						<td align="right" class="l-table-edit-td">至：</td>
						<td align="left" class="l-table-edit-td"><input class="Wdate"
							name="end_date" id="end_date" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
				</table>
			</td>
			<td align="right" class="l-table-edit-td" width="10%">
				<span  style="color:red">*</span>供货单位：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:false}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%">订单号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="order_code" type="text" id="order_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">摘要：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="brif" type="text" id="brif" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">需求仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="req_store_code" type="text" id="req_store_code" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
		</tr>
	</table>
	<hr/>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border='0'>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%"><span style="color:red">*</span>入库单号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="in_no" type="text" id="in_no" value="自动生成" ltype="text" disabled="disabled" required="true" validate="{required:true}"/>
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%"><span style="color:red">*</span>业务类型：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%"><span style="color:red">*</span>仓库：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%"><span style="color:red">*</span>制单日期：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input class="Wdate" name="in_date" id="in_date" type="text" required="true" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%"><span style="color:red">*</span>采购类型：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:true}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%">采购员：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="stocker" type="text" id="stocker" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			
			
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">协议编号：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:false}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">项&nbsp;&nbsp;&nbsp;&nbsp;目：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false,maxlength:20}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%">申请科室：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="app_dept" type="text" id="app_dept" ltype="text" validate="{required:true}" />
			</td>
			
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">货单号：</td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<input name="delivery_code" style="background-color:#FF7F50;" type="text" id="delivery_code" ltype="text" validate="{required:false,maxlength:20}" />
			</td>
			
			<td align="right" class="l-table-edit-td" width="10%">验收员：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="examiner" type="text" id="examiner" ltype="text" validate="{required:false,maxlength:20}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%">摘&nbsp;&nbsp;&nbsp;&nbsp;要：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="brief" type="text" id="brief" ltype="text" validate="{required:false,maxlength:50}" />
			</td>
		</tr>
	</table>
	</form>
	<div id="maingrid"></div>
	<!-- <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		 <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
			<tr>
				<td align="center" class="l-table-edit-td" colspan="3">
					<button id="query" accessKey="Q"><b>查询订单（<u>Q</u>）</b></button> &nbsp;&nbsp;
					<button id="save" accessKey="B"><b>生成入库单（<u>B</u>）</b></button> &nbsp;&nbsp;
					<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table> 
	</div> -->
</body>
</html>
