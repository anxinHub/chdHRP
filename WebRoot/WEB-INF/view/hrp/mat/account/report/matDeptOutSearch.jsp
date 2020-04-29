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
    var isSum = 0;
	var renderFunc = {
			price:function(value){//单价
				return formatNumber(value, '${p04006}', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p04005}', 1);
			},
			amount:function(value){//数量
				return formatNumber(value, '${p04005}', 1);
			}
	}; 
	
    $(function (){
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 
		 isSum = $("#is_sum").is(":checked") ? 1 : 0;
		 query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
        //根据表字段进行添加查询条件
        var begin_date = $("#begin_date").val();
        var end_date  = $("#end_date").val();
        
        if(begin_date == ''){
        	$.ligerDialog.warn('开始期间不能为空 ');
        	return ;
        }
        
        if(end_date == ''){
        	$.ligerDialog.warn('结束期间不能为空 ');
        	return ;
        }
        
		grid.options.parms.push({
			name : 'begin_date',
			value : begin_date
		});
		grid.options.parms.push({
			name : 'end_date',
			value : end_date
		});
		grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue() == null ? "" : liger.get("dept_code").getValue().split(",")[0]
		});
	/* 	grid.options.parms.push({
			name : 'mat_type_id',
			value : liger.get("mat_type_code").getValue() == null ? "" : liger.get("mat_type_code").getValue()
		});  */
		grid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : "("+liger.get("bus_type_code").getValue().replace(/;/g,',')+")"
		}); 
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
// 		grid.options.parms.push({
// 			name : 'inv_id',
// 			value : liger.get("inv_code").getValue() == null ? "" : liger.get("inv_code").getValue().split(",")[0]
// 		}); 
		grid.options.parms.push({
			name : 'inv_code',
			value : $('#inv_code').val()
		});
		grid.options.parms.push({
			name : 'set_code',
			value : liger.get("set_code").getValue() == null ? "" : liger.get("set_code").getValue().split(",")[0]
		}); 
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger.get("store_code").getValue().split(",")[0]
		});
		grid.options.parms.push({
			name : 'is_charge',
			value : liger.get("is_charge").getValue() == null ? "" : liger.get("is_charge").getValue()
		});
		grid.options.parms.push({
			name : 'out_no',
			value : $('#out_no').val()
		});
		grid.options.parms.push({
			name : 'is_highvalue',
			value : liger.get("is_highvalue").getValue() == null ? "" : liger.get("is_highvalue").getValue()
		});
		grid.options.parms.push({
			name : 'sup_id',
			value : liger.get("sup_id").getValue() == null ? "" : liger.get("sup_id").getValue()
		});
		grid.options.parms.push({
			name : 'is_last',
			value :  $("#is_last").is(":checked") ? "1":"0"
		});
		
    	//加载查询条件 
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	if(isSum=="0"){
    		grid = $("#maingrid").ligerGrid({
    			columns: [{
    					display: '期间', name: 'year_month',  align: 'left', minWidth: '150', frozen: true
    				},  { 
    		 			display: '科室名称', name: 'dept_name', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '物资编码', name: 'inv_code', align: 'left', minWidth: '100', frozen: true
    		 		}, { 
    		 			display: '物资名称', name: 'inv_name', align: 'left', minWidth: '150', frozen: true
    		 		}, { 
    		 			display: '类别编码', name: 'mat_type_code', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '类别名称', name: 'mat_type_name', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '业务类型编码', name: 'bus_type_code', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '业务类别名称', name: 'bus_type_name', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '仓库', name: 'store_name', align: 'left', minWidth: '100'
    		 		}, { 
    		 			display: '规格', name: 'inv_model', align: 'right', minWidth: '100'
    		 		}, { 
    		 			display: '单位', name: 'unit_name', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '数量', name: 'amount', align: 'right', minWidth: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
    					},
    					formatter:'0.00'
    		 		}, { 
    		 			display: '单价', name: 'price', align: 'right', minWidth: '80',formatter:"###,##0.00",
    		 			render : function(rowdata, rowindex, value) {
    						return value ==null ? "" : formatNumber(value, '${p04006}', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '80',formatter:"###,##0.00",
    		 			render : function(rowdata, rowindex, value) {
    						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
    					},formatter:'###,##0.00'
    		 		}, { 
    		 			display: '库管员', name: 'confirmer', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '出库日期', name: 'confirm_date', align: 'left', minWidth: '80', formatter: "yyyy-MM-dd"
    		 		},{ 
    		 			display: '领用人', name: 'dept_emp', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '备注', name: 'note', align: 'left', minWidth: '80'
    		 		}
    		 		],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountReportDeptOutSearch.do?isCheck=true&isSum=0',
    			width: '100%', height: '98%',rownumbers:true,pageSize:500,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '打印（<u>P</u>）', id:'print', click: print, icon:'print' }
    			]},
    			lodop:{
    				//title:"科室出库查询",
    			}
    			 
    		});
    	}else{
    		grid = $("#maingrid").ligerGrid({
    			columns: [{ 
		 				display: '物资编码', name: 'inv_code', align: 'left', minWidth: '100', frozen: true
			 		}, { 
			 			display: '物资名称', name: 'inv_name', align: 'left', minWidth: '150', frozen: true
			 		}, { 
		 				display: '仓库名称', name: 'store_name', align: 'left', minWidth: '80'
		 			}, { 
    		 			display: '科室名称', name: 'dept_name', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '类别编码', name: 'mat_type_code', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '类别名称', name: 'mat_type_name', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '业务类别名称', name: 'bus_type_name', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '规格', name: 'inv_model', align: 'right', minWidth: '100'
    		 		}, { 
    		 			display: '单位', name: 'unit_name', align: 'left', minWidth: '80'
    		 		}, { 
    		 			display: '数量', name: 'amount', align: 'right', minWidth: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
    					},formatter:'0.00'
    		 		/* }, { 
    		 			display: '单价', name: 'price', align: 'right', minWidth: '80',formatter:"###,##0.00",
    		 			render : function(rowdata, rowindex, value) {
    						return value ==null ? "" : formatNumber(value, '${p04006}', 1);
    					} */
    		 		}, { 
    		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '80',formatter:"###,##0.00",
    		 			render : function(rowdata, rowindex, value) {
    						return value ==null ? "" : formatNumber(value, '${p04005}', 1);
    					}
    		 		}
    		 		],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAccountReportDeptOutSearch.do?isCheck=true&isSum=1',
    			width: '100%', height: '98%',rownumbers:true,
    			delayLoad: true,//初始化不加载，默认false
    			selectRowButtonOnly:true,//heightDiff: -10,
    			toolbar: { items: [
    				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
    				{ line:true },
    				{ text: '打印（<u>P</u>）', id:'print', click: printSum, icon:'print' },

    			]}
    			 
    		});
    	}
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${hos_name}</td></tr>";
 		head=head+"<tr><td>确认日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title=liger.get("store_code").getText().split(" ")[1]+"科室出库查询表";
    }
    
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
   
    function loadDict(){
		//字典下拉框
/* 		autocomplete("#dept_code", "../../queryMatAppDept.do?isCheck=false", "id", "text", true, true);
		autocomplete("#mat_type_code", "../../queryMatType.do?isCheck=false", "id", "text", true, true,'',false,'',240); */
		autocomplete("#dept_code", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1});
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},false,'',240);
		autocompleteAsyncMulti("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '3, 21, 9,11,13,23,49,50'}, true);
		//autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true);
/* 		autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true, "", true); */
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1}, true);
		autocomplete("#set_code", "../../queryMatVirStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_id", "../../queryHosSup.do?isCheck=false", "id", "text", true, true); 
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_highvalue", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        $("#out_no").ligerTextBox({width:240});
        $("#inv_code").ligerTextBox({width:240});
        $("#mat_type_code").ligerTextBox({width:240});
	}
    
  	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
    	          {"cell":3,"value":"仓库："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""},
    	          {"cell":6,"value":"虚仓："},
    	          {"cell":7,"value":""+liger.get("set_code").getText()==''?'空':liger.get("set_code").getText().split(" ")[1]+""}
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制表日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var printPara={
          		title: "科室领用汇总表(财务)",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutSearchService",
       			method_name: "queryMatAccountReportDeptOutSearchPrint",
       			bean_name: "matAccountReportDeptOutSearchService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
       			
           	};
    	    
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
   		
    }
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()}
        	]}; 
    	var printPara={
          		title: "科室领用汇总表(财务)",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutSearchService",
       			method_name: "queryMatAccountReportDeptOutSearchPrint",
       			bean_name: "matAccountReportDeptOutSearchService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			
           	};
    	    
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
   		
    }
  	//打印
	function printSum(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
    	          {"cell":3,"value":""+'仓库'+"："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()}
        	]};
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制表日期:"} ,
				{"cell":1,"value":date} 
				
			]
		};
    	
    
    	var printPara={
          		title: "科室领用汇总表(财务)",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutSearchService",
       			method_name: "queryMatAccountReportDeptOutSearchByCollectPrint",
       			bean_name: "matAccountReportDeptOutSearchService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 

           	};
    	    
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
   		
    }
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+$("#begin_date").val()+"至"+$("#end_date").val()},
    	          {"cell":3,"value":""+'仓库'+"："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()}
        	]};
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制表日期:"} ,
				{"cell":1,"value":date} 
				
			]
		}; 
    	var printPara={
          		title: "出库明细汇总表(科室)",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.account.report.MatAccountReportDeptOutSearchService",
       			method_name: "queryMatAccountReportDeptOutSearchPrint",
       			bean_name: "matAccountReportDeptOutSearchService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 

           	};
    	    
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
   		
    }
	function showSum(){
		isSum = $("#is_sum").is(":checked") ? 1 : 0;
		loadHead();
		query(); 
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
            	确认日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td"  width="5%">
				业务类型：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" />
            </td>
	        
			<td align="right" class="l-table-edit-td"  width="10%">
				科室：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_code" type="text" id="dept_code" ltype="text" required="true" validate="{required:false}" />
            </td>
			
        </tr>
        
        <tr>
            <td align="right" class="l-table-edit-td"  width="10%">
				物资类别：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
          <td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" required="true" validate="{required:false}" />
            </td>
	        <td align="right" class="l-table-edit-td"  width="10%">
				虚仓名称：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="set_code" type="text" id="set_code" ltype="text" />
            </td>
			
			
        </tr>
        
        <tr>
            
              <td align="right" class="l-table-edit-td"  width="10%">
            	材料信息：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<input name="inv_code" type="text" id="inv_code" ltype="text" required="true" validate="{required:false}" />
	        </td>
            <td align="right" class="l-table-edit-td"  width="10%">
				是否收费：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">
            	供货单位：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<input name="sup_id" type="text" id="sup_id" ltype="text" required="true" validate="{required:false}" />
	        </td>
	        
			
			
        </tr>    
         <tr>
         <td align="right" class="l-table-edit-td"  width="10%">
				出库单号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="out_no" type="text" id="out_no" ltype="text" required="true" validate="{required:false}" />
            </td>
         <td align="right" class="l-table-edit-td"  width="10%">
				是否高值：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_highvalue" type="text" id="is_highvalue" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td width="10%"></td>
            <td align="left" class="l-table-edit-td"  width="20%">
	        	<input name="is_last" type="checkbox" id="is_last" ltype="text" checked="checked"/>显示末级类别&nbsp;&nbsp;
	        	<input name="is_sum" type="checkbox" id="is_sum" ltype="text" onclick="showSum();"/>是否汇总
	        </td>
         </tr>
    </table>
	<div id="maingrid"></div>
</body>
</html>
