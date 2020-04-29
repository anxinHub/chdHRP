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
 
        var formPara={ 
        		
           ei_code:$("#ei_code").val(),
           
           ei_date:$("#ei_date").val(),
           
           check_code:$("#check_code").val(), 
           
           ei_money:$("#ei_money").val(),
           
           reimburse_man:liger.get("reimburse_man").getValue().split(".")[0],
        	   
           reimburse_date:$("#reimburse_date").val(),
         };
        ajaxJsonObjectByUrl("addAccEInvoice.do",formPara,function(responseData){
            
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
   
    function saveAccEInvoice(){
        if($("form").valid()){
            save();
        }
   	}
    
   	function loadDict(){
   		autocomplete("#reimburse_man", "../../queryEmpDictById.do?isCheck=false",  "id", "text", true, true, "", false);
   	}  
</script>
  
</head>
<body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">发票号码：</td>
                <td align="left" class="l-table-edit-td"><input name="ei_code" type="text" id="ei_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开票日期：</td>
               <td align="left" class="l-table-edit-td"><input name="ei_date" class="Wdate" id="ei_date" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">检验码：</td>
                <td align="left" class="l-table-edit-td"><input name="check_code" type="text" id="check_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开据金额：</td>
                <td align="left" class="l-table-edit-td"><input name="ei_money" type="text" id="ei_money" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销人：</td>
                <td align="left" class="l-table-edit-td"><input name="reimburse_man" type="text" id="reimburse_man" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">报销日期：</td>
                <td align="left" class="l-table-edit-td"><input name="reimburse_date" class="Wdate" id="reimburse_date" ltype="text" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   
</body>
</html>
