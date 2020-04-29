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
         loadDict()//加载下拉框
        loadForm(); 
         
     });   
     
     function  save(){
        var formPara={
            
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val(),
           supper_code:$("#supper_code").val(),
           is_last:liger.get("is_last").getValue(),
           is_stop:liger.get("is_stop").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addAssTypeSixEight.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='type_code']").val('');
				 $("input[name='type_name']").val('');
				 $("input[name='is_stop']").val('');
				 $("input[name='supper_code']").val('');
				 $("input[name='is_last']").val('');
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
   
    function saveAssUsageDict(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
                $('#is_stop').ligerComboBox({
					data:[{id:1,text:'是'},{id:0,text:'否'}],
					valueField: 'id',
		            textField: 'text',
					cancelable:true,
					width : 180
				})
				$('#is_last').ligerComboBox({
					data:[{id:1,text:'是'},{id:0,text:'否'}],
					valueField: 'id',
		            textField: 'text',
					cancelable:true,
					width : 180
				})
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">68分类编码<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">68分类名称<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td"><input name="type_name" type="text" id="type_name" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
              <td align="right" class="l-table-edit-td"  style="padding-left:20px;">上级编码<span style="color:red">*</span>：</td>
              <td align="left" class="l-table-edit-td"><input name="supper_code" type="text" id="supper_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                
              <td align="left"></td>
            </tr> 
                 <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否末级<span style="color:red">*</span>：</td>
               <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select> -->
                	<input name="is_last" type="text" id="is_last" validate="{required:true,maxlength:20}" />
                </td>

                </td>
                <td align="left"></td>
            </tr> 
            <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td">
                	<!-- <select id="is_stop" name="is_stop" style="width: 135px;">
			               <option value="0">否</option>
			               <option value="1">是</option>
                	</select> -->
                	<input name="is_stop" type="text" id="is_stop" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
