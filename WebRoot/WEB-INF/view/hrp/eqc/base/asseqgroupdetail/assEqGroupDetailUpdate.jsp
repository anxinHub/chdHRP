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
        eq_unit_code:$("#eq_unit_code").val(),
        eo_eq_group :$("#eo_eq_group ").val(),
        main_flag:$("#main_flag").val(),
        income_rate:$("#income_rate").val(),
        expend_rate:$("#expend_rate").val(),
        from_date:$("#from_date").val(),
        to_date:$("#to_date").val(),
        update_date:$("#update_date").val(),
        update_time:$("#update_time").val(),
        update_userdr:$("#update_userdr").val(),
        invalid_flag:$("#invalid_flag").val(),
        remark:$("#remark").val()
        };
        ajaxJsonObjectByUrl("updateAssEqgroupdetail.do",formPara,function(responseData){
            
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
   
    function saveAssEqgroupdetail(){
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量编码（HOS_UNIT）：</td>
            <td align="left" class="l-table-edit-td"><input name="eq_unit_code" type="text" id="eq_unit_code" ltype="text" value="${eq_unit_code}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="eo_eq_group " type="text" id="eo_eq_group" ltype="text" value="${eo_eq_group}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新日期：</td>
            <td align="left" class="l-table-edit-td"><input name="main_flag" type="text" id="main_flag" ltype="text" value="${main_flag}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="income_rate" type="text" id="income_rate" ltype="text" value="${income_rate}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">无效标志：</td>
            <td align="left" class="l-table-edit-td"><input name="expend_rate" type="text" id="expend_rate" ltype="text" value="${expend_rate}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glRemark：</td>
            <td align="left" class="l-table-edit-td"><input name="from_date" type="text" id="from_date" ltype="text" value="${from_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">grTodate：</td>
            <td align="left" class="l-table-edit-td"><input name="to_date" type="text" id="to_date" ltype="text" value="${to_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glUpdatedate：</td>
            <td align="left" class="l-table-edit-td"><input name="update_date" type="text" id="update_date" ltype="text" value="${update_date}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glUpdatetime：</td>
            <td align="left" class="l-table-edit-td"><input name="update_time" type="text" id="update_time" ltype="text" value="${update_time}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glUpdateuserdr：</td>
            <td align="left" class="l-table-edit-td"><input name="update_userdr" type="text" id="update_userdr" ltype="text" value="${update_userdr}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glInvalidflag：</td>
            <td align="left" class="l-table-edit-td"><input name="invalid_flag" type="text" id="invalid_flag" ltype="text" value="${invalid_flag}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glSort：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" value="${remark}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
