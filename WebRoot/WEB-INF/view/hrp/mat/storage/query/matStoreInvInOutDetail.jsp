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
<script src="<%=path%>/lib/hrp/mat/mat.js" type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    //var gridManager = null;

	$(function() {
		loadHead();
	});
	
	function loadHead() {
		
			grid = $("#maingrid").ligerGrid({
				columns : [ 
				        
	     		 		 { display: '出/入库', name: 'flag', align: 'center', width: '60',
	     		 			 render:function(data,index,value){
	     		 			 	if(value=='in'){
	     		 			 		return '入库'
	     		 			 	}else if(value=='out'){
	     		 			 		return '出库'
	     		 			 	}else{
	     		 			 		return '';
	     		 			 	}
	     		 		 }},
	                     { display: '制单日期', name: 'make_date', align: 'left',width: '90'},
	                     { display: '确认日期', name: 'confirm_date', align: 'left',width: '90'},	 
	                     { display: '出入库单号', name: 'no', align: 'left', width: '130',},
	                     { display: '业务类型', name: 'bus_type_name', align: 'left',width: '80'},
	                     { display: '领用科室/库房', name: 'dept_name', align: 'left',width:'160'},
	                     { display: '供应商/库房', name: 'sup_name', align: 'left',width: '160'},
	     		 		 { display: '生产商', name: 'fac_name', align: 'left', width: '120'},
	     		 		 { display: '单价', name: 'price', align: 'right', width: '80',
	     		 			render: function (rowdata, rowindex, value) {
	 							return formatNumber(value, '${p04006 }', 1);
	 						}
	     		 		 },
	     		 		 { display: '数量', name: 'amount', align: 'right', width: '80'},
	     		 		 { 
	     		 			display: '金额', name: 'amount_money', align: 'right', width: '100',
	     		 			render : function(rowdata, rowindex, value) {
	     						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
	     					}
	     		 		 },
	     		 		 { display: '结存数量', name: 'balance_amount', align: 'right', width: '80'},
	     		 		 { 
	     		 			display: '结存金额', name: 'balance_amount_money', align: 'right', width: '100',
	     		 			render : function(rowdata, rowindex, value) {
	     						return formatNumber(rowdata.balance_amount_money ==null ? 0 : rowdata.balance_amount_money, '${p04005 }', 1);
	     					}
	     		 		 },
	     		 		 { display: '批号', name: 'batch_no', align: 'left', width: '80'},
	     		 		 { display: '条形码', name: 'bar_code', align: 'left', width: '80'},
	                     { display: '制单人', name: 'maker_name', align: 'left',width: '80'},
	                     { display: '采购员', name: 'stocker_name', align: 'left',width: '80'}
    	        ],
    	        rowAttrRender:function(rowdata,rowid){
    	 			if(rowdata.flag=='in'){
    	 				return "style='background:rgba(252,222,250, 0.31)'"
    	 			}else if(rowdata.flag=='out'){
    	 				return "style='background:rgba(185,250,219, 0.31)'"
    	 			}else if(rowdata.flag=='init' || rowdata.flag=='end'){
    	 				return "style='background:rgba(207,223,238, 1)'"
    	 			}
     			},
     			alternatingRow:false,//是否增加奇偶颜色差异效果
				dataAction : 'server',
				dataType : 'server',
				usePager : true,
				data:${matStoreInvInOutDetail},
				width : '100%', 
				height : '100%',
				checkbox : false,
				rownumbers : true,
				delayLoad : false,//初始化不加载，默认false
				checkBoxDisplay : false,
				selectRowButtonOnly : true,//heightDiff: -10,
				onsuccess : function() {}
		});
		//gridManager = $("#maingrid").ligerGetGridManager();
		
	}
	
	
	

</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="80%" style="font-size:20px;font-weight:bolder ;">
		<tr>
			<td align="right" class="l-table-edit-td"  >仓库名称：</td>
			<td align="left" class="l-table-edit-td"  >
				${storeMsg.store_name }
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"  >材料编码：</td>
			<td align="left" class="l-table-edit-td"  >
				${invMsg.inv_code }
			</td>
			<td align="right" class="l-table-edit-td"  >材料名称：</td>
			<td align="left" class="l-table-edit-td"  >
				${invMsg.inv_name }
			</td>
			<td align="right" class="l-table-edit-td"  >规格型号：</td>
			<td align="left" class="l-table-edit-td"  >
				${invMsg.inv_model }
			</td>
			<td align="right" class="l-table-edit-td"  >单&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
			<td align="left" class="l-table-edit-td"  >
				${invMsg.unit_name }
			</td>
		</tr>
		
	</table>
	<div id="maingrid"></div>
</body>
</html>
