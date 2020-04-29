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
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<style>
	.l-dialog-table tbody:first-child{
		display: none;
	} 
	#listbox table{
	   margin-bottom: 80px;
	}
</style>
<script type="text/javascript">
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	$(function() {
		$("#navtab").ligerTab();
		$("#listbox").ligerListBox({
            isShowCheckBox: false, isMultiSelect: false,
            url:'queryHrTableColByCodes2.do?isCheck=false' ,
            urlParms: dialog.get('data'),
            valueField: 'col_code',
            textField: 'col_name',
            valueFieldID: 'field_val',height:240, width: 300
        }); 
	});
	
	function getValue(){
		return $("#field_val").val() ? $("#field_val").val() : $("#field_txt").val();
	}
</script>
</head>
<body style="padding: 0px;">
	<div id="navtab" style="width: 600px;overflow:hidden; border:1px solid #A3C0E8; ">
		<div tabid="home" title="列表" lselected="true" style="height: 300px">
			<div id="listbox" style="width:278px; margin-bottom:50px;"></div> 
		</div>
		<div title="编辑">
			<div id="maingrid2" style="margin: 10px; height: 300px;">
				<textarea rows="5" class="l-textarea" id="field_txt" style="width:265px" placeholder="手动输入列名需加上表名，例如：'to_char(hos_emp.emp_id)'"></textarea>
			</div>
			
		</div>
	</div>
</body>
</html>
