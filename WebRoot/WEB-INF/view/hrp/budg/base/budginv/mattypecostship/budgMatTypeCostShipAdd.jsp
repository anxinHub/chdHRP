<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
    var budg_year = '${budg_year}';
	$(function(){
		loadDict();
        loadForm();
        
        $("#budg_year").change(function() {
			budg_year = liger.get("budg_year").getValue();
			//物资分类下拉框
			autocomplete("#mat_type_id","queryMatTypes.do?isCheck=false&is_filter=1&budg_year="+budg_year,"id","text",true,true,'',false,'',180,150);
			//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
			autocomplete("#cost_subj_code","queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'',180);
			
			 $("#income_subj_code").ligerComboBox({
		            width: 180,
		            url: "queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1&budg_year="+budg_year,
		            valueField: "id",
		            textField: "text",
		            resize: false,
		            isShowCheckBox: true,
		            isMultiSelect: true,
		            cancelable: true,
		     });
        })
	})
	
	function save(){
		
		var formPara = {
			budg_year:liger.get("budg_year").getValue(),
			mat_type_id:liger.get("mat_type_id").getValue(),
			cost_subj_code:liger.get("cost_subj_code").getValue(),
			income_subj_code:(liger.get("income_subj_code").getValue()?liger.get("income_subj_code").getValue():""),
			no_medical : liger.get("no_medical").getValue()
		};
		
		ajaxJsonObjectByUrl("addBudgMatTypeCostShip.do", formPara, function(
				responseData) {

			if (responseData.state == "true") {
				$("input[name='mat_type_id']").val('');
				$("input[name='cost_subj_code']").val('');
				$("input[name='income_subj_code']").val('');
				loadDict()
				parent.query();
			}
		}); 
	}
	
	function saveBudgMatTypeSubj() {
		if ($("form").valid()) {
			save();
		}
	}
  	//加载字典下拉框
	function loadDict() {
		//预算年度下拉框
		autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',false,budg_year,180,100);
       	
		//物资分类下拉框
		autocomplete("#mat_type_id","queryMatTypes.do?isCheck=false&is_filter=1&budg_year="+budg_year,"id","text",true,true,'',false,'${mat_type_id}',180);
        
		//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
		autocomplete("#cost_subj_code","queryBudgSubj.do?isCheck=false&subj_type=05&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'${subj_code}',180);
       // autocomplete("#income_subj_code","queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'${subj_code}',180,150);
        $("#income_subj_code").ligerComboBox({
            width: 160,
            url: "queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1&budg_year="+budg_year,
            valueField: "id",
            textField: "text",
            resize: false,
            isShowCheckBox: true,
            isMultiSelect: true,
            cancelable: true,
        });
        
        autoCompleteByData("#no_medical", yes_or_no.Rows, "id", "text", true, true,'',true,'',180);
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
    </script>
  
  </head>
  
   <body>
	   <div id="pageloading" class="l-loading" style="display: none"></div>
	   <form name="form1" method="post"  id="form1" >
	        <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top:20px;margin-left:20px;">
				<tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算年度<font color="red" >*</font>:</b></td>
		            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" validate="{required:true}" /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>物资分类<font color="red">*</font>:</b></td>
		            <td align="left" class="l-table-edit-td"><input name="mat_type_id" type="text" id="mat_type_id" validate="{required:true}" /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>支出预算科目<font color="red">*</font>:</b></td>
		            <td align="left" class="l-table-edit-td"><input name="cost_subj_code" type="text" id="cost_subj_code" validate="{required:true,maxlength:50}" /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>收入预算科目:</b></td>
		            <td align="left" class="l-table-edit-td"><input name="income_subj_code" type="text" id="income_subj_code" /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>非医用标识:</b></td>
		            <td align="left" class="l-table-edit-td"><input name="no_medical" type="text" id="no_medical" validate="{required:true,maxlength:4}" /></td>
		            <td align="left"></td>
		        </tr> 
	
			</table>
	    </form>
    </body>
</html>
