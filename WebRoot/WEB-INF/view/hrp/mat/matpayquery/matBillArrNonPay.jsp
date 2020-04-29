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
    var show_detail=0;
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
    		bill_money:function(value){//发票金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			},
    		coupon_money:function(value){//优惠金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			}, 
			payable_money:function(value){//应付金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			},
			payed_money:function(value){//已付金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			},
			nopay_money:function(value){//未付金额
				return formatNumber(value==null?0:value, '${p04005 }', 1);
			}
	}; 
    
    $(function ()
    {
    	
   
        loadDict()//加载下拉框
    	//加载数据
    	//loadHead(null);	
        
     	$("#set_code").bind("change",function(){
	    	if(liger.get("set_code").getValue()){
	    		liger.get("store_code").setValue("");
				liger.get("store_code").setText("");
	   	 		$("#store_code").ligerComboBox({disabled:true});
	   	 		grid.toggleCol('02', true);
	    	}else{
	    		$("#store_code").ligerComboBox({disabled:false});
	    		grid.toggleCol('02', false);
	    	}
	    	
		});
    	$("#store_code").bind("change",function(){
	    	if(liger.get("store_code").getValue()){
	    		liger.get("set_code").setValue("");
				liger.get("set_code").setText("");
	   	 		$("#set_code").ligerComboBox({disabled:true});
	   	 		grid.toggleCol('03', true);
	    	}else{
	    		$("#set_code").ligerComboBox({disabled:false});
	    		grid.toggleCol('03', false);
	    	}
	    	
		});
    	
    	showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0 ;
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
       	grid.options.parms.push({
			name : 'begin_date',
			value : $('#begin_date').val()
		}); 
        
       	grid.options.parms.push({
			name : 'end_date',
			value : $('#end_date').val()
		}); 
        
    	grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]});
		grid.options.parms.push({
			name : 'sup_type_code',
			value : liger.get("sup_type_code").getValue() == null ? "" : liger.get("sup_type_code").getValue().split(",")[0]
		}); 
		
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_code").getValue() == null ? "" : liger.get("sup_code").getValue().split(",")[0]
		});
		
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		});
		
		grid.options.parms.push({
			name : 'stocker',
			value : liger.get("emp_id").getValue() == null ? "" : liger.get("emp_id").getValue().split(",")[0]
		});
		grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue()});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	
    	if(show_detail=="1"){
    		grid = $("#maingrid").ligerGrid({
    			columns: [{ 
    		 			display: '供货单位', name: 'sup_name', align: 'left', minWidth: '200'
    		 		}, { 
    		 			display: '采购员', name: 'emp_name', align: 'center', width: '80'
    		 		}, { 
    		 			display: '发票金额', name: 'bill_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.bill_money ==null ? 0 : rowdata.bill_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '优惠金额', name: 'coupon_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.coupon_money ==null ? 0 : rowdata.coupon_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '应付金额', name: 'payable_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.payable_money ==null ? 0 : rowdata.payable_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '已付金额', name: 'payed_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.payed_money ==null ? 0 : rowdata.payed_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '未付金额', name: 'nopay_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.nopay_money ==null ? 0 : rowdata.nopay_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '开户银行', name: 'bank_name', align: 'left', width: '120'
    		 		}, { 
    		 			display: '银行账号', name: 'bank_no', align: 'left', width: '120'
    		 		}, { 
    		 			display: '仓库', name: 'store_name', align: 'left', width: '120'
    		 		}
    		 		],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountReportBillArrNonPay.do?isCheck=false&show_detail=1',
    			width: '100%', height: '100%', rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    			    { text: '打印', id:'print', click: printSum, icon:'print' },
    				{ line:true }
    			]}, 
    		});
    	}else{
    		grid = $("#maingrid").ligerGrid({
    			columns: [
    					{
    					display: '发票号', name: 'bill_no', align: 'left', width: '120'
    				}, { 
    		 			display: '开票日期', name: 'bill_date', align: 'left', minWidth: '120', formatter: "yyyy-MM-dd"
    		 		},{ 
    		 			display: '供货单位', name: 'sup_name', align: 'left', minWidth: '200'
    		 		}, { 
    		 			display: '采购员', name: 'emp_name', align: 'center', width: '80'
    		 		}, { 
    		 			display: '发票金额', name: 'bill_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.bill_money ==null ? 0 : rowdata.bill_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '优惠金额', name: 'coupon_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.coupon_money ==null ? 0 : rowdata.coupon_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '应付金额', name: 'payable_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.payable_money ==null ? 0 : rowdata.payable_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '已付金额', name: 'payed_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.payed_money ==null ? 0 : rowdata.payed_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '未付金额', name: 'nopay_money', align: 'right', width: '120',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.nopay_money ==null ? 0 : rowdata.nopay_money, '${p04005 }', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '开户银行', name: 'bank_name', align: 'left', width: '120'
    		 		}, { 
    		 			display: '银行账号', name: 'bank_no', align: 'left', width: '120'
    		 		}, { 
    		 			display: '仓库', name: 'store_name', align: 'left', width: '120'
    		 		}
    		 		],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountReportBillArrNonPay.do?isCheck=false&show_detail=0',
    			width: '100%', height: '100%', rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    			    { text: '打印', id:'print', click: printDetial, icon:'print' },
    				{ line:true }
    			]}, 
    		});
    	}
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>开票日期："+$("#begin_date").val() +" 至  "+ $("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="票到款未付明细表";
    }
    
    //键盘事件
	/* function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('C', copy_no);
		hotkeys('O', offset);
		hotkeys('D', remove);
		hotkeys('S', audit);
		hotkeys('U', unAudit);
		hotkeys('F', confirm);
		hotkeys('P', print);
	}
     */
 	//打印汇总打印
 	function printSum(){
     	
     	if(grid.getData().length==0){
     		
 			$.ligerDialog.error("请先查询数据！");
 			
 			return;
 		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月:"},
    	          {"cell":1,"value":""+liger.get("begin_date").getValue()+"至"+liger.get("end_date").getValue()}
        	]}; 
    	//表尾
    	var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var foots={
    			"rows":[{"cell":0,"value":"制表日期"},{"cell":1,"value":date}]
    	};
    	var printPara={
          		title: "票到款未付明细表(汇总)",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.matpayquery.MatAccountReportBillArrNonPayService",
       			method_name: "queryMatAccountReportBillArrNonPayMainPrint",
       			bean_name: "matAccountReportBillArrNonPayService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots:JSON.stringify(foots)
       			
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);

    		
     }
 	//打印明细打印
 	function printDetial(){
     	
     	if(grid.getData().length==0){
     		
 			$.ligerDialog.error("请先查询数据！");
 			
 			return;
 		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+liger.get("begin_date").getValue()+"至"+liger.get("end_date").getValue()}
        	]}; 
    	//表尾
    	var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var foots={
    			"rows":[{"cell":0,"value":"制表日期"},{"cell":1,"value":date}]
    	};
    	var printPara={
          		title: "票到款未付明细表(明细)",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.matpayquery.MatAccountReportBillArrNonPayService",
       			method_name: "queryMatAccountReportBillArrNonPayPrint",
       			bean_name: "matAccountReportBillArrNonPayService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots:JSON.stringify(foots)
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);

    		
     }
	 
	//字典下拉框
    function loadDict(){
		//仓库
    	//autocomplete("#store_code", "../queryMatStore.do?isCheck=false", "id", "text", true, true);
/*     		autocomplete("#store_code", "../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true, '',true,'',240); */
    		autocomplete("#store_code", "../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1},true,'',240);
		//供应商
		autocomplete("#sup_code", "../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		//采购员
		autocomplete("#emp_id", "../queryMatPurStockEmp.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		//科室
/* 		autocomplete("#dept_code", "../queryHosDept.do?isCheck=false", "id", "text", true, true,'',false,'',240); */
		autocomplete("#dept_code", "../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false,'',240);
		//供应商分类
		autocomplete("#sup_type_code", "../queryHosSupType.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		autocomplete("#set_code", "../queryMatVirStore.do?isCheck=false", "id", "text", true, true, '',false,'',240);
		autodate("#begin_date","yyyy-MM-dd");
  	    autodate("#end_date","yyyy-MM-dd");
  	  $("#set_code").ligerTextBox({width:240});
  	  $("#begin_date").ligerTextBox({width:110});
      $("#end_date").ligerTextBox({width:110});
      $("#sup_code").ligerTextBox({width:240});
	}
	
    function showDetail(){
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if(show_detail==0){
			//liger.get("inv_code").clear();
			$("#inv_code").val();
		}
		loadHead();
		query();
	 }
	
  	/* //导出Excel
	function exportExcel(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
   		var printPara={
   			rowCount:1,
   			title:'票到款未付明细表',
   			type:1,
   			columns:grid.getColumns(1)
   			};
   		ajaxJsonObjectByUrl("queryMatAccountReportBillArrNonPay.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    } */
	
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
            	开票日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td"  width="5%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				供应商分类：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_type_code" type="text" id="sup_type_code" ltype="text" validate="{required:false}" />
            </td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" >
				供应商：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        	<td align="right" class="l-table-edit-td" >
				科室：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" >
				采购员：
			</td>
            <td align="left" class="l-table-edit-td">
            	<input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
        <tr>
        
         <td align="right" class="l-table-edit-td" width="10%">虚&nbsp;&nbsp;仓：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%"></td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示汇总
            </td>
            
        </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
