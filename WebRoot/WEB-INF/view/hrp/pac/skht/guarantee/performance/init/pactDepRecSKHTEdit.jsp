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
var cus_no,cus_id;
	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#cus_no"),required : true},
				{el : $("#pact_code"),required : true},
				{el : $("#date"),required : true},
				{el : $("#pay_way"),required : true},
				{el : $("#money"),required : true},
				{el : $("#ret_plan_date"),required : true},
				{el : $("#note"),required : true},
				]
		});
		if(!formValidate.test()){
			return;
		};
		ajaxPostData({
			url : 'updatePactDepRecSKHT.do',
			data : {
				pay_code : $("#pay_code").val(),
				cus_no : $("#cus_no").val(),
				cus_id : cus_id,
				pact_code : $("#pact_code").val(),
				date : $("#date").val(),
				pay_way : $("#pay_way").val(),
				money : $("#money").val(),
				cheq_no : $("#cheq_no").val(),
				ret_plan_date : $("#ret_plan_date").val(),
				note : $("#note").val(),
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
    	ajaxPostData({
      		url: '../../../../basicset/select/queryHosCusDictSelect.do?isCheck=false',
			  success: function (result) {
				  cus_no = $("#cus_no").etSelect({
					 defaultValue: '${entity.cus_no}',
					 options:result ,
					 onChange:function(value, $item){
						 for(var i = 0;i<result.length;i++){
							 var obj = result[i];
							 if(value == obj.id){
								 cus_id = obj.cus_id;
							 }
						 }
						pact_code.setValue(0);
						pact_code.reload({url: '../../../../basicset/select/queryPactSKHTSelect.do?isCheck=false&is_deposit=1&deposit_type=01&state_code=12&state=3&cus_no='+value,defaultValue: "none"})
			      	 }  
				 });
			  },
		});
    	
    	ajaxPostData({
      		url: '../../../../basicset/select/queryPactSKHTSelect.do?isCheck=false&state_code=12&is_deposit=1&deposit_type=01',
			  success: function (result) {
				  pact_code = $("#pact_code").etSelect({
					 defaultValue: '${entity.pact_code}',
					 options:result ,
					 onItemAdd:function(value, $item){
						 ajaxPostData({
	                		 	url: 'queryPactSKHT.do?isCheck=false&is_init=1',
	                		 	data:{
	                		 		pact_code:value
	                		 	},
	                			success: function (result) {
	                				recDate = $("#date").etDatepicker({
	                					defaultDate: result.start_date,
	                					maxDate:result.start_date,
	                					todayButton: false
	                				});
	                				ret_plan_date = $("#ret_plan_date").etDatepicker({
	                					defaultDate: result.end_date,
	                					minDate:result.start_date
	                				});
									$("#money").val(result.deposit_money);
	                			}
	                		});
			      	 }  
				 });
			  },
		});
    	
      	pay_way = $("#pay_way").etSelect({url: '../../../../basicset/select/queryPayTypeDictSelect.do?isCheck=false',defaultValue: "${entity.pay_way}"});
      }
    
	$(function(){
    	initSelect();
   		initfrom();
   		
		$("#save").on("click", function () {
			save();
		})
		$("#cancle").on("click", function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	        parent.$.etDialog.close(curIndex); 
		})
	})
	
	window.onload = function(){
		if("01" != "${entity.state}"){
   			pay_way.disabled();
   			$("#ret_plan_date").attr("disabled" , "disabled");
   			$("#date").attr("disabled" , "disabled");
   			$("#note").attr("disabled" , "disabled");$("#note").attr("style","background-color:#EAEAEA");
   			$("#cheq_no").attr("disabled" , "disabled");$("#cheq_no").attr("style","background-color:#EAEAEA");
   			$("#money").attr("disabled" , "disabled");$("#money").attr("style","background-color:#EAEAEA");
   			$("#save").attr("disabled" , "disabled");$("#save").attr("style","background-color:#EAEAEA");
   			cus_no.disabled();
   			pact_code.disabled();
   		}
	} 
	  //日期
	var initfrom = function(){
		ret_plan_date = $("#ret_plan_date").etDatepicker({
			defaultDate: "${ret_plan_date}",
			onChange: function (date) {
		  		var start = recDate.getValue();
		  		if(start > date){
		  			ret_plan_date.setValue(start);
		  		}
		  	}
		});
		recDate = $("#date").etDatepicker({
			defaultDate: "${date}",todayButton: false
		});
	};
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">付款编号：</td>
			<td class="ipt"><input id="pay_code" type="text" disabled="disabled" style="background-color: #EAEAEA" value="${entity.pay_code }"/></td>
			<td class="label no-empty" style="width: 100px;">供应商：</td>
			<td class="ipt"><select id="cus_no" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt"><select id="pact_code" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">付款日期：</td>
			<td class="ipt"><input id="date" type="text"/></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">付款方式：</td>
			<td class="ipt"><select id="pay_way" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">付款金额：</td>
			<td class="ipt"><input id="money" type="text" value="${entity.money }" maxlength="15" ></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">支票号码：</td>
			<td class="ipt"><input id="cheq_no" type="text" value="${entity.cheq_no }"/></td>
			<td class="label  no-empty" style="width: 140px;">计划归还日期：</td>
			<td class="ipt"><input id="ret_plan_date" type="text" /></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">摘要：</td>
			<td class="ipt" colspan="5"><input id="note" type="text" style="width: 550px" value="${entity.note }"> </td>
		</tr>
	</table>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
	<div>
	 <table class="table-layout">
	  	<tr>
	  		<td class="label" style="width: 100px;">制单人：</td>
	  		<td class="ipt">${user_name }</td>
	  		<td class="label" style="width: 100px;">确认人：</td>
	  		<td class="ipt">${entity.confirmer_name }</td>
	  	</tr>
	  	<tr>
	  		<td class="label" style="width: 100px;">制单日期：</td>
	  		<td class="ipt"><span id="today"></span>${make_date }</td>
	  		<td class="label" style="width: 100px;">确认日期：</td>
	  		<td class="ipt"><span id="today"></span>${confirm_date }</td>
	  	</tr>
	  </table>
	</div>
</body>
</html>

