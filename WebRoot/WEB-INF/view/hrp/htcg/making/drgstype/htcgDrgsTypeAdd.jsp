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
         loadDict()//加载下拉框
         loadForm();
        
     });  
     function  save(){
        var formPara={
           drgs_type_code:$("#drgs_type_code").val(),
           drgs_type_name:$("#drgs_type_name").val(),
           drgs_type_note:$("#drgs_type_note").val()
         };
        ajaxJsonObjectByUrl("addHtcgDrgsType.do",formPara,function(responseData){
            if(responseData.state=="true"){
				 $("input[name='drgs_type_code']").val('');
				 $("input[name='drgs_type_name']").val('');
				 $("textarea[name='drgs_type_note']").val('');
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
   
    function saveDrgsType(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种分类编号：</td>
                <td align="left" class="l-table-edit-td"><input name="drgs_type_code" type="text" id="drgs_type_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种分类名称：</td>
                <td align="left" class="l-table-edit-td"><input name="drgs_type_name" type="text" id="drgs_type_name" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">病种分类描述：</td>
                <td align="left" class="l-table-edit-td"><textarea name="drgs_type_note"  id="drgs_type_note"  cols="30" rows="4" ></textarea></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>