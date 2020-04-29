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

	var wageItemCodeBox;
	
	$(function() {

		loadDict();//加载下拉框

		loadForm();

	});

	function save() {

		if ($("#year_month").val() == "") {

			$.ligerDialog.error("统计年月不能为空.");

			return false;
		}

		if (liger.get("emp_kind_code").getValue() == "") {

			$.ligerDialog.error("职工分类必选.");

			return false;
		}

		if (liger.get("cost_item_id").getValue() == "") {

			$.ligerDialog.error("成本项目必选.");

			return false;
		}

		var emp_kind_code = liger.get("emp_kind_code").getValue()

		var wage_item_code = liger.get("wage_item_code").getValue()

		var cost_item_id = liger.get("cost_item_id").getValue()

		var formPara = {
			id : '',
			acc_year : $("#year_month").val().substring(0, 4),

			acc_month : $("#year_month").val().substring(4, 6),

			emp_kind_code : emp_kind_code,

			wage_item_code : wage_item_code,

			cost_item_id : cost_item_id.split(".")[0],

			cost_item_no : cost_item_id.split(".")[1]

		};

		ajaxJsonObjectByUrl("addCostWageCostRela.do", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				$("input[name='year_month']").val('');
				$("input[name='emp_kind_code']").val('');
				$("input[name='wage_item_code']").val('');
				$("input[name='cost_item_id']").val('');
				$("input[name='cost_item_no']").val('');
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

	function saveCostWageCostRela() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		//autocomplete("#cost_item_id","../queryItemDictNo.do?isCheck=false","id","text",true,true); 
		//autocomplete("#wage_item_code","../queryWageItemArrt.do?isCheck=false","id","text",true,true);
		//autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);

		wageItemCodeBox = $("#wage_item_code").ligerListBox({
			url : '../queryWageItemArrt.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : true,
			height : 325,
			width : 240
		});

		$("#emp_kind_code").ligerListBox({
			url : '../queryEmpTypeArrt.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : false,
			height : 325,
			width : 240,
			onSelected : function(selectValue) {
               
				wageItemCodeBox.clearContent();

				wageItemCodeBox= $("#wage_item_code").ligerListBox({
						parms : {
							emp_kind_code : selectValue
						},
						url : '../queryWageItemArrt.do?isCheck=false',
						valueField : 'id',
						textField : 'text',
						isShowCheckBox : true,
						isMultiSelect : true,
						height : 325,
						width : 240
					});
			}

		});

		$("#cost_item_id").ligerListBox({
			url : '../queryItemDictNoLast.do?isCheck=false',
			valueField : 'id',
			textField : 'text',
			isShowCheckBox : true,
			isMultiSelect : false,
			height : 325,
			width : 240
		});
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">


			<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">统计年月：</td>
				<td align="left" class="l-table-edit-td"><input
					name="year_month" type="text" id="year_month" ltype="text"
					class="Wdate"
					onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"
					validate="{required:true,maxlength:20}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td">职工分类：</td>
				<td align="left" class="l-table-edit-td">工资项(不选择为全部)：</td>
				<td align="left" class="l-table-edit-td">成本项目：</td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td"><div
						id="emp_kind_code"></div></td>
				<td align="left" class="l-table-edit-td"><div
						id="wage_item_code"></div></td>
				<td align="left" class="l-table-edit-td">
				<div id="cost_item_id"></div>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>
