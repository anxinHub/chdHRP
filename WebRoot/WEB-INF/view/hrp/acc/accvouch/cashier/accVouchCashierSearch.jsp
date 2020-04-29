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
        
        $("#tell_id").ligerTextBox({width:160 });
        
        $("#acc_year").ligerTextBox({width:160 });
        
        $("#vouch_id").ligerTextBox({width:160 });
        
        $("#vouch_no").ligerTextBox({width:160 });
        
		$("#summary").ligerTextBox({width:160 });
        
        $("#att_num").ligerTextBox({width:160 });
        
		$("#subj_code").ligerTextBox({width:160 });
        
        $("#subj_id").ligerTextBox({width:160 });
        
		$("#vouch_type_code").ligerTextBox({width:160 });
        
        $("#state").ligerTextBox({width:160 });
        
        $("#create_user").ligerTextBox({width:160 });
        
        $("#cash_user").ligerTextBox({width:160 });
        
        $("#audit_user").ligerTextBox({width:160 });
         
        $("#acc_user").ligerTextBox({width:160 });
         
        $("#business_no").ligerTextBox({width:400 });
         
        $("#occur_date").ligerTextBox({width:160 });
         
         
     });  
     
     function  save(){
        var formPara={
           tell_id:$("#tell_id").val(),

           acc_year:$("#acc_year").val(),
            
           cash_subj_code:$("#cash_subj_code").val(),
            
           other_subj_code:$("#other_subj_code").val(),
            
           summary:$("#summary").val(),
            
           att_num:$("#att_num").val(),
            
           debit:$("#debit").val(),
            
           credit:$("#credit").val(),
            
           vouch_type_code:$("#vouch_type_code").val(),
            
           debit_w:$("#debit_w").val(),
            
           create_user:$("#create_user").val(),
            
           cash_user:$("#cash_user").val(),
            
           audit_user:$("#audit_user").val(),
            
           acc_user:$("#acc_user").val(),
            
           business_no:$("#business_no").val(),
            
           occur_date:$("#occur_date").val()
            
         };
        
        ajaxJsonObjectByUrl("addAccTell.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='tell_id']").val('');
				 $("input[name='acc_year']").val('');
				 $("input[name='cash_subj_code']").val('');
				 $("input[name='other_subj_code']").val('');
				 $("input[name='summary']").val('');
				 $("input[name='att_num']").val('');
				 $("input[name='debit']").val('');
				 $("input[name='credit']").val('');
				 $("input[name='vouch_type_code']").val('');
				 $("input[name='debit_w']").val('');
				 $("input[name='create_user']").val('');
				 $("input[name='cash_user']").val('');
				 $("input[name='audit_user']").val('');
				 $("input[name='acc_user']").val('');
				 $("input[name='business_no']").val('');
				 $("input[name='occur_date']").val('');
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
            //字典下拉框
    	autocomplete("#subj_code","../../querySubj.do?isCheck=false","id","text",true,true);
    	autocomplete("#subj_id","../../querySubj.do?isCheck=false","id","text",true,true);
    	autocomplete("#vouch_type_code", "../../queryVouchType.do?isCheck=false","id", "text", true, true);
    	autocomplete("#state", "../../queryAccVouchState.do?isCheck=false","id", "text", true, true);
    	autocomplete("#acc_user", "../../../sys/queryEmp.do?isCheck=false","id", "text", true, true);
    	autocomplete("#audit_user", "../../../sys/queryEmp.do?isCheck=false","id", "text", true, true);
    	autocomplete("#create_user", "../../../sys/queryEmp.do?isCheck=false","id", "text", true, true);
    	autocomplete("#cash_user", "../../../sys/queryEmp.do?isCheck=false","id", "text", true, true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
                <td align="left" class="l-table-edit-td"><input name="tell_id"  class="Wdate" type="text" id="tell_id" ltype="text" validate="{required:true,maxlength:20}"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
                <td align="left" >--</td>
                <td align="left" class="l-table-edit-td"  colspan="2"><input name="acc_year"  class="Wdate"  type="text" id="acc_year" ltype="text" validate="{required:true,maxlength:20}"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})"/></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证号：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no" type="text" id="vouch_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left" >--</td>
                <td align="left" class="l-table-edit-td" colspan="2"><input name="vouch_id" type="text" id="vouch_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
                <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" >--</td>
                <td align="left" class="l-table-edit-td" colspan="2"><input name="att_num" type="text" id="att_num" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="right" >--</td>
                <td align="left" class="l-table-edit-td" colspan="2"><input name="subj_id" type="text" id="subj_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证类型：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="left" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
                <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
                <td align="left" class="l-table-edit-td"><input name="create_user" type="text" id="create_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="left" class="l-table-edit-td"  style="padding-left:20px;">出纳人：</td>
                <td align="left" class="l-table-edit-td"><input name="cash_user" type="text" id="cash_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
                <td align="left" class="l-table-edit-td"><input name="audit_user" type="text" id="audit_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="left" class="l-table-edit-td"  style="padding-left:20px;">记账人：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_user" type="text" id="acc_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><input name="business_no" type="text" id="business_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种：</td>
                <td align="left" class="l-table-edit-td"><input name="occur_date" type="text" id="occur_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">展现方式：</td>
                <td align="left" class="l-table-edit-td">
                <input name="pay_type_code" type="radio" id="pay_type_code" ltype="text" validate="{required:true,maxlength:20}"  checked="checked"/>按凭证展示
                <input name="pay_type_code" type="radio" id="pay_type_code" ltype="text" validate="{required:true,maxlength:20}" />按凭证分录展示
                </td>
                
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><input name="note" type="checkbox" id="note" ltype="text" validate="{required:true,maxlength:20}" />标注凭证：</td>
                <td align="left"></td>
            </tr>

        </table>
    </form>
   
    </body>
</html>
