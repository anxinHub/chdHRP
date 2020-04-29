<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
     var dataFormat;
     $(function (){

        loadForm();
        
     });  
     
     function  save(){
    	 
        var formPara={
     	       
        	year_month:$("#year_month").val(),
        	
        	sub_scheme_seq_no:$("#sub_scheme_seq_no").val(),
        		
	        rbtnl:$('#wrap input[name="rbtnl"]:checked').val()
	        
         };
        
        ajaxJsonObjectByUrl("createHpmSubSchemeConf.do",formPara,function(responseData){
            
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
   
    function saveSchemeApply(){
        if($("form").valid()){
            save();
        }
   }

    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<input type="hidden" name="year_month" id="year_month" value="${year_month}"> <input type="hidden" name="sub_scheme_seq_no" id="sub_scheme_seq_no"
			value="${sub_scheme_seq_no}">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否生成该核算年月：</td>
				<td align="left" class="l-table-edit-td">
					<div id="wrap">
						<input id="rbtnl_0" type="radio" name="rbtnl" value="no" checked="checked" /> <label for="rbtnl_0">否</label> <input id="rbtnl_0" type="radio"
							name="rbtnl" value="yes" /> <label for="rbtnl_1">是</label>
					</div>
				</td>
				<td align="left"></td>
			</tr>
		</table>
	</form>

</body>
</html>
