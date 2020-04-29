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
    <script src="<%=path%>/lib/stringbuffer.js"></script>
<script type="text/javascript">
     var grid;
     var detailGrid;
     var gridManager = null;
     
     $(function (){
    	 
		$("#layout1").ligerLayout({ 
			topHeight:125,
			centerWidth:888
		});
        loadDict();
        //loadForm();
        loadHead(null);	
     });  
     
    function loadDict(){
        //字典下拉框
    	$("#out_no").ligerTextBox({width:160,disabled: true }); 
            
    	$("#out_date").ligerTextBox({width:160}); 
    	
    	autodate("#out_date");
    	
    	$("#brief").ligerTextBox({width:465}); 
            
    	autocompleteAsync("#store_id", "../../../queryMatStore.do?isCheck=false", "id", "text", true, true);
    	if("${matOutMain.store_id}"){
	    	liger.get("store_id").setValue("${matOutMain.store_id},${matOutMain.store_no}");
	    	liger.get("store_id").setText("${matOutMain.store_code} ${matOutMain.store_name}");
    	}

    	autocompleteAsync("#bus_type_code", "../../queryMatBusType.do?isCheck=false", "id", "text", true, true, {codes : '15'}, false, "${matOutMain.bus_type_code}");
    	autocompleteAsync("#dept_id", "../../queryMatDeptDict.do?isCheck=false", "id", "text", true, true,{is_last: 1});
    	if("${matOutMain.dept_id}"){
	    	liger.get("dept_id").setValue("${matOutMain.dept_id},${matOutMain.dept_no}");
	    	liger.get("dept_id").setText("${matOutMain.dept_code} ${matOutMain.dept_name}");
    	}
    	autocomplete("#dept_emp", "../../queryMatEmpDict.do?isCheck=false", "id", "text", true, true, {dept_id: '${matOutMain.dept_id}'}); 
    	if("${matOutMain.dept_emp}"){
	    	liger.get("dept_emp").setValue("${matOutMain.dept_emp},${matOutMain.emp_no}");
	    	liger.get("dept_emp").setText("${matOutMain.emp_code} ${matOutMain.emp_name}");
    	}
    	autocomplete("#use_code", "../../queryMatOutUse.do?isCheck=false", "id", "text", true, true);
    	if("${matOutMain.use_code}"){
    		liger.get("use_code").setValue("${matOutMain.use_code}");
        	liger.get("use_code").setText("${matOutMain.use_name}");
    	}
    	autocomplete("#proj_code", "../../queryMatProj.do?isCheck=false", "id", "text", true, true);
    	if("${matOutMain.proj_id}"){
    		liger.get("proj_code").setValue("${matOutMain.proj_id}");
        	liger.get("proj_code").setText("${matOutMain.proj_code} ${matOutMain.proj_name}");
    	} 
     } 
    
    function loadHead(){
    	var matOutDetail  = ${matOutDetail};
    	grid = $("#maingrid").ligerGrid({
			columns: [ { 
				display: '材料编码', name: 'inv_code', align: 'left',width:80
			}, { 
				display: '材料名称(E)', name: 'inv_id', textField: 'inv_name',align: 'left',width:180,
	         }, { 
	         	display: '规格型号', name: 'inv_model', align: 'left',width:80
	         }, { 
	         	display: '计量单位', name: 'unit_name', align: 'left',width:60
	         }, { 
	         	display: '批号', name: 'batch_no', align: 'left',width:80
	         }, { 
	         	display: '当前库存', name: 'cur_amount', align: 'right',width:80
	         }, { 
	         	display: '即时库存', name: 'imme_amount', align: 'right',width:80
	         }, { 
	         	display: '数量(E)', name: 'amount', align: 'right', width:80, editor : {type : 'number'}
	         }, { 
				display: '单价', name: 'price', align: 'right',width:90,
				render : function(rowdata, rowindex, value) {
					rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '金额', name: 'amount_money', align: 'right',width:90,
				render : function(rowdata, rowindex, value) {
					rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '有效日期', name: 'inva_date', align: 'left',width:90
			}, { 
				display: '灭菌日期', name: 'disinfect_date', align: 'left',type: 'date',format: 'yyyy-MM-dd',width:90
			}, { 
				display: '条形码', name: 'bar_code', align: 'left',width:120
			}, { 
				display: '批发价格', name: 'sale_price', align: 'right',width:90,
				/*editor : {
					type : 'numberbox',
					precision : '${p04006 }'
        		},*/
				render : function(rowdata, rowindex, value) {
					rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
					return value == null ? "" : formatNumber(value, '${p04006 }', 1);
				}
			}, { 
				display: '批发金额', name: 'sale_money', align: 'right',width:90,
				render : function(rowdata, rowindex, value) {
					rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
					return value == null ? "" : formatNumber(value, '${p04005 }', 1);
				}
			}, { 
				display: '零售价格', name: 'sell_price', align: 'right',width:90,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04072 }', 0);
					return value == null ? "" : formatNumber(value, '${p04072 }', 1);
				}
			}, { 
				display: '零售金额', name: 'sell_money', align: 'right',width:90,
				render : function(rowdata, rowindex, value) {
					rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04073 }', 0);
					return value == null ? "" : formatNumber(value, '${p04073 }', 1);
				}
			}, { 
				display: '货位名称', name: 'location_name', align: 'left',width:180
			}, { 
				display: '备注(E)', name: 'note', align: 'left',width:180,
				editor : {
					type : 'text'
				}
			}, { 
				display: '批次数量合计', name: 'sum_amount',width:0 
			}, { 
				display: '批次明细', name: 'inv_detail_data',width:0 
			} ],
			usePager : false,width : '100%',height : '100%',enabledEdit :false,fixedCellHeight:true,data:matOutDetail,heightDiff:-20,
			checkbox: true, rownumbers:true, frozen:false,//这个属性有detail明细的时候必须为false,否则明细显示不出来
			detail: { onShowDetail: showBatchSn, reload: true, single: true},//材料批次明细
    	});

        gridManager = $("#maingrid").ligerGetGridManager();

		grid.toggleCol("sum_amount", false);
		grid.toggleCol("inv_detail_data", false);
        
    }
    
    var gridRowData;
    function showBatchSn(row, detailPanel,callback){
    	gridRowData = row;
    	batchSn = document.createElement('div');
        $(detailPanel).append(batchSn);
		detailGrid =$(batchSn).css({'margin-top':10, 'margin-left':60}).ligerGrid({
    		columns: [{ 
    			display: '材料编码', name: 'inv_code',width:80, align : 'left'
    		}, { 
    			display: '材料名称(E)', name: 'inv_id', textField: 'inv_name', width:100, align : 'left'
    		}, { 
    			display: '批次', name: 'batch_sn', align : 'left', width : 60
    		}, { 
     			display : '库存', name : 'cur_amount', width : 80, align : 'right'
     		}, { 
     			display : '即时库存', name : 'imme_amount', width : 80, align : 'right', 
     		}, { 
    			display: '数量(E)', name: 'amount', width: 80, align : 'right', editor : {type : 'float'}
    		}, { 
    			display: '单价', name: 'price', width: 90, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '金额', name: 'amount_money', align: 'right', width: 90,
    			render : function(rowdata, rowindex, value) {
    				rowdata.amount_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		}, { 
    			display: '批发单价', name: 'sale_price', width: 90, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			}
    		}, { 
    			display: '批发金额', name: 'sale_money', align: 'right', width: 90,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sale_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		}, { 
    			display: '零售单价', name: 'sell_price', width: 90, align : 'right',
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_price = value == null ? "" : formatNumber(value, '${p04006 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04006 }', 1);
    			} 
    		}, { 
    			display: '零售金额', name: 'sell_money', align: 'right', width: 90,
    			render : function(rowdata, rowindex, value) {
    				rowdata.sell_money = value == null ? "" : formatNumber(value, '${p04005 }', 0);
    				return value == null ? "" : formatNumber(value, '${p04005 }', 1);
    			}
    		} ], 
    		dataAction : 'server',dataType : 'server',usePager : true,checkbox: true,
    		rownumbers: true, enabledEdit : false, fixedCellHeight: true, frozen: false,
    		width: '65%',height: '90%',data : f_getInvDetailData(row)
		});
    }
    
    function f_getInvDetailData(rowdata){
    	var data = { Rows: [] };
		if(validateStr(rowdata.inv_id) && validateStr(rowdata.amount) && rowdata.amount != 0){
			//明细中有批次信息并且主数量和明细数量相等
			if(validateStr(rowdata.inv_detail_data) && validateStr(rowdata.sum_amount) && rowdata.amount == rowdata.sum_amount){
				var rows = jsonRowsToObject(rowdata.inv_detail_data, "out_detail_id");
				for(var i = 0; i < rows.length; i++){
					data.Rows.push(rows[i]);
				}
    		}else{
    			//明细中没有批次信息，需要根据先进先出从后台取出
        		var invPara = {
    				store_id : liger.get("store_id").getValue().split(",")[0], 
    				out_id : '${out_id}', 
            		inv_id : rowdata.inv_id, 
            		inv_batch : rowdata.inv_batch, 
            		bar_code : rowdata.bar_code, 
            		amount : rowdata.amount 
            	}
        		ajaxJsonObjectByUrl("../../queryMatInvByFifo.do?isCheck=false",invPara,function(responseData){
        			data = responseData;
                }, false);
				//变更主数据中材料批次信息
        		grid.updateCell('sum_amount', gridRowData.amount, gridRowData); 
        		grid.updateCell('inv_detail_data', JSON.stringify(data.Rows), gridRowData); 
        	}
    	}
        return data;
    }

	function validateStr(str){
		if(str == null || str == 'undefined' || str == ''){
			return false;
		}
		return true;
	}
	//关闭当前弹出框
	function this_close(){
		frameElement.dialog.close();
	}
    </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
	<input name="hos_id"  type="hidden" id="hos_id" value="${matOutMain.hos_id}" />
	<input name="group_id"  type="hidden" id="group_id" value="${matOutMain.group_id}" />
	<input name="copy_code"  type="hidden" id="copy_code" value="${matOutMain.copy_code}" />
	<input name="out_id"  type="hidden" id="out_id" value="${matOutMain.out_id}" />
  	<input name="state"  type="hidden" id="state" value="${matOutMain.state}" />
	<div id="layout1">
		<div position="top">
				<form name="form1" method="post"  id="form1" >
			        <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>出库单号：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_no" type="text" id="out_no" ltype="text" value="${matOutMain.out_no}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>业务类型：</td>
			            <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>仓库：</td>
			            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><span style="color:red">*</span>制单日期：</td>
			            <td align="left" class="l-table-edit-td"><input name="out_date" type="text" id="out_date" ltype="text" value="${matOutMain.out_date}" validate="{required:true,maxlength:20}"  class="Wdate"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领用科室：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领料人：</td>
			            <td align="left" class="l-table-edit-td"><input name="dept_emp" type="text" id="dept_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
			            <td align="left"></td>
			        </tr> 
			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	物资用途：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="use_code" type="text" id="use_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">
			            	项目：
			            </td>
			            <td align="left" class="l-table-edit-td">
			            	<input name="proj_code" type="text" id="proj_code" ltype="text" validate="{required:false}" />
			            </td>
			            <td align="left"></td>
			        </tr> 
			        
			        <tr>
			            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
			            <td align="left" class="l-table-edit-td" colspan="4"><input name="brief" type="text" id="brief" ltype="text"  value="${matOutMain.brief}"/></td>
			            <td align="left"></td>
			        </tr> 
			
			    </table>
			    
			    </form>
		
		</div>
	
		<div position="center" >
			<div id="maingrid"></div>
		</div>
		
	</div>

    </body>
</html>
