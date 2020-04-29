<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">

	var parentData = frameElement.dialog.options.data;
	
	$(function(){
		$("#deduct_code").ligerTextBox({width:180});
		$("#deduct_name").ligerTextBox({width:180});
		$("#high_point").ligerTextBox({width:180, number: true}); 
		$("#sort_code").ligerTextBox({width:180, disabled: true, digits: true});
		
		var matEvaTargetDeduct = parentData.matEvaTargetDeduct;
		$("#target_text").text(parentData.target_code + " " + parentData.target_name);
		if(matEvaTargetDeduct){
			$('#target_code').val(matEvaTargetDeduct.target_code);
			$('#deduct_code').val(matEvaTargetDeduct.deduct_code);
			liger.get("deduct_code").setDisabled();
			$('#deduct_name').val(matEvaTargetDeduct.deduct_name);
			$('#deduct_desc').val(matEvaTargetDeduct.deduct_desc);
			$('#high_point').val(matEvaTargetDeduct.high_point);
			$('#sort_code').val(matEvaTargetDeduct.sort_code);
			liger.get("sort_code").setEnabled();
			if(matEvaTargetDeduct.is_stop == "1"){
				$('#is_stop1').prop("checked", "checked");
			}
			$("#saveDeduct1").hide();
		}else{
			$('#target_code').val(parentData.target_code);
		}
	})
	
	function saveDeduct(flag){
		if(!$("#deduct_code").val()) {
			$.ligerDialog.warn("扣分项代码不能为空！");
			return false;
		}
		if(!$("#deduct_name").val()) {
			$.ligerDialog.warn("扣分项名称不能为空！");
			return false;
		} 

		var para = {
			target_code: $("#target_code").val(),
			deduct_code: $("#deduct_code").val(),
			deduct_name: $("#deduct_name").val(),
			deduct_desc: $("#deduct_desc").val(),
			high_point: $("#high_point").val(),
			sort_code: $("#sort_code").val(),
			is_stop: $('.is_stop:checked').val(), 
			is_update: parentData.is_update
		};
		
		ajaxJsonObjectByUrl("saveMatEvaTargetDeduct.do?isCheck=false", para, function (responseData) {
			if (responseData.state == "true") {
				// 刷新父页面的表格
				parentFrameUse().query_deduct();
				if(parentData.is_update == 0){
					if(flag == 1){
						//清空表单以便再次添加
						$('#deduct_code').val("");
						$('#deduct_name').val("");
						$('#deduct_desc').val("");
						$('#high_point').val("");
					}else{
						is_update = 1;
						liger.get("deduct_code").setDisabled();
						$('#sort_code').val(responseData.sort_code);
						liger.get("sort_code").setEnabled();
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
	<h3 style="margin-left: 20px;">评价指标：<span id="target_text"></span></h3>
	<table class="table-layout" style="margin-top:20px;border-bottom: 1px solid #f1f1f1" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>扣分项代码：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="hidden" id="target_code" />
				<input type="text" id="deduct_code" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>扣分项名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="deduct_name" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				扣分项描述：
			</td> 
			<td align="left" class="l-table-edit-td">
				<textarea id="deduct_desc" rows="3" style="width: 180px;"></textarea>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				上限值：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="high_point" />
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
				<span style="color: red">*</span>是否停用：
			</td>
			<td align="left" class="l-table-edit-td">
				<input type="radio" name="is_stop" class="is_stop" id="is_stop1" value="1" />是 
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="is_stop" class="is_stop" id="is_stop0" value="0" checked/>否
			</td>
		</tr>
	</table>
	<div align="center" style="margin-top: 60px;">
		<button class="l-button l-button-test" id="saveDeduct1" onclick="saveDeduct(1);" style="width: 100px;">保存&继续</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="l-button l-button-test" onclick="saveDeduct(0);">保存</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button class="l-button l-button-test" onclick="thisClose();">关闭</button>
	</div>
</body>
</html>