<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
	var wbal_amt_check = 0;
	var check_code_check = 0;
	var con_no_check = 0;
	var business_no_check = 0;
	var occur_date_check = 0;
	var due_date_check = 0;
	var summary_check = 0;
	
    $(function (){
        loadDict();
    });  
     
 	function loadDict(){
 		
 		var mydate = new Date();
		var vYear = mydate.getFullYear();
		var vMon = mydate.getMonth() + 1;
		
		var acc_month;
		if(vMon<10){
			acc_month = getMonthDate(vYear,"0"+vMon);
		}else{
			acc_month = getMonthDate(vYear,vMon);
		}
		
		$("#begin_date").val(acc_month.split(";")[0]);
		$("#end_date").val(acc_month.split(";")[1]);
		
		$("#begin_date").ligerTextBox({});
     	$("#end_date").ligerTextBox({});
 	}       
   
 	function save(){
 		if($("#begin_date").val() == "" || $("#end_date").val() == ""){
    		$.ligerDialog.warn('请选择日期！');
        	return;
    	}
    	
   		var subjCode = parent.$("#subj_code").val().split(".")[1];
   	
   		if($("#wbal_amt_check").attr("checked") == true){
	    	wbal_amt_check = 1;
	    }else{
	    	wbal_amt_check = 0;
	    }
		
	    if($("#check_code_check").attr("checked") == true){
	    	check_code_check = 1;
	    }else{
	    	check_code_check = 0;
	    }
	    
	    if($("#con_no_check").attr("checked") == true){
	    	con_no_check = 1;
	    }else{
	    	con_no_check = 0;
	   	}
	    
	    if($("#business_no_check").attr("checked") == true){
	    	business_no_check = 1;
	    }else{
	    	business_no_check = 0;
	    }
	    
	    if($("#occur_date_check").attr("checked") == true){
	    	occur_date_check = 1;
	    }else{
	    	occur_date_check = 0;
	    }
	    
	    if($("#due_date_check").attr("checked") == true){
	    	due_date_check = 1;
	    }else{
	    	due_date_check = 0;
	    }
	    
	    if($("#summary_check").attr("checked") == true){
	    	summary_check = 1;
	    }else{
	    	summary_check = 0;
	    }
	    
	    var formPara={
	    	subj_name : '${subj_name}',	
	    	is_check : '${is_check}',
	    	subj_code : subjCode,
	    	veriState : 1,
	    	check_type : '${check_type}',
	    	check_type_id : '${check_type_id}',
	    	proj1 : '${proj1}',
	    	proj2 : '${proj2}',
	    	begin_date : $("#begin_date").val(),
	    	end_date : $("#end_date").val(),
	    	wbal_amt_check : wbal_amt_check,
			check_code_check : check_code_check,
			con_no_check : con_no_check,
			business_no_check : business_no_check,
			occur_date_check : occur_date_check,
			due_date_check : due_date_check,
			summary_check : summary_check
	    }
       ajaxJsonObjectByUrl("addBatchAccVerica.do",formPara,function(responseData){
           if(responseData.state=="true"){
               parent.veriALL();
               frameElement.dialog.close();
           }
       });
 	}
   function saveAccUnitBankCheck(){
	   save();
   }
    
    </script>
  <script type="text/javascript">
  	
  </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   		<center>
         	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	         	<tr>
	         		<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red" size="2">*</font>日期：</td>
			        <td align="left" class="l-table-edit-td"><input class="Wdate"  name="begin_date" type="text" id="begin_date"   class="l-text-field" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			        <td align="center" class="l-table-edit-td">至:</td>
			        <td align="left" class="l-table-edit-td"><input class="Wdate"  name="end_date" type="text" id="end_date"  class="l-text-field"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>	
	         	</tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">对账方式：</td>
	                <td align="left" class="l-table-edit-td"  style="padding-top:10px;">
	                	<input  name="wbal_amt_check"  readonly="readonly" checked="checked" type="checkbox" id="wbal_amt_check"  />&nbsp;&nbsp;未核销金额
	                </td>
	                <td align="left"></td>
	                <td align="left"></td>
	            </tr> 
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="check_code_check" type="checkbox" id="check_code_check"  />&nbsp;&nbsp;往来项目
	                </td>
	                <td align="left"></td>
	            </tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="con_no_check" type="checkbox" id="con_no_check"  />&nbsp;&nbsp;合同号
	                </td>
	                <td align="left"></td>
	            </tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="business_no_check" type="checkbox" id="business_no_check"  />&nbsp;&nbsp;单据号
	                </td>
	                <td align="left"></td>
	            </tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="occur_date_check" type="checkbox" id="occur_date_check"  />&nbsp;&nbsp;发生日期
	                </td>
	                <td align="left"></td>
	            </tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="due_date_check" type="checkbox" id="due_date_check"  />&nbsp;&nbsp;到期日期
	                </td>
	                <td align="left"></td>
	            </tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="summary_check" type="checkbox" id="summary_check"  />&nbsp;&nbsp;摘要
	                </td>
	                <td align="left"></td>
	            </tr>
           </table>
   		</center>
    </form>
    </body>
</html>
