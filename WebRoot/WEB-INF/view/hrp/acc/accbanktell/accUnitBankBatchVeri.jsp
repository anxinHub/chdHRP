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
	var pay_type_code = 0;
	var check_no = 0;
	
	//var  is_money = 1;
    $(function (){
        loadDict();
    });  
     
     function  save(){
    	if($("#begin_date").val() == "" || $("#end_date").val() == ""){
     		$.ligerDialog.warn('请选择日期！');
         	return;
     	}
     	
    	var subjCode = parent.$("#subj_code").val();
    	var subj_name =  parent.$("#subj_code").text();
     	/* if($('#pay_type_code').is(':checked') == true){
     		pay_type_code = 1;
	    }else{
	    	pay_type_code = 0;
	    }
     	
	    if($('#check_no').is(':checked') == true){
	    	check_no = 1;
	    }else{
	    	check_no = 0;
	    }
	     */
	    var formPara={
	    	subj_name : subj_name,	
	    	begin_date : $("#begin_date").val(),
	    	end_date : $("#end_date").val(),
	    	subj_code : subjCode/*,
	    	is_money : is_money,
	    	pay_type_code : pay_type_code,
	    	check_no : check_no */
	    }
	    //saveBatchAccBankVeri
        ajaxJsonObjectByUrl("saveBatchAccBankVeri.do",formPara,function(responseData){
            if(responseData.state=="true"){
                parent.checkAll();
            }
        });
    }
     
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
   
    function saveAccUnitBankCheck(){
    	save();
   }
    
    </script>
  <script type="text/javascript">
  	
  </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none;"></div>
   <form name="form1" method="post"  id="form1" >
   		<center>
         	<table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 20px">
	         	<tr>
	         		<td align="right" class="l-table-edit-td"  style="padding-left:10px;"><font color="red" size="2">*</font>日期：</td>
			        <td align="left" class="l-table-edit-td"><input class="Wdate"  name="begin_date" type="text" id="begin_date"   class="l-text-field" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			        <td align="center" class="l-table-edit-td">至:</td>
			        <td align="left" class="l-table-edit-td"><input class="Wdate"  name="end_date" type="text" id="end_date"  class="l-text-field"  onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>	
	         	</tr>
	            <!-- <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">对账方式：</td>
	                <td align="left" class="l-table-edit-td"  style="padding-top:10px;">
	                	<input  name="is_money"  readonly="readonly" checked="checked" type="checkbox" id="is_money"  />&nbsp;&nbsp;金额
	                </td>
	                <td align="left"></td>
	                <td align="left"></td>
	            </tr>  -->
	           <!--  <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="pay_type_code" type="checkbox" id="pay_type_code"  />&nbsp;&nbsp;结算类型
	                </td>
	                <td align="left"></td>
	            </tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="check_no" type="checkbox" id="check_no"  />&nbsp;&nbsp;票据号
	                </td>
	                <td align="left"></td>
	            </tr> -->
	            <!-- <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="occur_date" type="checkbox" id="occur_date"  />&nbsp;&nbsp;发生日期
	                </td>
	                <td align="left"></td>
	            </tr>
	            <tr>
	            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"></td>
	                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
	                	<input  name="summary" type="checkbox" id="summary"  />&nbsp;&nbsp;摘要
	                </td>
	                <td align="left"></td>
	            </tr> -->
           </table>
   		</center>
    </form>
    </body>
</html>
