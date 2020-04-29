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
	<jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<!-- script src="<%=path%>/lib/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script-->

<script type="text/javascript">
var cus_no;
	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#sign_date"),required : true},
				{el : $("#sp_money"),required : true,type: 'number'},
				{el : $("#pact_code"),required : true},
				{el : $("#cus_no"),required : true},
				{el : $("#party"),required : true},
				{el : $("#first_man"),required : true},
				{el : $("#second_man"),required : true}
				]
		});
		if(!formValidate.test()){
			return;
		};
		ajaxPostData({
			url : 'updatePactSPSKHT.do',
			data : {
				sp_code: $("#sp_code").val(),
				sign_date: sign_date.getValue(),
				sp_money: $("#sp_money").val(),
				party : party.getValue(),
				pact_code : '${entity.pact_code }',
				note : $("#note").val(),
				first_man : $("#first_man").val(),
				second_man : $("#second_man").val(),
				reason : $("#reason").val(),
				depend : $("#depend").val(),
			},
			success : function(data) {
				var parentFrameName = parent.$.etDialog.parentFrameName;
				var parentWindow = parent.window[parentFrameName];
				parentWindow.query(); 
				var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		        parent.$.etDialog.close(curIndex); 
			},
			delayCallback : true
		})
	}

    var initSelect=  function(){
    	first_man = $("#first_man").etSelect({url: '../../../basicset/select/queryHosEmpDictSelect.do?isCheck=false',defaultValue: "${entity.first_man }"});
    	party = $("#party").etSelect({url: '../../../basicset/select/queryDictSelect.do?isCheck=false&f_code=PARTY',defaultValue: "${entity.party}"});
    	cus_no = $("#cus_no").etSelect({options: [ { id: 0, text: '${entity.cus_name }' }] , defaultValue: "0"});
    	pact_code = $("#pact_code").etSelect({options: [ { id: 0, text: '${entity.pact_name }' }], defaultValue: "0"});
      }
    
	$(function(){
    	initSelect();
   		initfrom();
   		
   		if("${entity.state}" != "01"){
   			$("#sign_date").attr("disabled" , "disabled");
   			party.disabled();
   			$("#sp_money").attr("disabled" , "disabled");
   			$("#sp_money").attr("style","background-color:#EAEAEA");
   			$("#second_man").attr("disabled" , "disabled");
   			$("#second_man").attr("style","background-color:#EAEAEA");
   			first_man.disabled();
   			$("#note").attr("disabled" , "disabled");
   			$("#reason").attr("disabled" , "disabled");
   			$("#depend").attr("disabled" , "disabled");
   			$("#save").attr("disabled" , "disabled");
   			$("#save").attr("style","background-color:#EAEAEA");
   		}
   		
		$("#save").on("click", function () {
			save();
		})
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
		})
	})
	
	  //日期
	var initfrom = function(){
		sign_date = $("#sign_date").etDatepicker({
			defaultDate: '${sign_date }',
		});
	};
</script>
</head>

<body style="overflow: scroll; ">
	<div>
		<table class="table-layout">
			<tr>
				<td class="label no-empty" style="width: 100px;">登记单号：</td>
				<td class="ipt"><input id="sp_code" type="text" disabled="disabled" value="${entity.sp_code }" style="background-color: #EAEAEA"/></td>
				<td class="label no-empty" style="width: 100px;">委托人：</td>
				<td class="ipt"><select id="cus_no" style="width: 180px;" disabled="disabled"></select> </td>
				<td class="label no-empty" style="width: 100px;">合同名称：</td>
				<td class="ipt"><select id="pact_code" style="width: 180px;" disabled="disabled"></select> </td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 100px;">登记日期：</td>
				<td class="ipt"><input id="sign_date" style="width: 180px;"/></td>
				<td class="label no-empty" style="width: 100px;">赔偿方：</td>
				<td class="ipt"><select id="party" style="width: 180px;"></select></td>
				<td class="label no-empty" style="width: 100px;">赔偿金额：</td>
				<td class="ipt"><input id="sp_money" type="text" value="${entity.sp_money }"/></td>
			</tr>
			<tr>
				<td class="label no-empty" style="width: 120px;">甲方负责人：</td>
				<td class="ipt"><input id="second_man" type="text" value="${entity.second_man }"> </td>
				<td class="label no-empty" style="width: 100px;">乙方负责人：</td>
				<td class="ipt"><select id="first_man" style="width: 180px"></select></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">摘要：</td>
				<td class="ipt" colspan="5"><textarea id="note" style="height: 110px;width: 840px;margin-top: 15px">${entity.note }</textarea></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">索赔原因：</td>
				<td class="ipt" colspan="5"><textarea id="reason" style="height: 110px;width: 840px;margin-top: 15px">${entity.reason }</textarea></td>
			</tr>
			<tr>
				<td class="label" style="width: 100px;">索赔依据：</td>
				<td class="ipt" colspan="5"><textarea id="depend" style="height: 110px;width: 840px;margin-top: 15px">${entity.depend }</textarea></td>
			</tr>
		</table>
	</div>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>
</html>

