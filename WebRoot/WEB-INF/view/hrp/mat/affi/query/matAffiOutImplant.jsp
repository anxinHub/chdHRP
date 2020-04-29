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
    var renderFunc = {
    		
    		amount_money:function(value){//金额
				return formatNumber(value, 2, 1);
			},
			price:function(value){//单价
   			 return formatNumber(value, '${p04006 }', 1); 
   		 
			},
    }
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'begin_date',value : $("#begin_date").val()});
		grid.options.parms.push({name : 'end_date',value : $("#end_date").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'out_no',value : $("#out_no").val()});
		grid.options.parms.push({name : 'inv_code',value : $("#inv_code").val()});
		grid.options.parms.push({name : 'state',value : liger.get("state").getValue()});
		grid.options.parms.push({name : 'is_involved',value : $("#is_involved").is(":checked") ? 1 : 0 });
		grid.options.parms.push({name : 'is_implant',value : $("#is_implant").is(":checked") ? 1 : 0 });
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					display: '单据号', name: 'out_no', align: 'left', width: '140'/* ,
					render : function(rowdata, rowindex, value) {
						if(value == '合计'){
							return value;
						}else{
							return '<a href=javascript:in_open("' 
								+ rowdata.group_id 
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code 
								+ ',' + rowdata.out_id
								+ '")>'+rowdata.out_no+'</a>';
						}
					} */
				}, { 
		 			display: '出库日期', name: 'out_date', align: 'left', width: '80',formatter:'yyyy-MM-dd'
		 		}, { 
		 			display: '摘要', name: 'brief', align: 'left', width: '300'
		 		}, { 
		 			display: '供应商名称', name: 'sup_name', align: 'left', width: '250'
		 		}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left', width: '120'
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: '300'
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: '180'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: '80'
		 		}, { 
		 			display: '单价', name: 'price', align: 'left', width: '80',
		 			render : function(rowdata, rowindex, value) {
		 				if(rowdata.price == null || rowdata.price == '' || rowdata.price == undefined){
		 					return "";
		 				}
						return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04006 }', 1);
					},formatter:"###,##0.00"
		 		},  { 
		 			display: '数量', name: 'amount', align: 'left', width: '80'
		 		},  { 
		 			display: '金额', name: 'amount_money', align: 'right', width: '100',
		 			render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
					},formatter:"###,##0.00"
		 		}, { 
		 			display: '生产厂商', name: 'fac_name', align: 'left', width: '120'
		 		}, { 
		 			display: '注册证号', name: 'cert_code', align: 'left', width: '120'
		 		}, { 
		 			display: '注册证有效期', name: 'end_date', align: 'left', width: '80',formatter:'yyyy-MM-dd'
		 		}, { 
		 			display: '生产日期', name: 'fac_date', align: 'left', width: '80',formatter:'yyyy-MM-dd'
		 		}, { 
		 			display: '生产批号', name: 'batch_no', align: 'left', width: '100'
		 		}, { 
		 			display: '有效期', name: 'inva_date', align: 'left', width: '80',formatter:'yyyy-MM-dd'
		 		}, /* { 
		 			display: '灭菌批号', name: 'disinfect_no', align: 'left', width: '90'
		 		},  */{ 
		 			display: '手术医师', name: 'stocker_name', align: 'left', width: '80'
		 		}, { 
		 			display: '巡回护士', name: 'examiner_name', align: 'left', width: '80'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiOutImplant.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { 
				items: [
						{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
						{ line:true },
						{ text: '打印', id:'print', click: print, icon:'print' },
				   		{ line:true }
				]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', print);
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
    	          {"cell":1,"value":date} ,/*   {"cell":1,"value":""+$("#year_month").val()}, */
    	         /*  {"cell":2, "from":"right","align":"right","value":"库房: "+ liger.get("store_code").getText(),"colSpan":3}, */
    	          {"cell":3,"value":"仓库："},
    	          {"cell":4,"value":""+liger.get("store_code").getText()==''?' ':liger.get("store_code").getText().split(" ")[1]+""}
        	]}; 
    	//表尾
		var foots = {
			rows: [
				{"cell":0,"value":"制单日期:"} ,
				{"cell":1,"value":date} ,
			]
		}; 
    	var printPara={
          		title: "植入介入材料出库查询",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.affi.query.MatAffiStockSearchService",
       			method_name: "queryMatAffiOutImplantPrint",
       			bean_name: "matAffiStockSearchService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
    	/* var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		
		var dates = getCurrentDate();
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
   		var printPara={
   			title:'植入介入材料出库查询',
   			head:[
				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至  "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMatAffiOutImplant.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
 */
   		
    }
   
    function loadDict(){
		//字典下拉框
/* 		autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true); */
	autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_com : 1,read_or_write:1},true);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		autoCompleteByData("#state", matOutMain_state.Rows, "id", "text", true, true);
		
		$("#begin_date").ligerTextBox({width:110});
        $("#end_date").ligerTextBox({width:110});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        
		$("#sup_code").ligerTextBox({width:280});
		$("#inv_code").ligerTextBox({width:280});
		$("#out_no").ligerTextBox({width:240});
		$("#state").ligerTextBox({width:160});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
        	<td align="right" class="l-table-edit-td"  width="10%">制单日期：</td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >至</td>
						<td>
							<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
	        <td align="right" class="l-table-edit-td"  width="10%">仓库：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
	        	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
	        </td>
	        
	        <td align="right" class="l-table-edit-td" width="10%">供应商：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
	         	<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:false}" />
	        </td>
	        
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">单据号：</td>
	        <td align="left" class="l-table-edit-td" width="20%">
	        	<input name="out_no" type="text" id="out_no" ltype="text" validate="{required:false,maxlength:100}" />
	        </td>
	        
            <td align="right" class="l-table-edit-td" width="10%">状态：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="state" type="text" id="state" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%"></td>
        	<td align="left" class="l-table-edit-td" width="20%" style="padding-left:20px;">
            	<input name="is_involved" type="checkbox" id="is_involved" />&nbsp;是否介入
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<input name="is_implant" type="checkbox" id="is_implant" />&nbsp;是否植入
            </td>
        </tr>
    </table>    
	
	<div id="maingrid"></div>
</body>
</html>
