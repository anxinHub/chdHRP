<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/map.js"></script>

<script type="text/javascript">
	var grid;
	var gridManager;
	var gridRowData;
	var detailDiv;
	var detailGrid;
	var state = ${matBillMain.state};
	
	$(function (){ 
		$("#layout").ligerLayout({ 
			topHeight: 125, 
			bottomHeight: 50, 
			//不允许调整大小
			allowTopResize: false, 
			allowBottomResize: false, 
		});
		loadDict();
		loadButton();
		loadHead(null);
		loadHotkeys();
		
		//单选框默认选中
		if("${matBillMain.is_init}" == "1"){
			$(":radio[name='is_init'][value=1]").prop("checked", "checked");
		}else{
			$(":radio[name='is_init'][value=0]").prop("checked", "checked");
		}
		queryDetail();
	}); 
	
	function queryDetail(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name: 'bill_id', value: $("#bill_id").val()});
		grid.options.parms.push({name: 'is_init', value: "${matBillMain.is_init}"});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '入库单号', name: 'in_no', align: 'left', width:130, 
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
			}, { 
				display: '业务类型', name: 'bus_type_name', align: 'left', width:80,
				/* render: function(rowdate, rowindex, value){
					return value ? value : "期初未付款送货单";
				} */
			}, { 
				display: '仓库', name: 'store_name', align: 'left',width: 150
			}, { 
				display: '供应商', name: 'sup_name', align: 'left', width: 180
			}, { 
				display: '制单日期', name: 'in_date', align: 'left', width: 80
			}, { 
				display: '摘要', name: 'brief', align: 'left', width: 120
			}, { 
				display: '入库日期', name: 'confirm_date', align: 'left', width: 80
			}, { 
				display: '确认人', name: 'confirmer_name', align: 'left', width: 60
			}, { 
				display: '单据金额', name: 'amount_money', align: 'right', width: 100,
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '未开票金额', name: 'not_bill_money', align: 'right', width: 100,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '已开票金额', name: 'had_bill_money', align: 'right', width: 100,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '发票金额', name: 'bill_money', align: 'right', width: 100,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				},
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, '${p04005 }', 1)+ '</div>';
                    }
                }
			}, { 
				display: '数量', name: 'amount', align: 'right', width: 60, 
				render:function(rowdata, rowindex, value){
					return formatNumber(value, 2, 0);
				}
			}, { 
				display: '使用数量', name: 'out_amount', align: 'right', width: 60, 
				render:function(rowdata, rowindex, value){
					return formatNumber(value, 2, 0);
				}
			}, { 
				display: '发票号', name: 'main_bill_no', align: 'left', width: 100
			}, { 
				display: '发票状态号', name: 'bill_state_name', align: 'left', width: 100
			}, { 
				display: '明细', name: 'detail_data', align: 'left', width: 50, hide: true  
			} ],
			rowAttrRender:function(rowdata,rowid){
				if(rowdata.amount < 0 ){
					return  "style='color:#ff320e;'"; 
				}
				return "";
			},
			usePager: false, width: '100%', height: '100%',
			url: "queryMatBillDetailById.do?isCheck=false",
			checkbox: true, rownumbers: true, frozen: false,
			enabledEdit: false, is_addRow: false, inWindow: false, 
			enabledSort: false, delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly: true, heightDiff: 30,
			onAfterAddRows: afterAddRows, 
			detail: { onShowDetail: showDetail , height:'auto', reload: true, single: true },//入库单明细
			toolbar: { id: "gridToolBar", items: [ { 
				text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add', disabled: state == 1 ? false : true 
			}, { 
				line:true 
			}, { 
				text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete', disabled: state == 1 ? false : true 
			}, { 
				line:true 
			}, { 
				text: '保存（<u>S</u>）', id: 'save', click: save, icon: 'save', disabled: state == 1 ? false : true 
			}, { 
				line:true 
			}, { 
				text: '审核（<u>Z</u>）', id: 'audit', click: audit, icon: 'bluebook', disabled: state == 1 ? false : true 
			}, { 
				line:true 
			}, { 
				text: '消审（<u>U</u>）', id: 'unaudit', click: unAudit, icon: 'bookpen', disabled: state == 2 ? false : true 
			} ] }
		});
		
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//新增行事件
	function afterAddRows(rowdataArr){
		var old_bill_money = parseFloat($("#bill_money").val() ? $("#bill_money").val() : 0);
		$.each(rowdataArr, function(){
			old_bill_money += parseFloat(this.bill_money);
		})
		$("#bill_money").val(formatNumber(old_bill_money, '${p04005 }', 0)); 
	}

	//显示明细事件
	function showDetail(row, detailPanel,callback){
		gridRowData = row;//记录主表格行数据
		
		//获得表格数据
		var detailGridDate = {
			Rows: jsonRowsToObject(row.detail_data, "in_detail_id")
		};
		
		detailDiv = document.createElement('div'); 
		$(detailPanel).append(detailDiv);
		detailGrid =$(detailDiv).css({'margin-top':10, 'margin-left':60}).ligerGrid({
			columns:[ { 
				display: '材料编码', name: 'inv_code', width: 100
			}, { 
				display: '材料名称', name: 'inv_name', width:190
			}, { 
				display: '计量单位', name: 'unit_name', width: 70
			}, { 
				display: '规格型号', name: 'inv_model', width: 200, align: 'left' 
			}, { 
				display: '批号', name: 'batch_no', width: 90 
			}, { 
				display: '条形码', name: 'bar_code', width: 100
			}, { 
				display: '单价', name: 'price', width: 80 , align: 'right',
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '单据金额', name: 'amount_money', align: 'right', width: 90,
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '未开票金额', name: 'not_bill_money', align: 'right', width: 90,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '已开票金额', name: 'had_bill_money', align: 'right', width: 90,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '发票金额(E)', name: 'bill_money', align: 'right', 
				width: 90, type: 'float', 
				editor: { 
					type: 'numberbox',
					precision: '${p04005 }'
				}, 
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '数量', name: 'amount', width: 80, align: "right"
			}, { 
				display: '使用数量', name: 'out_amount', width: 80, align: "right" 
			}, { 
				display: '发票号(E)', name: 'bill_no', width: 100, editor: {type: "text"}
			}, { 
				display: '选中', name: 'check_row', align: 'left', width: 50, hide: true
			} ], 
			width: '98%',
			height: 'auto',
			usePager : false,
			data: detailGridDate,
			enabledEdit : true, 
			isAddRow: false,
			checkbox: true, 
			selectRowButtonOnly: true,
			isScoll: true, 
			frozen: false, 
			enabledSort: false, 
			checkBoxDisplay: isCheckDisplay,
			isChecked: isChecked,
			onBeforeEdit: f_onBeforeEdit,
			onBeforeSubmitEdit: f_onBeforeSubmitEdit,
			onAfterEdit: f_onAfterEdit, 
			onCheckAllRow: checkAllDetailRow, 
			onCheckRow: checkDetailRow, 
		});
	}
	
	//编辑前事件
	function f_onBeforeEdit(e){
		if(parseFloat(e.record.not_bill_money) == 0){
			return false;
		}
		return true;
	}
	
	//验证编辑结果
	function f_onBeforeSubmitEdit(e){
		if(e.column.name == "bill_money"){
			//发票金额不能大于未开票金额
			if(parseFloat(e.value) > parseFloat(e.record.not_bill_money)){
				e.value = e.record.not_bill_money;
			}
		}
		return true;
	}
	
	// 编辑完明细表格单元格后   选择该行数据
	function f_onAfterEdit(e){
		if(e.column.name == "bill_money"){
			if(e.record.check_row && e.record.check_row == "1"){
				var bill_money = 0;//存储  主表格发票金额
				var detailData =  detailGrid.getData();
				$.each(detailData, function(i, v){
					if(v.check_row && v.check_row == "1"){
						bill_money += parseFloat(v.bill_money);
					}
				})
				
				grid.updateCell('bill_money', bill_money, gridRowData);
				grid.updateCell('detail_data', JSON.stringify(detailData), gridRowData);
				
				updateBill_money();
			}else{
				detailGrid.select(e.record);
			}
		}else{
			var detailData =  detailGrid.getData();
			grid.updateCell('detail_data', JSON.stringify(detailData), gridRowData);
		}
	} 
	
	//是否显示复选框
	function isCheckDisplay(rowdata) {
		if (parseFloat(rowdata.not_bill_money) > 0)
			return true;
		return false;
	}
	
	//是否默认勾选
	function isChecked(rowdata){
		if(parseFloat(rowdata.not_bill_money) > 0 && rowdata.check_row && rowdata.check_row == "1"){
			return true;
		}
		return false;
	}
	
	//全选明细
	function checkAllDetailRow(checked, element){
		var flag = checked ? "1" : "0";
		var bill_money = 0;
		var data = detailGrid.getData();
		$.each(data, function(index, item){
			if(parseFloat(item.not_bill_money) > 0){
				detailGrid.updateCell("check_row", flag, index);
				detailGrid.updateCell("bill_money", checked ? item.not_bill_money : 0, index);
				
				bill_money += (checked ? parseFloat(item.not_bill_money) : 0);
			}
		})
		
		var data1 = detailGrid.getData();
		grid.updateCell("bill_money", bill_money, gridRowData);
		grid.updateCell("detail_data", JSON.stringify(data1), gridRowData);

		updateBill_money();
	}
	
	//单选明细
	function checkDetailRow(checked, rowdata, rowindex, rowobj){
		detailGrid.updateCell('check_row', checked ? "1" : "0", rowdata);
		detailGrid.updateCell('bill_money', checked ? rowdata.not_bill_money : 0, rowdata);
		var bill_money = 0;//存储  主表格发票金额
		var detailData =  detailGrid.getData();
		$.each(detailData, function(index, item){
			if(parseFloat(item.not_bill_money) > 0){
				if(item.check_row && item.check_row == "1"){
					bill_money += parseFloat(item.not_bill_money);
				}
			}
		})
		
		//勾选行金额合计为0时 则表示整单保存
		grid.updateCell('bill_money', bill_money, gridRowData);
		grid.updateCell('detail_data', JSON.stringify(detailData), gridRowData);
		
		updateBill_money();
	}
	
	//改变表格数据需更新表单中发票金额
	function updateBill_money(){
		//更新表格合计行
		grid.updateTotalSummary();
		var bill_money =0;
		var data =  grid.getData();
		if(data.length > 0){
			$.each(data, function(i, v){
				bill_money += parseFloat(v.bill_money ? v.bill_money : 0);
			})
		}
		
		$("#bill_money").val(formatNumber(bill_money, '${p04005 }', 0));
	}
	
	//添加
	function add_open(){
		if(!liger.get("sup_code").getValue()){
			$.ligerDialog.error('请选择供应商！');
			return false;
		}
		if(!liger.get("bill_type").getValue()){
			$.ligerDialog.error('请选择发票状态！');
			return false;
		}
		var data = grid.getData();
		var exists_ids = "";
		var index = 0;
		$.each(data, function(){
			if(this.in_id){
				if(index > 0){
					exists_ids += ",";
				}
				exists_ids += this.in_id;
				index ++;
			}
		})
		
		$.ligerDialog.open({
			title:'选择入库单',
			url: 'matInByBillPage.do?isCheck=false',
			height: $(window).height() - 80,
			width: $(window).width() - 160,
			data: {
				sup_id: liger.get("sup_code").getValue(), 
				sup_text: liger.get("sup_code").getText(), 
				bill_type: liger.get("bill_type").getValue(),
				store_id: liger.get("store_code").getValue(),
				store_text: liger.get("store_code").getText(),
				bill_state: liger.get("bill_state").getValue(),
				is_init: $("input[name='is_init']:checked").val(), 
				exists_ids: exists_ids
			}, 
			modal: true, showToggle: false, isResize: true, 
			showMin: false, showMax: false, //开启最大化最小化按钮
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
	}
	
	//删除
	function remove(){
		//更新主表金额
		var data = grid.getCheckedRows();
		var remove_bill_money = 0;
		$.each(data, function(){
			remove_bill_money += parseFloat(this.bill_money);
		})
		var old_bill_money = $("#bill_money").val();
		$("#bill_money").val(formatNumber(parseFloat(old_bill_money) - remove_bill_money, '${p04005 }', 0));
		//移除表格选中行
    	gridManager.deleteSelectedRow();
	}
	
	//保存
	function  save(){
		var bill_no = $("#bill_no").val();
		if(!bill_no){
			$.ligerDialog.warn('请填写发票号！');
			return false;
		}
		var bill_date = $("#bill_date").val();
		if(!bill_date){
			$.ligerDialog.warn('请填写发票日期！');
			return false;
		}
		var bill_type = liger.get("bill_type").getValue();
		if(!bill_type){
			$.ligerDialog.warn('请选择发票类别！');
			return false;
		}
		var sup_id = liger.get("sup_code").getValue();
		if(!sup_id){
			$.ligerDialog.warn('请选择供应商！');
			return false;
		}
		var bill_state = liger.get("bill_state").getValue();
		if(!bill_state){
			$.ligerDialog.warn('请选择发票状态！');
			return false;
		}
		
		var data = grid.getData();
		if (data.length == 0){
			$.ligerDialog.error('请添加入库单后再保存');
			return false;
		}
		//（java中处理）解析得明细结果集（明细存在勾选则取勾选的明细，不勾选则取所有明细）
		//1.判断库房是否一致；2.判断发票状态是否一致
		var store_code = liger.get("store_code").getValue();
		var store_error = "";
		var warn_msg = "";
		$.each(data, function(index, value){
			if(value.in_id){
				if(store_code && value.store_id != store_code.split(",")[0]){
					store_error += '第'+(index+1)+'行<br>';
				}
				if(value.bill_state != bill_state){
					warn_msg += '第'+(index+1)+'行<br>';
				}
			}
		})
		if(store_error.length > 0){
			$.ligerDialog.error(store_error+'入库单与采购发票仓库不一致，请修改！');
			return false;
		}
		if(warn_msg.length > 0){
			$.ligerDialog.confirm(warn_msg+'入库单发票状态与采购发票不一致，保存后会更新入库单状态，是否继续?', function (yes){
				if(yes){
					thisSave();
				}
			})
		}else{
			thisSave();
		}
	}
	
	function thisSave(){
		var data = grid.getData();
		var formPara={
			bill_id: $("#bill_id").val(),
			bill_code: $("#bill_code").val(),
			bill_no: $("#bill_no").val(),
			bill_date: $("#bill_date").val(),
			bill_type: liger.get("bill_type").getValue(),
			store_id: liger.get("store_code").getValue().split(",")[0],
			sup_id: liger.get("sup_code").getValue().split(",")[0],
			sup_no: liger.get("sup_code").getValue().split(",")[1],
			pay_code: liger.get("pay_code").getValue(),
			bill_money: $("#bill_money").val(),
			bill_state: liger.get("bill_state").getValue(),
			note: $("#note").val(),
			is_init: $("input[name='is_init']:checked").val(), 
			allData: JSON.stringify(data)
		};

		ajaxJsonObjectByUrl("saveMatBill.do?isCheck=false", formPara, function(responseData){
			if(responseData.state=="true"){
				queryDetail();
				parentFrameUse().query();
			}
		});
	}
	
	//关闭页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	// 审核
	function audit(){
		if (state != 1){
			$.ligerDialog.error('审核失败！单据不是未审核状态,不能审核');
			return;
		}
		
		$.ligerDialog.confirm('确定审核?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("auditMatBill.do", {ids : $("#bill_id").val()}, function (responseData){
					if(responseData.state=="true"){
						state = 2;
						liger.get("gridToolBar").setDisabled("add");
						liger.get("gridToolBar").setDisabled("delete");
						liger.get("gridToolBar").setDisabled("save");
						liger.get("gridToolBar").setDisabled("audit");
						liger.get("gridToolBar").setEnabled("unaudit");
						//parentFrameUse().query();
						parentFrameUse().grid.updateRow(parentFrameUse().gridRowIdByOpen, {state: 2, state_name: '已审核'});
					}
				});
			}
		}); 
	}
	
	//消审
	function unAudit(){
		if (state != 2){
			$.ligerDialog.error('消审失败！单据不是已审核状态,不能消审');
			return;
		}
		
		$.ligerDialog.confirm('确定消审?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("unAuditMatBill.do", {ids : $("#bill_id").val()}, function (responseData){
					if(responseData.state=="true"){
						state = 1;
						liger.get("gridToolBar").setEnabled("add");
						liger.get("gridToolBar").setEnabled("delete");
						liger.get("gridToolBar").setEnabled("save");
						liger.get("gridToolBar").setEnabled("audit");
						liger.get("gridToolBar").setDisabled("unaudit");
						//parentFrameUse().query();
						parentFrameUse().grid.updateRow(parentFrameUse().gridRowIdByOpen, {state: 1, state_name: '未审核'});
					}
				});
			}
		}); 
	}
	
	//加载快捷键
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		hotkeys('Z', audit);
		hotkeys('U', unAudit);
		hotkeys('C', this_close);
	}
	
	//字典下拉框
	function loadDict(){
		//供应商下拉框
		autocomplete("#sup_code", "../../queryHosSupDictMethod.do?isCheck=false", "id", "text", true, true, '', false, '', 240);
		if("${matBillMain.sup_id}"){
			liger.get("sup_code").setValue("${matBillMain.sup_id},${matBillMain.sup_no}");
			liger.get("sup_code").setText("${matBillMain.sup_code} ${matBillMain.sup_name}");
		}
		//库房
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_write:1});
		if("${matBillMain.store_id}"){
			liger.get("store_code").setValue("${matBillMain.store_id},${matBillMain.store_no}");
			liger.get("store_code").setText("${matBillMain.store_code} ${matBillMain.store_name}");
		}
		//付款条件
		autocomplete("#pay_code", "../../queryMatPayTerm.do?isCheck=false", "id", "text", true, true, "", false, "", 240);
		if("${matBillMain.pay_code}"){
			liger.get("pay_code").setValue("${matBillMain.pay_code}");
			liger.get("pay_code").setText("${matBillMain.pay_term_code} ${matBillMain.pay_term_name}");
		}
		
		autoCompleteByData("#bill_type", [{id: "1", text: "普通发票"}, {id: "2", text: "红冲发票"}], "id", "text", true, true, "", false, "${matBillMain.bill_type}");
		autoCompleteByData("#bill_state", [{id: "0", text: "货到票未到"}, {id: "1", text: "货票同到"}], "id", "text", true, true, "", false, "${matBillMain.bill_state}");
		
		$("#bill_date").ligerTextBox({width: 160});
		
		$("#bill_code").ligerTextBox({width: 160, disabled: true});
		$("#bill_no").ligerTextBox({width: 240});
		$("#bill_money").ligerTextBox({width: 160, disabled: true, number: true, precision: '${p04006 }'});
		$("#note").ligerTextBox({width: 533});
	}
	
	//加载按钮
	function loadButton(){
        $("#print").ligerButton({ click: printDate, width: 90 });
        $("#printSet").ligerButton({ click: printSet, width: 100 });
        $("#close").ligerButton({ click: this_close, width: 90 });
	}
	
	//打印
	function printDate(){
		var useId = 0;//统一打印
		if('${p04027 }' == 1){
			//按用户打印
			useId = '${user_id }';
		}
		var para={
				template_code: '04024',
				class_name: "com.chd.hrp.mat.serviceImpl.payment.MatBillServiceImpl",
				method_name: "queryMatBillMainByPrintPage",
				//isSetPrint:flag,//是否套打，默认非套打
				isPreview: true,//是否预览，默认直接打印
				bill_id: $("#bill_id").val(),
				use_id: useId,
				p_num: 0
		};
		
		officeFormPrint(para);
	}
	
	//打印设置
	function printSet(){
		var useId=0;//统一打印
		if('${p04027 }'==1){
			//按用户打印
			useId='${user_id }';
		}
		
		officeFormTemplate({template_code: "04024", use_id: useId});
	}
	
	//打开入库单
	function openInPage(obj){
		parentFrameUse().updateInOpenForPrint(obj);
	}
</script>
</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<div id="layout">
		<div position="top" >
			<table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						流水号：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="hidden" id="bill_id" value="${matBillMain.bill_id }"/>
						<input type="text" id="bill_code" disabled="disabled" value="${matBillMain.bill_code }"/>
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>发票号：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="bill_no"  value="${matBillMain.bill_no }"/>
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>开票日期：
					</td>
					<td align="left" class="l-table-edit-td">
						<input class="Wdate" type="text" id="bill_date" value="${matBillMain.bill_date }" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>发票类别：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="bill_type"  />
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>供应单位：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="sup_code" />
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>发票状态：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="bill_state" />
					</td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td" >仓库：</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="store_code" />
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						付款条件：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="pay_code" />
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						发票金额：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="bill_money" disabled="disabled" value="${matBillMain.bill_money }"/>
					</td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td">
						是否期初：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="is_init" type="radio" value="0"/>&nbsp;&nbsp;非期初
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="is_init" type="radio" value="1"/>&nbsp;&nbsp;期初
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						摘要：
					</td>
					<td align="left" class="l-table-edit-td" colspan="4">
						<input type="text" id="note" value="${matBillMain.note }"/>
					</td>
				</tr>
			</table>
		</div>
		<div position="center" >
			<div id="maingrid"></div>
		</div>
		<div position="bottom" >
			<div align="center" style="margin-top: 10px;">
				<button id="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
				&nbsp;&nbsp;
				<button id="printSet" accessKey="M"><b>打印模板（<u>M</u>）</b></button>
				&nbsp;&nbsp;
				<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
			</div>
		</div>
	</div>
</body>
</html>
