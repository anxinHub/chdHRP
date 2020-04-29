<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<link rel="stylesheet" href="<%=path%>/lib/font-awesome/css/font-awesome.min.css"/>
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script type="text/javascript">
var dialog = frameElement.dialog;
var source;
	$(function() {
		loadDict();
	});
	
	function loadDict() {
		$("#type_code").ligerTextBox({width : 180,disabled:true});
		$("#type_name").ligerTextBox({width : 180});
		$("#type_name").ligerTextBox({width : 180});
		$("#save").ligerButton();
		$("#cancle").ligerButton();
		$("#is_stop").ligerComboBox({ 
			width: 180,
            data: [
                { text: '启用', id: '0' },
                { text: '停用', id: '1' },
            ]
        }); 
		liger.get("is_stop").setValue(${is_stop});
		liger.get("type_code").setValue('${type_code}');
		liger.get("type_name").setValue('${type_name}');
	}
	function cancle(){
		 dialog.close();
	}
	//保存主表格
	function save(){
		var type_code = liger.get("type_code").getValue();
		if(!type_code){
			$.ligerDialog.error("请填写类别编码！");
			return;
		}
		var type_name = liger.get("type_name").getValue();
		if(!type_name){
			$.ligerDialog.error("请填写类别名称！");
			return;
		}
		
		var para={
				type_code :liger.get("type_code").getValue(),
				type_name :liger.get("type_name").getValue(),
				is_stop :liger.get("is_stop").getValue()
		};
		
		ajaxJsonObjectByUrl("updateAmortizeType.do",para,function(responseData){
            if(responseData.state=="true"){
            	parentFrameUse().query();
            	if(!liger.get("byadd").getValue()){
            		cancle();
            	}
            }
        });
	}
	
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;padding-top: 10px"><strong style="color: red;">*</strong>类别编码：</td>
			<td align="left" class="l-table-edit-td"><input id="type_code" name="type_code" /></td>	
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;padding-top: 10px"><strong style="color: red;">*</strong>类别名称：</td>
			<td align="left" class="l-table-edit-td"><input id="type_name" name="type_name" /></td>	
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 10px;padding-top: 10px">是否停用：</td>
			<td align="left" class="l-table-edit-td"><input id="is_stop" name="is_stop" /></td>	
		</tr>
		<tr>
			<td align="left" colspan="2" style="padding-left: 20px;padding-top: 20px">
				<input type="checkbox" id="byadd" class="liger-checkbox" /> 连续添加
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2" style="padding-top: 10px"><input type="button" id="save" value="保存" onclick="save()"/>
			<input type="button" id="cancle" value="取消" onclick="cancle()"/></td>
		</tr>
	</table>
</body>
</html>
