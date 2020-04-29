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
        bus_type_code:$("#bus_type_code").val(),
        bus_type_name:$("#bus_type_name").val(),
      
        in_out_type:$("#in_out_type").val(),
        is_stop:$("#is_stop").val()
        
        };
        ajaxJsonObjectByUrl("updateAssBusTypeDict.do",formPara,function(responseData){
            
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
   
    function saveAssBusTypeDict(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
		//是否停用	
    	$("#is_stop").ligerComboBox({
    		
			width : 160
		});
    	
    	liger.get("is_stop").setValue("${is_stop}"); 
    	
		//类型属性	
    	$("#in_out_type").ligerComboBox({
    		
			width : 160
		});
    	
    	liger.get("in_out_type").setValue("${in_out_type}"); 
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类别编码：</td>
                <td align="left" class="l-table-edit-td"><input name="bus_type_code" type="text" id="bus_type_code" ltype="text"  value="${bus_type_code}" validate="{required:true,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">业务类别名称：</td>
                <td align="left" class="l-table-edit-td"><input name="bus_type_name" type="text" id="bus_type_name" ltype="text"  value="${bus_type_name}" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>类型属性<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="in_out_type" name="in_out_type" style="width: 135px;">
			               <option value="0">增加</option>
			               <option value="1">减少</option>
                	</select>
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
