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
    var gridManager = null;
    var userUpdateStr;
    var show_detail=0;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
		loadHotkeys();
        showDetail();
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_confirm_date',value : $("#begin_confirm_date").val()});
		grid.options.parms.push({name : 'end_confirm_date',value : $("#end_confirm_date").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'mat_type_no',value : liger.get("mat_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'bill_no',value : $("#bill_no").val()});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_no',value : liger.get("sup_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'sup_msg',value : $("#sup_msg").val()});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'in_no',value : $("#in_no").val()});
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
		
		//业务类型
		if(liger.get("bus_type_code").getValue().length>0){
			
			var bus_type_codes=liger.get("bus_type_code").getValue().split(";");
			var bus_type_code="";
			for(var code of bus_type_codes){
				if(code==='31'){
					grid.options.parms.push({name : 'tran_code',value : '31'});
				}else{
					bus_type_code+=bus_type_code.length>0?","+code:code;
				}
			}
			if(bus_type_code.length>0){
				grid.options.parms.push({name : 'bus_type_code',value : bus_type_code});
			}
		}
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	//入库汇总查询
    	if (show_detail != "1") {
    		grid = $("#maingrid").ligerGrid({
    			columns: [ { 
    		 			display: '供应商编码', name: 'sup_code', align: 'left', minWidth: '150'
    		 		}, { 
    		 			display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '80'
    		 		}, {
    					display: '仓库名称', name: 'store_name', align: 'left', minWidth: '140',
    				}, { 
    		 			display: '物资类别', name: 'mat_type_name', align: 'left', minWidth: '90'
    		 		}, { 
    		 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '入库数量', name: 'amount', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '单价', name: 'price', align: 'left', minWidth: '80',
    		 			render : function(rowdata, rowindex, value) {
    		 				if(value == null){
    		 					return "";
    		 				}
    						return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04006 }', 1);
    					}
    		 		},  { 
    		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
    					}
    		 		},{ 
    		 			display: '生产厂家', name: 'fac_name', align: 'left', minWidth: '80'
    		 		}],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiInInvCollection.do?isCheck=false',
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
    	}else{
    		grid = $("#maingrid").ligerGrid({
    			columns: [ { 
    	 			display: '业务类型', name: 'bus_type_name', align: 'left', minWidth: '80'
    	 		},  { 
    		 			display: '供应商编码', name: 'sup_code', align: 'left', minWidth: '150'
    		 		}, { 
    		 			display: '供应商名称', name: 'sup_name', align: 'left', minWidth: '80'
    		 		}, {
    					display: '仓库名称', name: 'store_name', align: 'left', minWidth: '140',
    				}, { 
    		 			display: '物资类别', name: 'mat_type_name', align: 'left', minWidth: '90'
    		 		}, {
    					display: '入库单号', name: 'in_no', align: 'left', minWidth: '140',
    				}, { 
    		 			display: '入库日期', name: 'confirm_date', align: 'left', minWidth: '150', formatter: "yyyy-MM-dd"
    		 		}, { 
    		 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '入库数量', name: 'amount', align: 'left', minWidth: '80'
    		 		},  { 
    		 			display: '单价', name: 'price', align: 'left', minWidth: '80',
    		 			render : function(rowdata, rowindex, value) {
    		 				if(value == null){
    		 					return "";
    		 				}
    						return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04006 }', 1);
    					}
    		 		},  { 
    		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
    		 			render : function(rowdata, rowindex, value) {
    						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
    					}
    		 		},{ 
    		 			display: '采购员', name: 'stocker_name', align: 'left', minWidth: '80'
    		 		},{ 
    		 			display: '生产厂家', name: 'fac_name', align: 'left', minWidth: '80'
    		 		}],
    			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiInDetail.do',
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
    	}

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
 
	//打印
	function print(){
		
		var time = new Date(); //获得当前时间
		var year = time.getFullYear();//获得年、月、日
		var month = time.getMonth()+1;
		var day = time.getDate(); 
		var date = year+"年"+month+"月"+day;	
    	
		if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"统计年月："},
    	          {"cell":1,"value":""+liger.get("begin_confirm_date").getValue()+"至"+liger.get("end_confirm_date").getValue()}	,
    	          {"cell":3,"value":"仓库："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?'空':liger.get("store_code").getText().split(" ")[1]+""}
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制单日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var title="代销材料入库情况查询表（汇总表）";
		if (show_detail != "1") {
			title="代销材料入库情况查询表（汇总表）";
		}else{
			title="代销材料入库情况查询表（明细表）";
		}
    	
    	var printPara={
          		title: title,//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.affi.query.MatAffiStockSearchService",
       			method_name: "queryMatAffiInDetailPrint",
       			bean_name: "matAffiStockSearchService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
       			show_detail:show_detail
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_com : 1,read_or_write:1},true);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write:1},false,'',250);
		autocompleteAsyncMulti("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes:"1,27,29,31",read_or_write : 1}, false,'',160);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',250);
        $("#begin_confirm_date").ligerTextBox({width:100});
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        $("#end_confirm_date").ligerTextBox({width:100});
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        
        $("#bill_no").ligerTextBox({width:160});
        $("#sup_msg").ligerTextBox({});
        $("#in_no").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:220});
        $("#sup_code").ligerTextBox({width:160});
        $("#mat_type_code").ligerTextBox({width:160});
	}  
    

    function showDetail() {
		show_detail = $("#show_detail").is(":checked") ? 1 : 0;
		if (grid) {
			//由于一个对象多次绑定相同的事件，需要进行解绑在绑定
			grid.unbind(); 
			grid.bind('contextmenu', grid.options.onContextmenu);
		}
		loadHead();
	}
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
	
	<table>
		<tr>
			<td align="right" class="l-table-edit-td">入库日期： </td>
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
	        <td align="right" class="l-table-edit-td"  width="10%"> 仓库： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
              <td align="right" class="l-table-edit-td"  width="10%"> 物资类别： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            
		</tr>
		<tr>
          <td align="right" class="l-table-edit-td"  width="10%">入库单号： </td>
          <td align="left" class="l-table-edit-td" width="20%">
          	<input name="in_no" type="text" id="in_no" ltype="text" validate="{required:false,maxlength:100}" />
          </td>
          <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
           <td align="left" class="l-table-edit-td" width="20%">
           	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
           </td>
          <td align="right" class="l-table-edit-td"  width="10%"> 供应商：</td>
           <td align="left" class="l-table-edit-td" width="20%">
           	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
           </td>
		   
		</tr>
		<tr>
            <td align="right" class="l-table-edit-td" width="10%">
				规格型号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td">业务类型：</td>
				<td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" required="true" validate="{required:true}" /></td>
			<td align="right" class="l-table-edit-td"  width="10%"> 供应商信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
           		<input name="sup_msg" type="text" id="sup_msg" ltype="text" validate="{required:false}" />
        		<input name="show_detail" type="checkbox" id="show_detail" onclick="showDetail();"/>&nbsp;&nbsp;显示明细
        	</td>
		</tr>
		 
	</table>
		 
	</div>
	<div id="maingrid"></div>
</body>
</html>
