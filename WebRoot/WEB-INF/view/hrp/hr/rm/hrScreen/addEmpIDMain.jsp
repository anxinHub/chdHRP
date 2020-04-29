<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
  
function save() {
	
	if(isnull($("#emp_code").val())){
		parent.$.ligerDialog.warn('人员编号不能为空！');
		return;
	}
		var data = frameElement.dialog.get("data");
		
		var ParamVo  = [];
		 $(data).each(function (i){	
				ParamVo.push({
					record_id : data[i].record_id,
		 			group_id : data[i].group_id,
		 		    hos_id: data[i].hos_id,
					record_state: '04',
		 			emp_code : $("#emp_code").val(),
		 		    emp_name: data[i].app_name,
		 		    dept_no : data[i].dept_no,
		 		    dept_id : data[i].dept_id,
		 		    id_card : data[i].app_cardid,
		 		    phone : data[i].app_phone,
		 		   	email : data[i].app_email,
		 		    sex_code : data[i].app_sex,
		 		    birthday : data[i].app_birth
				});
      });
		$.post("updateRecordStage03Batch.do?isCheck=false&tab_code=HR_RECRUIT_RECORD",  {ParamVo:JSON.stringify(ParamVo)},
				   function(data,status){
					   if(data.state == "true"){
						   parent.$.ligerDialog.success("录用成功！");
						   $("input[name='emp_code']").val('');
						   parent.query();
						   frameElement.dialog.close();
					   }else{
						   parent.$.ligerDialog.warn(data.error);
					   }
						
				 },"json");
		
	}


	function saveDemandAudit() {
		save();
	}
	function this_close() {
		frameElement.dialog.close();
	}

    </script>

</head>

<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1" style="display:blok">
   	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
       	 <td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">工号<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="emp_code" type="text" id="emp_code" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
	  	</tr>
    </table>
    </form>
	<table align="center" cellpadding="0" cellspacing="0"
				class="l-table-edit">
				<tr align="center">
					<td align="center" class="l-table-edit-td"
						style="padding-left: 20px;"><input
						class="l-button l-button-test" style="float: right;" type="button"
						value="保存" onclick="saveDemandAudit();" /></td>
					<td align="center" class="l-table-edit-td"
						style="padding-left: 20px;"><input
						class="l-button l-button-test" style="float: right;" type="button"
						value="关闭" onclick="this_close();" /></td>
				</tr>
	</table>

</body>
</html>
