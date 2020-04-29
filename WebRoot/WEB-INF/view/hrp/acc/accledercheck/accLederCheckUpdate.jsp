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
        check_id:$("#check_id").val(),
        leger_id:$("#leger_id").val(),
				
				
				
        acc_year:$("#acc_year").val(),
        acc_month:$("#acc_month").val(),
        subj_code:$("#subj_code").val(),
        bal_os:$("#bal_os").val(),
        this_od:$("#this_od").val(),
        this_oc:$("#this_oc").val(),
        sum_od:$("#sum_od").val(),
        sum_oc:$("#sum_oc").val(),
        end_os:$("#end_os").val(),
        bal_os_w:$("#bal_os_w").val(),
        this_od_w:$("#this_od_w").val(),
        this_oc_w:$("#this_oc_w").val(),
        sum_od_w:$("#sum_od_w").val(),
        sum_oc_w:$("#sum_oc_w").val(),
        end_os_w:$("#end_os_w").val(),
        check1_id:$("#check1_id").val(),
        check1_no:$("#check1_no").val(),
        check2_id:$("#check2_id").val(),
        check2_no:$("#check2_no").val(),
        check3_id:$("#check3_id").val(),
        check3_no:$("#check3_no").val(),
        check4_id:$("#check4_id").val(),
        check4_no:$("#check4_no").val(),
        check5_id:$("#check5_id").val(),
        check5_no:$("#check5_no").val(),
        check6_id:$("#check6_id").val(),
        check6_no:$("#check6_no").val(),
        check7_id:$("#check7_id").val()
        };
        ajaxJsonObjectByUrl("updateAccLederCheck.do",formPara,function(responseData){
            
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
   
    function saveAccLederCheck(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">辅助账ID：</td>
                <td align="left" class="l-table-edit-td"><input name="check_id" type="text" id="check_id" ltype="text"  value="${check_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务账ID：</td>
                <td align="left" class="l-table-edit-td"><input name="leger_id" type="text" id="leger_id" ltype="text"  value="${leger_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text"  value="${acc_year}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month" ltype="text"  value="${acc_month}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目编码：</td>
                <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text"  value="${subj_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期初余额：</td>
                <td align="left" class="l-table-edit-td"><input name="bal_os" type="text" id="bal_os" ltype="text"  value="${bal_os}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本期借方金额：</td>
                <td align="left" class="l-table-edit-td"><input name="this_od" type="text" id="this_od" ltype="text"  value="${this_od}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本期贷方金额：</td>
                <td align="left" class="l-table-edit-td"><input name="this_oc" type="text" id="this_oc" ltype="text"  value="${this_oc}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本年累计借方金额：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_od" type="text" id="sum_od" ltype="text"  value="${sum_od}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本年累计贷方金额：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_oc" type="text" id="sum_oc" ltype="text"  value="${sum_oc}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期末余额：</td>
                <td align="left" class="l-table-edit-td"><input name="end_os" type="text" id="end_os" ltype="text"  value="${end_os}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期初余额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="bal_os_w" type="text" id="bal_os_w" ltype="text"  value="${bal_os_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本期借方金额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="this_od_w" type="text" id="this_od_w" ltype="text"  value="${this_od_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本期贷方金额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="this_oc_w" type="text" id="this_oc_w" ltype="text"  value="${this_oc_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本年累计借方金额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_od_w" type="text" id="sum_od_w" ltype="text"  value="${sum_od_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">本年累计贷方金额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="sum_oc_w" type="text" id="sum_oc_w" ltype="text"  value="${sum_oc_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">期末余额外币：</td>
                <td align="left" class="l-table-edit-td"><input name="end_os_w" type="text" id="end_os_w" ltype="text"  value="${end_os_w}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门核算：</td>
                <td align="left" class="l-table-edit-td"><input name="check1_id" type="text" id="check1_id" ltype="text"  value="${check1_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门核算变更号：</td>
                <td align="left" class="l-table-edit-td"><input name="check1_no" type="text" id="check1_no" ltype="text"  value="${check1_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工核算：</td>
                <td align="left" class="l-table-edit-td"><input name="check2_id" type="text" id="check2_id" ltype="text"  value="${check2_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工核算变更号：</td>
                <td align="left" class="l-table-edit-td"><input name="check2_no" type="text" id="check2_no" ltype="text"  value="${check2_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目核算：</td>
                <td align="left" class="l-table-edit-td"><input name="check3_id" type="text" id="check3_id" ltype="text"  value="${check3_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目核算变更号：</td>
                <td align="left" class="l-table-edit-td"><input name="check3_no" type="text" id="check3_no" ltype="text"  value="${check3_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房核算：</td>
                <td align="left" class="l-table-edit-td"><input name="check4_id" type="text" id="check4_id" ltype="text"  value="${check4_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房核算变更号：</td>
                <td align="left" class="l-table-edit-td"><input name="check4_no" type="text" id="check4_no" ltype="text"  value="${check4_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">客户核算：</td>
                <td align="left" class="l-table-edit-td"><input name="check5_id" type="text" id="check5_id" ltype="text"  value="${check5_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">客户核算变更号：</td>
                <td align="left" class="l-table-edit-td"><input name="check5_no" type="text" id="check5_no" ltype="text"  value="${check5_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商核算：</td>
                <td align="left" class="l-table-edit-td"><input name="check6_id" type="text" id="check6_id" ltype="text"  value="${check6_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商核算变更号：</td>
                <td align="left" class="l-table-edit-td"><input name="check6_no" type="text" id="check6_no" ltype="text"  value="${check6_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源核算：</td>
                <td align="left" class="l-table-edit-td"><input name="check7_id" type="text" id="check7_id" ltype="text"  value="${check7_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
