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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		store_Column();
		batch_no_Column();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name : 'sup_id',value : liger.get("sup_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'mat_type_no',value : liger.get("mat_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		grid.options.parms.push({name : 'c_batch_no',value : $("#c_batch_no").is(":checked") ? 1 : ''}); 
		grid.options.parms.push({name : 'c_store',value : $("#c_store").is(":checked") ? 1 : ''}); 
		grid.options.parms.push({name : 'is_com',value : $("#is_com").is(":checked") ? 1 : 0});
		//grid.options.parms.push({name : 'is_zero_store',value : $("#is_zero_store").is(":checked") ? 1 : 0});
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
		grid.options.parms.push({
			name : 'fac_id',//生产厂商编码
			value : liger.get("fac_code").getValue() == null ? "" : liger.get("fac_code").getValue().split(",")[0]
    	});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
	 			display: '交易编码', name: 'bid_code', align: 'left',width: '110'
	 			},{
					display: '仓库名称', name: 'store_name', align: 'left',id:'store_name',minWidth: '140',
					render : function(rowdata, rowindex, value) {
						if(rowdata.store_name == null){
							return "合计"
						}else {
							return rowdata.store_name
						}
					}
				}, { 
		 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '150',
						render : function(rowdata, rowindex, value) {
							var store = $("#c_store").is(":checked") ? 1 : 0;
							if(store == 0 ){
								if(rowdata.inv_code == null){
									return "合计"
									
								}else {
									return rowdata.inv_code
								}
							}else {
								
								return rowdata.inv_code
							}
							 
						}
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left',width: '260'
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '150'
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', minWidth: '200'
		 		}, { 
		 			display: '生产厂商', name: 'fac_name', align: 'left', minWidth: '200'
		 		}, { 
		 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '批号', name: 'batch_no', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '单价', name: 'price', align: 'right', minWidth: '80',
		 			 render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04006}', 1);
						},formatter:'###,##0.00'
		 		},  { 
		 			display: '结存数量', name: 'amount', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '结存金额', name: 'money', align: 'right', minWidth: '80',
		 			 render : function(rowdata, rowindex, value) {
						return formatNumber(rowdata.money ==null ? 0 : rowdata.money, '${p04005}', 1);
					},formatter:'###,##0.00'
		 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatAffiStockRouting.do?isCheck=false',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click:print, icon:'print' },
				{ line:true}
			]}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('P', print);
	}
 
    function print(){
    	if(grid.getData().length==0){
    		$.ligerDialog.error("请先查询数据!");
    		return;
    	}
    	var heads={
    			/* "isAuto":true,//系统默认,页眉显示页码
    			"rows":[{"cell":0,"value":"统计年月"},
    			        {"cell":1,"value":""+liger.get("year").getValue()+"年"+liger.get("month").getValue()+"月"}]
    	 */
    	};
    	//表尾
    	var time=new Date();
    	var date=time.getFullYear()+"年"+(time.getMonth()+1)+"月"+time.getDate()+"日";
    	var foots = {
    			rows: [
    				{"cell":0,"value":"制表日期:"} ,
    				{"cell":1,"value":date} ,
    			]
    		}; 
    	var printPara={
    			title:"库存分布查询",
    			columns:JSON.stringify(grid.getPrintColumns()),
    			class_name:'com.chd.hrp.mat.service.affi.query.MatAffiStockSearchService',
    			method_name:'queryMatAffiStockRoutingPrint',
    			bean_name:'matAffiStockSearchService',
    			heads:JSON.stringify(heads),
    			foots:JSON.stringify(foots),
    	};
    	
    	$.each(grid.options.parms,function(i,obj){
    		printPara[obj.name]=obj.value;
    	});
    	
    	officeGridPrint(printPara);
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
		}else {
			 grid.toggleCol('batch_no', false)
		}
		query();
	}
    function loadDict(){
		//字典下拉框
		autocompleteAsync("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true, "", false, false, 260, false, 240);
		autocomplete("#fac_code", "../../queryHosFacInv.do?isCheck=false", "id", "text", true, true);
/* 		autocompleteAsync("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true,{is_com : 1},true); */
		autocompleteAsync("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{is_com : 1,read_or_write:1},true);
/* 		autocomplete("#mat_type_code", "../../queryMatTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false); */
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {is_last : 1,read_or_write:1},false);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
	//	autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true,'',false,'',240);
        $("#batch_no").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:240});
        $("#store_code").ligerTextBox({width:240});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div class="search-block clearfix">
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
        <tr>
	        <td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">
            	批号：
            </td>
             <td align="left" class="l-table-edit-td" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" />
            </td>
             <td align="right" class="l-table-edit-td"  width="10%">是否收费： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            
        </tr> 
        <tr>
        	
	        <td align="right" class="l-table-edit-td" width="10%">材料信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
	      <td align="right" class="l-table-edit-td" width="10%">
				规格型号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				物资类别：
			</td>
			
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
	       
            
        </tr>
        <tr>
        	 <td align="right" class="l-table-edit-td"  width="10%">查&nbsp;&nbsp;&nbsp;&nbsp;询：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	 <table>
            	 	<tr>
            	 		<td><input name="c_store" type="checkbox" id="c_store" ltype="text" onclick="store_Column()"/> 分库房显示 &nbsp;</td>
            	 		<td><input name="c_batch_no" type="checkbox" id="c_batch_no" ltype="text" onclick="batch_no_Column()"/> 按批查询 &nbsp;</td>
            	        <td><input name="is_com" type="checkbox" id="is_com" ltype="text" checked="checked"/> 是否包含代销 &nbsp;</td>
            	        <!-- <td><input name="is_zero_store" type="checkbox" id="is_zero_store" ltype="text" /> 是否零库存 &nbsp;</td> -->
            	 	</tr>
            	 </table>
            </td>
             <td align="right" class="l-table-edit-td"  >
	            	供&nbsp;&nbsp;应&nbsp;&nbsp;商：
	            </td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:true}" />
	            </td>
	            
	            	<td align="right" class="l-table-edit-td"  width="10%">
	                    	生产厂商：
	                    </td>
	                    <td align="left" class="l-table-edit-td" width="20%">
	                    	<input name="fac_code" type="text" id="fac_code" ltype="text" validate="{required:true,maxlength:100}" />
	                    </td>
            
        </tr>
    </table>
	</div>
	<div id="maingrid"></div>
</body>
</html>
