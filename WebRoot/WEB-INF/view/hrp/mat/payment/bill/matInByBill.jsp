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
<script src="<%=path%>/lib/map.js"></script>
<script type="text/javascript">
	var grid;
	var gridRowData;
	var gridManager = null;
	var detailGrid;
	var parentData = frameElement.dialog.options.data;
	
	$(function ()
	{
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);	
		loadHotkeys();
		query();
	});
	
	//查询
	function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name: 'in_date_begin',
			value: $("#in_date_begin").val()
		});
		grid.options.parms.push({
			name: 'in_date_end',
			value: $("#in_date_end").val()
		});
		
		if(!liger.get("bus_type_code").getValue()){
			$.ligerDialog.warn('请选择业务类型！');
			return false;
		}
		grid.options.parms.push({
			name: 'bus_type_code',
			value: liger.get("bus_type_code").getValue()
		});
		grid.options.parms.push({
			name: 'store_id',
			value: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name: 'sup_id',
			value: liger.get("sup_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'stocker',
			value : liger.get("stocker").getValue()
		});
		grid.options.parms.push({
			name: 'in_no',
			value: $("#in_no").val()
		});
		grid.options.parms.push({
			name: 'brief',
			value: $("#brief").val()
		});
		grid.options.parms.push({
			name: 'bill_state',
			value: liger.get("bill_state").getValue()
		});
		grid.options.parms.push({
			name: 'is_init',
			value: parentData.is_init
		});
		grid.options.parms.push({
			name: 'exists_ids',
			value: parentData.exists_ids
		});
		
		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '入库单号', name: 'in_no', align: 'left', width: 140
			}, { 
				display: '入库日期', name: 'in_date', align: 'left', width: 80
			}, { 
				display: '仓库', name: 'store_name', align: 'left', width: 120
			}, { 
				display: '供应商', name: 'sup_name', align: 'left', width: 200
			}, { 
				display: '申请科室', name: 'dept_name', align: 'left', width: 140
			}, { 
				display: '采购员', name: 'emp_name', align: 'left', width: 70
			}, { 
				display: '单据金额', name: 'amount_money', align: 'right', width: 100,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '发票日期', name: 'bill_date', align: 'left', width: 80
			}, { 
				display: '发票号', name: 'main_bill_no', align: 'left', width: 80
			}, { 
				display: '发票状态', name: 'bill_state_name', align: 'left', width: 80
			}, { 
				display: '未开票金额', name: 'not_bill_money', align: 'right', width: 100,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '制单人', name: 'maker_name', align: 'left', width: 80
			}, { 
				display: '确认人', name: 'confirmer_name', align: 'left', width: 80
			}, { 
				display: '明细', name: 'detail_data', align: 'left', width: 80, hide: true 
			} ],
			dataAction: 'server', dataType: 'server', usePager: true, 
			url: 'queryMatInByBill.do?isCheck=false',
			width: '100%', height: '100%', 
			checkbox: true, rownumbers: true, frozen: false,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly: true, //heightDiff: -10, 
			detail: { onShowDetail: showDetail, height: 'auto', reload: true ,single: true },//入库单明细
			onCheckAllRow: checkAllRow, 
			onCheckRow: checkRow, 
			toolbar: { items: [ { 
				text: '查询（<u>Q</u>）', id: 'search', click: query, icon: 'search' 
			}, { 
				line: true 
			}, { 
				text: '添加（<u>A</u>）', id: 'add', click: addToParent, icon: 'add' 
			}, { 
				line: true 
			}, { 
				text: '关闭（<u>C</u>）', id: 'close', click: this_close, icon: 'close' 
			} ] } 
		});
		
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//全选主表
	function checkAllRow(checked, element){
		var data = grid.getData();
		var detailData = "";
		$.each(data, function(index, item){
			if(item.detail_data){
				detailData = jsonRowsToObject(item.detail_data, "in_detail_id");
				$.each(detailData, function(){
					this.check_row = checked ? "1" : "0";
					this.bill_money = checked ? this.not_bill_money : 0;
				})
				grid.updateCell("detail_data", JSON.stringify(detailData), index);
			}
		})
	}
	
	//单选主表
	function checkRow(checked, rowdata, rowindex, rowobj){
		if(rowdata.detail_data){
			detailData = jsonRowsToObject(rowdata.detail_data, "in_detail_id");
			$.each(detailData, function(){
				this.check_row = checked ? "1" : "0";
				this.bill_money = checked ? this.not_bill_money : 0;
			})
			grid.updateCell("detail_data", JSON.stringify(detailData), rowindex);
		}
	}
	
	function showDetail(row, detailPanel, callback){
		gridRowData = row;
		var detailDiv = document.createElement('div'); 
		$(detailPanel).append(detailDiv);
		detailGrid = $(detailDiv).css({'margin-top':10, 'margin-left':60}).ligerGrid({
			columns: [ { 
				display: '材料编码', name: 'inv_code', width: 100, align: 'left'
			}, { 
				display: '材料名称', name: 'inv_name', width: 150, align: 'left'
			}, { 
				display: '计量单位', name: 'unit_name', width: 70, align: 'left'
			}, { 
				display: '规格型号', name: 'inv_model', width: 140, align: 'left'
			}, { 
				display: '批号', name: 'batch_no', width: 100, align: 'left'
			}, { 
				display: '条形码', name: 'bar_code', width: 120, align: 'left'
			}, { 
				display: '单价', name: 'price', align: 'right', width: 80,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '数量', name: 'amount', align: 'right', width: 80
			}, { 
				display: '使用数量', name: 'out_amount', align: 'right', width: 80
			}, { 
				display: '金额', name: 'amount_money', align: 'right', width: 90,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '未开票金额', name: 'not_bill_money', align: 'right', width: 90,
				render:function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '发票金额', name: 'bill_money', align: 'right', width: 60, hide: true
			}, { 
				display: '选中', name: 'check_row', align: 'left', width: 40, hide: true
			} ], 
			dataAction: 'server', dataType: 'server', usePager: false, width: '98%',
			data: f_getDetailData(row), 
			checkbox: true, selectRowButtonOnly: true, 
			isScoll: true,  delayLoad: false,//初始化不加载，默认false
			fixedCellHeight: true, frozen: false, isAddRow: false,
			enabledSort: false, 
			checkBoxDisplay: isCheckDisplay,
			isChecked: isChecked,
			onAfterShowData: callback, 
			onSelectRow: selectDetailRow, 
			onUnSelectRow: unSelectDetailRow
		}); 
	} 
	
	function f_getDetailData(rowdata){
		var data = { Rows: [] };
		//有明细信息
		if(rowdata.detail_data){
			var rows = jsonRowsToObject(rowdata.detail_data, "in_detail_id"); 
			for(var i = 0; i < rows.length; i++){
				data.Rows.push(rows[i]);
			}
		}else{
			//明细中没有批次信息，需要根据先进先出从后台取出
			var para = {
				in_id : rowdata.in_id, 
				is_init: parentData.is_init
			}
			ajaxJsonObjectByUrl("queryMatInDetailByBill.do?isCheck=false", para, function(responseData){
				data = responseData;
			}, false);
			//变更主数据中材料批次信息
			grid.updateCell('detail_data', JSON.stringify(data.Rows), gridRowData); 
		}
		return data;
	}
	
	//是否显示复选框
	function isCheckDisplay(rowdata) {
		if (parseFloat(rowdata.not_bill_money) > 0)
			return true;
		return false;
	}
	
	//是否默认勾选
	function isChecked(rowdata){
		if(rowdata.check_row && rowdata.check_row == "1"){
			return true;
		}
		return false;
	}
	
	//勾选明细后
	function selectDetailRow(rowdata, rowid, rowobj){
		detailGrid.updateCell('check_row', "1", rowdata);
		detailGrid.updateCell('bill_money', rowdata.not_bill_money, rowdata);
		var detailDate =  detailGrid.getData();
		grid.updateCell('detail_data', JSON.stringify(detailDate), gridRowData);
		if(!grid.isSelected(gridRowData)){
			grid.select(gridRowData);
		} 
	}
	
	// 取消勾选明细后
	function unSelectDetailRow(rowdata, rowid, rowobj){
		detailGrid.updateCell('check_row', "0", rowdata);
		detailGrid.updateCell('bill_money', 0, rowdata);
		var detailDate =  detailGrid.getData();
		var is_check = false;
		$.each(detailDate, function(i, v){
			if(v.check_row && v.check_row == "1"){
				is_check = true;
				return;
			}
		})
		if(is_check){
			grid.updateCell('detail_data', JSON.stringify(detailDate), gridRowData);
			if(!grid.isSelected(gridRowData)){
				grid.select(gridRowData);
			}
		}else{
			grid.updateCell('detail_data', "", gridRowData);
			if(grid.isSelected(gridRowData)){
				grid.unselect(gridRowData);
			}
		}
	}
	
	function addToParent(){
		var rows = grid.getSelectedRows(); 
		if(rows.length == 0){
			$.ligerDialog.error('请选择行数据!');
			return false; 
		}
		
		var para = {
			detailData : JSON.stringify(rows)
		}
		ajaxJsonObjectByUrl("queryMatBillDetailByIn.do?isCheck=false", para, function(resData){
			if(resData.Rows){
				parent.grid.addRows(resData.Rows);
				this_close();
			}
		}, false);
	}
	
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', this_close);
	}
	
	//字典下拉框
	function loadDict(){
		$("#sup_code").ligerComboBox({width: 202, disabled: true, cancelable: false});
		liger.get("sup_code").setValue(parentData.sup_id);
		liger.get("sup_code").setText(parentData.sup_text);
		//业务类型下拉框
		if(parentData.is_init == 0){
			var bus_para;
			if(parentData.bill_type == 1){
				//普通发票入库、退货都有
				bus_para = {codes : '2,4,6,8,10,12,16,18,22,47,48', is_write: 1}
			}else{
				//红冲发票只有退货
				bus_para = {codes : '10,12,16,22,48', is_write: 1}
			}
			autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, bus_para, true);
		}else{
			//期初未付款送货单
			$("#bus_type_code").ligerComboBox({width: 160, disabled: true, cancelable: false});
			liger.get("bus_type_code").setValue(2);
			liger.get("bus_type_code").setText("采购入库");
		}
		
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_com : 0, is_write:'1'});
		if(parentData.store_id){
			liger.get("store_code").setValue(parentData.store_id);
			liger.get("store_code").setText(parentData.store_text);
		}
		autocomplete("#dept_code", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, { is_last: '1',is_write:'1' });
		//采购员下拉框
		autocomplete("#stocker", "../../queryMatStoctEmpDict.do?isCheck=false", "id", "text", true, true);
		autoCompleteByData("#bill_state", [{id: "0", text: "货到票未到"}, {id: "1", text: "货票同到"}], "id", "text", true, true, "", false, parentData.bill_state);

		autodate("#in_date_begin", "yyyy-mm-dd", "month_first");
		autodate("#in_date_end", "yyyy-mm-dd", "month_last");
		$("#in_date_begin").ligerTextBox({width: 90});
		$("#in_date_end").ligerTextBox({width: 90});
		$("#brief").ligerTextBox({width: 202});
		$("#in_no").ligerTextBox({width: 160});
	}

	//关闭页面
	function this_close(){
		frameElement.dialog.close();
	}
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" >入库日期：</td>
			<td align="left" class="l-table-edit-td">
				<table>
					<tr>
						<td>
							<input class="Wdate" type="text" id="in_date_begin" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="left" class="l-table-edit-td">至</td>
						<td>
							<input class="Wdate" type="text" id="in_date_end" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td" >业务类型：</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="bus_type_code" />
			</td>
			
			<td align="right" class="l-table-edit-td" >仓库：</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="store_code" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >供应商：</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="sup_code" />
			</td>
			
			<td align="right" class="l-table-edit-td" >发票状态：</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="bill_state"  />
			</td>
			
			<td align="right" class="l-table-edit-td" >科室：</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="dept_code" />
			</td>
		</tr> 
		<tr>
			<td align="right" class="l-table-edit-td" >摘要：</td>
			<td align="left" class="l-table-edit-td" >
				<input type="text" id="brief" />
			</td>
			
			<td align="right" class="l-table-edit-td" >入库单号：</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="in_no" />
			</td>
			
			<td align="right" class="l-table-edit-td" >采购员：</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="stocker" />
			</td>
		</tr> 
	</table>
	
	<div id="maingrid"></div>
</body>
</html>
