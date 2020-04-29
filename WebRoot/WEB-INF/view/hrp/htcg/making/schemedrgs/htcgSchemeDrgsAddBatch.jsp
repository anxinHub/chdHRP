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
<script type="text/javascript">
	$(function() {
		 loadDict()//加载下拉框
	});
	function saveSchemeDrgs() {
		if(liger.get("scheme_code").getValue() == ""){
			$.ligerDialog.error('请选择核算方案');
			return;
		}
		if($("#listbox_no").val() == ""){
			$.ligerDialog.error('请选择病种');
			return;
		}
	
		var formPara = {
			scheme_code : liger.get("scheme_code").getValue(),
			drgs_codes : $("#listbox_no").val()
		}; 
		ajaxJsonObjectByUrl("addBatchHtcgSchemeDrgs.do?isCheck=false",
				formPara, function(responseData) {
					if (responseData.state == "true") {
						$("input[name='scheme_code']").val(''); 
						liger.get("listbox_no").refresh();
						parent.query();
					}
		});
	}
	 
	function  loadDict(){
		 autocomplete("#scheme_code","../../base/queryHtcgSchemeDict.do?isCheck=false","id","text",true,true);
		$("#listbox_no").ligerListBox({
			url : '../../base/queryHtcgDrgsDict.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 340,
			width : 450,
			valueFieldID : 'id'
		});
	}
</script>
<style type="text/css">
.middle input {
	display: block;
	width: 30px;
	margin: 2px;
}
</style>
</head>
<body style="padding: 10px">
	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算方案：</td>
			<td align="left"><input name="scheme_code" type="text" id="scheme_code" ltype="text"  /></td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">病种：</td>
            <td align="left"><div style="margin:4px;float:left;"><div id="listbox_no"></div></div></td>
        </tr> 
    </table>
</body>
</html>