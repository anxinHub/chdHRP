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
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        disabled();
        
        
     });  
     
     var tell_number;
     var create_date;
     function disabled(){
    	 
         tell_number = "${tell_number}";
         
         occur_date = "${occur_date}";
         
         if(tell_number!="" || occur_date!=""){

        	 $("#occur_date").val(occur_date);
        	 
         }else {
        	 
        	 $('#occur_date').attr("disabled",false);
         }
    	 
     }
     
     function  save(){
    	 
    	 var acc_para = '${a030}';

    	 if(isNaN($('#debit').val())==true || isNaN($('#credit').val())==true){
    		 $.ligerDialog.error('金额请输入数字');
    		 return;
    	 }
    	 
    	 if($('#debit').val().length > 0 && $('#credit').val().length > 0 && $('#debit').val()!= '0.0' && $('#credit').val() != '0.0'){
    		 $.ligerDialog.error('借方金额和贷方金额只能填写一个');
    		 return;
    	 }
    	 

    	 if(acc_para==1){
    		 $.ligerDialog.confirm('是否往对方科目添加出纳账？', '出纳账', function(flag){
 				if(flag){
 				  
 					formPara(1);
 					
 				}else{
 					formPara(0);
 				}
 		  });
    		 
    	 }else {
    		 
    		 formPara(0);
    	 }
    }
 function formPara(subj_id){
	 
	    var formPara={
        		
	            cash_subj_id:liger.get("subj_code").getValue().split(".")[0],
	            
	            occur_date:$("#occur_date").val(),
	            
	            summary:$("#summary").val(),
	            
	            att_num:$("#att_num").val(),
	            
	            check_no:$("#check_no").val(),
	            
	            other_subj_id:liger.get("other_subj_code").getValue().split(".")[0],
	             
	            debit:$("#debit").val(),
	             
	            credit:$("#credit").val(),
	            
	            subj_id:subj_id,
	            
	            tell_number:tell_number,

	          };
	         ajaxJsonObjectByUrl("addAccCashAccount.do",formPara,function(responseData){
	             
	             if(responseData.state=="true"){
	 				 $("input[name='summary']").val('');
	 				 $("input[name='att_num']").val('');
	 				 $("input[name='check_no']").val('');
	 				 $("input[name='other_subj_code']").val('');
	 				 $("input[name='debit']").val('');
	 				 $("input[name='credit']").val('');
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
     $("form").ligerForm();
 }       
   
    function saveAccTell(){
    	
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
    	autocomplete("#subj_code","../querySubj.do?isCheck=false&subj_nature_code=02&is_last=1","id","text",true,true);
    	autocomplete("#other_subj_code","../querySubj.do?isCheck=false&is_last=1","id","text",true,true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form" method="post"  id="form" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>科目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
           <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>出纳日期：</td>
                <td align="left" class="l-table-edit-td"><input class="Wdate"   name="occur_date" type="text" id="occur_date" ltype="text" disabled="disabled" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd',minDate:'${modStartTime}'})" /></td>
                <td align="left"></td>
            </tr>
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>摘要：</td>
                <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" validate="{maxlength:10,required:true} " /></td>
                <td align="left"></td>
            </tr>
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>附件张数：</td>
                <td align="left" class="l-table-edit-td"><input name="att_num" type="text" id="att_num" ltype="text" validate="{maxlength:10,required:true}" /></td>
                <td align="left"></td>  
             </tr>
              <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据号：</td>
                <td align="left" class="l-table-edit-td"><input name="check_no" type="text" id="check_no" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对方科目：</td>
                <td align="left" class="l-table-edit-td"><input name="other_subj_code" type="text" id="other_subj_code" ltype="text"/></td>
                <td align="left"></td>
            </tr>  
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>收入金额：</td>
                <td align="left" class="l-table-edit-td"><input name="debit" type="text" id="debit" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>支出金额：</td>
                <td align="left" class="l-table-edit-td"><input name="credit" type="text" id="credit" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red" size="2">*</font>流水号：</td>
                <td align="left" class="l-table-edit-td"><input name="tell_number" type="text" id="tell_number" value ="自动生成"  disabled="disabled" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
