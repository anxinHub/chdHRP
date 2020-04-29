<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<script type="text/javascript">
	$(function(){
		loadDict();
		
		$("#warn_way").change(function(){
			if(liger.get("warn_way").getValue() == 2 || liger.get("warn_way").getValue() == 4){
				$("#days").ligerTextBox({ width : 160, disabled : false});
			}else{
				$("#days").ligerTextBox({ width : 160, disabled : true});
				$("#days").val("");
			}
		});
		
		if(${warnType.warn_way == 1} || ${warnType.warn_way == 3} || ${warnType.warn_way == 5}){
			$("#days").ligerTextBox({ width : 160, disabled : true});
			$("#days").val("");
		}
		
		$('#icon_url').change(function(){
			$("#showImg").prop("src", "../../../../dhc/images/warnType/" + liger.get("icon_url").getValue());
		});
	});
	
	function loadDict(){
		
		if(${warnType.is_sys == 1}){
			$("#warn_type_code").ligerTextBox({ width : 160, disabled: true});
		}else{
			$("#warn_type_code").ligerTextBox({ width : 160, disabled: false});
		}
		$("#warn_type_name").ligerTextBox({ width : 160});
		$("#days").ligerTextBox({ width : 160});
		
		$("#warn_way").ligerComboBox({
			data:[{id:1,text:'过期'},{id:2,text:'提前'},{id:3,text:'到期'},{id:4,text:'未到期'},{id:5,text:'缺失'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		}).selectValue("${warnType.warn_way}");
		$("#space").ligerComboBox({
			data:[{id:1,text:'每天'},{id:2,text:'每周'},{id:3,text:'每月'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160,
			disabled:true
		}).selectValue("${warnType.space}");
		$('#is_stop').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		}).selectValue("${warnType.is_stop}");
		$('#is_warn').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		}).selectValue("${warnType.is_warn}");
		
		$("#save").ligerButton({ click: save, width: 90 });
		$("#close").ligerButton({ click: thisClose, width: 90 });
		
		ajaxJsonObjectByUrl("queryMatWarnTypeImg.do?isCheck=false", {}, 
			function(responseData) {
				var imgData = new Array();
				responseData.forEach(function( val, index ) {
					imgData.push({id: val, text:val})
				});		
				$('#icon_url').ligerComboBox({
					data:imgData,
					valueField: 'id',
			        textField: 'text',
					cancelable:true,
					width:130
				}).selectValue("${warnType.icon_url}");
			}
		);
	}
	
	function save(){
		if(!$("#warn_type_code").val()) {
			$.ligerDialog.warn("预警类型编码不能为空！");
			return false;
		}
		if(!$("#warn_type_name").val()) {
			$.ligerDialog.warn("预警类型名称不能为空！");
			return false;
		} 
		
		var warn_way = liger.get("warn_way").getValue();
		var space = liger.get("space").getValue();
		var is_stop = liger.get("is_stop").getValue();
		var is_warn = liger.get("is_warn").getValue();
		
		if(!warn_way) {
			$.ligerDialog.warn("提醒方式不能为空！");
			return false;
		} 
		if(!space) {
			$.ligerDialog.warn("提醒间隔不能为空！");
			return false;
		}
		if(!is_stop) {
			$.ligerDialog.warn("是否停用不能为空！");
			return false;
		}
		if(!is_warn) {
			$.ligerDialog.warn("是否待办提醒不能为空！");
			return false;
		}
		
		var para = {
			old_code: "${warnType.warn_type_code}",
			warn_type_code: $("#warn_type_code").val(),
			warn_type_name: $("#warn_type_name").val(),
			warn_way: liger.get("warn_way").getValue(), 
			days: $("#days").val(), 
			space: liger.get("space").getValue(),
			is_stop: liger.get("is_stop").getValue(),
			is_warn: liger.get("is_warn").getValue(),
			icon_url: $("#icon_url").val(),
			is_update: 1
		};
		
		ajaxJsonObjectByUrl("saveMatWarnType.do?isCheck=false", para, function (responseData) {
			if (responseData.state == "true") {
				// 刷新父页面的表格
				parentFrameUse().query();
			}
		}, false);
	}
	
	//关闭页面
	function thisClose(){
 		frameElement.dialog.close();
	} 
</script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toolbarDiv"></div>
	<table class="table-layout" style="margin-top:10px;" width="100%">
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>预警类型编码：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="warn_type_code" validate="{required:true}" value="${warnType.warn_type_code }"/>
			</td>
			
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>预警类型名称：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="warn_type_name" validate="{required:true}" value="${warnType.warn_type_name }" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>提醒方式：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="warn_way" validate="{required:true}" />
			</td>

			<td align="right" class="l-table-edit-td">
				天数：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="days" validate="{required:true}" value="${warnType.days }"/>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>提醒间隔：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="space" validate="{required:true}" />
			</td>

			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>是否停用：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="is_stop" validate="{required:true}" />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td">
				<span style="color: red">*</span>是否待办提醒：
			</td> 
			<td align="left" class="l-table-edit-td">
				<input type="text" id="is_warn" validate="{required:true}" />
			</td>

			<td align="right" class="l-table-edit-td">
				图标：
			</td> 
			<td align="left" class="l-table-edit-td">
				<i style="display:inline-block"><input type="text" id="icon_url" validate="{required:true}"/></i>
				<i style="display:inline-block"><img id="showImg" src="../../../../dhc/images/warnType/${warnType.icon_url}" /></i>
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" style="margin-top: 50px;">
		<tr>
			<td align="center" class="l-table-edit-td">
				<button id="save" ><b>保存</b></button> &nbsp;&nbsp;
				<button id="close" ><b>关闭</b></button>
			</td>
		</tr>
	</table>
	<div id="grid" style="display: none"></div>
</body>
</html>