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
			acc_year : $("#year_month").val().substring(0,4),
			acc_month : $("#year_month").val().substring(4,6),
			dept_id:liger.get("dept_id").getValue().split(".")[0],
			dept_no:liger.get("dept_id").getValue().split(".")[1],
			cost_item_id:liger.get("cost_item_id").getValue().split(".")[0],
			cost_item_no:liger.get("cost_item_id").getValue().split(".")[1],
			source_id:liger.get("source_id").getValue(),
			tot_amount : $("#tot_amount").val(),
			prime_amount : $("#prime_amount").val(),
			pub_amount : $("#pub_amount").val(),
			man_amount : $("#man_amount").val(),
			ass_amount : $("#ass_amount").val()

		};

		ajaxJsonObjectByUrl("addHtcIncomeDeptCost.do", formPara, function(responseData) {
			if (responseData.state == "true") {
				$("input[name='tot_amount']").val('');
				$("input[name='prime_amount']").val('');
				$("input[name='pub_amount']").val('');
				$("input[name='man_amount']").val('');
				$("input[name='ass_amount']").val('');
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

	function saveDeptCost() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		//科室成本科室字典
		autocomplete("#dept_id","../../../info/base/queryHtcDeptDict.do?isCheck=false","id","text",true,true);
		autocomplete("#cost_item_id","../../../info/base/queryHtcCostItemDict.do?isCheck=false","id","text",true,true);
		autocomplete("#source_id","../../../info/base/queryHtcSourceDict.do?isCheck=false","id","text",true,true);
		autodate("#year_month","YYYYmm");
		 
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">年月：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate"
					name="year_month" type="text" id="year_month" ltype="text"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">成本项目：</td>
				<td align="left" class="l-table-edit-td"><input name="cost_item_id" type="text" id="cost_item_id" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">资金来源：</td>
				<td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">总成本：</td>
				<td align="left" class="l-table-edit-td"><input name="tot_amount" type="text" id="tot_amount" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">直接成本：</td>
				<td align="left" class="l-table-edit-td"><input name="prime_amount" type="text" id="prime_amount" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">公用分摊成本：</td>
				<td align="left" class="l-table-edit-td"><input name="pub_amount" type="text" id="pub_amount" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">管理分摊成本：</td>
				<td align="left" class="l-table-edit-td"><input name="man_amount" type="text" id="man_amount" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">医辅分摊成本：</td>
				<td align="left" class="l-table-edit-td"><input name="ass_amount" type="text" id="ass_amount" ltype="text"  /></td>
				<td align="left"></td>
			</tr>

		</table>
	</form>

</body>
</html>
