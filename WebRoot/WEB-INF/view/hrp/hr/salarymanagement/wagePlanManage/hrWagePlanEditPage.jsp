<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,select,validate,checkbox,datepicker" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var state, start_date;
	var saveOrUpdateUrl = "addWagePlan.do";
	$(function(){
		initValidate();
		initForm();
	});
	
	var initValidate = function () {
        formValidate = $.etValidate({
        	config: {},
        	items: [
				{ el: $("#plan_code"), required: true },
				{ el: $("#plan_name"), required: true },
				{ el: $("#state"), required: true }
            ]
        });
    };
	
	var initForm = function(){
		state = $("#state").etSelect({
			showClear: false,
		    options: [
		        { id: '1', text: '启用' },
		        { id: '0', text: '停用' }
		    ],
		    defaultValue: '${hrWagePlan.state}'
		});
		
		start_date = $("#start_date").etDatepicker({
			defaultDate: "${hrWagePlan.start_date}" || true
		});
		if("${hrWagePlan.plan_code}"){
			$("#plan_code").prop("disabled", "disabled");
			saveOrUpdateUrl = "updateWagePlan.do?isCheck=false";
		}
		var len = ${fn:length(hrWagePlan.empKindList)};
		if(len > 0){
			var empKinds = [];
			<c:forEach items="${hrWagePlan.empKindList}" var="item">
				empKinds.push("${item.emp_kind_code}");
			</c:forEach>
			for(var i = 0; i < len; i++){
				$("#emp_kind_code_" + empKinds[i]).prop("checked", true);
			}
		}
		
		$("#close").click(function () {
	        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex);
		});
		
		$("#save").click(function(){
			if (!formValidate.test()) {
				return;
			}
			var kinds = $("input[name='emp_kind_code']:checked");
			var empKinds = [];
			if(kinds.length == 0){
				$.etDialog.warn("请选择职工分类！");
				return;
			}else{
				$(kinds).each(function(){
					empKinds.push(this.value);
				});
			}
			ajaxPostData({
				url: saveOrUpdateUrl,
				data: {
					plan_code: $("#plan_code").val(),
					plan_name: $("#plan_name").val(),
					emp_kinds: JSON.stringify(empKinds),
					note: $("#note").val(),
					start_date: start_date.getValue(),
					state: state.getValue()
				},
				delayCallback:true,
				success: function(res){
					var parentFrameName = parent.$.etDialog.parentFrameName;
					var parentWindow = parent.window[parentFrameName];
					parentWindow.queryLeftGrid();
					var curIndex = parent.$.etDialog.getFrameIndex(window.name);
					parent.$.etDialog.close(curIndex);
				}
			});
		});
	}
	
</script>
<body style="overflow:hidden;">
	<div class="main flex-wrap">
		<table class="flex-item-1 table-layout">
			<tr>
				<td class="label">薪资方案编码<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="plan_code" id="plan_code" value="${hrWagePlan.plan_code}"/>
				</td>
			</tr>
			<tr>
				<td class="label">薪资方案名称<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<input type="text" name="plan_name" id="plan_name" value="${hrWagePlan.plan_name}"/>
				</td>
			</tr>
			<!-- 职工类别HOS_EMP_KIND -->
			<tr>
				<td class="label">职工类别<span style="color:red;">*</span>：</td>
				<td class="ipt" colspan="3">
					<c:forEach items="${emp_kinds}" var="item">
						<input type="checkbox" name="emp_kind_code" id="emp_kind_code_${item.kind_code}" value="${item.kind_code}">
						<label>${item.kind_name}</label>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<td class="label">备注：</td>
				<td class="ipt" colspan="3">
					<input type="text" name="note" id="note" value="${hrWagePlan.note}" style="width: 440px"/>
				</td>
			</tr>
			<tr>
				<td class="label">启用日期：</td>
				<td class="ipt" >
					<input type="text" name="start_date" id="start_date" value="${hrWagePlan.start_date}"/>
				</td>
				<td class="label">状态<span style="color:red;">*</span>：</td>
				<td class="ipt">
					<select id="state" style="width: 80px;"></select>
				</td>
			</tr>
		</table>
	</div>
	<div class="button-group">
        <button id="save">保存(S)</button>
        <button id="close">关闭(E)</button>
    </div>
</body>
</html>