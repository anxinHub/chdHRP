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
         loadDict()//加载下拉框
        loadForm();
        
     });  
     
     function  save(){
        var formPara={
            
           check_plan_no:$("#check_plan_no").val(),
           
           check_plan_no_s:'${check_plan_no_s}',
           
           check_plan_no_g:'${check_plan_no_g}',
           
           check_plan_no_o:'${check_plan_no_o}',
            
 	       dept_id:liger.get("dept_id").getValue().split("@")[0],
           
           dept_no:liger.get("dept_id").getValue().split("@")[1],
            
           summary:$("#summary").val(),
            
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("addAssCheckD.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='dept_id']").val('');
				 $("input[name='summary']").val('');
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
   
    function saveAssCheckDGeneral(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 $("#check_plan_no").ligerTextBox({
  			disabled : true,
  			cancelable : false
  		});
    	 autocomplete("#dept_id", "../../queryDeptDict.do?isCheck=false", "id",
					"text", true, true, null, null,null);
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  width="100%">
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">任务单号：</td>
            <td align="left" class="l-table-edit-td"><input name="check_plan_no" type="text" id="check_plan_no" ltype="text" value="${check_plan_no}" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
         </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">摘要：</td>
            <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
         </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
