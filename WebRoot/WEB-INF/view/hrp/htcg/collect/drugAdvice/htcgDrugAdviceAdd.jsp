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
	var dataFormat;
	$(function() {
		loadDict()//加载下拉框
		loadForm();
	});

	function save() {
		var formPara = {
				mr_no : $("#mr_no").val(),
				in_hos_no : $("#in_hos_no").val(),
				advice_date : $("#advice_date").val(),
				order_by_id : liger.get("order_by_code").getValue().split(".")[0],
				order_by_no : liger.get("order_by_code").getValue().split(".")[1],
				perform_by_id : liger.get("perform_by_code").getValue().split(".")[0],
				perform_by_no : liger.get("perform_by_code").getValue().split(".")[1],
				drug_code : liger.get("drug_code").getValue(),
				amount : $("#amount").val(),
				price : $("#price").val(),
				recipe_type_code : liger.get("recipe_type_code").getValue(),
				place : $("#place").val()
		};

		ajaxJsonObjectByUrl("addHtcgDrugAdvice.do", formPara, function(
				responseData) {
			if (responseData.state == "true") {
				$("input[name='mr_no']").val('');
				$("input[name='in_hos_no']").val('');
				$("input[name='advice_date']").val('');
				$("input[name='order_by_code']").val('');
				$("input[name='perform_by_code']").val('');
				$("input[name='drug_code']").val('');
				$("input[name='amount']").val('');
				$("input[name='price']").val('');
				$("input[name='income_money']").val('');
				$("input[name='recipe_type_code']").val('');
				$("input[name='place']").val('');
				parent.query();
			}
		});
	}

	function loadForm() {

		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}

	function saveDrugAdvice() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#order_by_code", "../../base/queryHtcgDeptDict.do?isCheck=false", "id", "text", true, true);
		autocomplete("#perform_by_code","../../base/queryHtcgDeptDict.do?isCheck=false", "id", "text",true, true);
		autocomplete("#drug_code","../../base/queryHtcgDrugDict.do?isCheck=false", "id", "text", true,true);
		autocomplete("#recipe_type_code","../../base/queryHtcgRecipeTypeDict.do?isCheck=false", "id", "text",true, true);
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">病案号：</td>
				<td align="left" class="l-table-edit-td"><input name="mr_no" type="text" id="mr_no" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">住院号：</td>
				<td align="left" class="l-table-edit-td"><input name="in_hos_no" type="text" id="in_hos_no" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">医嘱时间：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate" name="advice_date" type="text" id="advice_date" ltype="text"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"
					validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">开单科室：</td>
				<td align="left" class="l-table-edit-td"><input name="order_by_code" type="text" id="order_by_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">执行科室：</td>
				<td align="left" class="l-table-edit-td"><input name="perform_by_code" type="text" id="perform_by_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">药品：</td>
				<td align="left" class="l-table-edit-td"><input name="drug_code" type="text" id="drug_code" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">数量：</td>
				<td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">单价：</td>
				<td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">医嘱时效：</td>
				<td align="left" class="l-table-edit-td"><input name="recipe_type_code" type="text" id="recipe_type_code" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" style="padding-left: 20px;">病人地点：</td>
				<td align="left" class="l-table-edit-td"><input name="place" type="text" id="place" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
