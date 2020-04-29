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
    	 
    	 
    	 if(parseInt($("#begin_num").val()) > parseInt($("#end_num").val())){
    		 
    		 
    		 $.ligerDialog.error('起始号码不能大于终止号码');
    		 
    		 return false;
    	 }
    	 
        var formPara={ 
        		
           pid:	"${pid}",
        		 
           type_code:liger.get("type_code").getValue(),
           
           dept_id:liger.get("dept_code").getValue().split(".")[0],
           
           dept_no:liger.get("dept_code").getValue().split(".")[1],
           
           user_id:liger.get("user_code").getValue(), 
           
           opt_date:$("#opt_date").val(),
           
           begin_num:$("#begin_num").val(),
        	   
           end_num:$("#end_num").val(),
        	   
           amount:"${amount}",
           
           amoney:$("#amoney").val(),
           
           state:1,
           
           note:$("#note").val(),
         };
        ajaxJsonObjectByUrl("updateAccPaperMain.do",formPara,function(responseData){
            
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
     function loadDict(){
         
    	 autocomplete("#type_code","../../queryAccPaperType.do?isCheck=false","id","text",true,true);
    	 
    	 liger.get("type_code").setValue("${type_code}");
         liger.get("type_code").setText("${type_name}");
    	 
    	 autocomplete("#dept_code","../../queryDeptDictNo.do?isCheck=false","id","text",true,true);
    	 
    	 liger.get("dept_code").setValue("${dept_id}.${dept_no}");
         liger.get("dept_code").setText("${dept_name}");

    	 autocomplete("#user_code","../../../sys/queryUserDict.do?isCheck=false","id","text",true,true);
    	 
    	 liger.get("user_code").setValue("${user_id}");
         liger.get("user_code").setText("${user_name}");

           
     }  
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">票据类型：</td>
                <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置科室：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置人：</td>
                <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置日期：</td>
                <td align="left" class="l-table-edit-td"><input name="opt_date" class="Wdate" id="opt_date" ltype="text" value="${opt_date}" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd'})" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">起始号码：</td>
                <td align="left" class="l-table-edit-td"><input name="begin_num" type="text" id="begin_num" ltype="text" value="${begin_num}"  disabled="disabled" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">终止号码：</td>
                <td align="left" class="l-table-edit-td"><input name="end_num" type="text" id="end_num" ltype="text" value="${end_num}"  disabled="disabled" validate="{required:true}" /></td>
                <td align="left"></td>
            </tr>  
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">购置费用：</td>
                <td align="left" class="l-table-edit-td"><input name="amoney" type="text" id="amoney" ltype="text" value="${amoney}" validate="{required:true}"/></td>
                <td align="left"></td>
            </tr> 
              <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text"  value="${note}" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
   
    </body>
</html>
