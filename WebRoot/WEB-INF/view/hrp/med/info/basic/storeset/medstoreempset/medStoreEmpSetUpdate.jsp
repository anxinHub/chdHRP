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
        loadDict();
        loadForm();
         
    });  
     
    function save(){
    	 
    	
        var formPara={
        		old_store:'${store_id}',
        		old_emp:'${emp_id}',
        		store_id : liger.get("store_id").getValue().split(",")[0],
                emp_id :liger.get("emp_id").getValue()
        };
        ajaxJsonObjectByUrl("updateMedStoreEmp.do?isCheck=true",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.query();
                parent.$.ligerDialog.close();
                parent.$(".l-dialog,.l-window-mask").remove();
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
   
    function saveMedStoreSet(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
        autocomplete("#store_id","../../../../queryMedStore.do?isCheck=false","id","text",true,true );
        liger.get("store_id").setValue("${store_id}");
        liger.get("store_id").setText("${store_code}  ${store_name}");
    	 
    	autocomplete("#emp_id","../../../../queryMedStockEmp.do?isCheck=false","id","text",true,true,'',false,'${emp_id}');   
 
    	 
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" > 
        
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>仓库<font color ="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>采购员<font color ="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
			
        </table>
    </form>
    </body>
</html>
