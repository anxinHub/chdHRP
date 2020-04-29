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
	var fac_id = "${fac_id}";
	var fac_name = "${fac_name}";
	
	$(function() {
		
		loadDict();//加载下拉框
		loadHead();
		query();
	});
	//查询
	function query() {
		grid.options.parms=[];
		grid.options.newPage=1;
		
		grid.options.parms.push({name:'fac_id',value:fac_id}); 

		//加载查询条件
		grid.loadData(grid.where);
	}
	
	function loadHead(){
		
		grid = $("#maingrid").ligerGrid({
    		columns: [
    			{display: '材料编码', name: 'inv_code', align: 'left', width: '20%'},
    			{display: '材料名称', name: 'inv_name', align: 'left', width: '20%'},
    			{display: '规格型号', name: 'inv_model', align: 'center', width: '20%' },
    			{display: '单位', name: 'unit_name', align: 'center', width: '10%' }, 
    			{display: '单价', name: 'plan_price', align: 'right', width: '10%'},
    			{display: '状态', name: 'use_state', align: 'center', width: '20%',
    				render: function (rowdata, rowindex, value) {
						if(value == 1){
							return "在用";
						}else if(value == 0){
							return "停用";
						}else{
							return;
						}
					}	
    			}
    		],
    		dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatFacCertInv.do?isCheck=false',
    		width: '100%', height: '100%', checkbox: false, rownumbers:true,
    		enabledEdit: true, delayLoad : true,//初始化不加载，默认false
    		selectRowButtonOnly:true,//heightDiff: -10,
    		toolbar: { items: [
    			{ text: '关闭', id:'search', click: thisClose, icon:'close' }
    		]}
    	});
		gridManager = $("#maingrid").ligerGetGridManager();
	}
	
	function loadDict() {
		
		$("#fac_name").ligerTextBox({ width : 160, disabled:true });		
		$("#fac_name").val(fac_name);
	}
	
	function thisClose(){
 		frameElement.dialog.close();
	} 
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0" width="30%">
		<tr>
			<td align="right" class="l-table-edit-td" >生产厂商：</td>
            <td align="left" class="l-table-edit-td"><input name="fac_name" type="text" id="fac_name" ltype="text" validate="{required:true}" /></td>
		</tr>
	</table>
	<div id="maingrid"></div>
</body>
</html>
