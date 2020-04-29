<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var parentData = frameElement.dialog.options.data;
	
	$(function() {
		autocompleteObj({
			id: "#cash_item_code", 
			urlStr: "../../queryCashItemSelect.do?isCheck=false", 
			valueField: "id", 
			textField: "text", 
			autocomplete: true, 
			highLight: true, 
			parmsStr: {cash_dire: parentData.cash_dire}, 
			initWidth: 350, 
			initHeight: 200, 
			boxwidth: 350
		})
	});
	
	function mySave(){
		var cash_item_id = liger.get("cash_item_code").getValue();
		if(!cash_item_id){
			$.ligerDialog.error("请选择现金流量项目！");
			return;
		}
		
		parentData.cash_item_id = cash_item_id;
		
		ajaxJsonObjectByUrl("saveAccVouchCashFlowBatch.do?isCheck=false", parentData, function(responseData) {
			if (responseData.state == "true") {
				parent.query();
			}
		});
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">现金流量项目：</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cash_item_code" />
			</td>
		</tr>
	</table>
</body>
</html>
