<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
  <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
        
    	/*  var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
         
         var data=dialog!=null?dialog.get("data").data:"";
        
         var ParamVo =[];
         
         $(data).each(function (){					
				ParamVo.push(
				//表的主键
				this.group_id   +"@"+ 
				this.hos_id   +"@"+ 
				this.copy_code +"@"+
				this.pid   +"@"+
				this.paper_num +"@"+
				"1" +"@"+
				this.is_check +"@"+
				(isnull($("#vouch_no").val())==true?' ':$("#vouch_no").val()) +"@"+
				(isnull($("#check_money").val())==true?' ':$("#check_money").val())
				)
         }); */
         
		var formData={
    			 
        		 vouch_no:isnull($("#vouch_no").val())==true?' ':$("#vouch_no").val(),
        				 
        		 check_money:isnull($("#check_money").val())==true?' ':$("#check_money").val(),
    			 
    			 paramVo:'${paramVo}'
   	 	}
    		
       ajaxJsonObjectByUrl("updateAccPaperDetailSolaVerification.do",formData,function (responseData){
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
   
    function saveAccPayType(){
        if($("form").valid()){
            save();
        }
   }
     function loadDict(){}  
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证号：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_no" type="text" id="vouch_no" ltype="text"/></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核销金额：</td>
                <td align="left" class="l-table-edit-td"><input name="check_money" type="text" id="check_money" ltype="text"/></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
