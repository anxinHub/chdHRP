<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	var dataFormat;
	var grid;
	var gridManager;
	$(function() {
		$("#simple_name").ligerTextBox({
			width : 180
		});
		$("#common_name").ligerTextBox({
			width : 180
		});
		$("#man_code").ligerTextBox({
			width : 180
		});
		$("#contract_code").ligerTextBox({
			width : 180
		});
		$("#file_number").ligerTextBox({
			width : 180
		});
		$("#ass_seq_no").ligerTextBox({
			width : 180
		});
		$("#service_date").ligerTextBox({
			width : 180
		});
		loadDict();
		
	});
	


	function save() {
	
		

		var formPara = {
				paramVo : "${paramVo}",

				simple_name : $("#simple_name").val(),
				
				man_code : $("#man_code").val(),
				
				contract_code : $("#contract_code").val(),
				
				file_number : $("#file_number").val(),
				
				ass_seq_no : $("#ass_seq_no").val(),
				
				
				service_date : $("#service_date").val(),
				
				ven_id : liger.get("ven_id").getValue().split("@")[0],
				ven_no : liger.get("ven_id").getValue().split("@")[1],
				
			    
				fac_id : liger.get("fac_id").getValue().split("@")[0],
				fac_no : liger.get("fac_id").getValue().split("@")[1],
				
				accept_emp : liger.get("accept_emp").getValue(),
				
				reg_no : liger.get("reg_no").getValue(),
				
				ass_nature : '${ass_nature}',
				
				common_name : $("#common_name").val()

		};
		$.post("updateBatchCard.do?isCheck=false", formPara,
				function(responseData) {
					 if (responseData.state == "true") {
						parent.$.ligerDialog.success("保存成功");
						parentFrameUse().query();
					}else{
						parent.$.ligerDialog.warn(responseData.msg);
					} 
				},"json");
	}


	function loadDict() {
		
		
		autocomplete("#ven_id", "../queryHosSupDictNo.do?isCheck=false", "id",
				"text", true, true, null, false, null, "180");
		
		autocomplete("#fac_id", "../queryHosFacDictNo.do?isCheck=false", "id",
				"text", true, true, null, false, null, "180");
		
		autocomplete("#accept_emp", "../queryMatStockEmp.do?isCheck=false", "id",
				"text", true, true, null, false, null, "180");
		
		autocomplete("#reg_no", "../queryCertNo.do?isCheck=false", "id",
				"text", true, true, null, false, null, "180");
		
		
	}
	function this_close() {
		frameElement.dialog.close();
	}
</script>

</head>

<body style="overflow-y: hidden;overflow-x: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1" style="display:blok">
		<table cellpadding="0" cellspacing="0" class="l-table-edit"
			width="100%">
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">简称：
				</td>
				<td align="left" class="l-table-edit-td">
				<input name="simple_name" type="text" id="simple_name"/>
				</td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">出厂编号：</td>
				<td align="left" class="l-table-edit-td"><input
					name="man_code" type="text" id="man_code" /></td>
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" >合同号：</td>
				<td align="left" class="l-table-edit-td" ><input
					name="contract_code" type="text" id="contract_code" /></td>
					
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" >档案号：</td>
				<td align="left" class="l-table-edit-td" ><input
					name="file_number" type="text" id="file_number" /></td>
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" >常用名称：</td>
				<td align="left" class="l-table-edit-td"  ><input
					name="common_name" type="text" id="common_name" /></td>		
				
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" >序列号：</td>
				<td align="left" class="l-table-edit-td" ><input
					name="ass_seq_no" type="text" id="ass_seq_no" /></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">供应商：</td>
				<td align="left" class="l-table-edit-td"><input name="ven_id"
					type="text" id="ven_id" /></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">生产厂商：</td>
				<td align="left" class="l-table-edit-td"><input name="fac_id"
					type="text" id="fac_id" /></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">验收人：</td>
				<td align="left" class="l-table-edit-td"><input name="accept_emp"
					type="text" id="accept_emp" /></td>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">保修截止日期：</td>
            	<td align="left" class="l-table-edit-td"><input name="service_date" type="text" id="service_date" ltype="text" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  /></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">注册证号：</td>
				<td align="left" class="l-table-edit-td"><input name="reg_no"
					type="text" id="reg_no" /></td>
			</tr>
			
		</table>
		<table align="center" cellpadding="0" cellspacing="0"
			class="l-table-edit">
			<tr align="center">
				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="保存" onclick="save();" /></td>

				<td align="center" class="l-table-edit-td"
					style="padding-left: 20px;"><input
					class="l-button l-button-test" style="float: right;" type="button"
					value="关闭" onclick="this_close();" /></td>
			</tr>
		</table>
	</form>
	
</body>
</html>
