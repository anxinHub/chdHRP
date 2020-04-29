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
        loadDict();
        loadForm();
        
    });  
     
    function save(){
        var formPara={
        year:$("#year").val(),
        month:$("#month").val(),
        ur_fromtype:$("#ur_fromtype").val(),
        ur_eq_group:$("#ur_eq_group").val(),
        dept_code:$("#dept_code").val(),
        oresource_code:$("#oresource_code").val(),
        price:$("#price").val(),
        unit_code:$("#unit_code").val(),
        quantity:$("#quantity").val(),
        amount:$("#amount").val(),
        remark:$("#remark").val(),
        is_input_flag:$("#is_input_flag").val(),
        sale_amount:$("#sale_amount").val(),
        add_date:$("#add_date").val(),
        add_time:$("#add_time").val(),
        add_user:$("#add_user").val(),
        update_date:$("#update_date").val(),
        update_time:$("#update_time").val(),
        update_user:$("#update_user").val(),
        status:$("#status").val(),
        submit_date:$("#submit_date").val(),
        submit_date:$("#submit_date").val(),
        submit_user:$("#submit_user").val(),
        audit_date:$("#audit_date").val(),
        audit_time:$("#audit_time").val(),
        audit_user:$("#audit_user").val(),
        cancel_date:$("#cancel_date").val(),
        cancel_time:$("#cancel_time").val(),
        cancel_user:$("#cancel_user").val()
        };
        ajaxJsonObjectByUrl("updateAssEqusedresource.do",formPara,function(responseData){
            
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
   
    function saveAssEqusedresource(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年：</td>
            <td align="left" class="l-table-edit-td"><input name="year" type="text" id="year" ltype="text" value="${year}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月：</td>
            <td align="left" class="l-table-edit-td"><input name="month" type="text" id="month" ltype="text" value="${month}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">01：机组:02：设备：</td>
            <td align="left" class="l-table-edit-td"><input name="ur_fromtype" type="text" id="ur_fromtype" ltype="text" value="${ur_fromtype}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">urEqGroup：</td>
            <td align="left" class="l-table-edit-td"><input name="ur_eq_group" type="text" id="ur_eq_group" ltype="text" value="${ur_eq_group}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">deptCode：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" value="${dept_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">代码：</td>
            <td align="left" class="l-table-edit-td"><input name="oresource_code" type="text" id="oresource_code" ltype="text" value="${oresource_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="price" type="text" id="price" ltype="text" value="${price}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">unitCode：</td>
            <td align="left" class="l-table-edit-td"><input name="unit_code" type="text" id="unit_code" ltype="text" value="${unit_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用量：</td>
            <td align="left" class="l-table-edit-td"><input name="quantity" type="text" id="quantity" ltype="text" value="${quantity}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">金额：</td>
            <td align="left" class="l-table-edit-td"><input name="amount" type="text" id="amount" ltype="text" value="${amount}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" value="${remark}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手工录入标志：</td>
            <td align="left" class="l-table-edit-td"><input name="is_input_flag" type="text" id="is_input_flag" ltype="text" value="${is_input_flag}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">销售金额：</td>
            <td align="left" class="l-table-edit-td"><input name="sale_amount" type="text" id="sale_amount" ltype="text" value="${sale_amount}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增日期：</td>
            <td align="left" class="l-table-edit-td"><input name="add_date" type="text" id="add_date" ltype="text" value="${add_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增时间：</td>
            <td align="left" class="l-table-edit-td"><input name="add_time" type="text" id="add_time" ltype="text" value="${add_time}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">新增人：</td>
            <td align="left" class="l-table-edit-td"><input name="add_user" type="text" id="add_user" ltype="text" value="${add_user}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新日期：</td>
            <td align="left" class="l-table-edit-td"><input name="update_date" type="text" id="update_date" ltype="text" value="${update_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新时间：</td>
            <td align="left" class="l-table-edit-td"><input name="update_time" type="text" id="update_time" ltype="text" value="${update_time}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新人：</td>
            <td align="left" class="l-table-edit-td"><input name="update_user" type="text" id="update_user" ltype="text" value="${update_user}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态（0:新增 1:提交 2:审核 3:作废）：</td>
            <td align="left" class="l-table-edit-td"><input name="status" type="text" id="status" ltype="text" value="${status}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">提交日期：</td>
            <td align="left" class="l-table-edit-td"><input name="submit_date" type="text" id="submit_date" ltype="text" value="${submit_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">提交时间：</td>
            <td align="left" class="l-table-edit-td"><input name="submit_date" type="text" id="submit_date" ltype="text" value="${submit_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">提交人：</td>
            <td align="left" class="l-table-edit-td"><input name="submit_user" type="text" id="submit_user" ltype="text" value="${submit_user}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核日期：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_date" type="text" id="audit_date" ltype="text" value="${audit_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核时间：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_time" type="text" id="audit_time" ltype="text" value="${audit_time}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_user" type="text" id="audit_user" ltype="text" value="${audit_user}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消日期：</td>
            <td align="left" class="l-table-edit-td"><input name="cancel_date" type="text" id="cancel_date" ltype="text" value="${cancel_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消时间：</td>
            <td align="left" class="l-table-edit-td"><input name="cancel_time" type="text" id="cancel_time" ltype="text" value="${cancel_time}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取消人：</td>
            <td align="left" class="l-table-edit-td"><input name="cancel_user" type="text" id="cancel_user" ltype="text" value="${cancel_user}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
