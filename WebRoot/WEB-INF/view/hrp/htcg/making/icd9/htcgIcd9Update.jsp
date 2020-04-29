<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
    <script type="text/javascript">
    var dataFormat;
    $(function (){
        loadDict();
        loadForm();
    });  
     
    function save(){
        var formPara={
        icd9_code:$("#icd9_code").val(),
        icd9_name:$("#icd9_name").val(),
        icd9_note:$("#icd9_note").val()
        };
        ajaxJsonObjectByUrl("updateHtcgIcd9.do",formPara,function(responseData){
            
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
   
    function saveIcd9(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        $("#icd9_code").ligerTextBox({disabled:true});   
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手术编码：</td>
                <td align="left" class="l-table-edit-td"><input name="icd9_code" type="text" id="icd9_code" ltype="text"  value="${icd9_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手术名称：</td>
                <td align="left" class="l-table-edit-td"><input name="icd9_name" type="text" id="icd9_name" ltype="text"  value="${icd9_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">手术描述：</td>
                <td align="left" class="l-table-edit-td"><textarea name="icd9_note"  id="icd9_note"  cols="30" rows="4">${icd9_note }</textarea></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>