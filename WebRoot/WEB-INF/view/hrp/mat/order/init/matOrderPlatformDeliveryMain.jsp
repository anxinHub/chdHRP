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
	var time = new Date(); //获得当前时间
	var year = time.getFullYear();//获得年、月、日
	var month = time.getMonth()+1;
	var day = time.getDate(); 
	var date = year+"年"+month+"月"+day;      
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
	
    });
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
	 			display: 'hrp订单编号', name: 'order_id', align: 'left', width: 110
		 		}, { 
		 			display: 'hrp订单明细编号', name: 'order_detail_id', align: 'left', width: 110
		 		}, { 
		 			display: '省平台订单编号', name: 'platform_order_id', align: 'left', width: 110
		 		}, { 
		 			display: '省平台订单明细编号', name: 'platform_detail_id', align: 'left', width: 110
		 		}, { 
		 			display: '配送明细编号 ', name: 'distribution_id', align: 'left', width: 110
		 		},{ 
		 			display: '发票号', name: 'invoice_id', align: 'left', width: 100
		 		},
		 		{ 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: 100
		 		},
		 		{ 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: 100
		 		},
		 		{ 
		 			display: '批号', name: 'batchrecord_id', align: 'left',width: 120,
		 		},  { 
		 			display: '配送数量', name: 'distribution_count', align: 'left', width: 60
		 		}, { 
		 			display: '配送时间', name: 'distribution_time', align: 'left', width: 90, formatter: "yyyy-MM-dd"
		 		}, { 
		 			display: '入库数量', name: 'warehouse_count', align: 'left', width: 50,type : 'float',align : 'right',
		 			totalSummary : {
						align : 'right',
						render : function(suminf, column,cell) {
								return '<div>'+ formatNumber(suminf.sum == null ? 0: suminf.sum,0, 0)+ '</div>';
						}
					}
		 		},  { 
		 			display: '入库时间', name: 'warehouse_time', align: 'left', width: 80
		 		},{display : '订单明细状态', name : 'line_state', align : 'left', width : 200,
					render : function(rowdata, rowindex,value) {
						if(rowdata.line_state == 0){
							
							return "已保存待提交";
						}if(rowdata.line_state == 1){
							return "已提交待响应";
						}if(rowdata.line_state == 2){
							return "已响应待配送";
						}if(rowdata.line_state == 3){
							return "拒绝响应";
						}if(rowdata.line_state == 4){
							
							return "已配送待收货";
						}if(rowdata.line_state == 5){
							return "拒绝配送";
						}if(rowdata.line_state == 6){
							return "未及时配送";
						}if(rowdata.line_state == 7){
							return "收货中";
						}if(rowdata.line_state == 8){
							return "已收货";
						}if(rowdata.line_state == 9){
							return "已撤单";
						}
					}	
				} ],
			dataAction: 'server',dataType: 'server',usePager:false,url:'../../storage/query/queryPlatformDelivery.do?isCheck=false&order_id=${order_id}',
			width: '100%', height: '100%',rownumbers:true,checkBoxDisplay : isCheckDisplay,checkbox : true,
			enabledEdit : true,delayLoad: false,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
					{ text: '更新订单状态', id:'save', click: ystate,icon:'save'  }
			]},
			
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    //根据省平台的状态来更新hrp订单明细中的状态,用于后期材料入库
    function ystate(){
   	 var allData = gridManager.getData();
   	 
   	 var order_id = allData[0].order_id;   
   	 
   	 ajaxJsonObjectByUrl("stateMatOrderInit.do?isCheck=false&order_id="+order_id,null,function(responseData){
	         if(responseData.state=="true"){
	        	 parentFrameUse().query();
	        	 loadHead(null);	
	         }
	    });	 
   	 
    }
    
    function isCheckDisplay(rowdata) {
    	
		if (rowdata.line_state == 8)
			return true;
		return false;
	}


    function loadDict(){
		//字典下拉框
		
		
		
        autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, { sel_flag: 'in' }, true);
		autocompleteAsync("#store_code", "../../queryMatStoreStocker.do?isCheck=false", "id", "text", true, true, {is_com : 0}, true);
		var store_id = liger.get("store_code").getValue().split(",")[0];
		
		autocompleteAsync("#req_store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {is_com : 0}, false);
		
		autocompleteAsync("#sup_code", "../../queryHosSupDictDisable.do?isCheck=false", "id", "text", true, true, "", false, false, 160);
		liger.get("sup_code").setValue('${matOrderMain.sup_id},${matOrderMain.sup_no}');
		liger.get("sup_code").setText('${matOrderMain.sup_code} ${matOrderMain.sup_name}');
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
		$("#in_date").ligerTextBox({ width: 160 });
		autodate("#in_date");//默认当前日期
		$("#bus_type_code").ligerTextBox({ width: 160 });
		$("#store_code").ligerTextBox({ width: 160 });
		$("#stock_type_code").ligerTextBox({ width: 160 });
		
		autocomplete("#examiner", "../../queryMatStockEmp.do?isCheck=false", "id", "text", true, true, '', false);
		
		$("#examiner").ligerTextBox({width:160});	
		
    	$("#createIn").ligerButton({click: createIn, width:90});
	}  
    
    function createIn(){
    	
    	grid.endEdit();
    	var data = gridManager.getCheckedRows();
		if (data.length == 0){
			$.ligerDialog.error('请选择行');
			return;
		}
    	//var allDate=gridManager.getData();
    	
		var formPara = {
			in_no: $("#in_no").val(),
			bus_type_code: liger.get("bus_type_code").getValue(),
			store_id: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0],
			store_no: liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[1],
			in_date: $("#in_date").val(),
			stocker: liger.get("stocker").getValue() == null ? "" : liger.get("stocker").getValue(),
			sup_id: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0],
			sup_no: liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[1],
			stock_type_code: liger.get("stock_type_code").getValue() == null ? "" : liger.get("stock_type_code").getValue(),
			examiner: liger.get("examiner").getValue() == null ? "" : liger.get("examiner").getValue(),
			come_from : 2,
			page_details:JSON.stringify(data),
			order_id:"${order_id}"
		};
		ajaxJsonObjectByUrl("../../storage/query/createInByPlatfromOrder.do?isCheck=false", formPara, function (responseData) {
			if (responseData.state == "true") {
				parentFrameUse().query();
				//parentFrameUse().update_mat_in(responseData.update_para);
				//this_close();
			}else{
				$.ligerDialog.error(responseData.errMsg);
			}
		});
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
<form name="form1" method="post"  id="form1" >
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
			<td align="right" class="l-table-edit-td" width="10%">
				<span  style="color:red">*</span>供货单位：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:false}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%"><span style="color:red">*</span>采购类型：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="stock_type_code" type="text" id="stock_type_code" ltype="text" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" width="10%">采购员：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="stocker" type="text" id="stocker" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" width="10%">验收员：</td>
			<td align="left" class="l-table-edit-td" width="20%">
				<input name="examiner" type="text" id="examiner" ltype="text" validate="{required:false,maxlength:20}" />
			</td>
		</tr>
	</table>
	</form>
	<div id="maingrid"></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
		<tr>	
			<td align="center" class="l-table-edit-td" colspan="3">
				<button id ="createIn" accessKey="P"><b>生成入库单（<u>P</u>）</b></button>
				&nbsp;&nbsp; 
			</td>
		</tr>
	</table>
</body>
</html>
