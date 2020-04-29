<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
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
        acc_year:$("#acc_year").val(),
        acc_month:$("#acc_month").val(),
        goal_code:$("#goal_code").val(),
        kpi_code:$("#kpi_code").val(),
        check_hos_id:$("#check_hos_id").val(),
        super_kpi_code:$("#super_kpi_code").val(),
        order_no:$("#order_no").val(),
        level:$("#level").val(),
        is_last:$("#is_last").val(),
        ratio:$("#ratio").val(),
        goal_value:$("#goal_value").val(),
        grade_meth_code:$("#grade_meth_code").val(),
        method_code:$("#method_code").val(),
        formula_code:$("#formula_code").val(),
        fun_code:$("#fun_code").val()
        };
        ajaxJsonObjectByUrl("updatePrmHosScheme.do",formPara,function(responseData){
            
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
   
    function savePrmHosScheme(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_year" type="text" id="acc_year" ltype="text"  value="${acc_year}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月份：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_month" type="text" id="acc_month" ltype="text"  value="${acc_month}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标编码：</td>
                <td align="left" class="l-table-edit-td"><input name="goal_code" type="text" id="goal_code" ltype="text"  value="${goal_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">指标编码：</td>
                <td align="left" class="l-table-edit-td"><input name="kpi_code" type="text" id="kpi_code" ltype="text"  value="${kpi_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考核医院：</td>
                <td align="left" class="l-table-edit-td"><input name="check_hos_id" type="text" id="check_hos_id" ltype="text"  value="${check_hos_id}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上级指标编码：</td>
                <td align="left" class="l-table-edit-td"><input name="super_kpi_code" type="text" id="super_kpi_code" ltype="text"  value="${super_kpi_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">相当于rowid：</td>
                <td align="left" class="l-table-edit-td"><input name="order_no" type="text" id="order_no" ltype="text"  value="${order_no}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用于补空格：</td>
                <td align="left" class="l-table-edit-td"><input name="level" type="text" id="level" ltype="text"  value="${level}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">1:末级 0:非末级：</td>
                <td align="left" class="l-table-edit-td"><input name="is_last" type="text" id="is_last" ltype="text"  value="${is_last}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">权重：</td>
                <td align="left" class="l-table-edit-td"><input name="ratio" type="text" id="ratio" ltype="text"  value="${ratio}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">目标值：</td>
                <td align="left" class="l-table-edit-td"><input name="goal_value" type="text" id="goal_value" ltype="text"  value="${goal_value}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">评分方法：</td>
                <td align="left" class="l-table-edit-td"><input name="grade_meth_code" type="text" id="grade_meth_code" ltype="text"  value="${grade_meth_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">01:手工录入 02:计算公式 03:取值函数：</td>
                <td align="left" class="l-table-edit-td"><input name="method_code" type="text" id="method_code" ltype="text"  value="${method_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值公式：</td>
                <td align="left" class="l-table-edit-td"><input name="formula_code" type="text" id="formula_code" ltype="text"  value="${formula_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值函数：</td>
                <td align="left" class="l-table-edit-td"><input name="fun_code" type="text" id="fun_code" ltype="text"  value="${fun_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
