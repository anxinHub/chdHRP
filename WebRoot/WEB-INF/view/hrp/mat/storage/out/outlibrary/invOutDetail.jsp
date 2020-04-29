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
    var gridManager = null;

	$(function() {
		loadHead();
	});
	
	function loadHead() {
		
			grid = $("#maingrid").ligerGrid({
				columns : [ 
	                     { display: '出库单号', name: 'out_no', align: 'left', width: '130',},
	                     { display: '摘要', name: 'brief', align: 'left',width:'200'},
	                     { display: '仓库', name: 'store_name', align: 'left',width:'120'},
	                     { display: '领用科室', name: 'dept_name', align: 'left',width:'120'},
	                     { display: '材料编码', name: 'inv_code', align: 'left', width: '120'},
	     		 		 { display: '材料名称', name: 'inv_name', align: 'left', width: '120'},
	     		 		 { display: '计量单位', name: 'unit_name', align: 'left', width: '60'},
	     		 		 { display: '规格型号', name: 'inv_model', align: 'left', width: '120'},
	     		 		 { display: '单价', name: 'price', align: 'right', width: '90',
	     		 			render: function (rowdata, rowindex, value) {
	 							return formatNumber(value, '${p04006 }', 1);
	 						}
	     		 		 },
	     		 		 { display: '数量', name: 'amount', align: 'right', width: '80'},
	     		 		 { 
	     		 			display: '金额', name: 'amount_money', align: 'right', width: '90',
	     		 			render : function(rowdata, rowindex, value) {
	     						return formatNumber(rowdata.amount_money ==null ? 0 : rowdata.amount_money, '${p04005 }', 1);
	     					}
	     		 		 },
	     		 		 { display: '批号', name: 'batch_no', align: 'left', width: '80'},
	     		 		 { display: '条形码', name: 'bar_code', align: 'left', width: '80'},
	     		 		 { display: '生产厂商', name: 'fac_name', align: 'left', width: '80'},
	                     { display: '制单日期', name: 'out_date', align: 'left',width: '90'},
	                     { display: '出库日期', name: 'confirm_date', align: 'left',width: '90'},	 
	                     { display: '制单人', name: 'maker', align: 'left',width: '80'},
	                     { display: '业务类型', name: 'bus_type_name', align: 'left',width: '80'}
    	        ],
				dataAction : 'server',
				dataType : 'server',
				usePager : true,
				data:${invOutDetail},
				width : '100%', 
				height : '100%',
				checkbox : false,
				rownumbers : true,
				delayLoad : false,//初始化不加载，默认false
				checkBoxDisplay : false,
				selectRowButtonOnly : true,//heightDiff: -10,
				onsuccess : function() {}
		});
		gridManager = $("#maingrid").ligerGetGridManager();
		
	}

</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<div id="maingrid"></div>
</body>
</html>
