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
				return formatNumber(value, '${p08006 }', 1);
			},
			amount_money:function(value){//金额
				return formatNumber(value, '${p08005 }', 1);
			},
			amount:function(value){//数量
				return formatNumber(value, '${p08005 }', 1);
			}
	}; 
	
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		 
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
			name : 'med_type_id',
			value : liger.get("med_type_code").getValue() == null ? "" : liger.get("med_type_code").getValue()
		});  */
		grid.options.parms.push({
			name : 'bus_type_code',
			value : liger.get("bus_type_code").getValue() == null ? "" : "("+liger.get("bus_type_code").getValue().replace(/;/g,',')+")"
		}); 
		grid.options.parms.push({name : 'med_type_code',value : liger.get("med_type_code").getText().split(" ")[0]});
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
			name : 'is_last',
			value :  $("#is_last").is(":checked") ? "1":"0"
		});
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
					display: '期间', name: 'year_month', textField: 'year_month', align: 'left', minWidth: '150', frozen: true
				},  { 
		 			display: '科室名称', name: 'dept_name', align: 'left', minWidth: '80'
		 		},  { 
		 			display: '药品编码', name: 'inv_code', align: 'left', minWidth: '100', frozen: true
		 		}, { 
		 			display: '药品名称', name: 'inv_name', align: 'left', minWidth: '150', frozen: true
		 		}, { 
		 			display: '类别编码', name: 'med_type_code', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '类别名称', name: 'med_type_name', align: 'left', minWidth: '80'
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
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '单价', name: 'price', align: 'right', minWidth: '80',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08006 }', 1);
					}
		 		}, { 
		 			display: '金额', name: 'amount_money', align: 'right', minWidth: '80',formatter:"###,##0.00",
		 			render : function(rowdata, rowindex, value) {
						return value ==null ? "" : formatNumber(value, '${p08005 }', 1);
					}
		 		}, { 
		 			display: '库管员', name: 'confirmer', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '出库日期', name: 'confirm_date', align: 'left', minWidth: '80'
		 		},{ 
		 			display: '领用人', name: 'dept_emp', align: 'left', minWidth: '80'
		 		}, { 
		 			display: '备注', name: 'note', align: 'left', minWidth: '80'
		 		}
		 		],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedAccountReportDeptOutSearch.do',
			width: '100%', height: '98%',rownumbers:true,
			delayLoad: true,//初始化不加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
				{ line:true },
				{ text: '打印', id:'print', click: print, icon:'print' },
				{ line:true }
			]},
			lodop:{
				//title:"科室出库查询",
			}
			 
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //打印回调方法
    function lodopPrint(){
    	var head="<table class='head' width='100%'><tr><td>单位：${sessionScope.hos_name}</td></tr>";
 		head=head+"<tr><td>确认日期："+$("#begin_date").val()+"至"+$("#end_date").val()+"</td></tr>";
 		head=head+"</table>";
 		grid.options.lodop.head=head; 
 		grid.options.lodop.fn=renderFunc;
 		grid.options.lodop.title=liger.get("store_code").getText().split(" ")[1]+"药品库存汇总表";
    }
    
    
    //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
	}
   
    function loadDict(){
		//字典下拉框
		autocomplete("#dept_code", "../../queryMedAppDept.do?isCheck=false", "id", "text", true, true);
		autocomplete("#med_type_code", "../../queryMedType.do?isCheck=false", "id", "text", true, true,'',false,'',240);
		autocompleteAsyncMulti("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true, {codes : '3, 21, 9,11,13,23,49,50'}, true);
		//autocomplete("#inv_code", "../../queryMedInv.do?isCheck=false", "id", "text", true, true);
		autocomplete("#store_code", "../../queryMedStoreByRead.do?isCheck=false", "id", "text", true, true, "", true);
		autocomplete("#set_code", "../../queryMedVirStore.do?isCheck=false", "id", "text", true, true);
		autocomplete("#sup_code", "../../queryHosSup.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_charge", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
		autocomplete("#is_highvalue", "../../queryMedYearOrNo.do?isCheck=false", "id", "text", true, true);
        $("#begin_date").ligerTextBox({width:100});
        autodate("#begin_date", "yyyy-mm-dd", "month_first");
        $("#end_date").ligerTextBox({width:100});
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        $("#out_no").ligerTextBox({width:240});
        $("#inv_model").ligerTextBox({width:240});
        $("#inv_code").ligerTextBox({width:240});
        $("#med_type_code").ligerTextBox({width:240});
	}
    
  	//打印
	function print(){
    	
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	var date=$("input[name=end_date]").val();
    	
    	if(date==null||date==""){
    		
    		$.ligerDialog.error("请选择结束日期！");
    		
    		return;
    	}else{
 				//alert(date);
 	   			var dateString = date.split("-");
 	   			date=dateString[0]+dateString[1];
 	   			//return;
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
   			title:'科室出库查询表',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"统计日期: " + $("#begin_date").val() +" 至 "+ $("#end_date").val(),"colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"主管:","colspan":6,"br":false} ,
				{"cell":6,"value":"复核人:","colspan":8,"br":false},
				{"cell":colspan_num-2,"value":"制单人: ${sessionScope.user_name}","colspan":2,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMedAccountReportDeptOutSearch.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});

   		
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
				药品类别：
			</td>
            <td align="left" class="l-table-edit-td" width="20%">
            	<input name="med_type_code" type="text" id="med_type_code" ltype="text" validate="{required:false,maxlength:100}" />
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
            	药品信息：
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
				<input name="sup_code" type="text" id="sup_code" ltype="text" required="true" validate="{required:false}" />
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
            <td align="right" class="l-table-edit-td"  width="10%">
	        	<input name="is_last" type="checkbox" id="is_last" ltype="text" checked="checked"/>显示末级类别
	        </td>
         </tr>
     <tr>
       <td align="right" class="l-table-edit-td"  width="10%">
		规格型号:
		</td>
		  <td align="left" class="l-table-edit-td" width="20%">
			<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		  </td>
		
     </tr>
		
       
    </table>
	<div id="maingrid"></div>
</body>
</html>
