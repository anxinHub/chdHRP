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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager;
	var gridRowData;
	var detailDiv;
	var detailGrid;
	
	$(function (){ 
		$("#layout").ligerLayout({ 
			topHeight: 155, 
			//不允许调整大小
			allowTopResize: false, 
		});
		loadDict();
		loadHead(null);
		loadHotkeys();
	}); 
	
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '发票流水号', name: 'bill_code', align: 'left', width: 120,
				totalSummary: {
					align : 'right',
                    render: function (suminf, column, cell) {
                        return '<div>合计：</div>';
                    }
                }
			}, { 
				display: '发票号', name: 'bill_no', align: 'left', width:100, 
			}, { 
				display: '仓库', name: 'store_name', align: 'left',width: 150
			}, { 
				display: '供应商', name: 'sup_name', align: 'left', width: 180
			}, { 
				display: '发票日期', name: 'bill_date', align: 'left', width: 80
			}, { 
				display: '制单人', name: 'maker_name', align: 'left', width: 60
			}, { 
				display: '审核人', name: 'checker_name', align: 'left', width: 60
			}, { 
				display: '审核日期', name: 'chk_date', align: 'left', width: 80
			}, {  
				display: '发票金额', name: 'bill_money', align: 'right', width: 100,
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
				display: '未付款金额', name: 'not_pay_money', align: 'right', width: 100,
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
				display: '已优惠金额', name: 'had_dis_money', align: 'right', width: 100,
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
				display: '已付款金额', name: 'had_pay_money', align: 'right', width: 100,
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
				display: '优惠金额', name: 'dis_money', align: 'right', width: 100,
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
				display: '付款金额', name: 'pay_money', align: 'right', width: 100,
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
				display: '发票状态', name: 'bill_state_name', align: 'left', width: 100
			}, { 
				display: '摘要', name: 'note', align: 'left', width: 120
			}, { 
				display: '明细', name: 'detail_data', align: 'left', width: 50, hide: true  
			} ],
			usePager: false, width: '100%', height: '100%',
			checkbox: true, rownumbers: true, frozen: false,
			enabledEdit: false, is_addRow: false, inWindow: false, 
			enabledSort: false, delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly: true, heightDiff: 30,
			onAfterAddRows: afterAddRows, 
			detail: { onShowDetail: showDetail , height:'auto', reload: true, single: true },//入库单明细
			toolbar: { items: [ { 
				text: '添加（<u>A</u>）', id: 'add', click: add_open, icon: 'add' 
			}, { 
				line:true 
			},  {
				text: '删除（<u>D</u>）', id: 'delete', click: remove, icon: 'delete' 
			}, { 
				line:true 
			}, {
				text: '保存（<u>S</u>）', id: 'save', click: save, icon: 'save' 
			}, { 
				line:true 
			}, {
				text: '关闭（<u>C</u>）', id: 'close', click: this_close, icon: 'close' 
			} ] }
		});
		
		gridManager = $("#maingrid").ligerGetGridManager();
	}

	//新增行事件
	function afterAddRows(rowdataArr){
		var old_pay_money = parseFloat($("#pay_money").val() ? $("#pay_money").val() : 0);
		var old_bill_count = parseInt($("#bill_count").val() ? $("#bill_count").val() : 0);
		$.each(rowdataArr, function(){
			old_pay_money += parseFloat(this.pay_money);
			old_bill_count++;
		})
		$("#pay_money").val(formatNumber(old_pay_money, '${p04005 }', 0));
		$("#bill_count").val(old_bill_count);
	}

	//显示明细事件
	function showDetail(row, detailPanel,callback){
		gridRowData = row;//记录主表格行数据
		
		//获得表格数据
		var detailGridDate = {
			Rows: jsonRowsToObject(row.detail_data, "bill_detail_id")
		};
		
		detailDiv = document.createElement('div'); 
		$(detailPanel).append(detailDiv);
		detailGrid =$(detailDiv).css({'margin-top':10, 'margin-left':60}).ligerGrid({
			columns:[ { 
				display: '入库单号', name: 'in_no', width: 120
			}, { 
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
				display: '发票金额', name: 'bill_money', align: 'right', width: 90,
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '未付款金额', name: 'not_pay_money', align: 'right', width: 90,
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '已优惠金额', name: 'had_dis_money', align: 'right', width: 90,
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '已付款金额', name: 'had_pay_money', align: 'right', width: 90,
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '优惠金额(E)', name: 'dis_money', align: 'right', 
				width: 90, type: 'float', 
				editor: { 
					type: 'numberbox',
					precision: '${p04005 }'
				}, 
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '付款金额(E)', name: 'pay_money', align: 'right', 
				width: 90, type: 'float', 
				editor: { 
					type: 'numberbox',
					precision: '${p04005 }'
				}, 
				render: function(rowdata, rowindex, value){
					return formatNumber(value, '${p04005 }', 1);
				}
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
			onAfterEdit: f_onAfterEdit, 
			onBeforeSubmitEdit: f_onBeforeSubmitEdit, 
			onCheckAllRow: checkAllDetailRow, 
			onCheckRow: checkDetailRow, 
		});
	}
	
	//编辑前事件
	function f_onBeforeEdit(e){
		if(parseFloat(item.not_pay_money) == 0){
			return false;
		}
		return true;
	}
	
	//验证编辑结果
	function f_onBeforeSubmitEdit(e){
		if(e.column.name == "dis_money"){
			//优惠金额不能大于未开票金额
			if(parseFloat(e.value) > parseFloat(e.record.not_pay_money)){
				e.value = parseFloat(e.record.not_pay_money);
			}
			//自动更细付款金额 = 未付款金额 - 优惠金额
			detailGrid.updateCell("pay_money", parseFloat(e.record.not_pay_money) - e.value, e.rowindex);
		}
		if(e.column.name == "pay_money"){
			//付款金额不能大于未开票金额-优惠金额
			if(parseFloat(e.value) > parseFloat(e.record.not_pay_money) - parseFloat(e.record.dis_money)){
				e.value = parseFloat(e.record.not_pay_money) - parseFloat(e.record.dis_money);
			}
		}
		return true;
	}
	
	// 编辑完明细表格单元格后   选择该行数据
	function f_onAfterEdit(e){
		if(e.column.name == "dis_money" || e.column.name == "pay_money"){
			if(e.record.check_row && e.record.check_row == "1"){
				var dis_money = 0;//存储  主表格优惠款金额
				var pay_money = 0;//存储  主表格付款金额
				var detailData =  detailGrid.getData();
				$.each(detailData, function(i, v){
					if(v.check_row && v.check_row == "1"){
						dis_money += parseFloat(v.dis_money);
						pay_money += parseFloat(v.pay_money);
					}
				})
				
				grid.updateCell('dis_money', dis_money, gridRowData);
				grid.updateCell('pay_money', pay_money, gridRowData);
				grid.updateCell('detail_data', JSON.stringify(detailData), gridRowData);
				
				updatePay_money();
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
		if (parseFloat(rowdata.not_pay_money) > 0)
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
	
	//全选明细
	function checkAllDetailRow(checked, element){
		var data = detailGrid.getData();
		var flag = checked ? "1" : "0";
		var dis_money = 0;
		var pay_money = 0;
		$.each(data, function(index, item){
			if(parseFloat(item.not_pay_money) > 0){
				detailGrid.updateCell("check_row", flag, index);
				detailGrid.updateCell("dis_money", 0, index);
				detailGrid.updateCell("pay_money", checked ? item.not_pay_money : 0, index);
				
				pay_money += (checked ? parseFloat(item.not_pay_money) : 0);
			}
		})
		
		var data1 = detailGrid.getData();
		grid.updateCell("dis_money", 0, gridRowData);
		grid.updateCell("pay_money", pay_money, gridRowData);
		grid.updateCell("detail_data", JSON.stringify(data1), gridRowData);

		updatePay_money();
	}
	
	//单选明细
	function checkDetailRow(checked, rowdata, rowindex, rowobj){
		detailGrid.updateCell('check_row', checked ? "1" : "0", rowdata);
		detailGrid.updateCell('dis_money', 0, rowdata);
		detailGrid.updateCell('pay_money', checked ? rowdata.not_pay_money : 0, rowdata);
		var pay_money = 0;//存储  主表格发票金额
		var detailData =  detailGrid.getData();
		$.each(detailData, function(index, item){
			if(parseFloat(item.not_pay_money) > 0){
				if(item.check_row && item.check_row == "1"){
					pay_money += parseFloat(item.not_pay_money);
				}
			}
		})
		
		//勾选行金额合计为0时 则表示整单保存
		grid.updateCell('dis_money', 0, gridRowData);
		grid.updateCell('pay_money', pay_money, gridRowData);
		grid.updateCell('detail_data', JSON.stringify(detailData), gridRowData);
		
		updatePay_money();
	}
	
	//改变表格数据需更新表单中发票金额
	function updatePay_money(){
		//更新表格合计行
		grid.updateTotalSummary();
		var pay_money =0;
		var data =  grid.getData();
		if(data.length > 0){
			$.each(data, function(i, v){
				pay_money += parseFloat(v.pay_money ? v.pay_money : 0);
			})
		}
		
		$("#pay_money").val(formatNumber(pay_money, '${p04005 }', 0));
	}
	
	//添加
	function add_open(){
		if(!liger.get("sup_code").getValue()){
			$.ligerDialog.error('请选择供应商！');
			return false;
		}
		if(!liger.get("pay_bill_type").getValue()){
			$.ligerDialog.error('请选择单据类别！');
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
				exists_ids += this.bill_id;
				index ++;
			}
		})
		
		$.ligerDialog.open({
			title:'选择发票',
			url: 'matBillByPayPage.do?isCheck=false',
			height: $(window).height() - 80,
			width: $(window).width() - 160,
			data: {
				sup_id: liger.get("sup_code").getValue(), 
				sup_text: liger.get("sup_code").getText(), 
				pay_bill_type: liger.get("pay_bill_type").getValue(),
				store_id: liger.get("store_code").getValue(),
				store_text: liger.get("store_code").getText(),
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
		var remove_pay_money = 0;
		var old_bill_count = parseInt($("#bill_count").val() ? $("#bill_count").val() : 0);
		$.each(data, function(){
			remove_pay_money += parseFloat(this.pay_money);
			old_bill_count = old_bill_count - 1;
		})
		var old_pay_money = $("#pay_money").val();
		$("#pay_money").val(formatNumber(parseFloat(old_pay_money) - remove_pay_money, '${p04005 }', 0));
		$("#bill_count").val(old_bill_count);
		//移除表格选中行
    	gridManager.deleteSelectedRow();
	}
	
	//保存
	function  save(){
		var pay_date = $("#pay_date").val();
		if(!pay_date){
			$.ligerDialog.warn('请填写发票日期！');
			return false;
		}
		var pay_bill_type = liger.get("pay_bill_type").getValue();
		if(!pay_bill_type){
			$.ligerDialog.warn('请选择发票类别！');
			return false;
		}
		var sup_id = liger.get("sup_code").getValue();
		if(!sup_id){
			$.ligerDialog.warn('请选择供应商！');
			return false;
		}
		var pay_dept_id = liger.get("pay_dept_id").getValue();
		if(!pay_dept_id){
			$.ligerDialog.warn('请选择付款部门！');
			return false;
		}
		var pay_type_code = liger.get("pay_type_code").getValue();
		if(!pay_type_code){
			$.ligerDialog.warn('请选择付款方式！');
			return false;
		}
		
		var data = grid.getData();
		if (data.length == 0){
			$.ligerDialog.error('请添加发票后再保存');
			return false;
		}
		//（java中处理）解析得明细结果集（明细存在勾选则取勾选的明细，不勾选则取所有明细）
		//1.判断库房是否一致；2.判断发票状态是否一致
		var store_code = liger.get("store_code").getValue();
		var store_error = "";
		var warn_msg = "";
		$.each(data, function(index, value){
			if(value.bill_id){
				if(store_code && value.store_id != store_code.split(",")[0]){
					store_error += '第'+(index+1)+'行<br>';
				}
			}
		})
		if(store_error.length > 0){
			$.ligerDialog.error(store_error+'发票与付款单仓库不一致，请修改！');
			return false;
		}
		
		thisSave();
	}
	
	function thisSave(){
		var data = grid.getData();
		var formPara={
			pay_bill_no: $("#pay_bill_no").val(),
			pay_date: $("#pay_date").val(),
			pay_bill_type: liger.get("pay_bill_type").getValue(),
			store_id: liger.get("store_code").getValue().split(",")[0],
			sup_id: liger.get("sup_code").getValue().split(",")[0],
			sup_no: liger.get("sup_code").getValue().split(",")[1],
			pay_dept_id: liger.get("pay_dept_id").getValue().split(",")[0],
			pay_type_code: liger.get("pay_type_code").getValue(), 
			pay_code: liger.get("pay_code").getValue(),
			pay_money: $("#pay_money").val(),
			cheq_no: $("#cheq_no").val(),
			bill_count: $("#bill_count").val(),
			note: $("#note").val(),
			is_init: $("input[name='is_init']:checked").val(), 
			allData: JSON.stringify(data)
		};

		ajaxJsonObjectByUrl("saveMatPay.do?isCheck=false", formPara, function(responseData){
			if(responseData.state == "true"){
				parentFrameUse().query();
				$.ligerDialog.confirm('是否继续添加付款单？', function (yes){
					if(yes){
						$("#pay_bill_no").val("");
						$("#note").val("");
						$("#pay_money").val("");
						autodate("#pay_date","yyyy-MM-dd");
						grid.deleteAllRows();
					}else{
						parentFrameUse().openUpdate(responseData.pay_id, 1);
						this_close();
					}
				});
			}
		});
	}
	
	//关闭页面
	function this_close(){
		frameElement.dialog.close();
	}
	
	//加载快捷键
	function loadHotkeys() {
		hotkeys('S', save);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		hotkeys('C', this_close);
	}
	
	//字典下拉框
	function loadDict(){
		//供应商下拉框
		autocomplete("#sup_code", "../../queryHosSupDictMethod.do?isCheck=false", "id", "text", true, true, '', false, '', 240);
		//库房
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_write:1}, false, '', 240);
		//部门
		autocomplete("#pay_dept_id", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, { is_last: '1',is_write:1 }, false, '', 240);
		//单据类型
		autoCompleteByData("#pay_bill_type", [{id: "0", text: "付款单"}, {id: "1", text: "退款单"}], "id", "text", true, true, "", true);
		//付款条件
		autocomplete("#pay_code", "../../queryMatPayTerm.do?isCheck=false", "id", "text", true, true);
        //付款方式下拉框
        autocomplete("#pay_type_code", "../../queryMatPayType.do?isCheck=false", "id", "text", true, true, "", true);
		
		
		autodate("#pay_date","yyyy-MM-dd");
		$("#pay_date").ligerTextBox({width: 160});
		
		$("#pay_bill_no").ligerTextBox({width: 160, disabled: true});
		$("#pay_money").ligerTextBox({width: 160, disabled: true, number: true, precision: '${p04006 }'});
		$("#cheq_no").ligerTextBox({width: 240});
		$("#bill_count").ligerTextBox({width: 160, disabled: true, number: true, precision: 0});
		$("#note").ligerTextBox({width: 533});
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
						付款单号：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="pay_bill_no" disabled="disabled" value="系统生成"/>
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>供应单位：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="sup_code" />
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>制单日期：
					</td>
					<td align="left" class="l-table-edit-td">
						<input class="Wdate" type="text" id="pay_date" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
					</td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>单据类别：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="pay_bill_type"  />
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>付款部门：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="pay_dept_id" />
					</td>
					
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						<font color="red">*</font>付款方式：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="pay_type_code"  />
					</td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						付款条件：
					</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="pay_code" />
					</td>
					
					<td align="right" class="l-table-edit-td" >仓库：</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="store_code" />
					</td>
					
					<td align="right" class="l-table-edit-td">付款金额：</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="pay_money" disabled="disabled" value="0"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">
						是否期初：
					</td>
					<td align="left" class="l-table-edit-td">
						<input name="is_init" type="radio" value="0" checked/>&nbsp;&nbsp;非期初
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="is_init" type="radio" value="1"/>&nbsp;&nbsp;期初
					</td>
					
					<td align="right" class="l-table-edit-td" >票据号：</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="cheq_no" />
					</td>
					
					<td align="right" class="l-table-edit-td" >发票张数：</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" id="bill_count" disabled="disabled" value="0"/>
					</td>
				</tr> 
				<tr>
					<td align="right" class="l-table-edit-td"  style="padding-left:20px;">
						摘要：
					</td>
					<td align="left" class="l-table-edit-td" colspan="4">
						<input type="text" id="note" />
					</td>
				</tr>
			</table>
		</div>
		<div position="center" >
			<div id="maingrid" ></div>
		</div>
	</div>
</body>
</html>
