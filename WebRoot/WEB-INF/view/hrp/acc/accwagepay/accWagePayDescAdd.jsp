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
     var dataFormat;
     $(function (){
    	 
         loadDict()//加载下拉框
         
         loadForm();
        
     });  
     
     function  save(){
        var formPara={
           desc_id:'${desc_id}',
            
           wage_code:'${wage_code}',
            
           acc_year:'${acc_year}',
            
           acc_month:'${acc_month}',
            
           kined_code:liger.get("kined_code").getValue(),
            
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("addOrUpdateAccWagePayDesc.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				 $("input[name='desc_id']").val('');
				 $("input[name='wage_code']").val('');
				 $("input[name='acc_year']").val('');
				 $("input[name='acc_month']").val('');
				 $("input[name='kined_code']").val('');
				 $("#note").val('');
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
   
    function saveAccWagePayDesc(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#kined_code","../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
         
    	 liger.get("kined_code").setValue('${kined_code}');
         
         liger.get("kined_code").setText('${kined_name}');
         
         $("#note").val('${note}');
            
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
            <td align="left" class="l-table-edit-td"><input name="kined_code" type="text" id="kined_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
        <tr>   
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-bottom: 230px">工资说明：</td>
            <td align="left" class="l-table-edit-td"><textarea style="width: 400px;height: 260px;resize: none;" name="note" type="text" id="note"></textarea></td>
            <td align="left"></td>
        </tr> 
    </table>
    </form>
   
    </body>
</html>
