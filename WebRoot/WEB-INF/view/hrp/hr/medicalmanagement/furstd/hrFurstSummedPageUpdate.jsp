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
	<jsp:param value="hr,select,validate,grid,upload,datepicker,dialog"
		name="plugins" />
</jsp:include>
<script>
	var application_date, surgery_name, emp_id;
	var user_hide_data = {};
	var initForm = function() {
		if (' ${is_commit}' != 0) {//置黑不能点击
			/* toobarmanage = gridManager.toolbarManager;
			toobarmanage.setDisabled('save'); */
			document.getElementById("save").disabled = true;
		}
		
		//主表保存
		$("#save").click(function() {
			if ($("#app_no").val() == "") {
				$.etDialog.error('进修单号不能为空');
				return;
			}
			if ($("#emp_id").val() == "") {
				$.etDialog.error('员工姓名不能为空');
				return;
			}
			if ($("#sum_date").val() == "") {
				$.etDialog.error('总结日期不能为空');
				return;
			}
		
			if ($("#tel").val() != "") {
				var tel = $("#tel").val();
				//验证手号码格式
				var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
				if (!myreg.test(tel)) {
					$.etDialog.error('请输入正确的手机号格式');
					return;
				}
			} 
		
			var formPara = {
				app_no : $("#app_no").val(),
				emp_id : ${emp_id},
				sum_date : $("#sum_date").val(),
				furstd_hos : $("#furstd_hos").val(),
				teacher : $("#teacher").val(),
				tel : $("#tel").val(),
				summary : $("#summary").val(),
				plan1 : $("#plan1").val(),
				plan3 : $("#plan3").val(),
				plan6 : $("#plan6").val(),
			};

			ajaxPostData({
				url : 'updateFurstSummed.do',
				data : formPara,
			    success: function (responseData) {
                  	$.etDialog.success(
							'修改成功',
							 function (index, el) {
								  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		                            
		                            var parentFrameName = parent.$.etDialog.parentFrameName;
		                            var parentWindow = parent.window[parentFrameName];
		                            parentWindow.query(); 
		                            parent.$.etDialog.close(curIndex);
							    }
							)
                  },
			})
		});
		//关闭
		$("#colse").click(function() { 
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
			});
	};

	$(function() {
		initForm();
		
	})
	
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label"><font size="2" color="red">*</font>进修单号：</td>
				<td class="ipt"><input id="app_no" type="text"  value="${app_no }" disabled/></td>
				<td class="label">员工姓名：</td>
				<td class="ipt"><input id="emp_name" type="text" value="${emp_name }" disabled/></td>
				<td class="label"><font size="2" color="red">*</font>总结日期：</td>
				<td class="ipt"><input id="sum_date" type="text" value="${sum_date }" disabled/></td>
			</tr>
			<tr>
				<td class="label">进修医院：</td>
				<td class="ipt"><input id="furstd_hos" type="text" value="${furstd_hos }" disabled/></td>
				<td class="label">进修导师：</td>
				<td class="ipt"><input id="teacher" type="text" value="${teacher }"></td>
				<td class="label">导师电话：</td>
				<td class="ipt"><input id="tel" type="text" value="${tel }"/></td>
			</tr>
            <tr><td class="label">进修后开展项目情况：</td></tr>
			<tr>
				<td class="label">一月：</td>
				<td class="ipt"><input id="plan1" type="text" value="${plan1 }"/></td>
				<td class="label">三月：</td>
				<td class="ipt"><input id="plan3" type="text" value="${plan3 }"/></td>
				<td class="label">六月：</td>
				<td class="ipt"><input id="plan6" type="text" value="${plan6 }"/></td>
			</tr>
			<tr>
				<td class="label">进修总结：</td>
			</tr>
			<tr>
				<td class="label"></td>
				<td><textarea class="ipt" id="summary" style="width: 1303px;height: 300px;">${summary }</textarea></td>
			</tr>							
		</table>
	</div>
	<div class="button-group">
		<button id="save">保存</button>
		<button id="colse">关闭</button>
<!-- 		<button id="submit">提交</button> -->
<!-- 		<button id="cancel">撤回</button> -->
	</div>
</body>

</html>