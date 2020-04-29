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
		loadDict();
		loadForm();
	});

	function save() {
		var formPara = {
				acc_year:$("#acc_year").val(),
        		plan_code:liger.get("plan_code").getValue(),
        		proj_dept_no:liger.get("proj_dept_code").getValue().split(".")[1],
            	proj_dept_id:liger.get("proj_dept_code").getValue().split(".")[0],
            	title_code:liger.get("title_code").getValue(),
        		work_code:liger.get("work_code").getValue(),
        		oper_percent:$("#oper_percent").val()
		};
		ajaxJsonObjectByUrl("updateHtcDeptWorkPeopleRatio.do", formPara, function(responseData) {
			if (responseData.state == "true") {
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

	function saveDeptWorkPeopleRatio() {
		if ($("form").valid()) {
			save();
		}
	}

	
	function loadDict() {
		 autocomplete("#plan_code","../../../info/base/queryHtcPlan.do?isCheck=false", "id", "text",true, true)
		 liger.get("plan_code").setValue('${plan_code}');
		 liger.get("plan_code").setText('${plan_name}');
		 autocomplete("#proj_dept_code","../../../info/base/queryHtcProjDeptDict.do?isCheck=false", "id", "text",true, true)
         liger.get("proj_dept_code").setValue('${proj_dept_id}.${proj_dept_no}');
	     liger.get("proj_dept_code").setText('${proj_dept_name}');
	     autocomplete("#title_code","../../../info/base/queryHtcPeopleTitleDict.do?isCheck=false", "id", "text",true, true)
         liger.get("title_code").setValue('${title_code}');
		 liger.get("title_code").setText('${title_name}');
	     autocomplete("#work_code","../../../info/base/queryHtcWorkDict.do?isCheck=false", "id", "text",true, true)
	     liger.get("work_code").setValue('${work_code}');
		 liger.get("work_code").setText('${work_name}');
		 $("#acc_year").ligerTextBox({disabled:true});
		 $("#plan_code").ligerTextBox({disabled:true});
		 $("#proj_dept_code").ligerTextBox({disabled:true});
		 $("#title_code").ligerTextBox({disabled:true});
		 $("#work_code").ligerTextBox({disabled:true});
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
		    <tr>
             <td align="right" class="l-table-edit-td" style="padding-left: 20px;">年度：</td>
			 <td align="left" class="l-table-edit-td"><input class="Wdate" name="acc_year" type="text" id="acc_year" ltype="text" value='${acc_year}'  validate="{required:true}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})"/></td>
			 <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案：</td>
                <td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code" ltype="text" validate="{required:true}"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算科室：</td>
                <td align="left" class="l-table-edit-td"><input name="proj_dept_code" type="text" id="proj_dept_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职称：</td>
                <td align="left" class="l-table-edit-td"><input name="title_code" type="text" id="title_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">作业：</td>
                <td align="left" class="l-table-edit-td"><input name="work_code" type="text" id="work_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">耗时比例：</td>
                <td align="left" class="l-table-edit-td"><input name="oper_percent" type="text" id="oper_percent" ltype="text" value='${oper_percent}'  validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
		</table>
	</form>
</body>
</html>
