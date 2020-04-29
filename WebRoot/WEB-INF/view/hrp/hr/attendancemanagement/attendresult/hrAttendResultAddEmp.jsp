<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,select,validate" name="plugins" />
</jsp:include>
<script type="text/javascript" src="<%=path%>/lib/laydate/laydate.js"></script>
<script>
	var formValidate, emp_code, dept_code;

	$(function () {
		initDict();
   		initValidate();
	})
	
	function initDict(){
		/* 部门 */
		dept_code = $("#dept_code").etSelect({
		    url: '../../queryHosDeptSelect.do?isCheck=false&is_last=1',
		    defaultValue: "none",
		});
		/* 职工 */
		emp_code = $("#emp_code").etSelect({
		    url: '../../queryEmpSelect.do?isCheck=false',
		    defaultValue: "none",
		});
	}

	function checked(){
		var dataValidate = $.etValidate({
			config:{},
			items:[
				{ el : $('#dept_code'), required : true }, 
				{ el : $('#emp_code'), required : true }
			]
		});
	
		return dataValidate.test();
	}

	var initValidate = function () {
		$("#save").click( function () {
 			//验证
 			if(!checked()){
				return;
			}
			ajaxPostData({
				url: 'addAttendResultEmp.do',
				data: {
					year_month: ${year_month}, 
					dept_id_c: dept_code.getValue().split("@")[1],
					emp_id: emp_code.getValue(),
				},
				success: function (responseData) {
					var parentFrameName = parent.$.etDialog.parentFrameName;
					var parentWindow = parent.window[parentFrameName];
					parentWindow.query(); 
					/* var curIndex = parent.$.etDialog.getFrameIndex(window.name);
					parent.$.etDialog.close(curIndex); */
				},
			})
		})
 		$("#close").click(function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		})
	};
</script>
</head>

<body>
	<div class="button-group" >
		 <table class="table-layout" style="width: 100%;">
	        <tr>
	            <td class="label ">出勤科室：</td>
	            <td class="ipt">
	                <select id="dept_code" style="width:160px"></select>
	            </td>
	        </tr>
	        <tr>
	            <td class="label ">职工：</td>
	            <td class="ipt">
	                <select id="emp_code" style="width:160px"></select>
	            </td>
	        </tr>
    	</table>
        <div class="button-group btn">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	</div>   
</body>

</html>