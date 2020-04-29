<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var dataFormat;
    var ref_id = '${ref_id}';
    $(function (){
    	
        loadDict();
        
        loadForm();
    });  
    
    function  save(){
        var formPara={
        	ref_id : ref_id ,
        	
        	dept_id:liger.get("dept_code").getValue(),
                 
            cost_custom_dept_id:liger.get("cost_custom_dept_code").getValue(),
                 
            is_stop:$("#is_stop").val()
           
            
           
         };
        ajaxJsonObjectByUrl("updateCostHrpDeptRef.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveGroup(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
	     autocomplete("#dept_code","queryHrpDept.do?isCheck=false","id","text",true,true,'',false,'${dept_id}' , 180);
    	 autocomplete("#cost_custom_dept_code","queryCostCustomDept.do?isCheck=false","id","text",true,true,'',false,'${cost_custom_dept_id}' , 180);
         
    	 $("#is_stop").val('${is_stop}');
	     
     }   
    
    </script>
  
  </head>
  
   <body>
   <div align="left" >
	   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>系统科室<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text"  id="dept_code" ltype="text"  validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>自定义科室<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td"><input name="cost_custom_dept_code" type="text"  id="cost_custom_dept_code" ltype="text" validate="{required:true,maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:10px;"><b>是否停用<font color="red">*</font>:</b></td>
                <td align="left" class="l-table-edit-td">
                	<select id="is_stop" name="is_stop"  style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                				</select>
                </td>
                <td align="left"></td>
            </tr> 
   		</table>
    </form>
    </div>
    </body>
</html>