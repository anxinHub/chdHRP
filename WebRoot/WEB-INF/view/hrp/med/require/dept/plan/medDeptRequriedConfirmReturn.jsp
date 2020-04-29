<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var grid;
	var gridManager = null;
	var userUpdateStr;
	$(function() {
		
		loadDict()//加载下拉框
		//加载数据
		loadHead(null);
		
	});
	//查询
	function query() {
		grid.options.parms = [];
		grid.options.newPage = 1;
		
		//加载查询条件
		grid.loadData(grid.where);
		
	}
	
	function loadHead() {
		
	}
	
	
	function loadDict() {
	
	}
	
	function saveMedDepartRequriedConfirmReturn(){
		
		ajaxJsonObjectByUrl("returnMedDeptRequriedConfirms.do?isCheck=true&reqIds="+'${reqIds}'+'&return_reason='+$("#return_reason").val(),function(responseData) {
			if (responseData.state == "true") {
				parent.query();
			}
		});
		
	}

</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr></tr>
		
		<tr>
			<td align="right" class="l-table-edit-td"  style="padding-left:10px;">退回原因：</td>
	        <td align="left" class="l-table-edit-td"  >
	            <textarea name="return_reason" type="text" requried="true" style="width:300px;height:150px;" id="return_reason"></textarea>
	        </td>
	       <td align="left"></td>     
	        
		</tr>
		
	</table>

	<div id="maingrid"></div>
	
</body>
</html>
