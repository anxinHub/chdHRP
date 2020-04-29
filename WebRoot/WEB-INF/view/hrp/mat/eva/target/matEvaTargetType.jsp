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
		ajaxJsonObjectByUrl("queryMatEvaTargetTypeRules.do?isCheck=false", {}, function(responseData) {
			$("#rules").text(responseData.rules);
        }, false);
		$("#super_name").ligerTextBox({width:180, disabled: true});
		$("#sort_code").ligerTextBox({width:180, disabled: true, digits: true});
		$("#target_type_code").ligerTextBox({width:180 }); 
		$("#target_type_name").ligerTextBox({width:180 });
		
		var matEvaTargetType = parentData.matEvaTargetType;
		if(is_update == 0){
			$('#super_code').val(matEvaTargetType.target_type_code);
			$('#super_level').val(matEvaTargetType.type_level);
			$('#super_name').val(matEvaTargetType.target_type_code + " " + matEvaTargetType.target_type_name);
		}else{
			$('#super_code').val(matEvaTargetType.super_code);
			$('#super_level').val(matEvaTargetType.super_level);
			$('#super_name').val(matEvaTargetType.super_name);
			$('#target_type_code').val(matEvaTargetType.target_type_code);
			liger.get("target_type_code").setDisabled();
			$('#target_type_name').val(matEvaTargetType.target_type_name);
			$('#sort_code').val(matEvaTargetType.sort_code);
			liger.get("sort_code").setEnabled();
			if(matEvaTargetType.is_stop == "1"){
				$('#is_stop1').prop("checked", "checked");
			}
		}
	})
	
	function saveType(falg){
		var super_code = $('#super_code').val();
		var target_type_code = $('#target_type_code').val();
		var target_type_name = $('#target_type_name').val();
		var is_stop = $('.is_stop:checked').val();
 
		if (target_type_code == "") {
			$.ligerDialog.warn("指标分类编码不能为空！");
			return false;
		}
		if (target_type_name == "") {
			$.ligerDialog.warn("指标分类名称不能为空！");
			return false;
		}

		var para = {
			super_code: $("#super_code").val(),
			super_level: $("#super_level").val(),
			rules: $("#rules").text(),
			target_type_code: target_type_code, 
			target_type_name: target_type_name, 
			sort_code: $("#sort_code").val(),
			is_stop: is_stop, 
			is_update: is_update
		};
		
		ajaxJsonObjectByUrl("saveMatEvaTargetType.do?isCheck=false", para, function (responseData) {
			if (responseData.state == "true") {
				if(is_update == 0){
					if(falg == 0){
						$('#sort_code').val(responseData.sort_code);
						liger.get("sort_code").setEnabled();
					}else{
						$("#target_type_code").val("");
						$("#target_type_name").val("");
					}
				}
				// 刷新父页面树形结构
				parent.tree.reload(); 
				//关闭页面
				//thisClose();
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
	<div style="padding-left: 20px;"><span style="color: red">编码规则：</span><span id="rules" style="color: red"></span></div>
	<table class="table-layout" style="margin:10px;border-bottom: 1px solid #f1f1f1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				指标分类上级：</td> 
			<td align="left" class="l-table-edit-td">
				<input type="hidden" id="super_code" />
				<input type="hidden" id="super_level" />
				<input type="text" id="super_name" disabled="disabled"/>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				指标分类编码：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="target_type_code" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				指标分类名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="target_type_name" />
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
		<tr> 
			<td align="right" class="l-table-edit-td">
				是否停用：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="radio" name="is_stop" class="is_stop" id="is_stop1" value="1" />是 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="is_stop" class="is_stop" id="is_stop0" value="0" checked/>否
			</td>
		</tr>
	</table>
	<div align="center" style="margin-top: 20px;">
		<button class="l-button l-button-test" onclick="saveType(1);">保存</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="l-button l-button-test" onclick="thisClose();">关闭</button>
	</div>
</body>
</html>