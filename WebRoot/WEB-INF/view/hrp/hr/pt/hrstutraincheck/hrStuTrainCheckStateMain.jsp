<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    });
  
function save() {
		var data = frameElement.dialog.get("data");
		
		var ParamVo  = [];
		var check_status;
		check_status=data[0].check_status;
		if(check_status=='auditDept'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,	
				 		   dept_state: liger.get("state").getValue(),
				 		   dept_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		    state:'0',			
				 		   dept_state: liger.get("state").getValue(),
				 		   dept_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}
		
			ajaxJsonObjectByUrl("updateDeptStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}else if(check_status=='auditDeptSubdecanal'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   deptsubdecanal_state: liger.get("state").getValue(),
				 		  deptsubdecanal_state_note : $("#note").val(),				 		  
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',				 		   
				 		 deptsubdecanal_state: liger.get("state").getValue(),
				 		  deptsubdecanal_state_note : $("#note").val(),				 		  
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateDeptSubdecanalStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}else if(check_status=='auditHr'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   hr_state : liger.get("state").getValue(),
				 		  hr_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		 hr_state : liger.get("state").getValue(),
				 		  hr_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateHrStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}else if(check_status=='auditHrSubdecanal'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   hrsubdecanal_state : liger.get("state").getValue(),
				 		  hrsubdecanal_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		 hrsubdecanal_state : liger.get("state").getValue(),
				 		  hrsubdecanal_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateHrSubdecanalStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}else if (check_status='auditFinSubdecanal'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   finsubdecanal_state : liger.get("state").getValue(),
				 		  finsubdecanal_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		 finsubdecanal_state : liger.get("state").getValue(),
				 		  finsubdecanal_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateFinSubdecanalStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}else{
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   chairman_state : liger.get("state").getValue(),
				 		  chairman_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							stutrain_id : data[i].stutrain_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		 chairman_state : liger.get("state").getValue(),
				 		  chairman_state_note : $("#note").val(),
				 			tab_code: 'HR_STUTRAIN_CHECK'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateChairmanStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}
	
		
	}



	function saveDemandAudit() {
// 		if ($("form").valid()) {
			save();
// 		}
	}
	function this_close() {
		frameElement.dialog.close();
	}
    function loadDict(){
            //字典下拉框
    	 $('#state').ligerComboBox({
				data:[{id:1,text:'通过'},{id:0,text:'拒绝'}],
				valueField: 'id',
	            textField: 'text',
				cancelable:true,
				width : 180
		}) 
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核：</td>
            <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核意见：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="note" name="note" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>  
                </td>
                <td align="left"></td>
             </tr>
    </table>
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
