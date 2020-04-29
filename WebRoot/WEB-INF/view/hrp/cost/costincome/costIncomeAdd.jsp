<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
            
            
           year_month:$("#year_month").val(),
            
           appl_dept_id:$("#appl_dept_id").val(),
            
           appl_dept_no:$("#appl_dept_no").val(),
            
           exec_dept_id:$("#exec_dept_id").val(),
            
           exec_dept_no:$("#exec_dept_no").val(),
            
           charge_kind_code:$("#charge_kind_code").val(),
            
           money:$("#money").val(),
            
           create_user:$("#create_user").val(),
            
           create_date:$("#create_date").val()
            
         };
        
        ajaxJsonObjectByUrl("addCostIncome.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				
				
				
				 $("input[name='year_month']").val('');
				 $("input[name='appl_dept_id']").val('');
				 $("input[name='appl_dept_no']").val('');
				 $("input[name='exec_dept_id']").val('');
				 $("input[name='exec_dept_no']").val('');
				 $("input[name='charge_kind_code']").val('');
				 $("input[name='money']").val('');
				 $("input[name='create_user']").val('');
				 $("input[name='create_date']").val('');
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
   
    function saveCostIncome(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">统计年月：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室：</td>
                <td align="left" class="l-table-edit-td"><input name="appl_dept_id" type="text" id="appl_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开单科室变更ID：</td>
                <td align="left" class="l-table-edit-td"><input name="appl_dept_no" type="text" id="appl_dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室：</td>
                <td align="left" class="l-table-edit-td"><input name="exec_dept_id" type="text" id="exec_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">执行科室变更ID：</td>
                <td align="left" class="l-table-edit-td"><input name="exec_dept_no" type="text" id="exec_dept_no" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">收费类别：</td>
                <td align="left" class="l-table-edit-td"><input name="charge_kind_code" type="text" id="charge_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
                <td align="left" class="l-table-edit-td"><input name="money" type="text" id="money" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">操作员ID：</td>
                <td align="left" class="l-table-edit-td"><input name="create_user" type="text" id="create_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">操作时间：</td>
                <td align="left" class="l-table-edit-td"><input name="create_date" type="text" id="create_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
