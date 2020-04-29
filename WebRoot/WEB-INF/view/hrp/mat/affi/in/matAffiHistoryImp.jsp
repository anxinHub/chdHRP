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
<script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	var flag=1; 
	$(function() {
		
		loadDict();//加载数据
	    loadHead(null);
		//query();
	});
	
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]}); 
		
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()});
		grid.options.parms.push({name : 'in_no',value : $("#in_no").val()});
		grid.options.parms.push({name : 'bus_type_code',value : liger.get("bus_type_code").getValue()});
		grid.options.parms.push({name : 'flag',value : 1 });
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	

	function loadHead() {
		grid = $("#maingrid").ligerGrid({
		       columns: [ 
		                 {    display: '入库单号', name: 'in_no', align: 'left' ,width : '150'},
		                 //{ display: '供应商', name: 'sup_name', align: 'left',width : '120' 
		                 //}, 
		                 { display: '材料编码', name: 'inv_code', align: 'left',width : '120'
					     }, { display: '材料名称', name: 'inv_name', align: 'left',width : '120'
					     }, { display: '规格型号', name: 'inv_model', align: 'left',width : '150'
						 }, { display: '计量单位', name: 'unit_name', width: 60, align: 'left'
						 }, { display: '生产厂商', name: 'fac_name', width: 120, align: 'left'
						 }, { display: '数量', name: 'amount', width: 80,  align: 'right',
						 }, { display: '单价', name: 'price', width: 80, align: 'right',
								render: function (rowdata, rowindex, value) {
									rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
									return value == null ? "" : formatNumber(value, '${p04006 }', 1);
								}
						  }, { display: '金额', name: 'amount_money', width: 80, align: 'right',
								render: function (rowdata, rowindex, value) {
									rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
									return value == null ? "" : formatNumber(value, '${p04005 }', 1);
							 } 
						 }, { display: '注册证号', name : 'cert_code',  width : 120, align : 'left',
						 }, { display: '生产日期', name: 'fac_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 80,
						 }, { display: '生产批号', name: 'batch_no', minWidth: 80, align: 'left',
						 }, { display: '有效日期', name: 'inva_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 80,
						 //}, { display: '批次', name: 'batch_sn', minWidth: 80, align: 'left',
						 }, { display: '灭菌日期', name: 'disinfect_date', type: 'date', align: 'left', format: 'yyyy-MM-dd', width: 80,
						 //}, { display: '灭菌批号', name: 'disinfect_no', minWidth: 80, align: 'left',
						 }, { display: '条形码', name: 'sn', width: 80, align: 'left',
						 }, { display: '是否个体码', name: 'is_per_bar', width: 60, align: 'left',
								render: function (rowdata, rowindex, value) {
									if (value == 0) {
										return "否";
									} else if (value == 1) {
										return "是";
									} else {
										rowdata.is_per_bar = 0;
										return "否";
									}
								}
						 }, { display: '院内码', name: 'bar_code', width: 80, align: 'left',
						 }, { display: '包装单位', name: 'pack_name',  width: 80, align: 'left',
						 }, { display: '转换量', name: 'num_exchange', width: 60, type: 'float', align: 'left',
						 }, { display: '包装件数', name: 'num', width: 80, type: 'float', align: 'left',
						 }, { display: '包装单价', name: 'pack_price', width: 80, align: 'right',
								render: function (rowdata, rowindex, value) {
									rowdata.pack_price = value == null ? "" : formatNumber(value, '${p04005 }', 0);
									return value == null ? "" : formatNumber(value, '${p04005 }', 1);
								}
						 },  { display: '批发单价', name: 'sale_price', width: 80, align: 'right',
								render: function (rowdata, rowindex, value) {
									rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
									return value == null ? "" : formatNumber(value, '${p04006 }', 1);
								}
						 }, { display: '批发金额', name: 'sale_money', width: 80, align: 'right',
								render: function (rowdata, rowindex, value) {
									rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
									return value == null ? "" : formatNumber(value, '${p04005 }', 1);
								}
						}, { display: '零售单价', name: 'sell_price', width: 80, align: 'right',
								render: function (rowdata, rowindex, value) {
									rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
									return value == null ? "" : formatNumber(value, '${p04072 }', 1);
								}
						}, { display: '零售金额', name: 'sell_money', width: 80, align: 'right',
								render: function (rowdata, rowindex, value) {
									rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
									return value == null ? "" : formatNumber(value, '${p04073 }', 1);
								}
						}, { display: '货位名称', name: 'location_name',  width: 80, align: 'left',
						}, { display: '备注', name: 'note', width: 120, align: 'left',
						}, { display: '明细', name: 'detail_id', width: 120, align: 'left',hide:true
						}
					 ],
		             dataAction: 'server',dataType: 'server',usePager:true,
		             url:'queryMatAffiInHistoryDetail.do?isCheck=false',
		             width: '95%', height: '95%', checkbox: true, rownumbers:true,delayLoad:true,
		             selectRowButtonOnly:true, isScroll :true,
		             toolbar: { items: [
		                 		{ text: '查询（<u>Q</u>）', id:'query', click: query, icon:'search' },
		                    	{ line : true},
		                    	{ text: '生成入库单（<u>I</u>）', id:'add', click: imp, icon:'add' },
		                    	{ line : true},
		                    	{ text: '关闭（<u>C</u>）', id:'close', click: this_close, icon:'close' }
		                 ]}
		             });

		    	gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	//获取选中行
	function getSelectRows() {
		var rows = grid.getCheckedRows();
		return rows;
	}
	
	//生成入库单
	function imp(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行!');
			return;
		}else{
			var detail_rows = new StringBuffer();
			detail_rows.append("[");
			$.each(data, function(index, data){
				if(index != 0){
					detail_rows.append(",");
				}
				detail_rows.append('{"detail_id":').append(data.detail_id).append(',');
				detail_rows.append('"inv_id":').append(data.inv_id).append('}');; 
			});
			detail_rows.append("]");
			
			if(liger.get("bus_type_code").getValue()=="27"){
				flag=1;
			}
			if(liger.get("bus_type_code").getValue()=="29"){
				flag=-1;
			}
			
			var formPara = {
				store_id :  '${store_id}'.split(",")[0],
				sup_id :  '${sup_id}'.split(",")[0],
				flag : flag,
				allData : detail_rows.toString()
			};
		 
			$.ligerDialog.confirm('确定生成入库单吗?', function (yes){
				if(yes){
					ajaxJsonObjectByUrl("queryMatAffiInHisDetail.do?isCheck=false", formPara, function(responseData) {
						//console.log(responseData)
						if(responseData.Rows.length > 0){
							parent.grid.options.data = {Rows:responseData.Rows};
							parent.grid._setData();
							parent.grid.addRow();
						}
						this_close();
					});
				}
			});	
		}
	}
	
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	
	
	//字典加载
	function loadDict() {
		
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "",false,"","","",160);//供应商
        //liger.get("sup_code").setValue("${sup_id}");
		//liger.get("sup_code").setText("${sup_text}");
		
		$("#store_code").ligerComboBox({width:160,disabled:true,cancelable: false});
        liger.get("store_code").setValue("${store_id}");
		liger.get("store_code").setText("${store_text}");
		
		autocomplete("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true,{codes:'27,29'},false,"29");//业务类型
		 
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        
        $("#begin_date").ligerTextBox({width:100});
        $("#end_date").ligerTextBox({width:100});
        $("#store_code").ligerTextBox({width:160});
        $("#sup_code").ligerTextBox({width:160});
		$("#inv_code").ligerTextBox({width:160});
		$("#bus_type_code").ligerTextBox({width:160});
		$("#in_no").ligerTextBox({width:160});
		
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">入库日期：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
				<table>
					<tr>
						<td align="left">
							<input class="Wdate" name="begin_date" id="begin_date" type="text" 
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
						<td align="right" class="l-table-edit-td">至：</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text"
							onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
					</tr>
				</table>
			</td>
			
			<td align="right" class="l-table-edit-td"  width="10%"><font color="red">*</font>仓库：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
	            <input name="store_code" type="text" id="store_code" requried="true" ltype="text" validate="{required:true}" />
	        </td>
	        
	        
			<td align="right" class="l-table-edit-td"  width="10%"><font color="red">*</font>供货单位：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
	            <input name="sup_code" type="text" id="sup_code" requried="true" ltype="text" validate="{required:true}" />
	        </td>
	        
	    </tr>
	    <tr>
	    	<td align="right" class="l-table-edit-td"  width="10%">单据号：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
	            <input name="in_no" type="text" requried="false"  id="in_no"  />
	        </td>
	        
	        
	        <td align="right" class="l-table-edit-td"  width="10%">业务类型：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
	            <input name="bus_type_code" type="text" requried="false"  id="bus_type_code"  />
	        </td>
	        
	        
	        <td align="right" class="l-table-edit-td"  width="10%">材料信息：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
	            <input name="inv_code" type="text" requried="false"  id="inv_code"  />
	        </td>
	        
		</tr>
	</table>
	<div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
	</div>
		
</body>
</html>
