<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
     var dataFormat;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        $("#store_code").ligerTextBox({ disabled: true });
        
        $("#store_name").ligerTextBox({ disabled: true });
        
        $("#is_stop").ligerTextBox({ disabled: true });
        
     });  
     
     function  save(){
    	 
    	 //负责人电话
     	  var reg=/^[1][3578][0-9]{9}$/;
           if($("#mobile").val()!=null && ($("#mobile").val()!="")){
     	 
         	if(reg.test($("#mobile").val())==false){
     		  $.ligerDialog.error("负责人电话输入不合法!");
  				
     		  return;
     	  }
      }
    	 
        var formPara={
        		
        	store_id:$("#store_id").val(),
        	
           dept_id:liger.get("dept_code").getValue().split(".")[0],
           
           is_proc:$("#is_proc").val(),
            
           head_emp_id:liger.get("head_emp_code").getValue().split(".")[0],
            
           acc_emp_id:liger.get("acc_emp_code").getValue().split(".")[0],
            
           safe_emp_id:liger.get("safe_emp_code").getValue().split(".")[0],
            
           proc_emp_id:liger.get("proc_emp_code").getValue().split(".")[0],
            
           address:$("#address").val(),
           
           mobile:$("#mobile").val(),
           
           note:$("#note").val()
            
         };
        
        ajaxJsonObjectByUrl("../accstoreattr/addAccStoreAttr.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parentFrameUse().query();
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
   
    function saveAccStoreAttr(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	autocomplete("#type_name","../../sys/queryStoreTypeDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#dept_code","../../sys/queryDeptDict.do?isCheck=false","id","text",true,true,{is_stock:1});
    	autocomplete("#head_emp_code","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#acc_emp_code","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#safe_emp_code","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#proc_emp_code","../../sys/queryEmpDict.do?isCheck=false","id","text",true,true);
    	
    	$("#type_name").ligerTextBox({disabled:true});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
                <td align="left" class="l-table-edit-td"><input name="store_id" type="hidden"  id="store_id" ltype="text"  value ="${store_id }" validate="{required:true,maxlength:20}" /></td>
            </tr> 
        <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房编码：</td>
                <td align="left" class="l-table-edit-td"><input name="store_code" type="text"  id="store_code" ltype="text"  value ="${store_code }" validate="{required:true,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房分类：</td>
                <td align="left" class="l-table-edit-td"><input name="type_name" type="text"  id="type_name" disabled="disabled" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房名称：</td>
                <td align="left" class="l-table-edit-td"><input name="store_name" type="text" id="store_name" ltype="text"  value ="${store_name }"  validate="{required:true,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                <select id="is_stop" name="is_stop" style="width: 135px;" disabled="disabled">
			                		<option value="0">启用</option>
			                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">主管部门：</td>
                <td align="left" class="l-table-edit-td">
              		   <input name="dept_code" type="text"  id="dept_code" ltype="text"   validate="{required:false,maxlength:20}" />
                </td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否采购库房：</td>
                <td align="left" class="l-table-edit-td">
                <select id="is_proc" name="is_proc" style="width: 135px;">
			                		<option value="0">否</option>
			                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">负责人：</td>
                <td align="left" class="l-table-edit-td"><input name="head_emp_code" type="text"  id="head_emp_code"  value ="${head_emp_name }"   ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">负责人电话：</td>
                <td align="left" class="l-table-edit-td"><input name="mobile" type="text"  id="mobile" ltype="text"  value ="${mobile }"   validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计：</td>
                <td align="left" class="l-table-edit-td"><input name="acc_emp_code" type="text" id="acc_emp_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left" width="150"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">保管员：</td>
                <td align="left" class="l-table-edit-td"><input name="safe_emp_code" type="text" id="safe_emp_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">采购员：</td>
                <td align="left" class="l-table-edit-td"><input name="proc_emp_code" type="text" id="proc_emp_code" ltype="text" validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">地址：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><input cols="60"  name="address" type="text" id="address" ltype="text"  validate="{required:false,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td" colspan="4"><textarea rows="3" cols="60" name="note" id="note"   validate="{required:false,maxlength:50}" style="resize: none;" ></textarea></td>
                <td align="left"></td>
            </tr> 

        </table>
    </form>
   
    </body>
</html>
