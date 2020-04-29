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
		loadHotkeys();
		
		$("#is_showStore").bind("change",function(){
			f_setColumns();
			query();
		});
    });
    //查询
    function  query(){
        
   		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        
        var begin_confirm_date = $("#begin_confirm_date").val();
        var end_confirm_date =  $("#end_confirm_date").val();
        
        if(begin_confirm_date == ''){
        	$.ligerDialog.warn('开始期间不能为空 ');
        }
        
        if(end_confirm_date == ''){
        	$.ligerDialog.warn('结束期间不能为空 ');
        }
        
        grid.options.parms.push({name : 'begin_in_date',value : $("#begin_in_date").val()});
		grid.options.parms.push({name : 'end_in_date',value : $("#end_in_date").val()});
		grid.options.parms.push({name : 'begin_confirm_date',value : begin_confirm_date});
		grid.options.parms.push({name : 'end_confirm_date',value : end_confirm_date});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText() == null ? "" :liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'set_id',value : liger.get("set_code").getValue()});
		grid.options.parms.push({name : 'is_showStore',value : $("#is_showStore").is(":checked") ? "1":""});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()});
		grid.options.parms.push({name : 'is_back',value : $('#is_back').is(":checked") ? "1":"0"});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInvPurMain.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click: print, icon:'print' },
   				{ line:true }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function f_setColumns(){
        
    	var show_store = $("#is_showStore").is(":checked") ? true:false;
    	
    	if(show_store){
	    	columns = [
				{display: '虚仓名称', name: 'set_name', align: 'left', minWidth: '100',render:
					function(rowdata, rowindex, value){
						if(rowdata.set_name == null){
							return "";
						}
						return rowdata.set_name;
					}
				},
				{display: '仓库名称', name: 'store_name', align: 'left', minWidth: '100'},
				{display: '供应商', name: 'sup_name', align: 'left', minWidth: '150'}, 
				{display: '物资类别', name: 'mat_type_name', align: 'left', minWidth: '120'},
				{display: '材料名称', name: 'inv_name', align: 'left', minWidth: '150'},
				{display: '单价', name: 'price', align: 'right', minWidth: '70',formatter:"###,##0.00",
					render : function(rowdata, rowindex, value) {
						if(rowdata.price == null ){
							return "";
						}
					return formatNumber(rowdata.price, '${p04006 }', 1);
					}
				},  
				{display: '数量', name: 'amount', align: 'right', minWidth: '45'},  
				{display: '金额', name: 'amount_money', align: 'right', minWidth: '100',formatter:"###,##0.00",
					render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
					}
				},
				{display: '生产厂商', name: 'fac_name', align: 'left', minWidth: '90'}
	    	];
    	}else{
    		columns = [
				{display: '虚仓名称', name: 'set_name', align: 'left', minWidth: '150',render:
					function(rowdata, rowindex, value){
						if(rowdata.set_name == null){
							return "";
						}
						return rowdata.set_name;
					}
				},
				{display: '供应商', name: 'sup_name', align: 'left', minWidth: '150'},  
				{display: '物资类别', name: 'mat_type_name', align: 'left', minWidth: '120'},
				{display: '材料名称', name: 'inv_name', align: 'left', minWidth: '150'},
				{display: '单价', name: 'price', align: 'right', minWidth: '70',
					render : function(rowdata, rowindex, value) {
						if(rowdata.price == null ){
							return "";
						}
					return formatNumber(rowdata.price, '${p04006 }', 1);
					}
				},  
				{display: '数量', name: 'amount', align: 'right', minWidth: '45'},  
				{display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
					render : function(rowdata, rowindex, value) {
					return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
					},formatter:"###,##0.00"
				},
				{display: '生产厂商', name: 'fac_name', align: 'left', minWidth: '90'}
    		];
    	}
    	grid.set('columns', columns); 
        //grid.reRender();
        //query();
    }
    
  
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
 
    function loadDict(){
		//字典下拉框 
//		autocomplete("#store_code", "../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true);
		autocomplete("#store_code", "../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1});
		autocomplete("#mat_type_code", "../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,false);
		//autocomplete("#mat_type_code", "../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write : 1},false);
		//autoCompleteByData("#state", matInMain_state.Rows, "id", "text", true, true);
		//autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {sel_flag : 'in'}, true);
		autocomplete("#sup_code", "../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		//autocomplete("#inv_code", "../queryMatInv.do?isCheck=false", "id", "text", true, true,'',false,'',220);
		autocomplete("#set_code", "../queryMatVirStore.do?isCheck=false", "id", "text", true, true,"", true);<%-- 虚仓 --%>
		autocomplete("#is_charge", "../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		//确认日期
        $("#begin_confirm_date").ligerTextBox({width:100});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        $("#end_confirm_date").ligerTextBox({width:100});
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        
        $("#begin_in_date").ligerTextBox({width:100});
        $("#end_in_date").ligerTextBox({width:100});
        $("#inv_model").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:220});
        $("#sup_code").ligerTextBox({width:160});
	}
    
  	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}

		
    	if(liger.get("set_code").getValue()== " "){ 
    		var heads={
            		"isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"统计年月："},
        	          {"cell":1,"value":""+$("#begin_confirm_date").val()+"至"+$("#begin_confirm_date").val()},
        	          {"cell":3,"value":"仓库："},
        	          {"cell":4,"value":""+liger.get("store_code").getText()==''?' ':liger.get("store_code").getText().split(" ")[1]+""}
        	         
            	]}; 
        	
    		}else if (liger.get("store_code").getValue()== " ") {
    			 
        	var heads={
            		"isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"统计年月："},
        	          {"cell":1,"value":""+$("#begin_confirm_date").val()+"至"+$("#begin_confirm_date").val()},
        	          {"cell":3,"value":"虚仓："},
        	          {"cell":4,"value":""+liger.get("set_code").getText()==''?' ':liger.get("set_code").getText().split(" ")[1]+""}
            	]};  
    	}else {
    		
    		var heads={
            		"isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":"统计年月："},
        	          {"cell":1,"value":""+$("#begin_confirm_date").val()+"至"+$("#begin_confirm_date").val()},
            	]}; 
    		
    	}
    	

    	var foots = {
    			rows: [
    				{"cell":0,"value":"制单日期:"} ,
    				{"cell":1,"value":date} ,
    			]
    		};  
    	var printPara={
          		title: "材料采购查询表",//标题 
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.matpur.MatPurCollectService", 
       			method_name: "queryMatInvPurMainPrint",
       			bean_name: "matPurCollectService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空
       			
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);

   		
    }
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;" onload="f_setColumns()">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
		
	<table>
		<tr>
			<td align="right" class="l-table-edit-td">确认日期： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        <td align="right" class="l-table-edit-td" width="10%">虚仓名称：</td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="set_code" type="text" id="set_code" ltype="text" validate="{required:false,maxlength:100}" />
  			</td> 
	        <td align="right" class="l-table-edit-td"  width="10%"> 仓库： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">制单日期： </td>
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_in_date" id="begin_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="end_in_date" id="end_in_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
	         <td align="right" class="l-table-edit-td" width="10%">供&nbsp;&nbsp;应&nbsp;&nbsp;商： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
            </td>
             <td align="right" class="l-table-edit-td"  width="10%"> 物资类别： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
		</tr>
		<tr>
			 <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
             <td align="right" class="l-table-edit-td"  width="10%">是否收费： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" />
            </td>
            <td align="right" class="l-table-edit-td" width="10%"> </td>
            <td align="left" class="l-table-edit-td" width="20%"> 
            	<input name="is_showStore" type="checkbox" id="is_showStore" />是否显示仓库
					&nbsp;&nbsp;&nbsp;&nbsp;
				<input name="is_back" type="checkbox" id="is_back" checked="checked"/>包含退货

  			</td> 
		</tr>
	</table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
