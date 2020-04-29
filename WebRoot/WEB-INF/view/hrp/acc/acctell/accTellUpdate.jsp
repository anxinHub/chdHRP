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
        
    });  
     
    function save(){
        var formPara={
        tell_id:$("#tell_id").val(),
				
				
				
        acc_year:$("#acc_year").val(),
        cash_subj_code:$("#cash_subj_code").val(),
        other_subj_id:$("#other_subj_id").val(),
        summary:$("#summary").val(),
        att_num:$("#att_num").val(),
        debit:$("#debit").val(),
        credit:$("#credit").val(),
        cur_code:$("#cur_code").val(),
        debit_w:$("#debit_w").val(),
        credit_w:$("#credit_w").val(),
        bal_w:$("#bal_w").val(),
        check_no:$("#check_no").val(),
        busi_type:$("#busi_type").val(),
        business_no:$("#business_no").val(),
        occur_date:$("#occur_date").val(),
        pay_type_code:$("#pay_type_code").val(),
        create_user:$("#create_user").val(),
        create_date:$("#create_date").val(),
        con_user:$("#con_user").val(),
        con_date:$("#con_date").val(),
        is_check:$("#is_check").val(),
        check_user:$("#check_user").val(),
        check_date:$("#check_date").val(),
        is_init:$("#is_init").val(),
        vouch_check_id:$("#vouch_check_id").val(),
        vouch_id:$("#vouch_id").val(),
        is_import:$("#is_import").val()
        };
        ajaxJsonObjectByUrl("updateAccTell.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
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
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出纳账ID：</td>
                <td align="left" class="l-table-edit-td"><input name="tell_id" type="text" id="tell_id" ltype="text"  value="${tell_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text"  value="${acc_year}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">现金科目：</td>
                <td align="left" class="l-table-edit-td"><input name="cash_subj_code" type="text" id="cash_subj_code" ltype="text"  value="${cash_subj_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对方科目：</td>
                <td align="left" class="l-table-edit-td"><input name="other_subj_id" type="text" id="other_subj_id" ltype="text"  value="${other_subj_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
                <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text"  value="${summary}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">附件数量：</td>
                <td align="left" class="l-table-edit-td"><input name="att_num" type="text" id="att_num" ltype="text"  value="${att_num}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">借方金额：</td>
                <td align="left" class="l-table-edit-td"><input name="debit" type="text" id="debit" ltype="text"  value="${debit}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">贷方金额：</td>
                <td align="left" class="l-table-edit-td"><input name="credit" type="text" id="credit" ltype="text"  value="${credit}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">币种编码：</td>
                <td align="left" class="l-table-edit-td"><input name="cur_code" type="text" id="cur_code" ltype="text"  value="${cur_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">借方金额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="debit_w" type="text" id="debit_w" ltype="text"  value="${debit_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">贷方金额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="credit_w" type="text" id="credit_w" ltype="text"  value="${credit_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">余额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="bal_w" type="text" id="bal_w" ltype="text"  value="${bal_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据号：</td>
                <td align="left" class="l-table-edit-td"><input name="check_no" type="text" id="check_no" ltype="text"  value="${check_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类型：</td>
                <td align="left" class="l-table-edit-td"><input name="busi_type" type="text" id="busi_type" ltype="text"  value="${busi_type}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单据号：</td>
                <td align="left" class="l-table-edit-td"><input name="business_no" type="text" id="business_no" ltype="text"  value="${business_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发生日期：</td>
                <td align="left" class="l-table-edit-td"><input name="occur_date" type="text" id="occur_date" ltype="text"  value="${occur_date}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结算方式：</td>
                <td align="left" class="l-table-edit-td"><input name="pay_type_code" type="text" id="pay_type_code" ltype="text"  value="${pay_type_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
                <td align="left" class="l-table-edit-td"><input name="create_user" type="text" id="create_user" ltype="text"  value="${create_user}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单日期：</td>
                <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" ltype="text"  value="${create_date}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">确认人：</td>
                <td align="left" class="l-table-edit-td"><input name="con_user" type="text" id="con_user" ltype="text"  value="${con_user}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">确认日期：</td>
                <td align="left" class="l-table-edit-td"><input name="con_date" type="text" id="con_date" ltype="text"  value="${con_date}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否对账：</td>
                <td align="left" class="l-table-edit-td"><input name="is_check" type="text" id="is_check" ltype="text"  value="${is_check}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对账人：</td>
                <td align="left" class="l-table-edit-td"><input name="check_user" type="text" id="check_user" ltype="text"  value="${check_user}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对账日期：</td>
                <td align="left" class="l-table-edit-td"><input name="check_date" type="text" id="check_date" ltype="text"  value="${check_date}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否初始：</td>
                <td align="left" class="l-table-edit-td"><input name="is_init" type="text" id="is_init" ltype="text"  value="${is_init}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">辅助核算ID：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_check_id" type="text" id="vouch_check_id" ltype="text"  value="${vouch_check_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证ID：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_id" type="text" id="vouch_id" ltype="text"  value="${vouch_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否外部导入：</td>
                <td align="left" class="l-table-edit-td"><input name="is_import" type="text" id="is_import" ltype="text"  value="${is_import}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
