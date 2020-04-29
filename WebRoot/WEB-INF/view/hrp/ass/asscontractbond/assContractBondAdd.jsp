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
     	if(liger.get("ven_id").getValue() == ""){
     		$.ligerDialog.error('供应商不能为空');
     		return;
     	}
     	if($("#create_date").val() == ""){
   			$.ligerDialog.error('登记日期不能为空');
   			return;	
   		}
     	if($("#bond_flag").val() == ""){
   			$.ligerDialog.error('保证金类型不能为空');
   			return;	
   		}
     	if($("#pay_flag").val() == ""){
   			$.ligerDialog.error('付款方式不能为空');
   			return;	
   		}
     	if($("#pay_money").val() == ""){
    		$.ligerDialog.error('金额不能为空');
    		return;	
    	}
     	if($("#check_no").val() == ""){
    		$.ligerDialog.error('支票号不能为空');
    		return;	
    	}
        var formPara={
            
           bond_no:$("#bond_no").val(),
            
           contract_id:liger.get("contract_id").getValue(),
            
           ven_id:liger.get("ven_id").getValue().split("@")[0],
           
           ven_no:liger.get("ven_id").getValue().split("@")[1],
            
           create_date:$("#create_date").val(),
            
           bond_flag:$("#bond_flag").val(),
            
           pay_flag:$("#pay_flag").val(),
            
           pay_money:$("#pay_money").val().replace(",",""),
            
           bill_no:$("#bill_no").val(),
            
           check_no:$("#check_no").val(),
            
           reason:$("#reason").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssContractBond.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	$("#bond_no").val(responseData.bond_no);
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
   
    function saveAssContractBond(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#contract_id", "../queryContractMainDict.do?isCheck=false","id",  "text",true,true); 
            
    	autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false","id",  "text",true,true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保证金单号：</td>
            <td align="left" class="l-table-edit-td"><input name="bond_no" type="text" id="bond_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_id" type="text" id="contract_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>登记日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>保证金类型：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="bond_flag" name="bond_flag">
            		<option value="0">收款</option>
            		<option value="1">退款</option>
            	</select>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>付款方式：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="pay_flag" name="pay_flag">
            		<option value="0">发票</option>
            		<option value="1">支票</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>金额：</td>
            <td align="left" class="l-table-edit-td"><input name="pay_money" type="text" id="pay_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号：</td>
            <td align="left" class="l-table-edit-td"><input name="bill_no" type="text" id="bill_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>支票号：</td>
            <td align="left" class="l-table-edit-td"><input name="check_no" type="text" id="check_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">原因：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
             	<textarea rows="5" cols="70" id="reason" name="reason"></textarea>
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
