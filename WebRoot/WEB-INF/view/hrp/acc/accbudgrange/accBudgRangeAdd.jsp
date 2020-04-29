<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>

<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	//alert("${start_day}");
        loadDict();//加载下拉框
        loadForm();  
        $("#begin_days").val(parseInt("${start_day}")+1);
     });  
     
     function  save(){
    	 var begin_day = 0;
    	 var end_days = 0;
    	 begin_day = $("#begin_days").val();
    	 if($("#validate_days").attr("checked") == true){
    		 end_days = "";
    	 }else{
    		 end_days = $("#end_days").val();
    		 if(end_days < parseInt(begin_day)){
    			 $.ligerDialog.error('终止天数必须大于或等于起始天数');
    			 return;
    		 }
    	 }
    	 
        var formPara={
           range_id:'', 
           begin_days:begin_day,           
           end_days:end_days,           
           range_pro:$("#range_pro").val(),           
           note:$("#note").val()           
        };
        
        ajaxJsonObjectByUrl("addAccBudgRange.do?isCheck=true",formPara,function(responseData){          
            if(responseData.state=="true"){
				 $("input[name='end_days']").val('');
				 $("input[name='range_pro']").val('');
				 $("input[name='note']").val('');
				 if(end_days!=""){
					 $("input[name='begin_days']").val(parseInt(end_days)+1);
				 }
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
   
    function saveAccBudgRange(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 $("#range_pro").ligerSpinner({ height: 20});
    	 $("#begin_days").ligerSpinner({ height: 20,type :'int',disabled:true});  
    	 $("#end_days").ligerSpinner({ height: 20,type :'int'}); 

     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">区间描述：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">起始天数：</td>
                <td align="left" class="l-table-edit-td"><input name="begin_days" type="text" disabled="disabled" id="begin_days" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">终止天数：</td>
                <td align="left" class="l-table-edit-td">
                <input name="end_days" type="text" id="end_days" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left"> <input name="validate_days" type="checkbox" id="validate_days"   />正无穷大</td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计提比例：</td>
                <td align="left" class="l-table-edit-td"><input name="range_pro" type="text" id="range_pro" ltype="text" validate="{required:true,maxlength:20}" />
                </td>
                <td align="left">%</td>
            </tr> 
            

        </table>
    </form>
   
    </body>
</html>
