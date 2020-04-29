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
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     var state = '${medOrderMain.state}';
     $(function (){
    	
		loadDict()//加载下拉框
		loadHead();
		
		$('#showUnit').show();
		$('#unit').hide();
		
		queryDetail();
     });  
     
     function queryDetail(){
    	grid.options.parms = [];
 		grid.options.newPage = 1;
 		//根据表字段进行添加查询条件
 		
     	grid.options.parms.push({name : 'order_id',value : $("#order_id").val()}); 
     	
 		//加载查询条件
 		grid.loadData(grid.where);
     }
     
  
    function loadDict(){
    	//字典下拉框
    	alert('${medOrderMain.sup_id}');
    	alert('${medOrderMain.sup_no}');
    	autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.sup_id},${medOrderMain.sup_no}');//供应商
    	autocomplete("#take_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.take_hos_id}');//收货单位
		autocomplete("#pay_hos_id", "../../../sys/queryHosInfoDictPerm.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.pay_hos_id}');//付款单位
		autoCompleteByData("#order_type", medOrderMain_orderType.Rows, "id", "text", true, true, "", false, '${medOrderMain.order_type}');//订单类型
    	autocomplete("#stock_type_code", "../../queryMedStockType.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.stock_type_code}');//采购类型
    	autocomplete("#pay_type", "../../queryMedPayType.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.pay_code}');//付款方式
		autocomplete("#dept_code", "../../queryPurDept.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.dept_id},${medOrderMain.dept_no}');//采购部门
		autocomplete("#stocker", "../../queryMedStockEmp.do?isCheck=false", "id", "text", true, true, "", false, '${medOrderMain.stocker}');//采购人
		
		
        $("#order_code").ligerTextBox({width:160, disabled:true});
        $("#order_date").ligerTextBox({width:160});
        $("#sup_code").ligerTextBox({width:160});
        
        $("#order_type").ligerTextBox({width:160});
        $("#take_hos_id").ligerTextBox({width:160});
        $("#pay_hos_id").ligerTextBox({width:160});
        
        $("#stock_type_code").ligerTextBox({width:160});
        $("#pay_type").ligerTextBox({width:160});
        
        $("#dept_code").ligerTextBox({width:160});
        $("#stocker").ligerTextBox({width:160});
        
        $("#arr_address").ligerTextBox({width:200});
        $("#note").ligerTextBox({width:200});
        
        
		$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
	
    function loadHead() {
		grid = $("#maingrid").ligerGrid({
			columns : [
			           {display : '药品编码',name : 'inv_code',minWidth : 100,align : 'left'},
			           {display : '药品名称(E)',name : 'inv_name',minWidth : 120,align : 'left'}, 
			           {display : '规格型号', name : 'inv_model', minWidth : 80, align : 'left'}, 
			           {display : '计量单位', name : 'unit_name', minWidth : 80, align : 'left'},  
			           {display : '包装单位(E)', name : 'pack_name', minWidth : 80, align :'left'}, 
			           {display : '包装数量(E)', name : 'num', minWidth : 80, type : 'number',align : 'right'}, 
			           {display : '采购数量(E)', name : 'amount',    minWidth : 80, type : 'number',align : 'right',
							render : function(rowdata, rowindex, value) {
								return rowdata.amount == null ? "" : formatNumber(rowdata.amount, 2, 1);
							}
						}, {display : '单价(E)', name : 'price', minWidth : 80, align : 'right',
							render : function(rowdata, rowindex, value) {
								return rowdata.price == null ? "" : formatNumber(rowdata.price, '${p08006}', 1);
							}
						}, {display : '金额', name : 'amount_money', minWidth : 80, type : 'number', align : 'right',
							render : function(rowdata, rowindex, value) {
								return rowdata.amount_money == null ? "" : formatNumber(rowdata.amount_money, '${p08005}', 1);
							}
						}
						
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : false,
			url : 'queryMedAffiInOrderImpDetail.do?isCheck=false',
			width : '90%',
			height : '67%',
			alternatingRow : true,			
			isScroll : true,
			rownumbers : true,
			delayLoad : true,//初始化明细数据
			selectRowButtonOnly : true//heightDiff: -10,
			
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		
	}
   
	function getData(){
		var manager = $("#maingrid").ligerGetGridManager();
		var data = manager.getData();
		return JSON.stringify(data);
	} 

	
	//键盘事件
	function loadHotkeys() {
		hotkeys('P', printDate);//打印
		hotkeys('C', this_close);//关闭
	}
	
	
	//打印
	function printDate(){
	}
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	//隐藏或显示  采购单位、请购单位、付款单位
    function change(){
		
		if(liger.get("order_type").getValue()=='1'){
			$('#showUnit').hide();
			$('#unit').show();
		}else{
			$('#showUnit').show();
			$('#unit').hide();
		}
   	 	
    }
    </script>
  
</head>
  
<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
	        <tr>
	            <td align="right" class="l-table-edit-td" >订单编号：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="order_id" type="hidden" id="order_id" value="${medOrderMain.order_id}" ltype="text" />
	            	<input name="order_code" type="text" id="order_code" readOnly value="${medOrderMain.order_code}" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>编制日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="order_date" id="order_date" required="true" value="${medOrderMain.order_date}" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td"><font color="red">*</font>供应商：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>订单类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="order_type" type="text" id="order_type"  ltype="text" required="true" onChange="change();" validate="{required:true,maxlength:20}"/>
	            </td>
	           
		            	<span id="showUnit">
		            		<td align="right" class="l-table-edit-td"  ><font color="red">*</font>收货单位：</td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="take_hos_id" type="text" id="take_hos_id" required="true" ltype="text" validate="{required:true}" />
				            </td>
				            
				            <td align="right" class="l-table-edit-td" ><font color="red">*</font>付款单位：</td>
				            <td align="left" class="l-table-edit-td">
				            	<input name="pay_hos_id" type="text" id="pay_hos_id" required="true" ltype="text" validate="{required:true}" />
				            </td>
		            	</span>
		            	<span id="unit">	
		            		<td></td>
		            		<td></td>
		            		<td></td>
		            		<td></td>
		            	</span>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td" ><font color="red">*</font>采购方式：</td>
	             <td align="left" class="l-table-edit-td">
	            	<input id="typeA" name="pur_type" type="radio" value="1" checked="checked" />自购订单
	            	<input id="typeB" name="pur_type" type="radio" value="2" />统购订单
           		 </td>
	        	
	            <td align="right" class="l-table-edit-td">采购类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stock_type_code" type="text" id="stock_type_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" >付款条件：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="pay_type" type="text" id="pay_type"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	           
	        </tr>
	        <tr>
	        	<td align="right" class="l-table-edit-td" >采购部门：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dept_code" type="text" id="dept_code"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	        	<td align="right" class="l-table-edit-td" >采购员：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="stocker" type="text" id="stocker"  ltype="text" required="false" validate="{required:true,maxlength:20}"/>
	            </td>
	            
	            <td></td>
	            <td></td>
	           
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" >到货地址：</td>
	            <td align="left" class="l-table-edit-td" >
	            	<input name="arr_address" type="text" id="arr_address" ltype="text"  value="${medOrderMain.arr_address}" validate="{required:true,maxlength:50}" />
	            </td>
	            <td align="right" class="l-table-edit-td" >备注：</td>
	            <td align="left" class="l-table-edit-td" colspan="3">
	            	<input name="note" type="text" id="note" ltype="text" value="${medOrderMain.note}" validate="{required:true,maxlength:50}" />
	            </td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					状态：已审核
				</td>
				<td align="center" class="l-table-edit-td" >
					制单人：${medOrderMain.maker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					审核人：${medOrderMain.checker_name}
				</td>
				<td align="center" class="l-table-edit-td" >
					审核日期：${medOrderMain.check_date}
				</td>
			</tr>
			<tr>	
				<td align="center" class="l-table-edit-td" colspan="4">
					
					<button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp;
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
