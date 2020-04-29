<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
        liger.get("subj_code").setValue('${subj_code}');
        liger.get("subj_code").setText('${subj_code}'+' '+'${subj_name}');
        $("#debit").val(formatNumber('${debit}',2,1));
        $("#credit").val(formatNumber('${credit}',2,1));
        liger.get("pay_type_code").setValue("${pay_type_code}");
        liger.get("pay_type_code").setText("${pay_name}");
    });  
     
    function save(){
    	
    	if("${is_check}" == 1){
   		 $.ligerDialog.error('已对账的数据不许修改');
   		 return;
	   	}
	   	
	   	if($('#debit').val().replace(/\s+/g,"")==""){
	   		$('#debit').val("0.00");
	   	}
	   	
	   	if($('#credit').val().replace(/\s+/g,"")==""){
	   		$('#credit').val("0.00");
	   	}
	   	
	   	var debit=$('#debit').val().replace(/,|\s/g,'');
	   	var credit=$('#credit').val().replace(/,|\s/g,'');
	   	 if(isNaN(debit) || isNaN(credit)){
	   		 $.ligerDialog.error('金额请输入数字');
	   		 return;
	   	 }
	   	 
	   	 if(debit.length > 0 && credit.length > 0 && parseInt(debit)!=0 && parseInt(credit)!=0){
	   		 $.ligerDialog.error('借方金额和贷方金额只能填写一个');
	   		 return;
	   	 }
	   	 
	   	 if(debit.length > 0 && credit.length > 0 && parseInt(debit)==0 && parseInt(credit)==0){
	   		 $.ligerDialog.error('借方金额和贷方金额只能填写一个');
	   		 return;
	   	 }


		var formPara = {
				
			bank_id : '${bank_id}',

			subj_code : liger.get("subj_code").getValue(),

			debit : debit,

			credit : credit,

			check_no : $("#check_no").val(),

			occur_date : $("#occur_date").val(),

			summary : $("#summary").val(),

			pay_type_code : liger.get("pay_type_code").getValue()
		};
		ajaxJsonObjectByUrl("updateAccBankAccount.do", formPara, function(
				responseData) {
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

	function saveAccTell() {
		if ($("form").valid()) {
			save();
		}
	}
	function loadDict() {
		//字典下拉框
		autocomplete("#subj_code", "../querySubj.do?isCheck=false&subj_nature_code=03&is_last=1", "id",
				"text", true, true,'','','','','',"300px");
		autocomplete("#pay_type_code", "../queryPayType.do?isCheck=false",
				"id", "text", true, true);
	}
</script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>科目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true}"  disabled="disabled"/></td>
                <td align="left"></td>
            </tr> 
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>业务日期：</td>
                <td align="left" class="l-table-edit-td"><input class="Wdate"   name="occur_date" type="text" id="occur_date" ltype="text"  value="${occur_date }"  validate="{required:true}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" disabled="disabled" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结算方式：</td>
                <td align="left" class="l-table-edit-td"><input name="pay_type_code" type="text" id="pay_type_code" ltype="text"  value="${pay_type_code }"  /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据号：</td>
                <td align="left" class="l-table-edit-td"><input name="check_no" type="text" id="check_no" ltype="text" value="${ check_no}"   validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>摘要：</td>
                <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" value="${ summary}"   validate="{maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">借方金额：</td>
                <td align="left" class="l-table-edit-td"><input name="debit" type="text" id="debit" ltype="text"  validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">贷方金额：</td>
                <td align="left" class="l-table-edit-td"><input name="credit" type="text" id="credit" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
