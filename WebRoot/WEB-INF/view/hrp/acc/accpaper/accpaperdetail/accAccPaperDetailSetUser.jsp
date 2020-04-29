<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript"> 
	 
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 var formData={
    			 
    			 user_id:liger.get("out_user").getValue(),
    			 
    			 out_date1:$("#begin_date").val(),
    			 
    			 paramVo:'${ParamVo}'
    	 }
    	
    	/*  $.ligerDialog.confirm('是否确认领用?', function (yes){
          	if(yes){ */
              	ajaxJsonObjectByUrl("updateAccPaperDetailUseOneReceive.do",formData,function (responseData){
              		if(responseData.state=="true"){
              			parent.query();
              		}
              	});
          	/* }
          }); */
    	 
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
   
    function saveUser(){
    	
        if($("form").valid()){
        	
            save();
            
        }
   }
    function loadDict(){
        
    	autocompleteObj({
    		
    		id:"#out_user",
    		urlStr:"../../../sys/queryEmpDict.do?isCheck=false",
    		valueField:"id",
    		textField:"text",
    		autocomplete:true,
    		initHeight:130,
    		initWidth:179
    	})
    	
    	//autocomplete("#out_user","../../../sys/queryUserDict.do?isCheck=false","id","text",true,true,);  
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领用人：</td>
                <td align="left" class="l-table-edit-td"><input name="out_user" type="text" id="out_user" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">领用日期：</td>
                <td align="left" class="l-table-edit-td"><input name="begin_date" class="Wdate" id="begin_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
