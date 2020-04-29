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

	var para = {
		group_id : '${group_id}',
		hos_id   : '${hos_id}',
		copy_code: '${copy_code}',
		acc_year : '${acc_year}',
		plan_code: '${plan_code}'
	};
	$(function() {
		 listbox_ok();
		 listbox_no();
	});
	
	function listbox_ok(){
		$("#listbox_ok").ligerListBox({
			parms : para,
			url : 'queryHtcRelativePlanDeptByPlanSetOk.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 340,
			width : 400,
			valueFieldID : 'dept_ok'
		 });
		}
	function listbox_no(){
		$("#listbox_no").ligerListBox({
			parms : para,
			url : 'queryHtcRelativePlanDeptByPlanSetNO.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 340,
			width : 400,
			valueFieldID : 'dept_no'
		});
	}
	function moveToLeft() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		var selecteds = box2.getSelectedItems();
		if (!selecteds || !selecteds.length)
			return;
		box2.removeItems(selecteds);
		box1.addItems(selecteds);

	}
	function moveToRight() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		var selecteds = box1.getSelectedItems();
		if (!selecteds || !selecteds.length)
			return;
		box1.removeItems(selecteds);
		box2.addItems(selecteds);
	}
	function moveAllToLeft() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		var selecteds = box2.data;
		if (!selecteds || !selecteds.length)
			return;
		box1.addItems(selecteds);
		box2.removeItems(selecteds);
	}
	function moveAllToRight() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		var selecteds = box1.data;
		if (!selecteds || !selecteds.length)
			return;
		box2.addItems(selecteds);
		box1.removeItems(selecteds);

	}
	
	function saveDeptSet() {
		var box1 = liger.get("listbox_ok"), box2 = liger.get("listbox_no");
		box1.selectAll();//全部选中
		var formPara = {
			group_id : '${group_id}',
			hos_id   : '${hos_id}',
			copy_code: '${copy_code}',
			acc_year : '${acc_year}',
			plan_code: '${plan_code}',
			proj_dept_id : liger.get("listbox_ok").getValue()
		};
		ajaxJsonObjectByUrl("addHtcRelativePlanDept.do?isCheck=false",
				formPara, function(responseData) {
					if (responseData.state == "true") {
						listbox_ok();
						listbox_no();
				  }
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
			<td align="left" class="l-table-edit-td" style="padding-left: 20px;">已存在的核算科室</td>
			<td align="left"></td>
			<td align="left">不存在的核算科室</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
			<!--左侧div-->
				<div style="margin: 4px; float: left;">
					<div id="listbox_ok"></div>
				</div>
			</td>
			<td align="left">
				<div>
					<input type="button" onclick="moveToLeft()"  value="左移" /> <br /><br />
					<input type="button" onclick="moveToRight()" value="右移" /><br /><br />
					<input type="button" onclick="moveAllToLeft()" value="全部左移" /><br /><br />
					<input type="button" onclick="moveAllToRight()" value="全部右移" />
				</div>
			</td>
			<td align="left">
				<div style="margin: 4px; float: left;">
					<div id="listbox_no"></div>
				</div>
			</td>
		</tr>
	</table>
</body>
</html>