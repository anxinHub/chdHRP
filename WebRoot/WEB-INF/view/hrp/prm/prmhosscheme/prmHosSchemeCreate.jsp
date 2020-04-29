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

var dialog = frameElement.dialog;

var dialogData;//获取data参数

var dataFormat;

     $(function (){
	
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 dialogData = dialog.get('data');//获取data参数
    	 
    	 
    	// alert(JSON.stringify(dialogData))
    	 var acc_year = dialogData.acc_year;
    	 
    	 var 	acc_month = dialogData.acc_month;
    	 
    	 var 	goal_code = dialogData.goal_code;
    	 
    	 var 	hos_id = dialogData.hos_id;
    	 
    	 var 	check_hos_id = dialogData.check_hos_id;

        var formPara={
        		
        	 	acc_year : dialogData.acc_year,
        	 	
           	  	acc_month : dialogData.acc_month,
           	  	
           	  	goal_code : dialogData.goal_code,
           	  	
           	  	hos_id : dialogData.hos_id,
           	 
           	 	check_hos_id : dialogData.check_hos_id,
           	  	
	        	rbtnl:$('#wrap input[name="rbtnl"]:checked').val()
	        
         };	
        ajaxJsonObjectByUrl("createPrmHosScheme.do",formPara,function(responseData){
            
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
   
    function createHosScheme(){
        if($("form").valid()){
            save();
        }
   }

    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生成类型：</td>
                <td align="left" class="l-table-edit-td">
                <div id="wrap" >
                	<input id="rbtnl_0" type="radio" name="rbtnl" value="all"  checked="checked"  />
                		<label for="rbtnl_0">覆盖生成</label> 
                	<input id="rbtnl_0" type="radio" name="rbtnl" value="check" />
                		<label for="rbtnl_1">增量生成</label>
				</div>
                </td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
