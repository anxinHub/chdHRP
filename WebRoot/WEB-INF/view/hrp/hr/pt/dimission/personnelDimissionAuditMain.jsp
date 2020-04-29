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
		var flag = data[0].flag,state,url;
		var ParamVo  = [];
	      //	科室部门审核
		 if(flag == 1){
			 $(data).each(function (i){	
				 ParamVo.push({
			 			dim_id : data[i].dim_id,
			 			group_id : data[i].group_id,
			 		    hos_id : data[i].hos_id,
			 		   	dim_dept_state :liger.get("audit").getValue(),
			 		   	dim_dept_view : $("#check_view").val()
					});
				});
			 url="updatePersonnelDimission02";
		 }
		        //职能部门审核    
		 if(flag == 2){
			 $(data).each(function (i){	
				 ParamVo.push({
			 			dim_id : data[i].dim_id,
			 			group_id : data[i].group_id,
			 		    hos_id: data[i].hos_id,
			 		   	dim_func_state :liger.get("audit").getValue(),
			 		   	dim_func_view : $("#check_view").val()
					});
			 });
			 url="updatePersonnelDimission03";
		 }
		     //    分管副院长审核
		 if(flag == 3){
			 $(data).each(function (i){	
				 ParamVo.push({
			 			dim_id : data[i].dim_id,
			 			group_id : data[i].group_id,
			 		    hos_id: data[i].hos_id,
			 		   	dim_charge_state :liger.get("audit").getValue(),
			 		   	dim_charge_view : $("#check_view").val()
					});
			 });
			 url="updatePersonnelDimission04";
		 }
//	        人力资源部审核
		 if(flag == 4){
			 $(data).each(function (i){	
				 ParamVo.push({
			 			dim_id : data[i].dim_id,
			 			group_id : data[i].group_id,
			 		    hos_id: data[i].hos_id,
			 		   	dim_hr_state :liger.get("audit").getValue(),
			 		  	dim_hr_view : $("#check_view").val()
					});
			 });
			 url="updatePersonnelDimission05";
		 }
//           院长审核
		 if(flag == 5){
			 $(data).each(function (i){	
				 ParamVo.push({
			 			dim_id : data[i].dim_id,
			 			group_id : data[i].group_id,
			 		    hos_id: data[i].hos_id,
			 		   	dim_dean_state :liger.get("audit").getValue(),
			 		  	dim_dean_view : $("#check_view").val()
					});
			 });
			 url="updatePersonnelDimission06";
		 }
		
	
		ajaxJsonObjectByUrl(url+"Batch.do?isCheck=false&tab_code=HR_PERSONNEL_DIMISSION",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
			if (responseData.state == "true") {
				parent.$.ligerDialog.success("审核成功");
				
				$("input[name='theme_id']").val('');
				$("input[name='demand_note']").val('');
				
				parent.query();
				frameElement.dialog.close();
			}else{
				parent.$.ligerDialog.warn(responseData.msg);
			}
		});
    
	}

// 	function loadForm() {

// // 		$.metadata.setType("attr", "validate");
// 		var v = $("form").validate({
// 			errorPlacement : function(lable, element) {
// 				if (element.hasClass("l-textarea")) {
// 					element.ligerTip({
// 						content : lable.html(),
// 						target : element[0]
// 					});
// 				} else if (element.hasClass("l-text-field")) {
// 					element.parent().ligerTip({
// 						content : lable.html(),
// 						target : element[0]
// 					});
// 				} else {
// 					lable.appendTo(element.parents("td:first").next("td"));
// 				}
// 			},
// 			success : function(lable) {
// 				lable.ligerHideTip();
// 				lable.remove();
// 			},
// 			submitHandler : function() {
// 				$("form .l-text,.l-textarea").ligerHideTip();
// 			}
// 		});
// 		$("form").ligerForm();
// 	}

	function saveDemandAudit() {
// 		if ($("form").valid()) {
			save();
// 		}
	}
	
    function loadDict(){
            //字典下拉框
    	$("#audit").ligerComboBox({
     		 data:[{id:'0',text:'拒绝'},
     		       {id:'1',text:'通过'}],
     		 valueField: 'id',
           	 textField: 'text',
			 cancelable:true,width:190
			 }); 
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核结果：</td>
            <td align="left" class="l-table-edit-td"><input name="audit" type="text" id="audit" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核意见：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="check_view" name="check_view" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>  
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
