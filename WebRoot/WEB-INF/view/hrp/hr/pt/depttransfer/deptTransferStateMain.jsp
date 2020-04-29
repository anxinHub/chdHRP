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
		var regular_state;
		 regular_state=data[0].regular_state;
		if(regular_state=='auditBef'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   bef_state : liger.get("state").getValue(),
				 		  bef_state_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		   bef_state : liger.get("state").getValue(),
					 		  bef_state_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}
		
			ajaxJsonObjectByUrl("updateDeptBefStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}else if(regular_state=='auditAft'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   aft_state: liger.get("state").getValue(),
				 		  aft_state_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		   aft_state: liger.get("state").getValue(),
					 		  aft_state_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateDeptAftDeptStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}else if(regular_state=='auditCharge'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   charge_state : liger.get("state").getValue(),
				 		  charge_state_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		  charge_state : liger.get("state").getValue(),
				 		  charge_state_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateDeptChargeStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
				if (responseData.state == "true") {
					parent.$.ligerDialog.success("审核成功");
					parent.query();
					frameElement.dialog.close();
				}else{
					parent.$.ligerDialog.warn(responseData.msg);
				}
	    	});
		}else if(regular_state=='auditHR'){
			if(liger.get("state").getValue()=='1'){
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   hr_state : liger.get("state").getValue(),
				 		  hr_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		  hr_state : liger.get("state").getValue(),
				 		  hr_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateDeptHRStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
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
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		   aft_dept_state : liger.get("state").getValue(),
				 		  aft_dept_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}else{
				 $(data).each(function (i){	
						
						ParamVo.push({
							transfer_id : data[i].transfer_id,
				 			group_id : data[i].group_id,
				 		    hos_id: data[i].hos_id,
				 		  state:'0',
				 		  aft_dept_state : liger.get("state").getValue(),
				 		  aft_dept_note : $("#note").val(),
				 			tab_code: 'HR_DEPT_TRANSFER'
						});
		      });
			}
			ajaxJsonObjectByUrl("updateDeptAftStateBatch.do",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
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
