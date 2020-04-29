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
		 $(data).each(function (i){	
				ParamVo.push({
		 			demand_id : data[i].demand_id,
		 			group_id : data[i].group_id,
		 		    hos_id: data[i].hos_id,
		 		    demand_state:'02',
		 			theme_id : liger.get("theme_select").getValue(),
		 			demand_note : $("#demand_note").val()
				});
      });
		
	
		ajaxJsonObjectByUrl("updateDemandState02Batch.do?isCheck=false&tab_code=HR_RECRUIT_DEMAND",{ParamVo : JSON.stringify(ParamVo)},function (responseData){
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
    	autocomplete("#theme_select","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_RECRUIT_THEME", "id", "text",
    			true, true, "", false, null, "190");
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">招聘主题：</td>
            <td align="left" class="l-table-edit-td"><input name="theme_select" type="text" id="theme_select" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核意见：</td>
                <td align="left" class="l-table-edit-td">
                	<textarea rows="3" cols="30" id="demand_note" name="demand_note" class="wishContent" placeholder="请输入不超过100个字" maxlength="100"></textarea>  
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
