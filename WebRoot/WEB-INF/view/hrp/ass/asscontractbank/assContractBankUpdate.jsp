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
    <script type="text/javascript">
     var dataFormat;
     $(function (){
         loadDict()//加载下拉框
        loadForm();
         $("#bond_no").ligerComboBox({disabled:true,cancelable: false});
         $("#pay_money").ligerTextBox({ nullText: '不能为空', currency: true });
     });  
     
     function  save(){
    	 if(liger.get("contract_id").getValue() == ""){
      		$.ligerDialog.error('合同不能为空');
      		return;
      	}
       	if($("#create_date").val() == ""){
     		$.ligerDialog.error('登记日期不能为空');
     		return;	
     	}
       	if($("#pay_money").val() == ""){
      		$.ligerDialog.error('担保金额不能为空');
      		return;	
      	}
       	if($("#reason").val() == ""){
      		$.ligerDialog.error('保函内容不能为空');
      		return;	
      	}
       	if($("#bank_info").val() == ""){
     		$.ligerDialog.error('银行信息不能为空');
     		return;	
     	}
       	if($("#bank_account").val() == ""){
     		$.ligerDialog.error('银行帐号不能为空');
     		return;	
     	}
       	if($("#delegate").val() == ""){
     		$.ligerDialog.error('委托方不能为空');
     		return;	
     	}
       	if($("#hos_info").val() == ""){
       		$.ligerDialog.error('医院信息不能为空');
       		return;
       	}
        var formPara={
            
           bond_no:$("#bond_no").val(),
            
           contract_id:liger.get("contract_id").getValue(),
            
           create_date:$("#create_date").val(),
            
           pay_money:$("#pay_money").val().replace(",",""),
            
           reason:$("#reason").val(),
            
           bank_info:$("#bank_info").val(),
            
           bank_emp:liger.get("bank_emp").getValue(),
            
           bank_tel:$("#bank_tel").val(),
            
           bank_account:$("#bank_account").val(),
            
           delegate:$("#delegate").val(),
            
           delegate_emp:liger.get("delegate_emp").getValue(),
            
           delegate_tel:$("#delegate_tel").val(),
            
           hos_info:$("#hos_info").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssContractBank.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	parentFrameUse().query();
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
     $("form").ligerForm();
 }       
   
    function saveAssContractBank(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#contract_id", "../queryContractMainDict.do?isCheck=false","id",  "text",true,true,null,false,'${contract_id}');  
            
		autocomplete("#bank_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true,null,false,'${bank_emp}');
		
		autocomplete("#delegate_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true,null,false,'${delegate_emp}');
     } 
    function this_close(){
    	frameElement.dialog.close();
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保函单号：</td>
            <td align="left" class="l-table-edit-td"><input name="bond_no" type="text" id="bond_no" value="${bond_no }" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_id" type="text" id="contract_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>登记日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" value="${create_date }" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>担保金额：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_money" type="text" id="pay_money" value="${pay_money }" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>银行信息：</td>
            <td align="left" class="l-table-edit-td"><input name="bank_info" type="text" id="bank_info" value="${bank_info }" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="bank_emp" type="text" id="bank_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行联系电话：</td>
            <td align="left" class="l-table-edit-td"><input name="bank_tel" type="text" id="bank_tel" value="${bank_tel }" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>银行帐号：</td>
            <td align="left" class="l-table-edit-td"><input name="bank_account" type="text" id="bank_account" value="${bank_account }" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>委托方：</td>
            <td align="left" class="l-table-edit-td"><input name="delegate" type="text" id="delegate" value="${delegate }" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">委托方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="delegate_emp" type="text" id="delegate_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">委托人联系电话：</td>
            <td align="left" class="l-table-edit-td"><input name="delegate_tel" type="text" id="delegate_tel" value="${delegate_tel }" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>医院信息：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_info" type="text" id="hos_info" value="${hos_info }" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保函内容：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
             	<textarea rows="5" cols="70" id="reason" name="reason">${reason }</textarea>
            </td>
            <td align="left"></td>
        </tr>
    </table>
    <table align="center" cellpadding="0" cellspacing="0" class="l-table-edit">
        	<tr align="center"> 
        		<td align="center" class="l-table-edit-td"  style="padding-left:20px;"><input class="l-button l-button-test" style="float: right;" type="button" value="保存" onclick="save();"/></td>
        		<td align="center" class="l-table-edit-td"  style="padding-left:20px;"><input class="l-button l-button-test" style="float: right;" type="button" value="关闭" onclick="this_close();"/></td>
        	</tr>
       </table>
    </form>
   
    </body>
</html>
