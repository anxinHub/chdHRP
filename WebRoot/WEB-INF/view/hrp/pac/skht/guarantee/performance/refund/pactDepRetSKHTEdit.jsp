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
var cus_id,cus_no;
	var save = function() {
		formValidate = $.etValidate({
			items : [ 
				{el : $("#cus_no"),required : true},
				{el : $("#pact_code"),required : true},
				{el : $("#date"),required : true},
				{el : $("#pay_way"),required : true},
				{el : $("#money"),required : true},
				{el : $("#note"),required : true},
				]
		});
		if(!formValidate.test()){
			return;
		};
		ajaxPostData({
			url : 'updatePactDepRetSKHT.do',
			data : {
				ret_code : $("#ret_code").val(),
				cus_no : $("#cus_no").val(),
				cus_id : cus_id,
				pact_code : $("#pact_code").val(),
				date : $("#date").val(),
				pay_way : $("#pay_way").val(),
				money : $("#money").val(),
				cheq_no : $("#cheq_no").val(),
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
					 defaultValue: 'none',
					 options:result ,
					 onItemAdd:function(value, $item){
						 for(var i = 0;i<result.length;i++){
							 var obj = result[i];
							 if(value == obj.id){
								 cus_id = obj.cus_id;
							 }
						 }
						pact_code.setValue(0);
				    	pact_code.reload({url: 'queryPactSKHTSelectForRet.do?isCheck=false&cus_no='+value,defaultValue: "none"})
			      	 }  
				 });
			  },
		});
    	pact_code = $("#pact_code").etSelect({
    		url: 'queryPactSKHTSelectForRet.do?isCheck=false',
    		defaultValue: "none",
    		onItemAdd:function(value, $item){
				 ajaxPostData({
           		 	url: 'queryPactSKHTForRet.do?isCheck=false',
           		 	data:{
           		 		pact_code:value
           		 	},
           			success: function (result) {
           				recDate = $("#date").etDatepicker({
           					defaultDate: result.start_date,
           					maxDate:result.start_date
           				});
						$("#money").val(result.deposit_money);
           			},
           			error :function(){
           				$("#money").val("");
           				pact_code.setValue(0);
           			}
           		});
	      	 }  
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
   		cus_no.disabled();
   		pact_code.disabled();
		if("01" != "${entity.state}"){
   			pay_way.disabled();
   			$("#date").attr("disabled" , "disabled");
   			$("#note").attr("disabled" , "disabled");$("#note").attr("style","background-color:#EAEAEA;width: 510px");
   			$("#cheq_no").attr("disabled" , "disabled");$("#cheq_no").attr("style","background-color:#EAEAEA");
   			$("#money").attr("disabled" , "disabled");$("#money").attr("style","background-color:#EAEAEA");
   			$("#save").attr("disabled" , "disabled");$("#save").attr("style","background-color:#EAEAEA");
   		}
	} 
	
	  //日期
	var initfrom = function(){
		recDate = $("#date").etDatepicker({
			defaultDate: "${date}",
		});
		
	};
</script>
</head>

<body style="overflow: scroll; ">
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">收回编号：</td>
			<td class="ipt"><input id="ret_code" type="text" disabled="disabled" style="background-color: #EAEAEA" value="${entity.ret_code }"/></td>
			<td class="label no-empty" style="width: 100px;">客户：</td>
			<td class="ipt"><select id="cus_no" style="width: 180px;"></select></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">合同名称：</td>
			<td class="ipt"><select id="pact_code" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">收回日期：</td>
			<td class="ipt"><input id="date" type="text"/></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">收回方式：</td>
			<td class="ipt"><select id="pay_way" style="width: 180px;"></select></td>
			<td class="label no-empty" style="width: 100px;">收回金额：</td>
			<td class="ipt"><input id="money" type="text" value="${entity.money }"></td>
		</tr>
		<tr>
			<td class="label" style="width: 100px;">支票号码：</td>
			<td class="ipt"><input id="cheq_no" type="text" value="${entity.cheq_no }"/></td>
		</tr>
		<tr>
			<td class="label no-empty" style="width: 100px;">摘要：</td>
			<td class="ipt" colspan="5"><input id="note" type="text" style="width: 510px" value="${entity.note }"> </td>
		</tr>
	</table>
	<div class="button-group">
	  <button id="save">保存</button>
	  <button id="cancle">取消</button>
	</div>
</body>
</html>

