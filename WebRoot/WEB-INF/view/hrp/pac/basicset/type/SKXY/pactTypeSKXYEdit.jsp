<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,tree,grid,select,dialog,datepicker,validate"
		name="plugins" />
</jsp:include>

<script type="text/javascript">
var datepicker;
	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#type_code"),required : true}, 
				{el : $("#type_name"),required : true}, 
				{el : $("#mark"),required : true}, 
				{el : $("#dept_id"),required : true}, 
				{el : $("#start_date"),required : true},
				{el : $("#is_stop"),required : true}, 
				{el : $("#subject_type"),required : true}, 
			]
		});
		
		if(!formValidate.test()){
			return;
		};
		var modStart="${modStart}";
		var statDate=datepicker.getValue().substring(0,7).replace('-','');
		if(modStart>statDate){
			$.etDialog.warn("启用日期必须大于系统启用日期");
			return;
		}
		var text = $("#mark").val();
		if (text.length > 4) {
			return ;
		}else{
			for(var i=0;i<text.length;i++){
				var c=text.charAt(i);
				if(c<'A' || c>'Z'){
					$.etDialog.warn("代号必须为1至4位大写英文字母");
					return;
				}
			}
		}
		
		ajaxPostData({
			url : 'updatePactTypeSKXY.do',
			data : {
				type_code : $("#type_code").val(),
				type_name : $("#type_name").val(),
				mark : $("#mark").val(),
				dept_id : $("#dept_id").val(),
				start_date :datepicker.getValue(),
				note : $("#note").val(),
				is_stop : $("#is_stop").val(),
				subject_type : $("#subject_type").val(),

			},
			success : function() {
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
				parent.$.etDialog.close(curIndex);
				parent.window.query();
			},
			delayCallback : true
		})
	}

	$(function(){
		initfrom();
		initSelect();
		  if("${is_user}" != 0){
			  document.getElementById("mark").disabled=true;
			  $("#mark").attr("style",'background-color: #DBDBDB;');
			  document.getElementById("start_date").disabled=true;
			} 
	});
	
	var initSelect = function(){
		var deptSelect = $("#dept_id").etSelect({url: "../../select/queryDeptSelect.do?isCheck=false",defaultValue: "${entity.dept_id }"});
		var subjectType = $("#subject_type").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=SUBJECT_TYPE',defaultValue: "${entity.subject_type}"});
		$("#is_stop").etSelect({
			  options: [
			    { id: 0, text: '否' },
			    { id: 1, text: '是' }
			  ],
			  defaultValue: "${entity.is_stop }"
		})
	}
	var initfrom = function(){
		datepicker = $("#start_date").etDatepicker({defaultDate: "${start_date}",position:'right'});
	}
	
</script>
</head>

<body>
	<table class="table-layout">
		<tr>
			<td class="label  no-empty" style="width: 100px;">类型编码：</td>
			<td class="ipt"><input id="type_code" type="text" value="${entity.type_code }" disabled="disabled" style="background-color: #DBDBDB;"/></td>
			<td class="label  no-empty" style="width: 100px;">类型名称：</td>
			<td class="ipt"><input id="type_name" type="text" value="${entity.type_name }" /></td>
		</tr>
		<tr>
			<td class="label  no-empty" style="width: 100px;">类型代号：</td>
			<td class="ipt"><input id="mark" type="text" value="${entity.mark }" /></td>

			<td class="label  no-empty" style="width: 100px;">主管科室：</td>
			<td class="ipt"><select id="dept_id" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label  no-empty" style="width: 100px;">启用日期：</td>
			<td class="ipt"><input id="start_date" type="text" value="${start_date }"/></td>
			<td class="label no-empty" style="width: 120px;">标的物类型：</td>
			<td class="ipt"><select id="subject_type" style="width: 180px"></select></td>
		</tr>
		<tr>
			<td class="label  no-empty" style="width: 100px;">是否停用：</td>
			<td class="ipt"><select id="is_stop" style="width: 180px"></select></td>
			<td class="label" style="width: 100px;">备注：</td>
			<td class="ipt"><input id="note" type="text" value="${entity.note }"/></td>
		</tr>
	</table>
</body>

</html>

