<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
	
     $(function (){
        loadForm();
     });  
     
     function  save(){
    	 if($("#create_date1").val() == "" || $("#create_date2").val() == ""){
    		 $.ligerDialog.error("请选填对账日期");
    		 return;
    	 }
    	
        var formPara={
           subj_code:'${subj_code}',
           create_date1:$("#create_date1").val(),
           create_date2:$("#create_date2").val()
         };
        
        ajaxJsonObjectByUrl("batchCancelAccTellVeri.do",formPara,function(responseData){
            if(responseData.state=="true"){
				$("input[name='create_date1']").val('');
				$("input[name='create_date2']").val('');
				parent.checkAll();
                frameElement.dialog.close();
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
   
    function saveAccUnitBankCheck(){
        if($("form").valid()){
            save();
        }
   }
    
    </script>
  <script type="text/javascript">
  	
  </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   <font color="red">科目：<font id="subj_name" color="red">${subj_name }</font> </font>
   <input type="hidden" id="subj_id" name="subj_id" value="${subj_id}"/>
   <center>
         <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">对账日期：</td>
                <td align="left" class="l-table-edit-td"  style="padding-top:10px;"><input class="Wdate" name="create_date1" type="text" id="create_date1" ltype="text" validate="{required:true,maxlength:20}"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
                <td align="left"></td>
              	
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">至：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input class="Wdate" name="create_date2" type="text" id="create_date2" ltype="text" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
                <td align="left"></td>
            </tr>
           </table>
   </center>
    </form>
   
    </body>
</html>
