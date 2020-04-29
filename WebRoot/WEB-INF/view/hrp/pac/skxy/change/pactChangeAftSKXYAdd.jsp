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
<script>
	var grid,sup_no,pact_code;
	var change_date;
	var initSelect = function() {
		var change = true;
		cus_no = $("#cus_no").etSelect({
   			url: '../../basicset/select/queryHosCusDictSelect.do?isCheck=false',
   			defaultValue: 'none',
   			onChange:function(value, $item){
   				if (change) {
   					 pact_code.setValue(0);
   				}else{
   					change = true
   				}
   				pact_code.reload({
   					 url: '../../basicset/select/queryPactSKXYSelectPerm.do?isCheck=false&cus_no=' + cus_no.getValue(),
   	    		})
   			 }, 
   		 });
       	pact_code = $("#pact_code").etSelect({
         		url: '../../basicset/select/queryPactSKXYSelectPerm.do?isCheck=false&cus_no=' + cus_no.getValue(),
         		defaultValue: 'none',
       	});
      }

	$(function() {
		initSelect();
		initfrom();
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
		})
		$("#save").on("click", function () {
			save();
		})
	})

	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#cus_no"), required : true},
				{el : $("#pact_code"),required : true},
				{el : $("#change_date"),required : true},
				{el : $("#change_reason"),required : true}
			]
		});
		if(!formValidate.test()){return;};
		ajaxPostData({
			url : 'addPactSKXYC.do?isCheck=false',
			data : {
				cus_no : $("#cus_no").val(),
				pact_code : $("#pact_code").val(),
				change_reason : $("#change_reason").val(),
				change_date : $("#change_date").val()
			},
			success : function(data) {
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        var parentFrameName = parent.$.etDialog.parentFrameName;
		        var parentWindow = parent.window[parentFrameName];
			    parentWindow.query(); 
		        parent.$.etDialog.close(curIndex); 
			},
			delayCallback : true
		})
	}
	
	//日期
	var initfrom = function() {
		change_date = $("#change_date").etDatepicker({
			defaultDate: true
		});
	}
</script>
</head>

<body>
	<table class="table-layout">
		<tr>
			<td class="label" style="width: 100px;">变更单号：</td>
			<td class="ipt"><input id="change_code" type="text" disabled="disabled" value="自动生成" style="background-color: #EAEAEA"/></td>
			<td class="label no-empty" style="width: 100px;">客户：</td>
			<td class="ipt"><select id="cus_no" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">协议名称：</td>
			<td class="ipt"><select id="pact_code" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">变更日期：</td>
			<td class="ipt" style="width: 180px;"><input id="change_date"type="text" style="width: 180px" /></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">变更原因：</td>
			<td class="ipt" colspan="6"><textarea id="change_reason" style="height: 80px;width: 840px"></textarea></td>
		</tr>
	</table>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>

</html>

