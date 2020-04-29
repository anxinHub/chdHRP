<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
     var dataFormat;
     var subj_id ;  
     $(function (){  
        loadDict();//加载下拉框  
        loadForm();
     });  
    
	function loadForm(){
	    $("form").ligerForm();   
	}
	
 	function loadDict(){
 		autodate("#start_date", "yyyy-mm-dd", "month_first");
        autodate("#end_date", "yyyy-mm-dd", "month_last");
        autocomplete("#create_user", "../../sys/queryUserDict.do?isCheck=false", "id", "text", true, true, "", false, false,"","",160);
		liger.get("create_user").setValue('${user_id}');
		liger.get("create_user").setText('${user_name}');
		$("#start_date").ligerTextBox({width:160});
		$("#end_date").ligerTextBox({width:160});
 	}
 	
    function deleteAccVerBatchCancle(){
		var start_days = 0;		   	 
		var end_days = 0;		   	
		var formPara = {
			subj_id : '${subj_id}',   
			subj_name : '${subj_name}',
			create_user : liger.get("create_user").getValue(),
			user_name : liger.get("create_user").getText(),
		   	start_days : $("#start_date").val(), 	 
		   	end_days : $("#end_date").val() 	            
		}
		
		$.ligerDialog.confirm('确定要删除?', 
			function (yes){
		   		if(yes){
		   			ajaxJsonObjectByUrl("deleteBatchCancleAccVerica.do?isCheck=true",formPara,
		   		    	function (responseData){
		   		    		if(responseData.state=="true"){
		   		    			parent.veriALL();
		   		    			frameElement.dialog.close();
		   		    		}
		   		    	}
		   		   );
		   		}else{
		   			return;
		   		}						
		   	}				
		);
   }
</script>
</head>
<body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
			<tr>
        		<td colspan="2"><center ><h3>说明：按核销日期清除核销记录</h3></center></td>
        	</tr>
        	<tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">往来科目：</td>
        		<td align="left"> ${subj_name} </td>
        	</tr>
        	<tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">核销日期：</td>
                <td align="left" class="l-table-edit-td">
	                <input class="Wdate" name="start_date" type="text" id="start_date"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:MM:ss'})" ltype="text" />
	            </td>
        	</tr>
            <tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 至：</td>
                <td align="left" class="l-table-edit-td">
                	<input class="Wdate" name="end_date" type="text" id="end_date"  onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:MM:ss'})" ltype="text" />
                </td>
        	</tr>
        	<tr>
        		<td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 核销人：</td>
                <td align="left" class="l-table-edit-td"><input name="create_user" type="text" id="create_user"/></td>
        	</tr>
        </table>
    </form>
</body>
</html>
