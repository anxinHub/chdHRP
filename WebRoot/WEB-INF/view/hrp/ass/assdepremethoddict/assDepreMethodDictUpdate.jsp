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
        ass_depre_code:$("#ass_depre_code").val(),
        ass_depre_name:$("#ass_depre_name").val(),
        ass_depre_define:$("#ass_depre_define").val(),
      
        is_stop:$("#is_stop").val()
        };
        ajaxJsonObjectByUrl("updateAssDepreMethodDict.do",formPara,function(responseData){
            
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
   
    function saveAssDepreMethodDict(){
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
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧方法编码：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_depre_code" type="text" id="ass_depre_code" disabled="disabled" ltype="text"  value="${ass_depre_code}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧方法名称：</td>
                <td align="left" class="l-table-edit-td"><input name="ass_depre_name" type="text" id="ass_depre_name" ltype="text"  value="${ass_depre_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧方法描述：</td>
                <td align="left" class="l-table-edit-td">
                <textarea name="ass_depre_define"  id="ass_depre_define" cols="100" rows="4" class="l-textarea" style="width:400px" >${ass_depre_define}</textarea>
                </td>
                <td align="left"></td>
            </tr> 

         <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否停用<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop" style="width: 135px;">
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
