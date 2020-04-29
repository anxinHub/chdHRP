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
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid; 
    var gridManager = null;
    var userUpdateStr;
    var renderFunc = {
			price:function(value){//单价
				return formatNumber(value==null?0:value, '${p08005 }', 1);
			},
			money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			},
			amount:function(value){//数量
				return formatNumber(value==null?0:value,2,1);
			}
	}; 
    
    $(function ()
    {
    	debugger
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		//store_Column();
		//batch_no_Column();
		//confirm_date_Column();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		if(liger.get("store_code").getValue() == null || liger.get("store_code").getValue() == undefined || liger.get("store_code").getValue() == ""){
			
			$.ligerDialog.error('仓库为必填项');
			return ; 
		}
        //根据表字段进行添加查询条件
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'med_type_no',value : liger.get("med_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'med_type_code',value : liger.get("med_type_code").getText().split(" ")[0]});
		//grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		//grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'inv_info',value : $("#inv_info").val()});
		grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		grid.options.parms.push({name : 'c_batch_no',value : $("#c_batch_no").is(":checked") ? 1 : ''}); 
		grid.options.parms.push({name : 'c_store',value : $("#c_store").is(":checked") ? 1 : ''}); 
		grid.options.parms.push({name : 'zeroStore',value : $("#zeroStore").prop("checked")? '' : 1});
		grid.options.parms.push({name : 'is_com',value : $("#is_com").is(":checked") ? 1 : ''});
		grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'is_highvalue',value : liger.get("is_highvalue").getValue()});
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
		grid.options.parms.push({
			name : 'location_code',//规格型号
			value : $("#location_code").val()
		});
		grid.options.parms.push({
			name : 'fac_code',//规格型号
			value : $("#fac_code").val()
		});
		
		grid.options.parms.push({
			name : 'type_level',
			value : liger.get("type_level").getValue() == null ? "" : liger.get("type_level").getValue()
		});
		
		grid.options.parms.push({name : 'showConfirmDate',value : $("#showConfirmDate").prop("checked")? 1 : ''});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [/* {
					display: '仓库名称', name: 'store_name', align: 'left',id:'store_name',width: '140',frozen:true,
					render : function(rowdata, rowindex, value) {
						if(rowdata.store_name == null){
							return "合计"
						}else {
							return rowdata.store_name
						}
					}
				}, */ { 
		 			display: '交易编码', name: 'bid_code', align: 'left', frozen: true, width: '100'
		 		}, {   
		 			display: '药品编码', name: 'inv_code', align: 'left', frozen: true, width: '100'
		 		}, { 
		 			display: '药品名称', name: 'inv_name', align: 'left', width: '200', frozen: true
		 		}, { 
		 			display: '存储编码', name: 'memory_encoding', align: 'left', width: '100', frozen: true
		 		}, /* { 
		 			display: '药品类别编码', name: 'med_type_code', align: 'left', width: '140'
		 		}, { 
		 			display: '药品类别名称', name: 'med_type_name', align: 'left', width: '140'
		 		},  */{ 
		 			display: '规格型号', name: 'inv_model', align: 'left', width: '150', frozen: true
		 		}, { 
		 			display: '生产厂商', name: 'fac_name', align: 'left', width: '200'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: '60'
		 		}, { 
		 			display: '批号', name: 'batch_no', align: 'left', width: '120'
		 		},{ 
		 			display: '供应商', name: 'sup_name', align: 'left', width: '200'
		 		},{ 
		 			display: '有效期', name: 'inva_date', align: 'left', width: '80'
		 		}, { 
		 			display: '入库日期', name: 'confirm_date', align: 'left', width: '80'
		 		},{ 
		 			display: '单价', name: 'price', align: 'right', width: '90',
		 			 render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p08005 }', 1);
						}
		 		},  { 
		 			display: '结存数量', name: 'amount', align: 'right', width: '100',
		 			 render : function(rowdata, rowindex, value) {
							return '<div style="font-weight: bold; font-size: 14px;">'+formatNumber(rowdata.amount ==null ? 0 : rowdata.amount, 2, 1)+'</div>';
					}
		 		},  { 
		 			display: '结存金额', name: 'money', align: 'right', width: '100',
		 			 render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.money ==null ? 0 : rowdata.money, '${p08005 }', 1);
					}
		 		}
		 		, { 
		 			display: '货位', name: 'location_name', align: 'left', width: '80'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedStorageQueryStockRouting.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click: exportExcel, icon:'print' },
				{ line:true }
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>仓库："+liger.get("store_code").getText().split(" ")[1]+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="药品库存分布查询";
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
 
	//批量打印
    function print(){
    	return ;
    }
	
  	//导出Excel
	function exportExcel(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'药品库存分布查询',
   			head:[
				{"cell":0,"value":"单位：${sessionScope.hos_name}","colspan":colspan_num,"br":true}/* ,
				{"cell":0,"value":"统计年月：" + liger.get("year").getValue()+"年"+liger.get("month").getValue()+"月","colspan":colspan_num,"br":true} */
   			],
   			foot:[
				{"cell":0,"value":"制单人：${sessionScope.user_name}","colspan":colspan_num-2,"br":false},
				{"cell":colspan_num-2,"value":"审核人：","colspan":2,"br":true},
				{"cell":0,"value":"打印日期："+cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		
   		ajaxJsonObjectByUrl("queryMedStorageQueryStockRouting.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
   
	function store_Column(){
		
		var store = $("#c_store").is(":checked") ? 1 : 0;

		if(store == 1){	
			 grid.toggleCol('store_name', true)
		}else {
			 grid.toggleCol('store_name', false)
		}
		
		 query();

	}
	
	function batch_no_Column(){
		
		var batch_no = $("#c_batch_no").is(":checked") ? 1 : 0;

		if(batch_no == 1){	
			
			 grid.toggleCol('batch_no', true)
			 grid.toggleCol('inva_date', true)
		}else {
			 grid.toggleCol('batch_no', false)
			 grid.toggleCol('inva_date', false)
		}
		query();
	}
	
	function zeroStroe_Column(){
		
		 query();

	}
	
	function confirm_date_Column(){
		
		var showConfirmDate = $("#showConfirmDate").is(":checked") ? 1 : 0;
		console.log(showConfirmDate)

		if(showConfirmDate == 1){	
			
			 grid.toggleCol('confirm_date', true)
		}else {
			console.log(1)
			 grid.toggleCol('confirm_date', false)
		}
		query();
	}
	
    function loadDict(){
		//字典下拉框
		
		//autocompleteAsync("#type_level", "../../queryMedTypeLevel.do?isCheck=false", "id", "text", true, true);
/* 		autocompleteAsync("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,"",true);
		autocomplete("#med_type_code", "../../queryMedTypeDictCode.do?isCheck=false", "id", "text", true, true, '',false,'','',120); */
		autocompleteAsync("#store_code", "../../queryMedStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write:1},true);
		autocomplete("#med_type_code", "../../queryMedTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write:1},false,'','',120);
		autocomplete("#is_charge", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_code", "../../queryHosSup.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		autocomplete("#type_level", "../../queryMedTypeLevel_2.do?isCheck=false", "id", "text", true, true,'',true);
		autocomplete("#is_highvalue", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		
		//autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true);
        $("#batch_no").ligerTextBox({width:160});
        $("#inv_info").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#sup_code").ligerTextBox({width:160});
        $("#location_code").ligerTextBox({width:160});
        $("#fac_code").ligerTextBox({width:160});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td" >
				<font color="red" size="2">*</font>仓库：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				药品类别：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				药品信息：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="inv_info" type="text" id="inv_info" ltype="text" validate="{required:false,maxlength:100}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				批号：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			
			<td align="right" class="l-table-edit-td" >
				是否收费：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				供应商信息：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="sup_code" type="text" id="sup_code" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				规格型号：
			</td>
		 	<td align="left" class="l-table-edit-td" >
				<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				货位：
			</td>
		 	<td align="left" class="l-table-edit-td" >
				<input name="location_code" type="text" id="location_code" ltype="text"    validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >
				<!-- <font color="red" size="2">*</font> -->
				类别级次：
			</td>
        	<td align="left" class="l-table-edit-td">
            	<input name="type_level" type="text" id="type_level" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
			<td align="right" class="l-table-edit-td" >
				生产厂商：
			</td>
		 	<td align="left" class="l-table-edit-td" >
				<input name="fac_code" type="text" id="fac_code" ltype="text"    validate="{required:true}" />
			</td>
			<td align="right" class="l-table-edit-td" >
				是否高值：
			</td>
			<td align="left" class="l-table-edit-td" >
				<input name="is_highvalue" type="text" id="is_highvalue" ltype="text" validate="{required:true,maxlength:20}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" >
				查询：
			</td>
			<td align="left" class="l-table-edit-td" colspan="5">
				<input name="c_store" type="checkbox" id="c_store" ltype="text"  onclick="store_Column()"/> 分库房显示 &nbsp;
				<input name="c_batch_no" type="checkbox" id="c_batch_no" ltype="text" checked="checked" onclick="batch_no_Column()"/> 按批查询 &nbsp;
				<input name="c_batch_no" type="checkbox" id="zeroStore" ltype="text"  onclick="zeroStroe_Column()"/> 显示零库存 &nbsp;
				<input name="is_com" type="checkbox" id="is_com" checked="checked" ltype="text"/>包含代销 &nbsp;
				<input name="showConfirmDate" type="checkbox" id="showConfirmDate" ltype="text" checked onclick="confirm_date_Column()"/>显示入库日期 &nbsp;
			</td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
