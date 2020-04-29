<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
	
	$(function() {
		loadDict();
	});

	function loadDict() {
		autocomplete("#acc_year", "../../../queryAccYear.do?isCheck=false", "id", "text", true, true);

		$("#dircet_target").ligerTextBox({width: 160, number: true});
		$("#grant_target").ligerTextBox({width: 160, number: true});
		
		//格式化按钮
		$("#save").ligerButton({click: save, width:90});
		$("#close").ligerButton({click: this_close, width:90});
	}
	
	function save(){
		if(!liger.get("acc_year").getValue()){
			$.ligerDialog.warn('会计年度不能为空！');
			return false;
		}
		if(!$("#dircet_target").val()){
			$.ligerDialog.warn('直接支付预算下达数不能为空！');
			return false;
		}
		if(!$("#grant_target").val()){
			$.ligerDialog.warn('授权支付预算下达数不能为空！');
			return false;
		}
		
		var paras = {
				acc_year: liger.get("acc_year").getValue(), 
				dircet_target: $("#dircet_target").val(), 
				grant_target: $("#grant_target").val() 
		}
		
		ajaxJsonObjectByUrl("saveAccBudgTarget.do?isCheck=true", paras, function (responseData){
    		if(responseData.state=="true"){
    			$("#dircet_target").val("");
    			$("#grant_target").val("");
    			parent.query();
    		}
    	});
	}
	
	function this_close(){
		frameElement.dialog.close();
	}
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div class="l-clear"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">会计年度：</td>
			<td align="left" class="l-table-edit-td">
				<input name="acc_year" type="text" id="acc_year" ltype="text" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">直接支付预算下达数：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dircet_target" type="text" id="dircet_target" ltype="text" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">授权支付预算下达数：</td>
			<td align="left" class="l-table-edit-td">
				<input name="grant_target" type="text" id="grant_target" ltype="text" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="center" class="l-table-edit-td" colspan="2">
				&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="center" class="l-table-edit-td" colspan="2">
				<input  type="button" id="save" accessKey="S"  value="保存(S)" />
				&nbsp;&nbsp;
				<input type="button" id="close" accessKey="C"  value="关闭(C)"/>
			</td>
		</tr>
	</table>
</body>
</html>
