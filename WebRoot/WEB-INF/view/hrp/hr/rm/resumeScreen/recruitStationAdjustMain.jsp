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
		
		var formPara = {
				record_id : record_id,
				demand_id : liger.get("demand_id").getValue(),
				record_state : '01'
			};
			
		ajaxJsonObjectByUrl("updateRecruitRecord01.do?isCheck=false&tab_code=HR_RECRUIT_Record",formPara,function (responseData){
			if (responseData.state == "true") {
				parent.$.ligerDialog.success("岗位调整成功");
				
				$("input[name='dept_select']").val('');
				$("input[name='station_select']").val('');
				
				parent.query();
				frameElement.dialog.close();
			}else{
				parent.$.ligerDialog.warn(responseData.msg);
			}
    	});
	}

	function saveDemandAudit() {
// 		if ($("form").valid()) {
			save();
// 		}
	}
	
    function loadDict(){
            //字典下拉框
    	$("#dept_select").ligerComboBox({
	      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT',
	      	valueField: 'id',
	       	textField: 'text', 
	       	selectBoxWidth: 160,
	      	autocomplete: true,
	      	width: 180,
// 	      	onSuccess: function (data) {
// 				this.setValue("${demand_sex}");
//       		}

		 });
		 
		 $("#station_select").ligerComboBox({
		      	url: '../../baseSelect.do?isCheck=false&&field_tab_code=DIC_STATION_MANAGE',
		      	valueField: 'id',
		       	textField: 'text', 
		       	selectBoxWidth: 160,
		      	autocomplete: true,
		      	width: 180,
		      	onBeforeSelect: function (newvalue)
		      	 {
		      		if(liger.get("dept_select").getValue() =='' ||  liger.get("dept_select").getValue() == null){
		      			return $.ligerDialog.warn('请先选择招聘科室！');
		      		}
		      	 }

			 });
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘科室<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="dept_select" type="text" id="dept_select" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
				<td align="left"></td>
		</tr>
		<tr>
				<td align="right" class="l-table-edit-td"
					style="padding-left: 20px;">招聘岗位<span style="color: red">*</span>：
				</td>
				<td align="left" class="l-table-edit-td"><input
					name="station_select" type="text" id="station_select" ltype="text" 
					validate="{required:true,maxlength:100}" /></td>
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
