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
        
     });  
     
     function  save(){
        var formPara={
            
            
            
           year:$("#year").val(),
            
           index_code:$("#index_code").val(),
            
           dept_id:$("#dept_id").val(),
            
           month:$("#month").val(),
            
           count_value:$("#count_value").val(),
            
           budg_value:$("#budg_value").val(),
            
           remark:$("#remark").val(),
            
           grow_rate:$("#grow_rate").val(),
            
           resolve_rate:$("#resolve_rate").val(),
            
           last_year_workload:$("#last_year_workload").val(),
            
           last_month_carried:$("#last_month_carried").val(),
            
           carried_next_month:$("#carried_next_month").val()
            
         };
        
        ajaxJsonObjectByUrl("addBudgWorkDeptMonth.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='year']").val('');
				 $("input[name='index_code']").val('');
				 $("input[name='dept_id']").val('');
				 $("input[name='month']").val('');
				 $("input[name='count_value']").val('');
				 $("input[name='budg_value']").val('');
				 $("input[name='remark']").val('');
				 $("input[name='grow_rate']").val('');
				 $("input[name='resolve_rate']").val('');
				 $("input[name='last_year_workload']").val('');
				 $("input[name='last_month_carried']").val('');
				 $("input[name='carried_next_month']").val('');
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
   
    function saveBudgWorkDeptMonth(){
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
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码：</td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门ID：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月份：</td>
            <td align="left" class="l-table-edit-td"><input name="month" type="text" id="month" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计算值：</td>
            <td align="left" class="l-table-edit-td"><input name="count_value" type="text" id="count_value" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算值：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_value" type="text" id="budg_value" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">说明：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">增长比例：</td>
            <td align="left" class="l-table-edit-td"><input name="grow_rate" type="text" id="grow_rate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分解比例：</td>
            <td align="left" class="l-table-edit-td"><input name="resolve_rate" type="text" id="resolve_rate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上年业务量：</td>
            <td align="left" class="l-table-edit-td"><input name="last_year_workload" type="text" id="last_year_workload" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上月结转：</td>
            <td align="left" class="l-table-edit-td"><input name="last_month_carried" type="text" id="last_month_carried" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">结转下月：</td>
            <td align="left" class="l-table-edit-td"><input name="carried_next_month" type="text" id="carried_next_month" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
