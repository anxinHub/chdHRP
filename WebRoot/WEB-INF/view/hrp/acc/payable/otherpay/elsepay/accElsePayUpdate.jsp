<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	$(function() {
		loadDict();//加载下拉框
		loadForm();
		
	});
	
	//保存
	function save() {
		var formPara = {
			pay_code : $("#pay_code").val(),
			dept_id : liger.get("dept_id").getValue().split(".")[0],
			dept_no : liger.get("dept_id").getValue().split(".")[1],
			payee : liger.get("payee").getValue().split(".")[0],
			phone : $("#phone").val(),
			pay_money : $("#pay_money").val(),
			card_no : $("#card_no").val(),
			bank: $("#bank").val(),
			remark : $("#remark").val(),
			price : $("#price").val(),
			amount : $("#amount").val()
		};
		console.log(formPara);
		ajaxJsonObjectByUrl("updateAccELsePay.do?isCheck=false", formPara,function(responseData) {
			if (responseData.state == "true") {
				parent.query();
			}
		});
	}
	function loadForm(){
	    
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element)
	         {
	             if (element.hasClass("l-textarea"))
	             {
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }
	             else if (element.hasClass("l-text-field"))
	             {
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }
	             else
	             {
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	     //$("form").ligerForm();
	 }       
	
	 function saveAccElsePay(){
        if($("form").valid()){
            save();
        }
	 }
	
	//加载字典
	function loadDict() {
		
		$("#dept_id").ligerComboBox({
          	url: "../../../../sys/queryDeptDict.do?isCheck=false&is_last=1",
          	valueField: 'id',textField: 'text', selectBoxWidth: 200,
          	autocomplete: true,width: 200,
          	onSelected:function(value){
          		var dept_id = value.split(".")[0];
				var dept_no = value.split(".")[1];
          		$("#payee").ligerComboBox({
                  	url: "../../../queryEmpDict.do?isCheck=false&dept_id="+dept_id+"&dept_no="+dept_no,
                  	valueField: 'id',textField: 'text',selectBoxWidth: 200,
                  	autocomplete: true,width: 200,
         		});
          		liger.get("payee").setValue("");
          		liger.get("payee").setText(""); 
          	}
 		});
		
		liger.get("dept_id").setValue('${dept_id}.${dept_no}.${dept_code}')
		
		liger.get("dept_id").setText('${dept_code} ${dept_name}')
		
		//autocomplete("#payee","../../../queryEmpDict.do?isCheck=false", "id","text", true, true,null,false,'',"200");
		
		$("#payee").ligerComboBox({
          	url: "../../../queryEmpDict.do?isCheck=false",
          	valueField: 'id',textField: 'text',selectBoxWidth: 200,
          	autocomplete: true,width: 200,
          	onSelected:function(v){
          		$("#card_no").val("");
          		var emp_id = v.split(".")[0];
				var emp_no = v.split(".")[1];
				if(emp_id!=""&&emp_no!=""){
					$.post("queryGetEmpCardNo.do?isCheck=false",{emp_id:emp_id,emp_no:emp_no},function(data){
						$("#card_no").val(data.accEmpAccount.account_code);
					},"json");
				}
          	}
 		});
    	
		liger.get("payee").setValue('${payee}.${emp_no}')
		
		liger.get("payee").setText('${emp_code} ${payee_name}')
		
    	
		$("#pay_code").ligerTextBox({disabled:true,cancelable: false,width : 200});
		$("#pay_money").ligerTextBox({width: 200});
		$("#phone").ligerTextBox({width : 200});
		$("#bank").ligerTextBox({width : 500});
		$("#card_no").ligerTextBox({width : 200});
		$("#price").ligerTextBox({width : 200});
		$("#amount").ligerTextBox({width : 200});
		
		$("#remark").val('${remark}') ;
    	
	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post"  id="form1" >
		<table cellpadding="0" cellspacing="0" class="l-table-edit" border="0">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>付款凭证号：</td>
				<td align="left" class="l-table-edit-td" ><input name="pay_code" disabled="disabled" type="text" value='${pay_code}' id="pay_code" validate="{required:true,maxlength:20}"  /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>科室：</td>
				<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" validate="{required:true}"  /></td>
				<td align="left"></td>
			</tr>	
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>领款人：</td>
				<td align="left" class="l-table-edit-td"><input	name="payee" type="text" id="payee" validate="{required:true}"  /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>联系电话：</td>
				<td align="left" class="l-table-edit-td"><input name="phone" type="text" id="phone" value='${phone}'  validate="{required:true}" /></td>
				<td align="left"></td>
			</tr>	
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>付款金额：</td>
				<td align="left" class="l-table-edit-td"><input name="pay_money" type="text" id="pay_money" value='${pay_money}' validate="{required:true,number:true}"/></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>银行帐号：</td>
				<td align="left" class="l-table-edit-td"><input name="card_no" type="text" id="card_no" value='${card_no}' validate="{required:true,creditcard:true}" /></td>
				<td align="left"></td>
			</tr>	
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>开户行：</td>
				<td align="left" class="l-table-edit-td" colspan="4"><input name="bank" type="text" id="bank" value='${bank}' validate="{required:true}"/></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;"><b><font color="red">*</font></b>付款事由：</td>
				<td align="left" class="l-table-edit-td" colspan="4">
					<textarea rows="6" cols="80" id="remark" name="remark"></textarea>
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">单价：</td>
				<td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" value='${price}' validate="{number:true}" /></td>
				<td align="left"></td>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">数量：</td>
				<td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" value='${amount}' validate="{number:true}"/></td>
				<td align="left"></td>
				
			</tr>	
		</table>
	</form>
	<div id="maingrid"></div>
	
</body>
</html>
