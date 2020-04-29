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
    		in_amount:function(value){//入库数量
				return formatNumber(value, 2, 1);
			},
			in_amount_money:function(value){//入库金额
				return formatNumber(value, '${p04006 }', 1);
			},
			out_amount:function(value){//出库数量
				return formatNumber(value, 2, 1);
			},
			out_amount_money:function(value){//出库金额
				return formatNumber(value, '${p04006 }', 1);
			},
			amount:function(value){//结存数量
				return formatNumber(value, 2, 1);
			},
			money:function(value){//结存金额
				return formatNumber(value, '${p04006 }', 1);
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
        grid.options.parms.push({name : 'sup_id',value : liger.get("sup_id").getValue().split(",")[0]}); 
        grid.options.parms.push({name : 'dept_id',value : liger.get("dept_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'year',value : $("#year_month").val().split("-")[0]});
		grid.options.parms.push({name : 'month',value : $("#year_month").val().split("-")[1]});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
// 		grid.options.parms.push({name : 'mat_type_id',value : liger.get("mat_type_code").getValue().split(",")[0]}); 
// 		grid.options.parms.push({name : 'mat_type_no',value : liger.get("mat_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'mat_type_code',value : liger.get("mat_type_code").getText().split(" ")[0]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		grid.options.parms.push({name : 'c_state',value : $("#c_state").is(":checked") ? '' : 1}); 
		grid.options.parms.push({name : 'c_batch_no',value : $("#c_batch_no").is(":checked") ? 1 : ''});
		grid.options.parms.push({name : 'cert_code',value : $("#cert_code").val()});
	
		if(($("#c_batch_no").is(":checked") ? 1 : '')==1){
			grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
		}
		grid.options.parms.push({ 
			name : 'inv_model',//规格型号  
			value : $("#inv_model").val()
		});
		grid.options.parms.push({
			name : 'show_zero',
			value : $("#show_zero").prop("checked") ? 1 : 0
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 			display: '材料编码', name: 'inv_code', align: 'left', width: 110
		 		}, { 
		 			display: '材料名称', name: 'inv_name', align: 'left', width: 240
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left',width: 120,
		 		},  { 
		 			display: '计量单位', name: 'unit_name', align: 'left', width: 60
		 		}, { 
		 			display: '单据类型', name: 'bus_type_name', align: 'left', width: 80
		 		}, { 
		 			display: '供应商', name: 'sup_name', align: 'left', width: 150
		 		}, { 
		 			display: '科室 / 调拨库房', name: 'dept_name', align: 'left', width: 110
		 		},  { 
		 			display: '单据号', name: 'in_no', align: 'left', width: 130,
		 			 render : function(rowdata, rowindex, value) {
		 				 if(rowdata.in_no==null){
		 					 return '';
		 				 }else{
		 					 if(rowdata.bus_type_code==2){
		 						return '<a href=javascript:update_open("' 
								+ rowdata.group_id
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code
								+ ',' + rowdata.in_id
								+ '")>'+rowdata.in_no+'</a>';
		 					 }else  if(rowdata.bus_type_code==3){
		 						return '<a href=javascript:openUpdate("' 
								+ rowdata.group_id
								+ ',' + rowdata.hos_id 
								+ ',' + rowdata.copy_code
								+ ',' + rowdata.in_id
								+ '")>'+rowdata.in_no+'</a>';
		 					 }else  if(rowdata.bus_type_code==14){
		 						 //增加的移入单号
			 						return '<a href=javascript:update_open("' 
									+ rowdata.group_id
									+ ',' + rowdata.hos_id 
									+ ',' + rowdata.copy_code
									+ ',' + rowdata.in_id
									+ '")>'+rowdata.in_no+'</a>';
			 			    }else  if(rowdata.bus_type_code==15){
			 			    	//增加的移出单号
				 						return '<a href=javascript:openUpdate("' 
										+ rowdata.group_id
										+ ',' + rowdata.hos_id 
										+ ',' + rowdata.copy_code
										+ ',' + rowdata.in_id
										+ '")>'+rowdata.in_no+'</a>';
				 					 }
		 					
		 				 }
							
						}
		 		}, {  
		 			display: '编制日期', name: 'confirm_date', align: 'left', width: 90, formatter: "yyyy-MM-dd"
		 		}, { 
		 			display: '注册证号', name: 'cert_code', align: 'left', width: 120
		 		}, { 
		 			display: '有效期', name: 'inva_date', align: 'left', width: 90
		 		},
		 		{ display: '入库',
		 		 columns : [{
		 				display: '数量', name: 'in_amount', align: 'left', width: 80,
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		}, { 
			 			display: '金额', name: 'in_amount_money', align: 'right',width: 80,
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
						},formatter:"###,##0.00"
			 		}]
		 		}, 
		 		{ display: '出库',
			 		 columns : [{
			 				display: '数量', name: 'out_amount', align: 'left',width: 80,
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
				 		}, { 
				 			display: '金额', name: 'out_amount_money', align: 'right',width: 80,
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
							},formatter:"###,##0.00"
				 		}]
			 		}, { display: '结存',
				 		 columns : [{
				 				display: '数量', name: 'amount', align: 'left',width: 80,
					 			render : function(rowdata, rowindex, value) {
									return value ==null ? "" : formatNumber(value, 2, 1);
								}
					 		}, { 
					 			display: '金额', name: 'money', align: 'right',width: 80,
					 			render : function(rowdata, rowindex, value) {
									return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
								},formatter:"###,##0.00"
					 		}]
				 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatStorageQueryStockDetail.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
			       			{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
							{ line:true },
							{ text: '打印', id:'print', click: print, icon:'print' },
			   				{ line:true }
			]},
			onDblClickRow : function (rowdata, rowindex, value)
			{
				if(rowdata.bus_type_code==2){
					update_open(
							rowdata.group_id   + "," + 
							rowdata.hos_id   + "," + 
							rowdata.copy_code   + "," + 
							rowdata.in_id   
						);
				}else if(rowdata.bus_type_code==3){
					openUpdate(
							rowdata.group_id   + "," + 
							rowdata.hos_id   + "," + 
							rowdata.copy_code   + "," + 
							rowdata.in_id   
						);
				}else if(rowdata.bus_type_code==14){
					//增加的移入单号
					update_open(
							rowdata.group_id   + "," + 
							rowdata.hos_id   + "," + 
							rowdata.copy_code   + "," + 
							rowdata.in_id   
						);
				}else if(rowdata.bus_type_code==15){
					//增加的移出单号
					openUpdate(
							rowdata.group_id   + "," + 
							rowdata.hos_id   + "," + 
							rowdata.copy_code   + "," + 
							rowdata.in_id   
						);
				}
				
			}  
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
     function openUpdate(obj){
    	
    	var voStr = obj.split(",");
		var paras = 
			"group_id=" + voStr[0].toString() + "&" 
			+ "hos_id=" + voStr[1].toString() + "&" 
			+ "copy_code=" + voStr[2].toString() + "&" 
			+ "out_id=" + voStr[3].toString();
		
		parent.$.ligerDialog.open({
			title:'出库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/out/outlibrary/matOutMainUpdatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});	
    }
    //入库单修改
    function update_open(obj){		
    
		var vo = obj.split(",");
		var paras = 
			"group_id="+vo[0] +"&"+ 
			"hos_id="+vo[1] +"&"+ 
			"copy_code="+vo[2] +"&"+ 
			"in_id="+vo[3];
		parent.$.ligerDialog.open({
			title: '入库单修改',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/mat/storage/in/updatePage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: true, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    } 
    
    function changeColumn(){
    	
    	var c_batch_no = $("#c_batch_no").is(":checked") ? 1 : 0

    	if(c_batch_no == 1){
    		
    		var columns =  [{ 
    			
	 			display: '材料编码', name: 'inv_code', align: 'left', width: 110
	 		}, { 
	 			display: '材料名称', name: 'inv_name', align: 'left', width: 240
	 		}, { 
	 			display: '规格型号', name: 'inv_model', align: 'left'
	 		},  { 
	 			display: '计量单位', name: 'unit_name', align: 'left', width: 60
	 		},  { 
	 			display: '批号', name: 'batch_no', align: 'left', width: 110
	 		},  { 
	 			display: '单据类型', name: 'bus_type_name', align: 'left', width: 80
	 		},  { 
	 			display: '单据号', name: 'in_no', align: 'left', width: 130,
	 			
	 		}, { 
	 			display: '编制日期', name: 'in_date', align: 'left', width: 90, formatter: "yyyy-MM-dd"
	 		},
	 		{ display: '入库',
	 		 columns : [{
	 				display: '数量', name: 'in_amount', align: 'left',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '单价', name: 'in_amount_money', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
					},formatter:"###,##0.00"
		 		}]
	 		}, 
	 		{ display: '出库',
		 		 columns : [{
		 				display: '数量', name: 'out_amount', align: 'left',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		}, { 
			 			display: '单价', name: 'out_amount_money', align: 'right',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
						},formatter:"###,##0.00"
			 		}]
		 		}, { display: '结存',
			 		 columns : [{
			 				display: '数量', name: 'amount', align: 'left',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
				 		}, { 
				 			display: '单价', name: 'money', align: 'right',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
							},formatter:"###,##0.00"
				 		}]
			 		}];
    		c_batch_no = 1
    		
    	}else if(c_batch_no==0){
    		var columns = [{ 
	 			display: '材料编码', name: 'inv_code', align: 'left', width: 110
	 		}, { 
	 			display: '材料名称', name: 'inv_name', align: 'left', width: 240
	 		}, { 
	 			display: '规格型号', name: 'inv_model', align: 'left'
	 		},  { 
	 			display: '计量单位', name: 'unit_name', align: 'left', width: 60
	 		}, { 
	 			display: '单据类型', name: 'bus_type_name', align: 'left', width: 80
	 		},  { 
	 			display: '单据号', name: 'in_no', align: 'left', width: 130
	 		}, { 
	 			display: '编制日期', name: 'in_date', align: 'left', width: 90, formatter: "yyyy-MM-dd"
	 		},
	 		{ display: '入库',
	 		 columns : [{
	 				display: '数量', name: 'in_amount', align: 'left',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, 2, 1);
					}
		 		}, { 
		 			display: '单价', name: 'in_amount_money', align: 'right',
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
					},formatter:"###,##0.00"
		 		}]
	 		}, 
	 		{ display: '出库',
		 		 columns : [{
		 				display: '数量', name: 'out_amount', align: 'left',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		}, { 
			 			display: '单价', name: 'out_amount_money', align: 'right',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
						},formatter:"###,##0.00"
			 		}]
		 		}, { display: '结存',
			 		 columns : [{
			 				display: '数量', name: 'amount', align: 'left',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
				 		}, { 
				 			display: '单价', name: 'money', align: 'right',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, '${p04006 }', 1);
							},formatter:"###,##0.00"
				 		}]
			 		}];
    		c_batch_no = 0
    	}
    	
        grid.set('columns', columns); 
        //grid.reRender();
        query();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>查询期间："+$("#year").val()+"年"+$("#month").val()+"日"+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title="库存明细查询";
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
          		title: "库存明细查询表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.mat.service.storage.query.MatInDetailService",
       			method_name: "queryMatStorageQueryStockDetailPrint",
       			bean_name: "matInDetailService",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
           	};
        	$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,"",false,'',160);
		//loadComboBox({id:"#dept_id",url:"../../queryMatDept.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'auto',maxWidth:'160',defaultSelect:false,async:false})
		autocomplete("#dept_id", "../../queryMatDeptDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},false);
		
		//autocomplete("#store_code", "../../queryMatStoreByRead.do?isCheck=false", "id", "text", true, true,"",true);
		autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true,{read_or_write : 1},true);
		//autocomplete("#mat_type_code", "../../queryMatTypeDictCode.do?isCheck=false", "id", "text", true, true, "",false);
		autocomplete("#mat_type_code", "../../queryMatTypeDictDate.do?isCheck=false", "id", "text", true, true, {read_or_write : 1},false);
		autocomplete("#is_charge", "../../queryMatYearOrNo.do?isCheck=false", "id", "text", true, true);
/* 		loadComboBox({id:"#inv_code",url:"../../queryMatInv.do?isCheck=false",value:"id",text:"text",autocomplete:true,hightLight:true,selectBoxWidth:'auto',maxWidth:'160',defaultSelect:false,async:false}) */
        $("#year_month").ligerTextBox({width:160});
        $("#inv_code").ligerTextBox({width:160});
        autodate("#year_month", "yyyy-MM", "yyyy-MM");
        $("#batch_no").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:160});
        $("#cert_code").ligerTextBox({width:160});
	}  
	
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
            	查询期间：
            </td>
			
			<td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="year_month" id="year_month" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM'})"/>
						</td>
            		</tr>
				</table>
	        </td>
			
			<td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text"  />
            </td>
			
			<td align="right" class="l-table-edit-td"  width="10%">
				物资类别：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="mat_type_code" type="text" id="mat_type_code" ltype="text" />
            </td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				材料信息：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				批号：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				规格型号：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text"  />
            </td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				是否收费：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				查询：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="c_state" type="checkbox" id="c_state" ltype="text" /> 包含未确定单价 &nbsp;
				<input name="c_batch_no" type="checkbox" id="c_batch_no" ltype="text" onclick="changeColumn()"/> 按照批查询 &nbsp;
            </td>
            
             <td align="right" class="l-table-edit-td"  width="10%">
				供应商：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:false}" />
            </td>
		</tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  width="10%">
				科室：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:false}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				注册证号：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="cert_code" type="text" id="cert_code" ltype="text" required="true" validate="{required:true}" />
            </td>
            <td align="left" class="l-table-edit-td">
    			<input name="show_zero" type="checkbox" id="show_zero" ltype="text" />不显示零库存
			</td>
		</tr>
	</table>
	
	<div id="maingrid"></div>
</body>
</html>
