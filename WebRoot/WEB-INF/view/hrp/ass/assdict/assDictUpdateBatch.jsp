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
		loadDict();
 
	});
	


	function save() {
	
		
		if(liger.get("is_measure").getValue() == ""){
			return;
		}
		

		var formPara = {
				paramVo : "${paramVo}",

			    is_measure : liger.get("is_measure").getValue(),
			    
			    measure_type : liger.get("measure_type").getValue(),
			    
			    is_s_measure : liger.get("is_s_measure").getValue(),
			    
				common_name : $("#common_name").val()

		};
		$.post("updateBatchDict.do?isCheck=false", formPara,
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
		//字典下拉框
		$('#is_measure').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			autocomplete: true,
			width : 180,
			value: 0,
		});
		$('#measure_type').ligerComboBox({
			data:[{id:0,text:'A类'},{id:1,text:'B类'},{id:2,text:'C类'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width : 200,
			value: 1,
		});
		 $('#is_s_measure').ligerComboBox({
				data:[{id:1,text:'是'},{id:0,text:'否'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 200,
				value: 0,
		})
		
		$("#common_name").ligerTextBox({
			width : 200
		});
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
					style="padding-left: 20px;">是否计量<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td">
				<input name="is_measure" type="text" id="is_measure"/>
				</td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">计量分类：</td>
				<td align="left" class="l-table-edit-td"><input
					name="measure_type" type="text" id="measure_type" /></td>
				<td align="left"></td>
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">常用名称：</td>
				<td align="left" class="l-table-edit-td"><input
					name="common_name" type="text" id="common_name" /></td>
				<td align="left"></td>		
				
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;" id="showis_s_measure1">是否强检：</td>
				<td align="left" class="l-table-edit-td"  id="showis_s_measure2"><input
					name="is_s_measure" type="text" id="is_s_measure" /></td>
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
