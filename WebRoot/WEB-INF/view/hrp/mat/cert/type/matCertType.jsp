<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	var is_update = parentData.is_update;
	
	$(function(){
		$("#cert_type_code").ligerTextBox({width:160});
		$("#cert_type_name").ligerTextBox({width:160});
		var paras = {
			field_code : "cert_kind_code"
		}
		autocomplete("#cert_kind_code", "../../queryMatSysList.do?isCheck=false", "id", "text", true, true, paras);
		autoCompleteByData("#is_cert_busi", [{id: 0, text: "否"}, {id: 1, text: "是"}], "id", "text", true, true, "", true);
		autoCompleteByData("#is_cert_name", [{id: 0, text: "否"}, {id: 1, text: "是"}], "id", "text", true, true, "", true);
		autoCompleteByData("#is_stop", [{id: 0, text: "否"}, {id: 1, text: "是"}], "id", "text", true, true, "", true);
		$("#sort_code").ligerTextBox({width:160, disabled: true, digits: true});
		
		var matCertType = parentData.matCertType;
		if(matCertType){
			$('#cert_type_code').val(matCertType.cert_type_code);
			liger.get("cert_type_code").setDisabled();
			$('#cert_type_name').val(matCertType.cert_type_name);
			liger.get("cert_kind_code").setValue(matCertType.cert_kind_code);
			liger.get("cert_kind_code").setText(matCertType.cert_kind_code + "：" + matCertType.cert_kind_name);
			$('#sort_code').val(matCertType.sort_code);
			liger.get("sort_code").setEnabled();
			liger.get("is_cert_busi").setValue(matCertType.is_cert_busi);
			liger.get("is_cert_name").setValue(matCertType.is_cert_name);
			liger.get("is_stop").setValue(matCertType.is_stop);
			$("#saveScale1").hide();
		}
	})
	
	function saveScale(flag){
		if(!$("#cert_type_code").val()) {
			$.ligerDialog.warn("证件类型编码不能为空！");
			return false;
		}
		if(!$("#cert_type_name").val()) {
			$.ligerDialog.warn("证件类型名称不能为空！");
			return false;
		} 
		var cert_kind_code = liger.get("cert_kind_code").getValue();
		var is_cert_busi = liger.get("is_cert_busi").getValue();
		var is_cert_name = liger.get("is_cert_name").getValue();
		var is_stop = liger.get("is_stop").getValue();

		if(!cert_kind_code) {
			$.ligerDialog.warn("证件类别不能为空！");
			return false;
		} 
		if(!is_cert_busi) {
			$.ligerDialog.warn("是否需维护经营范围不能为空！");
			return false;
		} 
		if(!is_cert_name) {
			$.ligerDialog.warn("是否需维护证件名称不能为空！");
			return false;
		} 
		if(!is_stop) {
			$.ligerDialog.warn("是否停用不能为空！");
			return false;
		} 

		var para = {
			cert_type_code: $("#cert_type_code").val(),
			cert_type_name: $("#cert_type_name").val(),
			cert_kind_code: cert_kind_code, 
			is_cert_busi: is_cert_busi, 
			is_cert_name: is_cert_name, 
			sort_code: $("#sort_code").val(),
			is_stop: is_stop, 
			is_update: is_update
		};
		
		ajaxJsonObjectByUrl("saveMatCertType.do?isCheck=false", para, function (responseData) {
			if (responseData.state == "true") {
				// 刷新父页面的表格
				parent.query(); 
				if(parentData.is_update == 0){
					if(flag == 1){
						//清空表单以便再次添加
						$('#cert_type_code').val("");
						$('#cert_type_name').val("");
					}else{
						liger.get("cert_type_code").setDisabled();
						$('#sort_code').val(parentData.sort_code);
						liger.get("sort_code").setEnabled();
						$("#saveScale1").hide();
						is_update = 1;
					}
				}
			}
		}, false);
	}
	
	function thisClose(){
 		frameElement.dialog.close();
	} 
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<table class="table-layout" style="margin:10px;border-bottom: 1px solid #f1f1f1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>证件类型编码：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_type_code" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>证件类型名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_type_name" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>证件类别：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="cert_kind_code" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>是否需维护经营范围：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="is_cert_busi" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>是否需维护证件名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="is_cert_name" />
			</td>
		</tr>
		<tr> 
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>是否停用：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="text" id="is_stop" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				排序号：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="sort_code" />
			</td>
		</tr>
	</table>
	<div align="center" style="margin-top: 20px;">
		<button class="l-button l-button-test" onclick="saveScale(0);">保存</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="l-button l-button-test" id="saveScale1" onclick="saveScale(1);" style="width: 100px;">保存&继续</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="l-button l-button-test" onclick="thisClose();">关闭</button>
	</div>
</body>
</html>