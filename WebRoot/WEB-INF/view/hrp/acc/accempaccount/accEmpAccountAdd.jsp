<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
     var dataFormat;
     var a=1;
     $(function (){
        loadDict();//加载下拉框
        loadForm();
        
        $("#dept_code").change(function(){
        	var dept_id = liger.get("dept_code").getValue().split(".")[0];
        	var dept_no = liger.get("dept_code").getValue().split(".")[1];
        	if(liger.get("dept_code").getValue() ==""){
        		return;
        	}else{
        		if(a == 0){
	        		liger.get("emp_code").setValue(0);
	        		$("#account_name").val("");
	 	 			$("#is_number").val("");
        		}
        		var para = {dept_id:dept_id,dept_no:dept_no};
            	autocomplete("#emp_code","../queryEmpDict.do?isCheck=false", "id", "text", true, true, para, false, '', 180);
        	}
        	a=0;
		});
		
        $("#emp_code").unbind();
        $("#emp_code").change(function(){
        	var formPara={
         	    	emp_id:liger.get("emp_code").getValue().split(".")[0]
         	    };
         	 	if(liger.get("emp_code").getValue()!= ""){
         	 		if(a==1){
	         	 		ajaxJsonObjectByUrl("../accempattr/queryDeptByEmpid.do?isCheck=false",formPara,function(responseData){
	         	 			liger.get("dept_code").setValue(responseData.Rows[0].dept_id);
	         	 			liger.get("dept_code").setText(responseData.Rows[0].dept_name);
	         	 		});
         	 		}
       	 			ajaxJsonObjectByUrl("../accempattr/queryAccEmpNumber.do?isCheck=false",formPara,function(responseData){
       	 				if(responseData.Total != 0){
            	 			$("#account_name").val(responseData.Rows[0].emp_name);
      	     	 			$("#is_number").val(responseData.Rows[0].id_number);
  	   	 				}
           	 	     });
         	 		
         	 	}
		});
        
     });  
     
     
     function  save(){
        var formPara={
           account_id:'',
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
        
        ajaxJsonObjectByUrl("addAccEmpAccount.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
	 function loadForm(){
	    $.metadata.setType("attr", "validate");
	     var v = $("form").validate({
	         errorPlacement: function (lable, element){
	             if (element.hasClass("l-textarea")){
	                 element.ligerTip({ content: lable.html(), target: element[0] }); 
	             }else if (element.hasClass("l-text-field")){
	                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
	             }else{
	                 lable.appendTo(element.parents("td:first").next("td"));
	             }
	         },
	         success: function (lable){
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function () {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         }
	     });
	     $("form").ligerForm();
	 }       
   
    function saveAccEmpAccount(){
    	 if($("form").valid()){
             save();
         }
    }
    
    function loadDict(){
    	//字典下拉框
    	var parm={
    		dept_id:"0",
    		dept_no:"0"
    	};
    	autocomplete("#dept_code","../../sys/queryDeptDict.do?isCheck=false", "id", "text", true, true, '', false, '', 180);
    	autocomplete("#emp_code","../queryAllEmpDict.do?isCheck=false", "id", "text", true, true, '', false, '', 180);
    	autocomplete("#type_id","../queryWageType.do?isCheck=false", "id", "text", true, true, '', false, '', 180);
    	
    	$("#dept_code").ligerTextBox({ width: 180 });
    	$("#emp_code").ligerTextBox({ width: 180 });
    	$("#account_name").ligerTextBox({ width: 180 });
    	$("#is_number").ligerTextBox({ width: 180 });
    	$("#account_code").ligerTextBox({ width: 180 });
    	$("#type_id").ligerTextBox({ width: 180 });
    	$("#is_stop").ligerTextBox({ width: 180 });
		$("#is_stop").ligerTextBox({cancelable: false});
		$("#emp_bank").ligerTextBox({ width: 180 });
		$("#emp_arear").ligerTextBox({ width: 180 });
		$("#note").ligerTextBox({ width: 180 });
		$("#is_bank_same").ligerTextBox({ width: 180 });
        $("#is_bank_same").ligerTextBox({cancelable: false});
        /* liger.get("is_bank_same").setValue('1');
        liger.get("is_bank_same").setText('是'); */
        $("#is_city_same").ligerTextBox({ width: 180 });
        $("#is_city_same").ligerTextBox({cancelable: false});
        /* liger.get("is_city_same").setValue('1');
        liger.get("is_city_same").setText('是'); */
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>部门名称：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>职工姓名：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>账户姓名：</td>
                <td align="left" class="l-table-edit-td"><input name="account_name" type="text" id="account_name" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">身份证号：</td>
                <td align="left" class="l-table-edit-td"><input name="is_number" type="text" id="is_number" ltype="text" validate="{required:false,maxlength:20}" disabled="disabled"/></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>工资账号：</td>
                <td align="left" class="l-table-edit-td"><input name="account_code" type="text" id="account_code" ltype="text" validate="{required:true,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>账户类别：</td>
                <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true,maxlength:18}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b><font color="red">*</font></b>是否停用：</td>
                <td align="left" class="l-table-edit-td">
	                <select id="is_stop" name = "is_stop">
		                <option value="0">否</option>
		                <option value="1">是</option>
	                </select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">开户银行：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_bank" type="text" id="emp_bank" ltype="text" validate="{required:false,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">银行地区：</td>
                <td align="left" class="l-table-edit-td"><input name="emp_arear" type="text" id="emp_arear" ltype="text" validate="{required:false,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否同行：</td>
                <td align="left" class="l-table-edit-td">
	                <select id="is_bank_same" name = "is_bank_same">
	                	<!-- <option value="0">否</option> -->
	                	<option value="1">是</option>
	                </select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否同城：</td>
                <td align="left" class="l-table-edit-td">
	                <select id="is_city_same" name = "is_city_same">
	                	<option value="1">是</option>
	                </select>
                </td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
                <td align="left" class="l-table-edit-td"><input name="note" type="text" id="note" ltype="text" validate="{required:false,maxlength:50}" /></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
    </body>
</html>
