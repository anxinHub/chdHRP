<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />

<script type="text/javascript">
	
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 var emp_item = liger.get("emp_id").getValue();
    	 
        var formPara={
        		
          emp_id:emp_item.split(".")[0],
                
           emp_no:emp_item.split(".")[1],
           
           emp_code:emp_item.split(".")[2],
           
           emp_name:$("#emp_id").val(),

           title_code:liger.get("title_code").getValue(),
           
           emp_kind_code:liger.get("emp_kind_code").getValue(),
            
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("addCostEmpAttr.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				
				
				 $("input[name='emp_id']").val('');
				 $("input[name='emp_no']").val('');
				 $("input[name='title_code']").val('');
				 $("input[name='emp_kind_code']").val('');
				 $("input[name='emp_code']").val('');
				 $("input[name='emp_name']").val('');
				 $("input[name='note']").val('');
				
				
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
   
    function saveCostEmpAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#emp_kind_code","../queryEmpTypeArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#title_code","../queryEmpTitleArrt.do?isCheck=false","id","text",true,true);
    	autocomplete("#emp_id","../queryCostEmpDict.do?isCheck=false","id","text",true,true);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工ID：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职称编码：</td>
              <!-- 2016/10/27 lxj
              		修改:去掉必填
               -->
                <td align="left" class="l-table-edit-td"><input name="title_code" type="text" id="title_code" ltype="text" validate="{maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分类编码：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_kind_code" type="text" id="emp_kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{maxlength:100}" /></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
