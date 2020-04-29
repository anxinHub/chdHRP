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
				in_date : $("#in_date").val(),
				in_dept_id :liger.get("in_dept_code").getValue().split(".")[0],
				in_dept_no :liger.get("in_dept_code").getValue().split(".")[1],
				out_date : $("#out_date").val(),
				out_dept_id : liger.get("out_dept_code").getValue().split(".")[0],
				out_dept_no : liger.get("out_dept_code").getValue().split(".")[1],
				patient_name : $("#patient_name").val(),
				patient_sex : liger.get("patient_sex").getValue(),
				patient_age : $("#patient_age").val(),
				birth_date : $("#birth_date").val(),
				identity_code : liger.get("identity_code").getValue(),
				in_days : $("#in_days").val(),
				director_name : $("#director_name").val(),
				major_name : $("#major_name").val(),
				in_hos_name : $("#in_hos_name").val(),
				outcome_code : liger.get("outcome_code").getValue()
		};
		ajaxJsonObjectByUrl("addHtcgMrInfo.do", formPara, function(responseData) {
			if (responseData.state == "true") {
			 $("input[name='mr_no']").val('');
			 $("input[name='in_hos_no']").val('');
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

	function saveMrInfo() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#in_dept_code","../../base/queryHtcgDeptDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#out_dept_code","../../base/queryHtcgDeptDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#identity_code","../../base/queryHtcgIdentityTypeDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#outcome_code","../../base/queryHtcgOutcomeDict.do?isCheck=false","id","text",true,true);
   	    $("#patient_sex").ligerComboBox({  
             data: [
                 { text: '男', id: '1' },
                 { text: '女', id: '2' },
             ]
         }); 
	}
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">病案号：</td>
				<td align="left" class="l-table-edit-td"><input name="mr_no" type="text" id="mr_no" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 200px;">住院号：</td>
				<td align="left" class="l-table-edit-td"><input name="in_hos_no" type="text" id="in_hos_no" ltype="text" validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">姓名：</td>
				<td align="left" class="l-table-edit-td"><input name="patient_name" type="text" id="patient_name" ltype="text"/></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">性别：</td>
				<td align="left" class="l-table-edit-td"><input name="patient_sex" type="text" id="patient_sex" ltype="text" /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td"nstyle="padding-left: 20px;">年龄：</td>
				<td align="left" class="l-table-edit-td"><input name="patient_age" type="text" id="patient_age" ltype="text" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">出生日期：</td>
				<td align="left" class="l-table-edit-td"><input class="Wdate"  name="birth_date" type="text" id="birth_date" ltype="text"
				 onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">医保类型：</td>
				<td align="left" class="l-table-edit-td"><input name="identity_code" type="text" id="identity_code" ltype="text" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">入院日期：</td>
				<td align="left" class="l-table-edit-td"><input name="in_date"  class="Wdate"  type="text" id="in_date" ltype="text"
				 onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">入院科室：</td>
				<td align="left" class="l-table-edit-td"><input name="in_dept_code" type="text" id="in_dept_code" ltype="text"  /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">出院日期：</td>
				<td align="left" class="l-table-edit-td"><input name="out_date" class="Wdate"  type="text" id="out_date" ltype="text"
				 onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">出院科室：</td>
				<td align="left" class="l-table-edit-td"><input name="out_dept_code" type="text" id="out_dept_code" ltype="text" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">住院天数：</td>
				<td align="left" class="l-table-edit-td"><input name="in_days" type="text" id="in_days" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">主任医师：</td>
				<td align="left" class="l-table-edit-td"><input name="director_name" type="text" id="director_name" ltype="text"  /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">主治医师：</td>
				<td align="left" class="l-table-edit-td"><input name="major_name" type="text" id="major_name" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">住院医师：</td>
				<td align="left" class="l-table-edit-td"><input name="in_hos_name" type="text" id="in_hos_name" ltype="text" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">转归名称：</td>
				<td align="left" class="l-table-edit-td"><input name="outcome_code" type="text" id="outcome_code" ltype="text"  /></td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
