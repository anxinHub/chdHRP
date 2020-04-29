<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
	$(function(){
		$("#che_name").ligerTextBox({width:180 });
		$("#che_group").ligerTextBox({width:180 }); 
		var accBookSch = parentFrameUse().accBookSch;
		if(accBookSch.sch_id){
			$('#sch_id').val(accBookSch.sch_id);
			$('#che_name').val(accBookSch.che_name);
			$('#che_group').val(accBookSch.che_group);
			if(accBookSch.che_type == 2){
				$('#che_type2').prop("checked", "checked");
			}
		}
	})
	
	function saveSch(){
		var sch_id  = $('#sch_id').val();
		var che_name  = $('#che_name').val();
		var che_group  = $('#che_group').val();
		var che_type = $('.che_type:checked').val();
 
		if (che_name == "") {
			$.ligerDialog.error("方案名称不能为空！");
			return false;
		}

		var para = {
			sch_id: sch_id, 
			che_name: che_name, 
			che_group: che_group, 
			che_type: che_type, 
			is_check: '${is_check}', 
			page: '${page}' 
		};
		
		ajaxJsonObjectByUrl("saveAccBookSch.do?isCheck=false", para, function (responseData) {
			if (responseData.state == "true") {
				// 执行主页面的loadTree方法
		        /* etDialog写法 */
				/* var parentFrameName = parent.$.etDialog.parentFrameName; // 拿取window名
				var parentWindow = parent.window[parentFrameName]; // 当前页拿取window对象
				parentWindow.loadTree(); */
				/* ligerDialog写法 */
				parentFrameUse().loadTree(); 
				if(parentFrameUse().parentFrameUse().liger.get("sch_id")){
					parentFrameUse().parentFrameUse().liger.get("sch_id").reload();
				}
				thisClose();
			}
		}, false);
	}
	
	function thisClose(){
		// 保存成功后的回调
        /* etDialog写法 */
        /* var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex); */
        /* ligerDialog写法 */
 		frameElement.dialog.close();
	} 
</script>
</head>
<body>
	<input type="hidden" id="sch_id" /> 
	<div id="pageloading" class="l-loading" style="display: none"></div>
	
	<table class="table-layout" style="margin:10px;border-bottom: 1px solid #f1f1f1">
		<tr>
			<td align="right" class="l-table-edit-td" style="padding: 15px"><font size="2" color="red">*</font>方案名称 ：</td> 
			<td align="left" class="l-table-edit-td">
				<input name="che_name" type="text" id="che_name" ltype="text"   />
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding: 15px">方案分组 ：</td> 
			<td align="left" class="l-table-edit-td">
				<input name="che_group" type="text" id="che_group" ltype="text"   />
			</td>
		</tr>
		<tr> 
			<td align="right" style="padding:20px;">
				<input style="vertical-align: middle" name="che_type" id="che_type1" type="radio" class="che_type" value="1" ltype="text"  checked/>个人信息
			</td>
			<td align="left" style="padding-left:20px;">
				<input style="vertical-align: middle" name="che_type" id="che_type2" type="radio" class="che_type" value="2" ltype="text"  />主体信息
			</td>
		</tr>
	</table>
	<div style="text-align: right;margin: 15px;">
		<button class="l-button l-button-test" onclick="saveSch();" style="font-size: 12px;background: #4490ba;color: #fff;">保存</button>
		<button class="l-button l-button-test" onclick="thisClose();" style="font-size: 12px;">取消</button>	
	</div>
</body>
</html>