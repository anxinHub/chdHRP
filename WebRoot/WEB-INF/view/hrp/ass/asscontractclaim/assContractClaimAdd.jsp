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
         $("#claim_no").ligerComboBox({disabled:true,cancelable: false});
         $("#breach_money").ligerTextBox({ nullText: '不能为空', currency: true });
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
     	if($("#breach_money").val() == ""){
    		$.ligerDialog.error('赔偿金额不能为空');
    		return;	
    	}
     	if($("#claim_flag").val() == ""){
     		$.ligerDialog.error('索赔方不能为空');
     		return;	
     	}
   		if(liger.get("first_emp").getValue() == ""){
   			$.ligerDialog.error('甲方负责人不能为空');
   			return;
   		}
   		if(liger.get("second_emp").getValue() == ""){
   			$.ligerDialog.error('乙方负责人不能为空');
   			return;
   		}
   		if($("#begin_date").val() == ""){
    		$.ligerDialog.error('开始日期不能为空');
    		return;	
    	}
     	if($("#end_date").val() == ""){
     		$.ligerDialog.error('结束日期不能为空');
     		return;	
     	} 
     	if($("#claim_basis").val() == ""){
     		$.ligerDialog.error('索赔依据不能为空');
     		return;	
     	}
        var formPara={
            
           claim_no:$("#claim_no").val(),
            
           contract_id:liger.get("contract_id").getValue(),
            
           ven_id:liger.get("ven_id").getValue().split("@")[0],
           
           ven_no:liger.get("ven_id").getValue().split("@")[1],
            
           create_date:$("#create_date").val(),
            
           breach_money:$("#breach_money").val().replace(",",""),
            
           claim_flag:$("#claim_flag").val(),
            
           first_emp:liger.get("first_emp").getValue(),
            
           second_emp:liger.get("second_emp").getValue(),
            
           begin_date:$("#begin_date").val(),
            
           end_date:$("#end_date").val(),
            
           claim_basis:$("#claim_basis").val(),
            
           claim_reason:$("#claim_reason").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssContractClaim.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	$("#claim_no").val(responseData.claim_no);
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
   
    function saveAssContractClaim(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#ven_id", "../queryHosSupDict.do?isCheck=false","id",  "text",true,true);
        
    	autocomplete("#contract_id", "../queryContractMainDict.do?isCheck=false","id",  "text",true,true);
    	
		autocomplete("#first_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true);
		
		autocomplete("#second_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true);
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">索赔单号：</td>
            <td align="left" class="l-table-edit-td"><input name="claim_no" type="text" id="claim_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>合同：</td>
            <td align="left" class="l-table-edit-td"><input name="contract_id" type="text" id="contract_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id" ltype="text" validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>登记日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>赔偿金额：</td>
            <td align="left" class="l-table-edit-td"><input name="breach_money" type="text" id="breach_money" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>索赔方：</td>
            <td align="left" class="l-table-edit-td">
            	<select id="claim_flag" name="claim_flag">
            		<option value="0">甲方</option>
            		<option value="1">乙方</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>甲方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="first_emp" type="text" id="first_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>乙方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="second_emp" type="text" id="second_emp" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>开始日期：</td>
            <td align="left" class="l-table-edit-td"><input name="begin_date" type="text" id="begin_date" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>结束日期：</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" ltype="text" validate="{required:true,maxlength:20}" class="Wdate"
				onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
            <td align="left"></td>
        </tr> 
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>索赔依据：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
            <textarea rows="5" cols="70" id="claim_basis" name="claim_basis"></textarea>
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">索赔原因：</td>
            <td align="left" class="l-table-edit-td" colspan="3">
             <textarea rows="5" cols="70" id="claim_reason" name="claim_reason"></textarea>
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
