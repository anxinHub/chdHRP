<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp" />
<script type="text/javascript">
    var dataFormat;
    var old_emp_code = '${emp_code}';
    
    $(function (){
    	
        loadDict();
        
        loadForm();
        
        if(${is_stop}==true){
        	
     		liger.get("is_stop").setValue(1);
     		liger.get("is_stop").setText("是");	
     		
     	}
     	
     	if(${is_stop}==false){
     		
     		liger.get("is_stop").setValue(0);
     		liger.get("is_stop").setText("否");	
     		
     	}
     	 if(${is_acc}==true){
     		 
      		liger.get("is_acc").setValue(1);
      		liger.get("is_acc").setText("是");	
      		
      	}
      	
      	if(${is_acc}==false){
      		
      		liger.get("is_acc").setValue(0);
      		liger.get("is_acc").setText("否");	
      		
      	}
      	
		liger.get("duty_code").setValue("${duty_code}");	
		liger.get("duty_code").setText("${duty_name}");	
		
    });  
     
    function save(){
    	
    	var emp_code = $("#emp_code").val();
    	
    	if(emp_code ==''){
    		$.ligerDialog.warn('职工编码不能为空');
    		return ; 
    	}
    	
    	var save_history = $("input[name='save_history']").attr("checked") ? 1 : 0 ;
    	
        var formPara={
        		
        		emp_id:$("#emp_id").val(),
        		
        		old_emp_code : old_emp_code,
        		
        		emp_code:emp_code,
        		
        		emp_name:$("#emp_name").val(),
        		
        		dept_id : liger.get("dept_id").getValue(),
        		
        		duty_code:liger.get("duty_code").getValue(),
        		
           		is_stop:liger.get("is_stop").getValue() ,
           		
           		is_acc:liger.get("is_acc").getValue() ,
           		
           		save_history : save_history
           		
        }; 
         
        
        ajaxJsonObjectByUrl("updateHpmEmp.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
            	old_emp_code = $("#emp_code").val();
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
   
    function saveEmp(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true,'',false,'${dept_id},${dept_no}');
        
    	autocomplete("#duty_code","../queryEmpDutyDict.do?isCheck=false","id","text",true,true,'',false,'${duty_code}');
    	liger.get("duty_code").setValue('${duty_code}');
        liger.get("duty_code").setText("${dept_id} ${duty_name}");
        
        $("#emp_code").ligerTextBox({width : 160});
        
     }   
    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<table cellpadding="0" cellspacing="0" class="l-table-edit">
			<tr>
				<td align="left" class="l-table-edit-td" style="display: none">
					<input name="emp_id" type="text" id="emp_id" ltype="text" value="${emp_id}" validate="{required:true,maxlength:20}" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工编码：</td>
				<td align="left" class="l-table-edit-td">
					<input name="emp_code" type="text" disabled="disabled" id="emp_code" ltype="text" value="${emp_code}" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职工名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="emp_name" type="text" id="emp_name" ltype="text" value="${emp_name}" validate="{required:true,maxlength:20}" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="dept_id" type="text" id="dept_id" ltype="text" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">职务名称：</td>
				<td align="left" class="l-table-edit-td">
					<input name="duty_code" type="text" id="duty_code" ltype="text" />
				</td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否停用：</td>
				<td align="left" class="l-table-edit-td"><select name="is_stop" id="is_stop">
						<option value=""></option>
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">是否参与核算：</td>
				<td align="left" class="l-table-edit-td"><select name="is_acc" id="is_acc">
						<option value="0">否</option>
						<option value="1">是</option>
				</select></td>
				<td align="left"></td>
			</tr>
			
			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
					<input name="save_history" type="checkbox" id="save_history"/>
				</td>
				<td align="left" class="l-table-edit-td">
					是否保留历史记录
				</td>
				<td align="left"></td>
			</tr>
		</table>
	</form>
</body>
</html>
