<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
String path = request.getContextPath(); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/> 
    <script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     var sup_id = null;
     var sup_no = null;
     $(function (){
         loadDict()//加载下拉框 
        loadForm();
         $("#is_manager").bind("change", function () {		
        	 if(liger.get("is_manager").getValue()==1){
 	    		$("#sup_id").ligerTextBox({disabled : true});
 	    		autocomplete("#sup_id","../../sys/querySupDictDict.do?isCheck=false","id","text",true,true,'',false,'');
 	    	}
 	    	
 	    	if(liger.get("is_manager").getValue()==0){
 	    		$("#sup_id").ligerTextBox({disabled : false});
 	    		liger.get("sup_id").setValue("");
 				liger.get("sup_id").setText("");
 				autocomplete("#sup_id","../../sys/querySupDictDict.do?isCheck=false","id","text",true,true,'',false,'');
 	    	}
 	    	$("#sup_id").ligerTextBox({ width:180});
 		});
     });  
     function validateGrid() {
			//主表
			if (!liger.get("copy_code").getValue()) {
				$.ligerDialog.warn("账套不能为空");
				return false;
			}
			if(liger.get("is_manager").getValue()==0){
				if (!liger.get("sup_id").getValue()) {
					$.ligerDialog.warn("供应商不能为空");
					return false;
				}
				sup_id = liger.get("sup_id").getValue().split(".")[0];
				sup_no = liger.get("sup_id").getValue().split(".")[1];
			}
			
			return true;
     }
     function  save(){
    	 if(validateGrid()){
    		 var formPara={
    		           user_code:$("#user_code").val(),
    		           user_name:$("#user_name").val(),
    		           copy_code:liger.get("copy_code").getValue(),
    		           is_disable:liger.get("is_disable").getValue(),
    		           is_manager:liger.get("is_manager").getValue(),
    		           sup_id: sup_id,
    		           sup_no: sup_no
    		         };
    		        
    		        ajaxJsonObjectByUrl("addSysSupUser.do",formPara,function(responseData){
    		            
    		            if(responseData.state=="true"){
    						 $("input[name='user_code']").val('');
    						 $("input[name='user_name']").val('');
    						 $("input[name='sup_id']").val('');
    		                parent.query();
    		            }
    		        });
    	 }
        
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
   
    function saveSysSupUser(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        //字典下拉框
    	autocomplete("#sup_id","../../sys/querySupDictDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#copy_code","../../sys/queryCopyCodeDict.do?isCheck=false","id","text",true,true,'',true);    
    	autoCompleteByData("#is_manager", yes_or_no.Rows, "id", "text", true, true,'',false,false,'180');
    	autoCompleteByData("#is_disable", yes_or_no.Rows, "id", "text", true, true,'',false,false,'180');
    	liger.get("is_manager").setValue(0);
    	liger.get("is_disable").setValue(1);
    	$("#sup_id").ligerTextBox({ width:180});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户编码：</td>
            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户名称：</td>
            <td align="left" class="l-table-edit-td"><input name="user_name" type="text" id="user_name" ltype="text" validate="{required:true}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">账套：</td>
            <td align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">所属供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text"  /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否启用：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_disable" type="text" id="is_disable" ltype="text" />
            </td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否管理员：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_manager" type="text" id="is_manager" ltype="text" />
            </td>
            <td align="left"></td>
        </tr>    
    </table>
    </form>
   
    </body>
</html>
