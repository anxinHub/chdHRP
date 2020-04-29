<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <!-- jsp:include page="${path}/inc.jsp"/-->
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var grid;
     var gridManager;
     
     $(function (){
		loadDict()//加载下拉框
		loadHead();
		queryDetail();
		
     });  
     
     function queryDetail(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
         //根据表字段进行添加查询条件
 		grid.options.parms.push({
 			name : 'out_id',
 			value : '${medAffiOut.out_id}'
 		});

     	//加载查询条件
     	grid.loadData(grid.where);
 	}
  
    function loadDict(){
    	//字典下拉框
    	autocomplete("#bus_type_code", "../../queryMedBusType.do?isCheck=false", "id", "text", true, true,{codes:'9,28,30,33,34'}, false, '${medAffiOut.bus_type_code}');//业务类型
    	autocomplete("#store_code", "../../queryMedStore.do?isCheck=false", "id", "text", true, true, "", false, '${medAffiOut.store_id},${medAffiOut.store_no}');//仓库
		autocomplete("#dept_code", "../../queryMedDept.do?isCheck=false", "id", "text", true, true,"", false, '${medAffiOut.dept_id},${medAffiOut.dept_no}');//领料科室
    	autocomplete("#dept_emp", "../../queryMedEmp.do?isCheck=false", "id", "text", true, true,"", false, '${medAffiOut.dept_emp}');//领料人
    	
		
        $("#out_no").ligerTextBox({width:160, disabled:true});
        $("#bus_type_code").ligerTextBox({width:160});
        $("#store_code").ligerTextBox({width:160});
        
        $("#order_date").ligerTextBox({width:160});
        $("#dept_code").ligerTextBox({width:160});
        $("#dept_emp").ligerTextBox({width:160});
        
        $("#brief").ligerTextBox({width:340});
        
		//$("#print").ligerButton({click: printDate, width:90});
		$("#close").ligerButton({click: this_close, width:90});
     } 
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{display: '药品编码', name: 'inv_code', align: 'left', width: 150,
							totalSummary: {
			                    type: 'sum',
			                    render: function (suminf, column, cell) {
			                        return '<div>合计</div>';
			                    }
			                }	
						}, 
					  {display: '药品名称', name: 'inv_name', align: 'left', width: 150}, 
					  {display: '规格型号', name: 'inv_model', align: 'left', width: 120}, 
					  {display: '计量单位', name: 'unit_name', align: 'left', width: 120}, 
					  {display: '批号', name: 'batch_no', align: 'left', width: 120}, 
					  {display: '批次', name: 'batch_sn', align: 'left', width: 120},
					  {display: '当前库存', name: 'cur_amount', align: 'right', width: 90,
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.cur_amount ==null ? 0 : rowdata.cur_amount,2,1);
							}  
					  },
					  {display: '数量', name: 'amount', align: 'right', width: 90,
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.amount ==null ? 0 : rowdata.amount,2,1);
							}   
					  }, 
					  {display: '单价', name: 'price', align: 'right', width: 90,
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.price ==null ? 0 : rowdata.price,'${p08006 }',1);
							}   
					  }, 
					  {display: '金额', name: 'amount_money', align: 'right', width: 90,
						  render:function(rowdata){
							  return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money,'${p08005 }',1);
			             },
			             totalSummary: {
		                        type: 'sum',
		                        render: function (suminf, column, cell) {
		                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,2,1)+ '</div>';
		                        }
		                    }  
					  }, 
					  {display: '有效日期', name: 'inva_date', align: 'left', width: 100},
					  {display: '灭菌日期', name: 'disinfect_date', align: 'left', width: 100}, 
					  {display: '条形码', name: 'bar_code', align: 'left', width: 100}, 
					  {display: '零售单价', name: 'sell_price', align: 'right', width: 100,
						  render : function(rowdata, rowindex, value) {
							  return formatNumber(rowdata.sell_price ==null ? 0 : rowdata.sell_price,'${p08006 }',1);
							}  
					  }, 
					  {display: '零售金额', name: 'sell_money', align: 'right', width: 100,
						  render:function(rowdata){
			            		return formatNumber(rowdata.sell_money ==null ? 0 : rowdata.sell_money,'${p08005 }',1);
			             },
			             totalSummary: {
		                        type: 'sum',
		                        render: function (suminf, column, cell) {
		                            return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum,'${p08005 }',1)+ '</div>';
		                        }
		                    }
					  },
					  {display: '货位编码', name: 'location_code', align: 'left', width: 100}, 
					  {display: '货位名称', name: 'location_name', align: 'left', width: 100}, 
					  {display: '备注', name: 'note', align: 'left', width: 100}
			],
			dataAction : 'server', dataType : 'server', usePager : false,
			url : 'queryMedOrderInitAffiOutDetail.do?isCheck=false',
			width : '95%', height : '85%', checkbox : false, rownumbers : true,
			delayLoad : true,//初始化不加载，默认false
			selectRowButtonOnly : true
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
   
	function printDate(){
	}

	function this_close(){
		frameElement.dialog.close();
	}
	
    </script>
  
</head>
  
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
	        <tr>
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>出库单号：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="out_no" type="text" id="out_no" value="${medAffiOut.out_no}" ltype="text" disabled="disabled"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td"><font color="red">*</font>业务类型：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="bus_type_code" type="text" id="bus_type_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td"><font color="red">*</font>仓库：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="store_code" type="text" id="store_code" required="true" ltype="text" validate="{required:true}" />
	            </td>
	        </tr> 
	        <tr>
	        	<td align="right" class="l-table-edit-td" ><font color="red">*</font>出库日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="out_date" id="out_date" required="true" type="text" value="${medAffiOut.out_date}" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>领料科室：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dept_code" type="text" id="dept_code"  ltype="text" required="true"  validate="{required:true,maxlength:20}"/>
	            </td>
	           
	            <td align="right" class="l-table-edit-td" ><font color="red">*</font>领料人：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input name="dept_emp" type="text" id="dept_emp"  ltype="text" required="true"  validate="{required:true,maxlength:20}"/>
	            </td>
	        </tr> 
	       
			<tr>
				<td align="right" class="l-table-edit-td" >备注：</td>
	            <td align="left" class="l-table-edit-td" colspan="3">
	            	<input name="brief" type="text" id="brief" ltype="text" value="${medAffiOut.brief}"  validate="{required:true,maxlength:50}" />
	            </td>
	            
	            <td></td>
	            <td></td>
			</tr>
	    </table>
    </form>
    <div style="width: 100%; height: 100%;">
		<div id="maingrid"></div>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%"  style="margin-top: 5px;">
			<tr>	
				<td align="center" class="l-table-edit-td" >
					<!-- <button id ="print" accessKey="P"><b>打印（<u>P</u>）</b></button>
					&nbsp;&nbsp; -->
					<button id ="close" accessKey="C"><b>关闭（<u>C</u>）</b></button>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
