<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	var dataFormat;
	$(function() {
		loadDict()//加载下拉框
		loadForm();
		YearMonth();
	});

	function save() {
		var formPara = {

			acct_year : $("#acct_year").val(),

			acct_month : $("#acct_month").val(),

			dept_id : liger.get("dept_id").getValue().split(",")[0],
			dept_no: liger.get("dept_id").getValue().split(",")[1],
			work_item_code : liger.get("work_item_code").getValue(),

			work_amount : $("#work_amount").val()

		};

		ajaxJsonObjectByUrl("addHpmWorkitemData.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				$("input[name='acct_year']").val('');
				$("input[name='acct_month']").val('');
				$("input[name='dept_id']").val('');
				$("input[name='work_item_code']").val('');
				$("input[name='work_amount']").val('');
				$("input[name='work_money']").val('');
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

	function saveWorkitemData() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		var para = {
    			acct_yearm:parent.$("#acct_yearm").val()
            }
		//字典下拉框
		autocomplete("#work_item_code", "../../hpm/queryHpmWorkitemSeqTime.do?isCheck=false", "id", "text", true, true,para);
		autocomplete("#dept_id", "../../hpm/queryDeptDictTime.do?isCheck=false", "id", "text", true, true,para);
	}
	
	function YearMonth(){
		var acct_yearm = parent.$("#acct_yearm").val();
		var acct_year = acct_yearm.substring(0,4);
		var acct_month = acct_yearm.substring(4);
		$("#acct_year").val(acct_year);
		$("#acct_month").val(acct_month);
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年度：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_year" type="text" class="Wdate" id="acct_year" ltype="text"
					validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算月份：</td>
				<td align="left" class="l-table-edit-td"><input name="acct_month" type="text" class="Wdate" id="acct_month" ltype="text"
					validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">工作量指标：</td>
				<td align="left" class="l-table-edit-td"><input name="work_item_code" type="text" id="work_item_code" ltype="text"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">计件工作量：</td>
				<td align="left" class="l-table-edit-td"><input name="work_amount" type="text" id="work_amount" ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<!-- tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计件奖金：</td>
                <td align="left" class="l-table-edit-td"><input name="work_money" type="text" id="work_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr-->

		</table>
	</form>

</body>
</html>
