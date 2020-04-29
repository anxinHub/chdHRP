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
        struct_code:$("#struct_code").val(),
        struct_name:$("#struct_name").val(),
        is_stop:liger.get("is_stop").getValue()
        };
        ajaxJsonObjectByUrl("updateAssStructDict.do",formPara,function(responseData){
            
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
   
    function saveAssStructDict(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
	$("#struct_code").ligerTextBox({
    		
			disabled:true
		});
	 $('#is_stop').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
	        textField: 'text',
	        cancelable:true,
			width : 180
	});
		liger.get("is_stop").setValue("${is_stop}");
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
       <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top: 45px">建筑结构编码<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td" style="padding-top: 45px;"><input  value="${struct_code }" name="struct_code" type="text" id="struct_code" ltype="text" validate="{required:true,maxlength:30}" /></td>
                <td align="left" style="padding-top: 45px;"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top: 45px">建筑结构名称<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td" style="padding-top: 45px;"><input name="struct_name" value="${struct_name }" type="text" id="struct_name" ltype="text" validate="{required:true,maxlength:200}" /></td>
                <td align="left" style="padding-top: 45px;"></td>
            </tr>  
            <tr >
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top: 30px;">是&nbsp&nbsp否&nbsp&nbsp停&nbsp用&nbsp<span style="color:red">*</span>：</td>
                <td align="left" class="l-table-edit-td" style="padding-top: 30px;">
                <!-- <select name="is_stop" id="is_stop">
						<option value="0">否</option>
						<option value="1">是</option>
			    </select> -->
			    <input name="is_stop" type="text" id="is_stop"  validate="{required:true,maxlength:30}" />
			    </td>
                <td align="left" style="padding-top: 30px;"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
