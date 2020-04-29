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
				{ display: '出库单号', name: 'OUT_NO', align: 'left', width: '130',},
				{ display: '卡片编号', name: 'ASS_CARD_NO', align: 'left', width: '130',},
				{ display: '仓库', name: 'store_name', align: 'left', width: '130',},    
				{ display: '部门编码', name: 'dept_code', align: 'left', width: '130',},    
				{ display: '部门名称', name: 'dept_name', align: 'left', width: '130',}, 
				{ display: '资产编码', name: 'ass_code', align: 'left', width: '130',},
				{ display: '资产名称', name: 'ass_name', align: 'left', width: '130',},
				{ display: '规格', name: 'ASS_SPEC', align: 'left', width: '130',},
				{ display: '型号', name: 'ASS_MODEL', align: 'left', width: '130',},
				{ display: '资产单位', name: 'unit_name', align: 'left', width: '130',},
				{ display: '单价', name: 'price', align: 'left', width: '130',
					render : function(rowdata, rowindex,value) {
						return formatNumber(value,'${ass_05005}',1);
						}	
					},
				{ display: '数量', name: 'out_amount', align: 'left', width: '130',},
				{ display: '生产厂商', name: 'fac_name', align: 'left', width: '130',},
				{ display: '供应商', name: 'sup_name', align: 'left', width: '130',},
				{ display: '制单日期', name: 'CREATE_DATE', align: 'left', width: '130',},
				{ display: '制单人', name: 'create_emp', align: 'left', width: '130',},
				{ display: '出库日期', name: 'AUDIT_DATE', align: 'left', width: '130',},
				{ display: '审核人', name: 'audit_emp', align: 'left', width: '130',},
			],
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			data:${assOutDetail},
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
