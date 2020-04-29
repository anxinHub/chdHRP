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
		grid.options.parms.push({name : 'year',value : $("#year").val()});
		grid.options.parms.push({name : 'month',value : $("#month").val()});
		grid.options.parms.push({name : 'store_id',value : liger.get("store_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'store_no',value : liger.get("store_code").getValue().split(",")[1]}); 
		grid.options.parms.push({name : 'med_type_id',value : liger.get("med_type_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'med_type_no',value : liger.get("med_type_code").getValue().split(",")[1]});
		grid.options.parms.push({name : 'inv_id',value : liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name : 'inv_no',value : liger.get("inv_code").getValue().split(",")[1]});
		if($("#c_batch_no").is(":checked")){//不按照批号查询,不传值
			grid.options.parms.push({name : 'batch_no',value : $("#batch_no").val()});
		}
		grid.options.parms.push({name : 'is_charge',value : liger.get("is_charge").getValue()}); 
		grid.options.parms.push({name : 'c_state',value : $("#c_state").is(":checked") ? '' : 1}); 
		grid.options.parms.push({name : 'c_batch_no',value : $("#c_batch_no").is(":checked") ? 1 : ''});
		grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{ 
		 			display: '药品编码', name: 'inv_code', align: 'left'
		 		}, { 
		 			display: '药品名称', name: 'inv_name', align: 'left'
		 		}, { 
		 			display: '规格型号', name: 'inv_model', align: 'left'
		 		},  { 
		 			display: '计量单位', name: 'unit_name', align: 'left'
		 		}, { 
		 			display: '单据类型', name: 'bus_type_name', align: 'left'
		 		},  { 
		 			display: '单据号', name: 'in_no', align: 'left'
		 		},
		 		 { 
		 			display: '开单日期', name: 'in_date', align: 'left'
		 		},
		 		{ display: '入库',
		 		 columns : [{
		 				display: '数量', name: 'in_amount', align: 'left',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, 2, 1);
						}
			 		}, { 
			 			display: '金额', name: 'in_amount_money', align: 'right',
			 			render : function(rowdata, rowindex, value) {
							return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
						}
			 		}]
		 		}, 
		 		{ display: '出库',
			 		 columns : [{
			 				display: '数量', name: 'out_amount', align: 'left',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, 2, 1);
							}
				 		}, { 
				 			display: '金额', name: 'out_amount_money', align: 'right',
				 			render : function(rowdata, rowindex, value) {
								return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
							}
				 		}]
			 		}, { display: '结存',
				 		 columns : [{
				 				display: '数量', name: 'amount', align: 'left',
					 			render : function(rowdata, rowindex, value) {
									return value ==null ? "" : formatNumber(value, 2, 1);
								}
					 		}, { 
					 			display: '金额', name: 'money', align: 'right',
					 			render : function(rowdata, rowindex, value) {
									return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
								}
					 		}]
				 		}],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAffiStorageQueryStockDetail.do',
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
    
    function changeColumn(){
    	
    	var c_batch_no = $("#c_batch_no").is(":checked") ? 1 : 0

    	if(c_batch_no == 1){
    		
    		var columns =  [{ 
    			
	 			display: '药品编码', name: 'inv_code', align: 'left'
	 		}, { 
	 			display: '药品名称', name: 'inv_name', align: 'left'
	 		}, { 
	 			display: '规格型号', name: 'inv_model', align: 'left'
	 		},  { 
	 			display: '计量单位', name: 'unit_name', align: 'left'
	 		},  { 
	 			display: '批号', name: 'batch_no', align: 'left'
	 		},  { 
	 			display: '单据类型', name: 'bus_type_name', align: 'left'
	 		},  { 
	 			display: '单据号', name: 'in_no', align: 'left'
	 		},
	 		 { 
	 			display: '开单日期', name: 'in_date', align: 'left'
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
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
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
							return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
						}
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
								return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
							}
				 		}]
			 		}];
    		c_batch_no = 1
    		
    	}else if(c_batch_no==0){
    		var columns = [{ 
	 			display: '药品编码', name: 'inv_code', align: 'left'
	 		}, { 
	 			display: '药品名称', name: 'inv_name', align: 'left'
	 		}, { 
	 			display: '规格型号', name: 'inv_model', align: 'left'
	 		},  { 
	 			display: '计量单位', name: 'unit_name', align: 'left'
	 		}, { 
	 			display: '单据类型', name: 'bus_type_name', align: 'left'
	 		},  { 
	 			display: '单据号', name: 'in_no', align: 'left'
	 		},
	 		 { 
	 			display: '开单日期', name: 'in_date', align: 'left'
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
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
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
							return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
						}
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
								return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
							}
				 		}]
			 		}];
    		c_batch_no = 0
    	}
    	
        grid.set('columns', columns); 
        grid.reRender();
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
    	
    	var selPara={};
    	
    	$.each(grid.options.parms,function(i,obj){
    		
    		selPara[obj.name]=obj.value;
    		
    	});
   		
		var dates = getCurrentDate();
    	
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'库存明细查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#year").val() +" 年  "+ $("#month").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":3,"br":false} ,
				{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
				{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMedAffiStorageQueryStockDetail.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
    }
   
    function loadDict(){
		//字典下拉框
		autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true,{is_com : 1},true);
		autocomplete("#med_type_code", "../../queryMedTypeDict.do?isCheck=false", "id", "text", true, true, {is_last : 1},false);
		autocomplete("#is_charge", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
	/* 	autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true,'',false,'',200); */
        $("#year").ligerTextBox({width:80});
        autodate("#year", "yyyy", "yyyy");
        $("#month").ligerTextBox({width:100});
        autodate("#month", "MM", "MM");
        $("#batch_no").ligerTextBox({width:160});
        $("#inv_model").ligerTextBox({width:200});
        $("#inv_code").ligerTextBox({width:200});
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
            	查询期间：
            </td>
           <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="year" id="year" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  > 至 </td>
						<td>
							<input class="Wdate" name="month" id="month" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'MM'})"/>
						</td>
            		</tr>
				</table>
	        </td>  
	        
	        <td align="right" class="l-table-edit-td"  width="10%">
				仓库：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
            </td>
            
            <td align="right" class="l-table-edit-td"  width="10%">
				药品类别：
			</td>
			<td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
        </tr> 
        <tr>
        	
	        <td align="right" class="l-table-edit-td" width="10%">药品信息：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
	       <td align="right" class="l-table-edit-td"  width="10%">是否收费： </td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="is_charge" type="text" id="is_charge" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            
	        <td align="right" class="l-table-edit-td"  width="10%">查&nbsp;&nbsp;&nbsp;&nbsp;询：</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	 <table>
            	 	<tr>
            	 		<td><input name="c_state" type="checkbox" id="c_state" ltype="text" /> 包含未确定单价 &nbsp;</td>
            	 		<td><input name="c_batch_no" type="checkbox" id="c_batch_no" ltype="text" onclick="changeColumn()"/> 按照批查询 &nbsp;</td>
            	 	</tr>
            	 </table>
            </td>
            
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td" width="10%">
				规格型号：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td"  width="10%">
            	批号：
            </td>
             <td align="left" class="l-table-edit-td" width="20%">
            	<input name="batch_no" type="text" id="batch_no" ltype="text" required="true" validate="{required:true}" />
            </td>
        </tr>
    </table>
		 
	</div>
	<div id="maingrid"></div>
</body>
</html>
