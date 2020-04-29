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
				return formatNumber(value, '${p04005 }', 1);
			},
			amount:function(value){//数量
				return formatNumber(value==null?0:value);
			}
	}; 
    
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
        
        var begin_confirm_date = $("#begin_confirm_date").val();
        var end_confirm_date = $("#end_confirm_date").val();
        
        if(begin_confirm_date == ''){
        	$.ligerDialog.warn('开始期间不能为空 ');
        	return ; 
        }
        
        if(end_confirm_date == ''){
        	$.ligerDialog.warn('结束期间不能为空 ');
        	return ; 
        }
        
		grid.options.parms.push({name : 'begin_confirm_date',value : begin_confirm_date});
		grid.options.parms.push({name : 'end_confirm_date',value :end_confirm_date});
		
		grid.options.parms.push({name : 'begin_out_date',value : $("#begin_out_date").val()});
		grid.options.parms.push({name : 'end_out_date',value : $("#end_out_date").val()});
		grid.options.parms.push({name : 'inv_msg',value : $("#inv_msg").val()});
	    grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getText().split(" ")[0]}); 
		//grid.options.parms.push({name : 'mat_type_no',value : liger.get("mat_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'dept_id',value : liger.get("dept_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'dept_no',value : liger.get("dept_code").getValue().split(",")[1]});
		//grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		//grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()});
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		
		
		if($("#is_detail").is(':checked')==true){
			//业务类型
			if(liger.get("bus_type_code").getValue().length>0){
				
				var bus_type_codes=liger.get("bus_type_code").getValue().split(";");
				var bus_type_code="";
				for(var code of bus_type_codes){
					if(code==='14'){
						grid.options.parms.push({name : 'tran_code',value : '14'});
					}else{
						bus_type_code+=bus_type_code.length>0?","+code:code;
					}
				}
				if(bus_type_code.length>0){
					grid.options.parms.push({name : 'bus_type_code',value : bus_type_code});
				}
			}
		}else{
			//业务类型
			if(liger.get("bus_type_code").getValue().length>0){
				
				var bus_type_codes=liger.get("bus_type_code").getValue().split(";");
				var bus_type_code="";
				for(var code of bus_type_codes){
					if(code==='14'){
						grid.options.parms.push({name : 'tran_code',value : '14'});
					}else{
						bus_type_code+=bus_type_code.length>0?","+code:code;
					}
				}
				if(bus_type_code.length>0){
					grid.options.parms.push({name : 'bus_type_code',value : bus_type_code});
				}
			}
			grid.options.parms.push({name : 'state',value : $("#state").prop("checked") ? '(1,2,3)':'(3)'});
		}
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	if($("#is_detail").is(':checked')==false){
	    	grid = $("#maingrid").ligerGrid({
	    		columns: [{ 
			 			display: '科室编码', name: 'dept_code', align: 'left', minWidth: '80',
			 				render : function(rowdata, rowindex, value) {
			 					if(rowdata.dept_code == null){
			 						return "合计"
			 					}else{
			 						return rowdata.dept_code
			 					} 
							}
			 		}, { 
			 			display: '科室名称', name: 'dept_name', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '物资类别', name: 'mat_type_name', align: 'left', minWidth: '80'
			 		},{ 
			 			display: '交易编码', name: 'bid_code', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '数量', name: 'amount', align: 'left', minWidth: '80'
			 			
			 		},  { 
			 			display: '单价', name: 'price', align: 'right', minWidth: '100',
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.price ==null ? 0 : rowdata.price, '${p04006 }', 1);
						},formatter:"###,##0.00"
			 		},{ 
			 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
						},formatter:"###,##0.00"
			 		}, { 
			 			display: '生产厂商名称', name: 'fac_name', align: 'left', minWidth: '80'
			 		}
			 		],
					delayLoad: true,//初始化不加载，默认false
			 		dataAction: 'server',dataType: 'server',usePager:true,
			 		url:'queryMatStorageQueryMatOutDept.do',
			 		width: '100%', height: '100%',rownumbers:true,
					selectRowButtonOnly:true,//heightDiff: -10,
					toolbar: { items: [
						{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
						{ line:true },
						{ text:'打印', id:'print', click: print,icon:'print'},
						{ line:true}
		
					]}
				});
	    		}else{
	    			grid = $("#maingrid").ligerGrid({
	    			columns: [{
						display: '出库单号', name: 'out_no', align: 'left', minWidth: '140',
						render : function(rowdata, rowindex, value) {
	                		if(value == '合计'){
	                			return value ; 
	                		}
	   						return '<a href=javascript:openUpdate("' 
	   							+ rowdata.group_id
	   							+ ',' + rowdata.hos_id 
	   							+ ',' + rowdata.copy_code
	   							+ ',' + rowdata.out_id
	   							+ ',' + rowdata.store_id
	   							+ '")>'+rowdata.out_no+'</a>';
	   					}	
					}, { 
			 			display: '出库日期', name: 'confirm_date', align: 'left', minWidth: '150'
			 		}, { 
			 			display: '业务类型', name: 'bus_type_name', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '领料科室', name: 'dept_name', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '状态', name: 'state', align: 'left', minWidth: '80',
			 			render : function(rowdata, rowindex, value) {
			 				if(rowdata.state == 1 ){
			 					return "未审核";
			 				}else if(rowdata.state == 2){
			 					return "审核";
			 				}else if(rowdata.state == 3){ 
			 					return "确定";
			 				}else {
			 					return "";
			 				}
						}
			 		}, { 
			 			display: '物资类别', name: 'mat_type_name', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '交易编码', name: 'bid_code', align: 'left', minWidth: '80'
			 		}, { 
			 			display: '材料编码', name: 'inv_code', align: 'left', minWidth: '80'
			 		},  { 
			 			display: '材料名称', name: 'inv_name', align: 'left', minWidth: '80'
			 		},  { 
			 			display: '规格型号', name: 'inv_model', align: 'left', minWidth: '80'
			 		},  { 
			 			display: '计量单位', name: 'unit_name', align: 'left', minWidth: '80'
			 		},  { 
			 			display: '单价', name: 'price', align: 'left', minWidth: '80',
			 			render : function(rowdata, rowindex, value) {
			 				if(rowdata.price == null ){
			 					return "";
			 				}
							return formatNumber(rowdata.price, '${p04006 }', 1);
						}
			 		},  { 
			 			display: '数量', name: 'amount', align: 'left', minWidth: '80'
			 		},  { 
			 			display: '金额', name: 'amount_money', align: 'right', minWidth: '100',
			 			render : function(rowdata, rowindex, value) {
							return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
						}
			 		},{ 
			 			display: '材料分类', name: 'mat_type_name', align: 'left', minWidth: '90'
			 		},{ 
			 			display: '批号', name: 'batch_no', align: 'left', minWidth: '90'
			 		},{ 
			 			display: '注册证号', name: 'cert_code', align: 'left', minWidth: '90'
			 		},{ 
			 			display: '有效期', name: 'inva_date', align: 'left', minWidth: '90'
			 		}
			 		, { 
			 			display: '生产厂商名称', name: 'fac_name', align: 'left', minWidth: '80'
			 		}
			 		],
				dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStorageQueryOutDetail.do?isCheck=false',
				width: '100%', height: '100%',rownumbers:true,
				delayLoad: true,//初始化不加载，默认false
				selectRowButtonOnly:true,//heightDiff: -10,
				toolbar: { items: [
					{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
					{ line:true },
					{ text:'打印', id:'print', click: print,icon:'print'},
					{ line:true}
	
				]}
			});
	    }
        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>出库日期："+$("#begin_confirm_date").val() +" 至  "+ $("#end_confirm_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="科室领用明细";
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
    	
		if($("#is_detail").is(':checked')==false){
			var printPara={
	         		title: "科室领用明细表",//标题
	         		columns: JSON.stringify(grid.getPrintColumns()),//表头
	         		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
	      			method_name: "queryMatStorageQueryMatOutDeptPrint",
	      			bean_name: "matInDetailService",
	      			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	      			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
	      			
	          	};
		}else{
			var printPara={
	         		title: "科室领用明细表",//标题
	         		columns: JSON.stringify(grid.getPrintColumns()),//表头
	         		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
	      			method_name: "queryMatStorageQueryOutDetailPrint",
	      			bean_name: "matInDetailService",
	      			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
	      			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
	          	};
		}
    	
   		
       	$.each(grid.options.parms,function(i,obj){
      			printPara[obj.name]=obj.value;
       	});
      		
       	officeGridPrint(printPara);
   		
    }
	

    function openUpdate(obj){
    		
    	var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "out_id=" + voStr[3].toString() + "&" 
			+ "store_id=" + voStr[4].toString();
		
		parent.$.ligerDialog.open({
			title:'出库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/outlibrary/matOutMainUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	
    }
 
   
    function loadDict(){
		//字典下拉框
		//var bus_type_code_paras={sel_flag : "out"};
		autocompleteAsyncMulti("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true,{codes : '3, 21, 9,11,13,23,49,50,19'},true,false,247);
		//autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true,"",true);
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,'',true);
		//loadComboBox({id:"#mat_type_code",url:"../../queryMatTypeDict.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'auto',maxWidth:'160',defaultSelect:false,async:false});
		//autocomplete("#mat_type_code", "../../queryMatTypeDict.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false,'',280);
		autocomplete("#sup_code", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		//autocomplete("#inv_code", "../../queryMatInv.do?isCheck=false", "id", "text", true, true,'',false,'',280);
		//autocomplete("#dept_code", "../../queryMatDept.do?isCheck=false", "id", "text", true, true, {is_last : '1'},false);
		autocomplete("#dept_code", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true, {is_last : '1',read_or_write : 1},false);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
		 
		$("#inv_code").ligerTextBox({width:280});
		
		$("#begin_confirm_date").ligerTextBox({width:100});
        $("#end_confirm_date").ligerTextBox({width:100});
        
        autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
        autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
        
        $("#begin_out_date").ligerTextBox({width:100});
        $("#end_out_date").ligerTextBox({width:100});
        
        $("#inv_model").ligerTextBox({width:247});
        $("#inv_msg").ligerTextBox({width:280});
        //autodate("#begin_out_date", "yyyy-mm-dd", "month_first");
        //autodate("#end_out_date", "yyyy-mm-dd", "month_last");
        
        //$("#is_charge").ligerTextBox({width:220});
	}  
	function is_detail(){
		if (grid) {
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
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	出库日期：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
	        <td align="right" class="l-table-edit-td"  width="10%">
            	仓库：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
        	</td>
        	
        	<td align="right" class="l-table-edit-td"  width="10%">
            	物资类别：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" validate="{required:true,maxlength:20}" />
        	</td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	制单日期：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="begin_out_date" id="begin_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至：
						</td>
						<td align="left" class="l-table-edit-td">
							<input class="Wdate" name="end_out_date" id="end_out_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
            		</tr>
				</table>
	        </td>
	        
	        <td align="right" class="l-table-edit-td"  width="10%">
            	领料科室：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="dept_code" type="text" id="dept_code" ltype="text" />
        	</td>
        	
        	<td align="right" class="l-table-edit-td"  width="10%">
            	材料信息：
        	</td>
        	
        	<!-- <td align="left" class="l-table-edit-td"  width="20%">
        		<input name="inv_code" type="text" id="inv_code" ltype="text"/>
        	</td> -->
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="inv_msg" type="text" id="inv_msg" ltype="text"/>
        	</td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	业务类型：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="bus_type_code" type="text" id="bus_type_code" ltype="text"  />
        	</td>
        	
        	<td align="right" class="l-table-edit-td"  width="10%">
            	是否收费：
        	</td>
        	
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="is_charge" type="text" id="is_charge" ltype="text" />
        	</td>
        	
        	<td align="right" class="l-table-edit-td"  width="10%"></td>
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="state" type="checkbox" id="state" ltype="text"/>包含未确认单据
        	</td>
        	
        	
		</tr>	
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	规格型号:
        	</td>
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="inv_model" type="text" id="inv_model" ltype="text"  />
        	</td>
        	<td align="right" class="l-table-edit-td"  width="10%"></td>
        	<td align="left" class="l-table-edit-td"  width="20%">
        		<input name="is_detail" type="checkbox" id="is_detail" ltype="text" onchange="is_detail()" />显示明细
        	</td>
		</tr>	
	</table>
	<div id="maingrid"></div>
</body>
</html>
