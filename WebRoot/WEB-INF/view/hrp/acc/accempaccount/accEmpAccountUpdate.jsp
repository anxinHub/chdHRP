<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
        liger.get("dept_code").setValue('${dept_id}'+'.'+'${dept_no}');
        liger.get("dept_code").setText('${dept_code}'+' '+'${dept_name}');
        
        liger.get("emp_code").setValue('${emp_id}'+'.'+'${emp_no}');
        liger.get("emp_code").setText('${emp_code}'+' '+'${emp_name}');
        
        liger.get("type_id").setValue('${type_id}');
        liger.get("type_id").setText('${type_name}');
        
		$("#dept_code").ligerTextBox({ disabled: true});
        
        $("#emp_code").ligerTextBox({ disabled: true});
        
        $("#id_number").ligerTextBox({ disabled: true});
        
		$("#is_stop").ligerTextBox({cancelable: false});
        
        $("#is_bank_same").ligerTextBox({cancelable: false});
        
        $("#is_city_same").ligerTextBox({cancelable: false});
        
        var is_stop = '${is_stop}';
        if(is_stop == '1'){
        	liger.get("is_stop").setValue('1');
            liger.get("is_stop").setText('是');
        }else{
        	liger.get("is_stop").setValue('0');
            liger.get("is_stop").setText('否');
        }
        
        var is_bank_same = '${is_bank_same}';
        if(is_bank_same == '1'){
        	liger.get("is_bank_same").setValue('1');
            liger.get("is_bank_same").setText('是');
        }else{
        	liger.get("is_bank_same").setValue('2');
            liger.get("is_bank_same").setText('否');
        }
        
		var is_city_same = '${is_city_same}';
        if(is_city_same == '1'){
        	liger.get("is_city_same").setValue('1');
            liger.get("is_city_same").setText('是');
        }else{
        	liger.get("is_city_same").setValue('2');
            liger.get("is_city_same").setText('否');
        }
        
    });  
     
    function save(){
    	
    	var formPara={
        		
    				account_id:'${account_id}',
        		
    	           emp_id:liger.get("emp_code").getValue().split(".")[0],
    	            
    	           emp_no:liger.get("emp_code").getValue().split(".")[1],
    	           
    	           account_name:$("#account_name").val(),
    	           
    	           account_code:$("#account_code").val(),
    	           
    	           type_id:liger.get("type_id").getValue(),
    	           
    	           is_stop:$("#is_stop").val(),
    	           
    	           emp_bank:$("#emp_bank").val(),
    	           
    	           emp_arear:$("#emp_arear").val(),
    	           
    	           is_bank_same:$("#is_bank_same").val(),
    	           
    	           is_city_same:$("#is_city_same").val(),
    	           
    	           note:$("#note").val()
    	            
    	         };
    	
        ajaxJsonObjectByUrl("updateAccEmpAccount.do?isCheck=false",formPara,function(responseData){
            
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
   
    function saveAccBank(){
        if($("form").valid()){
            save();
        }
    }
	/* function changeEmpKind(){
    	
    	var dept_code = liger.get("dept_code").getValue();
    	
    	if(dept_code == ""){
    		
    		return;
    		
    	}
    	var para = {dept_code:dept_code};
    	
    	$("#emp_code").val("");
    	
    	autocomplete("#emp_code","../queryEmpDict.do?isCheck=false", "id", "text",
				true, true, para);
    	
    	$("#emp_code").ligerTextBox({ width: 180 });
    	
    	
    }
    
    function changeEmp(){
    	
    	var formPara={
         		
 	           emp_id:liger.get("emp_code").getValue().split(".")[0]
 	            
 	         };

 	 	if(liger.get("emp_code").getValue()!= ""){
 	 		
 	 		ajaxJsonObjectByUrl("../accempattr/queryAccEmpNumber.do?isCheck=false",formPara,function(responseData){
 	 	         
 	 			$("#account_name").val(responseData.Rows[0].emp_name);
 	 			
 	 			$("#is_number").val(responseData.Rows[0].id_number);
 	 	         
 	 	     });
 	 	}
    } */
    
    function loadDict(){
    	var parm={
    			dept_id:"0",
    			dept_no:"0"
    	};
            //字典下拉框
		autocomplete("#dept_code","../../sys/queryDeptDict.do?isCheck=false","id","text",true,true);
            
    	autocomplete("#emp_code","../queryEmpDict.do?isCheck=false","id","text",true,true,parm);
    	
    	autocomplete("#type_id","../queryWageType.do?isCheck=false","id","text",true,true);
    	
    	
     }  
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
         <table cellpadding="0" cellspacing="0" class="l-table-edit" >

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门名称：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code"  ltype="text"  disabled="disabled"/></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工姓名：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" disabled="disabled" /></td>
                <td align="left"></td>
            </tr> 
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">账户姓名：</td>
                <td align="left" class="l-table-edit-td"><input name="account_name" type="text" id="account_name" ltype="text" value="${account_name }" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
          
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">身份证号：</td>
                <td align="left" class="l-table-edit-td"><input name="is_number" type="text" id="is_number" value="${is_number }" ltype="text" validate="{required:false,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">工资账号：</td>
                <td align="left" class="l-table-edit-td"><input name="account_code" type="text" id="account_code" value="${account_code }" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">账户类别：</td>
                <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" value="${type_id }"  validate="{required:true,maxlength:18}" /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
                <select name="is_stop" id="is_stop">
                <option value="0">否</option>
                <option value="1">是</option>
                </select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开户银行：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_bank" type="text" id="emp_bank" ltype="text" value="${emp_bank }" validate="{required:false,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行地区：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_arear" type="text" id="emp_arear" ltype="text" value="${emp_arear }"  validate="{required:false,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否同行：</td>
                <td align="left" class="l-table-edit-td">
                <select id="is_bank_same" name = "is_bank_same">
                <!-- <option value="2">否</option> -->
                <option value="1">是</option>
                </select>
                </td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否同城：</td>
                <td align="left" class="l-table-edit-td">
                <select id="is_city_same" name = "is_city_same">
                <!-- <option value="2">否</option> -->
                <option value="1">是</option>
                </select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" value="${note }" validate="{required:false,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>

        </table>
    </form>
    </body>
</html>
