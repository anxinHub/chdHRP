<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow: hidden;"  xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/stringbuffer.js"></script>
    <script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
	<script type="text/javascript">
	var grid;
	var gridManager = null;
	var paraMoney = '${p04005}';
	var paraPrice = '${p04006}';
     
	$(function (){
		$("#layout1").ligerLayout({ topHeight: 90, centerWidth: 888});
        loadDict();
        //loadForm();
        loadHead(null);	
		loadHotkeys();
		
        //库房监听事件：动态改变材料下拉列表
		$("#store_code").bind("change", function(){
			grid.columns[4].editor.grid.url = '../../../queryMatDuraStoreInvList.do?isCheck=false&store_id=' 
					+ liger.get("store_code").getValue().split(",")[0];
		})
	
	});  
	
	function  save(){
		//grid结束编辑
		grid.endEdit();
		
		if(!liger.get("make_date").getValue()){
			$.ligerDialog.warn('编制日期不能为空！');
			return false;
		}
		if(!liger.get("store_code").getValue()){
			$.ligerDialog.warn('库房不能为空！');
			return false;
		}
    	if($("#brief").val() && $("#brief").val().length > 100){
    		$.ligerDialog.warn('摘要长度不能大于100！');
			return false;
    	}
    	 
		var dura_detail_data = gridManager.rows;
		var targetMap = new HashMap();
		var msg = new StringBuffer();
		var rows = 0;
		if(dura_detail_data.length > 0){
  			$.each(dura_detail_data, function(i, v){ 
	  	      	if(v.inv_id){
  	      			var value="第"+(i+1)+"行";
	  	      		if(!v.amount && v < 0){
						msg.append(value+"数量为零或空 请输入;\n");
					}
	  	      		
	  	      		var key=v.inv_id +"|" + v.price + "|" + v.bar_code;
	  				if(!targetMap.get(key)){
	  					targetMap.put(key , value);
	  				}else{
	  					msg.append(targetMap.get(key)+"与"+value+"材料编码、单价、条形码不能重复;</br>");
	  				}
	  				rows += 1;
	  	      	}
 			}); 
  		}

		if(msg.toString()  != ""){
 			$.ligerDialog.warn(msg.toString(), '', '', {width: 450});
 			return false;
 		}

 		if(rows == 0){
 			$.ligerDialog.warn('请选择材料');
 			return false;
 		}

		var formPara={
			check_no: $("#check_no").val(), 
			make_date: $("#make_date").val(), 
 			store_id: liger.get("store_code").getValue().split(",")[0], 
 			store_no: liger.get("store_code").getValue().split(",")[1], 
 			brief: $("#brief").val(), 
 			detailData: JSON.stringify(dura_detail_data)
		};
		//alert(JSON.stringify(dura_detail_data));
	
        ajaxJsonObjectByUrl("addMatDuraCheckStore.do", formPara, function(responseData){
            if(responseData.state=="true"){
            	parentFrameUse().query();
            	$.ligerDialog.confirm('是否继续添加盘点单?', function (yes){
            		if(yes){
            			$("#brief").val("");
            			loadDict();
                        grid.reload();
                        is_addRow();
            		}else{
            			parentFrameUse().openUpdate(responseData.update_para);
            			this_close();
            		}
            	});
            }
        });
    }
     
	function loadForm(){
		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement: function (lable, element){
				if (element.hasClass("l-textarea")){
					element.ligerTip({ content: lable.html(), target: element[0] }); 
				}else if (element.hasClass("l-text-field")){
					element.parent().ligerTip({ content: lable.html(), target: element[0] });
				}else{
					lable.appendTo(element.parents("td: first").next("td"));
				}
			}, 
			success: function (lable){
				lable.ligerHideTip();
				lable.remove();
			}, 
			submitHandler: function (){
				$("form .l-text, .l-textarea").ligerHideTip();
			}
		});
		//$("form").ligerForm();
	}     
	
	function loadDict(){
        //字典下拉框
    	$("#check_no").ligerTextBox({width: 160, disabled: true}); 
            
		//字典下拉框
		autocompleteAsync("#store_code", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true, "", true);

		$("#make_date").ligerTextBox({width: 160});
		autodate("#make_date");
    	//$("#brief").ligerTextBox({width: 380}); 
    	
		//格式化按钮
		$("#save").ligerButton({ click: save, width: 90 });
		$("#close").ligerButton({ click: this_close, width: 90 });
	} 
    
	function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '交易编码', name: 'bid_code', align: 'left', width: 100
			}, { 
				display: '材料变更号', name: 'inv_no', align: 'left', width: 100, hide:true
			}, { 
				display: '材料编码', name: 'inv_code', align: 'left', width: 110, 
				totalSummary: {
					align: 'right', 
					render: function (suminf, column, cell) {
						return '<div>合计：</div>';
					}
				}
			}, { 
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', align: 'left', width: 240
			}, { 
				display: '规格型号', name: 'inv_model', align: 'left', width: 180
			}, { 
				display: '计量单位', name: 'unit_name', align: 'left', width: 60
			}, { 
				display: '单价', name: 'price', align: 'right', width: 90, 
				render: function(rowdata, rowindex, value) {
					return value == null ? "" : formatNumber(value, paraPrice, 0);
				}
			}, { 
				display: '条形码(E)', name: 'bar_code', align: 'left', width: 120
			}, { 
				display: '账面数量', name: 'cur_amount', align: 'left', width: 80, 
				totalSummary: {
					align: 'left', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '盘点数量(E)', name: 'chk_amount', align: 'left', width: 80, editor: {type: 'float'}, 
				render: function (rowdata, rowindex, value) {
	           		 if(rowdata.chk_amount==0){
	           			 rowdata.chk_amount = "0";
	           			 return "0" ;
	           		 }else{
	           			 return value;
	           		 }
	           	},
				totalSummary: {
					align: 'left', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '盈亏数量', name: 'amount', align: 'left', width: 80,  
				totalSummary: {
					align: 'left', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, 2, 1)+ '</div>';
					}
				}
			}, { 
				display: '盈亏金额', name: 'amount_money', align: 'right', width: 90, 
				render: function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, paraMoney, 0);
					return value == null ? "" : formatNumber(value, paraMoney, 1);
				}, 
				totalSummary: {
					align: 'right', 
					render: function (suminf, column, cell) {
						return '<div>' + formatNumber(suminf.sum ==null ? 0: suminf.sum, paraMoney, 1)+ '</div>';
					}
				}
			}, { 
				display: '生产厂商', name: 'fac_name', align: 'left', width: 180
			}, { 
				display: '备注(E)', name: 'note', align: 'left', width: 240, editor: {type: 'text'}
			} ], 
			usePager: false, width: '100%', height: '100%', enabledEdit: true, fixedCellHeight: true, heightDiff: -20, 
			onBeforeEdit: f_onBeforeEdit, onBeforeSubmitEdit: f_onBeforeSubmitEdit, onAfterEdit: f_onAfterEdit, 
			selectRowButtonOnly: true, checkbox: true, rownumbers: true, isScroll: true, isAddRow:false,
			toolbar: { items: [ { 
				text: '删除（<u>D</u>）', id: 'delete', click: deleteRow, icon: 'delete' 
			}, { 
				line: true 
			}, { 
				text: '选择材料（<u>X</u>）', id: 'add', click: choice_inv, icon: 'add'  
			}, { 
				line: true 
			}, { 
				text: '导入当前库存', id: 'import', click: importInv, icon: 'up'  
			}] }, 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('D', deleteRow);
		hotkeys('X', choice_inv);
	}
    
    //选择材料
    function choice_inv(){
    	if(liger.get("store_code").getValue() == null){
			$.ligerDialog.warn('请选择库房');
			return ;
		}
		
		var store_id = liger.get("store_code").getValue();
		var store_text = liger.get("store_code").getText();
		
		parent.$.ligerDialog.open({
			url: "hrp/mat/dura/check/store/choiceInvPage.do?isCheck=false&store_id="+store_id+"&store_text="+store_text, 
			height : $(window).height(),
			width : $(window).width(),
			title: '选择材料', 
			modal: true, 
			showToggle: false, 
			showMax: true, 
			showMin: true, 
			isResize: true, 
			parentframename : window.name //用于parent弹出层调用本页面的方法或变量
		});
    }
    
    //关闭
 	function this_close(){
 		frameElement.dialog.close();
 	}
    
    var rowindex_id = "";
	var column_name = "";
	function f_onBeforeEdit(e) {
		rowindex_id = e.rowindex;
		column_name = e.column.name;		
	}

    function f_onAfterEdit(e){
    	if("chk_amount" == e.column.name){
    		
    		gridManager.updateCell('amount', (e.record.chk_amount -e.record.cur_amount), e.record); 
    		gridManager.updateCell('amount_money', (e.record.chk_amount -e.record.cur_amount)*e.record.price, e.record); 
    	}
    	if("amount" == column_name || "pay_money" == column_name || "income_money" == column_name){
        	//更新合计行
    		grid.updateTotalSummary();
    	}
    }
    
	// 编辑单元格提交编辑状态之前作判断限制
	function f_onBeforeSubmitEdit(e) {
		/*
		if (e.column.name == "inv_id" && e.value == ""){
			//$.ligerDialog.warn('请选择材料！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		if (e.column.name == "amount" && e.value == 0){
			//$.ligerDialog.warn('数量不能为0！');
			//grid.setCellEditing(e.record, e.column, true);
			return false;
		}
		*/
		return true;
	} 
	
	//移除行
	function deleteRow(){ 
		
		gridManager.deleteSelectedRow();
    }
	
	function importInv(){
		
		if(liger.get("store_code").getValue() == null){
			$.ligerDialog.warn('请选择库房');
			return ;
		}
		
		var store_id = liger.get("store_code").getValue().split(",")[0];
		
		ajaxJsonObjectByUrl("queryMatInvByBalance.do?isCheck=false", {store_id : store_id},
			function(responseData){
	          	grid.loadData(responseData);
			}
		);
	}
	
	function addParts(addData){
		
		$.each(addData, function(i, v) { 
			
			var rowData={
				amount: v.amount,
				amount_money: v.amount_money,
				bar_code: v.bar_code,
				bid_code: v.bid_code,
				chk_amount: v.chk_amount,
				copy_code: v.copy_code,
				cur_amount: v.cur_amount,
				fac_code: v.fac_code,
				fac_id: v.fac_id,
				fac_name: v.fac_name,
				group_id: v.group_id,
				hos_id: v.hos_id,
				inv_code: v.inv_code,
				inv_id: v.inv_id,
				inv_no: v.inv_no,
				inv_model: v.inv_model,
				inv_name: v.inv_name,
				price: v.price,
				unit_code: v.unit_code,
				unit_name: v.unit_name,
			};
			
			grid.addRow(rowData);
	  	});
	}
    </script>

	</head>
  
	<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div position="top">
			<form name="form1" method="post"  id="form1" >
				<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			       <tr>
			       		<td align="right" class="l-table-edit-td"  >
			       			<span style="color: red">*</span>单据号：
			       		</td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="check_no" type="text" id="check_no" disabled="disabled" ltype="text" value="自动生成"/>
			            </td>
			            
			        	<td align="right" class="l-table-edit-td"  >
			        		<span style="color: red">*</span>编制日期：
			        	</td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="make_date" type="text" id="make_date" ltype="text"  validate="{required: true}"  class="Wdate" onFocus="WdatePicker({isShowClear: true, readOnly: false, dateFmt: 'yyyy-MM-dd'})"/>
			            </td>

			           	<td align="right" class="l-table-edit-td"  >
			           		<span style="color: red">*</span>库房：
			           	</td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required: true}" />
			            </td>
			        </tr>  
					<tr>
			            
			            <td align="right" class="l-table-edit-td"  >
			            	摘      要：
			            </td>
			            <td align="left" class="l-table-edit-td" colspan="3">
			            	<textarea class="l-textarea" name="brief" id="brief" rows="3" style="width: 380px;"></textarea>
			            </td>
			        </tr> 
			    </table>
			</form>
		</div>
	
		<div position="center" >
			<div id="maingrid"></div>
		
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 5px;">
				<tr> 
					<td align="center" class="l-table-edit-td">
						<button id="save" accessKey="B"><b>保存（<u>B</u>）</b></button> &nbsp;&nbsp;
						<button id="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
					</td>
				</tr>
			</table>
		</div>
		</div>
    </body>
</html>
