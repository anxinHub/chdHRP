<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
    
     var dataFormat;
     
     var is_audit = '${ is_audit}';
     
     $(function (){
    	 
        loadDict();
        
        loadForm();
        
        $("#target_code").ligerTextBox({ disabled:true  });
        
     });  
     
     function  save(){
        var formPara={
            
            
           acct_year:$("#acct_year").val(),
            
           acct_month:$("#acct_month").val(),
            
           target_code:liger.get("target_code").getValue(),
            
           target_value:$("#target_value").val()
            
         };
        
        ajaxJsonObjectByUrl("updateHpmHospTargetData.do",formPara,function(responseData){
            
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
   
    function saveHospTargetData(){
    	 if(is_audit == 1){
         	return false;
         }
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){

    	var param={
    			target_nature:'01',
    			method_code:'01'
    	}
    	autocomplete("#target_code","../queryTargetMethod.do?isCheck=false","id","text",true,true,param);
  
        liger.get("target_code").setValue("${target_code}");
        
        liger.get("target_code").setText("${target_name}");

        if(is_audit == 1){
        	$("#target_value").ligerTextBox({disabled: true });
        }
     } 

    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 30px; padding-top: 20px">核算年度：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px"><input name="acct_year" class="Wdate" type="text" id="acct_year" disabled="disabled"
					ltype="text" value="${ acct_year}" validate="{required:true,maxlength:20}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy'})" /></td>
				<td align="left" style="padding-top: 20px"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 30px; padding-top: 20px">核算月份：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px"><input name="acct_month" class="Wdate" type="text" id="acct_month"
					disabled="disabled" ltype="text" value="${ acct_month}" validate="{required:true,maxlength:20}"
					onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'MM'})" /></td>
				<td align="left" style="padding-top: 20px"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 30px; padding-top: 20px">指标：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px"><input name="target_code" type="text" id="target_code" disabled="disabled"
					ltype="text" validate="{required:true,maxlength:20}" /></td>
				<td align="left" style="padding-top: 20px"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 30px; padding-top: 20px">指标值：</td>
				<td align="left" class="l-table-edit-td" style="padding-top: 20px"><input name="target_value" type="text" id="target_value" ltype="text"
					value="${ target_value}" validate="{required:true,maxlength:20}" /></td>
				<td align="left" style="padding-top: 20px"></td>
			</tr>
		</table>
	</form>

</body>
</html>
